package service;

import java.util.List;

import dao.ScheduleDAO;
import model.Schedule;

/**
 * スケジュール関連のビジネスロジックを提供するサービスクラス。
 * <p>
 * このクラスは、ユーザーごとのスケジュール取得、スケジュール登録、
 * スケジュール削除などの処理を提供します。
 * DAOに直接アクセスすることで、DB操作を仲介します。
 * </p>
 */
public class ScheduleService {

    // ★ DAOのインスタンス生成
    private ScheduleDAO dao = new ScheduleDAO();

    /**
     * ユーザーIDから予定リストを取得する
     *
     * @param userId 取得対象のユーザーID
     * @return 指定ユーザーのスケジュールリスト
     */
    public List<Schedule> getSchedulesByUserId(int userId) {
        return dao.findSchedulesByUserId(userId);
    }

    /**
     * 予定を登録する
     *
     * @param schedule 登録するスケジュール情報
     */
    public void registerSchedule(Schedule schedule) {
        dao.Scheduleinsert(schedule);
    }

    /**
     * 予定を削除する
     * (型、変数名)
     *
     * @param scheduleId 削除対象のスケジュールID
     * @param userId スケジュール所有者のユーザーID
     */
    public void deleteSchedule(int scheduleId, int userId) {
        dao.deleteSchedule(scheduleId, userId);
    }
}
