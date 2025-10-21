package servlet;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import dao.ScheduledetailDAO;
import model.User;
import service.ScheduleService;

@WebServlet("/ScheduleDleateServlet")
public class ScheduleDleateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ScheduleDleateServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/scheduledetail.jsp");
	    dispatcher.forward(request, response);
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
		String detailIdStr = request.getParameter("detail_id");

		//スケジュールの詳細の場合
		if (scheduleIdStr == null || scheduleIdStr.isEmpty() 
			&& (detailIdStr != null && detailIdStr.isEmpty())) {
			
			//detailを削除する処理
			//画面を遷移する処理
			
			try {
				int detailId = Integer.parseInt(detailIdStr);
				ScheduledetailDAO dao = new ScheduledetailDAO();
				boolean result = dao.deleteSchedule(detailId);

				// 成功/失敗にかかわらず、MainServletへフォワードして一覧を更新
				//予定の詳細の一覧を取得
				
				//予定の将来の一覧の画面に遷移
				//ScheduleRegisterServletリダイレクト
				//ScheduleRegisterServletのdoGetにdetailを取得してリクエストスコープに保存
				
				
				response.sendRedirect("ScheduleRegisterServlet");
				return;


			} catch (NumberFormatException e) {
				request.setAttribute("errorMsg", "無効なスケジュールIDが指定されました。");
				e.printStackTrace();
				RequestDispatcher dispatcher = request.getRequestDispatcher("/ScheduleRegisterServlet");
				dispatcher.forward(request, response);

			}
		}
	
		//スケジュールの場合
		if (scheduleIdStr == null || scheduleIdStr.isEmpty()) {
			request.setAttribute("errorMsg", "削除対象のスケジュール情報が見つかりません。");
			// エラー時は必ずMainServletにフォワード
			RequestDispatcher dispatcher = request.getRequestDispatcher("/MainServlet");
			dispatcher.forward(request, response);
			return;
		}

		try {
			int scheduleId = Integer.parseInt(scheduleIdStr);
			//ScheduleServiceインスタンス生成
			ScheduleService scheduleService = new ScheduleService();
			//メソッド型合わせる
			scheduleService.deleteSchedule(scheduleId, userId);
			
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
