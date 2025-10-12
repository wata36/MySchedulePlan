package service;

import dao.UserDAO;
import model.User; 

public class UserService {

    public User execute(User inputUser) {
        
        UserDAO userDAO = new UserDAO();
        
        // DAOにログイン認証を依頼
        // findByLogin(User user) メソッドの呼び出し
        User loginUser = userDAO.findByLogin(inputUser);
        
        return loginUser;
    }
}