/**
 * Created by zhaohaochen on 16/8/17.
 */
var async = require('async'),
    fs = require('fs'),
    Handlebars = require('handlebars'),
    tools=require('./tools');
/**
 *
 * @type {exports}
 */
var createApi = module.exports = exports = function (program, _dirname, connection, cback) {
    var entitys = [];
    var fileString = '';

    async.parallel([function (cb) {
        fs.readFile(_dirname + '/tpl/java/api/restful/BaseController.java', function (err, data) {
            fs.writeFile(_dirname + '/_tmp/' + program.name + '-api' + '/src/main/java/' + program.groupid.replace(/\./g, "/") + '/' + program.name + '_api/restful/BaseController.java', Handlebars.compile(data.toString())(program), function (e) {
                cb(e);
            });

        });
    }, function (cb) {
        fs.readFile(_dirname + '/tpl/java/api/restful/LoginCotroller.java', function (err, data) {
            fs.writeFile(_dirname + '/_tmp/' + program.name + '-api' + '/src/main/java/' + program.groupid.replace(/\./g, "/") + '/' + program.name + '_api/restful/LoginCotroller.java', Handlebars.compile(data.toString())(program), function (e) {
                cb(e);
            });

        });
    }], function (err, results) {
        connection.query('SELECT TABLE_NAME,TABLE_COMMENT FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = "' + program.mysql_database + '"', function (err, rows, fields) {
            if (err) {
                console.error(err);
            }
            async.eachSeries(rows, function (item, callback) {
                var entity = {
                    groupid: program.groupid,
                    name: program.name,
                    entity: item['TABLE_NAME'],
                    classname: tools.tf2(item['TABLE_NAME']),
                    comments: []
                }
                connection.query('SHOW FULL COLUMNS FROM ' + item['TABLE_NAME'], function (er, rs, fields) {
                    if (er) {
                        console.error(er);
                    }
                    entity.comments = rs;
                    entitys.push(entity);
                    callback(err, entity);
                });

            }, function (err) {
                async.parallel([function (cb) {
                    fs.readFile(_dirname + '/tpl/java/api/common/System.java', function (err, data) {
                        fs.writeFile(_dirname + '/_tmp/' + program.name + '-api' + '/src/main/java/' + program.groupid.replace(/\./g, "/") + '/' + program.name + '_api/common/' + tools.tf2(program.name) + 'System.java', Handlebars.compile(data.toString())(program), function (e) {
                            cb(e);
                        });
                    });
                }, function (cb) {
                    fs.readFile(_dirname + '/tpl/java/api/filter/LoginInterceptor.java', function (err, data) {
                        fs.writeFile(_dirname + '/_tmp/' + program.name + '-api' + '/src/main/java/' + program.groupid.replace(/\./g, "/") + '/' + program.name + '_api/filter/' + 'LoginInterceptor.java', Handlebars.compile(data.toString())(program), function (e) {
                            cb(e);
                        });
                    });
                }, function (cb) {
                    fs.readFile(_dirname + '/tpl/java/api/filter/SimpleCORSFilter.java', function (err, data) {
                        fs.writeFile(_dirname + '/_tmp/' + program.name + '-api' + '/src/main/java/' + program.groupid.replace(/\./g, "/") + '/' + program.name + '_api/filter/' + 'SimpleCORSFilter.java', Handlebars.compile(data.toString())(program), function (e) {
                            cb(e);
                        });
                    });
                }, function (cb) {
                    fs.readFile(_dirname + '/tpl/java/api/jetty/Main.java', function (err, data) {
                        fs.writeFile(_dirname + '/_tmp/' + program.name + '-api' + '/src/main/java/' + program.groupid.replace(/\./g, "/") + '/' + program.name + '_api/jetty/' + 'Main.java', Handlebars.compile(data.toString())(program), function (e) {
                            cb(e);
                        });
                    });
                }, function (cb) {
                    fs.readFile(_dirname + '/tpl/java/api/restful/ControllerBase.java', function (err, data) {
                        async.eachSeries(entitys, function (item, callback) {
                            fs.writeFile(_dirname + '/_tmp/' + item.name + '-api' + '/src/main/java/' + item.groupid.replace(/\./g, "/") + '/' + item.name + '_api/restful/' + tools.tf2(item.entity) + 'Controller.java', Handlebars.compile(data.toString())(item), function (e) {
                                callback(e);
                            });
                        }, function (er) {
                            cb(er);
                        });

                    });
                }], function (err, results) {
                    console.log('5.创建api:完成');
                    cback(err);
                });


                //console.log("err: " + err);


            });
        });
    });


};