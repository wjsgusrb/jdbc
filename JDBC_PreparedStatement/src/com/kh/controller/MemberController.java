package com.kh.controller;

import java.util.ArrayList;

import com.kh.model.dao.MemberDao.MemberDao;
import com.kh.model.vo.Member.Member;
import com.kh.view.MemberMenu;

public class MemberController {

	/**
	 * 
	 * @param userId
	 * @param userPw
	 * @param userName
	 * @param gender
	 * @param age
	 * @param email
	 * @param phone
	 * @param address
	 * @param hobby
t	 */
	public void insertMember(String userId, String userPw, String userName, String gender, String age, String email,
			String phone, String address, String hobby) {
		// TODO Auto-generated method stub

		// view로부터 전달받은 값을 바로 dao쪽으로 전달x
		// 어딘가(Member객체(vo))에 닮아서 전달

		// 방법1) 기본생성자로 생성한 후에 각 필드에 setter매ㅐ소드를 통해서 일일히 담는 방법
		// 방법2) 매게변수 생성자로 생성과 동시에
		Member m = new Member(userId, userPw, userName, gender, Integer.parseInt(age), email, phone, address, hobby);
		System.out.print(m);
		
	int result=	new MemberDao().insertMember(m);
	if(result>0) {//insert가 성공
		new MemberMenu().displaySuccess("성공적으로 회우너이 추가 되었습니다.");
	}else {//insert가 실패
		new MemberMenu().displayFail("회원추가를 실패하였습니다..");
	}
	}
	
	
	public void selectList() {
		ArrayList<Member> list =new MemberDao().selectList();
		
		if(list.isEmpty()) {//list가 비어있을 경우
			new MemberMenu().displayNoData("전체 조회 결과가 업슷ㅂ니다.");
			
		}else {
			new MemberMenu().displayMemberList(list);
		}
		
		
	}
	/**
	 * 사용자의 아이디로 회원 검색 요청을 처리해주는 메소드
	 * @param userId :사용자가 입력한 검색하고자하는 아이디
	 */
	public void selectByUserId(String userId) {
	Member m =	new MemberDao().selectByUserId(userId);
	
	if(m ==null) {//검색 결과가 없는 경우
		new MemberMenu().displayNoData(userId+"에 해당하는 조회결과가 없습니다.");
	}else {//검색결과가 있는경우
		new MemberMenu().displayMember(m);
	}
	
	}
	/**
	 * 이름으로 키워드 검색 요청시 처리해주는 메소드
	 * @param keyword :사용자가 입력할 원하는 키워드명
	 */
	public void selectByUserName(String keyword) {
		
		ArrayList<Member>list = new MemberDao().selectByUserName(keyword);
		if(list.size() >0) {//검색 결과가 없는 경우
			new MemberMenu().displayMemberList(list);
		}else {//검색결과가 있는경우
			
			new MemberMenu().displayNoData(keyword+"로 해당하는 조회결과가 없습니다.");
		}
		
		}
	public void deleteMember(String userId) {
		//new MemberDao//쓰다말았음...
	}
		
	}

