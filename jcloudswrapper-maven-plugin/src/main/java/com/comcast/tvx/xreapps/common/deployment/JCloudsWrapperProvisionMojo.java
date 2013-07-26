package com.comcast.tvx.xreapps.common.deployment;

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
	 * @parameter expression="${script}"
	 */
	private String script;

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
				if (script == null || script.isEmpty()) {
					getLog().info("Script is : " + script);
					vmMetadata = jCloudsWrapperService.provisionVM(count,
							groupName, null, null);
				} else {
					getLog().info("Script is : " + script);
					vmMetadata = jCloudsWrapperService.provisionVM(count,
							groupName, null, script);
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
				if (script == null || script.isEmpty()) {
					getLog().info("Script is : " + script);
					vmMetadata = jCloudsWrapperService.provisionVM(count,
							groupName, floatingIPs, null);
				} else {
					getLog().info("Script is : " + script);
					vmMetadata = jCloudsWrapperService.provisionVM(count,
							groupName, floatingIPs, script);
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
				if (script == null || script.isEmpty()) {
					getLog().info("Script is : " + script);
					vmMetadata = jCloudsWrapperService.provisionVM(
							operatingSystemImage, hardwareProfile, count,
							groupName, null, null);
				} else {
					getLog().info("Script is : " + script);
					vmMetadata = jCloudsWrapperService.provisionVM(
							operatingSystemImage, hardwareProfile, count,
							groupName, null, script);

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
				if (script == null || script.isEmpty()) {
					getLog().info("Script is : " + script);
					vmMetadata = jCloudsWrapperService.provisionVM(
							operatingSystemImage, hardwareProfile, count,
							groupName, floatingIPs, null);

				} else {
					getLog().info("Script is : " + script);
					vmMetadata = jCloudsWrapperService.provisionVM(
							operatingSystemImage, hardwareProfile, count,
							groupName, floatingIPs, script);
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
}
