package pojo;

import java.util.List;

public class User {
    /**
     * The {UserData} class represents the data of a user.
     */
    public static class UserData {
        private int id;
        private String email;
        private String first_name;
        private String last_name;
        private String avatar;
        public UserData() {
        }
        // Getters
        public int getId() {
            return id;
        }
        public String getEmail() {
            return email;
        }
        public String getFirst_name() {
            return first_name;
        }
        public String getLast_name() {
            return last_name;
        }
        public String getAvatar() {
            return avatar;
        }
        //Setters
        public void setId(int id) {
            this.id = id;
        }
        public void setEmail(String email) {
            this.email = email;
        }
        public void setFirst_name(String first_name) {
            this.first_name = first_name;
        }
        public void setLast_name(String last_name) {
            this.last_name = last_name;
        }
        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }
    }
    /**
     * The {UserResponse} class represents the response data of a user request.
     */
    public static class UserResponse {
        private int page;
        private int per_page;
        private int total;
        private int total_pages;
        private List<UserData> data;
        private SupportData support;

        public int getPage() {
            return page;
        }
        public int setPage(int page) {
            this.page = page;
            return page;
        }
        public int getPer_page() {
            return per_page;
        }
        public void setPer_page(int per_page) {
            this.per_page = per_page;
        }
        public int getTotal() {
            return total;
        }
        public void setTotal(int total) {
            this.total = total;
        }
        public int getTotal_pages() {
            return total_pages;
        }
        public void setTotal_pages(int total_pages) {
            this.total_pages = total_pages;
        }
        public List<UserData> getData() {
            return data;
        }
        public void setData(List<UserData> data) {
            this.data = data;
        }
        public SupportData getSupport() {
            return support;
        }
        public void setSupport(SupportData support) {
            this.support = support;
        }
    }
    /**
     * The {SupportData} class represents the support information.
     */
    public static class SupportData {
        private String url;
        private String text;

        public String getText() {
            return text;
        }
        public void setText(String text) {
            this.text = text;
        }
        public String getUrl() {
            return url;
        }
        public void setUrl(String url) {
            this.url = url;
        }
    }

}
