import {logout} from "./logout";
import useSearchEstimate from "./useSearchEstimate";
import useViewProjectDetails from "./useViewProjectDetails";
import useViewContractDetails from "./contracts/useViewContractDetails";

const UserService={
    logout,
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
        update:()=>console.log("Hi")
    },
    works,
    contracts
}


export const CustomisedHooks ={
   Hooks,
   UserService
}
