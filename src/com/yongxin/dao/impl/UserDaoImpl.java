/**
 * 
 */
package com.yongxin.dao.impl;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.yongxin.entity.UserGrade;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.yongxin.dao.UserDao;
import com.yongxin.entity.User;

/**
 * @author Administrator 2017年12月28日
 *
 */
@Repository
public class UserDaoImpl implements UserDao {

	/* (non-Javadoc)
	 * @see com.yongxin.dao.UserDao#getUserById(int)
	 */
	@Resource
	private SessionFactory sessionFactory;
	private Session getSession() {
		return sessionFactory.openSession();
	}

	@Override
	public User loginUser(String account) {
		Session session = this.getSession();
		User user = new User();
		try {
			user = (User) session.createQuery("from User where userAccount=?").setParameter(0, account).uniqueResult();
		} finally {
			session.close();
		}
		return user;

	}

	@Override
	public User getUserById(int id) {
		Session session = this.getSession();
		User user = new User();
		try {
			user = (User) session.createQuery("from User where id=?").setParameter(0, id).uniqueResult();
		} finally {
            session.close();
        }
		return user;

	}
	@Override
	public User getUserByAccount(String account) {
		// TODO Auto-generated method stub
		Session session = this.getSession();
		User user = new User();
		try {
			user = (User) session.createQuery("from User where userAccount=?").setParameter(0, account).uniqueResult();
		} finally {
			session.close();
		}
		return user;
	}
	/* (non-Javadoc)
	 * @see com.yongxin.dao.UserDao#getUserByName(java.lang.String)
	 */
	@Override
	public User getUserByName(String name) {
		// TODO Auto-generated method stub
		Session session = this.getSession();
		User user = new User();
		try {
			user = (User) session.createQuery("from User where name=?").setParameter(0, name).uniqueResult();
		} finally {
            session.close();
		}
		return user;
	}

	/* (non-Javadoc)
	 * @see com.yongxin.dao.UserDao#addUser(com.yongxin.entity.User)
	 */
	@Override
	public int addUser(User user) {
		Session session = this.getSession();
		try {
			session.saveOrUpdate(user);
		} finally {
            session.close();
		}
		return 1;
	}

