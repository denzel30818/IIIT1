package tw.t1.entity;



import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "cart")
public class Cart {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="product_id")
//	@JoinColumn(name="p_id", referencedColumnName = "p_id")
//	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private int product_id;
	
	@Column(name="user_id") 
	private int user_id;
	
	@Column(name = "quantity")
	private int quantity;
	
	/*----- 增加虛擬欄位存值-----01/28-- */ 
	@Transient
	private String p_name;
	
	@Transient
	private String sub_name;
	
	@Transient
	private int actualPrice;
	
	@Transient
	private String imagePath;
	
	@Transient
	private int totalP;
	/*------------------------------*/
	

	
	public Cart() {
		super();
	}

	public Cart(int id, int product_id, int user_id, int quantity, String p_name,String sub_name, int actualPrice, String imagePath, int totalP) {
		super();
		this.id = id;
		this.product_id = product_id;
		this.user_id = user_id;
		this.quantity = quantity;
		this.p_name = p_name;
		this.sub_name=sub_name;
		this.actualPrice = actualPrice;
		this.imagePath = imagePath;
		this.totalP = totalP;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getProduct_id() {
		return product_id;
	}

	public void setProduct_id(int product_id) {
		this.product_id = product_id;
	}

	public long getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getP_name() {
		return p_name;
	}

	public void setP_name(String p_name) {
		this.p_name = p_name;
	}

	public int getActualPrice() {
		return actualPrice;
	}

	public void setActualPrice(int actualPrice) {
		this.actualPrice = actualPrice;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	
	
	public String getSub_name() {
		return sub_name;
	}

	public void setSub_name(String sub_name) {
		this.sub_name = sub_name;
	}

	public int getTotalP() {
		return totalP;
	}

	public void setTotalP(int totalP) {
		this.totalP = totalP;
	}

	@Override
	public String toString() {
		return "Cart [id=" + id + ", product_id=" + product_id + ", user_id=" + user_id + ", quantity=" + quantity
				+ ", p_name=" + p_name + ", actualPrice=" + actualPrice + ", imagePath=" + imagePath + ", totalP="
				+ totalP + "]";
	}
	

	

	
	
	
}
