import React, {useEffect, useMemo, useState} from 'react';
import { useTranslation } from "react-i18next";
import { useHistory } from 'react-router-dom';
import { FormComposer, Loader, Toast } from '@egovernments/digit-ui-react-components';
import { getTomorrowsDate, getBankAccountUpdatePayload, getOrgPayload } from '../../../../utils';
import debounce from 'lodash/debounce';

const navConfig =  [
    {
        name:"location_details",
        code:"ES_COMMON_LOCATION_DETAILS",
    },
    {
        name:"contact_Details",
        code:"ES_COMMON_CONTACT_DETAILS",
    },
    {
        name:"financial_Details",
        code:"MASTERS_FINANCIAL_DETAILS",
    }
];

const CreateOrganizationForm = ({ createOrganizationConfig, sessionFormData, setSessionFormData, clearSessionFormData, isModify, orgDataFromAPI }) => {
    const {t} = useTranslation();
    const history = useHistory()
    const orgId = orgDataFromAPI?.organisation?.orgNumber

    const stateTenant = Digit.ULBService.getStateId();
    const tenantId = Digit.ULBService.getCurrentTenantId();
    const headerLocale = Digit.Utils.locale.getTransformedLocale(tenantId)

    const [selectedWard, setSelectedWard] = useState(sessionFormData?.locDetails_ward?.code || '')
    const [selectedOrg, setSelectedOrg] = useState('')
    const [showDuplicateUserError, setShowDuplicateUserError] = useState(false)
    const [showDuplicateContactToast, setShowDuplicateContactToast] = useState(false)
    const [showValidToError, setShowValidToError] = useState(false)
    const [showCBOToVendorError, setShowCBOToVendorError] = useState(false)

    const { mutate: CreateOrganisationMutation } = Digit.Hooks.organisation.useCreateOrganisation();
    const { mutate: UpdateOrganisationMutation } = Digit.Hooks.organisation.useUpdateOrganisation();

    const { mutate: CreateBankAccountMutation } = Digit.Hooks.bankAccount.useCreateBankAccount();
    const { mutate: UpdateBankAccountMutation } = Digit.Hooks.bankAccount.useUpdateBankAccount();
    //location data
    const ULB = Digit.Utils.locale.getCityLocale(tenantId);
    const ORG_VALIDTO_DATE = '2099-03-31';
    let ULBOptions = []
    ULBOptions.push({code: tenantId, name: t(ULB),  i18nKey: ULB });

    const { isLoading: locationDataFetching, data : wardsAndLocalities } = Digit.Hooks.useLocation(
      tenantId, 'Ward',
      {
          select: (data) => {
              const wards = []
              const localities = {}
              data?.TenantBoundary[0]?.boundary.forEach((item) => {
                  localities[item?.code] = item?.children.map(item => ({ code: item.code, name: item.name, i18nKey: `${headerLocale}_ADMIN_${item?.code}`, label : item?.label }))
                  wards.push({ code: item.code, name: item.name, i18nKey: `${headerLocale}_ADMIN_${item?.code}` })
              });
             return {
                  wards, localities
             }
          }
      });
    const filteredLocalities = wardsAndLocalities?.localities[selectedWard];

    //org data 
    const {isLoading: orgDataFetching, data: orgData } = Digit.Hooks.useCustomMDMS(
        stateTenant,
        "common-masters",
        [ { "name": "OrgType" }, { name: "OrgFunctionCategory" }],
        {
            select: (data) => {
                let orgTypes = []
                let orgSubTypes = {}
                let orgFunCategories = {}
                data?.["common-masters"]?.OrgType?.forEach(item => {
                    if(!item?.active) return
                    const orgType = item?.code?.split('.')?.[0]
                    const orgSubType = item?.code?.split('.')?.[1]
                    if(!orgTypes.includes(orgType)) orgTypes.push(orgType)
                    if(orgSubTypes[orgType]) {
                        orgSubTypes[orgType].push({code: orgSubType, name: `COMMON_MASTERS_SUBORG_${orgSubType}`})
                    } else {
                        orgSubTypes[orgType] = [{code: orgSubType, name: `COMMON_MASTERS_SUBORG_${orgSubType}`}]
                    }
                })
                data?.["common-masters"]?.OrgFunctionCategory?.forEach(item => {
                    if(!item?.active) return
                    const orgType = item?.code?.split('.')?.[0]
                    const orgFunCategory = item?.code?.split('.')?.[1]
                    if(orgFunCategories[orgType]) {
                        orgFunCategories[orgType].push({code: orgFunCategory, name: `COMMON_MASTERS_FUNCATEGORY_${orgFunCategory}`})
                    } else {
                        orgFunCategories[orgType] = [{code: orgFunCategory, name: `COMMON_MASTERS_FUNCATEGORY_${orgFunCategory}`}]
                    }
                })
                orgTypes = orgTypes.map(item => ({code: item, name: `COMMON_MASTERS_ORG_${item}`}))
                return {
                    orgTypes,
                    orgSubTypes,
                    orgFunCategories
                }
            }
        }
    );
    const filteredOrgSubTypes = orgData?.orgSubTypes[selectedOrg]
    const filteredOrgFunCategories = orgData?.orgFunCategories[selectedOrg]

     //wage seeker form config
     const config = useMemo(
        () => Digit.Utils.preProcessMDMSConfig(t, createOrganizationConfig, {
          updateDependent : [
            {
                key : "basicDetails_dateOfIncorporation",
                value : [new Date().toISOString().split("T")[0]]
            },
            {
                key : "funDetails_orgType",
                value : [orgData?.orgTypes]
            },
            {
                key : "funDetails_orgSubType",
                value : [filteredOrgSubTypes]
            },
            {
                key : "funDetails_category",
                value : [filteredOrgFunCategories]
            },
            {
                key : "funDetails_validFrom",
                value : [new Date().toISOString().split("T")[0]]
            },
            {
                key : "funDetails_validTo",
                value : [getTomorrowsDate()]
            },
            {
                key : 'locDetails_city',
                value : [ULBOptions]
            },
            {
                key : 'locDetails_ward',
                value : [wardsAndLocalities?.wards]
            },
            {
                key : 'locDetails_locality',
                value : [filteredLocalities]
            },
            {
                key : "basicDetails_orgId",
                value : [!isModify ? "none" : "flex"]
            }
          ]
        }),
        [orgData, filteredOrgSubTypes, filteredOrgFunCategories, wardsAndLocalities, filteredLocalities, ULBOptions]);

    useEffect(() => {
        if(showDuplicateUserError) {
            setTimeout(()=>{
                setShowDuplicateUserError(false);
            },3000);
        }
    },[showDuplicateUserError]);

    useEffect(() => {
        if(showValidToError) {
            setTimeout(()=>{
                setShowValidToError(false);
            },3000);
        }
    },[showValidToError]);
    const onFormValueChange = async (setValue, formData, formState, reset, setError, clearErrors, trigger, getValues) => {
        if (!_.isEqual(sessionFormData, formData)) {
            const difference = _.pickBy(sessionFormData, (v, k) => !_.isEqual(formData[k], v));
            if(formData.locDetails_ward) {
                setSelectedWard(formData?.locDetails_ward?.code)
            }
            if (difference?.locDetails_ward) {
                setValue("locDetails_locality", '');
            }
            if(formData.funDetails_orgType) {
                setSelectedOrg(formData?.funDetails_orgType?.code)
                if(formData?.funDetails_orgType?.code === "CBO") {
                    setValue("funDetails_category", { code: 'NA' , name: `COMMON_MASTERS_FUNCATEGORY_NA`})
                }
            }
            if (difference?.funDetails_orgType) {
                setValue("funDetails_orgSubType", '');
                if(formData?.funDetails_orgType?.code === "CBO") {
                    setValue("funDetails_category", { code: 'NA' , name: `COMMON_MASTERS_FUNCATEGORY_NA`})
                } else {
                    setValue("funDetails_category", '');   
                }             
            }
            if(formData?.transferCodesData?.[0]?.name?.code == 'IFSC' && formData?.transferCodesData?.[0]?.value ) {
                if(formData?.transferCodesData?.[0]?.value.length > 10) {
                    setTimeout(() => {
                        fetchIFSCDetails(formData?.transferCodesData?.[0]?.value, 'financeDetails_branchName', 'financeDetails_bankName', setValue, setError, clearErrors);
                    }, 500);
                } else {
                    setValue("financeDetails_branchName", "")
                    setValue("financeDetails_bankName", "")
                }
            }
            setSessionFormData({ ...sessionFormData, ...formData });
        }
    }

    const fetchIFSCDetails = async (ifscCode, branchNameField, bankNameField, setValue, setError, clearErrors) => {
        const res = await window.fetch(`https://ifsc.razorpay.com/${ifscCode}`);
        if (res.ok) {
            const { BANK, BRANCH } = await res.json();
            setValue(bankNameField, `${BANK}`)
            setValue(branchNameField, `${BRANCH}`)
            clearErrors("transferCodesData")
        }
        if(res.status === 404) {
            setValue(bankNameField, "")
            setValue(branchNameField, "")
            setError("transferCodesData",{ type: "custom" }, { shouldFocus: true })
        }
    }
    const sendDataToResponsePage = (orgId, isSuccess, message, showId, otherMessage = "") => {
        history.push({
            pathname: `/${window?.contextPath}/employee/masters/response-org`,
            search: orgId ? `?tenantId=${tenantId}&orgId=${orgId}` : '',
            state : {
                message,
                showId,
                isSuccess,
                isWageSeeker: false,
                otherMessage
            }
        }); 
    }

    const handleResponseForUpdate = async (orgPayload, bankAccountPayload) => {
            await UpdateOrganisationMutation(orgPayload, {
                onError: async (error) => sendDataToResponsePage(orgId, false, "MASTERS_ORG_MODIFICATION_FAIL", true),
                onSuccess: async (responseData) => {
                    if(orgDataFromAPI?.bankDetails && orgDataFromAPI?.bankDetails?.length > 0)
                        await UpdateBankAccountMutation(bankAccountPayload, {
                            onError :  async (error) => sendDataToResponsePage(orgId, false, "MASTERS_ORG_MODIFICATION_FAIL", true),
                            onSuccess: async (responseData) => {
                                sendDataToResponsePage(orgId, true, "MASTERS_ORG_MODIFICATION_SUCCESS", true)
                                clearSessionFormData()
                            }
                        })
                    else
                        await CreateBankAccountMutation(bankAccountPayload, {
                            onError :  async (error) => sendDataToResponsePage('', false, "MASTERS_ORG_MODIFICATION_FAIL", false),
                            onSuccess: async (bankResponseData) => {
                                sendDataToResponsePage(orgId, true, "MASTERS_ORG_MODIFICATION_SUCCESS")
                                clearSessionFormData()
                            }
                        }) 
                    }
            });
    }

    const handleResponseForCreate = async (orgPayload, data) => {
        await CreateOrganisationMutation(orgPayload, {
            onError: async (error) => sendDataToResponsePage('', false, "MASTERS_ORG_CREATION_FAIL", false),
            onSuccess: async (responseData) => {
                //Update bank account details if wage seeker update success
                const bankAccountPayload = getBankAccountUpdatePayload({formData: data, apiData: '', tenantId, isModify, referenceId: responseData?.organisations?.[0].id});
                await CreateBankAccountMutation(bankAccountPayload, {
                    onError :  async (error) => sendDataToResponsePage('', false, "MASTERS_ORG_CREATION_FAIL", false),
                    onSuccess: async (bankResponseData) => {
                        sendDataToResponsePage(responseData?.organisations?.[0].orgNumber, true, "MASTERS_ORG_CREATION_SUCCESS", true, "MASTERS_ORG_CREATION_SUCCESS_MESSAGE")
                        clearSessionFormData()
                    }
                })
            },
        });
    }

    const closeToast = () => {
        setTimeout(() => {
            setShowDuplicateContactToast(false)
            setShowCBOToVendorError(false)
        }, 10000);
    }
    


    const OnModalSubmit = async (data) => {
        data = Digit.Utils.trimStringsInObject(data)
        //here call org search with mobile number and see if number is already there with some other org , do an early return
        
        const contactNumber = data?.contactDetails_mobile
        const orgNumber = data?.basicDetails_orgId
       
        const searchOrgResponse = await Digit.WorksService.searchOrg({
            SearchCriteria: {
                "tenantId": tenantId,
                "contactMobileNumber": contactNumber
            }
          })

        // let doEarlyReturn = false
        // searchOrgResponse?.organisations?.forEach(row => {
        //     if(row?.orgNumber === orgNumber){
        //         setShowDuplicateContactToast(true)
        //         doEarlyReturn = true
        //         return 
        //     }
        // })
        // if(doEarlyReturn) return
        
        //check if another org associated with entered number
        if(searchOrgResponse?.organisations?.length>0 && searchOrgResponse?.organisations?.[0]?.orgNumber !== orgNumber ){
            setShowDuplicateContactToast(true)
            closeToast()
            return 
        }

        if((data?.funDetails_validTo ? Digit.Utils.pt.convertDateToEpoch(data?.funDetails_validTo) : Digit.Utils.pt.convertDateToEpoch(ORG_VALIDTO_DATE)) < Digit.Utils.pt.convertDateToEpoch(data?.funDetails_validFrom)){
            setShowValidToError(true);
        }
        else{
            const orgPayload = getOrgPayload({formData: data, orgDataFromAPI, tenantId, isModify})
        if(isModify) {
            if(searchOrgResponse?.organisations?.length>0 && data?.funDetails_orgType?.code === 'VEN' && searchOrgResponse?.organisations?.[0]?.functions?.[0]?.type.includes("CBO")){
                setShowCBOToVendorError(true);
            }
            else{
                const bankAccountPayload = orgDataFromAPI?.bankDetails && orgDataFromAPI?.bankDetails?.length > 0 ? getBankAccountUpdatePayload({formData: data, apiData: orgDataFromAPI, tenantId, isModify, referenceId: '', isWageSeeker: false}) : getBankAccountUpdatePayload({formData: data, apiData: '', tenantId, isModify:false, referenceId: orgDataFromAPI?.organisation?.id});
                handleResponseForUpdate(orgPayload, bankAccountPayload);
            }
        }else {
            const userData = await Digit.UserService.userSearch(stateTenant, { mobileNumber: data?.contactDetails_mobile }, {})
            if(userData?.user?.length > 0 && userData?.user?.[0]?.roles.some(role => role.code === "ORG_ADMIN")) {
                setShowDuplicateUserError(true)
                return
            }
            handleResponseForCreate(orgPayload, data);
        }
    }
    };  

    const debouncedOnModalSubmit = Digit.Utils.debouncing(OnModalSubmit,20000);

    const handleSubmit = (_data) => {
        // Call the debounced version of onModalSubmit
        debouncedOnModalSubmit(_data);
      };

    if(locationDataFetching || orgDataFetching) return <Loader/>
    return (
      <React.Fragment>
        <FormComposer
          label={isModify ? "CORE_COMMON_SAVE" : t("MASTERS_CREATE_ORGANISATION")}
          config={config?.form}
          onSubmit={handleSubmit}
          submitInForm={false}
          fieldStyle={{ marginRight: 0 }}
          inline={false}
          className="form-no-margin"
          defaultValues={sessionFormData}
          showWrapperContainers={false}
          isDescriptionBold={false}
          noBreakLine={true}
          showNavs={config?.metaData?.showNavs}
          showFormInNav={true}
          showMultipleCardsWithoutNavs={false}
          showMultipleCardsInNavs={false}
          horizontalNavConfig={navConfig}
          onFormValueChange={onFormValueChange}
          cardClassName="mukta-header-card"
          labelBold={true}
        />
        {showDuplicateUserError && (
          <Toast error={true} label={t("ES_COMMON_MOBILE_EXISTS_ERROR")} isDleteBtn={true} onClose={() => setShowDuplicateUserError(false)} />
        )}
        {showValidToError && (
          <Toast error={true} label={t("DATE_VALIDATION_VALID_TO_VALID_FROM_MSG")} isDleteBtn={true} onClose={() => setShowValidToError(false)} />
        )}
        {showDuplicateContactToast && (
          <Toast warning={true} label={t("ES_COMMON_ORG_EXISTS_WITH_MOBILE_NUMBER")} isDleteBtn={true} onClose={() => setShowDuplicateContactToast(false)} />
        )}
        {showCBOToVendorError && (
          <Toast warning={true} label={t("ORG_CBO_CANNOT_BE_CHANGE_TO_VENDOR")} isDleteBtn={true} onClose={() => setShowCBOToVendorError(false)} />
        )}
      </React.Fragment>
    );
}

export default CreateOrganizationForm;