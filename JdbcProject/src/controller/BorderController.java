package controller;

import java.util.ArrayList;
import java.util.HashMap;

import dto.Borders;
import service.BorderService;

public class BorderController {
	private BorderService service = new BorderService();
	public BorderController() {

	}
	public int add(Borders bor) {
		return service.add(bor);
		
	}
	public ArrayList <Borders> selectList() {
		return service.selectList();
	}
	public int selectIndex() {
		return service.selectIndex();
	}
	public Borders selectOne(int index_num) {
		return service.selectOne(index_num);
				
	}
	public ArrayList <Borders> selectUserBord(String userid) {
		return service.selectUserBord(userid);		
	}
	public boolean deleteOne(int delete_num, String delete_pwd) {
		return service.deleteOne(delete_num,delete_pwd);
	}

}
