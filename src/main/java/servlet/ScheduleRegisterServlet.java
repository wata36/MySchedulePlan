package servlet;
		
		import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import model.Schedule;
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
				// 一覧を取得する セッションからどのスケジュールを使うか取得
				HttpSession session = request.getSession();
				String scheduleId = (String)session.getAttribute("scheduleId");
				System.out.println(" scheduleId"+ scheduleId);
				// 予定の中の詳細を登録する処理
				ScheduledetailService detailService = new ScheduledetailService();
				List<Scheduledetail> detailList = detailService.getScheduledetailsByUserId(Integer.parseInt(scheduleId));//1対多
				request.setAttribute("scheduledetailList", detailList);				
				RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/scheduledetail.jsp");
				dispatcher.forward(request, response);
		
			}
		
			protected void doPost(HttpServletRequest request, HttpServletResponse response)
					throws ServletException, IOException {
		
				String action = request.getParameter("action");
			
				//フォームから送られてきたscheduleを取得する 日付、タイトル
				String scheduleId = request.getParameter("schedule_id");
				String title = request.getParameter("title");
				String dateStr = request.getParameter("date");
				//取得確認
				System.out.println(title);
				System.out.println(dateStr);
				System.out.println(scheduleId);
				HttpSession session = request.getSession();
				
				//スケジュールタイトル取得(詳細登録の時はスキップ)
				if(!"regist".equals(action)) {
					if (scheduleId !=null && !scheduleId.isEmpty()) {
						request.setAttribute("schedulId", scheduleId);	
						Schedule schedule = new Schedule();
						schedule.setTitle(title);
						LocalDate date = LocalDate.parse(dateStr);
						schedule.setDate(date);
						//JSPにスケジュールIDを渡す
						session.setAttribute("schedule", schedule);
					}
				}
				
				//registの時に詳細登録 
				if ("regist".equals(action)) {
					//フォームから値を取得
					String timestr = request.getParameter("time");
					String place = request.getParameter("place");
					String detail = request.getParameter("detail");
					String map = request.getParameter("map");
					//取得確認
					System.out.println(timestr);
					System.out.println(place);
					System.out.println(detail);
					System.out.println(map);
		
					String scheduleIdStr = request.getParameter("schedule_id");
				    // ... (他の String 変数の取得は省略) ...
					int scheduleIdInt = -1; // int型のIDを定義
				    
					//scheduleidが正しいかチェック
					// IDがない/空の場合、ここでエラー表示して処理を終了
				    if (scheduleIdStr == null || scheduleIdStr.isEmpty()) {
				        request.setAttribute("errorMsg", "スケジュールIDが不明です。");
				        RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/scheduledetail.jsp");
				        dispatcher.forward(request, response);
				        return; 
				    }
				    
				    // IDをintに変換。数値でない場合はエラー処理して終了
				    try {
				        scheduleIdInt = Integer.parseInt(scheduleIdStr);
				    } catch (NumberFormatException e) {
				        request.setAttribute("errorMsg", "スケジュールIDの形式が不正です。");
				        RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/scheduledetail.jsp");
				        dispatcher.forward(request, response);
				        return; 
				    }
				    
					if (timestr != null && place != null && !timestr.isEmpty() && !place.isEmpty()) {
						try {
							LocalTime time = LocalTime.parse(timestr);
		
							Scheduledetail newDetail = new Scheduledetail();
							newDetail.setSchedule_id(scheduleIdInt);
//							newDetail.setSchedule_id( Integer.parseInt(scheduleId));
							newDetail.setTime(time);
							newDetail.setPlace(place);
							newDetail.setDetail(detail);
							newDetail.setMap(map);
		
							//ScheduledetailService 経由で DB に登録
							ScheduledetailService detailService = new ScheduledetailService();
							detailService.registerScheduledetail(newDetail);
		
						} catch (DateTimeParseException e) {
							request.setAttribute("errorMsg", "必須項目が入力されていません");
						}
					}
					
				}
				
				
				//一覧を再取得して表示
				//スケジュールIDをセッションに保持
				session.setAttribute("scheduleId",scheduleId);
				
				ScheduledetailService detailService = new ScheduledetailService();
				
				List<Scheduledetail> detailList = detailService.getScheduledetailsByUserId(Integer.parseInt(scheduleId));//1対多
				request.setAttribute("scheduledetailList", detailList);
							
				// 詳細JSPへフォワード
				RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/scheduledetail.jsp");
				dispatcher.forward(request, response);
			}
		}