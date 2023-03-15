import {logoutV1} from "./logout";
import useSearchEstimate from "./useSearchEstimate";
import useViewProjectDetails from "./useViewProjectDetails";

const UserService={
    logoutV1,
}

const works = {
    useViewProjectDetails,
    useSearchEstimate
}

const Hooks ={
    attendance:{
        update:()=>console.log("Hi")
    },
    works
}


export const CustomisedHooks ={
   Hooks,
   UserService
}
