import React, { Fragment, useEffect, useMemo, useState } from 'react'
import { AddIcon, DeleteIcon, RemoveIcon, TextInput, CardLabelError, Dropdown, CustomDropdown, Loader, MultiUploadWrapper } from '@egovernments/digit-ui-react-components'
import { Controller } from 'react-hook-form';
import _ from 'lodash';

//Constant Declaration
const initialState = [
    {
        key: 1,
        isShow: true,
    },
];
//these params depend on what the controller of the associated type is sending.
const SubProjectDetailsTable = ({t, register, control, setValue, onChange, errors, sectionFormCategory, selectedFormCategory}) => {
    console.log(selectedFormCategory, sectionFormCategory);
    const [rows, setRows] = useState(initialState);
    const columns = [
        {label : t('WORKS_SNO'), isMandatory : false },
        {label : t('WORKS_NAME_OF_WORK'), isMandatory : true },
        {label : t('WORKS_ESTIMATED_AMOUNT'), isMandatory : false },
        {label : t('WORKS_WORK_TYPE'), isMandatory : true },
        {label : t('WORKS_SUB_TYPE_WORK'), isMandatory : false },
        {label : t('WORKS_WORK_NATURE'), isMandatory : false },
        {label : t('WORKS_PROJECT_START_DATE'), isMandatory : false },
        {label : t('WORKS_PROJECT_END_DATE'), isMandatory : false },
        {label : t('WORKS_MODE_OF_ENTRUSTMENT'), isMandatory : false },
        {label : t('WORKS_LOCALITY'), isMandatory : false },
        {label : t('WORKS_WARD'), isMandatory : false },
        {label : t('WORKS_URBAN_LOCAL_BODY'), isMandatory : false },
        {label : t('WORKS_GEO_LOCATION'), isMandatory : false },
        {label : t('WORKS_UPLOAD_FILES'), isMandatory : false },
    ];
    const [subProjectTypeOfWorkOptions, setSubProjectTypeOfWorkOptions] = useState([]);
    const [subProjectSubTypeOfWorkOptions, setSubProjectSubTypeOfWorkOptions] = useState([]);
    const formFieldName = "subProjectDetails"; //keep this name diff from the key in config
    const tenantId = Digit.ULBService.getCurrentTenantId();
    const headerLocale = Digit.Utils.locale.getTransformedLocale(tenantId); 
    const [subProjectDetailsSelectedWard, setSubProjectDetailsSelectedWard] = useState([]);
    const [subProjectDetailsLocalities, setSubProjectDetailsLocalities] = useState([]);
    const { data: cities } = Digit.Hooks.useTenants();
    const getCities = () => cities?.filter((e) => e.code.includes(tenantId)) || [];

    const { isLoading, data : wardsAndLocalities } = Digit.Hooks.useLocation(
        tenantId, 'Ward',
        {
            select: (data) => {
                const wards = []
                const localities = {}
                data?.TenantBoundary[0]?.boundary.forEach((item) => {
                    localities[item?.code] = item?.children.map(item => ({ code: item.code, name: item.name, i18nKey: `${headerLocale}_ADMIN_${item?.code}` }))
                    wards.push({ code: item.code, name: item.name, i18nKey: `${headerLocale}_ADMIN_${item?.code}` })
                });
               return {
                    wards, localities
               }
            }
        });
        
    useEffect(()=>{
        let filteredSubTypeOfWork = ( subProjectTypeOfWorkOptions && subProjectTypeOfWorkOptions?.subTypes ) ? (
        subProjectTypeOfWorkOptions?.subTypes.map(subType=>({code : subType?.code, name : `ES_COMMON_${subType?.code}`}))) : [];
        setSubProjectSubTypeOfWorkOptions(filteredSubTypeOfWork);
    },[subProjectTypeOfWorkOptions]);

    useEffect(()=>{
        setSubProjectDetailsLocalities(wardsAndLocalities?.localities[subProjectDetailsSelectedWard?.code] ? wardsAndLocalities?.localities[subProjectDetailsSelectedWard?.code]: [] );
    },[subProjectDetailsSelectedWard]);

    const getDropDownDataFromMDMS = (t, row, inputName, props, register, optionKey="name", options=[]) => {
        console.log(options);
        const { isLoading, data } = Digit.Hooks.useCustomMDMS(
                Digit.ULBService.getStateId(),
                options?.mdmsConfig?.moduleName,
                [{ name: options?.mdmsConfig?.masterName }],
                {
                select: (data) => {
                    const optionsData = _.get(data, `${options?.mdmsConfig?.moduleName}.${options?.mdmsConfig?.masterName}`, []);
                    return optionsData.filter((opt) => opt?.active).map((opt) => ({ ...opt, name: `${options?.mdmsConfig?.localePrefix}_${opt.code}` }));
                },
                enabled: options?.mdmsConfig ? true : false,
                }
            );
            if (isLoading) {
                return <Loader />;
                //show MDMS data if options are not provided. Options are in use here for pre defined options from config. 
                //Usage example : dependent dropdown
                } else return <Dropdown
                        inputRef={register()}
                        option={options?.mdmsConfig ? data : options}
                        selected={props?.value}
                        optionKey={optionKey}
                        t={t}
                        select={(e)=>handleDropdownChange(e, props, row, inputName)}
                        onBlur={props?.onBlur}
                    />
    }

    const handleDropdownChange = (e,props, row, inputName) => {
        if(inputName === "subProjectDetailsTypeOfWork") {
            setValue(`${formFieldName}.${row.key}.subProjectDetailsSubTypeOfWork`, '');
            setSubProjectTypeOfWorkOptions(e);
        }
        if(inputName === "subProjectDetailsWard") {
            setValue(`${formFieldName}.${row.key}.subProjectDetailsLocality`, '');
            setSubProjectDetailsSelectedWard(e);
        }
        props?.onChange(e);
    }

    //TODOy
    const getStyles = (type) => {
        if(type === "upload") {
            return { "minWidth": "20rem" };
        }
        return { "minWidth": "14rem" };
    }

    const renderHeader = () => {
        return columns?.map((column, index) => {
            return <th key={index} style={getStyles()} >{column?.label}{column?.isMandatory && '*'} </th>
        })
    }

    const showDelete = () => {
        let countIsShow = 0
        rows.map(row => row.isShow && countIsShow++)
        if (countIsShow === 1) {
            return false
        }
        return true
    }
    const removeRow = (row) => {
        //make a new state here which doesn't have this key
        const updatedState = rows.map(e => {
            if (e.key === row.key) {
                return {
                    key: e.key,
                    isShow: false
                }
            }
            return e
        })

        setRows(prev => updatedState)
    }
    const addRow = () => {
        const obj = {
            key: null,
            isShow: true
        }
        obj.key = rows[rows.length - 1].key + 1
        setRows(prev => [...prev, obj])
    }

    function getFileStoreData(filesData, value) {
        const numberOfFiles = filesData.length;
        let finalDocumentData = [];
        if (numberOfFiles > 0) {
          filesData.forEach((value) => {
            finalDocumentData.push({
              fileName: value?.[0],
              fileStoreId: value?.[1]?.fileStoreId?.fileStoreId,
              documentType: value?.[1]?.file?.type,
            });
          });
        }
        //here we need to update the form the same way as the state of the reducer in multiupload, since Upload component within the multiupload wrapper uses that same format of state so we need to set the form data as well in the same way. Previously we were altering it and updating the formData
        onChange(numberOfFiles>0?filesData:[]);
      }

    const renderErrorIfAny = (row, name, isErrorForDropdown=false) => {
        return <>
            {errors && errors?.[formFieldName]?.[row.key]?.[name]?.type === "pattern" && (
                <CardLabelError className={!isErrorForDropdown ? 'projects-subProject-details-error' : 'projects-subProject-details-error dropdown-field'} >{t(`WORKS_PATTERN_ERR`)}</CardLabelError>)}
            {errors && errors?.[formFieldName]?.[row.key]?.[name]?.type === "required" && (
                <CardLabelError className={!isErrorForDropdown ? 'projects-subProject-details-error' : 'projects-subProject-details-error dropdown-field'} >{t(`WORKS_REQUIRED_ERR`)}</CardLabelError>)}
        </>
    }

    const renderBody = () => {
        let i = 0
        return rows.map((row, index) => {
            if (row.isShow) i++
            return row.isShow && <tr key={index} style={{ "height": "50%" }}>
                <td style={getStyles()}>{i}</td>
                <td style={getStyles()} >
                    <div className='field' style={{ "width": "100%" }} >
                        <TextInput style={{ "marginBottom": "0px" }} name={`${formFieldName}.${row.key}.subProjectDetailsNameOfWork`} inputRef={(selectedFormCategory === sectionFormCategory) ? register({required : true}) : register({required : false})}/>
                        {renderErrorIfAny(row, "subProjectDetailsNameOfWork")}
                    </div>
                </td>
                <td style={getStyles()}>
                    <div className='field' style={{ "width": "100%" }} >
                        <TextInput style={{ "marginBottom": "0px" }} name={`${formFieldName}.${row.key}.subProjectDetailsEstimatedAmount`} inputRef={register({required : false})}/>
                        {renderErrorIfAny(row, "subProjectDetailsEstimatedAmount")}
                    </div>
                </td>
                <td style={getStyles()}>
                    <div className='field sub-projects-details-field-mt' style={{ "width": "100%" }} >
                      <Controller
                        control={control}
                        name={`${formFieldName}.${row.key}.subProjectDetailsTypeOfWork`}
                        rules={(selectedFormCategory === sectionFormCategory) ? {required : true} : {}}
                        render={(props)=>(
                                getDropDownDataFromMDMS(t, row, "subProjectDetailsTypeOfWork" , props, register, "name", { 
                                    mdmsConfig: {
                                    masterName: "TypeOfWork",
                                    moduleName: "works",
                                    localePrefix: "ES_COMMON",
                                }})
                            )}
                      />
                    {renderErrorIfAny(row, "subProjectDetailsTypeOfWork", true)}
                    </div>
                </td>  
                <td style={getStyles()}>
                    <div className='field sub-projects-details-field-mt ' style={{ "width": "100%" }} >
                    <Controller
                        control={control}
                        name={`${formFieldName}.${row.key}.subProjectDetailsSubTypeOfWork`}
                        rules={{ required: false}}
                        render={(props)=>(
                            getDropDownDataFromMDMS(t, row, "subProjectDetailsSubTypeOfWork", props, register, "name",  subProjectSubTypeOfWorkOptions)
                        )}
                      />
                    {renderErrorIfAny(row, "subProjectDetailsSubTypeOfWork", true)}
                    </div>
                </td>
                <td style={getStyles()}>
                    <div className='field sub-projects-details-field-mt ' style={{ "width": "100%" }} >
                    <Controller
                        control={control}
                        name={`${formFieldName}.${row.key}.subProjectDetailsNatureOfWork`}
                        rules={{ required: false}}
                        render={(props)=>(
                            getDropDownDataFromMDMS(t, row, "subProjectDetailsNatureOfWork", props, register, "name", { 
                                mdmsConfig: {
                                    masterName: "NatureOfWork",
                                    moduleName: "works",
                                    localePrefix: "ES_COMMON",
                            }})
                        )}
                      />
                    {renderErrorIfAny(row, "subProjectDetailsNatureOfWork", true)}
                    </div>
                </td>
                <td style={getStyles()}>
                    <div className='field sub-projects-details-field-mt' style={{ "width": "100%" }} >
                    <TextInput
                        type={"date"}
                        name={`${formFieldName}.${row.key}.subProjectDetailsStartDate`}
                        onChange={onChange}
                        inputRef={register({required : false})}
                        style={{paddingRight: "3px"}}
                    />
                    {renderErrorIfAny(row, "subProjectDetailsStartDate")}
                    </div>
                </td>  
                <td style={getStyles()}>
                    <div className='field sub-projects-details-field-mt' style={{ "width": "100%" }} >
                    <TextInput
                        type={"date"}
                        name={`${formFieldName}.${row.key}.subProjectDetailsEndDate`}
                        onChange={onChange}
                        inputRef={register({required : false})}
                        style={{paddingRight: "3px"}}
                    />
                    {renderErrorIfAny(row, "subProjectDetailsEndDate")}
                    </div>
                </td> 
                <td style={getStyles()}>
                    <div className='field sub-projects-details-field-mt ' style={{ "width": "100%" }} >
                    <Controller
                        control={control}
                        name={`${formFieldName}.${row.key}.subProjectDetailsModeOfEntrustment`}
                        rules={{ required: false}}
                        render={(props)=>(
                            getDropDownDataFromMDMS(t, row, "subProjectDetailsModeOfEntrustment", props, register, "name", { 
                                mdmsConfig: {
                                    masterName: "EntrustmentMode",
                                    moduleName: "works",
                                    localePrefix: "ES_COMMON",
                            }})
                        )}
                      />
                    {renderErrorIfAny(row, "subProjectDetailsModeOfEntrustment", true)}
                    </div>
                </td>   
                <td style={getStyles()}>
                    <div className='field sub-projects-details-field-mt ' style={{ "width": "100%" }} >
                        <Controller
                            control={control}
                            name={`${formFieldName}.${row.key}.subProjectDetailsWard`}
                            rules={{ required: false}}
                            render={(props)=>(
                                getDropDownDataFromMDMS(t, row, "subProjectDetailsWard", props, register, "i18nKey", wardsAndLocalities?.wards)
                            )}
                        />
                    {renderErrorIfAny(row, "subProjectDetailsWard", true)}
                    </div>
                </td> 
                <td style={getStyles()}>
                    <div className='field sub-projects-details-field-mt ' style={{ "width": "100%" }} >
                        <Controller
                            control={control}
                            name={`${formFieldName}.${row.key}.subProjectDetailsLocality`}
                            render={(props)=>(
                                getDropDownDataFromMDMS(t, row, "subProjectDetailsLocality", props, register, "i18nKey", subProjectDetailsLocalities)
                            )}
                        />
                    {renderErrorIfAny(row, "subProjectDetailsLocality", true)}
                    </div>
                </td>  
                <td style={getStyles()}>
                    <div className='field sub-projects-details-field-mt ' style={{ "width": "100%" }} >
                        <Controller
                            control={control}
                            name={`${formFieldName}.${row.key}.subProjectDetailsUrbanLocalBody`}
                            rules={{ required: false}}
                            render={(props)=>(
                                getDropDownDataFromMDMS(t, row, "subProjectDetailsUrbanLocalBody", props, register, "i18nKey", getCities())
                            )}
                        />
                    {renderErrorIfAny(row, "subProjectDetailsUrbanLocalBody", true)}
                    </div>
                </td> 
                <td style={getStyles()} >
                    <div className='field' style={{ "width": "100%" }} >
                        <TextInput style={{ "marginBottom": "0px" }} name={`${formFieldName}.${row.key}.subProjectDetailsGeoLocation`} inputRef={register({required : false})}/>
                        {renderErrorIfAny(row, "subProjectDetailsGeoLocation")}
                    </div>
                </td>
                <td style={getStyles("upload")}>
                    <div className='field' style={{ "width": "100%" }} >
                           <Controller
                                name={`${formFieldName}.${row.key}.subProjectDetailsFilesUpload`}
                                control={control}
                                rules={{ required: false}}
                                render={({ onChange, ref, value = [] }) => {
                                function getFileStoreData(filesData) {
                                    const numberOfFiles = filesData.length;
                                    let finalDocumentData = [];
                                    if (numberOfFiles > 0) {
                                    filesData.forEach((value) => {
                                        finalDocumentData.push({
                                        fileName: value?.[0],
                                        fileStoreId: value?.[1]?.fileStoreId?.fileStoreId,
                                        documentType: value?.[1]?.file?.type,
                                        });
                                    });
                                    }
                                    //here we need to update the form the same way as the state of the reducer in multiupload, since Upload component within the multiupload wrapper uses that same format of state so we need to set the form data as well in the same way. Previously we were altering it and updating the formData
                                    onChange(numberOfFiles>0?filesData:[]);
                                }
                                return (
                                    <MultiUploadWrapper
                                        t={t}
                                        module="works"
                                        tenantId={Digit.ULBService.getCurrentTenantId()}
                                        getFormState={getFileStoreData}
                                        showHintBelow={true}
                                        setuploadedstate={value}
                                        allowedFileTypesRegex={/(.*?)(pdf|docx|msword|openxmlformats-officedocument|wordprocessingml|document|spreadsheetml|sheet)$/i}
                                        allowedMaxSizeInMB={2}
                                        hintText={""}
                                        maxFilesAllowed={2}
                                        extraStyleName={{ padding: "0.5rem" }}
                                        customClass={"upload-margin-bottom"}
                                    />
                                );
                                }}
                            />
                        {renderErrorIfAny(row, "subProjectDetailsFilesUpload")}
                    </div>
                </td> 
                <td style={getStyles(4)} >{showDelete() && <span onClick={() => removeRow(row)}><DeleteIcon fill={"#B1B4B6"} style={{ "margin": "auto" }} /></span>}</td>
            </tr>
        })
    }

    return (
        <div className="sub-project-table-wrapper">
            <table className='table reports-table sub-work-table project-details-table'>
            <thead>
                <tr>{renderHeader()}</tr>
            </thead>
            <tbody>
                {renderBody()}
                <tr>
                    <td style={getStyles()}></td>
                    <td style={{ ...getStyles(2), "textAlign": "center" }} onClick={addRow}><span><AddIcon fill={"#F47738"} styles={{ "margin": "auto", "display": "inline", "marginTop": "-2px" }} /><label style={{ "marginLeft": "10px" }}>{t("WORKS_ADD_ITEM")}</label></span></td>
                    <td style={getStyles()}></td>
                    <td style={getStyles()}></td>
                </tr>
            </tbody>
        </table>
        </div>
    )
}
export default SubProjectDetailsTable;