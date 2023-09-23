
import { Card, Header, Button, Loader } from "@egovernments/digit-ui-react-components";
import React from "react";
import { useTranslation } from "react-i18next";
import { useState } from "react";

const CustomCollapsibleTable = ({ children, isTableCollapsed }) => {
  return (
    <div className={`custom-collapsible-table ${isTableCollapsed ? 'collapsed' : ''}`}>
      {children}
    </div>
  );
};



const MeasurementHistory = ({ contractNumber, measurementNumber }) => {
  const { t } = useTranslation();
  const tenantId = Digit.ULBService.getCurrentTenantId();

  const [isTableCollapsed, setIsTableCollapsed] = useState(false);

  const toggleTableCollapse = () => {
    setIsTableCollapsed(prevState => !prevState);
  };

  const requestCriteria = {
    url: "/measurement-service/v1/_search",

    body: {
      criteria: {
        tenantId : tenantId,
        referenceId: [contractNumber]
        // ids: ["70380648-45c2-4407-bf91-27ede3c481e5"],
      },
      pagination: {
        "limit": 10,
        "offSet": 0,
        "sortBy": "string",
        "order": "DESC"
    }
    },
  };


  const { isLoading, data } = Digit.Hooks.useCustomAPIHook(requestCriteria);
  const columns = [
    { label: t("MB_SNO"), key: "sno" },
    { label: t("MB_REFERENCE_NUMBER"), key: "mbref" },
    { label: t("MB_MUSTER_ROLL_ID"), key: "musterid" },
    { label: t("MB_DATE"), key: "mbDate" },
    { label: t("MB_PERIOD"), key: "period" },
    { label: t("MB_STATUS"), key: "status" },
    { label: t("MB_ONLY_AMOUNT"), key: "amount" },
  ];

  const filteredArray = data?.measurements.filter(item => item.measurementNumber !== measurementNumber);

  const filteredRows = filteredArray?.map((item, index) =>({
    sno :index + 1,
    mbref : item?.measurementNumber,
    musterid : "M1234",
    mbDate : item?.entryDate,
    period : "test",
    status : "Submitted",
    amount : 1000
  }))

  if (isLoading) {
    return <Loader></Loader>
  }

  return (
    <Card className="override-card">
      <Header className="works-header-view">{t("MB_HISTORY")}</Header>
      <CustomCollapsibleTable isTableCollapsed={isTableCollapsed}>
        <table className="table reports-table sub-work-table">
          <thead>
            <tr>
              {columns.map((column, index) => (
                <th key={index}>{column.label}</th>
              ))}
            </tr>
          </thead>
          <tbody>
            {filteredRows.map((row, rowIndex) => (
              <tr key={rowIndex}>
                {columns.map((column, columnIndex) => (
                  <td key={columnIndex}>
                    {column.key === "mbref" ? (
                      <a
                        href={`${window.location.origin}${window.location.pathname}?tenantId=${tenantId}&workOrderNumber=${contractNumber}&mbNumber=${row.mbref}`}
                        style={{ color: "#f37f12" }}
                      >
                        {row[column.key]}
                      </a>
                    ) : (
                      row[column.key]
                    )}
                  </td>
                ))}
              </tr>
            ))}
          </tbody>
        </table>
      </CustomCollapsibleTable>
      <Button
        className={"collapse-button"}
        onButtonClick={toggleTableCollapse}
        label={isTableCollapsed ? "Show MB History" : "Hide MB History"}
      ></Button>
    </Card>
  );
};

export default MeasurementHistory;
