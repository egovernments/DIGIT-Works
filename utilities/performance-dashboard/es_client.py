# es_client.py
import requests
from requests.auth import HTTPBasicAuth
import urllib3
import os
import json
from dotenv import load_dotenv

load_dotenv('.env')
# Suppress warnings related to unverified HTTPS requests
urllib3.disable_warnings(urllib3.exceptions.InsecureRequestWarning)

ES_URL = os.getenv('ES_URL')
ES_USERNAME = os.getenv('ES_USERNAME')
ES_PASSWORD = os.getenv('ES_PASSWORD')
def get_es_session():
    # Initialize a session
    session = requests.Session()
    session.verify = False  # Disable SSL verification

    # Set basic authentication if needed
    session.auth = HTTPBasicAuth(ES_USERNAME, ES_PASSWORD)

    # Base URL of the Elasticsearch cluster
    session.base_url = ES_URL

    return session


def search_es(index_name, query):
    session = get_es_session()
    url = f"{session.base_url}/{index_name}/_search"

    headers = {
        "Content-Type": "application/json"
    }

    response = session.post(url, headers=headers, data=json.dumps(query))

    # Check for HTTP request errors
    response.raise_for_status()

    return response.json()