import React, { Fragment, useState } from 'react'
import { useTranslation } from "react-i18next";
import { Toast } from "@egovernments/digit-ui-react-components";
import { searchEstimatePayload } from "../../utils/searchEstimatePayload";

const SearchEstimate = () => {
  const { t } = useTranslation();
  const tenantId = Digit.ULBService.getCurrentTenantId();
  const SearchApplication = Digit.ComponentRegistryService.getComponent("SearchEstimateApplication");
  const [showToast, setShowToast] = useState(null);
  const [result, setResult] = useState({})
  const { mutate: searchEstimateMutation } = Digit.Hooks.works.useSearchEstimate();

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

    if (data.estimateNumber === "" && data.adminSanctionNumber === "" && !data.department && !data.typeofwork && !data.fromProposalDate && !data.toProposalDate) {
      setShowToast({ warning: true, label: "ERR_PT_FILL_VALID_FIELDS" });
      setTimeout(() => {
        setShowToast(false);
      }, 3000);
      return
    }
    const payload = searchEstimatePayload(data)
    const searchCriteria = { searchCriteria: payload }
    await searchEstimateMutation(searchCriteria, {
      onError: (error, variables) => {

        setShowToast({ warning: true, label: error?.response?.data?.Errors?.[0].message ? error?.response?.data?.Errors?.[0].message : error });
        setTimeout(() => {
          setShowToast(false);
        }, 5000);
      },
      onSuccess: async (responseData, variables) => {
        setResult(responseData)
      }
    })
  }
  return (
    <Fragment>
      <SearchApplication onSubmit={onSubmit} />
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