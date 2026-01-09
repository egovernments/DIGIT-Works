package org.egov.digit.expense.calculator.util;

import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;
import org.egov.common.models.individual.Individual;
import org.egov.tracer.model.CustomException;
import org.egov.works.services.common.models.attendance.AttendanceRegister;
import org.egov.works.services.common.models.attendance.StaffPermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Validator for checking user permissions on attendance registers before bill generation.
 *
 * Implements the same permission validation logic as the Attendance service's validateUpdateAgainstDB method.
 * This ensures that only users who are associated with a register (as staff members) can initiate billing for that register.
 *
 * Design Principles:
 * - Single Responsibility: Focused solely on register permission validation
 * - Fail-Fast: Validates permissions before expensive operations like bill generation
 * - Scalable: Uses batch processing and efficient Set lookups
 * - Consistent: Mirrors the permission model used in Attendance service
 *
 * @author DIGIT Works Team
 */
@Component
@Slf4j
public class RegisterPermissionValidator {

    private final IndividualUtil individualUtil;
    private final AttendanceUtil attendanceUtil;

    @Autowired
    public RegisterPermissionValidator(IndividualUtil individualUtil, AttendanceUtil attendanceUtil) {
        this.individualUtil = individualUtil;
        this.attendanceUtil = attendanceUtil;
    }

    /**
     * Validates that the requesting user has permission to generate bills for the given registers.
     *
     * Permission is granted if:
     * 1. The user's individual ID is present in the register's staff list
     * 2. The register has staff permissions configured
     *
     * This validation prevents unauthorized users from initiating bill generation for registers
     * they don't have access to.
     *
     * @param requestInfo Request info containing user details
     * @param registerIds List of attendance register IDs to validate
     * @param tenantId Tenant ID
     * @param localityCode Locality code for fetching registers
     * @param projectId Project ID for context
     * @throws CustomException if user lacks permission for any register
     */
    public void validateUserPermissionForBillGeneration(
            RequestInfo requestInfo,
            List<String> registerIds,
            String tenantId,
            String localityCode,
            String projectId) {

        if (requestInfo == null || requestInfo.getUserInfo() == null) {
            log.error("RegisterPermissionValidator::validateUserPermissionForBillGeneration::RequestInfo or UserInfo is null");
            throw new CustomException("INVALID_REQUEST", "RequestInfo and UserInfo are mandatory for bill generation");
        }

        if (CollectionUtils.isEmpty(registerIds)) {
            log.debug("RegisterPermissionValidator::validateUserPermissionForBillGeneration::No registers to validate");
            return; // No registers to validate
        }

        Long userId = requestInfo.getUserInfo().getId();
        String userUuid = requestInfo.getUserInfo().getUuid();

        log.info("RegisterPermissionValidator::validateUserPermissionForBillGeneration::Validating permissions for user {} on {} registers for project {}",
                userUuid, registerIds.size(), projectId);

        // Step 1: Convert user ID to individual ID (required for staff permission check)
        String individualId;
        try {
            List<Individual> individuals = individualUtil.getIndividualDetailsFromUserId(userId, requestInfo, tenantId);

            if (CollectionUtils.isEmpty(individuals)) {
                log.error("RegisterPermissionValidator::validateUserPermissionForBillGeneration::No individual found for userId: {}", userId);
                throw new CustomException("INDIVIDUAL_NOT_FOUND",
                    "No individual mapping found for user. Cannot validate register permissions.");
            }

            individualId = individuals.get(0).getId();
            log.debug("RegisterPermissionValidator::validateUserPermissionForBillGeneration::Mapped userId {} to individualId {}", userId, individualId);

        } catch (CustomException e) {
            log.error("RegisterPermissionValidator::validateUserPermissionForBillGeneration::Failed to fetch individual for userId: {} - {}",
                userId, e.getMessage());
            throw new CustomException("PERMISSION_CHECK_FAILED",
                "Unable to validate user permissions: " + e.getMessage());
        }

        // Step 2: Fetch all registers and validate permissions
        try {
            int offset = 0;
            int batchSize = 100; // Process in batches for scalability
            boolean hasMoreRegisters = true;

            while (hasMoreRegisters) {
                // Fetch batch of registers
                List<AttendanceRegister> registers = attendanceUtil.fetchAttendanceRegister(
                    projectId, tenantId, requestInfo, localityCode, false, offset
                );

                if (CollectionUtils.isEmpty(registers)) {
                    hasMoreRegisters = false;
                    break;
                }

                // Filter to only the registers we need to validate
                List<AttendanceRegister> relevantRegisters = registers.stream()
                    .filter(register -> registerIds.contains(register.getId()))
                    .collect(Collectors.toList());

                // Validate permissions for this batch
                validateRegistersPermissions(relevantRegisters, individualId, userUuid);

                // Update offset for next batch
                offset += registers.size();
                hasMoreRegisters = registers.size() >= batchSize;
            }

            log.info("RegisterPermissionValidator::validateUserPermissionForBillGeneration::Successfully validated permissions for user {} on all {} registers",
                userUuid, registerIds.size());

        } catch (CustomException e) {
            // Re-throw permission exceptions
            throw e;
        } catch (Exception e) {
            log.error("RegisterPermissionValidator::validateUserPermissionForBillGeneration::Unexpected error validating permissions: {}",
                e.getMessage(), e);
            throw new CustomException("PERMISSION_VALIDATION_ERROR",
                "Failed to validate register permissions: " + e.getMessage());
        }
    }

