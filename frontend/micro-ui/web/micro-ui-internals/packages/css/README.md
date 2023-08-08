<!-- TODO: update this -->

# digit-ui-works-css

## Install

```bash
npm install --save @egovernments/digit-ui-works-css
```

## Limitation

```bash
This Package is more specific to DIGIT-UI's can be used across mission's
It is the base css for Works UI
Parent CSS would be digit-ui-css(https://www.npmjs.com/package/@egovernments/digit-ui-css)
```

## Usage

After adding the dependency make sure you have this dependency in

```bash
frontend/micro-ui/web/package.json
```

```json
"@egovernments/digit-ui-works-css":"^0.2.0",
```

then navigate to App.js

```bash
frontend/micro-ui/web/public/index.html
```

```jsx
/** add this import **/

  <link rel="stylesheet" href="https://unpkg.com/@egovernments/digit-ui-works-css@0.2.0/dist/index.css" />

```
# Changelog

```bash
0.2.12 added dss for Table chart
0.2.11 added the style for dss metric chart
0.2.10 fixed minor issues
0.2.7 added the readme file
0.2.6 base version
```

## Published from DIGIT Works 
Digit Dev Repo (https://github.com/egovernments/DIGIT-Works)

## License

MIT © [jagankumar-egov](https://github.com/jagankumar-egov)