package controller;

import java.util.ArrayList;

import dto.Comments;
import service.CommentService;

public class CommentController {
	private CommentService service = new CommentService();
	public CommentController() {
		// TODO Auto-generated constructor stub
	}
	public int insertComment (Comments com){
		
		return service.insertComment(com);
		
	}
	public ArrayList<Comments> selectCommentList(int indexNum){
		return service.selectCommentList(indexNum);
		
	}
	
}
