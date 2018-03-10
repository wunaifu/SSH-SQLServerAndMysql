/**
 * 
 */
package com.yongxin.service;

import java.util.List;

import com.yongxin.entity.UserGrade;

/**
 * @author Administrator 2017年12月28日
 *
 */
public interface UserGradeService {
	public UserGrade getUserGradeById(int id);
	public UserGrade getUserGradeByType(int type);
	public int addUserGrade(UserGrade userGrade);
	public void deleteUserGradeById(int id);
	public List<UserGrade> getUserGrades();
	public boolean update(UserGrade userGrade);
}
