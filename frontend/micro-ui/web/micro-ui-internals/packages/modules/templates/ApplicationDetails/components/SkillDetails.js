import React from 'react'
import { useTranslation } from 'react-i18next'

const SkillDetails = ({ data }) => {
    const t = useTranslation()
    return (
        <React.Fragment>
            <div>
                {
                    data?.length > 0 ? 
                    data?.map(item => (
                        <div>
                            {`${item?.level} - ${item?.type}`}
                        </div>
                    ))
                    : t('NA')
                }
            </div>
        </React.Fragment>
    )
}

export default SkillDetails