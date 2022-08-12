import React, { useState, useEffect} from "react";
import { CardLabel, LabelFieldPair, Dropdown, TextInput, LinkButton, CardLabelError, MobileNumber, DatePicker, Loader } from "@egovernments/digit-ui-react-components";
import { useTranslation } from "react-i18next";
import { useForm, Controller } from "react-hook-form";

const WORKSContractorDetails = () => {
  const { t } = useTranslation();
  const [focusIndex, setFocusIndex] = useState({ index: -1, type: "" });
  const { control, formState: localFormState, watch, setError: setLocalError, clearErrors: clearLocalErrors, setValue, trigger, getValues } = useForm();
  const formValue = watch();
  const { errors } = localFormState;

  const errorStyle = { width: "70%", marginLeft: "30%", fontSize: "12px", marginTop: "-21px" };
  const CardLabelStyle={marginTop: "-5px", fontWeight: "700" }
  return (
    <div>
          <LabelFieldPair>
            <CardLabel style={CardLabelStyle}>{`${t("WORKS_CONTRACTOR_CODE")}`}</CardLabel>
            <div className="field">
              <Controller
                control={control}
                name={"ContractorCode"}
                defaultValue={""}
                rules={{ required: t("REQUIRED_FIELD"), validate: { pattern: (val) => (/^[-@.\/#&+\w\s]*$/.test(val) ? true : t("INVALID_NAME")) } }}
                render={(props) => (
                  <TextInput
                    value={props.value}
                    autoFocus={focusIndex.index === "" && focusIndex.type === "name"}
                    errorStyle={(localFormState.touched.tradeName && errors?.tradeName?.message) ? true : false}
                    onChange={(e) => {
                      props.onChange(e.target.value);
                      setFocusIndex({ index: "", type: "ContractorCode" });
                    }}
                    onBlur={(e) => {
                      setFocusIndex({ index: -1 });
                      props.onBlur(e);
                    }}
                    // disable={isRenewal}
                  />
                )}
              />
            </div>
          </LabelFieldPair>
          <CardLabelError style={errorStyle}>{localFormState.touched.ContractorCode ? errors?.ContractorCode?.message : ""}</CardLabelError>
          <LabelFieldPair>
            <CardLabel style={CardLabelStyle}>{`${t("WORKS_NAME")}`}</CardLabel>
            <div className="field">
              <Controller
                control={control}
                name={"Name"}
                defaultValue={""}
                rules={{ required: t("REQUIRED_FIELD"), validate: { pattern: (val) => (/^[-@.\/#&+\w\s]*$/.test(val) ? true : t("INVALID_NAME")) } }}
                render={(props) => (
                  <TextInput
                    value={props.value}
                    autoFocus={focusIndex.index === "" && focusIndex.type === "name"}
                    errorStyle={(localFormState.touched.tradeName && errors?.tradeName?.message) ? true : false}
                    onChange={(e) => {
                      props.onChange(e.target.value);
                      setFocusIndex({ index: "", type: "Name" });
                    }}
                    onBlur={(e) => {
                      setFocusIndex({ index: -1 });
                      props.onBlur(e);
                    }}
                    // disable={isRenewal}
                  />
                )}
              />
            </div>
          </LabelFieldPair>
          <CardLabelError style={errorStyle}>{localFormState.touched.Name ? errors?.Name?.message : ""}</CardLabelError>
          <LabelFieldPair>
          <CardLabel style={CardLabelStyle} className="card-label-smaller">{`${t("WORKS_CORRESPONDANCE_ADDRESS")}`}</CardLabel>
          <div className="field">
            <Controller
              control={control}
              name="CorrespondanceAddress"
              defaultValue={""}
              rules={{ validate: (e) => ((e && getPattern("CorrespondanceAddress").test(e)) || !e ? true : t("ERR_DEFAULT_INPUT_FIELD_MSG")), required: t("REQUIRED_FIELD") }}
              isMandatory={true}
              render={(props) => (
                <TextInput
                  value={props.value}
                  autoFocus={focusIndex.index === 1 && focusIndex.type === "CorrespondanceAddress"}
                  errorStyle={(localFormState.touched.CorrespondanceAddress && errors?.CorrespondanceAddress?.message) ? true : false}
                  onChange={(e) => {
                    props.onChange(e.target.value);
                    setFocusIndex({ index: 1, type: "CorrespondanceAddress" });
                  }}
                  labelStyle={{ marginTop: "unset" }}
                  onBlur={props.onBlur}
                />
              )}
            />
          </div>
          </LabelFieldPair>
          <CardLabelError style={errorStyle}>{localFormState.touched.CorrespondanceAddress ? errors?.CorrespondanceAddress?.message : ""}</CardLabelError>
          <LabelFieldPair>
          <CardLabel style={CardLabelStyle} className="card-label-smaller">{`${t("WORKS_PERMENANT_ADDRESS")}`}</CardLabel>
          <div className="field">
            <Controller
              control={control}
              name="permenantAddress"
              defaultValue={""}
              rules={{ validate: (e) => ((e && getPattern("permenantAddress").test(e)) || !e ? true : t("ERR_DEFAULT_INPUT_FIELD_MSG")), required: t("REQUIRED_FIELD") }}
              isMandatory={true}
              render={(props) => (
                <TextInput
                  value={props.value}
                  autoFocus={focusIndex.index === 1 && focusIndex.type === "permenantAddress"}
                  errorStyle={(localFormState.touched.address && errors?.address?.message) ? true : false}
                  onChange={(e) => {
                    props.onChange(e.target.value);
                    setFocusIndex({ index: 1, type: "permenantAddress" });
                  }}
                  labelStyle={{ marginTop: "unset" }}
                  onBlur={props.onBlur}
                />
              )}
            />
          </div>
          </LabelFieldPair>
          <CardLabelError style={errorStyle}>{localFormState.touched.permenantAddress ? errors?.address?.permenantAddress : ""}</CardLabelError>
          <LabelFieldPair>
                <CardLabel style={CardLabelStyle}>{t("WORKS_CONTACT_PERSON")}</CardLabel>
                <div className="field">
                  <Controller
                    control={control}
                    name={"contactPerson"}
                    defaultValue={""}
                    rules={{
                      required: t("CORE_COMMON_REQUIRED_ERRMSG"),
                      validate: {
                        pattern: (v) => (/^[a-zA-Z\s]+$/.test(v) ? true : t("ERR_DEFAULT_INPUT_FIELD_MSG")),
                      },
                    }}
                    render={(props) => (
                      <TextInput
                        value={props.value}
                        // disable={isEditScreen}
                        name={"contactPerson"}
                        autoFocus={focusIndex.index === 101 && focusIndex.type === "contactPerson"}
                        onChange={(e) => {
                          props.onChange(e.target.value);
                          setFocusIndex({ index: 101, type: "contactPerson"});
                        }}
                        onBlur={(e) => {
                          setFocusIndex({ index: -1 });
                          props.onBlur(e);
                        }}
                      />
                    )}
                  />
                </div>
          </LabelFieldPair>
          <CardLabelError style={errorStyle}>{localFormState.touched?.contactPerson ? errors?.contactPerson?.message : ""}</CardLabelError>
          <LabelFieldPair>
          <CardLabel style={CardLabelStyle} className="card-label-smaller">{`${t("CORE_COMMON_EMAIL")}`}</CardLabel>
          <div className="field">
            <Controller
              control={control}
              name="email"
              defaultValue={""}
              rules={{ validate: (e) => ((e && getPattern("email").test(e)) || !e ? true : t("ERR_DEFAULT_INPUT_FIELD_MSG")), required: t("REQUIRED_FIELD") }}
              isMandatory={true}
              render={(props) => (
                <TextInput
                  value={props.value}
                  autoFocus={focusIndex.index === 1 && focusIndex.type === "email"}
                  errorStyle={(localFormState.touched.address && errors?.address?.message) ? true : false}
                  onChange={(e) => {
                    props.onChange(e.target.value);
                    setFocusIndex({ index: 1, type: "email" });
                  }}
                  labelStyle={{ marginTop: "unset" }}
                  onBlur={props.onBlur}
                />
              )}
            />
          </div>
          </LabelFieldPair>
          <CardLabelError style={errorStyle}>{localFormState.touched.email ? errors?.address?.email : ""}</CardLabelError>
          <LabelFieldPair>
                <CardLabel style={CardLabelStyle}>{t("WORKS_NARRATION")}</CardLabel>
                <div className="field">
                  <Controller
                    control={control}
                    name={"narration"}
                    defaultValue={""}
                    rules={{
                      required: t("CORE_COMMON_REQUIRED_ERRMSG"),
                      validate: {
                        pattern: (v) => (/^[a-zA-Z\s]+$/.test(v) ? true : t("ERR_DEFAULT_INPUT_FIELD_MSG")),
                      },
                    }}
                    render={(props) => (
                      <TextInput
                        value={props.value}
                        // disable={isEditScreen}
                        name={"narration"}
                        autoFocus={focusIndex.index === 101 && focusIndex.type === "narration"}
                        onChange={(e) => {
                          props.onChange(e.target.value);
                          setFocusIndex({ index: 101, type: "narration"});
                        }}
                        onBlur={(e) => {
                          setFocusIndex({ index: -1 });
                          props.onBlur(e);
                        }}
                      />
                    )}
                  />
                </div>
          </LabelFieldPair>
          <CardLabelError style={errorStyle}>{localFormState.touched?.narration ? errors?.narration?.message : ""}</CardLabelError>
          <LabelFieldPair>
          <CardLabel style={CardLabelStyle} className="card-label-smaller">{`${t("CORE_COMMON_MOBILE_NUMBER")}`}</CardLabel>
          <div className="field">
            <Controller
              control={control}
              name="mobileNumber"
              defaultValue={""}
              rules={{ validate: (e) => ((e && getPattern("MobileNo").test(e)) || !e ? true : t("ERR_DEFAULT_INPUT_FIELD_MSG")), required: t("REQUIRED_FIELD") }}
              //type="number"
              isMandatory={true}
              render={(props) => (
                <TextInput
                  //type="number"
                  value={props.value}
                  autoFocus={focusIndex.index === 1 && focusIndex.type === "mobileNumber"}
                  errorStyle={(localFormState.touched.mobileNumber && errors?.mobileNumber?.message) ? true : false}
                  onChange={(e) => {
                    props.onChange(e.target.value);
                    setFocusIndex({ index: 1, type: "mobileNumber" });
                  }}
                  labelStyle={{ marginTop: "unset" }}
                  onBlur={props.onBlur}
                />
              )}
            />
          </div>
          </LabelFieldPair>
          <CardLabelError style={errorStyle}>{localFormState.touched.mobileNumber ? errors?.mobileNumber?.message : ""}</CardLabelError>
          <LabelFieldPair>
            <CardLabel style={CardLabelStyle}>{`${t("WORKS_PAN_NUMBER")} `}</CardLabel>
            <div className="field">
              <Controller
                control={control}
                name="panNo"
                defaultValue={""}
                rules={{ validate: (e) => ((e && getPattern("panNo").test(e)) || !e ? true : t("ERR_DEFAULT_INPUT_FIELD_MSG")) }}
                render={(props) => (
                  <TextInput
                    value={props.value}
                    autoFocus={focusIndex.index === "" && focusIndex.type === "panNo"}
                    errorStyle={(localFormState.touched.gstNo && errors?.gstNo?.message) ? true : false}
                    onChange={(e) => {
                      props.onChange(e.target.value);
                      setFocusIndex({ index: "", type: "panNo" });
                    }}
                    labelStyle={{ marginTop: "unset" }}
                    onBlur={props.onBlur}
                    // disable={isRenewal}
                  />
                )}
              />
            </div>
          </LabelFieldPair>
          <CardLabelError style={errorStyle}>{localFormState.touched.panNo ? errors?.panNo?.message : ""}</CardLabelError>
          <LabelFieldPair>
            <CardLabel style={CardLabelStyle}>{`${t("WORKS_TIN_NUMBER")} `}</CardLabel>
            <div className="field">
              <Controller
                control={control}
                name="tinNo"
                defaultValue={""}
                rules={{ validate: (e) => ((e && getPattern("tinNo").test(e)) || !e ? true : t("ERR_DEFAULT_INPUT_FIELD_MSG")) }}
                render={(props) => (
                  <TextInput
                    value={props.value}
                    autoFocus={focusIndex.index === "" && focusIndex.type === "tinNo"}
                    errorStyle={(localFormState.touched.gstNo && errors?.gstNo?.message) ? true : false}
                    onChange={(e) => {
                      props.onChange(e.target.value);
                      setFocusIndex({ index: "", type: "tinNo" });
                    }}
                    labelStyle={{ marginTop: "unset" }}
                    onBlur={props.onBlur}
                    // disable={isRenewal}
                  />
                )}
              />
            </div>
          </LabelFieldPair>
          <CardLabelError style={errorStyle}>{localFormState.touched.tinNo ? errors?.tinNo?.message : ""}</CardLabelError>
          <LabelFieldPair>
            <CardLabel style={CardLabelStyle}>{`${t("WORKS_GSTIN_NUMBER")} `}</CardLabel>
            <div className="field">
              <Controller
                control={control}
                name="gstNo"
                defaultValue={""}
                rules={{ validate: (e) => ((e && getPattern("GSTNo").test(e)) || !e ? true : t("ERR_DEFAULT_INPUT_FIELD_MSG")) }}
                render={(props) => (
                  <TextInput
                    value={props.value}
                    autoFocus={focusIndex.index === "" && focusIndex.type === "gstNo"}
                    errorStyle={(localFormState.touched.gstNo && errors?.gstNo?.message) ? true : false}
                    onChange={(e) => {
                      props.onChange(e.target.value);
                      setFocusIndex({ index: "", type: "gstNo" });
                    }}
                    labelStyle={{ marginTop: "unset" }}
                    onBlur={props.onBlur}
                    // disable={isRenewal}
                  />
                )}
              />
            </div>
          </LabelFieldPair>
          <CardLabelError style={errorStyle}>{localFormState.touched.gstNo ? errors?.gstNo?.message : ""}</CardLabelError>
          <LabelFieldPair>
          <CardLabel style={CardLabelStyle} className="card-label-smaller">{`${t("WORKS_BANK")}`}</CardLabel>
          <Controller
            control={control}
            name={"Bank"}
            defaultValue={""}
            rules={{ required: t("REQUIRED_FIELD") }}
            isMandatory={true}
            render={(props) => (
              <Dropdown
                className="form-field"
                selected={getValues("Bank")}
                disable={false}
                option={[]}
                errorStyle={(localFormState.touched.Bank && errors?.Bank?.message) ? true : false}
                select={(e) => {
                  props.onChange(e);
                }}
                optionKey="i18nKey"
                onBlur={props.onBlur}
                t={t}
              />
            )}
          />
          </LabelFieldPair>
          <CardLabelError style={errorStyle}>{localFormState.touched.Bank ? errors?.Bank?.message : ""}</CardLabelError>
          <LabelFieldPair>
          <CardLabel style={CardLabelStyle} className="card-label-smaller">{`${t("WORKS_IFSC_CODE")}`}</CardLabel>
          <Controller
            control={control}
            name={"IFSCCode"}
            defaultValue={""}
            rules={{ required: t("REQUIRED_FIELD") }}
            isMandatory={true}
            render={(props) => (
              <Dropdown
                className="form-field"
                selected={getValues("IFSCCode")}
                disable={false}
                option={[]}
                errorStyle={(localFormState.touched.IFSCCode && errors?.relationship?.IFSCCode) ? true : false}
                select={(e) => {
                  props.onChange(e);
                }}
                optionKey="i18nKey"
                onBlur={props.onBlur}
                t={t}
              />
            )}
          />
          </LabelFieldPair>
          <CardLabelError style={errorStyle}>{localFormState.touched.IFSCCode ? errors?.IFSCCode?.message : ""}</CardLabelError>
          <LabelFieldPair>
            <CardLabel style={CardLabelStyle}>{`${t("WORKS_BANK_ACCOUNT_NUMBER")} `}</CardLabel>
            <div className="field">
              <Controller
                control={control}
                name="bankAccountNumber"
                defaultValue={""}
                rules={{ validate: (e) => ((e && getPattern("bankAccountNumber").test(e)) || !e ? true : t("ERR_DEFAULT_INPUT_FIELD_MSG")) }}
                render={(props) => (
                  <TextInput
                    value={props.value}
                    autoFocus={focusIndex.index === "" && focusIndex.type === "bankAccountNumber"}
                    errorStyle={(localFormState.touched.gstNo && errors?.gstNo?.message) ? true : false}
                    onChange={(e) => {
                      props.onChange(e.target.value);
                      setFocusIndex({ index: "", type: "bankAccountNumber" });
                    }}
                    labelStyle={{ marginTop: "unset" }}
                    onBlur={props.onBlur}
                    // disable={isRenewal}
                  />
                )}
              />
            </div>
          </LabelFieldPair>
          <CardLabelError style={errorStyle}>{localFormState.touched.bankAccountNumber ? errors?.bankAccountNumber?.message : ""}</CardLabelError>
          <LabelFieldPair>
            <CardLabel style={CardLabelStyle}>{`${t("WORKS_PWD_APPROVAL_CODE")} `}</CardLabel>
            <div className="field">
              <Controller
                control={control}
                name="PWDApprovalCode"
                defaultValue={""}
                rules={{ validate: (e) => ((e && getPattern("PWDApprovalCode").test(e)) || !e ? true : t("ERR_DEFAULT_INPUT_FIELD_MSG")) }}
                render={(props) => (
                  <TextInput
                    value={props.value}
                    autoFocus={focusIndex.index === "" && focusIndex.type === "PWDApprovalCode"}
                    errorStyle={(localFormState.touched.gstNo && errors?.gstNo?.message) ? true : false}
                    onChange={(e) => {
                      props.onChange(e.target.value);
                      setFocusIndex({ index: "", type: "PWDApprovalCode" });
                    }}
                    labelStyle={{ marginTop: "unset" }}
                    onBlur={props.onBlur}
                    // disable={isRenewal}
                  />
                )}
              />
            </div>
          </LabelFieldPair>
          <CardLabelError style={errorStyle}>{localFormState.touched.PWDApprovalCode ? errors?.PWDApprovalCode?.message : ""}</CardLabelError>
          <LabelFieldPair>
          <CardLabel style={CardLabelStyle} className="card-label-smaller">{`${t("WORKS_EXEMPTED_FROM")}`}</CardLabel>
          <Controller
            control={control}
            name={"exemptedFrom"}
            defaultValue={""}
            rules={{ required: t("REQUIRED_FIELD") }}
            isMandatory={true}
            render={(props) => (
              <Dropdown
                className="form-field"
                selected={getValues("exemptedFrom")}
                disable={false}
                option={[]}
                errorStyle={(localFormState.touched.exemptedFrom && errors?.exemptedFrom?.message) ? true : false}
                select={(e) => {
                  props.onChange(e);
                }}
                optionKey="i18nKey"
                onBlur={props.onBlur}
                t={t}
              />
            )}
          />
          </LabelFieldPair>
          <CardLabelError style={errorStyle}>{localFormState.touched.exemptedFrom ? errors?.exemptedFrom?.message : ""}</CardLabelError>
    </div>
  )
}

export default WORKSContractorDetails