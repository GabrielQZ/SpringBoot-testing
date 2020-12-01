const router = require('express').Router();

router.post('/', (req, res) => {

  console.log(req.body.routing());

  res.json({
    message: "user created"
  })
})

module.exports = router;