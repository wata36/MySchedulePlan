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
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/scheduledetail.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// リクエストパラメーターの取得
		request.setCharacterEncoding("UTF-8");
		
		String detailIdStr = request.getParameter("detail_id"); 
		String timestr = request.getParameter("time");
		String place = request.getParameter("place");
		String detail = request.getParameter("detail");
		String map = request.getParameter("map");
		//取得確認
		System.out.println(timestr);
		System.out.println(place);
		System.out.println(detail);
		System.out.println(map);

		// 2. IDチェック
	    if (detailIdStr == null || detailIdStr.isEmpty()) { 
	        request.setAttribute("errorMsg", "必須項目が入力されていません");
	        return;
	    }
	    
	    try {
	        // 型変換
	        int detailId = Integer.parseInt(detailIdStr);
	        LocalTime localTime = LocalTime.parse(timestr); 

	        // Modelのオブジェクトを生成
	        Scheduledetail updateDetail = new Scheduledetail();
	        
	        // Setterを使って必要なデータのみを設定
	        updateDetail.setDetail_id(detailId); 
	        updateDetail.setTime(localTime); 
	        updateDetail.setPlace(place);
	        updateDetail.setDetail(detail);
	        updateDetail.setMap(map);

	        // Service層に処理を委譲 (インスタンスとオブジェクトを正しく使う)
	        ScheduledetailService detailService = new ScheduledetailService();
	        detailService.updatePlan(updateDetail); 
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	        request.setAttribute("errorMsg", "データの更新中にエラーが発生しました");
	        return;
	    }

	    // 完了後リダイレクト
	    response.sendRedirect("WEB-INF/jsp/scheduledetail.jsp");
	}
	
	}