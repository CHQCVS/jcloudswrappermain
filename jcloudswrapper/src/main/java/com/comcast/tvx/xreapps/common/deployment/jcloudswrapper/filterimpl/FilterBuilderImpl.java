package com.comcast.tvx.xreapps.common.deployment.jcloudswrapper.filterimpl;

import org.jclouds.compute.domain.ComputeMetadata;
import org.jclouds.compute.domain.NodeMetadata;
import org.jclouds.compute.predicates.NodePredicates;

import com.comcast.tvx.xreapps.common.deployment.jcloudswrapper.filter.Filter;
import com.comcast.tvx.xreapps.common.deployment.jcloudswrapper.filter.FilterBuilder;
import com.comcast.tvx.xreapps.common.deployment.jcloudswrapper.filter.ComputeMetadataPredicate;

import com.google.common.base.Predicate;

/**
 * 
 * @author MRoowa000
 *
 */
public class FilterBuilderImpl implements FilterBuilder {
	
	private Predicate<ComputeMetadata> locationPredicate;	
	private Predicate<NodeMetadata> nodeMetadataPredicate;
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Filter build() {
		return new FilterImpl(getLocationPredicate(), getNodePredicate());
	}
	
	/**
	 * {@inheritDoc}
	 */
    @Override public FilterBuilder chooseComputeMetadataPredicate(ComputeMetadataPredicate computeMetadataPredicate, String var) {
        try {

            if (computeMetadataPredicate.equals(ComputeMetadataPredicate.BY_LOCATION_ID)) {

                if (var != null) {
                    this.setLocationPredicate(NodePredicates.locationId(
                            var));
                } 
                
            } else if (computeMetadataPredicate.equals(ComputeMetadataPredicate.BY_PARENT_LOCATION_ID)) {

                if (var != null) {
                    this.setLocationPredicate(NodePredicates.parentLocationId(
                            var));
                } 
            } else if (computeMetadataPredicate.equals(ComputeMetadataPredicate.NAME_STARTS_WITH)) {
            	if(var != null) {
            		this.setLocationPredicate(nameStartsWith(var));
            	}
            } else {
                this.setLocationPredicate(NodePredicates.all());
            }
        } catch (NullPointerException e) {
            this.setLocationPredicate(NodePredicates.all());
        }

        return this;
    }
    
    /**
     * {@inheritDoc}
     */
	@Override
	public FilterBuilder chooseNodeMetadataPredicate(Predicate<NodeMetadata> nodeMetadataPredicate) {
		this.setNodePredicate(nodeMetadataPredicate);
		return this;
	}
	

	public Predicate<ComputeMetadata> getLocationPredicate() {
		return locationPredicate;
	}
	
	public void setLocationPredicate(Predicate<ComputeMetadata> locationPredicate) {
		this.locationPredicate = locationPredicate;
	}

	public Predicate<NodeMetadata> getNodePredicate() {
		return nodeMetadataPredicate;
	}

	public void setNodePredicate(Predicate<NodeMetadata> nodePredicate) {
		this.nodeMetadataPredicate = nodePredicate;
	}
	
	private static Predicate<ComputeMetadata> nameStartsWith(final String prefix) {
		 return new Predicate<ComputeMetadata>() {
			 
	         @Override
	         public boolean apply(ComputeMetadata computeMetadata) {
	            return computeMetadata.getName().startsWith(prefix);
	         }

	         @Override
	         public String toString() {
	            return "nameStartsWith(" + prefix + ")";
	         }
	      };		
	}
}
