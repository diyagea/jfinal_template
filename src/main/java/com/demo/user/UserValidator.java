package com.demo.user;

import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;


public class UserValidator extends Validator {

	//private static final Log log = Log.getLog(UserValidator.class);
	
	protected void validate(Controller c) {
		//if ("save".equals(getActionMethod().getName())) {
			
		//} else if ("update".equals(getActionMethod().getName())) {
			
		//}else {
			//addError("msg", "只能用于xxx方法");
		//}
	}
	
	protected void handleError(Controller c) {
		c.renderJson();
	}
	
}