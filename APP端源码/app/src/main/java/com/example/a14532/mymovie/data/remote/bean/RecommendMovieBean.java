package com.example.a14532.mymovie.data.remote.bean;

import java.util.List;
//推荐电影数据Bean
public class RecommendMovieBean {
    private List<SubjectsBean> subjects;

    public List<SubjectsBean> getSubjects(){
        return subjects;
    }

    public void setSubjects(List<SubjectsBean> subjects){
        this.subjects=subjects;
    }

    public static class SubjectsBean {
        private String movieId;
        private String movieTitle;
        private String movieDirector;
        private String movieYear;
        private String movieType;
        private String movieLanguage;
        private String reason;


        public String getMovieId() {
            return movieId;
        }

        public String getMovieTitle() {
            return movieTitle;
        }

        public String getMovieDirector() {
            return movieDirector;
        }

        public String getMovieLanguage() {
            return movieLanguage;
        }

        public String getMovieType() {
            return movieType;
        }

        public String getMovieYear() {
            return movieYear;
        }

        public String getReason() {
            return reason;
        }

        public void setMovieDirector(String movieDirector) {
            this.movieDirector = movieDirector;
        }

        public void setMovieLanguage(String movieLanguage) {
            this.movieLanguage = movieLanguage;
        }

        public void setMovieType(String movieType) {
            this.movieType = movieType;
        }

        public void setMovieYear(String movieYear) {
            this.movieYear = movieYear;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }

        public void setMovieId(String movieId) {
            this.movieId = movieId;
        }

        public void setMovieTitle(String movieTitle) {
            this.movieTitle = movieTitle;
        }


    }
}
