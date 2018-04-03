package com.demo.user;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.demo.common.model.User;

/**
 * User 管理	
 * 描述：
 */
public class UserService {

	//private static final Log log = Log.getLog(UserService.class);
	
	public static final UserService me = new UserService();
	private final User dao = new User().dao();
	
	
	/**
	* 列表-分页
	*/
	public Page<User> paginate(int pageNumber, int pageSize) {
		return dao.paginate(pageNumber, pageSize, "SELECT * ", "FROM tb_user");
	}
	
	/**
	* 保存
	*/
	public void save(User user) {
		user.save();
	}
	
	/**
	* 更新
	*/
	public void update(User user) {
		user.update();
	}
	
	/**
	* 查询
	*/
	public User findById(int userId) {
		return dao.findFirst("select * from tb_user where id=?", userId);
	}
	
	/**
	* 删除
	*/
	public void delete(int userId) {
		Db.update("delete from tb_user where id=?", userId);
	}
	
	
}