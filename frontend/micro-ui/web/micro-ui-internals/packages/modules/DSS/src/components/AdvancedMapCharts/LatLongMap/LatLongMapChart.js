import React, { useContext, useEffect, useRef, useState } from "react";
import { useTranslation } from "react-i18next";
import { LatLngBounds } from "leaflet";
import { Loader } from "@egovernments/digit-ui-react-components";
import LatLongMap from "./LatLongMap";
import NoData from "../../NoData";
import GenericChart from "../../GenericChart";
import FilterContext from "../../FilterContext";

const LatLongMapChart = ({ data, chartName }) => {
  const { t } = useTranslation();
  const { value } = useContext(FilterContext);
  const chartId = data.charts.filter((c) => c.chartType === "points")?.[0].id;
  const subHeader = t(`SUB_${chartName}`);
  const mapData = useRef({});
  const pointProps = useRef({});
  const tableData = useRef({});
  const [locationKeyState, setLocationKeyState] = useState("");
  const [filterFeature, setFilterFeature] = useState("");
  const tenantId = Digit.ULBService.getCurrentTenantId();

  useEffect(() => {
    const province = value?.filters?.province;
    const district = value?.filters?.district;

    if (province) {
      if (district) {
        setFilterFeature(district.toLowerCase());
      }
      setLocationKeyState(province.toLowerCase());
    }
  }, [value, chartId]);

  const { isLoading } = Digit.Hooks.dss.useMDMS(Digit.ULBService.getStateId(), "map-config", [locationKeyState], {
    select: (data) => {
      const res = data?.["map-config"]?.[locationKeyState]?.[0];
      mapData.current = res || {};
    },
    enabled: locationKeyState.length > 0,
  });

  const toTitleCase = (str) => {
    if (str) {
      return str.charAt(0).toUpperCase() + str.slice(1);
    }
  };

  const addlFilter = locationKeyState?.length ? { locationKey: locationKeyState.toUpperCase() } : {};

  const generateTable = (chart, value) => {
    const { id, chartType } = chart;
    const tenantId = Digit.ULBService.getCurrentTenantId();
    const { isLoading, data: response } = Digit.Hooks.dss.useGetChart({
      key: id,
      type: chartType,
      tenantId,
      requestDate: { ...value?.requestDate, startDate: value?.range?.startDate?.getTime(), endDate: value?.range?.endDate?.getTime() },
      filters: value?.filters,
    });
    if (isLoading) {
      return <Loader />;
    }
    response?.responseData?.data?.forEach((d) => {
      let plotLabel = response?.responseData?.plotLabel;
      d?.plots?.forEach((p) => {
        let obj = {};
        let plotName = p.name.toLowerCase();
        obj[plotLabel] = p.name;
        obj[d.headerName] = p.value;
        if (tableData.current.hasOwnProperty(plotName)) {
          tableData.current[plotName] = { ...tableData.current[plotName], ...obj };
        } else {
          tableData.current[plotName] = obj;
        }
      });
    });
  };

  const generateMarkers = (chart, value, addlFilter, tenantId) => {
    const chartId = chart?.id;

    const { isLoading: isFetchingChart, data: response } = Digit.Hooks.dss.useGetChart({
      key: chartId,
      type: "table",
      tenantId,
      requestDate: { ...value?.requestDate, startDate: value?.range?.startDate?.getTime(), endDate: value?.range?.endDate?.getTime() },
      filters: {...addlFilter,...value?.filters}
    });
    const chartData = () => {
      let locationObject = {};
      response?.responseData?.data?.forEach((item) => {
        const value = item.plots?.filter((p) => p.symbol === "number" && p.name !== "latitude" && p.name !== "longitude")?.[0]?.value;
        locationObject[item.headerName?.toLowerCase()] = value;
      });
      return locationObject;
    };

    const markers = () => {
      let markersArray = [];
      let markersName = [];
      let showPoints = [];
      let boundaryNames = [];
      let tooltipString;
      mapData.current?.geoJSON?.features?.forEach((feature) => {
        const name = feature.properties?.name.toLowerCase();
        const bounds = new LatLngBounds(feature.geometry?.coordinates);
        const origin = bounds.getCenter();
        [origin.lng, origin.lat] = [origin.lat, origin.lng];
        markersArray.push({ name, origin });
        markersName.push(name);
        boundaryNames.push(name);
      });

      response?.responseData?.data?.forEach((d) => {
        let obj = {
          name: d.plots.filter((p) => p.name !== "latitude" && p.name !== "longitude" && p.label !== null)?.[0]?.label?.toLowerCase(),
          origin: {
            lat: d.plots.filter((p) => p.name === "latitude")?.[0]?.value,
            lng: d.plots.filter((p) => p.name === "longitude")?.[0]?.value,
          },
        };
        showPoints.push(obj.name);
        if (!markersName.includes(obj.name)) markersArray.push(obj);
        tooltipString =  d.plots.filter((p) => p.name !== "latitude" && p.name !== "longitude" && p.label !== null)?.[0].name
      });
      return { markersArray, showPoints, boundaryNames , tooltipString};
    };

    pointProps.current = { isFetchingChart, chartData: chartData(), markers: markers() };
  };

  data?.charts?.forEach((chart) => {
    chart?.chartType === "points" ? generateMarkers(chart, value, addlFilter, tenantId) : generateTable(chart, value);
  });
  const renderMap = () => {
    if (pointProps.current.isFetchingChart || isLoading) {
      return <Loader />;
    }

    const data = pointProps.current.chartData;
    if (Object.keys(mapData.current).length === 0 ) {
      return <NoData t={t} />;
    }

    return (
      <LatLongMap
        chartId={chartId}
        isFetchingChart={pointProps.current.isFetchingChart}
        isLoading={isLoading}
        mapData={mapData.current}
        chartData={pointProps.current.chartData}
        markers={pointProps.current.markers}
        setLocationKeyState={setLocationKeyState}
        filterFeature={filterFeature}
        tableData={tableData.current}
      />
    );
  };

  const Wrapper = () => {
    return (
      <GenericChart key={chartId} header={chartName} subHeader={subHeader}>
        <div>{renderMap()}</div>
      </GenericChart>
    );
  };

  return <Wrapper />;
};

export default LatLongMapChart;
