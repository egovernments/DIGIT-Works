import React, { Fragment, useReducer, useState } from 'react'
import { Toast } from '@egovernments/digit-ui-react-components';
import { useTranslation } from 'react-i18next';
import CreateLoiForm from '../../../components/CreateLOI/CreateLoiForm';
import { useHistory } from 'react-router-dom';


const CreateLOI = () => {

    const [showToast, setShowToast] = useState(null);

    const { mutate: LOIMutation } = Digit.Hooks.works.useCreateLOI();
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
            comments:_data?.comments,
            assignee:[_data?.app?.uuid]
        }
        Object.keys(workflow).forEach(key => {
            if (workflow[key] === undefined) {
                delete workflow[key];
            }
        });
        const letterOfIndent = { letterOfIndent: data,workflow }


        await LOIMutation(letterOfIndent, {
            onError: (error, variables) => {
                //Show toast here with error message
                setShowToast({ error: true, label: "CORE_SOMETHING_WENT_WRONG" });
                setTimeout(() => {
                    setShowToast(false);
                }, 3000);
            },
            onSuccess: async (responseData, requestData) => {

                
                //take us to the response page on succesfull create
                // const state = {
                //     header: "Estimate Created and Forwarded Successfully",
                //     id: "EP/ENG/00001/07/2021-22",
                //     info: "Estimate ID",
                //     message: "A new Estimate has been created successfully and forwarded to Designation or the <Department>  Department for processing.",
                //     links: [
                //         {
                //             name: "Create new Estimate",
                //             redirectUrl: "/digit-ui/employee/works/create-estimate",
                //             code: "",
                //             svg: "CreateEstimateIcon"
                //         }
                //     ]
                // }
                //create a state obj in this format and push
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
                        },
                        {
                            name: t("WORKS_GOTO_LOI_INBOX"),
                            redirectUrl: `/${window.contextPath}/employee/works/LOIInbox`,
                            code: "",
                            svg: "CreateEstimateIcon",
                            isVisible: true,
                        },
                    ],
                    responseData,
                    requestData
                }
                history.push(`/${window.contextPath}/employee/works/response`, state)
            }
        })


    }




    return (
        <Fragment>
            <CreateLoiForm onFormSubmit={onFormSubmit} />
            {showToast && (
                <Toast
                    style={{"zIndex":"9999999"}}
                    error={showToast.error}
                    warning={showToast.warning}
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