package tw.com.ispan.ted.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.RequestEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

import tw.com.ispan.ted.domain.MembersBean;
import tw.com.ispan.ted.domain.UserBean;
import tw.com.ispan.ted.service.MembersService;
import tw.com.ispan.ted.service.UserService2;


@Controller
public class LoginController {

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private UserService2 userService2;

    @Autowired
    private MembersService membersService;

//	@PostMapping("/login")
//	public


    @RequestMapping(path = {"/loginController"})
    public String handlerMethod(Locale locale, Model model, String username, String password, HttpSession session) {
//接收資料
//驗證資料
        System.out.println("logininininininin");


        UserBean bean = new UserBean();
        bean.setUserAccount(username);
        bean.setUserPassword(password);
//		System.out.println(locale.getCountry());System.out.println(locale.getDisplayCountry());  TODO 為什麼locale都抓到西班牙

        if (username == null || username.length() == 0) {
            model.addAttribute("username", messageSource.getMessage("login.username.required", null, Locale.TAIWAN));  //model.addAttr = request.setAttr
        }
        if (password == null || password.length() == 0) {
            model.addAttribute("password", "請輸入密碼");
        }
        if (model.getAttribute("username") != null || model.getAttribute("password") != null) {
            return "th_boLoginPage";
        }

//根據model執行結果，導向view
        if (!userService2.login(bean)) { //驗證帳號密碼
            model.addAttribute("login", "帳號密碼輸入有誤");
            return "th_boLoginPage";
        } else {

            var temp = userService2.selectByUserAccount(username);
            if (temp != null) {
                session.setAttribute("role", temp.get(0).getUserRole());
                System.out.println("login role=" + temp.get(0).getUserRole());
            }
            session.setAttribute("login", username);//為session設定屬性("login"),值=username
            session.setAttribute("memberId", 4);//模擬前台會員登入要把member的ID放到session
            return "redirect:/home";
        }
    }


    @RequestMapping("/backOfficelogout")
    public String handlerMethod2(HttpSession session) {
        session.invalidate();
        return "th_boLoginPage";
    }

//	@GetMapping("/frontLogout")
//	public String frontLogout(HttpSession session) {
//		RequestEntity.post("https://www.google.com/accounts/Logout?continue=https://appengine.google.com/_ah/logout?continue=http:localhost://8081/logout").build();
//
//		session.invalidate();
//		//todo
//			return "th_boLoginPage";
//	}

    @GetMapping("/loginCheck")
    public RedirectView index(@AuthenticationPrincipal OAuth2User principal, HttpSession session, HttpServletResponse response) {
        //System.out.println(principal);
        //System.out.println((String) principal.getAttribute("sub"));
        String email = (String) principal.getAttribute("email");
        System.out.println("email=" + email);
        MembersBean theMembersBean = membersService.selectByEmail(email);

        // Cookie cookie = new Cookie("googleLogin", "ture");
        // response.addCookie(cookie);

        if (theMembersBean != null) {

            System.out.println("find="+theMembersBean);
            session.setAttribute("user", theMembersBean);
            return new RedirectView("http://localhost:8081/index#");
        } else {
            System.out.println("not find, third party email" + email);
            MembersBean tempMember = new MembersBean();
            tempMember.setMemberEmail(email);
            tempMember.setMemberId(0);
            session.setAttribute("email", email);
            return new RedirectView("http://localhost:8081/index#/register");
        }

    }

}
