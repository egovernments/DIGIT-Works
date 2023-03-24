import { Request, Response, Router } from 'express';

const usersRouter = Router();

usersRouter.get('/', (request:Request, response:Response):any => {
  return response.json("OK");
});

export default usersRouter;