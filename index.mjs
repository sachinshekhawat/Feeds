import express from 'express'
import endpointsRoute from './endpoints.mjs';
//import mongoose from 'mongoose';
import pg from 'pg'

const app = express()
app.use(express.json());
app.use(endpointsRoute);
const PORT = process.env.PORT || 8000
const Pool = pg.Pool;

app.listen(PORT,(error)=>{
    if(!error){
        console.log("Running on port "+PORT);
    }else{
        console.log("Error while listing to port\n"+error);
    }
})

function setUpDB(){

    // await mongoose.connect("mongodb+srv://SatwikMohan:Captain47.@cluster0.mrfxxzw.mongodb.net/DatabaseTamarind")
    // .then(()=>{
    //     console.log("Connected to Database");
    // })
    // .catch((err)=>{
    //     console.log(err);
    // })

    return new Pool({
        user:"postgres",
        host:"localhost",
        database:"tamarind",
        password:"Captain47.",
        port:4000
    });

}

let pool = setUpDB()

export default pool;
