var express = require("express");
var router = express.Router();
var url = require("url");
var config = require("../config");

var { search_projectDetails, create_pdf } = require("../api");
const { asyncMiddleware } = require("../utils/asyncMiddleware");

function renderError(res, errorMessage, errorCode) {
    if (errorCode == undefined) errorCode = 500;
    res.status(errorCode).send({ errorMessage })

}
router.post(
    "/project-details",
    asyncMiddleware(async function (req, res, next) {
        var tenantId = req.query.tenantId;
        var projectNum = req.query.projectId;
        var requestinfo = req.body;
        if (requestinfo == undefined) {
            return renderError(res, "requestinfo can not be null", 400)
        }
        if (!tenantId) {
            return renderError(res, "tenantId is mandatory to generate the receipt", 400)
        }
        if (!projectNum) {
            return renderError(res, "projectId is mandatory to generate the receipt", 400)
        }
        try {
            try {
                resProject = await search_projectDetails(tenantId, requestinfo, projectNum);
            }
            catch (ex) {
                if (ex.response && ex.response.data) console.log(ex.response.data);
                return renderError(res, "Failed to query details of the project", 500);
            }
            var project = resProject.data;
            if (project && project.Projects && project.Projects.length > 0) {
                    var pdfResponse;
                    var pdfkey = config.pdf.project_details_template;
                    

                    try {
                        pdfResponse = await create_pdf(
                            tenantId,
                            pdfkey,
                            project,
                            requestinfo
                        )
                    }
                    catch (ex) {
                        console.log(ex)
                        if (ex.response && ex.response.data) console.log(ex.response.data);
                        return renderError(res, "Failed to generate PDF for project details", 500);
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
                        "There is no project created using this project number",
                        404
                    );
                }
            } catch (ex) {
                return renderError(res, "Failed to query details of the project", 500);
            }

        })

);

module.exports = router;