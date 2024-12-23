# CSS Customisation

**Overview**

This document describes how to publish CSS if there is any CSS Customization/changes.

&#x20;

While Customizing, if any changes are made In the CSS folder it has to be compiled and published to npm.

Currently, the CSS was published in npm as **@egovernments/digit-ui-works-css**\
Please check this NPM link [works-css](https://www.npmjs.com/package/@egovernments/digit-ui-works-css).

&#x20;

So if any changes are made to the CSS folder locally have to be published in different Organisations and in the same or different package name.

&#x20;

ie as @xyz/digit-ui-works-css and version as 1.0.0

then following changes has to be made in the code to reflect in the digit-ui&#x20;

[index.html](https://github.com/egovernments/DIGIT-Works/blob/master/frontend/micro-ui/web/public/index.html)  file location

_**frontend/micro-ui/web/public/index.html**_

style sheet link has to be updated as follow,

`<link rel="stylesheet" href="https://unpkg.com/@xyz/digit-ui-works-css@1.0.0/dist/index.css"/>`\
\


Use Either of the Following commands to publish the CSS

* In the `frontend/micro-ui/web/micro-ui-internals` folder run\
  `yarn run publish:css`\
  or
* In the `frontend/micro-ui/web/micro-ui-internals/packages/css` folder run\
  `yarn run publish --access public`

**Reference** Doc for Publishing any package to npm

[![](https://docs.npmjs.com/favicon-32x32.png?v=f44ec608ba91563f864a30a276cd9065)Creating and publishing scoped public packages | npm Docs](https://docs.npmjs.com/creating-and-publishing-scoped-public-packages)
