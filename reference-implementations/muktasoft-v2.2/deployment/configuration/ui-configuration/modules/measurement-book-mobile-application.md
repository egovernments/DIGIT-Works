---
description: Measurement Book Tech Document
---

# Measurement Book (Mobile Application)

## Overview

This module helps in taking the measurement, for an accepted contract&#x20;

**Roles**: MB\_CREATOR, MB\_VERIFIER, MB\_APPROVER,MB\_VIEWER

This module has 5 associated screens :&#x20;

1. Inbox
2. Create
3. Edit
4. Update
5. Search

### Home Screen&#x20;

This screen renders dynamically based on the user's role mapping. It contains two cards: Measurement Book and Work-order Book. These modules are displayed according to the roles assigned to the logged-in user. If the user has the role of MB Creator, the Work-order card will be visible. Similarly, if the user has the MB\_VERIFIER, MB\_APPROVER,MB\_VIEWER role, the Measurement card will be visible. If the user has both roles, both cards will be displayed.

## User Actions in Work Order Inbox

1. **New Measurement Book creation:**

* Tap the "Create Measurement Book" button on any work order to generate a new measurement book.

2. **Work Order Filter:**

* Users can filter the work order list by work order number, ward, and CBO name.

3. **Work Order Sorting:**

* Users have the flexibility to sort the entire list based on the amount or CBO name.

## User Actions in Measurement Book Inbox:

1. **Existing Measurement Book view/update:**

* Users can update/view measurement books by tapping on the Create Measurement Book button of any of the work orders.

2. **Measurement Book Filter:**

* Users have the flexibility in the application to filter the work-order list based on the Measurement Number, projectId, ward and workflow state.

3. **Measurement Book Sorting:**

* Users have been given the flexibility to sort the whole list based on the amount, SLA days remaining and workflow state.

## Create Measurement Book

To create the new Measurement Book, The user is required to go to the Work-order inbox screen by tapping the work-order card on the home screen. In that screen, multiple work orders assigned to him will be available in the list. He/she can tap on the work order to create a Measurement Book.

### Conditions for check before Creating new Measurement Book

There are certain check points are expected to be validated before navigating to create a new Measurement Book screen from the work-order card.

| SNo | CheckPoints                                                         | MB create? |
| --- | ------------------------------------------------------------------- | ---------- |
| 1   | If the existing MB for the selected work-order is in workflow state | NO         |
| 2   | Time Extension is in progress                                       | NO         |
| 3   | Revision Estimate is in progress                                    | NO         |
| 4   | Above points are approved in state                                  | YES        |

### API specifications for the validation:

<table><thead><tr><th width="81">SNo.</th><th>API</th><th>body</th><th>Description</th></tr></thead><tbody><tr><td>1</td><td><pre data-overflow="wrap"><code>mukta-services/measurement/_search
</code></pre></td><td><p>{</p><p></p><pre data-overflow="wrap"><code>"contractNumber": "",
        "tenantId": "",
</code></pre><p>}</p></td><td>Gives the list of measurements related to the contract Number</td></tr><tr><td>2</td><td><pre data-overflow="wrap"><code>estimate/v1/_search    
</code></pre></td><td><pre data-overflow="wrap"><code>{
          "tenantId": "",
          "estimateNumber":""
        }
</code></pre></td><td>Gives the estimate details for the estimate Number</td></tr></tbody></table>



### Measurement Book create screen:

This screen contains the below components:

1. SORs, NON-SORs cards for the current opened screen
2. The primary detail section contains the previous MB list and the brief description of the MB.
3. Workflow button

### User Action in Measurement Book Screen:

User can fill the measurement of the SORs and NON-SORs associated with Work-order. Image of the worksite can be uploaded by the Measurement book reader as to give proof of work done.

After filling the necessary details , user has been given two options such as Save-as-draft and submit.

if the user does save as draft he has the control to edit it as many time as he wants. once the form is submitted. then   He can not edit the application in the same screen.

### &#x20;API Specification for Measurement Book Creation:

<table><thead><tr><th width="81">SNo</th><th>API</th><th width="293">body</th><th>Description</th></tr></thead><tbody><tr><td>1</td><td><pre data-overflow="wrap"><code>egov-hrms/employees/_search
</code></pre></td><td><p>{"<code>roles":""</code></p><p><code>"isActive":""</code></p><p><code>"tenantId":""}</code></p></td><td>Fetches the HRMS employees</td></tr><tr><td>2</td><td><pre data-overflow="wrap"><code>measurement-service/v1/_create
</code></pre></td><td><pre><code>{
      "documents": 
            [],
      'id': "",
      'tenantId': "",
      'measurementNumber': "",
      'entryDate': "",
      'isActive': "",
      'wfStatus': "",
      'referenceId': "",
      'physicalRefNumber': "",
      'workflow': {
        'action': "",
        'comments': "",
        'assignes': ,
        'documents': [],
      'additionalDetails': {
        'endDate': "",
        'sorAmount': "",
        'startDate': "",
        'totalAmount': "",
        'nonSorAmount': "",
        'musterRollNumber': [],
      },
      'measures': [{
          'description': "",
          'comments': "",
          'targetId': "",
          
          'breadth':
              "",
          'length':
              "",
          'height':
              "",
          'isActive': "",
          'referenceId': "",
          'numItems': "",
          'id': "",
          'cumulativeValue': "",
          'currentValue': "",
          'additionalDetails': {
            'type': "",
            'mbAmount': "",
            'measureLineItems': [],
          },
        };
      ],
    };
</code></pre></td><td>Create new measurement </td></tr></tbody></table>



## Measurement Book View/Edit/Update:

In order to update or edit the measurement book, the user has to navigate from home screen to measurement Book Inbox by tapping on the Measurement Book Card. In the MB Inbox Screen he/she can edit or update any measurement book by tapping the **Open Measurement Book** button.

### User Action in Measurement Book Screen:

In this screen use can take the actions:

1. Verify And Forward
2. Approve
3. Send Back
4. Reject
5. Edit

### API Specification for Measurement Book Update:



<table><thead><tr><th width="97">SNo.</th><th>API</th><th width="257">Body</th><th>Description</th></tr></thead><tbody><tr><td>1</td><td><pre data-overflow="wrap"><code>mukta-services/measurement/_search
</code></pre></td><td><p>{</p><p></p><pre data-overflow="wrap"><code>"contractNumber:"",
        "measurementNumber": "",
        "key":"View",
      });
