import {
  DownloadIcon,
  EmailIcon,
  FilterIcon,
  Header,
  Loader,
  MultiLink,
  RemoveableTag,
  ShareIcon,
  WhatsappIcon,
  DownloadImageIcon,
  DownloadPDFIcon,
} from "@egovernments/digit-ui-react-components";
import { format, differenceInDays } from "date-fns";
import React, { useEffect, Fragment, useMemo, useRef, useState } from "react";
import { useTranslation } from "react-i18next";
import { useParams, useLocation } from "react-router-dom";
import { checkCurrentScreen } from "../components/DSSCard";
import FilterContext from "../components/FilterContext";
import Filters from "../components/Filters";
import FiltersNational from "../components/FiltersNational";
import Layout from "../components/Layout";
import ProgressBar from "../components/ProgressBar";


const key = "DSS_FILTERS";

const getInitialRange = () => {
  const location = useLocation();
  const campaignData = Digit.SessionStorage.get("campaigns-info");
  const projectType = getProjectType(window.location.pathname);
  const province = new URLSearchParams(location.search).get("province");
  const data = Digit.SessionStorage.get(key);
  let startDate = data?.range?.startDate ? new Date(data?.range?.startDate) : Digit.Utils.dss.getDefaultFinacialYear().startDate;
  let endDate = data?.range?.endDate ? new Date(data?.range?.endDate) : Digit.Utils.dss.getDefaultFinacialYear().endDate;
  const title = `${format(startDate, "MMM d, yyyy")} - ${format(endDate, "MMM d, yyyy")}`;
  const interval = Digit.Utils.dss.getDuration(startDate, endDate);
  const denomination = data?.denomination || "Lac";
  const dateFilterSelected = "DSS_TODAY";
  const tenantId = data?.filters?.tenantId || [];
  const moduleLevel = data?.moduleLevel || "";

  let boundaries;
  if (campaignData && projectType) {
    let data = campaignData[projectType];
    if (data) {
      startDate = data?.startDate ? new Date(data?.startDate) : Digit.Utils.dss.getDefaultFinacialYear().startDate;
      endDate = data?.endDate ? new Date(data?.endDate) : Digit.Utils.dss.getDefaultFinacialYear().endDate;
      boundaries = data?.boundaries;
      return { startDate, endDate, title, interval, denomination, dateFilterSelected, tenantId, moduleLevel, boundaries };
    }
  }
  return { startDate, endDate, title, interval, denomination, dateFilterSelected, tenantId, moduleLevel };
};

