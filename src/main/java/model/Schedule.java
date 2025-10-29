package model;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * ユーザーの予定を表すモデルクラス。
 * <p>
 * このクラスは、特定のユーザー(user_id)に紐づく予定(schedule_id)を保持します。
 * 予定のタイトル(title)と日付(date)を扱います。
 * </p>
 */
public class Schedule implements Serializable {

    private int schedule_id;
    private int user_id;
    private String title;
    private LocalDate date;

    /**
     * デフォルトコンストラクタ
     */
    public Schedule() {
    }

    /**
     * 全フィールドを指定するコンストラクタ
     *
     * @param schedule_id 予定ID
     * @param user_id ユーザーID
     * @param title 予定のタイトル
     * @param date 予定の日付
     */
    public Schedule(int schedule_id, int user_id, String title, LocalDate date) {
        this.schedule_id = schedule_id;
        this.user_id = user_id;
        this.title = title;
        this.date = date;
    }

    /** 予定IDを取得する */
    public int getSchedule_id() {
        return schedule_id;
    }

    /** 予定IDを設定する */
    public void setSchedule_id(int schedule_id) {
        this.schedule_id = schedule_id;
    }

    /** ユーザーIDを取得する */
    public int getUser_id() {
        return user_id;
    }

    /** ユーザーIDを設定する */
    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    /** 予定のタイトルを取得する */
    public String getTitle() {
        return title;
    }

    /** 予定のタイトルを設定する */
    public void setTitle(String title) {
        this.title = title;
    }

    /** 予定の日付を取得する */
    public LocalDate getDate() {
        return date;
    }

    /** 予定の日付を設定する */
    public void setDate(LocalDate date) {
        this.date = date;
    }
}
