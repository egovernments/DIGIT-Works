import React, { useState, useEffect} from "react";
import { CardLabel, LabelFieldPair, Dropdown, TextInput, LinkButton, CardLabelError, MobileNumber, DatePicker, Loader } from "@egovernments/digit-ui-react-components";
import { useTranslation } from "react-i18next";
import { useForm, Controller } from "react-hook-form";
import _ from "lodash";

const createContractorDetails = () => ({
  contractorCode: "",
  contractorName: "",
  correspondanceAddress: "",
  permenantAddress: "",
  contactPerson: "",
  email: "",
  narration: "",
  mobileNumber: "",
  panNo:"",
  tinNo:"",
  gstNo:"",
  bankName:"",
  IFSCCode:"",
  bankAccountNumber:"",
  PWDApprovalCode:"",
  key: Date.now()
});

const WORKSContractorDetails = ({ config, onSelect, userType, formData, setError, formState, clearErrors }) => {
  const { t } = useTranslation();
  const [contractorDetails,setContractorDetails]=useState(createContractorDetails());
  const [focusIndex, setFocusIndex] = useState({ index: -1, type: "" });
  const { control, formState: localFormState, watch, setError: setLocalError, clearErrors: clearLocalErrors, setValue, trigger, getValues } = useForm();
  const formValue = watch();
  const { errors } = localFormState;
  const [isErrors, setIsErrors] = useState(false);
  const tenant = Digit.ULBService.getStateId();
  const { isLoading, data, isFetched } = Digit.Hooks.useCustomMDMS(
    tenant,
    "finance",
    [
        {
            "name": "Bank"
        }
    ]
    );
    if(data?.finance){
      var { Bank } = data?.finance
    }
  const exemptedFromULB=[
      {
        name:"Income tax", code:'Income tax', active:true
      },
      {
        name:"Earnest money deposit (EMD)", code:'Earnest money deposit (EMD)', active:true
      },
      {
        name:"VAT", code:'VAT', active:true
      },
  ]  
  const dummyData=[
    {
      name: 'Burhan', code: 'Burhan', active: true
    },
    {
      name: 'Jagan', code: 'Jagan', active: true
    },
    {
      name: 'Nipun', code: 'Nipun', active: true
    }
  ]
  useEffect(() => {
    if (Object.keys(errors).length && !_.isEqual(formState.errors[config.key]?.type || {}, errors)) {
      setError(config.key, { type: errors });
    }
    else if (!Object.keys(errors).length && formState.errors[config.key] && isErrors) {
      clearErrors(config.key);
    }
  }, [errors]);

  useEffect(() => {
    trigger();
  }, []);

  useEffect(() => {
    const keys = Object.keys(formValue);
    const part = {};
    keys.forEach((key) => (part[key] = contractorDetails[key]));
    let _ownerType = {};
    if (!_.isEqual(formValue, part)) {
      Object.keys(formValue).map(data => {
        if (data != "key" && formValue[data] != undefined && formValue[data] != "" && formValue[data] != null && !isErrors) {
          setIsErrors(true);
        }
      });
      setContractorDetails((prev) =>
         (prev.key && prev.key === contractorDetails.key ? { ...prev, ...formValue, ..._ownerType } : { ...prev })
      );
      // setContractorDetails((prev) => prev.map((o) => {
      //   return (o.key && o.key === contractorDetails.key ? { ...o, ...formValue, ..._ownerType } : { ...o })
      // }));
      trigger();
    }
  }, [formValue]);

  const errorStyle = { width: "70%", marginLeft: "30%", fontSize: "12px", marginTop: "-21px" };
  const CardLabelStyle={marginTop: "-5px", fontWeight: "700" }
  return (
    <div>
          <React.Fragment>
          <LabelFieldPair>
            <CardLabel style={CardLabelStyle}>{`${t("WORKS_CONTRACTOR_CODE")}`}</CardLabel>
            <div className="field">
              <Controller
                control={control}
                name={"ContractorCode"}
                defaultValue={contractorDetails.contractorCode}
                rules={{ validate: {
                  pattern: (v) => (/^$|^[a-zA-Z0-9]+$/.test(v) ? true : t("ERR_DEFAULT_INPUT_FIELD_MSG")),
                } }}
                render={(props) => (
                  <TextInput
                    value={props.value}
                    autoFocus={focusIndex.index === contractorDetails.key && focusIndex.type === "ContractorCode"}
                    errorStyle={(localFormState.touched.ContractorCode && errors?.ContractorCode?.message) ? true : false}
                    onChange={(e) => {
                      props.onChange(e.target.value);
                      setFocusIndex({ index: contractorDetails.key, type: "ContractorCode" });
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
                name="Name"
                defaultValue={contractorDetails.contractorName}
                rules={{ required: t("WORKS_REQUIRED_ERR"), validate: {
                  pattern: (v) => (/^[a-zA-Z\s]+$/.test(v) ? true : t("ERR_DEFAULT_INPUT_FIELD_MSG")),
                } }}
                render={(props) => (
                  <TextInput
                    value={props.value}
                    autoFocus={focusIndex.index === contractorDetails.key && focusIndex.type === "Name"}
                    errorStyle={(localFormState.touched.Name && errors?.Name?.message) ? true : false}
                    onChange={(e) => {
                      props.onChange(e.target.value);
                      setFocusIndex({ index: contractorDetails.key, type: "Name" });
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
              defaultValue={contractorDetails.correspondanceAddress}
              rules={{ validate: { pattern: (val) => (/^$|^[ A-Za-z0-9/._$@#]+$/.test(val) ? true : t("ERR_DEFAULT_INPUT_FIELD_MSG")) } }}
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
              defaultValue={contractorDetails.permenantAddress}
              rules={{ validate: { pattern: (val) => (/^$|^[A-Za-z0-9/._$@#]+$/.test(val) ? true : t("ERR_DEFAULT_INPUT_FIELD_MSG")) } }}
              isMandatory={true}
              render={(props) => (
                <TextInput
                  value={props.value}
                  autoFocus={focusIndex.index === 1 && focusIndex.type === "permenantAddress"}
                  errorStyle={(localFormState.touched.permenantAddress && errors?.permenantAddress?.message) ? true : false}
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
          <CardLabelError style={errorStyle}>{localFormState.touched.permenantAddress ? errors?.permenantAddress?.message : ""}</CardLabelError>
          <LabelFieldPair>
                <CardLabel style={CardLabelStyle}>{t("WORKS_CONTACT_PERSON")}</CardLabel>
                <div className="field">
                  <Controller
                    control={control}
                    name={"contactPerson"}
                    defaultValue={contractorDetails.contactPerson}
                    rules={{validate: {
                        pattern: (v) => (/^$|^[a-zA-Z\s]+$/.test(v) ? true : t("ERR_DEFAULT_INPUT_FIELD_MSG")),
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
          <CardLabel style={CardLabelStyle} className="card-label-smaller">{t("WORKS_EMAIL")}</CardLabel>
          <div className="field">
            <Controller
              control={control}
              name="email"
              defaultValue={contractorDetails.email}
              rules={{ validate: {
                pattern: (v) => (/^$|^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test(v) ? true : t("ERR_DEFAULT_INPUT_FIELD_MSG")),
              } }}
              isMandatory={true}
              render={(props) => (
                <TextInput
                  value={props.value}
                  autoFocus={focusIndex.index === 1 && focusIndex.type === "email"}
                  errorStyle={(localFormState.touched.address && errors?.email?.message) ? true : false}
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
          <CardLabelError style={errorStyle}>{localFormState.touched.email ? errors?.email?.message : ""}</CardLabelError>
          <LabelFieldPair>
                <CardLabel style={CardLabelStyle}>{t("WORKS_NARRATION")}</CardLabel>
                <div className="field">
                  <Controller
                    control={control}
                    name={"narration"}
                    defaultValue={contractorDetails.narration}
                    rules={{ validate: {
                      pattern: (v) => (/^$|^[a-zA-Z0-9\s]+$/.test(v) ? true : t("ERR_DEFAULT_INPUT_FIELD_MSG")),
                    } }}
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
              defaultValue={contractorDetails.mobileNumber}
              rules={{ validate: {
                pattern: (v) => (/^$|^[0-9]{10}/.test(v) ? true : t("ERR_DEFAULT_INPUT_FIELD_MSG")),
              } }}
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
                defaultValue={contractorDetails.panNo}
                rules={{ validate: {
                  pattern: (v) => (/^$|^[a-zA-Z0-9\s]+$/.test(v) ? true : t("ERR_DEFAULT_INPUT_FIELD_MSG")),
                } }}
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
                defaultValue={contractorDetails.tinNo}
                rules={{ validate: {
                  pattern: (v) => (/^$|^[a-zA-Z0-9\s]+$/.test(v) ? true : t("ERR_DEFAULT_INPUT_FIELD_MSG")),
                } }}
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
                defaultValue={contractorDetails.gstNo}
                rules={{ validate: {
                  pattern: (v) => (/^$|^[a-zA-Z0-9\s]+$/.test(v) ? true : t("ERR_DEFAULT_INPUT_FIELD_MSG")),
                } }}
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
            defaultValue={contractorDetails.bankName}
            rules={{ required: t("WORKS_REQUIRED_ERR") }}
            isMandatory={true}
            render={(props) => (
              <Dropdown
                  className="form-field"
                  option={Bank}
                  selected={props?.value}
                  optionKey={"name"}
                  t={t}
                  select={props?.onChange}
                  onBlur={props.onBlur}
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
            defaultValue={contractorDetails.IFSCCode}
            rules={{ required: t("WORKS_REQUIRED_ERR") }}
            isMandatory={true}
            render={(props) => (
              <Dropdown
                  className="form-field"
                  option={dummyData}
                  selected={props?.value}
                  optionKey={"name"}
                  t={t}
                  select={props?.onChange}
                  onBlur={props.onBlur}
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
                defaultValue={contractorDetails.bankAccountNumber}
                rules={{ validate: {
                  pattern: (v) => (/^$|^[0-9]{9,18}/.test(v) ? true : t("ERR_DEFAULT_INPUT_FIELD_MSG")),
                } }}
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
                defaultValue={contractorDetails.PWDApprovalCode}
                rules={{ validate: { pattern: (val) => (/^$|^[ A-Za-z0-9/._$@#]*$/.test(val) ? true : t("ERR_DEFAULT_INPUT_FIELD_MSG")) } }}
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
            defaultValue={contractorDetails.exemptedFrom}
            // rules={{ required: t("REQUIRED_FIELD") }}
            isMandatory={true}
            render={(props) => (
              <Dropdown
                className="form-field"
                option={exemptedFromULB}
                selected={props?.value}
                optionKey={"name"}
                t={t}
                select={props?.onChange}
                onBlur={props.onBlur}
              />
            )}
          />
          </LabelFieldPair>
          <CardLabelError style={errorStyle}>{localFormState.touched.exemptedFrom ? errors?.exemptedFrom?.message : ""}</CardLabelError>
          </React.Fragment>
    </div>
  )
}

export default WORKSContractorDetails