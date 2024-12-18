package fr.insa.brauard.AuthentificationService.Model;

public class User {
    private int userId;
    private String userPseudo;
    private String userPassword;
    private String userReview;
    private String userType;

    public User(int userId, String userPseudo, String userPassword, String userReview, String userType) {
        this.userId = userId;
        this.userPseudo = userPseudo;
        this.userPassword = userPassword;
        this.userReview = userReview;
        this.userType = userType;
    }

    // Getters et setters
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserPseudo() {
        return userPseudo;
    }

    public void setUserPseudo(String userPseudo) {
        this.userPseudo = userPseudo;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserReview() {
        return userReview;
    }

    public void setUserReview(String userReview) {
        this.userReview = userReview;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}
