package com.kh.view;

import java.util.ArrayList;
import java.util.Scanner;

import com.kh.controller.MemberController;
import com.kh.model.vo.Member.Member;


//view :사용자가 보게될 시각적인 요소(화면)출력및 입력
public class MemberMenu {
	
	private Scanner sc =new Scanner(System.in);
	
	private MemberController mc =new MemberController();
	
	
	/**
	 *  사용자가 보게될 첫 화면(메인화면)
	 */ 
	public void  mainMenu() {
		while(true) {
			System.out.println("\n==회우너관리 프로그램==");
			System.out.println("1 :회원추가");
			System.out.println("2 :회원전체조회");
			System.out.println("3 :회원 아이디 검색");
			System.out.println("4 :회원 이름으로 키워드 검색");
			System.out.println("5 :회원정보 변경");
			System.out.println("6 :회원탈퇴");
			System.out.println("0 :프로그램종료");
			
			System.out.println(">> 메뉴선택:");
			int menu =sc.nextInt();
			sc.nextLine();
			
			switch (menu) {
			case 1 :
				inputMember();
				break;
			case 2 :
				mc.selectList();
				break;
			case 3 :
				mc.selectByUserId(inputMemberId());
				break;
			case 4 :
			//String keyword =inputMemberName();
			//mc.selectByUserName(keyword);
				mc.selectByUserName(inputMemberName());
			
				break;
			case 5 :
				
				
				break;
			case 6 :
				//DELLOW FROM MEMBER WHERE USERID = '아이디'
				String userId =inputMemberId();
				//mc.deleteMember
				break;
			case 0 :
				System.out.println("이용해주셔서 감사용 프로그램도 종료용");
				return;
			default:
				System.out.println("메뉴를 자못입력 다시눌러볼래?");
			
			}
		}
			
	}

	/**
	 * 회원 추가창(서브화면) 즉,추가하고자 하는 회원의 정보를 입력받아 회원을 추가요청하는 창
	 */
	public void inputMember() {
		System.out.print("\n===회원추가===");
		
		System.out.print("아이디:");
		String userId =sc.nextLine();
		
		System.out.print("비밀번호:");
		String userPw =sc.nextLine();
		
		System.out.print("이름:");
		String userName =sc.nextLine();
		
		System.out.print("성별(m/f):");
		String gender =sc.nextLine().toUpperCase();
		
		System.out.print("나이:");
		String age =sc.nextLine();
		
		System.out.print("이메일:");
		String email =sc.nextLine();
		
		System.out.print("전화번호(-제외):");
		String phone =sc.nextLine();
		
		System.out.print("주소:");
		String address =sc.nextLine();
		
		System.out.print("취미(,로 연이어서 작성):");
		String hobby =sc.nextLine();
	//회원 추가 요청 ==> Controller 메서드 요청
		mc.insertMember(userId,userPw,userName,gender,age,email,phone,address,hobby);
	}
	
	
	public String inputMemberId() {
		System.out.print("\n회원아이디 입력");
		return sc.nextLine();
		
	}
	
	public void updateMember() {
		System.out.print("\n===회원정보변경");
		String userId = inputMemberId();
		
		
	
		
		
	}
	
	
	
	//--------------------------------응답화면-------------------------------------------------
	/***
	 * 서비스 요청 처리후 성공했을 경우 사용자가 보게될 응답화면
	 * @param message:객체별 성공메세지
	 */
	public void displaySuccess(String message) {
		System.out.print("서비스요청 성공"+ message);
	}
	/***
	 * 서비스 요청 처리후 실패했을 경우 사용자가 보게될 응답화면
	 * @param message:객체별 실패메세지
	 */
	public void displayFail(String message) {
		System.out.print("서비스요청 실패"+ message);
	}
	
	/**
	 * 조회 서비스 요청시 조회결과가 없을때 사용자가 보게될 응답화면
	 * @param message:조회결과에 대한 응답메세지
	 */
	public void displayNoData(String message) {
		System.out.print("\n"+ message);
	}
	/**
	 * 조회서비스 요청시 조회 결과가 여러행일 경우 사용자가 보게될 응답화면
	 * @param message:출력할 member들이 담겨있는 list
	 */
	public void displayMemberList(ArrayList<Member>list) {
		System.out.print("\n 조회된 테이턴ㄴ 다음과 같습니다");
		
		//for loop
		for(int i =0; i<list.size();i++) {	
			System.out.println(list.get(i));
		}
		
		//for each
//		for(Member m :list) {
//			System.out.println(m);
//		}
		
	}
	
	public void displayMember(Member m) {
		System.out.print("\n 조회된 테이턴ㄴ 다음과 같습니다");
		System.out.print(m);
	}
	
	
	public String inputMemberName() {
		System.out.print("\n회원이름(키워드) 입력");
		return sc.nextLine();
		
	}
	
}