	/* (non-Javadoc)
	 * @see com.yongxin.dao.UserDao#deleteUserById(int)
	 */
	@Override
	public Integer deleteUserById(int id) {
		// TODO Auto-generated method stub
		Session session = this.getSession();
		int result = 0;
		try {
			result = session.createQuery("delete User where id=?").setParameter(0, id).executeUpdate();
		} finally {
            session.close();
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see com.yongxin.dao.UserDao#update(com.yongxin.entity.User)
	 */
	@Override
	public int update(User user) {
		Session session = this.getSession();
		Integer result = 0;
		try {
			List<Object> objects= new ArrayList<Object>();
			String hql;

			objects.add(user.getUserName());
			objects.add(user.getId());
			hql = "update User set userName= ? where id=?";
			org.hibernate.Query query = session.createQuery(hql)
					.setParameter(0, objects);
			if (objects!=null&&objects.size()>0) {
				for (int i = 0; i < objects.size(); i++) {
					query.setParameter(i, objects.get(i));
				}
			}
			result = query.executeUpdate();
		}finally {
            session.close();
		}
		return result;
		
	}

	/* (non-Javadoc)
	 * @see com.yongxin.dao.UserDao#getUsers()
	 */
	@Override
	public List<User> getUsers() {
		// TODO Auto-generated method stub
		Session session = this.getSession();
		List<User> userList = new ArrayList<>();
		try {
			userList = session.createCriteria(User.class).list();
		} finally {
            session.close();
		}
		return userList;
	}

	/* (non-Javadoc)
	 * @see com.yongxin.dao.UserDao#getUsers(int)
	 */
	@Override
	public List<User> getUsers(int num) {
//		 * 分页查询步骤
		Session session = this.getSession();
		List<User> userList = new ArrayList<>();
		try{
			Criteria query = session.createCriteria(User.class);
			ScrollableResults scroll = query.scroll();
			// 滚动到最后一行
			scroll.last();
			// 一共多少条数据
			int n = scroll.getRowNumber() + 1;
			System.out.println(n);
			// 设定一页显示多少条
			int nu = 10;
			int begin = (num - 1) * nu;
			//int last = n - nu * num;
			// 设置分页位置
			query.setFirstResult(begin);
			//结束位置
			query.setMaxResults(nu);
			userList = query.list();
			System.out.println(userList);
		}finally {
           session.close();
		}
		return userList;
	}

	/* (non-Javadoc)
	 * @see com.yongxin.dao.UserDao#getPageNum()
	 */
	@Override
	public int getPageNum() {
		// TODO Auto-generated method stub
		Session session = this.getSession();

		int pages = 0;
		try {
			Criteria query = session.createCriteria(User.class);
			ScrollableResults scroll = query.scroll();
			// 滚动到最后一行
			scroll.last();
			// 一共多少条数据
			int n = scroll.getRowNumber() + 1;
			System.out.println(n);
			int num = 10;
			if (n % num != 0) {
				pages = n / num + 1;
			}else {
				pages = n / num;
			}
		} finally {
			session.close();
		}
		return pages;
	}

	/* (non-Javadoc)
	 * @see com.yongxin.dao.UserDao#checkUserPsw(int, java.lang.String)
	 */
	@Override
	public List<User> checkUserPsw(int id, String oldPassword) {
		Session session = this.getSession();
		List<User> userList = new ArrayList<>();
		try {
			List<Object> objects= new ArrayList<Object>();
			objects.add(id);
			objects.add(oldPassword);
			Query q = session.createQuery("from User where id=? and password=?");
			if (objects != null && objects.size() > 0) {
				for (int i = 0; i < objects.size(); i++) {
					q.setParameter(i, objects.get(i));
				}
			}
			userList = q.list();
		}finally {
			session.close();
		}
		return userList;
	}

	/* (non-Javadoc)
	 * @see com.yongxin.dao.UserDao#updateUserPsw(int, java.lang.String)
	 */
	@Override
	public Integer updateUserPsw(int id, String newPassword) {
		// TODO Auto-generated method stub
		Session session = this.getSession();
		int result = 0;
		try {
			List<Object> objects = new ArrayList<Object>();
			objects.add(newPassword);
			objects.add(id);
			org.hibernate.Query query = session.createQuery("update User set password=? where id=?")
					.setParameter(0, objects);
			if (objects != null && objects.size() > 0) {
				for (int i = 0; i < objects.size(); i++) {
					query.setParameter(i, objects.get(i));
				}
			}
			result = query.executeUpdate();
		}finally {
			session.close();
		}

		return result;
	}
	@Override
	public Integer updateUserInfo(int id, String name) {
		// TODO Auto-generated method stub
		Session session = this.getSession();
		int result = 0;
		try {
			List<Object> objects = new ArrayList<Object>();
			objects.add(name);
			objects.add(id);
			org.hibernate.Query query = session.createQuery("update User set userName=? where id=?")
					.setParameter(0, objects);
			if (objects != null && objects.size() > 0) {
				for (int i = 0; i < objects.size(); i++) {
					query.setParameter(i, objects.get(i));
				}
			}
			result = query.executeUpdate();
		}finally {
			session.close();
		}

		return result;
	}
	/* (non-Javadoc)
	 * @see com.yongxin.dao.UserDao#updateUserGrade(int, int)
	 */
	@Override
	public Integer updateUserGrade(int id, int userGradeId) {
		Session session = this.getSession();
		int result = 0;
		try {
			List<Object> objects = new ArrayList<Object>();
			objects.add(userGradeId);
			objects.add(id);
			org.hibernate.Query query = session.createQuery("update User set userGradeId=? where id=?")
					.setParameter(0, objects);
			if (objects != null && objects.size() > 0) {
				for (int i = 0; i < objects.size(); i++) {
					query.setParameter(i, objects.get(i));
				}
			}
			result = query.executeUpdate();
		}finally {
			session.close();
		}

		return result;
	}

	/**
	 * 分页查询
	 * @param permission
	 * @param num
	 * @return
	 */
	@Override
	public List<User> getUserListByPermission(int permission,int num) {
		// TODO Auto-generated method stub
		Session session = this.getSession();
		List<User> userList = new ArrayList<>();
		try {
			/*
			 * 分页查询步骤
			 */
//			UserGrade userGrade = (UserGrade) session.createQuery("from UserGrade where userGrade=?").setParameter(0, grade).uniqueResult();
			List<Object> param= new ArrayList<Object>();
			param.add(permission);
			org.hibernate.Query q = session.createQuery("from User where permission = ?");
			if (param != null && param.size() > 0) {
				for (int j = 0; j < param.size(); j++) {
					q.setParameter(j, param.get(j));
				}
			}
			int nu = 10;
			int begin = (num - 1) * nu;
			userList= q.setFirstResult(begin).setMaxResults(nu).list();
		}finally {
			session.close();
		}
		return userList;
	}

	/**
	 * 获取页数
	 * @param permission
	 * @return
	 */
	@Override
	public int getPageNumByPermission(int permission) {
		Session session = this.getSession();
		List<Object> param= new ArrayList<Object>();
		param.add(permission);
		org.hibernate.Query q = session.createQuery("from User where permission = ?");
		int pages = 0;
		try {
			if (param != null && param.size() > 0) {
				for (int j = 0; j < param.size(); j++) {
					q.setParameter(j, param.get(j));
				}
			}
			int num = 10;
			int n = q.list().size();

			if (n % num != 0) {
				pages = n / num + 1;
			}else {
				pages = n / num;
			}
		} finally {
			session.close();
		}
		return pages;

	}

	@Override
	public Integer updateUserPermission(int id, int permission) {
		// TODO Auto-generated method stub
		Session session = this.getSession();
		int result = 0;
		try {
			List<Object> objects = new ArrayList<Object>();
			objects.add(permission);
			objects.add(id);
			org.hibernate.Query query = session.createQuery("update User set permission=? where id=?")
					.setParameter(0, objects);
			if (objects != null && objects.size() > 0) {
				for (int i = 0; i < objects.size(); i++) {
					query.setParameter(i, objects.get(i));
				}
			}
			result = query.executeUpdate();
		}finally {
			session.close();
		}

		return result;
	}

	/**
	 * 是否通过客户注册审核，返回1通过审核
	 * @param id
	 * @param permission
	 * @return
	 */
	@Override
	public Integer isOrNotAgreeAdd(int id, int permission) {
		Session session = this.getSession();
		int result = 0;
		try {
			List<Object> objects= new ArrayList<Object>();
			objects.add(permission);
			objects.add(id);
			org.hibernate.Query query = session.createQuery("update User set permission = ? where id=?")
					.setParameter(0, objects);
			if (objects!=null&&objects.size()>0) {
				for (int i = 0; i < objects.size(); i++) {
					query.setParameter(i, objects.get(i));
				}
			}
			result = query.executeUpdate();
		}finally {
			session.close();
		}
		return result;
	}
}
