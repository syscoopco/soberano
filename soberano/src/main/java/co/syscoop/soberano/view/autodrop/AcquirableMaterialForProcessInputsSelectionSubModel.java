package co.syscoop.soberano.view.autodrop;

import org.zkoss.zul.AbstractListModel;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.ListSubModel;
import co.syscoop.soberano.domain.tracked.AcquirableMaterial;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class AcquirableMaterialForProcessInputsSelectionSubModel extends AbstractListModel<Object> implements ListSubModel<Object> { 

	// Holds only the currently displayed subset (max nRows)
    private List<Object> currentResults = new ArrayList<>();
	
    @Override
    public ListModel<Object> getSubModel(Object value, int nRows) {
        String typed = (value == null) ? "" : value.toString().trim();

        if (typed.isEmpty()) {
            currentResults = new ArrayList<>();
        } else {
            try {
                // Query the database with the typed prefix and limit
                currentResults = new AcquirableMaterial().getAllWithStringId(typed, 15 /*nRows*/);
            } catch (SQLException e) {
                e.printStackTrace();
                currentResults = new ArrayList<>();
            }
        }

        // Return this model (which is a ListModel)
        return this;
    }
    
    @Override
    public int getSize() {
        return currentResults.size();
    }

    @Override
    public Object getElementAt(int index) {
        return currentResults.get(index);
    }
}