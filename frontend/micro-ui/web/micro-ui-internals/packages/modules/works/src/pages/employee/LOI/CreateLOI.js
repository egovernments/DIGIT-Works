import React, { useReducer, useState } from 'react'
import { Controller, useForm } from 'react-hook-form'
import { Card, Header, CardSectionHeader, LabelFieldPair, CardLabel, CardText, CardSectionSubText, TextInput, Dropdown, UploadFile, MultiUploadWrapper, ActionBar, SubmitBar, DatePicker, Row, StatusTable, CardLabelError, AddIcon, SubtractIcon, InfoBannerIcon } from '@egovernments/digit-ui-react-components';
import { useTranslation } from 'react-i18next';
import CreateLoiForm from '../../../components/CreateLOI/CreateLoiForm';
import { useHistory } from 'react-router-dom';


const CreateLOI = () => {
    const { mutate: LOIMutation } = Digit.Hooks.works.useCreateLOI();
    const history = useHistory();
    const { t } = useTranslation()
    const onFormSubmit = async (_data) => {

        const data = Object.keys(_data)
            .filter((k) => _data[k])
            .reduce((acc, key) => ({ ...acc, [key]: typeof _data[key] === "object" && key !== "uploads" ? _data[key].name : _data[key] }), {})

        data.fileDate = Digit.Utils.pt.convertDateToEpoch(data.fileDate)
        data.agreementDate = Digit.Utils.pt.convertDateToEpoch(data.agreementDate)
        const letterOfIndent = { letterOfIndent: data }


        await LOIMutation(letterOfIndent, {
            onError: (error, variables) => {

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
                    id: "LI/ENG/0001/07/2021-22",
                    info: t("WORKS_LOI_ID"),
                    message: t("WORKS_LOI_RESPONSE_MESSAGE"),
                    links: [
                        {
                            name: t("WORKS_CREATE_NEW_LOI"),
                            redirectUrl: `/${window.contextPath}/employee/works/create-loi`,
                            code: "",
                            svg: "CreateEstimateIcon"
                        },
                        {
                            name: t("WORKS_GOTO_LOI_INBOX"),
                            redirectUrl: `/${window.contextPath}/employee/works/LOIInbox`,
                            code: "",
                            svg: "CreateEstimateIcon"
                        },
                    ]
                }
                history.push(`/${window.contextPath}/employee/works/response`, state)
            }
        })


    }




    return (
        <CreateLoiForm onFormSubmit={onFormSubmit} />

    )
}

export default CreateLOI