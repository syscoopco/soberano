package co.syscoop.soberano.models;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import org.zkoss.zul.AbstractListModel;
import org.zkoss.zul.FieldComparator;
import org.zkoss.zul.event.ListDataEvent;
import org.zkoss.zul.ext.Sortable;

import co.syscoop.soberano.util.SQLQueryFilterParam;

@SuppressWarnings({ "rawtypes", "serial" })
public class SoberanoAbstractListModel<T> extends AbstractListModel<T> implements Sortable<T>
{
	protected int _size = -1;
	protected List<T> _cache;
	protected int _beginOffset = 0;
	protected String _orderBy;
	protected boolean _ascending;
	protected boolean _descending;
	protected FieldComparator _sorting;
	protected ArrayList<SQLQueryFilterParam> filterParams = null;
	
	public SoberanoAbstractListModel(String _orderBy, boolean _ascending, boolean _descending) {
		this._orderBy = _orderBy;
		this._ascending = _ascending;
		this._descending = _descending;
	}

	@Override
	public T getElementAt(int index) {
		return null;
	}

	@Override
	public int getSize() {
		return 0;
	}
	
	private void set_descending(boolean _descending) {
		this._descending = _descending;
	}

	@Override
	public void sort(Comparator cmpr, boolean ascending) {

		_cache = null; //purge cache
		_size = -1; //so size will be reloaded
		set_descending(!(_ascending = ascending));
		_orderBy = ((FieldComparator) cmpr).getRawOrderBy();
		_sorting = (FieldComparator) cmpr;
	       
		//Here we assume sort="auto(fieldName)" is specified in ZUML, so cmpr is FieldComparator
		//On other hand, if you specifies your own comparator, such as sortAscending="${mycmpr}",
		//then, cmpr will be the comparator you assigned
		fireEvent(ListDataEvent.CONTENTS_CHANGED, -1, -1);
	}

	@Override
	public String getSortDirection(Comparator cmpr) {
		   
		if (Objects.equals(_sorting, (FieldComparator) cmpr))
			return _ascending?"ascending":"descending";
		return "natural"; 
	}
	
	public String get_orderBy() {
		return _orderBy;
	}

	public boolean is_ascending() {
		return _ascending;
	}

	public boolean is_descending() {
		return _descending;
	}
	
	public ArrayList<SQLQueryFilterParam> getFilterParams() {
		return filterParams;
	}

	public void setFilterParams(ArrayList<SQLQueryFilterParam> filterParams) {
		this.filterParams = filterParams;
	}
}
