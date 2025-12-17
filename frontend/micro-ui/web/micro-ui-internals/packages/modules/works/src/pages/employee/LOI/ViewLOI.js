import React, { useState, Fragment, useEffect, useRef } from "react";
import { Header, SubmitBar, Menu, ActionBar, Loader, MultiLink } from "@egovernments/digit-ui-react-components";
import { useParams, useHistory } from "react-router-dom";
import { Controller, useForm, useWatch } from 'react-hook-form'
import { useTranslation } from "react-i18next";
import ApplicationDetailsTemplate  from "../../../../../templates/ApplicationDetails"
import ApplicationDetailsContent from "../../../../../templates/ApplicationDetails/components/ApplicationDetailsContent";
import ProcessingModal from "../../../components/Modal/ProcessingModal";
import RejectLOIModal from '../../../components/Modal/RejectLOIModal'
import getPDFData from "../../../utils/getWorksAcknowledgementData"
const ViewLOI = (props) => {
    const { t } = useTranslation()
    const { register, control, watch, handleSubmit, formState: { errors, ...rest }, reset, trigger, getValues} = useForm({defaultValues: {}, mode: "onSubmit"});
    const menuRef = useRef();
    const [displayMenu, setDisplayMenu] = useState(false);
    let { loiNumber, subEstimateNumber } = Digit.Hooks.useQueryParams();
    subEstimateNumber = "EP/2022-23/09/000094/000070"
    const tenant = Digit.ULBService.getStateId();
    const tenantId = Digit.ULBService.getCurrentTenantId();
    
    // to fetch a details of LOI by using params t, tenantInfo, loiNumber, subEstiamteNumber
    let { isLoading, isError, data: applicationDetails, error } = Digit.Hooks.works.useViewLOIDetails(t,tenantId,loiNumber,subEstimateNumber,{enabled:!!(loiNumber && subEstimateNumber)});
    
    let workflowDetails = Digit.Hooks.useWorkflowDetails(
        {
            tenantId: tenantId,
            id: loiNumber,
            moduleCode: applicationDetails?.processInstancesDetails?.[0]?.businessService,
            config: {
                enabled: applicationDetails?.processInstancesDetails?.[0]?.businessService ? true : false,
            }
        },
    );

    workflowDetails?.data?.actionState?.nextActions?.forEach((action) => {
        if (action?.action === "EDIT") {
            
            let pathName = `/${window?.contextPath}/employee/works/create-loi?loiNumber=${applicationDetails?.applicationData?.letterOfIndentNumber}&isEdit=true&subEstimateNumber=EP/2022-23/09/000080/000056`;
            action.redirectionUrll = {
                action: "EDIT_LOI_APPLICATION",
                pathname: pathName,
                state: {
                    applicationDetails: applicationDetails,
                    action: "EDIT_LOI_APPLICATION"
                },
            };
        }
    })

    let paginationParams = { limit: 10, offset:0, sortOrder:"ASC" }
    const { isLoading: hookLoading, data:employeeData } = Digit.Hooks.hrms.useHRMSSearch(
        null,
        tenantId,
        paginationParams,
        null
    );
    const { isLoading:desgLoading, data:designationData } = Digit.Hooks.useCustomMDMS(
        tenant,
        "common-masters",
        [
            {
                "name": "Designation"
            }
        ]
        );

    if (designationData?.[`common-masters`]) {
        var { Designation } = designationData?.[`common-masters`]
    }
    
    const {  data, isFetched } = Digit.Hooks.useCustomMDMS(
        tenant,
        "works",
        [
            {
                "name": "Department"
            }
        ]
    );

    if (data?.works) {
        var { Department } = data?.works
    }
    
    const [showModal, setShowModal] = useState(false)
    const [showRejectModal, setShowRejectModal] = useState(false)
    
    const actionULB=[
        {
            "name":"FORWARD_LOI"
        },
        {
            "name":"REJECT_LOI"
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

    // call update LOI API to update LI form values and application staus during workflow action 
    const {
        isLoading: updatingApplication,
        isError: updateApplicationError,
        data: updateResponse,
        error: updateError,
        mutate,
    } = Digit.Hooks.works.useApplicationActionsLOI();

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
                <div className={"employee-application-details"} style={{ marginBottom: "24px" }}>
                    <Header styles={{ marginLeft: "0px", paddingTop: "10px", fontSize: "32px" }}>{t("WORKS_VIEW_LOI")}</Header>
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
                    Department={Department}
                    Designation={Designation}

                />}

                {showRejectModal && <RejectLOIModal
                  t={t}
                  heading={"WORKS_REJECT_LOI"}
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
                    mutate={mutate}
                    workflowDetails={workflowDetails}
                    businessService={applicationDetails?.processInstancesDetails?.[0]?.businessService?.toUpperCase()}
                    moduleCode="works"
                    showToast={showToast}
                    setShowToast={setShowToast}
                    closeToast={closeToast}
                    timelineStatusPrefix={`WORKS_${applicationDetails?.processInstancesDetails?.[0]?.businessService?.toUpperCase()}_`}
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
            {/* <ActionBar>
                {displayMenu ?
                    <Menu
                    localeKeyPrefix={"WORKS"}
                    options={actionULB}
                    optionKey={"name"}
                    t={t}
                    onSelect={onActionSelect}
                    />:null}
                <SubmitBar ref={menuRef} label={t("WF_TAKE_ACTION")} onSubmit={() => setDisplayMenu(!displayMenu)} />
            </ActionBar> */}
        </Fragment>

    )
}

export default ViewLOI