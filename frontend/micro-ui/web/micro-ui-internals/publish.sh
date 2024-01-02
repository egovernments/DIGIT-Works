#!/bin/bash

BASEDIR="$(cd "$(dirname "$0")" && pwd)"

msg() {
  echo -e "\n\n\033[32;32m$1\033[0m"
}

# msg "Pre-building all packages"
# yarn build
# sleep 5

msg "Building and publishing css"
cd "$BASEDIR/packages/css" && yarn publish --access public

# msg "Building and publishing libraries"
# cd "$BASEDIR/packages/libraries" && yarn publish --access public

# msg "Building and publishing react-components"
# cd "$BASEDIR/packages/react-components" && yarn publish --access public

# sleep 10
# msg "Updating dependencies"
# cd "$BASEDIR" && yarn upgrade -S @egovernments
# sleep 5

msg "Building and publishing AttendenceMgmt module"
cd "$BASEDIR/packages/modules/AttendenceMgmt" &&  rm -rf node_modules &&  rm -rf dist && yarn&& npm publish --tag works-1.0

msg "Building and publishing Expenditure module"
cd "$BASEDIR/packages/modules/Expenditure" &&  rm -rf node_modules &&  rm -rf dist && yarn&& npm publish --tag works-1.0

msg "Building and publishing Contracts module"
cd "$BASEDIR/packages/modules/Contracts" &&  rm -rf node_modules &&  rm -rf dist && yarn&& npm publish --tag works-1.0

msg "Building and publishing Masters module"
cd "$BASEDIR/packages/modules/Masters" &&  rm -rf node_modules &&  rm -rf dist && yarn&& npm publish --tag works-1.0

msg "Building and publishing Works module"
cd "$BASEDIR/packages/modules/works" &&  rm -rf node_modules &&  rm -rf dist && yarn&& npm publish --tag works-1.0

msg "Building and publishing Project module"
cd "$BASEDIR/packages/modules/Project" &&  rm -rf node_modules &&  rm -rf dist && yarn && npm publish --tag works-1.0

msg "Building and publishing Estimate module"
cd "$BASEDIR/packages/modules/Estimate" &&  rm -rf node_modules &&  rm -rf dist && yarn && npm publish --tag works-1.0

msg "Building and publishing Measurement module"
cd "$BASEDIR/packages/modules/Measurement" &&  rm -rf node_modules &&  rm -rf dist && yarn && npm publish --tag works-1.0
