#!/usr/bin/env python3
import json
import os
import re

def parse_postgresql_copy_data(filename):
    """Parse PostgreSQL COPY data and extract rows."""
    with open(filename, 'r') as f:
        lines = f.readlines()
    
    # Find the COPY statement to get column names
    copy_line = None
    for i, line in enumerate(lines):
        if 'COPY public.eg_mdms_schema_definition' in line:
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
            print(f"Skipping malformed row: {line[:100]}...")
    
    return rows

def create_json_files(rows, output_dir):
    """Create individual JSON files for each row using the code column as filename."""
    os.makedirs(output_dir, exist_ok=True)
    
    created_files = []
    for row in rows:
        code = row.get('code', '')
        if not code:
            print(f"Row missing 'code' field, skipping: {row.get('id', 'unknown')}")
            continue
        
        # Clean up the code for use as filename (replace problematic characters)
        safe_filename = code.replace('/', '_').replace('\\', '_').replace(':', '_')
        filename = os.path.join(output_dir, f"{safe_filename}.json")
        
        # Parse the definition JSON if it's a string
        if 'definition' in row and row['definition']:
            try:
                row['definition'] = json.loads(row['definition'])
            except json.JSONDecodeError:
                print(f"Could not parse definition JSON for {code}")
        
        # Convert boolean strings to actual booleans
        if 'isactive' in row:
            row['isactive'] = row['isactive'] == 't'
        
        # Convert timestamps to integers if they're strings
        for field in ['createdtime', 'lastmodifiedtime']:
            if field in row and row[field] and row[field].isdigit():
                row[field] = int(row[field])
        
        # Write the JSON file
        with open(filename, 'w') as f:
            json.dump(row, f, indent=2)
        
        created_files.append(filename)
        print(f"Created: {filename}")
    
    return created_files

def main():
    input_file = '/tmp/mdms_full_data.txt'
    output_dir = '/Users/jagankumar/Office/Work/repo/DIGIT-Works/docs/configs/mdms_json_files'
    
    print(f"Parsing data from: {input_file}")
    rows = parse_postgresql_copy_data(input_file)
    print(f"Found {len(rows)} rows")
    
    if rows:
        print(f"\nCreating JSON files in: {output_dir}")
        created_files = create_json_files(rows, output_dir)
        print(f"\nSuccessfully created {len(created_files)} JSON files")
    else:
        print("No data found to process")

if __name__ == "__main__":
    main()