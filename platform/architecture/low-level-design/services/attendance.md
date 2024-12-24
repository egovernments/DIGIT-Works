---
description: Description of the attendance service
---

# Attendance

## Overview

Attendance service allows users to maintain attendance registers, enrol individuals, create, update or search attendance logs and manage staff permissions.

## API Specifications

**Base Path:** /attendance/

### API Contract Link

{% embed url="https://editor.swagger.io/?url=https://raw.githubusercontent.com/egovernments/DIGIT-Works/master/backend/attendance/Attendance-Service-1.0.0.yaml" %}

## Data Model

### DB Schema Diagram

<figure><img src="../../../../.gitbook/assets/image (40).png" alt=""><figcaption></figcaption></figure>

## Web Sequence Diagram

#### Attendance Register

{% tabs %}
{% tab title="Create" %}
<figure><img src="../../../../.gitbook/assets/Attendance-Register Create.png" alt=""><figcaption></figcaption></figure>


{% endtab %}

{% tab title="Update" %}
<figure><img src="../../../../.gitbook/assets/Attendance-Register Update (1).png" alt=""><figcaption></figcaption></figure>


{% endtab %}

{% tab title="Search" %}
<figure><img src="../../../../.gitbook/assets/Attendance-Register Search.png" alt=""><figcaption></figcaption></figure>


{% endtab %}
{% endtabs %}

#### Staff

{% tabs %}
{% tab title="Create" %}
<figure><img src="../../../../.gitbook/assets/Staff Create.png" alt=""><figcaption></figcaption></figure>


{% endtab %}

{% tab title="Update/Delete" %}
<figure><img src="../../../../.gitbook/assets/Staff Delete.png" alt=""><figcaption></figcaption></figure>


{% endtab %}
{% endtabs %}

#### Attendee

{% tabs %}
{% tab title="Create" %}
<figure><img src="../../../../.gitbook/assets/Attendee Create.png" alt=""><figcaption></figcaption></figure>


{% endtab %}

{% tab title="Delete" %}
<figure><img src="../../../../.gitbook/assets/Attendee Delete.png" alt=""><figcaption></figcaption></figure>


{% endtab %}
{% endtabs %}

#### Attendance Log

{% tabs %}
{% tab title="Create" %}
<figure><img src="../../../../.gitbook/assets/Attendance Log Create.png" alt=""><figcaption></figcaption></figure>


{% endtab %}

{% tab title="Update" %}
<figure><img src="../../../../.gitbook/assets/Attendance Log Update.png" alt=""><figcaption></figcaption></figure>


{% endtab %}

{% tab title="Search" %}
<figure><img src="../../../../.gitbook/assets/Attendance Log Search.png" alt=""><figcaption></figcaption></figure>


{% endtab %}
{% endtabs %}

## Related Topics

* [Functional specifications - Attendance](../../../../specifications/functional-specifications/attendance-management.md)
* [Attendance module service configuration](../../../../setup/configure-works/service-configuration/attendance.md)
* [Attendance UI configuration](../../../../reference-implementations/muktasoft-v2.2/deployment/configuration/ui-configuration/modules/attendance-cbo-application.md) - for MuktaSoft
* [Attendance employee user manual](../../../../reference-implementations/muktasoft-v2.2/implementation/training-resources/user-manual/mobile-application-user-manual/cbo-getting-started/track-attendance.md) - for MuktaSoft
