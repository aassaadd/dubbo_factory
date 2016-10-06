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
var createModel = module.exports = exports = function (program, _dirname, connection,cback) {
    var entitys = [];
    var fileString = '';
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

            //console.log("err: " + err);
            fs.readFile(_dirname + '/tpl/java/facade/model/BeanBase.java', function (err, data) {
                async.eachSeries(entitys, function (item, callback) {
                    fs.writeFile(_dirname + '/_tmp/' + item.name + '-facade' + '/src/main/java/' + item.groupid.replace(/\./g, "/") + '/' + item.name + '_facade/model/' + tools.tf2(item.entity) + '.java', Handlebars.compile(data.toString())(item), function (e) {
                        callback(e);
                    });
                }, function (er) {
                    console.log('3.创建model:完成');
                    cback(er);
                });

            });


        });
    });

};