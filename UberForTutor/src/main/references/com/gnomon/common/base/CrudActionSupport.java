package com.gnomon.common.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;

/**
 * Struts2�е���CRUD Action�ĳ������.
 * 
 * ��Ҫ�����˶�Preparable,ModelDriven�ӿڵ�ʹ��,�Լ�CRUD�����ͷ���ֵ������.
 *
 * @param <T> CRUDAction������Ķ�������.
 * 
 * @author calvin
 */
public abstract class CrudActionSupport<T> extends ActionSupport implements ModelDriven<T>, Preparable {

	private static final long serialVersionUID = -1653204626115064950L;

	/** ������ɾ�Ĳ�����,��redirect��ʽ���´�actionĬ��ҳ��result��.*/
	public static final String RELOAD = "reload";

	protected Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * Action����, Ĭ�ϵ�action����, Ĭ�ϵ���list()����.
	 */
	@Override
	public String execute() throws Exception {
		return list();
	}

	//-- CRUD Action���� --//
	/**
	 * Action����,��ʾEntity�б����.
	 * ����return SUCCESS.
	 */
	public abstract String list() throws Exception;

	/**
	 * Action����,��ʾ�������޸�Entity����.
	 * ����return INPUT.
	 */
	@Override
	public abstract String input() throws Exception;

	/**
	 * Action����,�������޸�Entity. 
	 * ����return RELOAD.
	 */
	public abstract String save() throws Exception;

	/**
	 * Action����,ɾ��Entity.
	 * ����return RELOAD.
	 */
	public abstract String delete() throws Exception;

	//-- Preparable���� --//
	/**
	 * ʵ�ֿյ�prepare()����,����������Action��������ִ�еĹ����Ķ��ΰ�.
	 */
	public void prepare() throws Exception {
	}

	/**
	 * ������input()ǰִ�ж��ΰ�.
	 */
	public void prepareInput() throws Exception {
		prepareModel();
	}

	/**
	 * ������save()ǰִ�ж��ΰ�.
	 */
	public void prepareSave() throws Exception {
		prepareModel();
	}

	/**
	 * ��ͬ��prepare()���ڲ�����,��prepardMethodName()��������. 
	 */
	protected abstract void prepareModel() throws Exception;
}
