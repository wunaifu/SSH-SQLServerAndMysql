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

/**
 * @author Administrator 2017年12月27日
 *
 */
@Entity
@Table(name = "userrelation")
public class UserRelation {
	@Id
	@Column(name = "id", nullable = false, unique = true)
	@GenericGenerator(name = "generator", strategy = "uuid")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	//上级
	@ManyToOne(targetEntity = User.class)
	@JoinColumn(name = "userFId",nullable = false)
	private User userF;
	//下级
	@ManyToOne(targetEntity = User.class)
	@JoinColumn(name = "userSId",nullable = false)
	private User userS;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public User getUserF() {
		return userF;
	}
	public void setUserF(User userF) {
		this.userF = userF;
	}
	public User getUserS() {
		return userS;
	}
	public void setUserS(User userS) {
		this.userS = userS;
	}
	@Override
	public String toString() {
		return "UserRelation [id=" + id + ", userF=" + userF + ", userS=" + userS + "]";
	}
	
	
}
