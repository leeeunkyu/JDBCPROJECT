package Util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import dto.Users;

public class TransferExcel {
	private static final int KEY_SIZE = 128;
    private static final int ITERATION_COUNT = 10000;
    private static final String IV = "F27D5C9927726BCEFE7510B1BDD3D137";
    private static final String SALT = "3FF2EC019C627B945225DEBAD71A01B6985FE84C95A70EB132882F88C0A59A55";
    private static final String PASSPHRASE = "passPhrase passPhrase aes encoding algorithm";
 
    public String getEncrypt(String plaintext) {
    	   AesUtil util = new AesUtil(KEY_SIZE, ITERATION_COUNT);
           String encrypt = util.encrypt(SALT, IV, PASSPHRASE, plaintext);
           String decrypt = util.decrypt(SALT, IV, PASSPHRASE, encrypt);
           return encrypt;
    }

	public Users transfer(Users user) {
		try {
			FileInputStream fis=new FileInputStream("C:\\Users.xlsx");
			XSSFWorkbook workbook=new XSSFWorkbook(fis);
			AesUtil util = new AesUtil(KEY_SIZE, ITERATION_COUNT);
			ArrayList<String> arr2 = new ArrayList<>();
			ArrayList<String> arrsha = new ArrayList<>();
			arr2.add(user.getUser_id());
			arr2.add(user.getUser_pwd());
			arr2.add(user.getUser_name());
			arr2.add(user.getGender());
			arr2.add(user.getUser_signup());			
			XSSFSheet sheet=workbook.getSheetAt(0);
			//2차는 sheet생성
			int rows=sheet.getPhysicalNumberOfRows();
		//	System.out.println(rows);
			//엑셀의 행
			XSSFRow row=null;
			//엑셀의 셀
			XSSFCell cell=null;			

			//출력 row 생성
			//System.out.println(sheet.getLastRowNum());
			row = sheet.createRow(rows+1);
			System.out.println(rows+1);
			for (int i = 0; i < arr2.size(); i++) {
				String encrypt = util.encrypt(SALT, IV, PASSPHRASE, arr2.get(i));
				String decrypt = util.decrypt(SALT, IV, PASSPHRASE, encrypt);

//				System.out.println(arr2.get(i));
				//1차로 workbook을 생성
//				System.out.println("문자열 : " + arr2.get(i) );
//				System.out.println("암호화 : " + encrypt);
//				System.out.println("복호화 : " + decrypt);
//				System.out.println(encrypt.getBytes().length);
				
				if(i==0) {
					arrsha.add(arr2.get(i));
					row.createCell(i).setCellValue(arr2.get(i));
				}
				else {
					arrsha.add(encrypt);
					row.createCell(i).setCellValue(encrypt);
				}
			//	System.out.println(encrypt);
			}
				//26비트
			//	System.out.println("엑셀파일생성성공");
				user.setUser_pwd(arr2.get(0));
				user.setUser_pwd(arrsha.get(1));
				user.setUser_name(arrsha.get(2));
				user.setGender(arrsha.get(3));
				user.setUser_signup(arrsha.get(4));
			
			//출력 cell 생성
							
			FileOutputStream fileoutputstream=new FileOutputStream("C:\\Users.xlsx");
			//파일을 쓴다
			workbook.write(fileoutputstream);
			//필수로 닫아주어야함
			fileoutputstream.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user;
		

	}
}
