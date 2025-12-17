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

def get_request_info():
    return {
        "apiId": "Rainmaker",
        # "ver": null,
        # "ts": null,
        # "action": null,
        # "did": null,
        # "key": null,
        "msgId": "1686748436982|en_IN",
        "authToken": "83bcae7a-65e8-4ddb-a3d3-12b36bbabbc7",
        # "plainAccessRequest": {
        #     # "recordId": null,
        #     # "plainRequestFields": null
        # },
        "userInfo": {
            "id": 965,
            "uuid": "ee3379e9-7f25-4be8-9cc1-dc599e1668c9",
            "roles": [
                {
                    "name": "HRMS Admin",
                    "code": "HRMS_ADMIN",
                    "tenantId": "pg.citya"
                },
                {
                    "name": "WORK ORDER VIEWER",
                    "code": "WORK_ORDER_VIEWER",
                    "tenantId": "pg.citya"
                },
                {
                    "name": "National Dashboard Admin",
                    "code": "NATADMIN",
                    "tenantId": "pg.citya"
                },
                {
                    "name": "Birth Application Viewer",
                    "code": "BIRTH_APPLICATION_VIEWER",
                    "tenantId": "pg.citya"
                },
                {
                    "name": "Redressal Officer",
                    "code": "RO",
                    "tenantId": "pg.citya"
                },
                {
                    "name": "Works Bill Creator",
                    "code": "WORKS_BILL_CREATOR",
                    "tenantId": "pg.citya"
                },
                {
                    "name": "Collection Report Viewer",
                    "code": "COLL_REP_VIEW",
                    "tenantId": "pg.citya"
                },
                {
                    "name": "Contractor Advance creator",
                    "code": "CONTRACTOR_ADVANCE_CREATOR",
                    "tenantId": "pg.citya"
                },
                {
                    "name": "Works Approver",
                    "code": "WORKS_APPROVER",
                    "tenantId": "pg.citya"
                },
                {
                    "name": "WS Receipt Cancellator",
                    "code": "CR_WS",
                    "tenantId": "pg.citya"
                },
                {
                    "name": "Property Approver",
                    "code": "PROPERTY_APPROVER",
                    "tenantId": "pg.citya"
                },
                {
                    "name": "TL Approver",
                    "code": "TL_APPROVER",
                    "tenantId": "pg.citya"
                },
                {
                    "name": "LOI APPROVER",
                    "code": "LOI_APPROVER",
                    "tenantId": "pg.citya"
                },
                {
                    "name": "EST CREATOR",
                    "code": "EST_CREATOR",
                    "tenantId": "pg.citya"
                },
                {
                    "name": "Fire Noc Department Approver",
                    "code": "FIRE_NOC_APPROVER",
                    "tenantId": "pg.citya"
                },
                {
                    "name": "HCM SYSTEM ADMINISTRATOR",
                    "code": "SYSTEM_ADMINISTRATOR",
                    "tenantId": "pg.citya"
                },
                {
                    "name": "PT Field Inspector",
                    "code": "PT_FIELD_INSPECTOR",
                    "tenantId": "pg.citya"
                },
                {
                    "name": "Abstract Estimate Creator",
                    "code": "AE_CREATOR",
                    "tenantId": "pg.citya"
                },
                {
                    "name": "ULB Administrator",
                    "code": "PTADMIN",
                    "tenantId": "pg.citya"
                },
                {
                    "name": "MUKTA Admin",
                    "code": "MUKTA_ADMIN",
                    "tenantId": "pg.citya"
                },
                {
                    "name": "Works Administrator",
                    "code": "WORKS_ADMINISTRATOR",
                    "tenantId": "pg.citya"
                },
                {
                    "name": "BPA Builder",
                    "code": "BPA_BUILDER",
                    "tenantId": "pg.citya"
                },
                {
                    "name": "Finance Voucher Creator",
                    "code": "EGF_VOUCHER_CREATOR",
                    "tenantId": "pg.citya"
                },
                {
                    "name": "Collections Remitter",
                    "code": "COLL_REMIT_TO_BANK",
                    "tenantId": "pg.citya"
                },
                {
                    "name": "ESTIMATE APPROVER",
                    "code": "ESTIMATE_APPROVER",
                    "tenantId": "pg.citya"
                },
                {
                    "name": "Auto Escalation Employee",
                    "code": "AUTO_ESCALATE",
                    "tenantId": "pg.citya"
                },
                {
                    "name": "MB_VERIFIER",
                    "code": "MB_VERIFIER",
                    "tenantId": "pg.citya"
                },
                {
                    "name": "EST FIN SANC",
                    "code": "EST_FIN_SANC",
                    "tenantId": "pg.citya"
                },
                {
                    "name": "PGR Last Mile Employee",
                    "code": "PGR_LME",
                    "tenantId": "pg.citya"
                },
                {
                    "name": "DEATH Application Editor",
                    "code": "DEATH_APPLICATION_EDITOR",
                    "tenantId": "pg.citya"
                },
                {
                    "name": "MUSTER ROLL APPROVER",
                    "code": "MUSTER_ROLL_APPROVER",
                    "tenantId": "pg.citya"
                },
                {
                    "name": "WS Approver",
                    "code": "WS_APPROVER",
                    "tenantId": "pg.citya"
                },
                {
                    "name": "Finance Voucher Approver",
                    "code": "EGF_VOUCHER_APPROVER",
                    "tenantId": "pg.citya"
                },
                {
                    "name": "PQM TP OPERATOR",
                    "code": "PQM_TP_OPERATOR",
                    "tenantId": "pg.citya"
                },
                {
                    "name": "Grivance Administrator",
                    "code": "GA",
                    "tenantId": "pg.citya"
                },
                {
                    "name": "BILL_ACCOUNTANT",
                    "code": "BILL_ACCOUNTANT",
                    "tenantId": "pg.citya"
                },
                {
                    "name": "BILL_VERIFIER",
                    "code": "BILL_VERIFIER",
                    "tenantId": "pg.citya"
                },
                {
                    "name": "TL Counter Employee",
                    "code": "TL_CEMP",
                    "tenantId": "pg.citya"
                },
                {
                    "name": "Property Tax Collection Employee",
                    "code": "PT_COLLECTION_EMP",
                    "tenantId": "pg.citya"
                },
                {
                    "name": "TL Report Viewer",
                    "code": "TL_REPORT_VIEWER",
                    "tenantId": "pg.citya"
                },
                {
                    "name": "FSM Desluding Operator",
                    "code": "FSM_DSO",
                    "tenantId": "pg.citya"
                },
                {
                    "name": "FSM Employee Application Viewer",
                    "code": "FSM_VIEW_EMP",
                    "tenantId": "pg.citya"
                },
                {
                    "name": "BPAREG doc verifier",
                    "code": "BPAREG_DOC_VERIFIER",
                    "tenantId": "pg.citya"
                },
                {
                    "name": "Asset Administrator",
                    "code": "ASSET_ADMINISTRATOR",
                    "tenantId": "pg.citya"
                },
                {
                    "name": "PGR Administrator",
                    "code": "PGR-ADMIN",
                    "tenantId": "pg.citya"
                },
                {
                    "name": "Field Employee",
                    "code": "FEMP",
                    "tenantId": "pg.citya"
                },
                {
                    "name": "Counter Employee",
                    "code": "CEMP",
                    "tenantId": "pg.citya"
                },
                {
                    "name": "Death Application Viewer",
                    "code": "DEATH_APPLICATION_VIEWER",
                    "tenantId": "pg.citya"
                },
                {
                    "name": "Admin of a ULB",
                    "code": "CITY_ADMIN",
                    "tenantId": "pg.citya"
                },
                {
                    "name": "BILL_CREATOR",
                    "code": "BILL_CREATOR",
                    "tenantId": "pg.citya"
                },
                {
                    "name": "Collection Operator",
                    "code": "COLL_OPERATOR",
                    "tenantId": "pg.citya"
                },
                {
                    "name": "FSM Employee Application Editor",
                    "code": "FSM_EDITOR_EMP",
                    "tenantId": "pg.citya"
                },
                {
                    "name": "BPAREG Approver",
                    "code": "BPAREG_APPROVER",
                    "tenantId": "pg.citya"
                },
                {
                    "name": "FSM Driver",
                    "code": "FSM_DRIVER",
                    "tenantId": "pg.citya"
                },
                {
                    "name": "WORK ORDER CREATOR",
                    "code": "WORK_ORDER_CREATOR",
                    "tenantId": "pg.citya"
                },
                {
                    "name": "Death Application Creator",
                    "code": "DEATH_APPLICATION_CREATOR",
                    "tenantId": "pg.citya"
                },
                {
                    "name": "Internal Microservice Role",
                    "code": "INTERNAL_MICROSERVICE_ROLE",
                    "tenantId": "pg.citya"
                },
                {
                    "name": "AssetCreator",
                    "code": "AssetCreator",
                    "tenantId": "pg.citya"
                },
                {
                    "name": "EGF Bill Creator",
                    "code": "EGF_BILL_CREATOR",
                    "tenantId": "pg.citya"
                },
                {
                    "name": "Employee Finance",
                    "code": "EMPLOYEE_FINANCE",
                    "tenantId": "pg.citya"
                },
                {
                    "name": "Sanitation Helper",
                    "code": "SANITATION_HELPER",
                    "tenantId": "pg.citya"
                },
                {
                    "name": "Legacy Receipt Creator",
                    "code": "LEGACY_RECEIPT_CREATOR",
                    "tenantId": "pg.citya"
                },
                {
                    "name": "QA Automation",
                    "code": "QA_AUTOMATION",
                    "tenantId": "pg.citya"
                },
                {
                    "name": "MB_CREATOR",
                    "code": "MB_CREATOR",
                    "tenantId": "pg.citya"
                },
                {
                    "name": "TL Field Inspector",
                    "code": "TL_FIELD_INSPECTOR",
                    "tenantId": "pg.citya"
                },
                {
                    "name": "Organization admin",
                    "code": "ORG_ADMIN",
                    "tenantId": "pg.citya"
                },
                {
                    "name": "BILL_APPROVER",
                    "code": "BILL_APPROVER",
                    "tenantId": "pg.citya"
                },
                {
                    "name": "BPA Architect",
                    "code": "BPA_ARCHITECT",
                    "tenantId": "pg.citya"
                },
                {
                    "name": "MUSTER ROLL VERIFIER",
                    "code": "MUSTER_ROLL_VERIFIER",
                    "tenantId": "pg.citya"
                },
                {
                    "name": "Citizen",
                    "code": "CITIZEN",
                    "tenantId": "pg.citya"
                },
                {
                    "name": "BPA Field Inspector",
                    "code": "BPA_FIELD_INSPECTOR",
                    "tenantId": "pg.citya"
                },
                {
                    "name": "Employee Common",
                    "code": "EMPLOYEE_COMMON",
                    "tenantId": "pg.citya"
                },
                {
                    "name": "Localisation admin",
                    "code": "LOC_ADMIN",
                    "tenantId": "pg.citya"
                },
                {
                    "name": "BILL_VIEWER",
                    "code": "BILL_VIEWER",
                    "tenantId": "pg.citya"
                },
                {
                    "name": "BPA Services verifier",
                    "code": "BPA_VERIFIER",
                    "tenantId": "pg.citya"
                },
                {
                    "name": "BPA Engineer",
                    "code": "BPA_ENGINEER",
                    "tenantId": "pg.citya"
                },
                {
                    "name": "FSM FSTP Opperator",
                    "code": "FSM_EMP_FSTPO",
                    "tenantId": "pg.citya"
                },
                {
                    "name": "Universal Collection Employee",
                    "code": "UC_EMP",
                    "tenantId": "pg.citya"
                },
                {
                    "name": "NoC Field Inpector",
                    "code": "NOC_FIELD_INSPECTOR",
                    "tenantId": "pg.citya"
                },
                {
                    "name": "FSM Employee Application Creator",
                    "code": "FSM_CREATOR_EMP",
                    "tenantId": "pg.citya"
                },
                {
                    "name": "PT Counter Approver",
                    "code": "PT_APPROVER",
                    "tenantId": "pg.citya"
                },
                {
                    "name": "EST TECH SANC",
                    "code": "EST_ADMIN_SANC",
                    "tenantId": "pg.citya"
                },
                {
                    "name": "ESTIMATE CREATOR",
                    "code": "ESTIMATE_CREATOR",
                    "tenantId": "pg.citya"
                },
                {
                    "name": "PQM Admin",
                    "code": "PQM_ADMIN",
                    "tenantId": "pg.citya"
                },
                {
                    "name": "NoC Doc Verifier",
                    "code": "NOC_DOC_VERIFIER",
                    "tenantId": "pg.citya"
                },
                {
                    "name": "Anonymous User",
                    "code": "ANONYMOUS",
                    "tenantId": "pg.citya"
                },
                {
                    "name": "Citizen Feedback Centre",
                    "code": "CFC",
                    "tenantId": "pg.citya"
                },
                {
                    "name": "State Dashboard Admin",
                    "code": "STADMIN",
                    "tenantId": "pg.citya"
                },
                {
                    "name": "Financials Approver",
                    "code": "WORKS_FINANCIAL_APPROVER",
                    "tenantId": "pg.citya"
                },
                {
                    "name": "ESTIMATE VERIFIER",
                    "code": "ESTIMATE_VERIFIER",
                    "tenantId": "pg.citya"
                },
                {
                    "name": "PTIS Master",
                    "code": "PTIS_MASTER",
                    "tenantId": "pg.citya"
                },
                {
                    "name": "CSC Collection Operator",
                    "code": "CSC_COLL_OPERATOR",
                    "tenantId": "pg.citya"
                },
                {
                    "name": "Birth Report Viewer",
                    "code": "BIRTH_REPORT_VIEWER",
                    "tenantId": "pg.citya"
                },
                {
                    "name": "LOI CREATOR",
                    "code": "LOI_CREATOR",
                    "tenantId": "pg.citya"
                },
                {
                    "name": "ULB Operator",
                    "code": "ULB_OPERATOR",
                    "tenantId": "pg.citya"
                },
                {
                    "name": "Commissioner",
                    "code": "COMMISSIONER",
                    "tenantId": "pg.citya"
                },
                {
                    "name": "EGF Bill Approver",
                    "code": "EGF_BILL_APPROVER",
                    "tenantId": "pg.citya"
                },
                {
                    "name": "Finance Payment Creator",
                    "code": "EGF_PAYMENT_CREATOR",
                    "tenantId": "pg.citya"
                },
                {
                    "name": "NoC counter Approver",
                    "code": "NOC_APPROVER",
                    "tenantId": "pg.citya"
                },
                {
                    "name": "Sanitation Worker",
                    "code": "SANITATION_WORKER",
                    "tenantId": "pg.citya"
                },
                {
                    "name": "BPA Structural Engineer",
                    "code": "BPA_STRUCTURALENGINEER",
                    "tenantId": "pg.citya"
                },
                {
                    "name": "OFFICER IN CHARGE",
                    "code": "OFFICER_IN_CHARGE",
                    "tenantId": "pg.citya"
                },
                {
                    "name": "Department Grievance Routing Officer",
                    "code": "DGRO",
                    "tenantId": "pg.citya"
                },
                {
                    "name": "BPA Services Approver",
                    "code": "BPA_APPROVER",
                    "tenantId": "pg.citya"
                },
                {
                    "name": "PROJECT CREATOR",
                    "code": "PROJECT_CREATOR",
                    "tenantId": "pg.citya"
                },
                {
                    "name": "Water Report Viewer",
                    "code": "WATER_REPORT_VIEWER",
                    "tenantId": "pg.citya"
                },
                {
                    "name": "Finance Master Admin",
                    "code": "EGF_MASTER_ADMIN",
                    "tenantId": "pg.citya"
                },
                {
                    "name": "BPA Town Planner",
                    "code": "BPA_TOWNPLANNER",
                    "tenantId": "pg.citya"
                },
                {
                    "name": "EMPLOYEE ADMIN",
                    "code": "EMPLOYEE_ADMIN",
                    "tenantId": "pg.citya"
                },
                {
                    "name": "Finance Adminsitrator",
                    "code": "EGF_ADMINISTRATOR",
                    "tenantId": "pg.citya"
                },
                {
                    "name": "FSM Payment Collector",
                    "code": "FSM_COLLECTOR",
                    "tenantId": "pg.citya"
                },
                {
                    "name": "PT Report Viewer",
                    "code": "PT_REPORT_VIEWER",
                    "tenantId": "pg.citya"
                },
                {
                    "name": "Sewerage Report Viewer",
                    "code": "SEWERAGE_REPORT_VIEWER",
                    "tenantId": "pg.citya"
                },
                {
                    "name": "MB_APPROVER",
                    "code": "MB_APPROVER",
                    "tenantId": "pg.citya"
                },
                {
                    "name": "BPA Supervisor",
                    "code": "BPA_SUPERVISOR",
                    "tenantId": "pg.citya"
                },
                {
                    "name": "Birth Application Creator",
                    "code": "BIRTH_APPLICATION_CREATOR",
                    "tenantId": "pg.citya"
                },
                {
                    "name": "Any User",
                    "code": "ANONYMUS",
                    "tenantId": "pg.citya"
                },
                {
                    "name": "WS Field Inspector",
                    "code": "WS_FIELD_INSPECTOR",
                    "tenantId": "pg.citya"
                },
                {
                    "name": "Property Verifier",
                    "code": "PROPERTY_VERIFIER",
                    "tenantId": "pg.citya"
                },
                {
                    "name": "Finance Payment Approver",
                    "code": "EGF_PAYMENT_APPROVER",
                    "tenantId": "pg.citya"
                },
                {
                    "name": "Birth and Death Dashboard User",
                    "code": "DASHBOARD_REPORT_VIEWER",
                    "tenantId": "pg.citya"
                },
                {
                    "name": "PQM CRONJOB SCHEDULER",
                    "code": "PQM_CRONJOB_SCHEDULER",
                    "tenantId": "pg.citya"
                },
                {
                    "name": "FSM Employee Report Viewer",
                    "code": "FSM_REPORT_VIEWER",
                    "tenantId": "pg.citya"
                },
                {
                    "name": "EST TECH SANC",
                    "code": "EST_TECH_SANC",
                    "tenantId": "pg.citya"
                },
                {
                    "name": "Auto Escalation Supervisor",
                    "code": "SUPERVISOR",
                    "tenantId": "pg.citya"
                },
                {
                    "name": "WORK ORDER VERIFIER",
                    "code": "WORK_ORDER_VERIFIER",
                    "tenantId": "pg.citya"
                },
                {
                    "name": "Detailed Estimate Creator",
                    "code": "DE_CREATOR",
                    "tenantId": "pg.citya"
                },
                {
                    "name": "AssetReportViewer",
                    "code": "AssetReportViewer",
                    "tenantId": "pg.citya"
                },
                {
                    "name": "SW Approver",
                    "code": "SW_APPROVER",
                    "tenantId": "pg.citya"
                },
                {
                    "name": "Birth and Death User",
                    "code": "BND_CEMP",
                    "tenantId": "pg.citya"
                },
                {
                    "name": "TECHNICAL SANCTIONER",
                    "code": "TECHNICAL_SANCTIONER",
                    "tenantId": "pg.citya"
                },
                {
                    "name": "Works Master creator",
                    "code": "WORKS_MASTER_CREATOR",
                    "tenantId": "pg.citya"
                },
                {
                    "name": "Birth Application Editor",
                    "code": "BIRTH_APPLICATION_EDITOR",
                    "tenantId": "pg.citya"
                },
                {
                    "name": "Death Report Viewer",
                    "code": "DEATH_REPORT_VIEWER",
                    "tenantId": "pg.citya"
                },
                {
                    "name": "National Dashboard Systeme user",
                    "code": "NDA_SYSTEM",
                    "tenantId": "pg.citya"
                },
                {
                    "name": "Fire Noc Report Viewer",
                    "code": "FIRENOC_REPORT_VIEWER",
                    "tenantId": "pg.citya"
                },
                {
                    "name": "SUPER USER",
                    "code": "SUPERUSER",
                    "tenantId": "pg.citya"
                },
                {
                    "name": "BPA NOC Verifier",
                    "code": "BPA_NOC_VERIFIER",
                    "tenantId": "pg.citya"
                },
                {
                    "name": "FSM Employee Dashboard Viewer",
                    "code": "FSM_DASHBOARD_VIEWER",
                    "tenantId": "pg.citya"
                },
                {
                    "name": "Employee",
                    "code": "EMPLOYEE",
                    "tenantId": "pg.citya"
                },
                {
                    "name": "Collections Receipt Creator",
                    "code": "COLL_RECEIPT_CREATOR",
                    "tenantId": "pg.citya"
                },
                {
                    "name": "PT Doc Verifier",
                    "code": "PT_DOC_VERIFIER",
                    "tenantId": "pg.citya"
                },
                {
                    "name": "FSM Administrator",
                    "code": "FSM_ADMIN",
                    "tenantId": "pg.citya"
                },
                {
                    "name": "OBPS Report Viewer",
                    "code": "OBPS_REPORT_VIEWER",
                    "tenantId": "pg.citya"
                },
                {
                    "name": "Bank Collection Operator",
                    "code": "BANK_COLL_OPERATOR",
                    "tenantId": "pg.citya"
                },
                {
                    "name": "CBO ADMIN",
                    "code": "CBO_ADMIN",
                    "tenantId": "pg.citya"
                },
                {
                    "name": "Airport authority Approver",
                    "code": "AIRPORT_AUTHORITY_APPROVER",
                    "tenantId": "pg.citya"
                },
                {
                    "name": "TL Admin",
                    "code": "TL_ADMIN",
                    "tenantId": "pg.citya"
                },
                {
                    "name": "TL Creator",
                    "code": "TL_CREATOR",
                    "tenantId": "pg.citya"
                },
                {
                    "name": "SW Field Inspector",
                    "code": "SW_FIELD_INSPECTOR",
                    "tenantId": "pg.citya"
                },
                {
                    "name": "Customer Support Representative",
                    "code": "CSR",
                    "tenantId": "pg.citya"
                },
                {
                    "name": "Reindexing Role",
                    "code": "REINDEXING_ROLE",
                    "tenantId": "pg.citya"
                },
                {
                    "name": "WO Creator",
                    "code": "WO_CREATOR",
                    "tenantId": "pg.citya"
                },
                {
                    "name": "PT Counter Employee",
                    "code": "PT_CEMP",
                    "tenantId": "pg.citya"
                },
                {
                    "name": "PTIS Admin",
                    "code": "PTIS_ADMIN",
                    "tenantId": "pg.citya"
                },
                {
                    "name": "TL doc verifier",
                    "code": "TL_DOC_VERIFIER",
                    "tenantId": "pg.citya"
                },
                {
                    "name": "ESTIMATE VIEWER",
                    "code": "ESTIMATE_VIEWER",
                    "tenantId": "pg.citya"
                },
                {
                    "name": "Property Tax Receipt Cancellator",
                    "code": "CR_PT",
                    "tenantId": "pg.citya"
                },
                {
                    "name": "WS Counter Employee",
                    "code": "WS_CEMP",
                    "tenantId": "pg.citya"
                },
                {
                    "name": "EST_CHECKER",
                    "code": "EST_CHECKER",
                    "tenantId": "pg.citya"
                },
                {
                    "name": "JUNIOR ENGINEER",
                    "code": "JUNIOR_ENGINEER",
                    "tenantId": "pg.citya"
                },
                {
                    "name": "LOI CHECKER",
                    "code": "LOI_CHECKER",
                    "tenantId": "pg.citya"
                },
                {
                    "name": "NoC counter employee",
                    "code": "NOC_CEMP",
                    "tenantId": "pg.citya"
                },
                {
                    "name": "LOA Creator",
                    "code": "LOA_CREATOR",
                    "tenantId": "pg.citya"
                },
                {
                    "name": "Finance Report View",
                    "code": "EGF_REPORT_VIEW",
                    "tenantId": "pg.citya"
                },
                {
                    "name": "WS Document Verifier",
                    "code": "WS_DOC_VERIFIER",
                    "tenantId": "pg.citya"
                },
                {
                    "name": "PROJECT VIEWER",
                    "code": "PROJECT_VIEWER",
                    "tenantId": "pg.citya"
                },
                {
                    "name": "Grievance Routing Officer",
                    "code": "GRO",
                    "tenantId": "pg.citya"
                },
                {
                    "name": "Organization staff",
                    "code": "ORG_STAFF",
                    "tenantId": "pg.citya"
                },
                {
                    "name": "SW Document Verifier",
                    "code": "SW_DOC_VERIFIER",
                    "tenantId": "pg.citya"
                },
                {
                    "name": "MUNICIPAL ENGINEER",
                    "code": "MUNICIPAL_ENGINEER",
                    "tenantId": "pg.citya"
                },
                {
                    "name": "SW Clerk",
                    "code": "SW_CLERK",
                    "tenantId": "pg.citya"
                },
                {
                    "name": "PQM Lab Operator",
                    "code": "PQM_LAB_OPERATOR",
                    "tenantId": "pg.citya"
                },
                {
                    "name": "WORK ORDER APPROVER",
                    "code": "WORK_ORDER_APPROVER",
                    "tenantId": "pg.citya"
                },
                {
                    "name": "Grievance Officer",
                    "code": "GO",
                    "tenantId": "pg.citya"
                },
                {
                    "name": "WS Clerk",
                    "code": "WS_CLERK",
                    "tenantId": "pg.citya"
                },
                {
                    "name": "SW Counter Employee",
                    "code": "SW_CEMP",
                    "tenantId": "pg.citya"
                },
                {
                    "name": "MDMS Admin",
                    "code": "MDMS_ADMIN",
                    "tenantId": "pg.citya"
                },
                {
                    "name": "BPAREG Employee",
                    "code": "BPAREG_EMPLOYEE",
                    "tenantId": "pg.citya"
                },
                {
                    "name": "MB_VIEWER",
                    "code": "MB_VIEWER",
                    "tenantId": "pg.citya"
                }
            ]
        }
    }

