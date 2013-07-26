package com.comcast.tvx.xreapps.common.deployment.jcloudswrapper.filterimpl;

import org.jclouds.compute.domain.ComputeMetadata;
import org.jclouds.compute.domain.NodeMetadata;

import com.comcast.tvx.xreapps.common.deployment.jcloudswrapper.filter.Filter;
import com.google.common.base.Predicate;

/**
 * 
 * @author MRoowa000
 *
 */
public class FilterImpl implements Filter {
	
	private Predicate<ComputeMetadata> computeMetadataPredicate;
	private Predicate<NodeMetadata> nodeMetadataPredicate;
	
	public FilterImpl(Predicate<ComputeMetadata> computeMetadataPredicate, Predicate<NodeMetadata> nodeMetadataPredicate) {
		this.computeMetadataPredicate = computeMetadataPredicate;
		this.nodeMetadataPredicate = nodeMetadataPredicate;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Predicate<ComputeMetadata> getComputeMetadataPredicate() {
	
		return computeMetadataPredicate;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Predicate<NodeMetadata> getNodeMetadataPredicate() {
		
		return nodeMetadataPredicate;
	}

}
