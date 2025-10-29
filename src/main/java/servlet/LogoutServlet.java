package servlet;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * {@code LogoutServlet} は、ログアウト処理を担当するサーブレットです。
 * <p>
 * セッションを無効化し、ログイン画面へリダイレクトします。
 * </p>
 *
 * <p>
 * 主な処理の流れ：
 * <ol>
 *   <li>リクエストパラメータ {@code action=logout} を確認</li>
 *   <li>セッションを破棄（invalidate）</li>
 *   <li>ログイン画面へリダイレクト</li>
 * </ol>
 * </p>
 *
 * <p>
 * URLパターン：{@code /LogoutServlet}
 * </p>
 */
@WebServlet("/LogoutServlet")
public class LogoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * デフォルトコンストラクタ。
	 */
	public LogoutServlet() {
		super();
	}

	/**
	 * GETリクエストを受け取った場合の処理。
	 * <p>
	 * 通常、ログアウトは POST メソッドで行われるため、<br>
	 * GETアクセス時はログイン画面へリダイレクトします。
	 * </p>
	 *
	 * @param request  クライアントからのリクエスト
	 * @param response サーバーからのレスポンス
	 * @throws ServletException サーブレットエラー発生時
	 * @throws IOException      入出力エラー発生時
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// GETで来た場合も安全にログアウト処理を行う
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.invalidate();
		}

		// ログイン画面へリダイレクト
		response.sendRedirect("login.jsp");
	}

	/**
	 * POSTリクエストによるログアウト処理。
	 * <p>
	 * action パラメータが {@code logout} の場合のみ、セッションを破棄し、<br>
	 * ログイン画面（{@code login.jsp}）へリダイレクトします。
	 * </p>
	 *
	 * @param request  クライアントからのリクエスト
	 * @param response サーバーからのレスポンス
	 * @throws ServletException サーブレットエラー発生時
	 * @throws IOException      入出力エラー発生時
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String action = request.getParameter("action");

		// action=logout の場合のみ処理
		if ("logout".equals(action)) {
			// セッションを取得し、存在すれば破棄
			HttpSession session = request.getSession(false);
			if (session != null) {
				session.invalidate();
			}

			// ログイン画面へリダイレクト
			response.sendRedirect("login.jsp");
			return;
		}

		// メイン画面へ戻す
		response.sendRedirect("MainServlet");
	}
}
