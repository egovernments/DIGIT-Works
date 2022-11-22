import { Card, KeyNote, DatePicker, DateRange, SearchIcon, SearchIconSvg,DateRangeNew,Table } from '@egovernments/digit-ui-react-components';
import React, { Fragment, useState,useContext,useCallback,useMemo } from 'react';
import { Link } from 'react-router-dom';
import { useTranslation } from "react-i18next";
import FilterContext from '../../../pageComponents/FilterContext';
import AttendenceTablev1 from '../../../pageComponents/AttendenceTablev1';
 import AttendenceRow from '../../../pageComponents/AttendenceRow';
import AttendenceTable from '../../../pageComponents/AttendenceTable';

const ViewRegister = (props) => {
    const [isExpanded, setIsExpanded] = useState(true)
    const { t } = useTranslation()

    const { value, setValue } = useContext(FilterContext);


    // const handleFilterChange = (data) => {
    //     setValue({ ...value, ...data });
    // };

    const [localSearchParams, setLocalSearchParams] = useState(() => ({  }));

    const handleChange = useCallback((data) => {
        setLocalSearchParams(() => ({  ...data }));
    }, [])

    const columns = useMemo(() => ([
        
    ]), [])

    const sampleUsersState = [
        {
            "name": "Sample Name",
            "aadhar": "9878-9378-2827",
            "guardian": "Sample guardian",
            "skill":"Unskilled",
            "state": [
                {
                    "key": 0,
                    "day": "mon",
                    "attendence": "half"
                },
                {
                    "key": 1,
                    "day": "tue",
                    "attendence": "full"
                },
                {
                    "key": 2,
                    "day": "wed",
                    "attendence": "zero"
                },
                {
                    "key": 3,
                    "day": "thu",
                    "attendence": "zero"
                },
                {
                    "key": 4,
                    "day": "fri",
                    "attendence": "half"
                },
                {
                    "key": 5,
                    "day": "sat",
                    "attendence": "full"
                },
                {
                    "key": 6,
                    "day": "sun",
                    "attendence": "full"
                }
            ]
        },
        {
            "name": "Sample Name One",
            "aadhar": "9888-321-227",
            "guardian": "Sample guardian One",
            "skill":"Skill 1",
            "state": [
                {
                    "key": 0,
                    "day": "mon",
                    "attendence": "full"
                },
                {
                    "key": 1,
                    "day": "tue",
                    "attendence": "full"
                },
                {
                    "key": 2,
                    "day": "wed",
                    "attendence": "full"
                },
                {
                    "key": 3,
                    "day": "thu",
                    "attendence": "zero"
                },
                {
                    "key": 4,
                    "day": "fri",
                    "attendence": "zero"
                },
                {
                    "key": 5,
                    "day": "sat",
                    "attendence": "half"
                },
                {
                    "key": 6,
                    "day": "sun",
                    "attendence": "half"
                }
            ]
        }
    ]

    const [userState, setUserState] = useState(sampleUsersState)

    

    return (
        <div>
            <div className='card'>
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
            </div>


            <div className='card'>
                <DateRangeNew t={t} values={localSearchParams?.range} onFilterChange={handleChange} filterLabel="MARK_ATTENDENCE_FOR_WEEK" />
            </div>
                        
            
            {/* <div className="map-search-bar-wrap"> */}
                {/* <img src={searchicon} className="map-search-bar-icon" alt=""/> */}
                {/* <SearchIconSvg className="map-search-bar-icon" /> */}
                {/* <input id="pac-input" className="map-search-bar" type="dropdown" placeholder={t("SEARCH_BY_NAME_AADHAR")} /> */}
            {/* </div> */}

            {/* <AttendenceTable initialUserState={userState} /> */}

            <AttendenceTablev1 userState={userState} setUserState={setUserState} t={t}/>
            {/* <AttendenceRow /> */}
            {/* <div style={{ "overflowX": "scroll" }}>

            </div> */}
        </div>
    )
}

export default ViewRegister