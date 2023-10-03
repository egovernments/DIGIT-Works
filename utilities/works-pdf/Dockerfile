FROM egovio/alpine-node-builder-16:yarn

ARG WORK_DIR
ENV npm_config_cache=/tmp
WORKDIR /app

COPY ${WORK_DIR}/package.json ${WORK_DIR}/yarn.lock

RUN apk --no-cache add npm && \
    npm install -g yarn

RUN yarn install

# CMD ["npm", "install" "-f"]
# CMD ["npm", "audit" "fix"]

COPY ${WORK_DIR}/src .

CMD ["yarn", "start"]
