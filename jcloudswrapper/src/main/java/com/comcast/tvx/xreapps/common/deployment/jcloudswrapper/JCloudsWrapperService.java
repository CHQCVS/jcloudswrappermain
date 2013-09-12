package com.comcast.tvx.xreapps.common.deployment.jcloudswrapper;

import com.comcast.tvx.xreapps.common.deployment.jcloudswrapper.filter.Filter;
import com.comcast.tvx.xreapps.common.deployment.jcloudswrapper.filter.FilterBuilder;
import com.comcast.tvx.xreapps.common.deployment.jcloudswrapper.filter.NodeMetadataPredicateBuilder;

import java.util.List;
import java.util.Set;

/**
 * Wrapper interface around JClouds
 * @author mroowa000
 *
 */
public interface JCloudsWrapperService {
	
	/**
	 * Allocate a {@link List} of FloatingIPAddresses
	 * 
	 * @param count - Number of Floating IPs to be allocated
	 * @return - {@link List} of FloatingIPAddresses allocated
	 */
	List<String> allocateFloatingIPAddresses(Integer count);
	
	/**
	 * Release the {@link List} of FloatingIPAddresses back to the pool
	 * @param floatingIPAddresses - {@link List} of FloatingIPAddresses to be released back to the pool
	 */
	void releaseFloatingIPAddresses(List<String> floatingIPAddresses);
	    
    /**
     * <br>Provision nodes (VMs)</br>
     * <br>Note: This assumes that a default Keypair name called "cloudkey" exists in the cloud provider</br>
     * <br>Note: This assumes that a default security group name called "default" exists in the cloud provider</br>
     * @param operatingSystemImage - {@link OperatingSystemImage}
     * @param hardwareProfile - {@link HardwareProfile}
     * @param count - Number of nodes to be provisioned
     * @param groupName - The name of the logical group which will associated these nodes (VMs)
     * @param floatingIPAddresses - {@link List} of FloatingIPAddresses to be allocated to the VMs. If <code>null</code> no Floating IP Address is allocated to VMs
     * @param script - Script to be run at VM startup. <code>null</code> argument indicates no script
     * @param securityGroupNames - {@link Set} of Security Group Names which already exists in the cloud provider. If <code>null</code> then a default security group is used.
     * @param keyPairName - Key Pair name which already exists in the cloud provider. If <code>null</code> then the default keyPairName is used.
     * @return - {@link Set} of {@link VMMetadata} of newly provisioned VMs
     */
    Set<VMMetadata> provisionVM(OperatingSystemImage operatingSystemImage,
        HardwareProfile hardwareProfile, Integer count, String groupName,
        List<String> floatingIPAddresses, String script, Set<String> securityGroupNames, String keyPairName);
    
    /**
     * <br>Provision nodes (VMs) using a default template - Default template uses a specific {@link OperatingSystemImage} and Hardware Profile {@link HardwareProfile}</br>
     * <br>Note: This assumes that a default Keypair name called "cloudkey" exists in the cloud provider</br>
     * <br>Note: This assumes that a default security group name called "default" exists in the cloud provider</br>
     * @param count - Number of nodes to be provisioned
     * @param groupName - The name of the logical group which will associated these nodes (VMs)
     * @param floatingIPAddresses - {@link List} of FloatingIPAddresses to be allocated to the VMs. If <code>null</code> no Floating IP Address is allocated to VMs
     * @param script - Script to be run at VM startup. <code>null</code> argument indicates no script
     * @param securityGroupNames - {@link Set} of Security Group Names which already exists in the cloud provider. If <code>null</code> then a default security group is used.
     * @param keyPairName - Key Pair name which already exists in the cloud provider. If <code>null</code> then the default keyPairName is used.
     * @return - {@link Set} of {@link VMMetadata} of newly provisioned VMs
     */
    Set<VMMetadata> provisionVM(Integer count, String groupName, List<String> floatingIPAddresses, String script, Set<String> securityGroupNames, String keyPairName);    
    
    /**
     * Get the {@link VMMetadata} for a node (VM)
     * @param id - id of node (VM)
     * @return - {@link VMMetadata} of node (VM)
     */
    VMMetadata getNodeMetadata(String id);   
    
    /**
     * Set the UserMetadata for the VM as not deletable 
     * @param vmMetadata - Set of {@link VMMetadata} to be marked as not deletable
     */
    void setVMUndeletable(Set<VMMetadata> vmMetadata); 
    
    /**
     * List nodes (VMs) matching a Filter expression - {@link Filter}
     * @param filterExpression - Filter expression - {@link Filter}
     * @return - {@link Set} of {@link VMMetadata}
     */
    Set<VMMetadata> listNodesDetailsMatching(Filter filterExpression);
    
    /**
     * Destroy nodes (VMs) matching a {@link Set} of {@link VMMetadata}
     * @param vmMetadataSet - {@link Set} of {@link VMMetadata}
     * @return - {@link Set} of {@link VMMetadata} of destroyed VMs
     */
    Set<VMMetadata> destroyNodesMatching(Set<VMMetadata> vmMetadataSet);
    
    /**
     * Destroy node (VM) matching a ID of a node (VM)
     * @param id - Id of VM
     */
    void destroyNode(String id);
    
    /**
     * {@link FilterBuilder} is a  Builder Pattern for building a {@link Filter} expression
     * @return - {@link FilterBuilder}
     */
    FilterBuilder getFilterBuilder();
    
    /**
     * {@link NodeMetadataPredicateBuilder} is a Builder Pattern used in {@link FilterBuilder} to build NodeMetadata used in a {@link Filter} expression
     * @return - {@link NodeMetadataPredicateBuilder}
     */
    NodeMetadataPredicateBuilder getNodeMetadataPredicateBuilder();
    
    /**
     * Close the connection
     */
    void close();    
}
