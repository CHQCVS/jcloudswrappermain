package com.comcast.tvx.xreapps.common.deployment.jcloudswrapper.filter;

import com.google.common.base.Predicate;

import org.jclouds.compute.domain.ComputeMetadata;
import org.jclouds.compute.domain.NodeMetadata;


/**
 * Filter interface for building a Filter expression
 * <p>Sample Usage:</p>
 * <code>
 * <br>JCloudWrapperFactory jCloudWrapperFactory = new JCloudWrapperFactory();</br>
 * <br>JCloudWrapperService jCloudWrapperService = jCloudWrapperFactory.getJCloudWrapperService(CloudProvider.XXXX);</br>
 * <br>FilterBuilder filterBuilder = jCloudWrapperService.getFilterBuilder();</br>
 * <br>NodeMetadataPredicateBuilder nodeMetadataPredicateBuilder = jCloudWrapperService.getNodeMetadataPredicateBuilder();</br>
 * <br>Predicate{@literal <}NodeMetadata> nodeMetadata = nodeMetadataPredicateBuilder.inGroup("XXXX").logicalOperation(FilterLogicalOperation.AND).withStatusRunning().logicalOperation(FilterLogicalOperation.OR).withStatusNotTerminated().build();</br>
 * <br>Filter filter = filterBuilder.chooseComputeMetadataPredicate(ComputeMetadataPredicate.ALL_LOCATIONS, null).chooseNodeMetadataPredicate(nodeMetadataPredicate).build();</br>
 * </code>
 * @author mroowa000
 *
 */
public interface Filter {

    /**
     * {@link Predicate} of {@link ComputeMetadata}
     * @return - {@link Predicate} of {@link ComputeMetadata}
     */
    Predicate<ComputeMetadata> getComputeMetadataPredicate();

    /**
     * {@link Predicate} of {@link NodeMetadata}
     * @return - {@link Predicate} of {@link NodeMetadata}
     */
    Predicate<NodeMetadata> getNodeMetadataPredicate();

}
