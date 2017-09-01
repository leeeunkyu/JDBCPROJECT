package service;

import dao.UserDao;
import dto.Users;

public class UserService {
	private UserDao dao = UserDao.getInstance();
	public UserService() {
	}
	public int add(Users usr) {
		return dao.addUsers(usr);
	}
	public boolean checkID(String userid) {
		return dao.isUsers(userid) ;
	}
	public boolean userLogin(String userid, String userpwd) {
		return dao.selectUser(userid,userpwd);
	}
	public String userSelectID(String username) {
		return dao.selectID(username);
	}

}
