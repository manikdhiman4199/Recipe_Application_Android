package com.example.projectapplication;

import io.realm.RealmObject;

public class FavoriteListModel extends RealmObject {
        private int recipeImg;
        private String recipeTitle, duration, tags, recipeProcedure;
        private boolean favorite;


        public FavoriteListModel(int recipeImg, String recipeTitle, String duration, String tags, String recipeProcedure) {
            this.recipeImg = recipeImg;
            this.recipeTitle = recipeTitle;
            this.duration = duration;
            this.tags = tags;
            this.recipeProcedure = recipeProcedure;
        }

        public int getRecipeImg() {
            return recipeImg;
        }

        public void setRecipeImg(int recipeImg) {
            this.recipeImg = recipeImg;
        }

        public String getRecipeTitle() {
            return recipeTitle;
        }

        public void setRecipeTitle(String recipeTitle) {
            this.recipeTitle = recipeTitle;
        }

        public String getDuration() {
            return duration;
        }

        public void setDuration(String duration) {
            this.duration = duration;
        }

        public String getTags() {
            return tags;
        }

        public void setTags(String tags) {
            this.tags = tags;
        }

        public String getRecipeProcedure() {
            return recipeProcedure;
        }

        public void setRecipeProcedure(String recipeProcedure) {
            this.recipeProcedure = recipeProcedure;
        }

        public FavoriteListModel() {
        }

    }

