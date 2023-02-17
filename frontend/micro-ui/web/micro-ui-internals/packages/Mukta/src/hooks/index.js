import {logout} from "./logout";

const UserService={
    logout
}

const Hooks ={
    attendance:{
        update:()=>console.log("Hi")
    }
}


export const CustomisedHooks ={
   Hooks,
   UserService
}
