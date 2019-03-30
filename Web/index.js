var express = require('express');
index = express();
index.set("view engine", "ejs");

index.get("/", function(req, res) {
    console.log("run");
    res.render("index");
});

index.get("/revise", function(req, res){
    console.log("gotta do sometin");
})

index.listen(3000, function(){
    console.log("server running");
});