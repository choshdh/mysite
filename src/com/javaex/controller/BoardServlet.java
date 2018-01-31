package com.javaex.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.javaex.dao.BoardDAO;
import com.javaex.util.WebUtil;
import com.javaex.vo.BoardVO;
import com.javaex.vo.UserVO;


@WebServlet("/board")
public class BoardServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("board 진입");
		request.setCharacterEncoding("UTF-8");
		
		System.out.println("페이지를 요청한 ip 주소 : " + request.getLocalAddr());
		System.out.println("접근 포트 : " + request.getLocalPort());
		System.out.println("ip 주소 지역 위치 : " + request.getLocale());
		
		HttpSession session = request.getSession();
		UserVO vo = (UserVO) session.getAttribute("authUser");
		
		BoardDAO dao = new BoardDAO(); //정상 로그인 상태일 시에 사용할 dao 객체를만든다
		String actionName = request.getParameter("a"); //사용자의 요청 action 값을 받아온다.
		
		//1.리스트 보기 , 2.자세히 보기
		if(actionName.equals("list")) { //게시물 리스트 페이지 선택 요청시
			System.out.println("list 진입");
			
			//페이징 세팅 값 2가지
			//1.
			int pageSize = 10; // 한페이지당 게시물 수
			//2.
			//선택한 페이지 번호를 가운데 정렬 할 것이기 때문에 무조건 pageBundleSize 를 홀수로 지정
			int pageBundleSize = 9; // 표시할 페이지 개수
			
			
			//--)이하 : 페이징 세팅 값을 설정하면 자동 세팅 되도록 설계
			
			//--0)사용자가 요청한 검색 스펙 받기
			//처음 list.jsp 페이지가 열릴때는 사용자가 페이지 넘버를 선택하지 않은 상태라 가장 최신 페이지인 1로 초기화
			int selectPage=1;
			try {
				selectPage = Integer.parseInt(request.getParameter("selectPage")); //사용자가 선택한 페이지 넘버를 가져온다.
			}catch (NumberFormatException e){
				System.out.println("사용자가 선택한 페이지가 없어서 선택 페이지 1로 사용");
			}
			System.out.println("selectPage : " + selectPage);
			String keyWord = request.getParameter("kwd"); //검색 키워드를 받아온다.
			String searchType = request.getParameter("searchType"); //검색 타입을 받아온다.
			if(keyWord==null) { //키워드가 null이면 "" 로 세팅
				keyWord="";
			}
			
			
			//--1)DB 검색 시작
			//DB 검색결과 게시물 개수를 담을 변수 선언
			int boardCount;	
			//DB 검색결과 게시물 리스트를 담을 변수 선언
			List<BoardVO> l ;
			
			//DAO 객체를 이용해서 사용자가 클릭한 페이지에 해당하는 게시물들을 가져온다
			//만약 2페이지를 누르면 11번~20번까지 게시물을 가져와야 하기때문에 (사용자가 선택한 페이지 번호에서 -1)을 한다.
			if(keyWord.equals("")) {
				boardCount = dao.boardCount();
				System.out.println("전체 게시물 수 : " + boardCount);
				l = dao.selectList((selectPage-1)*pageSize,selectPage*pageSize); 
			}else {
				String copyKeyWord = keyWord; //키워드 가공을 위한 복사
				copyKeyWord.replace(" ", "%"); //띄어 쓰기 한곳을 전부 %로 변경
				copyKeyWord = "%".concat(keyWord).concat("%"); //DB 검색을 위해서 앞뒤에 %를 붙이도록 변경
				if(searchType==null) {
					System.out.println("검색 타입이 없습니다.");
					boardCount = 0;
					l = new ArrayList<BoardVO>();
					WebUtil.redirect(request, response, "/mysite/board?a=list");
				}else if(searchType.equals("title")) {
					System.out.println("검색 타입 : 제목");
					System.out.println("키워드 확인 : " + copyKeyWord);
					boardCount = dao.t_SearchBoardCount(copyKeyWord);
					System.out.println("키워드 검색 일치 게시물 수 : " + boardCount);
					l = dao.t_SearchList((selectPage-1)*pageSize,selectPage*pageSize,copyKeyWord);
				}else if(searchType.equals("title,content")) {
					System.out.println("검색 타입 : 제목+내용");
					System.out.println("키워드 확인 : " + copyKeyWord);
					boardCount = dao.tc_SearchBoardCount(copyKeyWord);
					System.out.println("키워드 검색 일치 게시물 수 : " + boardCount);
					l = dao.tc_SearchList((selectPage-1)*pageSize,selectPage*pageSize,copyKeyWord);
				}else if(searchType.equals("username")) {
					System.out.println("검색 타입 : 작성자명");
					System.out.println("키워드 확인 : " + copyKeyWord);
					boardCount = dao.un_SearchBoardCount(copyKeyWord);
					System.out.println("키워드 검색 일치 게시물 수 : " + boardCount);
					l = dao.un_SearchList((selectPage-1)*pageSize,selectPage*pageSize,copyKeyWord);
				}else {
					System.out.println("잘못된 검색 타입 입니다.");
					boardCount = 0;
					l = new ArrayList<BoardVO>();
					WebUtil.redirect(request, response, "/mysite/board?a=list");
				}
			}
			//DB 검색 결과로 얻은 게시물 개수를 이용하여 몇페이지를 만들수 있는지 계산
			int pageCount = ((boardCount-1)/pageSize)+1; // 페이지 총 갯수
			 // 게시물이 10개 이하일때도 1페이지라도 무조건 나와야 하기 때문에 페이지 개수를 구할때 올림 기능을 만들어 내야해서 무조건 결과에 1을 더해줬는데
			 // 게시물이 10개가 될때는 올림 해줄 필요가없어서 2페이지라는 오류가 발생하기 때문에 강제적으로 게시물 수에서 -1을 해주어 pageSize 로 나누었다
			
			
			//--2)사용자에게 표시할 페이지의 정상 작동을 위한 페이지 이동 및 범위 관리 로직
			int movePage = (pageBundleSize/2)+1;//화살표 클릭시 몇페이지씩 옆으로 이동시킬 것인지 지정
			int minPage=1; // 페이지의 최소 범위 지정
			int maxPage=pageCount; // 페이지 최대 범위 지정
			
			//사용자가 선택한 페이지의 넘버가 페이지의 최소, 최대범위값을 벗어나는 것을 방지
			if(selectPage<minPage) {
				selectPage = minPage;
			}else if(selectPage>maxPage) {
				selectPage = maxPage;
			}	
			
			System.out.println("페이지 최소 최대 범위");
			System.out.println("minPage : " + minPage);
			System.out.println("maxPage : " + maxPage);
			
			//보여줄 페이지의 최소,최대 범위 지정
			int showMinPage = selectPage-(pageBundleSize/2); 
			int showMaxPage = selectPage+(pageBundleSize/2);
			
			System.out.println("표시할 페이지 범위");
			System.out.println("showMinPage : " + showMinPage);
			System.out.println("showMaxPage : " + showMaxPage);
			
			//보여줄 페이지가 최소,최대 범위를 벗어나는 것을 방지
			if(showMinPage<minPage) { // 보여줄 페이지의 범위가 페이지의 최소 범위를 벗어나면
				showMinPage = minPage;
				showMaxPage = (showMinPage-1)+pageBundleSize;
				System.err.println("표시할 페이지 범위가 페이지 최소 범위 초과 : 재설정");
				System.err.println("showMinPage : " + showMinPage);
				System.err.println("showMaxPage : " + showMaxPage);		
			}else if(showMaxPage>maxPage) { // 보여줄 페이지의 범위가 페이지의 최대 범위를 벗어나면
				if(maxPage<pageBundleSize) { //DB에 저장되어있는 데이터로 만들수 있는 리스트 크기가 페이지 묶음 크기보다 작으면
					showMinPage = minPage;
					showMaxPage = pageBundleSize;
					System.err.println("표시할 페이지 범위가 페이지 최대 범위 초과 : 재설정");
					System.err.println("showMinPage : " + showMinPage);
					System.err.println("showMaxPage : " + showMaxPage);	
				}else { //리스트 크기가 페이지 묶음 크기보다 작지 않을때
					showMaxPage = maxPage;
					showMinPage = (showMaxPage+1)-pageBundleSize;
					System.err.println("표시할 페이지 범위가 페이지 최대 범위 초과 : 재설정");
					System.err.println("showMinPage : " + showMinPage);
					System.err.println("showMaxPage : " + showMaxPage);		
				}
			}
																						   
			request.setAttribute("keyWord", keyWord);
			request.setAttribute("searchType", searchType);
			request.setAttribute("minPage", minPage);
			request.setAttribute("maxPage", maxPage);
			request.setAttribute("showMinPage", showMinPage);
			request.setAttribute("showMaxPage", showMaxPage);
			request.setAttribute("selectPage", selectPage);
			request.setAttribute("movePage", movePage);
			request.setAttribute("l", l);
			request.setAttribute("pageCount", pageCount);
			WebUtil.forward(request, response, "/WEB-INF/views/board/list.jsp");
			
		}else if(actionName.equals("view")){ //게시물 자세히 보기 요청시
			System.out.println("view 진입");
			int getNo = Integer.parseInt(request.getParameter("bno")); //사용자가 요청한 게시물의 번호를 받아와
			dao.boardHit(getNo); //조회수를 하나 올린다
			BoardVO bvo = dao.select(getNo); //그번호를 DAO 객체에 이용해서 게시물의 정보를 받아온다.
			request.setAttribute("bvo", bvo); //검색해온 게시물의 정보를 가지고 view.jsp 완성해 사용자에게 넘겨준다.
			WebUtil.forward(request, response, "/WEB-INF/views/board/view.jsp");
			
		}else {
			
			//로그인 상태라면
			if(vo!=null){
				//3.게시물 작성폼, 4.게시물 삽입 
				if(actionName.equals("write")){ //게시물 작성 폼 요청시
					System.out.println("write 진입");
					WebUtil.forward(request, response, "/WEB-INF/views/board/write.jsp"); //단순 작성폼 요청이기에 로그인만 되어있다면 그냥 페이지를 준다.
					
				}else if(actionName.equals("writing")){ //작성한 게시물 DB 삽입 요청시
					System.out.println("writing 진입");
					String title = request.getParameter("title"); //사용자가 작성한 title 을 받아온다.
					String content = request.getParameter("content"); //사용자가 작성한 content 를 받아온다.
					BoardVO bvo = new BoardVO(); //정보가 비어있는 게시물 객체를 하나 생성한다.
					bvo.setTitle(title); //비어있는 게시물의 제목에 사용자가 작성한 title 을 넣어준다.
					bvo.setContent(content); //비어있는 게시물의 내용에 사용자가 작성한 content 를 넣어준다
					bvo.setUserNo(vo.getNo()); //현재 로그인 되어있는 사용자의 번호를 받아와서 게시물을 작성한 사람의 번호라고 적어준다.
					dao.insert(bvo); //DAO 객체를 통해서 DB 에 작성된 게시물을 삽입한다.
					WebUtil.redirect(request, response, "/mysite/board?a=list"); // 사용자의 브라우저 새로고침으로 인한 insert 요청의 중복 발생을 방지 하기위하여 redirect 로 사용자의 브라우저 url 을 바꿔준다. 
					
				// 5.게시물 수정폼, 6.게시물 수정요청, 7.삭제 요청시 :따로 분리한 이유는 위의 select 과 insert 와는 다르게 DB에 이미 기록된 정보를 수정, 삭제 요청 하는 것이기 때문에 로그인한 유저와 해당 게시물의 작성자가 동일 한지 검사가 필요하다.
				}else if(actionName.equals("modify") || actionName.equals("modifying") || actionName.equals("delete")){
					int loginUserNo = vo.getNo(); //로그인유저의 번호를 받아오고
					int getBoardNo = Integer.parseInt(request.getParameter("bno")); // 어떤 글을 수정을 원하는지 번호를 받아온다.
					BoardVO bvo = dao.select(getBoardNo); //dao 객체를 이용해서 사용자가 선택한 게시글의 정보를 검색해온다.
					int boardUserNo = bvo.getUserNo(); //게시물 을 작성한 유저의 번호를 변수로 만든다
					if(loginUserNo == boardUserNo) { //로그인 한 유저와 게시물을 작성한 유저가 같은지 비교하고 같을때 실행 되게 만든다.
						if(actionName.equals("modify")){ //글 수정 폼 요청시
							System.out.println("modify 진입");
							request.setAttribute("bvo", bvo); //검색해온 게시물의 정보를 가지고 modify.jsp 완성해 사용자에게 넘겨준다.
							WebUtil.forward(request, response, "/WEB-INF/views/board/modify.jsp");
							
						}else if(actionName.equals("modifying")){ //수정한 글 DB에 업데이트 요청시
							System.out.println("modifying 진입");
							String title = request.getParameter("title"); //사용자가 작성한 수정한 게시글 제목을 받아온다.
							String content = request.getParameter("content"); //사용자가 작성한 수정한 게시글 내용을 받아온다.
							BoardVO updateVo = new BoardVO(); //업데이트에 사용할 게시물 객체를 생성한다.
							updateVo.setNo(getBoardNo); //비어있는 게시물 번호에 위에 아까 사용자가 선택한 게시물의 번호를 기입한다.
							updateVo.setTitle(title); //비어있는 게시물 제목에 사용자가 작성한 수정한 게시글 title 을 넣어준다.
							updateVo.setContent(content); //비어있는 게시물 내용에 사용자가 작성한 수정한 게시글 cotent 를 넣어준다.
							dao.update(updateVo); //DAO 객체를 이용해서 게시물을 update 한다.
							WebUtil.redirect(request, response, "/mysite/board?a=list"); // 사용자의 브라우저 새로고침으로 인한 update 요청의 중복 발생을 방지 하기위하여 redirect 로 사용자의 브라우저 url 을 바꿔준다. 
							
						}else if(actionName.equals("delete")){ //선택한 글 DB에서 삭제 요청시
							System.out.println("delete 진입");
							dao.delete(getBoardNo); //DAO 객체를 이용해서 해당 번호 게시물을 삭제한다.
							WebUtil.redirect(request, response, "/mysite/board?a=list"); // 사용자의 브라우저 새로고침으로 인한 delete 요청의 중복 발생을 방지 하기위하여 redirect 로 사용자의 브라우저 url 을 바꿔준다. 
						}	
					}
				}
			}else {
				System.out.println("잘못된 접근입니다.");
				WebUtil.redirect(request, response, "/mysite/board?a=list");
			}
			
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
