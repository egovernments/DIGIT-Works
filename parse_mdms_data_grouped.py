#!/usr/bin/env python3
import json
import os
import re
from collections import defaultdict

def parse_postgresql_copy_data(filename):
    """Parse PostgreSQL COPY data and extract rows."""
    with open(filename, 'r', encoding='utf-8') as f:
        lines = f.readlines()
    
    # Find the COPY statement to get column names
    copy_line = None
    for i, line in enumerate(lines):
        if 'COPY public.eg_mdms_data' in line:
            copy_line = i
            break
    
    if copy_line is None:
        print("COPY statement not found")
        return []
    
    # Extract column names from COPY statement
    columns_match = re.search(r'\((.*?)\)', lines[copy_line])
    if not columns_match:
        print("Could not parse columns")
        return []
    
    columns = [col.strip() for col in columns_match.group(1).split(',')]
    print(f"Found columns: {columns}")
    
    # Parse data rows (between COPY statement and \.)
    rows = []
    for i in range(copy_line + 1, len(lines)):
        line = lines[i].strip()
        if line == '\\.':
            break
        if not line:
            continue
            
        # Split by tabs (PostgreSQL COPY default delimiter)
        values = line.split('\t')
        
        if len(values) == len(columns):
            row = dict(zip(columns, values))
            # Handle NULL values
            for key, value in row.items():
                if value == '\\N':
                    row[key] = None
            rows.append(row)
        else:
            print(f"Skipping malformed row (expected {len(columns)} columns, got {len(values)})")
    
    return rows

def group_data_by_schemacode_tenantid(rows):
    """Group rows by schemacode and tenantid."""
    grouped = defaultdict(list)
    
    for row in rows:
        schemacode = row.get('schemacode', '')
        tenantid = row.get('tenantid', '')
        
        if not schemacode or not tenantid:
            print(f"Row missing schemacode or tenantid, skipping: {row.get('id', 'unknown')}")
            continue
        
        # Parse the data JSON if it's a string
        if 'data' in row and row['data']:
            try:
                row['data'] = json.loads(row['data'])
            except json.JSONDecodeError:
                print(f"Could not parse data JSON for {row.get('id', 'unknown')}")
        
        # Convert boolean strings to actual booleans
        if 'isactive' in row:
            row['isactive'] = row['isactive'] == 't'
        
        # Convert timestamps to integers if they're strings
        for field in ['createdtime', 'lastmodifiedtime']:
            if field in row and row[field] and row[field].isdigit():
                row[field] = int(row[field])
        
        # Add to grouped data
        key = (schemacode, tenantid)
        grouped[key].append(row)
    
    return grouped

def create_grouped_json_files(grouped_data, output_dir):
    """Create JSON files for each schemacode-tenantid group."""
    os.makedirs(output_dir, exist_ok=True)
    
    created_files = []
    for (schemacode, tenantid), rows in grouped_data.items():
        # Clean up the schemacode and tenantid for use as filename
        safe_schemacode = schemacode.replace('/', '_').replace('\\', '_').replace(':', '_').replace('.', '_')
        safe_tenantid = tenantid.replace('/', '_').replace('\\', '_').replace(':', '_').replace('.', '_')
        
        filename = os.path.join(output_dir, f"{safe_schemacode}-{safe_tenantid}.json")
        
        # Write the JSON file with array of objects
        with open(filename, 'w', encoding='utf-8') as f:
            json.dump(rows, f, indent=2, ensure_ascii=False)
        
        created_files.append(filename)
        print(f"Created: {filename} ({len(rows)} rows)")
    
    return created_files

def main():
    input_file = '/tmp/mdms_data_full.txt'
    output_dir = '/Users/jagankumar/Office/Work/repo/DIGIT-Works/docs/configs/mdms_data_json'
    
    print(f"Parsing data from: {input_file}")
    rows = parse_postgresql_copy_data(input_file)
    print(f"Found {len(rows)} total rows")
    
    if rows:
        print(f"\nGrouping data by schemacode and tenantid...")
        grouped_data = group_data_by_schemacode_tenantid(rows)
        print(f"Found {len(grouped_data)} unique schemacode-tenantid combinations")
        
        print(f"\nCreating JSON files in: {output_dir}")
        created_files = create_grouped_json_files(grouped_data, output_dir)
        print(f"\nSuccessfully created {len(created_files)} JSON files")
        
        # Print summary statistics
        print("\nSummary:")
        for (schemacode, tenantid), rows in sorted(grouped_data.items()):
            print(f"  {schemacode}-{tenantid}: {len(rows)} rows")
    else:
        print("No data found to process")

if __name__ == "__main__":
    main()