package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Schedule;

public class ScheduleDAO {
	public List<Schedule> findSchedulesByUserId(int userId) {
		Schedule schedule=null;
		List<Schedule> scheduleList=new ArrayList<>();
		//		DBManagerからgetConnection()でSQL接続
		try (Connection conn = DBManager.getConnection()) {

			// SELECT文を準備
			String sql = "SELECT schedule_id,user_id,title,date FROM schedule WHERE user_id = ? ";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setInt(1, userId);

			// SELECT文を実行し、結果表（ResultSet）を取得
			ResultSet rs = pStmt.executeQuery();
			// 結果表に格納されたレコードの内容を表示
			while (rs.next()) {
				int scheduleId = rs.getInt("schedule_id");
				int id = rs.getInt("user_id");
				java.time.LocalDate date = rs.getDate("date").toLocalDate();
				String title = rs.getString("title");


				schedule = new Schedule(scheduleId,id,title,date);
				scheduleList.add(schedule);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return scheduleList;
	}

	public void Scheduleinsert(Schedule schedule) {
			// SELECT文を準備	
			try (Connection conn = DBManager.getConnection()) {
	            String sql = "INSERT INTO schedule (user_id, title, date) VALUES (?, ?, ?)";
	            PreparedStatement pStmt = conn.prepareStatement(sql);
	            pStmt.setInt(1, schedule.getUser_id());
	            pStmt.setString(2, schedule.getTitle());
	            pStmt.setDate(3, java.sql.Date.valueOf(schedule.getDate())); 
	            // LocalDate → SQL Date

	            pStmt.executeUpdate(); // 実行

	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
		}
	}
	

