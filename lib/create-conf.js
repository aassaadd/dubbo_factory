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
var createConf = module.exports = exports = function (program, _dirname, connection,cback) {
    async.parallel([function (cb) {
        fs.readFile(_dirname + '/tpl/resources/api/conf/applicationContext.xml', function (err, data) {
            fs.writeFile(_dirname + '/_tmp/' + program.name + '-api/src/main/resources/conf/applicationContext.xml', Handlebars.compile(data.toString())(program), function (e) {
                cb(e);
            });
        });
    },function (cb) {
        fs.readFile(_dirname + '/tpl/resources/api/conf/dubbo.properties', function (err, data) {
            fs.writeFile(_dirname + '/_tmp/' + program.name + '-api/src/main/resources/conf/dubbo.properties', Handlebars.compile(data.toString())(program), function (e) {
                cb(e);
            });
        });
    },function (cb) {
        fs.readFile(_dirname + '/tpl/resources/api/conf/log4j.properties', function (err, data) {
            fs.writeFile(_dirname + '/_tmp/' + program.name + '-api/src/main/resources/conf/log4j.properties', Handlebars.compile(data.toString())(program), function (e) {
                cb(e);
            });
        });
    },function (cb) {
        fs.readFile(_dirname + '/tpl/resources/api/conf/spring-mvc.xml', function (err, data) {
            fs.writeFile(_dirname + '/_tmp/' + program.name + '-api/src/main/resources/conf/spring-mvc.xml', Handlebars.compile(data.toString())(program), function (e) {
                cb(e);
            });
        });
    },function (cb) {
        fs.readFile(_dirname + '/tpl/resources/api/conf/System.properties', function (err, data) {
            fs.writeFile(_dirname + '/_tmp/' + program.name + '-api/src/main/resources/conf/' + tools.tf2(program.name) + 'System.properties', Handlebars.compile(data.toString())(program), function (e) {
                cb(e);
            });
        });
    },function (cb) {
        fs.readFile(_dirname + '/tpl/resources/common/conf/ehcache.xml', function (err, data) {
            fs.writeFile(_dirname + '/_tmp/common/src/main/resources/conf/ehcache.xml', Handlebars.compile(data.toString())(program), function (e) {
                cb(e);
            });
        });
    },function (cb) {
        fs.readFile(_dirname + '/tpl/resources/service/conf/applicationContext.xml', function (err, data) {
            fs.writeFile(_dirname + '/_tmp/' + program.name + '-service/src/main/resources/conf/applicationContext.xml', Handlebars.compile(data.toString())(program), function (e) {
                cb(e);
            });
        });
    },function (cb) {
        fs.readFile(_dirname + '/tpl/resources/service/conf/applicationContext-source.xml', function (err, data) {
            fs.writeFile(_dirname + '/_tmp/' + program.name + '-service/src/main/resources/conf/applicationContext-source.xml', Handlebars.compile(data.toString())(program), function (e) {
                cb(e);
            });
        });
    },function (cb) {
        fs.readFile(_dirname + '/tpl/resources/service/conf/dubbo.properties', function (err, data) {
            fs.writeFile(_dirname + '/_tmp/' + program.name + '-service/src/main/resources/conf/dubbo.properties', Handlebars.compile(data.toString())(program), function (e) {
                cb(e);
            });
        });
    },function (cb) {
        fs.readFile(_dirname + '/tpl/resources/service/conf/jdbc.properties', function (err, data) {
            fs.writeFile(_dirname + '/_tmp/' + program.name + '-service/src/main/resources/conf/jdbc.properties', Handlebars.compile(data.toString())(program), function (e) {
                cb(e);
            });
        });
    },function (cb) {
        fs.readFile(_dirname + '/tpl/resources/service/conf/apllicationContext-redis.xml', function (err, data) {
            fs.writeFile(_dirname + '/_tmp/' + program.name + '-service/src/main/resources/conf/apllicationContext-redis.xml', Handlebars.compile(data.toString())(program), function (e) {
                cb(e);
            });
        });
    },function (cb) {
        fs.readFile(_dirname + '/tpl/resources/service/conf/redis.properties', function (err, data) {
            fs.writeFile(_dirname + '/_tmp/' + program.name + '-service/src/main/resources/conf/redis.properties', Handlebars.compile(data.toString())(program), function (e) {
                cb(e);
            });
        });
    },function (cb) {
        fs.readFile(_dirname + '/tpl/resources/service/log4j.properties', function (err, data) {
            fs.writeFile(_dirname + '/_tmp/' + program.name + '-service/src/main/resources/log4j.properties', Handlebars.compile(data.toString())(program), function (e) {
                cb(e);
            });
        });
    },function (cb) {
        fs.readFile(_dirname + '/tpl/resources/api/webapp/WEB-INF/web.xml', function (err, data) {
            fs.writeFile(_dirname + '/_tmp/' + program.name + '-api/src/main/webapp/WEB-INF/web.xml', Handlebars.compile(data.toString())(program), function (e) {
                cb(e);
            });
        });
    }], function (err, results) {
        console.log('6.创建conf文件:完成');
        cback(err);
    })











};