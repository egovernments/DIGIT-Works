import {logout} from "./logout";
import useSearchEstimate from "./useSearchEstimate";
import useViewProjectDetails from "./useViewProjectDetails";

const UserService={
    logout,
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
