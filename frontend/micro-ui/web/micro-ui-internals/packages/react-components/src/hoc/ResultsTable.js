import React, { useMemo, useCallback, useState, useEffect, Fragment,useContext } from 'react'
import { useTranslation } from 'react-i18next';
import DetailsCard from '../molecules/DetailsCard'
import Table from '../atoms/Table'
import TextInput from '../atoms/TextInput'
import { useForm, Controller } from "react-hook-form";
import _ from "lodash";
import { InboxContext } from './InboxSearchComposerContext';
import { Link } from "react-router-dom";
import { Loader } from '../atoms/Loader';
import Card from '../atoms/Card'
// const searchResult = [
//     {
//         "_id": "63cfb686a362962fa93c03a2",
//         "balance": "$2,782.00",
//         "age": 25,
//         "eyeColor": "blue",
//         "name": "Schultz Short",
//         "gender": "male",
//         "company": "ZOXY",
//         "email": "schultzshort@zoxy.com",
//         "phone": "+1 (850) 563-3334",
//         "phno":{
//             "phone": "999977777",
//         },
//         "tags": [
//             "sunt",
//             "aute",
//             "ad",
//             "eiusmod",
//             "nostrud",
//             "exercitation",
//             "esse"
//         ],
//         "friends": [
//             {
//                 "id": 0,
//                 "name": "Michele Wolfe"
//             },
//             {
//                 "id": 1,
//                 "name": "Huffman Sharp"
//             },
//             {
//                 "id": 2,
//                 "name": "Tommie Burch"
//             }
//         ],
//         "greeting": "Hello, Schultz Short! You have 5 unread messages.",
//         "favoriteFruit": "banana"
//     },
//     {
//         "phno": {
//             "phone": "+1 (850) 563-3334",
//         },
//         "_id": "63cfb68604e9b8272bdf9f2f",
//         "balance": "$3,080.13",
//         "age": 30,
//         "eyeColor": "green",
//         "name": "Winifred Eaton",
//         "gender": "female",
//         "company": "GEOFORMA",
//         "email": "winifredeaton@geoforma.com",
//         "phone": "+1 (957) 437-2550",
//         "tags": [
//             "qui",
//             "qui",
//             "cupidatat",
//             "est",
//             "irure",
//             "sit",
//             "culpa"
//         ],
//         "friends": [
//             {
//                 "id": 0,
//                 "name": "Bobbie Nichols"
//             },
//             {
//                 "id": 1,
//                 "name": "Bean Clayton"
//             },
//             {
//                 "id": 2,
//                 "name": "Daniels Austin"
//             }
//         ],
//         "greeting": "Hello, Winifred Eaton! You have 8 unread messages.",
//         "favoriteFruit": "banana"
//     },
//     {
//         "phno": {
//             "phone": "+1 (850) 563-3334",
//         },
//         "_id": "63cfb686a7414ae3e0aaf6e3",
//         "balance": "$1,593.75",
//         "age": 20,
//         "eyeColor": "green",
//         "name": "Anna Pickett",
//         "gender": "female",
//         "company": "PERKLE",
//         "email": "annapickett@perkle.com",
//         "phone": "+1 (860) 580-3081",
//         "tags": [
//             "nisi",
//             "incididunt",
//             "et",
//             "do",
//             "qui",
//             "et",
//             "exercitation"
//         ],
//         "friends": [
//             {
//                 "id": 0,
//                 "name": "Beverley Nicholson"
//             },
//             {
//                 "id": 1,
//                 "name": "Evans Cherry"
//             },
//             {
//                 "id": 2,
//                 "name": "Mandy Wilkinson"
//             }
//         ],
//         "greeting": "Hello, Anna Pickett! You have 1 unread messages.",
//         "favoriteFruit": "banana"
//     },
//     {
//         "phno": {
//             "phone": "+1 (850) 563-3334",
//         },
//         "_id": "63cfb6867f2982e5515ed1d4",
//         "balance": "$3,852.13",
//         "age": 35,
//         "eyeColor": "brown",
//         "name": "Stevenson Craft",
//         "gender": "male",
//         "company": "QUAILCOM",
//         "email": "stevensoncraft@quailcom.com",
//         "phone": "+1 (977) 540-3685",
//         "tags": [
//             "excepteur",
//             "culpa",
//             "anim",
//             "labore",
//             "deserunt",
//             "sit",
//             "do"
//         ],
//         "friends": [
//             {
//                 "id": 0,
//                 "name": "Rollins Jennings"
//             },
//             {
//                 "id": 1,
//                 "name": "Colette Carr"
//             },
//             {
//                 "id": 2,
//                 "name": "Fitzpatrick Bartlett"
//             }
//         ],
//         "greeting": "Hello, Stevenson Craft! You have 3 unread messages.",
//         "favoriteFruit": "apple"
//     },
//     {
//         "phno": {
//             "phone": "+1 (850) 563-3334",
//         },
//         "_id": "63cfb6860fcdcfe1dbdacec3",
//         "balance": "$1,928.10",
//         "age": 39,
//         "eyeColor": "blue",
//         "name": "Good Acevedo",
//         "gender": "male",
//         "company": "ARCHITAX",
//         "email": "goodacevedo@architax.com",
//         "phone": "+1 (936) 544-3872",
//         "tags": [
//             "dolore",
//             "voluptate",
//             "est",
//             "nulla",
//             "et",
//             "anim",
//             "qui"
//         ],
//         "friends": [
//             {
//                 "id": 0,
//                 "name": "Cassandra Cardenas"
//             },
//             {
//                 "id": 1,
//                 "name": "Monica Clements"
//             },
//             {
//                 "id": 2,
//                 "name": "Rochelle Sharpe"
//             }
//         ],
//         "greeting": "Hello, Good Acevedo! You have 5 unread messages.",
//         "favoriteFruit": "banana"
//     }
// ]


