package tw.t1.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import tw.t1.entity.Products;

public interface ProductsRepository extends JpaRepository<Products, Integer> {

	@Query(value = "SELECT * FROM products ORDER BY update_time DESC", nativeQuery = true)
	public List<Products> findProductsDateDesc();

	// 分類商品分頁
	@Query(value = "SELECT * FROM products where category = ?1", nativeQuery = true)
	public List<Products> findProductsByCategory(String category);

	// 分類商品總數
	@Query(value = "SELECT COUNT(*) FROM products where category = ?1", nativeQuery = true)
	public long countByCategory(String category);

	// 遊戲名稱搜尋商品
	@Query(value = "SELECT * FROM products WHERE UPPER(p_name) LIKE UPPER(CONCAT('%',?1,'%'))", nativeQuery = true)
	public List<Products> findByPnameContaining(String p_name);

	// 遊戲名稱搜尋商品總數
	@Query(value = "SELECT COUNT(*) FROM products WHERE UPPER(p_name) LIKE UPPER(CONCAT('%',?1,'%'))", nativeQuery = true)
	public long countBySearch(String p_name);

	// mac商品總數
	@Query(value = "SELECT COUNT(*) FROM products WHERE mac = 'y'", nativeQuery = true)
	public long countByMac();

	// mac商品
	@Query(value = "SELECT * FROM products WHERE mac = 'y'", nativeQuery = true)
	public List<Products> findByMac();

	// windows商品總數
	@Query(value = "SELECT COUNT(*) FROM products WHERE windows = 'y'", nativeQuery = true)
	public long countByWindows();

	// windows商品
	@Query(value = "SELECT * FROM products WHERE windows = 'y'", nativeQuery = true)
	public List<Products> findByWindows();

	// sale商品
	@Query(value = "SELECT * FROM products where discount != 1", nativeQuery = true)
	public List<Products> findSaleProduct();

	// sale商品
	@Query(value = "SELECT count(*) FROM products where discount != 1", nativeQuery = true)
	public long countSaleProduct();

	// 以下為排序用！！

	// 發行日期新到舊
	// 全部
	@Query(value = "SELECT * FROM products order by release_date desc", nativeQuery = true)
	public List<Products> findAllOrderByDate();

	// 發行日期新到舊
	// 分cate
	@Query(value = "SELECT * FROM products where category = ?1 order by release_date desc", nativeQuery = true)
	public List<Products> findByCategoryOrderByDate(String category);

	// 發行日期新到舊
	// search
	@Query(value = "SELECT * FROM products WHERE UPPER(p_name) LIKE UPPER(CONCAT('%',?1,'%')) order by release_date desc", nativeQuery = true)
	public List<Products> findBySearchOrderByDate(String p_name);

	// 發行日期新到舊
	// mac
	@Query(value = "SELECT * FROM products WHERE mac = 'y' order by release_date desc", nativeQuery = true)
	public List<Products> findByMacOrderByDate();

	// 發行日期新到舊
	// windows
	@Query(value = "SELECT * FROM products WHERE windows = 'y' order by release_date desc", nativeQuery = true)
	public List<Products> findByWinOrderByDate();

	// 發行日期新到舊
	// sale
	@Query(value = "SELECT * FROM products where discount != 1 order by release_date desc", nativeQuery = true)
	public List<Products> findSaleProductOrderByDate();

	// 銷量高到低
	// 全部
	@Query(value = "SELECT * FROM products order by sales_volume desc", nativeQuery = true)
	public List<Products> findAllOrderBySales();

	// 銷量高到低
	// 分cate
	@Query(value = "SELECT * FROM products where category = ?1 order by sales_volume desc", nativeQuery = true)
	public List<Products> findByCategoryOrderBySales(String category);

	// 銷量高到低
	// search
	@Query(value = "SELECT * FROM products where UPPER(p_name) LIKE UPPER(CONCAT('%',?1,'%')) order by sales_volume desc", nativeQuery = true)
	public List<Products> findBySearchOrderBySales(String p_name);

	// 銷量高到低
	// mac
	@Query(value = "SELECT * FROM products  WHERE mac = 'y' order by sales_volume desc", nativeQuery = true)
	public List<Products> findByMacOrderBySales();

	// 銷量高到低
	// windows
	@Query(value = "SELECT * FROM products  WHERE windows = 'y' order by sales_volume desc", nativeQuery = true)
	public List<Products> findByWinOrderBySales();

	// 銷量高到低
	// sale
	@Query(value = "SELECT * FROM products where discount != 1 order by sales_volume desc", nativeQuery = true)
	public List<Products> findSaleProductOrderBySales();

	// 價格高到低
	// 全部
	@Query(value = "SELECT * FROM products order by price*discount desc", nativeQuery = true)
	public List<Products> findAllOrderByPriceDesc();

	// 價格高到低
	// 分cate
	@Query(value = "SELECT * FROM products where category = ?1 order by price*discount desc", nativeQuery = true)
	public List<Products> findByCategoryOrderByPriceDesc(String category);

	// 價格高到低
	// search
	@Query(value = "SELECT * FROM products where UPPER(p_name) LIKE UPPER(CONCAT('%',?1,'%')) order by price*discount desc", nativeQuery = true)
	public List<Products> findBySearchOrderByPriceDesc(String p_name);

	// 價格高到低
	// mac
	@Query(value = "SELECT * FROM products WHERE mac = 'y' order by price*discount desc", nativeQuery = true)
	public List<Products> findByMacOrderByPriceDesc();

	// 價格高到低
	// windows
	@Query(value = "SELECT * FROM products WHERE windows = 'y' order by price*discount desc", nativeQuery = true)
	public List<Products> findByWinOrderByPriceDesc();

	// 價格高到低
	// sale
	@Query(value = "SELECT * FROM products where discount != 1 order by price*discount desc", nativeQuery = true)
	public List<Products> findSaleProductOrderByPriceDesc();

	// 價格低到高
	// 全部
	@Query(value = "SELECT * FROM products order by price*discount asc", nativeQuery = true)
	public List<Products> findAllOrderByPriceAsc();

	// 價格低到高
	// 分cate
	@Query(value = "SELECT * FROM products where category = ?1 order by price*discount asc", nativeQuery = true)
	public List<Products> findByCategoryOrderByPriceAsc(String category);

	// 價格低到高
	// search
	@Query(value = "SELECT * FROM products where UPPER(p_name) LIKE UPPER(CONCAT('%',?1,'%')) order by price*discount asc", nativeQuery = true)
	public List<Products> findBySearchOrderByPriceAsc(String p_name);

	// 價格低到高
	// mac
	@Query(value = "SELECT * FROM products WHERE mac = 'y' order by price*discount asc", nativeQuery = true)
	public List<Products> findByMacOrderByPriceAsc();

	// 價格低到高
	// windows
	@Query(value = "SELECT * FROM products WHERE windows = 'y' order by price*discount asc", nativeQuery = true)
	public List<Products> findByWinOrderByPriceAsc();

	// 價格低到高
	// sale
	@Query(value = "SELECT * FROM products where discount != 1 order by price*discount asc", nativeQuery = true)
	public List<Products> findSaleProductOrderByPriceAsc();

	// ID搜尋商品
	@Query(value = "SELECT * FROM products WHERE p_id = ?1", nativeQuery = true)
	public Products findProductsById(int p_id);

}
