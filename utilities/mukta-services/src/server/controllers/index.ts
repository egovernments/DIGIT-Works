import MeasurementController from "./measurement/measurement.controller";
import MusterRollController from "./musterRoll/musterRoll.controller";
import PostsController from "./posts/posts.controller";
import TempsController from "./temp/temp.controller";




const controllers = [
  new PostsController(),
  new TempsController(),
  new MeasurementController(),
  new MusterRollController()
]

export default controllers;