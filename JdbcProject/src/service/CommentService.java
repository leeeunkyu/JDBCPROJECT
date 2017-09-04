package service;

import java.util.ArrayList;

import dao.CommentDao;
import dto.Comments;

public class CommentService {
	private CommentDao cdao = CommentDao.getInstance();
	
	public ArrayList<Comments> selectCommentList (int indexNum) {
		return cdao.selectCommentList(indexNum);
	}

	public int insertComment(Comments com) {
		return cdao.insertComment(com);
	}

	

}
