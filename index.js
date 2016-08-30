/**
 * Created by zhaohaochen on 16/8/17.
 */
//1.收集必要参数

var program = require('commander'),
    directory=require('./lib/directory'),
    content=require('./lib/content'),
    _dirname=__dirname;
// function setMysql(val, memo) {
//     memo.push(val);
//     return memo;
// }
function setString(val) {
    return val;

}
program
    .version('1.0.0')
    // .usage('[options] <file ...>')
    .option('-g, --groupid <n>', 'groupId', setString,'com.xihui.b2c')
    .option('-n, --name <n>', '项目名称', setString,'myapp')
    .option('-p, --parent <value>', '是否需要通用pom', parseInt,1)
    .option('-c, --common <value>', '是否需要通用common', parseInt,1)
    .option('-o, --mysql_host <value>', 'mysql参数:host', setString,'localhost')
    .option('-u, --mysql_user <value>', 'mysql参数:user', setString,'root')
    .option('-a, --mysql_password <value>', 'mysql参数:password', setString,'root')
    .option('-d, --mysql_database <value>', 'mysql参数:database', setString,'myapp')
    .option('-s, --src <value>', '存储路径', setString,'./tmp')
    .parse(process.argv);

console.log(' groupId: %j', program.groupid);
console.log(' name: %j', program.name);
console.log(' parent: %j', program.parent);
console.log(' common: %j', program.common);
console.log(' mysql参数:host: %j', program.mysql_host);
console.log(' mysql参数:user: %j', program.mysql_user);
console.log(' mysql参数:password: %j', program.mysql_password);
console.log(' mysql参数:database: %j', program.mysql_database);
console.log(' 存储路径: %j', program.src);
console.log(' args: %j', program.args);

//2.创建项目目录
directory(program,_dirname);
content(program,_dirname);
//3.生成项目内容

//4.压缩