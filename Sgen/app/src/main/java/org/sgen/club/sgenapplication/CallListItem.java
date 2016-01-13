package org.sgen.club.sgenapplication;

/**
 * Created by HOJUNGPC on 2016-01-09.
 */
public class CallListItem {


    private String name;
    private String size;
    private String price;
    private String code;
    private String amount;
    private String color;
    private String imageURL_cloth;
    private String stockURL;
    private String request_code;
    private String room_number;

    public String getRoom_number() {
        return room_number;
    }

    public void setRoom_number(String room_number) {
        this.room_number = room_number;
    }


    public String getRequest_code() {
        return request_code;
    }

    public void setRequest_code(String request_code) {
        this.request_code = request_code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getImageURL_cloth() {
        return imageURL_cloth;
    }

    public void setImageURL_cloth(String imageURL_cloth) {
        this.imageURL_cloth = imageURL_cloth;
    }

    public String getStockURL() {
        return stockURL;
    }

    public void setStockURL(String stockURL) {
        this.stockURL = stockURL;
    }


    public String toString(){
        String returnStr="name : "+name+", size : "+size+", price : "+price+", code : "+code+", amount : "+amount+", color : "+color+", imageURL : "+imageURL_cloth+", stockURL : "+stockURL;
        return returnStr;
    }
}
