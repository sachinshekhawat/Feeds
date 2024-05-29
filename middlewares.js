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
    }
}