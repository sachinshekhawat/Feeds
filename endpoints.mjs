import {Router} from 'express';
import pkg from './middlewares.js';
const { contentDataCheckMiddleware, userInfoDataCheckMiddleware } = pkg;
import pool from './index.mjs';

const router = Router();

router.post('/addUser',userInfoDataCheckMiddleware,(request,response)=>{
    const {body} =request;
    // const userModel = mongoose.model("users",new mongoose.Schema({
    //     name:String,
    //     email:String,
    //     password:String,
    //     dateOfJoin:String
    // }));
    // let data = new userModel({
    //     name:body.name,
    //     email:body.email,
    //     password:body.password,
    //     dateOfJoin:body.dateOfJoin
    // })
    // data.save()
    // .then((res)=>{
    //     return response.status(200).send(res);
    // })
    // .catch((err)=>{
    //     return response.status(400).send({msg:"Error while resgistering"});
    // })

    const {name,email,password,dateOfJoin} = body;

    pool.query("SELECT * FROM users user WHERE user.email = $1",[email],(err,result)=>{//check Email Exists
        if(result){
            if(result["rows"].length){
                return response.status(400).send({msg:"Email already exists"});
            }
        }
        pool.query("INSERT INTO users (name,email,password,dateOfJoin) VALUES ($1,$2,$3,$4)",[name,email,password,dateOfJoin],(err,result)=>{
            if(err) throw err;
            response.status(200).send({msg:"User Data Added"});
        })  
    })

})

router.post('/addContent',contentDataCheckMiddleware,(request,response)=>{
    const {body} = request;
    // const contentModel = mongoose.model("contents",new mongoose.Schema({
    //     id:String,
    //     contentUrl:String,
    //     contentType:String,
    //     likes:Number,
    //     dislikes:Number,
    //     shares:Number,
    //     creationDate:String,
    //     tags:[
    //         String
    //     ],
    //     rightViewContent:String,
    //     leftViewContent:String
    // }))
    // let data = new contentModel({
    //     contentType:body.contentType,
    //     contentUrl:body.contentUrl,
    //     likes:body.likes,
    //     dislikes:body.dislikes,
    //     shares:body.shares,
    //     creationDate:body.creationDate,
    //     tags:body.tags,
    //     rightViewContent:body.rightViewContent,
    //     leftViewContent:body.leftViewContent
    // })
    // data.save()
    // .then((res)=>{
    //     return response.status(200).send(res);
    // })
    // .catch((err)=>{
    //     return response.status(400).send({msg:"Something went wrong"});
    // })
    const {
        contentUrl,
        contentType,
        creatorId,
        likes,
        dislikes,
        shares,
        creationDate,
        tags
    } = body;

    let rightViewContent = "null";
    let leftViewContent = "null";

    if(body.rightViewContent){
        rightViewContent = body.rightViewContent;
    }
    if(body.leftViewContent){
        leftViewContent = body.leftViewContent;
    }

    pool.query("INSERT INTO contents (contenturl,contenttype,likes,dislikes,shares,creationdate,rightviewcontent,leftviewcontent,tags,creatorid) VALUES ($1,$2,$3,$4,$5,$6,$7,$8,$9,$10)",
        [contentUrl,contentType,likes,dislikes,shares,creationDate,rightViewContent,leftViewContent,tags,creatorId],(err,result)=>{
        if(err) throw err;
        response.status(200).send({msg:"Content Data Added"});
    })

})

router.get('/getUsers',(request,response)=>{
    pool.query('SELECT * FROM users',(err,result)=>{
        if(err) throw err;
        if(result){
            response.status(200).send(result["rows"]);
        }
    })
})

router.get('/getContents',(request,response)=>{
    pool.query('SELECT * FROM contents',(err,result)=>{
        if(err) throw err;
        if(result){
            response.status(200).send(result["rows"]);
        }
    })
})

router.get('/addLike',(request,response)=>{
    const {params} = request;
    const contentId = params.id;
    // const contentModel = mongoose.model("contents",new mongoose.Schema({
    //     id:String,
    //     contentUrl:String,
    //     contentType:String,
    //     likes:Number,
    //     dislikes:Number,
    //     shares:Number,
    //     creationDate:String,
    //     tags:[
    //         String
    //     ],
    //     rightViewContent:String,
    //     leftViewContent:String
    // }))
    // contentModel.updateOne(
    //     {id:contentId},
    //     {
    //         $inc:{
    //             likes:1
    //         }
    //     }
    // ).then((res)=>{
    //     return response.status(200).send(res);
    // })
    // .catch((err)=>{
    //     return response.status(400).send({msg:"Something went wrong"});
    // })
})

router.get('/addDisLike',(request,response)=>{
    const {params} = request;
    const contentId = params.id;
    // const contentModel = mongoose.model("contents",new mongoose.Schema({
    //     contentUrl:String,
    //     contentType:String,
    //     likes:Number,
    //     dislikes:Number,
    //     shares:Number,
    //     creationDate:String,
    //     tags:[
    //         String
    //     ],
    //     rightViewContent:String,
    //     leftViewContent:String
    // }))
    // contentModel.updateOne(
    //     {id:contentId},
    //     {
    //         $inc:{
    //             dislikes:1
    //         }
    //     }
    // ).then((res)=>{
    //     return response.status(200).send(res);
    // })
    // .catch((err)=>{
    //     return response.status(400).send({msg:"Something went wrong"});
    // })
})

router.get('/addShare',(request,response)=>{
    const {params} = request;
    const contentId = params.id;
    // const contentModel = mongoose.model("contents",new mongoose.Schema({
    //     contentUrl:String,
    //     contentType:String,
    //     likes:Number,
    //     dislikes:Number,
    //     shares:Number,
    //     creationDate:String,
    //     tags:[
    //         String
    //     ],
    //     rightViewContent:String,
    //     leftViewContent:String
    // }))
    // contentModel.updateOne(
    //     {id:contentId},
    //     {
    //         $inc:{
    //             shares:1
    //         }
    //     }
    // ).then((res)=>{
    //     return response.status(200).send(res);
    // })
    // .catch((err)=>{
    //     return response.status(400).send({msg:"Something went wrong"});
    // })
})

export default router;