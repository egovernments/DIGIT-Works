// import { TextInput } from "@egovernments/digit-ui-react-components";
import React, { useEffect, useState, useCallback, useRef } from "react";
import { useTranslation } from "react-i18next";
import {Toast,Button,TextInput } from "@egovernments/digit-ui-components";

const fetchSorDetails = async (inputDATA) => {
 //method to fetch the data for estimate template
 const tenantId = Digit.ULBService.getCurrentTenantId();


 
 const requestCriteria = {
   url: "/mdms-v2/v2/_search",
   body: {
     MdmsCriteria: {
       tenantId: tenantId,
       schemaCode: "WORKS-SOR.SOR",
       uniqueIdentifiers: inputDATA,
     },
   },
   changeQueryName: "sorRates",
 };


 try {
   const data = await Digit.CustomService.getResponse(requestCriteria);
   
   return data["mdms"];


 } catch (error) {
   
   return [];
 }


 
};


const fetchTemplateData = async (searchText, setShowToast) => {
 //method to fetch the data for estimate template
 const tenantId = Digit.ULBService.getCurrentTenantId();
 let requestCriteria = {
   url: "/mdms-v2/v1/_search",
   body: {
     MdmsCriteria: {
       tenantId: tenantId,
       // schemaCode: "WORKS.EstimateTemplate",
       // limit: 100,
       // offset: 0,
       // filters: {}
       moduleDetails: [
         {
           moduleName: "WORKS",
           masterDetails: [
             {
               name: "EstimateTemplate",
               filter: `$[?(@.templateId=~/.*${searchText}.*/i  || @.templateName=~/.*${searchText}.*/i    )]`,
             },
           ],
         },
       ],
     },
   },
   useCache: false,
   setTimeParam: false,
 };


 //either search with Template id or name
 // if (searchText.startsWith("TMP_")) {
 //   requestCriteria.body.MdmsCriteria.filters.templateId = searchText;
 // } else if (searchText.length >= 3) {
 //   requestCriteria.body.MdmsCriteria.filters.templateName = searchText;
 // } else {
 //   setShowToast({ show: true, error: true, label: "WORKS_MINIMUM_CHAR_ERROR" });
 //   return [];
 // }


 try {
   const data = await Digit.CustomService.getResponse(requestCriteria);
   if (data?.MdmsRes?.WORKS?.EstimateTemplate?.length > 0) {
     setShowToast({ show: false, label: "", type:"" });
     return data?.MdmsRes?.WORKS?.EstimateTemplate;
   }
   
 } catch (error) {
   
   setShowToast({ show: true, type: "error", label: "TMP_API_ERROR" });
   return [];
 }
};


const fetchData = async (sorid, state, setState, setShowToast, t) => {
 //fetch the data of SOR recieved from estimate template
 const tenantId = Digit.ULBService.getCurrentTenantId();
 if (sorid == null) {
   setShowToast({ show: true, type: "error", label: "WORKS_CANNOT_ADD_EMPTY_DATA" });
   return true;
 }
 let currentDateInMillis = new Date().getTime();


 const requestCriteria = {
   url: "/mdms-v2/v1/_search",
   body: {
     MdmsCriteria: {
       tenantId: tenantId,
       moduleDetails: [
         {
           moduleName: "WORKS-SOR",
           masterDetails: [
             {
               name: "Rates",
               filter: `[?(@.sorId=='${sorid}')]`,
             },
           ],
         },
       ],
     },
   },
 };
 // const requestCriteria = {
 //   url: "/mdms-v2/v2/_search",
 //   body: {
 //     MdmsCriteria: {
 //       tenantId: tenantId,
 //       uniqueIdentifiers: [sorid],
 //       schemaCode: "WORKS-SOR.Rates",
      
 //     },
 //   },
 //   changeQueryName: "ratesQuery",
 // };
 try {
   const data = await Digit.CustomService.getResponse(requestCriteria);
   
   if (data?.MdmsRes?.["WORKS-SOR"]?.Rates?.length > 0) {
     const Rates = data?.MdmsRes?.["WORKS-SOR"]?.Rates?.filter((rate) => {
       // Convert validFrom and validTo to milliseconds
       let validFromInMillis = new Date(parseInt(rate?.validFrom)).getTime();
       
       let validToInMillis =  rate?.validTo && rate?.validTo !== "null" ? new Date(parseInt(rate?.validTo)).getTime() : Infinity;
       
       // Check if the current date is within the valid date range
       return validFromInMillis <= currentDateInMillis && currentDateInMillis < validToInMillis;
     });
     
     if (Rates.length <= 0) {
       //setShowToast({show: true, type: "error", label:`${t(`TMP_RATE_NO_ACTIVE_RATE_ERROR`)} ${sorid}`});
       return undefined;
     }
     return Rates;
   } else {
     //setShowToast({show: true, type: "error", label:`${t(`TMP_RATE_NOT_FOUND_ERROR`)} ${sorid}`});
     return undefined;
   }
 } catch (error) {
   // Handle any errors here
   
 }
};


