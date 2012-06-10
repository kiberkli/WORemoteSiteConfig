// $LastChangedRevision$ DO NOT EDIT.  Make changes to StoredApp.java instead.
package com.dyned.woremotesiteconfig.eom;

import com.webobjects.eoaccess.*;
import com.webobjects.eocontrol.*;
import com.webobjects.foundation.*;
import java.math.*;
import java.util.*;
import org.apache.log4j.Logger;

@SuppressWarnings("all")
public abstract class _StoredApp extends  EOGenericRecord {
	public static final String ENTITY_NAME = "StoredApp";

	// Attributes
	public static final String INSTANCE_INTERLEAVE_KEY = "instanceInterleave";
	public static final String JSON_STRING_KEY = "jsonString";
	public static final String NAME_KEY = "name";
	public static final String SCHEDULE_BEGIN_HOUR_KEY = "scheduleBeginHour";
	public static final String SCHEDULE_END_HOUR_KEY = "scheduleEndHour";
	public static final String SCHEDULE_HOURLY_INTERVAL_KEY = "scheduleHourlyInterval";
	public static final String SCHEDULE_TYPE_KEY = "scheduleType";
	public static final String SCHEDULE_WEEK_DAY_KEY = "scheduleWeekDay";

	// Relationships
	public static final String STORED_INSTANCE_HOSTS_KEY = "storedInstanceHosts";
	public static final String STORED_SITE_KEY = "storedSite";
	public static final String TIME_POINT_APPLICATIONS_KEY = "timePointApplications";

  private static Logger LOG = Logger.getLogger(_StoredApp.class);

  public StoredApp localInstanceIn(EOEditingContext editingContext) {
    StoredApp localInstance = (StoredApp)EOUtilities.localInstanceOfObject(editingContext, this);
    if (localInstance == null) {
      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
    }
    return localInstance;
  }

  public Integer instanceInterleave() {
    return (Integer) storedValueForKey("instanceInterleave");
  }

  public void setInstanceInterleave(Integer value) {
    if (_StoredApp.LOG.isDebugEnabled()) {
    	_StoredApp.LOG.debug( "updating instanceInterleave from " + instanceInterleave() + " to " + value);
    }
    takeStoredValueForKey(value, "instanceInterleave");
  }

  public String jsonString() {
    return (String) storedValueForKey("jsonString");
  }

  public void setJsonString(String value) {
    if (_StoredApp.LOG.isDebugEnabled()) {
    	_StoredApp.LOG.debug( "updating jsonString from " + jsonString() + " to " + value);
    }
    takeStoredValueForKey(value, "jsonString");
  }

  public String name() {
    return (String) storedValueForKey("name");
  }

  public void setName(String value) {
    if (_StoredApp.LOG.isDebugEnabled()) {
    	_StoredApp.LOG.debug( "updating name from " + name() + " to " + value);
    }
    takeStoredValueForKey(value, "name");
  }

  public String scheduleBeginHour() {
    return (String) storedValueForKey("scheduleBeginHour");
  }

  public void setScheduleBeginHour(String value) {
    if (_StoredApp.LOG.isDebugEnabled()) {
    	_StoredApp.LOG.debug( "updating scheduleBeginHour from " + scheduleBeginHour() + " to " + value);
    }
    takeStoredValueForKey(value, "scheduleBeginHour");
  }

  public String scheduleEndHour() {
    return (String) storedValueForKey("scheduleEndHour");
  }

  public void setScheduleEndHour(String value) {
    if (_StoredApp.LOG.isDebugEnabled()) {
    	_StoredApp.LOG.debug( "updating scheduleEndHour from " + scheduleEndHour() + " to " + value);
    }
    takeStoredValueForKey(value, "scheduleEndHour");
  }

  public String scheduleHourlyInterval() {
    return (String) storedValueForKey("scheduleHourlyInterval");
  }

  public void setScheduleHourlyInterval(String value) {
    if (_StoredApp.LOG.isDebugEnabled()) {
    	_StoredApp.LOG.debug( "updating scheduleHourlyInterval from " + scheduleHourlyInterval() + " to " + value);
    }
    takeStoredValueForKey(value, "scheduleHourlyInterval");
  }

