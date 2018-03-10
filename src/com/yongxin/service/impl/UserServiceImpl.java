/**
 * 
 */
package com.yongxin.service.impl;

import java.util.List;

import com.yongxin.dao.UserRelationDao;
import com.yongxin.dao.UsertestDao;
import com.yongxin.entity.UserGrade;
import com.yongxin.entity.UserRelation;
import com.yongxin.entityerp.Usertest;
import com.yongxin.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yongxin.dao.UserDao;
import com.yongxin.entity.User;
import com.yongxin.service.UserService;

/**
 * @author Administrator 2017年12月28日
 *
 */
@Service
public class UserServiceImpl implements UserService{
	@Autowired
	UserDao userDao;
	@Autowired
	UserRelationDao userRelationDao;
	@Autowired
	UsertestDao usertestDao;

	@Override
	public User loginUser(String account) {
		User user = userDao.loginUser(account);
		if (user != null) {
			if (user.getPermission() == 3) {
				UserRelation userRelation = userRelationDao.getUserRelationBySonId(user.getId());
				if (userRelation.getUserF().getPermission() == 4 || userRelation.getUserF().getPermission() == 5) {
					return null;
				}
			}
		}
		return user;
	}

	@Override
	public User getUserById(int id) {
		// TODO Auto-generated method stub
		return userDao.getUserById(id);
	}

	@Override
	public User getUserByAccount(String account) {
		return userDao.getUserByAccount(account);
	}

	/* (non-Javadoc)
         * @see com.yongxin.service.UserService#getUserByName(java.lang.String)
         */
	@Override
	public User getUserByName(String name) {
		// TODO Auto-generated method stub
		return userDao.getUserByName(name);
	}

	/* (non-Javadoc)
	 * @see com.yongxin.service.UserService#addUser(com.yongxin.entity.User)
	 */
	@Override
	public int addUser(User user) {
		// TODO Auto-generated method stub
		return userDao.addUser(user);
	}

	/* (non-Javadoc)
	 * @see com.yongxin.service.UserService#deleteUserById(int)
	 */
	@Override
	public boolean deleteUserById(int id) {
		// TODO Auto-generated method stub
		if (userDao.deleteUserById(id) == 1) {
			return true;
		}
		return false;
	}

	@Override
	public boolean updateUserPermission(int id, int permission) {
		if (userDao.updateUserPermission(id, permission)==1) {
			return true;
		}
		return false;
	}

	/* (non-Javadoc)
         * @see com.yongxin.service.UserService#getUsers()
         */
	@Override
	public List<User> getUsers() {
		// TODO Auto-generated method stub
		return userDao.getUsers();
	}

	/* (non-Javadoc)
	 * @see com.yongxin.service.UserService#getUsers(int)
	 */
	@Override
	public List<User> getUsers(int num) {
		// TODO Auto-generated method stub
		return userDao.getUsers(num);
	}

	/* (non-Javadoc)
	 * @see com.yongxin.service.UserService#getPageNum()
	 */
	@Override
	public int getPageNum() {
		// TODO Auto-generated method stub
		return userDao.getPageNum();
	}

	/* (non-Javadoc)
	 * @see com.yongxin.service.UserService#checkUserPsw(int, java.lang.String)
	 */
	@Override
	public boolean checkUserPsw(int id, String oldPassword) {
		// TODO Auto-generated method stub
		List<User> managers = userDao.checkUserPsw(id,oldPassword);
		if (managers != null&&managers.size()>0) {
			return true;
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see com.yongxin.service.UserService#updateUserPsw(int, java.lang.String)
	 */
	@Override
	public boolean updateUserPsw(int id, String newPassword) {
		// TODO Auto-generated method stub
		if (userDao.updateUserPsw(id,newPassword) == 1) {
			return true;
		}
		return false;
	}
	@Override
	public boolean updateUserInfo(int id, String name) {
		// TODO Auto-generated method stub
		if (userDao.updateUserInfo(id,name) == 1) {
			return true;
		}
		return false;
	}
	/* (non-Javadoc)
	 * @see com.yongxin.service.UserService#update(com.yongxin.entity.User)
	 */
	@Override
	public boolean update(User user) {
		// TODO Auto-generated method stub
		if(userDao.update(user)==1){
			return true;
			}
		return false;
	}

	@Override
	public List<User> getUserListByPermission(int permission, int num) {
		return userDao.getUserListByPermission(permission,num);
	}

	@Override
	public int getPageNumByPermission(int grade) {
		return userDao.getPageNumByPermission(grade);
	}

	@Override
	public boolean isOrNotAgreeAdd(int id, int permission) {
		if (userDao.updateUserPermission(id, permission) == 1) {
			return true;
		}
		return false;
	}

	@Override
	public int addUserManager() {

		User user = new User();
		user.setUserAccount("admin");
		user.setPassword("123456");
		user.setUserName("管理员");
		user.setPermission(1);
		user.setPermissionTime(TimeUtil.stringToDate("2200-01-01"));
		UserGrade userGrade = new UserGrade();
		userGrade.setUserGrade(1);
		userGrade.setUserGradeName("超级管理员");
		user.setUserGrade(userGrade);
		return 1;
	}

	@Override
	public List<Usertest> getUsertestList() {
		return usertestDao.getUsertestList();
	}
}
