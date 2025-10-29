package model;

import java.io.Serializable;

/**
 * ユーザー情報を表すモデルクラス。
 * <p>
 * このクラスは、ユーザーID、ログインID、名前、パスワードを保持します。
 * セッションやDBとのやり取りで使用されます。
 * </p>
 */
public class User implements Serializable {

    private int userId;
    private String loginid;
    private String name;
    private String password;

    /**
     * デフォルトコンストラクタ
     */
    public User() {
    }

    /**
     * ユーザー登録用のコンストラクタ
     *
     * @param loginid ログインID
     * @param name ユーザー名
     * @param password パスワード
     */
    public User(String loginid, String name, String password) {
        this.loginid = loginid;
        this.name = name;
        this.password = password;
    }

    /**
     * ログイン用のコンストラクタ
     *
     * @param loginid ログインID
     * @param password パスワード
     */
    public User(String loginid, String password) {
        super();
        this.loginid = loginid;
        this.password = password;
    }

    /**
     * 全フィールドを指定するコンストラクタ
     *
     * @param userId ユーザーID
     * @param loginid ログインID
     * @param name ユーザー名
     * @param password パスワード
     */
    public User(int userId, String loginid, String name, String password) {
        this.userId = userId;
        this.loginid = loginid;
        this.name = name;
        this.password = password;
    }

    /**
     * ユーザーIDを取得する
     * @return ユーザーID
     */
    public int getUserId() {
        return userId;
    }

    /**
     * ユーザーIDを設定する
     * @param userId ユーザーID
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * ログインIDを取得する
     * @return ログインID
     */
    public String getLoginid() {
        return loginid;
    }

    /**
     * ログインIDを設定する
     * @param loginid ログインID
     */
    public void setLoginid(String loginid) {
        this.loginid = loginid;
    }

    /**
     * ユーザー名を取得する
     * @return ユーザー名
     */
    public String getName() {
        return name;
    }

    /**
     * ユーザー名を設定する
     * @param name ユーザー名
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * パスワードを取得する
     * @return パスワード
     */
    public String getPassword() {
        return password;
    }

    /**
     * パスワードを設定する
     * @param password パスワード
     */
    public void setPassword(String password) {
        this.password = password;
    }

}