  public String scheduleType() {
    return (String) storedValueForKey("scheduleType");
  }

  public void setScheduleType(String value) {
    if (_StoredApp.LOG.isDebugEnabled()) {
    	_StoredApp.LOG.debug( "updating scheduleType from " + scheduleType() + " to " + value);
    }
    takeStoredValueForKey(value, "scheduleType");
  }

  public String scheduleWeekDay() {
    return (String) storedValueForKey("scheduleWeekDay");
  }

  public void setScheduleWeekDay(String value) {
    if (_StoredApp.LOG.isDebugEnabled()) {
    	_StoredApp.LOG.debug( "updating scheduleWeekDay from " + scheduleWeekDay() + " to " + value);
    }
    takeStoredValueForKey(value, "scheduleWeekDay");
  }

  public com.dyned.woremotesiteconfig.eom.StoredSite storedSite() {
    return (com.dyned.woremotesiteconfig.eom.StoredSite)storedValueForKey("storedSite");
  }

  public void setStoredSiteRelationship(com.dyned.woremotesiteconfig.eom.StoredSite value) {
    if (_StoredApp.LOG.isDebugEnabled()) {
      _StoredApp.LOG.debug("updating storedSite from " + storedSite() + " to " + value);
    }
    if (value == null) {
    	com.dyned.woremotesiteconfig.eom.StoredSite oldValue = storedSite();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, "storedSite");
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, "storedSite");
    }
  }
  
  public NSArray<com.dyned.woremotesiteconfig.eom.StoredInstanceHost> storedInstanceHosts() {
    return (NSArray<com.dyned.woremotesiteconfig.eom.StoredInstanceHost>)storedValueForKey("storedInstanceHosts");
  }

  public NSArray<com.dyned.woremotesiteconfig.eom.StoredInstanceHost> storedInstanceHosts(EOQualifier qualifier) {
    return storedInstanceHosts(qualifier, null, false);
  }

  public NSArray<com.dyned.woremotesiteconfig.eom.StoredInstanceHost> storedInstanceHosts(EOQualifier qualifier, boolean fetch) {
    return storedInstanceHosts(qualifier, null, fetch);
  }

  public NSArray<com.dyned.woremotesiteconfig.eom.StoredInstanceHost> storedInstanceHosts(EOQualifier qualifier, NSArray<EOSortOrdering> sortOrderings, boolean fetch) {
    NSArray<com.dyned.woremotesiteconfig.eom.StoredInstanceHost> results;
    if (fetch) {
      EOQualifier fullQualifier;
      EOQualifier inverseQualifier = new EOKeyValueQualifier(com.dyned.woremotesiteconfig.eom.StoredInstanceHost.STORED_APP_KEY, EOQualifier.QualifierOperatorEqual, this);
    	
      if (qualifier == null) {
        fullQualifier = inverseQualifier;
      }
      else {
        NSMutableArray qualifiers = new NSMutableArray();
        qualifiers.addObject(qualifier);
        qualifiers.addObject(inverseQualifier);
        fullQualifier = new EOAndQualifier(qualifiers);
      }

      results = com.dyned.woremotesiteconfig.eom.StoredInstanceHost.fetchStoredInstanceHosts(editingContext(), fullQualifier, sortOrderings);
    }
    else {
      results = storedInstanceHosts();
      if (qualifier != null) {
        results = (NSArray<com.dyned.woremotesiteconfig.eom.StoredInstanceHost>)EOQualifier.filteredArrayWithQualifier(results, qualifier);
      }
      if (sortOrderings != null) {
        results = (NSArray<com.dyned.woremotesiteconfig.eom.StoredInstanceHost>)EOSortOrdering.sortedArrayUsingKeyOrderArray(results, sortOrderings);
      }
    }
    return results;
  }
  
  public void addToStoredInstanceHostsRelationship(com.dyned.woremotesiteconfig.eom.StoredInstanceHost object) {
    if (_StoredApp.LOG.isDebugEnabled()) {
      _StoredApp.LOG.debug("adding " + object + " to storedInstanceHosts relationship");
    }
    addObjectToBothSidesOfRelationshipWithKey(object, "storedInstanceHosts");
  }

  public void removeFromStoredInstanceHostsRelationship(com.dyned.woremotesiteconfig.eom.StoredInstanceHost object) {
    if (_StoredApp.LOG.isDebugEnabled()) {
      _StoredApp.LOG.debug("removing " + object + " from storedInstanceHosts relationship");
    }
    removeObjectFromBothSidesOfRelationshipWithKey(object, "storedInstanceHosts");
  }

  public com.dyned.woremotesiteconfig.eom.StoredInstanceHost createStoredInstanceHostsRelationship() {
    EOClassDescription eoClassDesc = EOClassDescription.classDescriptionForEntityName("StoredInstanceHost");
    EOEnterpriseObject eo = eoClassDesc.createInstanceWithEditingContext(editingContext(), null);
    editingContext().insertObject(eo);
    addObjectToBothSidesOfRelationshipWithKey(eo, "storedInstanceHosts");
    return (com.dyned.woremotesiteconfig.eom.StoredInstanceHost) eo;
  }

  public void deleteStoredInstanceHostsRelationship(com.dyned.woremotesiteconfig.eom.StoredInstanceHost object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "storedInstanceHosts");
    editingContext().deleteObject(object);
  }

  public void deleteAllStoredInstanceHostsRelationships() {
    Enumeration objects = storedInstanceHosts().immutableClone().objectEnumerator();
    while (objects.hasMoreElements()) {
      deleteStoredInstanceHostsRelationship((com.dyned.woremotesiteconfig.eom.StoredInstanceHost)objects.nextElement());
    }
  }

  public NSArray<com.dyned.woremotesiteconfig.eom.TimePointApplication> timePointApplications() {
    return (NSArray<com.dyned.woremotesiteconfig.eom.TimePointApplication>)storedValueForKey("timePointApplications");
  }

  public NSArray<com.dyned.woremotesiteconfig.eom.TimePointApplication> timePointApplications(EOQualifier qualifier) {
    return timePointApplications(qualifier, null, false);
  }

  public NSArray<com.dyned.woremotesiteconfig.eom.TimePointApplication> timePointApplications(EOQualifier qualifier, boolean fetch) {
    return timePointApplications(qualifier, null, fetch);
  }

  public NSArray<com.dyned.woremotesiteconfig.eom.TimePointApplication> timePointApplications(EOQualifier qualifier, NSArray<EOSortOrdering> sortOrderings, boolean fetch) {
    NSArray<com.dyned.woremotesiteconfig.eom.TimePointApplication> results;
    if (fetch) {
      EOQualifier fullQualifier;
      EOQualifier inverseQualifier = new EOKeyValueQualifier(com.dyned.woremotesiteconfig.eom.TimePointApplication.STORED_APP_KEY, EOQualifier.QualifierOperatorEqual, this);
    	
      if (qualifier == null) {
        fullQualifier = inverseQualifier;
      }
      else {
        NSMutableArray qualifiers = new NSMutableArray();
        qualifiers.addObject(qualifier);
        qualifiers.addObject(inverseQualifier);
        fullQualifier = new EOAndQualifier(qualifiers);
      }

      results = com.dyned.woremotesiteconfig.eom.TimePointApplication.fetchTimePointApplications(editingContext(), fullQualifier, sortOrderings);
    }
    else {
      results = timePointApplications();
      if (qualifier != null) {
        results = (NSArray<com.dyned.woremotesiteconfig.eom.TimePointApplication>)EOQualifier.filteredArrayWithQualifier(results, qualifier);
      }
      if (sortOrderings != null) {
        results = (NSArray<com.dyned.woremotesiteconfig.eom.TimePointApplication>)EOSortOrdering.sortedArrayUsingKeyOrderArray(results, sortOrderings);
      }
    }
    return results;
  }
  
  public void addToTimePointApplicationsRelationship(com.dyned.woremotesiteconfig.eom.TimePointApplication object) {
    if (_StoredApp.LOG.isDebugEnabled()) {
      _StoredApp.LOG.debug("adding " + object + " to timePointApplications relationship");
    }
    addObjectToBothSidesOfRelationshipWithKey(object, "timePointApplications");
  }

  public void removeFromTimePointApplicationsRelationship(com.dyned.woremotesiteconfig.eom.TimePointApplication object) {
    if (_StoredApp.LOG.isDebugEnabled()) {
      _StoredApp.LOG.debug("removing " + object + " from timePointApplications relationship");
    }
    removeObjectFromBothSidesOfRelationshipWithKey(object, "timePointApplications");
  }

  public com.dyned.woremotesiteconfig.eom.TimePointApplication createTimePointApplicationsRelationship() {
    EOClassDescription eoClassDesc = EOClassDescription.classDescriptionForEntityName("TimePointApplication");
    EOEnterpriseObject eo = eoClassDesc.createInstanceWithEditingContext(editingContext(), null);
    editingContext().insertObject(eo);
    addObjectToBothSidesOfRelationshipWithKey(eo, "timePointApplications");
    return (com.dyned.woremotesiteconfig.eom.TimePointApplication) eo;
  }

  public void deleteTimePointApplicationsRelationship(com.dyned.woremotesiteconfig.eom.TimePointApplication object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "timePointApplications");
    editingContext().deleteObject(object);
  }

  public void deleteAllTimePointApplicationsRelationships() {
    Enumeration objects = timePointApplications().immutableClone().objectEnumerator();
    while (objects.hasMoreElements()) {
      deleteTimePointApplicationsRelationship((com.dyned.woremotesiteconfig.eom.TimePointApplication)objects.nextElement());
    }
  }


  public static StoredApp createStoredApp(EOEditingContext editingContext, String name
, com.dyned.woremotesiteconfig.eom.StoredSite storedSite) {
    StoredApp eo = (StoredApp) EOUtilities.createAndInsertInstance(editingContext, _StoredApp.ENTITY_NAME);    
		eo.setName(name);
    eo.setStoredSiteRelationship(storedSite);
    return eo;
  }

  public static NSArray<StoredApp> fetchAllStoredApps(EOEditingContext editingContext) {
    return _StoredApp.fetchAllStoredApps(editingContext, null);
  }

  public static NSArray<StoredApp> fetchAllStoredApps(EOEditingContext editingContext, NSArray<EOSortOrdering> sortOrderings) {
    return _StoredApp.fetchStoredApps(editingContext, null, sortOrderings);
  }

  public static NSArray<StoredApp> fetchStoredApps(EOEditingContext editingContext, EOQualifier qualifier, NSArray<EOSortOrdering> sortOrderings) {
    EOFetchSpecification fetchSpec = new EOFetchSpecification(_StoredApp.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray<StoredApp> eoObjects = (NSArray<StoredApp>)editingContext.objectsWithFetchSpecification(fetchSpec);
    return eoObjects;
  }

  public static StoredApp fetchStoredApp(EOEditingContext editingContext, String keyName, Object value) {
    return _StoredApp.fetchStoredApp(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static StoredApp fetchStoredApp(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray<StoredApp> eoObjects = _StoredApp.fetchStoredApps(editingContext, qualifier, null);
    StoredApp eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = (StoredApp)eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one StoredApp that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static StoredApp fetchRequiredStoredApp(EOEditingContext editingContext, String keyName, Object value) {
    return _StoredApp.fetchRequiredStoredApp(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static StoredApp fetchRequiredStoredApp(EOEditingContext editingContext, EOQualifier qualifier) {
    StoredApp eoObject = _StoredApp.fetchStoredApp(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no StoredApp that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static StoredApp localInstanceIn(EOEditingContext editingContext, StoredApp eo) {
    StoredApp localInstance = (eo == null) ? null : (StoredApp)EOUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
