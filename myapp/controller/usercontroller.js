const express = require("express");
const userModel = require("../models/user");
const app = express();

app.post("/add_user", async (request, response) => {
  
    try {
        const user = new userModel(request.body);
        console.log("Entrfghj")
      await user.save();
      response.send(user);
    } catch (error) {
      response.status(500).send(error);
    }
});


module.exports = app;