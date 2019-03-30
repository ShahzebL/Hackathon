var express = require('express');
index = express();
index.set("view engine", "ejs");

index.get("/", function(req, res) {
    res.render("index");
});

index.listen(3000, function(){
    console.log("server running");
});