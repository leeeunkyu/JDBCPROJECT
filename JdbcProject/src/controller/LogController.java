package controller;

import java.util.ArrayList;

import dto.Logs;
import service.LogSerivce;

public class LogController {
	private LogSerivce service = new LogSerivce();

	public LogController() {
		// TODO Auto-generated constructor stub
	}
	
	public ArrayList<Logs> selectLog(String userid) {
		return service.selectLog(userid);
	}

}
