import React, { Fragment, useState } from 'react'
import { useTranslation } from "react-i18next";
import { Toast } from "@egovernments/digit-ui-react-components";
import { searchEstimatePayload } from "../../utils/searchEstimatePayload";

const SearchEstimate = () => {
    const { t } = useTranslation();
    const tenantId = Digit.ULBService.getCurrentTenantId();
    const SearchApplication = Digit.ComponentRegistryService.getComponent("SearchEstimateApplication");
    const [showToast, setShowToast] = useState(null);
    const [payload, setPayload] = useState({});


  const onSubmit = async (_data) => {
    var fromProposalDate = new Date(_data?.fromProposalDate);
    fromProposalDate?.setSeconds(fromProposalDate?.getSeconds() - 19800);
    var toProposalDate = new Date(_data?.toProposalDate);
    toProposalDate?.setSeconds(toProposalDate?.getSeconds() + 86399 - 19800);
    const data = {
      ..._data,
      ...(_data.toDate ? { toDate: toDate?.getTime() } : {}),
      ...(_data.fromProposalDate ? { fromProposalDate: fromProposalDate?.getTime() } : {}),
    };

    setPayload(
      Object.keys(data)
      .filter((k) => data[k])
      .reduce((acc, key) => ({ ...acc, [key]: typeof data[key] === "object" ? data[key].code : data[key] }), {})
      );

    if(data.estimateNumber==="" && data.adminSanctionNumber==="" && !data.department && !data.typeofwork && !data.fromProposalDate && !data.toProposalDate ){
      setShowToast({ warning: true, label: "ERR_PT_FILL_VALID_FIELDS" });
      setTimeout(() => {
        setShowToast(false);
      }, 3000);
      return
    }
  }
    const config = {
      enabled: !!(payload && Object.keys(payload).length > 0),
    };
    const result = Digit.Hooks.works.useSearchWORKS({ tenantId, filters: payload, config });

  
  return (
    <Fragment>
      <SearchApplication 
        onSubmit={onSubmit}
        data={result?.estimates ? result?.estimates : { display: "ES_COMMON_NO_DATA" }}
        // count={result?.count}
        resultOk={!result?.estimates}
      />
      {showToast && (
        <Toast
          error={showToast.error}
          warning={showToast.warning}
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