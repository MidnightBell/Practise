package per.zs.insulationMateria.service;

import java.util.List;

import per.zs.insulationMateria.entity.ResponseBody;
import per.zs.insulationMateria.entity.User;

public interface UserService {
	
	/**
	 * 新增账号
	 * @param user 新增账号信息
	 * @return ResponseBody 操作结果信息
	 */
	public ResponseBody insert(User user);
	
	/**
	 * 通过账号名查询账号信�?
	 * @param account
	 * @return User 账号信息
	 */
	public User findByAccount(String account);

	/**
	 * 查询�?有账号信�?
	 * @return List<User> 账号信息列表
	 */
	List<User> findAllAccount();

	/**
	 * 通过id为账号增加user角色
	 * @param id 账号的唯�?id
	 * @return ResponseBody 操作结果信息
	 */
	ResponseBody addUserRoles(String id);

	/**
	 * 通过账号id删除账号的user角色
	 * @param id 账号的唯�?id
	 * @return ResponseBody 操作结果信息
	 */
	ResponseBody removeUserRoles(String id);
	
	
}
