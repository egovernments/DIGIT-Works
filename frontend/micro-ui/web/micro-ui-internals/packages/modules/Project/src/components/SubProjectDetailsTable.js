import React, { Fragment, useEffect, useState } from 'react'
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
const SubProjectDetailsTable = ({t, register, control, setValue, onChange}) => {
    const [rows, setRows] = useState(initialState);
    const columns = [t('WORKS_SNO'), t('WORKS_NAME_OF_WORK'), t('WORKS_ESTIMATED_AMOUNT'), t('WORKS_WORK_TYPE'), t('WORKS_SUB_TYPE_WORK'), t('WORKS_WORK_NATURE'), t('WORKS_PROJECT_START_DATE'), t('WORKS_PROJECT_END_DATE'), t('WORKS_MODE_OF_ENTRUSTMENT'), t('WORKS_LOCALITY'), t('WORKS_WARD'), t('WORKS_URBAN_LOCAL_BODY'), t('WORKS_GEO_LOCATION'), t('WORKS_UPLOAD_FILES'),''];
    const [subProjectTypeOfWorkOptions, setSubProjectTypeOfWorkOptions] = useState([]);
    const [subProjectSubTypeOfWorkOptions, setSubProjectSubTypeOfWorkOptions] = useState([]);
    const formFieldName = "subProjectDetails";
    const tenantId = Digit.ULBService.getCurrentTenantId();
    const headerLocale = Digit.Utils.locale.getTransformedLocale(tenantId); 
    const [subProjectDetailsSelectedWard, setSubProjectDetailsSelectedWard] = useState('');
    const [subProjectDetailsLocalities, setSubProjectDetailsLocalities] = useState('');

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
        })
    
    useEffect(()=>{
        setSubProjectSubTypeOfWorkOptions(subProjectTypeOfWorkOptions?.subTypes ? subProjectTypeOfWorkOptions?.subTypes : [] );
    },[subProjectTypeOfWorkOptions]);

    useEffect(()=>{
        setSubProjectDetailsLocalities(wardsAndLocalities?.localities[subProjectDetailsSelectedWard?.code] ? wardsAndLocalities?.localities[subProjectDetailsSelectedWard?.code]: [] );
    },[subProjectDetailsSelectedWard]);

    const getDropDownDataFromMDMS = (t, row, inputName, props, register, options={}) => {
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
                        optionKey={"name"}
                        t={t}
                        select={(e)=>handleDropdownChange(e, props, row, inputName)}
                        onBlur={props?.onBlur}
                    />
    }

    const handleDropdownChange = (e,props, row, inputName) => {
        if(inputName === "subProjectsTypeOfWork") {
            setValue(`${formFieldName}.${row.key}.subProjectDetailsSubTypeOfWork`, '');
            setSubProjectTypeOfWorkOptions(e);
        }
        if(inputName === "subProjectWard") {
            setValue(`${formFieldName}.${row.key}.subProjectDetailsLocality`, '');
            setSubProjectDetailsSelectedWard(e);
        }
        props?.onChange(e);
    }

    //TODOy
    const getStyles = () => {
        return { "width": "100vw" };
    }

    const renderHeader = () => {
        return columns?.map((key, index) => {
            return <th key={index} style={getStyles(key)} > {key} </th>
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

    const renderBody = () => {
        let i = 0
        return rows.map((row, index) => {
            if (row.isShow) i++
            return row.isShow && <tr key={index} style={{ "height": "50%" }}>
                <td style={getStyles()}>{i}</td>
                <td style={getStyles()} >
                    <div className='field' style={{ "width": "100%" }} >
                        <TextInput style={{ "marginBottom": "0px" }} name={`${formFieldName}.${row.key}.subProjectDetailsNameOfWork`} inputRef={register()}/>
                    </div>
                </td>
                <td style={getStyles()}>
                    <div className='field' style={{ "width": "100%" }} >
                        <TextInput style={{ "marginBottom": "0px" }} name={`${formFieldName}.${row.key}.subProjectDetailsEstimatedAmount`} inputRef={register()}/>
                    </div>
                </td>
                <td style={getStyles()}>
                    <div className='field' style={{ "width": "100%" }} >
                      <Controller
                        control={control}
                        name={`${formFieldName}.${row.key}.subProjectDetailsTypeOfWork`}
                        render={(props)=>(
                                getDropDownDataFromMDMS(t, row, "subProjectDetailsTypeOfWork" , props, register, { 
                                    mdmsConfig: {
                                    masterName: "TypeOfWork",
                                    moduleName: "works",
                                    localePrefix: "ES_COMMON",
                                }})
                            )}
                      />
                    </div>
                </td>  
                <td style={getStyles()}>
                    <div className='field' style={{ "width": "100%" }} >
                    <Controller
                        control={control}
                        name={`${formFieldName}.${row.key}.subProjectDetailsSubTypeOfWork`}
                        render={(props)=>(
                            getDropDownDataFromMDMS(t, row, "subProjectDetailsSubTypeOfWork", props, register, subProjectSubTypeOfWorkOptions)
                        )}
                      />
                    </div>
                </td>
                <td style={getStyles()}>
                    <div className='field' style={{ "width": "100%" }} >
                    <Controller
                        control={control}
                        name={`${formFieldName}.${row.key}.subProjectDetailsNatureOfWork`}
                        render={(props)=>(
                            getDropDownDataFromMDMS(t, row, "subProjectDetailsNatureOfWork", props, register, { 
                                mdmsConfig: {
                                    masterName: "NatureOfWork",
                                    moduleName: "works",
                                    localePrefix: "ES_COMMON",
                            }})
                        )}
                      />
                    </div>
                </td>
                <td style={getStyles()}>
                    <div className='field' style={{ "width": "100%" }} >
                    <TextInput
                        type={"date"}
                        name={`${formFieldName}.${row.key}.subProjectDetailsStartDate`}
                        onChange={onChange}
                        inputRef={register()}
                        style={{paddingRight: "3px"}}
                    />
                    </div>
                </td>  
                <td style={getStyles()}>
                    <div className='field' style={{ "width": "100%" }} >
                    <TextInput
                        type={"date"}
                        name={`${formFieldName}.${row.key}.subProjectDetailsEndDate`}
                        onChange={onChange}
                        inputRef={register()}
                        style={{paddingRight: "3px"}}
                    />
                    </div>
                </td> 
                <td style={getStyles()}>
                    <div className='field' style={{ "width": "100%" }} >
                    <Controller
                        control={control}
                        name={`${formFieldName}.${row.key}.subProjectDetailsModeOfEntrustment`}
                        render={(props)=>(
                            getDropDownDataFromMDMS(t, row, "subProjectDetailsModeOfEntrustment", props, register, { 
                                mdmsConfig: {
                                    masterName: "EntrustmentMode",
                                    moduleName: "works",
                                    localePrefix: "ES_COMMON",
                            }})
                        )}
                      />
                    </div>
                </td>   
                <td style={getStyles()}>
                    <div className='field' style={{ "width": "100%" }} >
                        <Controller
                            control={control}
                            name={`${formFieldName}.${row.key}.subProjectDetailsWard`}
                            render={(props)=>(
                                getDropDownDataFromMDMS(t, row, "subProjectDetailsWard", props, register, wardsAndLocalities?.wards)
                            )}
                        />
                    </div>
                </td> 
                <td style={getStyles()}>
                    <div className='field' style={{ "width": "100%" }} >
                        <Controller
                            control={control}
                            name={`${formFieldName}.${row.key}.subProjectDetailsLocality`}
                            render={(props)=>(
                                getDropDownDataFromMDMS(t, row, "subProjectDetailsLocality", props, register, subProjectDetailsLocalities)
                            )}
                        />
                    </div>
                </td>  
                <td style={getStyles()}>
                    <div className='field' style={{ "width": "100%" }} >
                        <Controller
                            control={control}
                            name={`${formFieldName}.${row.key}.subProjectDetailsUrbanLocalBody`}
                            render={(props)=>(
                                getDropDownDataFromMDMS(t, row, "subProjectDetailsUrbanLocalBody", props, register, subProjectDetailsLocalities)
                            )}
                        />
                    </div>
                </td> 
                <td style={getStyles()} >
                    <div className='field' style={{ "width": "100%" }} >
                        <TextInput style={{ "marginBottom": "0px" }} name={`${formFieldName}.${row.key}.subProjectDetailsGeoLocation`} inputRef={register()}/>
                    </div>
                </td>
                <td style={getStyles()} >
                    <div className='field' style={{ "width": "100%" }} >
                        <TextInput style={{ "marginBottom": "0px" }} name={`${formFieldName}.${row.key}.subProjectDetailsGeoLocation`} inputRef={register()}/>
                    </div>
                </td>
                {/* <td style={getStyles()}>
                    <div className='field' style={{ "width": "100%" }} >
                        <MultiUploadWrapper
                            inputRef={register()}
                            t={t}
                            module="works"
                            tenantId={Digit.ULBService.getCurrentTenantId()}
                            getFormState={(filesData)=> getFileStoreData(filesData, setValue)}
                            showHintBelow={true}
                            setuploadedstate={setValue}
                            allowedFileTypesRegex={"/(.*?)(jpeg|jpg|png|pdf|image)$/i,"}
                            allowedMaxSizeInMB={2}
                            hintText={""}
                            maxFilesAllowed={2}
                            extraStyleName={{ padding: "0.5rem" }}
                            customClass={"upload-margin-bottom"}
                            name={`${formFieldName}.${row.key}.subProjectDetailsFilesUpload`}
                        />
                    </div>
                </td>  */}
                <td style={getStyles(4)} >{showDelete() && <span onClick={() => removeRow(row)}><DeleteIcon fill={"#B1B4B6"} style={{ "margin": "auto" }} /></span>}</td>
            </tr>
        })
    }

    return (
        <div className="sub-project-table-wrapper">
            <table className='table reports-table sub-work-table'>
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