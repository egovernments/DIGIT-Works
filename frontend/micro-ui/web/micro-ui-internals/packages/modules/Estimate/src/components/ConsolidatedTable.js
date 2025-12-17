import React,{useEffect} from "react";
import { useTranslation } from "react-i18next";
import { Amount } from "@egovernments/digit-ui-react-components";

const GroupedTable = (props) => {
  const { t } = useTranslation();
  const {emptyTableMsg}=props;
  let data = props?.arrayProps?.fields || [];
  let sorType = props?.arrayProps?.type || "W";
  // Function to calculate totals for a specific type (M, L, MH) within subrows
 

  useEffect(()=>{},[
    
  ]);
  // Function to group by SORID and calculate totals for each type
  const groupAndCalculateTotals = () => {
    let groupedData = [];

    // Iterate through each item in data array

    // Iterate through each subrow of the item

    data.forEach((subrow, index) => {
      
      const { rate,sNo, amount, description, quantity, type, sorId ,uom} = subrow;

      // Check if the SORID (code) exists in groupedData, if not, initialize it
  if (type===sorType) {
    
  
      groupedData.push({
        sNo: index,
        code: sorId,
        description: description, // Use subrow name for description
        uom: uom, // Use subrow unit for UOM
        rate: rate|| 0,
        type,
        totalQuantity: quantity || 0,
        totalAmount: amount || 0,
      });
    }
    });

    // Convert object to array of values

    return sortedData(groupedData);
  };

  const sortedData = (groupedDataList) => {
   
    const grouped = {};

    groupedDataList.forEach((item) => {
      const { sNo, code, description, uom, rate, type, totalQuantity, totalAmount } = item;
      if (!grouped[code]) {
        grouped[code] = {
          sNo: sNo,
          code: code,
          description: description,
          type: type,
          uom: uom,
          totalAmount: 0,
          totalQuantity: 0,
          rate:rate,
        };
      }
      grouped[code].totalQuantity += totalQuantity;
      if (totalAmount !== null) {
        grouped[code].totalAmount += totalAmount;
      }
    });
    
    const result = Object.values(grouped).map((item) => ({
      
      uom: item?.uom, // Use subrow unit for UOM
      rate: item?.rate,
      sNo: item?.sNo,
      code: item?.code,

      description: item?.description,
      type: item.type,
      totalAmount: item.totalAmount,
      totalQuantity: item.totalQuantity,
    }));
   
    return result;
  };
  // Render table header
  const renderHeader = () => {
    const columns = [
      { key: t("WORKS_SNO"), width: "5%", textAlign:"left"  },
      { key: t("WORKS-SORS_COLUMN_CODE"), width: "15%", textAlign:"left"  },
      { key: t("CORE_COMMON_NAME"), width: "25%", textAlign:"left"  },
      { key: t("ACTION_TEST_COMMONMASTERUOM"), width: "10%", textAlign:"left"  },
      { key: t("MB_RATE"), width: "10%", textAlign:"right" },
      { key: t("MB_QUANTITY"), width: "15%", textAlign:"right" },
      { key: t("MB_ONLY_AMOUNT"), width: "15%", textAlign:"right" },
    ];
    return (
      <tr>
        {columns.map((col, index) => (
          <th key={index} style={{ width: col.width  , textAlign:col?.textAlign, backgroundColor: "#EFEFEF",
            fontSize: "16px",
            lineHeight: "18.75px",
            fontFamily: "Roboto",
            fontWeight: "700",
            color: "#0B0C0C",}}>
            {col.key}
          </th>
        ))}
      </tr>
    );
  };

  // Render table body
  const renderBody = () => {
    const groupedData = groupAndCalculateTotals();
       
    let sno = 0;
    return groupedData.map((row, index) => (
      <tr key={index} style={{border: "1px solid #A6A6A6"}}>
        <td style={{ width: "5%" ,border: "1px solid #A6A6A6"}}>{++sno}</td>
        <td style={{ width: "15%",border: "1px solid #A6A6A6" }}>{row.code}</td>
        <td style={{ width: "25%" ,border: "1px solid #A6A6A6"}}>{row.description}</td>
        <td style={{ width: "10%" ,border: "1px solid #A6A6A6"}}>{row.uom}</td>
        <td style={{ width: "10%" ,textAlign:"right",border: "1px solid #A6A6A6" }}>
          {<Amount value={Digit.Utils.dss.formatterWithoutRound(parseFloat(row.rate).toFixed(2), "number", undefined, true, undefined, 2)} t={t} sameDisplay={true} roundOff={false} />}
        </td>
        <td style={{ width: "15%",textAlign:"right", border: "1px solid #A6A6A6"}}>
          {/*<Amount value={row.totalQuantity} t={t} />*/}
          {<Amount value={parseFloat(row.totalQuantity).toFixed(4)} t={t} sameDisplay={true} roundOff={false} />}
        </td>
        <td style={{ width: "15%" ,textAlign:"right",border: "1px solid #A6A6A6"}}>
          <Amount value={Digit.Utils.dss.formatterWithoutRound(parseFloat(row.totalAmount).toFixed(2), "number", undefined, true, undefined, 2)} t={t} sameDisplay={true} roundOff={false}/>
        </td>
        {/* <td style={{ width: "15%" }}>
          <Amount value={parseFloat(row.totalMH).toFixed(2)} t={t} />
        </td>*/}
      </tr>
    ));
  };
  
  return (
    <React.Fragment>
      <table className="table reports-table reports-table sub-work-table">
        <thead style={{backgroundColor:"#EFEFEF"}}>{renderHeader()}</thead>
        <tbody>{renderBody()}
        {
          groupAndCalculateTotals().length>0&&
        <tr style={{border: "1px solid #A6A6A6"}}>
        <td colSpan={6} style={{ width: "25%", textAlign:"right", fontWeight:"bold" ,border: "1px solid #A6A6A6"}}>{t("MB_AMOUNT_TOTAL")}</td>
        <td style={{ width: "10%" ,border: "1px solid #A6A6A6"}}>
          
         <div style={{ textAlign:"right", fontWeight:"bold" }}> {Digit.Utils.dss.formatterWithoutRound( parseFloat( groupAndCalculateTotals().reduce((accumulator, currentValue) => accumulator + currentValue.totalAmount, 0.00)).toFixed(2), "number", undefined, true, undefined, 2)} </div>
        </td>
        </tr>
        
        }

        {
          groupAndCalculateTotals().length===0&&
          <td colSpan={7} style={{marginLeft:"10px", color:"#9E9E9E", textAlign:"center",border: "1px solid #A6A6A6"}}>
          {t(emptyTableMsg)}
        </td>
        }
        </tbody>
      </table>
    </React.Fragment>
  );
};

export default GroupedTable;