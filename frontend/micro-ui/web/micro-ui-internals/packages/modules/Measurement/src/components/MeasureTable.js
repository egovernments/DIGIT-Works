import { AddIcon, Card, TextInput } from "@egovernments/digit-ui-react-components";
import React, { useState, Fragment } from "react";
import { useTranslation } from "react-i18next";
import { useHistory } from "react-router-dom/cjs/react-router-dom.min";
import MeasureCard from "./MeasureCard";


const data = [
  {
    "id": "fe57bc90-9744-42a5-b756-fd7e08e8c208",
    "sorId": null,
    "category": "OVERHEAD",
    "name": "SC",
    "description": "Supervision Charge",
    "unitRate": 0,
    "noOfunit": 0,
    "uom": null,
    "uomValue": 0,
    "amountDetail": [
      {
        "id": "af7de1f5-02da-427a-8c27-ffcc8ea824d5",
        "type": "SC",
        "amount": 751242.6,
        "isActive": true,
        "additionalDetails": null
      }
    ],
    "isActive": true,
    "additionalDetails": {
      "row": {
        "name": {
          "id": "1",
          "code": "SC",
          "name": "ES_COMMON_OVERHEADS_SC",
          "type": "percentage",
          "value": "7.5",
          "active": true,
          "description": "Supervision Charge",
          "effectiveTo": null,
          "effectiveFrom": 1682164954037,
          "isAutoCalculated": true,
          "isWorkOrderValue": true
        },
        "amount": "751242.60",
        "percentage": "7.5 %"
      }
    }
  },
  {
    "id": "aeaff50b-9f1c-4854-9f9c-5d5f61a4337e",
    "sorId": null,
    "category": "OVERHEAD",
    "name": "GST",
    "description": "Goods and Service Tax",
    "unitRate": 0,
    "noOfunit": 0,
    "uom": null,
    "uomValue": 0,
    "amountDetail": [
      {
        "id": "42453b3c-5d18-4dae-b0c9-f81455a9db73",
        "type": "GST",
        "amount": 1802982.24,
        "isActive": true,
        "additionalDetails": null
      }
    ],
    "isActive": true,
    "additionalDetails": {
      "row": {
        "name": {
          "id": "2",
          "code": "GST",
          "name": "ES_COMMON_OVERHEADS_GST",
          "type": "percentage",
          "value": "18",
          "active": true,
          "description": "Goods and Service Tax",
          "effectiveTo": null,
          "effectiveFrom": 1682164954037,
          "isAutoCalculated": true,
          "isWorkOrderValue": true
        },
        "amount": "1802982.24",
        "percentage": "18 %"
      }
    }
  },
  {
    "id": "ca1ec299-6d05-48e7-98b0-d03d31d659ff",
    "sorId": "SOR001A",
    "category": "SOR",
    "name": "TEST SOR",
    "description": "TEST SOR",
    "unitRate": 500,
    "noOfunit": 300,
    "uom": "KG",
    "uomValue": 0,
    "amountDetail": [
      {
        "id": "77ecca09-1b44-45ed-8080-91746b602b77",
        "type": "EstimatedAmount",
        "amount": 150000,
        "isActive": true,
        "additionalDetails": null
      }
    ],
    "isActive": true,
    "additionalDetails": {
      "measurement": [
        {
          "uom": "KG",
          "width": 2,
          "height": 1,
          "length": 10,
          "numItems": 3,
          "uomValue": 5,
          "totalValue": 150,
          "description": "LHS",
          "isDeduction": false,
          "additionalDetails": {}
        },
        {
          "uom": "KG",
          "width": 2,
          "height": 1,
          "length": 10,
          "numItems": 3,
          "uomValue": 5,
          "totalValue": 150,
          "description": "RHS",
          "isDeduction": false,
          "additionalDetails": {}
        },
        {
          "uom": "KG",
          "width": 2,
          "height": 1,
          "length": 10,
          "numItems": 3,
          "uomValue": 5,
          "totalValue": 150,
          "description": "LHS",
          "isDeduction": false,
          "additionalDetails": {}
        }
      ]
    }
  },
  {
    "id": "0fc9cc95-3912-48bc-a024-3c3e6abeef73",
    "sorId": "SOR001B",
    "category": "SOR",
    "name": "TEST SOR 2",
    "description": "TEST SOR 2",
    "unitRate": 444,
    "noOfunit": 22222,
    "uom": "SQM",
    "uomValue": 0,
    "amountDetail": [
      {
        "id": "628c48dd-8d88-419d-82a9-73c711a79499",
        "type": "EstimatedAmount",
        "amount": 9866568,
        "isActive": true,
        "additionalDetails": null
      }
    ],
    "isActive": true,
    "additionalDetails": {
      "measurement": [
        {
          "uom": "KG",
          "width": 2,
          "height": 1,
          "length": 10,
          "numItems": 3,
          "uomValue": 5,
          "totalValue": 150,
          "description": "LHS",
          "isDeduction": false,
          "additionalDetails": {}
        },
        {
          "uom": "KG",
          "width": 2,
          "height": 1,
          "length": 10,
          "numItems": 3,
          "uomValue": 5,
          "totalValue": 150,
          "description": "RHS",
          "isDeduction": false,
          "additionalDetails": {}
        },
        {
          "uom": "KG",
          "width": 2,
          "height": 1,
          "length": 10,
          "numItems": 3,
          "uomValue": 5,
          "totalValue": 150,
          "description": "LHS",
          "isDeduction": false,
          "additionalDetails": {}
        }
      ]
    }
  }
]


