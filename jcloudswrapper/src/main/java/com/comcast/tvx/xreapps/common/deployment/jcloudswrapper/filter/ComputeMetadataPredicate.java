package com.comcast.tvx.xreapps.common.deployment.jcloudswrapper.filter;

import org.jclouds.compute.domain.ComputeMetadata;

/**
 * Enum used by {@link FilterBuilder} to build a {@link Filter} expression based on a {@link ComputeMetadata}
 * @author mroowa000
 *
 */
public enum ComputeMetadataPredicate {	
	
	ALL_LOCATIONS, BY_LOCATION_ID, BY_PARENT_LOCATION_ID, NAME_STARTS_WITH ;
	
}
