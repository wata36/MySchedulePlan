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
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/main.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		//★ユーザーログインする
		//リクエストパラメータを取得
		request.setCharacterEncoding("UTF-8");
		String loginid = request.getParameter("loginid");
		String password = request.getParameter("pass");

		String action = request.getParameter("action");

		HttpSession session = request.getSession();
		User loginUser = (User) session.getAttribute("loginUser");

		if (loginid != null && password != null && !loginid.isEmpty()) {
			//入力パスワードのハッシュ化
			try {
				MessageDigest md = MessageDigest.getInstance("SHA-256");
				md.update(password.getBytes());
				byte[] hashBytes = md.digest();
				password = Base64.getEncoder().encodeToString(hashBytes);

			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}

			//Userインスタンスの生成
			User user = new User(loginid, password);

			//ログイン処理
			UserService userService = new UserService();
			loginUser = userService.execute(user);

			//ログイン成功
			if (loginUser != null) {
				//					//HttpSessionインスタンスの取得
				//					HttpSession session = request.getSession();
				//セッションスコープにインスタンスを保存
				session.setAttribute("loginUser", loginUser);

				//ログイン失敗
			} else {
				request.setAttribute("errorMsg", "ログインIDまたはパスワードが正しくありません");
				request.getRequestDispatcher("login.jsp").forward(request, response);
				return;//処理を終了	
			}

		}

		if ("regist".equals(action)) {
			//予定登録
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

		}

		// 予定一覧を取得する
		ScheduleService scheduleService = new ScheduleService();
		List<Schedule> scheduleList = scheduleService.getSchedulesByUserId(loginUser.getUserId());
		request.setAttribute("scheduleList", scheduleList);
		//ログイン成功時の画面遷移
		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/main.jsp");
		dispatcher.forward(request, response);
	}
}
