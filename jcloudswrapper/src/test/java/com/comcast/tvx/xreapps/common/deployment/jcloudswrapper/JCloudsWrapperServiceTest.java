package com.comcast.tvx.xreapps.common.deployment.jcloudswrapper;

import java.util.ArrayList;
import java.util.HashSet;
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
		Set<String> securityGroupNames = new HashSet<String>();
		securityGroupNames.add("default");
		securityGroupNames.add("haproxy");
		securityGroupNames.add("mohsin-test-security");
		securityGroupNames.add("zookeeper");
		Set<VMMetadata> vMMetadata = jCloudsWrapperService.provisionVM(OperatingSystemImage.CENTOS_LATEST_x86_64, HardwareProfile.MEDIUM_RAM_4096, 1, "mohsin-jclouds-wrapper", null, null, securityGroupNames, "xcalibur-openstack");
		
		for (VMMetadata eachVMMetaData : vMMetadata) {
			System.out.println(eachVMMetaData);
		} 
	//	System.out.println(this.getUserData());
	//	System.out.println("Length of user data is: " + this.getUserData().getBytes().length);
		System.out.println("\n");		
	}	
	
	//@Test
	public void testVMUndeletable() {
		System.out.println("********************TEST VM UNDELETABLE ******************");
		FilterBuilder filterBuilder = jCloudsWrapperService.getFilterBuilder();		
		Filter filter = filterBuilder.chooseComputeMetadataPredicate(ComputeMetadataPredicate.NAME_STARTS_WITH, "mohsin-jclouds-wrapper").build();
		Set<VMMetadata> vmMetadata = jCloudsWrapperService.listNodesDetailsMatching(filter);
		for (VMMetadata eachVMMetadata : vmMetadata) {
			System.out.println(eachVMMetadata);
		}
		jCloudsWrapperService.setVMUndeletable(vmMetadata);
		Set<VMMetadata> vmMetadata1 = jCloudsWrapperService.listNodesDetailsMatching(filter);
		for (VMMetadata eachVMMetadata : vmMetadata1) {
			System.out.println(eachVMMetadata);
		}	
		
		System.out.println("\n");
	}
	
