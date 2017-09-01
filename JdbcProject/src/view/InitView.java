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
			System.out.println("1. ȸ������ :");
			System.out.println("2. �α��� :");
			System.out.println("3. ���̵� ã�� :");
			System.out.println("4. ��й�ȣã�� :");	
			System.out.println("5. �ý��� ���� :");			
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
			System.out.println("----------ȸ������ �޴�------------");
			while(true) {
				System.out.print("���̵� �Է� �ϼ���.");
				userid = scan.next();
				if(ucontrol.check(userid)) {
					System.err.println("�ߺ��Ǵ� ���̵� �ֽ��ϴ�. �ٸ� ���̵� �Է��ϼ���.");
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
			df = DateFormat.getDateInstance(0, Locale.KOREA);		
			result = ucontrol.add(new Users(userid,userpwd,username,gender,df.format(new Date())));
			if(result == 0) {
				System.err.println("ȸ������ �������� ������ �־����ϴ�. �ٽ� �õ��� �ֽñ� �ٶ��ϴ�.");
			}
			initmenu();
			break;
		case 2:
			System.out.println("���̵� �Է��ϼ���.");
			userid = scan.next();
			System.out.println("��й�ȣ�� �Է��ϼ���.");
			userpwd = scan.next();
			if(ucontrol.login(userid,userpwd)) {
				System.out.println("�������� �α���");
				login = new LoginView(userid);
				login.BorderMenu();
			}else {
				System.out.println("���̵� ��й�ȣ�� Ȯ���ϼ���.");
			}
			break;
		case 3:
			System.out.println("�̸��� �Է��ϼ���.");
			String id;
			username = scan.next();
			id = ucontrol.selectID(username);
			System.out.println("ȸ������ ���̵��:  "+id+ "�Դϴ�.");
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
