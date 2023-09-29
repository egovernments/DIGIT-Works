import { Button, CustomDropdown, Dropdown } from '@egovernments/digit-ui-react-components';
import React, { useEffect, useState } from 'react'
import { useTranslation } from 'react-i18next'
import { useHistory } from 'react-router-dom/cjs/react-router-dom.min';
import EstimateDropdown from './EstimateDropdown';
import SearchBar from './SearchBar';

const searchSor = ({props}) => {
    const { t } = useTranslation();
    const history = useHistory();
    const tenantId = Digit.ULBService.getCurrentTenantId()
    const [stateData, setStateData] = useState({});
    const [selectedSOR, setSelectedSOR] = useState(null);
    const {ref} = props;

    useEffect(() => {
        console.log(selectedSOR)
    }
    ,[selectedSOR]);

  return (
    <div ref={ref}>
        <EstimateDropdown
            label="SOR Type"
            stateData={stateData}
            setStateData={setStateData}
            schemaCode={"WORKS_SOR.Type"}
            type = "SORType"
        />
        <EstimateDropdown
            label="SOR Sub Type"
            stateData={stateData}
            setStateData={setStateData}
            schemaCode={"WORKS_SOR.SubType"}
            type="SORSubType"
        />
        <EstimateDropdown
            label="SOR Variant"
            stateData={stateData}
            setStateData={setStateData}
            schemaCode={"WORKS_SOR.Variant"}
            type="SORVariant"
        />

        <SearchBar selectedSOR={selectedSOR} setSelectedSOR={setSelectedSOR} />
    </div>
  )
}

export default searchSor