def search_payment_instruction_from_ifms_adapter(id):
    print("search_payment_instruction_from_ifms_adapter")
    try:
        ifms_service_host = os.getenv('IFMS_HOST')
        ifms_pi_search = os.getenv('IFMS_PI_SEARCH')
        
        api_url = f"{ifms_service_host}/{ifms_pi_search}"
        headers = {
            'Content-Type': 'application/json'
        }
        request = {
            "RequestInfo": get_request_info(),
            "searchCriteria": {
                "ids": [id],
                "limit": "10000",
                "offset": "0",
                "sortBy": "createdTime",
                "order": "ASC"
            }
        }
        response = requests.post(api_url, json=request, headers=headers)
        paymentInstructions = []
        if response.status_code == 200:
            response_data = response.json()
            # Assuming your response is stored in the variable 'response_data'
            paymentInstructions.extend(response_data.get('paymentInstructions', []))
        else:
            print(f"Failed to fetch data from the API. Status code: {response.status_code}")
            print(response.text)
        return paymentInstructions
    except Exception as e:
        print("search_payment_instruction_from_ifms_adapter : error {}".format(str(e)))
        raise e

def search_disburse_from_program_service(id, locaiton_code):
    print("search_disburse_from_program_service")
    try:
        program_service_host = os.getenv('PROGRAM_SERVICE_HOST')
        program_disburse_search = os.getenv('PROGRAM_DISBURSE_SEARCH')

        api_url = f"{program_service_host}/{program_disburse_search}"
        headers = {
            'Content-Type': 'application/json'
        }
        request = {
            "signature": None,
            "header": {
                "message_id": "123",
                "message_ts": "1708428280",
                "message_type": "disburse",
                "action": "search",
                "sender_id": os.getenv('PROGRAM_DISBURSE_UPDATE_SENDER_ID'),
                "receiver_id": os.getenv('PROGRAM_DISBURSE_UPDATE_RECEIVER_ID')
            },
            "message": {
                "location_code": locaiton_code,
                "ids": [id],
                "pagination": {
                    "limit": 100,
                    "offset": 0
                }
            }
        }

        print(api_url)

        response = requests.post(api_url, json=request, headers=headers)
        disbursements = []
        if response.status_code == 200:
            response_data = response.json()
            # Assuming your response is stored in the variable 'response_data'
            disbursements.extend(response_data.get('disbursements', []))
        else:
            print(f"Failed to fetch data from the API. Status code: {response.status_code}")
            print(response.text)
        return disbursements
    except Exception as e:
        print("search_disburse_from_program_service : error {}".format(str(e)))
        raise e

