import os
import json

def getIsProjectCompleted(contractedAmount, billAmount):

    return billAmount >= 0.95 * contractedAmount

def writeDataToFile(tenantId, kpiId, data):
    output_directory = os.path.join(os.getcwd(), kpiId)
    createFolderIfNotExists(output_directory)
    # Path to your Excel file
    file_name = f'{tenantId}.json'
    # Combine the directory and file name to get the full file path
    file_path = os.path.join(output_directory, file_name)
    with open(file_path, 'w') as f:
        json.dump(data, f, indent=4)
    print(data)

def createFolderIfNotExists(folderPath):
    # Check if the folder already exists
    if not os.path.exists(folderPath):
        # If the folder does not exist, create it
        os.makedirs(folderPath)
        print(f"Folder '{folderPath}' created.")
