package tw.t1.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import tw.t1.dao.ProductsRepository;
import tw.t1.entity.GameType;
import tw.t1.entity.ProductType;
import tw.t1.entity.Productcategory;
import tw.t1.entity.Products;
import tw.t1.entity.Supplier;
import tw.t1.service.CartService;
import tw.t1.service.GameTypeService;
import tw.t1.service.ProductCategoryService;
import tw.t1.service.ProductTypeService;
import tw.t1.service.ProductsService;
import tw.t1.service.SupplierService;
import tw.t1.service.WishListService;

@Controller
@RequestMapping(value = "/products")
public class ProductsController {

	private ProductsService pService;
	private ProductsRepository repo; // 做上傳要用
	private GameTypeService gService;
	private ProductTypeService ptService;
	private CartService cService;
	private WishListService wService;
	private ProductCategoryService pcService;
	private SupplierService sService;

	// 用參數建構子 or @Autowired寫在service上 不然抓不到controller
	@Autowired
	public ProductsController(ProductsService service, ProductsRepository repo, GameTypeService gService,
			ProductTypeService ptService, CartService cService, WishListService wService,
			ProductCategoryService pcService, SupplierService sService) {
		this.pService = service;
		this.repo = repo;
		this.gService = gService;
		this.ptService = ptService;
		this.cService = cService;
		this.wService = wService;
		this.pcService = pcService;
		this.sService = sService;
	}

	/* 0206家寶 */
	@GetMapping(path = "/upload")
	public String goUpdate(HttpSession session, Model m, HttpServletRequest request, Products currentProduct) {

		if ("true".equals(request.getParameter("back"))) {

			Products product = (Products) session.getAttribute("currentProduct");
			String[] thisProductType = (String[]) session.getAttribute("thisProductType");
			session.setAttribute("currentProduct", product);
			session.setAttribute("thisProductType", thisProductType);
			m.addAttribute("thisProductType", thisProductType);

		} else {
			Products product = new Products();
			product.setCategory("PC");
			session.setAttribute("currentProduct", product);

		}

		List<GameType> gameType = gService.findAll();
		m.addAttribute("gameType", gameType);

		/* 0205家寶 */
		List<Supplier> supplier = sService.findAll();
		m.addAttribute("supplier", supplier);
		/* 0205家寶 */

		List<Productcategory> productCate = pcService.findAll();
		m.addAttribute("productCate", productCate);

		return "/manager/manager-product-upload";
	}

	@PostMapping(value = "/preview")
	public String goPreview(HttpSession session, Products currentProduct, HttpServletRequest req, Model m) {

		if (req.getParameterValues("gameType[]") != null) {
			String[] thisProductType = req.getParameterValues("gameType[]");
			m.addAttribute("thisProductType", thisProductType);
			session.setAttribute("thisProductType", thisProductType);
		}
		System.out.println(req.getParameter("mac"));
		System.out.println("hi");
		currentProduct.setMac(req.getParameter("mac"));
		currentProduct.setWindows(req.getParameter("windows"));
		/* 0203家寶 */
		currentProduct.setCategory(req.getParameter("category"));
		/* 0203家寶 */

		session.setAttribute("currentProduct", currentProduct);
		System.out.println(currentProduct.getDescription());
		return "/manager/manager-product-preview";
	}

	// list
	@GetMapping(value = "/list")
	public String productList(Model model, HttpServletRequest request) {

		/* 算要分幾頁 */
		int total = (int) pService.count();
		int pgtotal;
		int page;
		int numOfProduct = 10;

		if (request.getParameter("page") == null) {
			page = 1;
		} else {
			page = Integer.parseInt(request.getParameter("page"));
		}

		if (total % numOfProduct == 0) {
			pgtotal = total / numOfProduct;
		} else {
			pgtotal = total / numOfProduct + 1;
		}
		model.addAttribute("pgtotal", pgtotal);

		List<Products> products = new ArrayList<Products>();
		List<Products> pgProducts = new ArrayList<Products>();

		/* 排序：預設以更新時間排序 */
		String sort = request.getParameter("sort");
		if (sort == null) {
			sort = "date";
		}

		/* 抓全部資料 */
		if (sort.equals("id")) {
			products = pService.findAll();
		} else {
			products = pService.findProductsDateDesc();
		}

		if (page == 1) {
			if (total < numOfProduct) {
				for (int i = 0; i < total; i++) {
					pgProducts.add(products.get(i));
				}
			} else {
				for (int i = 0; i < numOfProduct; i++) {
					pgProducts.add(products.get(i));
				}
			}

		} else if (page == pgtotal) {
			for (int i = (page - 1) * numOfProduct; i < total; i++) {
				pgProducts.add(products.get(i));
			}
		} else {
			for (int i = (page - 1) * numOfProduct; i < page * numOfProduct; i++) {
				pgProducts.add(products.get(i));
			}
		}
		/* 0210家寶 */
		List<Productcategory> cate = pcService.findAll();
		List<ProductType> productType = ptService.findAll();
		model.addAttribute("sort", sort);
		model.addAttribute("page", page);
		model.addAttribute("productcategory", cate);
		/* 0210家寶 */
		model.addAttribute("products", pgProducts);
		model.addAttribute("productType", productType);
		model.addAttribute("total", total);

		return "/manager/manager-product-list";
	}

