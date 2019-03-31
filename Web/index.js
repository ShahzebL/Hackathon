var express = require('express');
const index = express();
const bodyParser = require("body-parser");
const fs = require("fs");
index.set("view engine", "ejs");
const { promisify } = require('util')

index.get("/", async function (req, res) {
    console.log("run");
    await res.render("index");

});

index.post("/revise", bodyParser(), async function (req, res) {
    const body = req.body.essay;

    let err = await promisify(fs.writeFile)('../in.txt', body)
    if (err) res.status(500).send("Error while writing to file.")

    // fancy java stuff to process. maybe use exec?
    // then respond back

    // await res.render("comments")
    res.status(200).send("Success. Now I should really do something good.")
    processText();
})

var processText = function() {
    var myClass = Java.type("JavaClasses.ReviseTester");
    myClass.main();
}

index.listen(3001, function () {
    console.log("server running");
});