import { Card, KeyNote, DatePicker, DateRange, SearchIcon, SearchIconSvg,Table,StatusTable,Row,WeekPicker, TextInput } from '@egovernments/digit-ui-react-components';
import React, { Fragment, useState, useContext, useCallback, useMemo, useReducer } from 'react';
import { Link } from 'react-router-dom';
import { useTranslation } from "react-i18next";
import TrackAttendenceTable from '../../../pageComponents/TrackAttendenceTable';
import reducer, { initialTableState } from '../../../config/reducer';



const sampleUsersState = [
    {
        "name": "Sample Name",
        "aadhar": "9878-9378-2827",
        "guardian": "Sample guardian",
        "skill": "Unskilled",
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
        "skill": "Skill 1",
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

const ViewRegister = (props) => {
    const [isExpanded, setIsExpanded] = useState(true)
    const { t } = useTranslation()
    const [state, dispatch] = useReducer(reducer,initialTableState)
    console.log('newState',state);
    const [localSearchParams, setLocalSearchParams] = useState(() => ({ range:{
        startDate:new Date(),
        endDate:new Date()
    } }));

    const [searchQuery, onSearch] = useState("");
    
    const columns = useMemo(() => ([
        
    ]), [])

    
    const SearchImg = () => {
        return <SearchIconSvg className="signature-img" />;
    };

    const [userState, setUserState] = useState(sampleUsersState)

    const handleChange = useCallback((data) => {
        setLocalSearchParams((prevLocalSearchParams) => ({ ...prevLocalSearchParams, ...data }));
    }, [])
    

    return (
        <React.Fragment>
            <Card className={"card"} style={{ "marginBottom": "1rem" }}>
                <StatusTable>
                <Row className="border-none" label={t("REGISTER_NAME")} text={"NA"} textStyle={{ whiteSpace: "pre" }} />
                {isExpanded && <div>
                    <Row className="border-none" label={t("REGISTER_ID")} text={"NA"} textStyle={{ whiteSpace: "pre" }} />
                    <Row className="border-none" label={t("REGISTER_ORG_NAME")} text={"NA"} textStyle={{ whiteSpace: "pre" }} />
                    <Row className="border-none" label={t("REGISTER_DATES")} text={"NA"} textStyle={{ whiteSpace: "pre" }} />
                    <Row className="border-none" label={t("REGISTER_INDIVIDUAL_COUNT")} text={"NA"} textStyle={{ whiteSpace: "pre" }} />
                    <Row className="border-none" label={t("REGISTER_LAST_ATTENDENCE_DATE")} text={"NA"} textStyle={{ whiteSpace: "pre" }} />
                    <Row className="border-none" label={t("REGISTER_STATUS")} text={"NA"} textStyle={{ whiteSpace: "pre" }} />
                </div>}

                <p>
                    <span className='link' style={{ color: "#f47738" }} onClick={() => setIsExpanded(!isExpanded)}>
                        {isExpanded ? t("HIDE_REGISTER_DETAILS") : t("VIEW_REGISTER_DETAILS")}
                    </span>
                </p>
                </StatusTable>
            </Card>


            {/* <div className='card'>
                <DateRangeNew t={t} values={localSearchParams?.range} onFilterChange={handleChange} filterLabel="MARK_ATTENDENCE_FOR_WEEK" />
            </div> */}
                        
            
            {/* <div className="map-search-bar-wrap"> */}
                {/* <img src={searchicon} className="map-search-bar-icon" alt=""/> */}
                {/* <SearchIconSvg className="map-search-bar-icon" /> */}
                {/* <input id="pac-input" className="map-search-bar" type="dropdown" placeholder={t("SEARCH_BY_NAME_AADHAR")} /> */}
            {/* </div> */}

            {/* <AttendenceTable initialUserState={userState} /> */}
            {/* <WeekPicker /> */}
            {/* <DatePicker stylesForInput={{ width: "calc(100% - 290px)" }} style={{ width: "202px" }} />
            <DateRangeComp /> */}
            <Card style={{padding:"8px 16px"}}>
                <DateRange t={t} values={localSearchParams?.range} onFilterChange={handleChange} filterLabel="ATM_MARK_ATTENDENCE_LABEL" inputStyle={{ margin: "0.5rem 0px" }} />
            </Card>
            
            <div className='card' style={{ "padding": "0px",marginTop:"1rem" }}>
                <TextInput className="searchInput" placeholder={t("ATM_SEARCH_WAGE_SEEKERS_PLACEHOLDER")} signature={true} signatureImg={<SearchImg />} onChange={(e)=>onSearch(e.target.value)} style={{border:"none",borderRadius:"200px"}}/>
            </div>

            <TrackAttendenceTable state={state} dispatch={dispatch} searchQuery={searchQuery} />
        </React.Fragment>
    )
}

export default ViewRegister