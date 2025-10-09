package model;

import java.io.Serializable;

public class Schedule implements Serializable {
	private String title;
	private String date;

	public Schedule() {
	}

	public Schedule(String title, String date) {

		this.title = title;
		this.date = date;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

}
