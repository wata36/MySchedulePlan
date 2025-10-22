package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.User;

public class UserDAO {

	//ユーザーログイン処理	
	public User findByLogin(User user) {
		User loginuser = null;
		//		DBManagerからgetConnection()でSQL接続
		try (Connection conn = DBManager.getConnection()) {

			// SELECT文を準備
			String sql = "SELECT user_id, user_login, user_name, password FROM users WHERE user_login = ? AND password = ?";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, user.getLoginid());
			pStmt.setString(2, user.getPassword());

			// SELECT文を実行し、結果表（ResultSet）を取得
			ResultSet rs = pStmt.executeQuery();

			// 結果表に格納されたレコードの内容を表示
			if (rs.next()) {
				int userId = rs.getInt("user_id");
				String loginId = rs.getString("user_login");
				String name = rs.getString("user_name");
				String password = rs.getString("password");

				loginuser = new User(userId, loginId, name, password);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return loginuser;
	}

	//ユーザー登録処理
	public boolean userCreate(User user) {
		//		DBManagerからgetConnection()でSQL接続
		try (Connection conn = DBManager.getConnection()) {

			// SELECT文を準備
			String sql = "INSERT INTO USERS (USER_LOGIN,USER_NAME,PASSWORD) VALUES (?,?,?)";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			// プレースホルダに値をセット
			pStmt.setString(1, user.getLoginid());
			pStmt.setString(2, user.getName());
			pStmt.setString(3, user.getPassword());

			// 実行結果（影響を受けた行数）を取得
			int rows = pStmt.executeUpdate();
			return rows > 0; // 1件以上登録できれば成功

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		}
		//ログインID重複
	// UserDAOにログインIDの存在チェックメソッド
	public boolean findByLoginId(String loginId) {
	    try (Connection conn = DBManager.getConnection()) {
	        String sql = "SELECT 1 FROM users WHERE user_login = ?";
	        PreparedStatement pStmt = conn.prepareStatement(sql);
	        pStmt.setString(1, loginId);
	        ResultSet rs = pStmt.executeQuery();
	        boolean exists = rs.next(); // 1件でもあればtrue
	        rs.close();
	        //存在したら返す
	        return exists;
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}

}
