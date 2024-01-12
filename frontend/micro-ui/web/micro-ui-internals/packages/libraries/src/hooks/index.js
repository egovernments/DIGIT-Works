import { useInitStore } from "./store";
import useWorkflowDetails from "./workflow";
import useWorkflowDetailsWorks from "./workflowWorks";
import useSessionStorage from "./useSessionStorage";
import useQueryParams from "./useQueryParams";
import useDocumentSearch from "./useDocumentSearch";
import useClickOutside from "./useClickOutside";
import useAccessControl from "./useAccessControl";
import useRouteSubscription from "./useRouteSubscription";
import {
  useFetchPayment,
  usePaymentUpdate,
  useFetchCitizenBillsForBuissnessService,
  useFetchBillsForBuissnessService,
  useGetPaymentRulesForBusinessServices,
  useDemandSearch,
  useRecieptSearch,
  usePaymentSearch,
  useBulkPdfDetails,
} from "./payment";
import { useUserSearch } from "./userSearch";
import { useApplicationsForBusinessServiceSearch } from "./useApplicationForBillSearch";
import useBoundaryLocalities from "./useLocalities";
import useCommonMDMS from "./useMDMS";
import useCustomMDMS from "./useCustomMDMS";
import useInboxGeneral from "./useInboxGeneral/useInboxGeneral";
import useApplicationStatusGeneral from "./useStatusGeneral";
import useModuleTenants from "./useModuleTenants";
import useStore from "./useStore";
import { useTenants } from "./useTenants";
import { useEvents, useClearNotifications, useNotificationCount } from "./events";
import useCreateEvent from "./events/useCreateEvent";
import useUpdateEvent from "./events/useUpdateEvent";
import useNewInboxGeneral from "./useInboxGeneral/useNewInbox";
import useDynamicData from "./useDynamicData";
import useGenderMDMS from "./useGenderMDMS";
import useCustomAPIHook from "./useCustomAPIHook";
import useCustomAPIMutationHook from "./useCustomAPIMutationHook";

import useComplaintDetails from "./pgr/useComplaintDetails";
import { useComplaintsList, useComplaintsListByMobile } from "./pgr/useComplaintList";
import useComplaintStatus from "./pgr/useComplaintStatus";
import useComplaintTable from "./pgr/useComplaintTable";
import useComplaintTypes from "./pgr/useComplaintTypes";
import useEmployeeFilter from "./pgr/useEmployeeFilter";
import useInboxData from "./pgr/useInboxData";
import useLocalities from "./pgr/useLocalities";
import useServiceDefs from "./pgr/useServiceDefs";
import usePGRTenants from "./pgr/useTenants";
import useComplaintSubType from "./pgr/useComplaintSubType";
import useComplaintStatusCount from "./pgr/useComplaintStatusWithCount";


import useEmployeeSearch from "./useEmployeeSearch";

import useDssMdms from "./dss/useMDMS";
import useDashboardConfig from "./dss/useDashboardConfig";
import useDSSDashboard from "./dss/useDSSDashboard";
import useGetChart from "./dss/useGetChart";


import useHRMSSearch from "./hrms/useHRMSsearch";
import useHrmsMDMS from "./hrms/useHRMSMDMS";
import useHRMSCreate from "./hrms/useHRMScreate";
import useHRMSUpdate from "./hrms/useHRMSUpdate";
import useHRMSCount from "./hrms/useHRMSCount";
import useHRMSGenderMDMS from "./hrms/useHRMSGender";



import useEventInbox from "./events/useEventInbox";
import useEventDetails from "./events/useEventDetails";
import { useEngagementMDMS } from "./engagement/useMdms";
import useDocSearch from "./engagement/useSearch";
import useDocCreate from "./engagement/useCreate";
import useDocUpdate from "./engagement/useUpdate";
import useDocDelete from "./engagement/useDelete";

import useSurveyCreate from "./surveys/useCreate";
import useSurveyDelete from "./surveys/useDelete";
import useSurveyUpdate from "./surveys/useUpdate";
import useSurveySearch from "./surveys/useSearch";
import useSurveyShowResults from "./surveys/useShowResults";
import useSurveySubmitResponse from "./surveys/useSubmitResponse";
import useSurveyInbox from "./surveys/useSurveyInbox";



import useGetHowItWorksJSON from "./useHowItWorksJSON";
import useGetFAQsJSON from "./useGetFAQsJSON";
import useGetDSSFAQsJSON from "./useGetDSSFAQsJSON";
import useGetDSSAboutJSON from "./useGetDSSAboutJSON";
import useStaticData from "./useStaticData";
import useBillAmendmentInbox from "./billAmendment/useInbox";
import { usePrivacyContext } from "./usePrivacyContext";

