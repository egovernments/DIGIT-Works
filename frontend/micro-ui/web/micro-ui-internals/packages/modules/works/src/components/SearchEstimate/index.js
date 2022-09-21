import React, { Fragment, useEffect, useCallback, useMemo } from "react";
import { SearchForm, Table, Card, Loader, Header } from "@egovernments/digit-ui-react-components";
import { useForm, Controller } from "react-hook-form";
import SearchFields from "./SearchFields";
import { useTranslation } from "react-i18next";
import { Link } from "react-router-dom";

const SearchEstimateApplication = ({onSubmit,data,resultOk,}) => {

    const { t } = useTranslation(); 
    const { register, control, handleSubmit, setValue, getValues, reset } = useForm({
      defaultValues: {
        offset: 0,
        limit: 10,
        sortBy: "department",
        sortOrder: "DESC",
        // isConnectionSearch: true,
      },
    });
  
    useEffect(() => {
      register("offset", 0);
      register("limit", 10);
      register("sortBy", "department");
      register("sortOrder", "DESC");
    }, [register]);
  
  return (
    <>
          <Header styles={{ fontSize: "32px" }}>{t("WORKS_SEARCH_ESTIMATES")}</Header>
          <SearchForm onSubmit={onSubmit} handleSubmit={handleSubmit} >
              <SearchFields {...{ register, control, reset, t }} />
          </SearchForm>
          {data?.display && resultOk ? 
            <Card style={{ marginTop: 20 }} >
              {t(data?.display)
                .split("\\n")
                .map((text, index) => (
                  <p key={index} style={{ textAlign: "center" }}>
                    {text}
                  </p>
                ))}
            </Card>:<Loader/>}

    </>
  )
}

export default SearchEstimateApplication