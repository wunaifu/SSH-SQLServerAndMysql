/**
 * 
 */
package com.yongxin.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yongxin.dao.UserGradeDao;
import com.yongxin.entity.UserGrade;
import com.yongxin.service.UserGradeService;

/**
 * @author Administrator 2017年12月28日
 *
 */
@Service
public class UserGradeServiceImpl implements UserGradeService{
	@Autowired
	UserGradeDao userGradeDao;
	/* (non-Javadoc)
	 * @see com.yongxin.service.UserGradeService#getUserGradeById(int)
	 */
	@Override
	public UserGrade getUserGradeById(int id) {
		// TODO Auto-generated method stub
		return userGradeDao.getUserGradeById(id);
	}
	@Override
	public UserGrade getUserGradeByType(int type) {
		// TODO Auto-generated method stub
		return userGradeDao.getUserGradeByType(type);
	}
	/* (non-Javadoc)
	 * @see com.yongxin.service.UserGradeService#addUserGrade(com.yongxin.entity.UserGrade)
	 */
	@Override
	public int addUserGrade(UserGrade userGrade) {
		// TODO Auto-generated method stub
		return userGradeDao.addUserGrade(userGrade);
	}

	/* (non-Javadoc)
	 * @see com.yongxin.service.UserGradeService#deleteUserGradeById(int)
	 */
	@Override
	public void deleteUserGradeById(int id) {
		// TODO Auto-generated method stub
		userGradeDao.deleteUserGradeById(id);
	}

	/* (non-Javadoc)
	 * @see com.yongxin.service.UserGradeService#getUserGrades()
	 */
	@Override
	public List<UserGrade> getUserGrades() {
		// TODO Auto-generated method stub
		return userGradeDao.getUserGrades();
	}

	/* (non-Javadoc)
	 * @see com.yongxin.service.UserGradeService#update(com.yongxin.entity.UserGrade)
	 */
	@Override
	public boolean update(UserGrade userGrade) {
		// TODO Auto-generated method stub
		if(userGradeDao.update(userGrade) == 1){
			return true;
		}
		return false;
	}

}
