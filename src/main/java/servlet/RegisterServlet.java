package servlet;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import model.User;
import service.UserService;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RegisterServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/register.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 1. セッションの取得はここで一度だけ行う
		HttpSession session = request.getSession();

		// リクエストパラメータの取得
		request.setCharacterEncoding("UTF-8");
		String loginid = request.getParameter("loginid");
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		String password2 = request.getParameter("password2");

		// フォームに戻すためのユーザーオブジェクト（パスワードは含めない）
		User formUser = new User(loginid, name, null);

		UserService userService = new UserService();
		String errorMsg = null;

		// 登録画面に戻すためのディスパッチャー
		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/register.jsp");

		// 入力値のチェック

		//パスワードと確認用パスワードの一致チェック
		if (!password.equals(password2)) {
			errorMsg = "パスワードと確認用パスワードが一致しません";
		}

		// パスワードの条件チェック
		else if (!password.matches("^[a-zA-Z0-9]{12,}$")) {
			errorMsg = "パスワードが条件を満たしていません (12文字以上の英数字)";
		}

		// ログインIDの重複チェック (Serviceに依頼)
		else if (!userService.isLoginIdAvailable(loginid)) {
			errorMsg = "このログインIDはすでに使われています";
		}

		// エラー判定と画面遷移 (エラー判定と画面遷移)

		if (errorMsg != null) {
			// エラーがあれば、メッセージとフォームデータを設定して登録画面に戻る
			request.setAttribute("errorMsg", errorMsg);
			request.setAttribute("form", formUser);
			dispatcher.forward(request, response);
			return; // 処理終了
		}

		// 登録処理 (エラーがなければ実行)

		// ★初期化
		String hashedPassword = null;
		try {
			// パスワードのハッシュ化
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(password.getBytes());
			byte[] hashBytes = md.digest();
			hashedPassword = Base64.getEncoder().encodeToString(hashBytes);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		// 登録用ユーザーインスタンス作成
		User newUser = new User(loginid, name, hashedPassword);

		// サービスに登録実行を依頼
		boolean success = userService.registerUser(newUser);

		// 結果の判定と画面遷移

		if (success) {
			// 登録成功フラグをセッションに設定
			session.setAttribute("registrationComplete", true);

			// ログイン画面へリダイレクト
			response.sendRedirect("login.jsp");
			return;
		} else {
			// 登録失敗（DBエラーなど）
			request.setAttribute("errorMsg", "登録に失敗しました");
			request.setAttribute("form", formUser);
			dispatcher.forward(request, response);
			return;
		}
	}
}
