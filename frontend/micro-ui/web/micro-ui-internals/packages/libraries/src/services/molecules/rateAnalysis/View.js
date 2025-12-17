import { RateAnalysisService } from "../../elements/rateAnalysis";

const transformRateAnalysisViewDataToApplicationDetails = async (data) => {
  let groupedByHeads = {};
  let infoCard = false;
  // Grouping amountDetails by heads, excluding those with null heads
  data.rateAnalysis.forEach((analysis) => {
    analysis.lineItems.forEach((item) => {
      if (item.type === "EXTRACHARGES") {
        let head = "EXTRACHARGES";

        if (!groupedByHeads[head]) {
          groupedByHeads[head] = [];
        }

        item.amountDetails.forEach((detail) => {
          let data = {
            id: item?.id,
            type: item?.type,
            targetId: item?.targetId,
            amountDetails: [
              {
                id: detail?.id,
                type: detail?.type,
                heads: detail?.heads,
                amount: detail?.amount,
              },
            ],
            additionalDetails: {
              id: item?.additionalDetails?.id,
              uom: item?.additionalDetails?.uom,
              sorType: detail?.heads,
              quantity: detail?.amount,
              sorSubType: item?.additionalDetails?.sorSubType,
              sorVariant: item?.additionalDetails?.sorVariant,
              description: item?.additionalDetails?.[detail?.heads][0],
            },
          };

          groupedByHeads[head].push(data);
        });
      } else {
        if (item.amountDetails == null) {
          let head = item?.additionalDetails?.sorType;

          if (!groupedByHeads[head]) {
            groupedByHeads[head] = [];
          }

          let data = {
            id: item?.id,
            type: item?.type,
            targetId: item?.targetId,
            amountDetails: [
              {
                id: "",
                type: "",
                heads: "",
                amount: 0.0,
              },
            ],
            additionalDetails: {
              id: item?.additionalDetails?.id,
              uom: item?.additionalDetails?.uom,
              sorType: item?.additionalDetails?.sorType,
              quantity: item?.additionalDetails?.definedQuantity,
              sorSubType: item?.additionalDetails?.sorSubType,
              sorVariant: item?.additionalDetails?.sorVariant,
              description: item?.additionalDetails?.description,
            },
          };

          groupedByHeads[head].push(data);
          infoCard = true;
        } else {
          // Handle cases where amountDetails is null or empty
          item.amountDetails.forEach((detail) => {
            infoCard = false;
            if (detail.heads) {
              let head = detail.heads;

              if (!groupedByHeads[head]) {
                groupedByHeads[head] = [];
              }
              let data = {
                id: item?.id,
                type: item?.type,
                targetId: item?.targetId,
                amountDetails: [
                  {
                    id: detail?.id,
                    type: detail?.type,
                    heads: detail?.heads,
                    amount: detail?.amount,
                  },
                ],
                additionalDetails: {
                  id: item?.additionalDetails?.id,
                  uom: item?.additionalDetails?.uom,
                  sorType: item?.additionalDetails?.sorType,
                  quantity: item?.additionalDetails?.definedQuantity,
                  sorSubType: item?.additionalDetails?.sorSubType,
                  sorVariant: item?.additionalDetails?.sorVariant,
                  description: item?.additionalDetails?.description,
                  basicRate:getBasicRate( item?.additionalDetails?.sorType,item?.additionalDetails?.rate?.amountDetails,detail.heads),
                },
              };
              if(detail.heads.split('.')[0]==="MA" ||detail.heads.split('.')[0]==="MHA"||detail.heads.split('.')[0]==="LA"){
             if (getStatusToAdd(item?.additionalDetails?.sorType,detail.heads.split('.')[0]))
              {groupedByHeads[head].push(data);}
            }
            else{
              groupedByHeads[head].push(data);
            }
          }
          });
        }
      }
    });
  });

  return {
    groupedByHead: groupedByHeads,
    rateAnalysisDetail: data.rateAnalysis[0],
    infoCard: infoCard,
  };
};

const getBasicRate = (sorType,amountDetails, headType) => {
  let rate = 0.0;
  

  rate=amountDetails.filter(data=>data.heads===headType)[0]?.amount;
  return rate;
}; 

const getStatusToAdd = (sorType, headType) => {
  switch (sorType) {
    case "M":
      return headType === "MA" || headType === "M";
    case "L":
      return headType === "LA" || headType === "LA"|| headType === "L";
    case "E":
      return headType === "MHA" || headType === "E";
    default:
      return false;
  }
};
// View object to handle fetching and transforming rate analysis details
export const View = {
  fetchRateAnalysisDetails: async (tenantId, data) => {
    try {
      const response = await RateAnalysisService.search(tenantId, data);

      if (!response || !response.rateAnalysis) {
        throw new Error("Invalid response from RateAnalysisService");
      }

      const res = await transformRateAnalysisViewDataToApplicationDetails(response);

      return res;
    } catch (error) {
      throw new Error(error?.response?.data?.Errors?.[0]?.message || "An error occurred");
    }
  },
};
