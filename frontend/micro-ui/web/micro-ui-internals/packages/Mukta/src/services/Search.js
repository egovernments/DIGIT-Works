import _ from "lodash";

const createProjectsArray = (t, project, searchParams, headerLocale) => {
    let totalProjects = {
        searchedProject : {},
        subProjects : []
    };
    let basicDetails = {};
    let totalProjectsLength = project.length;
    // for(let projectIndex = 0; projectIndex < totalProjectsLength; projectIndex++) {
        let currentProject = project[0];
        const headerDetails = {
            title: " ",
            asSectionHeader: true,
            values: [
                { title: "WORKS_PROJECT_ID", value: currentProject?.projectNumber || "NA"},
                { title: "WORKS_DATE_PROPOSAL", value: Digit.Utils.pt.convertEpochToDate(currentProject?.additionalDetails?.dateOfProposal) || "NA"},
                { title: "WORKS_PROJECT_NAME", value: currentProject?.name || "NA"},
                { title: "PROJECT_PROJECT_DESC", value: currentProject?.description || "NA"}
            ]
        };

        const projectDetails = {
            title: "WORKS_PROJECT_DETAILS",
            asSectionHeader: true,
            values: [
                { title: "PROJECT_LOR", value: currentProject?.referenceID || "NA" },
                { title: "WORKS_PROJECT_TYPE", value: currentProject?.projectType ? t(`COMMON_MASTERS_${Digit.Utils.locale.getTransformedLocale(currentProject?.projectType)}`) : "NA" },
                { title: "PROJECT_TARGET_DEMOGRAPHY", value: currentProject?.additionalDetails?.targetDemography ? t(`COMMON_MASTERS_${currentProject?.additionalDetails?.targetDemography }`) : "NA" },
                { title: "PROJECT_ESTIMATED_COST", value: currentProject?.additionalDetails?.estimatedCostInRs ? `₹ ${Digit.Utils.dss.formatterWithoutRound(currentProject?.additionalDetails?.estimatedCostInRs, 'number')}` : "NA" },
            ]
        };

        const locationDetails = {
            title: "WORKS_LOCATION_DETAILS",
            asSectionHeader: true,
            values: [
                { title: "WORKS_GEO_LOCATION",value: currentProject?.address?.addressLine1 || "NA" },
                { title: "WORKS_CITY",value: currentProject?.address?.city ? t(`TENANT_TENANTS_${Digit.Utils.locale.getTransformedLocale(currentProject?.address?.city)}`) : "NA" }, //will check with Backend
                { title: "WORKS_WARD", value: currentProject?.address?.boundary ? t(`${headerLocale}_ADMIN_${currentProject?.address?.boundary}`) : "NA"  }, ///backend to update this
                { title: "WORKS_LOCALITY",value: currentProject?.additionalDetails?.locality ? t(`${headerLocale}_ADMIN_${currentProject?.additionalDetails?.locality?.code}`) : "NA" },
            ]
        };

        const financialDetails = {
            title: "WORKS_FINANCIAL_DETAILS",
            asSectionHeader: false,
            values: [
                { title: "WORKS_HEAD_OF_ACCOUNTS", value: currentProject?.additionalDetails?.fund ? t(`COMMON_MASTERS_FUND_${currentProject?.additionalDetails?.fund}`) : "NA" },
            ],
          };

        const documentDetails = {
            title: "",
            asSectionHeader: true,
            additionalDetails: {
                documents: [{
                    title: "WORKS_RELEVANT_DOCUMENTS",
                    BS : 'Works',
                    values: currentProject?.documents?.map((document) => {
                        return {
                            title: document?.documentType,
                            documentType: document?.documentType,
                            documentUid: document?.fileStore,
                            fileStoreId: document?.fileStore,
                        };
                    }),
                },
                ]
            }
        }

        // if(currentProject?.projectNumber === searchParams?.Projects?.[0]?.projectNumber) {
            basicDetails = {
                projectID : currentProject?.projectNumber,
                projectProposalDate : Digit.Utils.pt.convertEpochToDate(currentProject?.additionalDetails?.dateOfProposal) || "NA",
                projectName : currentProject?.name || "NA",
                projectDesc : currentProject?.description || "NA",
                projectHasSubProject : (totalProjectsLength > 1 ? "COMMON_YES" : "COMMON_NO"),
                projectParentProjectID : currentProject?.ancestors?.[0]?.projectNumber || "NA",
                uuid:currentProject?.id,
                address:currentProject?.address,
                ward: currentProject?.additionalDetails?.ward
            }
            totalProjects.searchedProject = {
                basicDetails,
                headerDetails, 
                projectDetails, 
                locationDetails, 
                financialDetails,
                documentDetails
            }
        // }
    // }
    return totalProjects;
}

export const Search = {
    viewProjectDetailsScreen: async(t,tenantId, searchParams, filters = {limit : 10, offset : 0, includeAncestors : true, includeDescendants : true}, headerLocale)=> {
        const response = await Digit.WorksService?.searchProject(tenantId, searchParams, filters);
        
        let projectDetails = {
            searchedProject : {
                basicDetails : {},
                details : {
                    projectDetails : [],
                }
            },
        }

        if(response?.Projects) {
            let projects = createProjectsArray(t, response?.Projects, searchParams, headerLocale);
        
            //searched Project details
            projectDetails.searchedProject['basicDetails'] = projects?.searchedProject?.basicDetails;
            projectDetails.searchedProject['details']['projectDetails'] = {applicationDetails : [projects?.searchedProject?.headerDetails, projects?.searchedProject?.projectDetails, projects?.searchedProject?.locationDetails,projects?.searchedProject?.financialDetails, projects?.searchedProject?.documentDetails]}; //rest categories will come here
    
        }

        return {
            projectDetails : response?.Projects ? projectDetails : [],
            response : response?.Projects,
            processInstancesDetails: [],
            applicationData: {},
            workflowDetails: [],
            applicationData:{},
            isNoDataFound : response?.Projects?.length === 0
        }
    },
    searchEstimate : async(tenantId, filters) => {
        const response = await Digit.WorksService?.estimateSearch({tenantId, filters});
        return response?.estimates;
    }
}