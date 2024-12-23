# Edit MB

## Scope

Edit Detailed Measurement Book

Home > Measurement Book> MB Inbox > Edit MB

## Actors <a href="#actors" id="actors"></a>

ULB: MB Creator

## Details <a href="#details" id="details"></a>

1. A measurement book can be edited during the workflow only.
2. Edit of measurement book should be configurable and can be enabled for any of workflow users.
3. As of now it is enabled for estimate creator only.
4. The attributes defining detailed measurement are given in below table.

### Actions <a href="#actions" id="actions"></a>

1. Save - It is to save the details captured for detailed MB and keeping MB with editor.
2. Generate Utilization- Generate the utilization statement out of saved detailed MB details.
3. Submit - It is to allow the user to forward the MB for verification/ next action.
4. View Utilization Statements - It will take the user to view utilization statement HTML page.
5. View MB History - To display all the MBs created so far as per the detail provided in the table above.

#### On Save

1. Changes gets save successfully.
2. No change in workflow state.
3. Toast success message is displayed.
4. Page gets refreshed and opened in editable mode.

`Changes saved successfully.`

#### On Submit

1. Changes gets saved.
2. Utilization statements are revised.
3. MB is forwarded to next user in the workflow.
4. Toast message is displayed with success message based on the workflow state transition.

#### On Generate Utilization

1. Changes gets saved.
2. The utilization is generated and success toast message is displayed.

`Utilization statements are generated successfully.`

`Utilization statements generation failed.`

### Validations <a href="#validations" id="validations"></a>

Not applicable.

### Configuration <a href="#configuration" id="configuration"></a>

Not applicable.

### Notifications <a href="#notifications" id="notifications"></a>

Not applicable

### User Interface <a href="#userinterface" id="userinterface"></a>

[https://www.figma.com/file/M2P3O9WlKtxuLCjQKxLLDg/DIGIT-Works?type=design\&node-id=9137-30903\&mode=design\&t=iYj7FtnLquFhW5r2-4](https://www.figma.com/file/M2P3O9WlKtxuLCjQKxLLDg/DIGIT-Works?type=design\&node-id=9137-30903\&mode=design\&t=iYj7FtnLquFhW5r2-4)

## Acceptance Criteria <a href="#acceptancecriteria" id="acceptancecriteria"></a>

1. MB can be edited during the workflow only.
2. Once approved it can not be edited.
3. Actions and messages are to be taken care.
