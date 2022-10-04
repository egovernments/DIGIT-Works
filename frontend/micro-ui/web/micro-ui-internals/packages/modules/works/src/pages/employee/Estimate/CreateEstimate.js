import React, { Fragment, useState } from 'react'
import { Toast } from "@egovernments/digit-ui-react-components";
import { useTranslation } from 'react-i18next';
import CreateEstimateForm from '../../../components/CreateEstimate/CreateEstimateForm';
import { createEstimatePayload } from '../../../utils/createEstimatePayload';
import { useHistory } from 'react-router-dom/cjs/react-router-dom.min';
//import SubWork from '../../components/CreateEstimate/SubWork';
const allowedFileTypes = /(.*?)(pdf|docx|msword|openxmlformats-officedocument|wordprocessingml|document|spreadsheetml|sheet)$/i;


const CreateEstimate = (props) => {
    const { mutate: EstimateMutation } = Digit.Hooks.works.useCreateEstimate("WORKS");
    const [showToast, setShowToast] = useState(null);
    const {t}=useTranslation();
    const history=useHistory();
    const onFormSubmit = async (_data) => {
        const payload = await createEstimatePayload(_data);
        const estimate = {
            estimate: payload, workflow: {
                "action": "CREATE",
                "comment": _data?.comments,
                "assignees": [
                    // _data?.app?.uuid
                ]
            }
        }

        await EstimateMutation(estimate, {
            onError: (error, variables) => {
                setShowToast({ warning: true, label: error?.response?.data?.Errors?.[0].message ? error?.response?.data?.Errors?.[0].message : error });
                setTimeout(() => {
                setShowToast(false);
                }, 5000);
            },
            onSuccess: async (responseData, variables) => {
                history.push("/works-ui/employee/works/response",{
                    header:"Estimate Created and Forwarded Successfully",
                    id:responseData?.estimates[0]?.estimateNumber,
                    info:"Estimate ID",
                    message:"A new Estimate has been created successfully and forwarded to Designation or the <Department>  Department for processing.",
                    links:[
                        {
                            name:"Create new Estimate",
                            redirectUrl:"/works-ui/employee/works/create-estimate",
                            code:"",
                            svg:"CreateEstimateIcon",
                            isVisible:true,
                        }
                    ]
                })
            }
        })
    }

    return (
        <Fragment>
            <CreateEstimateForm onFormSubmit={onFormSubmit} />
            {showToast && (
                <Toast
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

export default CreateEstimate