/**
 * 
 */
package com.yongxin.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.yongxin.dao.UserGradeDao;
import com.yongxin.entity.User;
import com.yongxin.entity.UserGrade;

/**
 * @author Administrator 2017年12月28日
 *
 */
@Repository
public class UserGradeDaoImpl implements UserGradeDao{
	@Resource
	private SessionFactory sessionFactory;
	private Session getSession() {
		return sessionFactory.openSession();
	}
	/* (non-Javadoc)
	 * @see com.yongxin.dao.UserGradeDao#getUserGradeById(int)
	 */
	@Override
	public UserGrade getUserGradeById(int id) {
		// TODO Auto-generated method stub
		Session session = this.getSession();
		UserGrade userGrade = new UserGrade();
		try {
			userGrade = (UserGrade) session.createQuery("from UserGrade where id=?").setParameter(0, id).uniqueResult();
		} finally {
			session.close();
		}
		return userGrade;
	}

	@Override
	public UserGrade getUserGradeByType(int type) {
		// TODO Auto-generated method stub
		Session session = this.getSession();
		UserGrade userGrade = new UserGrade();
		try {
			userGrade = (UserGrade) session.createQuery("from UserGrade where userGrade=?").setParameter(0, type).uniqueResult();
		} finally {
			session.close();
		}
		return userGrade;
	}
	/* (non-Javadoc)
	 * @see com.yongxin.dao.UserGradeDao#addUserGrade(com.yongxin.entity.UserGrade)
	 */
	@Override
	public int addUserGrade(UserGrade userGrade) {
		Session session = this.getSession();
		try {
			session.save(userGrade);
		} finally {
            session.close();
		}
		return 1;
	}
	/* (non-Javadoc)
	 * @see com.yongxin.dao.UserGradeDao#deleteUserGradeById(int)
	 */
	@Override
	public void deleteUserGradeById(int id) {
		// TODO Auto-generated method stub
		Session session = this.getSession();
		try {
			session.createQuery("delete UserGrade where id=?").setParameter(0, id).executeUpdate();
		} finally {
            session.close();
		}
	}
	/* (non-Javadoc)
	 * @see com.yongxin.dao.UserGradeDao#getUserGrades()
	 */
	@Override
	public List<UserGrade> getUserGrades() {
		// TODO Auto-generated method stub
		Session session = this.getSession();
		List<UserGrade> userGradeList = new ArrayList<>();
		try {
			userGradeList = session.createCriteria(UserGrade.class).list();
		} finally {
            session.close();
		}
		return userGradeList;
	}
	/* (non-Javadoc)
	 * @see com.yongxin.dao.UserGradeDao#update(com.yongxin.entity.UserGrade)
	 */
	@Override
	public int update(UserGrade userGrade) {
		Session session = this.getSession();
		Integer result = 0;
		try {
			List<Object> objects= new ArrayList<Object>();
			String hql;
			objects.add(userGrade.getUserGradeName());
			objects.add(userGrade.getId());
			hql = "update UserGrade set userGradeName= ? where id=?";
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
}
