package com.demo.hello.kwq;

public class RateItem {
    private int id;
    private String curName;
    private String curRate;

    public RateItem() {
        super();
        curName = "";
        curRate = "";
    }

    public RateItem(String curName, String curRate) {
        super();
        this.curName = curName;
        this.curRate = curRate;
    }

    public int getId() {
        return id;
    }

    public String getCurName() {
        return curName;
    }

    public String getCurRate() {
        return curRate;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCurName(String curName) {
        this.curName = curName;
    }

    public void setCurRate(String curRate) {
        this.curRate = curRate;
    }
}
