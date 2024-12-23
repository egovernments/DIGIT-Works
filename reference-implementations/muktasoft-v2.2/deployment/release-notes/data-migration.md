# Data Migration

## Rates Migration to SOR

{% hint style="info" %}
This migration is mukta-specific only.
{% endhint %}

To convert Labour Skills rates to SOR rates in MDMS-v2, we need to map skill\_codes to sorId. We'll use skill\_codes to get rates from MDMS v1 and sorId to upload them into MDMS v2. Upload an Excel file in the required format and execute the curl command.\
Note: The migration script is already present in our earlier build; only the input file will be changed.

### Migration Steps

To migrate skills rate follow the steps given below:

1. **Create skill\_codes and sorId mapping** **:**&#x20;
   *   An Excel sheet is with fields (heads, rates as total, Sor Id, Valid From, and Valid To).\
       Example:&#x20;

       | LC.7 | MHA.3 | ADC.4 | CA.9 | EMF.6 | DMF.5 | MA.2 | LA.1 | RA.8 | Total | Sor Id      | Valid From | Valid To   |
       | ---- | ----- | ----- | ---- | ----- | ----- | ---- | ---- | ---- | ----- | ----------- | ---------- | ---------- |
       | 0    | 0     | 0     | 0    | 0     | 0     | 0    | 345  | 0    | 345   | SOR\_000290 | 22-04-2023 | 30-09-2023 |


2. **Use the Specific Build**:
   * egovio/bulk-upload:UAT-f91e96b8-18&#x20;
   * [click here](https://github.com/odisha-muktasoft/MUKTA_IMPL/tree/UAT/utilities/bulk-upload) to access the branch and change details
3. **Build and Deploy**:
   * Deploy the build provided above or build and deploy from the code linked above if custom changes are required.
   * Upload the Excel file in the body.
   * Use the curl below to migrate the data.

```
curl --location 'localhost:8098/bulk-upload/_bulkUpload?schemaCode=WORKS-SOR.Rates&tenantId=pg.citya' \
--form 'file=@"/path/to/file"'
```

4. **Verification in DB:**\
   Query on the eg\_mdms\_data table and check if the Rates uploaded are present in the data.\
   \
   Example:\
   select \* from eg\_mdms\_data where schemacode ='WORKS-SOR.Rates' and data- >>'sorId' = 'Eg.SOR\_000002';



## Upload SOR Composition

Works SOR requires SOR Composition, for existing SORs it requires uploading data using script.

### Migration Steps

1. Clone DIGIT-Works repository in your local [https://github.com/egovernments/Digit-works](https://github.com/egovernments/Digit-works)
2. Checkout to the branch `sor-composition-migration`  &#x20;
3. Create an excel file same as below sheet and fill the details\
   [https://github.com/egovernments/DIGIT-Works/blob/sor-composition-migration/utilities/sor-composition/Rate\_analysis\_for\_composition.xlsx](https://github.com/egovernments/DIGIT-Works/blob/sor-composition-migration/utilities/sor-composition/Rate_analysis_for_composition.xlsx)
4. Port-forward MDMS-v2&#x20;
5. Update these details in .env
   1. MDMS\_V2\_HOST url
   2. MDMS\_v2\_SOR\_COMPOSITION\_CREATE url if it's changed - Make sure that  `WORKS-SOR.Composition` schema is created
6. Install dependencies on your local\
   `pip3 install -f requirements.txt`
7. Run `main.py` file\


