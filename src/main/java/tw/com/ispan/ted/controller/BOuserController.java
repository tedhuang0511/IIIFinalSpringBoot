package tw.com.ispan.ted.controller;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tw.com.ispan.ted.domain.UserBean;
import tw.com.ispan.ted.service.UserService2;
import tw.com.ispan.ted.utils.OnlineUser;

import javax.servlet.http.HttpSession;
import java.util.Date;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class BOuserController {

    @Autowired
    UserService2 userService2;

    @GetMapping({"/user/{id}"})
    public ResponseEntity<?> select(@PathVariable("id") Integer id){
        UserBean bean = new UserBean();
        bean.setUserId(id);
        var res = userService2.select(bean);
        if(res!=null){
            return ResponseEntity.ok(res);
        }
        return ResponseEntity.notFound().build();
    }
    @PostMapping("/user/mutilSelect")
    public ResponseEntity<?> mutilselect(@RequestBody UserBean bean){
        System.out.println(bean);
        var res = userService2.select(bean);
        if(res!=null){
            return ResponseEntity.ok(res);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/user/add")
    public ResponseEntity<?> newUser(@RequestBody UserBean bean,
                                     HttpSession session){
        bean.setCreateUser((String) session.getAttribute("login"));
        bean.setUpdateUser((String) session.getAttribute("login"));
        bean.setCreateDate(new Date());
        bean.setUpdateDate(new Date());
        bean.setUserId(0);
        System.out.println(bean);
        if (bean != null) {
            var res = userService2.insert(bean);
            if(res!=null){
                return ResponseEntity.ok(res);
            }
        }
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/user/update")
    public ResponseEntity<?> updateUser(@RequestBody UserBean bean,HttpSession session){
        bean.setUpdateUser((String) session.getAttribute("login"));
        bean.setUpdateDate(new Date());
        System.out.println(bean);
        if(bean!=null){
            var res = userService2.update(bean);
            return ResponseEntity.ok(res);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/user/delete/{id}")
    public  ResponseEntity<?> deleteUser(@PathVariable int id){
        UserBean bean = new UserBean();
        bean.setUserId(id);
        if(userService2.delete(bean)){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/user/session_user_list")
    public ResponseEntity<?> getSessionUserDataList(){
        var sessions = OnlineUser.sessions;
        var jarr = new JSONArray();
        if (!sessions.isEmpty()){
            sessions.values().forEach(session1 -> {
                JSONObject jobj = new JSONObject();
                jobj.put("userName",session1.getAttribute("login"));
                jobj.put("createTime",Long.toString(session1.getCreationTime()));
                jobj.put("lastAccess",Long.toString(session1.getLastAccessedTime()));
                jobj.put("userRole",session1.getAttribute("role"));
                jobj.put("sessionId",session1.getId());
                jarr.put(jobj);
            });
            return ResponseEntity.ok(jarr.toString());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/user/session/kick/{username}")
    public ResponseEntity kickUser(@PathVariable("username") String sessionId){
        try{
            var sessions = OnlineUser.sessions;
            var session = sessions.get(sessionId);
            session.invalidate();
            return ResponseEntity.ok().build();
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }

    }
}
