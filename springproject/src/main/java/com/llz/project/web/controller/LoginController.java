package com.llz.project.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.llz.project.meta.Content;
import com.llz.project.meta.Person;
import com.llz.project.meta.Product;
import com.llz.project.meta.Transaction;
import com.llz.project.service.ContentService;
import com.llz.project.service.ImageService;
import com.llz.project.service.PersonService;
import com.llz.project.service.ProductService;
import com.llz.project.service.TransactionService;

@Controller
public class LoginController {
	private static Logger log = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	private PersonService personService;

	@Autowired
	private ContentService contentService;

	@Autowired
	private ProductService productService;

	@Autowired
	private ImageService imageService;

	@Autowired
	private TransactionService transactionService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(HttpSession session, ModelMap map) {
		List<Content> contentList = contentService.getContent();
		List<Product> productList = new ArrayList<Product>();
		if (session.getAttribute("user") != null) {
			Person person = (Person) session.getAttribute("user");
			int personId = person.getId();
			int userType = person.getUserType();
			if (userType == 0) {
				List<Content> contentBuyerList = contentService
						.getBuyerContentByPersonId(personId);
				productList = productService.getUserProductList(
						contentBuyerList, contentList, userType);
			}
			if (userType == 1) {
				List<Content> contentSellerList = contentService
						.getSellerContent();
				productList = productService.getUserProductList(
						contentSellerList, contentList, userType);
			}
		} else {
			productList = productService.getVisitorProductList(contentList);
		}
		map.addAttribute("productList", productList);
		return "index";
	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public String show(@RequestParam("id") int id, ModelMap map,
			HttpSession session) {
		Person person = (Person) session.getAttribute("user");
		Content content = contentService.getContentById(id);
		Product product = productService.getProduct(content, person);
		map.addAttribute("product", product);
		return "show";
	}

	@RequestMapping(value = "/account", method = RequestMethod.GET)
	public String account(HttpSession session, ModelMap map) {
		if (session.getAttribute("user") != null) {
			Person person = (Person) session.getAttribute("user");
			List<Product> accountProductList = productService
					.getProductByAccount(person.getId());
			map.addAttribute("buyList", accountProductList);
			return "account";
		} else {
			return "login";
		}

	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login() {
		return "login";
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String login(HttpSession session) {
		session.invalidate();
		return "login";
	}

	@RequestMapping(value = "api/login", method = RequestMethod.POST)
	// public void checkLogin(@RequestParam("userName") String userName,
	// @RequestParam("password") String password, ModelMap map,
	// HttpSession session) {
	public void checkLogin(@ModelAttribute Person user, ModelMap map,
			HttpSession session) {
		Person person = personService.checkUser(user.getUserName(),
				user.getPassword());
		if (person != null) {
			session.setAttribute("user", person);
			map.addAttribute("code", 200);
			map.addAttribute("message", "成功登录！");
			map.addAttribute("result", true);

		} else {
			map.addAttribute("code", 400);
			map.addAttribute("message", "该用户不存在！");
			map.addAttribute("result", false);
		}
	}

	@RequestMapping(value = "/api/delete", method = RequestMethod.POST)
	public void delete(@RequestParam("id") int id, ModelMap map) {
		if (contentService.deleteContentById(id) > 0
				&& imageService.deleteImageFileById(id)) {
			map.addAttribute("code", 200);
			map.addAttribute("message", "已成功删除数据");
			map.addAttribute("result", true);
		} else {
			map.addAttribute("code", 404);
			map.addAttribute("message", "删除数据失败！");
			map.addAttribute("result", false);
		}
	}

	@RequestMapping(value = "/api/buy", method = RequestMethod.POST)
	public void buy(@RequestParam("id") int id, HttpSession session,
			ModelMap map) {
		Person person = (Person) session.getAttribute("user");
		if (person.getUserType() == 0) {
			Transaction trx = new Transaction();
			trx.setContentId(id);
			trx.setPersonId(person.getId());
			Content content = contentService.getContentById(id);
			trx.setPrice(content.getPrice());
			trx.setTime(System.currentTimeMillis());
			transactionService.insertTransaction(trx);
			if (trx.getId() > 0) {
				map.addAttribute("code", 200);
				map.addAttribute("message", "购买成功！");
				map.addAttribute("result", true);
			} else {
				map.addAttribute("code", 404);
				map.addAttribute("message", "购买失败！");
				map.addAttribute("result", false);
			}
		} else {
			map.addAttribute("code", 404);
			map.addAttribute("message", "购买失败！");
			map.addAttribute("result", false);
		}
	}

	@RequestMapping(value = "/public", method = RequestMethod.GET)
	public String toPublic(HttpSession session, ModelMap map) {
		if (session.getAttribute("user") != null) {
			Person person = (Person) session.getAttribute("user");
			if (person.getUserType() == 1) {
				return "public";
			} else {
				return "login";
			}
		} else
			return "login";

	}

	@RequestMapping(value = "/publicSubmit", method = RequestMethod.POST)
	public String toPublicSubmit(HttpSession session, ModelMap map,
			@RequestParam("title") String title,
			@RequestParam("image") String image,
			@RequestParam("detail") String detail,
			@RequestParam("price") double price,
			@RequestParam("summary") String summary) throws Exception {
		// D2.1 标题长度在[2,80]字符内；
		// D2.2 图片限制大小为<1MB；
		// D2.3 摘要长度在[2,140]字符内；
		// D2.4 正文长度在[2,1000]字符内；
		// D2.5 设计内容总数不超过1000个。
		if (session.getAttribute("user") != null) {
			// Person person = (Person) session.getAttribute("user");
			// map.addAttribute("user", person);
			byte[] icon = imageService.imageUrlToByte(image);
			if (title.length() >= 2 && title.length() <= 80
					&& detail.length() >= 2 && detail.length() <= 1000
					&& summary.length() >= 2 && summary.length() <= 140
					&& icon.length < 1048576) {
				byte[] text = detail.getBytes();
				Content product = new Content();
				product.setIcon(icon);
				product.setPrice((int) (Math.ceil(price * 100)));
				product.setSummary(summary);
				product.setText(text);
				product.setTitle(title);
				contentService.insertRecord(product);
				System.out.println("id:" + product.getId());
				// 创建图片
				imageService.setImageFile(product.getId(), icon, false);
				map.addAttribute("product", product);
				return "publicSubmit";
			} else {
				return "public";
			}

		} else
			return "login";

	}

	@RequestMapping(value = "edit", method = RequestMethod.GET)
	public String edit(HttpSession session, ModelMap map,
			@RequestParam("id") int id) {
		if (session.getAttribute("user") != null) {
			Person person = (Person) session.getAttribute("user");
			if (person.getUserType() == 1) {
				Product p = productService.getProductById(id);
				map.addAttribute("product", p);
				return "edit";
			} else {
				return "login";
			}
		} else
			return "login";

	}

	@RequestMapping(value = "editSubmit", method = RequestMethod.POST)
	public void editSubmit(HttpSession session, ModelMap map,
			@RequestParam("id") int id, @RequestParam("title") String title,
			@RequestParam("image") String imageURL,
			@RequestParam("detail") String detail,
			@RequestParam("price") double price,
			@RequestParam("summary") String summary) throws Exception {
		Person person = (Person) session.getAttribute("user");
		if (person.getUserType() == 1) {
			byte[] icon = imageService.imageUrlToByte(imageURL);
			// 替换文件路径下的图片
			imageService.setImageFile(id, icon, true);
			byte[] text = detail.getBytes();
			Content content = new Content();
			content.setId(id);
			content.setIcon(icon);
			content.setPrice((int) (Math.ceil(price * 100)));
			content.setSummary(summary);
			content.setText(text);
			content.setTitle(title);
			contentService.updateRecord(content);
			map.addAttribute("product", content);
		}
	}

	// 404
	@RequestMapping(value = "/noFound", method = RequestMethod.GET)
	public String noFound() {
		return "/error/noFound";
	}

	// 400
	@RequestMapping(value = "/badRequest")
	public String badRequest() {
		return "/error/badRequest";
	}

	// 403、405
	@RequestMapping(value = "/noPermission")
	public String noPermission() {
		return "/error/noPermission";
	}

	// 500
	@RequestMapping(value = "/internalError")
	public String internalError() {
		return "/error/internalError";
	}
}
