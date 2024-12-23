---
description: This page describes the low level design for the SOR and rates services
---

# Schedule of Rates (archive)

## Overview

Schedule of rates are defined by the state to provide detailed specifications for materials, labour, machinery etc..Rates are also defined at the state level but can be customized at the ULB level. This service provides a way to create/update/search for SORs and add rates to the SORs.

## API Specifications

### API Contract Link

{% file src="../../../../.gitbook/assets/sor-rates-1.0.0 (2).yaml" %}

### APIs

{% swagger src="../../../../.gitbook/assets/sor-rates-1.0.0 (2).yaml" path="/sor/v1/_create" method="post" %}
[sor-rates-1.0.0 (2).yaml](<../../../../.gitbook/assets/sor-rates-1.0.0 (2).yaml>)
{% endswagger %}

{% swagger src="../../../../.gitbook/assets/sor-rates-1.0.0 (2).yaml" path="/sor/v1/_update" method="post" %}
[sor-rates-1.0.0 (2).yaml](<../../../../.gitbook/assets/sor-rates-1.0.0 (2).yaml>)
{% endswagger %}

{% swagger src="../../../../.gitbook/assets/sor-rates-1.0.0 (2).yaml" path="/sor/v1/_search" method="post" %}
[sor-rates-1.0.0 (2).yaml](<../../../../.gitbook/assets/sor-rates-1.0.0 (2).yaml>)
{% endswagger %}

{% swagger src="../../../../.gitbook/assets/sor-rates-1.0.0 (2).yaml" path="/sor/rate/v1/_create" method="post" %}
[sor-rates-1.0.0 (2).yaml](<../../../../.gitbook/assets/sor-rates-1.0.0 (2).yaml>)
{% endswagger %}

{% swagger src="../../../../.gitbook/assets/sor-rates-1.0.0 (2).yaml" path="/sor/rate/v1/_update" method="post" %}
[sor-rates-1.0.0 (2).yaml](<../../../../.gitbook/assets/sor-rates-1.0.0 (2).yaml>)
{% endswagger %}

{% swagger src="../../../../.gitbook/assets/sor-rates-1.0.0 (2).yaml" path="/sor/rate/v1/_search" method="post" %}
[sor-rates-1.0.0 (2).yaml](<../../../../.gitbook/assets/sor-rates-1.0.0 (2).yaml>)
{% endswagger %}

## Data Model&#x20;

### DB Schema Diagram

<figure><img src="../../../../.gitbook/assets/SOR.png" alt=""><figcaption></figcaption></figure>

### Web Sequence Diagrams

#### SOR APIs

{% tabs %}
{% tab title="SOR Create" %}
<figure><img src="../../../../.gitbook/assets/SORServiceCreateSORAPI.png" alt=""><figcaption></figcaption></figure>

<figure><img src="../../../../.gitbook/assets/SORServiceCreateSORAPIValidation.png" alt=""><figcaption></figcaption></figure>
{% endtab %}

{% tab title="SOR Update" %}
<figure><img src="../../../../.gitbook/assets/SORServiceUpdateSORAPI.png" alt=""><figcaption></figcaption></figure>

<figure><img src="../../../../.gitbook/assets/SORServiceUpdateSORAPIValidation.png" alt=""><figcaption></figcaption></figure>
{% endtab %}

{% tab title="SOR Search" %}
<figure><img src="../../../../.gitbook/assets/SORServiceSearchSORAPI.png" alt=""><figcaption></figcaption></figure>
{% endtab %}
{% endtabs %}

#### Rates

{% tabs %}
{% tab title="Create Rates" %}
<figure><img src="../../../../.gitbook/assets/SORServiceCreateRateAPI.png" alt=""><figcaption></figcaption></figure>

<figure><img src="../../../../.gitbook/assets/SORServiceCreateRateAPIValidation.png" alt=""><figcaption></figcaption></figure>
{% endtab %}

{% tab title="Update Rates" %}
<figure><img src="../../../../.gitbook/assets/SORServiceUpdateRateAPI.png" alt=""><figcaption></figcaption></figure>

<figure><img src="../../../../.gitbook/assets/SORServiceUpdateRateAPIValidation.png" alt=""><figcaption></figcaption></figure>
{% endtab %}

{% tab title="Search Rates" %}
<figure><img src="../../../../.gitbook/assets/SORServiceSearchRateAPI.png" alt=""><figcaption></figcaption></figure>
{% endtab %}
{% endtabs %}

### Master Data Types

The following masters need to be created as part of this module:

* SOR Type
* SOR Subtype
* Unit of measurement
