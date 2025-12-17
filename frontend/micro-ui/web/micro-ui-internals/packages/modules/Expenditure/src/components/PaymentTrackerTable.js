import { Table, CheckBox } from '@egovernments/digit-ui-react-components'
import React, { useEffect, useMemo, useState } from 'react'
import { useTranslation } from 'react-i18next'
import { Button } from '@egovernments/digit-ui-components'
import { Link } from "react-router-dom";

const PaymentTrackerTable = ({excludeFailed, setExcludeFailed, ...props}) => {
  const { t } = useTranslation();
  const tenantId = Digit.ULBService.getCurrentTenantId();

  const [tableRows, setTableRows] = useState(props?.tableRows);
  
  const tableColumns = useMemo(()=>{
    return [
      {
        Header: t("BILL_NUMBER"),
        accessor: "billNumber",
        Cell: ({ value, column, row }) => {
          return (
            <div>
              {value ? (
                <span className={"link"}>
                  <Link
                    to={`/${window.contextPath}/employee/expenditure/${value?.includes("PB")? "purchase-bill-details" : (value?.includes("SB")? "supervision-bill-details" : "wage-bill-details")}?tenantId=${tenantId}&billNumber=${value}&workOrderNumber=${row.original.workOrderNumber?.split("_")?.[0]}`}
                  >
                    <Button
                      className=""
                      iconFill=""
                      label={String(value ? value : t("ES_COMMON_NA"))}
                      size="medium"
                      style={{ padding: "0px" }}
                      title=""
                      variation="link"
                    />
                  </Link>
                </span>
              ) : (
                <span>{t("ES_COMMON_NA")}</span>
              )}
            </div>
          );
        }
      },
      {
        Header: t("BILL_TYPE"),
        accessor: "billType",
        Cell: ({ value, column, row }) => {
          return String(t(value || "ES_COMMON_NA"));
        }
      },
      {
        Header: () => (
          <div style={{ minWidth: '100px', textAlign: 'right' }}>
            {t("BILL_AMOUNT_RS")}
          </div>
        ),
        accessor: "total",
        Cell: ({ value, column, row }) => {
          return (
            <div style={{ textAlign: 'right' }}>
              {Digit.Utils.dss.formatterWithoutRound(parseFloat(value || 0).toFixed(2),"number",undefined,true,undefined,2)}
            </div>
          );
        }
      },
      {
        Header: () => (
          <div style={{ minWidth: '200px' }}>
            {t("PI_NUMBER")}
          </div>
        ),
        accessor: "piNumber",
        Cell: ({ value, column, row }) => {
          if (value === "NA") {
            return String(t("NA"));
          }
          return (
            <div>
              {value ? (
                <span className={"link"}>
                  <Link
                    to={`/${window.contextPath}/employee/expenditure/view-payment?tenantId=${tenantId}&paymentNumber=${row.original.paymentNumber}`}
                  >
                    <Button
                      className=""
                      iconFill=""
                      label={String(value ? value : t("ES_COMMON_NA"))}
                      size="medium"
                      style={{ padding: "0px" }}
                      title=""
                      variation="link"
                    />
                  </Link>
                </span>
              ) : (
                <span>{t("ES_COMMON_NA")}</span>
              )}
            </div>
          );
        }
      },
      {
        Header: () => (
          <div style={{ minWidth: '200px' }}>
            {t("PARENT_PI")}
          </div>
        ),
        accessor: "parentPi",
        Cell: ({ value, column, row }) => {
          return String(t(value || "ES_COMMON_NA"));
        }
      },
      {
        Header: () => (
          <div style={{ minWidth: '100px' }}>
            {t("PI_TYPE")}
          </div>
        ),
        accessor: "piType",
        Cell: ({ value, column, row }) => {
          return String(t(value || "ES_COMMON_NA"));
        }
      },
      {
        Header: () => (
          <div style={{ minWidth: '100px' }}>
            {t("PI_CREATION_DATE")}
          </div>
        ),
        accessor: "piCreationDate",
        Cell: ({ value, column, row }) => {
          return String(t(value || "ES_COMMON_NA"));
        }
      },
      {
        Header: () => (
          <div style={{ minWidth: '100px', textAlign: 'right' }}>
            {t("PI_AMOUNT_RS")}
          </div>
        ),
        accessor: "paidAmount",
        Cell: ({ value, column, row }) => {
          return (
            <div style={{ textAlign: 'right' }}>
              {Digit.Utils.dss.formatterWithoutRound(parseFloat(value || 0).toFixed(2),"number",undefined,true,undefined,2)}
            </div>
          );
        }
      },
      {
        Header: () => (
          <div style={{ minWidth: '100px' }}>
            {t("PI_DATE")}
          </div>
        ),
        accessor: "piDate",
        Cell: ({ value, column, row }) => {
          return String(t(value || "ES_COMMON_NA"));
        }
      },
      {
        Header: () => (
          <div style={{ minWidth: '100px' }}>
            {t("PI_STATUS")}
          </div>
        ),
        accessor: "piStatus",
        Cell: ({ value, column, row }) => {
          return String(t(value || "ES_COMMON_NA"));
        }
      },
      {
        Header: () => (
          <div style={{ minWidth: '100px', textAlign: 'right' }}>
            {t("PAYMENT_FAILED_RS")}
          </div>
        ),
        accessor: "paymentFailed",
        Cell: ({ value, column, row }) => {
          return (
            <div style={{ textAlign: 'right' }}>
              {Digit.Utils.dss.formatterWithoutRound(parseFloat(value || 0).toFixed(2),"number",undefined,true,undefined,2)}
            </div>
          );
        }
      },
      {
        Header: () => (
          <div style={{ minWidth: '100px', textAlign: 'right' }}>
            {t("PAYMENT_SUCCESSFUL_RS")}
          </div>
        ),
        accessor: "paymentSuccessful",
        Cell: ({ value, column, row }) => {
          return (
            <div style={{ textAlign: 'right' }}>
              {Digit.Utils.dss.formatterWithoutRound(parseFloat(value || 0).toFixed(2),"number",undefined,true,undefined,2)}
            </div>
          );
        }
      },
    ]
  },[])

  useEffect(() => {
    if (excludeFailed) {
      setTableRows(props?.tableRows.filter(row => row.piStatus !== "FAILED"));
    } else {
      setTableRows(props?.tableRows);
    }
  }, [excludeFailed])
  return (
    <div>
      <CheckBox 
        styles={{marginTop: "14px"}}
        style={{marginLeft: "40px"}}
        onChange={() => setExcludeFailed(!excludeFailed)}
        checked={excludeFailed}
        label={t("EXCLUDE_FAILED_PAYMENT_INSTRUCTIONS")}
      />
      <div style={{marginTop: "0rem", border: "1px solid #D6D5D4",  borderRadius: "2px" }}>
        <Table
          customTableWrapperClassName={"dss-table-wrapper"}
          t={t}
          data={tableRows}
          totalRecords={tableRows.length}
          columns={tableColumns}
          manualPagination={false}
          getCellProps={(cellInfo) => {
            return {
              style: {
                padding: "20px 18px",
                // fontSize: "16px",
              },
            };
          }}
        />
      </div>
    </div>
  )
}

export default PaymentTrackerTable