const e = require("express");
const { logger } = require("../logger");

const transformStatementData = (data) => {
    try {
        const sorMap = new Map();
        statement = data["statement"][0];

        statement["sorDetails"].forEach(sorDetail => {
            sorDetail["lineItems"].forEach(lineItem => {
                const amountDetail = lineItem["amountDetails"][0];

                // Check if the sorType already exists in the map
                if (!sorMap.has(lineItem["sorType"])) {
                    sorMap.set(lineItem["sorType"], []);
                }

                // Add the amountDetail to the appropriate sorType entry
                sorMap.get(lineItem["sorType"]).push(amountDetail);
            });
        });
        console.log(sorMap);
    }catch (ex){
        console.log(ex);
    }
};

module.exports = {
    transformStatementData
}