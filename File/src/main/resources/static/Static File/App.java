package myproject;
import java.util.ResourceBundle;
public class App {
public int userlogin(String in_user,String in_pwd) {
	ResourceBundle rb=ResourceBundle.getBundle("config");
	String username=rb.getString("username");
	String password=rb.getString("password");
	if(in_user==in_pwd) {
		return 1;
	}
	else
		return 0;
}
}
