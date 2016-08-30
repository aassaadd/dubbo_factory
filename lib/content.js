/**
 * 创建临时目录
 * Created by zhaohaochen on 16/8/17.
 */
var async = require('async'),
    fs = require('fs'),
    copyFolder = require('./copy-folder'),
    createModel = require('./create-model'),
    createMapper = require('./create-mapper'),
    createService = require('./create-service'),
    createApi = require('./create-api'),
    createConf = require('./create-conf'),
    createDubbo = require('./create-dubbo'),
    mysql = require('mysql'),
    archiver=require('archiver'),
    Handlebars = require('handlebars');
/**
 *
 * @param name 项目名
 * @param parent 是否有父pom 0,1
 * @param common 是否有通用common 0,1
 * @param src 目录地址
 * @param sqlargs sql参数
 */
var content = function (program, _dirname) {
    var groupids = program.groupid.split('.');
    //1.创建pom/公用配置文件
    async.parallel([function (callback) {
        fs.readFile(_dirname + '/tpl/pom/common/pom.xml', function (err, data) {
            fs.writeFile(_dirname + '/_tmp/common/pom.xml', Handlebars.compile(data.toString())(program), function (e) {

                callback(e);
            });

        });
    }, function (callback) {
        fs.readFile(_dirname + '/tpl/pom/pom-parent/pom.xml', function (err, data) {
            fs.writeFile(_dirname + '/_tmp/pom-parent/pom.xml', Handlebars.compile(data.toString())(program), function (e) {
                callback(e);
            });

        });
    }, function (callback) {
        fs.readFile(_dirname + '/tpl/pom/facade/pom.xml', function (err, data) {
            fs.writeFile(_dirname + '/_tmp/' + program.name + '-facade/pom.xml', Handlebars.compile(data.toString())(program), function (e) {
                callback(e);
            });

        });
    }, function (callback) {
        fs.readFile(_dirname + '/tpl/pom/service/pom.xml', function (err, data) {
            fs.writeFile(_dirname + '/_tmp/' + program.name + '-service/pom.xml', Handlebars.compile(data.toString())(program), function (e) {
                callback(e);
            });

        });
    }, function (callback) {
        fs.readFile(_dirname + '/tpl/pom/api/pom.xml', function (err, data) {
            fs.writeFile(_dirname + '/_tmp/' + program.name + '-api/pom.xml', Handlebars.compile(data.toString())(program), function (e) {
                callback(e);
            });

        });
    }, function (callback) {
        fs.readFile(_dirname + '/tpl/pom/api/assembly-standalone.xml', function (err, data) {
            fs.writeFile(_dirname + '/_tmp/' + program.name + '-api/assembly-standalone.xml', Handlebars.compile(data.toString())(program), function (e) {
                callback(e);
            });
        });
    }], function (err, results) {
        console.log('1.创建pom/公用配置文件:完成');
    });
    //2.创建文件夹
    copyFolder(_dirname + '/tpl/java/common', _dirname + '/_tmp' + '/common' + '/src/main/java/' + program.groupid.replace(/\./g, "/") + '/common', program);

    //创建数据库连接
    var connectionpool = mysql.createPool({
        host: program.mysql_host,
        user: program.mysql_user,
        password: program.mysql_password,
        database: program.mysql_database
    });
    connectionpool.getConnection(function (err, connection) {
        if (err) {
            console.error('CONNECTION error: ', err);
        } else {
            async.parallel([function (callback) {
                //3.创建mapper/model
                createModel(program, _dirname, connection,callback);

            },function (callback) {
                //3.创建mapper/model
                createMapper(program, _dirname, connection,callback);
            },function (callback) {
                //4.创建service
                createService(program, _dirname, connection,callback);
            },function (callback) {
                //5.创建api/apidoc
                createApi(program, _dirname, connection,callback);
            },function (callback) {
                //6.创建conf文件
                createConf(program, _dirname, connection,callback);
            },function (callback) {
                //7.创建dubbo文件
                createDubbo(program, _dirname, connection,callback);
            }], function (err, results) {
                console.log('完成...');
                var zipPath = program.src+'/dist.zip';
                //创建一最终打包文件的输出流
                var output = fs.createWriteStream(zipPath);
                //生成archiver对象，打包类型为zip
                var zipArchiver = archiver('zip');

                //将打包对象与输出流关联
                zipArchiver.pipe(output);
                // zipArchiver.directory(_dirname+'/_tmp',zipPath);
                zipArchiver.bulk([{ expand: true, cwd: _dirname+'/_tmp', src: ['**'] }]);
                //打包
                function it() {
                    if(zipArchiver._state.finalized){
                        process.exit(0);
                    }else{
                        setTimeout(it,1000);

                    }
                }
                zipArchiver.finalize();
                it();
            });
        }

    })


}
module.exports = exports = content;