package com.comcast.tvx.xreapps.common.deployment.jcloudswrapper.utilities;

import java.util.Collection;
import java.util.HashSet;

/**
 * 
 * @author MRoowa000
 *
 */
public class Utility {
	/**
     * Returns a collection of common elements.
     *
     * @param   list1  - Collection 1
     * @param   list2  - Collection 2
     *
     * @return  - Collection of common elements.
     */
    public static Collection<String> returnCommonElementsInCollection(Collection<String> list1,
                                                                  Collection<String> list2) {
        Collection<String> similar = new HashSet<String>(list1);
        Collection<String> different = new HashSet<String>();

        different.addAll(list1);
        different.addAll(list2);

        similar.retainAll(list2);
        different.removeAll(similar);

        return similar;
    } // end method returnCommonElementsInCollection
}
