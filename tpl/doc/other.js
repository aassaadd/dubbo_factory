/**
 * @api {post} /customer/loginForMsg
 * @apiVersion 1.0.0
 * @apiName loginForMsgCustomer
 * @apiGroup customer
 * @apiDescription <div>客户登录</div>
 * @apiUse MySuccess
 * @apiParam {String} mobile 手机号
 * @apiParam {String} code 短信验证码
 * @apiSuccess (Success 200) {Object} data.id
 * @apiSuccess (Success 200) {Object} data.nickname 昵称
 * @apiSuccess (Success 200) {Object} data.name 用户名
 * @apiSuccess (Success 200) {Object} data.cate 用户分类（0：游客，1注册用户）
 * @apiSuccess (Success 200) {Object} data.headimgId 用户头像
 * @apiSuccess (Success 200) {Object} data.mobile 用户电话
 * @apiSuccess (Success 200) {Object} data.gender 性别（1男，2女）
 * @apiSuccess (Success 200) {Object} data.age 年龄
 * @apiSuccess (Success 200) {Object} data.birth 出生年月日
 * @apiSuccess (Success 200) {Object} data.city 用户所在城市
 * @apiSuccess (Success 200) {Object} data.province 用户所在国家
 * @apiSuccess (Success 200) {Object} data.country 用户所在省份
 * @apiSuccess (Success 200) {Object} data.headimgurl 用户头像
 * @apiSuccess (Success 200) {Object} data.enabled 是否开启
 * @apiSuccess (Success 200) {Object} data.optId
 * @apiSuccess (Success 200) {Object} data.optDate
 * @apiSuccess (Success 200) {Object} data.createId
 * @apiSuccess (Success 200) {Object} data.createDate
 * @apiSuccess (Success 200) {Object} data.deleted
 * @apiSuccess (Success 200) {Object} token
 */
/**
 * @api {post} /customer/login
 * @apiVersion 1.0.0
 * @apiName loginCustomer
 * @apiGroup customer
 * @apiDescription <div>客户登录</div>
 * @apiUse MySuccess
 * @apiParam {String} name 用户名
 * @apiParam {String} password 密码
 * @apiSuccess (Success 200) {Object} data.id
 * @apiSuccess (Success 200) {Object} data.nickname 昵称
 * @apiSuccess (Success 200) {Object} data.name 用户名
 * @apiSuccess (Success 200) {Object} data.cate 用户分类（0：游客，1注册用户）
 * @apiSuccess (Success 200) {Object} data.headimgId 用户头像
 * @apiSuccess (Success 200) {Object} data.mobile 用户电话
 * @apiSuccess (Success 200) {Object} data.gender 性别（1男，2女）
 * @apiSuccess (Success 200) {Object} data.age 年龄
 * @apiSuccess (Success 200) {Object} data.birth 出生年月日
 * @apiSuccess (Success 200) {Object} data.city 用户所在城市
 * @apiSuccess (Success 200) {Object} data.province 用户所在国家
 * @apiSuccess (Success 200) {Object} data.country 用户所在省份
 * @apiSuccess (Success 200) {Object} data.headimgurl 用户头像
 * @apiSuccess (Success 200) {Object} data.enabled 是否开启
 * @apiSuccess (Success 200) {Object} data.optId
 * @apiSuccess (Success 200) {Object} data.optDate
 * @apiSuccess (Success 200) {Object} data.createId
 * @apiSuccess (Success 200) {Object} data.createDate
 * @apiSuccess (Success 200) {Object} data.deleted
 * @apiSuccess (Success 200) {Object} token
 */
/**
 * @api {post} /customer/senMsg
 * @apiVersion 1.0.0
 * @apiName senMsg
 * @apiGroup customer
 * @apiDescription <div>短信发送</div>
 * @apiUse MySuccess
 * @apiParam {String} mobile 手机号
 * @apiSuccess (Success 200) {Object} data.reason 短信接口返回文字
 * @apiSuccess (Success 200) {Object} data.result 短信接口返回数据
 * @apiSuccess (Success 200) {Object} data.error_code 短信接口返回错误码
 */

/**
 * @api {post} order?method=buy&buyType=:buyType
 * @apiVersion 1.0.0
 * @apiName 购买订单
 * @apiGroup order
 * @apiDescription <div>购买订单</div>
 * @apiUse MySuccess
 * @apiParam {String} [goodsId] 商品id
 * @apiParam {String} [optionId] 规格明细id
 * @apiParam {Number} total 数量
 * @apiParam {String} remark 备注
 * @apiParam {String} buyType 不传标示商品购买,传CART 标示购物车购买
 *
 */
/**
 * @api {put} /order/:id?method=pay&payType=:payType
 * @apiVersion 1.0.0
 * @apiName 支付订单
 * @apiGroup order
 * @apiDescription <div>支付订单</div>
 * @apiUse MySuccess
 * @apiParam {Number} id ID
 * @apiParam {String} payType WECHAT(微信支付),ALI(阿里支付)
 *
 */