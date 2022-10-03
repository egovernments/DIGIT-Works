import React, { Fragment, useState } from 'react'
import { Loader, Toast } from "@egovernments/digit-ui-react-components";
import { useTranslation } from 'react-i18next';
import { updateEstimatePayload } from '../../utils/updateEstimatePayload';
import { useHistory, useLocation } from 'react-router-dom/cjs/react-router-dom.min';
import ModifyEstimateForm from './ModifyEstimateForm';
//import SubWork from '../../components/CreateEstimate/SubWork';
const allowedFileTypes = /(.*?)(pdf|docx|msword|openxmlformats-officedocument|wordprocessingml|document|spreadsheetml|sheet)$/i;

 
const ModifyEstimate = (props) => {
    const { mutate: EstimateMutation } = Digit.Hooks.works.useUpdateEstimate("WORKS");
    const [showToast, setShowToast] = useState(null);
    const {t} = useTranslation();
    const history = useHistory();
    const {state} = useLocation()
    const {tenantId, estimateNumber} = state
    const {status, data} = Digit.Hooks.works.useSearchWORKS({ tenantId,filters: {estimateNumber:estimateNumber}});
    let estimate = data?.estimates[0]
    const onFormSubmit = async (estimateId,_data) => {
        const payload = await updateEstimatePayload(estimateId, _data);
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
                setShowToast({ error: true, label: error?.response?.data?.Errors?.[0].message ? error?.response?.data?.Errors?.[0].message : error });
                setTimeout(() => {
                setShowToast(false);
                }, 5000);
            },
            onSuccess: async (responseData, variables) => {
                history.push("/works-ui/employee/works/response",{
                    header:"Estimate Modified and Forwarded Successfully",
                    id:responseData?.estimates[0]?.estimateNumber,
                    info:"Estimate ID",
                    message:"Estimate has been modified and sent to <User> for approval.",
                    links:[
                        {
                            name:"Go To Estimate Inbox",
                            redirectUrl:"/works-ui/employee/works/inbox",
                            code:"",
                            svg:"RefreshIcon"
                        }
                    ]
                })
            }
        })
    }

    return (
        <Fragment>
            {status==="loading"?<Loader/>:
            <ModifyEstimateForm onFormSubmit={onFormSubmit} estimate={estimate} />}
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

export default ModifyEstimate