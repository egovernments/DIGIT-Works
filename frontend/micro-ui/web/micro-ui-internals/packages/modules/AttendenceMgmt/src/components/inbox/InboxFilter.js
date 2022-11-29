import React, {useCallback, useEffect, useState} from 'react';
import { useTranslation } from "react-i18next";
import { CloseSvg, DateRangeNew, FilterIcon, FormComposer, RefreshIcon } from "@egovernments/digit-ui-react-components";


const InboxFilter = ({type, onFilterChange, onClose}) => {
  const { t } = useTranslation();
  const [localSearchParams, setLocalSearchParams] = useState(null);
  const [disabled, setDisabled] = useState(true)

  useEffect(() => {
    localSearchParams ? setDisabled(false) : setDisabled(true)
  }, [localSearchParams])
  
  const configs = [{
    head: "",
    body: [
      {
        type: "custom",
        isMandatory: false,
        withoutLabel: true,
        populators: {
          name: "musterRolldateRange",
          customProps: { t, optionKey: "i18nKey" },
          component: (props, customProps) => (
            <DateRangeNew 
              {...customProps} 
              values={localSearchParams?.range} 
              label="Muster Roll Date Range" 
              onFilterChange={handleChange} 
              customStyles={{marginBottom: '0px', fontSize: '16px'}}
            />
          )
        },
      }
    ],
  }]

  const handleChange = useCallback((data) => {
    setLocalSearchParams(() => ({  ...data }));
  }, [])

  const clearAll = () => {
    setLocalSearchParams(() => null)
  }

  const onSubmit = () => {
    //TODO: code to filter on click of apply
    let filterParams = localSearchParams
    for(var key in filterParams){
      if(filterParams[key]=== undefined){
        delete filterParams[key]
      }
    }
    onFilterChange(filterParams)
  }

  return (
    <React.Fragment> 
      <div className="filter">
        <div className="filter-card">
          <div className="heading" style={{ alignItems: "center" }}>
            <div className="filter-label" style={{ display: "flex", alignItems: "center" }}>
                <FilterIcon/>
                <span style={{ marginLeft: "8px", fontWeight: "normal" }}>{"Filters"}:</span>
            </div>
            {type === "desktop" && (
              <span className="clear-search" onClick={clearAll} style={{ border: "1px solid #e0e0e0", padding: "6px" }}>
                <RefreshIcon/>
              </span>
            )}
            {type === "mobile" && (
              <span onClick={onClose}>
                <CloseSvg />
              </span>
            )}
          </div>
          <div style={{marginTop:"20px"}}>
            <FormComposer
              label={'Apply'}
              config={configs}
              onSubmit={onSubmit}
              buttonStyle={{marginTop: "24px"}}
              submitInForm
              noCardStyle
              isDisabled={disabled}
            />
          </div>
        </div>
      </div>
    </React.Fragment>
  )
}

export default InboxFilter;