	/*-----------------02/12-----中-------------------------*/
	// save商品
	@PostMapping(path = "/save")
	public @ResponseBody void saveProduct(HttpSession session, @RequestPart(name = "image") MultipartFile photo)
			throws IOException {

		Products currentProduct = (Products) session.getAttribute("currentProduct");
		/*--------取得上傳檔案的檔名存入Photo_uri-----------*/
		String fileName = photo.getOriginalFilename();
		currentProduct.setPhoto_uri(fileName);

		/*-----------取得現在時間當作商品更新時間-------------*/
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		currentProduct.setUpdate_time(sdf.format(new Date()));

		/*------存檔-------*/
		System.out.println(currentProduct.getP_id());
		repo.save(currentProduct);

		/*------儲存圖片------*/
		String filePath = "C:/SpringBoot/workspace/T1/src/main/resources/static/images/products/"
				+ currentProduct.getP_id() + "/"; // 存檔路徑

		if (photo.getSize() > 0) { // 確認檔案不為空
			File dir = new File(filePath); // 建立檔案根目錄

			if (!dir.exists()) { // 如果根目錄不存在就建一個新的
				dir.mkdirs();
			}
		}

		photo.transferTo(new File(filePath + fileName));

		/*------存入ProductType------*/

		String[] thisProductType = (String[]) session.getAttribute("thisProductType");
		if (thisProductType != null) {
			for (String x : thisProductType) {
				ptService.save(new ProductType(currentProduct.getP_id(), x));
			}
		}

		/*------以上都做完後移除session裡Products物件------*/
		session.removeAttribute("thisProductType");
		session.removeAttribute("currentProduct");

	}
	/*-----^^^^^^^^^^^^^^^---------------02/12-----------------*/

	// delete 刪除商品
	@GetMapping("/delete")
	public String deleteProduct(@RequestParam("p_id") int Id) {
		Products products = pService.findById(Id);

		/*------刪除productType---------*/
		if (ptService.findByP_id(Id) != null) {
			ptService.deleteByP_id(Id);
		}

		/*------從購物車和願望清單中刪除該商品---------*/
		if (cService.findByP_id(Id) != null) {
			cService.deleteByProduct_id(Id);
		}
		if (wService.findByP_id(Id) != null) {
			System.out.println("進入if");
			wService.deleteByP_id(Id);
		}

		/*------刪除圖檔---------*/
		String imgPath = "C:/SpringBoot/workspace/T1" + products.getPhotosImagePath(); // 換資料夾要修改
		System.out.println("imgPath : " + imgPath);
		File dir = new File(imgPath);
		if (dir.exists()) {
			System.out.println(dir.delete());
		}
		/*------刪除以id命名的空資料夾------*/
		String imagePath = "C:/SpringBoot/workspace/T1" + products.getDeleteImagePath(); // 換資料夾要修改
		System.out.println("刪除資料夾路徑" + imagePath);
		File dir_test = new File(imagePath);
		if (dir_test.isDirectory() && dir_test.exists()) {
			System.out.println(dir_test.delete());
		}
		System.out.println("Directory deleted!");

		/*-------刪除資料-------*/
		pService.deleteById(Id);
		return "redirect:list";
	}

