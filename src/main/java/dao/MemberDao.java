package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import db.DBClose;
import db.DBConnection;
import dto.MemberDto;

public class MemberDao {
	// 싱글턴 만들기 : 여러곳에서 이 dao에 접근하도록.
	private static MemberDao dao = null;
	
	private MemberDao() {
		DBConnection.initConnection();  // 이 글을 적어줌으로써 db자료와 연동되어 id중복 유무를 확인할수있다. // 여기서 해줬기때문에 BbsDao에는 기입하지 않아도 된다.
	}
	
	public static MemberDao getInstance() {
		if(dao == null) {
			dao = new MemberDao(); // 한번 생상되면 두번다시 새로 생성할 필요없이 getInstance를 한다. 이게 싱글턴
		}
		return dao;
	}
	
	public boolean getId(String id) {
		
		
		String sql = " select id "
				+ "    from member "
				+ "    where id=? ";
		
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		boolean findid = false;     // id가 있다면 false를 true로 변경!
		
		try {
			conn = DBConnection.getConnection();
			System.out.println("1/3 getId success");
			
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, id);
			System.out.println("2/3 getId success");
			
			rs = psmt.executeQuery();
			System.out.println("3/3 getId success");
			
			if(rs.next()) {
				findid = true;		// id가 있으면 true
			}
			
		} catch (SQLException e) {
			System.out.println("getId fail");
			e.printStackTrace();
		} finally {
			DBClose.close(conn, psmt, rs);
		}
		
		return findid;
	}
	
	//회원가입 부분
	public boolean addMember(MemberDto dto) {
		
		String sql = " insert into member(id, pwd, name, email, auth) "
				+ "    values(?, ?, ?, ?, 3) ";  // 3은 유저를 나타냄
		
		Connection conn = null;
		PreparedStatement psmt = null;
		
		int count = 0;
		
		try {
			conn = DBConnection.getConnection();
			System.out.println("1/3 addMember success");
			
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, dto.getId());
			psmt.setString(2, dto.getPwd());
			psmt.setString(3, dto.getName());
			psmt.setString(4, dto.getEmail());
			System.out.println("2/3 addMember success");

			count = psmt.executeUpdate();
			System.out.println("3/3 addMember success");
			
		} catch(Exception e) {
			System.out.println("addMember fail");
			e.printStackTrace();
		} finally {
			DBClose.close(conn, psmt, null);
		}
		
		return count>0?true:false;
	}
	// login 하기( back-end 싱클턴으로 만든다.)
	public MemberDto login(String id, String pwd) {  // 로그인이 안되면 null값으로된다. () 안에 MemberDto 를써도왼다.
		
		String sql = " select id, name, email, auth "  // 결과값을 가져온다.
				+ "    from member "
				+ "    where id=? and pwd=? ";			// id,pwd 맞춰서 결과값을 가져온다.
		
		Connection conn = null;  // DB정보를 받는 것을 뜻한다.
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		MemberDto mem = null;  // id pwd가 틀리면 null
		
		try {
			conn = DBConnection.getConnection();
			System.out.println("1/3 login success");
			
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, id);
			psmt.setString(2, pwd);
			System.out.println("2/3 login success");
			
			rs = psmt.executeQuery();				// db문이 열렸다.
			System.out.println("3/3 login success");
			
			if(rs.next()) {
				String _id = rs.getString("id");  // id가 넘어온다.
				String _name = rs.getString(2); 	
				String _email = rs.getString(3);
				int _auth = rs.getInt(4);
				
				mem = new MemberDto(_id, null, _name, _email, _auth);
			}
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		} finally {
			DBClose.close(conn, psmt, rs);			// db문이 닫힌다.
		}
		
		return mem;
		
	}
}






