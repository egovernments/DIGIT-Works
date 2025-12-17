import csv
import pandas as pd
from numpy import *
import requests
import json
from dateutil import parser
import datetime as dt
import psycopg2
import logging
import calendar
import time
import os
import pytz
import pytz
from datetime import datetime
import re
from dotenv import load_dotenv

load_dotenv('.env')

# Replace these with your PostgreSQL database details
DB_HOST = os.getenv('DB_HOST')
DB_PORT = os.getenv('DB_PORT')
DB_NAME = os.getenv('DB_NAME')
DB_USER = os.getenv('DB_USER')
DB_PASSWORD = os.getenv('DB_PASSWORD')

# Connect to PostgreSQL
def connect_to_database():
    return psycopg2.connect(
        host=DB_HOST, port=DB_PORT, database=DB_NAME, user=DB_USER, password=DB_PASSWORD
    )

def data_migration():
    try:
        print("Data deletion started")
        print(DB_HOST, DB_PORT, DB_NAME, DB_USER, DB_PASSWORD)
        connection = connect_to_database()
        cursor = connection.cursor()
        print("Connection established successfully")

        # cursor.execute("""
            # SELECT contract_id 
            # FROM (
            #     SELECT tenant_id, contract_id, estimate_line_item_id, COUNT(*) 
            #     FROM eg_wms_contract_line_items 
            #     WHERE status = 'ACTIVE' 
            #     GROUP BY tenant_id, contract_id, estimate_line_item_id
            # ) AS tmp 
            # WHERE COUNT > 1 
            # GROUP BY tenant_id, contract_id 
            # ORDER BY tenant_id
        # """)
        # contract_ids = cursor.fetchall()

        contract_ids = [
            "07e37b88-7778-4707-9cfa-c1fcfe509404"
        ]
        
        # Dictionary to store unique estimate_line_item_id for each contract_id
        unique_items = {}

        for contract_id in contract_ids:
            print(f"Migrating contract: {contract_id}")

            cursor.execute("""
                SELECT id, estimate_line_item_id 
                FROM eg_wms_contract_line_items 
                WHERE contract_id = %s
            """, (contract_id,))
            rows = cursor.fetchall()

            seen_estimate_line_item_ids = set()
            unique_items[contract_id] = []
            line_items_ids_to_delete = []

            for row in rows:
                id, estimate_line_item_id = row
                print(f"line_item_id: {id}")
                if estimate_line_item_id not in seen_estimate_line_item_ids:
                    unique_items[contract_id].append((id, estimate_line_item_id))
                    seen_estimate_line_item_ids.add(estimate_line_item_id)
                else:
                    line_items_ids_to_delete.append(id)

            for id in line_items_ids_to_delete:
                cursor.execute("""
                    DELETE FROM eg_wms_contract_amount_breakups 
                    WHERE line_item_id = %s
                """, (id,))
                cursor.execute("""
                    DELETE FROM eg_wms_contract_line_items 
                    WHERE id = %s
                """, (id,))
                print(f"Deleted line_item_id: {id}")

        connection.commit()
        cursor.close()
        connection.close()

        print("Migration completed successfully!")

    except Exception as e:
        print(f"An error occurred: {str(e)}")



if __name__ == "__main__":
    try:
        logging.info('migration statrted')

        data_migration()


    except Exception as ex:
        logging.error("Exception occured on main.", exc_info=True)
        raise(ex)