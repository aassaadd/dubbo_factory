/**
 * Created by zhaohaochen on 16/8/17.
 */
var async = require('async'),
    fs = require('fs'),
    Handlebars = require('handlebars'),
    tools = require('./tools');
/**
 *
 * @type {exports}
 */
var createDoc = module.exports = exports = function (program, _dirname, connection, cback) {
    var entitys = [];
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
                comment: item['TABLE_COMMENT'],
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
                fs.readFile(_dirname + '/tpl/doc/_apidoc.js', function (err, data) {
                    fs.writeFile(_dirname + '/_doc/_apidoc.js', Handlebars.compile(data.toString())(program), function (e) {
                        cb(e);
                    });
                });
            }, function (cb) {
                fs.readFile(_dirname + '/tpl/doc/apidoc.json', function (err, data) {
                    fs.writeFile(_dirname + '/_doc/apidoc.json', Handlebars.compile(data.toString())(program), function (e) {
                        cb(e);
                    });
                });
            }, function (cb) {
                fs.readFile(_dirname + '/tpl/doc/example.js', function (err, data) {
                    fs.writeFile(_dirname + '/_doc/example.js', Handlebars.compile(data.toString())({
                        entitys: entitys
                    }), function (e) {
                        var spawn = require('child_process').spawn,
                            free = spawn('node', [_dirname + '/node_modules/apidoc/bin/apidoc','-i',_dirname + '/_doc/','-o',_dirname + '/_doc/output']);
                        free.stderr.on('data', function (data) {
                            console.log('standard error output:\n' + data);
                        });
                        free.on('exit', function (code, signal) {
                            // console.log('child process eixt ,exit:' + code);
                            fs.readFile(_dirname + '/tpl/doc/send_sample_request.js', function (err, data) {
                                fs.writeFile(_dirname + '/_doc/output/utils/send_sample_request.js', data.toString(), function (e) {
                                    cb(e);
                                });
                            });
                        });

                        // cb(e);
                    });
                });
            }], function (err, results) {
                console.log('8.创建doc文件:完成');
                cback(err);
            });
        });


    });

};