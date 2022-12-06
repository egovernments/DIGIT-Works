import { FormComposer,Header } from '@egovernments/digit-ui-react-components'
import React from 'react'
import { useTranslation } from 'react-i18next'
import { configChecklist } from './config'
const Checklist = (props) => {
    const { t } = useTranslation()
    const onSubmit = (data) => {
        debugger
        console.log("form submitted");
    }

  return (
    <React.Fragment>
        <Header styles={{ marginLeft: "15px" }}>{t("WORKS_CHECKLIST")}</Header>
        <FormComposer
            heading={t("WORKS_KICKOFF_CHECKLIST")}
            label={t("CS_ACTION_DISPOSE")}
            //description={"Sample Description"}
            //text={"Sample Text"}
            config={configChecklist(t).form.map((config) => {
                return {
                    ...config,
                    body: config.body.filter((a) => !a.hideInEmployee),
                };
            })}
            defaultValues={{}}
            onSubmit={onSubmit}
            fieldStyle={{ marginLeft: "0.5rem" }}
            showWrapperContainers={true}
            isDescriptionBold={true}
        />
    </React.Fragment>
  )
}

export default Checklist