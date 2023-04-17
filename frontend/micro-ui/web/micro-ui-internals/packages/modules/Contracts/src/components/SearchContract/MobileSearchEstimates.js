import React, { Fragment, useCallback, useMemo, useReducer } from "react";
import { Link } from "react-router-dom";
import { CloseSvg, SearchForm, Card, SearchAction, PopUp, DetailsCard } from "@egovernments/digit-ui-react-components";
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
            [t("WORKS_CONTRACT_ID")]: (
                <div>
                    <span className="link">
                    <Link
                      to={`view-contract?tenantId=${row?.tenantId}&contractId=${row?.contractId}`}>
                      {row?.contractId || "NA"}
                    </Link>
                    </span>
                </div> 
            ),
            [t("WORKS_CONTRACT_DATE")]: row?.contractDate || t("ES_COMMON_NA"),
            [t("WORKS_CONTRACT_TYPE")]: row.contractType || t("ES_COMMON_NA"),
            [t("WORKS_NAME_OF_WORK")]: row.nameOfTheWork || t("ES_COMMON_NA"),
            [t("WORKS_ABSTRACT_ESTIMATE_NO")]: row.abstractEstimateNumber || t("ES_COMMON_NA"),
            [t("WORKS_IMPLEMENT_AUTH")]: row.implementingAuthority || t("ES_COMMON_NA"),
            [t("WORKS_NAME_OF_ORGN")]: row.orgnName || t("ES_COMMON_NA"),
            [t("WORKS_OFFICER_INCHARGE_NAME")]: row.officerIncharge || t("ES_COMMON_NA"),
            [t("WORKS_AGREEMENT_AMT")]: row.agreemntAmount || t("ES_COMMON_NA"),
            [t("WORKS_STATUS")]: row.status || t("ES_COMMON_NA"),
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
                        linkPrefix: `/${window.contextPath}/employee/contracts/view-contract/`,
                            serviceRequestIdKey: t("WORKS_CONTRACT_ID"),
                    }}
                />
            )}
        </React.Fragment>
    );
};

export default MobileSearchApplication;