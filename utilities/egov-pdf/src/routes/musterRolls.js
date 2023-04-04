var express = require("express");
var router = express.Router();
var url = require("url");
var config = require("../config");

var { search_musterRoll, create_pdf } = require("../api");
const { asyncMiddleware } = require("../utils/asyncMiddleware");

function renderError(res, errorMessage, errorCode) {
    if (errorCode == undefined) errorCode = 500;
    res.status(errorCode).send({ errorMessage })

}
router.post(
    "/muster-roll",
    asyncMiddleware(async function (req, res, next) {

        var tenantId = req.query.tenantId;
        var musterRollNumber = req.query.musterRollNumber;
        var requestinfo = req.body;
        if (requestinfo == undefined) {
            return renderError(res, "requestinfo can not be null", 400)
        }
        if (!tenantId) {
            return renderError(res, "tenantId is mandatory to generate the receipt", 400)
        }
        if (!musterRollNumber) {
            return renderError(res, "musterRollNumber is mandatory to generate the receipt", 400)
        }
        try {
            try {
                resMuster = await search_musterRoll(tenantId, requestinfo, musterRollNumber);

            }
            catch (ex) {
                if (ex.response && ex.response.data) console.log(ex.response.data);
                return renderError(res, "Failed to query details of the muster roll", 500);
            }
            var muster = resMuster.data;
            console.log(muster.musterRolls[0].individualEntries)
            if (muster && muster.musterRolls && muster.musterRolls.length > 0) {
                var pdfResponse;
                var pdfkey = config.pdf.nominal_muster_roll_template;


                try {

                    pdfResponse = await create_pdf(
                        tenantId,
                        pdfkey,
                        muster,
                        requestinfo
                    )
                }
                catch (ex) {
                    if (ex.response && ex.response.data) console.log(ex.response.data);

                    return renderError(res, "Failed to generate PDF for muster roll", 500);
                }

                var filename = `${pdfkey}_${new Date().getTime()}`;

                //pdfData = pdfResponse.data.read();
                res.writeHead(200, {
                    "Content-Type": "application/pdf",
                    "Content-Disposition": `attachment; filename=${filename}.pdf`,
                });
                pdfResponse.data.pipe(res);
            }
            else {
                return renderError(
                    res,
                    "There is no data found using this muster roll number",
                    404
                );
            }
        } catch (ex) {
            return renderError(res, "Failed to query details of the muster", 500);
        }

    })

);

module.exports = router;