import React from "react";
import { Link } from "react-router-dom";
import { useTranslation } from "react-i18next";
import { Button} from "@egovernments/digit-ui-components";

export const statusBasedNavigation = ( status, contractOrProjectNumber, measurementOrEstimateNumber, tenantId, value, mode = "MEASUREMENT", businessId ) => {
    const { t } = useTranslation();
    
    let linkTo = `/${window?.contextPath}/employee/measurement/update?tenantId=${tenantId}&workOrderNumber=${contractOrProjectNumber}&mbNumber=${measurementOrEstimateNumber}`;

        if (status !== "DRAFTED") {
            linkTo = `/${window?.contextPath}/employee/measurement/view?tenantId=${tenantId}&workOrderNumber=${contractOrProjectNumber}&mbNumber=${measurementOrEstimateNumber}`;
        }
        
    if(mode === "REVISION-ESTIMATE")
    {
        linkTo = `/${window?.contextPath}/employee/estimate/update-revision-detailed-estimate?tenantId=${tenantId}&revisionNumber=${measurementOrEstimateNumber}&estimateNumber=${businessId}&projectNumber=${contractOrProjectNumber}&isEditRevisionEstimate=${true}`;

        if (status !== "DRAFT") {
            linkTo = `/${window?.contextPath}/employee/estimate/estimate-details?tenantId=${tenantId}&revisionNumber=${measurementOrEstimateNumber}&estimateNumber=${businessId}&projectNumber=${contractOrProjectNumber}`;
        }
    }

    if(mode === "ESTIMATE")
    {
        linkTo = `/${window?.contextPath}/employee/estimate/update-detailed-estimate?tenantId=${tenantId}&estimateNumber=${measurementOrEstimateNumber}&projectNumber=${contractOrProjectNumber}&isEdit=${true}`;

        if (status !== "DRAFT") {
            linkTo = `/${window?.contextPath}/employee/estimate/estimate-details?tenantId=${tenantId}&estimateNumber=${measurementOrEstimateNumber}&projectNumber=${contractOrProjectNumber}`;
        }
    }
    
        
    

    return (
        <Link to={linkTo}>
            <Button
                className=""
                iconFill=""
                label={value ? value : t("ES_COMMON_NA")}
                size="medium"
                style={{ padding: "0px" }}
                title=""
                variation="link"
              />
        </Link>
    );
};