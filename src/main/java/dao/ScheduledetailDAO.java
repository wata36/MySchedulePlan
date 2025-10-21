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
		 Scheduledetail scheduledetail = null; 
		List<Scheduledetail> scheduledetailList = new ArrayList<>();

		 //			DBManagerからgetConnection()でSQL接続
			try (Connection conn = DBManager.getConnection()) {
		 // SELECT文を準備
		String sql = "SELECT detail_id,schedule_id,time,place,detail,map FROM schedule_detail WHERE schedule_id = ?  ORDER BY time";
		PreparedStatement pStmt = conn.prepareStatement(sql);
		pStmt.setInt(1, scheduleId);
     
	// SELECT文を実行し、結果表（ResultSet）を取得
	ResultSet rs = pStmt.executeQuery();
	// 結果表に格納されたレコードの内容を表示
	while (rs.next()) {
		int scheduledetailId = rs.getInt("detail_id");
		int id = rs.getInt("schedule_id");
		java.time.LocalTime time = rs.getTime("time").toLocalTime();
		String place = rs.getString("place");
		String detail = rs.getString("detail");
		String map = rs.getString("map");

		scheduledetail = new Scheduledetail(scheduledetailId, id, time, place, detail, map);
        scheduledetailList.add(scheduledetail);
	}	
    }catch(SQLException e){
	    e.printStackTrace();
	     return null;
  }
     return scheduledetailList;
  }
  
  public void Scheduleinsert(Scheduledetail scheduledetail) {
		// SELECT文を準備	
		try (Connection conn = DBManager.getConnection()) {
          String sql = "INSERT INTO schedule_detail (schedule_id,time,place,detail,map) VALUES (?, ?, ?, ?, ?)";
          PreparedStatement pStmt = conn.prepareStatement(sql);
          
          pStmt.setInt(1, scheduledetail.getSchedule_id());
          pStmt.setTime(2, java.sql.Time.valueOf(scheduledetail.getTime()));  // LocalTime → java.sql.Time
          pStmt.setString(3, scheduledetail.getPlace());
          pStmt.setString(4, scheduledetail.getDetail());
          pStmt.setString(5, scheduledetail.getMap());
          
          pStmt.executeUpdate(); // 実行

      } catch (SQLException e) {
          e.printStackTrace();
      }
	}
  
  public boolean deleteScheduledetail(int detailId) {
	    String sqlDeleteDetail = "DELETE FROM schedule_detail WHERE detail_id = ?";
	    int scheduleDeleteCount = 0; // スケジュール本体の削除件数を記録する変数

	    try (Connection conn = DBManager.getConnection();
	         PreparedStatement pStmtDetail = conn.prepareStatement(sqlDeleteDetail)){

	        //  スケジュール詳細を削除 (失敗しても続行)
	        pStmtDetail.setInt(1, detailId);
	        scheduleDeleteCount =pStmtDetail.executeUpdate();
	       System.out.println(scheduleDeleteCount);
	        // スケジュール本体が1件削除できた場合に成功とする
	        return scheduleDeleteCount == 1;

	    } catch (SQLException e) {
	        // SQLエラーが発生したらログに出力して、false（失敗）を返す
	        e.printStackTrace();
	        return false;
	    }
	}
}

