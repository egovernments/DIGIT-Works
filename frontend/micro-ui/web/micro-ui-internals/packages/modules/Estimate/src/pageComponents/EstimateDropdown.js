import { Loader } from "@egovernments/digit-ui-react-components";
import { Dropdown } from "@egovernments/digit-ui-components";
import React from "react";
import { useTranslation } from "react-i18next";

const EstimateDropdown = (props) => {
  const { t } = useTranslation();
  const tenantId = Digit.ULBService.getCurrentTenantId();

  const requestCriteria = {
    url: "/mdms-v2/v2/_search",
    body: {
      MdmsCriteria: {
        tenantId: tenantId,
        filters: {},
        isActive: true,
        schemaCode: props?.schemaCode,
        limit: 50,
        offset: 0,
      },
    },
    changeQueryName: props?.schemaCode,
  };
  const { isLoading, data } = Digit.Hooks.useCustomAPIHook(requestCriteria);


  if (isLoading) {
    return <Loader />;
  }
  const filteredCodes = data?.mdms
    .filter((item) => item.data && item.data.code && item?.data?.description !== "Not Applicable") // Ensure 'data' and 'code' exist
    .map((item) => ({ code: item.data.code, label: Digit.Utils.locale.getTransformedLocale(`${props?.schemaCode}.${item.data.code}`) }));

  return (
    <div className="sor-dropdowns">
      <div className="sor-label">{props?.label}</div>
      <Dropdown
        t={t}
        optionKey={"label"}
        select={(e) => {
          props?.setStateData({
            ...props?.stateData,
            [props?.type]: e?.code,
          });
        }}
        option={filteredCodes}
        selected={filteredCodes?.filter((e) => e.code == props?.stateData[props?.type])?.[0] || { code: null }}
        // className = "dropdown-width"
      />
    </div>
  );
};

export default EstimateDropdown;
