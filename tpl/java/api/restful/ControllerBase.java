package {{groupid}}.{{name}}_api.restful;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import {{groupid}}.{{name}}_facade.model.{{classname}};
import {{groupid}}.{{name}}_facade.service.{{classname}}Service;
import {{groupid}}.shop_api.common.MytSystem;

@RestController
@RequestMapping(value = "/api/v1/{{tf entity}}")
public class {{classname}}Controller extends BaseController {

@Autowired
private {{classname}}Service {{tf entity}}Service;

public {{classname}}Controller() {
		// TODO Auto-generated constructor stub
		}

@RequestMapping(value = "", method = RequestMethod.POST)
public Map<String, Object> add(
@RequestBody {{classname}} {{tf entity}}) {
		Integer currentUserId = (Integer) MytSystem.getCurrentUserId();
		{{tf entity}}.setCreateId(currentUserId);
		{{tf entity}}.setOptId(currentUserId);
		try {
		{{tf entity}}={{tf entity}}Service.add({{tf entity}});
		} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return getReturnMapFailure();
		}
		return getReturnMapSuccess({{tf entity}});

		}

@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
public Map<String, Object> update(@PathVariable(value = "id") Integer id,
@RequestBody {{classname}} {{tf entity}}) {
		Integer currentUserId = (Integer) MytSystem.getCurrentUserId();
		{{tf entity}}.setOptId(currentUserId);
		{{tf entity}}.setId(id);
		try {
		{{tf entity}}={{tf entity}}Service.update({{tf entity}});
		} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return getReturnMapFailure();
		}
		return getReturnMapSuccess({{tf entity}});

		}

@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
public Map<String, Object> delete(@PathVariable(value = "id") Integer id) {
		Integer currentUserId = (Integer) MytSystem.getCurrentUserId();
		{{classname}} {{tf entity}} = new {{classname}}();
		{{tf entity}}.setId(id);
		{{tf entity}}.setOptId(currentUserId);
		try {
		{{tf entity}}={{tf entity}}Service.delete({{tf entity}});
		} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return getReturnMapFailure();
		}
		return getReturnMapSuccess({{tf entity}});

		}

@RequestMapping(value = "/{id}", method = RequestMethod.GET)
public Map<String, Object> getById(@PathVariable(value = "id") Integer id) {
		{{classname}} {{tf entity}} = {{tf entity}}Service
		.getById(id);

		if ({{tf entity}} == null) {
		return getReturnMapFailure();
		}
		return getReturnMapSuccess({{tf entity}});

		}

@RequestMapping(value = "", method = RequestMethod.GET)
public Map<String, Object> getByPage(HttpServletRequest request) {
		Map<String, Object> params = getParameterMap(request);
		if (!params.containsKey("page")) {
		return getReturnMapSuccess({{tf entity}}Service.getByList(params));
		}
		Long pageNumber = Long.parseLong((String) params.get("page"));
		Long pageSize = Long.parseLong((String) params.get("pageSize"));
		return getReturnMapSuccess({{tf entity}}Service.getByPage(pageNumber,
		pageSize, params));

		}
		}
