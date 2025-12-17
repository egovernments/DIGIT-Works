import React, { useState } from "react";
import { Controller } from "react-hook-form";
import _ from "lodash";
import { AddIcon, DeleteIcon, TextInput, Loader, Header } from "@egovernments/digit-ui-react-components";
import { Dropdown, ErrorMessage } from "@egovernments/digit-ui-components";

const TransferCodeTable = (props) => {
  const { t, register, errors, setValue, getValues, onSelect, formData, control, formState, onChange, unregister } = props;

  const isMandatory = props?.props?.isMandatory;
  const orgSession = Digit.Hooks.useSessionStorage("ORG_CREATE", {});
  const [sessionFormData] = orgSession;

  const columns =
    props?.config?.key === "transferCodes"
      ? [t("WORKS_SNO"), t("MASTERS_IDENTIFIER_TYPE"), t("MASTERS_IDENTIFIER_VALUE")]
      : [t("WORKS_SNO"), t("MASTERS_IDENTIFIER_TYPE"), t("MASTERS_IDENTIFIER_VALUE"), t("CS_COMMON_ACTION")];
  const formFieldName = props?.config?.key === "transferCodes" ? "transferCodesData" : "taxIdentifierData";

  const module = props?.config?.key;
  const isTranferCodeTable = formFieldName === "transferCodesData";

  const renderTableFromSession = () => {
    if (!sessionFormData?.taxIdentifierData) {
      return [
        {
          key: 0,
          isShow: true,
        },
      ];
    }
    let tableState = [];
    for (let i = 0; i < sessionFormData?.taxIdentifierData?.length; i++) {
      if (sessionFormData?.taxIdentifierData[i]) {
        tableState.push({
          key: i,
          isShow: true,
        });
      }
    }
    return tableState;
  };

  const initialState = [
    {
      key: 0,
      isShow: true,
    },
  ];
  const [rows, setRows] = useState(isTranferCodeTable ? initialState : renderTableFromSession());

  const getStyles = (index) => {
    let obj = {};
    switch (index) {
      case 1:
        obj = { width: "7%", textAlign: "center" };
        break;
      case 2:
        obj = { width: "30rem" };
        break;
      case 3:
        obj = { width: "20rem" };
        break;
      case 4:
        obj = { width: "5%" };
        break;
      default:
        obj = { width: "1rem" };
        break;
    }
    return obj;
  };

  const getPatterns = (rowKey) => {
    if (isTranferCodeTable) return Digit.Utils.getPattern("IFSC");
    if (formData?.taxIdentifierData?.[rowKey]?.name?.code === "PAN") return Digit.Utils.getPattern("PAN");
    if (formData?.taxIdentifierData?.[rowKey]?.name?.code === "GSTIN") return Digit.Utils.getPattern("GSTNo");
    return "";
  };

  const showDelete = () => {
    let countIsShow = 0;
    rows.map((row) => row.isShow && countIsShow++);
    if (countIsShow === 1) {
      return false;
    }
    return true;
  };

  const removeRow = (row) => {
    //make a new state here which doesn't have this key
    const updatedState = rows.map((e) => {
      if (e.key === row.key) {
        return {
          key: e.key,
          isShow: false,
        };
      }
      return e;
    });
    unregister(`${formFieldName}.${row.key}.name`);
    unregister(`${formFieldName}.${row.key}.value`);
    setRows((prev) => updatedState);
  };

  const addRow = () => {
    const obj = {
      key: null,
      isShow: true,
    };
    obj.key = rows[rows.length - 1].key + 1;
    setRows((prev) => [...prev, obj]);
  };

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

  const setIFSC = (arg, name) => {
    setValue(name, arg.target.value.toUpperCase());
  };

  const getDropDownDataFromMDMS = (t, row, inputName, props, register, optionKey = "name", options = []) => {
    const { isLoading, data } = Digit.Hooks.useCustomMDMS(
      Digit.ULBService.getStateId(),
      options?.mdmsConfig?.moduleName,
      [{ name: options?.mdmsConfig?.masterName }],
      {
        select: (data) => {
          const optionsData = _.get(data, `${options?.mdmsConfig?.moduleName}.${options?.mdmsConfig?.masterName}`, []);
          return optionsData.filter((opt) => opt?.active).map((opt) => ({ ...opt, name: `${options?.mdmsConfig?.localePrefix}_${opt.code}` }));
        },
        enabled: options?.mdmsConfig ? true : false,
      }
    );

    if (isLoading) {
      return <Loader />;
    } else {
      let filteredOptions = [];
      if (options?.mdmsConfig) {
        filteredOptions = data?.filter((row) => {
          return formData?.[formFieldName] && !formData?.[formFieldName]?.some((formRow) => formRow?.name?.code === row?.code);
        });
      }
      return (
        <Dropdown
          inputRef={register()}
          option={options?.mdmsConfig ? (isTranferCodeTable ? data : filteredOptions) : options}
          selected={props?.value}
          optionKey={optionKey}
          t={t}
          select={(e) => {
            props.onChange(e);
          }}
          onBlur={props.onBlur}
          optionCardStyles={{ maxHeight: "15rem" }}
          style={{ marginBottom: "0px" }}
        />
      );
    }
  };

  const cellContainerStyle = { display: "flex", flexDirection: "column" };
  const errorCardStyle = { width: "20rem", fontSize: "12px", overflow: "hidden", textOverflow: "ellipsis", whiteSpace: "nowrap" };
  const errorContainerStyles = { display: "block", height: "1rem", overflow: "hidden" };

  const renderBody = () => {
    let i = 0;
    return rows.map((row, index) => {
      if (row.isShow) i++;
      return (
        row.isShow && (
          <tr key={index} style={{ height: "50%" }}>
            <td style={getStyles(1)}>{i}</td>
            <td style={getStyles(2)}>
              <div style={cellContainerStyle}>
                <Controller
                  control={control}
                  name={`${formFieldName}.${row.key}.name`}
                  defaultValue={formData?.[`${formFieldName}.${row.key}.name`]}
                  rules={{ required: isMandatory }}
                  render={(props) =>
                    getDropDownDataFromMDMS(t, row, "name", props, register, "name", {
                      mdmsConfig: {
                        masterName: isTranferCodeTable ? "OrgTransferCode" : "OrgTaxIdentifier",
                        moduleName: "common-masters",
                        localePrefix: `COMMON_MASTERS_${Digit.Utils.locale.getTransformedLocale(module)}`,
                      },
                    })
                  }
                />
              </div>
              <div>
                {errors && errors?.[formFieldName]?.[row.key]?.name?.type === "required" && (
                  <ErrorMessage message={t(`WORKS_REQUIRED_ERR`)} truncateMessage={true} maxLength={256} showIcon={true} />
                )}
              </div>
            </td>
            <td style={getStyles(3)}>
              <div style={cellContainerStyle}>
                <TextInput
                  style={{ marginBottom: "0px" }}
                  name={`${formFieldName}.${row.key}.value`}
                  selected={formData && formData[formFieldName] ? formData[formFieldName][`${formFieldName}.${row.key}.value`] : undefined}
                  inputRef={register({ required: isMandatory, pattern: getPatterns(row.key) })}
                  onChange={(arg) => setIFSC(arg, `${formFieldName}.${row.key}.value`)}
                />
              </div>
              <div>
                {errors && errors?.[formFieldName]?.[row.key]?.value?.type === "required" && (
                  <ErrorMessage message={t(`WORKS_REQUIRED_ERR`)} truncateMessage={true} maxLength={256} showIcon={true} />
                )}
                {errors && (errors?.[formFieldName]?.[row.key]?.value?.type === "pattern" || errors?.[formFieldName]?.type === "custom") && (
                  <ErrorMessage
                    message={isTranferCodeTable ? t("ES_COMMON_IFSC_CODE_ERROR") : t("WORKS_PATTERN_ERR")}
                    truncateMessage={true}
                    maxLength={256}
                    showIcon={true}
                  />
                )}
              </div>
            </td>
            {!isTranferCodeTable && (
              <td style={getStyles(4)}>
                <div style={cellContainerStyle}>
                  {showDelete() && (
                    <span onClick={() => removeRow(row)} className="icon-wrapper">
                      <DeleteIcon fill={"#B1B4B6"} />
                    </span>
                  )}
                </div>
                <div style={errorContainerStyles}></div>
              </td>
            )}
          </tr>
        )
      );
    });
  };

  return (
    <React.Fragment>
      <Header styles={{ fontSize: "24px", marginTop: "16px", marginBottom: "16px" }}>
        {isTranferCodeTable ? t("MASTERS_TRANSFER_CODE") : t("MASTERS_TAX_INDENTIFIERS")}
        {isMandatory ? " *" : ""}
      </Header>
      <table className="table reports-table sub-work-table" style={{ marginTop: "-10px", marginBottom: "2rem" }}>
        <thead>
          <tr>{renderHeader()}</tr>
        </thead>
        <tbody>
          {renderBody()}
          {formFieldName === "taxIdentifierData" ? (
            <tr>
              <td colSpan={7} style={{ textAlign: "center" }} onClick={addRow}>
                <span>
                  <AddIcon fill={"#C84C0E"} styles={{ margin: "auto", display: "inline", marginTop: "-2px" }} />
                  <label style={{ marginLeft: "10px", fontWeight: "600", color: "#C84C0E" }}>{t("WORKS_ADD_ITEM")}</label>
                </span>
              </td>
            </tr>
          ) : null}
        </tbody>
      </table>
    </React.Fragment>
  );
};

export default TransferCodeTable;
