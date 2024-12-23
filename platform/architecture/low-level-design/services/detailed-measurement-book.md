# Detailed Measurement Book

## Overview

The measurement book is a measure of progress in a Works contract. This service&#x20;

## API Specifications

### API Contract Link

{% file src="../../../../.gitbook/assets/Measurement-Book-v1.0.0.yaml" %}

### APIs

{% swagger src="../../../../.gitbook/assets/Measurement-Book-v1.0.0.yaml" path="/measurement/v1/_create" method="post" %}
[Measurement-Book-v1.0.0.yaml](../../../../.gitbook/assets/Measurement-Book-v1.0.0.yaml)
{% endswagger %}

{% swagger src="../../../../.gitbook/assets/Measurement-Book-v1.0.0.yaml" path="/measurement/v1/_update" method="post" %}
[Measurement-Book-v1.0.0.yaml](../../../../.gitbook/assets/Measurement-Book-v1.0.0.yaml)
{% endswagger %}

{% swagger src="../../../../.gitbook/assets/Measurement-Book-v1.0.0.yaml" path="/measurementservice/v1/_create" method="post" %}
[Measurement-Book-v1.0.0.yaml](../../../../.gitbook/assets/Measurement-Book-v1.0.0.yaml)
{% endswagger %}

{% swagger src="../../../../.gitbook/assets/Measurement-Book-v1.0.0.yaml" path="/measurementservice/v1/_update" method="post" %}
[Measurement-Book-v1.0.0.yaml](../../../../.gitbook/assets/Measurement-Book-v1.0.0.yaml)
{% endswagger %}

## Data Model&#x20;

### DB Schema Diagram

TBD

### Web Sequence Diagrams

#### Measurement Registry

{% tabs %}
{% tab title="Create" %}
<figure><img src="../../../../.gitbook/assets/MBRegistryCreateAPI.png" alt=""><figcaption></figcaption></figure>

<figure><img src="../../../../.gitbook/assets/MBRegistryCreateAPIValidation.png" alt=""><figcaption></figcaption></figure>
{% endtab %}

{% tab title="Update" %}
<figure><img src="../../../../.gitbook/assets/MBRegistryUpdateAPI.png" alt=""><figcaption></figcaption></figure>

<figure><img src="../../../../.gitbook/assets/MBRegistryUpdateAPIValidation.png" alt=""><figcaption></figcaption></figure>
{% endtab %}

{% tab title="Search" %}
<figure><img src="../../../../.gitbook/assets/MBRegistrySearchAPI.png" alt=""><figcaption></figcaption></figure>
{% endtab %}
{% endtabs %}

#### Measurement Service

{% tabs %}
{% tab title="Create" %}
<figure><img src="../../../../.gitbook/assets/MBServiceCreateAPI.png" alt=""><figcaption></figcaption></figure>

<figure><img src="../../../../.gitbook/assets/MBServiceCreateAPIValidation.png" alt=""><figcaption></figcaption></figure>
{% endtab %}

{% tab title="Update" %}
<figure><img src="../../../../.gitbook/assets/MBServiceUpdateAPI.png" alt=""><figcaption></figcaption></figure>
{% endtab %}

{% tab title="Search" %}
<figure><img src="../../../../.gitbook/assets/MBServiceSearchAPI (1).png" alt=""><figcaption></figcaption></figure>
{% endtab %}
{% endtabs %}
