# High Level Design

The platform architecture illustration below provides a visual representation of the key components and layers that facilitate a seamless flow of information across multiple departments.

<figure><img src="../../.gitbook/assets/image (37).png" alt=""><figcaption></figcaption></figure>

The high-level design of the Works System is divided into three main parts, the details of which are available below.

## **1. Master (Reference) Data**

This part includes various classifications of master data used in the Works platform. Some examples of this master data include:

* Organisation Class
* Organisation Functional Area
* Organisation Type
* Department
* Nature of Work
* Wage Seeker Skills
* Labour Charges
* Overheads
* Headcodes
* Applicable Charges
* Mode of Entrustment
* Beneficiary Type
* Designations
* Hierarchical Masters like Type of Work, Sub-type of Work
* Location (which is the same as DIGIT)

1. Works Registries (Services)
2. Reused/Enhanced DIGIT Core Services

{% hint style="info" %}
DIGIT core service masters are not covered here. Refer to the service documentation to find the comprehensive list of services.
{% endhint %}

## 2. Works Registries

This part comprises various registries that store information related to the Works platform. Some of the key registries are:

* [**Individual Registry**](low-level-design/registries/individual.md)**:** Stores details of individual citizens, whether or not they are DIGIT system users. In cases where login access is required, user accounts are created and stored separately in the User registry. This allows for a clear distinction between citizen data and user management within the system.
* [**Organisation Registry:**](low-level-design/registries/organization.md) Holds details of different types of organisations, their functional areas, and classes.
* [**Bank Accounts Registry:**](low-level-design/registries/bank-account.md) Stores bank account details securely for online payments.

### Works Services

This part includes domain-specific services (listed below) developed or planned for the Works platform.

* [Project](low-level-design/services/project.md)
* [Estimates](../../specifications/functional-specifications/estimates.md)
* [Contracts](low-level-design/services/contracts.md)
* [Attendance](low-level-design/services/attendance.md)
* [Muster roll](low-level-design/services/muster-roll.md)
* [Expense/Billing](low-level-design/services/expense.md)
* JIT Adapter&#x20;
* Milestones&#x20;
* Payment Calendar&#x20;
* [Measurement Book ](../../specifications/functional-specifications/measurements.md)

## 3. Reused/Enhanced DIGIT Services

This part lists the core services from DIGIT that are reused or enhanced in the Works Project. Some of these as-is reused DIGIT core services include:

* Master Data Management Service (MDMS)
* Location Service
* User Service
* Access Control Service
* Zuul API Gateway
* PDF Service
* File Store Service
* IdGen Service
* Persister
* Indexer
* Inbox Service
* Report Service

This architectural design ensures seamless data flow across multiple departments and provides a foundation for efficient and integrated operations within the Works Management System.
