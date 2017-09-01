package service;

import java.util.ArrayList;
import java.util.HashMap;

import dao.BorderDao;
import dto.Borders;

public class BorderService {
	private BorderDao bdao = BorderDao.getInstance();
	
	public BorderService() {
	}
	
	public int add(Borders bor) {
		return bdao.addBorder(bor);
	}
	public ArrayList<Borders> selectList() {
		return bdao.selectList();
	}
	public int selectIndex() {
		return bdao.selectIndex();
	}
	public Borders selectOne(int index_num) {
		
		return bdao.selctOne(index_num);
	}
	public ArrayList<Borders> selectUserBord(String userid) {
		// TODO Auto-generated method stub
		return bdao.selectUserBord(userid);
	}

	public boolean deleteOne(int delete_num, String delete_pwd) {
		// TODO Auto-generated method stub
		return bdao.deleteOne(delete_num,delete_pwd);
	}

}
