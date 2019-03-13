package com.example.a14532.mymovie.data.remote.bean;

import java.util.List;
//用户统计数据Bean
public class UserCountBean {
    private List<NameCountBean> manYear;
    private List<NameCountBean> womanYear;
    private List<NameCountBean> manStudy;
    private List<NameCountBean> womanStudy;

    public List<NameCountBean> getManStudy() {
        return manStudy;
    }

    public List<NameCountBean> getManYear() {
        return manYear;
    }

    public List<NameCountBean> getWomanStudy() {
        return womanStudy;
    }

    public List<NameCountBean> getWomanYear() {
        return womanYear;
    }

    public static class NameCountBean{
        private String name;
        private int count;

        public int getCount() {
            return count;
        }

        public String getName() {
            return name;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
