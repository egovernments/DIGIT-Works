import React from "react";
import { Modal, Card,LabelFieldPair,CardLabel, CardLabelError,Dropdown,TextArea } from "@egovernments/digit-ui-react-components";
import { Controller } from 'react-hook-form'


const Heading = (props) => {
    return <h1 className="heading-m">{props.t(props.heading)}</h1>;
};

const Close = () => (
    <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="#FFFFFF">
        <path d="M0 0h24v24H0V0z" fill="none" />
        <path d="M19 6.41L17.59 5 12 10.59 6.41 5 5 6.41 10.59 12 5 17.59 6.41 19 12 13.41 17.59 19 19 17.59 13.41 12 19 6.41z" />
    </svg>
);

const CloseBtn = (props) => {
    return (
        <div className="icon-bg-secondary" onClick={props.onClick}>
            <Close />
        </div>
    );
};


const RejectLOIModal = ({
    t,
    heading,
    closeModal,
    actionCancelLabel,
    actionCancelOnSubmit,
    actionSaveLabel,
    actionSaveOnSubmit,
    handleSubmit,
    control,
    register,
    errors:formErrors,
    estimateNumber,
    department
}) => {

    const rejectReasons = [
        {
            name: "Estimate Details are incorrect"
        },
        {
            name: "Financial Details are incorrect"
        },
        {
            name: "Agreement Details are incorrect"
        },
        {
            name: "Vendor Details are incorrect"
        },
        {
            name: "Attachments provided are wrong"
        },
        {
            name: "Others"
        },
    ]

    return (
        <Modal
            headerBarMain={<Heading t={t} heading={heading} />}
            headerBarEnd={<CloseBtn onClick={closeModal} />}
            // actionCancelLabel={t(actionCancelLabel)}
            // actionCancelOnSubmit={actionCancelOnSubmit}
            actionSaveLabel={t(actionSaveLabel)}
            actionSaveOnSubmit={handleSubmit(actionSaveOnSubmit)}
            formId="modal-action"
            headerBarMainStyle={{ marginLeft: "20px" }}
        >

            <Card style={{ boxShadow: "none" }}>
                <LabelFieldPair>
                  <CardLabel style={{fontWeight:"bold"}}>{estimateNumber ? t("WORKS_ESTIMATE_NO") : t("WORKS_LOI_ID")}</CardLabel>
                  <CardLabel style={{width:"100%",marginLeft:"35px"}}>{estimateNumber ? estimateNumber : "NA"}</CardLabel>
                </LabelFieldPair>
                <LabelFieldPair>
                  <CardLabel style={{ "fontSize": "16px",fontWeight:"bold"}}>{t("WORKS_DEPARTMENT")}</CardLabel>
                  <CardLabel >{department ? department : department}</CardLabel>
                </LabelFieldPair>
                <LabelFieldPair>
                  <CardLabel style={{ "fontSize": "16px",fontWeight:"bold"}}>{t("WORKS_REJECT_REASON")}</CardLabel>
                  <Controller
                        name="reason"
                        control={control}
                        rules={{ required: true }}
                        render={(props) => {
                            return (
                                <Dropdown
                                    style={{width:"100%","marginTop":"3px",marginLeft:"35px"}}
                                    onBlur={props.onBlur}
                                    option={rejectReasons}
                                    selected={props?.value}
                                    optionKey={"name"}
                                    t={t}
                                    select={props?.onChange}
                                />
                            );
                        }}
                    />
                </LabelFieldPair>
                      {formErrors && formErrors?.reason?.type === "required" && (
                          <CardLabelError style={{marginLeft:"135px"}}>{t(`WORKS_REQUIRED_ERR`)}</CardLabelError>)}
                <LabelFieldPair>
                  <CardLabel style={{ "fontSize": "16px",fontWeight:"bold"}}>{t("WORKS_COMMENTS")}</CardLabel>
                      <Controller
                          name="comments"
                          control={control}
                          rules={{ required: true }}
                          render={(props) => {
                              return (
                                <TextArea
                                style={{width:"100%",marginLeft:"35px"}}
                                name={"comments"}
                                inputRef={register({
                                    maxLength:140
                                })}
                            />
                              );
                          }}
                      />
                </LabelFieldPair>
                      {formErrors && formErrors?.comments?.type === "required" && (
                          <CardLabelError style={{marginLeft:"135px"}}>{t(`WORKS_REQUIRED_ERR`)}</CardLabelError>)}
            </Card>
        </Modal>
    );
};

export default RejectLOIModal;