const express = require('express');
const os = require('os');
const getIpAdd = require('./get-ip-address')

const app = express();
const port = 3000;

const pad = (number, length) => {
    number = `${number}`
    
    while (number.length < length) {
        number = `0${number}`
    }

    return number;
}

app.get('/newId/:namespace', (req, res) => {
    const namespace = req.params.namespace;

    const randomNumber1 = pad(Math.floor(Math.random() * 1000).toString(36), 2);
    const randomNumber2 = pad(Math.floor(Math.random() * 1000).toString(36), 2);
    const randomNumber3 = pad(Math.floor(Math.random() * 1000).toString(36), 2);
    const randomNumber4 = pad(Math.floor(Math.random() * 1000).toString(36), 2);
    
    const timestamp = (new Date().getTime()).toString(36);
    //const hostname = os.hostname().replace(/-/g, '');
    const ipAddress = parseInt(getIpAdd().split('.').map(part => pad(part, 3)).join('') || 0).toString(36);
    const pid = process.pid.toString(36);
    
    const uniqueId = `${randomNumber1}${timestamp}${randomNumber2}${ipAddress}${randomNumber3}${pid}${randomNumber4}`
    
    res.send(`${namespace}-${uniqueId}`);
})

app.listen(port, () => console.log(`Example app listening on port ${port}!`));