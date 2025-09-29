package co.syscoop.soberano.database.relational;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import co.syscoop.soberano.util.rowdata.TranslationRowData;

public final class TranslationExtractor implements ResultSetExtractor<List<Object>> {
	
	@Override
	public List<Object> extractData(ResultSet rs) throws SQLException, DataAccessException {
		
		List<Object> translationTableData = new ArrayList<Object>();
		TranslationRowData translationRowData = null;
        while (rs.next()) {
        	translationRowData = new TranslationRowData(rs.getInt("translationId"));
        	translationRowData.setLanguage(rs.getString("language"));
        	translationRowData.setPosition(rs.getInt("position"));
        	translationRowData.setFromTerm(rs.getString("fromTerm"));
        	translationRowData.setToTerm(rs.getString("toTerm"));
        	translationTableData.add(translationRowData);
        }
        return translationTableData;
	}
}