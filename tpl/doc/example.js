{{#each entitys}}
/**
 * @api {get} /{{tf entity}}/:id
 * @apiVersion 1.0.0
 * @apiName {{classname}}
 * @apiGroup {{tf entity}}
 * @apiDescription <div>{{comment}}</div>
 * @apiHeader {String} token token
 * @apiParam {Number} id ID
 * @apiUse MySuccess
 {{#each comments}}
 * @apiSuccess (Success 200) {Object} data.{{tf Field}} {{Comment}}
 {{/each}}
 */
/**
 * @api {get} /{{tf entity}}
 * @apiVersion 1.0.0
 * @apiName {{classname}}Page
 * @apiGroup {{tf entity}}
 * @apiDescription <div>{{comment}}</div>
 * @apiHeader {String} token token
 * @apiParam {Number} id ID
 * @apiUse MySuccess
 * @apiUse MySuccessPage
 {{#each comments}}
 * @apiParam {String} [{{tf Field}}]
 * @apiSuccess (Success 200) {Object} data.{{tf Field}} {{Comment}}
 {{/each}}
 */
/**
 * @api {post} /{{tf entity}}
 * @apiVersion 1.0.0
 * @apiName {{classname}}Add
 * @apiGroup {{tf entity}}
 * @apiDescription <div>{{comment}}</div>
 * @apiHeader {String} token token
 * @apiUse MySuccess
 {{#each comments}}
 * @apiParam {String} [{{tf Field}}]
 * @apiSuccess (Success 200) {Object} data.{{tf Field}} {{Comment}}
 {{/each}}
 */
/**
 * @api {put} /{{tf entity}}/:id
 * @apiVersion 1.0.0
 * @apiName {{classname}}Edit
 * @apiGroup {{tf entity}}
 * @apiDescription <div>{{comment}}</div>
 * @apiHeader {String} token token
 * @apiParam {Number} id ID
 * @apiUse MySuccess
 {{#each comments}}
 * @apiParam {String} [{{tf Field}}]
 * @apiSuccess (Success 200) {Object} data.{{tf Field}} {{Comment}}
 {{/each}}
 */
/**
 * @api {delete} /{{tf entity}}/:id
 * @apiVersion 1.0.0
 * @apiName {{classname}}Delete
 * @apiGroup {{tf entity}}
 * @apiDescription <div>{{comment}}</div>
 * @apiHeader {String} token token
 * @apiParam {Number} id ID
 * @apiUse MySuccess
 {{#each comments}}
 * @apiSuccess (Success 200) {Object} data.{{tf Field}} {{Comment}}
 {{/each}}
 */

{{/each}}