package model;

import java.io.Serializable;
import java.time.LocalTime;

public class Scheduledetail implements Serializable {
	private int detail_id;
	private int schedule_id;
	private LocalTime time;
	private String place;
	private String detail;
	private String map;

	public Scheduledetail() {
	}

	public Scheduledetail(int detail_id, int schedule_id, LocalTime time, String place, String detail, String map) {
		this.detail_id = detail_id;
		this.schedule_id = schedule_id;
		this.time = time;
		this.place = place;
		this.detail = detail;
		this.map = map;
	}

	public int getDetail_id() {
		return detail_id;
	}

	public void setDetail_id(int detail_id) {
		this.detail_id = detail_id;
	}

	public int getSchedule_id() {
		return schedule_id;
	}

	public void setSchedule_id(int schedule_id) {
		this.schedule_id = schedule_id;
	}

	public LocalTime getTime() {
		return time;
	}

	public void setTime(LocalTime time) {
		this.time = time;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getMap() {
		return map;
	}

	public void setMap(String map) {
		this.map = map;
	}

}
