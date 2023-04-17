import React, { Fragment, useEffect, useCallback, useMemo, useState } from "react";
import { SearchForm, Table, Card, Loader, Header, CreateLoiIcon } from "@egovernments/digit-ui-react-components";
import { useForm, Controller } from "react-hook-form";
import SearchFields from "./SearchFields";
import { useTranslation } from "react-i18next";
import { Link } from "react-router-dom";
import MobileSearchApplication from "./MobileSearchApprovedEstimates";
const SearchApprovedSubEs = ({ tenantId, onSubmit, data, count,isLoading,resultOk,onClearSearch,showTable }) => {
    
    const { t } = useTranslation();

    const { register, control, handleSubmit, setValue, getValues, reset,formState } = useForm({
        defaultValues: {
            "offset": 0,
            "limit": 10,
            "sortBy": "department",
            "sortOrder": "DESC",
        }
    });

    useEffect(() => {
        register("offset", 0)
        register("limit", 10)
        register("sortBy", "department")
        register("sortOrder", "DESC")
    }, [register])

    const GetCell = (value) => <span className="cell-text">{value}</span>;
    const columns = useMemo(() => ([
        {
            Header: t("WORKS_SUB_ESTIMATE_NO"),
            accessor: "estimateNumber",
            disableSortBy: true,
            Cell: ({ row }) => {
                return (
                    <div>
                        <span className="link">
                            <Link to={`view-estimate?tenantId=${row.original.tenantId}&estimateNumber=${row.original.estimateNumber}&estimateStatus=Approved`}>
                                {row.original["estimateDetailNumber"]}
                            </Link>
                        </span>
                    </div>
                );
            },
        },
        {
            Header: t("WORKS_NAME_OF_WORK"),
            disableSortBy: true,
            accessor: (row) => GetCell(row.name || t("ES_COMMON_NA"))
        },
        {
            Header: t("WORKS_WARD"),
            disableSortBy: true,
            accessor: (row) => {
                let wardLocation =row?.location.replace(/(^:)|(:$)/g, '').split(":")
                return GetCell(wardLocation[4] ? t(`ES_COMMON_${wardLocation[4]}`) : t("NA"))
            }
        },
        {
            Header: t("WORKS_DEPARTMENT"),
            disableSortBy: true,
            accessor: (row) => GetCell(t(`ES_COMMON_${row.department}`)),
        },
        // {
        //     Header: t("WORKS_ADMIN_SANC_NO"),
        //     disableSortBy: true,
        //     accessor: (row) => GetCell(row.adminSanctionNumber || t("ES_COMMON_NA")),
        // },
        {
            Header: t("WORKS_DATE_CREATED"),
            disableSortBy: true,
            accessor: (row) => GetCell(Digit.DateUtils.ConvertEpochToDate(row.auditDetails.createdTime) ),
        },
        {
            Header: t("WORKS_ADMIN_APP_DATE"),
            disableSortBy: true,
            accessor: (row) => GetCell(Digit.DateUtils.ConvertEpochToDate(row.auditDetails.lastModifiedTime) ),
        },
        // {
        //     Header: t("WORKS_FUND"),
        //     disableSortBy: true,
        //     accessor: (row) => GetCell(t(`ES_COMMON_FUND_${row.fund}`) ),
        // },
        // {
        //     Header: t("WORKS_FUNCTION"),
        //     accessor: (row) => GetCell(t(`ES_COMMON_${row.function}`)),
        //     disableSortBy: true,
        // },
        // {
        //     Header: t("WORKS_BUDGET_HEAD"),
        //     accessor: (row) => GetCell(t(`ES_COMMON_BUDGETHEAD_${row.budgetHead}`)),
        //     disableSortBy: true,
        // },
        {
            Header: t("WORKS_CREATED_BY"),
            accessor: (row) => GetCell(row?.additionalDetails?.owner || t("ES_COMMON_NA")),
            disableSortBy: true,
        },
        // {
        //     Header: t("WORKS_OWNER"),
        //     accessor: (row) => GetCell(row?.additionalDetails?.owner || t("ES_COMMON_NA")),
        //     disableSortBy: true,
        // },
        {
            Header: t("WORKS_STATUS"),
            accessor: (row) => GetCell(row.estimateStatus || t("ES_COMMON_NA")),
            disableSortBy: true,
        },
        {
            Header: t("WORKS_TOTAL_AMT"),
            accessor: (row) => {
                let amount = row?.amount;
                amount = amount.toString();
                var lastThree = amount.substring(amount.length-3);
                var otherNumbers = amount.substring(0,amount.length-3);
                if(otherNumbers != '')
                    lastThree = ',' + lastThree;
                var res = otherNumbers.replace(/\B(?=(\d{2})+(?!\d))/g, ",") + lastThree;
                return GetCell(`Rs ${res}` || t("ES_COMMON_NA"))},
            disableSortBy: true,
        },
        {
            Header: t("WORKS_ACTIONS"),
            Cell: ({ row }) => {
                return (
                        <span className="link">
                        <Link to={`/${window?.contextPath}/employee/contracts/create-contract?estimateNumber=${row.original.estimateNumber}&task=${row.original.name}&subEstimate=${row.original["estimateDetailNumber"]}`}>
                                <div style={{"display":"flex","alignItems":"center"}}>
                                    <span ><CreateLoiIcon style={{ "margin": "auto" }} />  </span>
                                    <p>{t("WORKS_CREATE_CONTRACT")}</p>
                                </div>  
                            </Link>
                        </span>
                );
            },
            disableSortBy: true,
        }
    ]), [])

    // const onSort = useCallback((args) => {
    //     if (args.length === 0) return
    //     setValue("sortBy", args.id)
    //     setValue("sortOrder", args.desc ? "DESC" : "ASC")
    // }, [])

    function onPageSizeChange(e) {
        setValue("limit", Number(e.target.value))
        handleSubmit(onSubmit)()
    }

    function nextPage() {
        setValue("offset", getValues("offset") + getValues("limit"))
        
        handleSubmit(onSubmit)()
    }
    function previousPage() {
        
        setValue("offset", getValues("offset") - getValues("limit"))
        
        handleSubmit(onSubmit)()
    }


    const isMobile = window.Digit.Utils.browser.isMobile();

    if (isMobile) {
        return <MobileSearchApplication {...{ Controller, register, control, t, reset, previousPage, handleSubmit, tenantId, data, onSubmit }} />;
    }

    

    
    return (
        <>
            <Header styles={{ fontSize: "32px" }}>{t("WORKS_SEARCH_APPROVED_ESTIMATE")}</Header>
            <SearchForm onSubmit={onSubmit} handleSubmit={handleSubmit}>
                <SearchFields {...{ register, control, reset, t,formState,onClearSearch }} />
            </SearchForm>

            {showTable?isLoading?<Loader/>: data?.display && !resultOk ? (
                <Card style={{ marginTop: 20 }}>
                    {t(data?.display)
                        ?.split("\\n")
                        ?.map((text, index) => (
                            <p key={index} style={{ textAlign: "center" }}>
                                {text}
                            </p>
                        ))}
                </Card>
            ) : resultOk?
                    <div style={{ "overflowX": "scroll" }}>
                        <Table
                            t={t}
                            data={data}
                           // totalRecords={count}
                            columns={columns}
                            getCellProps={(cellInfo) => {
                                
                                return {
                                    style: {
                                        minWidth: cellInfo.column.Header === "Actions" ? "8rem" : "",
                                        padding: "20px 12px",
                                        fontSize: "16px"
                                    },
                                };
                            }}
                            onPageSizeChange={onPageSizeChange}
                            currentPage={getValues("offset") / getValues("limit")}
                            onNextPage={nextPage}
                            onPrevPage={previousPage}
                            pageSizeLimit={getValues("limit")}
                            //onSort={onSort}
                            //disableSort={false}
                            //sortParams={[{ id: getValues("sortBy"), desc: getValues("sortOrder") === "DESC" ? true : false }]}
                            autoSort={true}
                        />
                    </div>
            :null:null}

            
            
        </>
        
    )
}

export default SearchApprovedSubEs