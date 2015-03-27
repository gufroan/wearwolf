package org.gufroan.wearwolf.data;

public class Part {

//    private int id;

    private String stringData;

    public Part(final String stringData) {
        this.stringData = stringData;
    }

//    public Part(final int id, final String stringData) {
//        this.id = id;
//        this.stringData = stringData;
//    }
//
//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }

    public String getStringData() {
        return stringData;
    }

    public void setStringData(String stringData) {
        this.stringData = stringData;
    }
}
