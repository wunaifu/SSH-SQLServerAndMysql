/**
 * 
 */
package com.yongxin.dao;

import java.util.List;

import com.yongxin.entity.User;
import com.yongxin.entity.UserGrade;

/**
 * @author Administrator 2017年12月28日
 *
 */
public interface UserGradeDao {
	public UserGrade getUserGradeById(int id);
	public UserGrade getUserGradeByType(int type);
	public int addUserGrade(UserGrade userGrade);
	public void deleteUserGradeById(int id);
	public List<UserGrade> getUserGrades();
	public int update(UserGrade userGrade);
}
