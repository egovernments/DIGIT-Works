export const getDefaultValues = (sordata, t, mbNumber, compositionData, allSORData, allOverheadData, isUpdate) => {
  //default values to show the data in create rate analysis screen
 

  let SORData = {
    SORCode: sordata?.uniqueIdentifier,
    SORType: sordata?.data?.sorType !== "NA"? t(`WORKS_SOR_TYPE_${sordata?.data?.sorType}`) : "NA",
    SORSubType: sordata?.data?.sorSubType !== "NA" ? t(`WORKS_SOR_SUBTYPE_${sordata?.data?.sorSubType}`) : "NA",
    SORVarient: sordata?.data?.sorVariant !== "NA" ? t(`WORKS_SOR_VARIANT_${sordata?.data?.sorVariant}`) : "NA",
    uom: sordata?.data?.uom !== "NA"? t(`COMMON_MASTERS_UOM_${sordata?.data?.uom}`) : "NA",
    rateDefinedForQty: sordata?.data?.quantity,
    description: sordata?.data?.description,
    status: sordata?.isActive ? "Active" : "InActive",
  };
  let SORDetails = [];
  let extraCharges = [];
  if (isUpdate) {
    SORDetails = getDefaultSORDetails(compositionData, allSORData);
    extraCharges = getDefaultExtraCharges(compositionData, allOverheadData);
    SORData.analysis_qty_defined = compositionData?.data.quantity;
    SORData.effective_from_date = compositionData?.data.effectiveFrom
    ? new Date(parseInt(compositionData?.data.effectiveFrom)).toLocaleString("en-IN", {
        timeZone: "Asia/Kolkata",
        year: "numeric",
        month: "2-digit",
        day: "2-digit"
      }).split(",")[0].split("/").reverse().join("-")
    : "";
  }
  return { SORData, SORDetails, extraCharges };
};

export const getDefaultSORDetails = (compositionData, allSORData) => {
  const SORTypeCodes = {
    M: "MATERIAL",
    E: "MACHINERY",
    L: "LABOUR",
  };

  return allSORData?.mdms?.map((ob, index) => {
    return {
      sNo: index + 1,
      category: "SOR",
      definedQuantity: ob?.data?.quantity,
      description: ob?.data?.description,
      quantity: compositionData?.data?.basicSorDetails?.filter((cd) => cd?.sorId === ob?.data?.id)?.[0]?.quantity,
      sorCode: ob?.data?.id,
      sorId: ob?.data?.id,
      sorSubType: ob?.data?.sorSubType,
      sorType: SORTypeCodes[ob?.data?.sorType],
      uom: ob?.data?.uom,
    };
  });
};

export const getDefaultExtraCharges = (compositionData, allOverheadData) => {
  return compositionData?.data?.additionalCharges.map((ob) => {
    return {
      ...ob,
      applicableOn: allOverheadData?.mdms?.filter((obj) => obj?.uniqueIdentifier === ob?.applicableOn)?.[0]?.data,
      calculationType: { name: ob?.calculationType, code: ob?.calculationType.toUpperCase() },
      isShow: true,
    };
  });
};

export const deepCompare = (obj1, obj2) => {
  if (obj1 === obj2) return false; // Identical references or values
  if (typeof obj1 !== "object" || typeof obj2 !== "object" || obj1 === null || obj2 === null) return true; // Different types or one is null

  const keys1 = Object.keys(obj1);
  const keys2 = Object.keys(obj2);

  for (let key of keys1) {
    if (!keys2.includes(key) || deepCompare(obj1[key], obj2[key])) return true; // Key missing or values differ
  }

  return false; // No differences found
};

export const getPerUnitQty = (data, analysisQty) => {
  return parseFloat(data?.quantity) / parseFloat(data?.definedQuantity) / parseFloat(analysisQty);
};

export const getBasicSORDetails = (data) => {
  return data?.SORDetails?.map((ob) => {
    return {
      sorId: ob?.sorId,
      quantity: parseFloat(ob?.quantity),
      perUnitQty: getPerUnitQty(ob, data?.analysis_qty_defined),
    };
  });
};

export const getAdditionCharges = (data) => {
  return data?.extraCharges
    ?.filter((ac) => ac !== null)
    .map((ob) => {
      return {
        description: ob?.description,
        applicableOn: `${ob?.applicableOn?.code}.${ob?.applicableOn?.id}`,
        calculationType: ob?.calculationType?.name,
        figure: parseFloat(ob?.figure),
      };
    });
};

