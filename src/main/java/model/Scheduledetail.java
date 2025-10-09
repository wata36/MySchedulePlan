package model;

import java.io.Serializable;

public class Scheduledetail implements Serializable {
	private String time;
	private String place;
	private String map;

	public Scheduledetail() {
	}

	public Scheduledetail(String time, String place, String map) {

		this.time = time;
		this.place = place;
		this.map = map;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getMap() {
		return map;
	}

	public void setMap(String map) {
		this.map = map;
	}

}
