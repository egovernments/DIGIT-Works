import {logoutV1} from "./logout";
import useSearchEstimate from "./useSearchEstimate";
import useViewProjectDetails from "./useViewProjectDetails";
import useViewContractDetails from "./contracts/useViewContractDetails";

const UserService={
    logoutV1,
}

const works = {
    useViewProjectDetails,
    useSearchEstimate
}

const contracts = {
    useViewContractDetails
}

const Hooks ={
    attendance:{
        update:()=>{}
    },
    works,
    contracts
}


export const CustomisedHooks ={
   Hooks,
   UserService
}
