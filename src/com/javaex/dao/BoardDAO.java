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
	
	
	public void update(BoardVO vo) {
		// 0. import java.sql.*;
		connect();
		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			String query = "update board " + 
						   "set title = ? , content = ? " + 
						   "where no = ? ";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContent());
			pstmt.setInt(3, vo.getNo());
			int result = pstmt.executeUpdate();
			
			// 4.결과처리
			System.out.println("처리 결과 : " + result);
			
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			close();
		}
	}
	
	
	public int delete(int getNo) {
		// 0. import java.sql.*;
		connect();
		int result = 0;
		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			String query = "delete from board where no = ? ";
			pstmt = conn.prepareStatement(query); 
			pstmt.setInt(1, getNo);
			result = pstmt.executeUpdate();
			
			// 4.결과처리	
			System.out.println("처리 결과 : " + result);
			
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			close();
		}
		return result;
	}
	
	
	public void boardHit(int getNo) {
		// 0. import java.sql.*;
		connect();
		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			String query = "update board " + 
						   "set hit = hit + 1 " + 
						   "where no = ? ";
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
	
	
	//view.jsp 를 위한 게시물 검색
	public BoardVO select(int getno) {
		// 0. import java.sql.*;
		connect();
		BoardVO vo = null;
		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			String query = "select b.title " +
								", b.content " + 
								", b.user_no " + 
						   "from users u, board b " + 
						   "where u.no = b.user_no and b.no= ? ";
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, getno);
			rs = pstmt.executeQuery();
			
			
			// 4.결과처리
			while(rs.next()) {
				vo = new BoardVO();
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
	
	
	//0)
	//list.jsp를 위한 게시물 리스트 검색
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
								 		"order by reg_date desc, no desc)) "+
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
	//전체 게시물수
	public int boardCount() {
		// 0. import java.sql.*;
		connect();
		int count=-1; //-1로 설정하여 쿼리가 제대로 실행 되었는지 확인
		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			String query = "select count(no) no " + 
						   "from board";
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
	
	
	//1)
	//제목으로 검색
	public List<BoardVO> t_SearchList(int min, int max, String keyWord) {
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
								 		"and b.title like ? " +
								 		"order by reg_date desc, no desc)) "+
							"where rn>? and rn<=?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, keyWord);
			pstmt.setInt(2, min);
			pstmt.setInt(3, max);
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
	//제목으로 검색한 게시물 수
	public int t_SearchBoardCount(String keyWord) {
		// 0. import java.sql.*;
		connect();
		int count=-1; //-1로 설정하여 쿼리가 제대로 실행 되었는지 확인
		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			String query = "select count(no) no " + 
						   "from board " + 
						   "where title like ?";
			pstmt = conn.prepareStatement(query); 
			pstmt.setString(1, keyWord);
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
	
	
	//2)
	//제목+내용으로 검색
	public List<BoardVO> tc_SearchList(int min, int max, String keyWord) {
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
								 	    "and (b.title like ? or b.content like ?)" +
								 		"order by reg_date desc, no desc)) "+
							"where rn>? and rn<=?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, keyWord);
			pstmt.setString(2, keyWord);
			pstmt.setInt(3, min);
			pstmt.setInt(4, max);
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
	//제목+내용으로 검색한 게시물 수
	public int tc_SearchBoardCount(String keyWord) {
		// 0. import java.sql.*;
		connect();
		int count=-1; //-1로 설정하여 쿼리가 제대로 실행 되었는지 확인
		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			String query = "select count(no) no " + 
						   "from board " +
					       "where title like ? or content like ?";
			pstmt = conn.prepareStatement(query); 
			pstmt.setString(1, keyWord);
			pstmt.setString(2, keyWord);
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

	
	//3))
	//작성자명으로 검색
	public List<BoardVO> un_SearchList(int min, int max, String keyWord) {
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
								 	    "and u.name like ? " +
								 		"order by reg_date desc, no desc)) "+
							"where rn>? and rn<=?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, keyWord);
			pstmt.setInt(2, min);
			pstmt.setInt(3, max);
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
	//작성자명으로 검색한 게시물 수
	public int un_SearchBoardCount(String keyWord) {
		// 0. import java.sql.*;
		connect();
		int count=-1; //-1로 설정하여 쿼리가 제대로 실행 되었는지 확인
		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			String query = "select count(b.no) no " +
						   "from board b, users u " +
						   "where b.user_no = u.no and u.name like ?";
			pstmt = conn.prepareStatement(query); 
			pstmt.setString(1, keyWord);
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
