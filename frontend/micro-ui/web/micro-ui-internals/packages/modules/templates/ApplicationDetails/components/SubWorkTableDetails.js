import { EditIcon,DownloadImgIcon,InfoBannerIcon } from '@egovernments/digit-ui-react-components';
import React from 'react'
import { useTranslation } from "react-i18next";
import { useHistory,Link } from 'react-router-dom';

const SubWorkTableDetails = ({data}) => {

    const tableStyles = data?.tableStyles
    const {rowStyle,cellStyle,tableStyle} = tableStyles
    const { t } = useTranslation();
    
    const renderHeader = (headers) => {
        return headers?.map((key, index) => {
            return <th key={index} style={cellStyle?.[index]} > {t(key)} </th>
        })
    }

    const renderRow = (row,index) => {
        return (
            <tr style={rowStyle}>
                {row?.map((lineItem,idx)=>{
                    let extraStyles = {}
                    if (lineItem === "Total") extraStyles = { "fontWeight": "bold" }
                    else if(lineItem?.type === "link") return (
                      <td>                      
                        <div className="link">
                          <Link to={lineItem?.path}>{String(lineItem.label ? lineItem.label : t("ES_COMMON_NA"))}</Link>
                        </div>
                      </td>
                    );
                    else if(lineItem?.type === "paymentStatus") return (
                        <td>
                            <div class="tooltip">
                                <span class="textoverflow" style={{display:"flex",...lineItem?.styles,alignItems:"center"}}>         
                                <p>{String(lineItem.value)}</p>
                                {lineItem?.hoverIcon && <InfoBannerIcon styles={{"margin-left":"10px"}} fill={"#ff0000"}/>}
                                </span>
                                {lineItem?.iconHoverTooltipText && <span class="tooltiptext" style={{ whiteSpace: "nowrap",...lineItem?.toolTipStyles }}>
                                {lineItem?.iconHoverTooltipText}
                                </span>}
                            </div>
                        </td>
                    )
                    return <td style={{ ...cellStyle?.[idx], ...extraStyles }}>{lineItem}</td>
                })}
            </tr>
        )
        
    }

    const renderBody = (rows) => {
        return rows?.map((row, index) => {
            return renderRow(row,index)
        })
    }

  return (
      <table className='table reports-table sub-work-table' style={tableStyle}>
          <thead>
              <tr>{renderHeader(data?.headers)}</tr>
          </thead>
          <tbody>
              {renderBody(data?.tableRows)}
          </tbody>
      </table>
  )
}

export default SubWorkTableDetails