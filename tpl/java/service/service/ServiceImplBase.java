
package {{groupid}}.{{name}}_service.service;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;
import {{groupid}}.common.util.BeanUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import {{groupid}}.common.ReturnPage;
import {{groupid}}.{{name}}_facade.model.{{classname}};
import {{groupid}}.{{name}}_facade.service.{{classname}}Service;
import {{groupid}}.{{name}}_service.mapper.{{classname}}Mapper;
@Service("{{tf entity}}Service")
public class {{classname}}ServiceImpl implements {{classname}}Service {

@Autowired
private {{classname}}Mapper mapper;
public {{classname}} add({{classname}} t) {
		// TODO Auto-generated method stub
		Long d=System.currentTimeMillis();
		t.setCreateDate(d);
		t.setOptDate(d);
		t.setEnabled(true);
		t.setDeleted((byte) 0);
		mapper.insertSelective(t);
		return t;
		}

public {{classname}} delete({{classname}} t) {
		// TODO Auto-generated method stub
		Long d=System.currentTimeMillis();
		t.setOptDate(d);
		t.setDeleted((byte) 1);
		mapper.updateByPrimaryKeySelective(t);
		return t;
		}

public {{classname}} update({{classname}} t) {
		// TODO Auto-generated method stub
		Long d=System.currentTimeMillis();
		t.setOptDate(d);
		mapper.updateByPrimaryKeySelective(t);
		return t;
		}

public {{classname}} getById(int id) {
		// TODO Auto-generated method stub
		{{classname}} t=new {{classname}}();
		t.setId(id);
		t.setDeleted((byte) 0);
		return mapper.selectOne(t);
		}

public ReturnPage<{{classname}}> getByPage(Long pageNumber,
		Long pageSize, Map<String, Object> params) {
		// TODO Auto-generated method stub
		PageHelper.startPage(pageNumber.intValue(), pageSize.intValue());
		List<{{classname}}> list=getByList(params);
		PageInfo<{{classname}}> page = new PageInfo<{{classname}}>(list);
		return new ReturnPage<{{classname}}>(page.getTotal(),pageNumber,pageSize,list);
		}

public List<{{classname}}> getByList(Map<String, Object> params) {
		// TODO Auto-generated method stub
		Map<String,Class<?>> returnType=BeanUtils.getBeanMethodsReturnType({{classname}}.class);
		Example example=new Example({{classname}}.class);
		//
		Criteria or=example.or();
		for(String key : params.keySet()){
		if(key.indexOf("_like")>-1){
		or.andLike(key.substring(0, key.indexOf("_like")), "%"+params.get(key)+"%");
		}
		if (!key.equals("pageSize") && !key.equals("page")
		&& !params.get(key).equals("") && key.indexOf("_in") < 0
		&& key.indexOf("_like") < 0) {
		if(returnType.containsKey(key)){
		or.andEqualTo(key, returnType.get(key).cast(params.get(key)));
		}else{
		or.andEqualTo(key, params.get(key));
		}
		}


		}
		or.andEqualTo("deleted", 0);
		example.setOrderByClause("create_date DESC");
		return mapper.selectByExample(example);
		}

		}
