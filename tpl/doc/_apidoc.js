/**
 * @apiDefine MySuccess
 * @apiSuccess (Success 200) {String} message 消息体
 * @apiSuccess (Success 200) {String} code 状态码 200
 * @apiSuccess (Success 200) {Boolean} succes true
 * @apiSuccess (Success 200) {Object} data 返回结果
 * @apiSuccess (Success 400) {String} message 错误信息
 * @apiSuccess (Success 400) {String} code 状态码 4xx
 * @apiSuccess (Success 400) {Boolean} succes false
 */

/**
 * @apiDefine MySuccessPage
 * @apiSuccess (Success 200) {Number} data.totalElements 总数
 * @apiSuccess (Success 200) {Number} data.totalPages 总页数
 * @apiSuccess (Success 200) {Number} data.size 每页个数
 * @apiSuccess (Success 200) {Number} data.number 页数下标
 * @apiSuccess (Success 200) {String} data.sort 排序
 * @apiSuccess (Success 200) {Boolean} data.last 是否最后一页
 * @apiSuccess (Success 200) {Boolean} data.first 是否第一页
 * @apiSuccess (Success 200) {[Object]} data.content 数据
 *
 */

