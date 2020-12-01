
const express = require('express');
const morgan = require('morgan');

const app = express();

app.use(morgan('dev'));
app.use(express.json());

app.post('/', (req, res) => {

  console.log(req.body);

  res.json({
    message: "user created"
  })
})


app.listen(2000);
