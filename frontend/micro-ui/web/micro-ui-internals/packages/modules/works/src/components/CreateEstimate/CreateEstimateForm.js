import React, { Fragment, useState, useEffect } from "react";
import { Controller, useForm, useWatch } from "react-hook-form";
import {
  Card,
  Header,
  CardSectionHeader,
  LabelFieldPair,
  CardLabel,
  CardText,
  CardSectionSubText,
  TextInput,
  Dropdown,
  UploadFile,
  MultiUploadWrapper,
  ActionBar,
  SubmitBar,
  CardLabelError,
  Loader,
} from "@egovernments/digit-ui-react-components";
import { useTranslation } from "react-i18next";
import SubWorkTable from "./SubWorkTable";
import ProcessingModal from "../Modal/ProcessingModal";
import _ from "lodash";

const allowedFileTypes = /(.*?)(pdf|docx|msword|openxmlformats-officedocument|wordprocessingml|document|spreadsheetml|sheet)$/i;

const CreateEstimateForm = React.memo(({ onFormSubmit, sessionFormData, setSessionFormData }) => {
  const { t } = useTranslation();
  const {
    register,
    control,
    watch,
    setValue,
    unregister,
    handleSubmit,
    formState: { errors, ...rest },
    reset,
    trigger,
    ...methods
  } = useForm({
    // defaultValues: sessionFormData,
    // defaultValues: {
    //     "fund": {
    //         "code": "01",
    //         "name": "General Fund",
    //         "active": true,
    //         "i18nKey": "General Fund"
    //     },
    //     "ward": {
    //         "id": 3,
    //         "code": "B1",
    //         "name": "Block 1",
    //         "label": "Block",
    //         "i18nKey": "Block 1",
    //         "children": [
    //             {
    //                 "id": 4,
    //                 "area": "Area1",
    //                 "code": "SUN04",
    //                 "name": "Ajit Nagar - Area1",
    //                 "label": "Locality",
    //                 "i18nKey": "Ajit Nagar - Area1",
    //                 "pincode": [
    //                     143001
    //                 ],
    //                 "children": [],
    //                 "latitude": 31.63089,
    //                 "localname": "Ajit Nagar - Area1",
    //                 "longitude": 74.871552,
    //                 "boundaryNum": 1
    //             },
    //             {
    //                 "id": 5,
    //                 "area": "Area1",
    //                 "code": "SUN11",
    //                 "name": "Back Side 33 KVA Grid Patiala Road - Area1",
    //                 "label": "Locality",
    //                 "i18nKey": "Back Side 33 KVA Grid Patiala Road - Area1",
    //                 "pincode": [
    //                     143001
    //                 ],
    //                 "children": [],
    //                 "latitude": null,
    //                 "localname": "Back Side 33 KVA Grid Patiala Road - Area1",
    //                 "longitude": null,
    //                 "boundaryNum": 1
    //             },
    //             {
    //                 "id": 6,
    //                 "area": "Area2",
    //                 "code": "SUN12",
    //                 "name": "Back Side 66 KVA Grid Patiala Road - Area2",
    //                 "label": "Locality",
    //                 "i18nKey": "Back Side 66 KVA Grid Patiala Road - Area2",
    //                 "pincode": [
    //                     143001
    //                 ],
    //                 "children": [],
    //                 "latitude": null,
    //                 "localname": "Back Side 66 KVA Grid Patiala Road - Area2",
    //                 "longitude": null,
    //                 "boundaryNum": 1
    //             },
    //             {
    //                 "id": 7,
    //                 "area": "Area1",
    //                 "code": "SUN13",
    //                 "name": "Back Side Civil Courts Colony - Area1",
    //                 "label": "Locality",
    //                 "i18nKey": "Back Side Civil Courts Colony - Area1",
    //                 "pincode": [
    //                     143001
    //                 ],
    //                 "children": [],
    //                 "latitude": null,
    //                 "localname": "Back Side Civil Courts Colony - Area1",
    //                 "longitude": null,
    //                 "boundaryNum": 1
    //             }
    //         ],
    //         "latitude": null,
    //         "localname": "Block1",
    //         "longitude": null,
    //         "boundaryNum": 1
    //     },
    //     "scheme": {
    //         "fund": "Grant Fund from Central Government",
    //         "active": true,
    //         "i18nKey": "15th CFC-15th Central Finance Commission",
    //         "schemeCode": "15th CFC",
    //         "schemeName": "15th CFC-15th Central Finance Commission",
    //         "subSchemes": [
    //             {
    //                 "code": "15th CFC-01",
    //                 "name": "Drinking Water Supply (including Rain Water Harvesting & Recycling)",
    //                 "active": true,
    //                 "i18nKey": "Drinking Water Supply (including Rain Water Harvesting & Recycling)"
    //             }
    //         ]
    //     },
    //     "uploads": [],
    //     "comments": "s",
    //     "function": {
    //         "code": "0001",
    //         "name": "Municipal Body",
    //         "active": true,
    //         "i18nKey": "Municipal Body",
    //         "functionGroup": "General Administration"
    //     },
    //     "location": {
    //         "id": 4,
    //         "area": "Area1",
    //         "code": "SUN04",
    //         "name": "Ajit Nagar - Area1",
    //         "label": "Locality",
    //         "i18nKey": "Ajit Nagar - Area1",
    //         "pincode": [
    //             143001
    //         ],
    //         "children": [],
    //         "latitude": 31.63089,
    //         "localname": "Ajit Nagar - Area1",
    //         "longitude": 74.871552,
    //         "boundaryNum": 1
    //     },
    //     "subScheme": {
    //         "code": "15th CFC-01",
    //         "name": "Drinking Water Supply (including Rain Water Harvesting & Recycling)",
    //         "active": true,
    //         "i18nKey": "Drinking Water Supply (including Rain Water Harvesting & Recycling)"
    //     },
    //     "budgetHead": {
    //         "code": "01",
    //         "name": "ADM",
    //         "active": true,
    //         "i18nKey": "ADM"
    //     },
    //     "department": {
    //         "code": "DEPT_1",
    //         "name": "Street Lights",
    //         "active": true,
    //         "i18nKey": "Street Lights"
    //     },
    //     "typeOfWork": {
    //         "code": "Road",
    //         "name": "Road",
    //         "active": true,
    //         "i18nKey": "Road",
    //         "subTypes": [
    //             {
    //                 "code": "RD01",
    //                 "name": "Road Forming",
    //                 "active": true,
    //                 "i18nKey": "Road Forming"
    //             },
    //             {
    //                 "code": "RD02",
    //                 "name": "Road Widening",
    //                 "active": true,
    //                 "i18nKey": "Road Widening"
    //             },
    //             {
    //                 "code": "RD03",
    //                 "name": "Maintenance",
    //                 "active": true,
    //                 "i18nKey": "Maintenance"
    //             }
    //         ]
    //     },
    //     "natureOfWork": {
    //         "code": "Capital",
    //         "name": "Capital",
    //         "active": true,
    //         "i18nKey": "Capital"
    //     },
    //     "subTypeOfWork": {
    //         "code": "RD01",
    //         "name": "Road Forming",
    //         "active": true,
    //         "i18nKey": "Road Forming"
    //     },
    //     "beneficiaryType": {
    //         "code": "General",
    //         "name": "General",
    //         "active": true,
    //         "i18nKey": "General"
    //     },
    //     "entrustmentMode": {
    //         "code": "Nomination",
    //         "name": "Nomination",
    //         "active": true,
    //         "i18nKey": "Nomination"
    //     },
    //     "estimateDetails": [
    //         null,
    //         {
    //             "name": "work11",
    //             "amount": "211"
    //         }
    //     ],
    //     "requirementNumber": "123"
    // },
    mode: "onSubmit",
  });
  const formData = watch();
//   useEffect(() => {
//     if (!_.isEqual(sessionFormData, formData)) {
//       setSessionFormData({ ...sessionFormData, ...formData });
//     }
//   }, [formData]);
  const tenantId = Digit.ULBService.getCurrentTenantId();

  const tenant = Digit.ULBService.getStateId();
  const { isLoading: desgLoading, data: designationData, isFetched: desgFetched } = Digit.Hooks.useCustomMDMS(tenant, "common-masters", [
    {
      name: "Department",
    },
  ]);

  if (designationData?.[`common-masters`]) {
    var { Department } = designationData?.[`common-masters`];
  }
  Department?.map((item) => Object.assign(item, { i18nKey: t(`ES_COMMON_${item?.code}`) }));

  const getDate = () => {
    const today = new Date();

    const date = today.getDate() + "/" + (today.getMonth() + 1) + "/" + today.getFullYear();
    return date;
  };

  const initialState = [
    {
      key: 1,
      isShow: true,
    },
  ];
  const [rows, setRows] = useState(initialState);

  const { isLoading, data, isFetched } = Digit.Hooks.useCustomMDMS(tenant, "works", [
    {
      name: "BeneficiaryType",
    },
    {
      name: "EntrustmentMode",
    },
    {
      name: "NatureOfWork",
    },
    {
      name: "TypeOfWork",
    },
  ]);

  const { isLoading: isFinanceDataLoading, data: financeData, isFetched: isFinanceDataFetched } = Digit.Hooks.useCustomMDMS(tenant, "finance", [
    {
      name: "Scheme",
    },
    {
      name: "BudgetHead",
    },
    {
      name: "Functions",
    },
    {
      name: "Fund",
    },
  ]);

  const { subTypes: SubTypeOfWork } = useWatch({ control: control, name: "typeOfWork", defaultValue: [] });
  SubTypeOfWork?.map((item) => Object.assign(item, { i18nKey: t(`ES_COMMON_${item?.code}`) }));

  if (data?.works) {
    var { EntrustmentMode, BeneficiaryType, NatureOfWork, TypeOfWork } = data?.works;
  }
  EntrustmentMode?.map((item) => Object.assign(item, { i18nKey: t(`ES_COMMON_${item?.code}`) }));
  BeneficiaryType?.map((item) => Object.assign(item, { i18nKey: t(`ES_COMMON_${item?.code}`) }));
  NatureOfWork?.map((item) => Object.assign(item, { i18nKey: t(`ES_COMMON_${item?.code}`) }));
  TypeOfWork?.map((item) => Object.assign(item, { i18nKey: t(`ES_COMMON_${item?.code}`) }));

  const { subSchemes: subScheme } = useWatch({ control: control, name: "scheme", defaultValue: [] });
  if (financeData?.finance) {
    var { Scheme, BudgetHead, Functions, Fund } = financeData?.finance;
  }
  Scheme?.map((item) => Object.assign(item, { i18nKey: t(`ES_COMMON_${item?.schemeCode}`) }));
  BudgetHead?.map((item) => Object.assign(item, { i18nKey: t(`ES_COMMON_BUDGETHEAD_${item?.code}`) }));
  Functions?.map((item) => Object.assign(item, { i18nKey: t(`ES_COMMON_${item?.code}`) }));
  Fund?.map((item) => Object.assign(item, { i18nKey: t(`ES_COMMON_FUND_${item?.code}`) }));
  subScheme?.map((item) => Object.assign(item, { i18nKey: t(`ES_COMMON_${item?.code}`) }));

  const { isLoading: locationLoading, data: locationData, isFetched: locationDataFetched } = Digit.Hooks.useCustomMDMS(tenantId, "egov-location", [
    {
      name: "TenantBoundary",
    },
  ]);

  const headerLocale = Digit.Utils.locale.getTransformedLocale(tenantId)

  const { data: localityOptions,isLocalityLoading } = Digit.Hooks.useLocation(
    tenantId, 'Locality',
    {
      select: (data) => {
        return data?.TenantBoundary[0]?.boundary.map((item) => ({ code: item.code, name: item.name, i18nKey: `${headerLocale}_ADMIN_${item?.code}` }));
      },
    })

  const { data: wardOptions } = Digit.Hooks.useLocation(
    tenantId, 'Ward',
    {
      select: (data) => {
        return data?.TenantBoundary[0]?.boundary.map((item) => ({ code: item.code, name: item.name, i18nKey: `${headerLocale}_ADMIN_${item?.code}` }));
      },
    })


  
  if (locationData?.[`egov-location`]) {
    var { children: ward } = locationData?.[`egov-location`]?.TenantBoundary[0]?.boundary?.children[0];
  }
  ward?.map((item) => Object.assign(item, { i18nKey: t(`ES_COMMON_${item?.code}`) }));

  const { children: location } = useWatch({ control: control, name: "ward", defaultValue: [] });
  location?.map((item) => Object.assign(item, { i18nKey: t(`ES_COMMON_${item?.code}`) }));

  const handleCreateClick = async () => {
    const subWorkFieldsToValidate = [];
    rows.map((row) => row.isShow && subWorkFieldsToValidate.push(...[`estimateDetails.${row.key}.name`, `estimateDetails.${row.key}.amount`]));
    const fieldsToValidate = [
      "requirementNumber",
      "department",
      "ward",
      "location",
      "beneficiaryType",
      "natureOfWork",
      "typeOfWork",
      "subTypeOfWork",
      "entrustmentMode",
      "fund",
      "function",
      "budgetHead",
      "scheme",
      "subScheme",
      ...subWorkFieldsToValidate,
    ];

    const result = await trigger(fieldsToValidate);
    if (result) {
      setShowModal(true);
    }
  };

  const [showModal, setShowModal] = useState(false);

  if (isLoading) {
    return <Loader />;
  }

  const checkKeyDown = (e) => {
    if (e.code === "Enter") e.preventDefault();
  };
  const errorStyle = { marginBottom: "0px" };
  const estimateStyle = { marginTop: "20px", marginBottom: "4px" };
  return isFetched && isFinanceDataFetched && desgFetched && locationDataFetched ? (
    <form onSubmit={handleSubmit(onFormSubmit)} onKeyDown={(e) => checkKeyDown(e)}>
      <Header styles={{ marginLeft: "14px" }}>{t("WORKS_CREATE_ESTIMATE")}</Header>
      <Card>
        <CardSectionHeader style={{ marginTop: "14px" }}>{t(`WORKS_ESTIMATE_DETAILS`)}</CardSectionHeader>
        {/* TEXT INPUT ROW */}
        <LabelFieldPair>
          <CardLabel style={{ fontSize: "16px", fontStyle: "bold", fontWeight: "600" }}>{`${t(`WORKS_DATE_PROPOSAL`)}:*`}</CardLabel>
          <TextInput
            className={"field"}
            name="proposalDate"
            inputRef={register()}
            value={getDate()}
            disabled
            style={{ backgroundColor: "#E5E5E5", marginBottom: "0px" }}
          />
        </LabelFieldPair>

        {/* Modal */}
        {showModal && (
          <ProcessingModal
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
            action={"estimate"}
            setValue={setValue}
          />
        )}

        {/* DROPDOWN ROW */}
        <LabelFieldPair>
          <CardLabel style={{ fontSize: "16px", fontStyle: "bold", fontWeight: "600" }}>{`${t(`WORKS_EXECUTING_DEPT`)}:*`}</CardLabel>
          <div className="field">
            <Controller
              name="department"
              control={control}
              rules={{ required: true }}
              render={(props) => {
                return (
                  <Dropdown
                    option={Department}
                    selected={props?.value}
                    optionKey="i18nKey"
                    t={t}
                    select={props?.onChange}
                    onBlur={props.onBlur}
                    style={estimateStyle}
                  />
                );
              }}
            />
            {errors && errors?.department?.type === "required" && <CardLabelError style={errorStyle}>{t(`WORKS_REQUIRED_ERR`)}</CardLabelError>}
          </div>
        </LabelFieldPair>

        <LabelFieldPair>
          <CardLabel style={{ fontSize: "16px", fontStyle: "bold", fontWeight: "600" }}>{`${t(`WORKS_LOR`)}:*`}</CardLabel>
          <div className="field">
            <TextInput
              name="requirementNumber"
              inputRef={register({
                pattern: /^[a-zA-Z0-9_.$@#\/]*$/,
                required: true,
              })}
              style={estimateStyle}
            />
            {errors && errors?.requirementNumber?.type === "pattern" && <CardLabelError style={errorStyle}>{t(`WORKS_PATTERN_ERR`)}</CardLabelError>}
            {errors && errors?.requirementNumber?.type === "required" && (
              <CardLabelError style={errorStyle}>{t(`WORKS_REQUIRED_ERR`)}</CardLabelError>
            )}
          </div>
        </LabelFieldPair>

        <CardSectionHeader style={{ marginTop: "14px" }}>{t(`WORKS_LOCATION_DETAILS`)}</CardSectionHeader>
        <LabelFieldPair>
          <CardLabel style={{ fontSize: "16px", fontStyle: "bold", fontWeight: "600" }}>{`${t(`WORKS_WARD`)}:*`}</CardLabel>
          <div className="field">
            <Controller
              name="ward"
              control={control}
              rules={{ required: true }}
              render={(props) => {
                return (
                  <Dropdown
                    option={wardOptions}
                    selected={props?.value}
                    optionKey={"i18nKey"}
                    t={t}
                    select={(val) => {
                      props?.onChange(val);
                      setValue("location", "");
                    }}
                    onBlur={props.onBlur}
                    style={estimateStyle}
                  />
                );
              }}
            />
            {errors && errors?.ward?.type === "required" && <CardLabelError style={errorStyle}>{t(`WORKS_REQUIRED_ERR`)}</CardLabelError>}
          </div>
        </LabelFieldPair>
        <LabelFieldPair>
          <CardLabel style={{ fontSize: "16px", fontStyle: "bold", fontWeight: "600" }}>{`${t(`WORKS_LOCATION`)}:`}</CardLabel>
          <Controller
            name="location"
            control={control}
            //rules={{ required: true }}
            render={(props) => {
              return (
                <Dropdown
                  className={`field`}
                  option={localityOptions}
                  selected={props?.value}
                  optionKey={"i18nKey"}
                  t={t}
                  select={props?.onChange}
                  onBlur={props.onBlur}
                  style={estimateStyle}
                />
              );
            }}
          />
        </LabelFieldPair>

        <CardSectionHeader style={{ marginTop: "14px" }}>{t(`WORKS_WORK_DETAILS`)}</CardSectionHeader>
        <LabelFieldPair>
          <CardLabel style={{ fontSize: "16px", fontStyle: "bold", fontWeight: "600" }}>{`${t(`WORKS_BENEFICIERY`)}:*`}</CardLabel>
          <div className="field">
            <Controller
              name="beneficiaryType"
              control={control}
              rules={{ required: true }}
              render={(props) => {
                return (
                  <Dropdown
                    option={BeneficiaryType}
                    selected={props?.value}
                    optionKey={"i18nKey"}
                    t={t}
                    select={props?.onChange}
                    onBlur={props.onBlur}
                    style={estimateStyle}
                  />
                );
              }}
            />
            {errors && errors?.beneficiaryType?.type === "required" && <CardLabelError style={errorStyle}>{t(`WORKS_REQUIRED_ERR`)}</CardLabelError>}
          </div>
        </LabelFieldPair>
        <LabelFieldPair>
          <CardLabel style={{ fontSize: "16px", fontStyle: "bold", fontWeight: "600" }}>{`${t(`WORKS_WORK_NATURE`)}:*`}</CardLabel>
          <div className="field">
            <Controller
              name="natureOfWork"
              control={control}
              rules={{ required: true }}
              render={(props) => {
                return (
                  <Dropdown
                    option={NatureOfWork}
                    selected={props?.value}
                    optionKey={"i18nKey"}
                    t={t}
                    select={props?.onChange}
                    onBlur={props.onBlur}
                    style={estimateStyle}
                  />
                );
              }}
            />
            {errors && errors?.natureOfWork?.type === "required" && <CardLabelError style={errorStyle}>{t(`WORKS_REQUIRED_ERR`)}</CardLabelError>}
          </div>
        </LabelFieldPair>
        <LabelFieldPair>
          <CardLabel style={{ fontSize: "16px", fontStyle: "bold", fontWeight: "600" }}>{`${t(`WORKS_WORK_TYPE`)}:*`}</CardLabel>
          <div className="field">
            <Controller
              name="typeOfWork"
              control={control}
              rules={{ required: true }}
              render={(props) => {
                return (
                  <Dropdown
                    option={TypeOfWork}
                    selected={props?.value}
                    optionKey={"i18nKey"}
                    t={t}
                    select={(val) => {
                      props?.onChange(val);
                      setValue("subTypeOfWork", "");
                    }}
                    onBlur={props.onBlur}
                    style={estimateStyle}
                  />
                );
              }}
            />
            {errors && errors?.typeOfWork?.type === "required" && <CardLabelError style={errorStyle}>{t(`WORKS_REQUIRED_ERR`)}</CardLabelError>}
          </div>
        </LabelFieldPair>
        <LabelFieldPair>
          <CardLabel style={{ fontSize: "16px", fontStyle: "bold", fontWeight: "600" }}>{`${t(`WORKS_SUB_TYPE_WORK`)}:`}</CardLabel>
          <Controller
            name="subTypeOfWork"
            control={control}
            render={(props) => {
              return (
                <Dropdown
                  className={`field`}
                  option={SubTypeOfWork}
                  selected={props?.value}
                  optionKey={"i18nKey"}
                  t={t}
                  select={props?.onChange}
                  onBlur={props.onBlur}
                  style={estimateStyle}
                />
              );
            }}
          />
        </LabelFieldPair>
        <LabelFieldPair>
          <CardLabel style={{ fontSize: "16px", fontStyle: "bold", fontWeight: "600" }}>{`${t(`WORKS_MODE_OF_INS`)}:*`}</CardLabel>
          <div className="field">
            <Controller
              name="entrustmentMode"
              control={control}
              rules={{ required: true }}
              render={(props) => {
                return (
                  <Dropdown
                    option={EntrustmentMode}
                    selected={props?.value}
                    optionKey={"i18nKey"}
                    t={t}
                    select={props?.onChange}
                    onBlur={props.onBlur}
                    style={estimateStyle}
                  />
                );
              }}
            />
            {errors && errors?.entrustmentMode?.type === "required" && <CardLabelError style={errorStyle}>{t(`WORKS_REQUIRED_ERR`)}</CardLabelError>}
          </div>
        </LabelFieldPair>

        <CardSectionHeader style={{ marginTop: "14px" }}>{t(`WORKS_FINANCIAL_DETAILS`)}</CardSectionHeader>
        <LabelFieldPair>
          <CardLabel style={{ fontSize: "16px", fontStyle: "bold", fontWeight: "600" }}>{`${t(`WORKS_FUND`)}:*`}</CardLabel>
          <div className="field">
            <Controller
              name="fund"
              control={control}
              rules={{ required: true }}
              render={(props) => {
                return (
                  <Dropdown
                    option={Fund}
                    selected={props?.value}
                    optionKey={"i18nKey"}
                    t={t}
                    select={props?.onChange}
                    onBlur={props.onBlur}
                    style={estimateStyle}
                  />
                );
              }}
            />
            {errors && errors?.fund?.type === "required" && <CardLabelError style={errorStyle}>{t(`WORKS_REQUIRED_ERR`)}</CardLabelError>}
          </div>
        </LabelFieldPair>
        <LabelFieldPair>
          <CardLabel style={{ fontSize: "16px", fontStyle: "bold", fontWeight: "600" }}>{`${t(`WORKS_FUNCTION`)}:*`}</CardLabel>
          <div className="field">
            <Controller
              name="function"
              control={control}
              rules={{ required: true }}
              render={(props) => {
                return (
                  <Dropdown
                    option={Functions}
                    selected={props?.value}
                    optionKey={"i18nKey"}
                    t={t}
                    select={props?.onChange}
                    onBlur={props.onBlur}
                    style={estimateStyle}
                  />
                );
              }}
            />
            {errors && errors?.function?.type === "required" && <CardLabelError style={errorStyle}>{t(`WORKS_REQUIRED_ERR`)}</CardLabelError>}
          </div>
        </LabelFieldPair>
        <LabelFieldPair>
          <CardLabel style={{ fontSize: "16px", fontStyle: "bold", fontWeight: "600" }}>{`${t(`WORKS_BUDGET_HEAD`)}:*`}</CardLabel>
          <div className="field">
            <Controller
              name="budgetHead"
              control={control}
              rules={{ required: true }}
              render={(props) => {
                return (
                  <Dropdown
                    option={BudgetHead}
                    selected={props?.value}
                    optionKey={"i18nKey"}
                    t={t}
                    select={props?.onChange}
                    onBlur={props.onBlur}
                    style={estimateStyle}
                  />
                );
              }}
            />
            {errors && errors?.budgetHead?.type === "required" && <CardLabelError style={errorStyle}>{t(`WORKS_REQUIRED_ERR`)}</CardLabelError>}
          </div>
        </LabelFieldPair>
        <LabelFieldPair>
          <CardLabel style={{ fontSize: "16px", fontStyle: "bold", fontWeight: "600" }}>{`${t(`WORKS_SCHEME`)}:`}</CardLabel>
          <div className="field">
            <Controller
              name="scheme"
              control={control}
              rules={{ required: false }}
              render={(props) => {
                return (
                  <Dropdown
                    option={Scheme}
                    selected={props?.value}
                    optionKey={"i18nKey"}
                    t={t}
                    select={(val) => {
                      props?.onChange(val);
                      setValue("subScheme", "");
                    }}
                    onBlur={props.onBlur}
                    style={estimateStyle}
                  />
                );
              }}
            />
            {errors && errors?.scheme?.type === "required" && <CardLabelError style={errorStyle}>{t(`WORKS_REQUIRED_ERR`)}</CardLabelError>}
          </div>
        </LabelFieldPair>

        <LabelFieldPair>
          <CardLabel style={{ fontSize: "16px", fontStyle: "bold", fontWeight: "600" }}>{`${t(`WORKS_SUB_SCHEME`)}:`}</CardLabel>
          <div className="field">
            <Controller
              name="subScheme"
              control={control}
              rules={{ required: false }}
              render={(props) => {
                return (
                  <Dropdown
                    option={subScheme}
                    selected={props?.value}
                    optionKey={"i18nKey"}
                    t={t}
                    select={props?.onChange}
                    onBlur={props.onBlur}
                    style={estimateStyle}
                  />
                );
              }}
            />
            {errors && errors?.subScheme?.type === "required" && <CardLabelError style={errorStyle}>{t(`WORKS_REQUIRED_ERR`)}</CardLabelError>}
          </div>
        </LabelFieldPair>

        {/* Render the sub work table here */}
        <CardSectionHeader>{`${t(`WORKS_SUB_WORK_DETAILS`)}*`}</CardSectionHeader>
        <SubWorkTable register={register} t={t} errors={errors} rows={rows} setRows={setRows} />

        <CardSectionHeader style={{ marginTop: "20px" }}>{t(`WORKS_RELEVANT_DOCS`)}</CardSectionHeader>
        <LabelFieldPair>
          <CardLabel style={{ fontSize: "16px", fontStyle: "bold", fontWeight: "600 " }}>{t(`WORKS_UPLOAD_FILES`)}</CardLabel>
          <div className="field">
            <Controller
              name="uploads"
              control={control}
              rules={{ required: false }}
              render={({ onChange, ref, value = [] }) => {
                function getFileStoreData(filesData) {
                  const numberOfFiles = filesData.length;
                  let finalDocumentData = [];
                  if (numberOfFiles > 0) {
                    filesData.forEach((value) => {
                      finalDocumentData.push({
                        fileName: value?.[0],
                        fileStoreId: value?.[1]?.fileStoreId?.fileStoreId,
                        documentType: value?.[1]?.file?.type,
                      });
                    });
                  }
                  onChange(finalDocumentData);
                }
                return (
                  <MultiUploadWrapper
                    t={t}
                    module="works"
                    tenantId={tenant}
                    getFormState={getFileStoreData}
                    showHintBelow={true}
                    setuploadedstate={value}
                    allowedFileTypesRegex={allowedFileTypes}
                    allowedMaxSizeInMB={5}
                    hintText={t("WORKS_DOC_UPLOAD_HINT")}
                    maxFilesAllowed={5}
                    extraStyleName={{ padding: "0.5rem" }}
                  />
                );
              }}
            />
          </div>
        </LabelFieldPair>

        <ActionBar>
          <SubmitBar onSubmit={handleCreateClick} label={t("WORKS_CREATE_ESTIMATE")} />
        </ActionBar>
      </Card>
    </form>
  ) : (
    <Loader />
  );
});

export default CreateEstimateForm;
