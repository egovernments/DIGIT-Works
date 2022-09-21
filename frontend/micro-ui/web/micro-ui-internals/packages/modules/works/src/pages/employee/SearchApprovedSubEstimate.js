import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { useTranslation } from "react-i18next";


const SearchApprovedSubEstimate = () => {

    const [payload, setPayload] = useState({})

    const onSubmit = (_data) => {


        var fromDate = new Date(_data?.fromProposalDate);
        fromDate?.setSeconds(fromDate?.getSeconds() - 19800);
        var toDate = new Date(_data?.toProposalDate);
        toDate?.setSeconds(toDate?.getSeconds() + 86399 - 19800);
        const data = {
            ..._data,
            ...(_data.toProposalDate ? { toDate: toDate?.getTime() } : {}),
            ...(_data.fromProposalDate ? { fromDate: fromDate?.getTime() } : {}),
        };
        setPayload(
            Object.keys(data)
                .filter((k) => data[k])
                .reduce((acc, key) => ({ ...acc, [key]: typeof data[key] === "object" ? data[key].code : data[key] }), {})
        );
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

    const SearchApplicationApproved = Digit.ComponentRegistryService.getComponent("SearchApprovedSubEs");
    return (
        <SearchApplicationApproved onSubmit={onSubmit} data={dummyResult.estimates} tenantId={"pb.jalandhar"} count={dummyResult.estimates.length} />
    )
}

export default SearchApprovedSubEstimate