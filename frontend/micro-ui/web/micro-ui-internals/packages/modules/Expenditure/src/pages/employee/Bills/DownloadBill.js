import React from 'react'
import { Header, InboxSearchComposer} from '@egovernments/digit-ui-react-components';
import { DownloadBillConfig } from '../../../configs/DownloadBillConfig';
import { useTranslation } from 'react-i18next';

const DownloadBill = () => {
  const { t } = useTranslation()
  const configs = DownloadBillConfig?.DownloadBillConfig?.[0]
  return (
    <React.Fragment>
         <Header className="works-header-search">{t(configs?.label)}</Header>
         <div className="inbox-search-wrapper">
          <InboxSearchComposer configs={configs}></InboxSearchComposer>
        </div>
    </React.Fragment>
  )
}

export default DownloadBill;