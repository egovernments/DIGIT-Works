FROM python:3.11.9-alpine3.20
# Install gcc and other necessary build tools
RUN apk add --no-cache gcc musl-dev libc-dev librdkafka-dev
WORKDIR /app/cron/
COPY requirements.txt .
RUN pip3 install -r requirements.txt
COPY .env .env
COPY transformer.py transformer.py