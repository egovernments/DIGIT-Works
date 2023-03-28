import React, { useMemo, useState, useEffect } from 'react'
import { useTranslation } from "react-i18next";
import { FormComposer } from '@egovernments/digit-ui-react-components';
import { getWageSeekerUpdatePayload, getBankAccountUpdatePayload } from '../../../../utils';

const navConfig =  [{
    name:"Wage_Seeker_Details",
    code:"WAGE_SEEKER_DETAILS"
}]

/*

find difference and check which data was updated
if only WS data updated - call only WS update
if only Bank data updated - call only bank update
if both updated - call both

Enable Save button if anything is updated
if individual Id then create else update
On click of save call Update WageSeeker and Update Bank account details API, if both success then redirect to Success Page else redorect to Error page

payload for WS update
id, tenantId, name => givenName, dateOfBirth, gender, mobileNumber, address => id, individualId, tenantId, doorNo, type, street, locality => code, ward => code, 
fatherName, relationship, skills => [{id,individualId, type, level, photo, additionalFields -> fields -> key, value, rowVersion  }]

payload for create
id, tenantId, name => givenName, dateOfBirth, gender, mobileNumber, address => id, individualId, tenantId, doorNo, type, street, locality => code, ward => code, 
fatherName, relationship, skills => [{id,individualId, type, level, photo, additionalFields -> fields -> key, value, rowVersion, identifiers => id, individualId, identifierType, identifierId }]
*/

const ModifyWageSeekerForm = ({createWageSeekerConfig, sessionFormData, setSessionFormData, clearSessionFormData, isModify, wageSeekerDataFromAPI }) => {
    const { t } = useTranslation();

    const [financeDetailsUpdated, setFinanceDetailsUpdated] = useState(false)
    const [individualDetailsUpdated, setIndividualDetailsUpdated] = useState(false)
    const stateTenant = Digit.ULBService.getStateId();
    const tenantId = Digit.ULBService.getCurrentTenantId();
    const headerLocale = Digit.Utils.locale.getTransformedLocale(tenantId)

    const [selectedWard, setSelectedWard] = useState(sessionFormData?.locDetails_ward?.code || '')

    const { mutate: UpdateWageSeekerMutation } = Digit.Hooks.wageSeeker.useUpdateWageSeeker();
    const { mutate: UpdateBankAccountMutation } = Digit.Hooks.bankAccount.useUpdateBankAccount();

    //Skill data
    const {isLoading: skillDataFetching, data: skillData } = Digit.Hooks.useCustomMDMS(
        stateTenant,
        "common-masters",
        [ { "name": "WageSeekerSkills" }],
        {
            select: (data) => {
                return data?.["common-masters"]?.WageSeekerSkills?.filter(item => item?.active)?.map(skill => ({
                    code: skill?.code, name: `COMMON_MASTERS_SKILLS_${skill?.code}`
                }))  
            }
        }
    );

    //location data
    const ULB = Digit.Utils.locale.getCityLocale(tenantId);
    let ULBOptions = []
    ULBOptions.push({code: tenantId, name: t(ULB),  i18nKey: ULB });
    const { data : wardsAndLocalities } = Digit.Hooks.useLocation(
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

    //wage seeker form config
    const config = useMemo(
    () => Digit.Utils.preProcessMDMSConfig(t, createWageSeekerConfig, {
      updateDependent : [
        {
            key : "basicDetails_dateOfBirth",
            value : [new Date().toISOString().split("T")[0]]
        },
        {
            key : "skillDetails_skill",
            value : [skillData]
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
            key : "basicDetails_wageSeekerId",
            value : [!isModify ? "none" : "flex"]
        }
      ]
    }),
    [skillData, wardsAndLocalities, filteredLocalities, ULBOptions]);

    const onFormValueChange = (setValue, formData, formState, reset, setError, clearErrors, trigger, getValues) => {
        if (!_.isEqual(sessionFormData, formData)) {
            const difference = _.pickBy(sessionFormData, (v, k) => !_.isEqual(formData[k], v));

            //check if wage seeker details are updated or bank details are udpated
            let updatedKey = Object.keys(difference)?.[0]
            if(updatedKey?.includes('basicDetails_') || updatedKey?.includes('skillDetails_') || updatedKey?.includes('locDetails_')) {
                setIndividualDetailsUpdated(true)
            }
            if(updatedKey?.includes('financeDetails_')) {
                setFinanceDetailsUpdated(true)
            }

            if(formData.locDetails_ward) {
                setSelectedWard(formData?.locDetails_ward?.code)
            }
            if (difference?.locDetails_ward) {
                setValue("locDetails_locality", '');
            }
            setSessionFormData({ ...sessionFormData, ...formData });
        }
    }

    const sendDataToResponsePage = (individualId, isSuccess, message, showWageSeekerID) => {
        history.push({
          pathname: `/${window?.contextPath}/employee/masters/response`,
          search: `?individualId=${individualId}&tenantId=${tenantId}&isSuccess=${isSuccess}`,
          state : {
            message,
            showWageSeekerID
          }
        }); 
      }

    const handleResponseForUpdate = async (payload) => {
        //individualDetailsUpdated , financeDetailsUpdated

        await UpdateWageSeekerMutation(payload, {
          onError: async (error, variables) => {
                console.log('Error Update WS', error);
                //sendDataToResponsePage(wageSeekerDataFromAPI?.individual?.individualId, false, "WORKS_PROJECT_MODIFICATION_FAILURE", true);
          },
          onSuccess: async (responseData, variables) => {
            //for parent with sub-projects send another call for sub-projects array. Add the Parent ID in each sub-project.
            if(financeDetailsUpdated) {
              payload = getBankAccountUpdatePayload({});
              await UpdateBankAccountMutation(payload, {
                onError :  async (error, variables) => {
                    console.log('Error Update Bank', error);
                    //sendDataToResponsePage(modify_projectNumber, false, "WORKS_PROJECT_MODIFICATION_FAILURE", true);
                },
                onSuccess: async (responseData, variables) => {
                  console.log('Success Bank responseData', responseData);
                }
              })
            }else{
                console.log('Success WS responseData', responseData);
            }
          },
        });
    }

    const handleResponseForCreate = async (payload) => {}

    const onSubmit = (data) => {
        console.log('DATA', data);
        console.log('@@@@@@', financeDetailsUpdated, individualDetailsUpdated);
        const wageSeekerPayload = getWageSeekerUpdatePayload({formData: data, wageSeekerDataFromAPI, tenantId, isModify})
        console.log('wageSeekerPayload', wageSeekerPayload);
        if(isModify) {
            handleResponseForUpdate(wageSeekerPayload);
        }else {
            handleResponseForCreate(wageSeekerPayload);
        }
    }

    return (
        <React.Fragment>
           <FormComposer
                label={isModify ? "Save" : "Create Wage Seeker"}
                config={config?.form}
                onSubmit={onSubmit}
                submitInForm={false}
                fieldStyle={{ marginRight: 0 }}
                inline={false}
                className="form-no-margin"
                defaultValues={sessionFormData}
                showWrapperContainers={false}
                isDescriptionBold={false}
                showNavs={config?.metaData?.showNavs}
                horizontalNavConfig={navConfig}
                noBreakLine={true}
                showFormInNav={true}
                showMultipleCardsWithoutNavs={false}
                showMultipleCardsInNavs={false}
                onFormValueChange={onFormValueChange}
                isDisabled={isModify ? !(individualDetailsUpdated || financeDetailsUpdated) : false}
                cardClassName = "mukta-header-card"
            />
        </React.Fragment>
    )
}

export default ModifyWageSeekerForm;