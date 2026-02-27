# Total registrations and interventions in the muster roll

## Motive
- Integrate dashboard analytics service to integrate the chart data for muster roll individuals
- Use the same data from muster roll in the attendance report

## Requirements
- Integrate dashboard analytics service to integrate the chart data for muster roll individuals
- Use the same data from muster roll in the attendance report
- The chart request/response should be as per specs defined below
- add these data into the muster roll when creating but no need to update
- remove from the attendance report to fetch these details again and again
- the muster roll's individual entry should have new columns
  - totalRegistrations: Total registrations done by the user for the period
  - totalInterventions: Total deliveries done by the user for the period
- it should pass proper filters for the request
   - startDate: start date in epoch from the muster roll
   - endDate: end date in epoch from the muster roll
   - uuid: [] list of individual user ids
   - projectId: projectId from the muster roll's attendance register

## Dashboard analytics api 
POST <dashboard-analytics-host>/dashboard-analytics/dashboard/getChartV2?_=1771939390214&tenantId=<tenantId>
```json
{
    "aggregationRequestDto": {
        "visualizationType": "METRIC",
        "visualizationCode": "distributionSummaryByDistrictBurundi",
        "queryType": "",
        "filters": {
            "campaignStartDate": "1757548800000",
            "campaignEndDate": "1763164800000",
            "province": "BUHUMUZA"
        },
        "moduleLevel": "",
        "aggregationFactors": null,
        "requestDate": {
            "startDate": 1771871400000,
            "endDate": 1771957740000,
            "interval": "day",
            "title": "Feb 24, 2026 - Feb 24, 2026"
        }
    },
    "headers": {
        "tenantId": "<tenantId>"
    },
    "RequestInfo": {
        "apiId": "Rainmaker",
        "msgId": "1771939390214|fr_BI"
    }
}
```