import useInbox from "../useInbox";


const useWorksInbox = ({ tenantId, _filters, config }) => {

    //you can take all the parameters from here
    const { estimateNumber,limit,offset,...rest } = _filters
    const USER_UUID = Digit.UserService.getUser()?.info?.uuid;

    const filters = {
        tenantId,
        processSearchCriteria: {
            moduleName: "estimate-service",
            tenantId,
            businessIds:[
                estimateNumber
            ],
            businessService: [
                "estimate-approval-2"
            ],
            // uuid:USER_UUID,
        },
        moduleSearchCriteria: {
            // ...(mobileNumber ? { mobileNumber } : {}),
            // ...(applicationNumber ? { applicationNumber } : {}),
            // ...(sortBy ? { sortBy } : {}),
            // ...(sortOrder ? { sortOrder } : {}),
            // ...(locality?.length > 0 ? { locality: locality.map((item) => item.code.split("_").pop()).join(",") } : {}),

            // @Burhan Add all the search params here. I have not added because pintu has not configured all of them currently
        },
        limit,
        offset
    }
    return useInbox({tenantId, filters, config})
}

export default useWorksInbox;