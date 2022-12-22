import React, { Fragment } from "react";
import { useHistory } from "react-router-dom";

const ViewBillsMenu = () => {
    const history = useHistory();

    const billsConfigs = {
        path : `/${window.contextPath}/employee/expenditure/view-bills/bills`,
        view : [
            {
                key : 'PO',
                label : 'View PO Bills'
            },
            {
                key : 'WO',
                label : 'View WO Bills'
            },
            {
                key : 'SHG',
                label : 'View SHG Bills'
            }
        ]
    }

    const handleBillMenu = (billType) => {
        history.push({
            pathname : billsConfigs?.path,
            state : {
                billType : billType?.key
            }
        });
    }

    return (
        <React.Fragment>
            {
                billsConfigs?.view?.map((billType)=>(
                    <div key={billType?.key} onClick={()=>handleBillMenu(billType)} className="bills-menu-options">{billType.label}</div>
                ))
            }
        </React.Fragment>
    )
}

export default ViewBillsMenu;