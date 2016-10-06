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
var createService = module.exports = exports = function (program, _dirname, connection, cback) {
    var entitys = [];
    var fileString = '';
    async.parallel([function (cb) {
        fs.readFile(_dirname + '/tpl/java/facade/service/LoginService.java', function (err, data) {
            fs.writeFile(_dirname + '/_tmp/' + program.name + '-facade' + '/src/main/java/' + program.groupid.replace(/\./g, "/") + '/' + program.name + '_facade/service/LoginService.java', Handlebars.compile(data.toString())(program), function (e) {
                cb(e);
            });
        });
    },function (cb) {
        fs.readFile(_dirname + '/tpl/java/service/service/LoginServiceImpl.java', function (err, data) {
            fs.writeFile(_dirname + '/_tmp/' + program.name + '-service' + '/src/main/java/' + program.groupid.replace(/\./g, "/") + '/' + program.name + '_service/service/LoginServiceImpl.java', Handlebars.compile(data.toString())(program), function (e) {
                cb(e);
            });
        });
    },function (cb) {
        fs.readFile(_dirname + '/tpl/java/service/token/RedisTokenManager.java', function (err, data) {
            fs.writeFile(_dirname + '/_tmp/' + program.name + '-service' + '/src/main/java/' + program.groupid.replace(/\./g, "/") + '/' + program.name + '_service/token/RedisTokenManager.java', Handlebars.compile(data.toString())(program), function (e) {
                cb(e);
            });
        });
    },function (cb) {
        fs.readFile(_dirname + '/tpl/java/service/token/TokenManager.java', function (err, data) {
            fs.writeFile(_dirname + '/_tmp/' + program.name + '-service' + '/src/main/java/' + program.groupid.replace(/\./g, "/") + '/' + program.name + '_service/token/TokenManager.java', Handlebars.compile(data.toString())(program), function (e) {
                cb(e);
            });
        });
    },function (cb) {
        fs.readFile(_dirname + '/tpl/java/service/token/TokenModel.java', function (err, data) {
            fs.writeFile(_dirname + '/_tmp/' + program.name + '-service' + '/src/main/java/' + program.groupid.replace(/\./g, "/") + '/' + program.name + '_service/token/TokenModel.java', Handlebars.compile(data.toString())(program), function (e) {
                cb(e);
            });
        });
    },function (cb) {
        fs.readFile(_dirname + '/tpl/java/facade/service/BaseService.java', function (err, data) {
            fs.writeFile(_dirname + '/_tmp/' + program.name + '-facade' + '/src/main/java/' + program.groupid.replace(/\./g, "/") + '/' + program.name + '_facade/service/BaseService.java', Handlebars.compile(data.toString())(program), function (e) {
                cb(e);
            });
        });
    },function (cb) {
        fs.readFile(_dirname + '/tpl/java/service/test/DubboProvider.java', function (err, data) {
            fs.writeFile(_dirname + '/_tmp/' + program.name + '-service' + '/src/test/java/' + program.groupid.replace(/\./g, "/") + '/' + program.name + '_service/DubboProvider.java', Handlebars.compile(data.toString())(program), function (e) {
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
                    fs.readFile(_dirname + '/tpl/java/facade/service/ServiceBase.java', function (err, data) {
                        async.eachSeries(entitys, function (item, callback) {
                            fs.writeFile(_dirname + '/_tmp/' + item.name + '-facade' + '/src/main/java/' + item.groupid.replace(/\./g, "/") + '/' + item.name + '_facade/service/' + tools.tf2(item.entity) + 'Service.java', Handlebars.compile(data.toString())(item), function (e) {
                                callback(e);
                            });
                        }, function (er) {
                            cb(er);
                        });

                    });
                }, function (cb) {
                    fs.readFile(_dirname + '/tpl/java/service/service/ServiceImplBase.java', function (err, data) {
                        async.eachSeries(entitys, function (item, callback) {
                            fs.writeFile(_dirname + '/_tmp/' + item.name + '-service' + '/src/main/java/' + item.groupid.replace(/\./g, "/") + '/' + item.name + '_service/service/' + tools.tf2(item.entity) + 'ServiceImpl.java', Handlebars.compile(data.toString())(item), function (e) {
                                callback(e);
                            });
                        }, function (er) {
                            cb(er);
                        });

                    });
                }], function (err, results) {
                    console.log('4.创建service:完成');
                    cback(err);
                });
                //console.log("err: " + err);

            });
        });
    });



};