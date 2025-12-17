import React, { useState } from "react";
import { getBreakupDetails, transformBillData } from "../utils/paymentTrackerUtils";
import { useTranslation } from "react-i18next";

export const paymentTrackerViewConfig = (project, projectBillPaidData ,projectBillData, projectId, headerLocale) => {

  const { t } = useTranslation();
  const [excludeFailed, setExcludeFailed] = useState(false);

  const breakupDetails = getBreakupDetails({projectBillPaidData});

  const tableRows = transformBillData({projectBillData});

  const amountFormatter = (amount) => {
    return Digit.Utils.dss.formatterWithoutRound(parseFloat(amount).toFixed(2),"number",undefined,true,undefined,2);
  }

  return {
    cards: [
      {
        sections: [
          {
            type: "DATA",
            values: [
              {
                key: "PROJECT_ID",
                value: project?.projectNumber,
              },
              {
                key: "PROJECT_NAME_SIMPLE",
                value: project?.name,
              },
              {
                key: "PROJECT_LOCATION",
                value: `${(project?.additionalDetails?.locality ? t(`${headerLocale}_ADMIN_${project?.additionalDetails?.locality}`) : t("ES_COMMON_NA"))}, ${(project?.address?.boundary ? t(`${headerLocale}_ADMIN_${project?.address?.boundary}`) : t("ES_COMMON_NA"))}, ${(headerLocale ? t(`TENANT_TENANTS_${headerLocale}`) : t("ES_COMMON_NA"))}`,
              },
              {
                key: "PROJECT_DESCRIPTION",
                value: project?.description,
              },
            ],
          },
        ],
      },
      {
        sections: [
          {
            type: "DATA",
            cardHeader: { value: "HEAD_WISE_BREAKUP", inlineStyles: {marginBottom : "16px"} },
            values: [
              {
                key: "ESTMATED_AMOUNT",
                value: amountFormatter(projectBillPaidData?.estimatedAmount || 0),
              },
              {
                key: "WAGE_AMOUNT_PAID",
                value: amountFormatter(breakupDetails?.wageAmountPaid || 0),
              },
              {
                key: "PURCHASE_AMOUNT_PAID",
                value: amountFormatter(breakupDetails?.purchaseAmountPaid || 0),
              },
              {
                key: "SUPERVISION_AMOUNT_PAID",
                value: amountFormatter(breakupDetails?.supervisionAmountPaid || 0),
              },
              {
                key: "FAILED_PAYMENT_AMOUNT",
                value: amountFormatter(breakupDetails?.failedPaymentAmount || 0),
              },
            ],
          },
        ],
      },
      {
        sections : [
          {
              type : "COMPONENT",
              cardHeader : { value : "PAYMENT_DETAILS" },
              component : "PaymentTrackerTable",
              props : {
                projectId : "test",
                tableRows : tableRows,
                excludeFailed : excludeFailed,
                setExcludeFailed : setExcludeFailed
              }
          },
          {
            type: "COMPONENT",
            // cardHeader: { value: "", inlineStyles: {} },
            component: "ViewTotalPaymentAmount",
            props: {
              mode: "VIEWES",
              tableRows : tableRows,
              excludeFailed : excludeFailed
            }
          }
        ]
      },
    ]
  }
}