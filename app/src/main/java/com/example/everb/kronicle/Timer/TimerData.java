package com.example.everb.kronicle.Timer;

public class TimerData {

    private int id;
    private String title;
//    private String color;

    private String focus_duration;
    private String short_break_duration;
    private String long_break_duration;
    private String long_break_wait;
//    private boolean session_auto_switch;
//
//    private  boolean screen_lock;
//
//    private boolean reminder;
//
//    private  boolean sb_enable_sound;
//    private int sb_sound;
//    private  short sb_volume;
//    private  boolean sb_tick;
//    private  boolean sb_back_noise;
//    private  boolean sb_vibrate;
//
//    private  boolean lb_enable_sound;
//    private int lb_sound;
//    private  short lb_volume;
//    private  boolean lb_tick;
//    private  boolean lb_back_noise;
//    private  boolean lb_vibrate;
//
//    private  boolean wi_fi;
//    private  boolean apps;

    public  TimerData() { }

    public TimerData(int id, String title, String focus_duration, String short_break_duration, String long_break_duration, String long_break_wait) {
        this.id = id;
        this.title = title;
        this.focus_duration = focus_duration;
        this.short_break_duration = short_break_duration;
        this.long_break_duration = long_break_duration;
        this.long_break_wait = long_break_wait;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFocus_duration() {
        return focus_duration;
    }

    public void setFocus_duration(String focus_duration) {
        this.focus_duration = focus_duration;
    }

    public String getShort_break_duration() {
        return short_break_duration;
    }

    public void setShort_break_duration(String short_break_duration) {
        this.short_break_duration = short_break_duration;
    }

    public String getLong_break_duration() {
        return long_break_duration;
    }

    public void setLong_break_duration(String long_break_duration) {
        this.long_break_duration = long_break_duration;
    }

    public String getLong_break_wait() {
        return long_break_wait;
    }

    public void setLong_break_wait(String long_break_wait) {
        this.long_break_wait = long_break_wait;
    }
}
