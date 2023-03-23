FROM egovio/alpine-node-builder-14:yarn

ARG WORK_DIR
ENV npm_config_cache=/tmp
WORKDIR /app

COPY ${WORK_DIR}/package.json ${WORK_DIR}/yarn.lock .

RUN yarn

RUN ls -lah

COPY ${WORK_DIR}/src .

CMD ["yarn", "start"]
