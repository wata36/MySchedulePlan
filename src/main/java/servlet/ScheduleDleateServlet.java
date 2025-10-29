package servlet;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import model.User;
import service.ScheduleService;
import service.ScheduledetailService;

/**
 * {@code ScheduleDleateServlet} クラスは、
 * スケジュールまたはスケジュール詳細（詳細項目）を削除するためのサーブレットです。
 * <p>
 * 以下の2つの削除処理を行います：
 * <ul>
 *   <li>スケジュール全体の削除（ScheduleService使用）</li>
 *   <li>スケジュール詳細（1件）の削除（ScheduledetailService使用）</li>
 * </ul>
 * 
 * <p>
 * ログイン中のユーザーのみが削除操作を行えるようにセッションチェックを行い、  
 * 削除後は一覧画面（MainServlet または ScheduleRegisterServlet）に戻ります。
 * </p>
 *
 * <p>URLパターン: {@code /ScheduleDleateServlet}</p>
 * 
 * @author 
 * @version 1.0
 */
@WebServlet("/ScheduleDleateServlet")
public class ScheduleDleateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * デフォルトコンストラクタ。
	 * サーブレットの初期化処理を行います。
	 */
	public ScheduleDleateServlet() {
		super();
	}

	/**
	 * GETリクエストを処理します。
	 * <p>
	 * 通常は削除処理はPOSTメソッドで行われるため、このメソッドでは
	 * {@code scheduledetail.jsp} へ単純にフォワードしています。
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
		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/scheduledetail.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * POSTリクエストを処理します。
	 * <p>
	 * ユーザーのセッションを確認し、フォームから送信されたIDをもとに
	 * スケジュールまたは詳細を削除します。  
	 * 削除対象は以下のように判断します：
	 * </p>
	 * 
	 * <ul>
	 *   <li>{@code detail_id} が存在 → スケジュール詳細を削除</li>
	 *   <li>{@code schedule_id} のみ存在 → スケジュール全体を削除</li>
	 * </ul>
	 * 
	 * <p>削除完了後は一覧画面（MainServlet または ScheduleRegisterServlet）へフォワード／リダイレクトします。</p>
	 *
	 * @param request  クライアントからのリクエスト
	 * @param response サーバーからのレスポンス
	 * @throws ServletException サーブレットエラーが発生した場合
	 * @throws IOException      入出力エラーが発生した場合
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(false);

		// -------------------------------
		// ログインチェック
		// -------------------------------
		if (session == null || session.getAttribute("loginUser") == null) {
			response.sendRedirect(request.getContextPath() + "/login.jsp");
			return;
		}

		User loginUser = (User) session.getAttribute("loginUser");
		int userId = loginUser.getUserId();

		// -------------------------------
		// パラメータ取得
		// -------------------------------
		String scheduleIdStr = request.getParameter("schedule_id");
		String detailIdStr = request.getParameter("detail_id");

		System.out.println("detailIdStr: " + detailIdStr);

		// -------------------------------
		// スケジュール詳細の削除処理
		// -------------------------------
		if ((scheduleIdStr == null || scheduleIdStr.isEmpty())
				&& (detailIdStr != null && !detailIdStr.isEmpty())) {

			try {
				int detailId = Integer.parseInt(detailIdStr);

				// ScheduledetailService を利用して詳細を削除
				ScheduledetailService scheduledetailService = new ScheduledetailService();
				scheduledetailService.deleteScheduledetail(detailId);

				// 削除後は詳細画面にリダイレクト
				response.sendRedirect("ScheduleRegisterServlet");
				return;

			} catch (NumberFormatException e) {
				request.setAttribute("errorMsg", "無効な詳細IDが指定されました。");
				e.printStackTrace();
				RequestDispatcher dispatcher = request.getRequestDispatcher("/ScheduleRegisterServlet");
				dispatcher.forward(request, response);
				return;
			}
		}

		// -------------------------------
		// スケジュール全体の削除処理
		// -------------------------------
		if (scheduleIdStr == null || scheduleIdStr.isEmpty()) {
			request.setAttribute("errorMsg", "削除対象のスケジュール情報が見つかりません。");
			RequestDispatcher dispatcher = request.getRequestDispatcher("/MainServlet");
			dispatcher.forward(request, response);
			return;
		}

		try {
			int scheduleId = Integer.parseInt(scheduleIdStr);

			// ScheduleService を利用してスケジュール全体を削除
			ScheduleService scheduleService = new ScheduleService();
			scheduleService.deleteSchedule(scheduleId, userId);

			// 削除完了後、スケジュール一覧を更新（MainServletへフォワード）
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
