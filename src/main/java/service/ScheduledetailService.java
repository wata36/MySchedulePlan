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
	public void registerScheduledetail(Scheduledetail scheduledetail) {
		dao.Scheduleinsert(scheduledetail);
	}
	
	//詳細を削除する
	public void deleteScheduledetail(int detailId) {
		dao.deleteScheduledetail(detailId);
	}
	
	//詳細を編集する
	public void updatePlan(Scheduledetail scheduledetail) {
		dao.Scheduleupdate(scheduledetail);
	}
}
