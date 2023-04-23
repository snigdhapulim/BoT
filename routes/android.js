var express = require('express');
const axios = require('axios')
const fs = require('fs/promises');
const cs = require('./client_secret.json')

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
            acces_token: result.data.acces_token,
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

module.exports = router;
