package com.kh.model.dao.MemberDao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.kh.model.vo.Member.Member;


//DAO(Data Access Object): DB에 직접적으로 접근해서 사용자의 요청에 맞는 sql문 실행후 결과 반환 (JDBC)
//							결과를 Controller로 다시 리턴
public class MemberDao {

	/*
	 * *JDBC용 객체 -Connection:DB에 연결정보를 담고있는 객체 -[Prepared]Statement :연결된 DB에 SQL문을
	 * 전달해서 실행하고, 결과를 받아내는 객체 -ResultSet :SELECT문 실행후 조회된 결과물들이 담겨있는 객체
	 * 
	 * 
	 * *JDBC과정(순서준요) 
	 * 1번째 JDBC driver 등록 :해당DBMS(오라클)가 제공하는 클래스 등록 
	 * 2번째 Connection생성 :
	 * 연결하고자 하는 db정보를 입력해서 해당DB와 연결하면서 생성 
	 * 3번쨰 Statement생성 : Connection 객체를 이용해서
	 * 생성(sql문 실행 및 결과받는 객체)
	 * 4번쨰 SQL문 전달하면서 실행:Statement 객체를 이용해서 sql문 실행 
	 * 5번빼 결과받기 >
	 * SELECT문 실행 =>ResultSet객체 (조회된 데이터들이 담겨있음) =>6_1번째) > DML문 실행 =>int(처리된 행수)
	 * 6_1번쨰 ResultSet에 담겨있는 데잍터들을 하나씩 하나씩 뽑아서 vo객체에 차근차근 옮겨담기[+ArrayList에 담아주기]
	 * 6_2번쨰 트랜잭션 처리 (성공했다면 coomit,실패했다면 rollback 실행) 
	 * 7번쨰 다 사용한 jdbc용 객체들 반듯이 자원
	 * 반납(close) =>생성된 역순으로
	 *
	 */
	/**
	 * 사용자가 입력한 정보들을 db에 추가시켜주는 메소드
	 * @param m:사용자가 입력한 값들이 담겨있는member객체
	 * @return
	 */
	public int insertMember(Member m) {


		//insert문 =>처리된 행수(int)=>트랜잭션 처리

		//필요한 변수들 먼저세팅
		int result =0;//처리된 결과 (처리된행수)를 받아줄 변수
		Connection conn=null;//연결된 db의 연결정보를 담는 객체
		Statement stmt =null;//완성된 sql문 전달해서 곧바로 실행 후 결과를 받는 객체

		//실행할 sql문
		//		INSERT INTO MEMBER
		//		VALUES(SEQ_USERNO.NEXTVAL, 'user01', 'pass01', '홍길동', null, 23, 'user01@iei.or.kr', 
		//									'01022222222', '부산', '등산, 영화보기', '2021-08-02');
		String sql ="INSERT INTO MEMBER VALUES(SEQ_USERNO.NEXTVAL,"
				+"'"+m.getUserId()+"',"
				+"'"+m.getUserPw()+"',"
				+"'"+m.getUserName()+"',"
				+"'"+m.getGender()+"',"
				+m.getAge()+","
				+"'"+m.getEmail()+"',"
				+"'"+m.getPhone()+"',"
				+"'"+m.getAddress()+"',"
				+"'"+m.getHobby()+"',SYSDATE)";


		try {
			//1)jdbc driber등록
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//2)Connection객체생성 =>db연결
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "JDBC", "JDBC");

			// 3) Statement객체 생성
			stmt = conn.createStatement();
			// 4~5)sql문 전달 하면서 실행후 결과받기(처리된 행수)
			result = stmt.executeUpdate(sql);
			//6)트랜잭션 처리
			if(result>0) {
				conn.commit();
			}else {
				conn.rollback();
			}

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			//7)다쓴 jdbc객체들 반환
			try {
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		return result;
	}


	public ArrayList<Member> selectList() {
		//select문 (여러행 조회)=>ResultSet객체 =>ArrayList<Member>에 담기

		//필요한 변수들 세팅

		ArrayList<Member>list =new ArrayList<>();//비어있는 상태

		ResultSet rset =null;
		Statement stmt =null;
		Connection conn =null;//select문 실행시 조회된 결과값들이 최초로 담기는 객체

		//실행할 sql
		String sql ="SELECT * FROM MEMBER";
		try {
			//1)jdbc driber등록
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//2)Connection객체생성 =>db연결
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "JDBC", "JDBC");

			stmt =conn.createStatement();

			rset =stmt.executeQuery(sql);

			while(rset.next()) {
				Member m =new Member();
				m.setUserNO(rset.getInt("USERNO"));
				m.setUserId(rset.getString("USERID"));
				m.setUserPw(rset.getString("USERPWD"));
				m.setUserName(rset.getString("USERNAME"));
				m.setGender(rset.getString("GENDER"));
				m.setAge(rset.getInt("AGE"));
				m.setEmail(rset.getString("EMAIL"));
				m.setPhone(rset.getString("PHONE"));
				m.setAddress(rset.getString("ADDRESS"));
				m.setHobby(rset.getString("HOBBY"));
				m.setEnrollDate(rset.getDate("ENROLLDATE"));

				list.add(m);
			}


		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			//7)다쓴 jdbc객체들 반환
			try {
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


		}
		return list;
	}
	public Member selectByUserId(String userId) {
		//select문(한개) =>ResultSet rorcp =>Member객체
		ResultSet rset =null;
		PreparedStatement pstmt =null;
		Connection conn =null;
		Member m =null;

		String sql = "SELECT * FROM MEMBER WHERE USERID = ?" ;
		
		try {
			//1)jdbc driber등록
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//2)Connection객체생성 =>db연결
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "JDBC", "JDBC");

			pstmt =conn.prepareStatement(sql);
///->여기부터 중요 
			pstmt.setString(1,userId);
			
			rset =pstmt.executeQuery();

			while(rset.next()) {
				m =new Member();
				m.setUserNO(rset.getInt("USERNO"));
				m.setUserId(rset.getString("USERID"));
				m.setUserPw(rset.getString("USERPWD"));
				m.setUserName(rset.getString("USERNAME"));
				m.setGender(rset.getString("GENDER"));
				m.setAge(rset.getInt("AGE"));
				m.setEmail(rset.getString("EMAIL"));
				m.setPhone(rset.getString("PHONE"));
				m.setAddress(rset.getString("ADDRESS"));
				m.setHobby(rset.getString("HOBBY"));
				m.setEnrollDate(rset.getDate("ENROLLDATE"));

				//	m = new Member(rset.getInt("USERNO"),rset.getString("USERID"),.))이런식도가능
			}



		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			//7)다쓴 jdbc객체들 반환
			try {
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


		}
		return m;
	}
	public  ArrayList<Member> selectByUserName(String keyword) {
		ArrayList<Member>list =new ArrayList<>();//비어있는 상태

		ResultSet rset =null;
		PreparedStatement pstmt =null;
		Connection conn =null;//select문 실행시 조회된 결과값들이 최초로 담기는 객체

		//실행할 sql
		String sql = "SELECT * FROM MEMBER WHERE USERNAME LIKE ?" ;
		try {
		/*	
			try {
				//1)jdbc driber등록
				Class.forName("oracle.jdbc.driver.OracleDriver");
				//2)Connection객체생성 =>db연결
				conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "JDBC", "JDBC");

				pstmt =conn.prepareStatement(sql);
	///->여기부터 중요 
				pstmt.setString(1,userId);
				
				rset =pstmt.executeQuery();
		*/	
			
			//1)jdbc driber등록
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//2)Connection객체생성 =>db연결
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "JDBC", "JDBC");

			pstmt =conn.prepareStatement(sql);
			pstmt.setString(1,"%"+keyword+"%");

			rset =pstmt.executeQuery();

			while(rset.next()) {
				Member m =new Member();
				m.setUserNO(rset.getInt("USERNO"));
				m.setUserId(rset.getString("USERID"));
				m.setUserPw(rset.getString("USERPWD"));
				m.setUserName(rset.getString("USERNAME"));
				m.setGender(rset.getString("GENDER"));
				m.setAge(rset.getInt("AGE"));
				m.setEmail(rset.getString("EMAIL"));
				m.setPhone(rset.getString("PHONE"));
				m.setAddress(rset.getString("ADDRESS"));
				m.setHobby(rset.getString("HOBBY"));
				m.setEnrollDate(rset.getDate("ENROLLDATE"));

				list.add(m);
			}


		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			//7)다쓴 jdbc객체들 반환
			try {
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	
}
		

		return list;
}
	public int deleteMember(String userId) {
		//delete문 =처리된 행수 =트랜잭션처리
		int result =0;
		Connection conn =null;
		Statement stmt =null;
		String sql ="DELLOW FROM MEMBER WHERE USERID = '"+userId+"'";
		
		try {
			//1)jdbc driber등록
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//2)Connection객체생성 =>db연결
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "JDBC", "JDBC");

			// 3) Statement객체 생성
			stmt = conn.createStatement();
			// 4~5)sql문 전달 하면서 실행후 결과받기(처리된 행수)
			result = stmt.executeUpdate(sql);
			//6)트랜잭션 처리
			if(result>0) {
				conn.commit();
			}else {
				conn.rollback();
			}

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			//7)다쓴 jdbc객체들 반환
			try {
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		return result;
	}

}

