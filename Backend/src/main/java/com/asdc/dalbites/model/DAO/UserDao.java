package com.asdc.dalbites.model.DAO;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents a user entity in the database.
 */
@Entity
@Getter
@Setter
@Table(name = "student")
public class UserDao {
	@Id
	@Column(name="user_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userId;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getIs_deleted() {
		return is_deleted;
	}

	public void setIs_deleted(int is_deleted) {
		this.is_deleted = is_deleted;
	}

	public Date getCreated_at() {
		return created_at;
	}

	public void setCreated_at(Date created_at) {
		this.created_at = created_at;
	}

	public Date getUpdated_at() {
		return updated_at;
	}

	public void setUpdated_at(Date updated_at) {
		this.updated_at = updated_at;
	}

	public LoginDao getLoginDao() {
		return loginDao;
	}

	public void setLoginDao(LoginDao loginDao) {
		this.loginDao = loginDao;
	}

	@Column(name="name")
	private String name;

	@Column(name="email")
	private String email;

	@Column(name="is_deleted")
	private int is_deleted = 0;

	@Column(name = "created_at")
	@Temporal(TemporalType.TIMESTAMP)
	@CreationTimestamp
	private Date created_at;

	@Column(name = "updated_at")
	@Temporal(TemporalType.TIMESTAMP)
	@CreationTimestamp
	private Date updated_at;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "login_id")
	@JsonIgnore
	private LoginDao loginDao;

	@OneToMany(mappedBy = "user")
	@JsonIgnore
	private List<OrderDao> orders;

	/**
	 * Default constructor for UserDao.
	 */
	public UserDao() {
	}

	/**
	 * Constructor for UserDao with specified name and email.
	 *
	 * @param name  The name of the user.
	 * @param email The email of the user.
	 */
	public UserDao(String name, String email) {
		this.name = name;
		this.email = email;
	}
}
