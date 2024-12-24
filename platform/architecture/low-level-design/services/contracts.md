---
description: Work related contracts
---

# Contracts

## Overview

The Contracts service allows users to enter, update, search and store functional details linked to works contracts.

## API Specifications

**Base path:**`/contracts`

### API Contract Link

API spec [YAML is here.](https://raw.githubusercontent.com/egovernments/DIGIT-Specs/master/Domain%20Services/Works/Contract-Service-v1.0.0.yaml) Click below to view it in Swagger Editor.

{% embed url="https://editor.swagger.io/?url=https://raw.githubusercontent.com/egovernments/DIGIT-Specs/master/Domain+Services/Works/Contract-Service-v1.0.0.yaml" %}

## High Level Design

#### Flow for revised contracts

<figure><img src="../../../../.gitbook/assets/ContractRevisionHLD.png" alt=""><figcaption><p>High level design diagram</p></figcaption></figure>

## Data Model&#x20;

### DB Schema Diagram

<figure><img src="../../../../.gitbook/assets/Revision Contract db diagram.png" alt=""><figcaption></figcaption></figure>

### Web Sequence Diagrams

{% tabs %}
{% tab title="Create" %}
<figure><img src="https://github.com/egovernments/DIGIT-Works/blob/master/backend/contracts/docs/SequenceDiagrams/pngs/ContractCreation.png?raw=true" alt=""><figcaption></figcaption></figure>
{% endtab %}

{% tab title="Update" %}
<figure><img src="https://github.com/egovernments/DIGIT-Works/blob/master/backend/contracts/docs/SequenceDiagrams/pngs/ContractUpdation.png?raw=true" alt=""><figcaption></figcaption></figure>
{% endtab %}

{% tab title="Search" %}
<figure><img src="../../../../.gitbook/assets/Contract Search.png" alt=""><figcaption></figcaption></figure>


{% endtab %}
{% endtabs %}

#### Creation & Consumption Of Revised Contracts

Below are the sequence diagrams for the creation and consumption of revision contracts.&#x20;

{% tabs %}
{% tab title="Creation of revision contract" %}
<figure><img src="../../../../.gitbook/assets/Create Revision Contract.png" alt=""><figcaption></figcaption></figure>
{% endtab %}

{% tab title="Consumption of revisions" %}
<figure><img src="../../../../.gitbook/assets/ConsumeRevisions2.png" alt=""><figcaption></figcaption></figure>
{% endtab %}
{% endtabs %}

## Related Topics

* [Functional specifications - Contracts](../../../../specifications/functional-specifications/contracts.md)
* [Contracts module service configuration](../../../../setup/configure-works/service-configuration/contract.md)
* [Contracts UI configuration - for MuktaSoft](../../../../reference-implementations/muktasoft-v2.2/deployment/sms-adapter-services/contact-notification-service.md)
* [Contracts user stories](../../../../reference-implementations/muktasoft-v2.2/specifications/functional-requirements/user-stories/work-order/) - for MuktaSoft
