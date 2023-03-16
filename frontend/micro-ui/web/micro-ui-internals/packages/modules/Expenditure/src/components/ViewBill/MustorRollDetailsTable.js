import React, {useState, useEffect, useMemo} from 'react'
import { useTranslation } from 'react-i18next'
import { Table,ArrowDown } from '@egovernments/digit-ui-react-components'
import { mustorRollDetailsTableColumns } from '../../configs/mustorRollDetailsTableColumns'
import { mustorRollDummyData } from '../../configs/mustorRollDummyData'

const MustorRollDetailsTable = ({ musterData }) => {
    const { t } = useTranslation()
    const mustorRollDetailsColumns = useMemo(() => mustorRollDetailsTableColumns(t))
    let tableRows = Object.values(musterData);

    const updateAmtAndWorkingDays = (state) => {
      let totalAmount = 0
      let totalActualWorkingDays = 0
      for (let row of Object.keys(state)) {
        if(row !== 'total') {
          totalAmount += state[row].amount
          totalActualWorkingDays += state[row].actualWorkingDays
        }    
      }
      return {totalAmount, totalActualWorkingDays};
    };
    
    const updateModifiedAmtAndWorkingDays = (state) => {
      let totalModifiedAmount = 0
      let totalModifiedActualWorkingDays = 0
      for (let row of Object.keys(state)) {
        if(row !== 'total') {
          totalModifiedAmount += state[row].modifiedAmount
          totalModifiedActualWorkingDays += state[row].modifiedWorkingDays
        }    
      }
      return {totalModifiedAmount, totalModifiedActualWorkingDays};
    };

    useEffect(() => {
      const { totalAmount, totalActualWorkingDays } = updateAmtAndWorkingDays(musterData)
      const { totalModifiedAmount, totalModifiedActualWorkingDays } = updateModifiedAmtAndWorkingDays(musterData)
      musterData = { ...musterData, total: { ...musterData.total, amount: totalAmount, actualWorkingDays: totalActualWorkingDays, modifiedAmount: totalModifiedAmount, modifiedWorkingDays: totalModifiedActualWorkingDays}  };
      tableRows = Object.values(musterData);
    }, [])
   
    return (
        <React.Fragment>
        <div style={{ padding: "0px", overflowX: "scroll" }} className='card week-table-card-wrapper'>
            <Table
                t={t}
                className="wage-seekers-table week-table"
                customTableWrapperClassName="table-wrapper attendence-table"
                disableSort={false}
                autoSort={false}
                columns={mustorRollDetailsColumns}
                initSortId="S N "
                data={tableRows}
                totalRecords={tableRows?.length}
                isPaginationRequired={false}
                styles={{marginTop: "16px"}}
                getCellProps={(cellInfo) => {
                    let tableProp = {};
                    if(cellInfo.column.Header === "Modified Amount(Rs)") {
                      tableProp["data-modified-amt"] = "modified-amt";
                    }
                    if(cellInfo.value === undefined) {
                      tableProp["data-radio-selection"] = "last-radio";
                    }
                    if(cellInfo?.row?.original?.type === "total") {
                      tableProp["data-last-row-cell"] = "last-row";
                    }
                    if (cellInfo.value === "ES_COMMON_TOTAL_AMOUNT") {
                      tableProp["colSpan"] = 4;
                    }
                    if(cellInfo.value === "DNR") {
                      tableProp["style"] = {
                        display: "none",
                      }
                    }
                    return tableProp;
                }}
            />
        </div>
        </React.Fragment>
    )
}

export default MustorRollDetailsTable