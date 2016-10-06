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
var createDubbo = module.exports = exports = function (program, _dirname, connection,cback) {
    var da = {
        groupid: program.groupid,
        name: program.name,
        entitys: []
    };
    connection.query('SELECT TABLE_NAME,TABLE_COMMENT FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = "' + program.mysql_database + '"', function (err, rows, fields) {
        if (err) {
            console.error(err);
        }
        rows.forEach(function (item) {
            var entity = {
                groupid:program.groupid,
                name:program.name,
                entity: item['TABLE_NAME'],
                classname: tools.tf2(item['TABLE_NAME'])
            }
            da.entitys.push(entity);
        });
        async.parallel([function (cb) {
            fs.readFile(_dirname + '/tpl/resources/api/conf/dubbo-consumer.xml', function (err, data) {
                fs.writeFile(_dirname + '/_tmp/' + program.name + '-api/src/main/resources/conf/dubbo-consumer.xml', Handlebars.compile(data.toString())(da), function (e) {
                    cb(e);
                });
            });
        },function (cb) {
            fs.readFile(_dirname + '/tpl/resources/service/conf/dubbo-provider.xml', function (err, data) {
                fs.writeFile(_dirname + '/_tmp/' + program.name + '-service/src/main/resources/conf/dubbo-provider.xml', Handlebars.compile(data.toString())(da), function (e) {
                    cb(e);
                });
            });
        }], function (err, results) {
            console.log('7.创建dubbo文件:完成');
            cback(err);
        });


    });

};