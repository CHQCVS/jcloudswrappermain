package com.comcast.tvx.xreapps.common.deployment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import com.comcast.tvx.xreapps.common.deployment.jcloudswrapper.CloudProvider;
import com.comcast.tvx.xreapps.common.deployment.jcloudswrapper.HardwareProfile;
import com.comcast.tvx.xreapps.common.deployment.jcloudswrapper.JCloudsWrapperFactory;
import com.comcast.tvx.xreapps.common.deployment.jcloudswrapper.JCloudsWrapperService;
import com.comcast.tvx.xreapps.common.deployment.jcloudswrapper.OperatingSystemImage;
import com.comcast.tvx.xreapps.common.deployment.jcloudswrapper.VMMetadata;

/**
 * @author MRoowa000
 * @goal provision-vm
 * 
 */
public class JCloudsWrapperProvisionMojo extends AbstractMojo {

	private JCloudsWrapperFactory jCloudsWrapperFactory;
	private JCloudsWrapperService jCloudsWrapperService;

	/**
	 * @parameter expression="${cloud.provider}"
	 */
	private CloudProvider cloudProvider;

	/**
	 * @parameter expression="${os}"
	 */
	private OperatingSystemImage operatingSystemImage;

	/**
	 * @parameter expression="${hardware}"
	 */
	private HardwareProfile hardwareProfile;

	/**
	 * @parameter default-value="1" expression="${count}"
	 */
	private Integer count;

	/**
	 * @parameter expression="${group.name}"
	 */
	private String groupName;

	/**
	 * List of Floating IPs
	 * 
	 * @parameter expression="${floating.ips}"
	 */
	private List<String> floatingIPs;

	/**
	 * @parameter expression="${userData}"
	 */
	private File userdata;

	public void execute() throws MojoExecutionException {
		Set<VMMetadata> vmMetadata = null;
		getLog().info("Cloud Provider is: " + cloudProvider);

		this.initializeService();

		if (groupName == null || groupName.isEmpty()) {
			this.close();
			throw new RuntimeException(
					"Set property \"-Dgroup.name\" before proceeding");
		} else {
			getLog().info("Group Name is: " + groupName);
		}

		getLog().info("Count is: " + count);

		if ((operatingSystemImage == null) || (hardwareProfile == null)) {
			if (floatingIPs.isEmpty() || floatingIPs == null) {
				getLog().info(
						"Using default operating system and hardware profile");
				getLog().info("Floating IP: " + floatingIPs);
				if (userdata == null || !userdata.exists()) {
					getLog().info("Path to userData is : " + userdata.getAbsolutePath());
					vmMetadata = jCloudsWrapperService.provisionVM(count,
							groupName, null, null);
				} else {
					getLog().info("Path to userData is : " + userdata.getAbsolutePath());
					vmMetadata = jCloudsWrapperService.provisionVM(count,
							groupName, null, this.getUserDataString(userdata));
				}
				for (VMMetadata eachVMMetadata : vmMetadata) {
					getLog().info(eachVMMetadata.toString());
				}
			} else {
				getLog().info(
						"Using default operating system and hardware profile");
				for (String eachFloatingIP : floatingIPs) {
					getLog().info("Floating IP: " + eachFloatingIP);
				}
				if (userdata == null || !userdata.exists()) {
					getLog().info("Path to userData is : " + userdata.getAbsolutePath());
					vmMetadata = jCloudsWrapperService.provisionVM(count,
							groupName, floatingIPs, null);
				} else {
					getLog().info("Path to userData is : " + userdata.getAbsolutePath());
					vmMetadata = jCloudsWrapperService.provisionVM(count,
							groupName, floatingIPs, this.getUserDataString(userdata));
				}
				for (VMMetadata eachVMMetadata : vmMetadata) {
					getLog().info(eachVMMetadata.toString());
				}
			}
		} else {
			if (floatingIPs.isEmpty() || floatingIPs == null) {
				getLog().info("Operating system is: " + operatingSystemImage);
				getLog().info("Hardware Profile is: " + hardwareProfile);
				getLog().info("Floating IP: " + floatingIPs);
				if (userdata == null || !userdata.exists()) {
					getLog().info("Path to userData is : " + userdata.getAbsolutePath());
					vmMetadata = jCloudsWrapperService.provisionVM(
							operatingSystemImage, hardwareProfile, count,
							groupName, null, null);
				} else {
					getLog().info("Path to userData is : " + userdata.getAbsolutePath());
					vmMetadata = jCloudsWrapperService.provisionVM(
							operatingSystemImage, hardwareProfile, count,
							groupName, null, this.getUserDataString(userdata));

				}
				for (VMMetadata eachVMMetadata : vmMetadata) {
					getLog().info(eachVMMetadata.toString());
				}
			} else {
				getLog().info("Operating system is: " + operatingSystemImage);
				getLog().info("Hardware Profile is: " + hardwareProfile);
				for (String eachFloatingIP : floatingIPs) {
					getLog().info("Floating IP: " + eachFloatingIP);
				}
				if (userdata == null || !userdata.exists()) {
					getLog().info("Path to userData is : " + userdata.getAbsolutePath());
					vmMetadata = jCloudsWrapperService.provisionVM(
							operatingSystemImage, hardwareProfile, count,
							groupName, floatingIPs, null);

				} else {
					getLog().info("Path to userData is : " + userdata.getAbsolutePath());
					vmMetadata = jCloudsWrapperService.provisionVM(
							operatingSystemImage, hardwareProfile, count,
							groupName, floatingIPs, this.getUserDataString(userdata));
				}
				for (VMMetadata eachVMMetadata : vmMetadata) {
					getLog().info(eachVMMetadata.toString());
				}
			}
		}
		this.close();
	}

	private void initializeService() {
		jCloudsWrapperFactory = new JCloudsWrapperFactory();
		jCloudsWrapperService = jCloudsWrapperFactory
				.getJCloudsWrapperService(cloudProvider);
		getLog().info("JClouds Wrapper Service Initialized");
	}

	private void close() {
		jCloudsWrapperService.close();
		getLog().info("JClouds Wrapper Service Closed");
	}
	
	private String getUserDataString(File userData) {
		StringBuffer sb = new StringBuffer();
		BufferedReader input;
		try {
			input = new BufferedReader(new FileReader(userData));
		} catch (FileNotFoundException e1) {
			throw new RuntimeException("File " + userData.getAbsolutePath() + " not found: " + e1.getMessage());
		}
		String line = null;
		try {
			while((line = input.readLine()) != null) {
				sb.append(line).append(" \n");
			}
		} catch (IOException e) {
			throw new RuntimeException("Exception while reading File " + userData.getAbsolutePath() + " : " + e.getMessage());
		}				
		return sb.toString();
	}
}
