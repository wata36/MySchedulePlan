package servlet;

import java.io.IOException;
import java.time.LocalTime;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import model.Scheduledetail;
import service.ScheduledetailService;

/**
 * {@code SchedulemodifyServlet} クラスは、
 * 既存のスケジュール詳細情報を編集・更新するためのサーブレットです。
 * <p>
 * フロントエンドのJavaScriptからフォーム送信されるデータを受け取り、
 * 対応するスケジュール詳細（時間・場所・詳細内容など）を更新します。
 * </p>
 * 
 * <p>
 * 主な機能：
 * <ul>
 *   <li>スケジュール詳細データの更新処理</li>
 *   <li>入力値検証（必須項目・IDチェック）</li>
 *   <li>更新完了後、スケジュール詳細画面へのリダイレクト</li>
 * </ul>
 * </p>
 * 
 * <p>URLパターン: {@code /SchedulemodifyServlet}</p>
 * 
 * @author 
 * @version 1.0
 */
@WebServlet("/SchedulemodifyServlet")
public class SchedulemodifyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * デフォルトコンストラクタ。
	 * <p>サーブレットの初期化処理を行います。</p>
	 */
	public SchedulemodifyServlet() {
		super();
	}

	/**
	 * GETリクエストの処理。
	 * <p>
	 * このサーブレットでは通常、更新はPOSTメソッドで行われるため、
	 * GET時の処理は特に実装されていません。
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
		// 特に処理は行わない（必要に応じて今後拡張可能）
	}

	/**
	 * POSTリクエストの処理。
	 * <p>
	 * フォーム送信データを受け取り、既存のスケジュール詳細を更新します。<br>
	 * 主な流れ：
	 * <ol>
	 *   <li>リクエストパラメータの取得と入力チェック</li>
	 *   <li>更新対象の {@link Scheduledetail} オブジェクト作成</li>
	 *   <li>{@link ScheduledetailService#updatePlan(Scheduledetail)} によりDB更新</li>
	 *   <li>更新成功時：スケジュール詳細一覧画面にリダイレクト</li>
	 *   <li>失敗時：エラーメッセージを設定して詳細画面にフォワード</li>
	 * </ol>
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

		// 文字コード設定（フォーム入力値の文字化け防止）
		request.setCharacterEncoding("UTF-8");

		// フォームからの入力値を取得
		String scheduleIdStr = request.getParameter("schedule_id");
		String detailIdStr = request.getParameter("detail_id");
		String timestr = request.getParameter("time");
		String place = request.getParameter("place");
		String detail = request.getParameter("detail");
		String map = request.getParameter("map");
		String action = request.getParameter("action");

		System.out.println("SchedulemodifyServlet scheduleIdStr: " + scheduleIdStr);

		/**
		 * 更新リクエストの妥当性チェック
		 * actionが"update"であること、detail_idが指定されていることを確認
		 */
		if (!"update".equals(action) || detailIdStr == null || detailIdStr.isEmpty()) {

			// エラーメッセージ設定
			request.setAttribute("errorMsg", "無効な更新リクエストです。更新に必要な情報が不足しています。");

			// スケジュールIDが存在する場合はスケジュール一覧へリダイレクト
			if (scheduleIdStr != null && !scheduleIdStr.isEmpty()) {
				response.sendRedirect("ScheduleRegisterServlet");
			} else {
				// IDがない場合はエラーページへフォワード
				RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/scheduledetail.jsp");
				dispatcher.forward(request, response);
			}
			return;
		}

		/**
		 * 必須入力項目（時間・場所）のチェック
		 */
		if (timestr == null || timestr.isEmpty() || place == null || place.isEmpty()) {
			request.setAttribute("errorMsg", "必須項目（時間・場所）が入力されていません");
			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/scheduledetail.jsp");
			dispatcher.forward(request, response);
			return;
		}

		/**
		 * 更新対象データを格納するモデルを作成
		 */
		Scheduledetail updateDetail = new Scheduledetail();

		try {
			// 型変換と値設定
			int scheduleId = Integer.parseInt(scheduleIdStr);
			int detailId = Integer.parseInt(detailIdStr);
			LocalTime localTime = LocalTime.parse(timestr);

			updateDetail.setSchedule_id(scheduleId);
			updateDetail.setDetail_id(detailId);
			updateDetail.setTime(localTime);
			updateDetail.setPlace(place);
			updateDetail.setDetail(detail);
			updateDetail.setMap(map);

			// DB更新処理を呼び出し
			ScheduledetailService detailService = new ScheduledetailService();
			detailService.updatePlan(updateDetail);

			// 更新成功 → スケジュール詳細画面にリダイレクト（PRGパターン）
			response.sendRedirect("ScheduleRegisterServlet");

		} catch (Exception e) {
			// エラーログ出力
			e.printStackTrace();

			// エラーメッセージ設定
			request.setAttribute("errorMsg", "更新中にエラーが発生しました");

			// エラー時はフォワードして詳細画面に戻す
			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/scheduledetail.jsp");
			dispatcher.forward(request, response);
		}
	}
}
