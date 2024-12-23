---
description: New release features, enhancements, and fixes
---

# Release Notes

## Release Summary <a href="#release-summary" id="release-summary"></a>

DIGIT-Works release v1.1 release offers no functional changes. The non-functional changes in this release include the capability to migrate the Skills master to the Schedule of Rates master.&#x20;

**Functional changes**&#x20;

* NA

**Non-functional changes**&#x20;

* Migration of skills master to SOR master present in mdmsV2 - refer to [this page](data-migration.md) for the steps for data migration.

## Known Issues

Below is the list of known issues that need to be addressed as part of the platform roadmap:

* Multiple measurements can not be created at the same time for one contract.
* Integrated error queue implementation for all services along with the necessary measures for addressing issues, is required. In situations of unrecoverable failures, this setup will provide a means to trigger prompt alerts and implement corrective actions.
* Establishing alert mechanisms for critical errors, particularly in the context of billing, is required.
* Managing offline & low connectivity use cases as a best practice.
* The services should include the workflow as part of the payload and push it into the Kafka topic for persistence.
* Separate SMS-related localization from all services and migrate it to a dedicated service.
* Performance testing and benchmarking of services.
* Security audit.
* Multiple mdms-v2 calls in services are needed because mdms-v2 returns only one master response.
* Code refactoring of works-services like
  * Remove unused models
  * Change package names
  * Remove duplicate validation logic

## Document Resources and Links <a href="#document-resources-and-links" id="document-resources-and-links"></a>

### Technical Documents

| Doc links                                                             |
| --------------------------------------------------------------------- |
| [High Level Design](../../platform/architecture/high-level-design.md) |
| [Low Level Design](../../platform/architecture/low-level-design/)     |
| [Data Migration](data-migration.md)                                   |

