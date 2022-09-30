import React, { useState, Fragment, useEffect, useRef } from "react";
import { Header, SubmitBar, Menu, ActionBar,Loader } from "@egovernments/digit-ui-react-components";
import { useParams, useHistory } from "react-router-dom";
import { Controller, useForm, useWatch } from 'react-hook-form'
import { useTranslation } from "react-i18next";
import { ApplicationDetailsTemplate } from "../../../../../templates/ApplicationDetails"
import ApplicationDetailsContent from "../../../../../templates/ApplicationDetails/components/ApplicationDetailsContent";
import ProcessingModal from "../../../components/Modal/ProcessingModal";
import RejectLOIModal from '../../../components/Modal/RejectLOIModal'
const ViewLOI = (props) => {
    const { t } = useTranslation()
    const { register, control, watch, handleSubmit, formState: { errors, ...rest }, reset, trigger, getValues} = useForm({defaultValues: {}, mode: "onSubmit"});
    const menuRef = useRef();
    const [displayMenu, setDisplayMenu] = useState(false);
    let { loiNumber, subEstimateNumber } = Digit.Hooks.useQueryParams();
    subEstimateNumber = "EP/2022-23/09/000094/000070"
    const tenant = Digit.ULBService.getStateId();
    const tenantId = Digit.ULBService.getCurrentTenantId();
    
    let { isLoading, isError, data: applicationDetails, error } = Digit.Hooks.works.useViewLOIDetails(t,tenantId,loiNumber,subEstimateNumber,{enabled:!!(loiNumber && subEstimateNumber)});
    
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

    if (isLoading) return <Loader />

    return (
        <Fragment>
            <div className={"employee-main-application-details"}>
                <div className={"employee-application-details"} style={{ marginBottom: "15px" }}>
                    <Header styles={{ marginLeft: "0px", paddingTop: "10px", fontSize: "32px" }}>{t("WORKS_VIEW_LOI")}</Header>
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

                <ApplicationDetailsContent
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
                />
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
                <SubmitBar ref={menuRef} label={t("WF_TAKE_ACTION")} onSubmit={() => setDisplayMenu(!displayMenu)} />
            </ActionBar>
        </Fragment>

    )
}

export default ViewLOI