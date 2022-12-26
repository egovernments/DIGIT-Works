import { fetchOrganisationDetails } from "../../services/molecules/Expenditure/Organisation/viewOrganisation";

const useViewOrg = () => {
  return fetchOrganisationDetails();
};

export default useViewOrg;
