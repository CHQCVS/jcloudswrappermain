package com.comcast.tvx.xreapps.common.deployment.jcloudswrapper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.comcast.tvx.xreapps.common.deployment.jcloudswrapper.filter.Filter;
import com.comcast.tvx.xreapps.common.deployment.jcloudswrapper.filter.FilterBuilder;
import com.comcast.tvx.xreapps.common.deployment.jcloudswrapper.filter.ComputeMetadataPredicate;
import com.comcast.tvx.xreapps.common.deployment.jcloudswrapper.filter.FilterLogicalOperation;
import com.comcast.tvx.xreapps.common.deployment.jcloudswrapper.filter.NodeMetadataPredicateBuilder;

import com.google.common.base.Predicate;


public class JCloudsWrapperServiceTest {
	private JCloudsWrapperFactory jCloudsWrapperFactory;
	private JCloudsWrapperService jCloudsWrapperService;
		
	@BeforeClass
	public void init() {
		jCloudsWrapperFactory = new JCloudsWrapperFactory();
		jCloudsWrapperService = jCloudsWrapperFactory.getJCloudsWrapperService(CloudProvider.OPEN_STACK);		
	}	
	
//	@Test
	public void testProvisionVM() {	
		
		System.out.println("********************PROVISION VM ******************");
		Set<VMMetadata> vMMetadata = jCloudsWrapperService.provisionVM(OperatingSystemImage.CENTOS_LATEST_x86_64, HardwareProfile.TINY_RAM_512, 3, "blue-rss-resources", this.getFloatingIPs(), null);
		
		for (VMMetadata eachVMMetaData : vMMetadata) {
			System.out.println(eachVMMetaData);
		}
		System.out.println("\n");
		
		
	}	

//	@Test
	public void testGenericProvisionVM() {
		
		System.out.println("********************PROVISION GENERIC VM ******************");
		Set<VMMetadata> vMMetadata = jCloudsWrapperService.provisionVM(3, "blue-rss-resources", getFloatingIPs(), null);
		for (VMMetadata eachVMMetaData : vMMetadata) {
			System.out.println(eachVMMetaData);
		}
		System.out.println("\n");
	}
	
//	@Test
	public void testDestroyNodes() {
		System.out.println("********************DESTROY VMs ******************");
		Set<VMMetadata> deletedVmMetadataSet = null;
	
		FilterBuilder filterBuilder = jCloudsWrapperService.getFilterBuilder();		
		NodeMetadataPredicateBuilder nodeMetadataPredicateBuilder = jCloudsWrapperService.getNodeMetadataPredicateBuilder();
		
	//	Predicate<NodeMetadata> nodeMetadataPredicate = nodeMetadataPredicateBuilder.inGroup("blue-rss-reader").logicalOperation(FilterLogicalOperation.AND).withStatusRunning().logicalOperation(FilterLogicalOperation.OR).withStatusNotTerminated().build();		
	//	System.out.println("nodeMetadataPredicate is: " + nodeMetadataPredicate.toString());			
	//	Filter filter = filterBuilder.chooseComputeMetadataPredicate(ComputeMetadataPredicate.ALL_LOCATIONS, null).chooseNodeMetadataPredicate(nodeMetadataPredicate).build();
		Filter filter = filterBuilder.chooseComputeMetadataPredicate(ComputeMetadataPredicate.NAME_STARTS_WITH, "blue-rss-resources").build();
		Set<VMMetadata> vMMetadata = jCloudsWrapperService.listNodesDetailsMatching(filter);
		if (!vMMetadata.isEmpty()) {
			deletedVmMetadataSet = jCloudsWrapperService.destroyNodesMatching(vMMetadata);
		}
		for (VMMetadata eachVMMetadata : deletedVmMetadataSet) {
			System.out.println(eachVMMetadata);
		}
		System.out.println("\n");
	}
	
//	@Test
	public void testNodeMetaData() {
		System.out.println("********************VM METADATA*********************");
		VMMetadata vMMetadata = jCloudsWrapperService.getNodeMetadata("RegionOne/22787e48-3909-441b-ab2e-9c388ffa87dc");
		System.out.println("Hostname Name: " + vMMetadata.getHostname());
		Set<String> publicAddresSet = vMMetadata.getPublicAddresses();
		Set<String> privateAddressSet = vMMetadata.getPrivateAddresses();
		Iterator<String> iter = publicAddresSet.iterator();
		Iterator<String> iter1 = privateAddressSet.iterator();
		while(iter.hasNext()) {
			System.out.println("Public Address is: " + iter.next());
		}
		while(iter1.hasNext()) {
			System.out.println("Private Address is: " + iter1.next());
		}
		
		System.out.println("Group: " + vMMetadata.getGroupName());
		System.out.println("ID: " + vMMetadata.getNodeId());
		Map<String, String> nodeMap = vMMetadata.getUserMetadata();
		Set<String> keys = nodeMap.keySet();
		for (String eachKey : keys) {
			System.out.println("Key is: " + eachKey + " and value is " + nodeMap.get(eachKey));
		}
		
		System.out.println("VMMetadata is: " + vMMetadata);
		System.out.println("\n");
	}
	

//	@Test
	public void testPredicateNodeMetaDataFilter() {
		System.out.println("***********************PREDICATE NODE DATA FILTER ***********************");
		FilterBuilder filterBuilder = jCloudsWrapperService.getFilterBuilder();		
		NodeMetadataPredicateBuilder nodeMetadataPredicateBuilder = jCloudsWrapperService.getNodeMetadataPredicateBuilder();
		
	//	Predicate<NodeMetadata> nodeMetadataPredicate = nodeMetadataPredicateBuilder.inGroup("blue-rss-resources").logicalOperation(FilterLogicalOperation.AND).withStatusRunning().logicalOperation(FilterLogicalOperation.OR).withStatusNotTerminated().build();		
	//	System.out.println("nodeMetadataPredicate is: " + nodeMetadataPredicate.toString());			
	//	Filter filter = filterBuilder.chooseComputeMetadataPredicate(ComputeMetadataPredicate.ALL_LOCATIONS, null).chooseNodeMetadataPredicate(nodeMetadataPredicate).build();
		Filter filter = filterBuilder.chooseComputeMetadataPredicate(ComputeMetadataPredicate.NAME_STARTS_WITH, "xtream").build();
	
		Iterable<VMMetadata> vMMetadatas = jCloudsWrapperService.listNodesDetailsMatching(filter);
		
		for (VMMetadata eachVMMetadata : vMMetadatas) {
			System.out.println(eachVMMetadata);
		}
		System.out.println("\n");
}
	
//	@Test
	public void testAllocateFloatingIP() {
		System.out.println("***********************ALLOCATE FLOATING IP ***********************");
		List<String> publicIPs = jCloudsWrapperService.allocateFloatingIPAddresses(3);
		for (String eachPublicIP : publicIPs) {
			System.out.println("Floating IP is " + eachPublicIP);			
		}
		System.out.println("\n");
	}
	
//	@Test
	public void testReleaseFloatingIP() {
		System.out.println("***********************RELEASE FLOATING IP ***********************");
		List<String> floatingIPAddresses = new ArrayList<String>();		
		floatingIPAddresses.add("162.150.40.153");
		floatingIPAddresses.add("162.150.40.154");
		floatingIPAddresses.add("162.150.40.155");
		jCloudsWrapperService.releaseFloatingIPAddresses(floatingIPAddresses);
		System.out.println("\n");
	}
	
	private List<String> getFloatingIPs() {
		List<String> floatingIPAddresses = new ArrayList<String>();		
		floatingIPAddresses.add("162.150.40.153");
		floatingIPAddresses.add("162.150.40.154");
		floatingIPAddresses.add("162.150.40.155");
		return floatingIPAddresses;
	}
	
	@AfterClass
	public void close() {
		jCloudsWrapperService.close();
	}

}
