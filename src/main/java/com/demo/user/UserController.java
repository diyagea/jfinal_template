package com.demo.user;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.demo.common.model.User;

/**
 * User 管理	
 * 描述：
 * 
 */
public class UserController extends Controller {

	//private static final Log log = Log.getLog(UserController.class);
	
	static UserService srv = UserService.me;
	
	/**
	 * 列表
	 * /demo/user/list
	 */
	public void list() {
		setAttr("page", srv.paginate(getParaToInt("p", 1), 40));
		render("userList.html");
	}
	
	/**
	 * 准备添加
	 * /demo/user/add
	 */
	public void add() {
		render("userAdd.html");
	}
	
	/**
	 * 保存
	 * /demo/user/save
	 */
	@Before({UserValidator.class})
	public void save() {
		srv.save(getModel(User.class));
		renderJson("isOk", true);
	}

	/**
	 * 准备更新
	 * /demo/user/edit
	 */
	public void edit() {
		User user = srv.findById(getParaToInt("id"));
		setAttr("user", user);
		render("userEdit.html");
	}

	/**
	 * 更新
	 * /demo/user/update
	 */
	@Before(UserValidator.class)
	public void update() {
		srv.update(getModel(User.class));
		renderJson("isOk", true);
	}

	/**
	 * 查看
	 * /demo/user/view
	 */
	public void view() {
		User user = srv.findById(getParaToInt("id"));
		setAttr("user", user);
		render("userView.html");
	}
	 
	/**
	 * 删除
	 * /demo/user/delete
	 */
	public void delete() {
		srv.delete(getParaToInt("id"));
		renderJson("isOk", true);
	}
	
}