import React, { useEffect, useState } from "react";
import { useTranslation } from "react-i18next";
import { ApplicationCard } from "./LOIInbox/ApplicationCard";
import InboxLinks from "./LOIInbox/InboxLink";

const LOIMobileInbox = ({data, isLoading, isSearch, searchFields, onFilterChange, onSearch, onSort, parentRoute, searchParams, sortParams, linkPrefix, tableConfig, filterComponent, }) => {
  const { t } = useTranslation();
  
  return (
    <div style={{ padding: 0 }}>
      <div className="inbox-container">
        <div className="filters-container">
          {!isSearch && <InboxLinks classNameForMobileView="linksWrapperForMobileInbox" linkPrefix={parentRoute} isMobile={true} />}
          <ApplicationCard
            t={t}
            data={data}
            onFilterChange={onFilterChange}
            isLoading={isLoading}
            isSearch={isSearch}
            onSearch={onSearch}
            onSort={onSort}
            searchParams={searchParams}
            searchFields={searchFields}
            linkPrefix={linkPrefix}
            sortParams={sortParams}
            serviceRequestIdKey={tableConfig?.serviceRequestIdKey}
            filterComponent={filterComponent}
          />
        </div>
      </div>
    </div>
  );
};

export default LOIMobileInbox;