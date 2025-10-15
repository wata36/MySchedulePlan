package service;

import java.util.List;

import dao.ScheduledetailDAO;
import model.Scheduledetail;

public class ScheduledetailService {
	
	private ScheduledetailDAO dao = new ScheduledetailDAO();
	
	//詳細リストを取得する
	public List<Scheduledetail> getScheduledetailsByUserId(int scheduleId){
		return dao.findSchedulesByscheduleId(scheduleId);
	}
	
	//詳細リストを登録する
	public void registerScheduledetail(DetailSchedule detailSchedule) {
		dao.Scheduleinsert(detailSchedule);
	}
}
