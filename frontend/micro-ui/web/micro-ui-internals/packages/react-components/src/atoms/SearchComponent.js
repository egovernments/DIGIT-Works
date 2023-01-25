import React from "react";
import { useForm } from "react-hook-form";
import { useTranslation } from "react-i18next";
import Header from "../atoms/Header";
import RenderFormFields from "../molecules/RenderFormFields";
import LinkLabel from '../atoms/LinkLabel';
import SubmitBar from "../atoms/SubmitBar";

const SearchComponent = ({ uiConfig, header = "", children = {}, screenType = "search"}) => {
  const { t } = useTranslation();
  
  console.log('config', uiConfig);
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
  });

  const formData = watch();

  const checkKeyDown = (e) => {
    const keyCode = e.keyCode ? e.keyCode : e.key ? e.key : e.which;
    if (keyCode === 13) {
      e.preventDefault();
    }
  };

  const onSubmit = (data) => {
    //send data to reducer
    console.log('data', data);
  }

  const clearSearch = () => {
    reset({
      projectId: "",
      subProjectId: "",
      projectName: "",
      workType: "",
      createdFromDate: ""
    })
  }
 
  return (
    <React.Fragment>
      <div className={'search-wrapper'}>
        {header && <Header styles={uiConfig?.headerStyle}>{header}</Header>}
        <form onSubmit={handleSubmit(onSubmit)} onKeyDown={(e) => checkKeyDown(e)}>
          <div className={`search-field-wrapper ${screenType}`}>
            <RenderFormFields 
              fields={children?.fields} 
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
          </div> 
          <div className="search-button-wrapper">
            <LinkLabel style={{marginBottom: 0}} onClick={clearSearch}>{uiConfig?.linkLabel}</LinkLabel>
            <SubmitBar label={uiConfig?.buttonLabel} submit="submit" disabled={false}/>
          </div>
        </form>
      </div>
    </React.Fragment>
  )
}

export default SearchComponent