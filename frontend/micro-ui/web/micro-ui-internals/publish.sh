#!/bin/bash

BASEDIR="$(cd "$(dirname "$0")" && pwd)"

msg() {
  echo -e "\n\n\033[32;32m$1\033[0m"
}

msg "Removing the default package dependency file"
rm package.json

msg "replacing the default package dependency file"
cp publish-package.json package.json

msg "Pre-building all packages"
yarn 
# sleep 5

msg "Building and publishing css"
cd "$BASEDIR/packages/css" &&  yarn&& npm publish --tag mukta-2.1.2-uicomp-v0.2

# sleep 10
# msg "Updating dependencies"
# cd "$BASEDIR" && yarn upgrade -S @egovernments
# sleep 5

msg "Building and publishing AttendenceMgmt module"
cd "$BASEDIR/packages/modules/AttendenceMgmt" &&  rm -rf node_modules &&  rm -rf dist && yarn&& npm publish --tag mukta-2.1.2-uicomp-v0.2

msg "Building and publishing Expenditure module"
cd "$BASEDIR/packages/modules/Expenditure" &&  rm -rf node_modules &&  rm -rf dist && yarn&& npm publish --tag mukta-2.1.2-uicomp-v0.2

msg "Building and publishing RateAnalysis module"
cd "$BASEDIR/packages/modules/RateAnalysis" &&  rm -rf node_modules &&  rm -rf dist && yarn&& npm publish --tag mukta-2.1.2-uicomp-v0.2

msg "Building and publishing Contracts module"
cd "$BASEDIR/packages/modules/Contracts" &&  rm -rf node_modules &&  rm -rf dist && yarn&& npm publish --tag mukta-2.1.2-uicomp-v0.2

msg "Building and publishing Masters module"
cd "$BASEDIR/packages/modules/Masters" &&  rm -rf node_modules &&  rm -rf dist && yarn&& npm publish --tag mukta-2.1.2-uicomp-v0.2

msg "Building and publishing Works module"
cd "$BASEDIR/packages/modules/works" &&  rm -rf node_modules &&  rm -rf dist && yarn&& npm publish --tag mukta-2.1.2-uicomp-v0.2

msg "Building and publishing Project module"
cd "$BASEDIR/packages/modules/Project" &&  rm -rf node_modules &&  rm -rf dist && yarn && npm publish --tag mukta-2.1.2-uicomp-v0.2

msg "Building and publishing Estimate module"
cd "$BASEDIR/packages/modules/Estimate" &&  rm -rf node_modules &&  rm -rf dist && yarn && npm publish --tag mukta-2.1.2-uicomp-v0.2

msg "Building and publishing Measurement module"
cd "$BASEDIR/packages/modules/Measurement" &&  rm -rf node_modules &&  rm -rf dist && yarn && npm publish --tag mukta-2.1.2-uicomp-v0.2
