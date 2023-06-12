import { Loader, RemoveableTag } from "@egovernments/digit-ui-react-components";
import React, { Fragment, useContext, useState, useEffect, useMemo } from "react";
import { useTranslation } from "react-i18next";
import { useHistory } from "react-router-dom";
import { Bar, BarChart, Brush, CartesianGrid, Legend, ResponsiveContainer, Tooltip, XAxis, YAxis, Cell, ReferenceLine } from "recharts";
import FilterContext from "./FilterContext";
import NoData from "./NoData";

const barColors = ["#048BD0", "#FBC02D", "#8E29BF", "#EA8A3B", "#0BABDE", "#6E8459", "#D4351C", "#0CF7E4", "#F80BF4", "#22F80B"];

const renderPlot = (plot, key, denomination) => {
  const plotValue = key ? plot?.[key] : plot?.value || 0;
  if (plot?.symbol?.toLowerCase() === "amount") {
    switch (denomination) {
      case "Unit":
        return plotValue;
      case "Lac":
        return Number((plotValue / 100000).toFixed(2));
      case "Cr":
        return Number((plotValue / 10000000).toFixed(2));
      default:
        return "";
    }
  } else if (plot?.symbol?.toLowerCase() === "number") {
    return Number(plotValue.toFixed(1));
  } else {
    return plotValue;
  }
};

