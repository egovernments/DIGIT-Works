# Revising Rate

## Scope

Writing the rate calculation logic and a scheduler to revise the rates for pending revise rate SORs on demand.

## Details <a href="#details" id="details"></a>

1. Search SOR is used to search and schedule the revision of rates.
2. It runs on demand and triggered by the user from pending rate revision screen.
3. User complete the rate revision of all required material, labour, and machinery SORs using edit SOR rate before calling for this JOB.
4. User search the works SORs which are to be revised.
5. User initiate the action by clicking on “Revise Rate” and select for the Effective Date for rates.
6. Revision rate JOB gets scheduled and runs in the back-end and only those SORs picked which has a different existing rate from calculated rate.
7. Revise rate scheduler runs in the back-ground, system generates the new rates and then link with the SOR with new effective date.
8. All the errors encountered during revision are captured and reported.
9. In case there is a rate already exists for same given effective date and existing rate is different from new generated rate, existing rate is inactivated and new rate is made effective from given effective date.
10. In case a rate exists from past effective date, existing rate is closed and new rate is added and made effective from given effective date.
11. In case no rate, new rate is generated and added from given effective date.
12. Users can see the progress and status of JOB from view rate revision JOBs screen.

### Actions <a href="#actions" id="actions"></a>

Revise Rate

On revise rate, all the components of rates are calculated and added/update to SOR.&#x20;

### Validations <a href="#validations" id="validations"></a>

1. All the validations applicable for add/edit SOR rates.
2. All the errors encountered are captured and reported.
3. For rates effective date should not be a date before current rate effective date.
4. Effective from time is always start of the day i.e. 00:00.&#x20;
5. Effective to date, the time is always 11:59:59.

### Configuration <a href="#configuration" id="configuration"></a>

The components/ heads defined to calculate the SOR rates are as given below.

1. Basic Rate
2. Conveyance
3. Royalty on Minerals
4. Environment Management Fund (EMF)
5. District Mineral Fund (DMF)
6. Additional Charges
7. Labour Cess

#### Calculation Logic

Basic Rate

1. Rate Analysis Basic Rate is calculated for the quantity define for analysis.
2. SOR Basic Rate = (Basic Rate\*Quantity defined for SOR)/ (Quantity defined for Rate Analysis).

Conveyance

1. Conveyance is calculated for the quantity define for analysis.
2. SOR Conveyance = (Conveyance\*Quantity defined for SOR)/ (Quantity defined for Rate Analysis).

Royalty on Minerals

1. Royalty is calculated for the quantity define for analysis.
2. SOR Royalty = (Royalty\*Quantity defined for SOR)/ (Quantity defined for Rate Analysis).

EMF

1. EMF is calculated for the quantity define for analysis.
2. SOR EMF= (EMF\*Quantity defined for SOR)/ (Quantity defined for Rate Analysis).

DMF

1. DMF is calculated for the quantity define for analysis.
2. SOR DMF= (DMF\*Quantity defined for SOR)/ (Quantity defined for Rate Analysis).

Additional Charges

1. Additional charges is calculated for the quantity define for analysis.
2. SOR Additional charges= (Additional charges\*Quantity defined for SOR)/ (Quantity defined for Rate Analysis).

Labour Cess

SOR Labour Cess = 1% of (SOR Basic Rate + SOR Conveyance + SOR Royalty + SOR EMF + SOR DMF + SOR Additional charges);

SOR Rate = Basic Rate + Conveyance + Royalty + EMF + DMF + Additional Charges + Labour Cess;

### Notifications <a href="#notifications" id="notifications"></a>

Not applicable

### User Interface <a href="#userinterface" id="userinterface"></a>

<figure><img src="../../../../../../.gitbook/assets/Revise Rates.jpg" alt=""><figcaption></figcaption></figure>

View Scheduled JOBs

<figure><img src="../../../../../../.gitbook/assets/View Jobs.png" alt=""><figcaption></figcaption></figure>

## Acceptance Criteria <a href="#acceptancecriteria" id="acceptancecriteria"></a>

1. Scheduler runs when triggered by user.
2. It reports the issues found during execution.
3. View scheduled job screen is provided to view all the jobs.
