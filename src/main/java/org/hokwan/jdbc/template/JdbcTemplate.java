package org.hokwan.jdbc.template;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.parkg.jdbc.raw.DaoException;

/**
 * JDBC helper 클래스.
 * 
 * @author Jacob
 */
public class JdbcTemplate {

	DataSource dataSource;

	/**
	 * dataSource로 초기화하는 컨스트럭터
	 * @param dataSource
	 */
	public JdbcTemplate(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	/**
	 * select 목록을 가져오는 템플릿 메서드
	 * 
	 * @param query     실행할 쿼리
	 * @param params    쿼리의 파라미터들
	 * @param rowMapper 테이블의 한 행과 오브젝트를 매핑하기 위한 functional interface
	 * @return select 결과 list
	 * @throws DaoException SQLException이 발생할 때 던지는 runtime exception
	 */
	public <T> List<T> queryForList(String query, Object[] params,
			RowMapper<T> rowMapper) throws DaoException {
		try (Connection con = dataSource.getConnection();
				PreparedStatement ps = con.prepareStatement(query)) {
			// preparedStatement에 파라미터를 넣음
			setParams(ps, params);
			List<T> list = new ArrayList<>();
			ResultSet rs = ps.executeQuery();
			while (rs.next())
				list.add(rowMapper.mapRow(rs));
			rs.close();
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException(e);
		}
	}

	/**
	 * select 한 줄을 가져오는 템플릿 메서드
	 * 
	 * @param query     실행할 쿼리
	 * @param params    쿼리의 파라미터들
	 * @param rowMapper 테이블의 한 행과 오브젝트를 매핑하기 위한 functional interface
	 * @return select 결과 object
	 * @throws DaoException SQLException이 발생할 때 던지는 runtime exception
	 */
	public <T> T queryForObject(String query, Object[] params,
			RowMapper<T> rowMapper) throws DaoException {
		try (Connection con = dataSource.getConnection();
				PreparedStatement ps = con.prepareStatement(query)) {
			// preparedStatement에 파라미터를 넣음
			setParams(ps, params);
			ResultSet rs = ps.executeQuery();
			T t = null;
			if (rs.next())
				t = rowMapper.mapRow(rs);
			rs.close();
			return t;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException(e);
		}
	}

	/**
	 * insert, update, delete를 하는 템플릿 메서드
	 * 
	 * @param query  실행할 쿼리
	 * @param params 쿼리의 파라미터들
	 * @return 변경된 row의 갯수
	 * @throws DaoException SQLException이 발생할 때 던지는 runtime exception
	 */
	public int update(String query, Object... params) throws DaoException {
		try (Connection con = dataSource.getConnection();
				PreparedStatement ps = con.prepareStatement(query)) {
			// preparedStatement에 파라미터를 넣음
			setParams(ps, params);
			return ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException(e);
		}
	}

	/**
	 * preparedStatement에 파라미터 세팅
	 * 
	 * @param ps     preparedStatement
	 * @param params preparedStatement에 넣을 파라미터들
	 * @throws SQLException
	 */
	private void setParams(PreparedStatement ps, Object[] params)
			throws SQLException {
		if (params != null) {
			for (int i = 0; i < params.length; i++)
				ps.setObject(i + 1, params[i]);
		}
	}
}