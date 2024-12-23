# Measurements

## Overview

Measurement books or M-Books help track the work progress once the execution of the contract is initiated. The page provides detailed steps on how to use a measurement book.

* Measurement Books are legal copies signed and issued by contract\_creator to the contractor noting down the Book ID, Number of Pages, and Title (as per project title).&#x20;
* In offline methods, measurements can be filled daily, weekly, monthly or as per demand (usually before the time of bill creation) to raise a bill and attach a copy of the book as a reference document validating the bill information.
* Measurement Books are initially empty. The M-Books will be filled with the estimated line items only. Once all the estimated line items are filled/counted in the MBook, they can be submitted along with the final Bill.&#x20;
* The Book is also sent as an attachment to the accountant for bill approval.

## **MBook Create**

### **Need**

Digitising offline measurement books offers several advantages, including:

1. **Faster Measurement Capture**: Digital tools allow for quicker and more efficient recording of measurements, reducing the time required for data entry.
2. **Timely Data Capture**: Real-time or near-real-time data capture ensures that measurements are recorded promptly, eliminating delays in processing.
3. **Error-Free Calculation**: Automated calculations reduce the risk of human errors in measurement calculations, leading to more accurate results.
4. **Automation of Billing**: Digital systems can automate the billing process, generating invoices and reports based on the captured measurements, further streamlining operations.

Overall, digitisation enhances efficiency, accuracy, and the overall effectiveness of measurement and billing processes.

### **User**

* The system creates the M-Book automatically while the contract is accepted.&#x20;
* Contractor/MBook-tracker will track M-Book readings
* MBook-Checker will check the measurements
* MBook-Approver will approve the measurements

### **Details**

1. While creating an estimate, the estimate creator can add measurement rows for each SOR /Non-SOR line item. It is not mandatory to fill in these measurements unless the user clicks the "+" icon in the Quantity section of the SOR line item row.
2. Upon selecting the measurement box, an expansion slide-down will appear. Here he/she can enter the measurement details
3. The user can delete or add multiple measurement items.&#x20;
4. The measurement box will contain fields for quantity, length, width, and height/depth and the product of these three values will be automatically calculated and populated in the Quantity box.
5. In cases where an object's area is not measured using length, width, and height, the user can directly enter the area in the Quantity field. The fields for length, width, and size should be greyed out and not editable in such cases.
6. The summation of all the measurement quantities will be automatically populated in the SOR/Non-SOR Quantity row. This quantity will then be multiplied by the rate to calculate the final amount.
7. Measurement Book will be created only when the contract is accepted.
8. Mbook line items will be SOR line items if while creating an estimate, a sub-line item under SOR is not created.&#x20;
   1. If SOR sub-line items are created, the M-Book will have to be tracked at the SOR sub-line items level.&#x20;
9. There should be an option to capture images of physical mbook and site photos along with m-book readings for each date.&#x20;
10. Each M-book entry will go for approval workflow

### **Specifications**

| Field                                                          | Data Type    | Required (Y/N) | Comments                                                                                                                                                                                                                                                    |
| -------------------------------------------------------------- | ------------ | -------------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| ID                                                             | NA           | NA             | UUID                                                                                                                                                                                                                                                        |
| MBook ID                                                       | Alphanumeric | Y              | <p>Mbook ID</p><p>MB&#x3C;Year>/&#x3C;Mo>/&#x3C;Running Sequence></p>                                                                                                                                                                                       |
| Contract ID                                                    | Autofill     | Y              | Every Mbook is linked to a contract                                                                                                                                                                                                                         |
| MBook Created Date                                             |              |                | <p>Date it is created in the system.</p><p>Typically date of acceptance of Contract</p>                                                                                                                                                                     |
| MBook start Date                                               | Date         | Y              | Start date of Measurement Book - From this date onwards readings can be taken                                                                                                                                                                               |
| MBook end date                                                 | Date         | Y              | End date of Measurement Book - On this date onwards readings capture should stop                                                                                                                                                                            |
| _\[Array for each dated entry]_                                |              |                |                                                                                                                                                                                                                                                             |
| Mbook reference number                                         | Text         | Y              | Offline reference number of Mbook, Since this may change after few weeks due to limit in number of pages in each mbook, readings this could be captured with each date                                                                                      |
| MB Entry Date                                                  | Alphanumeric | Y              | Default to today's date. Can take dates between mbook start date and today's date. Cannot be future date                                                                                                                                                    |
| MB from page                                                   | Alphanumeric | Y              | Offline reference of Page numbers in mbook hard copy.                                                                                                                                                                                                       |
| Mb To Page                                                     | Date         | Y              | Offline reference of Page numbers in mbook hard copy.                                                                                                                                                                                                       |
| Attachments                                                    | Attachments  | N              | Photos of site, photos of physical mbook                                                                                                                                                                                                                    |
| _\[Array for each SOR/NON SOR Line Item]_                      |              |                |                                                                                                                                                                                                                                                             |
| SOR ID                                                         | View Only    | N              | Only for Line items that have SOR ID                                                                                                                                                                                                                        |
| Description                                                    | View Only    | Y              | From Estimate                                                                                                                                                                                                                                               |
| UOM                                                            | View Only    | Y              | From Estimate                                                                                                                                                                                                                                               |
| Approved Quantity                                              | View Only    | Y              | From Estimate                                                                                                                                                                                                                                               |
| Approved Rate                                                  | View Only    | Y              | From Estimate                                                                                                                                                                                                                                               |
| Current MB Entry                                               | Numeric      | Y              | Entry from last reading till current reading                                                                                                                                                                                                                |
| Remarks                                                        | Alphanumeric | N              | Comments if any against that particular reading.                                                                                                                                                                                                            |
| _\[Array for each Sub Line Item if defined during estimation]_ |              |                |                                                                                                                                                                                                                                                             |
| Is Deduction?                                                  | Binary       | Y              | <p>Yes/No.</p><p>This is used to measure if any Sub line item has to be measured fully and removed a certain piece. Since payment is done on overall quantity and not based on individual sub line item level measurements this will not affect billing</p> |
| Description                                                    | Alphanumeric | Y              |                                                                                                                                                                                                                                                             |
| Number                                                         | Numeric      | Y              | Quantity of construction mentioned in the description                                                                                                                                                                                                       |
| Length                                                         | Numeric      | Y              | Length of construction mentioned in the description                                                                                                                                                                                                         |
| Width                                                          | Numeric      | Y              | Width of construction mentioned in the description                                                                                                                                                                                                          |
| Depth/height                                                   | Numeric      | Y              | Depth of construction mentioned in the description                                                                                                                                                                                                          |

