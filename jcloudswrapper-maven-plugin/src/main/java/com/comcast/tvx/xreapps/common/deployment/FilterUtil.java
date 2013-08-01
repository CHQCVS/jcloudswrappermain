package com.comcast.tvx.xreapps.common.deployment;

import com.comcast.tvx.xreapps.common.deployment.jcloudswrapper.JCloudsWrapperService;
import com.comcast.tvx.xreapps.common.deployment.jcloudswrapper.filter.ComputeMetadataPredicate;
import com.comcast.tvx.xreapps.common.deployment.jcloudswrapper.filter.Filter;
import com.comcast.tvx.xreapps.common.deployment.jcloudswrapper.filter.FilterBuilder;
import com.comcast.tvx.xreapps.common.deployment.jcloudswrapper.filter.NodeMetadataPredicateBuilder;

/**
 * 
 * @author MRoowa000
 *
 */
public class FilterUtil {
	private JCloudsWrapperService jCloudsWrapperService;
	private FilterBuilder filterBuilder;
	private NodeMetadataPredicateBuilder nodeMetadataPredicateBuilder;
	
	public FilterUtil(JCloudsWrapperService jCloudsWrapperService) {
		this.jCloudsWrapperService = jCloudsWrapperService;
		filterBuilder = jCloudsWrapperService.getFilterBuilder();
		nodeMetadataPredicateBuilder = jCloudsWrapperService
		.getNodeMetadataPredicateBuilder();
	}
	
	/**
	 * Get the {@link Filter} based in inGroup and nameStartsWith
	 * @param group - groupName
	 * @param name - nameStartsWith
	 * @return - {@link Filter}
	 */
	public Filter getFilter(String group, String name) {
		Filter filter = null;				
		if ((name == null || name.isEmpty())
				&& (group == null || group.isEmpty())) {	
			jCloudsWrapperService.close();
			throw new RuntimeException("Both \"groupName\" and \"nameStartsWith\" are null or Empty. Enter any one");

		} else if ((name != null && !name.isEmpty())
				&& (group != null && !group.isEmpty())) {
			jCloudsWrapperService.close();
			throw new RuntimeException("Both \"groupName\" and \"nameStartsWith\" cannot be set. Enter any one");
		} else if (group != null && !group.isEmpty()) {			
			filter = filterBuilder
					.chooseComputeMetadataPredicate(
							ComputeMetadataPredicate.ALL_LOCATIONS, null)
					.chooseNodeMetadataPredicate(
							nodeMetadataPredicateBuilder.inGroup(group).build())
					.build();

		} else if ((name != null && !name.isEmpty())) {			
			filter = filterBuilder.chooseComputeMetadataPredicate(
					ComputeMetadataPredicate.NAME_STARTS_WITH, name).build();

		}		
		return filter;
	}
	
	/**
	 * List All VMs
	 * @return - {@link Filter}
	 */
	public Filter getFilterListAll() {		
		return filterBuilder.chooseComputeMetadataPredicate(ComputeMetadataPredicate.ALL_LOCATIONS, null).build();
	}	
}
