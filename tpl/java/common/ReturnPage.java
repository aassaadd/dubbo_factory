package {{groupid}}.common;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;


public  class   ReturnPage<T> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5011282729698657196L;
	// totalElements: 0,//所有数据的条目
	// totalPages: 0,//设置分页的总页数,
	// size: 0,//每一页的条目数,
	// number: 0,//当前页号,
	// content: [],//分页数据内容,
	// sort: {},//排序,
	// last: true,//末页,
	// numberOfElements: 0,//当前页的数据条目数
	// first: false//首页
	private Long totalElements;
	private Long totalPages;
	private Long size;
	private Long number;
	private List<T> content;
	private Map<String,String> sort;
	private Boolean last;
	private Boolean first;
	private Long numberOfElements;
	
	public Long getTotalElements() {
		return totalElements;
	}

	public void setTotalElements(Long totalElements) {
		this.totalElements = totalElements;
	}

	public Long getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(Long totalPages) {
		this.totalPages = totalPages;
	}

	public Long getSize() {
		return size;
	}

	public void setSize(Long size) {
		this.size = size;
	}

	public Long getNumber() {
		return number;
	}

	public void setNumber(Long number) {
		this.number = number;
	}

	public List<T> getContent() {
		return content;
	}

	public void setContent(List<T> content) {
		this.content = content;
	}

	public Map<String, String> getSort() {
		return sort;
	}

	public void setSort(Map<String, String> sort) {
		this.sort = sort;
	}

	public Boolean getLast() {
		return last;
	}

	public void setLast(Boolean last) {
		this.last = last;
	}

	public Boolean getFirst() {
		return first;
	}

	public void setFirst(Boolean first) {
		this.first = first;
	}

	public Long getNumberOfElements() {
		return numberOfElements;
	}

	public void setNumberOfElements(Long numberOfElements) {
		this.numberOfElements = numberOfElements;
	}


	
	public ReturnPage(Long count ,Long pageNumber,Long pageSize,
			List<T> content) {
		//解决在dubbo下多次重复序列化造成空值的问题
		try{
			// 计算是否最后一页
			this.last = false;
			this.first = true;
			this.size = pageSize;
			this.number = pageNumber - 1;
			this.totalPages = new Long(1);// 总页数
			this.totalElements = count;// 总数据
			long y = count % pageSize;
			this.totalPages = count / pageSize;
			if (y != 0) {
				this.totalPages += 1;
			}
			if (pageNumber != 1) {
				this.first = false;
			}
			if (this.number.equals(this.totalPages - 1)) {
				this.last = true;
			}

			this.content = content;
			if(this.content.size()==0){
				this.last = true;
			}
		}catch(Exception e){
			e.getLocalizedMessage();
		}

	}

}
