/**
 * 
 */
package com.yongxin.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import java.util.Date;

/**
 * @author Administrator 2017年12月27日
 *
 */
@Entity
@Table(name="user")
public class User {
	@Id
	@Column(name = "id", nullable = false, unique = true)
	@GenericGenerator(name = "generator", strategy = "uuid")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	//登录账号
	@Column(name = "userAccount", nullable = false, length = 16, unique = true)
	private String userAccount;

	@Column(name = "password",nullable = false, length = 64)
	private String password;

	@Column(name = "userName",nullable = false,length = 16)
	private String userName;

	//用户权限，主要是客户注册审核，
	// 0：客户未通过注册，2：通过注册(客户) 1：管理员 3：供应商  4：客户的员工  -1：表示已删除
	@Column(name = "permission",nullable = false)
	private int permission;
	//用户等级
	@ManyToOne(targetEntity = UserGrade.class)
	@JoinColumn(name = "userGradeId")
	private UserGrade UserGrade;

	//permission不为 1 的用户使用权限到期时间
	@Column(name = "permissionTime",nullable = true)
	private Date permissionTime;

	public Date getPermissionTime() {
		return permissionTime;
	}

	public void setPermissionTime(Date permissionTime) {
		this.permissionTime = permissionTime;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getPermission() {
		return permission;
	}

	public void setPermission(int permission) {
		this.permission = permission;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUserAccount() {
		return userAccount;
	}
	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}
	public UserGrade getUserGrade() {
		return UserGrade;
	}
	public void setUserGrade(UserGrade userGrade) {
		UserGrade = userGrade;
	}

	@Override
	public String toString() {
		return "User{" +
				"id=" + id +
				", userAccount='" + userAccount + '\'' +
				", password='" + "*****" + '\'' +
				", userName='" + userName + '\'' +
				", permission=" + permission +
				", UserGrade=" + UserGrade +
				", permissionTime=" + permissionTime +
				'}';
	}
}
