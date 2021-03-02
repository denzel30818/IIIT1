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

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Entity
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "announcement")
public class Announcement {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "manager_id")
	private int managerID;

	@Column(name = "manager_Name")
	private String managerName;

	@Column(name = "title")
	private String title;

	@Column(name = "content")
	private String content;

	@Column(name = "category")
	private String category;

	@Column(name = "forum_id")
	private int forumID;

	@Column(name = "forum_Name")
	private String forumName;

	@Column(name = "photo_uri")
	private String photoUri;

	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "update_time")
	private Date updateTime;

	@Column(name = "update_millisecond")
	private long updateMillisecond;

	@Column(name = "passtime")
	private String passtime;

	@Column(name = "art_type")
	private String art_type = "公告";

}
