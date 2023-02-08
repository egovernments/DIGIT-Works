import React, { useContext, useEffect, useState } from "react";
import { useForm, FormProvider, useFormContext } from "react-hook-form";
import { useTranslation } from "react-i18next";
import { InboxContext } from "../hoc/InboxSearchComposerContext";
import RenderFormFields from "../molecules/RenderFormFields";
import Header from "../atoms/Header";
import LinkLabel from '../atoms/LinkLabel';
import SubmitBar from "../atoms/SubmitBar";
import Toast from "../atoms/Toast";
import { FilterIcon, RefreshIcon } from "./svgindex";

const SearchComponent = ({ uiConfig, header = "", screenType = "search"}) => {
  const { t } = useTranslation();
  const { state, dispatch } = useContext(InboxContext)
  const [showToast,setShowToast] = useState(null)
  let updatedFields = [];
  const [componentType, setComponentType] = useState(uiConfig?.type);

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
    // debugger
    if(updatedFields.length >= uiConfig?.minReqFields) {
      
      //@prachi We can't make this validation here as this is a generic comp
      // tip -> handle it before the onsumbmit 
      //use custom validation function prop given by userform's register()
      // try using it using config in renderformfields comp
      if(formData.startDate && formData.endDate) {
        if(new Date(formData.startDate).getTime() > new Date(formData.endDate).getTime()) {
          setError("endDate", { type: "focus" }, { shouldFocus: true })
          return
        }
      }
      
      // here based on screenType call respective dispatch fn
      dispatch({
        type: screenType === "search" ? "searchForm" : "filterForm",
        state: {
          ...data
        }
      })
    } else {
      setShowToast({ warning: true, label: t("MIN_SEARCH_CRITERIA_MSG") })
      setTimeout(closeToast, 3000);
    }
  }

  const clearSearch = () => {
    reset(uiConfig?.defaultValues)
    dispatch({
      type: "clearSearchForm",
      state: { ...uiConfig?.defaultValues }
    })
  }
 
  const closeToast = () => {
    setShowToast(null);
  }

  const handleFilterRefresh = () => {}

  const renderHeader = () => {
    switch(uiConfig?.type) {
      case "filter" : {
        return (
          <div className="filter-header-wrapper">
            <div className="icon-filter"><FilterIcon></FilterIcon></div>
            <div className="label">{header}</div>
            <div className="icon-refresh" onClick={handleFilterRefresh}><RefreshIcon></RefreshIcon></div>
          </div>
        )
      }
      default : {
        return <Header styles={uiConfig?.headerStyle}>{header}</Header>
      }
    }
  }

  return (
    <React.Fragment>
      <div className={'search-wrapper'}>
        {header && <Header styles={uiConfig?.headerStyle}>{t(header)}</Header>}
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
            />  
            <div className={`search-button-wrapper ${screenType}`}>
              <LinkLabel style={{marginBottom: 0, whiteSpace: 'nowrap'}} onClick={clearSearch}>{t(uiConfig?.secondaryLabel)}</LinkLabel>
              <SubmitBar label={t(uiConfig?.primaryLabel)} submit="submit" disabled={false}/>
            </div>
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
}

export default SearchComponent