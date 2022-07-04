package tw.com.ispan.ted.controller;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import tw.com.ispan.ted.domain.MembersBean;
import tw.com.ispan.ted.service.MemberService2;
import tw.com.ispan.ted.service.MembersService;
import tw.com.ispan.ted.utils.JavaMail;

import javax.servlet.http.HttpSession;
import java.net.URI;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@RestController
@CrossOrigin
public class KevinLoginController {
    String memberAccount = "";
    String memberPassword = "";
    Map<String, String> errors;
    @Autowired
    private MembersService membersService;
    @Autowired
    private MemberService2 memberService2;
    @Autowired
    private MessageSource messageSource;


    @PostMapping(path = {"/loginJson"})
    public ResponseEntity<?> loginByJson(@RequestBody String body, Locale locale, Model model, HttpSession session) {
        System.out.println("loginByJson有被呼叫到");

//接收資料
//驗證資料
        errors = new HashMap<String, String>();
        model.addAttribute("errors", errors);

        try {
            JSONObject jsonObject = new JSONObject(body);
            //從Json取得帳號密碼
            memberAccount = jsonObject.getString("memberAccouunt");
            memberPassword = jsonObject.getString("memberPassword");

            if(memberAccount==null || memberAccount.length()==0){
                System.out.println("測試memberAccount==null || memberAccount.length()==0)");
                errors.put("memberAccouunt", messageSource.getMessage("member.memberAccouunt.required",null,locale));
            }
            if(memberPassword==null||memberPassword.length()==0){
                System.out.println("測試memberPassword==null||memberPassword.length()==0不應該成立");
                errors.put("memberPassword", messageSource.getMessage("member.memberPassword.required",null,locale));
            }
        } catch (Exception e) {
            System.out.println("轉換帳號密碼有錯誤");
            e.printStackTrace();
            //"帳號跟密碼其中一個有錯誤"
            errors.put("AccountPassword", messageSource.getMessage("member.AccountPassword.failed",null,locale));
        }
        //呼叫model
        MembersBean bean = membersService.login(memberAccount, memberPassword);

        //根據model執行結果，導向view
        if (bean == null) {
            errors.put("beanNull", messageSource.getMessage("member.beanNull.failed",null,locale));
            System.out.println("errors.toString()"+errors.toString());
            //這是回傳404失敗
            ResponseEntity<?> entity = ResponseEntity.notFound().build();
            return entity;
        } else {
            System.out.println("有成功驗證比對帳號密碼！！");
            System.out.println("session.getId() = " + session.getId());
            //把先前登入的帳密刪掉
            session.removeAttribute("user");
            //新增現在登入的這組帳密
            session.setAttribute("user", bean);

            //這是回傳201成功，並回傳成功resource的URI
            URI uri = URI.create("再來要跳轉的網址放這邊");
            ResponseEntity<MembersBean> entity = ResponseEntity.created(uri).body(bean);
            return entity;
        }
    }

