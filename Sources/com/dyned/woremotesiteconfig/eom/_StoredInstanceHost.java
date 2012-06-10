// $LastChangedRevision$ DO NOT EDIT.  Make changes to StoredInstanceHost.java instead.
package com.dyned.woremotesiteconfig.eom;

import com.webobjects.eoaccess.*;
import com.webobjects.eocontrol.*;
import com.webobjects.foundation.*;
import java.math.*;
import java.util.*;
import org.apache.log4j.Logger;

@SuppressWarnings("all")
public abstract class _StoredInstanceHost extends  EOGenericRecord {
	public static final String ENTITY_NAME = "StoredInstanceHost";

	// Attributes
	public static final String HOST_NAME_KEY = "hostName";
	public static final String INSTANCES_KEY = "instances";

	// Relationships
	public static final String STORED_APP_KEY = "storedApp";

  private static Logger LOG = Logger.getLogger(_StoredInstanceHost.class);

  public StoredInstanceHost localInstanceIn(EOEditingContext editingContext) {
    StoredInstanceHost localInstance = (StoredInstanceHost)EOUtilities.localInstanceOfObject(editingContext, this);
    if (localInstance == null) {
      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
    }
    return localInstance;
  }

  public String hostName() {
    return (String) storedValueForKey("hostName");
  }

  public void setHostName(String value) {
    if (_StoredInstanceHost.LOG.isDebugEnabled()) {
    	_StoredInstanceHost.LOG.debug( "updating hostName from " + hostName() + " to " + value);
    }
    takeStoredValueForKey(value, "hostName");
  }

  public Integer instances() {
    return (Integer) storedValueForKey("instances");
  }

  public void setInstances(Integer value) {
    if (_StoredInstanceHost.LOG.isDebugEnabled()) {
    	_StoredInstanceHost.LOG.debug( "updating instances from " + instances() + " to " + value);
    }
    takeStoredValueForKey(value, "instances");
  }

  public com.dyned.woremotesiteconfig.eom.StoredApp storedApp() {
    return (com.dyned.woremotesiteconfig.eom.StoredApp)storedValueForKey("storedApp");
  }

  public void setStoredAppRelationship(com.dyned.woremotesiteconfig.eom.StoredApp value) {
    if (_StoredInstanceHost.LOG.isDebugEnabled()) {
      _StoredInstanceHost.LOG.debug("updating storedApp from " + storedApp() + " to " + value);
    }
    if (value == null) {
    	com.dyned.woremotesiteconfig.eom.StoredApp oldValue = storedApp();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, "storedApp");
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, "storedApp");
    }
  }
  

  public static StoredInstanceHost createStoredInstanceHost(EOEditingContext editingContext, String hostName
, Integer instances
, com.dyned.woremotesiteconfig.eom.StoredApp storedApp) {
    StoredInstanceHost eo = (StoredInstanceHost) EOUtilities.createAndInsertInstance(editingContext, _StoredInstanceHost.ENTITY_NAME);    
		eo.setHostName(hostName);
		eo.setInstances(instances);
    eo.setStoredAppRelationship(storedApp);
    return eo;
  }

  public static NSArray<StoredInstanceHost> fetchAllStoredInstanceHosts(EOEditingContext editingContext) {
    return _StoredInstanceHost.fetchAllStoredInstanceHosts(editingContext, null);
  }

  public static NSArray<StoredInstanceHost> fetchAllStoredInstanceHosts(EOEditingContext editingContext, NSArray<EOSortOrdering> sortOrderings) {
    return _StoredInstanceHost.fetchStoredInstanceHosts(editingContext, null, sortOrderings);
  }

  public static NSArray<StoredInstanceHost> fetchStoredInstanceHosts(EOEditingContext editingContext, EOQualifier qualifier, NSArray<EOSortOrdering> sortOrderings) {
    EOFetchSpecification fetchSpec = new EOFetchSpecification(_StoredInstanceHost.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray<StoredInstanceHost> eoObjects = (NSArray<StoredInstanceHost>)editingContext.objectsWithFetchSpecification(fetchSpec);
    return eoObjects;
  }

  public static StoredInstanceHost fetchStoredInstanceHost(EOEditingContext editingContext, String keyName, Object value) {
    return _StoredInstanceHost.fetchStoredInstanceHost(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static StoredInstanceHost fetchStoredInstanceHost(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray<StoredInstanceHost> eoObjects = _StoredInstanceHost.fetchStoredInstanceHosts(editingContext, qualifier, null);
    StoredInstanceHost eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = (StoredInstanceHost)eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one StoredInstanceHost that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static StoredInstanceHost fetchRequiredStoredInstanceHost(EOEditingContext editingContext, String keyName, Object value) {
    return _StoredInstanceHost.fetchRequiredStoredInstanceHost(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static StoredInstanceHost fetchRequiredStoredInstanceHost(EOEditingContext editingContext, EOQualifier qualifier) {
    StoredInstanceHost eoObject = _StoredInstanceHost.fetchStoredInstanceHost(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no StoredInstanceHost that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static StoredInstanceHost localInstanceIn(EOEditingContext editingContext, StoredInstanceHost eo) {
    StoredInstanceHost localInstance = (eo == null) ? null : (StoredInstanceHost)EOUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
