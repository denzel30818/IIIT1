package tw.t1.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
@Entity
@Table(name = "webmanager")
public class Manager {

	@Id
	@Column(name = "managerID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int managerID;

	@Column(name = "managerAccount")
	private String managerAccount;

	@Column(name = "managerPassword")
	private String managerPassword;

	@Column(name = "managerName")
	private String managerName;

	@Column(name = "managerPhotoUri")
	private String managerPhotoUri;
}
