import React, {useMemo} from "react";
import { Dropdown, DatePicker, CloseSvg, SubmitBar, FilterIcon, RefreshIcon, TextInput} from "@egovernments/digit-ui-react-components";
import { useTranslation } from "react-i18next";
import _ from "lodash";
import { Controller, useForm} from 'react-hook-form'

const Filter = ({ onFilterChange, ...props }) => {
  const { t } = useTranslation();
  const {register,control,getValues,reset} = useForm()
  const currentContractType = useMemo(()=>[
    { code: "WORKS_WORK_ORDER", name: "WORKS_WORK_ORDER"},
    { code: "WORKS_PURCHASE_ORDER", name: "WORKS_PURCHASE_ORDER"}
],[t])
  
  const applyLocalFilters = () => {
    let form=getValues();
    for(var key in form){
      if(form[key]=== undefined){
        delete form[key]
      }
    }
    onFilterChange(form)
  };

  const clearAll = () => {
    reset(
      {
        estimateFromDate:"",
        estimateToDate:"",
        fund:"",
        function:"",
        budgetHead:"",
      });
  };
  let validation={}
  return (
    <React.Fragment> 
      <div className="filter">
        <div className="filter-card">
          <div className="heading" style={{ alignItems: "center" }}>
            <div className="filter-label" style={{ display: "flex", alignItems: "center" }}>
              <FilterIcon/>
              <span style={{ marginLeft: "8px", fontWeight: "normal" }}>{t("ES_COMMON_FILTER_BY")}:</span>
            </div>
            <div className="clearAll" onClick={clearAll}>
              {t("ES_COMMON_CLEAR_ALL")}
            </div>
            {props.type === "desktop" && (
              <span className="clear-search" onClick={clearAll} style={{ border: "1px solid #e0e0e0", padding: "6px" }}>
                <RefreshIcon/>
              </span>
            )}
            {props.type === "mobile" && (
              <span onClick={props.Close}>
                <CloseSvg />
              </span>
            )}
          </div>
          <div style={{marginTop:"20px"}}>
            <div>
              <div className="filter-label" style={{ fontWeight: "normal" }}>
                {t("WORKS_CONTRACT_FROM_DATE")}:
              </div>
              <Controller
                name={`contractFromDate`}
                control={control}
                rules={{ required: true }}
                render={(props) => {
                  return (
                  <DatePicker 
                  date={props.value} 
                  onChange={props.onChange}       
                  />
                  );
                }}
              />
            </div>
            <div>
              <div className="filter-label" style={{ fontWeight: "normal" }}>
                {t("WORKS_CONTRACT_TO_DATE")}:
              </div>
              <Controller
                name={`contractToDate`}
                control={control}
                rules={{ required: true }}
                render={(props) => {
                  return (
                  <DatePicker 
                  date={props.value} 
                  onChange={props.onChange}       
                  />
                  );
                }}
              />
            </div>
            <div>
              <div className="filter-label" style={{ fontWeight: "normal" }}>
                {t("WORKS_CONTRACT_TYPE")}:
              </div>
              <Controller
                name={'contractType'}
                control={control}
                rules={{ required: true }}
                render={(props) => {
                  return (
                    <Dropdown
                      option={currentContractType}
                      selected={props?.value}
                      optionKey={"name"}
                      t={t}
                      select={props?.onChange}
                      onBlur={props.onBlur}
                    />
                  );
                }}
              />
            </div>
            <div>
              <div className="filter-label" style={{ fontWeight: "normal" }}>
                {t("WORKS_FILE_NO")}:
              </div>
              <TextInput 
                  name="fileNo" 
                  inputRef={register()} 
                  {...(validation = {
                    isRequired: false,
                    pattern: "^[a-zA-Z0-9-_\/]*$",
                    type: "text",
                    title: t("ERR_INVALID_ESTIMATE_NO"),
                  })}
                />
            </div>
            <div>
              <div className="filter-label" style={{ fontWeight: "normal" }}>
                {t("WORKS_ESTIMATE_ID")}:
              </div>
              <TextInput 
                  name="estimateId" 
                  inputRef={register()} 
                  {...(validation = {
                    isRequired: false,
                    pattern: "^[a-zA-Z0-9-_\/]*$",
                    type: "text",
                    title: t("ERR_INVALID_ESTIMATE_NO"),
                  })}
                />
            </div>
            <div>
              <SubmitBar onSubmit={() => applyLocalFilters()} label={t("ES_COMMON_APPLY")} />
            </div>
          </div>
        </div>
      </div>
    </React.Fragment>
  );
};

export default Filter;
