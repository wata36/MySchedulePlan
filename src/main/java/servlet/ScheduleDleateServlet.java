package servlet;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import dao.ScheduleDAO;
import model.User;

@WebServlet("/ScheduleDleateServlet")
public class ScheduleDleateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ScheduleDleateServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);

		// ログインチェックとユーザーIDの取得 	
		if (session == null || session.getAttribute("loginUser") == null) {
			response.sendRedirect(request.getContextPath() + "/login.jsp");
			return;
		}

		User loginUser = (User) session.getAttribute("loginUser");
		int userId = loginUser.getUserId();

		String scheduleIdStr = request.getParameter("schedule_id");

		if (scheduleIdStr == null || scheduleIdStr.isEmpty()) {
			request.setAttribute("errorMsg", "削除対象のスケジュール情報が見つかりません。");
			// エラー時は必ずMainServletにフォワード
			RequestDispatcher dispatcher = request.getRequestDispatcher("/MainServlet");
			dispatcher.forward(request, response);
			return;
		}

		try {
			int scheduleId = Integer.parseInt(scheduleIdStr);
			ScheduleDAO dao = new ScheduleDAO();
			boolean success = dao.deleteSchedule(scheduleId, userId);

			// ２．削除成功/失敗に基づく画面遷移
			if (success) {
				// 削除成功: MainServletへフォワードし、最新の一覧を取得させる
				request.setAttribute("successMsg", "予定を削除しました。");
			} else {
				// 削除失敗
				request.setAttribute("errorMsg", "スケジュールの削除に失敗しました。権限がないか、情報がありません。");
			}

			// 成功/失敗にかかわらず、MainServletへフォワードして一覧を更新
			RequestDispatcher dispatcher = request.getRequestDispatcher("/MainServlet");
			dispatcher.forward(request, response);

		} catch (NumberFormatException e) {
			request.setAttribute("errorMsg", "無効なスケジュールIDが指定されました。");
			e.printStackTrace();
			RequestDispatcher dispatcher = request.getRequestDispatcher("/MainServlet");
			dispatcher.forward(request, response);

		}
	}
}
