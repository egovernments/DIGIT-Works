import React from "react";
import { Dropdown, DatePicker, CloseSvg, SubmitBar, FilterIcon, RefreshIcon} from "@egovernments/digit-ui-react-components";
import { useTranslation } from "react-i18next";
import _ from "lodash";
import { Controller, useForm} from 'react-hook-form'

const Filter = ({ onFilterChange, ...props }) => {
  const { t } = useTranslation();
  const {register,control,watch,getValues,reset} = useForm()
  const tenant = Digit.ULBService.getStateId();
  
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

    let form = getValues();
    for (var key in form) {
      form[key] = ""
    }
    onFilterChange(form)

    reset(
      {
        woIssueFromDate:"",
        woIssueToDate:"",
        woAcceptedFromDate:"",
        woAcceptedToDate:""
      });
  };

  return (
    <React.Fragment> 
       <div className="filter">
        <div className="filter-card">
          <div className="heading" style={{ alignItems: "center" }}>
            <div className="filter-label" style={{ display: "flex", alignItems: "center" }}>
              <FilterIcon/>
              <span style={{ marginLeft: "8px", fontWeight: "normal" }}>{t("ES_COMMON_FILTER_BY")}</span>
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
                {t("WORKS_WO_ISSUED_FROM_DATE")}
              </div>
              <Controller
                name={`woIssueFromDate`}
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
                {t("WORKS_WO_ISSUED_TO_DATE")}
              </div>
              <Controller
                name={`woIssueToDate`}
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
              {t("WORKS_WO_ACCEPTED_FROM_DATE")}
            </div>
            <Controller
              name={`woAcceptedFromDate`}
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
              {t("WORKS_WO_ACCEPTED_TO_DATE")}
            </div>
            <Controller
              name={`woAcceptedToDate`}
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
              <SubmitBar onSubmit={() => applyLocalFilters()} label={t("ES_COMMON_APPLY")} />
            </div>
          </div>
        </div>
      </div>
    </React.Fragment>
  );
};

export default Filter;
