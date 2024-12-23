---
description: UI Tech Documentation for UI Enhancements in Dashboard (DSS)
---

# Mukta Dashboard

## Here's a list of Enhancements Made in DSS



### Pie Chart Enhancements

* Sample config for the same is shown below:
* There was a requirement in Mukta Dashboard to render pie charts in the following way. Refer the attached screenshot:
*

    <figure><img src="https://lh6.googleusercontent.com/2j5rp5n9QGj56uIcMbRVIzt8nMMz0wHb__Qk5Y7Vsm5vjCqr3OsNBGChgXA4bA8uWXMlyaxS5jYR6K1xOgB2NmzJ4elIxJA7NhxFkZn8g76IFeRwsi3YvjidT90ZmyMZYrwkzUFAaiulgm87epKmpO4" alt="" width="375"><figcaption></figcaption></figure>
* In the MasterDashboardConfig.json we defined a flag called variant for pieChart
* In order to use this enhancement we need to use "variant":"pieChartv2"
* This pie chart component was enhanced to show colored legends below, percentages in each pie and total number of applications in the center of the pie.
* Sample config for the same is shown below:



```json
{
              "id": 502,
              "name": "DSS_MUKTA_PENDING_BILLS",
              "dimensions": {
                "height": 250,
                "width": 4
              },
              "label": "",
              "vizType": "chart",
              "isCollapsible": false,
              "charts": [
                {
                  "id": "pendingBillsByBillNumberType",
                  "name": "DSS_MUKTA_PENDING_BILLS_BY_BILL_NUMBER_TYPE",
                  "code": "",
                  "chartType": "donut",
                  "variant":"pieChartv2",
                  "filter": "",
                  "headers": [],
                  "tabName": "Number"
                },
                {
                  "id": "pendingBillsByBillAmountType",
                  "name": "DSS_MUKTA_PENDING_BILLS_BY_BILL_AMOUNT_TYPE",
                  "code": "",
                  "chartType": "donut",
                  "variant":"pieChartv2",
                  "filter": "",
                  "headers": [],
                  "tabName": "Amount"
                }
              ]
            }



```



### Horizontal Chart Enhancements (New Metric component to show the KPI's in horizontal way)

* There was a requirement in Mukta Dashboard to show KPIs in the form of horizontal bar charts. Refer the attached screenshot:
*

    <figure><img src="https://lh5.googleusercontent.com/4V2mdbf26nMXltO61gCp0N1vK8WmoVP-cViz4CRXTCofo6eYocGlGx5RQweQoZLEg-KJFvf3Ju-Kxk4E-EaysisxVgeFZ4QT6FqHRCLERj35F-PmuVmry0LtlBpXEVzMwmWv-_LuXkpo3V_L_d5Sh6U" alt="" width="375"><figcaption></figcaption></figure>
* In MasterDashboardConfig.json we defined a boolean flag called “horizontalBarv2”
* Set this flag to true if you want to show the chart in this way.
* Counts of each bar are shown at the side of the bar, labels can be customized.

### &#x20;New Metric component to show the KPI's in horizontal way:

* There was a requirement in the MUKTA Dashboard to show KPIs in a horizontal way. Refer to the sample screenshot below:
*

    <figure><img src="https://lh3.googleusercontent.com/ngrmyqXzJCF0PAtRnjlSZrC8etJDs9IiA4SUP30fHPADgu5zM_Eu2fymdbq5FV7W0Tsfd9d3fM4XvVflWCn1vbWT2qJELLyZY0C4MRWUZC5jCdXyyrsGRk7pOD9Gk8SL9vQRqoScSOhzFbcWbNYS8dw" alt="" width="563"><figcaption></figcaption></figure>
* KPIs are shown in horizontal manner along with their description, aligned vertically to them.&#x20;
* In MasterDashboardConfig we defined a boolean flag “isHorizontalChart”. Set this to true to make use of this enhancement.
* Icons are customizable and are defined in the MasterDashboardConfig itself.

## References:

* [MasterDashboardConfig](https://github.com/egovernments/works-configs/blob/DEV/egov-dss-dashboard/dashboard-analytics/MasterDashboardConfig.json)
* [Dashboard Configuration](https://urban.digit.org/platform/configure-digit/configuring-digit-services/national-dashboard-service-configuration/national-dashboard-ui-technical-doc)

Employee Role:\
**STADMIN**

**Note::**



{% hint style="info" %}
```
The Existing Global Config is to be updated, following flag is to be removed.

In the invalidEmployeeRoles array STADMIN role should be removed to use Mukta dashboard
```
{% endhint %}

\


\


\
\
