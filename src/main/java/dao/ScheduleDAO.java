package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Schedule;

/**
 * スケジュール(schedule)テーブルに対するデータベース操作を行うDAOクラス
 * <p>
 * このクラスは、スケジュールの取得、登録、削除を扱います。
 * </p>
 */
public class ScheduleDAO {

    /**
     * 指定されたユーザーIDに紐づくスケジュールリストを取得する
     * 
     * @param userId 取得対象のユーザーID
     * @return Scheduleオブジェクトのリスト、取得失敗時はnull
     */
    public List<Schedule> findSchedulesByUserId(int userId) {
        Schedule schedule = null;
        List<Schedule> scheduleList = new ArrayList<>();
        // DBManagerからgetConnection()でSQL接続
        try (Connection conn = DBManager.getConnection()) {

            // SELECT文を準備
            String sql = "SELECT schedule_id, user_id, title, date FROM schedule WHERE user_id = ? ORDER BY date DESC";
            PreparedStatement pStmt = conn.prepareStatement(sql);
            pStmt.setInt(1, userId);

            // SELECT文を実行し、結果表（ResultSet）を取得
            ResultSet rs = pStmt.executeQuery();
            // 結果表に格納されたレコードの内容を取得
            while (rs.next()) {
                int scheduleId = rs.getInt("schedule_id");
                int id = rs.getInt("user_id");
                java.time.LocalDate date = rs.getDate("date").toLocalDate();
                String title = rs.getString("title");

                schedule = new Schedule(scheduleId, id, title, date);
                scheduleList.add(schedule);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return scheduleList;
    }

    /**
     * スケジュールを登録する
     * 
     * @param schedule 登録するScheduleオブジェクト
     */
    public void Scheduleinsert(Schedule schedule) {
        // SQL文を準備
        try (Connection conn = DBManager.getConnection()) {
            String sql = "INSERT INTO schedule (user_id, title, date) VALUES (?, ?, ?)";
            PreparedStatement pStmt = conn.prepareStatement(sql);
            pStmt.setInt(1, schedule.getUser_id());
            pStmt.setString(2, schedule.getTitle());
            pStmt.setDate(3, java.sql.Date.valueOf(schedule.getDate())); // LocalDate → SQL Date

            pStmt.executeUpdate(); // 実行

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * スケジュールと紐づく詳細を削除する
     * 
     * @param scheduleId 削除対象のスケジュールID
     * @param userId     操作を行うユーザーID（認可チェック用）
     * @return 削除成功ならtrue、失敗ならfalse
     */
    public boolean deleteSchedule(int scheduleId, int userId) {
        String sqlDeleteDetail = "DELETE FROM schedule_detail WHERE schedule_id = ?";
        String sqlDeleteSchedule = "DELETE FROM schedule WHERE schedule_id = ? AND user_id = ?";
        int scheduleDeleteCount = 0; // スケジュール本体の削除件数を記録する変数

        try (Connection conn = DBManager.getConnection();
             PreparedStatement pStmtDetail = conn.prepareStatement(sqlDeleteDetail);
             PreparedStatement pStmtSchedule = conn.prepareStatement(sqlDeleteSchedule)) {

            // スケジュール詳細を削除 (失敗しても続行)
            pStmtDetail.setInt(1, scheduleId);
            pStmtDetail.executeUpdate();

            // スケジュール本体を削除 (ユーザーIDで認可チェック)
            pStmtSchedule.setInt(1, scheduleId);
            pStmtSchedule.setInt(2, userId);
            scheduleDeleteCount = pStmtSchedule.executeUpdate();

            // スケジュール本体が1件削除できた場合に成功とする
            return scheduleDeleteCount == 1;

        } catch (SQLException e) {
            // SQLエラーが発生したらログに出力して、false（失敗）を返す
            e.printStackTrace();
            return false;
        }
    }
}
