# Aadhaar Integration

## Introduction <a href="#id-7px9kkkiexs2" id="id-7px9kkkiexs2"></a>

MUKTASoft will be integrated with AADHAAR to uniquely identify the wage seekers and authenticate the AADHAAR number provided during the registration of wage seekers.&#x20;

The API documents for Aadhar integration are shared by the Odisha Computer Application Centre (OCAC).

## Scope <a href="#z4c7egne5yz2" id="z4c7egne5yz2"></a>

1. Ensure that the valid AADHAR is captured.
2. Validate AADHAAR - the Yes/ No authentication API will be used for the validation.
3. Since AADHAAR is not supposed to be mandatory, the option to capture any other ID will be provided.
4. Enter the **AADHAAR Number** and **Name** and then click on the **Validate** button.
5. In case, the validation fails, the wage seeker can not be registered with the given AADHAAR.
6. In case of any other ID besides AADHAAR is provided at the time of registration, the wage seeker is registered with the provided ID without validation.

## Process Flow <a href="#lx8xb2kbz1ib" id="lx8xb2kbz1ib"></a>

### Integration Flow Diagram <a href="#foflffios276" id="foflffios276"></a>

![](<../../../../../.gitbook/assets/0 (17).png>)

## Functional Details <a href="#iw1d0rq8obpu" id="iw1d0rq8obpu"></a>

### Wage Seeker Registration <a href="#nkxatbqo3q8j" id="nkxatbqo3q8j"></a>

#### Individual Details <a href="#jdzrhbzcwr6o" id="jdzrhbzcwr6o"></a>

Individual details are divided into the below-given four sections/screens.

**Individual Identification Details**

Individual identification details cover the identity document details. As AADHAAR is non-mandatory, the system allows accepting other documents to validate the identity. The documents permitted are listed below.

1. AADHAAR
2. Election Photo Identity Card (EPIC)
3. Driving License
4. Ration Card under TPDS

The remaining system is to be integrated with the AADHAAR system to validate the AADHAAR entered. The rest of the document details are captured without validation.

**Attributes**

<table><thead><tr><th width="74">#</th><th width="139">Field Name</th><th width="143">Data Type</th><th width="116">Is Mandatory?</th><th>Description</th></tr></thead><tbody><tr><td>1</td><td>Identity Document</td><td>Drop-down</td><td>Yes</td><td>The name of the identity document from the list provided by HuDD.</td></tr><tr><td>2</td><td>Identity Number</td><td>Alphanumeric*</td><td>Yes</td><td>Identity number as per the document selected.</td></tr><tr><td>3</td><td>Name on the Document</td><td>Display</td><td>Yes</td><td>Name of the identity document holder as provided on the document</td></tr><tr><td>4</td><td>Validate</td><td>Action</td><td>Yes*</td><td>An action button to validate, applicable only in case of document selected AADHAAR.</td></tr><tr><td>5</td><td>Next</td><td>Action</td><td>Yes</td><td>An action button to go to the next page.</td></tr></tbody></table>

#### **Screen Mock-ups**

| <img src="../../../../../.gitbook/assets/1 (15).png" alt="" data-size="original"> | <img src="../../../../../.gitbook/assets/2 (15).png" alt="" data-size="original"> |
| --------------------------------------------------------------------------------- | --------------------------------------------------------------------------------- |

**Individual’s Personal Details**

**Attributes**

<table><thead><tr><th width="90">Sr.No.</th><th width="134">Field Name</th><th width="132">Data Type</th><th width="123">Is Mandatory?</th><th>Description</th></tr></thead><tbody><tr><td>1</td><td>Date of birth</td><td>Date Picker</td><td>Yes</td><td>Date of birth of the individual, the 18 years age eligibility validation is applicable.</td></tr><tr><td>2</td><td>Gender</td><td>Radio</td><td>Yes</td><td>Gender of the individual.</td></tr><tr><td>3</td><td>Father/ Husband Name</td><td>Textbox</td><td>Yes</td><td>Father or husband name of the individual.</td></tr><tr><td>4</td><td>Relationship</td><td>Drop-down</td><td>Yes</td><td>The relationship of the guardian with the individual.</td></tr><tr><td>5</td><td>Mobile Number</td><td>Textbox</td><td>Yes</td><td>Mobile no. of the individual, multiple individuals can share the same mobile no.</td></tr><tr><td>6</td><td>Social Category</td><td>Drop-down</td><td>No</td><td>Social category of the individual.</td></tr><tr><td>7</td><td>Next</td><td>Action</td><td>Yes</td><td>An action button to go to the next page.</td></tr></tbody></table>

**Screen Mock-ups**

<div align="left"><img src="../../../../../.gitbook/assets/3 (15).png" alt=""></div>

**Individual’s Skill Details**

**Attributes**

A drop-down to select the skills for the individual is provided. An individual can have multiple skills.

**Screen Mock-ups**

<div align="left"><img src="../../../../../.gitbook/assets/4 (15).png" alt=""></div>

**Individual’s Photo**

