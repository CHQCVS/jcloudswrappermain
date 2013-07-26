package com.comcast.tvx.xreapps.common.deployment.jcloudswrapper;

import com.google.common.base.Objects;
import com.google.common.base.Objects.ToStringHelper;

import java.util.Map;
import java.util.Set;

/**
 * Node (VM) Metadata
 * @author MRoowa000
 *
 */
public class VMMetadata {

    private Map<String, String> userMetadata;
    private String NodeId;
    private String providerId;
    private String hostname;
    private String groupName;
    private Set<String> publicAddresses;
    private Set<String> privateAddresses;
    
    /**
     * Get User Metadata
     * @return - User Metadata
     */
    public Map<String, String> getUserMetadata() {
        return userMetadata;
    }

    /**
     * Set User metadata
     * @param userMetadata - {@link Map} of User Metadata
     */
    public void setUserMetadata(Map<String, String> userMetadata) {
        this.userMetadata = userMetadata;
    }
    
    /**
     * Get Node ID (Includes Zone name) of Node (VM)
     * @return - Node ID
     */
    public String getNodeId() {
        return NodeId;
    }

    /**
     * Set Node ID (Includes Zone name) of Node (VM)
     * @param NodeId - Node ID
     */
    public void setNodeId(String NodeId) {
        this.NodeId = NodeId;
    }
    
    /**
     * Get Provider ID (Does not include Zone name) of Node (VM)
     * @return - Provider ID
     */
    public String getProviderId() {
    	return providerId;
    }
    
    /**
     * Set Provider ID (Does not include Zone name) of Node (VM)
     * @param providerId - Provider ID
     */
    public void setProviderId(String providerId) {
    	this.providerId = providerId;
    }

    /**
     * Get Hostname of Node (VM)
     * @return - Hostname
     */
    public String getHostname() {
        return hostname;
    }

    /**
     * Set Hostname of the Node (VM)
     * @param hostname - Hostname
     */
    public void setHostname(String hostname) {
        this.hostname = hostname;
    }
    
    /**
     * Get Group Name of which the Node (VM) is a part of
     * @return - Group name
     */
    public String getGroupName() {
        return groupName;
    }
    
    /**
     * Set Group Name of which the Node (VM) is a part of
     * @param groupName - Group name
     */
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
    
    /**
     * Get {@link Set} of Public Addresses of Node (VM)
     * @return - {@link Set} of Public Addresses of Node (VM) 
     */
    public Set<String> getPublicAddresses() {
        return publicAddresses;
    }
    
    /**
     * Set {@link Set} of Public Addresses of Node (VM)
     * @param publicAddresses - {@link Set} of Public Addresses of Node (VM)
     */
    public void setPublicAddresses(Set<String> publicAddresses) {
        this.publicAddresses = publicAddresses;
    }
    
    /**
     * Get {@link Set} of Private Addresses of Node (VM)
     * @return - {@link Set} of Private Addresses of Node (VM)
     */
    public Set<String> getPrivateAddresses() {
        return privateAddresses;
    }
    
    /**
     * Set {@link Set} of Private Addresses of Node (VM)
     * @param privateAddresses - {@link Set} of Private Addresses of Node (VM)
     */
    public void setPrivateAddresses(Set<String> privateAddresses) {
        this.privateAddresses = privateAddresses;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override public String toString() {
        return string().toString();
    }

    protected ToStringHelper string() {
        return Objects.toStringHelper("").omitNullValues().add("ID",
                getNodeId()).add("ProviderId", getProviderId()).add("Hostname", getHostname()).add("GroupName", getGroupName()).add(
                "Public Addresses", getPublicAddresses()).add("Private Addresses", getPrivateAddresses()).add("userMetadata",
                getUserMetadata());
    }
}
