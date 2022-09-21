import React, { Fragment, useEffect, useCallback, useMemo } from "react";
import { SearchForm, Table, Card, Loader, Header, CreateLoiIcon } from "@egovernments/digit-ui-react-components";
import { useForm, Controller } from "react-hook-form";
import SearchFields from "./SearchFields";
import { useTranslation } from "react-i18next";
import { Link } from "react-router-dom";

const SearchApprovedSubEs = ({ tenantId, onSubmit, data, count }) => {
    const { t } = useTranslation();

    const { register, control, handleSubmit, setValue, getValues, reset,formState } = useForm({
        defaultValues: {
            "offset": 0,
            "limit": 10,
            "sortBy": "commencementDate",
            "sortOrder": "DESC",
            "estimateNumber": "12123",
            "estiamteDetailNumber": "213131221",
            "adminSanctionNumber": "12121",
            "department": {
                "name": "Engg"
            },
            "fromProposalDate": "2022-09-01",
            "toProposalDate": "2022-09-02"
        }
    });

    useEffect(() => {
        register("offset", 0)
        register("limit", 10)
        register("sortBy", "createdTime")
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
                            <Link to={`/digit-ui/employee/`}>
                                {row.original["estimateNumber"]}
                            </Link>
                        </span>
                    </div>
                );
            },
        },
        {
            Header: t("WORKS_NAME_OF_WORK"),
            disableSortBy: true,
            accessor: (row) => GetCell(row.subject)
        },
        {
            Header: t("WORKS_DEPARTMENT"),
            disableSortBy: true,
            accessor: (row) => GetCell(row.department),
        },
        {
            Header: t("WORKS_ADMIN_SANC_NO"),
            disableSortBy: true,
            accessor: (row) => GetCell(row.adminSanctionNumber),
        },
        {
            Header: t("WORKS_ADMIN_APP_DATE"),
            disableSortBy: true,
            accessor: (row) => GetCell(row.adminSanctionNumber),
        },
        {
            Header: t("WORKS_FUND"),
            disableSortBy: true,
            accessor: (row) => GetCell(row.fund ),
        },
        {
            Header: t("WORKS_FUNCTION"),
            accessor: (row) => GetCell(row.function),
            disableSortBy: true,
        },
        {
            Header: t("WORKS_BUDGET_HEAD"),
            accessor: (row) => GetCell(row.budgetHead),
            disableSortBy: true,
        },
        {
            Header: t("WORKS_CREATED_BY"),
            accessor: (row) => GetCell(row.auditDetails.createdBy),
            disableSortBy: true,
        },
        {
            Header: t("WORKS_OWNER"),
            accessor: (row) => GetCell("owner"),
            disableSortBy: true,
        },
        {
            Header: t("WORKS_STATUS"),
            accessor: (row) => GetCell(row.estimateStatus),
            disableSortBy: true,
        },
        {
            Header: t("WORKS_TOTAL_AMT"),
            accessor: (row) => GetCell(row.totalAmount),
            disableSortBy: true,
        },
        {
            Header: t("WORKS_ACTIONS"),
            Cell: ({ row }) => {
                return (
                    <div>
                        <span className="link">
                            <Link to={`/digit-ui/employee/`}>
                                <span ><CreateLoiIcon  style={{ "margin": "auto" }} /> {"Create LOI"} </span>
                            </Link>
                        </span>
                    </div>
                );
            },
            disableSortBy: true,
        }
    ]), [])

    const onSort = useCallback((args) => {
        if (args.length === 0) return
        setValue("sortBy", args.id)
        setValue("sortOrder", args.desc ? "DESC" : "ASC")
    }, [])

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


    return (
        <>
            <Header styles={{ fontSize: "32px" }}>{t("WORKS_SEARCH_APPROVED_ESTIMATE")}</Header>
            <SearchForm onSubmit={onSubmit} handleSubmit={handleSubmit} >
                <SearchFields {...{ register, control, reset, t,formState }} />
            </SearchForm>
            <div style={{"overflow-x":"scroll"}}>
            <Table
                t={t}
                data={data}
                totalRecords={count}
                columns={columns}
                getCellProps={(cellInfo) => {
                    return {
                        style: {
                            minWidth: cellInfo.column.Header === t("ES_INBOX_APPLICATION_NO") ? "240px" : "",
                            padding: "20px 18px",
                            fontSize: "16px"
                        },
                    };
                }}
                onPageSizeChange={onPageSizeChange}
                currentPage={parseInt((getValues("offset") / getValues("limit")))}
                onNextPage={nextPage}
                onPrevPage={previousPage}
                pageSizeLimit={getValues("limit")}
                onSort={onSort}
                disableSort={false}
                sortParams={[{ id: getValues("sortBy"), desc: getValues("sortOrder") === "DESC" ? true : false }]}
            />
            </div>
        </>
        
    )
}

export default SearchApprovedSubEs