package org.hokwan.jdbc.template;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * mapRow() 메서드를 파라미터로 넘기기 위한 functional interface<br>
 * resultSet과 오브젝트를 매핑한다.
 * @author hokwan
 *
 * @param <T> resultSet과 매핑하는 오브젝트의 타입
 */
@FunctionalInterface
public interface RowMapper<T> {
	/**
	 * resultSet과 오브젝트를 매핑하는 메서드
	 * @param rs resultSet
	 * @return resultSet과 매핑한 오브젝트
	 * @throws SQLException
	 */
    T mapRow(ResultSet rs) throws SQLException;
}