import React, { useState, Fragment } from "react";
import { useTranslation } from "react-i18next";
import { Toast } from "@egovernments/digit-ui-react-components";
import { searchEstimatePayload } from "../../utils/searchEstimatePayload";

const Search = ({ path }) => {
  const { t } = useTranslation();
  const tenantId = Digit.ULBService.getCurrentTenantId();
  const Search = Digit.ComponentRegistryService.getComponent("SearchEstimate");
  const [showToast, setShowToast] = useState(null);
  const [result, setResult] = useState({})

  const { mutate: searchEstimateMutation } = Digit.Hooks.works.useSearchWORKS();

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

    if (data.adminSanctionNumber === "" && data.estimateNumber === "" && data.subEstimateNumber === "" && !data.department && !data.fromProposalDate && !data.toProposalDate) {
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
  // const result={data:[{subEstimateNumber:"LE/ENG/00002/10/2017-18",
  //                       nameOfWork:"Providing CC Drain in Birla Gaddah (Tungabhaqdra workers colony) in 27th ward",
  //                       department:"ENGINEERING",
  //                       administrativeSanctionNo:29,
  //                       adminApprovedDate:"29/05/2022",
  //                       fund:"Municipal Fund",
  //                       function:'City and Town Planning',
  //                       budgetHead:"4123001-CWIP-Concrete Road",
  //                       createdBy:"A.P.Sreenivasulu",
  //                       owner:"A.P.Sreenivasulu",
  //                       status:"Created",
  //                       totalAmount:"Rs 10,000",
  //                       actions:"Create LOI"
  //                     }]}
  return (
    <Fragment>
      <Search
        t={t}
        tenantId={tenantId}
        onSubmit={onSubmit}
        data={result?.estimates ? result?.estimates : { display: "ES_COMMON_NO_DATA" }}
        count={result?.count}
        resultOk={!result?.isLoading}
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
  );
};

export default Search;