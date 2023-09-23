import { Loader, FormComposerV2, Header } from "@egovernments/digit-ui-react-components";
import React, { useState, useEffect } from "react";
import { useTranslation } from "react-i18next";
import { useHistory } from "react-router-dom";
// import CreateMeasurement from "./CreateMeasurement";
import _ from "lodash";
import { transformMeasurementData } from "../../utils/transformMeasurementData";
const UpdateMeasurement = () => {
    const tenantId = Digit.ULBService.getCurrentTenantId();
    const { t } = useTranslation();
    const history = useHistory();

    const [data, setData] = useState(null);
    // get MBNumber from the url
    const searchparams = new URLSearchParams(location.search);
    const mbNumber = searchparams.get("mbNumber");

    useEffect(() => {
        const fetchData = async () => {
            try {
                // Call the utility function to fetch and transform measurement data
                const data = await transformMeasurementData(mbNumber);

                // Update state with the transformed data
                setData(data);
            } catch (error) {
                // Handle any errors here
                console.error('Error:', error);
            }
        };

        // Trigger the data fetching when the component mounts
        fetchData();
    }, []);

    return (
        <div>
            <h2>jhdgfhgfhgf</h2>
        </div>
    );
};
export default UpdateMeasurement;