    /**
     * Validates user permissions for a batch of registers.
     *
     * @param registers List of registers to validate
     * @param individualId Individual ID of the requesting user
     * @param userUuid UUID of the requesting user (for error messages)
     * @throws CustomException if user lacks permission for any register
     */
    private void validateRegistersPermissions(
            List<AttendanceRegister> registers,
            String individualId,
            String userUuid) {

        for (AttendanceRegister register : registers) {
            validateSingleRegisterPermission(register, individualId, userUuid);
        }
    }

    /**
     * Validates user permission for a single register.
     *
     * Permission check logic (mirrors Attendance service):
     * 1. If register has no staff configured -> DENY (configuration error)
     * 2. If user's individual ID is NOT in staff list -> DENY
     * 3. If user's individual ID IS in staff list -> ALLOW
     *
     * @param register Attendance register to validate
     * @param individualId Individual ID of the requesting user
     * @param userUuid UUID of the requesting user (for error messages)
     * @throws CustomException if user lacks permission
     */
    private void validateSingleRegisterPermission(
            AttendanceRegister register,
            String individualId,
            String userUuid) {

        // Check if register has staff permissions configured
        if (register.getStaff() == null || register.getStaff().isEmpty()) {
            log.warn("RegisterPermissionValidator::validateSingleRegisterPermission::Register {} has no staff configured. " +
                    "This may indicate a data integrity issue.", register.getId());

            // Fail-safe: If no staff configured, deny bill generation
            throw new CustomException("REGISTER_NO_STAFF_CONFIGURED",
                String.format("Register %s has no staff configured. Cannot validate permissions for bill generation.",
                    register.getId()));
        }

        // Extract staff individual IDs for efficient lookup
        Set<String> staffIndividualIds = register.getStaff().stream()
            .map(StaffPermission::getUserId)
            .collect(Collectors.toSet());

        // Check if user is in the staff list
        if (!staffIndividualIds.contains(individualId)) {
            log.error("RegisterPermissionValidator::validateSingleRegisterPermission::User {} (individual: {}) does not have permission " +
                    "to generate bills for register {}. Register staff: {}",
                    userUuid, individualId, register.getId(), staffIndividualIds);

            throw new CustomException("PERMISSION_DENIED",
                String.format("User %s does not have permission to generate bills for register %s. " +
                    "Only staff members associated with the register can initiate billing.",
                    userUuid, register.getId()));
        }

        log.debug("RegisterPermissionValidator::validateSingleRegisterPermission::User {} has valid permission for register {}",
            userUuid, register.getId());
    }

