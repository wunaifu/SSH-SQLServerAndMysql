/**
 * 
 */
package com.yongxin.dao.impl;

import com.yongxin.dao.UserGradeDao;
import com.yongxin.dao.UsertestDao;
import com.yongxin.entity.UserGrade;
import com.yongxin.entityerp.Usertest;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator 2017年12月28日
 *
 */
@Repository
public class UsertestImpl implements UsertestDao{
	@Resource
	private SessionFactory sessionFactorySQL;
	private Session getSession() {
		return sessionFactorySQL.openSession();
	}

	@Override
	public List<Usertest> getUsertestList() {
		// TODO Auto-generated method stub
		Session session = this.getSession();
		List<Usertest> userGradeList = new ArrayList<>();
		try {
			userGradeList = session.createCriteria(Usertest.class).list();
			System.out.println("jklllllllllllllllllkkkkkkkkkkkkkkkkkkkkk=========="+userGradeList.toString());
		} finally {
            session.close();
		}
		return userGradeList;
	}

}
