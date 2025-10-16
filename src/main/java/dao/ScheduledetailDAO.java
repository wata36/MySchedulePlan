package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Scheduledetail;

public class ScheduledetailDAO {

	public List<Scheduledetail>findSchedulesByscheduleId(int scheduleId){
		 List<Scheduledetail> scheduleList = new ArrayList<>();

		 //			DBManagerからgetConnection()でSQL接続
			try (Connection conn = DBManager.getConnection()) {
		 // SELECT文を準備
		String sql = "SELECT scheduledetail_id,schedule_id,time,place,derail,map FROM scheduledetail WHERE schedule_id = ? ";
		PreparedStatement pStmt = conn.prepareStatement(sql);
		pStmt.setInt(1, schedule_Id);
     

	// SELECT文を実行し、結果表（ResultSet）を取得
	ResultSet rs = pStmt.executeQuery();
	// 結果表に格納されたレコードの内容を表示
	while (rs.next()) {
		int scheduledetailId = rs.getInt("scheduledetail_id");
		int id = rs.getInt("schedule_id");
		java.time.LocalDate date = rs.getDate("date").toLocalDate();
		String place = rs.getString("place");
		String detail = rs.getString("detail");
		String map = rs.getString("map");

		schedule = new Scheduledetail(scheduledetailId,id,time,place,detail,map);
		scheduleList.add(schedule);
	}	
    }catch(SQLException e){
	    e.printStackTrace();
	     return null;
  }
     return Scheduledetail;
  }
  
  public void Scheduleinsert(Scheduledetail scheduledetail) {
		// SELECT文を準備	
		try (Connection conn = DBManager.getConnection()) {
          String sql = "INSERT INTO schedule (schedule_id,time,place,detail,map) VALUES (?, ?, ?, ?, ?)";
          PreparedStatement pStmt = conn.prepareStatement(sql);
          pStmt.setInt(1, scheduledetail.getUser_id());
          pStmt.setTime(2, java.sql.Time.valueOf(scheduledetail.getTime()));
          pStmt.setPlace(3, scheduledetail.getPlace());
          pStmt.setdetail(4, scheduledetail.getdetail());
          pStmt.setDate(5, scheduledetail.getTitle());
          
          pStmt.executeUpdate(); // 実行

      } catch (SQLException e) {
          e.printStackTrace();
      }
	}
}
