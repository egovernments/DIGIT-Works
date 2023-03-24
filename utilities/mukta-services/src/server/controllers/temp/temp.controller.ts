import * as express from 'express';
import Temp from './temp';
 
class TempsController {
  public path = '/temp';
  public router = express.Router();
 
  private posts: Temp[] = [
    {
      author: 'jagan',
      content: 'HI',
      title: 'MR sasasasasas',
    }
  ];
 
  constructor() {
    this.intializeRoutes();
  }
 
  public intializeRoutes() {
    this.router.get(this.path, this.getAllPosts);
    this.router.post(this.path, this.createAPost);
  }
 
  getAllPosts = (request: express.Request, response: express.Response) => {
    response.send(this.posts);
  }
 
  createAPost = (request: express.Request, response: express.Response) => {
    const post: Temp = request.body;
    this.posts.push(post);
    response.send(post);
  }
}
 
export default TempsController;
// https://wanago.io/2018/12/03/typescript-express-tutorial-routing-controllers-middleware/
// to do doc