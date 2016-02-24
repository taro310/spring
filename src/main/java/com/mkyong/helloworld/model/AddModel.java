package com.mkyong.helloworld.model;

/**
 * @author Satoshi Taromaru
 * @since 1.5
 */
public class AddModel {
    private String title;
    private String msg;
    
    private Integer num1;
    private Integer num2;
    private Integer answer;

    
    public void calc() {
        answer = num1 + num2;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getNum1() {
        return num1;
    }

    public void setNum1(Integer num1) {
        this.num1 = num1;
    }

    public Integer getNum2() {
        return num2;
    }

    public void setNum2(Integer num2) {
        this.num2 = num2;
    }

    public Integer getAnswer() {
        return answer;
    }

    public void setAnswer(Integer answer) {
        this.answer = answer;
    }
}
