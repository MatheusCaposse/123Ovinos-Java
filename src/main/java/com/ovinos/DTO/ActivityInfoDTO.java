package com.ovinos.DTO;

public class ActivityInfoDTO {
    private int nexts;

    private int now;

    private int pending;

    public ActivityInfoDTO(){}

    public ActivityInfoDTO(int nexts, int now, int pending) {
        this.nexts = nexts;
        this.now = now;
        this.pending = pending;
    }

    public int getNexts() {
        return nexts;
    }

    public void setNexts(int nexts) {
        this.nexts = nexts;
    }

    public int getNow() {
        return now;
    }

    public void setNow(int now) {
        this.now = now;
    }

    public int getPending() {
        return pending;
    }

    public void setPending(int pending) {
        this.pending = pending;
    }


}
