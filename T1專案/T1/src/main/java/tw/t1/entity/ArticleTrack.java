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

import lombok.Data;

@Entity
@Data
@Table(name = "articletrack")
public class ArticleTrack {
	
	
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "user_id")
	private int user_id;

	@Column(name = "article_id")
	private int article_id;

	@Column(name = "article_title")
	private String article_title;
	
	@Column(name = "article_category")
	private String article_category;
	
	@Column(name = "article_forumid")
	private int article_forumid;
	
	@Column(name = "article_forum")
	private String article_forum;
	
	@Transient
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");	
	
	@Column(name = "add_time")
	private String add_time=sdf.format(new Date());


	public ArticleTrack() {
		super();
	}
	
	
	public ArticleTrack(int id, int user_id, int article_id, String article_title, String article_category,
			int article_forumid, String article_forum) {
		super();
		this.id = id;
		this.user_id = user_id;
		this.article_id = article_id;
		this.article_title = article_title;
		this.article_category = article_category;
		this.article_forumid = article_forumid;
		this.article_forum = article_forum;
	}




	
	

}
