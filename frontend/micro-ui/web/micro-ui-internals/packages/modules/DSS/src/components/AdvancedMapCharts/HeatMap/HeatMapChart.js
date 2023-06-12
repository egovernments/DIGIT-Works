import React, { useCallback, useContext, useEffect, useMemo, useRef, useState } from "react";
import { useTranslation } from "react-i18next";
import { LatLngBounds } from "leaflet";
import { Loader, RemoveableTag } from "@egovernments/digit-ui-react-components";
import { subDays, addMinutes } from "date-fns";
import Map from "./Map";
import NoData from "../../NoData";
import GenericChart from "../../GenericChart";
import FilterContext from "../../FilterContext";

export default function HeatMapChart({ chartId, visualizer, initialRange, isNational, showLabel }) {
  const { t } = useTranslation();
  const { value } = useContext(FilterContext);
  const subHeader = t(`SUB_${visualizer?.name}`);

  const mapData = useRef({});
  const [locationKeyState, setLocationKeyState] = useState("");
  const [filterFeature, setFilterFeature] = useState("");
  const [drillDownChart, setDrillDownChart] = useState("none");
  const [chartKey, setChartKey] = useState(chartId);
  const [drillDownStack, setDrillDownStack] = useState([{ id: chartId, label: isNational ? "national-map" : locationKeyState }]);

  useEffect(() => {
    setChartKey(chartId);
    const province = value?.filters?.province;
    const district = value?.filters?.district;

    if (province) {
      if (district) {
        setFilterFeature(district.toLowerCase());
      }
      setLocationKeyState(province.toLowerCase());
    }

    return () => {
      setChartKey("");
    };
  }, [value, isNational, chartId]);

  useEffect(() => {
    if (drillDownStack?.length === 1) {
      setChartKey(drillDownStack[0].id);
      setDrillDownChart("none");
    }
  }, [drillDownStack]);

  //TODO: Replace this with the values from the campaign interval
  const getInitialRange = () => {
    if(initialRange) {
      return initialRange;
    }

    const data = Digit.SessionStorage.get("DSS_FILTERS");
    const startDate = data?.range?.startDate ? new Date(data?.range?.startDate) : Digit.Utils.dss.getDefaultFinacialYear().startDate;
    const endDate = data?.range?.endDate ? new Date(data?.range?.endDate) : Digit.Utils.dss.getDefaultFinacialYear().endDate;
    const interval = Digit.Utils.dss.getDuration(startDate, endDate);
    return { startDate, endDate, interval };
  };

  const tenantId = Digit.ULBService.getCurrentTenantId();
  const { startDate, endDate, interval } = getInitialRange();
  const requestDate = {
    startDate: startDate.getTime(),
    endDate: endDate.getTime(),
    interval: interval,
    title: "home",
  };

  let moduleSelector = "national-map";
  if (isNational) {
    if (locationKeyState) {
      moduleSelector = locationKeyState;
    }
  } else {
    moduleSelector = locationKeyState;
  }
  const { isLoading } = Digit.Hooks.dss.useMDMS(Digit.ULBService.getStateId(), "map-config", [moduleSelector], {
    select: (data) => {
      const res = data?.["map-config"]?.[moduleSelector]?.[0];
      mapData.current = res || {};
    },
    enabled: moduleSelector.length > 0,
  });

  const toTitleCase = (str) => {
    if (str) {
      return str.charAt(0).toUpperCase() + str.slice(1);
    }
  };

  const addlFilter = locationKeyState?.length ? { locationKey: locationKeyState.toUpperCase() } : {};
  const { isLoading: isFetchingChart, data: response } = Digit.Hooks.dss.useGetChart({
    key: chartKey,
    type: "table",
    tenantId,
    requestDate: requestDate,
    addlFilter,
  });

  // const date = new Date();
  // const currentDate = new Date(date.getFullYear(), date.getMonth(), date.getDate());
  // const startTime = new Date(currentDate.getFullYear(), currentDate.getMonth(), currentDate.getDate()).getTime();
  // const endTime = new Date(currentDate.getFullYear(), currentDate.getMonth(), currentDate.getDate(), 23, 59, 0).getTime();

  // const { data: todaysResponse } = Digit.Hooks.dss.useGetChart({
  //   key: chartKey,
  //   type: "table",
  //   tenantId,
  //   requestDate: {
  //     startDate: startTime,
  //     endDate: endTime,
  //     interval: "day",
  //     title: "home",
  //   },
  //   addlFilter,
  // });

  // const previousDay = subDays(currentDate, 1);
  // const { data: pastDaysResponse } = Digit.Hooks.dss.useGetChart({
  //   key: chartKey,
  //   type: "table",
  //   tenantId,
  //   requestDate: {
  //     startDate: previousDay.getTime(),
  //     endDate: addMinutes(previousDay, 1439).getTime(),
  //     interval: "day",
  //     title: "home",
  //   },
  //   addlFilter,
  // });

  // const insightsResults = useMemo(() => {
  //   if (!todaysResponse || !pastDaysResponse) return;

  //   const responseList = [todaysResponse, pastDaysResponse];
  //   let todaysData = {};
  //   let previousDaysData = {};

  //   responseList.forEach((response, idx) => {
  //     response?.responseData?.data?.forEach((item) => {
  //       const key = item.headerName;
  //       const value = item.plots?.filter((p) => p.label === null && p.name === "total_count")?.[0]?.value;

  //       if (idx === 0) {
  //         todaysData[key] = value;
  //       } else {
  //         previousDaysData[key] = value;
  //       }
  //     });
  //   });

  //   if (todaysData !== {} && previousDaysData !== {}) {
  //     let mergedKeys = Object.keys(todaysData).concat(Object.keys(previousDaysData));
  //     mergedKeys = Array.from(new Set(mergedKeys));
  //     const results = {};

  //     mergedKeys.forEach((key) => {
  //       const currentData = todaysData[key] || 0;
  //       const pastData = previousDaysData[key] || 0;
  //       const diff = currentData - pastData;
  //       const formattedKey = key.toLowerCase();

  //       if (diff > 0) {
  //         const insightValue = pastData === 0 ? null : (diff / pastData) * 100;
  //         if (insightValue) {
  //           results[formattedKey] = {
  //             indicator: "positive",
  //             insightValue,
  //           };
  //         }
  //       } else if (diff === 0) {
  //         results[formattedKey] = {
  //           indicator: "no_diff",
  //           insightValue: 0,
  //         };
  //       } else {
  //         const diff = pastData - currentData;
  //         const insightValue = pastData === 0 ? null : (diff / pastData) * 100;
  //         if (insightValue) {
  //           results[formattedKey] = {
  //             indicator: "negative",
  //             insightValue,
  //           };
  //         }
  //       }
  //     });

  //     return results;
  //   }
  // }, [todaysResponse, pastDaysResponse]);

  useEffect(() => {
    setDrillDownChart(response?.responseData?.drillDownChartId || "none");
  }, [response]);

  const chartData = useCallback(() => {
    let locationObject = {};
     
    response?.responseData?.data?.forEach((item) => {
      const value = item.plots?.filter((p) => p.symbol === "percentage")?.[0]?.value;
      locationObject[item.headerName?.toLowerCase()] = value;
    });
    
    return locationObject;
  }, [response]);

  const markers = useCallback(() => {
    return mapData.current?.geoJSON?.features?.map((feature) => {
      const name = feature.properties?.name.toLowerCase();

      const bounds = new LatLngBounds(feature.geometry?.coordinates);
      const origin = bounds.getCenter();

      return { name, origin };
    });
  }, [mapData]);

  const GradientScale = () => {
    const range = (value) => {
      return <div style={{ width: "5%", textAlign: "center" }}>{`${value}%`}</div>;
    };

    if (isLoading || isFetchingChart) return null;

    return (
      <div
        style={{
          display: "flex",
          justifyContent: "center",
          marginLeft: "auto",
          marginRight: "auto",
          marginTop: "20px",
          alignItems: "center",
          width: "100%",
        }}
      >
        {range("0")}
        <div
          style={{
            height: "14px",
            background: "linear-gradient(270deg, #01D66F 0%, #FFC42E 46.03%, #FF7373 101.5%)",
            width: "90%",
            marginLeft: "10px",
            marginRight: "10px",
          }}
        ></div>
        {range("100")}
      </div>
    );
  };

  const RemovableFilters = () => {
    return (
      <React.Fragment>
        {drillDownStack?.length > 1 && (
          <div className="tag-container">
            <span style={{ marginTop: "20px" }}>{t("DSS_FILTERS_APPLIED")}: </span>
            {drillDownStack.map((filter, id) =>
              id > 0 ? (
                <RemoveableTag
                  key={id}
                  text={filter.label && toTitleCase(filter.label)}
                  onClick={() => {
                    const filtered = drillDownStack.filter((d) => d.id !== filter.id);
                    setDrillDownStack(filtered);

                    if (filtered.length === 0) return;

                    const currentChart = filtered[filtered.length - 1];
                    if (filtered?.length === 1) {
                      setLocationKeyState(currentChart.label);
                      setChartKey(currentChart.id);
                      setDrillDownChart("none");
                      return;
                    }

                    setLocationKeyState(currentChart.label);
                    setChartKey(currentChart.id);
                  }}
                />
              ) : null
            )}
          </div>
        )}
      </React.Fragment>
    );
  };

  const renderMap = () => {
    if (isFetchingChart || isLoading) {
      return <Loader />;
    }

    const data = chartData();

    if (Object.keys(mapData.current).length === 0 || Object.keys(data).length === 0) {
     
    return <NoData t={t} />;
    }

    return (
      <React.Fragment>
        <RemovableFilters />
        <Map
          chartId={chartId}
          isFetchingChart={isFetchingChart}
          isLoading={isLoading}
          mapData={mapData.current}
          chartData={data}
          markers={markers()}
          drillDownChart={drillDownChart}
          setLocationKeyState={setLocationKeyState}
          setDrillDownStack={setDrillDownStack}
          setChartKey={setChartKey}
          // insightsResults={insightsResults}
          showLabel={showLabel}
          filterFeature={filterFeature}
        />
        <GradientScale />
      </React.Fragment>
    );
  };

  const Wrapper = () => {
    if (isNational) {
      return (
        <GenericChart
          key={chartId}
          header={visualizer?.name}
          subHeader={subHeader !== `SUB_${visualizer?.name}` ? subHeader : ""}
          className={"dss-card-parent heatMap"}
        >
          <div>{renderMap()}</div>
        </GenericChart>
      );
    }

    return <div>{renderMap()}</div>;
  };

  return <Wrapper />;
}