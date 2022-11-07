import React, { Fragment, useState } from 'react'
import { Toast } from "@egovernments/digit-ui-react-components";
import { useTranslation } from 'react-i18next';
import CreateContractForm from '../../components/CreateContract/CreateContractForm';

const CreateContract = (props) => {
    const [showToast, setShowToast] = useState(null);
    const {t}=useTranslation();
    //To Call create contract API by using requestInfo,contract(payload,workflow)
    // const { mutate: contractMutation } = Digit.Hooks.works.useCreateContract("WORKS");
    
    const onFormSubmit = async (_data) => {
    //      use below code for create contract API CALL
    
    //     await contractMutation(payload, {
    //         onError: (error, variables) => {
    //             setShowToast({ warning: true, label: error?.response?.data?.Errors?.[0].message ? error?.response?.data?.Errors?.[0].message : error });
    //             setTimeout(() => {
    //             setShowToast(false);
    //             }, 5000);
    //         },
    //         onSuccess: async (responseData, variables) => {
    //             history.push("/works-ui/employee/works/response",{
    //                 header:"Work Order Created Successfully and sent for Approval",
    //                 id:"WO/ENG/0001/07/2021-22",
    //                 info:t("WORKS_ORDER_ID"),
    //                 message:`Work order with Work Order ID {workID} created successfully.`,
    //                 links:[
    //                     {
    //                         name:t("WORKS_CREATE_CONTRACT"),
    //                         redirectUrl:"/works-ui/employee/works/create-contract",
    //                         code:"",
    //                         svg:"CreateEstimateIcon",
    //                         isVisible:true,
    //                         type:"add"
    //                     },
    //                     {
    //                         name:t("WORKS_GOTO_CONTRACT_INBOX"),
    //                         redirectUrl:"/works-ui/employee/works/create-contract",
    //                         code:"",
    //                         svg:"RefreshIcon",
    //                         isVisible:true,
    //                         type:"add"
    //                     }
    //                 ]
    //             })
    //         }
    //     })
    }

    return (
        <Fragment>
            <CreateContractForm onFormSubmit={onFormSubmit} />
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

export default CreateContract