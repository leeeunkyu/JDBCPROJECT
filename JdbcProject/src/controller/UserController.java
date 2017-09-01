package controller;

import dto.Users;
import service.UserService;

public class UserController {
	private UserService service = new UserService();
	public UserController() {
	}
	public int add(Users users) {
		return service.add(users);
	}
	public boolean check(String userid) {
		return service.checkID(userid);
	}
	public boolean login(String userid, String userpwd) {
		return service.userLogin(userid,userpwd);
	}
	public String selectID(String username) {
		return service.userSelectID(username);
	}
	

}
