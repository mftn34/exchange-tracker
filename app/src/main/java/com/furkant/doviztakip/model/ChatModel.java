package com.furkant.doviztakip.model;

public class ChatModel {

        private String message;
        private String author;

        // Required default constructor for Firebase object mapping
        @SuppressWarnings("unused")
        private ChatModel() {
        }

        public ChatModel(String message, String author) {
            this.message = message;
            this.author = author;
        }

        public String getMessage() {
            return message;
        }

        public String getAuthor() {
            return author;
        }
}
