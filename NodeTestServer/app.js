
const express = require('express');
const morgan = require('morgan');

const app = express();

const authRouter = require('./routes/authsys')
const authPort = process.env.AUTHPORT;

app.use(morgan('dev'));
app.use(express.json());
app.use(authRouter)

app.listen(authPort);
