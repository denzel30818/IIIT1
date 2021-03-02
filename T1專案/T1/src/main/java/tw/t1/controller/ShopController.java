package tw.t1.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import tw.t1.dao.ProductsRepository;
import tw.t1.entity.Cart;
import tw.t1.entity.ProductType;
import tw.t1.entity.Productcategory;
import tw.t1.entity.Products;
import tw.t1.entity.WishList;
import tw.t1.service.CartService;
import tw.t1.service.ProductCategoryService;
import tw.t1.service.ProductTypeService;
import tw.t1.service.ProductsService;
import tw.t1.service.WishListService;

@Controller
@RequestMapping("/shop")
public class ShopController {

	private ProductsService service;
	private ProductsRepository repo;
	private WishListService wService;
	private ProductTypeService ptService;
	private CartService cartService;
	private ProductCategoryService pcService;

	@Autowired
	public ShopController(ProductsService service, ProductsRepository repo, WishListService wService,
			ProductTypeService ptService, CartService cartService, ProductCategoryService pcService) {
		this.service = service;
		this.repo = repo;
		this.wService = wService;
		this.ptService = ptService;
		this.cartService = cartService;
		this.pcService = pcService;
	}

	// 商城首頁
	@GetMapping("")
	public String mainPage(Model m) {

		// 要改id!!
		int cartnum = (int) cartService.countByUserid(1);
		m.addAttribute("cartnum", cartnum);

		List<Productcategory> cates = pcService.findAll();

		for (Productcategory cate : cates) {
			String category;
			if (cate.getCategory().equals("遊戲週邊")) {
				category = "Physical";
			} else {
				category = cate.getCategory();
			}
			/* 0206家寶 */
			String name = category + "pro";

			List<Products> products = service.findByCategoryOrderBySales(cate.getCategory());

			int total = products.size();
			List<Products> mainproduct = new ArrayList<Products>();

			if (total < 11) {
				for (int i = 0; i < total; i++) {
					mainproduct.add(products.get(i));
				}

			} else {
				for (int i = 0; i < 10; i++) {
					mainproduct.add(products.get(i));
				}
			}
			m.addAttribute(name, mainproduct);

		}

		return "shop/shop-mainPage";
	}

