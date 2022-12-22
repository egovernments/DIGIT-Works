import React, { Fragment, useState } from 'react'
import { useTranslation } from "react-i18next";
import { Toast } from "@egovernments/digit-ui-react-components";

const SearchOrganization = () => {
    const { t } = useTranslation();
    const SearchOrganisationApplication = Digit.ComponentRegistryService.getComponent("SearchOrganisationApplication");
    const [showToast, setShowToast] = useState(null);
    const [showTable, setShowTable] = useState(true);
    const [selectedOrg, setSelectedOrg] = useState([]);

    const onClearSearch = (isShow = true) => {
        setShowTable(isShow);
    }
  const onSubmit = async (data) => {
    setShowTable(true);
      if(!data?.nameOfTheOrg){
        setShowToast({ warning: true, label: "ERR_PT_FILL_VALID_FIELDS" });
        setTimeout(() => {
          setShowToast(false);
        }, 3000);
        return
      }else{
        setSelectedOrg(data?.nameOfTheOrg);
      }
    }

    const result = {
      status: "success",
      isSuccess: true,
      totalCount: 10,
      isLoading: false,
      data:{
        Organisation1: [
          {
            org_id : "DXTYUIOPO89",
            name_of_org : "Organisation1",
            type_of_org : "TypeA",
            org_category : "CategoryA",
            no_of_members : "10",
            district : "DistrictA"
          }
        ]
      }
    }

    const getData = () => {
      if (result?.data?.Organisation1?.length == 0 ) {
        return { display: "ES_COMMON_NO_DATA" }
      } else if (result?.data?.Organisation1?.length > 0) {
        return result?.data?.Organisation1;
      } else {
        return [];
      }
    }
  
    const isResultsOk = () => {
      return result?.data.Organisation1?.length > 0 ? true : false;
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