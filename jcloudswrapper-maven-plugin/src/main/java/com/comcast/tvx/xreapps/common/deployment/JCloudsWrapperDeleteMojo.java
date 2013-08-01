package com.comcast.tvx.xreapps.common.deployment;

import java.util.Set;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

import com.comcast.tvx.xreapps.common.deployment.jcloudswrapper.CloudProvider;
import com.comcast.tvx.xreapps.common.deployment.jcloudswrapper.JCloudsWrapperFactory;
import com.comcast.tvx.xreapps.common.deployment.jcloudswrapper.JCloudsWrapperService;
import com.comcast.tvx.xreapps.common.deployment.jcloudswrapper.VMMetadata;
import com.comcast.tvx.xreapps.common.deployment.jcloudswrapper.filter.Filter;

/**
 * 
 * @author MRoowa000
 * @goal delete-vm
 * 
 */
public class JCloudsWrapperDeleteMojo extends AbstractMojo {

	private JCloudsWrapperFactory jCloudsWrapperFactory;
	private JCloudsWrapperService jCloudsWrapperService;

	/**
	 * @parameter expression="${cloudProvider}" default-value="OPEN_STACK"
	 */
	private CloudProvider cloudProvider;

	/**
	 * @parameter expression="${nameStartsWith}"
	 */
	private String nameStartsWith;

	/**
	 * @parameter expression="${groupName}"
	 */
	private String groupName;

	public void execute() throws MojoExecutionException, MojoFailureException {

		Set<VMMetadata> vmMetadata = null;
		Set<VMMetadata> deletedVMMetadata = null;
		Filter filter = null;

		getLog().info("Cloud Provider is: " + cloudProvider);
		getLog().info("nameStartsWith is: " + nameStartsWith);
		getLog().info("groupName is: " + groupName);

		this.initializeService();

		FilterUtil filterUtil = new FilterUtil(jCloudsWrapperService);
		filter = filterUtil.getFilter(groupName, nameStartsWith);
		vmMetadata = jCloudsWrapperService.listNodesDetailsMatching(filter);
		if (!vmMetadata.isEmpty()) {
			deletedVMMetadata = jCloudsWrapperService
					.destroyNodesMatching(vmMetadata);
			for (VMMetadata eachVMMetadata : deletedVMMetadata) {
				getLog().info(eachVMMetadata.toString());
			}
		}
		this.close();
	}

	private void initializeService() {
		jCloudsWrapperFactory = new JCloudsWrapperFactory();
		jCloudsWrapperService = jCloudsWrapperFactory
				.getJCloudsWrapperService(cloudProvider);
		getLog().debug("JClouds Wrapper Service Initialized");
	}

	private void close() {
		jCloudsWrapperService.close();
		getLog().debug("JClouds Wrapper Service Closed");
	}
}
