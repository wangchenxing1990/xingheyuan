package com.xhy.xhyapp.myactivity;

/**
 * Created by Administrator on 2016/8/5.
 */
public class Servicecenter {

   private String articleTitle;
    private String url;





    public Servicecenter(String string1, String string2){

        articleTitle=string1;
        url=string2;

    } public Servicecenter(){



    }

    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getArticleTitle() {
        return articleTitle;
    }

    public String getUrl() {
        return url;
    }





}





