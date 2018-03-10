package com.yongxin.service;

import java.util.List;

import com.yongxin.entity.User;
import com.yongxin.entityerp.Usertest;

/**
 * @author Lee
 *2017年11月13日 上午8:34:30
 */
public interface UserService {

	public User loginUser(String account);
	public User getUserById(int id);
	public User getUserByAccount(String account);
	public User getUserByName(String name);

	public int addUserManager();

	public int addUser(User user);

	public boolean deleteUserById(int id);

	public boolean updateUserPermission(int id, int permission);

	public boolean update(User user);
	
	public List<User> getUsers();
	
	public List<User> getUsers(int num);

	public int getPageNum();

	public List<User> getUserListByPermission(int permission, int num);

	public int getPageNumByPermission(int grade);

	public boolean isOrNotAgreeAdd(int id, int permission);
	
	public boolean checkUserPsw(int id, String oldPassword);

	public boolean updateUserPsw(int id, String newPassword);
	public boolean updateUserInfo(int id, String name);
	public List<Usertest> getUsertestList();
}
