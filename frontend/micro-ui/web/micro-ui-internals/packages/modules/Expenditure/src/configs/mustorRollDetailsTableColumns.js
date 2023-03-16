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
        <p className="detail">{value?.accountNo}</p>
        <p className="detail">{value?.ifsc}</p>
      </div>
    );
};

export const mustorRollDetailsTableColumns = (t) => [
  {
    Header: () => <p>{t("WORKS_SNO")}</p>,
    accessor: "sno",
    Cell: ({ value, column, row }) => {
      if (row.original.type === "total") {
        return renderTotalLabel(t(row.original.sno));
      }
      return String(t(value));
    },
  },
  {
    Header: () => <p>{t("EXP_REG_ID")}</p>,
    accessor: "registerId",
    Cell: ({ value, column, row }) => {
      return String(t(value));
    },
  },
  {
    Header: () => <p>{t("EXP_NAME_OF_THE_INDIVIDUAL")}</p>,
    accessor: "nameOfIndividual",
    Cell: ({ value, column, row }) => {
      return String(t(value));
    },
  },
  {
    Header: () => <p>{t("EXP_FATHER_GUARDIAN_NAME")}</p>,
    accessor: "guardianName",
    Cell: ({ value, column, row }) => {
      return String(t(value));
    },
  },
  {
    Header: () => <p>{t("ATM_SKILL")}</p>,
    accessor: "skill",
    Cell: ({ value, column, row }) => {
      return String(t(value));
    }
  },
  {
    Header: () => <p>{"Days worked"}</p>,
    accessor: "actualWorkingDays",
    Cell: ({ value, column, row }) => {
      return String(t(value));
    }
  },
  {
    Header: () => <p>{"Days measured"}</p>,
    accessor: "modifiedWorkingDays",
    Cell: ({ value, column, row }) => {
      return String(t(value));
    }
  },
  {
    Header: () => <p>{"Per day wage (₹)"}</p>,
    accessor: "amount",
    Cell: ({ value, column, row }) => {
      return Digit.Utils.dss.formatterWithoutRound(value, 'number');
    }
  },
  {
    Header: () => <p>{"Total Wage (₹)"}</p>,
    accessor: "modifiedAmount",
    Cell: ({ value, column, row }) => {
      return Digit.Utils.dss.formatterWithoutRound(value, 'number');
    }
  },
  {
    Header: () => <p>{t("EXP_BANK_ACCOUNT_DETAILS")}</p>,
    accessor: "bankAccountDetails",
    Cell: ({ value, column, row }) => {
      return renderBankAccountDetails(value);
    }
  },
  {
    Header: () => <p>{"Payment Status"}</p>,
    accessor: "paymentStatus",
    Cell: ({ value, column, row }) => {
      if (row.original.type === "total") {
        return String(t(value));
      }
      if(value === 'PAYMENT_COMPLETED') {
        return <span style={{"color":"#0B6623"}}>{t(`EXP_${value}`)}</span>
      } else {
        return <span style={{"color":"#FF0000"}}>{t(`EXP_${value}`)}</span>
      }
    }
  },
  // {
  //     Header: t("EXP_AADHAR_NO"),
  //     accessor: "aadharNumber",
  //     Cell: ({ value, column, row }) => {
  //       return String(t(value));
  //     }
  // }    
]

/*
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
        Header:t("EXP_NAME_OF_THE_INDIVIDUAL"),
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
*/