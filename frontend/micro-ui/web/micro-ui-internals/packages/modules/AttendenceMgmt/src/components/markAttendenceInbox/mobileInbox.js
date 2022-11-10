import React, { Fragment, useCallback, useMemo, useReducer, useState } from "react";
import { Link } from "react-router-dom";
import { CloseSvg, SearchForm, Table, Card, SearchAction, PopUp, DetailsCard, Loader, Toast } from "@egovernments/digit-ui-react-components";
import { useHistory } from "react-router-dom";
import SearchFormFields from "./SearchFields";
import { useForm, Controller } from "react-hook-form";
import { useTranslation } from "react-i18next";
//import CancelBillModal from "./CancelBillModal";

const mobileInbox = ({ tenantId="pb.amritsar", data={}, onSubmit=()=>console.log(submitted), isLoading=false, resultOk=true }) => {
    
    data= [[],[]]
    const history = useHistory()
    const { t } = useTranslation()
    const { register, control, handleSubmit, setValue, getValues, reset, formState } = useForm({
        // defaultValues: {
        //     offset: 0,
        //     limit: 10,
        //     sortBy: "commencementDate",
        //     sortOrder: "DESC",
        //     searchType: "CONNECTION",
        // },
    });

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

    const searchFormFieldsComponentProps = { Controller, register, control, t, reset };

    const MobileComponentDirectory = ({ currentlyActiveMobileModal, searchFormFieldsComponentProps, tenantId, ...props }) => {
        const { closeMobilePopupModal } = props;
        switch (currentlyActiveMobileModal) {
            case "SearchFormComponent":
                return (
                    <SearchForm {...props}>
                        <MobilePopUpCloseButton />
                        <div className="MobilePopupHeadingWrapper">
                            <h2>{t("ACTION_SEARCH")}:</h2>
                        </div>
                        <SearchFormFields {...searchFormFieldsComponentProps} {...{ closeMobilePopupModal, tenantId, t }} />
                    </SearchForm>
                );
            default:
                return <span></span>;
        }
    };

    const convertEpochToDate = (dateEpoch) => {
        if (dateEpoch == null || dateEpoch == undefined || dateEpoch == "") {
            return "NA";
        }
        const dateFromApi = new Date(dateEpoch);
        let month = dateFromApi.getMonth() + 1;
        let day = dateFromApi.getDate();
        let year = dateFromApi.getFullYear();
        month = (month > 9 ? "" : "0") + month;
        day = (day > 9 ? "" : "0") + day;
        return `${day}/${month}/${year}`;
    };

    const CurrentMobileModalComponent = useCallback(
        ({ currentlyActiveMobileModal, searchFormFieldsComponentProps, tenantId, ...props }) =>
            MobileComponentDirectory({ currentlyActiveMobileModal, searchFormFieldsComponentProps, tenantId, ...props }),
        [currentlyActiveMobileModal]
    );

    

    const GetCell = (value) => <span className="cell-text">{value}</span>;
   
    const propsMobileInboxCards = useMemo(() => {
        // if (data?.display) {
        //     return [];
        // }
        if (isLoading) {
            return [];
        }
        return data?.map((row) => ({
            // [t("ABG_COMMON_TABLE_COL_CONSUMER_NAME")]: row?.user?.name ? t(`${row?.user?.name}`) : "NA",
            // [t("ABG_COMMON_TABLE_COL_BILL_DATE")]: GetCell(convertEpochToDate(row?.billDate)),
            // [t("ABG_COMMON_TABLE_COL_BILL_AMOUNT")]: GetCell(row?.totalAmount || "NA"),
            // [t("ABG_COMMON_TABLE_COL_STATUS")]: GetCell(row?.status || "NA"),
            [t("REGISTER_NAME")]: "NA",
            [t("REGISTER_ID")]: "NA",
            [t("REGISTER_ORG_NAME")]: "NA",
            [t("REGISTER_DATES")]: "NA",
            [t("REGISTER_STATUS")]:"NA",
        }));
    }, [data]);

    return (
        <React.Fragment>
            {/* <div className="searchBox">
                <SearchAction
                    text={t("ACTION_SEARCH")}
                    handleActionClick={() => setActiveMobileModal({ type: "set", payload: "SearchFormComponent" })}
                    {...{ tenantId, t }}
                />
                <SearchAction
                    text={t("ACTION_FILTER")}
                    handleActionClick={() => setActiveMobileModal({ type: "set", payload: "SearchFormComponent" })}
                    {...{ tenantId, t }}
                />
                <SearchAction
                    text={t("ACTION_SORT")}
                    handleActionClick={() => setActiveMobileModal({ type: "set", payload: "SearchFormComponent" })}
                    {...{ tenantId, t }}
                />
            </div> */}
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
            {/* {isLoading && <Loader />} */}
            {!isLoading && resultOk && (
                <DetailsCard
                    {...{
                        handleSelect: (e) => { },
                        handleDetailCardClick: (e) => { history.push(`/${window.contextPath}/employee/attendencemgmt/view-register/`, { state:{} }) },
                        data: propsMobileInboxCards,
                        serviceRequestIdKey: t("Register_Name"),
                    }}
                />
            )}
        </React.Fragment>
    );
};

export default mobileInbox;
