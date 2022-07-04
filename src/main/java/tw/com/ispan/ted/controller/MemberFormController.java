package tw.com.ispan.ted.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tw.com.ispan.ted.domain.MembersBean;
import tw.com.ispan.ted.service.MemberService2;
import tw.com.ispan.ted.service.MembersService;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class MemberFormController {
    private SimpleDateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd");
    @Autowired
    private MembersService membersService;

    @Autowired
    private MemberService2 memberService2;

    @GetMapping("/member/{memberId}")
    public ResponseEntity<?> findMember(@PathVariable("memberId") int id) {
        MembersBean bean = new MembersBean();
        bean.setMemberId(id);
        var res = membersService.select(bean).get(0);
        if(res!=null){
            return ResponseEntity.ok(res.toString());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/member/new")
    public ResponseEntity<?> insert(@RequestBody MembersBean bean, HttpSession session){

        //設一個errors放置錯誤
        Map<String, String> errors = new HashMap<String, String>();
        Map<String, String> good = new HashMap<String, String>();

        //ID先設0不然會報錯
        bean.setMemberId(0);
        //資料庫欄位的更新創早者資料
        bean.setUpdateUser((String) session.getAttribute("login"));
        bean.setCreateUser((String) session.getAttribute("login"));
        bean.setCreateDate(new Date());
        bean.setUpdateDate(new Date());
        System.out.println(bean);

        //1.
        if(bean.getMemberAccouunt()==null){
            System.out.println("請輸入帳號");
            errors.put("memberAccouunt","請輸入帳號");
        }

        //2.
        if(bean.getMemberPassword()==null){
            System.out.println("請輸入密碼");
            errors.put("MemberPassword","請輸入密碼");
        }
        //3.
        if(bean.getMemberLastname()==null){
            System.out.println("請輸入姓氏");
            errors.put("MemberLastname","請輸入姓氏");
        }
        //4.
        if(bean.getMemberFirstname()==null){
            System.out.println("請輸入名字");
            errors.put("MemberFirstname","請輸入名字");
        }
        //5.
        if(bean.getMemberEmail()==null){
            System.out.println("請輸入信箱");
            errors.put("MemberEmail","請輸入信箱");
        }
        //6.
        if(bean.getMemberAddr() ==null){
            System.out.println("請輸入地址");
            errors.put("MemberAddr","請輸入地址");
        }
        //7.
        if(bean.getMemberBirth() ==null){
            System.out.println("請輸入生日");
            errors.put("MemberBirth","請輸入生日");
        }
        //8.
        if(bean.getMemberNickname() ==null){
            System.out.println("請輸入暱稱");
            errors.put("MemberNickname","請輸入暱稱");
        }
        //9.
        if(bean.getMemberTel() ==null){
            System.out.println("請輸入電話");
            errors.put("MemberTel","請輸入電話");
        }
        //10.
        if(bean.getMemberGender() ==null){
            System.out.println("請輸入性別");
            errors.put("MemberGender","請輸入性別");
        }
        //表示信箱有值
        else if(bean.getMemberEmail()!=null){
            //如果不符合信箱規範
            if (!patternMatches(bean.getMemberEmail())) {
                System.out.println("信箱不符合規範");
                errors.put("MemberEmail","信箱不符合規範");
            }
        }

        //上面五個任一有空值就在這邊報錯
        if(bean.getMemberAccouunt()==null||bean.getMemberPassword()==null||bean.getMemberLastname()==null
        ||bean.getMemberFirstname()==null||bean.getMemberEmail()==null||bean.getMemberAddr() ==null||
        bean.getMemberBirth() ==null||bean.getMemberNickname() ==null||bean.getMemberTel() ==null||
         bean.getMemberGender() ==null||!patternMatches(bean.getMemberEmail())){
            errors.put("returnCode","0001");
            return ResponseEntity.ok(errors);
        }

        //把bean設定好之後做新增的動作
        //1.這是未加密的版本
//        MembersBean res = membersService.insert(bean);

        //2.這是加密的版本
        MembersBean res = memberService2.CryptInsert(bean);


        //回傳200
        if(res!=null){
            good.put("returnCode","0000");
            return ResponseEntity.ok(good);
        }
        //回傳404跟要跳轉的網址
        return ResponseEntity.notFound().build();
    }
    //Email Regex Function
    public static boolean patternMatches(String emailAddress) {
        String regexPattern = "^(.+)@(\\S+)$";
        return Pattern.compile(regexPattern).matcher(emailAddress).matches();
    }
}