	// 商品清單頁面（全部商品及category分類商品
	@GetMapping("/list")
	public String productCategoryListPage(Model m, HttpServletRequest request) {

		// 要改id!!
		int cartnum = (int) cartService.countByUserid(1);
		m.addAttribute("cartnum", cartnum);

		String sort;
		String cate = request.getParameter("category");
		String withCate = "false";
		int numOfProduct = 15;
		int pg;

		if (request.getParameter("page") == null) {
			pg = 1;
		} else {
			pg = Integer.parseInt(request.getParameter("page"));
		}

		if (request.getParameter("sort") == null) {
			sort = "default";
		} else {
			sort = request.getParameter("sort");
		}

		// 有傳分類參數(分類頁面)
		if (cate != null) {
			withCate = "true";
			if ("physical".equals(cate)) {
				cate = "遊戲週邊";
			}
			int total = (int) service.countByCategory(cate);
			int pgtotal;
			if (total % numOfProduct == 0) {
				pgtotal = total / numOfProduct;
			} else {
				pgtotal = total / numOfProduct + 1;
			}

			m.addAttribute("pgtotal", pgtotal);
			List<Products> products = new ArrayList<Products>();

			switch (sort) {
			case "date":
				products = service.findByCategoryOrderByDate(cate);
				break;
			case "sales":
				products = service.findByCategoryOrderBySales(cate);
				break;
			case "priceA":
				products = service.findByCategoryOrderByPriceAsc(cate);
				break;
			case "priceD":
				products = service.findByCategoryOrderByPriceDesc(cate);
				break;
			case "default":
				products = service.findProductsByCategory(cate);
				break;
			}

			List<Products> pgproducts = new ArrayList<Products>();

			if (pg == 1) {
				if (total < numOfProduct) {
					for (int i = 0; i < total; i++) {
						pgproducts.add(products.get(i));
					}
				} else {
					for (int i = 0; i < numOfProduct; i++) {
						pgproducts.add(products.get(i));
					}
				}

			} else if (pg == pgtotal) {
				for (int i = numOfProduct * (pg - 1); i < total; i++) {
					pgproducts.add(products.get(i));
				}
			} else {
				for (int i = numOfProduct * (pg - 1); i < pg * numOfProduct; i++) {
					pgproducts.add(products.get(i));
				}

			}
			m.addAttribute("products", pgproducts);
			m.addAttribute("page", pg);
			m.addAttribute("category", request.getParameter("category"));
			m.addAttribute("withCate", withCate);
			m.addAttribute("cate", cate);
			m.addAttribute("total", total);
			m.addAttribute("sort", sort);

		} else {
			int total = (int) service.count();
			int pgtotal;
			if (total % numOfProduct == 0) {
				pgtotal = total / numOfProduct;

			} else {

				pgtotal = total / numOfProduct + 1;
			}

			m.addAttribute("pgtotal", pgtotal);

			List<Products> products = new ArrayList<Products>();

			switch (sort) {
			case "date":
				products = service.findAllOrderByDate();
				break;
			case "sales":
				products = service.findAllOrderBySales();
				break;
			case "priceA":
				products = service.findAllOrderByPriceAsc();
				break;
			case "priceD":
				products = service.findAllOrderByPriceDesc();
				break;
			case "default":
				products = service.findAll();
				break;
			}

			List<Products> pgproducts = new ArrayList<Products>();

			if (pg == 1) {
				if (total < numOfProduct) {
					for (int i = 0; i < total; i++) {
						pgproducts.add(products.get(i));
					}
				} else {
					for (int i = 0; i < numOfProduct; i++) {
						pgproducts.add(products.get(i));
					}
				}

			} else if (pg == pgtotal) {
				for (int i = numOfProduct * (pg - 1); i < total; i++) {
					pgproducts.add(products.get(i));
				}
			} else {
				for (int i = numOfProduct * (pg - 1); i < pg * numOfProduct; i++) {
					pgproducts.add(products.get(i));
				}
			}

			m.addAttribute("products", pgproducts);
			m.addAttribute("page", pg);
			m.addAttribute("withCate", withCate);
			m.addAttribute("total", total);
			m.addAttribute("sort", sort);

			return "shop/shop-productlist";
		}

		return "shop/shop-productlist";
	}

	// 搜尋
	@GetMapping("/list/search")
	public String productSearchListPage(Model m, HttpServletRequest request) {

		// 要改id!!
		int cartnum = (int) cartService.countByUserid(1);
		m.addAttribute("cartnum", cartnum);

		String sort;
		String withCate = "search";
		String key = request.getParameter("key");

		int total = (int) repo.countBySearch(key);
		int pgtotal;
		int numOfProduct = 15;

		if (total % numOfProduct == 0) {
			pgtotal = total / numOfProduct;

		} else {

			pgtotal = total / numOfProduct + 1;
		}

		if (request.getParameter("sort") == null) {
			sort = "default";
		} else {
			sort = request.getParameter("sort");
		}

		m.addAttribute("pgtotal", pgtotal);

		int pg;
		if (request.getParameter("page") == null) {
			pg = 1;
		} else {
			pg = Integer.parseInt(request.getParameter("page"));
		}

		List<Products> products = new ArrayList<Products>();

		switch (sort) {
		case "date":
			products = service.findBySearchOrderByDate(key);
			break;
		case "sales":
			products = service.findBySearchOrderBySales(key);
			break;
		case "priceA":
			products = service.findBySearchOrderByPriceAsc(key);
			break;
		case "priceD":
			products = service.findBySearchOrderByPriceDesc(key);
			break;
		case "default":
			products = service.findByPnameContaining(key);
			break;
		}

		List<Products> pgproducts = new ArrayList<Products>();

		if (pg == 1) {
			if (total < numOfProduct) {
				for (int i = 0; i < total; i++) {
					pgproducts.add(products.get(i));
				}
			} else {
				for (int i = 0; i < numOfProduct; i++) {
					pgproducts.add(products.get(i));
				}
			}

		} else if (pg == pgtotal) {
			for (int i = numOfProduct * (pg - 1); i < total; i++) {
				pgproducts.add(products.get(i));
			}
		} else {
			for (int i = numOfProduct * (pg - 1); i < pg * numOfProduct; i++) {
				pgproducts.add(products.get(i));
			}

		}

		m.addAttribute("sort", sort);
		m.addAttribute("products", pgproducts);
		m.addAttribute("page", pg);
		m.addAttribute("withCate", withCate);
		m.addAttribute("key", key);
		m.addAttribute("total", total); // 商品總數

		return "shop/shop-productlist";
	}

