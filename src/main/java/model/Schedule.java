package model;

import java.io.Serializable;
import java.time.LocalDate;

public class Schedule implements Serializable {
	private int schedule_id;
	private int user_id;
	private String title;
	private LocalDate date;

	public Schedule() {
	}

	public Schedule(int schedule_id, int user_id, String title, LocalDate  date) {
		this.schedule_id = schedule_id;
		this.user_id = user_id;
		this.title = title;
		this.date = date;
	}

	public int getSchedule_id() {
		return schedule_id;
	}

	public void setSchedule_id(int schedule_id) {
		this.schedule_id = schedule_id;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	
}
