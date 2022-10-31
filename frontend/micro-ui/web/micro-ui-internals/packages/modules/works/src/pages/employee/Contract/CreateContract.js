import React, { Fragment, useState } from 'react'
import { Toast } from "@egovernments/digit-ui-react-components";
import { useTranslation } from 'react-i18next';
import CreateContractForm from '../../../components/CreateContract/CreateContractForm';

const CreateContract = (props) => {
    const [showToast, setShowToast] = useState(null);
    const {t}=useTranslation();
    const onFormSubmit = async (_data) => {

    }

    return (
        <Fragment>
            <CreateContractForm onFormSubmit={onFormSubmit} />
            {showToast && (
                <Toast
                style={{"zIndex":"9999999"}}
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

export default CreateContract