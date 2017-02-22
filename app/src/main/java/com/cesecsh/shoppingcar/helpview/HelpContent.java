package com.cesecsh.shoppingcar.helpview;

/**
 * ShoppingCar
 * Created by RockQ on 2017/2/22.
 */

public class HelpContent {
    private String title;
    private String content;

    public HelpContent(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getHelpContent() {
        return title + ":\t\t" + content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
