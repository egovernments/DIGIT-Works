import React, { useEffect, useRef } from "react";
import { useTranslation } from "react-i18next";
import SubmitBar from "./SubmitBar";
import ActionBar from "./ActionBar";
import Menu from "./Menu";



const WorkflowActions = ({ businessService, tenantId, forcedActionPrefix, ActionBarStyle = {}, MenuStyle = {}, saveAttendanceState,displayMenu,setDisplayMenu,onActionSelect }) => {
  const { applicationNo } = Digit.Hooks.useQueryParams();
  const { t } = useTranslation();
  let user = Digit.UserService.getUser();

  let workflowDetails = Digit.Hooks.useWorkflowDetailsWorks(
    {
      tenantId: tenantId,
      id: applicationNo,
      moduleCode: businessService,
      config: {
        enabled: true,
        cacheTime: 0
      }
    }
  );

  const menuRef = useRef();

  const userRoles = user?.info?.roles?.map((e) => e.code);
  let isSingleButton = false;
  let isMenuBotton = false;
  let actions = workflowDetails?.data?.actionState?.nextActions?.filter((e) => {
    return userRoles.some((role) => e.roles?.includes(role)) || !e.roles;
  }) || workflowDetails?.data?.nextActions?.filter((e) => {
    return userRoles.some((role) => e.roles?.includes(role)) || !e.roles;
  });

  const closeMenu = () => {
    setDisplayMenu(false);
  }
  Digit.Hooks.useClickOutside(menuRef, closeMenu, displayMenu);

  if (actions?.length > 0) {
    isMenuBotton = true;
    isSingleButton = false;
  }

  if (saveAttendanceState?.displaySave) {
    isMenuBotton = false;
    isSingleButton = true;
    actions = [
      {
        action: "SAVE",
        state: "UPDATED"
      }
    ]
  }


  return (
    <React.Fragment>
      {!workflowDetails?.isLoading && isMenuBotton && !isSingleButton && (
        <ActionBar style={{ ...ActionBarStyle }}>
          {displayMenu && (workflowDetails?.data?.actionState?.nextActions || workflowDetails?.data?.nextActions) ? (
            <Menu
              localeKeyPrefix={forcedActionPrefix || `WORKS_${businessService?.toUpperCase()}`}
              options={actions}
              optionKey={"action"}
              t={t}
              onSelect={onActionSelect}
              style={MenuStyle}
            />
          ) : null}
          <SubmitBar ref={menuRef} label={t("WORKS_ACTIONS")} onSubmit={() => setDisplayMenu(!displayMenu)} />
        </ActionBar>
      )}
      {!workflowDetails?.isLoading && !isMenuBotton && isSingleButton && (
        <ActionBar style={{ ...ActionBarStyle }}>
          <button
            style={{ color: "#FFFFFF", fontSize: "18px" }}
            className={"submit-bar"}
            name={actions?.[0]?.action}
            value={actions?.[0]?.action}
            onClick={(e) => { onActionSelect(actions?.[0] || {}) }}>
            {t(`${forcedActionPrefix || `WF_EMPLOYEE_${businessService?.toUpperCase()}`}_${actions?.[0]?.action}`)}
          </button>
        </ActionBar>
      )}
    </React.Fragment>
  );
}

export default WorkflowActions