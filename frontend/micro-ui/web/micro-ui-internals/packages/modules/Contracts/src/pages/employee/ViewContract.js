import React, { useState, Fragment } from "react";
import { Header, Loader, MultiLink, ActionBar, SubmitBar, Menu} from "@egovernments/digit-ui-react-components";
import { useForm } from 'react-hook-form'
import { useTranslation } from "react-i18next";
import ApplicationDetailsTemplate  from "../../../../templates/ApplicationDetails"
import ProcessingModal from "@egovernments/digit-ui-module-works/src/components/Modal/ProcessingModal";
import RejectLOIModal from "@egovernments/digit-ui-module-works/src/components/Modal/RejectLOIModal";
import getPDFData from "../../../utils/getWorksAcknowledgementData"
const ViewContract = (props) => {
    const { t } = useTranslation()
    const { register, control, watch, handleSubmit, formState: { errors, ...rest }, getValues} = useForm({mode: "onSubmit"});
    const [displayMenu, setDisplayMenu] = useState(false);
    let { contractId } = Digit.Hooks.useQueryParams();
    let subEstimateNumber = "EP/2022-23/09/000094/000070"
    const tenant = Digit.ULBService.getStateId();
    const tenantId = Digit.ULBService.getCurrentTenantId();
    
    // to fetch a details of Contract by using params t, tenantInfo, contractId, subEstiamteNumber
    let { isLoading, isError, data: applicationDetails, error } = Digit.Hooks.contracts.useViewContractDetails(t,tenantId,contractId,subEstimateNumber,{enabled:!!(contractId && subEstimateNumber)});
    // let workflowDetails = Digit.Hooks.useWorkflowDetails(
    //     {
    //         tenantId: tenantId,
    //         id: loiNumber,
    //         moduleCode: applicationDetails?.processInstancesDetails?.[0]?.businessService,
    //         config: {
    //             enabled: applicationDetails?.processInstancesDetails?.[0]?.businessService ? true : false,
    //         }
    //     },
    // );

    // workflowDetails?.data?.actionState?.nextActions?.forEach((action) => {
    //     if (action?.action === "EDIT") {
    //         let pathName = `/${window?.contextPath}/employee/contracts/create-contract?contractId=${applicationDetails?.applicationData?.contractId}&isEdit=true&subEstimateNumber=EP/2022-23/09/000080/000056`;
    //         action.redirectionUrll = {
    //             action: "EDIT_CONTRACT_APPLICATION",
    //             pathname: pathName,
    //             state: {
    //                 applicationDetails: applicationDetails,
    //                 action: "EDIT_CONTRACT_APPLICATION"
    //             },
    //         };
    //     }
    // })

    let paginationParams = { limit: 10, offset:0, sortOrder:"ASC" }
    const { isLoading: hookLoading, data:employeeData } = Digit.Hooks.hrms.useHRMSSearch(
        null,
        tenantId,
        paginationParams,
        null
    );
    
    const [showModal, setShowModal] = useState(false)
    const [showRejectModal, setShowRejectModal] = useState(false)
    
    const actionULB=[
        {
            "name":"APPROVE_FORWARD"
        },
        {
            "name":"REJECT"
        } 
    ]

    function onActionSelect(action) {
        if(action?.name==="FORWARD_LOI"){
            setShowModal(true)
        }
        if(action?.name==="REJECT_LOI"){
            setShowRejectModal(true)
        }
    }

    const onFormSubmit=()=>{
        let forwardValue=getValues();
        setDisplayMenu(false)
        setShowModal(false)
    }

    const onRejectionSubmit=()=>{
        let rejectionValue=getValues();
        setDisplayMenu(false)
        setShowRejectModal(false)
    }

    // // call update Contract API to update Contract form values and application staus during workflow action 
    // const {
    //     isLoading: updatingApplication,
    //     isError: updateApplicationError,
    //     data: updateResponse,
    //     error: updateError,
    //     mutate,
    // } = Digit.Hooks.contracts.useApplicationActionsContract();

    const [showToast, setShowToast] = useState(null);

    const HandleDownloadPdf = () => {
        let result = applicationDetails;
        const PDFdata = getPDFData({...result },tenantId, t);
        PDFdata.then((ress) => Digit.Utils.pdf.generatev1(ress));
        return null;
    }

    const closeToast = () => {
        setShowToast(null);
    };

    if (isLoading) return <Loader />

    return (
        <Fragment>
            <div className={"employee-main-application-details"}>
                <div className={"employee-application-details"} style={{ marginBottom: "15px" }}>
                    <Header styles={{ marginLeft: "0px", paddingTop: "10px", fontSize: "32px" }}>{t("WORKS_VIEW_CONTRACT")}</Header>
                    <MultiLink
                        className="multilinkWrapper employee-mulitlink-main-div"
                        onHeadClick={HandleDownloadPdf}
                        downloadBtnClassName={"employee-download-btn-className"}
                    />
                </div>

                {showModal && <ProcessingModal
                    t={t}
                    heading={"WORKS_PROCESSINGMODAL_HEADER"}
                    closeModal={() => setShowModal(false)}
                    actionCancelLabel={"WORKS_CANCEL"}
                    actionCancelOnSubmit={() => setShowModal(false)}
                    actionSaveLabel={"WORKS_FORWARD"}
                    actionSaveOnSubmit={onFormSubmit}
                    onSubmit={onFormSubmit}
                    control={control}
                    register={register}
                    handleSubmit={handleSubmit}
                    errors={errors}
                    employeeData={employeeData}
                />}

                {showRejectModal && <RejectLOIModal
                  t={t}
                  heading={"WORKS_REJECT"}
                  closeModal={() => setShowRejectModal(false)}
                  actionCancelLabel={"WORKS_CANCEL"}
                  actionCancelOnSubmit={() => setShowRejectModal(false)}
                  actionSaveLabel={"WORKS_REJECT"}
                  actionSaveOnSubmit={onRejectionSubmit}
                  onSubmit={onRejectionSubmit}
                  control={control}
                  register={register}
                  handleSubmit={handleSubmit}
                  errors={errors}
              />}


                <ApplicationDetailsTemplate
                    applicationDetails={applicationDetails}
                    isLoading={isLoading }
                    // isDataLoading={isLoading || isBillingServiceLoading || isCommonmastersLoading || isServicesMasterLoading}
                    applicationData={applicationDetails?.applicationData}
                    // mutate={mutate}
                    // workflowDetails={workflowDetails}
                    // businessService={applicationDetails?.processInstancesDetails?.[0]?.businessService?.toUpperCase()}
                    moduleCode="works"
                    showToast={showToast}
                    setShowToast={setShowToast}
                    closeToast={closeToast}
                    // timelineStatusPrefix={`WORKS_${applicationDetails?.processInstancesDetails?.[0]?.businessService?.toUpperCase()}_`}
                    // oldValue={res}
                    // isInfoLabel={true}
                    // clearDataDetails={clearDataDetails}
                />

                {/* <ApplicationDetailsContent
                    applicationDetails={applicationDetails}
                    //workflowDetails={workflowDetails}
                    //isDataLoading={isDataLoading}
                    //applicationData={applicationData}
                    //businessService={businessService}
                    //timelineStatusPrefix={timelineStatusPrefix}
                    //statusAttribute={statusAttribute}
                    //paymentsList={paymentsList}
                    showTimeLine={false}
                //oldValue={oldValue}
                //isInfoLabel={isInfoLabel}
                /> */}
            </div>
            <ActionBar>
                {displayMenu ?
                    <Menu
                    localeKeyPrefix={"WORKS"}
                    options={actionULB}
                    optionKey={"name"}
                    t={t}
                    onSelect={onActionSelect}
                    />:null}
                <SubmitBar label={t("WF_TAKE_ACTION")} onSubmit={() => setDisplayMenu(!displayMenu)} />
            </ActionBar>
        </Fragment>

    )
}

export default ViewContract