const getProjectType = (currentUrl) => {
  let projectTypes = Digit.SessionStorage.get("projectTypes");
  if (!projectTypes) {
    return null;
  }
  let currentProjectType = projectTypes.find((projectType) =>Object.values(projectType?.dashboardUrls).includes(currentUrl))
  return currentProjectType?.code;
}
const DashBoard = ({ stateCode }) => {
  const tenantId = Digit.ULBService.getCurrentTenantId();
  const { t } = useTranslation();
  const [filters, setFilters] = useState(() => {
    const { startDate, endDate, title, interval, denomination, dateFilterSelected, tenantId, moduleLevel, boundaries } = getInitialRange();
    return {
      denomination,
      dateFilterSelected,
      range: { startDate, endDate:new Date(), title, interval },
      requestDate: {
        startDate: startDate.getTime(),
        endDate: Digit.Utils.dss.getDefaultFinacialYear().endDate.getTime(),
        interval: interval,
        title: title,
      },
      filters: {
        tenantId,
        campaignStartDate: startDate.getTime().toString(),
        campaignEndDate:endDate.getTime().toString(),
        province: boundaries?.province?.[0] || "",
        district: boundaries?.district?.[0] || ""
      },
      moduleLevel: moduleLevel
    };
  });
  const [isFilterModalOpen, setIsFilterModalOpen] = useState(false);
  const isNational = checkCurrentScreen();
  const { moduleCode } = useParams();

  const language = Digit.StoreData.getCurrentLanguage();

  const { isLoading: localizationLoading, data: store } = Digit.Services.useStore({ stateCode, moduleCode, language });
  const { data: screenConfig, isLoading: isServicesLoading } = Digit.Hooks.dss.useMDMS(stateCode, "dss-dashboard", "DssDashboard", {
    select: (data) => {
      let screenConfig = data?.["dss-dashboard"]["dashboard-config"][0].MODULE_LEVEL;
      let reduced_array = [];
      for(let i = 0 ; i < screenConfig.length ; i++){
        if(screenConfig[i].dashboard !== null ){
          reduced_array.push(screenConfig[i]);
        }
      }

      const serviceJS = reduced_array.map((obj, idx) => {
        return {
          code: obj[Object.keys(obj)[0]].filterKey,
          name: Digit.Utils.locale.getTransformedLocale(`DSS_${obj[Object.keys(obj)[0]].services_name}`)
        }
      }) ;
      return serviceJS
    }
  });
  const { data: nationalInfo, isLoadingNAT } = Digit.Hooks.dss.useMDMS(stateCode, "tenant", ["nationalInfo"], {
    select: (data) => {
      let nationalInfo = data?.tenant?.nationalInfo || [];
      let combinedResult = nationalInfo.reduce((acc, curr) => {
        if (acc[curr.stateCode]) {
          acc[curr.stateCode].push(curr);
        } else {
          acc[curr.stateCode] = [curr];
        }
        return { ...acc };
      }, {});
      let formattedResponse = { ddr: [], ulb: [] };
      Object.keys(combinedResult).map((key) => {
        let stateName = combinedResult[key]?.[0].stateName;
        formattedResponse.ddr.push({ code: key, ddrKey: stateName, ulbKey: stateName });
        formattedResponse.ulb.push(...combinedResult[key].map((e) => ({ code: e.code, ulbKey: e.name, ddrKey: e.stateName })));
      });
      return formattedResponse;
    },
    enabled: isNational,
  });

  const { data: response, isLoading } = Digit.Hooks.dss.useDashboardConfig(moduleCode);
  const { data: ulbTenants, isLoading: isUlbLoading } = Digit.Hooks.useModuleTenants("DSS");
  // const { isLoading: isMdmsLoading, data: mdmsData } = Digit.Hooks.useCommonMDMS(stateCode, "FSM", "FSTPPlantInfo");
  const [showShareOptions, setShowShareOptions] = useState(false);
  const [showDownloadOptions, setShowDownloadOptions] = useState(false);
  const [showFilters, setShowFilters] = useState(false);
  const [tabState, setTabState] = useState("");
  const { search } = useLocation();
  const [showProgressBar, setShowProgressBar] = useState(false);

  const [progressDuration, setProgressDuration] = useState({ campaignDuration: 0, daysElapsed: 0 });
  const campaignInfo = Digit.SessionStorage.get("campaigns-info");
  const projectType = getProjectType(window.location.pathname)

  const handleFilters = (data) => {
    const userInfo = Digit.UserService.getUser()?.info;
    const province = new URLSearchParams(search).get("province");
    const eligibleRolesForFilter = {
      NATIONAL_SUPERVISOR: true,
      PROVINCIAL_SUPERVISOR: true,
    };

    const userRoles = userInfo?.roles?.filter((role) => eligibleRolesForFilter[role.code]);
    let updatedData = data;
    if (userRoles?.length) {
      let updatedFilters = {
        ...data.filters,
      };

      if (province && campaignInfo[projectType]?.boundaries?.province?.includes(province)) {
        updatedFilters = {
          ...updatedFilters,
          province,
        };
      }

      updatedData = {
        ...data,
        filters: updatedFilters,
      };
    }

    Digit.SessionStorage.set(key, updatedData);
    setFilters(updatedData);
    if (campaignInfo) {
      setShowProgressBar(true);
      setProgressDuration(getValuesForProgressBar);
    }
  };
  const fullPageRef = useRef();
  const provided = useMemo(
    () => ({
      value: filters,
      setValue: handleFilters,
      ulbTenants: isNational ? nationalInfo : ulbTenants,
      // fstpMdmsData: mdmsData,
      screenConfig: screenConfig,
    }),
    [filters, isUlbLoading, isServicesLoading]
  );

  const mobileView = window.Digit.Utils.browser.isMobile();

  const handlePrint = () => Digit.Download.PDF(fullPageRef, t(dashboardConfig?.[0]?.name));

  const removeULB = (id) => {
    handleFilters({
      ...filters,
      filters: { ...filters?.filters, tenantId: [...filters?.filters?.tenantId].filter((tenant, index) => index !== id) },
    });
  };
  const removeST = (id) => {
    let newStates = [...filters?.filters?.state].filter((tenant, index) => index !== id);
    let newUlbs = filters?.filters?.ulb || [];
    if (newStates?.length == 0) {
      newUlbs = [];
    } else {
      let filteredUlbs = nationalInfo?.ulb?.filter((e) => Digit.Utils.dss.getCitiesAvailable(e, newStates))?.map((ulbs) => ulbs?.code);
      newUlbs = newUlbs.filter((ulb) => filteredUlbs.includes(ulb));
    }
    handleFilters({
      ...filters,
      filters: { ...filters?.filters, state: newStates, ulb: newUlbs },
    });
  };

  const removeService = () => {
    handleFilters({
      ...filters,
      moduleLevel: "" ,
    });
  }

  const removeTenant = (id) => {
    handleFilters({
      ...filters,
      filters: { ...filters?.filters, ulb: [...filters?.filters?.ulb].filter((tenant, index) => index !== id) },
    });
  };

  const handleClear = () => {
    handleFilters({ ...filters, filters: { ...filters?.filters, tenantId: [] } });
  };

  const clearAllTn = () => {
    handleFilters({ ...filters, filters: { ...filters?.filters, ulb: [] } });
  };
  const clearAllSt = () => {
    handleFilters({ ...filters, filters: { ...filters?.filters, state: [], ulb: [] } });
  };
  const clearAllServices = () => {
    handleFilters({ ...filters, moduleLevel: "" });
  }

  const dashboardConfig = response?.responseData;
  const hideFilterFields = dashboardConfig?.[0]?.hideFilterFields || [];
  let tabArrayObj =
    dashboardConfig?.[0]?.visualizations?.reduce((curr, acc) => {
      curr[acc.name] = 0;
      return { ...curr };
    }, {}) || {};
  let tabArray = Object.keys(tabArrayObj).map((key) => key);

  useEffect(() => {
    if (tabArray?.length > 0 && tabState == "") {
      setTabState(tabArray[0]);
    }
  }, [tabArray]);


  const getValuesForProgressBar = () => {

    const campaignStartDate = new Date(Number(JSON.parse(window.sessionStorage.getItem("Digit.DSS_FILTERS"))?.value?.filters?.campaignStartDate));
    const campaignEndDate = new Date(Number(JSON.parse(window.sessionStorage.getItem("Digit.DSS_FILTERS"))?.value?.filters?.campaignEndDate));
    const campaignDuration = differenceInDays(campaignEndDate, campaignStartDate);
    const daysElapsed = differenceInDays(new Date().getTime() <= campaignEndDate.getTime() ? new Date() : campaignEndDate, campaignStartDate);
    return ({ campaignDuration: campaignDuration, daysElapsed: daysElapsed });

  }

  const shareOptions =
    navigator.share
      ? [
          {
            label: t("ES_DSS_SHARE_PDF"),
            onClick: (e) => {
              setShowShareOptions(!showShareOptions);
              setTimeout(() => {
                return Digit.ShareFiles.DownloadImage(tenantId, fullPageRef, t(dashboardConfig?.[0]?.name));
              }, 500);
            },
          },
          {
            label: t("ES_DSS_SHARE_IMAGE"),
            onClick: () => {
              setShowShareOptions(!showShareOptions);
              setTimeout(() => {
                return Digit.ShareFiles.DownloadImage(tenantId, fullPageRef, t(dashboardConfig?.[0]?.name));
              }, 500);
            },
          },
        ]
      :
    [
      {
        icon: <EmailIcon />,
        label: t("ES_DSS_SHARE_PDF"),
        onClick: () => {
          setShowShareOptions(!showShareOptions);
          setTimeout(() => {
            return Digit.ShareFiles.PDF(tenantId, fullPageRef, t(dashboardConfig?.[0]?.name), "mail");
          }, 500);
        },
      },
      {
        icon: <WhatsappIcon />,
        label: t("ES_DSS_SHARE_PDF"),
        onClick: () => {
          setShowShareOptions(!showShareOptions);
          setTimeout(() => {
            return Digit.ShareFiles.PDF(tenantId, fullPageRef, t(dashboardConfig?.[0]?.name), "whatsapp");
          }, 500);
        },
      },
      {
        icon: <EmailIcon />,
        label: t("ES_DSS_SHARE_IMAGE"),
        onClick: () => {
          setShowShareOptions(!showShareOptions);
          setTimeout(() => {
            return Digit.ShareFiles.DownloadImage(tenantId, fullPageRef, t(dashboardConfig?.[0]?.name), "mail");
          }, 500);
        },
      },
      {
        icon: <WhatsappIcon />,
        label: t("ES_DSS_SHARE_IMAGE"),
        onClick: () => {
          setShowShareOptions(!showShareOptions);
          setTimeout(() => {
            return Digit.ShareFiles.DownloadImage(tenantId, fullPageRef, t(dashboardConfig?.[0]?.name), "whatsapp");
          }, 500);
        },
      },
    ];

  const downloadOptions = [
    {
      icon: <DownloadImageIcon />,
      label: t("ES_DSS_DOWNLOAD_IMAGE"),
      onClick: () => {
        setShowDownloadOptions(!showDownloadOptions);
        setTimeout(() => {
          return Digit.Download.Image(fullPageRef, t(dashboardConfig?.[0]?.name));
        }, 500);
      },
    },
    {
      icon: <DownloadPDFIcon />,
      label: t("ES_DSS_DOWNLOAD_PDF"),
      onClick: () => {
        setShowDownloadOptions(!showDownloadOptions);
        setTimeout(() => {
          return Digit.Download.PDF(fullPageRef, t(dashboardConfig?.[0]?.name));
        }, 500);
      },
    },
  ];

  if (isLoading || isUlbLoading || localizationLoading || isLoadingNAT || isServicesLoading) {
    return <Loader />;
  }

  const optionStyle = {
    paddingLeft: "5px",
    paddingRight: "5px",
  };

  const boundaryName = filters.filters.district != "" ?  filters.filters.district : filters.filters.province;

  return (
    <FilterContext.Provider value={provided}>
      <div ref={fullPageRef} id="divToPrint">
        <div className="options">
          <Header styles={mobileView ? { marginLeft: "0px", whiteSpace: "pre-line" } : { marginBottom: "0px", whiteSpace: "pre" }}>
            {t(dashboardConfig?.[0]?.name)} {boundaryName && <span style={{fontSize : "24px"}}>({t(`DSS_HEALTH_LOCATION_${boundaryName}`)})</span>}
          </Header>
          {mobileView ? null : (
            <div className="divToBeHidden">
              <div className="mrlg divToBeHidden" style={optionStyle}>
                <MultiLink
                  className="multilink-block-wrapper divToBeHidden"
                  label={t(`ES_DSS_SHARE`)}
                  icon={<ShareIcon className="mrsm" />}
                  showOptions={(e) => {
                  setShowShareOptions(e)}
                  }
                  onHeadClick={(e) => {
                    setShowShareOptions(e !== undefined ? e : !showShareOptions);
                  }}
                  displayOptions={showShareOptions}
                  options={shareOptions}
                />
              </div>
              <div className="mrsm divToBeHidden" style={optionStyle}>
                <MultiLink
                    className="multilink-block-wrapper"
                    label={t(`ES_DSS_DOWNLOAD`)}
                    icon={<DownloadIcon className="mrsm" />}
                    showOptions={(e) => setShowDownloadOptions(e)}
                    onHeadClick={(e) => setShowDownloadOptions(e !== undefined ? e : !showDownloadOptions)}
                    displayOptions={showDownloadOptions}
                    options={downloadOptions}
                />
              </div>
            </div>
          )}
        </div>
        {isNational ? (
          <FiltersNational
            t={t}
            ulbTenants={nationalInfo}
            isOpen={isFilterModalOpen}
            closeFilters={() => setIsFilterModalOpen(false)}
            isNational={isNational}
          />
        ) : (
          <Filters
            t={t}
            showModuleFilter={(!isNational && dashboardConfig?.[0]?.name.includes("OVERVIEW") ? true : false) &&
            !hideFilterFields.includes("ModuleFilter")
            }
            services={screenConfig}
            ulbTenants={isNational ? nationalInfo : ulbTenants}
            isOpen={isFilterModalOpen}
            closeFilters={() => setIsFilterModalOpen(false)}
            isNational={isNational}
            showDateRange= {(dashboardConfig?.[0]?.name.includes("DSS_FINANCE_DASHBOARD") ? false : true) && 
              !hideFilterFields.includes("DateRange")
            }
            showDDR={!hideFilterFields.includes("DDR")}
            showUlb={!hideFilterFields.includes("Ulb")}
            showDenomination={!hideFilterFields.includes("Denomination")}
          />
        )}
        {filters?.filters?.tenantId?.length > 0 && (
          <div className="tag-container">
            {!showFilters &&
              filters?.filters?.tenantId &&
              filters.filters.tenantId
                .slice(0, 5)
                .map((filter, id) => <RemoveableTag key={id} text={`${t(`DSS_HEADER_ULB`)}: ${t(filter)}`} onClick={() => removeULB(id)} />)}
            {filters?.filters?.tenantId?.length > 6 && (
              <>
                {showFilters &&
                  filters.filters.tenantId.map((filter, id) => (
                    <RemoveableTag key={id} text={`${t(`DSS_HEADER_ULB`)}: ${t(filter)}`} onClick={() => removeULB(id)} />
                  ))}
                {!showFilters && (
                  <p className="clearText cursorPointer" onClick={() => setShowFilters(true)}>
                    {t(`DSS_FILTER_SHOWALL`)}
                  </p>
                )}
                {showFilters && (
                  <p className="clearText cursorPointer" onClick={() => setShowFilters(false)}>
                    {t(`DSS_FILTER_SHOWLESS`)}
                  </p>
                )}
              </>
            )}
            <p className="clearText cursorPointer" onClick={handleClear}>
              {t(`DSS_FILTER_CLEAR`)}
            </p>
          </div>
        )}
        {filters?.filters?.state?.length > 0 && (
          <div className="tag-container">
            {!showFilters &&
              filters?.filters?.state &&
              filters.filters.state
                .slice(0, 5)
                .map((filter, id) => (
                  <RemoveableTag
                    key={id}
                    text={`${t(`DSS_HEADER_STATE`)}: ${t(`DSS_TB_${Digit.Utils.locale.getTransformedLocale(filter)}`)}`}
                    onClick={() => removeST(id)}
                  />
                ))}
            {filters?.filters?.state?.length > 6 && (
              <>
                {showFilters &&
                  filters.filters.state.map((filter, id) => (
                    <RemoveableTag
                      key={id}
                      text={`${t(`DSS_HEADER_STATE`)}: ${t(`DSS_TB_${Digit.Utils.locale.getTransformedLocale(filter)}`)}`}
                      onClick={() => removeST(id)}
                    />
                  ))}
                {!showFilters && (
                  <p className="clearText cursorPointer" onClick={() => setShowFilters(true)}>
                    {t(`DSS_FILTER_SHOWALL`)}
                  </p>
                )}
                {showFilters && (
                  <p className="clearText cursorPointer" onClick={() => setShowFilters(false)}>
                    {t(`DSS_FILTER_SHOWLESS`)}
                  </p>
                )}
              </>
            )}
            <p className="clearText cursorPointer" onClick={clearAllSt}>
              {t(`DSS_FILTER_CLEAR_ST`)}
            </p>
          </div>
        )}
        {filters?.filters?.ulb?.length > 0 && (
          <div className="tag-container">
            {!showFilters &&
              filters?.filters?.ulb &&
              filters.filters.ulb
                .slice(0, 5)
                .map((filter, id) => (
                  <RemoveableTag
                    key={id}
                    text={`${t(`DSS_HEADER_ULB`)}: ${t(`DSS_TB_${Digit.Utils.locale.getTransformedLocale(filter)}`)}`}
                    onClick={() => removeTenant(id)}
                  />
                ))}
            {filters?.filters?.ulb?.length > 6 && (
              <>
                {showFilters &&
                  filters.filters.ulb.map((filter, id) => (
                    <RemoveableTag
                      key={id}
                      text={`${t(`DSS_HEADER_ULB`)}: ${t(`DSS_TB_${Digit.Utils.locale.getTransformedLocale(filter)}`)}`}
                      onClick={() => removeTenant(id)}
                    />
                  ))}
                {!showFilters && (
                  <p className="clearText cursorPointer" onClick={() => setShowFilters(true)}>
                    {t(`DSS_FILTER_SHOWALL`)}
                  </p>
                )}
                {showFilters && (
                  <p className="clearText cursorPointer" onClick={() => setShowFilters(false)}>
                    {t(`DSS_FILTER_SHOWLESS`)}
                  </p>
                )}
              </>
            )}
            <p className="clearText cursorPointer" onClick={clearAllTn}>
              {t(`DSS_FILTER_CLEAR_TN`)}
            </p>
          </div>
        )}
        {filters?.moduleLevel?.length > 0 && (
          <div className="tag-container">
            {!showFilters &&
              filters?.moduleLevel &&
              (<RemoveableTag key={filters?.moduleLevel} text={`${t(`DSS_HEADER_SERVICE`)}: ${t(filters?.moduleLevel)}`} onClick={() => removeService()} />)}
            <p className="clearText cursorPointer" onClick={clearAllServices}>
              {t(`DSS_FILTER_CLEAR`)}
            </p>
          </div>
        )}

        {mobileView ? (
          <div className="options-m">
            <div>
              <FilterIcon onClick={() => setIsFilterModalOpen(!isFilterModalOpen)} style />
            </div>
            <div className="divToBeHidden">
              <MultiLink
                className="multilink-block-wrapper"
                label={t(`ES_DSS_SHARE`)}
                icon={<ShareIcon className="mrsm" />}
                showOptions={(e) => setShowShareOptions(e)}
                onHeadClick={(e) => {
                  setShowShareOptions(e !== undefined ? e : !showShareOptions);
                }}
                displayOptions={showShareOptions}
                options={shareOptions}
              />
            </div>
            <div onClick={handlePrint} className="divToBeHidden">
              <DownloadIcon />
              {t(`ES_DSS_DOWNLOAD`)}
            </div>
          </div>
        ) : null}
        <div>
          {tabArray && tabArray?.length > 1 && (
            <div className="dss-switch-tabs chart-row">
              <div className="dss-switch-tab-wrapper" style={{ overflowX: "auto" }}>
                {tabArray?.map((key) => (
                  <div className={tabState === key ? "dss-switch-tab-selected" : "dss-switch-tab-unselected"} onClick={() => setTabState(key)}>
                    {t(key)}
                  </div>
                ))}
              </div>
              {showProgressBar && (<ProgressBar style={{ overflowX: "hidden" }} className="dss-switch-tab-wrapper" bgcolor="#00703C" total={progressDuration.campaignDuration} completed={progressDuration.daysElapsed} />)}
            </div>
          )}
        </div>
        {dashboardConfig?.[0]?.visualizations
          .filter((row) => row.name === tabState)
          .map((row, key) => {
            return <Layout rowData={row} key={key} />;
          })}
      </div>
    </FilterContext.Provider>
  );
};

export default DashBoard;
