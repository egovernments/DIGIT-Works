import * as express from 'express';
import { search_contract, search_estimate } from '../../api/index';
import { convertObjectForMeasurment, extractEstimateIds } from '../../utils/index';

class MeasurementController {
    public path = '/measurement';
    public router = express.Router();

    constructor() {
        this.intializeRoutes();
    }

    public intializeRoutes() {
        this.router.post(`${this.path}/_search`, this.getAllMeasurements);
    }

    getAllMeasurements = async (request: express.Request, response: express.Response) => {
        try {
            const requestBody = {
                "RequestInfo": request.body.RequestInfo,
            }
            var contractResponse = await search_contract(request.body.tenantId, request.body, request.body.contractNumber);
            const allEstimateIds = extractEstimateIds(contractResponse);
            const estimateIds = allEstimateIds.join(',');
            var estimateResponse = await search_estimate(request.body.tenantId, estimateIds, requestBody, estimateIds);
            const payload = {
                contracts: contractResponse.contracts,
                estimates: estimateResponse.estimates
            }
            const config = [
                {
                    "path": "contractNumber",
                    "jsonPath": "$.contracts[*].contractNumber",
                },
                {
                    "path": "estimateId",
                    "jsonPath": "$.contracts[*].lineItems[*].estimateId"
                },
                {
                    "path": "measurements",
                    "jsonPath": "$.contracts[*].lineItems[*].additionalDetails.measurement"
                }
            ]
            const finalResponse = convertObjectForMeasurment(payload, config);
            return response.json(finalResponse);
        } catch (e) {
            return response.status(500).json({ error: 'Internal Server Error' });
        }
    }
}

export default MeasurementController;