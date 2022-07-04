package tw.com.ispan.ted.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PathController {
	@RequestMapping(value = "/pages/product.view")
	public String handlerMethod3() {
		return "/pages/product";
	}
	@RequestMapping("/pages/display.view")
	public String handlerMethod4() {
		return "/pages/display";
	}

	//	html
	@RequestMapping(path = {"/"})  //外部轉址
	public String handlerMethod5() {
		return "th_boLoginPage"; //程式內部轉址
	}
	@RequestMapping(path = {"/home"})  //外部轉址
	public String handlerMethod6() {
		return "th_home"; //程式內部轉址
	}
	@RequestMapping(path = {"/checkout"})  //外部轉址
	public String handlerMethod7() {
		return "th_testShoppingCart"; //程式內部轉址
	}
	@RequestMapping(path = {"/checkout/linepay"})  //外部轉址
	public String handlerMethod8() {
		return "th_linepayprocesspage"; //程式內部轉址
	}
	@RequestMapping(path = {"/user"})  //外部轉址
	public String handlerMethod9() {
		return "th_displayBoUser"; //程式內部轉址
	}
	@RequestMapping(path = {"/index"})  //外部轉址
	public String handlerMethod10() {
		return "th_index"; //程式內部轉址
	}

}
