import * as express from 'express';
import { search_contract, search_estimate, search_mdms, search_measurement } from '../../api/index';
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

    // Request for /measurement/_search
    getAllMeasurements = async (request: express.Request, response: express.Response) => {
        try {
            const { tenantId, RequestInfo, contractNumber, measurementNumber } = request.body;

            // Define the request body for MDMS
            const mdmsRequestBody = {
                "RequestInfo": RequestInfo,
            };

            // Search for MDMS data
            const mdmsResponse = await search_mdms(tenantId.split(".")[0], "commonUiConfig", "MeasurementBFFConfig", mdmsRequestBody);
            const config = mdmsResponse.MdmsRes.commonUiConfig.MeasurementBFFConfig;

            // Define an array of promises for parallel execution
            const promises = [
                search_contract(tenantId, request.body, contractNumber),
                search_measurement(mdmsRequestBody, tenantId, null, contractNumber),
            ];

            if (measurementNumber) {
                // Add measurement search promise if measurementNumber is provided
                promises.push(search_measurement(mdmsRequestBody, tenantId, measurementNumber, null));
            }

            // Execute promises in parallel
            const [contractResponse, measurementResponse, uniqueMeasurementResponse] = await Promise.all(promises);

            if (contractResponse !== null) {
                // Extract estimate IDs from the contract response
                const allEstimateIds = extractEstimateIds(contractResponse);
                const estimateIds = allEstimateIds.join(',');

                const estimateResponse = await search_estimate(tenantId, estimateIds, mdmsRequestBody, estimateIds);

                // Prepare the payload based on the responses
                const payload = {
                    contract: contractResponse?.contracts?.[0],
                    estimate: estimateResponse?.estimates?.[0],
                    allMeasurements: measurementResponse?.measurements,
                    measurement: uniqueMeasurementResponse?.measurements?.[0] || [],
                };

                // Convert the payload according to the configuration
                const finalResponse = convertObjectForMeasurment(payload, config);

                // Send the final response
                return response.json(finalResponse);
            } else {
                // Handle the case where contractResponse is null
                return response.status(404).json({ error: 'Contract not found' });
            }
        } catch (e) {
            // Handle errors
            console.error(e);
            return response.status(500).json({ error: 'Internal Server Error' });
        }

    }
}

export default MeasurementController;