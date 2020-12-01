const router = require('express').Router();

router.post('/', (req, res) => {

  res.json({
    message: "user created"
  })
})

router.put("/login", (req, res) => {
  res.json(req.body);
})

router.get('/id/:id', (req, res) => {
  res.json({
    user: "ID:" + req.params.id
  })
})


router.delete('/id/:id', (req, res) => {
  res.json({
    user: "ID:" + req.params.id
  })
})
module.exports = router;