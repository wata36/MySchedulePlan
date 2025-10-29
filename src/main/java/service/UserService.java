package service;

import dao.UserDAO;
import model.User;

/**
 * ユーザー関連のビジネスロジックを提供するサービスクラス。
 * <p>
 * このクラスは、ユーザーのログイン認証、ログインID重複チェック、
 * ユーザー登録などの処理を提供します。
 * DAOに直接アクセスすることで、DB操作を仲介します。
 * </p>
 */
public class UserService {

    UserDAO userDAO = new UserDAO();

    /**
     * DAOにログイン認証を依頼
     * findByLogin(User user) メソッドの呼び出し
     *
     * @param inputUser ログインIDとパスワードを持つユーザー情報
     * @return 認証成功時はログインユーザー情報（Userオブジェクト）、認証失敗時は null
     */
    public User execute(User inputUser) {
        User loginUser = userDAO.findByLogin(inputUser);
        return loginUser;
    }

    /**
     * loginID重複チェック
     *
     * @param loginId チェックするログインID
     * @return 利用可能であれば true、すでに存在する場合は false
     */
    public boolean isLoginIdAvailable(String loginId) {
        boolean exists = userDAO.findByLoginId(loginId);
        return !exists; // 存在しなければ利用可能
    }

    /**
     * 登録
     * <p>
     * もし重複チェックして問題あれば false を返す
     * DAOの登録メソッドを呼ぶ
     * </p>
     *
     * @param user 登録するユーザー情報
     * @return 登録成功時は true、重複IDやDBエラーなどで登録できない場合は false
     */
    public boolean registerUser(User user) {
        // もし重複チェックして問題あればfalse返す
        if (!isLoginIdAvailable(user.getLoginid())) {
            return false;
        }
        // DAOの登録メソッドを呼ぶ
        return userDAO.userCreate(user);
    }
}
