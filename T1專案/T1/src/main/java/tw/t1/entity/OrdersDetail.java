package tw.t1.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@Entity
@NoArgsConstructor
@Table(name = "orderdetail")
public class OrdersDetail {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "order_id")
	private long orderId;

	@Column(name = "pay_method")
	private String payMethod;

	@Column(name = "product_id")
	private int productId;

	@Column(name = "actual_price")
	private int actualPrice = 0;

	@Column(name = "quantity")
	private int quantity;

	@Transient
	private String productName;

	@Transient
	private String orderTime;

	@Transient
	private int singlePrice;

	@Transient
	private int totalPrice;
	
	@Transient
	private int fee;

	public OrdersDetail(int id, long orderId, int productId, int quantity, String payMethod, String productName,
			String orderTime, int singlePrice, int actualPrice, int totalPrice) {

		this.id = id;
		this.orderId = orderId;
		this.productId = productId;
		this.quantity = quantity;
		this.payMethod = payMethod;
		this.productName = productName;
		this.orderTime = orderTime;
		this.singlePrice = singlePrice;
		this.totalPrice = totalPrice;
		this.actualPrice = actualPrice;

	}

}