    /**
     * Convenience method for validating permissions for a single register.
     *
     * @param requestInfo Request info containing user details
     * @param registerId Attendance register ID to validate
     * @param tenantId Tenant ID
     * @param localityCode Locality code for fetching register
     * @param projectId Project ID for context
     * @throws CustomException if user lacks permission
     */
    public void validateUserPermissionForSingleRegister(
            RequestInfo requestInfo,
            String registerId,
            String tenantId,
            String localityCode,
            String projectId) {

        validateUserPermissionForBillGeneration(
            requestInfo,
            java.util.Collections.singletonList(registerId),
            tenantId,
            localityCode,
            projectId
        );
    }

    /**
     * Validates that the requesting user has permission to update review status for registers of a project.
     *
     * This validation mirrors the permission logic in Attendance service's validateUpdateAgainstDB method.
     * Permission requirements vary based on the target review status:
     * - PENDINGFORAPPROVAL: User must be APPROVER or EDITOR
     * - APPROVED: User must be APPROVER only
     *
     * Design: This validation is performed BEFORE processing begins to ensure fail-fast behavior
     * and prevent partial operations that would fail when calling the attendance service.
     *
     * @param requestInfo Request info containing user details
     * @param projectId Project ID for which registers are being updated
     * @param tenantId Tenant ID
     * @param reviewStatus Target review status (PENDINGFORAPPROVAL or APPROVED)
     * @param localityCode Locality code for fetching registers
     * @throws CustomException if user lacks permission for any register
     */
    public void validateUserPermissionForReviewStatusUpdate(
            RequestInfo requestInfo,
            String projectId,
            String tenantId,
            String reviewStatus,
            String localityCode) {

        if (requestInfo == null || requestInfo.getUserInfo() == null) {
            log.error("RegisterPermissionValidator::validateUserPermissionForReviewStatusUpdate::RequestInfo or UserInfo is null");
            throw new CustomException("INVALID_REQUEST", "RequestInfo and UserInfo are mandatory for review status update");
        }

        Long userId = requestInfo.getUserInfo().getId();
        String userUuid = requestInfo.getUserInfo().getUuid();

        log.info("RegisterPermissionValidator::validateUserPermissionForReviewStatusUpdate::Validating permissions for user {} to update review status to '{}' for project {}",
                userUuid, reviewStatus, projectId);

        // Step 1: Convert user ID to individual ID (required for staff permission check)
        String individualId;
        try {
            List<Individual> individuals = individualUtil.getIndividualDetailsFromUserId(userId, requestInfo, tenantId);

            if (CollectionUtils.isEmpty(individuals)) {
                log.error("RegisterPermissionValidator::validateUserPermissionForReviewStatusUpdate::No individual found for userId: {}", userId);
                throw new CustomException("INDIVIDUAL_NOT_FOUND",
                    "No individual mapping found for user. Cannot validate register permissions.");
            }

            individualId = individuals.get(0).getId();
            log.debug("RegisterPermissionValidator::validateUserPermissionForReviewStatusUpdate::Mapped userId {} to individualId {}", userId, individualId);

        } catch (CustomException e) {
            log.error("RegisterPermissionValidator::validateUserPermissionForReviewStatusUpdate::Failed to fetch individual for userId: {} - {}",
                userId, e.getMessage());
            throw new CustomException("PERMISSION_CHECK_FAILED",
                "Unable to validate user permissions: " + e.getMessage());
        }

        // Step 2: Fetch all registers for the project and validate review status permissions
        try {
            int offset = 0;
            int batchSize = 100; // Process in batches for scalability
            boolean hasMoreRegisters = true;
            int totalRegistersValidated = 0;

            while (hasMoreRegisters) {
                // Fetch batch of registers
                List<AttendanceRegister> registers = attendanceUtil.fetchAttendanceRegister(
                    projectId, tenantId, requestInfo, localityCode, false, offset
                );

                if (CollectionUtils.isEmpty(registers)) {
                    hasMoreRegisters = false;
                    break;
                }

                // Validate review status permissions for this batch
                for (AttendanceRegister register : registers) {
                    validateReviewStatusPermissionForRegister(register, individualId, userUuid, reviewStatus);
                    totalRegistersValidated++;
                }

                // Update offset for next batch
                offset += registers.size();
                hasMoreRegisters = registers.size() >= batchSize;
            }

            if (totalRegistersValidated == 0) {
                log.warn("RegisterPermissionValidator::validateUserPermissionForReviewStatusUpdate::No registers found for project {}. " +
                        "Review status update will be skipped.", projectId);
            } else {
                log.info("RegisterPermissionValidator::validateUserPermissionForReviewStatusUpdate::Successfully validated review status update permissions " +
                        "for user {} on {} registers for project {}", userUuid, totalRegistersValidated, projectId);
            }

        } catch (CustomException e) {
            // Re-throw permission exceptions
            throw e;
        } catch (Exception e) {
            log.error("RegisterPermissionValidator::validateUserPermissionForReviewStatusUpdate::Unexpected error validating permissions: {}",
                e.getMessage(), e);
            throw new CustomException("PERMISSION_VALIDATION_ERROR",
                "Failed to validate review status update permissions: " + e.getMessage());
        }
    }

