import React from 'react'
import { Header, Loader, ViewComposer, MultiLink } from "@egovernments/digit-ui-react-components";
import { useTranslation } from 'react-i18next';
import { paymentTrackerViewConfig } from '../../configs/paymentTrackerViewConfig';

const PaymentTrackerView = () => {
  const {t} = useTranslation();
  const tenantId = Digit.ULBService.getCurrentTenantId();
  const headerLocale = Digit.Utils.locale.getTransformedLocale(tenantId);
  const { projectId } = Digit.Hooks.useQueryParams();

  const {projectData, billPaidData, billData, isProjectLoading, isBillPaidLoading, isBillLoading} = Digit.Hooks.paymentInstruction.useViewPaymentTracker({projectId, tenantId});

  const HandleDownloadPdf = () => {
    Digit.Utils.downloadEgovPDF("paymentTracker/payment-tracker", { projectId : projectId, tenantId }, `project-payments-${projectId}.pdf`);
  };

  const config = paymentTrackerViewConfig(projectData?.Project?.[0], billPaidData?.aggsResponse?.projects?.filter((ob) => ob?.projectNumber === projectId)?.[0], billData?.items, projectId, headerLocale);

  if (isProjectLoading || isBillPaidLoading || isBillLoading) {
    return <Loader />;
  }

  return (
    <React.Fragment>
      <div className={"employee-application-details"} style={{ marginBottom: "15px" }}>
        <Header className="works-header-view" styles={{ marginLeft: "0px", paddingTop: "10px" }}>
          {t("PJ_PAYMENTS")}
        </Header>
        <MultiLink onHeadClick={() => HandleDownloadPdf()} downloadBtnClassName={"employee-download-btn-className"} label={t("CS_COMMON_DOWNLOAD")} />
      </div>
      <ViewComposer data={config} isLoading={false} />
    </React.Fragment>
  );
};

export default PaymentTrackerView;