package nlu.hmuaf.android_bookapp.user.HomeScreen.Class;

public class Review {
    private String userName;
    private String userReview;

    public Review(String userName, String userReview) {
        this.userName = userName;
        this.userReview = userReview;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserReview() {
        return userReview;
    }
}
