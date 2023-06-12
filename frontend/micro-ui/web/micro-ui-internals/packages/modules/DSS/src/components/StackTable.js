import React, { useEffect, useState } from "react";
import { useTranslation } from "react-i18next";
import GenericChart from "./GenericChart";

export default function StackedTable({ chartId, visualizer, initialRange, isNational, routeTo, redirectUrl }) {
  const { t } = useTranslation();
  const subHeader = t(`SUB_${visualizer?.name}`);
  const [chartData, setChartData] = useState([]);
  const [searchQuery, setSearchQuery] = useState("");

  const getInitialRange = () => {
    if (initialRange) {
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
  const { isLoading: isFetchingChart, data: response } = Digit.Hooks.dss.useGetChart({
    key: chartId,
    type: "table",
    tenantId,
    requestDate: requestDate,
  });
  const AbsoluteCell = (absValue, absText) => {
    return (
      <div className="stacked-abs-cell">
        <div className="stacked-abs-value">{absValue}</div>
        <div className="stacked-cell-sub-text">{t(`DSS_${Digit.Utils.locale.getTransformedLocale(absText)}`)}</div>
      </div>
    );
  };

  const PercentageCell = (percentageValue, percentageText, index) => {
    const color = ["#f92323", "#f1f438", "#F47738", "#0babde", "#3b38f4", "#d538f4", "#3ef438"];
    const formatter = new Intl.NumberFormat("en-IN", { maximumSignificantDigits: 3 });
    const value = `${formatter.format(percentageValue?.toFixed(2))}%`;
    const progressWidth = percentageValue > 100 ? 100.0 : percentageValue;
    return (
      <div className="stacked-percentage-cell">
        <div className="stacked-progress-cell">
          <div className="stacked-percentage-value" style={{marginRight:25}}>{value}</div>
          <div className="stacked-percentage-bar-grey">
            <div
              className="stacked-percentage-bar-progress"
              style={{ width: `${progressWidth}%`, backgroundColor: `${color[index % color.length]}` }}
            ></div>
          </div>
        </div>
        <div className="stacked-cell-sub-text">{t(`DSS_${Digit.Utils.locale.getTransformedLocale(percentageText)}`)}</div>
      </div>
    );
  };

  const ViewButton = ({ isDisabled, name }) => {
    let color = isDisabled ? "#B1B4B6" : "#F47738";

    return (
      <button
        className="stacked-row-button"
        style={{
          border: `1px solid ${color}`,
          color: `${color}`,
        }}
        onClick={() => {
          routeTo(`/digit-ui/employee/dss/dashboard/${redirectUrl}?province=${name}`);
        }}
      >
        {t("DSS_VIEW_DASHBOARD")}
      </button>
    );
  };

  const StackedRowV2 = (rowData) => {
    const values = rowData?.values;
    const valueKeys = Object.keys(values);

    return (
      <div className="stacked-row">
        <div className="stacked-row-index">{rowData?.name}</div>
        {valueKeys?.map((key, index) => {
          const valueItem = values[key];

          return (
            <React.Fragment>
              {valueItem?.symbol === "percentage" ? PercentageCell(valueItem?.value, key, index) : AbsoluteCell(valueItem?.value, key)}
            </React.Fragment>
          );
        })}
        <ViewButton isDisabled={rowData?.shouldDisableRow} name={rowData?.name} />
      </div>
    );
  };

  useEffect(() => {
    let disabledData = [];
    let enabledData = [];
    let plotKeys = [];
    let plotSymbols = [];

    response?.responseData?.data?.forEach((row) => {
      row.plots?.forEach((plot) => {
        if (plot.name && plot.label === null) {
          if (!plotKeys.includes(plot.name)) {
            plotKeys.push(plot.name);
            plotSymbols.push(plot.symbol);
          }
        }
      });
    });

    response?.responseData?.data?.forEach((row) => {
      let values = {};
      let shouldDisableRow = true;

      row.plots?.map((plot) => {
        if (plot.name && plot.label === null) {
          if (plot.value !== 0) {
            shouldDisableRow = false;
          }
          values[plot.name] = {
            value: plot.value,
            symbol: plot.symbol,
          };
        }
      });

      const mergedArray = Object.keys(values).concat(plotKeys);
      mergedArray.sort((a, b) => plotKeys.indexOf(a) - plotKeys.indexOf(b));

      let mergedValues = {};
      mergedArray.forEach((k, i) => {
        if (!values[k]) {
          mergedValues[k] = {
            value: 0,
            symbol: plotSymbols[i],
          };
        } else {
          mergedValues[k] = {
            value: values[k].value,
            symbol: values[k].symbol,
          };
        }
      });

      if (mergedValues !== {}) {
        const obj = {
          name: row.headerName,
          values: mergedValues,
          shouldDisableRow,
        };

        if (shouldDisableRow) {
          disabledData.push(obj);
        } else {
          enabledData.push(obj);
        }
      }
    });

    enabledData.sort((a, b) => (a.name > b.name ? 1 : b.name > a.name ? -1 : 0));
    disabledData.sort((a, b) => (a.name > b.name ? 1 : b.name > a.name ? -1 : 0));

    const data = enabledData.concat(disabledData);

    setChartData(data);
  }, [isFetchingChart, response]);

  const handleSearch = (e) => {
    const value = e.target.value;
    setSearchQuery(value);
  };

  const filteredRows = () => {
    let filteredData = chartData;

    if (searchQuery?.length > 0) {
      filteredData = filteredData?.filter((data) => {
        if (data.name?.toLowerCase().startsWith(searchQuery.toLowerCase())) {
          return data;
        }
      });
    }

    if (filteredData?.length) {
      return filteredData?.map((item) => StackedRowV2(item));
    } else {
      return <div className="stacked-row">{t("DSS_NO_RESULTS")}</div>;
    }
  };
  const subHead = "SUB_" + visualizer?.name;
  return (
    <GenericChart header={visualizer?.name} showSearch={true} className={"stackedTable"} subHeader={t(subHead)} onChange={handleSearch}>
      <div className="stacked-table-container">{chartData?.length ? filteredRows() : <div className="stacked-row">{t("DSS_NO_DATA")}</div>}</div>
    </GenericChart>
  );
}
