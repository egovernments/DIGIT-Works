import React, { useState, useEffect, useMemo, createContext, Fragment } from "react";
import { useTranslation } from "react-i18next";
import { Header, Loader, ActionBar, SubmitBar, TextInput, Modal, CardText } from "@egovernments/digit-ui-react-components";
import { InboxSearchComposer } from "@egovernments/digit-ui-react-components";
import { useHistory } from "react-router-dom";
import searchSORConfig from "../../configs/searchSORConfig";
import {Toast,Button} from "@egovernments/digit-ui-components";

const Heading = (props) => {
  return <h1 className="heading-m">{props.t(props.heading)}</h1>;
};

const Close = () => (
  <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="#FFFFFF">
    <path d="M0 0h24v24H0V0z" fill="none" />
    <path d="M19 6.41L17.59 5 12 10.59 6.41 5 5 6.41 10.59 12 5 17.59 6.41 19 12 13.41 17.59 19 19 17.59 13.41 12 19 6.41z" />
  </svg>
);

const CloseBtn = (props) => {
  return (
    <div className="icon-bg-secondary" onClick={props.onClick} style={{ cursor: "pointer" }}>
      <Close />
    </div>
  );
};

const SearchSOR = () => {
  const { t } = useTranslation();
  const history = useHistory();
  const tenantId = Digit.ULBService.getCurrentTenantId();
  const [triggerApiCall, setTriggerApiCall] = useState(false);
  const [selectedSorIds, setSelectedSorIds] = useState(Digit.SessionStorage.get("RA_SELECTED_SORS") || {});
  const [showToast, setShowToast] = useState({ show: false, label: "", type:""});
  const [popup, setPopup] = useState(false);
  const [effectiveFromDate, setEffectiveFromDate] = useState("");
  const data = searchSORConfig?.SearchSORConfig?.[0];
  const [reviseAll, setReviseAll] = useState(false);
  let configs = data;

  const reqCriteria = {
    url: "/rate-analysis/v1/scheduler/_create",
    params: {
      tenantId: tenantId,
      limit: 10,
      offset: 0,
    },
    body: {
      apiOperation: "CREATE",
      Schedule: {
        tenantId: tenantId,
        effectiveFrom: effectiveFromDate,
        ...(reviseAll ? {} : selectedSorIds?.sorIds?.length > 0 && { sorIds: selectedSorIds?.sorIds }),
      },
    },
    config: {
      enabled: triggerApiCall,
    },
  };
  const { isLoading, data: reviseratesdata, revalidate } = Digit.Hooks.useCustomAPIHook(reqCriteria);

  const handleSchedulerCreate = () => {
    setTriggerApiCall(true);
    revalidate();
  };

  const handleDateChange = (e) => {
    const selectedDate = Digit.Utils.pt.convertDateToEpoch(e.target.value);
    setEffectiveFromDate(selectedDate);
  };

  const getNextDayDate = () => {
    const today = new Date();
    const nextDay = new Date(today);
    nextDay.setDate(today.getDate() + 1);
    return nextDay.toISOString().split("T")[0];
  };

  useEffect(() => {
    Digit.SessionStorage.set("RA_SELECTED_SORS", {});
  }, []);

  useEffect(() => {}, [triggerApiCall, effectiveFromDate, selectedSorIds]);

  useEffect(() => {
    if (reviseratesdata) {
      if (reviseratesdata.ResponseInfo.status === "successful") {
        const jobId = reviseratesdata.ScheduledJobs?.[0]?.jobId || "";
        setShowToast({ show: true, label: `Revision of rates is scheduled. JOB ID ${jobId}`, type:"" });
      } else {
        setShowToast({ show: true, label: "Failed to revise rates", type:"error" });
      }
    }
  }, [reviseratesdata]);

  const handleChange = () => {
    const newData = Digit.SessionStorage.get("RA_SELECTED_SORS") || {};
    if (JSON.stringify(newData) !== JSON.stringify(selectedSorIds)) {
      setSelectedSorIds(newData);
    }
  };

  useEffect(() => {
    window.addEventListener("session-storage-update", handleChange, false);
    return () => window.removeEventListener("session-storage-update", handleChange);
  }, [Digit.SessionStorage.get("RA_SELECTED_SORS")]);

  if (isLoading) {
    return <Loader />;
  }

  return (
    <React.Fragment>
      <div className="jk-header-btn-wrapper">
        <Header className="works-header-search">{t(configs?.label)}</Header>
        <Button
          label={t(configs?.actionLabel)}
          variation="secondary"
          onClick={() => {
            history.push(`/${window?.contextPath}/employee/${configs?.actionLink}`);
          }}
          type="button"
        />
      </div>
      <div className="inbox-search-wrapper">
        <InboxSearchComposer configs={configs}></InboxSearchComposer>
      </div>
      <ActionBar style={{ display: "flex", gap: "24px", justifyContent: "flex-end" }}>
        <SubmitBar
          label={t("RA_REVISE_RATE_FOR_SELECTED")}
          onSubmit={() => {
            setPopup(true);
            setReviseAll(false);
          }}
          disabled={!selectedSorIds.hasOwnProperty("sorIds") || selectedSorIds?.sorIds?.length <= 0 || selectedSorIds?.sorType !== "W"}
        />
        <SubmitBar
          label={t("RA_REVISE_RATE_FOR_ALL")}
          onSubmit={() => {
            setPopup(true);
            setReviseAll(true);
          }}
          disabled={selectedSorIds?.sorType !== "W"}
          //disabled={true}
        />
      </ActionBar>
      {showToast?.show && (
        <Toast
          // labelstyle={{ width: "100%" }}
          type={showToast?.type}
          label={t(showToast?.label)}
          isDleteBtn={true}
          onClose={() => setShowToast({ show: false, label: "", type: "" })}
        />
      )}
      {popup && (
        <Modal
          headerBarMain={
            <>
              <Heading t={t} heading={t("SELECT_DATE")} />
              <CardText className="popup-effective-date-description" style={{ marginLeft: "16px" }}>
                {t("RA_EFFECTIVE_DATE_DESCRIPTION")}
              </CardText>
            </>
          }
          headerBarMainStyle={{gap:"4px"}}
          headerBarEnd={<CloseBtn onClick={() => setPopup(false)} />}
          formId="modal-action"
          popupStyles={{ margin: "auto auto" }}
          style={{ width: "100%" }}
          hideSubmit={true}
        >
          <TextInput type="date" isRequired={true} onChange={(e) => handleDateChange(e)} min={getNextDayDate()} />
          <Button
            label={t("SUBMIT_EFFECTIVE_DATE")}
            variation="primary"
            onClick={() => {
              setPopup(false);
              handleSchedulerCreate();
            }}
            type="button"
            style={{ width: "100%" }}
          />
        </Modal>
      )}
    </React.Fragment>
  );
};

export default SearchSOR;
