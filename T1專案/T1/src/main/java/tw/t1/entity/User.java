package tw.t1.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.ToString;

@Data
@Table(name = "Users")
@Entity
@ToString
public class User {

	@Id
	@Column(name = "userID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int userID;

	@Column(name = "account")
	private String account;

	@Column(name = "userPassword")
	private String userPassword;

	@Column(name = "nickName")
	private String nickName;

	@Column(name = "gender")
	private String gender;

	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "birthDate")
	private Date birthDate;

	@Column(name = "email")
	private String email;

	@Column(name = "point")
	private int point;

	@Column(name = "memberPhotoUri")
	private String memberPhotoUri = "/images/member/joker.png";

	@Column(name = "code")
	private String code;

	@Column(name = "active")
	private String active = "否";

	@Column(name = "banned")
	private String banned = "n";

	@Column(name = "congratulated")
	private String congratulated = "n";
}
