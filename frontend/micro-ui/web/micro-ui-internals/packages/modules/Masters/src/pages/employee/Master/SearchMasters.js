import React, { Fragment, useState } from 'react'
import { useTranslation } from "react-i18next";
import { Toast } from "@egovernments/digit-ui-components";

const SearchMasters = () => {
    const { t } = useTranslation();
    const SearchMastersApplication = Digit.ComponentRegistryService.getComponent("SearchMastersApplication");
    const [showToast, setShowToast] = useState(null);
    const [showTable, setShowTable] = useState(false);
    const [selectedOrg, setSelectedOrg] = useState(null);

    const onClearSearch = (isShow = true) => {
        setShowTable(isShow);
    }
  const onSubmit = async (data) => {
      if(!data?.nameOfTheOrg){
        setShowToast({type: "warning", label: "ERR_PT_FILL_VALID_FIELDS" });
        setTimeout(() => {
          setShowToast(false);
        }, 3000);
        return
      }else{
        setShowTable(true);
        setSelectedOrg(data?.nameOfTheOrg);
      }
    }

    //will update this to some api service based on search, once api integration starts
    const result = {
      status: "success",
      isSuccess: true,
      totalCount: 1,
      isLoading: false,
      data:{
        Organisation1: [
          {
            org_id : "DXTYUIOPO89",
            name_of_org : "Organisation1",
            type_of_org : "Community",
            org_category : "Social",
            no_of_members : "10",
            district : "Dhenkanal"
          },
          {
            org_id : "DXTYUIOPO23",
            name_of_org : "Organisation1",
            type_of_org : "SHG",
            org_category : "Social",
            no_of_members : "20",
            district : "Dhenkanal"
          },
          {
            org_id : "DXTYUIO2289",
            name_of_org : "Organisation1",
            type_of_org : "Community",
            org_category : "Social",
            no_of_members : "10",
            district : "Dhenkanal"
          }
        ]
      }
    }

    const getData = () => {
      if(selectedOrg) {
        if (result?.data?.Organisation1?.length == 0) {
          return { display: "ES_COMMON_NO_DATA" }
        } else if (result?.data?.Organisation1?.length > 0) {
          return result?.data?.Organisation1;
        } else {
          return [];
        }
      }
      return [];
    }
  
    const isResultsOk = () => {
      return result?.data.Organisation1?.length > 0 ? true : false;
    }
  
  return (
    <Fragment>
      <SearchMastersApplication
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
          type={showToast?.type}
          label={t(showToast.label)}
          onClose={() => {
            setShowToast(null);
          }}
        />
      )}
    </Fragment>
  )
}

export default SearchMasters