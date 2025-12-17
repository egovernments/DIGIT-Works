import React, { Fragment, useReducer, useState } from 'react'
import { Toast } from '@egovernments/digit-ui-components';
import { useTranslation } from 'react-i18next';
import CreateLoiForm from '../../../components/CreateLOI/CreateLoiForm';
import { useHistory,useLocation } from 'react-router-dom';


const CreateLOI = (props) => {
    
    // call update LOI API to update LOI form values and application staus during workflow action 
    const {
        isLoading: updatingApplication,
        isError: updateApplicationError,
        data: updateResponse,
        error: updateError,
        mutate:updateLOI,
    } = Digit.Hooks.works.useApplicationActionsLOI();

    const { state } = useLocation();
    const { loiNumber, isEdit } = Digit.Hooks.useQueryParams();
    const [showToast, setShowToast] = useState(null);
    const defaultFormValues = isEdit ? { ...state?.data.applicationDetails.applicationData.additionalDetails.formData } : {}
    //deleting the approver field so that it is not populated by default while editing loi upon rejection
    delete defaultFormValues?.app
    delete defaultFormValues?.uploads
    // Call create LOI API by using requestInfo,letterOfIndent
    const { mutate: LOIMutation } = Digit.Hooks.works.useCreateLOI();
    //here fetch the update mutation as well for modify upon rejection use case
    const history = useHistory();
    const { t } = useTranslation()

    const onFormSubmit = async (_data) => {
        const data = Object.keys(_data)
            .filter((k) => _data[k])
            .reduce((acc, key) => ({ ...acc, [key]: typeof _data[key] === "object" && key !== "uploads" ? _data[key].name : _data[key] }), {})

        data.fileDate = Digit.Utils.pt.convertDateToEpoch(data.fileDate)
        data.agreementDate = Digit.Utils.pt.convertDateToEpoch(data.agreementDate)
        data.status="DRAFT"
        data.tenantId =  Digit.ULBService.getCurrentTenantId();
        data.additionalDetails = {}
        data.additionalDetails.filesAttached = _data?.uploads
        data.additionalDetails.oic = _data?.officerIncharge
        data.additionalDetails.formData = _data
        data.oicId = _data?.officerIncharge?.uuid
        Object.keys(data).forEach(key => {
            if (data[key] === undefined) {
                delete data[key];
            }
        });
        delete data.uploads
        const workflow = {
            action:"CREATE",
            comment:_data?.comments,
            assignees:[_data?.app?.uuid]
        }
        Object.keys(workflow).forEach(key => {
            if (workflow[key] === undefined) {
                delete workflow[key];
            }
        });
        const letterOfIndent = { letterOfIndent: data,workflow }

        
        //here if the action is edit then call the update api(call mutate) instead of create and show appropriate response/error toast
        if(isEdit){
            
            data.letterOfIndentNumber = loiNumber
            workflow.action = "EDIT"
            const updateCallObj = { letterOfIndent: { ...state?.data.applicationDetails.applicationData,...data },workflow}
            await updateLOI(updateCallObj,{
                onError: (error, variables) => {
                    
                    //Show toast here with error message
                    setShowToast({ type:"error" , label: "CORE_SOMETHING_WENT_WRONG" });
                    setTimeout(() => {
                        setShowToast(false);
                    }, 3000);
                },
                onSuccess: async (responseData, requestData) => {
                    
                    const state = {
                        header: t("WORKS_LOI_MODIFIED_RESPONSE_HEADER"),
                        id: responseData?.letterOfIndents?.[0]?.letterOfIndentNumber,
                        info: t("WORKS_LOI_ID"),
                        message: t("WORKS_LOI_MODIFIED_RESPONSE_MESSAGE", { loiNumber: responseData?.letterOfIndents?.[0]?.letterOfIndentNumber }),
                        links: [
                            {
                                name: t("WORKS_CREATE_NEW_LOI"),
                                redirectUrl: `/${window.contextPath}/employee/works/create-loi`,
                                code: "",
                                svg: "CreateEstimateIcon",
                                isVisible: true,
                                type:"add"
                            },
                            {
                                name: t("WORKS_GOTO_LOI_INBOX"),
                                redirectUrl: `/${window.contextPath}/employee/works/LOIInbox`,
                                code: "",
                                svg: "CreateEstimateIcon",
                                isVisible: true,
                                type:"inbox"
                            },
                        ],
                        responseData,
                        requestData
                    }
                    history.push(`/${window.contextPath}/employee/works/response`, state)
                }

            })
        }
        else{
        await LOIMutation(letterOfIndent, {
            onError: (error, variables) => {
                //Show toast here with error message
                setShowToast({ type:"error", label: "CORE_SOMETHING_WENT_WRONG" });
                setTimeout(() => {
                    setShowToast(false);
                }, 3000);
            },
            onSuccess: async (responseData, requestData) => {
                const state = {
                    header: t("WORKS_LOI_RESPONSE_HEADER"),
                    id: responseData?.letterOfIndents?.[0]?.letterOfIndentNumber,
                    info: t("WORKS_LOI_ID"),
                    message: t("WORKS_LOI_RESPONSE_MESSAGE", { loiNumber: responseData?.letterOfIndents?.[0]?.letterOfIndentNumber }),
                    links: [
                        {
                            name: t("WORKS_CREATE_NEW_LOI"),
                            redirectUrl: `/${window.contextPath}/employee/works/create-loi`,
                            code: "",
                            svg: "CreateEstimateIcon",
                            isVisible:true,
                            type:"add"
                        },
                        {
                            name: t("WORKS_GOTO_LOI_INBOX"),
                            redirectUrl: `/${window.contextPath}/employee/works/LOIInbox`,
                            code: "",
                            svg: "CreateEstimateIcon",
                            isVisible: true,
                            type:"inbox"
                        },
                    ],
                    responseData,
                    requestData
                }
                history.push(`/${window.contextPath}/employee/works/response`, state)
            }
        })

        }
    }




    return (
        <Fragment>
            <CreateLoiForm onFormSubmit={onFormSubmit} defaultFormValues={defaultFormValues} state={state} loiNumber={loiNumber} isEdit={isEdit}/>
            {showToast && (
                <Toast
                    style={{"zIndex":"9999999"}}
                    type={showToast?.type}
                    label={t(showToast.label)}
                    onClose={() => {
                        setShowToast(null);
                    }}
                />
            )}
        </Fragment>
    )
}

export default CreateLOI