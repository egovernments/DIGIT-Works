import React from "react";
import { useForm } from "react-hook-form";
import { useTranslation } from "react-i18next";
import Header from "../atoms/Header";
import RenderFormFields from "../molecules/RenderFormFields";
import LinkLabel from '../atoms/LinkLabel';
import SubmitBar from "../atoms/SubmitBar";

let config = [
//   {
//     isMandatory: false,
//     key: "org_type",
//     type: "radio",
//     label: 'Label',
//     disable: false,
//     populators: {
//         name: "org_type",
//         optionsKey: "name",
//         error: "Error",
//         required: false,
//         mdmsConfig: {
//             masterName: "OrganisationType",
//             moduleName: "works",
//             localePrefix: "MASTERS",
//         }
//     },
// },
{
    inline: true,
    label:"text label",
    isMandatory: true,
    key: "org_name",
    type: "text",
    disable: false,
    populators: { 
        name: "org_name", 
        error: "Error", 
        validation: { pattern: /^[^{0-9}^\$\"<>?\\\\~!@#$%^()+={}\[\]*,/_:;“”‘’]{1,120}$/i },
    },
},
{
    inline: true,
    label: "Date label",
    isMandatory: false,
    type: "date",
    disable: false,
    populators: { 
        name: "formation_date", 
        max: new Date().toISOString().split("T")[0]
    },
},
{
    isMandatory: true,
    key: "org_classification",
    type: "dropdown",
    label: "Dropdown label",
    disable: false,
    populators: {
      name: "org_classification",
      optionsKey: "name",
      error: "Error",
      required: true,
      mdmsConfig: {
        masterName: "OrganisationClassification",
        moduleName: "works",
        localePrefix: "MASTERS",
    }
    },
},
{
    label:'Number label',
    isMandatory: false,
    key: "total",
    type: "number",
    disable: false,
    populators: { name: "total", error: "Error", validation: { min: 1 } },
},
{
  label:'Number label',
  isMandatory: false,
  key: "total",
  type: "number",
  disable: false,
  populators: { name: "total", error: "Error", validation: { min: 1 } },
},
{
  label:'Number label',
  isMandatory: false,
  key: "total",
  type: "number",
  disable: false,
  populators: { name: "total", error: "Error", validation: { min: 1 } },
}
]

const SearchComponent = (props) => {
  
  const { t } = useTranslation();
  const { fields } = props

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
    console.log('data', data);
  }

  const clearSearch = () => {
    
  }
  /*
  formStyle 
  headerflag, headerStyle, check based on is search config has header
  labelstyle, isMandatory
  */

  return (
    <React.Fragment>
      <div style={{width: '100%', padding: '16px'}}>
        {false && <Header>{t("WORKS_CREATE_CONTRACTOR")}</Header>}
        <form onSubmit={handleSubmit(onSubmit)} onKeyDown={(e) => checkKeyDown(e)}>
          <div style={{display: 'grid', gridTemplateColumns: 'repeat(4, 1fr)', rowGap: '4px', columnGap: '16px'}}>
            <RenderFormFields 
              fields={config} 
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
          <SubmitBar label={'Search'} submit="submit" disabled={false}/>
        </form>
        
        {/* <div>
          <LinkLabel style={{display: 'inline'}} onClick={clearSearch}>{t("ES_COMMON_CLEAR_SEARCH")}</LinkLabel>
          <SubmitBar label={'Search'} submit="submit" disabled={false}/>
        </div> */}
      </div>
      
    </React.Fragment>
  )
}

export default SearchComponent