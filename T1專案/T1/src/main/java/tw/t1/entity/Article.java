package tw.t1.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

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
@Table(name = "article")
public class Article {

	@Id
	@Column(name = "art_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "userID")
	private int userID;

	@Column(name = "nickName")
	private String nickName;

	@Column(name = "title")
	private String title;

	@Column(name = "body")
	private String body;

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
	private Date update_time;

	@Column(name = "update_millisecond")
	private long update_millisecond;

	@Column(name = "passtime")
	private String passtime;

	@Column(name = "likes")
	private int likes = 0;

	@Column(name = "art_type")
	private String type;

	@Transient
	private String add_time;

	@Transient
	public String getImagePath() {
		if (photoUri == null || id == 0)
			return null;

		return "/images/articles/upload" + id + "/" + photoUri;
	}
}
