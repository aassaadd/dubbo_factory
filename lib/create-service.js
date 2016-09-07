/**
 * Created by zhaohaochen on 16/8/17.
 */
var async = require('async'),
    fs = require('fs'),
    Handlebars = require('handlebars'),
    tf = function (ss) {
        return ss.replace(/_(\w)/g, function (x) {
            return x.slice(1).toUpperCase();
        });
    }, tf2 = function (ss) {
        return tf(ss).toString()[0].toUpperCase() + tf(ss).toString().slice(1);
    };
Handlebars.registerHelper('tf', tf);
Handlebars.registerHelper('tf2', tf2);
Handlebars.registerHelper('sql2bean', function (ss) {
    var _t = ss.toString().toUpperCase(),
        r = 'String',
        type = _t.indexOf('(') >= 0 ? _t.substring(0, _t.indexOf('(')) : _t,
        long = _t.indexOf('(') >= 0 ? parseInt(_t.substring(_t.indexOf('(') + 1, _t.indexOf(')'))) : -1;
    switch (type) {
        case 'VARCHAR':
            r = 'String';
            break;
        case 'CHAR':
            r = 'String';
            break;
        case 'BLOB':
            r = 'Byte[]';
            break;
        case 'TINYINT':
        case 'INT':
            r = 'Integer';
            if (long > 11) {
                r = 'Long';
            }
            break;
        case 'BIT':
            r = 'Boolean';
            break;
        case 'BIGINT':
            r = 'Long';
            break;
        case 'FLOAT':
            r = 'FLOAT';
            break;
        case 'DECIMAL':
            r = 'BigDecimal';
            break;
        case 'DATE':
            r = 'Date';
            break;
        case 'TIME':
            r = 'Time';
            break;
    }
    // VARCHAR	L+N	VARCHAR	java.lang.String	12
    // CHAR	N	CHAR	java.lang.String	1
    // BLOB	L+N	BLOB	java.lang.byte[]	-4
    // TEXT	65535	VARCHAR	java.lang.String	-1
    // INTEGER	4	INTEGER UNSIGNED	java.lang.Long	4
    // TINYINT	3	TINYINT UNSIGNED	java.lang.Integer	-6
    // SMALLINT	5	SMALLINT UNSIGNED	java.lang.Integer	5
    // MEDIUMINT	8	MEDIUMINT UNSIGNED	java.lang.Integer	4
    // BIT	1	BIT	java.lang.Boolean	-7
    // BIGINT	20	BIGINT UNSIGNED	java.math.BigInteger	-5
    // FLOAT	4+8	FLOAT	java.lang.Float	7
    // DOUBLE	22	DOUBLE	java.lang.Double	8
    // DECIMAL	11	DECIMAL	java.math.BigDecimal	3
    // BOOLEAN	1	同TINYINT
    // ID	11	PK (INTEGER UNSIGNED)	java.lang.Long	4
    // DATE	10	DATE	java.sql.Date	91
    // TIME	8	TIME	java.sql.Time	92
    // DATETIME	19	DATETIME	java.sql.Timestamp	93
    // TIMESTAMP	19	TIMESTAMP	java.sql.Timestamp	93
    // YEAR	4	YEAR	java.sql.Date	91


    return r;
});
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
                    classname: tf2(item['TABLE_NAME']),
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
                            fs.writeFile(_dirname + '/_tmp/' + item.name + '-facade' + '/src/main/java/' + item.groupid.replace(/\./g, "/") + '/' + item.name + '_facade/service/' + tf2(item.entity) + 'Service.java', Handlebars.compile(data.toString())(item), function (e) {
                                callback(e);
                            });
                        }, function (er) {
                            cb(er);
                        });

                    });
                }, function (cb) {
                    fs.readFile(_dirname + '/tpl/java/service/service/ServiceImplBase.java', function (err, data) {
                        async.eachSeries(entitys, function (item, callback) {
                            fs.writeFile(_dirname + '/_tmp/' + item.name + '-service' + '/src/main/java/' + item.groupid.replace(/\./g, "/") + '/' + item.name + '_service/service/' + tf2(item.entity) + 'ServiceImpl.java', Handlebars.compile(data.toString())(item), function (e) {
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