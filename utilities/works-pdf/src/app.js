var createError = require("http-errors");
var express = require("express");
var path = require("path");
var cookieParser = require("cookie-parser");
var logger = require("morgan");
var config = require("./config");
var projectsRouter=require("./routes/projects");
var estimateRouter=require("./routes/estimate");
var musterRollRouter = require("./routes/musterRolls");
var workOrderRouter = require("./routes/workOrder");
var groupBills = require("./routes/groupBill");

var {listenConsumer} = require("./consumer")



var app = express();
app.disable('x-powered-by');

// view engine setup
app.set("views", path.join(__dirname, "views"));
app.set("view engine", "jade");

app.use(logger("dev"));
app.use(express.json());
app.use(express.urlencoded({ extended: false }));
app.use(cookieParser());
app.use(express.static(path.join(__dirname, "public")));

app.use(config.app.contextPath + "/download/project", projectsRouter);
app.use(config.app.contextPath + "/download/estimate", estimateRouter);
app.use(config.app.contextPath + "/download/musterRoll", musterRollRouter);
app.use(config.app.contextPath + "/download/workOrder", workOrderRouter);
app.use(config.app.contextPath + "/bill", groupBills);


// catch 404 and forward to error handler
app.use(function (req, res, next) {
  next(createError(404));
});

// error handler
app.use(function (err, req, res, next) {
  // set locals, only providing error in development
  res.locals.message = err.message;
  res.locals.error = req.app.get("env") === "development" ? err : {};

  // render the error page
  res.status(err.status || 500);
  res.render("error");
});
listenConsumer();
module.exports = app;
