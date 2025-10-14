package service;

import java.util.List;

import dao.ScheduleDAO;
import model.Schedule;

public class ScheduleService {
	public List<Schedule> getSchedulesByUserId(int userId) {
		//DAOのインスタンス生成
	    ScheduleDAO dao = new ScheduleDAO();
	    //userIDの予定をDBから取得し結果リストを返す
	    return ScheduleDAO.findSchedulesByUserId(userId);
	}

}
