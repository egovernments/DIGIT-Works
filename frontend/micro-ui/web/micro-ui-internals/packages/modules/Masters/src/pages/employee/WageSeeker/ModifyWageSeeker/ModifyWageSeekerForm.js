import React, { useMemo, useState, useEffect } from 'react'
import { useTranslation } from "react-i18next";
import { useHistory } from 'react-router-dom';
import { FormComposer,Toast } from '@egovernments/digit-ui-react-components';
import { getWageSeekerUpdatePayload, getBankAccountUpdatePayload, getWageSeekerSkillDeletePayload } from '../../../../utils';

const navConfig =  [{
    name:"Wage_Seeker_Details",
    code:"WAGE_SEEKER_DETAILS"
}]

const ModifyWageSeekerForm = ({createWageSeekerConfig, sessionFormData, setSessionFormData, clearSessionFormData, isModify, wageSeekerDataFromAPI }) => {
    const { t } = useTranslation();
    const history = useHistory()
    const [showToast,setShowToast] = useState(null)
    const individualId = wageSeekerDataFromAPI?.individual?.individualId

    const [financeDetailsUpdated, setFinanceDetailsUpdated] = useState(false)
    const [individualDetailsUpdated, setIndividualDetailsUpdated] = useState(false)
    const [isBirthDateValid, setIsBirthDateValid] = useState(true);

    const stateTenant = Digit.ULBService.getStateId();
    const tenantId = Digit.ULBService.getCurrentTenantId();
    const headerLocale = Digit.Utils.locale.getTransformedLocale(tenantId)

    const [selectedWard, setSelectedWard] = useState(sessionFormData?.locDetails_ward?.code || '')

    const { mutate: UpdateWageSeekerMutation } = Digit.Hooks.wageSeeker.useUpdateWageSeeker();
    const { mutate: UpdateBankAccountMutation } = Digit.Hooks.bankAccount.useUpdateBankAccount();

    const { mutate: CreateWageSeekerMutation } = Digit.Hooks.wageSeeker.useCreateWageSeeker();
    const { mutate: CreateBankAccountMutation } = Digit.Hooks.bankAccount.useCreateBankAccount();
    const { mutate: DeleteWageSeeker } = Digit.Hooks.wageSeeker.useDeleteWageSeeker();

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
            value : [new Date().toISOString().split("T")[0], !isBirthDateValid ? () => isBirthDateValid : true]
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
        },
        {
            key : "basicDetails_aadhar",
            value : [!isModify ? "auto" : "none", !isModify ? "" : "field-value-no-border"]
        }
      ]
    }),
    [skillData, wardsAndLocalities, filteredLocalities, ULBOptions]);

    const closeToast = () => {
      setTimeout(() => {
        setShowToast(null);
      }, 5000);
    };

    const validateSelectedSkills = (formData) => {
      //write logic to validate skills selected
      let validateCheckPass = true
      const countSkillsInCategory = {}
      formData.skillDetails_skill.map(skill => {
        countSkillsInCategory[skill.code.split('.')[1]] = countSkillsInCategory[skill.code.split('.')[1]] ? countSkillsInCategory[skill.code.split('.')[1]] + 1 : 1
      });

      Object.keys(countSkillsInCategory).forEach(key => {
        if(countSkillsInCategory[key] > 1){
            validateCheckPass = false 
        }
      })

      if(!validateCheckPass){
        setShowToast({ label: t("SKILLS_SELECTION_INVALID") });
        closeToast();
        return true
      }else{
        return false
      }
    };

    const onFormValueChange = async (setValue, formData, formState, reset, setError, clearErrors, trigger, getValues) => {
        
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
            if(formData.basicDetails_dateOfBirth) {
                let ageInYear = Digit.DateUtils.getYearDifference(formData.basicDetails_dateOfBirth)
                setIsBirthDateValid(!(ageInYear < 18));
            }
            if(formData.financeDetails_ifsc) {
                //capitalize ifsc
                setValue("financeDetails_ifsc",formData?.financeDetails_ifsc?.toUpperCase())
                if(formData.financeDetails_ifsc?.length > 10) {
                    setTimeout(() => {
                        fetchIFSCDetails(formData.financeDetails_ifsc, 'financeDetails_branchName', setValue, setError, clearErrors);
                    }, 500);
                }
            }
            setSessionFormData({ ...sessionFormData, ...formData });
        }
    }

    const fetchIFSCDetails = async (ifscCode, branchNameField, setValue, setError, clearErrors) => {
        const res = await window.fetch(`https://ifsc.razorpay.com/${ifscCode}`);
        if (res.ok) {
            const { BANK, BRANCH } = await res.json();
            setValue(branchNameField, `${BANK}, ${BRANCH}`)
            clearErrors("financeDetails_ifsc")
        }
        if(res.status === 404) {
            setValue(branchNameField, "")
            setError("financeDetails_ifsc",{ type: "pattern" }, { shouldFocus: true })
        }
    }

    const sendDataToResponsePage = (individualId, isSuccess, message, showId) => {
        history.push({
            pathname: `/${window?.contextPath}/employee/masters/response`,
            search: individualId ? `?tenantId=${tenantId}&individualId=${individualId}` : '',
            state : {
                message,
                showId,
                isSuccess,
                isWageSeeker: true
            }
        }); 
    }

    const handleResponseForUpdate = async (wageSeekerPayload, bankAccountPayload) => {
        if(individualDetailsUpdated && !financeDetailsUpdated) {
            await UpdateWageSeekerMutation(wageSeekerPayload, {
                onError: async (error) => sendDataToResponsePage(individualId, false, 'MASTERS_WAGE_SEEKER_MODIFICATION_FAIL', true),
                onSuccess: async (responseData) => {
                    //check if skills.skillsTobeRemoved has records, then call delete API
                    if(wageSeekerPayload?.Individual?.skillsTobeRemoved?.length > 0) {
                        const wageSeekerSkillDeletePayload = getWageSeekerSkillDeletePayload({wageSeekerDataFromAPI: responseData, tenantId, skillsTobeRemoved: wageSeekerPayload?.Individual?.skillsTobeRemoved});
                        await DeleteWageSeeker(wageSeekerSkillDeletePayload, {
                            onError :  async (error) => sendDataToResponsePage(individualId, false, "MASTERS_WAGE_SEEKER_MODIFICATION_FAIL", true),
                            onSuccess: async (responseData) => {
                                sendDataToResponsePage(individualId, true, 'MASTERS_WAGE_SEEKER_MODIFICATION_SUCCESS', true)
                                clearSessionFormData()
                            }
                        })
                    }
                    else {
                        sendDataToResponsePage(individualId, true, 'MASTERS_WAGE_SEEKER_MODIFICATION_SUCCESS', true)
                        clearSessionFormData()
                    }
                }
              });
        } else if(!individualDetailsUpdated && financeDetailsUpdated) {
            await UpdateBankAccountMutation(bankAccountPayload, {
                onError :  async (error) => sendDataToResponsePage(individualId, false, "MASTERS_WAGE_SEEKER_MODIFICATION_FAIL", true),
                onSuccess: async (responseData) => {
                    sendDataToResponsePage(individualId, true, "MASTERS_WAGE_SEEKER_MODIFICATION_SUCCESS", true)
                    clearSessionFormData()
                }
              })
        } else if(individualDetailsUpdated && financeDetailsUpdated) {
            await UpdateWageSeekerMutation(wageSeekerPayload, {
                onError: async (error) => sendDataToResponsePage(individualId, false, "MASTERS_WAGE_SEEKER_MODIFICATION_FAIL", true),
                onSuccess: async (responseData) => {
                    if(wageSeekerPayload?.Individual?.skillsTobeRemoved?.length > 0) {
                        const wageSeekerSkillDeletePayload = getWageSeekerSkillDeletePayload({wageSeekerDataFromAPI: responseData, tenantId, skillsTobeRemoved: wageSeekerPayload?.Individual?.skillsTobeRemoved});
                        await DeleteWageSeeker(wageSeekerSkillDeletePayload, {
                            onError :  async (error) => sendDataToResponsePage(individualId, false, "MASTERS_WAGE_SEEKER_MODIFICATION_FAIL", true),
                            onSuccess: async (responseData) => {
                                await UpdateBankAccountMutation(bankAccountPayload, {
                                    onError :  async (error) => sendDataToResponsePage(individualId, false, "MASTERS_WAGE_SEEKER_MODIFICATION_FAIL", true),
                                    onSuccess: async (responseData) => {
                                        sendDataToResponsePage(individualId, true, "MASTERS_WAGE_SEEKER_MODIFICATION_SUCCESS", true)
                                        clearSessionFormData()
                                    }
                                })
                            }
                        })
                    } else {
                        await UpdateBankAccountMutation(bankAccountPayload, {
                            onError :  async (error) => sendDataToResponsePage(individualId, false, "MASTERS_WAGE_SEEKER_MODIFICATION_FAIL", true),
                            onSuccess: async (responseData) => {
                                sendDataToResponsePage(individualId, true, "MASTERS_WAGE_SEEKER_MODIFICATION_SUCCESS", true)
                                clearSessionFormData()
                            }
                        })
                    }
                },
            });
        }  
    }

    const handleResponseForCreate = async (wageSeekerPayload, data) => {
        await CreateWageSeekerMutation(wageSeekerPayload, {
            onError: async (error) => sendDataToResponsePage('', false, "MASTERS_WAGE_SEEKER_CREATION_FAIL", false),
            onSuccess: async (responseData) => {
                //Update bank account details if wage seeker update success
                const bankAccountPayload = getBankAccountUpdatePayload({formData: data, apiData: '', tenantId, isModify, referenceId: responseData?.Individual?.id, isWageSeeker: true});
                await CreateBankAccountMutation(bankAccountPayload, {
                    onError :  async (error) => sendDataToResponsePage('', false, "MASTERS_WAGE_SEEKER_CREATION_FAIL", false),
                    onSuccess: async (bankResponseData) => {
                        sendDataToResponsePage(responseData?.Individual?.individualId, true, "MASTERS_WAGE_SEEKER_CREATION_SUCCESS", true)
                        clearSessionFormData()
                    }
                })
            },
        });
    }

    const onSubmit = (data) => {
        data = Digit.Utils.trimStringsInObject(data)
        const validationError = validateSelectedSkills(data)
        if(validationError) return
        const wageSeekerPayload = getWageSeekerUpdatePayload({formData: data, wageSeekerDataFromAPI, tenantId, isModify})
        if(isModify) {
            const bankAccountPayload = getBankAccountUpdatePayload({formData: data, apiData: wageSeekerDataFromAPI, tenantId, isModify, referenceId: '', isWageSeeker: true});
            handleResponseForUpdate(wageSeekerPayload, bankAccountPayload);
        }else {
            handleResponseForCreate(wageSeekerPayload, data);
        }
    }

    return (
        <React.Fragment>
           <FormComposer
                label={isModify ? "CORE_COMMON_SAVE" : "ACTION_TEST_MASTERS_CREATE_WAGESEEKER"}
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
                labelBold={true}
            />
            {showToast && <Toast label={showToast?.label} error={true} isDleteBtn={true} onClose={()=>{
                setShowToast(null)
            }}></Toast>}
        </React.Fragment>
    )
}

export default ModifyWageSeekerForm;