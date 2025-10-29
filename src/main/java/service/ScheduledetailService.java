package service;

import java.util.List;

import dao.ScheduledetailDAO;
import model.Scheduledetail;

/**
 * スケジュール詳細に関するビジネスロジックを提供するサービスクラス。
 * <p>
 * このクラスは、スケジュール詳細の取得、登録、削除、編集などの処理を提供します。
 * DAOを通じてDB操作を行います。
 * </p>
 */
public class ScheduledetailService {

    private ScheduledetailDAO dao = new ScheduledetailDAO();

    /**
     * 詳細リストを取得する
     *
     * @param scheduleId 取得対象のスケジュールID
     * @return 指定スケジュールに紐づく詳細リスト
     */
    public List<Scheduledetail> getScheduledetailsByUserId(int scheduleId) {
        return dao.findSchedulesByscheduleId(scheduleId);
    }

    /**
     * 詳細リストを登録する
     *
     * @param scheduledetail 登録する詳細情報
     */
    public void registerScheduledetail(Scheduledetail scheduledetail) {
        dao.Scheduleinsert(scheduledetail);
    }

    /**
     * 詳細を削除する
     *
     * @param detailId 削除対象の詳細ID
     */
    public void deleteScheduledetail(int detailId) {
        dao.deleteScheduledetail(detailId);
    }

    /**
     * 詳細を編集する
     *
     * @param scheduledetail 更新する詳細情報
     */
    public void updatePlan(Scheduledetail scheduledetail) {
        dao.Scheduleupdate(scheduledetail);
    }
}
