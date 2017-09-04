package view;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;
import java.util.regex.Pattern;

import controller.CommentController;
import dto.Borders;
import dto.Comments;
import oracle.net.aso.b;

public class CommentsView{
	public int indexNum;
	public String userId;
	Scanner scan;
	CommentController ccontrl;
	DateFormat df;

	public CommentsView(int index_num, String userid) {
		scan = new Scanner(System.in);
		ccontrl = new CommentController();
		this.indexNum = index_num;
		this.userId = userid;
	}
	public void contentInsert(Borders borders) {
		df = DateFormat.getDateTimeInstance(0,1 ,Locale.KOREA);
		int result = 0;
		System.out.println("댓글 내용을 작성하세요----------------------------------");
		String content;
		content = scan.next();
		result = ccontrl.insertComment(new Comments(userId, indexNum, content, df.format(new Date())));
		if (result == 1) {
			System.out.println("정상적으로 댓글이 등록 되었습니다.");
			ContentMenu(borders);
		}
		
	}

	public void ContentMenu(Borders borders) {
		String num;
		if (borders == null) {
		}else {
			ArrayList<Comments> acom = new ArrayList<>();
			System.out.println(borders);
			System.out.println("글내용- "+borders.getBord_body());
			System.out.println("댓글 목록---------------------------------------------");
			acom = ccontrl.selectCommentList(indexNum);
			for (int i = 0; i < acom.size(); i++) {
				System.out.println(acom.get(i).toString());	
			}
			System.out.println("---------------------------------------------------");
			System.out.println("1.뒤로 가기 2.댓글작성");
			String pattern="^[1-2]+";
			while(true) {
				num = scan.next();
				if (Pattern.matches(pattern,num)) {
					switch (Integer.parseInt(num)) {
					case 1:
						return;
					case 2:
						contentInsert(borders);
						break;
					default:
						break;
					}
				}else{
					System.out.println("적절한  번호를 선택해 주세요.");
				}	
			}
				
		}
	
	}
	
}