export const transformRequestBody = async (data, createdState, tenantId, compositionData, isUpdate) => {
  let composition_Id = isUpdate ? compositionData?.mdms?.[0]?.data?.compositionId : await preProcessData();

  let requestBody = {
    Mdms: {
      tenantId: tenantId?.split(".")?.[0],
      schemaCode: "WORKS-SOR.Composition",
      uniqueIdentifier: null,
      data: {
        compositionId: composition_Id,
        sorId: data?.SORCode,
        sorType: "W",
        type: "SOR",
        quantity: parseFloat(data?.analysis_qty_defined),
        active: true,
        basicSorDetails: getBasicSORDetails(data),
        additionalCharges: getAdditionCharges(data),
        effectiveFrom: `${Digit.Utils.date.convertDateToEpoch(data?.effective_from_date)}`,
      },
      isActive: true,
    },
  };

  if (isUpdate) {
    requestBody.Mdms.id = compositionData?.mdms?.[0]?.id;
    requestBody.Mdms.uniqueIdentifier = compositionData?.mdms?.[0]?.data?.compositionId;
    requestBody.Mdms.data.sorType = compositionData?.mdms?.[0]?.data?.sorType;
    requestBody.Mdms.auditDetails = compositionData?.mdms?.[0]?.auditDetails;
  }

  return requestBody;
};

const preProcessData = async () => {
  let composition_Id = await generateId();
  return composition_Id;
};

const generateId = async (tenantId = Digit.ULBService.getCurrentTenantId()) => {
  const requestCriteria = {
    url: "/egov-idgen/id/_generate",
    body: {
      idRequests: [
        {
          tenantId: tenantId,
          idName: "works.composition.number",
        },
      ],
    },
  };

  const response = await Digit.CustomService.getResponse({ url: requestCriteria?.url, body: requestCriteria?.body });
  return response?.idResponses?.[0]?.id;
};

export const has4DecimalPlaces = (value) => {
  const regex = /^[0-9]*\.?[0-9]{0,4}$/;
  return regex.test(value);
};

/* table content rows */

export const getRefactoredTableRows = (listData, sorType) => {
  return listData?.map((data) => ({
    description: data?.additionalDetails?.description,
    uom: data?.additionalDetails?.uom,
    id: data?.additionalDetails?.id,
    sorType: sorType,
    amount: data?.amountDetails[0]?.amount,
    quantity: data?.additionalDetails?.quantity,
    basicRate: data?.additionalDetails?.basicRate,
    sorCode: data?.additionalDetails?.id,
  }));
};

export const getRefactoreExtraChargesTableRows = (listData, sorType) => {
  return listData?.map((data) => ({
    description: data?.additionalDetails?.description,
    uom: data?.additionalDetails?.uom,
    id: data?.additionalDetails?.id,
    sorType: sorType,
    amount: data?.amountDetails[0]?.amount,
    quantity: data?.additionalDetails?.quantity,
    basicRate: data?.amountDetails[0]?.amount,
    calculationType: data?.amountDetails[0]?.type,
    appliedOn: `WORKS_SOR_OVERHEAD_${data?.amountDetails[0]?.heads?.split(".")[0]}_${data?.amountDetails[0]?.heads?.split(".")[1]}`,
    figure: data?.amountDetails[0]?.amount,
  }));
};
/* table content rows */

export const calculateTotalAmount = (listData) => {
  let totalAmount = listData.reduce((sum, item) => sum + (item.amount || 0), 0);
  //return totalAmount;

  return Digit.Utils.dss.formatterWithoutRound(parseFloat(totalAmount).toFixed(2),"number",undefined,true,undefined,2)
 // return parseFloat(totalAmount).toFixed(2)
};

/* gross total amount calculation */

export const grossTotalAmountCalculation = (listData) => {
  let totalAmount = 0.0;

  const categories = [
    "Labour",
    "Conveyance",
    "Royality",
    "MaterialBasicRate",
    "MachineryBasicRate",
    "DistrictMineralfund",
    "ExplorationMineralfund",
    "AdditionalCharges",
    "ExtraCharges"
  ];

  categories.forEach((category) => {
    if (listData?.[category]) {
      totalAmount += getRefactoredTableRows(listData[category], category).reduce((sum, item) => sum + (item.amount || 0), 0);
    }
  });

  return [
    { amount: totalAmount, name: "ES_COMMON_TOTAL_AMOUNT" },
    { amount: (totalAmount * 1) / 100, name: "RA_LABOUR_CESS" },
    { amount:( totalAmount + (totalAmount * 1) / 100), name: `RA_RATE_AMOUNT` },
  ];
};

export const formatDate = (epochTime) => {
  if (!epochTime) {
    return null;
  }

  const date = new Date(parseInt(epochTime));
  const day = String(date.getDate()).padStart(2, "0");
  const month = String(date.getMonth() + 1).padStart(2, "0");
  const year = date.getFullYear();

  return `${day}/${month}/${year}`;
};