package service;

import java.util.List;

import dao.ScheduleDAO;
import model.Schedule;

public class ScheduleService {

	// ★ DAOのインスタンス生成
	private ScheduleDAO dao = new ScheduleDAO();

	// ユーザーIDから予定リストを取得する
	public List<Schedule> getSchedulesByUserId(int userId) {
		return dao.findSchedulesByUserId(userId);
	}

	// 予定を登録する
	public void registerSchedule(Schedule schedule) {
		dao.Scheduleinsert(schedule);
	}

    //予定を削除する   (型、変数名)
	public void deleteSchedule(int scheduleId, int userId) {
		dao.deleteSchedule(scheduleId, userId);
	}

}