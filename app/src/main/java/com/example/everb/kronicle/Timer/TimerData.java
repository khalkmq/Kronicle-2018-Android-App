package com.example.everb.kronicle.Timer;

public class TimerData {

    private String title;
    private String color;

    private long focus_duration;
    private long short_break_duration;
    private long long_break_duration;
    private short long_break_wait;
    private boolean session_auto_switch;
    
//    private  boolean screen_lock;

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

    public TimerData(String title, String color, long focus_duration, long short_break_duration, long long_break_duration, short long_break_wait, boolean session_auto_switch) {
        this.title = title;
        this.color = color;
        this.focus_duration = focus_duration;
        this.short_break_duration = short_break_duration;
        this.long_break_duration = long_break_duration;
        this.long_break_wait = long_break_wait;
        this.session_auto_switch = session_auto_switch;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public long getFocus_duration() {
        return focus_duration;
    }

    public void setFocus_duration(long focus_duration) {
        this.focus_duration = focus_duration;
    }

    public long getShort_break_duration() {
        return short_break_duration;
    }

    public void setShort_break_duration(long short_break_duration) {
        this.short_break_duration = short_break_duration;
    }

    public long getLong_break_duration() {
        return long_break_duration;
    }

    public void setLong_break_duration(long long_break_duration) {
        this.long_break_duration = long_break_duration;
    }

    public short getLong_break_wait() {
        return long_break_wait;
    }

    public void setLong_break_wait(short long_break_wait) {
        this.long_break_wait = long_break_wait;
    }

    public boolean isSession_auto_switch() {
        return session_auto_switch;
    }

    public void setSession_auto_switch(boolean session_auto_switch) {
        this.session_auto_switch = session_auto_switch;
    }
}
