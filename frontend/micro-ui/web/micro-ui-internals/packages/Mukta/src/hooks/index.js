import {logout} from "./logout";
import useViewProjectDetails from "./useViewProjectDetails";

const UserService={
    logout,
}

const works = {
    useViewProjectDetails
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
