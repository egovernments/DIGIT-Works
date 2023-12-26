import React, { useEffect, useMemo } from "react";
import { useTranslation } from "react-i18next";
import { Header, InboxSearchComposer, Loader, Button, AddFilled } from "@egovernments/digit-ui-react-components";
import { useHistory, useLocation } from "react-router-dom";
import InboxMeasurementConfig from "../../configs/MeasurementInboxConfig";

const InboxMeasurement = () => {
  const { t } = useTranslation();
  const configs = InboxMeasurementConfig();
  const location = useLocation()
  return (
    <React.Fragment>
      <div className="jk-header-btn-wrapper">
        <Header styles={{ fontSize: "32px" }}>{t(configs?.label)}{location?.state?.count ? <span className="inbox-count">{location?.state?.count}</span> : null}</Header>
      </div>
      <div className="inbox-search-wrapper">
        <InboxSearchComposer configs={configs}></InboxSearchComposer>
      </div>
    </React.Fragment>
  );
};

export default InboxMeasurement;