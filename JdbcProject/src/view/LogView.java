package view;

import java.util.ArrayList;
import java.util.Scanner;

import controller.LogController;
import dto.Logs;
/**
 * 사용자 로그를 보여주기위한 view페이지
 * @author kosta
 *
 */
public class LogView {
	Scanner scan;
	LogController lcontrl;
	String userid;
	public LogView() {
	}

	public LogView(String userid) {
		lcontrl = new LogController();
		scan = new Scanner(System.in);
		this.userid = userid;
	}
	/**
	 * 로그 기록을 보기위한 메뉴
	 */
	public void LogMenu() {
		int num = 0;
		System.out.println("------------로그화면-----------");
		System.out.println("1. 로그기록보기");
		System.out.println("2. 뒤로가기");
		System.out.println("-----------------------------");
		num = scan.nextInt();
		switch (num) {
		case 1:
			report();
			break;
		case 2:
			return;
		default:
			break;
		}
	}
	/**
	 * 보여지는 로그 기록들
	 */
	public void report() {
		ArrayList<Logs> al = new ArrayList<Logs>();
		al=lcontrl.selectLog(userid);
		System.out.println("-----------------------------");
		System.out.println(al);
		for (int i = 0; i < al.size(); i++) {
			al.get(i).toString();
		}
		System.out.println("-----------------------------");
	}
}
