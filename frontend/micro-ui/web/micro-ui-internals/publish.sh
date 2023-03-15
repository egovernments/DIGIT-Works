#!/bin/bash

BASEDIR="$(cd "$(dirname "$0")" && pwd)"

msg() {
  echo -e "\n\n\033[32;32m$1\033[0m"
}

# msg "Pre-building all packages"
# yarn build
# sleep 5

# msg "Building and publishing css"
# cd "$BASEDIR/packages/css" && yarn publish --access public

# msg "Building and publishing libraries"
# cd "$BASEDIR/packages/libraries" && yarn publish --access public

# msg "Building and publishing react-components"
# cd "$BASEDIR/packages/react-components" && yarn publish --access public

# sleep 10
# msg "Updating dependencies"
# cd "$BASEDIR" && yarn upgrade -S @egovernments
# sleep 5

msg "Building and publishing AttendenceMgmt module"
cd "$BASEDIR/packages/modules/AttendenceMgmt" && yarn && yarn prepublish && npm publish --tag works-1.0

msg "Building and publishing Expenditure module"
cd "$BASEDIR/packages/modules/Expenditure" && yarn && yarn prepublish && npm publish --tag works-1.0

msg "Building and publishing Contracts module"
cd "$BASEDIR/packages/modules/Contracts" && yarn && yarn prepublish && npm publish --tag works-1.0

msg "Building and publishing Masters module"
cd "$BASEDIR/packages/modules/Masters" && yarn && yarn prepublish && npm publish --tag works-1.0

msg "Building and publishing Works module"
cd "$BASEDIR/packages/modules/works" && yarn && yarn prepublish && npm publish --tag works-1.0

msg "Building and publishing Project module"
cd "$BASEDIR/packages/modules/Project" && yarn && yarn prepublish && npm publish --tag works-1.0

msg "Building and publishing Estimate module"
cd "$BASEDIR/packages/modules/Estimate" && yarn && yarn prepublish && npm publish --tag works-1.0
