import useInbox from "../useInbox";


const useWorksInbox = ({ tenantId, filters, config }) => {

    //you can take all the parameters from here
    const { estimateNumber,limit,offset,...rest } = filters
    const USER_UUID = Digit.UserService.getUser()?.info?.uuid;

    const _filters = {
        tenantId,
        processSearchCriteria: {
            moduleName: "estimate-service",
            businessService: [
                "estimate-approval-2"
            ],
            uuid:USER_UUID,
        },
        moduleSearchCriteria: {
            // ...(mobileNumber ? { mobileNumber } : {}),
            // ...(applicationNumber ? { applicationNumber } : {}),
            // ...(sortBy ? { sortBy } : {}),
            // ...(sortOrder ? { sortOrder } : {}),
            // ...(locality?.length > 0 ? { locality: locality.map((item) => item.code.split("_").pop()).join(",") } : {}),

            //@Burhan Add all the search params here. I have not added because pintu has not configured all of them currently
        },
        limit,
        offset
    }

}

export default useWorksInbox;