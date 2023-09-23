import { Loader, FormComposerV2, Header } from "@egovernments/digit-ui-react-components";
import React, { useState, useEffect } from "react";
import { useTranslation } from "react-i18next";
import { useHistory } from "react-router-dom";
import CreateMeasurement from "./CreateMeasurement";
import _ from "lodash";
const UpdateMeasurement = () => {
    const tenantId = Digit.ULBService.getCurrentTenantId();
    const { t } = useTranslation();
    const history = useHistory();


    const data = {}

    return (
        <div>

        </div>
    );
};
export default UpdateMeasurement;