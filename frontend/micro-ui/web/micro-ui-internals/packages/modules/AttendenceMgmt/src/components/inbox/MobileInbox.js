import React, {useState, useEffect, useMemo} from 'react'
import { useTranslation } from "react-i18next";
import { Link } from "react-router-dom";
import InboxLinks from './InboxLinks'
import { SearchAction, FilterAction, PopUp, Card, Loader, DetailsCard} from "@egovernments/digit-ui-react-components";

const MobileInbox = (props) => {
  const {isFilter, isSearch, parentRoute, data, onFilterChange, onSearch, isLoading, filterComponent, searchComponent, sortParams } = props;
  const { t } = useTranslation();
  const [type, setType] = useState("");
  const [popup, setPopup] = useState(false);
  const [sortparams, setSortParams] = useState(sortParams);

  const AttendenceInboxFilter = Digit.ComponentRegistryService.getComponent(filterComponent);
  const AttendenceInboxSearch = Digit.ComponentRegistryService.getComponent(searchComponent);

  useEffect(() => {
    if (type) setPopup(true);
  }, [type]);

  const propsMobileInboxCards = useMemo(() => {
    if (data?.noDataFound) {
        return [];
    }

    return data?.map((row) => ({
        ["Muster Roll ID"]: (
            <div>
                <span className="link">
                  <Link to={`viewAttendance?mustorRollId=${row.mustorRollId}`}>
                    {row.mustorRollId || t("ES_COMMON_NA")}
                  </Link>
                </span>
            </div> 
        ),
        ["Name of the Work"]: row?.work || t("ES_COMMON_NA"),
        ["Attendance Week"]: row.week || t("ES_COMMON_NA"),
        ["IA/IP"]: row?.iaip || t("ES_COMMON_NA"),
        ["Number of Individuals"]: row?.individualCount || t("ES_COMMON_NA"),
        ["SLA (days)"]: row?.slaDays || t("ES_COMMON_NA"),
    }));
  }, [data]);

  let result;
  if(isLoading) {
    result = <Loader />;
  } else if(data?.noDataFound || data?.length === 0) {
    result = (
      <Card>
        {t(data?.noDataFound)
          .split("\\n")
          .map((text, index) => (
            <p key={index} style={{ textAlign: "center" }}>
              {text}
            </p>
          ))}
      </Card>
    );
  } 
  else if(data?.length > 0) {
    result = (
      <DetailsCard
        {...{
          data: propsMobileInboxCards,
          linkPrefix: `/${window.contextPath}/employee/attendencemgmt/inbox`,
          serviceRequestIdKey: "mustorRollId",
        }}
      />)
  }
  else {
    result=null
  }

  const handlePopupClose = () => {
    setPopup(false);
    setType("");
    setSortParams(sortParams);
  };

  return (
    <div className="inbox-container">
      <div className="filters-container">
        <InboxLinks classNameForMobileView={"linksWrapperForMobileInbox"}/>
        <React.Fragment>
          <div className="searchBox">
            {isSearch && (
              <SearchAction
                text="SEARCH"
                handleActionClick={() => {
                  setType("SEARCH");
                  setPopup(true);
                }}
              />
            )}
            {isFilter && (
              <FilterAction
                text="FILTER"
                handleActionClick={() => {
                  setType("FILTER");
                  setPopup(true);
                }}
              />
            )}
          </div>
          {result}
          {popup && (
            <PopUp>
              {type === "FILTER" && (
                <div className="popup-module">
                  <AttendenceInboxFilter
                    type="mobile"
                    onFilterChange={onFilterChange}
                    onClose={handlePopupClose}
                  />
                </div>
              )}
              {type === "SEARCH" && (
                <div className="popup-module">
                  <AttendenceInboxSearch
                    type="mobile"
                    isInboxPage={true}
                    onSearch={onSearch}
                    onClose={handlePopupClose}
                  />
                </div>
              )}
            </PopUp>
          )}
        </React.Fragment>
      </div>
    </div>
  )
}

export default MobileInbox;