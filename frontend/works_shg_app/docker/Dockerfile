# Docker flutter tags https://hub.docker.com/r/cirrusci/flutter/tags?page=1&name=1.16

FROM cirrusci/flutter:3.3.9 AS build
ARG WORK_DIR
WORKDIR /app
# copy the project files
COPY ${WORK_DIR} .
RUN flutter doctor
RUN flutter pub get
RUN flutter build web
# Create runtime image
FROM nginx:mainline-alpine
ENV WEB_DIR=/var/web/works_shg_app
#RUN mkdir -p ${WEB_DIR}
COPY --from=build /app/build/web/ ${WEB_DIR}/
COPY --from=build /app/docker/nginx.conf /etc/nginx/conf.d/default.conf