	// 利用遊戲分類tag來搜尋
	@GetMapping("/list/tag")
	public String productTagListPage(Model m, HttpServletRequest request) {

		// 要改id!!
		int cartnum = (int) cartService.countByUserid(1);
		m.addAttribute("cartnum", cartnum);

		String sort;
		String withCate = "tag";
		String tag = request.getParameter("tag");
		int pg;

		int total = (int) ptService.countByGameType(tag);
		int pgtotal;
		int numOfProduct = 15;

		if (total % numOfProduct == 0) {
			pgtotal = total / numOfProduct;

		} else {

			pgtotal = total / numOfProduct + 1;
		}

		m.addAttribute("pgtotal", pgtotal);

		if (request.getParameter("sort") == null) {
			sort = "default";
		} else {
			sort = request.getParameter("sort");
		}

		List<Products> productsAll = new ArrayList<Products>();

		switch (sort) {
		case "date":
			productsAll = service.findAllOrderByDate();
			break;
		case "sales":
			productsAll = service.findAllOrderBySales();
			break;
		case "priceA":
			productsAll = service.findAllOrderByPriceAsc();
			break;
		case "priceD":
			productsAll = service.findAllOrderByPriceDesc();
			break;
		case "default":
			productsAll = service.findAll();
			break;
		}

		List<ProductType> proTypes = ptService.findByGameType(tag);
		List<Products> products = new ArrayList<Products>();

		for (Products p : productsAll) {
			for (ProductType t : proTypes) {
				if (t.getP_id() == p.getP_id()) {
					products.add(p);
				}
			}
		}

		List<Products> productsPage = new ArrayList<Products>();
		total=products.size();
		// 分頁 第一頁分成 商品總數小於一頁的數量(15)和足夠一頁的數量
		if (request.getParameter("page") == null | "1".equals(request.getParameter("page"))) {

			if (total < numOfProduct) {
				for (int i = 0; i < total; i++) {
					productsPage.add(products.get(i));
				}

			} else {
				for (int i = 0; i < numOfProduct; i++) {
					productsPage.add(products.get(i));
				}
			}
			pg = 1;

			// 分頁 若是最後一頁(數量可能不足15)
		} else if (Integer.parseInt(request.getParameter("page")) == pgtotal) {
			pg = Integer.parseInt(request.getParameter("page"));

			for (int i = (pg - 1) * numOfProduct; i < total; i++) {
				productsPage.add(products.get(i));
			}
		} else {
			// 分頁 其他會剛剛好15
			pg = Integer.parseInt(request.getParameter("page"));

			for (int i = (pg - 1) * numOfProduct; i < pg * numOfProduct; i++) {
				productsPage.add(products.get(i));
			}

		}

		m.addAttribute("sort", sort);
		m.addAttribute("products", productsPage);
		m.addAttribute("page", pg);
		m.addAttribute("withCate", withCate);
		m.addAttribute("tag", tag);
		m.addAttribute("total", total); // 商品總數

		return "shop/shop-productlist";
	}

