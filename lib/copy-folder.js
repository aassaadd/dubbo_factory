/**
 * Created by zhaohaochen on 16/8/17.
 */
var async = require('async'),
    fs = require('fs'),
    Handlebars = require('handlebars');
/**
 * 删除目录
 * @type {exports}
 */
var copyFolder=module.exports = exports = function(path,cypath,program) {
    var files = [];
    if( fs.existsSync(path) ) {
        files = fs.readdirSync(path);

        async.eachSeries(files, function (file, callback) {
            var curPath = path + "/" + file;
            if(fs.statSync(curPath).isDirectory()) { // recurse
                fs.mkdirSync(cypath+'/'+file);
                copyFolder(curPath,cypath+'/'+file,program);
            } else { // copy file
                fs.readFile(curPath,function(err, data){
                    fs.writeFile(cypath+'/'+file,Handlebars.compile(data.toString())(program),function(e){
                        callback(e);
                    });
                });
            }
        }, function (er) {
            console.log('2.创建文件夹:完成');
        });

    }
};