import { FormComposer, Header } from '@egovernments/digit-ui-react-components'
import React, { useState } from 'react'

import { useTranslation } from 'react-i18next'
import { configChecklist } from './config'
import { configChecklistTest } from './configTest'
import { configChecklistTest1 } from './configTest1'
import { configChecklistTest2 } from './configTest2'

const ChecklistSampleForm = (props) => {
    const { t } = useTranslation()
    const onSubmit = (data) => {
    }

    const [showForm, setShowForm] = useState(false)
// All the three use cases for form composer are covered in this file
    return (
        <React.Fragment>
            {/* <Header styles={{ marginLeft: "15px" }}>{t("WORKS_CHECKLIST")}</Header> */}
            <FormComposer
                //heading={t("WORKS_KICKOFF_CHECKLIST")}
                label={t("CS_ACTION_DISPOSE")}
                //description={"Sample Description"}
                //text={"Sample Text"}
                config={configChecklistTest(t, setShowForm, showForm).form.map((config) => {
                    return {
                        ...config,
                        body: config?.body?.filter((a) => !a.hideInEmployee),
                    };
                })}
                defaultValues={{}}
                onSubmit={onSubmit}
                fieldStyle={{ marginLeft: "0.5rem" }}
                showWrapperContainers={true}
                isDescriptionBold={true}
                noBreakLine={true}
                showMultipleCards={true}
                horizontalNavConfig={
                    [
                        {
                            name:"Kickoff Checklist"
                        },
                        {
                            name:"Closure Checklist"
                        }
                    ]
                }
            />
            {"end"}
            <FormComposer
                //heading={t("WORKS_KICKOFF_CHECKLIST")}
                label={t("CS_ACTION_DISPOSE")}
                //description={"Sample Description"}
                //text={"Sample Text"}
                config={configChecklistTest1(t, setShowForm, showForm).form.map((config) => {
                    return {
                        ...config,
                        body: config?.body?.filter((a) => !a.hideInEmployee),
                    };
                })}
                defaultValues={{}}
                onSubmit={onSubmit}
                fieldStyle={{ marginLeft: "0.5rem" }}
                showWrapperContainers={true}
                isDescriptionBold={true}
                noBreakLine={true}
                showMultipleCards={true}
            />
            {"end"}
            <FormComposer
                //heading={t("WORKS_KICKOFF_CHECKLIST")}
                label={t("CS_ACTION_DISPOSE")}
                //description={"Sample Description"}
                //text={"Sample Text"}
                config={configChecklistTest2(t, setShowForm, showForm).form.map((config) => {
                    return {
                        ...config,
                        body: config?.body?.filter((a) => !a.hideInEmployee),
                    };
                })}
                defaultValues={{}}
                onSubmit={onSubmit}
                fieldStyle={{ marginLeft: "0.5rem" }}
                showWrapperContainers={true}
                isDescriptionBold={true}
                noBreakLine={true}
                // showMultipleCards={true}  
            />
        </React.Fragment>


    )
}

export default ChecklistSampleForm