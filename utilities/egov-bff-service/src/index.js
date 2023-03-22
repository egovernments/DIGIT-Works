var app = require("./app");
var config = require("./server/config");

app.listen(config.app.port);