package tw.t1.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Entity
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "feedback")
public class Feedback {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "user_name")
	private String user_name;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "type")
	private String type;
	
	@Column(name = "content")
	private String content;
	
	@Transient
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");	
	
	@Column(name = "date")
	private String date=sdf.format(new Date());
	
	@Column(name = "manager_name")
	private String manager_name;
	
	@Column(name = "reply")
	private String reply;
	
	@Column(name = "state")
	private String state="待處理";

}
