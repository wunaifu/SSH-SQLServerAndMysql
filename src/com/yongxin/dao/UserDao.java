/**
 * 
 */
package com.yongxin.dao;

import java.sql.ResultSet;
import java.util.List;

import com.yongxin.entity.User;



/**
 * @author Lee 2017年12月18日
 *
 */
public interface UserDao {

	public User loginUser(String account);

	public User getUserById(int id);

	public User getUserByAccount(String account);

	public User getUserByName(String name);
	
	public int addUser(User user);

	public Integer deleteUserById(int id);

	public int update(User user);
	
	public List<User> getUsers();
	
	public List<User> getUsers(int num);

	public List<User> getUserListByPermission(int permission, int num);

	public int getPageNumByPermission(int grade);

	public Integer isOrNotAgreeAdd(int id, int permission);
	public int getPageNum();
	
	public List<User> checkUserPsw(int id, String oldPassword);

	public Integer updateUserPsw(int id, String newPassword);
	public Integer updateUserInfo(int id, String name);
	
	public Integer updateUserGrade(int id, int userGradeId);

	public Integer updateUserPermission(int id, int permission);


}
