import {logout} from "./logout";
import useViewProjectDetailsInEstimate from "./useViewProjectDetailsInEstimate";

const UserService={
    logout
}

const Hooks ={
    attendance:{
        update:()=>console.log("Hi")
    },
    project : {
        useViewProjectDetailsInEstimate
    }

}


export const CustomisedHooks ={
   Hooks,
   UserService
}
