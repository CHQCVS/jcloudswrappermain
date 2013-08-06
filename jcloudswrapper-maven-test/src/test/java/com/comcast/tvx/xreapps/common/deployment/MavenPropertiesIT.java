package com.comcast.tvx.xreapps.common.deployment;

import org.testng.Assert;
import org.testng.annotations.Test;

public class MavenPropertiesIT {
	
	@Test
	public void testMavenProperties() {
		System.out.println("Hostname is: " + System.getProperty("cmc.os.hostname.1") );
		System.out.println("Private IP is: " + System.getProperty("cmc.os.privateIP.1"));
		System.out.println("Public IP is: " + System.getProperty("cmc.os.publicIP.1"));
		System.out.println("***********************************************************");
		System.out.println("Hostname is: " + System.getProperty("cmc.os.hostname.2") );
		System.out.println("Private IP is: " + System.getProperty("cmc.os.privateIP.2"));
		System.out.println("Public IP is: " + System.getProperty("cmc.os.publicIP.2"));		
		
		Assert.assertNotNull(System.getProperty("cmc.os.hostname.1"));
		Assert.assertNotNull(System.getProperty("cmc.os.hostname.2"));
		Assert.assertNotNull(System.getProperty("cmc.os.privateIP.1"));
		Assert.assertNotNull(System.getProperty("cmc.os.privateIP.2"));
	}

}
