import React, { useEffect, useState } from 'react'
import { createOrganizationConfig } from '../../../../Masters/src/configs/createOrganizationConfig'
import { FormComposer } from '@egovernments/digit-ui-react-components'
import { useTranslation } from 'react-i18next'
import { useHistory } from 'react-router-dom'

const CreateOrganizationForm = ({setCreateOrgStatus}) => {
    const { t } = useTranslation();

    const userInfo = Digit.UserService.getUser();
    const tenantId = Digit.ULBService.getCurrentTenantId();
    const ULB = Digit.Utils.pt.getCityLocale(tenantId);
    const city = userInfo && userInfo?.info?.permanentCity;
    
    let ULBOptions = []
    ULBOptions.push({code: tenantId, name: t(ULB),  i18nKey: ULB })

    let districtOptions = []
    districtOptions.push({code: tenantId, name: t(ULB),  i18nKey: ULB })

    const defaultValues = {
      'ulb': ULBOptions[0],
      'district': ULBOptions[0]
    }

    //Upload
    const [file, setFile] = useState(null);
    const [uploadedFile, setUploadedFile] = useState(null);
    const [error, setError] = useState(null);

    const selectFile = (e) => {
        console.log('selected file')
        setFile(e.target.files[0]);
    }

    useEffect(() => {
        (async () => {
          setError(null);
          if (file) {
            const allowedFileTypesRegex = /(.*?)(jpg|jpeg|png|image|pdf)$/i
            if (file.size >= 5242880) {
              setError(t("CS_MAXIMUM_UPLOAD_SIZE_EXCEEDED"));
            } else if (file?.type && !allowedFileTypesRegex.test(file?.type)) {
              setError(t(`NOT_SUPPORTED_FILE_TYPE`))
            } else {
              try {
                const response = await Digit.UploadServices.Filestorage("NOC", file, Digit.ULBService.getStateId() || tenantId?.split(".")[0]);
                if (response?.data?.files?.length > 0) {
                  setUploadedFile(response?.data?.files[0]?.fileStoreId);
                } else {
                  setError(t("CS_FILE_UPLOAD_ERROR"));
                }
              } catch (err) {
                setError(t("CS_FILE_UPLOAD_ERROR"));
              }
            }
          }
        })();
      }, [file]);
    
    const config = createOrganizationConfig({selectFile, uploadedFile, setUploadedFile, error});


    const onSubmit = (data) => {
        console.log('Submitted data', data)
        console.log('Submitted file', uploadedFile)
        //TODO: based on API response, pass as true/false
        setCreateOrgStatus(true)
    }

    return (
        <React.Fragment>
        <FormComposer
            heading={""}
            label={config.label.submit}
            config={config.form}
            onSubmit={onSubmit}
            fieldStyle={{ fontWeight: '600' }}
            noBreakLine={true}
            sectionHeadStyle={{marginTop: '1rem', marginBottom: '2rem'}}
            defaultValues={defaultValues}
        /> 
        </React.Fragment>
    )
}

export default CreateOrganizationForm;