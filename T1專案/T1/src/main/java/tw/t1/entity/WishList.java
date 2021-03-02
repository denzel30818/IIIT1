package tw.t1.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
@Entity
@Table(name = "wishlist")
public class WishList {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "wish_id")
	private int wish_id;

	@Column(name = "user_id")
	private int user_id;

	@Column(name = "p_id")
	private int p_id;

	public WishList(int user_id, int p_id) {
		this.user_id = user_id;
		this.p_id = p_id;
	}

}
