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