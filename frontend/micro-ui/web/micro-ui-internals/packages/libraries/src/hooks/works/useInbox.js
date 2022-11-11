import useInbox from "../useInbox";


const useWorksInbox = ({ tenantId, _filters, config }) => {
    const { estimateNumber, 
        department, 
        typeOfWork,
        fund,
        budgetHead,
        fromProposalDate,
        toProposalDate,
        sortOrder,
        limit,
        offset,
        ...rest } = _filters
    const USER_UUID = Digit.UserService.getUser()?.info?.uuid;

    const filters = {
        tenantId,
        processSearchCriteria: {
            tenantId,
            businessService: [
                "estimate-approval-2"
            ],
            moduleName: "estimate-service",
            assignee:USER_UUID,
        },
        moduleSearchCriteria: {
            department,
            typeOfWork,
            fund,
            function: rest.function,
            budgetHead,
            estimateNumber,
            fromProposalDate,
            toProposalDate,
            sortOrder
        },
        limit,
        offset
    }
    return useInbox({tenantId, filters, config})
}

export default useWorksInbox;