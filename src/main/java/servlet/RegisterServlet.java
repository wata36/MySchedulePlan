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
 * {@code RegisterServlet} クラスは、新規ユーザー登録を処理するサーブレットです。
 * <p>
 * 登録フォームから送信されたユーザー情報（ログインID、名前、パスワード）を受け取り、
 * 以下の手順で新規ユーザー登録を行います。
 * </p>
 *
 * <ol>
 *   <li>入力チェック（パスワード一致、形式、重複チェック）</li>
 *   <li>パスワードをSHA-256でハッシュ化</li>
 *   <li>DBにユーザー情報を登録（{@link service.UserService} を利用）</li>
 *   <li>成功時：ログイン画面へリダイレクト</li>
 *   <li>失敗時：エラーメッセージを設定して登録画面に戻す</li>
 * </ol>
 *
 * <p>URLパターン: {@code /RegisterServlet}</p>
 *
 * @author 
 * @version 1.0
 */
@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * デフォルトコンストラクタ。
	 * サーブレットの初期化処理を行います。
	 */
	public RegisterServlet() {
		super();
	}

	/**
	 * GETリクエストを処理します。
	 * <p>
	 * 登録フォームを表示するために {@code register.jsp} にフォワードします。
	 * </p>
	 *
	 * @param request  クライアントからのリクエスト
	 * @param response サーバーからのレスポンス
	 * @throws ServletException サーブレットエラーが発生した場合
	 * @throws IOException      入出力エラーが発生した場合
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/register.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * POSTリクエストを処理します。
	 * <p>
	 * ユーザー登録フォームの内容を受け取り、入力チェック → パスワードのハッシュ化 →
	 * DB登録 → 結果に応じた画面遷移を行います。
	 * </p>
	 *
	 * @param request  クライアントからのリクエスト（フォームデータを含む）
	 * @param response サーバーからのレスポンス
	 * @throws ServletException サーブレットエラーが発生した場合
	 * @throws IOException      入出力エラーが発生した場合
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// -------------------------------------
		// セッションの取得（完了フラグ管理に使用）
		// -------------------------------------
		HttpSession session = request.getSession();

		// -------------------------------------
		// フォームデータ取得
		// -------------------------------------
		request.setCharacterEncoding("UTF-8");
		String loginid = request.getParameter("loginid");
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		String password2 = request.getParameter("password2");

		// 再表示用（パスワードは含めない）
		User formUser = new User(loginid, name, null);

		UserService userService = new UserService();
		String errorMsg = null;
		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/register.jsp");

		// -------------------------------------
		// 入力チェック
		// -------------------------------------

		// パスワード一致確認
		if (!password.equals(password2)) {
			errorMsg = "パスワードと確認用パスワードが一致しません";
		}

		// パスワードの形式チェック（12文字以上の英数字）
		else if (!password.matches("^[a-zA-Z0-9]{12,}$")) {
			errorMsg = "パスワードが条件を満たしていません (12文字以上の英数字)";
		}

		// ログインID重複チェック
		else if (!userService.isLoginIdAvailable(loginid)) {
			errorMsg = "このログインIDはすでに使われています";
		}

		// -------------------------------------
		// エラー時の処理
		// -------------------------------------
		if (errorMsg != null) {
			request.setAttribute("errorMsg", errorMsg);
			request.setAttribute("form", formUser);
			dispatcher.forward(request, response);
			return;
		}

		// -------------------------------------
		// パスワードハッシュ化処理
		// -------------------------------------
		String hashedPassword = null;
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(password.getBytes());
			byte[] hashBytes = md.digest();
			hashedPassword = Base64.getEncoder().encodeToString(hashBytes);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		// -------------------------------------
		// ユーザー登録処理
		// -------------------------------------
		User newUser = new User(loginid, name, hashedPassword);
		boolean success = userService.registerUser(newUser);

		// -------------------------------------
		// 結果に応じた画面遷移
		// -------------------------------------
		if (success) {
			// 登録成功フラグを設定してログイン画面へ
			session.setAttribute("registrationComplete", true);
			response.sendRedirect("login.jsp");
			return;
		} else {
			// 登録失敗時（DBエラーなど）
			request.setAttribute("errorMsg", "登録に失敗しました");
			request.setAttribute("form", formUser);
			dispatcher.forward(request, response);
			return;
		}
	}
}
