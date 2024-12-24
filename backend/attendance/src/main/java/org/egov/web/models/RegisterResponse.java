package org.egov.web.models;

import lombok.*;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class RegisterResponse {
    private List<AttendanceRegister> attendanceRegisters;
    private long totalRows;
    private long approvedCount;
    private long pendingCount;
}
