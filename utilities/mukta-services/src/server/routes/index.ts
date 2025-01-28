import muster from "./muster";
import usersRouter from "./user";



const routeConfig =[
    {
        path:"/muster",router:muster
    },
    {
        path :"/user",router :usersRouter
    }
]


export default routeConfig;