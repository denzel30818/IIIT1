package tw.t1.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tw.t1.dao.ProductsRepository;
import tw.t1.entity.Products;
import tw.t1.service.ProductsService;

@Service
public class ProductsServiceImpl implements ProductsService {

	@Autowired
	private ProductsRepository productsRepository;

	// 必須有此參數建構子
	public ProductsServiceImpl(ProductsRepository productsRepository) {
		this.productsRepository = productsRepository;
	}

	@Override
	public List<Products> findAll() {

		return productsRepository.findAll();
	}

	@Override
	public Products findById(int Id) {
		Optional<Products> result = productsRepository.findById(Id);
		Products products = null;
		if (result.isPresent()) {
			products = result.get();
		} else {
			throw new RuntimeException("找不到此ID遊戲" + Id);
		}

		return products;
	}

	@Override
	public void save(Products products) {
		productsRepository.save(products);

	}

	@Override
	public void deleteById(int Id) {
		productsRepository.deleteById(Id);
	}

	@Override
	public long countByCategory(String category) {
		return productsRepository.countByCategory(category);
	}

	@Override
	public long countBySearch(String p_name) {
		return productsRepository.countBySearch(p_name);
	}

	@Override
	public long count() {
		return productsRepository.count();
	}

	@Override
	public long countByMac() {
		return productsRepository.countByMac();
	}

	@Override
	public List<Products> findByMac() {
		return productsRepository.findByMac();
	}

	@Override
	public long countByWindows() {
		return productsRepository.countByWindows();
	}

	@Override
	public List<Products> findByWindows() {
		return productsRepository.findByWindows();
	}

	@Override
	public List<Products> findByPnameContaining(String p_name) {
		return productsRepository.findByPnameContaining(p_name);
	}

	@Override
	public List<Products> findProductsDateDesc() {
		return productsRepository.findProductsDateDesc();
	}

	@Override
	public List<Products> findProductsByCategory(String category) {
		return productsRepository.findProductsByCategory(category);
	}

	@Override
	public List<Products> findAllOrderByDate() {
		return productsRepository.findAllOrderByDate();
	}

	@Override
	public List<Products> findByCategoryOrderByDate(String category) {
		return productsRepository.findByCategoryOrderByDate(category);
	}

	@Override
	public List<Products> findAllOrderBySales() {
		return productsRepository.findAllOrderBySales();
	}

	@Override
	public List<Products> findByCategoryOrderBySales(String category) {
		return productsRepository.findByCategoryOrderBySales(category);
	}

	@Override
	public List<Products> findAllOrderByPriceDesc() {
		return productsRepository.findAllOrderByPriceDesc();
	}

	@Override
	public List<Products> findByCategoryOrderByPriceDesc(String category) {
		return productsRepository.findByCategoryOrderByPriceDesc(category);
	}

	@Override
	public List<Products> findAllOrderByPriceAsc() {
		return productsRepository.findAllOrderByPriceAsc();
	}

	@Override
	public List<Products> findByCategoryOrderByPriceAsc(String category) {
		return productsRepository.findByCategoryOrderByPriceAsc(category);
	}

	@Override
	public List<Products> findBySearchOrderByDate(String p_name) {
		return productsRepository.findBySearchOrderByDate(p_name);
	}

	@Override
	public List<Products> findBySearchOrderBySales(String p_name) {
		return productsRepository.findBySearchOrderBySales(p_name);
	}

	@Override
	public List<Products> findBySearchOrderByPriceDesc(String p_name) {
		return productsRepository.findBySearchOrderByPriceDesc(p_name);
	}

	@Override
	public List<Products> findBySearchOrderByPriceAsc(String p_name) {
		return productsRepository.findBySearchOrderByPriceAsc(p_name);
	}

	@Override
	public List<Products> findByMacOrderByDate() {
		return productsRepository.findByMacOrderByDate();
	}

	@Override
	public List<Products> findByWinOrderByDate() {
		return productsRepository.findByWinOrderByDate();
	}

	@Override
	public List<Products> findByMacOrderBySales() {
		return productsRepository.findByMacOrderBySales();
	}

	@Override
	public List<Products> findByWinOrderBySales() {
		return productsRepository.findByWinOrderBySales();
	}

	@Override
	public List<Products> findByMacOrderByPriceDesc() {
		return productsRepository.findByMacOrderByPriceDesc();
	}

	@Override
	public List<Products> findByWinOrderByPriceDesc() {
		return productsRepository.findByWinOrderByPriceDesc();
	}

	@Override
	public List<Products> findByMacOrderByPriceAsc() {
		return productsRepository.findByMacOrderByPriceAsc();
	}

	@Override
	public List<Products> findByWinOrderByPriceAsc() {
		return productsRepository.findByWinOrderByPriceAsc();
	}

	@Override
	public long countSaleProduct() {
		return productsRepository.countSaleProduct();
	}

	@Override
	public List<Products> findSaleProduct() {
		return productsRepository.findSaleProduct();
	}

	@Override
	public List<Products> findSaleProductOrderByDate() {
		return productsRepository.findSaleProductOrderByDate();
	}

	@Override
	public List<Products> findSaleProductOrderBySales() {
		return productsRepository.findSaleProductOrderBySales();
	}

	@Override
	public List<Products> findSaleProductOrderByPriceDesc() {
		return productsRepository.findSaleProductOrderByPriceDesc();
	}

	@Override
	public List<Products> findSaleProductOrderByPriceAsc() {
		return productsRepository.findSaleProductOrderByPriceAsc();
	}

	@Override
	public Products findProductsById(int p_id) {
		return productsRepository.findProductsById(p_id);
	}

}
