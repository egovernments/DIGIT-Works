import React, { Fragment, useState } from 'react'
import { useTranslation } from "react-i18next";
import { Toast } from "@egovernments/digit-ui-react-components";

const SearchOrganization = () => {
    const { t } = useTranslation();
    const SearchOrganisationApplication = Digit.ComponentRegistryService.getComponent("SearchOrganisationApplication");
    const [showToast, setShowToast] = useState(null);
    const [showTable, setShowTable] = useState(true)
    const onClearSearch = (isShow = true) => {
        setShowTable(isShow);
    }
  const onSubmit = async (_data) => {
    setShowTable(true)

      if(data.nameOfTheOrganisation==="" ){
        setShowToast({ warning: true, label: "ERR_PT_FILL_VALID_FIELDS" });
        setTimeout(() => {
          setShowToast(false);
        }, 3000);
        return
      }
    }

    const getData = () => {
      return [];
    }
  
    const isResultsOk = () => {
      return true;
      //return result?.data.contracts?.length > 0 ? true : false;
    }
  
  return (
    <Fragment>
      <SearchOrganisationApplication
        onSubmit={onSubmit}
        data={getData()}
        // count={result?.count}
        resultOk={isResultsOk()}
        // isLoading={result?.isLoading}
        onClearSearch={onClearSearch} 
        showTable={showTable}
      />
      {showToast && (
        <Toast
          error={showToast.error}
          warning={showToast.warning}
          label={t(showToast.label)}
          onClose={() => {
            setShowToast(null);
          }}
        />
      )}
    </Fragment>
  )
}

export default SearchOrganization