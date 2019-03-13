package com.example.a14532.mymovie.data.remote.bean;

import java.util.List;
//统计电影数据Bean
public class DataCountBean {
    private List<NameCountBean> typeCount;
    private List<NameCountBean> languageCount;
    private List<NameCountBean> yearCount;

    public List<NameCountBean> getLanguageCount() {
        return languageCount;
    }

    public List<NameCountBean> getTypeCount() {
        return typeCount;
    }

    public List<NameCountBean> getYearCount() {
        return yearCount;
    }

    public void setLanguageCount(List<NameCountBean> languageCount) {
        this.languageCount = languageCount;
    }

    public void setTypeCount(List<NameCountBean> typeCount) {
        this.typeCount = typeCount;
    }

    public void setYearCount(List<NameCountBean> yearCount) {
        this.yearCount = yearCount;
    }

    public static class NameCountBean{
        private String name;
        private float count;

        public float getCount() {
            return count;
        }

        public String getName() {
            return name;
        }

        public void setCount(float count) {
            this.count = count;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
