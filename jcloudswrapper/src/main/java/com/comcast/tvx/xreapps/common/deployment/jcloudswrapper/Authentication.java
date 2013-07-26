package com.comcast.tvx.xreapps.common.deployment.jcloudswrapper;

import org.jclouds.ContextBuilder;

import org.jclouds.compute.ComputeService;
import org.jclouds.compute.ComputeServiceContext;

import org.jclouds.openstack.nova.v2_0.NovaApi;
import org.jclouds.openstack.nova.v2_0.NovaApiMetadata;

import java.io.Closeable;
import java.io.IOException;

/**
 * Authentication Object use by a implementation of {@link JCloudsWrapperService} for a particular {@link CloudProvider}
 * @author mroowa000
 *
 */
public class Authentication implements Closeable {

    private ComputeService compute;
    private NovaApi openStackNovaApi;
    private String keyStoneEndPoint;
    private String tenantName;
    private String userName;
    private String password;

    public ComputeService getComputeServiceOpenStack() {
    	this.validateOpenStackCredentials();
        ComputeServiceContext context = ContextBuilder.newBuilder(        		
                new NovaApiMetadata()).endpoint(keyStoneEndPoint).credentials(
                		tenantName + ":" + userName , password).buildView(
                ComputeServiceContext.class);

        compute = context.getComputeService();

        return compute;
    }

    public NovaApi getOpenStackNovaApi() {
    	this.validateOpenStackCredentials();
        openStackNovaApi = ContextBuilder.newBuilder(new NovaApiMetadata())
            .endpoint(keyStoneEndPoint).credentials(
                tenantName+ ":" + userName, password).buildApi(NovaApi.class);

        return openStackNovaApi;
    }

    @Override public void close() throws IOException {
        compute.getContext().close();
    }

    public void novaclose() throws IOException {
        openStackNovaApi.close();
    }
    
    private void validateOpenStackCredentials() {
    	keyStoneEndPoint = System.getProperty("keystone.endpoint");    	
    	tenantName = System.getProperty("tenant.name");    	
    	userName = System.getProperty("username");    	
    	password = System.getProperty("password");    	
    	
    	if (keyStoneEndPoint == null) {
    		throw new NullPointerException("Please pass the keystone endpoint as a Maven property \"[-Dkeystone.endpoint]\"");
    	} else if (tenantName == null) {
    		throw new NullPointerException("Please pass the Openstack tenant name as a Maven property \"[-Dtenant.name]\"");
    	} else if (userName == null) {
    		throw new NullPointerException("Please pass the Openstack username as a Maven property \"[-Dusername]\"");
    	} else if (password == null) {
    		throw new NullPointerException("Please pass the Openstack password as a Maven property \"[-Dpassword]\"");
    	}
    }
}