def modify_disburse(disbursement, payment):
    print("modify_disburse")
    disbursement['status'] = {
        'status_code': payment['piStatus'],
        'status_message': payment['piStatus']
    }
    if 'additional_details' in disbursement:
        disbursement['additional_details']['piStatus'] = payment['piStatus']
    
    for beneficiary in payment['beneficiaryDetails']:
        for child in disbursement['children']:
            if beneficiary['beneficiaryId'] == child['additional_details']['beneficiaryId']:
                child['additional_details']['beneficiaryStatus'] = beneficiary['paymentStatus']
                if beneficiary['paymentStatus'] == 'Payment Failed':
                    child['status']['status_code'] = 'FAILED'
                    child['status']['status_message'] = 'FAILED'
                elif beneficiary['paymentStatus'] == 'Payment Successful':
                    child['status']['status_code'] = 'SUCCESSFUL'
                    child['status']['status_message'] = 'SUCCESSFUL'
                elif beneficiary['paymentStatus'] == 'Payment In Process':
                    child['status']['status_code'] = 'INPROCESS'
                    child['status']['status_message'] = 'INPROCESS'
                elif beneficiary['paymentStatus'] == 'Payment Initiated':
                    child['status']['status_code'] = 'INITIATED'
                    child['status']['status_message'] = 'INITIATED'
                else:
                    child['status']['status_code'] = 'FAILED'
                    child['status']['status_message'] = 'FAILED'

    return disbursement

