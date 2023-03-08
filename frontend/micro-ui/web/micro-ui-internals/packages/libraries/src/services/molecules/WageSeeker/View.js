import { WageSeekerService } from "../../elements/WageSeeker";

const transformViewDataToApplicationDetails = (t, response) => {
    return {}
}

export const View = {
    fetchWageSeekerDetails: async (t, tenantId, data, searchParams) => {
        console.log('params', {t, tenantId, data, searchParams});
        return {}
        try {
            const response = await WageSeekerService.search(tenantId, data, searchParams);
            console.log('response', response);
            return transformViewDataToApplicationDetails(t, response)
        } catch (error) {
            console.log('error', error);
            throw new Error(error?.response?.data?.Errors[0].message);
        }
    }
}