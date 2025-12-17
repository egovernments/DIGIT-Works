import React, { Fragment, useState, useEffect, useMemo } from "react";
import {
  AddIcon,
  DeleteIcon,
  RemoveIcon,
  TextInput,
  CardLabelError,
  Dropdown,
  Loader,
  TextArea,
  InputTextAmount
} from "@egovernments/digit-ui-react-components";
import { Controller } from "react-hook-form";
import _ from "lodash";
import { has4DecimalPlaces } from "../utils/transformData";

const ExtraCharges = ({ control, watch, config, ...props }) => {
  const populators = config?.populators;
  const stateId = Digit.ULBService.getStateId()

  const formFieldName = "extraCharges"; // this will be the key under which the data for this table will be present on onFormSubmit
  const initialState = [
    {
      key: 1,
      description: "",
      applicableOn: "",
      calculationType: "",
      figure: "",
      isShow: true,
    },
  ];

  const { t, register, errors, setValue, getValues, formData, unregister } = props;
  const [rows, setRows] = useState(formData?.[formFieldName]?.length > 0 ? formData?.[formFieldName] : []);

  useEffect(() => {
    if(window.location.href.includes("update"))
    if (formData && formData[formFieldName]) {
      setRows(formData[formFieldName].map((item, index) => ({
        ...item,
        key: index + 1,
        isShow: true,
      })));
    } else {
      setRows(initialState);
    }
  }, []);

  const getStyles = (index) => {
    let obj = {};
    switch (index) {
      case 1:
        obj = { width: "1rem" };
        break;
      case 2:
        obj = { width: "70rem" };
        break;
      case 3:
        obj = { width: "12rem" };
        break;
      case 4:
        obj = { width: "10rem" };
        break;
      case 5:
        obj = { width: "15rem",textAlign:"left" };
        break;
      case 6:
        obj = { width: "18rem" };
        break;
      case 7:
        obj = { width: "10rem" };
        break;
      case 8:
        obj = { width: "3%" };
        break;
      default:
        obj = { width: "1rem" };
        break;
    }
    return obj;
  };

  const columns = [
    t("RA_SNO"),
    t("RA_DESCRIPTION"),
    t("RA_APPLICABLE_ON"),
    t("RA_CALCULATION_TYPE"),
    t("RA_FIGURE"),
  ];

  const renderHeader = () => {
    return columns?.map((key, index) => {
      return (
        <th key={index} style={getStyles(index + 1)}>
          {" "}
          {key}{" "}
        </th>
      );
    });
  };

  const removeRow = (rowIndex) => {
    // const updatedRows = rows.map((row, index) =>
    //   index === rowIndex ? { ...row, isShow: false } : row
    // );
    const updatedRows = rows.filter((row, index) => index != rowIndex );
    setRows([...updatedRows]);
    setValue(formFieldName,[...updatedRows]);
  };

  const addRow = () => {
    const newRow = {
      key: rows.length > 0 ? rows[rows?.length -1]?.key +1 : 1,
      description: "",
      applicableOn: "",
      calculationType: "",
      figure: "",
      isShow: true,
    };
    setRows([...rows, newRow]);
  };

  const getCalculationType = () => {
    return [
      //{ code: "PERCENTAGE", name: "percentage" },
      { code: "FIXED", name: "fixed" }
    ];
  };

  const getDropDownDataFromMDMS = (t, row, inputName, props, register, optionKey = "name", rowIndex) => {
    const requestCriteria = {
      url: "/mdms-v2/v2/_search",
      body: {
        MdmsCriteria: {
          tenantId: stateId,
          schemaCode: "WORKS-SOR.Overhead",
        },
      },
      changeQueryName: "sorOverhead"
    };

    let { isLoading, data } = Digit.Hooks.useCustomAPIHook(requestCriteria);

    data = data?.mdms?.map((ob) => (ob?.data));

    if (isLoading) {
      return <Loader />;
    } else {
      return (
        <Dropdown
          inputRef={register()}
          option={data}
          selected={props?.value}
          optionKey={optionKey}
          t={t}
          select={(e) => {
            const updatedRows = rows.map((row, index) =>
            index === rowIndex ? { ...row, applicableOn: e } : row
          );
          setRows(updatedRows);
          setValue(`${formFieldName}[${rowIndex}].applicableOn`, e);
          props.onChange(e);
          }}
          onBlur={props?.onBlur}
          optionCardStyles={{ maxHeight: "15rem" }}
          style={{ marginBottom: "0px" }}
        />
      );
    }
  };

  const setAmountField = (e, rowIndex) => {
    const updatedRows = rows.map((row, index) =>
      index === rowIndex ? { ...row, figure: e.target.value } : row
    );
    setRows(updatedRows);
    setValue(`${formFieldName}[${rowIndex}].figure`, e.target.value);
  };

  const setDescription = (e, rowIndex) => {
    const updatedRows = rows.map((row, index) =>
      index === rowIndex ? { ...row, description: e.target.value } : row
    );
    setRows(updatedRows);
    setValue(`${formFieldName}[${rowIndex}].description`, e.target.value);
  };

  const isValidQuantity = (value) => {
    const regex = /^\d{0,4}(\.\d{0,2})?$/;
    return regex.test(value);
  };

  const cellContainerStyle = { display: "flex", flexDirection: "column" };
  const errorCardStyle = { width: "100%", fontSize: "12px", whiteSpace: "nowrap", overflow: "hidden", textOverflow: "ellipsis" };
  const errorContainerStyles = { display: "block", height: "1rem", overflow: "hidden" };

  const renderBody = useMemo(() => {
    let i = 0;
    return rows.map((row, rowIndex) => {
      if (row.isShow) i++;
      return row.isShow && (
        <tr key={row?.key} style={!row?.isShow ? { display: 'none' } : {}}>
          <td style={getStyles(1)}>{i}</td>

          <td style={getStyles(2)}>
            <div style={cellContainerStyle}>
              <div>
                <TextInput
                  style={{ marginBottom: "0px", wordWrap: "break-word" }}
                  name={`${formFieldName}[${rowIndex}].description`}
                  //value={formData?.extraCharges?.[rowIndex]?.description || row.description}
                  defaultValue={window.location.href.includes("update") ? (formData?.extraCharges?.[rowIndex]?.description || row.description) : null}
                  onChange={(e) => {
                    setDescription(e,rowIndex);
                  }}
                  inputRef={register({
                    maxLength: {
                      value: 512,
                      message: t(`WORKS_PATTERN_ERR`)
                    },
                    required: false
                  })}
                />
              </div>
              <div style={errorContainerStyles}>
                {errors && errors?.[formFieldName]?.[rowIndex]?.description?.type === "pattern" && (
                  <CardLabelError style={errorCardStyle}>{t(`WORKS_PATTERN_ERR`)}</CardLabelError>
                )}
                {errors && errors?.[formFieldName]?.[rowIndex]?.description?.type === "maxLength" && (
                  <CardLabelError style={errorCardStyle}>{t(`WORKS_DESC_LENGTH_EXCEEDED_512`)}</CardLabelError>
                )}
                {errors && errors?.[formFieldName]?.[rowIndex]?.description?.type === "required" && (
                  <CardLabelError style={errorCardStyle}>{t(`WORKS_REQUIRED_ERR`)}</CardLabelError>
                )}
              </div>
            </div>
          </td>

          <td style={getStyles(3)}>
            <div style={cellContainerStyle}>
              <div>
                <Controller
                  control={control}
                  name={`${formFieldName}[${rowIndex}].applicableOn`}
                  defaultValue={window.location.href.includes("update") ? (formData?.extraCharges?.[rowIndex]?.applicableOn || row.applicableOn) : null}
                  rules={{
                    required: false,
                    pattern: /^[a-zA-Z0-9_ .$@#\/ ]*$/,
                  }}
                  render={(props) =>
                    getDropDownDataFromMDMS(t, row, "applicableOn", props, register, "description", rowIndex)
                  }
                />
              </div>
              <div style={errorContainerStyles}>
                {errors && errors?.[formFieldName]?.[rowIndex]?.uom?.type === "pattern" && (
                  <CardLabelError style={errorCardStyle}>{t(`WORKS_PATTERN_ERR`)}</CardLabelError>
                )}
                {errors && errors?.[formFieldName]?.[rowIndex]?.uom?.type === "required" && (
                  <CardLabelError style={errorCardStyle}>{t(`WORKS_REQUIRED_ERR`)}</CardLabelError>
                )}
              </div>
            </div>
          </td>

          <td style={getStyles(4)}>
            <div style={cellContainerStyle}>
              <div>
                <Controller
                  control={control}
                  name={`${formFieldName}[${rowIndex}].calculationType`}
                  rules={{
                    required: false,
                    pattern: /^[a-zA-Z0-9_ .$@#\/ ]*$/,
                  }}
                  render={(props) => (
                    <Dropdown
                      inputRef={register()}
                      option={getCalculationType()}
                      selected={window.location.href.includes("update") ? (formData?.extraCharges?.[rowIndex]?.calculationType || row.calculationType) : null}
                      optionKey={"name"}
                      t={t}
                      select={(e) => {
                        const updatedRows = rows.map((row, index) =>
                        index === rowIndex ? { ...row, calculationType: e } : row
                      );
                      setRows(updatedRows);
                      setValue(`${formFieldName}[${rowIndex}].calculationType`, e);
                      props.onChange(e);
                      }}
                      onBlur={props?.onBlur}
                      optionCardStyles={{ maxHeight: "15rem" }}
                      style={{ marginBottom: "0px" }}
                    />
                  )}
                />
              </div>
              <div style={errorContainerStyles}>
                {errors && errors?.[formFieldName]?.[rowIndex]?.uom?.type === "pattern" && (
                  <CardLabelError style={errorCardStyle}>{t(`WORKS_PATTERN_ERR`)}</CardLabelError>
                )}
                {errors && errors?.[formFieldName]?.[rowIndex]?.uom?.type === "required" && (
                  <CardLabelError style={errorCardStyle}>{t(`WORKS_REQUIRED_ERR`)}</CardLabelError>
                )}
              </div>
            </div>
          </td>

          <td style={getStyles(5)}>
            <div style={cellContainerStyle}>
              <div>
                <TextInput
                  style={{ marginBottom: "0px", textAlign: "left", paddingRight: "1rem" }}
                  name={`${formFieldName}[${rowIndex}].figure`}
                  //value={formData?.extraCharges?.[rowIndex]?.figure || row.figure}
                  defaultValue={window.location.href.includes("update") ? (formData?.extraCharges?.[rowIndex]?.figure || row.figure) : null}
                  inputRef={register({
                    required: false,
                    max: populators?.quantity?.max,
                    pattern: /^\s*(?=.*[1-9])\d*(?:\.\d{1,2})?\s*$/,
                  })}
                  onChange={(e) => {
                    if(isValidQuantity(parseFloat(e?.target.value))){
                      setAmountField(e, rowIndex)
                    }
                    else
                    {
                      e.target.value = e?.target.value.slice(0, e?.target.value.length - 1);
                    }
                  }
                }
                />
              </div>
              <div style={errorContainerStyles}>
                {errors && errors?.[formFieldName]?.[rowIndex]?.estimatedQuantity?.type === "pattern" && (
                  <CardLabelError style={errorCardStyle}>{t(`WORKS_QT_ERR`)}</CardLabelError>
                )}
                {errors && errors?.[formFieldName]?.[rowIndex]?.estimatedQuantity?.type === "max" && (
                  <CardLabelError style={errorCardStyle}>{t(`${populators?.quantity?.error}`)}</CardLabelError>
                )}
                {errors && errors?.[formFieldName]?.[rowIndex]?.estimatedQuantity?.type === "required" && (
                  <CardLabelError style={errorCardStyle}>{t(`WORKS_REQUIRED_ERR`)}</CardLabelError>
                )}
              </div>
            </div>
          </td>

          <td style={getStyles(8)}>
            <div style={cellContainerStyle}>
              {(
                <span onClick={() =>  removeRow(rowIndex)} className="icon-wrapper">
                  <DeleteIcon fill={"#FF9100"} />
                </span>
              )}
            </div>
            <div style={errorContainerStyles}></div>
          </td>
        </tr>
      );
    });
  }, [rows, formData]);

  return (
    <table className="table reports-table sub-work-table">
      <thead>
        <tr>{renderHeader()}</tr>
      </thead>
      <tbody>
        {renderBody}
        <tr>
          <td colSpan={7} style={{ textAlign: "center" }} onClick={addRow}>
            <span>
              <AddIcon fill={"#C84C0E"} styles={{ margin: "auto", display: "inline", marginTop: "-2px" }} />
              <label style={{ marginLeft: "10px", fontWeight: "600", color: "#C84C0E" }}>{t("RA_ADD_ITEM")}</label>
            </span>
          </td>
        </tr>
      </tbody>
    </table>
  );
};

export default ExtraCharges;