package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javaex.vo.BoardVO;

public class BoardDAO {
	
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	public void insert(BoardVO vo) {
		// 0. import java.sql.*;
		connect();
		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			String query = "insert into board values(seq_board_no.nextval, ? , ? , sysdate , 0 , ?)"; 
			pstmt = conn.prepareStatement(query); 
			pstmt.setString(1, vo.getTitle()); 
			pstmt.setString(2, vo.getContent());
			pstmt.setInt(3, vo.getUserNo());
			int result = pstmt.executeUpdate();
			
			// 4.결과처리
			System.out.println("처리 결과 : " + result);

		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			close();
		}
	}

//	public List<BoardVO> selectAll() {
//		// 0. import java.sql.*;
//		connect();
//		List<BoardVO> l = new ArrayList<BoardVO>();
//		try {
//			// 3. SQL문 준비 / 바인딩 / 실행
//			String query = "select b.no , b.title, to_char(b.reg_date, 'YYYY-MM-DD') reg_date, b.hit, b.user_no , u.name from users u, board b where u.no = b.user_no ";
//			pstmt = conn.prepareStatement(query);
//			rs = pstmt.executeQuery();
//			
//			// 4.결과처리
//			while(rs.next()) {
//				BoardVO vo = new BoardVO();
//				int no = rs.getInt("no");
//				String title = rs.getString("title");
//				String regDate = rs.getString("reg_date");
//				int hit = rs.getInt("hit");
//				int userNo = rs.getInt("user_no");
//				String name = rs.getString("name");
//				vo.setNo(no);
//				vo.setTitle(title);
//				vo.setRegDate(regDate);
//				vo.setHit(hit);
//				vo.setUserNo(userNo);
//				vo.setName(name);
//				l.add(vo);
//				System.out.println(vo.toString());
//			}
//			
//		} catch (SQLException e) {
//			System.out.println("error:" + e);
//			
//			
//		} finally {
//			close();
//		}
//		return l;
//	}
	
	
	public List<BoardVO> selectList(int min,int max) {
		// 0. import java.sql.*;
		connect();
		List<BoardVO> l = new ArrayList<BoardVO>();
		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			String query = "select rn "+
								 ",no "+
								 ",title "+
								 ",reg_date "+
								 ",hit "+
								 ",user_no "+
								 ",name "+
						   "from "+
								 "(select rownum rn "+
								 		",no "+
								 		",title "+
								 		",reg_date "+
								 		",hit "+
								 		",user_no "+
								 		",name "+
								 "from "+
								 		"(select b.no "+
								 			   ",b.title "+
								 			   ",to_char(b.reg_date, 'YYYY-MM-DD HH24:MI') reg_date "+
								 			   ",b.hit "+
								 			   ",b.user_no "+
								 			   ",u.name "+
								 		"from users u, board b "+
								 	    "where u.no = b.user_no "+
								 		"order by reg_date desc)) "+
							"where rn>? and rn<=?";
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, min);
			pstmt.setInt(2, max);
			rs = pstmt.executeQuery();
			
			// 4.결과처리
			while(rs.next()) {
				BoardVO vo = new BoardVO();
				int no = rs.getInt("no");
				String title = rs.getString("title");
				String regDate = rs.getString("reg_date");
				int hit = rs.getInt("hit");
				int userNo = rs.getInt("user_no");
				String name = rs.getString("name");
				vo.setNo(no);
				vo.setTitle(title);
				vo.setRegDate(regDate);
				vo.setHit(hit);
				vo.setUserNo(userNo);
				vo.setName(name);
				l.add(vo);
				System.out.println(vo.toString());
			}
			
		} catch (SQLException e) {
			System.out.println("error:" + e);
			
			
		} finally {
			close();
		}
		return l;
	}
	
	
	public BoardVO select(int getno) {
		// 0. import java.sql.*;
		connect();
		BoardVO vo = new BoardVO();
		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			String query = "select b.title, b.content, b.user_no from users u, board b where u.no = b.user_no and b.no= ? ";
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, getno);
			rs = pstmt.executeQuery();
			
			
			// 4.결과처리
			while(rs.next()) {
				String title = rs.getString("title");
				String content = rs.getString("content");
				int userNo = rs.getInt("user_no");
				
				vo.setNo(getno);
				vo.setTitle(title);
				vo.setContent(content);
				vo.setUserNo(userNo);
				
			}
				
		} catch (SQLException e) {
			System.out.println("error:" + e);
			
		} finally {
			close();
		}

		return vo;
	}
	
	
	
	public void update(BoardVO vo) {
		// 0. import java.sql.*;
		connect();
		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			String query = "update board set title = ? , content = ? where no = ? ";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContent());
			pstmt.setInt(3, vo.getNo());
			int result = pstmt.executeUpdate();
			
			// 4.결과처리
			System.out.println("처리 결과 : " + result);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("error:" + e);
			
		} finally {
			close();
		}
	}
	
	
	public int delete(int getNo) {
		// 0. import java.sql.*;
		connect();
		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			String query = "delete from board where no = ? ";
			pstmt = conn.prepareStatement(query); 
			pstmt.setInt(1, getNo);
			int result = pstmt.executeUpdate();
			
			// 4.결과처리	
			System.out.println("처리 결과 : " + result);
			
			return result;
		} catch (SQLException e) {
			System.out.println("error:" + e);
			return 0;
		} finally {
			close();
		}
	}
	
	
	public void boardHit(int getNo) {
		// 0. import java.sql.*;
		connect();
		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			String query = "update board set hit = hit + 1 where no = ? ";
			pstmt = conn.prepareStatement(query); 
			pstmt.setInt(1, getNo);
			int result = pstmt.executeUpdate();
			
			// 4.결과처리	
			System.out.println("처리 결과 : " + result);
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			close();
		}
	}
	
	public int boardCount() {
		// 0. import java.sql.*;
		connect();
		int count=0;
		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			String query = "select count(no) no from board";
			pstmt = conn.prepareStatement(query); 
			rs = pstmt.executeQuery();
			
			// 4.결과처리	
			while(rs.next()) {
				count = rs.getInt("no");
			}
			
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			close();
		}
		return count;
	}
	
	
	private void connect() {
		try {
			// 1. JDBC 드라이버 (Oracle) 로딩
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// 2. Connection 얻어오기
			String url = "jdbc:oracle:thin:@localhost:1521:xe"; 
			conn = DriverManager.getConnection(url, "webdb", "webdb");
		} catch (ClassNotFoundException e) {
			System.out.println("error: 드라이버 로딩 실패 - " + e);
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

	}

	
	private void close() {
		// 5. 자원정리

		try {
			if (rs != null) {
				rs.close();
			}

			if (pstmt != null) {
				pstmt.close();
			}

			if (conn != null) {
				conn.close();
			}

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
	}


	
	
}
