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
	public boolean userDelete(String deleteid, String deletepwd) {
		return dao.deleteUser(deleteid,deletepwd);
	}
	public boolean userSelectPWD(String userid, String username) {
		return dao.selectUserPWD(userid,username);
	}
	public boolean userModifyPWD(String userid, String modipwd) {
		return dao.modifyUserPWD(userid,modipwd);
	}

}
