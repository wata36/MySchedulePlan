package model;

import java.io.Serializable;
import java.time.LocalTime;

/**
 * 予定の詳細情報を表すモデルクラス。
 * <p>
 * このクラスは、特定の予定(schedule_id)に紐づく詳細(detail_id)を保持します。
 * 時間、場所、詳細内容、地図情報を扱います。
 * </p>
 */
public class Scheduledetail implements Serializable {

    private int detail_id;
    private int schedule_id;
    private LocalTime time;
    private String place;
    private String detail;
    private String map;

    /**
     * デフォルトコンストラクタ
     */
    public Scheduledetail() {
    }

    /**
     * 全フィールドを指定するコンストラクタ
     *
     * @param detail_id 詳細ID
     * @param schedule_id 予定ID
     * @param time 時間
     * @param place 場所
     * @param detail 詳細内容
     * @param map 地図情報
     */
    public Scheduledetail(int detail_id, int schedule_id, LocalTime time, String place, String detail, String map) {
        this.detail_id = detail_id;
        this.schedule_id = schedule_id;
        this.time = time;
        this.place = place;
        this.detail = detail;
        this.map = map;
    }

    /** 詳細IDを取得する */
    public int getDetail_id() {
        return detail_id;
    }

    /** 詳細IDを設定する */
    public void setDetail_id(int detail_id) {
        this.detail_id = detail_id;
    }

    /** 予定IDを取得する */
    public int getSchedule_id() {
        return schedule_id;
    }

    /** 予定IDを設定する */
    public void setSchedule_id(int schedule_id) {
        this.schedule_id = schedule_id;
    }

    /** 時間を取得する */
    public LocalTime getTime() {
        return time;
    }

    /** 時間を設定する */
    public void setTime(LocalTime time) {
        this.time = time;
    }

    /** 場所を取得する */
    public String getPlace() {
        return place;
    }

    /** 場所を設定する */
    public void setPlace(String place) {
        this.place = place;
    }

    /** 詳細内容を取得する */
    public String getDetail() {
        return detail;
    }

    /** 詳細内容を設定する */
    public void setDetail(String detail) {
        this.detail = detail;
    }

    /** 地図情報を取得する */
    public String getMap() {
        return map;
    }

    /** 地図情報を設定する */
    public void setMap(String map) {
        this.map = map;
    }

}
