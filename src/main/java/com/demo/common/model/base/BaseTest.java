package com.demo.common.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseTest<M extends BaseTest<M>> extends Model<M> implements IBean {

	public M setId(java.lang.Integer Id) {
		set("Id", Id);
		return (M)this;
	}

	public java.lang.Integer getId() {
		return getInt("Id");
	}

}
