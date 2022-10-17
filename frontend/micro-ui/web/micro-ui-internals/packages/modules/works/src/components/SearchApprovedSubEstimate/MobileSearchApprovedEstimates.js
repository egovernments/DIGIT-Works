import React, { Fragment, useCallback, useMemo, useReducer } from "react";
import { Link } from "react-router-dom";
import { CloseSvg, SearchForm, Table, Card, SearchAction, PopUp, DetailsCard, Loader, Toast, CreateLoiIcon } from "@egovernments/digit-ui-react-components";
import SearchFormFields from "./SearchFields";

const MobileSearchApplication = ({ Controller, register, control, t, reset, previousPage, handleSubmit, tenantId, data, onSubmit, businessService="" }) => {
    function activateModal(state, action) {
        switch (action.type) {
            case "set":
                return action.payload;
            case "remove":
                return false;
            default:
                break;
        }
    }

    const [currentlyActiveMobileModal, setActiveMobileModal] = useReducer(activateModal, false);

    const closeMobilePopupModal = () => {
        setActiveMobileModal({ type: "remove" });
    };

    const MobilePopUpCloseButton = () => (
        <div className="InboxMobilePopupCloseButtonWrapper" onClick={closeMobilePopupModal}>
            <CloseSvg />
        </div>
    );

    const searchFormFieldsComponentProps = { Controller, register, control, t, reset, previousPage, businessService };
    const MobileComponentDirectory = ({ currentlyActiveMobileModal, searchFormFieldsComponentProps, tenantId, ...props }) => {
        const { closeMobilePopupModal } = props;
        switch (currentlyActiveMobileModal) {
            case "SearchFormComponent":
                return (
                    <SearchForm {...props}>
                        <MobilePopUpCloseButton />
                        <div className="MobilePopupHeadingWrapper">
                            <h2>{t("ES_COMMON_SEARCH")}:</h2>
                        </div>
                        <SearchFormFields {...searchFormFieldsComponentProps} {...{ closeMobilePopupModal, tenantId, t }} />
                        {/* <SearchField className="submit">
                      <SubmitBar label={t("ES_COMMON_SEARCH")} submit form="search-form"/>
                      <p onClick={onResetSearchForm}>{t(`ES_COMMON_CLEAR_ALL`)}</p>
                  </SearchField> */}
                    </SearchForm>
                );
            default:
                return <span></span>;
        }
    };

    const CurrentMobileModalComponent = useCallback(
        ({ currentlyActiveMobileModal, searchFormFieldsComponentProps, tenantId, ...props }) =>
            MobileComponentDirectory({ currentlyActiveMobileModal, searchFormFieldsComponentProps, tenantId, ...props }),
        [currentlyActiveMobileModal]
    );

    const propsMobileInboxCards = useMemo(() => {
        if (data?.display) {
            return [];
        }

        return data?.map((row) => ({
            [t("WORKS_SUB_ESTIMATE_NO")]: (
                <div>
                    <span className="link">
                        <Link to={`view-estimate?tenantId=${row.tenantId}&estimateNumber=${row.estimateNumber}&estimateStatus=Approved`}>
                            {row["estimateDetailNumber"]}
                        </Link>
                    </span>
                </div>
            ),
            [t("WORKS_NAME_OF_WORK")]: row.name || t("ES_COMMON_NA"),
            [t("WORKS_DEPARTMENT")]: t(`ES_COMMON_${row.department}`),
            [t("WORKS_ADMIN_SANC_NO")]: row.adminSanctionNumber || t("ES_COMMON_NA"),
            [t("WORKS_ADMIN_APP_DATE")]: Digit.DateUtils.ConvertEpochToDate(row.auditDetails.lastModifiedTime),
            [t("WORKS_FUND")]: t(`ES_COMMON_FUND_${row.fund}`),
            [t("WORKS_FUNCTION")]: t(`ES_COMMON_${row.function}`),
            [t("WORKS_BUDGET_HEAD")]: t(`ES_COMMON_BUDGETHEAD_${row.budgetHead}`),
            [t("WORKS_CREATED_BY")]: row?.additionalDetails?.owner || t("ES_COMMON_NA"),
            [t("WORKS_OWNER")]: row?.additionalDetails?.owner || t("ES_COMMON_NA"),
            [t("WORKS_STATUS")]: row.estimateStatus || t("ES_COMMON_NA"),
            [t("WORKS_TOTAL_AMT")]: row.amount || t("ES_COMMON_NA"),
            [t("WORKS_ACTIONS")]: (
                <span className="link">
                    <Link to={`create-loi?estimateNumber=${row.estimateNumber}&subEstimateNumber=${row.estimateDetailNumber}`}>
                        <div style={{ "display": "flex", "justifyContent": "flex-start", "alignItems": "center" }}>
                            <span ><CreateLoiIcon style={{ "margin": "auto" }} />  </span>
                            <p style={{ "marginLeft": "0.5rem" }}>{t("WORKS_Create_LOI")}</p>
                        </div>
                    </Link>
                </span>
            )

        }));
    }, [data]);

    return (
        <React.Fragment>
            <div className="searchBox">
                <SearchAction
                    text={t("ES_COMMON_SEARCH")}
                    handleActionClick={() => setActiveMobileModal({ type: "set", payload: "SearchFormComponent" })}
                    {...{ tenantId, t }}
                />
                {/* {isInboxLoading ? <Loader /> : <FilterAction text={t("ES_COMMON_FILTER")} handleActionClick={() => setActiveMobileModal({type:"set", payload:"FilterFormComponent"})}/>} */}
                {/* <SortAction text={t("ES_COMMON_SORT")} handleActionClick={() => setActiveMobileModal({type:"set", payload:"SortComponent"})}/> */}
            </div>
            {currentlyActiveMobileModal ? (
                <PopUp>
                    <CurrentMobileModalComponent
                        onSubmit={(data) => {
                            setActiveMobileModal({ type: "remove" });
                            onSubmit(data);
                        }}
                        handleSubmit={handleSubmit}
                        id="search-form"
                        className="rm-mb form-field-flex-one inboxPopupMobileWrapper"
                        {...{ searchFormFieldsComponentProps, currentlyActiveMobileModal, closeMobilePopupModal, tenantId }}
                    />
                </PopUp>
            ) : null}
            {data?.display ? (
                <Card style={{ marginTop: 20 }}>
                    {t(data?.display)
                        .split("\\n")
                        .map((text, index) => (
                            <p key={index} style={{ textAlign: "center" }}>
                                {text}
                            </p>
                        ))}
                </Card>
            ) : (
                <DetailsCard
                    {...{
                        data: propsMobileInboxCards,
                        linkPrefix: `/${window.contextPath}/employee/works/view-estimate/`,
                            serviceRequestIdKey: t("WORKS_SUB_ESTIMATE_NO"),
                    }}
                />
            )}
        </React.Fragment>
    );
};

export default MobileSearchApplication;
