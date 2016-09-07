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
            fs.writeFile(_dirname + '/_tmp/' + program.name + '-api/src/main/resources/conf/' + tf2(program.name) + 'System.properties', Handlebars.compile(data.toString())(program), function (e) {
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