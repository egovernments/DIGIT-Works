import React, { useEffect, useState, useMemo } from "react";
import { useTranslation } from "react-i18next";
import { ApplicationCard } from "./ChecklistInbox/ApplicationCard";
import ApplicationLinks from "./ChecklistInbox/ApplicationLinks";

const MobileInbox = ({ data, isLoading, isSearch, searchFields, onFilterChange, onSearch, onSort, parentRoute, searchParams, sortParams, linkPrefix, tableConfig, filterComponent, }) => {
    const { t } = useTranslation();
    return (
        <div style={{ padding: 0 }}>
            <div className="inbox-container">
                <div className="filters-container">
                    {!isSearch && <ApplicationLinks classNameForMobileView="linksWrapperForMobileInbox" linkPrefix={parentRoute} isMobile={true} />}
                    <ApplicationCard
                        t={t}
                        data={data.table}
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

export default MobileInbox;