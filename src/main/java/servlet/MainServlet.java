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
 * Servlet implementation class MainServlet
 */
@WebServlet("/MainServlet")
public class MainServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MainServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/main.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//リクエストパラメータを取得
		request.setCharacterEncoding("UTF-8");
		String loginid = request.getParameter("loginid");
		String password = request.getParameter("pass");

		//入力パスワードのハッシュ化
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(password.getBytes());
			byte[] hashBytes = md.digest();
			password = Base64.getEncoder().encodeToString(hashBytes);

			// ★★★ デバッグコード(テスト） ★★★
			System.out.println("--- ログインデバッグ情報 ---");
			System.out.println("入力ID: " + loginid);
			System.out.println("生成ハッシュ: " + password);
			System.out.println("--------------------------");
			// ★★★ -------------------- ★★★
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		//Userインスタンスの生成
		User user = new User(loginid, password);

		//ログイン処理
		UserService userService = new UserService();
		User loginUser = userService.execute(user);

		//ログイン成功
		if (loginUser != null) {
			//HttpSessionインスタンスの取得
			HttpSession session = request.getSession();
			//セッションスコープにインスタンスを保存
			session.setAttribute("loginUser", loginUser);
			//ログイン成功時の画面遷移
			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/main.jsp");
			dispatcher.forward(request, response);
			//ログイン失敗
		} else {
			request.setAttribute("errorMsg", "ログインIDまたはパスワードが正しくありません。");
			request.getRequestDispatcher("login.jsp").forward(request, response);
		}
	}

}
