import React, { Fragment, useState, useMemo, } from 'react'
import cloneDeep from "lodash/cloneDeep";
import { Dropdown,Table } from '@egovernments/digit-ui-react-components';
import SkillSelector from './SkillSelector';




const AttendenceTablev1 = ({ userState,setUserState,t }) => {
    
    const [showSkillSelector,setShowSkillSelector] = useState(true)

    const setSkill = (e) => {
        
    }

    const updateState = (idxOut, idxIn) => {
        console.log(idxOut, idxIn);

        //const updatedStateObj = userState
        const updatedStateObj = cloneDeep(userState)

        updatedStateObj[idxOut].state[idxIn].attendence = nextState(updatedStateObj[idxOut].state[idxIn].attendence)
        setUserState(updatedStateObj)
    }

    
    const GetCell = (value) => <span className="cell-text">{value}</span>;

    const columns = useMemo(() => ([
        {
            Header: t("ATM_NAME"),
            accessor: (row) => GetCell(row.name || t("ES_COMMON_NA")),
        },
        {
            Header: t("ATM_GUARDIAN"),
            accessor: (row) => GetCell(row.guardian || t("ES_COMMON_NA"))
        },
        {
            Header: t("ATM_AADHAR"),
            accessor: (row) => GetCell(row.aadhar || t("ES_COMMON_NA")),
        },
        
       
        // {
        //     Header: t("ATM_SKILL_LEVEL"),
        //     disableSortBy: true,
        //     Cell: ({ row }) => {
        //         return (
        //             <Dropdown option={sampleSkills} optionKey={"level"} value={row.skill} selected={ulbLists} select={setulbLists} t={t} />
        //         );
        //         },
        // },
        
    ]), [])


    


    return (
        <>
        {showSkillSelector && <SkillSelector t={t} closeModal={setShowSkillSelector} userState={userState} setUserState={setSkill} /> }
        <div style={{ "overflowX": "scroll" }} className={"card"}>
            <Table
                t={t}
                data={userState}
                columns={columns}
                //onSort={onSort}
                //disableSort={false}
                //sortParams={[{ id: getValues("sortBy"), desc: getValues("sortOrder") === "DESC" ? true : false }]}
                autoSort={true}
                manualPagination={false}
                disableSort={false}
                className="customTable table-fixed-first-column"
                getCellProps={(cellInfo) => {
                    return {
                        style: {},
                    };
                }}
            />
        </div>
        </>
    )
}

export default AttendenceTablev1