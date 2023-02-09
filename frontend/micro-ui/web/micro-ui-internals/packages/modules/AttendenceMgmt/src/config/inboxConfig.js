import { useTranslation } from "react-i18next";

const InboxConfig = () => {
    const { t } = useTranslation();

    const tenant = Digit.ULBService.getStateId();

    //TODO: update api call to fetch agencies/partners
    const { isLoading, data: agencyData } = Digit.Hooks.useCustomMDMS(
      tenant,
      "common-masters",
      [
          {
              "name": "Department"
          }
      ]
      );

    if (agencyData?.[`common-masters`]) {
        var { Department: Agencies } = agencyData?.[`common-masters`]
    }
    Agencies?.map((item)=> Object.assign(item, {i18nKey:t(`ES_COMMON_${item?.code}`)}))
    console.log(Agencies);
    return {
        label : "ES_COMMON_INBOX",
        type : "inbox", 
        //Added search config, will be updated with inbox api config while integration
        apiDetails: {
            serviceName: "/muster-roll/v1/_search",
            requestParam: {},
            requestBody: {},
            minParametersForSearchForm:1,
            masterName:"commonUiConfig",
            moduleName:"SearchAttendanceConfig",
            tableFormJsonPath:"requestParam",
            filterFormJsonPath:"rrequestParam",
            searchFormJsonPath:"requestParam",
        },
        sections : {
            search : {
                uiConfig : {
                    headerStyle : null,
                    primaryLabel: 'ACTION_TEST_SEARCH',
                    secondaryLabel: 'CLEAR_SEARCH_LINk',
                    minReqFields: 1,
                    defaultValues : {
                        workName: "",
                        iaip:null,
                    },
                    fields : [
                        {
                            label:"WORKS_NAME_OF_WORK",
                            type: "text",
                            isMandatory: false,
                            disable: false,
                            populators: { 
                                name: "workName",
                                //validation: { pattern: /^[a-z0-9\/-]*$/i, minlength : 2 }
                            }
                        },
                        {
                            label: "ATM_IMPLEMENTING_AGENCY",
                            type: "dropdown",
                            isMandatory: false,
                            disable: false,
                            populators: {
                              name: "iaip",
                              optionsKey: "i18nKey",
                              //optionsKey: "code",
                              options: Agencies,
                            }
                        },

                    ]
                },
                label : "",
                children : {},
                show : true
            },
            links : {
                uiConfig : {
                    links : [
                        {
                            text: "WORKS_ENROLL_WAGE_SEEKER",
                            url: `/employee/contracts/create-contract`,
                            roles: [],
                        }
                    ],
                    label : "ATM_ATTENDANCE_MANAGEMENT",
                    logoIcon : { //Pass the name of the Icon Component as String here and map it in the InboxSearchLinks Component   
                        component : "BioMetricIcon",
                        customClass : "search-icon--projects"       
                    }
                },
                children : {},
                show : true //by default true. 
            },
            filter : {
                uiConfig : {
                    type : 'filter',
                    headerStyle : null,
                    primaryLabel: 'ACTION_TEST_APPLY',
                    minReqFields: 0,
                    secondaryLabel: '',
                    defaultValues : {
                        musterRolldateRange:"",
                    },
                    fields : [
                        {
                            label:"ATM_MUSTER_ROLL_DATE_RANE",
                            type: "date",
                            isMandatory: false,
                            disable: false,
                            populators: { 
                                name: "musterRolldateRange"
                            },
                        },
                    ]
                },
                label : "FILTERS",
                show : true
            },
            searchResult: {
                label: "",
                uiConfig: {
                    columns: [
                        {
                            label: "ATM_MUSTER_ROLL_ID",
                            jsonPath: "musterRollNumber",
                        },
                        {
                            label: "WORKS_NAME_OF_WORK",
                            jsonPath: "work",
                        },
                        {
                            label: "ATM_ATTENDANCE_WEEK",
                            jsonPath: "week",
                        },
                        {
                            label: "ATM_IA_AP",
                            jsonPath: "iaip",
                        },
                        {
                            label: "ATM_NO_OF_INDIVIDUALS",
                            jsonPath: "individualCount",
                        },
                        {
                            label: "ATM_SLA",
                            jsonPath: "slaDays",
                        }
                    ],
                    enableGlobalSearch: false,
                    enableColumnSort: true,
                    resultsJsonPath: "musterRolls", //CONFIRM!!
                },
                children: {},
                show: true 
            }
        },
        additionalSections : {
            //Open for Extensions
            //One can create a diff Parent card and add additional fields in it.
        }
    }
}

export default InboxConfig;