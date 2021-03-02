package tw.t1.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "articlelike")
public class ArticleLike {
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "user_id")
	private int user_id;
	
	@Column(name = "article_id")
	private int article_id;


	public ArticleLike() {
		super();
	}
	
	
	public ArticleLike(int id, int user_id, int article_id) {
		super();
		this.id = id;
		this.user_id = user_id;
		this.article_id = article_id;
	}

	
	

}