**Attributes**

The option is provided to click the individual's photo from mobile and upload it, also individual’s photo can be uploaded from the phone gallery as well.

**Screen Mock-ups**

| <img src="../../../../../.gitbook/assets/5 (17).png" alt="" data-size="original"> | <img src="../../../../../.gitbook/assets/6 (18).png" alt="" data-size="original"> |
| --------------------------------------------------------------------------------- | --------------------------------------------------------------------------------- |

#### Location Details <a href="#id-480ahcaf25nf" id="id-480ahcaf25nf"></a>

**Attributes**

<table><thead><tr><th width="92">#</th><th width="118">Field Name</th><th width="122">Data Type</th><th width="148">Is Mandatory?</th><th>Description</th></tr></thead><tbody><tr><td>1</td><td>Pincode</td><td>Textbox</td><td>Yes</td><td>Pincode of the area the individual belongs to.</td></tr><tr><td>2</td><td>City</td><td>Drop-down</td><td>Yes</td><td>Name of city from where the individual belongs to.</td></tr><tr><td>3</td><td>Ward</td><td>Drop-down</td><td>Yes</td><td>Name of ward of selected city from where the individual belongs to.</td></tr><tr><td>4</td><td>Locality</td><td>Drop-down</td><td>Yes</td><td>Name of locality of selected city from where the individual belongs to.</td></tr><tr><td>5</td><td>Street Name</td><td>Textbox</td><td>No</td><td>Street name</td></tr><tr><td>6</td><td>Door Number</td><td>Textbox</td><td>No</td><td>Door number/ house number</td></tr><tr><td>7</td><td>Next</td><td>Action</td><td>Yes</td><td>An action button to go to the next page.</td></tr></tbody></table>

**Screen Mock-ups**

<div align="left"><img src="../../../../../.gitbook/assets/7 (15).png" alt=""></div>

#### Financial Details <a href="#ibz36i1vw36b" id="ibz36i1vw36b"></a>

**Attributes**

<table><thead><tr><th width="98">S.No.</th><th width="140">Field Name</th><th width="108">Data Type</th><th width="151">Is Mandatory?</th><th>Description</th></tr></thead><tbody><tr><td>1</td><td>Account holder name</td><td>Textbox</td><td>Yes</td><td>Name of account holder/ individual.</td></tr><tr><td>2</td><td>Account number</td><td>Textbox</td><td>Yes</td><td>Account number of the account holder.</td></tr><tr><td>3</td><td>Re-enter account number</td><td>Textbox</td><td>Yes</td><td>Option to re-enter the account number.</td></tr><tr><td>4</td><td>Account type</td><td>Radio</td><td>Yes</td><td>Account type, Savings/ Current.</td></tr><tr><td>5</td><td>IFSC</td><td>Textbox</td><td>Yes</td><td>IFSC of the bank and branch</td></tr><tr><td>7</td><td>Next</td><td>Action</td><td>Yes</td><td>An action button to go to the next page.</td></tr></tbody></table>

**Screen Mock-ups**

<div align="left"><img src="../../../../../.gitbook/assets/8 (16).png" alt=""></div>

{% hint style="info" %}
**Note:** Once all the details are furnished, the last summary page is displayed to check the details and make the corrections if need be. Once submitted, the success page is displayed containing the individual’s ID.
{% endhint %}

### Wage Seeker Modification <a href="#id-1qhwmovap5eg" id="id-1qhwmovap5eg"></a>

A wage seeker can only be modified by a ULB user who has access to do so. While modifying already existing wage seeker’s details proper identity details are captured and in the case of AADHAAR it is validated from the AADHAAR system.

### Screen Mock-ups <a href="#wgarw8nyv42c" id="wgarw8nyv42c"></a>

![](<../../../../../.gitbook/assets/9 (15).png>)

![](<../../../../../.gitbook/assets/10 (10).png>)

## Services <a href="#id-7nu3122ulyih" id="id-7nu3122ulyih"></a>

### **Authentication**

#### Sample requests and responses <a href="#j2ggjkk0tiq4" id="j2ggjkk0tiq4"></a>

**Request**

`"uid": "9999999999",`

`"uidType": "A /V /T /E",`

`"consent": "Y",`

`"subAuaCode": "0002590000",`

`"txn": "",`

`"isPI": "y/n",`

`"isBio": "y/n",`

`"isOTP": "y/n",`

`"bioType": "FMR/FIR/IIR`

`"name": "XXXXX XXXX XXXXX",`

`"dob": "XXXX-XX-XX",`

`"gender":"M",`

`"rdInfo": "xxxxxx",`

`"rdData": "xxxxxx",`

`"otpValue": "xxxxxx”`

**Response**

`"ret": "y/n",`

`"err": null/"xxx",`

`"status": "SUCCESS/ERROR",`

`"errMsg": null/"xxxxxx",`

`"txn": "xxxxxx",`

`"responseCode": "xxxxxx",`

`"uidToken": "xxxxxx",`

`"mobileNumber": null,`

`"email": null`
