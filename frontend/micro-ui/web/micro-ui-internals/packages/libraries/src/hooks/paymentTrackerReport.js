import { useTranslation } from "react-i18next";

const cyrb53 = (str, seed = 0) => {
    let h1 = 0xdeadbeef ^ seed, h2 = 0x41c6ce57 ^ seed;
    for(let i = 0, ch; i < str.length; i++) {
        ch = str.charCodeAt(i);
        h1 = Math.imul(h1 ^ ch, 2654435761);
        h2 = Math.imul(h2 ^ ch, 1597334677);
    }
    h1  = Math.imul(h1 ^ (h1 >>> 16), 2246822507);
    h1 ^= Math.imul(h2 ^ (h2 >>> 13), 3266489909);
    h2  = Math.imul(h2 ^ (h2 >>> 16), 2246822507);
    h2 ^= Math.imul(h1 ^ (h1 >>> 13), 3266489909);
  
    return 4294967296 * (2097151 & h2) + (h1 >>> 0);
};

export const paymentTrackerReport = (props) => {
    const { t } = useTranslation();
    var { isLoading, data, revalidate, isFetching, error } = Digit.Hooks.useCustomAPIHook(props);
    //search Project
    const requestrevisionCriteria = {
        url: "/wms/project/_search",
        params : {tenantId : Digit.ULBService.getCurrentTenantId() , includeAncestors : true},
        config : {
          cacheTime : 0,
          enabled : data ? true : false
        },
        body: {
            "inbox": {
                "moduleSearchCriteria": {
                    "tenantId": Digit.ULBService.getCurrentTenantId(),
                    "projectNumber": data?.aggsResponse?.projects.map((ob) => ob?.projectNumber) /* ["PJ/2023-24/11/000085"] */
                },
                "tenantId": Digit.ULBService.getCurrentTenantId(),
                "limit": 300,
                "offset": 0
        }},
        changeQueryName: "projectpaymenttracker"
      };
    const changeQueryName = requestrevisionCriteria.body.inbox.moduleSearchCriteria.projectNumber?.sort().join(",");
    requestrevisionCriteria.changeQueryName = data ? cyrb53(changeQueryName) : "projectpaymenttracker";
    
    const {isLoading: isProjectDetailsLoading, data: projectDetails} = Digit.Hooks.useCustomAPIHook(requestrevisionCriteria);

    if(data?.aggsResponse?.projects) {
        data.aggsResponse.projects = data?.aggsResponse?.projects?.map((ob) => {
            return {
                ...ob,
                wagebillsuccess : ob?.paymentDetails?.filter((obj) => obj?.billType?.includes("WAGE"))?.[0]?.paidAmount,
                wagebillFailed : ob?.paymentDetails?.filter((obj) => obj?.billType?.includes("WAGE"))?.[0]?.remainingAmount,
                purchasebillSuccess : ob?.paymentDetails?.filter((obj) => obj?.billType?.includes("PURCHASE"))?.[0]?.paidAmount,
                purchasebillFailed : ob?.paymentDetails?.filter((obj) => obj?.billType?.includes("PURCHASE"))?.[0]?.remainingAmount,
                supervisionbillSuccess : ob?.paymentDetails?.filter((obj) => obj?.billType?.includes("SUPERVISION"))?.[0]?.paidAmount,
                supervisionbillFailed : ob?.paymentDetails?.filter((obj) => obj?.billType?.includes("SUPERVISION"))?.[0]?.remainingAmount,
                project : projectDetails?.items?.filter((pj) => pj?.businessObject?.projectNumber === ob?.projectNumber)?.[0] /* projectDetails?.items?.[0] */
            }
        })
        data.stickyFooterRow = [
            {'name': '', 'value':'', style: {fontWeight: 'bold'}},
            {'name': 'total', 'value': t('PAGE_TOTAL'), style: {fontWeight: 'bold'}},
            {'name': 'estimatedAmount', 'value': 0, style: {fontWeight: 'bold', textAlign: 'right'}},
            {'name': 'wagebillsuccess', 'value': 0, style: {fontWeight: 'bold', textAlign: 'right'}},
            {'name': 'wagebillFailed', 'value': 0, style: {fontWeight: 'bold', textAlign: 'right'}},
            {'name': 'purchasebillSuccess', 'value': 0, style: {fontWeight: 'bold', textAlign: 'right'}},
            {'name': 'purchasebillFailed', 'value': 0, style: {fontWeight: 'bold', textAlign: 'right'}},
            {'name': 'supervisionbillSuccess', 'value': 0, style: {fontWeight: 'bold', textAlign: 'right'}},
            {'name': 'supervisionbillFailed', 'value': 0, style: {fontWeight: 'bold', textAlign: 'right'}}
        ]

        data?.aggsResponse?.projects?.forEach((ob, index) => {
            data.stickyFooterRow.forEach((row, index) => {
                if(index > 1 && typeof ob?.[row.name] === 'number') {
                    data.stickyFooterRow[index].value += ob?.[row.name] || 0;
                }
            })
        })
        for (let i = 2; i < data.stickyFooterRow.length; i++) {
            data.stickyFooterRow[i].value = "₹" + Digit.Utils.dss.formatterWithoutRound(parseFloat(data.stickyFooterRow[i].value || 0).toFixed(2),"number",undefined,true,undefined,2);
        }
    }

    return { isLoading : (isLoading || isProjectDetailsLoading), data, revalidate, isFetching, error };
}