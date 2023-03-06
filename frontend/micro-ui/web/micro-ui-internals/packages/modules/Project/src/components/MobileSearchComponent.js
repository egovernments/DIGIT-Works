import React, { useContext, useEffect, useState } from "react";
import { useForm } from "react-hook-form";
import { useTranslation } from "react-i18next";
import { MobileInboxContext } from "./MobileInboxContext";
import { CloseSvg, SearchIcon, FilterIcon, ActionBar, ApplyFilterBar, RenderFormFields, Toast } from "@egovernments/digit-ui-react-components";

export const MobileSearchComponent = ({ uiConfig, header = "", screenType = "search", fullConfig, data, onClose}) => {
  const { t } = useTranslation();
  const { state, dispatch } = useContext(MobileInboxContext)
  const [showToast,setShowToast] = useState(null)
  let updatedFields = [];
  const {apiDetails} = fullConfig

  if (fullConfig?.postProcessResult){
    //conditions can be added while calling postprocess function to pass different params
    Digit?.Customizations?.[apiDetails?.masterName]?.[apiDetails?.moduleName]?.postProcess(data, uiConfig) 
  }

  const {
    register,
    handleSubmit,
    setValue,
    getValues,
    reset,
    watch,
    control,
    formState,
    errors,
    setError,
    clearErrors,
  } = useForm({
    defaultValues: uiConfig?.defaultValues,
  });
  const formData = watch();
  const checkKeyDown = (e) => {
    const keyCode = e.keyCode ? e.keyCode : e.key ? e.key : e.which;
    if (keyCode === 13) {
      e.preventDefault();
    }
  };

  useEffect(() => {
    updatedFields = Object.values(formState?.dirtyFields)
  }, [formState])

  const onSubmit = (data) => {
    onClose?.()
    if(updatedFields.length >= uiConfig?.minReqFields) {
     // here based on screenType call respective dispatch fn
      dispatch({
        type: uiConfig?.type === "search" ? "searchForm" : "filterForm",
        state: {
          ...data
        }
      })
    } else {
      setShowToast({ warning: true, label: t("ES_COMMON_MIN_SEARCH_CRITERIA_MSG") })
      setTimeout(closeToast, 3000);
    }
  }

  const clearSearch = () => {
    reset(uiConfig?.defaultValues)
    dispatch({
      type: "clearSearchForm",
      state: { ...uiConfig?.defaultValues }
      //need to pass form with empty strings 
    })
  }
 
  const closeToast = () => {
    setShowToast(null);
  }

const renderHeader = () => {
  switch(uiConfig?.type) {
    case "filter" : {
      return (
        <span className="header" style={{ display : "flex" }}>
          <span className="icon" style ={{ marginRight: "12px", marginTop: "5px",  paddingBottom: "3px" }}><FilterIcon/></span>
          <span style ={{ fontSize: "large" }}>{t("ES_COMMON_FILTER_BY")}:</span>
        </span>
      )
      }
    case "search" : {
      return (
        <span className="header" style={{ display : "flex" }}>
          <span className="icon" style ={{ marginRight: "12px", marginTop: "5px"}}><SearchIcon/></span>
          <span style ={{ fontSize: "large" }}>{t("ES_COMMON_SEARCH_BY")}</span>
        </span>
      )
    }
    default : {
      return (
        <span className="header" style={{ display : "flex" }}>
          <span className="icon" style ={{ marginRight: "12px", marginTop: "5px"}}><SearchIcon/></span>
          <span style ={{ fontSize: "large" }}>{t("ES_COMMON_SEARCH_BY")}</span>
        </span>
      )
    }
  }
}

  return (
    <React.Fragment>
      <div className="search-wrapper">
        <div className="popup-label" style={{ display: "flex", paddingBottom: "20px" }}>
                {renderHeader()}
                <span onClick={onClose}>
                  <CloseSvg />
                </span>        
        </div>
        <form onSubmit={handleSubmit(onSubmit)} onKeyDown={(e) => checkKeyDown(e)}>
          <div className={`search-field-wrapper ${screenType} ${uiConfig?.type}`}>
            <RenderFormFields 
              fields={uiConfig?.fields} 
              control={control} 
              formData={formData}
              errors={errors}
              register={register}
              setValue={setValue}
              getValues={getValues}
              setError={setError}
              clearErrors={clearErrors}
              labelStyle={{fontSize: "16px"}}
              apiDetails={apiDetails}
            />  
            <ActionBar className="clear-search-container">
             <ApplyFilterBar
               submit="submit"
               labelLink={t(uiConfig?.secondaryLabel)}
               buttonLink={t(uiConfig?.primaryLabel)}
              onClear={clearSearch}
              style={{ flex: 1 }}
              />
            </ActionBar>
          </div> 
        </form>
        { showToast && <Toast 
          error={showToast.error}
          warning={showToast.warning}
          label={t(showToast.label)}
          isDleteBtn={true}
          onClose={closeToast} />
        }
      </div>
    </React.Fragment>
  )
};