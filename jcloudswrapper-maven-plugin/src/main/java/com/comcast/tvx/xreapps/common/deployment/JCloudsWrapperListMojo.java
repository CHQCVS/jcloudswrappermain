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
 * @author MRoowa000
 * @goal list-vm
 * 
 */
public class JCloudsWrapperListMojo extends AbstractMojo {

	private JCloudsWrapperFactory jCloudsWrapperFactory;
	private JCloudsWrapperService jCloudsWrapperService;

	/**
	 * @parameter expression="${cloud.provider}"
	 */
	private CloudProvider cloudProvider;

	/**
	 * @parameter expression="${nameStartsWith}"
	 */
	private String name;

	/**
	 * @parameter expression="${inGroup}"
	 */
	private String group;

	/**
	 * @parameter default-value="false" expression="${listAll}"
	 */
	private boolean listAllInstances;

	public void execute() throws MojoExecutionException, MojoFailureException {

		Set<VMMetadata> vmMetadata = null;
		Filter filter = null;

		getLog().info("Cloud Provider is: " + cloudProvider);
		getLog().info("listAll is: " + listAllInstances);
		getLog().info("nameStartsWith is: " + name);
		getLog().info("inGroup is: " + group);

		this.initializeService();

		FilterUtil filterUtil = new FilterUtil(jCloudsWrapperService);
		if (listAllInstances) {
			filter = filterUtil.getFilterListAll();
		} else {
			filter = filterUtil.getFilter(group, name);
		}
		vmMetadata = jCloudsWrapperService.listNodesDetailsMatching(filter);
		for (VMMetadata eachVMMetadata : vmMetadata) {
			getLog().info(eachVMMetadata.toString());
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
