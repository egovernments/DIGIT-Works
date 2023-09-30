import { Button, CustomDropdown, Dropdown } from '@egovernments/digit-ui-react-components';
import React, { useEffect, useState } from 'react'
import { useTranslation } from 'react-i18next'
import { useHistory } from 'react-router-dom/cjs/react-router-dom.min';
import EstimateDropdown from './EstimateDropdown';
import SearchBar from './SearchBar';
import { transform } from 'lodash';

const searchSor = (props) => {
    // console.log("props", props);
    const { t } = useTranslation();
    const history = useHistory();
    const tenantId = Digit.ULBService.getCurrentTenantId()
    const [stateData, setStateData] = useState({});
    const [selectedSOR, setSelectedSOR] = useState(null);
    const {ref,register,setValue, formData} = props;

    register("searchSor", stateData);
    useEffect(() => {
        // console.log("selectedSOR", selectedSOR);
        setStateData({
                ...stateData,
                selectedSor: selectedSOR
            });
        setValue("searchSor", stateData);
    }
    ,[selectedSOR]);


    const transformSOR = (sor) => {
        const transformedSOR = {
            sNo: 1,
            description: sor?.description,
            uom: sor?.uom,
            approvedQuantity: sor?.quantity,
            consumedQ: sor?.quantity,
            rate: sor?.rate,
            amount: sor?.rate,
            measures: sor?.measures,
            targetId: sor?.id,
        }
        return transformedSOR;
    }
  return (
    <div ref={ref}>
        <EstimateDropdown
            label="SOR Type"
            stateData={stateData}
            setStateData={setStateData}
            schemaCode={"WORKS-SOR.Type"}
            type = "SORType"
        />
        <EstimateDropdown
            label="SOR Sub Type"
            stateData={stateData}
            setStateData={setStateData}
            schemaCode={"WORKS-SOR.SubType"}
            type="SORSubType"
        />
        <EstimateDropdown
            label="SOR Variant"
            stateData={stateData}
            setStateData={setStateData}
            schemaCode={"WORKS-SOR.Variant"}
            type="SORVariant"
        />

        <SearchBar stateData={stateData} selectedSOR={selectedSOR} setSelectedSOR={setSelectedSOR} />
        <Button label="Add" onButtonClick={() => {
            // console.log("stateData", stateData);
            const sor = transformSOR(stateData?.selectedSor);
            formData.SOR.push(sor);
            console.log("formData", formData);
        }} />
    </div>
  )
}

export default searchSor