import React from 'react'
import { useTranslation } from 'react-i18next'
import { CardLabel } from '@egovernments/digit-ui-react-components'
import { DisplayPhotos } from '@egovernments/digit-ui-react-components'

const Photos = ({ data, OpenImage, fullImage }) => {
    const { t } = useTranslation()
    return (
        <React.Fragment>
            <CardLabel style={{fontSize: "16px", fontWeight: "600", marginBottom: "8px", marginTop: "20px"}}>{t(data?.title)}</CardLabel>
            { data?.thumbnailsToShow ? 
                <DisplayPhotos style={{maxWidth: "850px", maxHeight:"300px", gap:"10px", paddingTop: 2}} srcs={fullImage ? data?.thumbnailsToShow?.fullImage : data?.thumbnailsToShow?.thumbs} onClick={(src, index) => { OpenImage(src, index, data?.thumbnailsToShow) }}/>
                : <CardLabel >{t('NA')}</CardLabel>
            }
        </React.Fragment>
    )
}

export default Photos