    //這個給dashboard跨越session要id再跟MemberRestController要會員所有資料
    @PostMapping(path = {"/dashboardGetMemberId"})
    public ResponseEntity<String> dashboardGetMemberId(Model model, HttpSession session) {
        URI uri = URI.create("再來要跳轉的網址放這邊");
        System.out.println("session.getId() = " + session.getId());
        MembersBean bean = (MembersBean) session.getAttribute("user");

        //這是回傳201成功，並回傳成功bean.getMemberId()+""
        if (bean != null) {
            bean.setMemberPassword("");
            System.out.println("bean.getMemberId() = " + bean.getMemberId());
            ResponseEntity<String> entityString = ResponseEntity.created(uri).body(bean.getMemberId() + "");
            return (entityString);
            //這是回傳404失敗
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @PostMapping(path = {"/loginJsonCrypt"})
    public ResponseEntity<?> loginCrypt(@RequestBody String body, Locale locale, Model model, HttpSession session) {
        System.out.println("loginCrypt有被呼叫到");
        //接收資料
        //驗證資料
        errors = new HashMap<String, String>();
        model.addAttribute("errors", errors);


        try {
            JSONObject jsonObject = new JSONObject(body);
            memberAccount = jsonObject.getString("memberAccouunt");
            memberPassword = jsonObject.getString("memberPassword");
        } catch (Exception e) {
            System.out.println("轉換帳號密碼有錯誤");
            e.printStackTrace();
            errors.put("AccountPassword", "帳號跟密碼其中一個有錯誤");
        }

        //執行比對
        MembersBean bean = memberService2.CryptLogin(memberAccount, memberPassword);

        //根據model執行結果，導向view
        if (bean == null) {
            System.out.println("無此帳號或帳密有錯");
            errors.put("beanNull", "bean == null");

            //這是回傳404失敗
            ResponseEntity<?> entity = ResponseEntity.notFound().build();
            return entity;
        } else {
            System.out.println("有成功驗證比對帳號密碼！！");
            System.out.println("session.getId() = " + session.getId());
            //把先前登入的帳密刪掉
            session.removeAttribute("user");
            //新增現在登入的這組帳密
            session.setAttribute("user", bean);

            //這是回傳201成功，並回傳成功resource的URI
            URI uri = URI.create("再來要跳轉的網址放這邊");
            ResponseEntity<MembersBean> entity = ResponseEntity.created(uri).body(bean);
            return entity;
        }
    }


    //這個應該用不到
    //直接去MemberRestController拿JSON檔
    @PostMapping(path = {"/getMemberAllInfo"})
    public ResponseEntity<?> getMemberAllInfo(HttpSession session) {
        URI uri = URI.create("再來要跳轉的網址放這邊");
        MembersBean bean = (MembersBean) session.getAttribute("user");
        //把密碼拿掉
        bean.setMemberPassword("");
        ResponseEntity<MembersBean> entity = ResponseEntity.created(uri).body(bean);

        //如果在登入狀態就吐出"user"的bean資料
        if (bean != null) {
            return entity;
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(path = {"/forgetPassword"})
    public ResponseEntity<?> forgetPassword(@RequestBody String body){
        JSONObject object = new JSONObject(body);

        if(!object.getString("memberAccouunt").isEmpty()) {
            System.out.println("JSON有抓到帳號");
            memberAccount = object.getString("memberAccouunt");
        }else{
            System.out.println("JSON沒抓到帳號");
            return ResponseEntity.notFound().build();
        }

        //Get memberEmail by login memberAccount
        MembersBean bean = memberService2.findByAccount(memberAccount);

        if(bean!=null) {
            String memberEmail = bean.getMemberEmail();
            String memberFullName = bean.getMemberFirstname() + " " + bean.getMemberLastname();

            JavaMail javaMail = new JavaMail(memberEmail, memberFullName);
            //Send password to email directly.
            String res = javaMail.sendMail(bean.getMemberPassword());
            System.out.println("beanID = "+ bean.getMemberId());
            System.out.println("寄送結果為：" + res);

            return ResponseEntity.ok().build();
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(path = {"/resetPassword"})
    public ResponseEntity<?> resetPassword(@RequestBody String body){
        JSONObject object = new JSONObject(body);

        if(!object.getString("memberAccouunt").isEmpty()) {
            System.out.println("JSON有抓到帳號");
            memberAccount = object.getString("memberAccouunt");
        }else{
            System.out.println("JSON沒抓到帳號");
            return ResponseEntity.notFound().build();
        }

        //Get memberEmail by login memberAccount
        MembersBean bean = memberService2.findByAccount(memberAccount);

        if(bean!=null) {
            String memberEmail = bean.getMemberEmail();
            String memberFullName = bean.getMemberFirstname() + " " + bean.getMemberLastname();

            JavaMail javaMail = new JavaMail(memberEmail, memberFullName);
            //Send password to email directly.
            ;
            String newPW="";

            for(int i=0;i<6;i++){
                newPW += (int)(Math.random()*10)+"";
            }

            System.out.println("newPW = "+newPW);

            String res = javaMail.sendPWMail(newPW);
            memberService2.changePassword(bean, newPW);
            System.out.println("beanID = "+ bean.getMemberId());
            System.out.println("寄送結果為：" + res);

            return ResponseEntity.ok().build();
        }else{
            return ResponseEntity.notFound().build();
        }
    }
}