import useSearchWORKS from "./works/useSearchWORKS";
import useSearchApprovedEstimates from "./works/useSearchApprovedEstimates";
import useViewEstimateDetails from "./works/useViewEstimateDetails";
import useViewProjectDetails from './works/useViewProjectDetails'
import useViewProjectClosureDetails from "./works/useViewProjectClosureDetails";
import useViewProjectClosureDetailsBills from "./works/useViewProjectClosureBills";
import useViewProjectClosureDetailsClosureChecklist from "./works/useViewProjectClosureDetailsClosureChecklist";
import useViewProjectClosureDetailsKickoffChecklist from "./works/useViewProjectClosureDetailsKickoffChecklist";
import useViewLOIDetails from "./works/useViewLOIDetails";
import useCreateLOI from "./works/useCreateLOI";
import useEstimateSearchWorks from "./works/useSearch";
import useCreateEstimate from "./works/useCreateEstimate";
import useCreateEstimateNew from "./works/useCreateEstimateNew";
import useSearchEstimate from "./works/useSearchEstimate";
import useApplicationActionsLOI from "./works/useApplicationActions";
import useApplicationActionsEstimate from "./works/useUpdateEstimate";
import useUpdateEstimateWorks from "./works/useUpdateEstimate";
import useWorksInbox from "./works/useInbox";
import useKickoffInbox from "./works/useKickoffInbox";
import useViewContractDetails from "./contracts/useViewContractDetails";
import useViewContractDetailsClosureScreen from "./contracts/useViewContractDetailsClosureScreen";
import useViewAttendance from "./attendance/useViewAttendance";
import useUpdateAttendance from "./attendance/useUpdateAttendance";
import useLocation from "./useLocation";
import useViewBills from "./bills/useViewBills";
import useViewOrg from "./bills/useViewOrg";
import useCreateProject from "./works/useCreateProject";

import useEstimateSearch from "./estimates/useEstimateSearch";
import useUpdateEstimate from "./estimates/useUpdateEstimate";
import useEstimateDetailsScreen from "./estimates/useEstimateDetailsScreen";

import useProjectSearch from "./project/useProjectSearch";
import useViewFinancialDetails from "./project/useViewFinancialDetails";

import useUpdateCustom from "./useUpdateCustom";

import useViewWageSeeker from "./wageSeeker/useViewWageSeeker";
import useWageSeekerDetails from "./wageSeeker/useWageSeekerDetails";
import useUpdateWageSeeker from "./wageSeeker/useUpdateWageSeeker";
import useCreateWageSeeker from "./wageSeeker/useCreateWageSeeker";
import useDeleteWageSeeker from "./wageSeeker/useDeleteWageSeeker";

import useCreateWO from "./works/useCreateWO";

import useSearchOrg from "./works/useSearchOrg";

import useViewWageBill from "./bills/useViewWageBill";
import useContractSearch from "./contracts/useContractSearch";
import useBillCreate from "./bills/useBillCreate";
import useUpdateProject from "./works/useUpdateProject";
import useUpdateWO from "./works/useUpdateWO";
import useSupervisionBillScreen from "./bills/useSupervisionBillScreen";

import useViewOrganisation from "./organisation/useViewOrganisation";
import useOrganisationDetails from "./organisation/useOrganisationDetails";
import useCreateOrganisation from "./organisation/useCreateOrganisation";
import useUpdateOrganisation from "./organisation/useUpdateOrganisation";

import useUpdateBankAccount from "./bankAccount/useUpdateBankAccount";
import useCreateBankAccount from "./bankAccount/useCreateBankAccount";
import useViewPurchaseBillDetails from "./bills/useViewPurchaseBillDetails";
import useCreatePurchaseBill from "./bills/useCreatePurchaseBill";
import useUpdatePurchaseBill from "./bills/useUpdatePurchaseBill";
import useSearchBill from "./bills/useSearchBill";

import useViewPaymentInstruction from "./payments/useViewPaymentInstruction";
import useViewPayment from "./payments/useViewPayment";
import useUpdatePI from "./expenditure/useUpdatePI";
import useMBDataForPB from "./expenditure/useMBDataForPB";

import useViewMeasurement from "./measurement/useViewMeasurement";
import useEstimateSearchValidation from "./mukta/useEstimateSearchValidation";
import useTEorMBCreateValidation from "./mukta/useTEorMBCreateValidation";

const works = {
  useViewEstimateDetails,
  useViewProjectDetails,
  useViewProjectClosureDetails,
  useViewProjectClosureDetailsBills,
  useViewProjectClosureDetailsKickoffChecklist,
  useViewProjectClosureDetailsClosureChecklist,
  useViewLOIDetails,
  useCreateLOI,
  useEstimateSearchWorks,
  useSearchWORKS,
  useCreateEstimate,
  useCreateEstimateNew,
  useSearchEstimate,
  useApplicationActionsLOI,
  useUpdateEstimate:useUpdateEstimateWorks,
  useApplicationActionsEstimate,
  useSearchApprovedEstimates,
  useInbox: useWorksInbox,
  useKickoffInbox,
  useCreateProject,
  useUpdateProject,
  useUpdateCustom
};

