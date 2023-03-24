import app from "./app";
import compression from "compression";
import helmet from "helmet";

app.use(helmet()); // set well-known security-related HTTP headers
app.use(compression());

app.disable("x-powered-by");
/**
 * Normalize a port into a number, string, or false.
 */

const normalizePort=(val:any)=> {
    let port = parseInt(val, 10);
  
    if (isNaN(port)) {
      // named pipe
      return val;
    }
  
    if (port >= 0) {
      // port number
      return port;
    }
  
    return false;
  }

var port = normalizePort(process.env.APP_PORT || '8080');
const server = app.listen(port, () =>
    console.log("Starting ExpressJS ee server on Port 3000"));

export default server;
