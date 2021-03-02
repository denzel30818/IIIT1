package tw.t1.entity;

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
@Table(name = "forumtype")
public class ForumType {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "forum_id")
	private int forumID;

	@Column(name = "forum_name")
	private String forumName;

	@Column(name = "category")
	private String category;

	@Column(name = "forum_content")
	private String forumContent;

	@Column(name = "photo_uri")
	private String photoUri;

	@Transient
	public String getPhotosImagePath() {
		if (photoUri == null || forumID == 0)
			return null;
		return "/images/forum/" + forumID + "/" + photoUri;
	}

	@Transient
	public String getDeleteImagePath() {
		if (photoUri == null || forumID == 0)
			return null;
		return "/src/main/resources/static/images/forum/" + forumID;
	}

}