const contracts = {
  useViewContractDetails,
  useViewContractDetailsClosureScreen,
  useCreateWO,
  useUpdateWO,
  useContractSearch
};

const organisation = {
  useSearchOrg,
  useViewOrganisation,
  useOrganisationDetails,
  useCreateOrganisation,
  useUpdateOrganisation
}

const estimates = {
  useEstimateSearch,
  useUpdateEstimate,
  useEstimateDetailsScreen
}

const pgr = {
  useComplaintDetails,
  useComplaintsList,
  useComplaintsListByMobile,
  useComplaintStatus,
  useComplaintTable,
  useComplaintTypes,
  useEmployeeFilter,
  useInboxData,
  useLocalities,
  useServiceDefs,
  useTenants: usePGRTenants,
  useComplaintSubType,
  useComplaintStatusCount,
};


const dss = {
  useMDMS: useDssMdms,
  useDashboardConfig,
  useDSSDashboard,
  useGetChart,
};


const hrms = {
  useHRMSSearch,
  useHrmsMDMS,
  useHRMSCreate,
  useHRMSUpdate,
  useHRMSCount,
  useHRMSGenderMDMS,
};




const events = {
  useInbox: useEventInbox,
  useCreateEvent,
  useEventDetails,
  useUpdateEvent,
};

const engagement = {
  useMDMS: useEngagementMDMS,
  useDocCreate,
  useDocSearch,
  useDocDelete,
  useDocUpdate,
};

const survey = {
  useCreate: useSurveyCreate,
  useUpdate: useSurveyUpdate,
  useDelete: useSurveyDelete,
  useSearch: useSurveySearch,
  useSubmitResponse: useSurveySubmitResponse,
  useShowResults: useSurveyShowResults,
  useSurveyInbox,
};


const attendance = {
  useViewAttendance,
  useUpdateAttendance
};

const bills = {
  useViewBills,
  useViewWageBill,
  useBillCreate,
  useViewPurchaseBillDetails,
  useSupervisionBillScreen,
  useCreatePurchaseBill,
  useUpdatePurchaseBill,
  useSearchBill
};
const masters = {
  useViewOrg
}

const project = {
  useViewFinancialDetails,
  useProjectSearch
}

const wageSeeker = {
  useViewWageSeeker,
  useWageSeekerDetails,
  useUpdateWageSeeker,
  useCreateWageSeeker,
  useDeleteWageSeeker
}

const bankAccount = {
  useUpdateBankAccount,
  useCreateBankAccount
}

const paymentInstruction = {
  useViewPaymentInstruction,
  useUpdatePI,
  useMBDataForPB,
  useViewPayment
}

const measurement = {
  useViewMeasurement
}

const mukta = {
  useEstimateSearchValidation,
  useTEorMBCreateValidation
}

const Hooks = {
  useSessionStorage,
  useQueryParams,
  useFetchPayment,
  usePaymentUpdate,
  useFetchCitizenBillsForBuissnessService,
  useFetchBillsForBuissnessService,
  useGetPaymentRulesForBusinessServices,
  useWorkflowDetails,
  useWorkflowDetailsWorks,
  useInitStore,
  useClickOutside,
  useUserSearch,
  useApplicationsForBusinessServiceSearch,
  useDemandSearch,
  useInboxGeneral,
  useEmployeeSearch,
  useBoundaryLocalities,
  useCommonMDMS,
  useApplicationStatusGeneral,
  useModuleTenants,
  useRecieptSearch,
  usePaymentSearch,
  useNewInboxGeneral,
  useEvents,
  useClearNotifications,
  useNotificationCount,
  useStore,
  useDocumentSearch,
  useTenants,
  useAccessControl,
  usePrivacyContext,
  useGenderMDMS,
  pgr,
 
  dss,
  
  hrms,
 
  events,
  engagement,
  survey,
 
  works,
  useRouteSubscription,
  useCustomMDMS,
  useCustomAPIHook,
  useCustomAPIMutationHook,
  useGetHowItWorksJSON,
  useGetFAQsJSON,
  useGetDSSFAQsJSON,
  useGetDSSAboutJSON,
  useStaticData,
  useDynamicData,
  useBulkPdfDetails,
  useBillAmendmentInbox,
  contracts,
  estimates,
  attendance,
  useLocation,
  bills,
  masters,
  project,
  wageSeeker,
  organisation,
  bankAccount,
  paymentInstruction,
  measurement,
  mukta
};

export default Hooks;