	// update商品更新
	@GetMapping(value = "update")
	public String updateProducts(@RequestParam("p_id") int Id, HttpSession session, Model m) {

		Products product = pService.findById(Id);
		List<ProductType> thisProductType = ptService.findByP_id(Id);
		List<GameType> gameType = gService.findAll();

		m.addAttribute("thisProductType", thisProductType);
		m.addAttribute("gameType", gameType);
		/* 0203家寶 */
		List<Productcategory> productCate = pcService.findAll();
		m.addAttribute("productCate", productCate);
		/* 0203家寶 */

		/* 0205家寶 */
		List<Supplier> supplier = sService.findAll();
		m.addAttribute("supplier", supplier);
		/* 0205家寶 */

		session.setAttribute("currentProduct", product); // 把選到的物件存到 session
		return "manager/manager-product-update";
	}

	@PostMapping(path = "/updateSubmit")
	public String updateProduct(HttpSession session, Products currentProduct, HttpServletRequest request)
			throws IOException {

		/*-----------取得現在時間當作商品更新時間-------------*/
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		currentProduct.setUpdate_time(sdf.format(new Date()));
		currentProduct.setMac(request.getParameter("mac"));
		currentProduct.setWindows(request.getParameter("windows"));

		/*------存檔-------*/
		System.out.println(currentProduct.getP_id());
		repo.save(currentProduct);

		/*------刪除舊的所有productType  新的存入ProductType------*/

		ptService.deleteByP_id(currentProduct.getP_id());

		String[] gameType = (String[]) request.getParameterValues("gameType[]");
		if (gameType != null) {
			for (String x : gameType) {
				ptService.save(new ProductType(currentProduct.getP_id(), x));
			}
		}

		/*------以上都做完後移除session裡Products物件------*/
		session.removeAttribute("currentProduct");
		return "redirect:list";
	}

	/* 0209家寶 */
	// 遊戲類型列表
	@GetMapping(path = "/gametype/list")
	public String gameTypelist(Model m) {

		List<GameType> gameTypes = gService.findAll();

		m.addAttribute("gameTypes", gameTypes);

		return "manager/manager-gameType-list";
	}

	// 遊戲類型更新
	/* 0209家寶 */
	@GetMapping(path = "/gametype/list/update")
	public @ResponseBody void gameTypeUpdate(@RequestParam("id") int id, @RequestParam("gameType") String type) {

		System.out.println(id);
		System.out.println(type);

		GameType gameType = new GameType();
		gameType.setId(id);
		gameType.setGameType(type);

		gService.save(gameType);

	}

	// 確認是否重複
	@GetMapping(path = "/gametype/list/check")
	public @ResponseBody String checkGameType(@RequestParam("gameType") String type) {

		String result = "false";

		if (gService.findByGametype(type) != null) {
			result = "true";
		}

		return result;
	}

	// 新增gameType
	@PostMapping(path = "/gametype/list/upload")
	public String uploadGametype(Model m, @RequestParam(name = "uploadgt") String type) {

		GameType gt = new GameType();
		gt.setGameType(type);

		gService.save(gt);

		return "redirect:";
	}

	// 遊戲類型刪除
	@GetMapping(path = "/gametype/list/remove")
	public @ResponseBody void deleteGameType(@RequestParam("id") int id) {
		gService.deleteById(id);
	}

	// 商品型別列表
	@GetMapping(path = "/productcategory/list")
	public String categorylist(Model m) {

		List<Productcategory> category = pcService.findAll();

		m.addAttribute("category", category);

		return "manager/manager-productcategory-list";
	}

	// 商品型別更新
	@GetMapping(path = "/productcategory/list/update")
	public @ResponseBody String categoryUpdate(@RequestParam("id") int id, @RequestParam("category") String cate) {

		System.out.println(id);
		System.out.println(cate);

		Productcategory category = new Productcategory();
		category.setId(id);
		category.setCategory(cate);

		pcService.save(category);

		return cate;

	}

	// 確認是否重複
	@GetMapping(path = "/productcategory/list/check")
	public @ResponseBody String checkCate(@RequestParam("category") String category) {

		String result = "false";

		if (pcService.findByCategory(category) != null) {
			result = "true";
		}

		return result;
	}

	// 新增category
	@PostMapping(path = "/productcategory/list/upload")
	public String uploadCategory(Model m, @RequestParam(name = "uploadpc") String cate) {

		Productcategory pc = new Productcategory();
		pc.setCategory(cate);

		pcService.save(pc);

		return "redirect:";
	}

	// 商品型別刪除
	@GetMapping(path = "/productcategory/list/remove")
	public @ResponseBody void deleteCategory(@RequestParam("id") int id) {
		pcService.deleteById(id);
	}