const MeasureTable = (props) => {
  let { columns } = props;
  const tenantId = Digit.ULBService.getCurrentTenantId();
  const { t } = useTranslation();
  const history = useHistory();
  

  const getStyles = (index) => {
    let obj = {};
    switch (index) {
      case 1:
        obj = { width: "1rem" };
        break;
      case 2:
        obj = { width: "30%" };
        break;
      case 3:
        obj = { width: "27rem" };
        break;
      case 4:
        obj = { width: "27rem" };
        break;
      case 5:
        obj = { width: "3%" };
        break;
      default:
        obj = { width: "92rem" };
        break;
    }
    return obj;
  };

  columns = [
    t("WORKS_SNO"),
    t("Description"),
    t("Unit"),
    t("Rate"),
    t("Approved Quantity"),
    t("Consumed Quantity"),
    t("Current MB Entry"),
    t("Amount for current entry"),
  ];
  const renderHeader = () => {
    return columns?.map((key, index) => {
      return (
        <th key={index} style={getStyles(index + 1)}>
          {" "}
          {key}{" "}
        </th>
      );
    });
  };

  const renderBody = () => {
    return data?.map((row, index) => {
      const [consumedQty, setConsumedQty] = useState(0);
      const [showMeasureCard, setShowMeasureCard] = useState(false);
      const [initialState, setInitialState] = useState({tableState: row?.additionalDetails?.measurement});
      return (
        <>
          <tr key={index}>
            <td>{index + 1}</td>
            <td>{row.description}</td>
            <td>{row.uom}</td>
            <td>{row.unitRate}</td>
            <td>{row.noOfunit}</td>
            <td>{null}</td>
            <td>
              <div className="measurement-table-input">
                <TextInput style={{ width: "80%" }} value={consumedQty} onChange={() => {}} />
                <button
                  onClick={() => {
                    setShowMeasureCard(!showMeasureCard);
                  }}
                >
                  <AddIcon fill={"#F47738"} styles={{ margin: "auto", display: "inline", marginTop: "-2px", width: "20px", height: "20px" }} />
                </button>
              </div>
            </td>
            <td>{null}</td>
          </tr>
          {showMeasureCard && (
            <tr>
            <td colSpan={"1"}></td>
              <td colSpan={"7"}>
                <MeasureCard columns={[]} consumedQty={consumedQty} setConsumedQty={setConsumedQty} setShowMeasureCard={setShowMeasureCard} initialState={initialState} setInitialState={setInitialState} />
              </td>
            </tr>
          )}
        </>
      );
    });
  };

  return (
    <Card>
      <table className="table reports-table sub-work-table" style={{ marginTop: "-2rem" }}>
        <thead>
          <tr>{renderHeader()}</tr>
        </thead>
        <tbody>{renderBody()}</tbody>
      </table>
    </Card>
  );
};

export default MeasureTable;
