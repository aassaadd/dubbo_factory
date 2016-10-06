/**
 * 创建临时目录
 * Created by zhaohaochen on 16/8/17.
 */
var async = require('async'),
    fs = require('fs'),
    deleteFolder = require('./delete-folder');
/**
 *
 * @param name 项目名
 * @param parent 是否有父pom 0,1
 * @param common 是否有通用common 0,1
 * @param src 目录地址
 */
var directory = function (program, _dirname) {

    var groupids = program.groupid.split('.');
    deleteFolder(_dirname + '/_tmp');
    fs.mkdirSync(_dirname + '/_tmp');
    //apidoc目录
    deleteFolder(_dirname + '/_doc');
    fs.mkdirSync(_dirname + '/_doc');
    var dirs = [];
    if (program.parent) {
        dirs.push('pom-parent');
    }
    if (program.common) {
        dirs.push('common');
    }
    dirs.push(program.name + '-facade');
    dirs.push(program.name + '-service');
    dirs.push(program.name + '-api');
    for (var i in dirs) {
        var dir = dirs[i];
        fs.mkdirSync(_dirname + '/_tmp/' + dir);
        fs.mkdirSync(_dirname + '/_tmp/' + dir + '/src');
        fs.mkdirSync(_dirname + '/_tmp/' + dir + '/src/main');
        fs.mkdirSync(_dirname + '/_tmp/' + dir + '/src/main/java');
        fs.mkdirSync(_dirname + '/_tmp/' + dir + '/src/main/resources');
        fs.mkdirSync(_dirname + '/_tmp/' + dir + '/src/main/resources/conf');
        fs.mkdirSync(_dirname + '/_tmp/' + dir + '/src/test');
        fs.mkdirSync(_dirname + '/_tmp/' + dir + '/src/test/java');
        if (dir.indexOf('pom') < 0) {
            var _dir = undefined;
            for (var i in groupids) {
                if (_dir) {
                    _dir += '/' + groupids[i];
                } else {
                    _dir = _dirname + '/_tmp/' + dir + '/src/main/java/' + groupids[i];

                }
                fs.mkdirSync(_dir);
            }

        }
        if (dir.indexOf('common') >= 0) {
            fs.mkdirSync(_dirname + '/_tmp/' + dir + '/src/main/java/' + program.groupid.replace(/\./g, "/") + '/common');
        }
        if (dir.indexOf('facade') >= 0) {
            fs.mkdirSync(_dirname + '/_tmp/' + dir + '/src/main/java/' + program.groupid.replace(/\./g, "/") + '/' + program.name + '_facade');
            fs.mkdirSync(_dirname + '/_tmp/' + dir + '/src/main/java/' + program.groupid.replace(/\./g, "/") + '/' + program.name + '_facade/model');
            fs.mkdirSync(_dirname + '/_tmp/' + dir + '/src/main/java/' + program.groupid.replace(/\./g, "/") + '/' + program.name + '_facade/service');
        }
        if (dir.indexOf('service') >= 0) {
            var _dir = undefined;
            for (var i in groupids) {
                if (_dir) {
                    _dir += '/' + groupids[i];
                } else {
                    _dir = _dirname + '/_tmp/' + dir + '/src/test/java/' + groupids[i];

                }
                fs.mkdirSync(_dir);
            }
            fs.mkdirSync(_dirname + '/_tmp/' + dir + '/src/test/java/' + program.groupid.replace(/\./g, "/") + '/' + program.name + '_service')
            fs.mkdirSync(_dirname + '/_tmp/' + dir + '/src/main/java/' + program.groupid.replace(/\./g, "/") + '/' + program.name + '_service');
            fs.mkdirSync(_dirname + '/_tmp/' + dir + '/src/main/java/' + program.groupid.replace(/\./g, "/") + '/' + program.name + '_service/mapper');
            fs.mkdirSync(_dirname + '/_tmp/' + dir + '/src/main/java/' + program.groupid.replace(/\./g, "/") + '/' + program.name + '_service/service');
            fs.mkdirSync(_dirname + '/_tmp/' + dir + '/src/main/java/' + program.groupid.replace(/\./g, "/") + '/' + program.name + '_service/token');
            fs.mkdirSync(_dirname + '/_tmp/' + dir + '/src/main/resources/mapper');
        }
        if (dir.indexOf('api') >= 0) {
            fs.mkdirSync(_dirname + '/_tmp/' + dir + '/src/main/java/' + program.groupid.replace(/\./g, "/") + '/' + program.name + '_api');
            fs.mkdirSync(_dirname + '/_tmp/' + dir + '/src/main/java/' + program.groupid.replace(/\./g, "/") + '/' + program.name + '_api/common');
            fs.mkdirSync(_dirname + '/_tmp/' + dir + '/src/main/java/' + program.groupid.replace(/\./g, "/") + '/' + program.name + '_api/filter');
            fs.mkdirSync(_dirname + '/_tmp/' + dir + '/src/main/java/' + program.groupid.replace(/\./g, "/") + '/' + program.name + '_api/jetty');
            fs.mkdirSync(_dirname + '/_tmp/' + dir + '/src/main/java/' + program.groupid.replace(/\./g, "/") + '/' + program.name + '_api/restful');
            fs.mkdirSync(_dirname + '/_tmp/' + dir + '/src/main/webapp');
            fs.mkdirSync(_dirname + '/_tmp/' + dir + '/src/main/webapp/WEB-INF');
        }


    }


}
module.exports = exports = directory;