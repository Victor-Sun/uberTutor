package com.gnomon.common.base;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;

/**
 * 统一定义id的entity基类.
 * 
 * 基类统一定义id的属性名称、数据类型、列名映射及生成策略.
 * 子类可重载getId()函数重定义id的列名映射和生成策略.
 * 
 * @author calvin
 */
//JPA 基类的标识
@MappedSuperclass
public abstract class IdEntity {

	protected Long id;

	@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	//@GeneratedValue(strategy = GenerationType.SEQUENCE)
//	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
//	 @GeneratedValue(strategy = GenerationType.AUTO,generator="WHS_DA022_SEQ")     
//	@SequenceGenerator(name="WHS_DA022_SEQ", sequenceName="WHS_DA022_SEQ")  

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}