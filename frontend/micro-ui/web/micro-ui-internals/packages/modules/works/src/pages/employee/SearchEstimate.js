import React, { Fragment, useState } from 'react'
import { useTranslation } from "react-i18next";
import { Toast } from "@egovernments/digit-ui-components";

const SearchEstimate = () => {
    const { t } = useTranslation();
    const tenantId = Digit.ULBService.getCurrentTenantId();
    const SearchApplication = Digit.ComponentRegistryService.getComponent("SearchEstimateApplication");
    const [showToast, setShowToast] = useState(null);
    const [payload, setPayload] = useState({});
    const { isLoading: hookLoading, isError, error, data:employeeData } = Digit.Hooks.hrms.useHRMSSearch(
        null,tenantId,
      );
    const [showTable, setShowTable] = useState(true)
    const onClearSearch = (isShow = true) => {
        setShowTable(isShow);
    }
  const onSubmit = async (_data) => {
    var fromProposalDate = new Date(_data?.fromProposalDate);
    fromProposalDate?.setSeconds(fromProposalDate?.getSeconds() - 19800);
    var toProposalDate = new Date(_data?.toProposalDate);
    toProposalDate?.setSeconds(toProposalDate?.getSeconds() + 86399 - 19800);
    const data = {
      ..._data,
      ...(_data.toProposalDate ? { toProposalDate: toProposalDate?.getTime() } : {}),
      ...(_data.fromProposalDate ? { fromProposalDate: fromProposalDate?.getTime() } : {}),
    };

    if(data.estimateNumber==="" && data.adminSanctionNumber==="" && !data.department && !data.typeOfWork && !data.fromProposalDate && !data.toProposalDate ){
      setShowToast({ type:"warning", label: "ERR_PT_FILL_VALID_FIELDS" });
      setTimeout(() => {
        setShowToast(false);
      }, 3000);
      return
    }
    setPayload(
      Object.keys(data)
      .filter((k) => data[k])
      .reduce((acc, key) => ({ ...acc, [key]: typeof data[key] === "object" ? data[key].code : data[key] }), {})
      );
      
    setShowTable(true)
  }
    const config = {
      enabled: !!(payload && Object.keys(payload).length > 0),
      cacheTime:0
    };
    // Call search estimate API by using params tenantId,filters
    const result = Digit.Hooks.works.useSearchWORKS({ tenantId, filters: payload, config });
    const getData = () => {
      if (result?.data?.estimates?.length == 0 ) {
        return { display: "ES_COMMON_NO_DATA" }
      } else if (result?.data?.estimates?.length > 0) {
        let newResult = [];
        result?.data?.estimates?.map((val)=>{
          let totalAmount = 0
              val?.estimateDetails?.map((amt)=>{
              totalAmount = totalAmount + amt?.amount
            })
            employeeData?.Employees?.map((item)=>{
              if(val?.auditDetails?.lastModifiedBy === item?.uuid){
                Object.assign(val,{"owner":item?.user?.name})
              }
              if(val?.auditDetails?.createdBy === item?.uuid){
                newResult.push(Object.assign(val,{"createdBy":item?.user?.name,"totalAmount":totalAmount}))
              }
          })
        })
      return newResult
      } else {
        return [];
      }
    }

    const isResultsOk = () => {
      return result?.data?.estimates?.length > 0 ? true : false;
    }
  
  return (
    <Fragment>
      <SearchApplication 
        onSubmit={onSubmit}
        data={getData()}
        count={result?.data?.count}
        resultOk={isResultsOk()}
        isLoading={result?.isLoading}
        onClearSearch={onClearSearch} 
        showTable={showTable}
      />
      {showToast && (
        <Toast
          type={showToast?.type}
          label={t(showToast.label)}
          onClose={() => {
            setShowToast(null);
          }}
        />
      )}
    </Fragment>
  )
}

export default SearchEstimate 