import React from 'react'
import { useTranslation } from 'react-i18next'
import { FormComposer, LinkLabel, Card, CardSectionHeader, BreakLine } from "@egovernments/digit-ui-react-components";
import { addVendorBillConfig } from '../../configs/addVendorBillConfig';

const AddVendorBill = (props) => {
    const { t } = useTranslation()
    const config = addVendorBillConfig();
    const onSubmit = (data) => {
        //TODO: based on API response, pass as true/false
        console.log('Data', data)
    }

    const addVendorBill = () => {}

    return (
        <React.Fragment>
            <Card>
                <FormComposer
                    heading={""}
                    label={config.label.submit}
                    config={config.form}
                    onSubmit={onSubmit}
                    noBoxShadow
                    cardStyle={{"padding" : 0}}
                    fieldStyle={{ fontWeight: '600' }}
                    sectionHeadStyle={{marginTop: '1rem', marginBottom: '2rem'}}
                /> 
                <LinkLabel style={{ display: "flex", marginTop: "1.5rem", marginLeft: "1rem" }} onClick={addVendorBill}>+ {t("EXP_ADD_ANOTHER_VENDOR_BILL")}</LinkLabel>
                {!props.noBreakLine && <BreakLine />}
                <div style={{margin: "0px 16px", display: "flex", justifyContent:"space-between"}}>
                    <CardSectionHeader>{t("EXP_TOTAL_VENDOR_BILL")}</CardSectionHeader>
                    <CardSectionHeader>{"₹ 1,20,000"}</CardSectionHeader>
                </div>
            </Card>
        </React.Fragment>
  )
}

export default AddVendorBill