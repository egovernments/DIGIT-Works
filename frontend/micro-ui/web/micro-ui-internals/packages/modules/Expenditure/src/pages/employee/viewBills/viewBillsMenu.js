import React, { Fragment } from "react";
import { useHistory } from "react-router-dom";
import ViewPOBills from "./viewPOBills";
import ViewSHGBills from "./viewSHGBills";
import ViewWOBills from "./viewWOBills";

const ViewBillsMenu = () => {
    const history = useHistory();

    const billsConfigs = {
        view : [
            {
                component : ViewPOBills,
                path : `/${window.contextPath}/employee/expenditure/view-bills/po`,
                key : 'PO',
                label : 'View PO Bills'
            },
            {
                component : ViewWOBills,
                path : `/${window.contextPath}/employee/expenditure/view-bills/wo`,
                key : 'WO',
                label : 'View WO Bills'
            },
            {
                component : ViewSHGBills,
                path : `/${window.contextPath}/employee/expenditure/view-bills/shg`,
                key : 'SHG',
                label : 'View SHG Bills'
            }
        ]
    }

    const handleBillMenu = (billType) => {
        history.push(billType?.path);
    }

    return (
        <React.Fragment>
            {
                billsConfigs?.view?.map((billType)=>(
                    <div key={billType?.key} onClick={()=>handleBillMenu(billType)}>{billType.label}</div>
                ))
            }
        </React.Fragment>
    )
}

export default ViewBillsMenu;