def call_on_disburse_update_api(disburse):
    print("call_on_disburse_update_api")
    try:
        program_service_host = os.getenv('PROGRAM_SERVICE_HOST')
        program_disburse_update = os.getenv('PROGRAM_ON_DISBURSE_CREATE')

        api_url = f"{program_service_host}/{program_disburse_update}"
        headers = {
            'Content-Type': 'application/json'
        }
        request = {
            "signature": None,
            "header": {
                "message_id": disburse['id'],
                "message_ts": disburse['audit_details']['lastModifiedTime'],
                "message_type": "on-disburse",
                "action": "update",
                "sender_id": os.getenv('PROGRAM_DISBURSE_UPDATE_SENDER_ID'),
                "receiver_id": os.getenv('PROGRAM_DISBURSE_UPDATE_RECEIVER_ID')
            },
            "message": disburse
        }

        response = requests.post(api_url, json=request, headers=headers)
        if response.status_code == 200:
            print(f"Disbursement updated for payment number: {disburse['target_id']}")
        else:
            print(f"Failed to update disburse for {disburse['target_id']} from the API. Status code: {response.status_code}")
            print(response.text)
    except Exception as e:
        print("search_disburse_from_program_service : error {}".format(str(e)))
        raise e


def data_correction():
    print("Data correction started")

# ids from select jp.id from eg_mukta_ifms_disburse as md inner join jit_payment_inst_details as jp on md.id = jp.id where md.status != jp.pistatus;

    ids = [
            "953347ac-a603-4550-b257-023056a611bb"
        ]
    for id in ids:
        payments = search_payment_instruction_from_ifms_adapter(id)
        payment = payments[0]
        if payment:
            tenantid = payment.get('tenantId')
            disbursements = search_disburse_from_program_service(id, tenantid)
            disbursement = disbursements[0]
            # if disbursement:
            #     disburse = modify_disburse(disbursement, payment)
            #     if disburse:
            #         call_on_disburse_update_api(disburse)
            # else:
            #     print(f"Disbursement not found for: {id}")
    print("Data migration finished")

if __name__ == '__main__':
    try:
        data_correction() 

    except Exception as ex:
        logging.error("Exception occured on main.", exc_info=True)
        raise(ex)