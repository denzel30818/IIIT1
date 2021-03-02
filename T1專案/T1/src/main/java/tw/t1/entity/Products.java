package tw.t1.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "products")
public class Products {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "p_id")
	private int p_id;

	@Column(name = "p_name")
	private String p_name;

	@Column(name = "sub_name")
	private String sub_name;

	@Column(name = "category")
	private String category;

	@Column(name = "publisher")
	private String publisher;

	@Column(name = "supplier")
	private String supplier;

	@Column(name = "release_date")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private String release_date;

	@Column(name = "sales_volume")
	private int sales_volume;

	@Column(name = "price")
	private int price;

	@Column(name = "quantity")
	private int quantity;

	@Column(name = "description")
	private String description;

	@Column(name = "update_time")
	private String update_time;

	@Column(name = "photo_uri")
	private String photo_uri;

	@Column(name = "mac")
	private String mac;

	@Column(name = "windows")
	private String windows;

	@Column(name = "discount")
	private double discount = 1.0;
	
	@Column(name = "forum_id")
	private int forum_id = 0;

	public Products(String p_name, String sub_name, String category, String publisher, String supplier,
			String release_date, int sales_volume, int price, int quantity, String description, String update_time,
			String photo_uri, String mac, String windows,int forum_id) {
		super();
		this.p_name = p_name;
		this.category = category;
		this.publisher = publisher;
		this.supplier = supplier;
		this.release_date = release_date;
		this.sales_volume = sales_volume;
		this.price = price;
		this.quantity = quantity;
		this.description = description;
		this.update_time = update_time;
		this.photo_uri = photo_uri;
		this.sub_name = sub_name;
		this.mac = mac;
		this.windows = windows;
		this.forum_id = forum_id;
	}

	@Transient
	public String getPhotosImagePath() {
		if (photo_uri == null || p_id == 0)
			return null;
		return "/images/products/" + p_id + "/" + photo_uri;
	}

	@Transient
	public String getDeleteImagePath() {
		if (photo_uri == null || p_id == 0)
			return null;
		return "/src/main/resources/static/images/products/" + p_id;
	}

}