    /**
     * Validates user permission to update review status for a list of registers.
     * This is an optimized version that works with pre-fetched registers to avoid duplicate API calls.
     *
     * @param registers Pre-fetched attendance registers
     * @param requestInfo Request info containing user details
     * @param reviewStatus Target review status (PENDINGFORAPPROVAL or APPROVED)
     * @param tenantId Tenant ID
     * @throws CustomException if user lacks permission for any register
     */
    public void validateUserPermissionForReviewStatusUpdateWithRegisters(
            List<AttendanceRegister> registers,
            RequestInfo requestInfo,
            String reviewStatus,
            String tenantId) {

        if (requestInfo == null || requestInfo.getUserInfo() == null) {
            log.error("RegisterPermissionValidator::validateUserPermissionForReviewStatusUpdateWithRegisters::RequestInfo or UserInfo is null");
            throw new CustomException("INVALID_REQUEST", "RequestInfo and UserInfo are mandatory");
        }

        if (CollectionUtils.isEmpty(registers)) {
            log.debug("RegisterPermissionValidator::validateUserPermissionForReviewStatusUpdateWithRegisters::No registers to validate");
            return;
        }

        Long userId = requestInfo.getUserInfo().getId();
        String userUuid = requestInfo.getUserInfo().getUuid();

        // Convert user ID to individual ID
        String individualId;
        try {
            List<Individual> individuals = individualUtil.getIndividualDetailsFromUserId(userId, requestInfo, tenantId);

            if (CollectionUtils.isEmpty(individuals)) {
                log.error("RegisterPermissionValidator::validateUserPermissionForReviewStatusUpdateWithRegisters::No individual found for userId: {}", userId);
                throw new CustomException("INDIVIDUAL_NOT_FOUND",
                    "No individual mapping found for user. Cannot validate register permissions.");
            }

            individualId = individuals.get(0).getId();
        } catch (CustomException e) {
            throw e;
        }

        // Validate review status permissions for all registers
        for (AttendanceRegister register : registers) {
            validateReviewStatusPermissionForRegister(register, individualId, userUuid, reviewStatus);
        }

        log.info("RegisterPermissionValidator::validateUserPermissionForReviewStatusUpdateWithRegisters::Successfully validated review status permissions for {} registers",
                registers.size());
    }

