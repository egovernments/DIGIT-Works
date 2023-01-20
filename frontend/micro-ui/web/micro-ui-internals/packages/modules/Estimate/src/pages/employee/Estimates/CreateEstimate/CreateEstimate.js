import { Card,StatusTable,Row,Header,HorizontalNav,ActionBar,SubmitBar,WorkflowModal,FormComposer } from '@egovernments/digit-ui-react-components'
import React,{Fragment,useEffect,useState} from 'react'
import { useTranslation } from 'react-i18next'
import getModalConfig from './config'
import { createEstimateConfig } from './createEstimateConfig'
import { createEstimatePayload } from './createEstimatePayload'

const cardState = {
    "title": " ",
    "asSectionHeader": true,
    values:[
        {
            "title": "WORKS_DATE_PROPOSAL",
            "value": "06/05/2022"
        },
        {
            "title": "WORKS_PROJECT_NAME_AST",
            "value": "RWHS Ward 1"
        },
        {
            "title": "EVENTS_DESCRIPTION",
            "value": "Rain Water Harvesting Scheme in Ward 1"
        },
        {
            "title": "WORKS_HAS_SUB_PROJECT_LABEL",
            "value": "No"
        },
    ]
}
const configNavItems = [
    {
        name: "Project Details",
        code: "WORKS_PROJECT_DETAILS"
    },
    {
        name: "Work Details",
        code: "WORKS_WORK_DETAILS"
    },
]
const CreateEstimate = ({ EstimateSession }) => {
    const [sessionFormData, setSessionFormData, clearSessionFormData] = EstimateSession;

    const { mutate: EstimateMutation } = Digit.Hooks.works.useCreateEstimateNew("WORKS");


    const [showModal, setShowModal] = useState(false);

    const { t } = useTranslation()
    const rolesForThisAction = "EST_CHECKER" //hardcoded for now
    const [config, setConfig] = useState({});
    const [approvers, setApprovers] = useState([]);
    const [selectedApprover, setSelectedApprover] = useState({});

    const [department, setDepartment] = useState([]);
    const [selectedDept, setSelectedDept] = useState({})

    const [designation, setDesignation] = useState([]);
    const [selectedDesignation, setSelectedDesignation] = useState({})

    const [inputFormData,setInputFormData] = useState(sessionFormData)

    const onFormSubmit = async (_data) => {
        
        setInputFormData((prevState) => _data)
        //first do whatever processing you want on form data then pass it over to modal's onSubmit function
        
        setShowModal(true);
        //      use below code for create contract API CALL

        //     await contractMutation(payload, {
        //         onError: (error, variables) => {
        //             setShowToast({ warning: true, label: error?.response?.data?.Errors?.[0].message ? error?.response?.data?.Errors?.[0].message : error });
        //             setTimeout(() => {
        //             setShowToast(false);
        //             }, 5000);
        //         },
        //         onSuccess: async (responseData, variables) => {
        //             history.push("/works-ui/employee/works/response",{
        //                 header:"Work Order Created Successfully and sent for Approval",
        //                 id:"WO/ENG/0001/07/2021-22",
        //                 info:t("WORKS_ORDER_ID"),
        //                 message:`Work order with Work Order ID {workID} created successfully.`,
        //                 links:[
        //                     {
        //                         name:t("WORKS_CREATE_CONTRACT"),
        //                         redirectUrl:"/works-ui/employee/works/create-contract",
        //                         code:"",
        //                         svg:"CreateEstimateIcon",
        //                         isVisible:true,
        //                         type:"add"
        //                     },
        //                     {
        //                         name:t("WORKS_GOTO_CONTRACT_INBOX"),
        //                         redirectUrl:"/works-ui/employee/works/create-contract",
        //                         code:"",
        //                         svg:"RefreshIcon",
        //                         isVisible:true,
        //                         type:"add"
        //                     }
        //                 ]
        //             })
        //         }
        //     })
    };
    const onModalSubmit = async (data) => {
        
        const completeFormData = {
            ...data,
            ...inputFormData,
            selectedApprover,
            selectedDept,
            selectedDesignation
        }
        setSessionFormData(completeFormData)
        console.log(completeFormData);

        const payload = createEstimatePayload(completeFormData)
        setShowModal(false);
        
        

        await EstimateMutation(payload, {
            onError: (error, variables) => {
                debugger
                setShowToast({ warning: true, label: error?.response?.data?.Errors?.[0].message ? error?.response?.data?.Errors?.[0].message : error });
                setTimeout(() => {
                    setShowToast(false);
                }, 5000);
            },
            onSuccess: async (responseData, variables) => {
                debugger
                clearSessionFormData();
                history.push("/works-ui/employee/works/response", {
                    header: t("WORKS_ESTIMATE_RESPONSE_CREATED_HEADER"),
                    id: responseData?.estimates[0]?.estimateNumber,
                    info: t("WORKS_ESTIMATE_ID"),
                    message: t("WORKS_ESTIMATE_RESPONSE_MESSAGE_CREATE", { department: t(`ES_COMMON_${responseData?.estimates[0]?.department}`) }),
                    links: [
                        {
                            name: t("WORKS_CREATE_ESTIMATE"),
                            redirectUrl: "/works-ui/employee/estimate/create-estimate",
                            code: "",
                            svg: "CreateEstimateIcon",
                            isVisible: true,
                            type: "add",
                        },
                        {
                            name: t("WORKS_GOTO_ESTIMATE_INBOX"),
                            redirectUrl: "/works-ui/employee/works/inbox",
                            code: "",
                            svg: "GotoInboxIcon",
                            isVisible: true,
                            type: "inbox",
                        },
                        {
                            name: t("WORKS_DOWNLOAD_PDF"),
                            redirectUrl: "/works-ui/employee/works/inbox",
                            code: "",
                            svg: "DownloadPrefixIcon",
                            isVisible: true,
                            type: "download",
                        },
                    ],
                });
            },
        });

        //here
        //console.log(createFormData);
        //console.log(sessionFormData);
        //here you can handle the data submitted in the modal and call the api
        //access comments from data and details such as dept,desig,approver are stored locally in this comp
    }

    const { isLoading: mdmsLoading, data: mdmsData, isSuccess: mdmsSuccess } = Digit.Hooks.useCustomMDMS(
        Digit.ULBService.getCurrentTenantId(),
        "common-masters",
        [
            {
                "name": "Designation"
            },
            {
                "name": "Department"
            }
        ]
    );

    mdmsData?.["common-masters"]?.Designation?.map(designation => {
        designation.i18nKey = `ES_COMMON_DESIGNATION_${designation?.name}`
    })

    mdmsData?.["common-masters"]?.Department?.map(department => {
        department.i18nKey = `ES_COMMON_${department?.code}`
    })
    useEffect(() => {

        //setApprovers(approverData?.Employees?.map((employee) => ({ uuid: employee?.uuid, name: employee?.user?.name })));
        //setApprovers(employeeDatav1?.Employees?.length > 0 ? employeeDatav1?.Employees : [])
        setDepartment(mdmsData?.["common-masters"]?.Department)
        setDesignation(mdmsData?.["common-masters"]?.Designation)
    }, [mdmsData]);


    const { isLoading: approverLoading, isError, error, data: employeeDatav1 } = Digit.Hooks.hrms.useHRMSSearch({ designations: selectedDesignation?.code, departments: selectedDept?.code, roles: rolesForThisAction, isActive: true }, Digit.ULBService.getCurrentTenantId(), null, null, { enabled: !!(selectedDept || selectedDesignation) });


    employeeDatav1?.Employees.map(emp => emp.nameOfEmp = emp?.user?.name || "NA")

    useEffect(() => {
        setApprovers(employeeDatav1?.Employees?.length > 0 ? employeeDatav1?.Employees.filter(emp => emp?.nameOfEmp !== "NA") : [])
    }, [employeeDatav1])
    useEffect(() => {

        setConfig(
            getModalConfig({
                t,
                approvers,
                selectedApprover,
                setSelectedApprover,
                designation,
                selectedDesignation,
                setSelectedDesignation,
                department,
                selectedDept,
                setSelectedDept,
                approverLoading
            })
        )

    }, [approvers, designation, department])

    const estimateFormConfig = createEstimateConfig(t)

  return (
    <Fragment>
          {showModal && <WorkflowModal
              closeModal={() => setShowModal(false)}
              onSubmit={onModalSubmit}
              config={config}
              sessionFormData={sessionFormData}
              setSessionFormData={setSessionFormData}
          />
          }
        <Header styles={{ marginLeft: "14px" }}>{t("ACTION_TEST_CREATE_ESTIMATE")}</Header>
        {/* Will fetch projectId from url params and do a search for project to show the below data in card while integrating with the API  */}
          <Card styles={{ marginLeft: "14px" }}>
            <StatusTable>
                {cardState.values.map((value)=>{
                    return (
                        <Row key={t(value.title)} label={t(value.title)} text={value.value} />
                    )
                })}
                  
            </StatusTable>
        </Card>
        <FormComposer
            label={"ACTION_TEST_CREATE_ESTIMATE"}
            config={estimateFormConfig?.form.map((config) => {
                return {
                    ...config,
                    body: config?.body.filter((a) => !a.hideInEmployee),
                };
            })}
            onSubmit={onFormSubmit}
            submitInForm={false}
            fieldStyle={{ marginRight: 0 }}
            inline={false}
            className="card-no-margin"
            defaultValues={estimateFormConfig?.defaultValues}
            showWrapperContainers={false}
            isDescriptionBold={false}
            noBreakLine={true}
            showMultipleCardsWithoutNavs={false}
            showMultipleCardsInNavs={true}
            horizontalNavConfig={configNavItems}
            showNavs={true}  
        />


    </Fragment>
  )
}

export default CreateEstimate