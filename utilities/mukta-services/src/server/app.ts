import express, { Request, Response } from "express";

const app = express()

app.get('/', (req: Request, res: Response) => res.send('Hello d W   orld from app.ts!'))

export default app;
