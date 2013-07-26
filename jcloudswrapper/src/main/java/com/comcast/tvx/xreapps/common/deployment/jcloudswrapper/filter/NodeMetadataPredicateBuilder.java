package com.comcast.tvx.xreapps.common.deployment.jcloudswrapper.filter;

import org.jclouds.compute.domain.NodeMetadata;

import com.google.common.base.Predicate;

/**
 * Builder Pattern used in the {@link FilterBuilder} to build a {@link Predicate} of {@link NodeMetadata}
 * @author mroowa000
 *
 */
public interface NodeMetadataPredicateBuilder {
	
	/**
	 * Node(VM) in Group
	 * @param group - Group Name
	 * @return - {@link NodeMetadataPredicateBuilder}
	 */
	NodeMetadataPredicateBuilder inGroup(final String group);
	
	/**
	 * Node(VM) with Status RUNNING in Group
	 * @param group - Group Name
	 * @return - {@link NodeMetadataPredicateBuilder}
	 */
	NodeMetadataPredicateBuilder runningInGroup(String group);
	
	/**
	 * Logical Operation
	 * @param logicalOperation - {@link FilterLogicalOperation} 
	 * @return - {@link NodeMetadataPredicateBuilder}
	 */
	NodeMetadataPredicateBuilder logicalOperation(FilterLogicalOperation logicalOperation);
	
	/**
	 * Node with Status NOT RUNNING
	 * @return - {@link NodeMetadataPredicateBuilder}
	 */
	NodeMetadataPredicateBuilder withStatusNotRunning();
	
	/**
	 * Node with Status RUNNING
	 * @return - {@link NodeMetadataPredicateBuilder}
	 */
	NodeMetadataPredicateBuilder withStatusRunning();
	
	/**
	 * Node with Status NOT TERMINATED
	 * @return - {@link NodeMetadataPredicateBuilder}
	 */
	NodeMetadataPredicateBuilder withStatusNotTerminated();
	
	/**
	 * Node with Status TERMINATED
	 * @return - {@link NodeMetadataPredicateBuilder}
	 */
	NodeMetadataPredicateBuilder withStatusTerminated();
	
	/**
	 * Build a {@link Predicate} of {@link NodeMetadata}
	 * @return - {@link Predicate} of {@link NodeMetadata}
	 */
	Predicate<NodeMetadata> build();
	
}
