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
        // res.cookie('refresh_token', result.data.a)
        console.log(result)
        return res.send({
            access_token: result.data.access_token,
            scope: result.data.scope,
            expires_in: result.data.expires_in,
            token_type: result.data.token_type,
            id_token: result.data.id_token,
            refresh_token: result.data.refresh_token
        })
    }).catch(err => {
        console.log(err)
    })
    // res.render('index', { title: 'Express' });
});

router.post('/user/create', async function(req,res,next){
    console.log(process.env.DB_URI)
    await mongoose.connect(
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

router.get('/user/check/:email', async function(req, res, next) {
    console.log(process.env.DB_URI)
    await mongoose.connect(
        `${process.env.DB_URI}/bot_app`
    );

    const email = req.params.email;

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


router.post('/user/calender/add', async function(req, res, next) {
    console.log(process.env.DB_URI)
    return mongoose.connect(
        `${process.env.DB_URI}/bot_app`
    ).then(async result => {
        const name = req.body.name
        const email = req.body.email;
        const authorization = req.body.authorization; // Extract the access token from the Authorization header
        try {
            return userModel.findOne({ email: email }).then(async user => {
                if (user) {
                    const savedUser = await axios.get("http://localhost:3000/api/android/get-token?token="+authorization).then(token => {
                        return token;
                    })
                    console.log(savedUser)
                    return res.json({
                        access_token: savedUser.data.access_token,
                        refresh_token: savedUser.data.refresh_token
                    });
                } else {
                    // Create a new calendar for the user
                    const calendarData = {
                        summary: `BOT Calendar`
                    };
                    const savedUser = await axios.get("http://localhost:3000/api/android/get-token?token="+authorization).then(token => {
                        const accessToken = token.data.access_token
                        const response = axios.post(
                            "https://www.googleapis.com/calendar/v3/calendars",
                            calendarData,
                            {
                                headers: {
                                    Authorization: `Bearer ${accessToken}`,
                                },
                            }
                        ).then(calender => {
                            const calendarId = calender.data.id;
            
                            // Create a new user with the given email and calendarId
                            const newUser = new userModel({
                                name: name,
                                email: email,
                                calenderId: calendarId,
                            });
                            const savedUser = newUser.save();
                            return savedUser;
                        })
    
                        return token
                    })
                    return res.send({
                        access_token: savedUser.data.access_token,
                        refresh_token: savedUser.data.refresh_token
                    });
                }
            })
        } catch (error) {
            console.log(error);
            res.status(500).send(error);
        }
    })
});

router.post('/calender/event/create', function (req,res,next) {
    const accessToken = req.body.access_token;
    const eventData = {
    summary: req.body.summary,
    location: req.body.location,
    description: req.body.description,
    start: {
        // dateTime: new Date(req.body.startDateTime).toISOString(),
        dateTime: req.body.startDateTime,
        timeZone: 'America/New_York'
    },
    end: {
        dateTime: req.body.endDateTime,
        timeZone: 'America/New_York'
    },
    reminders: {
        useDefault: true
    }
    };

    // Set up the request options
    const config = {
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${accessToken}`
        }
    };

    mongoose.connect(
        `${process.env.DB_URI}/bot_app`
    ).then(async result => {
        const email = req.body.email;
        return userModel.findOne({ email: email }).then(async user => {
            if (user) {
                console.log(user.calenderId)
                const url = `https://www.googleapis.com/calendar/v3/calendars/${user.calenderId}/events`;
                axios.post(url, eventData, config).then(result => {
                    console.log(result)
                }).catch(err => {
                    console.log(err)
                })
            }
        }).catch(err => {
            console.log(err)
        })
    }).catch(err => {
        console.log(err)
    })

    return res.send("Event created succesfully")
})

router.post("/user/update/home", async function (req,res,next){
    const userEmail = req.body.email;
    const newHomeAddress = req.body.homeAddress;

    // Update the user's homeAddress field
    const update = await mongoose.connect(
        `${process.env.DB_URI}/bot_app`
        ).then(async result => {
            await userModel.findOneAndUpdate({ email: userEmail }, { homeAddress: newHomeAddress }, { new: true })
            .then(updatedUser => {
                console.log(`Successfully updated homeAddress for user with email ${userEmail}`);
                console.log(updatedUser);
                return true;
            })
            .catch(error => {
                console.error(`Error updating homeAddress for user with email ${userEmail}`);
                console.error(error);
                return false;
            });
    })

    if(update){
        return res.send("Update complete")
    }else{
        return res.send("Something went wrong")
    }
})


router.get("/user/home/check/:email", async function(req,res,next) {
    const userEmail = req.params.email;

    var address;

    const home = await mongoose.connect(
        `${process.env.DB_URI}/bot_app`
        ).then(async result => {
        return await userModel.findOne({ email: userEmail })
        .then(user => {
            if (user.homeAddress) {
            console.log(`The homeAddress for user with email ${userEmail} is ${user.homeAddress}`);
                return {
                    success:true,
                    address: user.homeAddress
                }
            } else {
            console.log(`The homeAddress is not present in the database for user with email ${userEmail}`);
                return {
                    success:false,
                }
            }
        })
        .catch(error => {
            console.error(`Error finding user with email ${userEmail}`);
            console.error(error);
            return {
                success:false,
            }
        });
    })

    if(home.success){
        return res.send({
            "success":true,
            "message":"Home address present",
            "address": home.address
        })
    }else{
        return res.send({
            "success":false,
            "message":"Home address not present",
            "address":"N/A"
        })
    }
})


module.exports = router;
