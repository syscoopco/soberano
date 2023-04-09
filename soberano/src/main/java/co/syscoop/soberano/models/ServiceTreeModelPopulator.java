package co.syscoop.soberano.models;

import org.zkoss.zul.Tree;

import co.syscoop.soberano.domain.untracked.DomainObject;
import co.syscoop.soberano.renderers.ServiceTreeNodeRenderer;

public class ServiceTreeModelPopulator extends DomainObjectTreeModelPopulator {

	//pageToRefreshZulURI is for operations requiring to refresh a page after they complete, like object deletion
	public ServiceTreeModelPopulator(DomainObject doo, Tree dooTree, String pageToRefreshZulURI) {
		super(doo, dooTree, new ServiceTreeNodeRenderer(pageToRefreshZulURI));
	}
}
