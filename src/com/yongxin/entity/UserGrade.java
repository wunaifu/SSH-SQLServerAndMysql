/**
 * 
 */
package com.yongxin.entity;

import javax.faces.bean.CustomScoped;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * @author Administrator 2017年12月27日
 *
 */
@Entity 
@Table(name = "usergrade")
public class UserGrade {
	@Id
	@Column(name = "id", nullable = false, unique = true)
	@GenericGenerator(name = "generator", strategy = "uuid")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	//等级名字
	@Column(name = "userGradeName",nullable = false, length = 16)
	private String userGradeName;
	//等级 1,2,3,4,5
	@Column(name = "userGrade",nullable = false, length = 16)
	private int userGrade;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUserGradeName() {
		return userGradeName;
	}
	public void setUserGradeName(String userGradeName) {
		this.userGradeName = userGradeName;
	}
	public int getUserGrade() {
		return userGrade;
	}
	public void setUserGrade(int userGrade) {
		this.userGrade = userGrade;
	}
	@Override
	public String toString() {
		return "UserGrade [id=" + String.valueOf(id) + ", userGradeName=" + userGradeName + ", userGrade=" + String.valueOf(userGrade) + "]";
	}
	
	
}
