---
description: Muster Roll Tech Document
---

# Muster Roll (CBO Application)

### Overview

This module helps in viewing and updating the muster rolls.

ROLE: ORG\_ADMIN

This module has 2 associated screens :&#x20;

1. View Muster Rolls Inbox&#x20;
2. Muster Roll Table Screen

### MDMS Configurations

<table data-header-hidden><thead><tr><th width="97.99999999999997"></th><th></th><th></th></tr></thead><tbody><tr><td>S.No.</td><td>Data</td><td>MDMS Link</td></tr><tr><td>1</td><td>AttendanceHours</td><td><a href="https://github.com/egovernments/works-mdms-data/blob/DEV/data/pg/common-masters/AttendanceHours.json">Attendance Hours</a></td></tr><tr><td><p>2</p><p><br></p></td><td>WageSeekerSkills</td><td><a href="https://github.com/egovernments/works-mdms-data/blob/DEV/data/pg/common-masters/WageSeekerSkills.json">Wage Seeker Skills</a></td></tr><tr><td>3</td><td>Sent Back to CBO</td><td><a href="https://github.com/egovernments/works-mdms-data/blob/UAT/data/statea/commonUiConfig/CBOMusterInboxConfig.json">Sent Back to CBO Code</a></td></tr></tbody></table>

