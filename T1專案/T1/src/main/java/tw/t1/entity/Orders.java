package tw.t1.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
public class Orders {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "generatorId")
	@GenericGenerator(name = "generatorId", strategy = "tw.t1.generator.generator")
	@Column(name = "id")
	private Long id;

	@Column(name = "user_id")
	private int userId;

	@Column(name = "address")
	private String address;

	@Column(name = "receiver")
	private String receiver;

	@Column(name = "receiver_phone")
	private String receiverPhone;

	@Column(name = "fee")
	private int fee;

	@Column(name = "status")
	private String status = "訂單處理中";

	@Column(name = "order_time")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private String orderTime;

	@Transient
	private String payMethod;

	@Transient
	private int OrdTotalPrice;

	@Transient
	private String[] photo_uri;

	@Transient
	private String productName;

	@Transient
	private int actualPrice;

	public Orders(int userId, String address, String receiver, String receiverPhone, String orderTime, String payMethod,
			int OrdTotalPrice, int fee, String status) {
		super();
		this.userId = userId;
		this.address = address;
		this.receiver = receiver;
		this.receiverPhone = receiverPhone;
		this.orderTime = orderTime;
		this.payMethod = payMethod;
		this.OrdTotalPrice = OrdTotalPrice;
		this.fee = fee;
		this.status = status;

	}

	public Orders(Long id, int userId, String address, String receiver, String receiverPhone, String orderTime,
			String payMethod, int OrdTotalPrice, int fee, String status) {
		super();
		this.id = id;
		this.userId = userId;
		this.address = address;
		this.receiver = receiver;
		this.receiverPhone = receiverPhone;
		this.orderTime = orderTime;
		this.payMethod = payMethod;
		this.OrdTotalPrice = OrdTotalPrice;
		this.fee = fee;
		this.status = status;
	}

}
