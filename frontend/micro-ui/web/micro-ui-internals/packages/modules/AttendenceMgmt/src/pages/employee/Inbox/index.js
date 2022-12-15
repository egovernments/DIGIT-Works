import React, {useEffect, useState, useCallback} from "react";
import { useForm } from "react-hook-form";
import { useTranslation } from "react-i18next";
import { Header } from "@egovernments/digit-ui-react-components";
import DesktopInbox from "../../../components/inbox/DesktopInbox";
import MobileInbox from "../../../components/inbox/MobileInbox";
import {dummyTableData} from '../../../config/dummyTableData';

const Inbox = ({isInbox, parentRoute, filterComponent, searchComponent,initialStates = {}}) => {
    const { t } = useTranslation();
    const tenantId = Digit.ULBService.getCurrentTenantId();
    let isMobile = window.Digit.Utils.browser.isMobile();
    const [sortParams, setSortParams] = useState(initialStates.sortParams || [{ id: "work", desc: false }]);
    const [searchParams, setSearchParams] = useState(() => {
      return initialStates?.searchParams || {
        offset: 0,
        limit: 10,
        sortBy: "work",
        sortOrder: "DESC",
      }
    });
    const [payload, setPayload] = useState({offset: 0,
      limit: 10,
      sortBy: "work",
      sortOrder: "DESC",});
    const { register, control, handleSubmit, setValue, getValues, reset } = useForm({
        defaultValues: {
          offset: 0, 
          limit: 10,
          sortBy: "work",
          sortOrder: "DESC",
        },
      });
    let paginationParams = isMobile
      ? { limit: 100, offset: 0, sortBy: sortParams?.[0]?.id, sortOrder: sortParams?.[0]?.desc ? "DESC" : "ASC" }
      : { limit: 100, offset: getValues("offset"), sortBy: sortParams?.[0]?.id, sortOrder: sortParams?.[0]?.desc ? "DESC" : "ASC" };

    const config = {
      enabled: !!(payload && Object.keys(payload).length > 0),
      cacheTime:0
    };

    //Todo: Update api call to fetch Attendance Mgmt Inbox
    const { isFetching, isLoading, data, ...rest } = Digit.Hooks.works.useInbox({
      tenantId,
      _filters: payload,
      config: config,
    });

    useEffect(() => { 
        register("offset", 0);
        register("limit", 10);
        register("sortBy", "work");
        register("sortOrder", "DESC");
    }, [register]);

    const onSort = useCallback((args) => {
        if (args.length === 0) return;
        setValue("sortBy", args.id);
        setValue("sortOrder", args.desc ? "DESC" : "ASC");
      }, []);

    const onPageSizeChange=(e)=> {
        setValue("limit", Number(e.target.value));
        handleSubmit(handleFilterChange)();
    }

    const nextPage=()=> {
        setValue("offset", getValues("offset") + getValues("limit"));
        handleSubmit(handleFilterChange)();
    }

    const previousPage=()=> {
        setValue("offset", getValues("offset") - getValues("limit"));
        handleSubmit(handleFilterChange)();
    }
    
    const handleFilterChange = (filterParam) => { };
    
    const getData = () => {
      if (dummyTableData?.length == 0 ) {
        return { noDataFound: "ES_COMMON_NO_DATA" }
      } else {
        return dummyTableData
      }   
    }
   
    return (
        <React.Fragment> {
            isMobile ?
             <MobileInbox 
              isFilter={true}
              isSearch={true}
              parentRoute={parentRoute}
              filterComponent={filterComponent}
              searchComponent={searchComponent}
              data={getData()}
              onFilterChange={handleFilterChange}
              onSearch={handleFilterChange}
              isLoading={isLoading}
              sortParams={sortParams}
             /> : 
            (<React.Fragment>
                {isInbox && <Header>{t("ES_COMMON_INBOX")}</Header>}
                 <DesktopInbox
                    isFilter={true}
                    isSearch={true}
                    parentRoute={parentRoute}
                    filterComponent={filterComponent}
                    searchComponent={searchComponent}
                    data={getData()}
                    onFilterChange={handleFilterChange}
                    onSearch={handleFilterChange}
                    onNextPage={nextPage}
                    onPrevPage={previousPage}
                    currentPage={getValues("offset") / getValues("limit")}
                    pageSizeLimit={getValues("limit")}
                    onPageSizeChange={onPageSizeChange}
                    totalRecords={dummyTableData.length}
                    onSort={onSort}
                    disableSort={false}
                    searchParams={searchParams}
                    sortParams={sortParams}
                    defaultSearchParams={initialStates.searchParams}
                    isLoading={isLoading}
                 /> 
            </React.Fragment>)
        }
        </React.Fragment>
    )
}

export default Inbox;