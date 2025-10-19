		package servlet;
		
		import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import model.Scheduledetail;
import service.ScheduledetailService;
		
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
		
			protected void doPost(HttpServletRequest request, HttpServletResponse response)
					throws ServletException, IOException {
		
				String action = request.getParameter("action");
				
				String scheduleId = request.getParameter("schedule_id");
				System.out.println(scheduleId);
				if (scheduleId !=null && !scheduleId.isEmpty()) {
					request.setAttribute("schedulId", scheduleId);
				}
				if ("regist".equals(action)) {
					//予定詳細登録
					//フォームから値を取得
					String timestr = request.getParameter("time");
					String place = request.getParameter("place");
					String detail = request.getParameter("detail");
					String map = request.getParameter("map");
					System.out.println(timestr);
					System.out.println(place);
					System.out.println(detail);
					System.out.println(map);
		
		//			HttpSession session = request.getSession();
		//			Schedule schedule = (Schedule) session.getAttribute("schedule");
					
					if (scheduleId  == null) {
					    // scheduleId が無い場合はエラー処理
					    request.setAttribute("errorMsg", "スケジュール情報が見つかりません");
					    RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/scheduledetail.jsp");
					    dispatcher.forward(request, response);
					    return;  // ここで処理を止める
					}
		
					// scheduleId = schedule.getSchedule_id();
					if (timestr != null && place != null && !timestr.isEmpty() && !place.isEmpty()) {
						try {
							LocalTime time = LocalTime.parse(timestr);
		
							Scheduledetail newDetail = new Scheduledetail();
							newDetail.setSchedule_id( Integer.parseInt(scheduleId));
							newDetail.setTime(time);
							newDetail.setPlace(place);
							newDetail.setDetail(detail);
							newDetail.setMap(map);
		
							ScheduledetailService detailService = new ScheduledetailService();
							detailService.registerScheduledetail(newDetail);
		
						} catch (DateTimeParseException e) {
							request.setAttribute("errorMsg", "必須項目が入力されていません");
						}
					}
					
				}
				
				// 一覧取得
							ScheduledetailService detailService = new ScheduledetailService();
							List<Scheduledetail> detailList = detailService.getScheduledetailsByUserId(Integer.parseInt(scheduleId));//1対多
							request.setAttribute("scheduledetailList", detailList);
							
				// 詳細JSPへフォワード
				RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/scheduledetail.jsp");
				dispatcher.forward(request, response);
			}
		}
