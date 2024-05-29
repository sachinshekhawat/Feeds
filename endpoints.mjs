import {Router} from 'express';
import { userInfoDataCheckMiddleware } from './middlewares.js';
import mongoose from './index.mjs';

const router = Router();

router.post('/addUser',userInfoDataCheckMiddleware,(request,response)=>{
    const {body} =request;
    const userModel = mongoose.model("users",new mongoose.Schema({
        name:String,
        email:String,
        password:String,
        dateOfJoin:String
    }));
    let data = new userModel({
        name:body.name,
        email:body.email,
        password:body.password,
        dateOfJoin:body.dateOfJoin
    })
    data.save()
    .then((res)=>{
        return response.status(200).send(res);
    })
    .catch((err)=>{
        return response.status(400).send({msg:"Error while resgistering"});
    })
})

export default router;