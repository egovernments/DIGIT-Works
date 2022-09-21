import React, { Fragment, useState } from 'react'
import { Card, Header, CardSectionHeader, LabelFieldPair, CardLabel, CardText, CardSectionSubText, TextInput, Dropdown, UploadFile, MultiUploadWrapper, ActionBar, SubmitBar, CardLabelError } from '@egovernments/digit-ui-react-components';
import { useTranslation } from 'react-i18next';
import CreateEstimateForm from '../../../components/CreateEstimate/CreateEstimateForm';
import { createEstimatePayload } from '../../../utils/createEstimatePayload';
//import SubWork from '../../components/CreateEstimate/SubWork';
const allowedFileTypes = /(.*?)(pdf|docx|msword|openxmlformats-officedocument|wordprocessingml|document|spreadsheetml|sheet)$/i;


const CreateEstimate = (props) => {
    const { mutate: EstimateMutation } = Digit.Hooks.works.useCreateEstimate("WORKS");

    const onFormSubmit = async (_data) => {

        const payload = await createEstimatePayload(_data);
        const estimate = {
            estimate: payload, workflow: {
                "action": "string",
                "comment": "string",
                "assignees": [
                    "string"
                ]
            }
        }

        await EstimateMutation(estimate, {
            onError: (error, variables) => {

            },
            onSuccess: async (responseData, variables) => {

            }
        })
    }

    return (
        <CreateEstimateForm onFormSubmit={onFormSubmit} />
    )
}

export default CreateEstimate