/**
 * Created by zhaohaochen on 16/8/17.
 */
var async = require('async'),
    fs = require('fs'),
    Handlebars = require('handlebars'),
    tools = require('./tools');
Handlebars.registerHelper('sql2bean2', function (ss) {
    var _t = ss.toString().toUpperCase(),
        r = 'String',
        type = _t.indexOf('(') >= 0 ? _t.substring(0, _t.indexOf('(')) : _t,
        long = _t.indexOf('(') >= 0 ? parseInt(_t.substring(_t.indexOf('(') + 1, _t.indexOf(')'))) : -1;
    switch (type) {
        case 'INT':
            r = 'INTEGER';
            if (long > 11) {
                r = 'Long';
            }
            break;
        default:
            r = type;
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
var createMapper = module.exports = exports = function (program, _dirname, connection, cback) {
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
            async.parallel([function (cb) {
                fs.readFile(_dirname + '/tpl/java/service/mapper/MapperBase.java', function (err, data) {
                    async.eachSeries(entitys, function (item, callback) {
                        fs.writeFile(_dirname + '/_tmp/' + item.name + '-service' + '/src/main/java/' + item.groupid.replace(/\./g, "/") + '/' + item.name + '_service/mapper/' + tools.tf2(item.entity) + 'Mapper.java', Handlebars.compile(data.toString())(item), function (e) {
                            callback(e);
                        });
                    }, function (er) {
                        cb(er);
                    });
                });
            }, function (cb) {
                fs.readFile(_dirname + '/tpl/resources/service/mapper/sqlMapConfig.xml', function (err, data) {
                    fs.writeFile(_dirname + '/_tmp/' + program.name + '-service' + '/src/main/resources/mapper/sqlMapConfig.xml', Handlebars.compile(data.toString())(program), function (e) {
                        cb(e);

                    });
                });
            }, function (cb) {
                fs.readFile(_dirname + '/tpl/resources/service/mapper/MapperBase.xml', function (err, data) {
                    async.eachSeries(entitys, function (item, callback) {
                        fs.writeFile(_dirname + '/_tmp/' + item.name + '-service' + '/src/main/resources/mapper/' + tools.tf2(item.entity) + 'Mapper.xml', Handlebars.compile(data.toString())(item), function (e) {

                            callback(e);
                        });
                    }, function (er) {
                        cb(er);
                    });

                });
            }], function (err, results) {
                console.log('3.mapper:完成');
                cback(err);
            });
        });
    });

};