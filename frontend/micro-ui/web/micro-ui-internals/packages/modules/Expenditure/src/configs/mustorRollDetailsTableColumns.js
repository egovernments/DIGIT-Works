import React from "react";

const GetCell = (value) => <span className="cell-text">{value}</span>;

const renderTotalLabel = (value) => {
    return (
      <div className="total-attendence-label">
        <span>{value}</span>
      </div>
    );
};

const renderBankAccountDetails = (value) => {
    return (
      <div className="column-bank-details">
        <p className="detail">{value.accountNo}</p>
        <p className="detail">{value.ifsc}</p>
      </div>
    );
};

export const mustorRollDetailsTableColumns = (t) => [
    {
        Header: t("EXP_SNO"),
        accessor: "sno",
        Cell: ({value, row}) => {
            
            if (row.original.type === "total") {
              return renderTotalLabel(t(value))
            }
            return GetCell(value)
          },
    },
    {
      Header: t("EXP_REG_ID"),
      accessor: (row) => (GetCell(row?.registrationId)),
    },
    {
        Header: t("EXP_NAME_OF_THE_INDIVIDUAL"),
        accessor: (row) => (GetCell(row?.individualName)),
    },
    {
        Header: t("EXP_FATHER_GUARDIAN_NAME"),
        accessor: (row) => (GetCell(row?.guardianName)),
    },
    {
        Header: t("EXP_MODIFIED_AMT_RS"),
        accessor: (row) => (GetCell(row?.modifiedAmt)),
    },
    {
        Header: t("EXP_BANK_ACCOUNT_DETAILS"),
        accessor: "bankAccountDetails",
        Cell: ({ value }) => {
            return renderBankAccountDetails(value);
        }
    },
    {
        Header: t("EXP_AADHAR_NO"),
        accessor: (row) => (GetCell(row?.aadhar)),
    }    
]