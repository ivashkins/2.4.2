package ivashproject.security;

import ivashproject.dao.UserDao;
import ivashproject.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("detailService")
public class UserDetailServiceIml2 implements UserDetailsService {
 final
 UserDao userDao;

    public UserDetailServiceIml2(UserDao userDao) {
        this.userDao = userDao;
    }


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = findUserByEmail(s);
        if (user == null) {
            throw new UsernameNotFoundException("User not found " + s);
        }
        return user;
    }

    public User findUserByEmail(String userEmail) {
        return userDao.show(userEmail);
    }
}
