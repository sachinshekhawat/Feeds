import {Router} from 'express';
import pkg from './middlewares.js';
const { contentDataCheckMiddleware, userInfoDataCheckMiddleware } = pkg;
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

router.post('/addContent',contentDataCheckMiddleware,(request,response)=>{
    const {body} = request;
    const contentModel = mongoose.model("contents",new mongoose.Schema({
        id:String,
        contentUrl:String,
        contentType:String,
        likes:Number,
        dislikes:Number,
        shares:Number,
        creationDate:String,
        tags:[
            String
        ],
        rightViewContent:String,
        leftViewContent:String
    }))
    let data = new contentModel({
        contentType:body.contentType,
        contentUrl:body.contentUrl,
        likes:body.likes,
        dislikes:body.dislikes,
        shares:body.shares,
        creationDate:body.creationDate,
        tags:body.tags,
        rightViewContent:body.rightViewContent,
        leftViewContent:body.leftViewContent
    })
    data.save()
    .then((res)=>{
        return response.status(200).send(res);
    })
    .catch((err)=>{
        return response.status(400).send({msg:"Something went wrong"});
    })
})

router.get('/addLike',(request,response)=>{
    const {params} = request;
    const contentId = params.id;
    const contentModel = mongoose.model("contents",new mongoose.Schema({
        id:String,
        contentUrl:String,
        contentType:String,
        likes:Number,
        dislikes:Number,
        shares:Number,
        creationDate:String,
        tags:[
            String
        ],
        rightViewContent:String,
        leftViewContent:String
    }))
    contentModel.updateOne(
        {id:contentId},
        {
            $inc:{
                likes:1
            }
        }
    ).then((res)=>{
        return response.status(200).send(res);
    })
    .catch((err)=>{
        return response.status(400).send({msg:"Something went wrong"});
    })
})

router.get('/addDisLike',(request,response)=>{
    const {params} = request;
    const contentId = params.id;
    const contentModel = mongoose.model("contents",new mongoose.Schema({
        contentUrl:String,
        contentType:String,
        likes:Number,
        dislikes:Number,
        shares:Number,
        creationDate:String,
        tags:[
            String
        ],
        rightViewContent:String,
        leftViewContent:String
    }))
    contentModel.updateOne(
        {id:contentId},
        {
            $inc:{
                dislikes:1
            }
        }
    ).then((res)=>{
        return response.status(200).send(res);
    })
    .catch((err)=>{
        return response.status(400).send({msg:"Something went wrong"});
    })
})

router.get('/addShare',(request,response)=>{
    const {params} = request;
    const contentId = params.id;
    const contentModel = mongoose.model("contents",new mongoose.Schema({
        contentUrl:String,
        contentType:String,
        likes:Number,
        dislikes:Number,
        shares:Number,
        creationDate:String,
        tags:[
            String
        ],
        rightViewContent:String,
        leftViewContent:String
    }))
    contentModel.updateOne(
        {id:contentId},
        {
            $inc:{
                shares:1
            }
        }
    ).then((res)=>{
        return response.status(200).send(res);
    })
    .catch((err)=>{
        return response.status(400).send({msg:"Something went wrong"});
    })
})

export default router;