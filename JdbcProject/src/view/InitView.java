package view;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Locale;
import java.util.Scanner;
import java.util.regex.Pattern;

import Util.TransferExcel;
import controller.UserController;
import dto.Users;

public class InitView {
	Scanner scan;
	DateFormat df;
	UserController ucontrol;
	LoginView login;
	TransferExcel te;
	public InitView() {
		te = new TransferExcel();
		ucontrol = new UserController();
		scan = new Scanner(System.in);
		initmenu();
	}
	public static void main(String[] args) {
		InitView iv = new InitView();
	}
	private void initmenu() {
		String choiceNum = null;
		System.out.println("------------------------------");
		System.out.println("1. ȸ������ :");
		System.out.println("2. �α��� :");
		System.out.println("3. ���̵� ã�� :");
		System.out.println("4. ��й�ȣã�� :");	
		System.out.println("5. ȸ�� Ż��");
		System.out.println("6. �ý��� ���� :");			
		System.out.println("------------------------------");
		String pattern="^[1-6]+";
		choiceNum = scan.next();
		if (Pattern.matches(pattern,choiceNum)) {
			choice(Integer.parseInt(choiceNum));
		}else{
			System.out.println("1~-6������ ���ڸ� �Է��� �ּ���.");
			initmenu();
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
			System.out.println("----------ȸ������ �޴�------------");
			System.out.print("���̵� �Է� �ϼ���.");
			while(true) {
				userid = scan.next();
				if(ucontrol.check(userid)) {
					System.err.println("�ߺ��Ǵ� ���̵� �ֽ��ϴ�.\n�ٸ� ���̵� �Է��ϼ���.");
				}else {
					System.out.println("�ߺ��Ǵ� ���̵� �����ϴ�. ����ϼŵ� �����ϴ�.");
					break;
				}
			}
			System.out.print("��й�ȣ�� �Է� �ϼ���.");
			userpwd = scan.next();
			System.out.print("�̸��� �Է� �ϼ���.");
			username = scan.next();
			System.out.print("����� ������ �Է� �ϼ���.");
			gender = scan.next();
			df = DateFormat.getDateTimeInstance(0, 1,Locale.KOREA);
			result = ucontrol.add(te.transfer(new Users(userid,userpwd,username,gender,df.format(new Date()))));
			if(result == 0) {
				System.err.println("ȸ������ �������� ������ �־����ϴ�. �ٽ� �õ��� �ֽñ� �ٶ��ϴ�.");
			}
			initmenu();
			break;
		case 2:
			while(true) {
				System.out.println("���̵� �Է��ϼ���.");
				userid = scan.next();
				System.out.println("��й�ȣ�� �Է��ϼ���.");
				userpwd = scan.next();
				if(ucontrol.login(userid,userpwd)) {
					System.out.println("�������� �α���");
					login = new LoginView(userid);
					login.BorderMenu();
					break;
				}else {
					System.out.println("���̵� ��й�ȣ�� Ȯ���ϼ���.");
				}
			}
			initmenu();
			break;
		case 3:
			while(true) {
				System.out.println("�̸��� �Է��ϼ���.");
				String id;
				username = scan.next();
				id = ucontrol.selectID(username);
				if (id != null) {
					System.out.println("ȸ������ ���̵��:  "+id+ "�Դϴ�.");
					break;
				}
				System.out.println("�ùٸ� �̸��� �Է��ϼ���.");
			}
			initmenu();
			break;	
		case 4:
				
			break;
		case 5:
			while(true) {
				System.out.print("���̵� �Է��ϼ���.");
				String deleteid;
				deleteid = scan.next();
				System.out.print("��й�ȣ�� �Է��ϼ���.");
				String deletepwd;
				deletepwd = scan.next();
				if(ucontrol.deleteUser(deleteid,deletepwd)) {	
					System.out.println("���������� ���� �Ǿ����ϴ�.");			
					break;
				}
				System.out.println("���̵� ��й�ȣ�� Ȯ���� �ּ���");
			}
			break;
		case 6:
			System.exit(1);
			break;
		default:
			System.out.println("�ùٸ� �Է°��� �Է��ϼ���.");
			initmenu();

			break;
		}
	}
}
