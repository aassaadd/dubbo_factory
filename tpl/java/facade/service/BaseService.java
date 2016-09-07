package {{groupid}}.{{name}}_facade.service;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import {{groupid}}.common.ReturnPage;


public interface BaseService<T> {

	
	@Transactional(rollbackFor=Exception.class) 
	public T add(T t) ;

	@Transactional(rollbackFor=Exception.class) 
	public T delete(T t);

	@Transactional(rollbackFor=Exception.class) 
	public T update(T t);

	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public T getById(int id);

	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public ReturnPage<T> getByPage(Long pageNumber, Long pageSize,
			Map<String, Object> params);

	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public List<T> getByList(Map<String, Object> params);

}
