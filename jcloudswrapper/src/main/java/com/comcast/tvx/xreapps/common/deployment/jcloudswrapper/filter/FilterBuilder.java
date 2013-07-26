package com.comcast.tvx.xreapps.common.deployment.jcloudswrapper.filter;

import org.jclouds.compute.domain.ComputeMetadata;
import org.jclouds.compute.domain.NodeMetadata;

import com.google.common.base.Predicate;

/**
 * Builder Pattern used to build a {@link Filter} expression
 * @author mroowa000
 *
 */
public interface FilterBuilder {	
	
	/**
	 * Uses the {@link ComputeMetadataPredicate} to build a {@link Filter} expression based on {@link ComputeMetadata}
	 * @param computeMetadataPredicate - {@link ComputeMetadataPredicate}
	 * @param var - Optional variable. If a null is passed for any enum value other than {@link ComputeMetadataPredicate}.ALL, it defaults to {@link ComputeMetadataPredicate}.ALL
	 * @return - {@link FilterBuilder}
	 */
	FilterBuilder chooseComputeMetadataPredicate(ComputeMetadataPredicate computeMetadataPredicate, String var);	
	
	/**
	 * Use the {@link NodeMetadataPredicateBuilder} to build a {@link Predicate} of {@link NodeMetadata}
	 * @param nodeMetadataPredicate - {@link Predicate} of {@link NodeMetadata}
	 * @return - {@link FilterBuilder}
	 */
	FilterBuilder chooseNodeMetadataPredicate(Predicate<NodeMetadata> nodeMetadataPredicate);	
	
	/**
	 * Builds a {@link Filter} 
	 * @return - {@link Filter}
	 */
	Filter build();
}
