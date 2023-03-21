import React from 'react'
import { useTranslation } from "react-i18next";
import { useHistory } from 'react-router-dom';
import { Header, ActionBar, SubmitBar } from '@egovernments/digit-ui-react-components';
import ApplicationDetails from '../../../../../templates/ApplicationDetails';

const ViewBill = () => {
  const { t } = useTranslation();
  const { tenantId, billNumber } = Digit.Hooks.useQueryParams();

  const {isLoading, data, isError, isSuccess, error} = Digit.Hooks.bills.useViewBill(tenantId, {}, { musterRollNumber: billNumber })

  const reqCriteria = {
    url: "/expensebilling/demand/v1/_search",
    params: {},
    body: {
      "SearchCriteria": {
        "tenantId": tenantId,
        "billNumbers": [data?.applicationData?.musterRollNumber],
        // "billNumber": "MR/2022-23/01/001",
        // "tenantId": "pg.citya",
      },
      "Pagination": {
        "offset": 0,
        "limit": 100
      }
    },
    config: {
      enabled: isSuccess ? true : false,
      // enabled: true
    },
  }

  const { isLoading: isBillLoading, data: billData, revalidate, isFetching: isBillFetching } = Digit.Hooks.useCustomAPIHook(reqCriteria);

  const tableEntriesMustor = data?.applicationDetails?.applicationDetails?.[1]?.additionalDetails?.table?.tableData
  
  const benficiaries = billData?.BillDemands[0]?.beneficiaries

  //mapping mustor roll table entries with bill search response beneficiery ids and updating status

  benficiaries?.forEach(row=>{
    
    tableEntriesMustor[row.id].paymentStatus = `PAYMENT_${row?.status}`
  })

  // Object.keys(tableEntriesMustor).map((key,idx)=> {
  //   tableEntriesMustor[key].paymentStatus = `PAYMENT_${row?.status}`
  // })
  // tableEntriesMustor?.forEach((entry,idx) => {
  //   benficiaries?.forEach((row)=>{
  //     if (row?.id === entry?.individualId) {
  //       entry.paymentStatus = `PAYMENT_${row?.status}`
  //     }
  //   })
  // })

  return (
    <React.Fragment>
      <Header>{t("EXP_VIEW_BILL")}</Header>
      <ApplicationDetails
        applicationDetails={data?.applicationDetails}
        isLoading={isLoading || isBillLoading}
        applicationData={data?.applicationData}
        moduleCode="AttendenceMgmt"
        isDataLoading={false}
        workflowDetails={data?.workflowDetails}
        showTimeLine={false}
        mutate={()=>{}}
        tenantId={tenantId}
      />
    </React.Fragment>
  )
}

export default ViewBill;