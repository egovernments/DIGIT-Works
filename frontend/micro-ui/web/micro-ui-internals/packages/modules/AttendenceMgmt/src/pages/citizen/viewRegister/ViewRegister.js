import { Card, KeyNote, DatePicker, DateRange, SearchIcon, SearchIconSvg } from '@egovernments/digit-ui-react-components'
import React, { Fragment, useState } from 'react'
import { Link } from 'react-router-dom'
import { useTranslation } from "react-i18next";

const ViewRegister = (props) => {
    const [isExpanded, setIsExpanded] = useState(true)
    const { t } = useTranslation()

    return (
        <div >
            <Card>
                <KeyNote keyValue={t("REGISTER_NAME")} note={"NAME"} />
                {isExpanded && <div>
                    <KeyNote
                        keyValue={t("REGISTER_ID")}
                        note={"ID"}
                    />
                    <KeyNote
                        keyValue={t("REGISTER_ORG_NAME")}
                        note={
                            "ORG"
                        }
                    />
                    <KeyNote keyValue={t("REGISTER_DATES")} note={"DATE"} />
                    <KeyNote keyValue={t("REGISTER_INDIVIDUAL_COUNT")} note={"COUNT"} />
                    <KeyNote keyValue={t("REGISTER_LAST_ATTENDENCE_DATE")} note={"LDATE"} />
                    <KeyNote keyValue={t("REGISTER_STATUS")} note={"STATUS"} />
                </div>}

                <p>
                    <span className='link' style={{ color: "#f47738" }} onClick={() => setIsExpanded(!isExpanded)}>
                        {isExpanded ? t("HIDE_REGISTER_DETAILS") : t("VIEW_REGISTER_DETAILS")}
                    </span>
                </p>
            </Card>


            <Card>
                <div className="filters-input">
                    <DateRange onFilterChange={(e)=>{console.log(e)}} values={1} t={t} />
                </div>
            </Card>
            <Card>
                <div className="map-search-bar-wrap">
                    {/* <img src={searchicon} className="map-search-bar-icon" alt=""/> */}
                    <SearchIconSvg className="map-search-bar-icon" />
                    <input id="pac-input" className="map-search-bar" type="text" placeholder={t("SEARCH_BY_NAME_AADHAR")} />
                </div>
            </Card>

        </div>
    )
}

export default ViewRegister