### **Acceptance Criteria**&#x20;

1. Create an M-Book when the contract is accepted with the start and end dates as per the contract
2. Measurements can be taken both on the web and mobile (Mobile First).
3. Measurement books initially will have line items from estimates with zero quantities. These will get filled as the project progresses and cannot go beyond what is estimated.&#x20;
4. Measurement Book readings can be sent for approval for any duration/marked period, from the last marked period.&#x20;
5. Once approved, that corresponding amount will be allowed for payment to the contractor.

## **MBook Track**

### **Need**

Tracking of MBook will be required to know the project progress and subsequently pay to contractor based on work done

**User**: Mbook-Tracker

**Detail**:

1. Mbook tracking can be done only against the line items that are captured in the estimate.&#x20;
   * This can be done at the estimate SOR line item level or sub-line item level. &#x20;
2. At least one measurement needs to be filled in to submit an MBook for approval.
3. Each submission of the measurement book can use the same ID of the MBook with a running sequence number appended at the end.
4. To add another measurement reading for the same project, users can click on Actions > Add New Measurements, which opens another tab.
5. The overall measurements cannot exceed the estimated quantities.
6. A project can have any number of measurement entries.
7. M Book tracking can only be done until the last date of Mbook.&#x20;
8. Each mBook recording will have an approval workflow.&#x20;
   * MBook is created by contractor/mbooktracker,
   * Checked by m-book checker,
   * Approved by m-book approver.
9. An Mbook should be able to tell using analysis of rates, how much labour, material and other heads as bifurcated in the estimate under SOR item detail 1.&#x20;
   1. Upon each mBook entry creation, a labour consumption log and material consumption log should also be generated as to how much material from the last entry till this entry is consumed.&#x20;
   2. Only based on material consumed, the material supplier is eligible to get paid.

## **MBook Cancel**

**Need:**&#x20;

Cancelling of Mbook is only possible with the cancellation of the contract &#x20;

**Roles:** Contract\_Canceller

**Detail**

1. If a contract gets cancelled Mbook will also get cancel status.&#x20;
2. MBook reading entry if is in the approval workflow, will be moved to cancel status as well.&#x20;
3. Whatever approvals done so far on Mbook workflows are eligible for payments.&#x20;
   1. Since we do automatic payments, a bill would have already been created for such mbook entries.

## Mockups

<table><thead><tr><th width="259"></th><th></th></tr></thead><tbody><tr><td>Track Measurement </td><td><img src="../../.gitbook/assets/image (178).png" alt=""></td></tr><tr><td>Detailed measurement input screen</td><td><img src="../../.gitbook/assets/image (179).png" alt=""></td></tr><tr><td>Measurements successfullly submitted</td><td><img src="../../.gitbook/assets/image (182).png" alt=""></td></tr><tr><td>Measurement Inbox</td><td><img src="../../.gitbook/assets/image (184).png" alt=""></td></tr><tr><td>Measurement Book Search</td><td><img src="../../.gitbook/assets/image (185).png" alt=""></td></tr></tbody></table>

