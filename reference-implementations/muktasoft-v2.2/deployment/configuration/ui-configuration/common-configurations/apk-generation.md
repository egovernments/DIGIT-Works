---
description: Steps to generate APK
---

# APK Generation

## Overview

This document says how to generate an APK and its configuration.

## Generate APK - Steps

1. Add the .env file at the root of the project with the required config based on the env in the file.

Sample env config:&#x20;

```
BASE_URL='https://works-dev.digit.org/'
MDMS_API_PATH='egov-mds-service/v1/_search'
GLOBAL_ASSETS='https://s3.ap-south-1.amazonaws.com/works-dev-asset/worksGlobalConfig.json'
ENV_NAME="DEV"

```

2. Run the below commands  in your terminal from the root of the project&#x20;

```
flutter clean
flutter pub get
```

3. Run the below command in your terminal from the root of the project to generate the APK&#x20;

```
flutter build apk --release
```

