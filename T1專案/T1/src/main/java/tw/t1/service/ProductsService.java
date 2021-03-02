package tw.t1.service;

import java.util.List;

import tw.t1.entity.Products;

public interface ProductsService {

	public List<Products> findAll();

	public Products findById(int Id);

	public void save(Products products);

	public void deleteById(int Id);

	public long countByCategory(String category);

	public List<Products> findByPnameContaining(String p_name);

	public long countBySearch(String p_name);

	public long count();

	public List<Products> findProductsDateDesc();

	public long countByMac();

	public List<Products> findByMac();

	public long countByWindows();

	public List<Products> findByWindows();

	public long countSaleProduct();

	public List<Products> findSaleProduct();

	public List<Products> findProductsByCategory(String category);

	public List<Products> findAllOrderByDate();

	public List<Products> findByCategoryOrderByDate(String category);

	public List<Products> findBySearchOrderByDate(String p_name);

	public List<Products> findByMacOrderByDate();

	public List<Products> findByWinOrderByDate();

	public List<Products> findSaleProductOrderByDate();

	public List<Products> findAllOrderBySales();

	public List<Products> findByCategoryOrderBySales(String category);

	public List<Products> findBySearchOrderBySales(String p_name);

	public List<Products> findByMacOrderBySales();

	public List<Products> findByWinOrderBySales();

	public List<Products> findSaleProductOrderBySales();

	public List<Products> findAllOrderByPriceDesc();

	public List<Products> findByCategoryOrderByPriceDesc(String category);

	public List<Products> findBySearchOrderByPriceDesc(String p_name);

	public List<Products> findByMacOrderByPriceDesc();

	public List<Products> findByWinOrderByPriceDesc();

	public List<Products> findSaleProductOrderByPriceDesc();

	public List<Products> findAllOrderByPriceAsc();

	public List<Products> findByCategoryOrderByPriceAsc(String category);

	public List<Products> findBySearchOrderByPriceAsc(String p_name);

	public List<Products> findByMacOrderByPriceAsc();

	public List<Products> findByWinOrderByPriceAsc();

	public List<Products> findSaleProductOrderByPriceAsc();

	public Products findProductsById(int p_id);
}
