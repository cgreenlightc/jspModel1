package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.DBClose;
import db.DBConnection;
import dto.BbsDto;

public class BbsDao {

	private static BbsDao dao = null;
	
	private BbsDao() {
	//	DBConnection.initConnection();    // 만약에 로그인을 안해도 볼수 있는 사이트를 만들려고 한다면 dao 에 다 입력해줘야한다. 그게 아니라면 한곳에만 작성하면 된다.
	}
	
	public static BbsDao getInstance() {
		if(dao == null) {
			dao = new BbsDao();
		}
		return dao;
	}
	
	// back - end 만든다
	public List<BbsDto> getBbsList() {      // 필요한거 가져와도 되지만 일단은 다 가져온다.
		
		String sql = " select seq, id, ref, step, depth,"
				+ "           title, content, wdate, del, readcount "
				+ "    from bbs "
				+ "    order by ref desc, step asc "; // order by : 정렬할때 쓰는 solting // ref(역순), step(정순) 나중에 설명
		
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		List<BbsDto> list = new ArrayList<BbsDto>();
		
		try {
			conn = DBConnection.getConnection();
			System.out.println("1/4 getBbsList success");
			
			psmt = conn.prepareStatement(sql);
			System.out.println("2/4 getBbsList success");
			
			rs = psmt.executeQuery();
			System.out.println("3/4 getBbsList success");
			
			while(rs.next()) {
			
				BbsDto dto = new BbsDto(rs.getInt(1), 
										rs.getString(2), 
										rs.getInt(3),
										rs.getInt(4), 
										rs.getInt(5),
										rs.getString(6),
										rs.getString(7),
										rs.getString(8),
										rs.getInt(9),
										rs.getInt(10));
				
				list.add(dto);
			}
			System.out.println("4/4 getBbsList success");
			
		} catch (SQLException e) {
			System.out.println("getBbsList fail");
			e.printStackTrace();
		} finally {
			DBClose.close(conn, psmt, rs);
		}
		
		return list;
	}
	
	public List<BbsDto> getBbsSearchList(String choice, String search) {      // 검색창 하는 부분 매개변수를 만들어야해서 String 추가
		
		String sql = " select seq, id, ref, step, depth,"
				+ "           title, content, wdate, del, readcount "
				+ "    from bbs ";
		
		String searchSql = "";
		if(choice.equals("title")) {
			searchSql = " where title like '%" + search + "%'";  // sql 에서 특정단어 포함 하기 위해 like를 씀.
		}
		else if(choice.equals("content")) {
			searchSql = " where content like '%" + search + "%'";
		}
		else if(choice.equals("writer")) {
			searchSql = " where id='" + search + "'";   // 작성자는 id가 완전히 매칭 되어야 하기 떄문에 위에 title, content와는 다르다. 
		}
		sql += searchSql;
		
		sql	+= "    order by ref desc, step asc "; // 중간 부분에 search가 들어가야한다. 그래서 나눴음.
		
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		List<BbsDto> list = new ArrayList<BbsDto>();
		
		try {
			conn = DBConnection.getConnection();
			System.out.println("1/4 getBbsList success");
			
			psmt = conn.prepareStatement(sql);
			System.out.println("2/4 getBbsList success");
			
			rs = psmt.executeQuery();
			System.out.println("3/4 getBbsList success");
			
			while(rs.next()) {
			
				BbsDto dto = new BbsDto(rs.getInt(1), 
										rs.getString(2), 
										rs.getInt(3),
										rs.getInt(4), 
										rs.getInt(5),
										rs.getString(6),
										rs.getString(7),
										rs.getString(8),
										rs.getInt(9),
										rs.getInt(10));
				
				list.add(dto);
			}
			System.out.println("4/4 getBbsList success");
			
		} catch (SQLException e) {
			System.out.println("getBbsList fail");
			e.printStackTrace();
		} finally {
			DBClose.close(conn, psmt, rs);
		}
		
		return list;
	}
	
	// 페이지 나누기 첫작업
	// 글의 (총수)갯수 파악하기
	public int getAllBbs(String choice, String search) {
		
		String sql = " select chount(*) from bbs ";       // 글의 총수
		
		String searchSql = "";
		if(choice.equals("title")) {
			searchSql = " where title like '%" + search + "%'";  // sql 에서 특정단어 포함 하기 위해 like를 씀.
		}
		else if(choice.equals("content")) {
			searchSql = " where content like '%" + search + "%'";
		}
		else if(choice.equals("writer")) {
			searchSql = " where id='" + search + "'";   // 작성자는 id가 완전히 매칭 되어야 하기 떄문에 위에 title, content와는 다르다. 
		}
		sql += searchSql;
		
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		int count = 0;
		
		try {
			conn = DBConnection.getConnection();
			
			psmt = conn.prepareStatement(sql);
			
			rs = psmt.executeQuery();
			if(rs.next()) {
				count = rs.getInt(1);    //retrun 값이 count(*) <-- 이거 하나이다.
			} 
		} catch (SQLException e) {
			e.printStackTrace();
			
		}finally {
				DBClose.close(conn, psmt, rs);
			} 
						
			return count;
		
		
	}
	
	// bbs write
	public boolean writeBbs(BbsDto dto) {
		
		String sql = " 	insert into bbs(id, ref, step, depth, title, content, wdate, del, readcount)"
				+ "		values(?, " 
				+ "			(select ifnull(max(ref), 0)+1 from bbs b), 0, 0, "
				+ "			?, ?, now(), 0, 0)";
		
		Connection conn = null;
		PreparedStatement psmt = null;
		
		int count = 0;  // 몇개가 추가가 되었느냐
		
		try {
			conn = DBConnection.getConnection();
			System.out.println("1/3 writeBbs success");
			
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, dto.getId());
			psmt.setString(2, dto.getTitle());
			psmt.setString(3, dto.getContent());
			System.out.println("2/3 writeBbs success");
			
			count = psmt.executeUpdate();
			System.out.println("3/3 writeBbs success");
			
		} catch (SQLException e) {
			System.out.println("writeBbs fail");
			e.printStackTrace();
		} finally {
			DBClose.close(conn, psmt, null);
		}
		
		return count>0? true:false;
		
	}
	
	
	
	
	
	
	
}
