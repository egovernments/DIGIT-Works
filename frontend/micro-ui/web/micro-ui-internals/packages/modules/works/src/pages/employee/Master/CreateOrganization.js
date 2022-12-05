import React from 'react'
import { Header } from '@egovernments/digit-ui-react-components'
import { useTranslation } from 'react-i18next'
import CreateOrganizationForm from '../../../components/CreateOrganization/CreateOrganizationForm'

const CreateOrganization = ({parentRoute}) => {
  const { t } = useTranslation()

  return (
    <React.Fragment>
       <Header>{t("WORKS_CREATE_ORGANISATION")}</Header>
       <CreateOrganizationForm/>
    </React.Fragment>
  )
}

export default CreateOrganization