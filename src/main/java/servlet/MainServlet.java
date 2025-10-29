package servlet;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Base64;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import model.Schedule;
import model.User;
import service.ScheduleService;
import service.UserService;

/**
 * {@code MainServlet} クラスは、ユーザーのログイン処理および
 * スケジュール一覧表示・スケジュール登録を担当するサーブレットです。
 *
 * <p>主な機能：</p>
 * <ul>
 *   <li>ログイン処理（ユーザー認証）</li>
 *   <li>ユーザーのスケジュール一覧表示</li>
 *   <li>スケジュールの新規登録</li>
 * </ul>
 *
 * <p>
 * ログイン済みユーザーのみがアクセス可能です。<br>
 * セッション管理により、未ログイン時はログイン画面へリダイレクトします。
 * </p>
 *
 * <p>URLパターン: {@code /MainServlet}</p>
 *
 * @author  
 * @version 1.0
 */
@WebServlet("/MainServlet")
public class MainServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * デフォルトコンストラクタ。
	 * サーブレットの初期化処理を行います。
	 */
	public MainServlet() {
		super();
	}

	/**
	 * GETリクエストを処理します。
	 * <p>
	 * ログイン済みユーザーのスケジュール一覧を取得し、メイン画面（{@code main.jsp}）へフォワードします。<br>
	 * 未ログイン状態の場合はログイン画面へリダイレクトします。
	 * </p>
	 *
	 * @param request  クライアントからのリクエスト
	 * @param response サーバーからのレスポンス
	 * @throws ServletException サーブレット関連のエラーが発生した場合
	 * @throws IOException      入出力エラーが発生した場合
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// セッション取得（存在しなければnull）
		HttpSession session = request.getSession(false);

		// 未ログインの場合はログイン画面へリダイレクト
		if (session == null) {
			response.sendRedirect("login.jsp");
			return;
		}

		User loginUser = (User) session.getAttribute("loginUser");
		if (loginUser == null) {
			response.sendRedirect("login.jsp");
			return;
		}

		// スケジュール一覧の取得
		ScheduleService scheduleService = new ScheduleService();
		List<Schedule> scheduleList = scheduleService.getSchedulesByUserId(loginUser.getUserId());
		request.setAttribute("scheduleList", scheduleList);

		// 一覧画面にフォワード
		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/main.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * POSTリクエストを処理します。
	 * <p>
	 * 主にログイン処理とスケジュール登録処理を行い、結果に応じて画面遷移を制御します。
	 * </p>
	 *
	 * <ul>
	 *   <li>ログイン処理：{@code loginid} と {@code pass} を検証し、成功時にセッションへ保存。</li>
	 *   <li>スケジュール登録：{@code action=regist} の場合、日付とタイトルをDBに登録。</li>
	 * </ul>
	 *
	 * @param request  クライアントからのリクエスト
	 * @param response サーバーからのレスポンス
	 * @throws ServletException サーブレット関連のエラーが発生した場合
	 * @throws IOException      入出力エラーが発生した場合
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// -------------------------------
		// リクエストパラメータ取得
		// -------------------------------
		request.setCharacterEncoding("UTF-8");
		String loginid = request.getParameter("loginid");
		String password = request.getParameter("pass");
		String action = request.getParameter("action");

		HttpSession session = request.getSession();
		User loginUser = (User) session.getAttribute("loginUser");

		// -------------------------------
		// ログイン処理
		// -------------------------------
		if (loginid != null && password != null && !loginid.isEmpty()) {

			// パスワードのハッシュ化（SHA-256）
			try {
				MessageDigest md = MessageDigest.getInstance("SHA-256");
				md.update(password.getBytes());
				byte[] hashBytes = md.digest();
				password = Base64.getEncoder().encodeToString(hashBytes);
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}

			// 入力値をUserオブジェクトに格納
			User user = new User(loginid, password);

			// サービスを使用して認証を実行
			UserService userService = new UserService();
			loginUser = userService.execute(user);

			// 認証結果による分岐
			if (loginUser != null) {
				// ログイン成功 → セッションに保存
				session.setAttribute("loginUser", loginUser);
			} else {
				// ログイン失敗 → エラーメッセージ設定
				request.setAttribute("errorMsg", "ログインIDまたはパスワードが正しくありません");
				request.getRequestDispatcher("login.jsp").forward(request, response);
				return;
			}
		}

		// -------------------------------
		// スケジュール登録処理
		// -------------------------------
		if ("regist".equals(action)) {
			String datestr = request.getParameter("date");
			String title = request.getParameter("title");

			if (datestr != null && title != null && !datestr.isEmpty() && !title.isEmpty()) {
				try {
					LocalDate date = LocalDate.parse(datestr);
					Schedule newSchedule = new Schedule();
					newSchedule.setUser_id(loginUser.getUserId());
					newSchedule.setDate(date);
					newSchedule.setTitle(title);

					ScheduleService scheduleService = new ScheduleService();
					scheduleService.registerSchedule(newSchedule);
				} catch (DateTimeParseException e) {
					request.setAttribute("errorMsg", "全ての項目を入力してください");
				}
			}

			// PRG（Post-Redirect-Get）でリロードによる再登録を防止
			response.sendRedirect("MainServlet");
			return;
		}

		// -------------------------------
		// スケジュール一覧再取得＆画面遷移
		// -------------------------------
		ScheduleService scheduleService = new ScheduleService();
		List<Schedule> scheduleList = scheduleService.getSchedulesByUserId(loginUser.getUserId());
		request.setAttribute("scheduleList", scheduleList);

		// メイン画面へフォワード
		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/main.jsp");
		dispatcher.forward(request, response);
	}
}