//	@Test
	public void testGenericProvisionVM() {
		
		System.out.println("********************PROVISION GENERIC VM ******************");
		Set<VMMetadata> vMMetadata = jCloudsWrapperService.provisionVM(3, "blue-rss-resources", getFloatingIPs(), null, null, null);
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
		Filter filter = filterBuilder.chooseComputeMetadataPredicate(ComputeMetadataPredicate.NAME_STARTS_WITH, "mohsin-jclouds-wrapper").build();
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
		Filter filter = filterBuilder.chooseComputeMetadataPredicate(ComputeMetadataPredicate.NAME_STARTS_WITH, "mklein").chooseNodeMetadataPredicate(nodeMetadataPredicateBuilder.inGroup("mklein").build()).build();
	
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
	//	floatingIPAddresses.add("162.150.40.153");
		floatingIPAddresses.add("162.150.40.154");
	//	floatingIPAddresses.add("162.150.40.155");
		jCloudsWrapperService.releaseFloatingIPAddresses(floatingIPAddresses);
		System.out.println("\n");
	}
	
	private List<String> getFloatingIPs() {
		List<String> floatingIPAddresses = new ArrayList<String>();		
	//	floatingIPAddresses.add("162.150.40.153");
		floatingIPAddresses.add("162.150.40.154");
	//	floatingIPAddresses.add("162.150.40.155");
		return floatingIPAddresses;
	}
	
	private String getUserData() {
		String userData = "#cloud-config \n" +

"bootcmd: \n" +
    "# Install rpm for access to public facing nexus artifact repository \n" +
    "- \"echo 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cm9vdAByb290AHJvb3QAcm9vdAByb290AHJvb3QAcm9vdAByb290AHJvb3QAcm9vdAByb290AHJvb3QAcm9vdAByb290AHJvb3QAcm9vdAByb290AHJvb3QAdHZ4LWF3cy1hcnRpZmFjdHMtcmVwby0xLjAuMi0xLnNyYy5ycG0AAAAA//////////////////////////////////////////////////////////90dngtYXdzLWFydGlmYWN0cy1yZXBvAAABAAAKAQAACnJwbWxpYihDb21wcmVzc2VkRmlsZU5hbWVzKQBycG1saWIoUGF5bG9hZEZpbGVzSGF2ZVByZWZpeCkAMy4wLjQtMQA0LjAtMQA0LjguMAAAAAAAAQAAAAEAAAABAAAAAQAAAAEAAAABAAAAAQAAAAEAAAABAAAAAQAAAAEAAAABAAAAAgAAAAMAAAAEAAAABQAAAAYAAAAHAAAACAAAAAkAAAAKAAAACwAAAAAAAAAAAAAAAAAAAAgxLjAuMi0xAAAAAAAAAAABAAAAAQAAAAEAAAABAAAAAgAAAAMAAAAEAAAABQAAAAYAAAAGc3NsAHR2eC1hcnRpZmFjdHMtY2EuY3J0AHR2eC1hcnRpZmFjdHMtY2xpZW50LmNydAB0dngtYXJ0aWZhY3RzLWNsaWVudC5rZXkAdHZ4LWFydGlmYWN0cy1jbGllbnQucDEyAHl1bS5yZXBvcy5kAHR2eC1hd3MtYXJpZmFjdHMucmVwbwBzc2wAc2JpbgBjaGVjay1odHRwLWNvbm5lY3Rpdml0eS1hd3Muc2gAY2hlY2steXVtLWNvbm5lY3Rpdml0eS1hd3Muc2gAL2V0Yy9jb21jYXN0LwAvZXRjL2NvbWNhc3Qvc3NsLwAvZXRjLwAvZXRjL3l1bS5yZXBvcy5kLwAvb3B0L3hjYWwvAC9vcHQveGNhbC9zc2wvAC9vcHQveGNhbC9zc2wvc2Jpbi8ALU8yAGNwaW8AZ3ppcAA5AG5vYXJjaC1yZWRoYXQtbGludXgAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAIAAAABAAAAAQAAAAEAAAAAAAAAAgAAAAEAAAACAAAAAgAAAAEAAAABAEFTQ0lJIHRleHQAZGlyZWN0b3J5AAAA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 | base64 -d > /tmp/tvx-aws-artifacts-repo.rpm\" \n"
    + "- \"rpm -i /tmp/tvx-aws-artifacts-repo.rpm\" \n" +

"packages: \n" +
   " - subversion \n" + 

"package_upgrade: true \n" + 

"runcmd: \n" +
    "# Set up fact file \n" +
    "- \"mkdir -p /etc/facter/facts.d\" \n" + 
    "- \"mkdir -p /var/lib/puppet/hiera-data/environments\" \n" + 
    "- \"echo 'zookeeper_connection=@ZOOKEEPER_CONNECTION@' > /etc/facter/facts.d/cloud-provided.txt\" \n" +
    "- \"echo 'project=horoscopes' >> /etc/facter/facts.d/cloud-provided.txt\" \n" + 
    "- \"echo 'role=horoscopes' >> /etc/facter/facts.d/cloud-provided.txt\" \n" +
    "- \"echo 'flavor=vanilla' >> /etc/facter/facts.d/cloud-provided.txt\" \n" +
    "- \"echo 'region=westchester' >> /etc/facter/facts.d/cloud-provided.txt\" \n"+
    "- \"echo 'availability_zone=@AVAILABILITY_ZONE@' >> /etc/facter/facts.d/cloud-provided.txt\" \n"+
    "- \"echo 'hiera_include( classes )' > /tmp/puppet.pp\" \n"+
    "# Update facter since \"package_upgrade\" doesn't seem to work \n"+
    "- \"yum clean all\" \n"+
    "- \"yum -y update facter\" \n"+
    "- \"yum -y update puppet\" \n"+
    "- \"gem install hiera-file\" \n"+
    "# Install Automation \n"+
    "- \"yum install -y tvx-automation\" \n"+
    "- \"yum install -y horoscopes-automation\" \n"+
    "# Fix sudoers for Puppet runs \n"+
    "- \"sed --in-place 's/requiretty/!requiretty/' /etc/sudoers\" \n"+
    "# Get Hiera data from Subversion \n"+
    "# - \"svn -q --trust-server-cert --non-interactive --no-auth-cache --username '@SCMUSER@' --password '@SCMPASS@' --force export '@REPOSRC@' '@REPODEST@'\" \n"+
    "- \"puppet apply /tmp/puppet.pp\" \n"+


"puppet: \n"+
    "conf: \n"+
        "agent: \n"+
            "onetime: true \n";
		System.out.println("User data is: **************" + userData);
		return userData;
	}
	
	@AfterClass
	public void close() {
		jCloudsWrapperService.close();
	}

}
