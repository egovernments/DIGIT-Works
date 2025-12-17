import React, { Fragment, useState } from 'react'
import { Loader } from "@egovernments/digit-ui-react-components";
import { useTranslation } from 'react-i18next';
import { Toast } from "@egovernments/digit-ui-components";
import { updateEstimatePayload } from '../../utils/updateEstimatePayload';
import { useHistory, useLocation } from 'react-router-dom/cjs/react-router-dom.min';
import ModifyEstimateForm from './ModifyEstimateForm';
//import SubWork from '../../components/CreateEstimate/SubWork';
const allowedFileTypes = /(.*?)(pdf|docx|msword|openxmlformats-officedocument|wordprocessingml|document|spreadsheetml|sheet)$/i;

 
const ModifyEstimate = (props) => {
    // Call update estimate API by using requestInfo, payload, workflow objects
    const { mutate: EstimateMutation } = Digit.Hooks.works.useUpdateEstimate("WORKS");
    const [showToast, setShowToast] = useState(null);
    const {t} = useTranslation();
    const history = useHistory();
    let {tenantId, estimateNumber} = Digit.Hooks.useQueryParams();

    // Call search estimate API by using params tenantId,filters
    const {status, data} = Digit.Hooks.works.useSearchWORKS({ tenantId,filters: {estimateNumber:estimateNumber}});
    let estimateEdit = data?.estimates[0]
    const onFormSubmit = async (_data) => {

        const payload = await updateEstimatePayload(_data, estimateEdit);
        const estimate = {
            estimate: payload, workflow: {
                "action": "EDIT",
                "comment": _data?.comments,
                "assignees": [
                    _data?.app?.uuid
                ]
            }
        }

        await EstimateMutation(estimate, {
            onError: (error, variables) => {
                setShowToast({ type:"error", label: error?.response?.data?.Errors?.[0].message ? error?.response?.data?.Errors?.[0].message : error });
                setTimeout(() => {
                setShowToast(false);
                }, 5000);
            },
            onSuccess: async (responseData, variables) => {
                history.push(`/${window?.contextPath}/employee/works/response`,{
                    header:"Estimate Modified and Forwarded Successfully",
                    id:responseData?.estimates[0]?.estimateNumber,
                    info:"Estimate ID",
                    message:"Estimate has been modified and sent to <User> for approval.",
                    links:[
                        {
                            name:"Go To Estimate Inbox",
                            redirectUrl:`/${window?.contextPath}/employee/works/inbox`,
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
            <ModifyEstimateForm onFormSubmit={onFormSubmit} estimate={estimateEdit} />}
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

export default ModifyEstimate