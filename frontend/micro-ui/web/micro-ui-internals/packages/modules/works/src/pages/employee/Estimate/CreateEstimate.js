import React, { Fragment, useState } from 'react'
import { Toast } from "@egovernments/digit-ui-react-components";
import { useTranslation } from 'react-i18next';
import CreateEstimateForm from '../../../components/CreateEstimate/CreateEstimateForm';
import { createEstimatePayload } from '../../../utils/createEstimatePayload';
//import SubWork from '../../components/CreateEstimate/SubWork';
const allowedFileTypes = /(.*?)(pdf|docx|msword|openxmlformats-officedocument|wordprocessingml|document|spreadsheetml|sheet)$/i;


const CreateEstimate = (props) => {
    const { mutate: EstimateMutation } = Digit.Hooks.works.useCreateEstimate("WORKS");
    const [showToast, setShowToast] = useState(null);
    const {t}=useTranslation();
    const onFormSubmit = async (_data) => {
        const payload = await createEstimatePayload(_data);
        const estimate = {
            estimate: payload, workflow: {
                "action": "CREATE",
                "comment": _data?.comments,
                "assignees": [
                    _data?.app?.uuid
                ]
            }
        }

        await EstimateMutation(estimate, {
            onError: (error, variables) => {
                console.log(error)
                setShowToast({ warning: true, label: error?.response?.data?.Errors?.[0].message ? error?.response?.data?.Errors?.[0].message : error });
                setTimeout(() => {
                setShowToast(false);
                }, 5000);
            },
            onSuccess: async (responseData, variables) => {

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