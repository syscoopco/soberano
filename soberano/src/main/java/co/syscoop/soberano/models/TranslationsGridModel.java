package co.syscoop.soberano.models;

import java.sql.SQLException;

import co.syscoop.soberano.database.relational.TranslationExtractor;
import co.syscoop.soberano.domain.tracked.Translation;

@SuppressWarnings("serial")
public class TranslationsGridModel extends SoberanoAbstractListModel<Object>
{
	public TranslationsGridModel() {
		
		//the set is sorted by position from major to minor (newer ones go first).
		super("position", true, false);
	}
	
	@Override
	public int getSize() {

		try {
			if (_size < 0)
				_size = new Translation().getCount();
			return _size;
		} 
		catch (SQLException e) 
		{
			return 0;
		}
	}
   
	@Override
	public Object getElementAt(int index) {
	   
		if (_cache == null || index < _beginOffset || index >= _beginOffset + _cache.size()) {
			try {
				_beginOffset = index;
				_cache = new Translation().getAll(_orderBy == null?"position":_orderBy,
													_ascending?false:true, 
													50, 
													_beginOffset, 
													new TranslationExtractor());
			}
			catch (SQLException e) 
			{
				return null;
			}
		}
		return _cache.get(index - _beginOffset);
	}
}