Based on the business workflow state set for Muster Roll ( Sent BAck for Correction) in respective environments, Add the corresponding code in [Sent Back to CBO Code](https://github.com/egovernments/works-mdms-data/blob/UAT/data/statea/commonUiConfig/CBOMusterInboxConfig.json) MDMS



#### User actions&#x20;

On this page, the following actions need to be performed: If the muster roll is in workflow, User can only view the muster roll, If the muster roll is sent back to CBO,then user can edit the attendance and resubmit the muster roll,\


### Validations

* If Muster Roll is in Workflow, The screen will only be a view page and Save as Draft and Re-Submit button will not be shown in the screen
* Based on the [Attendance Hours](https://github.com/egovernments/works-mdms-data/blob/DEV/data/pg/common-masters/AttendanceHours.json) list, users will be allowed to mark the attendance as Full Day, Half Day, or absent. If the list is of length 2, users will be allowed to mark only Full and Absent, Else, users can mark halfday as well.\


#### API Details

<table data-header-hidden><thead><tr><th width="76"></th><th></th><th></th><th></th></tr></thead><tbody><tr><td>1</td><td><p>muster-roll/v1/_estimate</p><p><br></p></td><td><p>{</p><p> "musterRoll": {</p><p>   "tenantId":,</p><p>   "registerId":,</p><p>   "startDate":,</p><p>   "endDate": </p><p> }</p><p>}</p><p><br></p></td><td>To get the attendance log for the selected Date range</td></tr><tr><td>3</td><td><p>attendance/log/v1/_create</p><p><br></p></td><td><p>{"attendance": []}</p><p><br></p></td><td>To log the attendance of the individuals for the week</td></tr><tr><td>4</td><td><p>attendance/log/v1/_update</p><p><br></p></td><td><br></td><td>To update the attendance log of the individuals for Sent Back To CBO Muster Rolls</td></tr><tr><td>5</td><td><p>muster-roll/v1/_search</p><p><br></p></td><td><br></td><td>To check if any existing muster rolls present for the selected week</td></tr><tr><td>6</td><td><p>egov-workflow-v2/egov-wf/businessservice/_search</p><p><br></p></td><td><p>{</p><p> "tenantId":,</p><p> "businessServices": “musterRollId”,</p><p>}</p><p><br></p></td><td>To check the workflow status of the Muster Roll</td></tr></tbody></table>

#### DIGIT Components and Custom Components Used&#x20;

<table data-header-hidden><thead><tr><th width="87"></th><th></th><th></th><th></th></tr></thead><tbody><tr><td>S.No</td><td>Component</td><td>Path</td><td>Description</td></tr><tr><td>1</td><td><p>DigitTable</p><p><br><br></p></td><td><a href="https://github.com/egovernments/DIGIT-Works/blob/master/frontend/works_shg_app/lib/widgets/molecules/digit_table.dart">DigitTable.dart</a></td><td>Custom Table with fixed first column and other columns scrollable</td></tr><tr><td>2</td><td><p>DropDownDialog</p><p><br></p></td><td><a href="https://github.com/egovernments/DIGIT-Works/blob/master/frontend/works_shg_app/lib/widgets/atoms/table_dropdown.dart">DropDownDialog</a></td><td>A dialog with dropdown options</td></tr><tr><td>3</td><td><p>DateRangePicker</p><p><br></p></td><td><a href="https://github.com/egovernments/DIGIT-Works/blob/master/frontend/works_shg_app/lib/widgets/atoms/date_range_picker.dart">DateRangePicker</a></td><td>A custom Date Range picker to select a range of the month / year</td></tr><tr><td>4</td><td><p>DigitElevatedButton</p><p><br></p></td><td><a href="https://github.com/egovernments/health-campaign-field-worker-app/blob/main-parallel/packages/digit_components/lib/widgets/digit_elevated_button.dart">DigitElevatedButton</a></td><td>An Elevated Submit Button </td></tr><tr><td>6</td><td><p>WorkDetailsCard</p><p><br></p></td><td><a href="https://github.com/egovernments/DIGIT-Works/blob/master/frontend/works_shg_app/lib/widgets/WorkDetailsCard.dart">DetailsCard</a></td><td>Details Card based on DIGIT Figma design</td></tr><tr><td>7</td><td>DigitTimeLine</td><td><a href="https://github.com/egovernments/DIGIT-Works/blob/master/frontend/works_shg_app/lib/widgets/atoms/digit_timeline.dart">DigitTimeline</a></td><td>WorkFlow TimeLine component viewing Workflow state, And Assignees Details</td></tr></tbody></table>

### Localization Configuration and Modules

<table data-header-hidden><thead><tr><th width="247"></th><th></th></tr></thead><tbody><tr><td>TenantID</td><td>Module</td></tr><tr><td>pg</td><td>rainmaker-common</td></tr><tr><td>pg</td><td>rainmaker-muster</td></tr><tr><td>pg</td><td>rainmaker-common-masters</td></tr><tr><td>pg</td><td>rainmaker-workflow</td></tr><tr><td>pg.citya</td><td>rainmaker-pg.citya</td></tr></tbody></table>

### Reference Files

1. Blocs :&#x20;

* [Attendance Create Log Bloc](https://github.com/egovernments/DIGIT-Works/blob/master/frontend/works_shg_app/lib/blocs/attendance/attendance_create_log.dart)
* [Search Muster Rolls in a Tenanat](https://github.com/egovernments/DIGIT-Works/blob/master/frontend/works_shg_app/lib/blocs/muster_rolls/search_muster_roll.dart)
* [Muster Search From Muster ID Bloc](https://github.com/egovernments/DIGIT-Works/blob/master/frontend/works_shg_app/lib/blocs/muster_rolls/search_individual_muster_roll.dart)
* [Get Muster Roll Workflow Bloc](https://github.com/egovernments/DIGIT-Works/blob/master/frontend/works_shg_app/lib/blocs/muster_rolls/get_muster_workflow.dart)
* [Muster Get Attendance Log Bloc](https://github.com/egovernments/DIGIT-Works/blob/master/frontend/works_shg_app/lib/blocs/muster_rolls/muster_roll_estimate.dart)
* [Create and Update Muster Roll Bloc](https://github.com/egovernments/DIGIT-Works/blob/master/frontend/works_shg_app/lib/blocs/muster_rolls/create_muster.dart)

2. Models :

* [Estimate Muster Roll Model](https://github.com/egovernments/DIGIT-Works/blob/master/frontend/works_shg_app/lib/models/muster_rolls/estimate_muster_roll_model.dart)
* [Search Muster Roll Model](https://github.com/egovernments/DIGIT-Works/blob/master/frontend/works_shg_app/lib/models/muster_rolls/muster_roll_model.dart)
* [Muster Workflow model](https://github.com/egovernments/DIGIT-Works/blob/master/frontend/works_shg_app/lib/models/muster_rolls/muster_workflow_model.dart)

3. Repositories:&#x20;

* [Attendance Register Repo](https://github.com/egovernments/DIGIT-Works/blob/master/frontend/works_shg_app/lib/data/repositories/attendence_repository/attendence_register.dart)
* [Muster Roll Repo](https://github.com/egovernments/DIGIT-Works/blob/master/frontend/works_shg_app/lib/data/repositories/muster_roll_repository/muster_roll.dart)
* [Workflow Repo](https://github.com/egovernments/DIGIT-Works/blob/master/frontend/works_shg_app/lib/data/repositories/workflow_repository/workflow.dart)

4. Screens

* [Muster Inbox Screen](https://github.com/egovernments/DIGIT-Works/blob/master/frontend/works_shg_app/lib/pages/view_muster_rolls.dart)
* [Muster Roll View and Edit Table](https://github.com/egovernments/DIGIT-Works/blob/master/frontend/works_shg_app/lib/pages/shg_inbox.dart)
