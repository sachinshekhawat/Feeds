module.exports={
    userInfoDataCheckMiddleware:function(request,response,next){
        const {body} = request;
        if(!body){
            return response.status(400).send({msg:"Data body is required containg fields name, email, password and dateOfJoin"});
        }else{
            const name = body.name;
            const email = body.email;
            const password = body.password;
            const dateOfJoin = body.dateOfJoin;
            if(!name){
                return response.status(400).send({msg:"name field is required"});
            }
            else if(!((typeof name=="string") && !(name===""))){
                return response.status(400).send({msg:"name field is invalid type or empty"});
            }
            if(!email){
                return response.status(400).send({msg:"email field is required"});
            }
            else if(!((typeof email=="string") && !(email===""))){
                return response.status(400).send({msg:"email field is invalid type or empty"});
            }
            if(!password){
                return response.status(400).send({msg:"password field is required"});
            }
            else if(!((typeof password=="string") && !(password===""))){
                return response.status(400).send({msg:"password field is invalid type or empty"});
            }
            if(!dateOfJoin){
                return response.status(400).send({msg:"dateOfJoin field is required"});
            }
            else if(!((typeof dateOfJoin=="string") && !(dateOfJoin===""))){
                return response.status(400).send({msg:"dateOfJoin field is invalid type or empty"});
            }
        }
        next();
    },

    contentDataCheckMiddleware:function(request,response,next){
        const {body} = request;
        if(!body){
            return response.status(400).send({msg:"Data body is required containg fields contentType, contentUrl, creationDate, creatorId, likes, dislikes, shares, rightViewContent, leftViewContent and tags"});
        }else{
            const contentUrl = body.contentUrl;
            const contentType = body.contentType;
            const creationDate = body.creationDate;
            const creatorId = body.creatorId;
            const likes = body.likes;
            const dislikes = body.dislikes;
            const shares = body.shares;
            const tags = body.tags;
            const rightViewContent = body.rightViewContent;
            const leftViewContent = body.leftViewContent;
            if(!contentUrl){
                return response.status(400).send({msg:"contentUrl field is required"});
            }
            else if(!((typeof contentUrl=="string") && !(contentUrl===""))){
                return response.status(400).send({msg:"contentUrl field is invalid type or empty"});
            }
            if(!contentType){
                return response.status(400).send({msg:"contentType field is required"});
            }
            else if(!((typeof contentType=="string") && !(contentType===""))){
                return response.status(400).send({msg:"contentType field is invalid type or empty"});
            }
            if(!creationDate){
                return response.status(400).send({msg:"creationDate field is required"});
            }
            else if(!((typeof creationDate=="string") && !(creationDate===""))){
                return response.status(400).send({msg:"creationDate field is invalid type or empty"});
            }
            if(!creatorId){
                return response.status(400).send({msg:"creatorId field is required"});
            }
            else if(!((typeof creatorId=="string") && !(creatorId===""))){
                return response.status(400).send({msg:"creatorId field is invalid type or empty"});
            }
            if(!((typeof likes=="number"))){
                return response.status(400).send({msg:"likes field is invalid type or empty"});
            }
            if(!((typeof dislikes=="number"))){
                return response.status(400).send({msg:"dislikes field is invalid type or empty"});
            }
            if(!((typeof shares=="number"))){
                return response.status(400).send({msg:"shares field is invalid type or empty"});
            }
            if(!(tags instanceof Array) || tags.length==0){
                return response.status(400).send({msg:"tags field is invalid type or empty"});
            }
            if(rightViewContent){
                if(!((typeof rightViewContent=="string") && !(rightViewContent===""))){
                    return response.status(400).send({msg:"rightViewContent field is invalid type or empty"});
                }
            }
            if(leftViewContent){
                if(!((typeof leftViewContent=="string") && !(leftViewContent===""))){
                    return response.status(400).send({msg:"leftViewContent field is invalid type or empty"});
                }
            }
        }
        next();
    }

}