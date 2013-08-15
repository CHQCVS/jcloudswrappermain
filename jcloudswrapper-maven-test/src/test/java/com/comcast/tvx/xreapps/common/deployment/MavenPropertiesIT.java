package com.comcast.tvx.xreapps.common.deployment;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.testng.Assert;
import org.testng.annotations.Test;

public class MavenPropertiesIT {
	
	private static final Logger LOG = Logger.getLogger("MavenPropertiesIT");
	
	@Test
	public void testMavenProperties() {
		LOG.log(Level.INFO, "Hostname is: " + System.getProperty("cmc.os.hostname.1") );
		LOG.log(Level.INFO, "Private IP is: " + System.getProperty("cmc.os.privateIP.1"));
		LOG.log(Level.INFO, "Public IP is: " + System.getProperty("cmc.os.publicIP.1"));
		LOG.log(Level.INFO, "***********************************************************");
		LOG.log(Level.INFO, "Hostname is: " + System.getProperty("cmc.os.hostname.2") );
		LOG.log(Level.INFO, "Private IP is: " + System.getProperty("cmc.os.privateIP.2"));
		LOG.log(Level.INFO, "Public IP is: " + System.getProperty("cmc.os.publicIP.2"));		
		
		Assert.assertNotNull(System.getProperty("cmc.os.hostname.1"));
		Assert.assertNotNull(System.getProperty("cmc.os.hostname.2"));
		Assert.assertNotNull(System.getProperty("cmc.os.privateIP.1"));
		Assert.assertNotNull(System.getProperty("cmc.os.privateIP.2"));
	}

}