const searchTemplate = (props) => {
 const { t } = useTranslation();
 const [stateData, setStateData] = useState({ searchText: "" });
 const [selectedTemplate, setSelectedTemplate] = useState(null);
 const [inputValue, setInputValue] = useState("");
 const [suggestions, setSuggestions] = useState([]);
 const menuRef = useRef();
 const [showToast, setShowToast] = useState({ show: false, label: "", type: "" });
 const { register, setValue, watch } = props;
 let formData = watch("SOR");
 let formNonSORdata = watch("NONSOR");


 useEffect(() => {
   register("searchSor", stateData);
 }, []);


 useEffect(() => {
   //if the text reaches minimun of 3 character it should search
   if (stateData.searchText.length >= 3) {
     fetchTemplateData(stateData.searchText, setShowToast).then((resp) => {
       if (resp && resp?.length > 0) {
         setSuggestions(resp);
       } else {
         setSuggestions([{ templateName: t("TMP_NO_MATCHING_SOR") }]);
       }
     });
   }
 }, [stateData.searchText]);


 const handleSearchTextChange = (event) => {
   setStateData({ ...stateData, searchText: event.target.value });
   setInputValue(event.target.value);
 };


 const closeMenu = () => {
   setSuggestions([]);
 };


 Digit.Hooks.useClickOutside(menuRef, closeMenu, suggestions);


 const setFormValue = useCallback(
   (value) => {
     setValue("SOR", value);
     setValue(`SORtable`, value);
   },
   [setValue]
 );


 const setNonSORFormValue = useCallback(
   (value) => {
     setValue("NONSOR", value);
     setValue(`NONSORtable`, value);
   },
   [setValue]
 );


 const buttonClick = async () => {
   // Check for duplicate SOR entries
   // if (
   //   formData?.length > 0 &&
   //   formData?.find((ob) => ob?.sorCode && ob?.sorCode === stateData?.selectedTemplate?.data?.lineItems[0]?.sorCode)
   // ) {
   //   setShowToast({ show: true, type: "error", label: "WORKS_CANNOT_ADD_DUPLICATE_SOR" });
   //   return;
   // }


   // Transform SOR and non-SOR items
   let transformedItems = stateData?.sorDetails? stateData?.sorDetails?.map((item) => ({
     sNo: 1,
     description: item?.data?.description,
     uom: item?.data?.uom,
     category: "SOR",
     approvedQuantity: item?.data?.quantity,
     consumedQ: 0,
     sorType: item?.data?.sorType,
     sorSubType: item?.data?.sorSubType,
     sorCode: item?.data?.id,
     currentMBEntry: 0,
     amount: 0,
     measures: [],
     targetId: null,
     sorId: item?.data?.id,
   })):[];


   let newnonsorIndex = formNonSORdata[formNonSORdata?.length - 1]?.sNo+1;
   let nosSorData = stateData?.selectedTemplate?.nonSorLineItems? stateData?.selectedTemplate.nonSorLineItems?.map((item, index) => ({
     sNo: newnonsorIndex++,
     description: item?.description,
     uom: item?.uom,
     category: "NON-SOR",
     approvedQuantity: item?.quantity,
     consumedQ: 0,
     sorType: item?.sorType,
     sorSubType: item?.sorSubType,
     sorCode: item?.id,
     currentMBEntry: 0,
     amount: 0,
     measures: [],
     targetId: null,
   })):[];


   
   transformedItems.push(...nosSorData);


   
   transformedItems = transformedItems?.filter((item) => {
     if (item.category === "NON-SOR") return true; // Always include NON-SOR items
     return !formData.some((existingItem) => existingItem.sorCode === item.sorCode);
   });


   try {
     // Fetch rates for SOR items
     const sorItems = transformedItems?.filter((item) => item.category === "SOR") || [];
     let ratesErrorSorIds = [];
     for (const sor of sorItems) {
       
       const apiData = await fetchData(sor.sorCode, formData, setFormValue, setShowToast, t);
       if (apiData !== undefined && apiData?.[0]?.sorId === sor.sorId) {
         sor.unitRate = apiData?.[0]?.rate || 0;
         sor.amountDetails = apiData?.[0]?.amountDetails;
       } else {
         ratesErrorSorIds.push(sor?.sorCode);
         console.error("Rates not available in fetchData response");
       }
     }


     if (ratesErrorSorIds?.length > 0) {
       
       setShowToast({ show: true, type: "error", label: `${t(`TMP_RATE_NOT_FOUND_OR_ACTIVE_ERROR`)} ${ratesErrorSorIds.join(", ")} ` });
     }


     // Combine SOR and non-SOR items
     let updatedFormData = [...formData, ...transformedItems?.filter((item) => item.category === "SOR" && item?.amountDetails)];


     // Remove any placeholder entries if present
     updatedFormData = updatedFormData?.filter((item) => item.description);


     setFormValue(updatedFormData);
     let updateNonsorFormdata = [...formNonSORdata, ...transformedItems?.filter((item) => item.category === "NON-SOR")];
     updateNonsorFormdata = updateNonsorFormdata?.filter((item) => item.description);
     setNonSORFormValue(updateNonsorFormdata);
     setInputValue("");
     setStateData({ ...stateData, SORSubType: null, SORVariant: null, selectedTemplate: null });
   } catch (error) {
     console.error("Error fetching data:", error);
   }


   setSelectedTemplate(null);
 };


 const handleSelectOption = async (option) => {
   //check if optionid is there or not
   
   if (option?.templateId) {
     
     const sorCodes = option.sorLineItems.map((item) => item.sorCode);
     
     await fetchSorDetails(sorCodes).then((listData) => {
       setStateData({
         ...stateData,
         selectedTemplate: option,


         sorDetails: listData,
       });
       setInputValue(option?.templateName);
       setSelectedTemplate(option);
       setSuggestions([]);
     });
     
   }
 };


 return (
   <div>
     <div className="search-sor-container">
       <span className="search-sor-label">{t("ESTIMATE_SEARCH_TEMPLATE_LABEL")}</span>
       <div className="search-sor-button">
         <div className={"search-bar-sor"} style={{ margin:"0px", marginTop: "20px", width:"unset" ,flex:1,maxWidth:"37.5rem"}} ref={menuRef}>
           <TextInput
             type="text"
             name={"Search"}
             placeholder={t("SEARCH_TEMPLATE_HINT")}
             value={inputValue}
             onChange={handleSearchTextChange}
            //  customClass="search-sor-input"
           />
           {suggestions?.length > 0 && (
             <ul className="suggestions-sor" style={{ zIndex: "21", maxHeight: "33rem", overflow: "auto" }}>
               {suggestions.map((option) => (
                 <li key={option?.templateId} onClick={() => handleSelectOption(option)}>
                   {option?.templateName}
                 </li>
               ))}
             </ul>
           )}
         </div>
         <Button label={t("ESTIMATE_ADD_LABEL")} onClick={buttonClick} className={"add-sor-button"}/>
       </div>
     </div>
     {showToast?.show && (
       <Toast
         labelstyle={{ width: "100%" }}
         type={showToast?.type}
         label={t(showToast?.label)}
         isDleteBtn={true}
         onClose={() => setShowToast({ show: false, label: "", type: "" })}
       />
     )}
   </div>
 );
};


export default searchTemplate;



