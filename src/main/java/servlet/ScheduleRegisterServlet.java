package servlet;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import model.Scheduledetail;
import model.User;
import service.ScheduledetailService;
import java.time.LocalTime;

/**
 * Servlet implementation class ScheduleRegisterServlet
 */
@WebServlet("/ScheduleRegisterServlet")
public class ScheduleRegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ScheduleRegisterServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/scheduledetail.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String action = request.getParameter("action");

		if ("regist".equals(action)) {
			//予定詳細登録
			//フォームから値を取得
			String timestr = request.getParameter("time");
			String place = request.getParameter("place");
			String ditail = request.getParameter("ditail");
			String map = request.getParameter("map");
			String scheduleIdStr = request.getParameter("schedule_id");
			int scheduleId = Integer.parseInt(scheduleIdStr);
			

			HttpSession session = request.getSession();
			User loginUser = (User) session.getAttribute("loginUser");
			if ( timestr != null && place != null && !timestr.isEmpty() && !place.isEmpty()) {
				try {
					LocalTime time = LocalTime.parse(timestr);

					Scheduledetail newDetail = new Scheduledetail();
					newDetail.setSchedule_id(scheduleId);
					newDetail.setTime(time);
					newDetail.setPlace(place);
					newDetail.setDitail(ditail);
					newDetail.setMap(map);
					
					ScheduledetailService detailService = new ScheduledetailService();
					detailService.registerScheduledetail(newDetail);

				} catch (DateTimeParseException e) {
					request.setAttribute("errorMsg", "必須項目が入力されていません");
				}
		
		// 一覧取得
		ScheduledetailService detailService = new ScheduledetailService();
		List<Scheduledetail> detailList = detailService.getScheduledetailsByUserId(loginUser.getUserId());
		request.setAttribute("scheduledetailList", detailList);

		// 詳細JSPへフォワード
		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/scheduledetail.jsp");
		dispatcher.forward(request, response);
		}
	}
	
	
