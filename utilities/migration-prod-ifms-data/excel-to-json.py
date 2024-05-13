import pandas as pd
import json
import os


def process_data(data_dict):
    data = {}
    for i in data_dict:
        if i['PAYMENT_NO'] not in data:
            data[i['PAYMENT_NO']] = {}
        jit_bill_details = {}
        if i["JIT_BILL_NUMBER"] not in data[i['PAYMENT_NO']]:
            data[i['PAYMENT_NO']][i["JIT_BILL_NUMBER"]] = {}
            jit_bill_details = {
                "PAYMENT_NO": i['PAYMENT_NO'],
                "JIT_BILL_NUMBER": i["JIT_BILL_NUMBER"],
                "JIT_BILL_DATE": i["JIT_BILL_DATE"],
                "SSU_IA_ID": i["SSU_IA_ID"],
                "HOA": i["HOA"],
                "GROSS_AMOUNT": i["BILL_GROSS_AMOUNT"],
                "MST_ALLOTMENT_DIST_ID": i["MST_ALLOTMENT_DIST_ID"],
                "AW_ALLOTMENT_DIST_ID": i["AW_ALLOTMENT_DIST_ID"],
                "TXN_SERIAL_NO": i["TXN_SERIAL_NO"],
                "beneficiaryDetails": []
            }
        else:
            jit_bill_details = data[i['PAYMENT_NO']][i["JIT_BILL_NUMBER"]]

        beneficiary = {
            "BENF_ID": i["BENF_ID"],
            "BENF_AMOUNT": i["BENF_AMOUNT"]
        }
        status = i.get('PAYMENT_STATUS', None)
        if status == None:
            beneficiary['STATUS'] = 'INITIATED'
        elif status == 'P':
            beneficiary['STATUS'] = 'SUCCESSFUL'
        elif status == 'F':
            beneficiary['STATUS'] = 'FAILED'  
            
        jit_bill_details['beneficiaryDetails'].append(beneficiary)
        data[i['PAYMENT_NO']][i["JIT_BILL_NUMBER"]] = jit_bill_details
    return data

# Get the current working directory
current_directory = os.getcwd()
# Path to your Excel file
file_name = 'all_pi_details.xlsx'
# Combine the directory and file name to get the full file path
file_path = os.path.join(current_directory, file_name)
excel_file = file_path

# Name of the sheet you want to read
sheet_name = 'Processed PI'  # Change this to the actual sheet name

# Read the Excel file into a DataFrame
df = pd.read_excel(excel_file, sheet_name=sheet_name)

# Convert Timestamp columns to string format
df['JIT_BILL_DATE'] = df['JIT_BILL_DATE'].astype(str)

# Convert DataFrame to dictionary
data_dict = df.to_dict(orient='records')

processed_pi_data = process_data(data_dict)
    
# If you want to write JSON data to a file, you can use the following:
with open(os.path.join(current_directory, 'processed_pi_output.json'), 'w') as f:
    json.dump(processed_pi_data, f)


# Name of the sheet you want to read
sheet_name = 'Underprocessed PI'  # Change this to the actual sheet name

# Read the Excel file into a DataFrame
df = pd.read_excel(excel_file, sheet_name=sheet_name)

# Convert Timestamp columns to string format
df['JIT_BILL_DATE'] = df['JIT_BILL_DATE'].astype(str)

# Convert DataFrame to dictionary
data_dict = df.to_dict(orient='records')

in_processed_pi = process_data(data_dict)

# If you want to write JSON data to a file, you can use the following:
with open(os.path.join(current_directory, 'in_progress_pi_output.json'), 'w') as f:
    json.dump(in_processed_pi, f)