	@GetMapping(value = "/imgupdate")
	public String updateImage(@RequestParam("imgId") int pid, @RequestParam("imgPath") String path, Model m) {
		System.out.println(path);
		System.out.println(pid);
		Products p = pService.findById(pid);
		m.addAttribute("product1", p);

		return "manager/manager-product-img-update";

	};

	/*---------------02/03---------------封面修改存擋--------------*/
	@PostMapping(path = "/imgsave")
	public String saveImage(@RequestParam(name = "dpath") String dpath,
			@RequestPart(name = "pimage") MultipartFile photo, @RequestParam(name = "p_id") int p_id)
			throws IllegalStateException, IOException {
		// 取得舊圖的路徑
		String oldPath = "C:/SpringBoot/workspace/T1/src/main/resources/static" + dpath; // 換資料夾要修改

		// 測試用
		System.out.println("oldPPPP:" + oldPath);

		Products p = pService.findById(p_id);

		// 把舊圖刪掉
		File dir = new File(oldPath);
		if (dir.exists()) {
			dir.delete();
		}

		// 抓新圖的檔名
		String fileName = photo.getOriginalFilename();

		// set給該Product
		p.setPhoto_uri(fileName);

		// 建立存擋路徑
		String upPath = "C:/SpringBoot/workspace/T1/src/main/resources/static/images/products/" + p.getP_id() + "/";

		if (photo.getSize() > 0) { // 確認檔案不為空
			File dir1 = new File(upPath); // 建立檔案根目錄

			if (!dir1.exists()) { // 如果根目錄不存在就建一個新的
				dir1.mkdirs();
			}
		}

		photo.transferTo(new File(upPath + fileName));
		// p.setPhoto_uri(fileName); 重複做了
		pService.save(p);

		return "redirect:list";
	}

	/*----------- 0205家寶 ------------------*/
	// supplier頁面
	@GetMapping(path = "/supplier/list")
	public String supplierList(Model model, HttpServletRequest request) {

		/* 分幾頁 */
		int total = (int) sService.countAll();
		int pgtotal;
		int page;
		int numOfSuppliers = 20;

		if (request.getParameter("page") == null) {
			page = 1;
		} else {
			page = Integer.parseInt(request.getParameter("page"));
		}

		if (total % numOfSuppliers == 0) {
			pgtotal = total / numOfSuppliers;
		} else {
			pgtotal = total / numOfSuppliers + 1;
		}
		model.addAttribute("pgtotal", pgtotal);

		List<Supplier> suppliers = sService.findAll();
		List<Supplier> pgsuppliers = new ArrayList<Supplier>();

		if (page == 1) {
			if (total < numOfSuppliers) {
				for (int i = 0; i < total; i++) {
					pgsuppliers.add(suppliers.get(i));
				}
			} else {
				for (int i = 0; i < numOfSuppliers; i++) {
					pgsuppliers.add(suppliers.get(i));
				}

			}
		} else if (page == pgtotal) {
			for (int i = (page - 1) * numOfSuppliers; i < total; i++) {
				pgsuppliers.add(suppliers.get(i));
			}

		} else {
			for (int i = (page - 1) * numOfSuppliers; i < page * numOfSuppliers; i++) {
				pgsuppliers.add(suppliers.get(i));
			}
		}

		model.addAttribute("page", page);
		model.addAttribute("suppliers", pgsuppliers);

		return "manager/manager-supplier-list";
	}

	// supplier ajax更新
	@GetMapping(path = "/supplier/list/update")
	public @ResponseBody Supplier updateSupplier(@RequestParam("id") int id, @RequestParam("supplier") String supplier,
			@RequestParam("person") String person, @RequestParam("phone") String phone) {

		Supplier sup = new Supplier();
		sup.setId(id);
		sup.setSupplier(supplier);
		sup.setContact_person(person);
		sup.setContact_phone(phone);

		sService.save(sup);

		return sup;
	}

	@PostMapping(path = "/supplier/list/upload")
	public String uploadSupplier(Model m, @RequestParam(name = "uploadsup") String supplier,
			@RequestParam(name = "uploadperson") String person, @RequestParam(name = "uploadphone") String phone) {

		Supplier sup = new Supplier();
		sup.setSupplier(supplier);
		sup.setContact_person(person);
		sup.setContact_phone(phone);

		sService.save(sup);

		return "redirect:";
	}

