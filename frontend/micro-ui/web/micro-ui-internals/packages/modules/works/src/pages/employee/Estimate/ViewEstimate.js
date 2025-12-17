import React, { useState, Fragment, useEffect, useRef } from "react";
import { Header, SubmitBar, Menu, ActionBar, Loader, MultiLink} from "@egovernments/digit-ui-react-components";
import { useParams, useHistory } from "react-router-dom";
import { Controller, useForm, useWatch } from 'react-hook-form'
import { useTranslation } from "react-i18next";
import ApplicationDetailsTemplate from "../../../../../templates/ApplicationDetails"
import ApplicationDetailsContent from "../../../../../templates/ApplicationDetails/components/ApplicationDetailsContent";
import ProcessingModal from "../../../components/Modal/ProcessingModal";
import RejectLOIModal from "../../../components/Modal/RejectLOIModal";
const ViewEstimate = (props) => {
    const { t } = useTranslation()
    const history = useHistory();
    const { register, control, watch, handleSubmit, formState: { errors, ...rest }, reset, trigger, getValues} = useForm({defaultValues: {}, mode: "onSubmit"});
    const menuRef = useRef();
    const [displayMenu, setDisplayMenu] = useState(false);

    const { tenantId, estimateNumber, department,estimateStatus } = Digit.Hooks.useQueryParams(); 

    const HandleDownloadPdf =async(tenantId,estimateNumber)=>{
        const response = await Digit.WorksService.downloadEstimate(tenantId, estimateNumber);
        downloadPdf(new Blob([response.data], { type: "application/pdf" }), `Estimate-${estimateNumber}.pdf`);
    }
    const downloadPdf = (blob, fileName) => {
        if (window.mSewaApp && window.mSewaApp.isMsewaApp() && window.mSewaApp.downloadBase64File) {
          var reader = new FileReader();
          reader.readAsDataURL(blob);
          reader.onloadend = function () {
            var base64data = reader.result;
            window.mSewaApp.downloadBase64File(base64data, fileName);
          };
        } else {
          const link = document.createElement("a");
          // create a blobURI pointing to our Blob
          link.href = URL.createObjectURL(blob);
          link.download = fileName;
          // some browser needs the anchor to be in the doc
          document.body.append(link);
          link.click();
          link.remove();
          // in case the Blob uses a lot of memory
          setTimeout(() => URL.revokeObjectURL(link.href), 7000);
        }
      };
    // to fetch a details of Estimate by using params t, tenantInfo, estimateNumber
    let { isLoading, isError, data: applicationDetails, error } = Digit.Hooks.works.useViewEstimateDetails(t,tenantId,estimateNumber);
    const tenant = Digit.ULBService.getStateId();

    let workflowDetails = Digit.Hooks.useWorkflowDetails(
        {
            tenantId: tenantId,
            id: estimateNumber,
            moduleCode: applicationDetails?.processInstancesDetails?.[0]?.businessService,
            config: {
                enabled: applicationDetails?.processInstancesDetails?.[0]?.businessService ? true : false,
                cacheTime:0
            }
        },
    );
    
    workflowDetails?.data?.actionState?.nextActions?.forEach((action) => {
        if (action?.action === "EDIT") {
            
            let pathName = `/${window.contextPath}/employee/works/modify-estimate?tenantId=${tenantId}&estimateNumber=${estimateNumber}`;
            action.redirectionUrll = {
                action: "EDIT_ESTIMATE_APPLICATION",
                pathname: pathName,
                state: {
                    applicationDetails: applicationDetails,
                    action: "EDIT_ESTIMATE_APPLICATION"
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
            "name":"FORWARD"
        },
        {
            "name":"REJECT_ESTIMATE"
        },
        {
            "name":"MODIFY_ESTIMATE"
        },
        {
            "name":"APPROVE_ESTIMATE"
        }
    ]

    function onActionSelect(action) {
        if(action?.name==="FORWARD"){
            setShowModal(true)
        }
        if(action?.name==="REJECT_ESTIMATE"){
            setShowRejectModal(true)
        }
        if(action?.name==="MODIFY_ESTIMATE"){
            history.push(`/${window?.contextPath}/employee/works/modify-estimate`,{ tenantId,estimateNumber })
        }
        if(action?.name==="APPROVE_ESTIMATE"){
            
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

    const closeMenu = () => {
        setDisplayMenu(false);
    }
    Digit.Hooks.useClickOutside(menuRef, closeMenu, displayMenu );

    // call update estimate API to update estimate form values and application staus during workflow action 
    const {
        isLoading: updatingApplication,
        isError: updateApplicationError,
        data: updateResponse,
        error: updateError,
        mutate,
    } = Digit.Hooks.works.useApplicationActionsEstimate();

    const [showToast, setShowToast] = useState(null);

    const closeToast = () => {
        setShowToast(null);
    };
    
    return (
        <Fragment>
            <div className={"employee-main-application-details"}>
                <div className={"employee-application-details"} style={{ marginBottom: "24px" }}>
                    <Header styles={{ marginLeft: "0px", paddingTop: "10px", fontSize: "32px" }}>{estimateStatus?t("WORKS_VIEW_APPROVED_ESTIMATE"):t("WORKS_VIEW_ESTIMATE")}</Header>
                    <MultiLink
                        className="multilinkWrapper employee-mulitlink-main-div"
                        onHeadClick={()=>HandleDownloadPdf(tenantId,estimateNumber)}
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
                  heading={"WORKS_REJECT_ESTIMATE"}
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
                  department={t(`ES_COMMON_${department}`)}
                  estimateNumber={estimateNumber}
                />}

                {isLoading ? <Loader/> :
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
                    timelineStatusPrefix={`WF_${applicationDetails?.processInstancesDetails?.[0]?.businessService?.toUpperCase()}_`}
                    // oldValue={res}
                    // isInfoLabel={true}
                    // clearDataDetails={clearDataDetails}
                />
                // <ApplicationDetailsContent
                //     applicationDetails={applicationDetails}
                //     //workflowDetails={workflowDetails}
                //     //isDataLoading={isDataLoading}
                //     //applicationData={applicationData}
                //     //businessService={businessService}
                //     //timelineStatusPrefix={timelineStatusPrefix}
                //     //statusAttribute={statusAttribute}
                //     //paymentsList={paymentsList}
                //     showTimeLine={false}
                // //oldValue={oldValue}
                // //isInfoLabel={isInfoLabel}
                // />
                }
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

export default ViewEstimate