// $LastChangedRevision$ DO NOT EDIT.  Make changes to TimePoint.java instead.
package com.dyned.woremotesiteconfig.eom;

import com.webobjects.eoaccess.*;
import com.webobjects.eocontrol.*;
import com.webobjects.foundation.*;
import java.math.*;
import java.util.*;
import org.apache.log4j.Logger;

@SuppressWarnings("all")
public abstract class _TimePoint extends  EOGenericRecord {
	public static final String ENTITY_NAME = "TimePoint";

	// Attributes
	public static final String HOUR_KEY = "hour";
	public static final String MIN_KEY = "min";
	public static final String NOTES_KEY = "notes";
	public static final String TITLE_KEY = "title";

	// Relationships
	public static final String STORED_SITE_KEY = "storedSite";
	public static final String TIME_POINT_APPLICATIONS_KEY = "timePointApplications";

  private static Logger LOG = Logger.getLogger(_TimePoint.class);

  public TimePoint localInstanceIn(EOEditingContext editingContext) {
    TimePoint localInstance = (TimePoint)EOUtilities.localInstanceOfObject(editingContext, this);
    if (localInstance == null) {
      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
    }
    return localInstance;
  }

  public Integer hour() {
    return (Integer) storedValueForKey("hour");
  }

  public void setHour(Integer value) {
    if (_TimePoint.LOG.isDebugEnabled()) {
    	_TimePoint.LOG.debug( "updating hour from " + hour() + " to " + value);
    }
    takeStoredValueForKey(value, "hour");
  }

  public Integer min() {
    return (Integer) storedValueForKey("min");
  }

  public void setMin(Integer value) {
    if (_TimePoint.LOG.isDebugEnabled()) {
    	_TimePoint.LOG.debug( "updating min from " + min() + " to " + value);
    }
    takeStoredValueForKey(value, "min");
  }

  public String notes() {
    return (String) storedValueForKey("notes");
  }

  public void setNotes(String value) {
    if (_TimePoint.LOG.isDebugEnabled()) {
    	_TimePoint.LOG.debug( "updating notes from " + notes() + " to " + value);
    }
    takeStoredValueForKey(value, "notes");
  }

  public String title() {
    return (String) storedValueForKey("title");
  }

  public void setTitle(String value) {
    if (_TimePoint.LOG.isDebugEnabled()) {
    	_TimePoint.LOG.debug( "updating title from " + title() + " to " + value);
    }
    takeStoredValueForKey(value, "title");
  }

  public com.dyned.woremotesiteconfig.eom.StoredSite storedSite() {
    return (com.dyned.woremotesiteconfig.eom.StoredSite)storedValueForKey("storedSite");
  }

  public void setStoredSiteRelationship(com.dyned.woremotesiteconfig.eom.StoredSite value) {
    if (_TimePoint.LOG.isDebugEnabled()) {
      _TimePoint.LOG.debug("updating storedSite from " + storedSite() + " to " + value);
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
      EOQualifier inverseQualifier = new EOKeyValueQualifier(com.dyned.woremotesiteconfig.eom.TimePointApplication.TIME_POINT_KEY, EOQualifier.QualifierOperatorEqual, this);
    	
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
    if (_TimePoint.LOG.isDebugEnabled()) {
      _TimePoint.LOG.debug("adding " + object + " to timePointApplications relationship");
    }
    addObjectToBothSidesOfRelationshipWithKey(object, "timePointApplications");
  }

  public void removeFromTimePointApplicationsRelationship(com.dyned.woremotesiteconfig.eom.TimePointApplication object) {
    if (_TimePoint.LOG.isDebugEnabled()) {
      _TimePoint.LOG.debug("removing " + object + " from timePointApplications relationship");
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


  public static TimePoint createTimePoint(EOEditingContext editingContext, Integer hour
, Integer min
, String title
, com.dyned.woremotesiteconfig.eom.StoredSite storedSite) {
    TimePoint eo = (TimePoint) EOUtilities.createAndInsertInstance(editingContext, _TimePoint.ENTITY_NAME);    
		eo.setHour(hour);
		eo.setMin(min);
		eo.setTitle(title);
    eo.setStoredSiteRelationship(storedSite);
    return eo;
  }

  public static NSArray<TimePoint> fetchAllTimePoints(EOEditingContext editingContext) {
    return _TimePoint.fetchAllTimePoints(editingContext, null);
  }

  public static NSArray<TimePoint> fetchAllTimePoints(EOEditingContext editingContext, NSArray<EOSortOrdering> sortOrderings) {
    return _TimePoint.fetchTimePoints(editingContext, null, sortOrderings);
  }

  public static NSArray<TimePoint> fetchTimePoints(EOEditingContext editingContext, EOQualifier qualifier, NSArray<EOSortOrdering> sortOrderings) {
    EOFetchSpecification fetchSpec = new EOFetchSpecification(_TimePoint.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray<TimePoint> eoObjects = (NSArray<TimePoint>)editingContext.objectsWithFetchSpecification(fetchSpec);
    return eoObjects;
  }

  public static TimePoint fetchTimePoint(EOEditingContext editingContext, String keyName, Object value) {
    return _TimePoint.fetchTimePoint(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static TimePoint fetchTimePoint(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray<TimePoint> eoObjects = _TimePoint.fetchTimePoints(editingContext, qualifier, null);
    TimePoint eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = (TimePoint)eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one TimePoint that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static TimePoint fetchRequiredTimePoint(EOEditingContext editingContext, String keyName, Object value) {
    return _TimePoint.fetchRequiredTimePoint(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static TimePoint fetchRequiredTimePoint(EOEditingContext editingContext, EOQualifier qualifier) {
    TimePoint eoObject = _TimePoint.fetchTimePoint(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no TimePoint that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static TimePoint localInstanceIn(EOEditingContext editingContext, TimePoint eo) {
    TimePoint localInstance = (eo == null) ? null : (TimePoint)EOUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
