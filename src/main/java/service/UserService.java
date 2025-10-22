package service;

import dao.UserDAO;
import model.User; 

public class UserService {

    UserDAO userDAO = new UserDAO();
 // DAOにログイン認証を依頼
    // findByLogin(User user) メソッドの呼び出し
    public User execute(User inputUser) {
        User loginUser = userDAO.findByLogin(inputUser);
        return loginUser;
    }

    // loginID重複チェック
    public boolean isLoginIdAvailable(String loginId) {
        boolean exists = userDAO.findByLoginId(loginId);
        return !exists;  // 存在しなければ利用可能
    }

    // 登録
    public boolean registerUser(User user) {
        // もし重複チェックして問題あればfalse返す
        if (!isLoginIdAvailable(user.getLoginid())) {
            return false;
        }
        // DAOの登録メソッドを呼ぶ
        return userDAO.userCreate(user);
    }
}

 