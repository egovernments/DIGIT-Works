import React, { useState, Fragment } from "react";
import { useTranslation } from "react-i18next";
import { Toast } from "@egovernments/digit-ui-components";
import { searchEstimatePayload } from "../../utils/searchEstimatePayload";

const Search = ({ path }) => {
  const { t } = useTranslation();
  const tenantId = Digit.ULBService.getCurrentTenantId();
  const Search = Digit.ComponentRegistryService.getComponent("SearchEstimate");
  const [showToast, setShowToast] = useState(null);
  const [payload, setPayload] = useState({});
  
  const onSubmit=async(_data)=> {
    var fromProposalDate = new Date(_data?.fromProposalDate);
    fromProposalDate?.setSeconds(fromProposalDate?.getSeconds() - 19800);
    var toProposalDate = new Date(_data?.toProposalDate);
    toProposalDate?.setSeconds(toProposalDate?.getSeconds() + 86399 - 19800);
    const data = {
      ..._data,
      ...(_data.toProposalDate ? { toProposalDate: toProposalDate?.getTime() } : {}),
      ...(_data.fromProposalDate ? { fromProposalDate: fromProposalDate?.getTime() } : {}),
    };
    
    setPayload(
      Object.keys(data)
      .filter((k) => data[k])
      .reduce((acc, key) => ({ ...acc, [key]: typeof data[key] === "object" ? data[key].code : data[key] }), {})
      );
      
    if(data.adminSanctionNumber==="" && data.estimateNumber==="" && data.subEstimateNumber==="" && !data.department && !data.fromProposalDate && !data.toProposalDate ){
      setShowToast({ type:"warning", label: "ERR_PT_FILL_VALID_FIELDS" });
      setTimeout(() => {
        setShowToast(false);
      }, 3000);
      return
    }
  }
    const config = {
      enabled: !!(payload && Object.keys(payload).length > 0),
    };
  // Call search estimate API by using params tenantId,filters
    const result = Digit.Hooks.works.useSearchWORKS({ tenantId, filters: payload, config });
    
  return (
    <Fragment>
      <Search
        t={t}
        tenantId={tenantId}
        onSubmit={onSubmit}
        data={result?.estimates ? result?.estimates : { display: "ES_COMMON_NO_DATA" }}
        count={result?.count}
        resultOk={!result?.estimates}
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
  );
};

export default Search;