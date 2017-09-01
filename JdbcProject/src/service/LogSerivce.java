package service;

import java.util.ArrayList;

import dao.LogDao;
import dto.Logs;

public class LogSerivce {
	private LogDao ldao = LogDao.getInstance();
	
	public ArrayList<Logs> selectLog(String userid) {
		return ldao.selectLog(userid);
	}

}