const CustomHorizontalBarChart = ({
  data,
  xAxisType = "category",
  yAxisType = "number",
  xDataKey = "name",
  yDataKey = "",
  xAxisLabel = "",
  yAxisLabel = "",
  layout = "horizontal",
  title,
  showDrillDown = false,
  setChartDenomination,
}) => {
  const { id } = data;
  const { t } = useTranslation();
  const history = useHistory();
  const { value } = useContext(FilterContext);
  const [activeIndex, setActiveIndex] = useState(null);
  const [activeBarId, setActiveBarId] = useState(null);
  const [hoverBarId, setHoverBarId] = useState(null);
  const [chartKey, setChartKey] = useState(id);
  const [selectedStack, setSelectedStack] = useState(null);
  const [filterStack, setFilterStack] = useState([{ id: id }]);
  const [symbolKeyMap, setSymbolKeyMap] = useState({});
  const tenantId = Digit.ULBService.getCurrentTenantId();
  const { isLoading, data: response } = Digit.Hooks.dss.useGetChart({
    key: chartKey,
    type: "metric",
    tenantId,
    requestDate: { ...value?.requestDate, startDate: value?.range?.startDate?.getTime(), endDate: value?.range?.endDate?.getTime() },
    filters:
      id === chartKey
        ? value.filters
        : { ...value?.filters, [filterStack[filterStack.length - 1]["filterKey"]]: filterStack[filterStack.length - 1]?.filterValue, selectedStack: selectedStack },
    moduleLevel: value?.moduleLevel,
  });

  const shouldDisplayTargetline = response && response?.responseData?.targetLineChart !== null && response?.responseData?.targetLineChart !== "none";

  let target = 0;
  let targetMessage = "";

  const { data: targetResponse } = Digit.Hooks.dss.useGetChart(
    {
      key: response?.responseData?.targetLineChart,
      type: "metric",
      tenantId,
      requestDate: { ...value?.requestDate, startDate: value?.range?.startDate?.getTime(), endDate: value?.range?.endDate?.getTime() },
      filters:
        id === response?.responseData?.targetLineChart
          ? value.filters
          : { ...value?.filters, [filterStack[filterStack.length - 1]["filterKey"]]: filterStack[filterStack.length - 1]?.filterValue },
      moduleLevel: value?.moduleLevel,
    },
    shouldDisplayTargetline
  );
  target = Math.ceil(targetResponse?.responseData?.data[0].headerValue) > 0 ? Math.ceil(targetResponse?.responseData?.data[0].headerValue) : 0;
  targetMessage = "TARGET_DSS_" + targetResponse?.responseData?.data[0].headerName?.replaceAll(" ", "_").toUpperCase();

  const constructChartData = (data, denomination) => {
    let result = {};
    let symbolKeyObject = {};

    for (let i = 0; i < data?.length; i++) {
      const row = data[i];
      for (let j = 0; j < row.plots.length; j++) {
        const plot = row.plots[j];
        symbolKeyObject[t(plot.name)] = plot.symbol;
        result[plot.name] = { ...result[plot.name], [t(row.headerName)]: renderPlot(plot, "value", denomination), name: t(plot.name) };
      }
    }

    setSymbolKeyMap(symbolKeyObject);
    return Object.keys(result).map((key) => {
      return {
        name: key,
        ...result[key],
      };
    });
  };

  const goToDrillDownCharts = () => {
    history.push(`/digit-ui/employee/dss/drilldown?chart=${response?.responseData?.drillDownChartId}&ulb=${value?.filters?.tenantId}&title=${title}`);
  };

  const CustomizedLabel = (props) => {
    const { value, viewBox, target } = props;
    const x = viewBox.width - value.length * 7;
    const y = viewBox.y + 16;
    return (
      <g>
        <text x={x} y={y} fill="#505A5F" style={{ fontSize: 12 }} className="customLabel">
          {" "}
          {value}: &nbsp;
          <tspan font-weight="bold">{target}</tspan>
        </text>
      </g>
    );
  };

  const CustomTooltip = ({ active, payload, label }) => {
    if (active && payload && payload.length) {
      var value = payload[0].payload[Object.keys(payload[0].payload)[hoverBarId + 1]];
      if (id === "fsmMonthlyWasteCal") {
        value = `${Digit.Utils.dss.formatter(Math.round((value + Number.EPSILON) * 100) / 100, "number", value?.denomination, true, t)} ${t(
          "DSS_KL"
        )}`;
      } else if (symbolKeyMap && symbolKeyMap[payload[0]?.payload?.name] === "percentage") {
        value = Digit.Utils.dss.formatter(value, "percentage", value?.denomination, true, t);
      } else {
        value = Digit.Utils.dss.formatter(Math.round((value + Number.EPSILON) * 100) / 100, "number", value?.denomination, true, t);
      }
      return (
        <div className="custom-tooltip">
          <p className="horizontalBarChartLabel" style={{fontSize:"16px", color:"#505A5F"}}>
            <b>{`${label}`}</b> &nbsp; {`${value}`}
          </p>
        </div>
      );
    }

    return null;
  };

  useEffect(() => {
    if (response) setChartDenomination(response?.responseData?.data?.[0]?.headerSymbol);
  }, [response]);

  useEffect(() => {
    const { id } = data;
    setFilterStack([{ id: id }]);
    setChartKey(id);
  }, [data, value]);

  const onBarClick = ({ payload, tooltipPayload }) => {
    let newStack = {
      id: response?.responseData?.drillDownChartId,
      ...payload,
      filterKey: response?.responseData.filter[0].key,
      filterValue: payload.name,
    };
    setSelectedStack(tooltipPayload?.[0]?.dataKey);
    setFilterStack([...filterStack, newStack]);
    setChartKey(response?.responseData?.drillDownChartId);
  };

  const chartData = useMemo(() => constructChartData(response?.responseData?.data, value?.denomination), [response, value?.denomination]);

  const renderLegend = (value) => <span style={{ fontSize: "14px", color: "#505A5F" }}>{t(`DSS_LEGEND_${Digit.Utils.locale.getTransformedLocale(value)}`)}</span>;

  const tickFormatter = (value) => {
    if (typeof value === "string") {
      return value.replace("-", ", ");
    } else if (typeof value === "number") return Digit.Utils.dss.formatter(value, "number", value?.denomination, true, t);
    return value;
  };

  if (isLoading) {
    return <Loader />;
  }
  const formatXAxis = (tickFormat) => {
    // if (tickFormat && typeof tickFormat == "string") {
    //   return `${tickFormat.slice(0, 16)}${tickFormat.length > 17 ? ".." : ""}`;
    // }
    return `${tickFormat}`;
  };
  const removeFilter = (id) => {
    const nextState = filterStack?.filter((filter, index) => index < id);
    setFilterStack(nextState);
    setChartKey(nextState[nextState?.length - 1]?.id);
  };

  const onMouseLeave = () => {
    setActiveIndex(null);
    setActiveBarId(null);
  };

  const bars = response?.responseData?.data?.map((bar) => bar?.headerName);
  return (
    <Fragment>
      {filterStack?.length > 1 && (
        <div className="tag-container">
          <span style={{ marginTop: "20px" }}>{t("DSS_FILTERS_APPLIED")}: </span>
          {filterStack.map((filter, id) =>
            id > 0 ? (
              <RemoveableTag
                key={id}
                text={`${t(`DSS_HEADER_${Digit.Utils.locale.getTransformedLocale(filter?.filterKey)}`)}: ${t(
                  `DSS_TB_${Digit.Utils.locale.getTransformedLocale(filter?.name)}`
                )}`}
                onClick={() => removeFilter(id)}
              />
            ) : null
          )}
        </div>
      )}
      <ResponsiveContainer
        width="94%"
        height={450}
        margin={{
          top: 5,
          right: 5,
          left: 5,
          bottom: 5,
        }}
      >
        {chartData?.length === 0 || !chartData ? (
          <NoData t={t} />
        ) : (
          <BarChart
            width="100%"
            height="100%"
            margin={{
              top: 5,
              right: 5,
              left: 5,
              bottom: 5,
            }}
            layout={layout}
            data={chartData}
            barGap={16}
            barSize={16}
          >
            <CartesianGrid strokeDasharray="2 2" vertical={false} />
            <YAxis
              dataKey={yDataKey}
              type={yAxisType}
              tick={{ fontSize: "12px", fill: "#505A5F" }}
              label={{
                value: yAxisLabel,
                angle: -90,
                position: "insideLeft",
                dy: 50,
                fontSize: "12px",
                fill: "#505A5F",
              }}
              allowDecimals={false}
              tickCount={5}
              tickFormatter={tickFormatter}
              unit={id === "fsmCapacityUtilization" || response?.responseData?.data?.[0]?.headerSymbol==="percentage" ? "%" : ""}
              width={layout === "vertical" ? 120 : 60}
              domain={shouldDisplayTargetline ? ["auto", target + 10] : [0, (dataMax) => Math.ceil(dataMax / 10) * 10]}
            />
            <XAxis
              dataKey={xDataKey}
              type={xAxisType}
              tick={{ fontSize: "14px", fill: "#505A5F" }}
              tickCount={10}
              tickFormatter={tickFormatter}
              height={40}
            />
            <Tooltip
              wrapperStyle={{ outline: "none", border: "1px solid #B1B4B6", borderRadius: "5px", padding: "8px", backgroundColor: "#FFFFFF" }}
              content={<CustomTooltip />}
              cursor={false}
            />
            {shouldDisplayTargetline ? (
              <ReferenceLine
                y={target}
                stroke={"#F47738"}
                ifOverflow="extendDomain"
                strokeWidth={"2px"}
                label={<CustomizedLabel value={t(targetMessage)} target={target} />}
              />
            ) : null}
            {bars?.map((bar, id) => (
              <Bar
                key={id}
                dataKey={t(bar)}
                fill={barColors[id]}
                stackId={bars?.length > 2 ? 1 : id}
                onClick={response?.responseData?.drillDownChartId !== "none" ? onBarClick : null}
                onMouseEnter={(_, index) => {
                  setHoverBarId(id);
                  if (response?.responseData?.drillDownChartId !== "none") {
                    setActiveIndex(index);
                    setActiveBarId(id);
                  } else {
                    setActiveIndex(null);
                    setActiveBarId(null);
                  }
                }}
                onMouseLeave={onMouseLeave}
              >
                {chartData.map((_, index) => {
                  var topIndex = 0;
                  var i = 0;
                  for (let properties in chartData[index]) {
                    if (Number.isInteger(chartData[index][properties])) {
                      if (chartData[index][properties] !== 0) {
                        topIndex = i;
                      }
                      i += 1;
                    }
                  }
                  return (
                    <Cell
                      radius={(id === topIndex) || bars.length===2 ? [5, 5, 0, 0] : undefined}
                      stroke={(activeIndex === index) & (activeBarId === id) ? "#ccc" : null}
                      strokeWidth={3}
                    />
                  );
                })}
              </Bar>
            ))}
            <Legend formatter={renderLegend} iconType="circle" wrapperStyle={{ paddingTop: "10px" }} />
            {chartData.length > 1 ? (
              <Brush
                dataKey="name"
                endIndex={chartData.length > 5 ? Math.floor(chartData.length / 2) : chartData.length - 1}
                height={24}
                travellerWidth={5}
                stroke="#F47738"
              />
            ) : null}
          </BarChart>
        )}
      </ResponsiveContainer>
      {showDrillDown && (
        <p className="showMore" onClick={goToDrillDownCharts}>
          {t("DSS_SHOW_MORE")}
        </p>
      )}
    </Fragment>
  );
};

export default CustomHorizontalBarChart;
