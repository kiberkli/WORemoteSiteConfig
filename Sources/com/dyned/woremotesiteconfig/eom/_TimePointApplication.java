// $LastChangedRevision$ DO NOT EDIT.  Make changes to TimePointApplication.java instead.
package com.dyned.woremotesiteconfig.eom;

import com.webobjects.eoaccess.*;
import com.webobjects.eocontrol.*;
import com.webobjects.foundation.*;
import java.math.*;
import java.util.*;
import org.apache.log4j.Logger;

@SuppressWarnings("all")
public abstract class _TimePointApplication extends  EOGenericRecord {
	public static final String ENTITY_NAME = "TimePointApplication";

	// Attributes
	public static final String INSTANCE_WEIGHT_KEY = "instanceWeight";

	// Relationships
	public static final String STORED_APP_KEY = "storedApp";
	public static final String TIME_POINT_KEY = "timePoint";

  private static Logger LOG = Logger.getLogger(_TimePointApplication.class);

  public TimePointApplication localInstanceIn(EOEditingContext editingContext) {
    TimePointApplication localInstance = (TimePointApplication)EOUtilities.localInstanceOfObject(editingContext, this);
    if (localInstance == null) {
      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
    }
    return localInstance;
  }

  public Integer instanceWeight() {
    return (Integer) storedValueForKey("instanceWeight");
  }

  public void setInstanceWeight(Integer value) {
    if (_TimePointApplication.LOG.isDebugEnabled()) {
    	_TimePointApplication.LOG.debug( "updating instanceWeight from " + instanceWeight() + " to " + value);
    }
    takeStoredValueForKey(value, "instanceWeight");
  }

  public com.dyned.woremotesiteconfig.eom.StoredApp storedApp() {
    return (com.dyned.woremotesiteconfig.eom.StoredApp)storedValueForKey("storedApp");
  }

  public void setStoredAppRelationship(com.dyned.woremotesiteconfig.eom.StoredApp value) {
    if (_TimePointApplication.LOG.isDebugEnabled()) {
      _TimePointApplication.LOG.debug("updating storedApp from " + storedApp() + " to " + value);
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
  
  public com.dyned.woremotesiteconfig.eom.TimePoint timePoint() {
    return (com.dyned.woremotesiteconfig.eom.TimePoint)storedValueForKey("timePoint");
  }

  public void setTimePointRelationship(com.dyned.woremotesiteconfig.eom.TimePoint value) {
    if (_TimePointApplication.LOG.isDebugEnabled()) {
      _TimePointApplication.LOG.debug("updating timePoint from " + timePoint() + " to " + value);
    }
    if (value == null) {
    	com.dyned.woremotesiteconfig.eom.TimePoint oldValue = timePoint();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, "timePoint");
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, "timePoint");
    }
  }
  

  public static TimePointApplication createTimePointApplication(EOEditingContext editingContext, Integer instanceWeight
, com.dyned.woremotesiteconfig.eom.StoredApp storedApp, com.dyned.woremotesiteconfig.eom.TimePoint timePoint) {
    TimePointApplication eo = (TimePointApplication) EOUtilities.createAndInsertInstance(editingContext, _TimePointApplication.ENTITY_NAME);    
		eo.setInstanceWeight(instanceWeight);
    eo.setStoredAppRelationship(storedApp);
    eo.setTimePointRelationship(timePoint);
    return eo;
  }

  public static NSArray<TimePointApplication> fetchAllTimePointApplications(EOEditingContext editingContext) {
    return _TimePointApplication.fetchAllTimePointApplications(editingContext, null);
  }

  public static NSArray<TimePointApplication> fetchAllTimePointApplications(EOEditingContext editingContext, NSArray<EOSortOrdering> sortOrderings) {
    return _TimePointApplication.fetchTimePointApplications(editingContext, null, sortOrderings);
  }

  public static NSArray<TimePointApplication> fetchTimePointApplications(EOEditingContext editingContext, EOQualifier qualifier, NSArray<EOSortOrdering> sortOrderings) {
    EOFetchSpecification fetchSpec = new EOFetchSpecification(_TimePointApplication.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray<TimePointApplication> eoObjects = (NSArray<TimePointApplication>)editingContext.objectsWithFetchSpecification(fetchSpec);
    return eoObjects;
  }

  public static TimePointApplication fetchTimePointApplication(EOEditingContext editingContext, String keyName, Object value) {
    return _TimePointApplication.fetchTimePointApplication(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static TimePointApplication fetchTimePointApplication(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray<TimePointApplication> eoObjects = _TimePointApplication.fetchTimePointApplications(editingContext, qualifier, null);
    TimePointApplication eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = (TimePointApplication)eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one TimePointApplication that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static TimePointApplication fetchRequiredTimePointApplication(EOEditingContext editingContext, String keyName, Object value) {
    return _TimePointApplication.fetchRequiredTimePointApplication(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static TimePointApplication fetchRequiredTimePointApplication(EOEditingContext editingContext, EOQualifier qualifier) {
    TimePointApplication eoObject = _TimePointApplication.fetchTimePointApplication(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no TimePointApplication that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static TimePointApplication localInstanceIn(EOEditingContext editingContext, TimePointApplication eo) {
    TimePointApplication localInstance = (eo == null) ? null : (TimePointApplication)EOUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
