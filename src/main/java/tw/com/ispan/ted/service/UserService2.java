package tw.com.ispan.ted.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import tw.com.ispan.ted.dao.UserRepository;
import tw.com.ispan.ted.domain.UserBean;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService2 {
    @Autowired
    UserRepository userRepository;

    public List<UserBean> selectByUserAccount(String acc){
        var res = userRepository.findByUserAccount(acc);
        if(!res.isEmpty()){
            return res;
        }
        return null;
    }

    public boolean login(UserBean bean) {
        var res = userRepository.findByUserAccount(bean.getUserAccount()); //controller丟回一個bean 把bean的account拿出來去找
        if(!res.isEmpty()){ //如果DB有找到這筆資料
            var userpw = res.get(0).getUserPassword();//拿到使用者DB的密碼
            System.out.println("input="+bean.getUserPassword());
            System.out.println("fromDB="+userpw);
            if(BCrypt.checkpw(bean.getUserPassword(),userpw)){
                //如果使用者輸入的跟DB撈出來的經過BCrypt一樣
                return true;
            }else{
                //密碼不一樣
                return false;
            }
        }else{
            //DB沒找到資料
            return false;
        }
    }

    public List<UserBean> select(UserBean bean){
        List<UserBean> result = new ArrayList<>();
        if(bean!=null && bean.getUserId()!=null && bean.getUserId()!=0){
            var data = userRepository.findById(bean.getUserId());
            if(data.isPresent()){
                result.add(data.get());
            }
        }else{

            ExampleMatcher matcher = ExampleMatcher.matching()
                    .withMatcher("userAccount",ExampleMatcher.GenericPropertyMatchers.contains())
                    .withMatcher("userRole",ExampleMatcher.GenericPropertyMatchers.contains())
                    .withMatcher("userEmail",ExampleMatcher.GenericPropertyMatchers.contains());
            result = userRepository.findAll(Example.of(bean,matcher));
        }
        return result;
    }

    public UserBean insert(UserBean bean){
        if(bean!=null && bean.getUserId()!=null){
            if(!userRepository.existsById(bean.getUserId())){
                String pw_hash = BCrypt.hashpw(bean.getUserPassword(), BCrypt.gensalt());
                System.out.println(pw_hash+"p_hash");
                bean.setUserPassword(pw_hash);
                return userRepository.save(bean);
            }
        }
        return null;
    }

    public UserBean update(UserBean bean){
        if(bean!=null && bean.getUserId()!=null){
            if(userRepository.existsById(bean.getUserId())){
                UserBean temp = userRepository.getReferenceById(bean.getUserId());
                temp.setUserPassword(BCrypt.hashpw(bean.getUserPassword(), BCrypt.gensalt()));
                temp.setUserRole(bean.getUserRole());
                temp.setUserEmail(bean.getUserEmail());
                temp.setUpdateDate(bean.getUpdateDate());
                temp.setUpdateUser(bean.getUpdateUser());
                return userRepository.save(temp);
            }
        }
        return null;
    }

    public boolean delete(UserBean bean){
        if(bean!=null && bean.getUserId()!=null){
            if(userRepository.existsById(bean.getUserId())){
                userRepository.deleteById(bean.getUserId());
                return true;
            }
        }
        return false;
    }
}