	// 利用os來搜尋
	@GetMapping("/list/os")
	public String productOsListPage(Model m, HttpServletRequest request) {

		// 要改id!!
		int cartnum = (int) cartService.countByUserid(1);
		m.addAttribute("cartnum", cartnum);

		String sort;
		String withCate = "os";
		String os = request.getParameter("os");
		int total;
		int pg;

		if (request.getParameter("sort") == null) {
			sort = "default";
		} else {
			sort = request.getParameter("sort");
		}

		List<Products> products = new ArrayList<Products>();

		if (os.equals("mac")) {

			total = (int) service.countByMac();

			switch (sort) {
			case "date":
				products = service.findByMacOrderByDate();
				break;
			case "sales":
				products = service.findByMacOrderBySales();
				break;
			case "priceA":
				products = service.findByMacOrderByPriceAsc();
				break;
			case "priceD":
				products = service.findByMacOrderByPriceDesc();
				break;
			case "default":
				products = service.findByMac();
				break;
			}

		} else {
			total = (int) service.countByWindows();

			switch (sort) {
			case "date":
				products = service.findByWinOrderByDate();
				break;
			case "sales":
				products = service.findByWinOrderBySales();
				break;
			case "priceA":
				products = service.findByWinOrderByPriceAsc();
				break;
			case "priceD":
				products = service.findByWinOrderByPriceDesc();
				break;
			case "default":
				products = service.findByWindows();
				break;
			}
		}

		int pgtotal;
		int numOfProduct = 15;

		if (total % numOfProduct == 0) {
			pgtotal = total / numOfProduct;
		} else {

			pgtotal = total / numOfProduct + 1;
		}

		m.addAttribute("pgtotal", pgtotal);

		List<Products> productsPage = new ArrayList<Products>();

		// 分頁 第一頁分成 商品總數小於一頁的數量(15)和足夠一頁的數量
		if (request.getParameter("page") == null | "1".equals(request.getParameter("page"))) {

			if (total < numOfProduct) {
				for (int i = 0; i < total; i++) {
					productsPage.add(products.get(i));
				}

			} else {
				for (int i = 0; i < numOfProduct; i++) {
					productsPage.add(products.get(i));
				}
			}
			pg = 1;

			// 分頁 若是最後一頁(數量可能不足15)
		} else if (Integer.parseInt(request.getParameter("page")) == pgtotal) {
			pg = Integer.parseInt(request.getParameter("page"));

			for (int i = (pg - 1) * numOfProduct; i < total; i++) {
				productsPage.add(products.get(i));
			}
		} else {
			// 分頁 其他會剛剛好15
			pg = Integer.parseInt(request.getParameter("page"));

			for (int i = (pg - 1) * numOfProduct; i < pg * numOfProduct; i++) {
				productsPage.add(products.get(i));
			}

		}

		m.addAttribute("sort", sort);
		m.addAttribute("products", productsPage);
		m.addAttribute("page", pg);
		m.addAttribute("withCate", withCate);
		m.addAttribute("os", os);
		m.addAttribute("total", total); // 商品總數

		return "shop/shop-productlist";
	}

