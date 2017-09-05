package view;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Locale;
import java.util.Scanner;
import java.util.regex.Pattern;

import Util.SecretString;
import Util.TransferExcel;
import controller.UserController;
import dto.Users;

/**
 * 실행시 초기화면을 담당하는 view 
 * @author kosta
 *
 */
public class InitView {
	Scanner scan;
	DateFormat df;
	UserController ucontrol;
	LoginView login;
	TransferExcel te;
	SecretString secret = new SecretString();
	public InitView() {
		te = new TransferExcel();
		ucontrol = new UserController();
		scan = new Scanner(System.in);
		initmenu();
	}
	public static void main(String[] args) {
		InitView iv = new InitView();
	}
	/**
	 * 초기 선택 메뉴 
	 * 1~6까지 메뉴를 선택후 선택한 값에 따른 데이터 처리
	 */
	private void initmenu() {
		String choiceNum = null;
		System.out.println("------------------------------");
		System.out.println("1. 회원가입 :");
		System.out.println("2. 로그인 :");
		System.out.println("3. 아이디 찾기 :");
		System.out.println("4. 비밀번호찾기 :");	
		System.out.println("5. 회원 탈퇴");
		System.out.println("6. 시스템 종료 :");			
		System.out.println("------------------------------");
		String pattern="^[1-6]+";
		choiceNum = scan.next();
		if (Pattern.matches(pattern,choiceNum)) {
			choice(Integer.parseInt(choiceNum));
		}else{
			System.out.println("1~-6까지의 숫자만 입력해 주세요.");
			initmenu();
		}
	}
	/**
	 * init 메뉴에서 선택한 값을 받아 해당 값에 따른 데이터 처리
	 * @param num
	 */
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
			System.out.print("아이디를 입력 하세요.");
			while(true) {
				userid = scan.next();
				if(ucontrol.check(userid)) {
					System.err.println("중복되는 아이디가 있습니다.\n다른 아이디를 입력하세요.");
				}else {
					System.out.println("중복되는 아이디가 없습니다. 사용하셔도 좋습니다.");
					break;
				}
			}
			System.out.print("비밀번호를 입력 하세요.");
			userpwd = scan.next();
			System.out.print("이름을 입력 하세요.");
			username = scan.next();
			System.out.print("사용자 성별을 입력 하세요(남:m 여:f 혹은:o).");
			gender = scan.next();
			df = DateFormat.getDateTimeInstance(0, 1,Locale.KOREA);
			result = ucontrol.add(te.transfer(new Users(userid,userpwd,username,gender,df.format(new Date()))));
			if(result == 0) {
				System.err.println("회원가입 과정에서 오류가 있었습니다. 다시 시도해 주시길 바랍니다.");
			}
			initmenu();
			break;
		case 2:
			int logincount = 0;
			while(true) {
				System.out.println("아이디를 입력하세요.");
				userid = scan.next();
				System.out.println("비밀번호를 입력하세요.");
				userpwd = scan.next();
				if(ucontrol.login(userid,userpwd)) {
					System.out.println("정상적인 로그인");
					login = new LoginView(userid);
					login.BorderMenu();
					break;
				}else {
					System.out.println("아이디나 비밀번호를 확인하세요.");
					logincount++;
					if (logincount>5) {
						System.out.println("로그인 허용횟수를 5회이상 초과해서 초기화면으로 돌아갑니다.");
						break;
					}
				}
			}
			initmenu();
			break;
		case 3:
			int namecount = 0;
			while(true) {
				System.out.println("이름을 입력하세요.");
				String id;
				username = scan.next();
				id = ucontrol.selectID(username);
				if (id != null) {
					System.out.println("회원님의 아이디는:  "+id+ "입니다.");
					break;
				}else {
					System.out.println("올바른 이름을 입력하세요.");
					namecount++;
					if (namecount > 5) {
						System.out.println("아이디 찾기 허용횟수를 5회이상 초과해서 초기화면으로 돌아갑니다.");
						break;
					}
				}
			}
			initmenu();
			break;	
		case 4:
			System.out.println("아이디를 입력하세요");
			userid = scan.next();
			System.out.println("이름을 입력 하세요");
			username = scan.next();
			if(ucontrol.selectPWD(userid,username)){
				System.out.println("보안문자를 입력해주세요");
				String str = secret.secret().toString();
				String tstr = null;
				System.out.println(str);
				tstr = scan.next();
				if(tstr.equals(str)) {
					System.out.println("변경할 비밀번호를 입력해 주세요");
					String modipwd = null;
					modipwd = scan.next();
					System.out.println("변경된 비밀번호:  "+modipwd);
					ucontrol.modifyPWD(userid,modipwd);
				}
				
			}else{
				System.out.println("아이디나 이름을 확인해 주세요");
			}
			initmenu();
			break;
		case 5:
			int deletecount = 0;
			while(true) {
				System.out.print("아이디를 입력하세요.");
				String deleteid;
				deleteid = scan.next();
				System.out.print("비밀번호를 입력하세요.");
				String deletepwd;
				deletepwd = scan.next();
				if(ucontrol.deleteUser(deleteid,deletepwd)) {	
					System.out.println("정상적으로 삭제 되었습니다.");			
					break;
				}else {
					System.out.println("아이디나 비밀번호를 확인해 주세요");
					deletecount++;
					if (deletecount > 5) {
						System.out.println("삭제 시도 횟수 5회를 초과해서 초기화면으로 이동합니다.");
						break;
					}
				}
			}
			initmenu();
			break;
		case 6:
			System.exit(1);
			break;
		default:
			System.out.println("올바른 입력값을 입력하세요.");
			initmenu();

			break;
		}
	}
}
