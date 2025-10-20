package servlet;

import java.io.IOException;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import dao.ScheduleDAO;

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
	    
	    int userId = (int) session.getAttribute("userId"); 
	    // JSPに合わせて "schedule_id" を取得
	    String scheduleIdStr = request.getParameter("schedule_id"); 

	    if (scheduleIdStr == null) {
	        // パラメータがない場合は、応答を返さず処理を終了
	        return; 
	    }

	    try {
	        int scheduleId = Integer.parseInt(scheduleIdStr);
	        ScheduleDAO dao = new ScheduleDAO();

	        dao.deleteSchedule(scheduleId, userId);
	        
	    } catch (NumberFormatException e) {
	        // IDの形式エラーが発生した場合、ログに出力
	        e.printStackTrace();
	    } catch (SQLException e) {
	        // DBエラーが発生した場合、ログに出力
	        e.printStackTrace();
	    }
	    
	    // ★★★ 無反応を解消し、画面を更新するためのリダイレクト ★★★
	    // 成功・失敗にかかわらず、一覧画面に強制的に戻します
	    response.sendRedirect(request.getContextPath() + "/scheduleList.jsp");
	}
}