const ResultsTable = ({ tableContainerClass, config,data,isLoading }) => {
    
    const { t } = useTranslation();
    const resultsKey = config.resultsJsonPath
    const searchResult = data?.[resultsKey] ? data?.[resultsKey] : []
    
    const {state,dispatch} = useContext(InboxContext)
    
    const tableColumns = useMemo(() => {
        //test if accessor can take jsonPath value only and then check sort and global search work properly
        return config?.columns?.map(column => {
            
            if(column.redirectUrl){
                return {
                    Header: t(column?.label) || t("ES_COMMON_NA"),
                    accessor:column.jsonPath,
                    Cell: ({ value, col, row }) => {
                        //integrate row.original with jsonPath
                        // return <span className="link">
                        //     <Link to={column?.redirectUrl || '/works-ui/employee/project/project-inbox-item'}>{String(t(_.get(row.original, column.jsonPath, "NA")))}</Link>
                        // </span>
                        return <span className="link">
                            <Link to={column?.redirectUrl || '/works-ui/employee/project/project-inbox-item'}>{String(value ? column.translate ? t(column.prefix ? `${column.prefix}${value}` : value) : value:t("ES_COMMON_NA"))}</Link>
                        </span>
                    }
                }
            }
            return {
                Header: t(column?.label) || t("ES_COMMON_NA"),
                accessor: column.jsonPath,
                Cell: ({ value, col, row }) => {
                    // if (column.preProcessfn){
                    //     return String( column.preProcessfn("pb.amritsar", { uuid: [uuid] }, {}) || t("ES_COMMON_NA"));
                    // }
                    // return String(t(_.get(row.original,column.jsonPath,"NA")));
                    return String(value ? column.translate? t(column.prefix?`${column.prefix}${value}`:value) : value : t("ES_COMMON_NA"));
                }
            }
        })
    }, [config])

    const {
        register,
        handleSubmit,
        setValue,
        getValues,
        reset,
        watch,
        trigger,
        control,
        formState,
        errors,
        setError,
        clearErrors,
        unregister,
    } = useForm({
        defaultValues: config.defaultValues,
    });
    
    const isMobile = window.Digit.Utils.browser.isMobile();
    const [searchQuery, onSearch] = useState("");

    const filterValue = useCallback((rows, id, filterValue = "") => {

        return rows.filter((row) => {
            const res = Object.keys(row?.values).find((key) => {
                if (typeof row?.values?.[key] === "object") {
                    return Object.keys(row?.values?.[key]).find((id) => {
                        if (id === "insight") {
                            return String(Math.abs(row?.values?.[key]?.[id]) + "%")
                                .toLowerCase()
                                .startsWith(filterValue?.toLowerCase());
                        }
                        return String(row?.values?.[key]?.[id])?.toLowerCase().includes(filterValue?.toLowerCase());
                    });
                }
                return (
                    String(row?.values?.[key]).toLowerCase()?.includes(filterValue?.toLowerCase()) ||
                    String(t(row?.values?.[key])).toLowerCase()?.includes(filterValue?.toLowerCase())
                );
            });
            return res;
        });
    }, []);

    useEffect(() => {
        register("offset", 0);
        register("limit", 10);
        // register("sortBy", "department");
        
    }, [register]);

    function onPageSizeChange(e) {
        setValue("limit", Number(e.target.value));
        handleSubmit(onSubmit)();
    }

    function nextPage() {
        setValue("offset", getValues("offset") + getValues("limit"));
        handleSubmit(onSubmit)();
    }
    function previousPage() {
        setValue("offset", getValues("offset") - getValues("limit"));
        handleSubmit(onSubmit)();
    }

    const onSubmit = (data) => {
        //here update the reducer state
        //call a dispatch to update table's part of the state and update offset, limit
        // this will in turn make the api call and give search results and table will be rendered acc to the new data
        
        dispatch({
            type:"tableForm",
            state:{...data}
        })
        
    }

    if (isLoading) return <Loader />
    if (searchResult.length === 0) return <Card style={{ marginTop: 20 }}>
        {t("ES_COMMON_NO_DATA")
            .split("\\n")
            .map((text, index) => (
                <p key={index} style={{ textAlign: "center" }}>
                    {text}
                </p>
            ))}
    </Card>
    return (
        <div >
            {config?.enableGlobalSearch && <div className='card' style={{ "padding": "0px", marginTop: "1rem" }}>
            <TextInput className="searchInput"  onChange={(e) => onSearch(e.target.value)} style={{ border: "none", borderRadius: "200px" }} />
             </div>}
            {searchResult.length > 0 && <Table
                //className="table-fixed-first-column-wage-seekers wage-seekers-table"
                t={t}
                //customTableWrapperClassName={"dss-table-wrapper"}
                disableSort={config?.enableColumnSort ? false : true}
                autoSort={config?.enableColumnSort ? true : false}
                //manualPagination={false}
                globalSearch={config?.enableGlobalSearch ? filterValue : undefined}
                onSearch={config?.enableGlobalSearch ? searchQuery : undefined}
                data={searchResult}
                totalRecords={searchResult?.length}//put total count return from api here
                columns={tableColumns}
                isPaginationRequired={true}
                onPageSizeChange={onPageSizeChange}
                currentPage={getValues("offset") / getValues("limit")}
                onNextPage={nextPage}
                onPrevPage={previousPage}
                pageSizeLimit={getValues("limit")}
                getCellProps={(cellInfo) => {
                    return {
                        style: {
                            padding: "20px 18px",
                            fontSize: "16px",
                        },
                    };
                }}
            />}
        </div>
    )
}

export default ResultsTable