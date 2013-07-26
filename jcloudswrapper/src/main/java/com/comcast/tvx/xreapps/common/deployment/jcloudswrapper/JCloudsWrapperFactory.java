package com.comcast.tvx.xreapps.common.deployment.jcloudswrapper;

import com.comcast.tvx.xreapps.common.deployment.jcloudswrapper.impl.JCloudsWrapperServiceOpenStackImpl;

import java.util.NoSuchElementException;

/**
 * Factory class for getting the JClouds Wrapper service
 * @author mroowa000
 *
 */
public class JCloudsWrapperFactory {
	
	/**
	 * Get a {@link JCloudsWrapperService} based on a {@link CloudProvider}
	 * @param cloudProvider - {@link CloudProvider}
	 * @return - {@link JCloudsWrapperService}
	 */
    public JCloudsWrapperService getJCloudsWrapperService(
        CloudProvider cloudProvider) {

        if (cloudProvider.equals(CloudProvider.OPEN_STACK)) {
            return new JCloudsWrapperServiceOpenStackImpl();
        } else {
            throw new NoSuchElementException("No such provider");
        }
    }
}
