import React, { useTransition } from 'react'
import { useHistory } from "react-router-dom";
import { useTranslation } from 'react-i18next';
import { ButtonSelector } from "@egovernments/digit-ui-react-components";

const SearchOrganization = ({parentRoute}) => {
  const { t } = useTranslation()
  const history = useHistory()
 
  const createOrgHandler = () => {
    history.push(`${parentRoute}/create-organization`, { data: {test: true} });
  }

  const handleViewOrganisation = () => {
    history.push(`${parentRoute}/view-organization`);
  }
  
  return (
    <div>
      <ButtonSelector theme="border" label={t("MASTERS_ADD_NEW_ORGANISATION")} onSubmit={createOrgHandler} />
      <ButtonSelector theme="border" label={t("MASTERS_VIEW_COMMUNITY_ORG")} onSubmit={handleViewOrganisation} />
    </div>
  )
}

export default SearchOrganization;