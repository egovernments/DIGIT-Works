import React, { useState, Fragment } from "react";
import { useTranslation } from "react-i18next";
import { Toast } from "@egovernments/digit-ui-react-components";

const Search = ({ path }) => {
  const { t } = useTranslation();
  const tenantId = Digit.ULBService.getCurrentTenantId();
  const [payload, setPayload] = useState({});
  const Search = Digit.ComponentRegistryService.getComponent("SearchEstimate");
  // const [businessServ, setBusinessServ] = useState("");
  const getUrlPathName = window.location.pathname;
  const checkPathName = getUrlPathName.includes("works/search-Estimate");
  const businessServ = checkPathName ? "WORKS" : "";
  const [showToast, setShowToast] = useState(null);

  function onSubmit(_data) {
    console.log("search Data",_data)
    if(_data.adminSanctionNumber==="" && _data.estimateNumber==="" && _data.subEstimateNumber==="" && !_data.department && !_data.fromProposalDate && !_data.toProposalDate ){
      setShowToast({ warning: true, label: "ERR_PT_FILL_VALID_FIELDS" });
      setTimeout(() => {
        setShowToast(false);
      }, 3000);
      return
    }
    var fromDate = new Date(_data?.fromDate);
    fromDate?.setSeconds(fromDate?.getSeconds() - 19800);
    var toDate = new Date(_data?.toDate);
    toDate?.setSeconds(toDate?.getSeconds() + 86399 - 19800);
    const data = {
      ..._data,
      ...(_data.toDate ? { toDate: toDate?.getTime() } : {}),
      ...(_data.fromDate ? { fromDate: fromDate?.getTime() } : {}),
    };
    setPayload(
      Object.keys(data)
        .filter((k) => data[k])
        .reduce((acc, key) => ({ ...acc, [key]: typeof data[key] === "object" ? data[key].code : data[key] }), {})
    );
  }

  const config = {
    enabled: !!(payload && Object.keys(payload).length > 0),
  };
  //API Call
  const result = Digit.Hooks.works.useSearchWORKS({ tenantId, filters: payload, config });
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
        data={result?.data ? result?.data : { display: "ES_COMMON_NO_DATA" }}
        count={result?.count}
        resultOk={!result?.isLoading}
        businessService={businessServ}
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