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
 * {@code ScheduleRegisterServlet} クラスは、スケジュールおよびスケジュール詳細を登録・表示するためのサーブレットです。
 * <p>
 * 主な機能：
 * <ul>
 *   <li>スケジュール詳細の一覧表示（GETリクエスト）</li>
 *   <li>スケジュールまたは詳細の登録（POSTリクエスト）</li>
 * </ul>
 * 
 * <p>URLパターン: {@code /ScheduleRegisterServlet}</p>
 * 
 * <p>PRG（Post-Redirect-Get）パターンを採用し、登録後の再読み込みによる重複登録を防止しています。</p>
 * 
 * @author 
 * @version 1.0
 */
@WebServlet("/ScheduleRegisterServlet")
public class ScheduleRegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * デフォルトコンストラクタ。
	 * Servletの初期化処理を行います。
	 */
	public ScheduleRegisterServlet() {
		super();
	}

	/**
	 * GETリクエストを処理します。
	 * <p>
	 * セッションからスケジュールIDを取得し、該当するスケジュール詳細の一覧を取得して
	 * {@code scheduledetail.jsp} にフォワードします。
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

		// セッションからスケジュールIDを取得
		HttpSession session = request.getSession();
		String scheduleId = (String) session.getAttribute("scheduleId");
		System.out.println("scheduleId: " + scheduleId);

		// スケジュールIDに紐づく詳細を取得
		ScheduledetailService detailService = new ScheduledetailService();
		List<Scheduledetail> detailList =
				detailService.getScheduledetailsByUserId(Integer.parseInt(scheduleId)); // 1対多

		// JSP に一覧データを渡す
		request.setAttribute("scheduledetailList", detailList);

		// JSP へフォワード
		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/scheduledetail.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * POSTリクエストを処理します。
	 * <p>
	 * 以下の2種類の処理を行います：
	 * <ul>
	 *   <li>スケジュールタイトル登録（action ≠ "regist"）</li>
	 *   <li>スケジュール詳細登録（action = "regist"）</li>
	 * </ul>
	 * <p>
	 * 登録処理完了後は {@code response.sendRedirect()} を使用してリダイレクトし、
	 * リロードによる重複登録を防ぎます。
	 * </p>
	 *
	 * @param request  クライアントからのリクエスト
	 * @param response サーバーからのレスポンス
	 * @throws ServletException サーブレットエラーが発生した場合
	 * @throws IOException      入出力エラーが発生した場合
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// パラメータ取得
		String action = request.getParameter("action");
		String scheduleId = request.getParameter("schedule_id");
		String title = request.getParameter("title");
		String dateStr = request.getParameter("date");

		// デバッグ用出力
		System.out.println("title: " + title);
		System.out.println("date: " + dateStr);
		System.out.println("scheduleId: " + scheduleId);

		HttpSession session = request.getSession();

		/**
		 * スケジュール情報登録（詳細登録以外のとき）
		 */
		if (!"regist".equals(action)) {
			if (scheduleId != null && !scheduleId.isEmpty()) {
				request.setAttribute("scheduleId", scheduleId);
				Schedule schedule = new Schedule();
				schedule.setTitle(title);
				LocalDate date = LocalDate.parse(dateStr);
				schedule.setDate(date);

				// セッションにスケジュール情報を保持
				session.setAttribute("schedule", schedule);
			}
		}

		/**
		 * 詳細登録処理（action = "regist" のとき）
		 */
		if ("regist".equals(action)) {

			// フォームから値を取得
			String timestr = request.getParameter("time");
			String place = request.getParameter("place");
			String detail = request.getParameter("detail");
			String map = request.getParameter("map");

			// デバッグ出力
			System.out.println("time: " + timestr);
			System.out.println("place: " + place);
			System.out.println("detail: " + detail);
			System.out.println("map: " + map);

			String scheduleIdStr = request.getParameter("schedule_id");
			int scheduleIdInt = -1;

			// スケジュールIDチェック
			if (scheduleIdStr == null || scheduleIdStr.isEmpty()) {
				request.setAttribute("errorMsg", "スケジュールIDが不明です。");
				RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/scheduledetail.jsp");
				dispatcher.forward(request, response);
				return;
			}

			try {
				scheduleIdInt = Integer.parseInt(scheduleIdStr);
			} catch (NumberFormatException e) {
				request.setAttribute("errorMsg", "スケジュールIDの形式が不正です。");
				RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/scheduledetail.jsp");
				dispatcher.forward(request, response);
				return;
			}

			// 必須項目が入力されている場合のみ登録処理を実行
			if (timestr != null && place != null && !timestr.isEmpty() && !place.isEmpty()) {
				try {
					LocalTime time = LocalTime.parse(timestr);

					Scheduledetail newDetail = new Scheduledetail();
					newDetail.setSchedule_id(scheduleIdInt);
					newDetail.setTime(time);
					newDetail.setPlace(place);
					newDetail.setDetail(detail);
					newDetail.setMap(map);

					// DB登録処理
					ScheduledetailService detailService = new ScheduledetailService();
					detailService.registerScheduledetail(newDetail);

				} catch (DateTimeParseException e) {
					request.setAttribute("errorMsg", "時間の形式が不正です。");
				}
			}
		}

		/**
		 * 登録後の処理：PRGパターンでリダイレクト
		 */
		session.setAttribute("scheduleId", scheduleId);
		response.sendRedirect("ScheduleRegisterServlet");
	}
}
