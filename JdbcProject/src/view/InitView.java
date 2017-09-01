package view;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;

import controller.UserController;
import dto.Users;

public class InitView {
	Scanner scan;
	DateFormat df;
	UserController ucontrol;
	LoginView login;
	public InitView() {
		ucontrol = new UserController();
		scan = new Scanner(System.in);
		initmenu();
	}
	public static void main(String[] args) {
		InitView iv = new InitView();
		

	}
	private void initmenu() {
		int num = 0;
		while(true) {
			System.out.println("------------------------------");
			System.out.println("1. 회원가입 :");
			System.out.println("2. 로그인 :");
			System.out.println("3. 아이디 찾기 :");
			System.out.println("4. 비밀번호찾기 :");	
			System.out.println("5. 시스템 종료 :");			
			System.out.println("------------------------------");
			num=scan.nextInt();
			choice(num);
		}
	}
	private void choice(int num) {
		String table_name = null;
		String y_or_n = null;
		String userid = null;
		String userpwd = null;
		String username = null;
		String gender = null;
		String signday = null;
		
		switch (num) {
		case 1:
			int result = 0;
			System.out.println("----------회원가입 메뉴------------");
			while(true) {
				System.out.print("아이디를 입력 하세요.");
				userid = scan.next();
				if(ucontrol.check(userid)) {
					System.err.println("중복되는 아이디가 있습니다. 다른 아이디를 입력하세요.");
				}else {
					System.out.println("중복되는 아이디가 없습니다. 사용하셔도 좋습니다.");
					break;
				}
			}
			System.out.print("비밀번호를 입력 하세요.");
			userpwd = scan.next();
			System.out.print("이름을 입력 하세요.");
			username = scan.next();
			System.out.print("사용자 성별을 입력 하세요.");
			gender = scan.next();
			df = DateFormat.getDateInstance(0, Locale.KOREA);		
			result = ucontrol.add(new Users(userid,userpwd,username,gender,df.format(new Date())));
			if(result == 0) {
				System.err.println("회원가입 과정에서 오류가 있었습니다. 다시 시도해 주시길 바랍니다.");
			}
			initmenu();
			break;
		case 2:
			System.out.println("아이디를 입력하세요.");
			userid = scan.next();
			System.out.println("비밀번호를 입력하세요.");
			userpwd = scan.next();
			if(ucontrol.login(userid,userpwd)) {
				System.out.println("정상적인 로그인");
				login = new LoginView(userid);
				login.BorderMenu();
			}else {
				System.out.println("아이디나 비밀번호를 확인하세요.");
			}
			break;
		case 3:
			System.out.println("이름을 입력하세요.");
			String id;
			username = scan.next();
			id = ucontrol.selectID(username);
			System.out.println("회원님의 아이디는:  "+id+ "입니다.");
			break;	
		case 4:
				
			break;
		case 5:
			System.exit(1);
			break;
		default:
			break;
		}
	}
}
