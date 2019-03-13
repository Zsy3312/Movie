package com.example.a14532.mymovie.data.remote.bean;

import java.util.List;
//收藏电影数据Bean
public class CollectMovieBean {

    private List<SubjectsBean> subjects;

    public List<SubjectsBean> getSubjects(){
        return subjects;
    }

    public void setSubjects(List<SubjectsBean> subjects){
        this.subjects=subjects;
    }

    public static class SubjectsBean {
        private String username;
        private String movieId;
        private String movieTitle;
        private String time;


        public String getMovieId() {
            return movieId;
        }

        public String getMovieTitle() {
            return movieTitle;
        }

        public String getUsername() {
            return username;
        }

        public String getTime() {
            return time;
        }


        public void setMovieId(String movieId) {
            this.movieId = movieId;
        }

        public void setMovieTitle(String movieTitle) {
            this.movieTitle = movieTitle;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public void setTime(String time) {
            this.time = time;
        }
    }
}
