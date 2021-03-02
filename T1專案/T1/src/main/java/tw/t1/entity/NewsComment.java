package tw.t1.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Entity
@Data
@Table(name = "newscomment")
public class NewsComment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "news_comment_id")
	private int newsCommentId;
	
	@Column(name = "content")
	private String content;
	
	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "news_id", referencedColumnName = "news_id")
	private News news;
	
	@Column(name = "nickname")
	private String nickName;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "post_time")
	private Date posttime;
	
	@Column(name = "memberPhotoUri")
	private String memberPhotoUri;
}
