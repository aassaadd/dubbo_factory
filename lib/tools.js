/**
 * Created by zhaohaochen on 2016/10/6.
 */
var Handlebars = require('handlebars');
var tools = {
    tf: function (ss) {
        function _tf(ss) {
            return ss.replace(/_(\w)/g, function (x) {
                return x.slice(1).toUpperCase();
            });
        };
        var _ss = _tf(ss);
        return _ss.toString()[0].toLowerCase() + _ss.toString().slice(1);
    },
    tf2: function (ss) {
        function _tf(ss) {
            return ss.replace(/_(\w)/g, function (x) {
                return x.slice(1).toUpperCase();
            });
        };
        var _ss = _tf(ss);
        return _ss.toString()[0].toUpperCase() + _ss.toString().slice(1);
    },
    sql2bean: function (ss) {
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
    }
}
Handlebars.registerHelper('tf', tools.tf);
Handlebars.registerHelper('tf2', tools.tf2);
Handlebars.registerHelper('sql2bean',tools.sql2bean);
module.exports = exports = tools;