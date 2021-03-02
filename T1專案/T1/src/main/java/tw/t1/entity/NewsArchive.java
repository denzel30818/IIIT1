package tw.t1.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Entity
@Table(name = "newsarchive")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class NewsArchive {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "newsArchive_id")
	private int id;

	@Column(name = "newsArchive_manager")
	private String manager;

	@Column(name = "newsArchive_title")
	private String title;

	@Column(name = "newsArchive_article")
	private String article;

	@Column(name = "newsArchive_type")
	private String type;

	@Column(name = "newsArchive_photo")
	private String newsPhoto;

	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "newsArchive_updateTime")
	private Date postTime;
	
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "news")
	private List<NewsComment> newsCommentList;

}
