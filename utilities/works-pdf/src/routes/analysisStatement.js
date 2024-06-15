const express = require("express");
const router = express.Router();
const config = require("../config");


const data = require("./statementRes.json");
const {asyncMiddleware} = require("../utils/asyncMiddleware");
const {transformStatementData} = require("../utils/transformStatementData");
const {create_pdf} = require("../api");

function renderError(res, errorMessage, errorCode) {
    if (errorCode == undefined) errorCode = 500;
    res.status(errorCode).send({ errorMessage })

}

router.post("/rate-analysis-statement", asyncMiddleware(async function (req, res, next) {
    const requestInfo = req.body;
    const tenantId = req.query.tenantId;
    if(requestInfo == undefined) {
        return renderError(res, "requestinfo can not be null", 400);
    }
    try {
        const AnalysisStatement = transformStatementData(data);
        if(AnalysisStatement){
            var pdfResponse;
            const pdfKey = config.pdf.rateAnalysisStatement_template;
            try {

                pdfResponse = await create_pdf(
                    tenantId,
                    pdfKey,
                    AnalysisStatement,
                    requestInfo
                )
                const filename = `${pdfKey}_${new Date().getTime()}`;

                res.writeHead(200, {

                    "Content-Type": "application/pdf",
                    "Content-Disposition": `attachment; filename=${filename}.pdf`,
                });

                pdfResponse.data.pipe(res);

            }


            catch (ex) {

                return renderError(res, "Failed to generate PDF for detailed estimate", 500);

            }
        } else {

            return renderError(
                res,
                "There is no estimate created using this estimate number",
                404
            );

        }
    }catch (ex){
        return renderError(res, "Failed to transform analysis statement", 500);
    }
    console.log(AnalysisStatement)
}))

module.exports = router;
