import React,{Fragment} from 'react'
import { CardSectionHeader,Table } from '@egovernments/digit-ui-react-components'
import { useTranslation } from 'react-i18next'

// const styles = {
//     root: {
//         width: "100%",
//         marginTop: "2px",
//         overflowX: "auto",
//         boxShadow: "none",
//     },
//     table: {
//         minWidth: 700,
//         backgroundColor: "rgba(250, 250, 250, var(--bg-opacity))",
//     },
//     cell: {
//         maxWidth: "7em",
//         minWidth: "1em",
//         border: "1px solid #e8e7e6",
//         padding: "4px 5px",
//         fontSize: "0.8em",
//         textAlign: "left",
//         lineHeight: "1.5em",
//     },
//     cellHeader: {
//         overflow: "hidden",
//         textOverflow: "ellipsis",
//     },
//     cellLeft: {
//         // position: 'sticky',
//         // backgroundColor:'rgba(250, 250, 250, var(--bg-opacity))',
//         // left: 0
//     },
//     cellRight: {
//         // position: 'sticky',
//         // backgroundColor:'rgba(250, 250, 250, var(--bg-opacity))',
//         // right: 0
//     },
// };


const SubWork = () => {
    
    const {t} = useTranslation()

  return (
    <>
          <CardSectionHeader >{t(`WORKS_SUB_WORK_DETAILS`)}</CardSectionHeader>
          
          
          
          
          
          {/* <table className={"customTable table-fixed-first-column table-border-style"} style={{ ...styles.table, tableLayout: "fixed", width: "100%", borderCollapse: "collapse" }}>
            <thead>
                <tr>
                    <th style={{"width":"10%"}}>{t("SNO")}</th>
                    <th colSpan={3}>{t("NAME_WORK")}</th>
                    <th>{t("ESTIMATED_AMT")}</th>
                    <th style={{"width":"5%"}}></th>
                </tr>
                <tr>
                    <td colSpan={6} style={{"textAlign":"center"}}>{t("ADD_LINE_ITEM")}</td>
                </tr>
            </thead>
          </table> */}
    </>
  )
}

export default SubWork