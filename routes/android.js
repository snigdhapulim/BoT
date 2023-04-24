var express = require('express');
const axios = require('axios')
const fs = require('fs/promises');
const cs = require('./client_secret.json')
const config = require('dotenv')
const mongoose = require("mongoose");
const userModel = require("../models/users")


mongoose.set("strictQuery", false);
const mongoDB = "mongodb://127.0.0.1/my_database";


var router = express.Router();

/* GET home page. */
router.get('/get-token', function(req, res, next) {
    const id_token = req.query.token
    cs['code'] = id_token
    axios.post('https://accounts.google.com/o/oauth2/token', {
        'code': cs.code,
        'client_id': cs.web.client_id,
        'client_secret': cs.web.client_secret,
        'redirect_uri': 'http://localhost:3000',
        'grant_type': 'authorization_code',
    }).then(result => {
        // console.log(result.data)
        return res.send({
            acces_token: result.data.access_token,
            scope: result.data.scope,
            expires_in: result.data.expires_in,
            token_type: result.data.token_type,
            id_token: result.data.id_token
        })
    }).catch(err => {
        console.log(err)
    })
    // res.render('index', { title: 'Express' });
});

router.post('/user/create', async function(req,res,next){
    console.log(process.env.DB_URI)
    mongoose.connect(
    `${process.env.DB_URI}/bot_app`
    );

    const user = new userModel(req.body);
    try {
      userData = await user.save().then(res => {
        return res
      }).catch(err => {
        console.log(err)
      })
      return res.send(userData)
    } catch (error) {
        console.log(error)
      res.status(500).send(error);
    }
})

router.post('/user/check', async function(req, res, next) {
    console.log(process.env.DB_URI)
    mongoose.connect(
        `${process.env.DB_URI}/bot_app`
    );

    const email = req.body.email;

    try {
        const user = await userModel.findOne({ email: email });
        if (user) {
            return res.send({ message: "User found" });
        } else {
            return res.send({ message: "User not found" });
        }
    } catch (error) {
        console.log(error)
        res.status(500).send(error);
    }
})


router.post('/user/addCalendarIfNewUser', async function(req, res, next) {
    console.log(process.env.DB_URI)
    mongoose.connect(
        `${process.env.DB_URI}/bot_app`
    );

    const email = req.body.email;
    const accessToken = req.headers.authorization.split(" ")[1]; // Extract the access token from the Authorization header

    try {
        const user = await userModel.findOne({ email: email });
        if (user) {
            return res.send({ email: user.email });
        } else {
            // Create a new calendar for the user
            const calendarData = {
                summary: `BOT Calendar`
            };
            const response = await axios.post(
                "https://www.googleapis.com/calendar/v3/calendars",
                calendarData,
                {
                    headers: {
                        Authorization: `Bearer ${accessToken}`,
                    },
                }
            );
            const calendarId = response.data.id;

            // Create a new user with the given email and calendarId
            const newUser = new userModel({
                email: email,
                calenderId: calendarId,
            });
            const savedUser = await newUser.save();

            return res.send(savedUser);
        }
    } catch (error) {
        console.log(error);
        res.status(500).send(error);
    }
});


module.exports = router;
