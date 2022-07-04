package tw.com.ispan.ted.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tw.com.ispan.ted.domain.UserBean;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserBean, Integer> {
    List<UserBean> findByUserAccount(String account);
}
