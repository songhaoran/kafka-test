package com.song.consume.vo;

/**
 * Created by Song on 2019/08/27.
 */
public class Msg {

    Integer id;

    String value;

    public Msg() {
    }

    public Msg(Integer id, String value) {
        this.id = id;
        this.value = value;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
