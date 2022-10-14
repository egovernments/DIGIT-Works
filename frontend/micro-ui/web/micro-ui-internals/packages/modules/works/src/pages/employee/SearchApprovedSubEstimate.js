import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { useTranslation } from "react-i18next";
import { Fragment } from "react";
import { Toast } from "@egovernments/digit-ui-react-components";


const SearchApprovedSubEstimate = () => {

    const tenantId = Digit.UserService.getUser()?.info?.tenantId;

    const [payload, setPayload] = useState(null)
    const [showToast, setShowToast] = useState(null);
    const {t} = useTranslation()
    const onSubmit = (_data) => {

        //show toast error if no params are added
        if (_data.estimateNumber === "" && _data.adminSanctionNumber === "" && !_data.department && !_data.estimateDetailNumber && !_data.fromProposalDate && !_data.toProposalDate) {
            setShowToast({ warning: true, label: "ERR_PT_FILL_VALID_FIELDS" });
            setTimeout(() => {
                setShowToast(false);
            }, 3000);
            return
        }
        
        var fromProposalDate = new Date(_data?.fromProposalDate);
        fromProposalDate?.setSeconds(fromProposalDate?.getSeconds() - 19800);
        var toProposalDate = new Date(_data?.toProposalDate);
        toProposalDate?.setSeconds(toProposalDate?.getSeconds() + 86399 - 19800);
        const data = {
            ..._data,
            ...(_data.toProposalDate ? { toProposalDate: toProposalDate?.getTime() } : {}),
            ...(_data.fromProposalDate ? { fromProposalDate: fromProposalDate?.getTime() } : {}),
        };
        setPayload(
            Object.keys(data)
                .filter((k) => data[k])
                .reduce((acc, key) => ({ ...acc, [key]: typeof data[key] === "object" ? data[key].code : data[key] }), {})
        );
        // console.log(Object.keys(data)
        //     .filter((k) => data[k])
        //     .reduce((acc, key) => ({ ...acc, [key]: typeof data[key] === "object" ? data[key].code : data[key] }), {}))
        
            
        //make the payload according to the swagger
    }


    //call API here and write the corresponding service and hook code 
    const config = {
        enabled: !!(payload && Object.keys(payload).length > 0),
    };

    // const { data, isLoading, isSuccess } = Digit.Hooks.works.useEstimateSearch({
    //     tenantId:"pb.jalandhar",
    //     filters: payload,
    //     config,
    // });

    const dummyResult = {
        "estimates": [
            {
                "id": "251c51eb-e970-4e01-a99a-70136c47a934",
                "tenantId": "pb.jalandhar OR dwss",
                "estimateNumber": "EST/2022-23/010",
                "adminSanctionNumber": "ASE/2022-23/110",
                "proposalDate": 1658222690000,
                "status": "ACTIVE",
                "estimateStatus": "CREATED",
                "subject": "Construct new schools",
                "requirementNumber": "File-18430283",
                "description": "Construct new schools",
                "department": "string",
                "location": "string",
                "workCategory": "string",
                "beneficiaryType": "string",
                "natureOfWork": "string",
                "typeOfWork": "string",
                "subTypeOfWork": "string",
                "entrustmentMode": "string",
                "fund": "string",
                "function": "string",
                "budgetHead": "string",
                "scheme": "string",
                "subScheme": "string",
                "totalAmount": 1.5,
                "estimateDetails": [
                    {
                        "id": "251c51eb-e970-4e01-a99a-70136c47a934",
                        "estimateDetailNumber": "SUB-EST/2022-23/010",
                        "name": "string",
                        "amount": 1.5
                    }
                ],
                "auditDetails": {
                    "createdBy": "string",
                    "lastModifiedBy": "string",
                    "createdTime": 0,
                    "lastModifiedTime": 0
                }
            },
            {
                "id": "251c51eb-e970-4e01-a99a-70136c47a2322",
                "tenantId": "pb.jalandhar OR dwss",
                "estimateNumber": "EST/2022-23/011",
                "adminSanctionNumber": "ASE/2022-23/111",
                "proposalDate": 1658222690002,
                "status": "ACTIVE",
                "estimateStatus": "CREATED",
                "subject": "Construct new Colleges",
                "requirementNumber": "File-18430284",
                "description": "Construct new Colleges",
                "department": "string",
                "location": "string",
                "workCategory": "string",
                "beneficiaryType": "string",
                "natureOfWork": "string",
                "typeOfWork": "string",
                "subTypeOfWork": "string",
                "entrustmentMode": "string",
                "fund": "string",
                "function": "string",
                "budgetHead": "string",
                "scheme": "string",
                "subScheme": "string",
                "totalAmount": 2.5,
                "estimateDetails": [
                    {
                        "id": "251c51eb-e970-4e01-a99a-70136c47a934",
                        "estimateDetailNumber": "SUB-EST/2022-23/011",
                        "name": "string",
                        "amount": 2.5
                    }
                ],
                "auditDetails": {
                    "createdBy": "string",
                    "lastModifiedBy": "string",
                    "createdTime": 0,
                    "lastModifiedTime": 0
                }
            }
        ]
    }

    const { isLoading: isLoadingEstimateSearch, isError: isErrorEstimateSearch, data: estimateSearchResponse, isSuccess: estimateSearchSuccess } = Digit.Hooks.works.useSearchApprovedEstimates({ tenantId: Digit.ULBService.getCurrentTenantId(), filters: { ...payload }, config: { enabled: !!payload }} );
    
    
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
            {showToast && (
                <Toast
                error={showToast.error}
                warning={showToast.warning}
                label={t(showToast.label)}
                onClose={() => {
                    setShowToast(null);
                }}
                />
            )}
            <SearchApplicationApproved onSubmit={onSubmit} data={getData()} tenantId={tenantId} count={isLoadingEstimateSearch?.length} resultOk={isResultsOk()} isLoading={isLoadingEstimateSearch}/>
      </Fragment>
        
    )
}

export default SearchApprovedSubEstimate