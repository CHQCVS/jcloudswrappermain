package com.comcast.tvx.xreapps.common.deployment.jcloudswrapper.filterimpl;

import java.util.ArrayList;
import java.util.List;

import org.jclouds.compute.domain.NodeMetadata;
import org.jclouds.compute.predicates.NodePredicates;

import com.comcast.tvx.xreapps.common.deployment.jcloudswrapper.filter.FilterLogicalOperation;
import com.comcast.tvx.xreapps.common.deployment.jcloudswrapper.filter.NodeMetadataPredicateBuilder;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

/**
 * 
 * @author MRoowa000
 *
 */
public class NodeMetadataPredicateBuilderImpl implements NodeMetadataPredicateBuilder {
	
	private List<Predicate<NodeMetadata>> conditions = new ArrayList<Predicate<NodeMetadata>>();
	private List<String> filterLogicalOperation = new ArrayList<String>();
	
	/**
	 * {@inheritDoc}
	 */
	public NodeMetadataPredicateBuilder inGroup(final String group) {
		conditions.add(NodePredicates.inGroup(group));
		return this;
	}
	
	/**
     * {@inheritDoc}
     */
	public NodeMetadataPredicateBuilder runningInGroup(String group) {
		conditions.add(NodePredicates.runningInGroup(group));
		return this;
	}	
	
	/**
     * {@inheritDoc}
     */
	public NodeMetadataPredicateBuilder logicalOperation(FilterLogicalOperation logicalOperation) {
		filterLogicalOperation.add(logicalOperation.toString());
		return this;
	}	
	
	/**
     * {@inheritDoc}
     */
	public NodeMetadataPredicateBuilder withStatusNotRunning() {
		conditions.add(Predicates.not(NodePredicates.RUNNING));
		return this;
	}
	
	/**
     * {@inheritDoc}
     */
	public NodeMetadataPredicateBuilder withStatusRunning() {
		conditions.add(NodePredicates.RUNNING);
		return this;
	}
	
	/**
     * {@inheritDoc}
     */
	public NodeMetadataPredicateBuilder withStatusNotTerminated() {
		conditions.add(Predicates.not(NodePredicates.TERMINATED));
		return this;
	}
	
	/**
     * {@inheritDoc}
     */
	public NodeMetadataPredicateBuilder withStatusTerminated() {
		conditions.add(NodePredicates.TERMINATED);
		return this;
	}
	
	/**
     * {@inheritDoc}
     */
	public Predicate<NodeMetadata> build() {
		if (conditions.isEmpty()) {
			return null;
		} else if (conditions.size() == 1) {
			return conditions.get(0);
		} else {
			checkValidityOfExpression();
			Predicate<NodeMetadata> evaluatedCondition = null;
			for (int numOfLogicalOperation = filterLogicalOperation.size() - 1 ; numOfLogicalOperation > -1 ; numOfLogicalOperation --) {
				if (evaluatedCondition == null) {
					evaluatedCondition = buildFilter(filterLogicalOperation.get(numOfLogicalOperation), conditions.get(numOfLogicalOperation), conditions.get(numOfLogicalOperation + 1));
				} else {
					evaluatedCondition = buildFilter(filterLogicalOperation.get(numOfLogicalOperation), conditions.get(numOfLogicalOperation), evaluatedCondition);
				}				
			}
			return evaluatedCondition;		
		}
	}
	
	private static Predicate<NodeMetadata> buildFilter(String operation, Predicate<NodeMetadata> condition1, Predicate<NodeMetadata> condition2) {
		Predicate<NodeMetadata> nodeMetadataPredicate = null;
		if (operation.equals(FilterLogicalOperation.AND.toString())) {
			nodeMetadataPredicate = Predicates.and(condition1, condition2);
		} else if (operation.equals(FilterLogicalOperation.OR.toString())) {
			nodeMetadataPredicate = Predicates.or(condition1, condition2);
		}
		return nodeMetadataPredicate;
	}	
	
	private void checkValidityOfExpression() {
		int numberOfConditions = conditions.size();
		int numberOfLogicalOperations = filterLogicalOperation.size();
		if (!((numberOfConditions - numberOfLogicalOperations) == 1)) {
			throw new RuntimeException("Invalid NodeDataPredicate operation");
		}
	}

}
