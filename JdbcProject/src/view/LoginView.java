package view;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Scanner;
import java.util.regex.Pattern;

import controller.BorderController;
import dto.Borders;

/**
 * 사용자가 정상적으로 로그인이 되었을경우 보이는 view 페이지
 * @author kosta
 *
 */
public class LoginView {
	Scanner bscan;
	String userid;
	DateFormat df;
	BorderController bcontrol;
	ArrayList<Borders> ab;
	LogView lv;
	CommentsView cv;
	public LoginView() {
		
	}
	/**
	 * 로그인 페이지를 위한 초기 설정
	 * @param userid 로그인에 성공한 사용자 아이디
	 */
	public LoginView(String userid) {
		bscan = new Scanner(System.in);
		bcontrol = new BorderController();
		lv = new LogView(userid);
		this.userid = userid;
	}
	/**
	 * 로그인 페이지에서 게시판을 사용하기 위한 메뉴
	 */
	public void BorderMenu() {
		String num = null;
		String pattern="^[1-7]+";

		System.out.println("-------------------------------");
		System.out.println("1.게시물 작성");
		System.out.println("2.게시물 리스트 보기");		
		System.out.println("3.선택한 글 보기");
		System.out.println("4.내가 작성한 게시물 보기");
		System.out.println("5.내가 작성한 게시물 삭제");		
		System.out.println("6.로그 보기");
		System.out.println("7.로그아웃 ");
		System.out.println("-------------------------------");
		num = bscan.next();
		if (Pattern.matches(pattern,num)) {
			choiceBorder(Integer.parseInt(num));
		}else{
			System.out.println("1~-7까지의 숫자만 입력해 주세요.");
			BorderMenu();
		}
	}
	public int indexCount() {
		int index=bcontrol.selectIndex();
		return index;
	}
	
	/**
	 * 게시판 메뉴에서 선택한 값에 따라서 게시판 기능 사용
	 * @param num 게시판 메뉴에서 선택한 값
	 */
	public void choiceBorder(int num) {
		String head = null;
		String body = null;
		df = DateFormat.getDateTimeInstance(0, 1,Locale.KOREA);
		switch (num) {
		case 1:
			System.out.println("-------------------------------");
			System.out.println("1.게시물 제목을 입력하세요. ");
			head = bscan.next();
			System.out.println("2.게시물 내용을 입력하세요. ");
			body = bscan.next();
			System.out.println("-------------------------------");
			bcontrol.add(new Borders(userid, indexCount(), head, body, 0, df.format(new Date())));
			BorderMenu();
			break;
		case 2:
			System.out.println("-------------------------------");
			ab = bcontrol.selectList();
			for (int i = 0; i < ab.size(); i++) {
				System.out.println(ab.get(i).toString());
			}
			System.out.println("-------------------------------");
			BorderMenu();
			break;
		case 3:
			String pattern="^[0-9]*";
			String index_num = null;
			System.out.println("-------------------------------");
			System.out.print("글 번호를 선택해 주세요. ");
			index_num = bscan.next();
			if (Pattern.matches(pattern,index_num)) {
				System.out.println(index_num+"번 게시글");
				cv = new CommentsView(Integer.parseInt(index_num),userid);
				cv.ContentMenu(bcontrol.selectOne(Integer.parseInt(index_num)));
			}else{
				System.out.println("적절한 글 번호를 선택해 주세요.");
			}			
			BorderMenu();
			break;
		case 4:
			System.out.println("-------------------------------");
			ab = bcontrol.selectUserBord(userid);
			for (int i = 0; i < ab.size(); i++) {
				System.out.println(ab.get(i).toString());
			}
			System.out.println("-------------------------------");
			BorderMenu();
			break;
		case 5:
			String delete_num = null;
			String delete_pwd = null;
			String pattern1="^[0-9]*";
		
			System.out.println("-------------------------------");
			System.out.print("글 번호를 선택해 주세요. ");
			delete_num = bscan.next();
			if (Pattern.matches(pattern1,delete_num)) {
				System.out.println(delete_num+"번 게시글 삭제");
				System.out.println("-------------------------------");
				System.out.println("회원님 비밀번호를 입력해 주세요.");
				delete_pwd = bscan.next();
				System.out.println("-------------------------------");
				if (bcontrol.deleteOne(Integer.parseInt(delete_num),delete_pwd)) {
					System.out.println("삭제가 완료 되었습니다.");
					BorderMenu();
				}
				System.out.println("비밀번호를 확인해 주세요.");				
			}else{
				System.out.println("올바른 글번호를 입력해 주세요.");
			}
			BorderMenu();
			break;
		case 6:
			lv.LogMenu();
			BorderMenu();
			break;
		case 7:
			System.out.println("정상적으로 로그아웃이 되었습니다.");
			
			break;
		default:
			break;
		}
	}

}
