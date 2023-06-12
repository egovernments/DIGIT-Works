import { Card, Header, Loader } from "@egovernments/digit-ui-react-components";
import React, { useState, Fragment, useEffect } from "react";
import { useTranslation } from "react-i18next";
import CalculationComponent from "./CalculationComponent";

const Calculations = () => {
  const nationalUser = Digit.SessionStorage.get("User")?.info?.roles?.some((role)=> role.code==="NATIONAL_SUPERVISOR");
  const user = Digit.UserService.getUser();
  const tenantId = user?.info?.tenantId || Digit.ULBService.getCurrentTenantId();
  const { t } = useTranslation();
  const { isLoading, data } = Digit.Hooks.useGetDSSCalculationsJSON(Digit.ULBService.getStateId());
  const calculations = data?.MdmsRes["dss-dashboard"]?.Calculations[0]?.[`DSS`].Calculations.slice(!nationalUser);
  const [selectedTab, setSelectedTab] = useState(calculations?.[0]);

  useEffect(() => {
    setSelectedTab(calculations?.[0]);
  }, [data]);

  const selectTab = (tabName) => {
    const tab = calculations?.filter((t) => t.tabName === tabName)?.[0];
    setSelectedTab(tab);
  };

  if (isLoading) {
    return <Loader />;
  }

  return (
    <Fragment>
      <Header styles={{ marginLeft: "0px", paddingTop: "10px", fontSize: "36px" }}>{t("DSS_CALCULATIONS")}</Header>
      <div>
        <div className="dss-switch-tabs chart-row">
          <div className="dss-switch-tab-wrapper" style={{ overflowX: "auto" }}>
            {calculations?.map((tabs) => (
              <div
                className={selectedTab?.tabName === tabs.tabName ? "dss-switch-tab-selected" : "dss-switch-tab-unselected"}
                onClick={() => selectTab(tabs.tabName)}
              >
                {t(tabs.tabName)}
              </div>
            ))}
          </div>
        </div>
        <Card className="margin-unset">
          {selectedTab?.hasOwnProperty("tabOverview") &&
            selectedTab?.tabOverview?.map((p) => (
              <div>
                {p.hasOwnProperty("para") ? (
                  <span>{t(p.para)}</span>
                ) : (
                  <span style={{ marginLeft: "20px", display: "flex" }}>
                    <span>{"•"}</span>
                    <span>{t(p.point)}</span>
                  </span>
                )}
              </div>
            ))}
        </Card>
        <div className="faq-list">{selectedTab && selectedTab?.cards.map((card, i) => <CalculationComponent card={card} index={i} />)}</div>
      </div>
    </Fragment>
  );
};

export default Calculations;
