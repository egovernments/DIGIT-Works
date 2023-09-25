import * as express from 'express';
import { search_contract, search_estimate, search_mdms, search_measurement } from '../../api/index';
import { convertObjectForMeasurment, extractEstimateIds, filterEstimateDetails } from '../../utils/index';

class MeasurementController {
    public path = '/measurement';
    public router = express.Router();

    constructor() {
        this.intializeRoutes();
    }

    public intializeRoutes() {
        this.router.post(`${this.path}/_search`, this.getAllMeasurements);
    }

    // /measurement/_search
    getAllMeasurements = async (request: express.Request, response: express.Response) => {
        try {
            const tenantId = request.body.tenantId;
            const requestBody = {
                "RequestInfo": request.body.RequestInfo,
            }
            let measurements = null;
            if (request.body.measurementNumber) {
                const measurementsResponse = await search_measurement(requestBody, tenantId, request.body.measurementNumber);
                measurements = measurementsResponse.measurements;
            }
            const mdmsRespone = await search_mdms(tenantId.split(".")[0], "commonUiConfig", "MeasurementBFFConfig", requestBody);
            const config = mdmsRespone.MdmsRes.commonUiConfig.MeasurementBFFConfig;
            const contractResponse = await search_contract(tenantId, request.body, request.body.contractNumber);
            const allEstimateIds = extractEstimateIds(contractResponse);
            const estimateIds = allEstimateIds.join(',');
            const estimateResponse = await search_estimate(tenantId, estimateIds, requestBody, estimateIds);

            const filteredEstimates = filterEstimateDetails(estimateResponse.estimates[0].estimateDetails);
            const payload = {
                contracts: contractResponse.contracts,
                estimates: filteredEstimates,
                measurements: measurements
            }
            const finalResponse = convertObjectForMeasurment(payload, config);
            console.log(finalResponse);
            return response.json(payload);
        } catch (e) {
            return response.status(500).json({ error: 'Internal Server Error' });
        }
    }
}

export default MeasurementController;