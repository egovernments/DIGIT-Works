import MeasurementController from "./measurement/measurement.controller";
import PostsController from "./posts/posts.controller";
import TempsController from "./temp/temp.controller";
import BulkUploadController from "./uploadSheet/uploadSheet.controller";
import { listener } from './uploadSheet/Consumer';

// Call the listener function to start consuming messages
listener();




const controllers = [
  new PostsController(),
  new TempsController(),
  new MeasurementController(),
  new BulkUploadController()
]

export default controllers;