    /**
     * Validates user permission to update review status for a single register.
     *
     * Permission check logic (mirrors Attendance service validateUpdateAgainstDB):
     * 1. If register has no staff configured -> DENY
     * 2. If user's individual ID is NOT in staff list -> DENY
     * 3. If reviewStatus = "PENDINGFORAPPROVAL" -> User must be APPROVER or EDITOR
     * 4. If reviewStatus = "APPROVED" -> User must be APPROVER only
     *
     * @param register Attendance register to validate
     * @param individualId Individual ID of the requesting user
     * @param userUuid UUID of the requesting user (for error messages)
     * @param reviewStatus Target review status
     * @throws CustomException if user lacks permission
     */
    private void validateReviewStatusPermissionForRegister(
            AttendanceRegister register,
            String individualId,
            String userUuid,
            String reviewStatus) {

        // Check if register has staff permissions configured
        if (register.getStaff() == null || register.getStaff().isEmpty()) {
            log.error("RegisterPermissionValidator::validateReviewStatusPermissionForRegister::Register {} has no staff configured. " +
                    "Cannot update review status.", register.getId());
            throw new CustomException("INVALID_REGISTER_MODIFY",
                String.format("The user %s does not have permission to modify the register %s",
                    userUuid, register.getId()));
        }

        // Extract staff individual IDs and find the user's staff permission
        StaffPermission userStaff = register.getStaff().stream()
            .filter(staff -> individualId.equals(staff.getUserId()))
            .findFirst()
            .orElse(null);

        // Check if user is in the staff list
        if (userStaff == null) {
            log.error("RegisterPermissionValidator::validateReviewStatusPermissionForRegister::User {} (individual: {}) is not in staff list " +
                    "for register {}. Cannot update review status.", userUuid, individualId, register.getId());
            throw new CustomException("INVALID_REGISTER_MODIFY",
                String.format("The user %s does not have permission to modify the register %s",
                    userUuid, register.getId()));
        }

        // Validate staff type based on review status
        if ("PENDINGFORAPPROVAL".equalsIgnoreCase(reviewStatus)) {
            // For PENDINGFORAPPROVAL, user must be APPROVER or EDITOR
            if (userStaff.getStaffType() == null ||
                (!userStaff.getStaffType().name().equals("APPROVER") &&
                 !userStaff.getStaffType().name().equals("EDITOR"))) {

                log.error("RegisterPermissionValidator::validateReviewStatusPermissionForRegister::User {} (staffType: {}) does not have permission " +
                        "to set review status to PENDINGFORAPPROVAL for register {}. Required: APPROVER or EDITOR",
                        userUuid, userStaff.getStaffType(), register.getId());
                throw new CustomException("INVALID_REGISTER_MODIFY",
                    String.format("The user %s does not have permission to modify the register %s",
                        userUuid, register.getId()));
            }
        } else if ("APPROVED".equalsIgnoreCase(reviewStatus)) {
            // For APPROVED, user must be APPROVER only
            if (userStaff.getStaffType() == null ||
                !userStaff.getStaffType().name().equals("APPROVER")) {

                log.error("RegisterPermissionValidator::validateReviewStatusPermissionForRegister::User {} (staffType: {}) does not have permission " +
                        "to set review status to APPROVED for register {}. Required: APPROVER",
                        userUuid, userStaff.getStaffType(), register.getId());
                throw new CustomException("INVALID_REGISTER_MODIFY",
                    String.format("The user %s does not have permission to modify the register %s",
                        userUuid, register.getId()));
            }
        }

        log.debug("RegisterPermissionValidator::validateReviewStatusPermissionForRegister::User {} (staffType: {}) has valid permission " +
                "to update review status to '{}' for register {}",
                userUuid, userStaff.getStaffType(), reviewStatus, register.getId());
    }
}
