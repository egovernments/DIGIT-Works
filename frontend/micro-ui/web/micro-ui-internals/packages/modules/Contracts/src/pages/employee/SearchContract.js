import React, { Fragment, useState } from 'react'
import { useTranslation } from "react-i18next";
import { Toast } from "@egovernments/digit-ui-components";

const SearchContracts = () => {
    const { t } = useTranslation();
    const tenantId = Digit.ULBService.getCurrentTenantId();
    const SearchContractApplication = Digit.ComponentRegistryService.getComponent("SearchContractApplication");
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
    setShowTable(true)

    var fromProposalDate = new Date(_data?.fromProposalDate);
    fromProposalDate?.setSeconds(fromProposalDate?.getSeconds() - 19800);
    var toProposalDate = new Date(_data?.toProposalDate);
    toProposalDate?.setSeconds(toProposalDate?.getSeconds() + 86399 - 19800);
    const data = {
      ..._data,
      ...(_data.toProposalDate ? { toProposalDate: toProposalDate?.getTime() } : {}),
      ...(_data.fromProposalDate ? { fromProposalDate: fromProposalDate?.getTime() } : {}),
    };

    if(data.nameOfTheProject==="" && data.contractId==="" && data.estimateNumber==="" && !data.fromProposalDate && !data.toProposalDate ){
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
  }
    const config = {
      enabled: !!(payload && Object.keys(payload).length > 0),
    };
    // Call search estimate API by using params tenantId,filters
    // const result = Digit.Hooks.works.useSearchWORKS({ tenantId, filters: payload, config });
    const result = {
      status: "success",
      isSuccess: true,
      totalCount: 10,
      isLoading: false,
      data:{
        contracts: [{
          tenantId:"pb.amritsar",
          contractId: "1136/TO/DB/FLOOD/10-11",
          contractDate: "08/09/2010",
          contractType: "Work Order",
          nameOfTheWork: "Providing CC Drain in Birla Gaddah (Tungabhaqdra workers colony) in 27th ward",
          abstractEstimateNumber: "EST/KRPN/1136",
          implementingAuthority: "Organisation",
          orgnName: "Maa Bhagavati SHG",
          officerIncharge: "S.A Bhasha",
          agreemntAmount: "35,53,600.00",
          status:"Approved"
        }]
      }
    }
    
    const getData = () => {
      if (result.data.contracts?.length == 0 ) {
        return { display: "ES_COMMON_NO_DATA" }
      } else if (result?.data.contracts?.length > 0) {
        return result?.data.contracts
      } else {
        return [];
      }
    }
  
    const isResultsOk = () => {
      return result?.data.contracts?.length > 0 ? true : false;
    }
  
  return (
    <Fragment>
      <SearchContractApplication
        onSubmit={onSubmit}
        data={getData()}
        // count={result?.count}
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

export default SearchContracts