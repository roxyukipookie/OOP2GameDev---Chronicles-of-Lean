package login;

public class Users {
    String username, password;
    //Constructor
    public Users(String username, String password){
        this.username = username;
        this.password = password;
    }

    //getters
    public String getUsername(){
        return this.username;
    }
    public String getPassword(){
        return this.password;
    }
}