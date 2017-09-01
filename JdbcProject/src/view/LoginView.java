package view;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Scanner;

import controller.BorderController;
import dto.Borders;

public class LoginView {
	Scanner bscan;
	String userid;
	DateFormat df;
	BorderController bcontrol;
	ArrayList<Borders> ab;
	LogView lv;
	public LoginView() {
		
	}
	public LoginView(String userid) {
		bscan = new Scanner(System.in);
		bcontrol = new BorderController();
		lv = new LogView(userid);
		this.userid = userid;
	}
	public void BorderMenu() {
		int num = 0;
		System.out.println("-------------------------------");
		System.out.println("1.�Խù� �ۼ�");
		System.out.println("2.�Խù� ����Ʈ ����");		
		System.out.println("3.������ �� ����");
		System.out.println("4.���� �ۼ��� �Խù� ����");
		System.out.println("5.���� �ۼ��� �Խù� ����");		
		System.out.println("6.�α� ����");
		System.out.println("7.�ڷΰ��� ");
		System.out.println("-------------------------------");
		num = bscan.nextInt();
		choiceBorder(num);
	}
	public int indexCount() {
		int index=bcontrol.selectIndex();
		return index;
	}
	public void choiceBorder(int num) {
		String head = null;
		String body = null;
		df = DateFormat.getDateInstance(0, Locale.KOREA);
		switch (num) {
		case 1:
			System.out.println("-------------------------------");
			System.out.println("1.�Խù� ������ �Է��ϼ���. ");
			head = bscan.next();
			System.out.println("2.�Խù� ������ �Է��ϼ���. ");
			body = bscan.next();
			System.out.println("-------------------------------");
			System.out.println(bcontrol.add(new Borders(userid, indexCount(), head, body, 0, df.format(new Date()))));
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
			int index_num = 0;
			System.out.println("-------------------------------");
			System.out.print("�� ��ȣ�� ������ �ּ���. ");
			index_num = bscan.nextInt();
			System.out.println(index_num+"�� �Խñ�");
			System.out.println(bcontrol.selectOne(index_num).toString());
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
			int delete_num = 0;
			String delete_pwd = null;
			System.out.println("-------------------------------");
			System.out.print("�� ��ȣ�� ������ �ּ���. ");
			delete_num = bscan.nextInt();
			System.out.println(delete_num+"�� �Խñ� ����");
			System.out.println("-------------------------------");
			System.out.println("ȸ���� ��й�ȣ�� �Է��� �ּ���.");
			delete_pwd = bscan.next();
			System.out.println("-------------------------------");

			if (bcontrol.deleteOne(delete_num,delete_pwd)) {
				System.out.println("������ �Ϸ� �Ǿ����ϴ�.");
				BorderMenu();
			}
			System.out.println("��й�ȣ�� Ȯ���� �ּ���.");
			BorderMenu();
			break;
		case 6:
			lv.LogMenu();
			BorderMenu();
			break;
		case 7:
			break;
		default:
			break;
		}
	}

}
