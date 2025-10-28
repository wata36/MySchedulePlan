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
 * Servlet implementation class SchedulemodifyServlet
 */
@WebServlet("/SchedulemodifyServlet")
public class SchedulemodifyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SchedulemodifyServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	//編集ボタンが押されたときにJS使って画面のフォームにデーターを設定しフォームを送信した時にサーブレットで
	//新規登録ではなく更新の処理を行う流れ
	//JS：編集ボタンを押下時その項目データを取得画面上のinput  
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// リクエストパラメーターの取得
		request.setCharacterEncoding("UTF-8");

		String scheduleIdStr = request.getParameter("schedule_id");
		String detailIdStr = request.getParameter("detail_id");
		String timestr = request.getParameter("time");
		String place = request.getParameter("place");
		String detail = request.getParameter("detail");
		String map = request.getParameter("map");

		String action = request.getParameter("action");
		
		System.out.println("SchedulemodifyServlet scheduleIdStr" + scheduleIdStr);

		//更新チェック リクエストが一つでも以下の条件を満たしてない場合
		// 更新に必要な情報がない場合（action="regist" や detail_id が空など）
		if (!"update".equals(action) || detailIdStr == null || detailIdStr.isEmpty()) { 
		    
		    // エラーメッセージを設定
		    request.setAttribute("errorMsg", "無効な更新リクエストです。更新に必要な情報が不足しています。");

		    // スケジュールIDがあれば、詳細表示サーブレットへリダイレクト（データの再読み込み）
		    if (scheduleIdStr != null && !scheduleIdStr.isEmpty()) {
		        // リダイレクトのみ実行し、フォワードはしない
		        //response.sendRedirect("ScheduleDetailServlet?schedule_id=" + scheduleIdStr);
		    	response.sendRedirect("ScheduleRegisterServlet" );
		    } else {
		        // scheduleIdもない場合は、フォワードして処理を終了
		        RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/scheduledetail.jsp");
		        dispatcher.forward(request, response);
		    }
		    
		    return; // 必ず処理を終了
		}

		// 必要入力項目が空ではないか
		if (timestr == null || timestr.isEmpty() || place == null || place.isEmpty()) {
			request.setAttribute("errorMsg", "必須項目（時間・場所）が入力されていません");

			// エラー時はフォワードで詳細画面に戻し、JSP側でエラーメッセージを表示させる
			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/scheduledetail.jsp");
			dispatcher.forward(request, response);
			return; // 処理をここで終了
		}

		// Modelのオブジェクトを生成
		//フォームから受け取ったデーターを保持するため
		Scheduledetail updateDetail = new Scheduledetail();

		//新規登録ではなく更新を行う
		try {
			// 型変換
			int scheduleId = Integer.parseInt(scheduleIdStr);
			int detailId = Integer.parseInt(detailIdStr);
			LocalTime localTime = LocalTime.parse(timestr);

			// 更新に必要なIDとデータをセット
			updateDetail.setSchedule_id(scheduleId);
			updateDetail.setDetail_id(detailId);
			updateDetail.setTime(localTime);
			updateDetail.setPlace(place);
			updateDetail.setDetail(detail);
			updateDetail.setMap(map);

			// Serviceに処理を委譲 (インスタンスとオブジェクトを正しく使う)SQLへ繋ぐ
			ScheduledetailService detailService = new ScheduledetailService();
			detailService.updatePlan(updateDetail);

			// 【成功時】スケジュール詳細画面にリダイレクト
			//response.sendRedirect("ScheduleDetailServlet?schedule_id=" + scheduleIdStr);
			response.sendRedirect("ScheduleRegisterServlet" );
		} catch (Exception e) {
			// エラーログ出力（最低限のデバッグ情報）
			e.printStackTrace();

			// 処理エラー時のメッセージ設定
			request.setAttribute("errorMsg", "更新中にエラーが発生しました");

			// エラー時のフォワード処理
			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/scheduledetail.jsp");
			dispatcher.forward(request, response);
		}

	}
}