	// 特價產品 0203
	@GetMapping("/list/sale")
	public String saleProductListPage(Model m, HttpServletRequest request) {

		// 要改id!!
		int cartnum = (int) cartService.countByUserid(1);
		m.addAttribute("cartnum", cartnum);

		String sort;
		String withCate = "sale";
		int pg;

		if (request.getParameter("page") == null) {
			pg = 1;
		} else {
			pg = Integer.parseInt(request.getParameter("page"));
		}

		int total = (int) service.countSaleProduct();
		int pgtotal;
		int numOfProduct = 15;

		if (total % numOfProduct == 0) {
			pgtotal = total / numOfProduct;

		} else {

			pgtotal = total / numOfProduct + 1;
		}

		m.addAttribute("pgtotal", pgtotal);

		if (request.getParameter("sort") == null) {
			sort = "default";
		} else {
			sort = request.getParameter("sort");
		}

		List<Products> products = new ArrayList<Products>();

		switch (sort) {
		case "date":
			products = service.findSaleProductOrderByDate();
			break;
		case "sales":
			products = service.findSaleProductOrderBySales();
			break;
		case "priceA":
			products = service.findSaleProductOrderByPriceAsc();
			break;
		case "priceD":
			products = service.findSaleProductOrderByPriceDesc();
			break;
		case "default":
			products = service.findSaleProduct();
			break;
		}

		List<Products> productsPage = new ArrayList<Products>();

		// 分頁 第一頁分成 商品總數小於一頁的數量(15)和足夠一頁的數量
		if (pg == 1) {

			if (total < numOfProduct) {
				for (int i = 0; i < total; i++) {
					productsPage.add(products.get(i));
				}

			} else {
				for (int i = 0; i < numOfProduct; i++) {
					productsPage.add(products.get(i));
				}
			}

			// 分頁 若是最後一頁(數量可能不足15)
		} else if (pg == pgtotal) {
			for (int i = (pg - 1) * numOfProduct; i < total; i++) {
				productsPage.add(products.get(i));
			}
		} else {
			// 分頁 其他會剛剛好15

			for (int i = (pg - 1) * numOfProduct; i < pg * numOfProduct; i++) {
				productsPage.add(products.get(i));
			}
		}

		m.addAttribute("sort", sort);
		m.addAttribute("products", productsPage);
		m.addAttribute("page", pg);
		m.addAttribute("withCate", withCate);
		m.addAttribute("total", total); // 商品總數

		return "shop/shop-productlist";
	}

	// 商品頁面 --判斷是否已加入願望清單
	@GetMapping("/product/{id}")
	public String productPage(@PathVariable("id") int id, Model m) {
		// 之後加入會員系統要先判斷session是否登入

		// 要改id!!
		int cartnum = (int) cartService.countByUserid(1);
		m.addAttribute("cartnum", cartnum);

		List<ProductType> type = ptService.findByP_id(id);
		m.addAttribute("type", type);

		Products product = service.findById(id);
		m.addAttribute("product", product);

		Boolean inWishList = false;

		// userid用session的
		List<WishList> wish = wService.findByUser_idAndP_id(1, id);
		if (!wish.isEmpty()) {
			inWishList = true;
		}

		m.addAttribute("inWishList", inWishList);

		return "shop/shop-product";
	}

	// 加入追蹤
	@GetMapping("/checkWishList")
	public @ResponseBody String checkList(@RequestParam("p_id") int id) {

		// id要改由session取得
		List<WishList> wish = wService.findByUser_idAndP_id(1, id);
		String result;
		if (wish.isEmpty()) {
			result = "true";
			WishList wishList = new WishList();
			wishList.setUser_id(1);
			wishList.setP_id(id);
			wService.save(wishList);
		} else {
			result = "false";
			wService.deleteByUser_idAndP_id(1, id);
		}

		return result;

	}

	@GetMapping("/add")
	public @ResponseBody String addToCart(@RequestParam("p_id") int p_id, @RequestParam("add_num") int quantity) {

		List<Cart> carts = cartService.findByUser_idAndP_id(1, p_id);

		String result;

		if (carts.isEmpty()) {
			result = "true";
			Cart cart = new Cart();
			cart.setUser_id(1); // 有會員系統後 從session抓
			cart.setProduct_id(p_id);
			cart.setQuantity(quantity);
			cartService.save(cart);

		} else {
			result = "false";

		}
		return result;

	}

}
