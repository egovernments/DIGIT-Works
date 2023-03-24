import PostsController from "./posts/posts.controller";
import TempsController from "./temp/temp.controller";




const controllers= [
    new PostsController(),
    new TempsController()
  ]

  export default controllers;