	@GetMapping(path = "/supplier/list/check")
	public @ResponseBody String checkSupplier(@RequestParam("supplier") String supplier) {

		String result = "false";

		if (sService.findBySupplierIgnoreCase(supplier) != null) {
			result = "true";
		}

		return result;
	}

	@GetMapping(path = "/supplier/list/remove")
	public @ResponseBody void removeSupplier(@RequestParam("id") int id) {

		sService.deleteById(id);

	}

	/*----------- 0205家寶 ------------------*/

	/*------------02/07----搜尋-------------------*/
	@GetMapping("/search")
	public String searchProducts(Model m, HttpServletRequest request) {

		String key = request.getParameter("key");
		List<Products> products = pService.findByPnameContaining(key);
		int total = products.size();
		int pgtotal;
		int numOfProduct = 5;

		String sort;

		if (total % numOfProduct == 0) {
			pgtotal = total / numOfProduct;
		} else {
			pgtotal = total / numOfProduct + 1;
		}

		if (request.getParameter("sort") == null) {
			sort = "id";
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

		List<Products> products2 = new ArrayList<Products>();
		switch (sort) {
		case "date":
			products2 = pService.findBySearchOrderByDate(key);
			break;
		case "id":
			products2 = pService.findByPnameContaining(key);
			break;
		}

		List<Products> pgproducts = new ArrayList<Products>();
		if (pg == 1) {
			if (total < numOfProduct) {
				for (int i = 0; i < total; i++) {
					pgproducts.add(products2.get(i));
				}
			} else {
				for (int i = 0; i < numOfProduct; i++) {
					pgproducts.add(products2.get(i));
				}
			}

		} else if (pg == pgtotal) {
			for (int i = numOfProduct * (pg - 1); i < total; i++) {
				pgproducts.add(products2.get(i));
			}
		} else {
			for (int i = numOfProduct * (pg - 1); i < pg * numOfProduct; i++) {
				pgproducts.add(products2.get(i));
			}

		}
		/* 0210家寶 */
		List<ProductType> pt = ptService.findAll();
		List<Productcategory> pc = pcService.findAll();

		m.addAttribute("productcategory", pc);
		m.addAttribute("productType", pt);
		/* 0210家寶 */
		m.addAttribute("sort", sort);
		m.addAttribute("products", pgproducts);
		m.addAttribute("page", pg);
		m.addAttribute("key", key);
		m.addAttribute("total", total); // 商品總數

		return "manager/manager-product-list";

	}
	/*--------------------^^^^^^^^^^^---------------------*/

	/* 0210家寶 */
	@GetMapping("/list/category")
	public String cateProducts(Model m, HttpServletRequest request) {

		String cate = request.getParameter("cate");

		if (cate.equals("all")) {
			return "redirect:/products/list";
		}

		List<Products> products = pService.findProductsByCategory(cate);
		int total = products.size();
		int pgtotal;
		int numOfProduct = 10;

		String sort;

		if (total % numOfProduct == 0) {
			pgtotal = total / numOfProduct;
		} else {
			pgtotal = total / numOfProduct + 1;
		}

		if (request.getParameter("sort") == null) {
			sort = "id";
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

		List<Products> products2 = new ArrayList<Products>();
		switch (sort) {
		case "date":
			products2 = pService.findByCategoryOrderByDate(cate);
			break;
		case "id":
			products2 = pService.findProductsByCategory(cate);
			break;
		}

		List<Products> pgproducts = new ArrayList<Products>();
		if (pg == 1) {
			if (total < numOfProduct) {
				for (int i = 0; i < total; i++) {
					pgproducts.add(products2.get(i));
				}
			} else {
				for (int i = 0; i < numOfProduct; i++) {
					pgproducts.add(products2.get(i));
				}
			}

		} else if (pg == pgtotal) {
			for (int i = numOfProduct * (pg - 1); i < total; i++) {
				pgproducts.add(products2.get(i));
			}
		} else {
			for (int i = numOfProduct * (pg - 1); i < pg * numOfProduct; i++) {
				pgproducts.add(products2.get(i));
			}

		}

		List<ProductType> pt = ptService.findAll();
		List<Productcategory> pc = pcService.findAll();

		m.addAttribute("productcategory", pc);
		m.addAttribute("productType", pt);
		m.addAttribute("category", cate);
		m.addAttribute("sort", sort);
		m.addAttribute("products", pgproducts);
		m.addAttribute("page", pg);
		m.addAttribute("total", total); // 商品總數

		return "manager/manager-product-list";

	}
	/* 0210家寶 */
}
