import React, { useContext, useEffect, useState } from "react";
import { useForm } from "react-hook-form";
import { useTranslation } from "react-i18next";
import { MobileInboxContext } from "./MobileInboxContext";
import { CloseSvg, SubmitBar, SearchIcon, FilterIcon, ActionBar, RefreshIcon, RenderFormFields, Toast,LinkLabel } from "@egovernments/digit-ui-react-components";

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
    trigger,
    control,
    formState,
    errors,
    setError,
    clearErrors,
    unregister,
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

//   const handleFilterRefresh = () => {
//     reset(uiConfig?.defaultValues)
//     dispatch({
//       type: "clearFilterForm",
//       state: { ...uiConfig?.defaultValues }
//       //need to pass form with empty strings 
//     })
//   }

const renderHeader = () => {
  switch(uiConfig?.type) {
    case "filter" : {
      return (
        <span style={{ marginLeft: "8px", fontWeight: "normal",  display : "flex" }}><FilterIcon/>{t("ES_COMMON_FILTER_BY")}:</span>
      )
      }
    case "search" : {
      return (
        <span style={{ marginLeft: "8px", fontWeight: "normal",  display : "flex" }}><SearchIcon/>{t("ES_COMMON_SEARCH_BY")}</span>
      )
    }
    default : {
      return <span style={{ marginLeft: "8px", fontWeight: "normal",  display : "flex" }}><SearchIcon/>{t("ES_COMMON_SEARCH_BY")}</span>
    }
  }
}

const renderSubmit = () => {
  switch(uiConfig?.type) {
    case "filter" : {
      return (
        <div className={`search-button-wrapper ${screenType} ${uiConfig?.type}`}>
              { uiConfig?.secondaryLabel && <LinkLabel style={{marginBottom: 0, whiteSpace: 'nowrap'}} onClick={clearSearch}>{t(uiConfig?.secondaryLabel)}</LinkLabel> }
              { uiConfig?.primaryLabel && <SubmitBar label={t(uiConfig?.primaryLabel)} submit="submit" disabled={false}/> }
        </div>
      )
      }
    case "search" : {
      return (
        <div className={`search-button-wrapper ${screenType} ${uiConfig?.type}`}>
             <ActionBar className="clear-search-container">
              { uiConfig?.secondaryLabel && <LinkLabel style={{marginBottom: 0, whiteSpace: 'nowrap'}} onClick={clearSearch}>{t(uiConfig?.secondaryLabel)}</LinkLabel> }
              { uiConfig?.primaryLabel && <SubmitBar label={t(uiConfig?.primaryLabel)} submit="submit" disabled={false}/> }
            </ActionBar>
        </div>
      )
    }
    default : {
      return (
        <div className={`search-button-wrapper ${screenType} ${uiConfig?.type}`}>
             <ActionBar className="clear-search-container">
              { uiConfig?.secondaryLabel && <LinkLabel style={{marginBottom: 0, whiteSpace: 'nowrap'}} onClick={clearSearch}>{t(uiConfig?.secondaryLabel)}</LinkLabel> }
              { uiConfig?.primaryLabel && <SubmitBar label={t(uiConfig?.primaryLabel)} submit="submit" disabled={false}/> }
            </ActionBar>
        </div>
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
            {renderSubmit()}
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