</code></pre><p>}</p></td><td>fetching the measurement book details</td></tr><tr><td>2</td><td><pre data-overflow="wrap"><code>measurement-service/v1/_update
</code></pre></td><td><pre><code>{
      "documents": 
            [],
      'id': "",
      'tenantId': "",
      'measurementNumber': "",
      'entryDate': "",
      ​'isActive': "",
      'wfStatus': "",
      'referenceId': "",
      'physicalRefNumber': "",
      'workflow': {
        'action': "",
        'comments': "",
        'assignes': ,
        'documents': [],
      'additionalDetails': {
        'endDate': "",
        'sorAmount': "",
        'startDate': "",
        'totalAmount': "",
        'nonSorAmount': "",
        'musterRollNumber': [],
      },
      'measures': [{
          'description': "",
          'comments': "",
          'targetId': "",
          
          'breadth':
              "",
          'length':
              "",
          'height':
              "",
          'isActive': "",
          'referenceId': "",
          'numItems': "",
          'id': "",
          'cumulativeValue': "",
          'currentValue': "",
          'additionalDetails': {
            'type': "",
            'mbAmount': "",
            'measureLineItems': [],
          },
        };
      ],
    };
</code></pre></td><td>updating the current measurement book</td></tr><tr><td>3</td><td><code>egov-workflow-v2/egov-wf/process/_search</code></td><td><p><code>{"history":"",</code></p><p><code>"tenantId":"",</code></p><p><code>"businessIds":""</code></p><p><code>}</code></p></td><td>fetches the workflow for the curent measurement book</td></tr></tbody></table>



## **DIGIT Components & Custom Components Used:**



<table><thead><tr><th width="100">SNO</th><th>Component</th><th width="200">Path</th><th>Description</th></tr></thead><tbody><tr><td>1</td><td>Common MB Card</td><td><a href="https://github.com/odisha-muktasoft/MUKTA_IMPL/blob/UAT/frontend/works_shg_app/lib/widgets/mb/mb_detail_card.dart">measurement card</a></td><td>Custom card  with  key value data binding</td></tr><tr><td>2</td><td>Multi line Items</td><td><a href="https://github.com/odisha-muktasoft/MUKTA_IMPL/blob/UAT/frontend/works_shg_app/lib/widgets/mb/multi_line_items.dart"><code>multiline items</code></a></td><td>custom component contains the digit card component with multi-line calculation functionalities</td></tr><tr><td>3</td><td>Floating Action Card</td><td><a href="https://github.com/odisha-muktasoft/MUKTA_IMPL/blob/UAT/frontend/works_shg_app/lib/widgets/mb/float_action_card.dart">floating action card</a></td><td>this component contains the floating action button with showModalBottomSheet functions</td></tr><tr><td>4</td><td>DropDownDialog</td><td><a href="https://github.com/egovernments/DIGIT-Works/blob/master/frontend/works_shg_app/lib/widgets/atoms/table_dropdown.dart">DropDownDialog</a></td><td>A dialog with dropdown options</td></tr><tr><td>5</td><td>DigitElevatedButton</td><td><a href="https://github.com/egovernments/health-campaign-field-worker-app/blob/main-parallel/packages/digit_components/lib/widgets/digit_elevated_button.dart">DigitElevatedButton</a></td><td>An Elevated Submit Button </td></tr></tbody></table>



## Reference Files/Links **:**

1. Blocs:

* [Measurement Bloc](https://github.com/odisha-muktasoft/MUKTA_IMPL/tree/UAT/frontend/works_shg_app/lib/blocs/employee/mb)
* [Estimate Bloc](https://github.com/odisha-muktasoft/MUKTA_IMPL/tree/UAT/frontend/works_shg_app/lib/blocs/employee/estimate)
* [HRMS Bloc](https://github.com/odisha-muktasoft/MUKTA_IMPL/blob/UAT/frontend/works_shg_app/lib/blocs/employee/emp_hrms/emp_hrms.dart)

2. Models:

* [Measurement models](https://github.com/odisha-muktasoft/MUKTA_IMPL/tree/UAT/frontend/works_shg_app/lib/models/employee/mb)
* [HomeConfig models](https://github.com/odisha-muktasoft/MUKTA_IMPL/tree/UAT/frontend/works_shg_app/lib/models/employee/homeconfig)
* [Estimate models](https://github.com/odisha-muktasoft/MUKTA_IMPL/tree/UAT/frontend/works_shg_app/lib/models/employee/estimate)
* [Work order models](https://github.com/odisha-muktasoft/MUKTA_IMPL/tree/UAT/frontend/works_shg_app/lib/models/employee/work_order)

3. Repositories:&#x20;

* [Measurement Book repository](https://github.com/odisha-muktasoft/MUKTA_IMPL/tree/UAT/frontend/works_shg_app/lib/data/repositories/employee_repository)

4. Screens:

* [Measurement screens](https://github.com/odisha-muktasoft/MUKTA_IMPL/tree/UAT/frontend/works_shg_app/lib/pages/employee)

