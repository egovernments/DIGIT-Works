import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { useTranslation } from "react-i18next";
import { Fragment } from "react";
import { Toast } from "@egovernments/digit-ui-components";


const SearchApprovedSubEstimate = () => {

    const tenantId = Digit.UserService.getUser()?.info?.tenantId;

    const [showTable, setShowTable] = useState(true)
    const onClearSearch = (isShow = true) => {
        setShowTable(isShow);
    }

    const [payload, setPayload] = useState(null)
    const [showToast, setShowToast] = useState(null);
    const {t} = useTranslation()
    const onSubmit = (_data) => {
        //show toast error if no params are added
        var fromProposalDate = new Date(_data?.fromProposalDate);
        fromProposalDate?.setSeconds(fromProposalDate?.getSeconds() - 19800);
        var toProposalDate = new Date(_data?.toProposalDate);
        toProposalDate?.setSeconds(toProposalDate?.getSeconds() + 86399 - 19800);
        const data = {
            ..._data,
            ...(_data.toProposalDate ? { toProposalDate: toProposalDate?.getTime() } : {}),
            ...(_data.fromProposalDate ? { fromProposalDate: fromProposalDate?.getTime() } : {}),
        };
 
        if (data.estimateNumber === "" && data.adminSanctionNumber === "" && !data.department && !data.estimateDetailNumber && !data.fromProposalDate && !data.toProposalDate) {
            setShowToast({ type:"warning", label: "ERR_PT_FILL_VALID_FIELDS" });
            setTimeout(() => {
                setShowToast(false);
            }, 3000);
            return
        }

        setPayload(
            Object.keys(data)
                .filter((k) => data[k])
                .reduce((acc, key) => ({ ...acc, [key]: typeof data[key] === "object" ? data[key].code : data[key] }), {})
        );
        setShowTable(true)          
    }


    //call API here and write the corresponding service and hook code 
    const config = {
        enabled: !!(payload && Object.keys(payload).length > 0),
        cacheTime:0
    };

    // const { data, isLoading, isSuccess } = Digit.Hooks.works.useEstimateSearch({
    //     tenantId:"pb.jalandhar",
    //     filters: payload,
    //     config,
    // });

    const { isLoading: isLoadingEstimateSearch, isError: isErrorEstimateSearch, data: estimateSearchResponse, isSuccess: estimateSearchSuccess } = Digit.Hooks.works.useSearchApprovedEstimates({ tenantId: Digit.ULBService.getCurrentTenantId(), filters: { ...payload }, config} );
    
    
    const isResultsOk = () => {
        return estimateSearchResponse?.length > 0 ? true : false;
    }

    const getData = () => {
        if (estimateSearchResponse?.length == 0) {
            return { display: "ES_COMMON_NO_DATA" }
        } else if (estimateSearchResponse?.length > 0) {
            return estimateSearchResponse
        } else {
            return [];
        }
    }
    const SearchApplicationApproved = Digit.ComponentRegistryService.getComponent("SearchApprovedSubEs");
    return (
        <Fragment>
            <SearchApplicationApproved 
                onSubmit={onSubmit} 
                data={getData()} 
                tenantId={tenantId} 
                count={isLoadingEstimateSearch?.length} 
                resultOk={isResultsOk()} 
                isLoading={isLoadingEstimateSearch} 
                onClearSearch={onClearSearch} 
                showTable={showTable}
            />
            {showToast && (
                <Toast
                type={showToast?.type}
                label={t(showToast.label)}
                onClose={() => {
                    setShowToast(null);
                }}
                />
            )}
      </Fragment>
        
    )
}

export default SearchApprovedSubEstimate