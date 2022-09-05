import React, { Fragment, useEffect, useCallback, useMemo } from "react";
import { SearchForm, Table, Card, Loader, Header } from "@egovernments/digit-ui-react-components";
import { useForm, Controller } from "react-hook-form";
import SearchFields from "./SearchFields";
import { useTranslation } from "react-i18next";
import { Link } from "react-router-dom";

const SearchEstimateApplication = ({onSubmit}) => {

    const { t } = useTranslation();

    const { register, control, handleSubmit, setValue, getValues, reset } = useForm();
  return (
    <>
          <Header styles={{ fontSize: "32px" }}>{t("WORKS_SEARCH_ESTIMATES")}</Header>
          <SearchForm onSubmit={onSubmit} handleSubmit={handleSubmit} >
              <SearchFields {...{ register, control, reset, t }} />
          </SearchForm>
    </>
  )
}

export default SearchEstimateApplication