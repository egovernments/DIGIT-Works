# Dashboard Reindexing

## Overview

Dashboard reindexing is a way to refresh and update both wage bill and muster roll indices once the "gender" field is added to the additional details of an individual. The following sections explain how to do this.

## Reindex Elastic Search Indices - Steps

1. In Elasticsearch, back up the original index and create a new migrate index.
2. Define a new migrate topic in the indexer and link it to the migrate index.
3. Ensure you have Python3 installed on your local system.
4. Start a Kafka client and set up port forwarding for Elasticsearch, pointing Kafka bootstrap servers to the Elasticsearch port (usually localhost:9200).
5. Launch an indexer service using indexer files.
6. Retrieve data from services via API, either in bulk or individual search.
7. Formulate an insert request for the indexer, whether for bulk or single data.
8. Send the insert request to the migrate topic.
9. Verify the data in the migrate index.
10. Delete the original index, create a new one, and transfer data from the migrate index to the new original index.

These steps help you update and manage Elasticsearch indices effectively.

## Services To Reindex

* expense-bill-index
* muster-inbox

## Reindexing Scripts

Refer to [Reindex](https://github.com/odisha-muktasoft/MUKTA_IMPL/tree/UAT/utilities/reindex) and run the migrate scripts using python3.

