// $LastChangedRevision$ DO NOT EDIT.  Make changes to StoredSite.java instead.
package com.dyned.woremotesiteconfig.eom;

import com.webobjects.eoaccess.*;
import com.webobjects.eocontrol.*;
import com.webobjects.foundation.*;
import java.math.*;
import java.util.*;
import org.apache.log4j.Logger;

@SuppressWarnings("all")
public abstract class _StoredSite extends  EOGenericRecord {
	public static final String ENTITY_NAME = "StoredSite";

	// Attributes
	public static final String JM_HOST_KEY = "jmHost";
	public static final String JM_PASSWORD_KEY = "jmPassword";
	public static final String JM_PORT_KEY = "jmPort";
	public static final String NAME_KEY = "name";
	public static final String NOTES_KEY = "notes";

	// Relationships
	public static final String STORED_APPS_KEY = "storedApps";
	public static final String TIME_POINTS_KEY = "timePoints";

  private static Logger LOG = Logger.getLogger(_StoredSite.class);

  public StoredSite localInstanceIn(EOEditingContext editingContext) {
    StoredSite localInstance = (StoredSite)EOUtilities.localInstanceOfObject(editingContext, this);
    if (localInstance == null) {
      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
    }
    return localInstance;
  }

  public String jmHost() {
    return (String) storedValueForKey("jmHost");
  }

  public void setJmHost(String value) {
    if (_StoredSite.LOG.isDebugEnabled()) {
    	_StoredSite.LOG.debug( "updating jmHost from " + jmHost() + " to " + value);
    }
    takeStoredValueForKey(value, "jmHost");
  }

  public String jmPassword() {
    return (String) storedValueForKey("jmPassword");
  }

  public void setJmPassword(String value) {
    if (_StoredSite.LOG.isDebugEnabled()) {
    	_StoredSite.LOG.debug( "updating jmPassword from " + jmPassword() + " to " + value);
    }
    takeStoredValueForKey(value, "jmPassword");
  }

  public String jmPort() {
    return (String) storedValueForKey("jmPort");
  }

  public void setJmPort(String value) {
    if (_StoredSite.LOG.isDebugEnabled()) {
    	_StoredSite.LOG.debug( "updating jmPort from " + jmPort() + " to " + value);
    }
    takeStoredValueForKey(value, "jmPort");
  }

  public String name() {
    return (String) storedValueForKey("name");
  }

  public void setName(String value) {
    if (_StoredSite.LOG.isDebugEnabled()) {
    	_StoredSite.LOG.debug( "updating name from " + name() + " to " + value);
    }
    takeStoredValueForKey(value, "name");
  }

  public String notes() {
    return (String) storedValueForKey("notes");
  }

  public void setNotes(String value) {
    if (_StoredSite.LOG.isDebugEnabled()) {
    	_StoredSite.LOG.debug( "updating notes from " + notes() + " to " + value);
    }
    takeStoredValueForKey(value, "notes");
  }

  public NSArray<com.dyned.woremotesiteconfig.eom.StoredApp> storedApps() {
    return (NSArray<com.dyned.woremotesiteconfig.eom.StoredApp>)storedValueForKey("storedApps");
  }

  public NSArray<com.dyned.woremotesiteconfig.eom.StoredApp> storedApps(EOQualifier qualifier) {
    return storedApps(qualifier, null, false);
  }

  public NSArray<com.dyned.woremotesiteconfig.eom.StoredApp> storedApps(EOQualifier qualifier, boolean fetch) {
    return storedApps(qualifier, null, fetch);
  }

  public NSArray<com.dyned.woremotesiteconfig.eom.StoredApp> storedApps(EOQualifier qualifier, NSArray<EOSortOrdering> sortOrderings, boolean fetch) {
    NSArray<com.dyned.woremotesiteconfig.eom.StoredApp> results;
    if (fetch) {
      EOQualifier fullQualifier;
      EOQualifier inverseQualifier = new EOKeyValueQualifier(com.dyned.woremotesiteconfig.eom.StoredApp.STORED_SITE_KEY, EOQualifier.QualifierOperatorEqual, this);
    	
      if (qualifier == null) {
        fullQualifier = inverseQualifier;
      }
      else {
        NSMutableArray qualifiers = new NSMutableArray();
        qualifiers.addObject(qualifier);
        qualifiers.addObject(inverseQualifier);
        fullQualifier = new EOAndQualifier(qualifiers);
      }

      results = com.dyned.woremotesiteconfig.eom.StoredApp.fetchStoredApps(editingContext(), fullQualifier, sortOrderings);
    }
    else {
      results = storedApps();
      if (qualifier != null) {
        results = (NSArray<com.dyned.woremotesiteconfig.eom.StoredApp>)EOQualifier.filteredArrayWithQualifier(results, qualifier);
      }
      if (sortOrderings != null) {
        results = (NSArray<com.dyned.woremotesiteconfig.eom.StoredApp>)EOSortOrdering.sortedArrayUsingKeyOrderArray(results, sortOrderings);
      }
    }
    return results;
  }
  
  public void addToStoredAppsRelationship(com.dyned.woremotesiteconfig.eom.StoredApp object) {
    if (_StoredSite.LOG.isDebugEnabled()) {
      _StoredSite.LOG.debug("adding " + object + " to storedApps relationship");
    }
    addObjectToBothSidesOfRelationshipWithKey(object, "storedApps");
  }

  public void removeFromStoredAppsRelationship(com.dyned.woremotesiteconfig.eom.StoredApp object) {
    if (_StoredSite.LOG.isDebugEnabled()) {
      _StoredSite.LOG.debug("removing " + object + " from storedApps relationship");
    }
    removeObjectFromBothSidesOfRelationshipWithKey(object, "storedApps");
  }

  public com.dyned.woremotesiteconfig.eom.StoredApp createStoredAppsRelationship() {
    EOClassDescription eoClassDesc = EOClassDescription.classDescriptionForEntityName("StoredApp");
    EOEnterpriseObject eo = eoClassDesc.createInstanceWithEditingContext(editingContext(), null);
    editingContext().insertObject(eo);
    addObjectToBothSidesOfRelationshipWithKey(eo, "storedApps");
    return (com.dyned.woremotesiteconfig.eom.StoredApp) eo;
  }

  public void deleteStoredAppsRelationship(com.dyned.woremotesiteconfig.eom.StoredApp object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "storedApps");
    editingContext().deleteObject(object);
  }

  public void deleteAllStoredAppsRelationships() {
    Enumeration objects = storedApps().immutableClone().objectEnumerator();
    while (objects.hasMoreElements()) {
      deleteStoredAppsRelationship((com.dyned.woremotesiteconfig.eom.StoredApp)objects.nextElement());
    }
  }

  public NSArray<com.dyned.woremotesiteconfig.eom.TimePoint> timePoints() {
    return (NSArray<com.dyned.woremotesiteconfig.eom.TimePoint>)storedValueForKey("timePoints");
  }

  public NSArray<com.dyned.woremotesiteconfig.eom.TimePoint> timePoints(EOQualifier qualifier) {
    return timePoints(qualifier, null, false);
  }

  public NSArray<com.dyned.woremotesiteconfig.eom.TimePoint> timePoints(EOQualifier qualifier, boolean fetch) {
    return timePoints(qualifier, null, fetch);
  }

  public NSArray<com.dyned.woremotesiteconfig.eom.TimePoint> timePoints(EOQualifier qualifier, NSArray<EOSortOrdering> sortOrderings, boolean fetch) {
    NSArray<com.dyned.woremotesiteconfig.eom.TimePoint> results;
    if (fetch) {
      EOQualifier fullQualifier;
      EOQualifier inverseQualifier = new EOKeyValueQualifier(com.dyned.woremotesiteconfig.eom.TimePoint.STORED_SITE_KEY, EOQualifier.QualifierOperatorEqual, this);
    	
      if (qualifier == null) {
        fullQualifier = inverseQualifier;
      }
      else {
        NSMutableArray qualifiers = new NSMutableArray();
        qualifiers.addObject(qualifier);
        qualifiers.addObject(inverseQualifier);
        fullQualifier = new EOAndQualifier(qualifiers);
      }

      results = com.dyned.woremotesiteconfig.eom.TimePoint.fetchTimePoints(editingContext(), fullQualifier, sortOrderings);
    }
    else {
      results = timePoints();
      if (qualifier != null) {
        results = (NSArray<com.dyned.woremotesiteconfig.eom.TimePoint>)EOQualifier.filteredArrayWithQualifier(results, qualifier);
      }
      if (sortOrderings != null) {
        results = (NSArray<com.dyned.woremotesiteconfig.eom.TimePoint>)EOSortOrdering.sortedArrayUsingKeyOrderArray(results, sortOrderings);
      }
    }
    return results;
  }
  
  public void addToTimePointsRelationship(com.dyned.woremotesiteconfig.eom.TimePoint object) {
    if (_StoredSite.LOG.isDebugEnabled()) {
      _StoredSite.LOG.debug("adding " + object + " to timePoints relationship");
    }
    addObjectToBothSidesOfRelationshipWithKey(object, "timePoints");
  }

  public void removeFromTimePointsRelationship(com.dyned.woremotesiteconfig.eom.TimePoint object) {
    if (_StoredSite.LOG.isDebugEnabled()) {
      _StoredSite.LOG.debug("removing " + object + " from timePoints relationship");
    }
    removeObjectFromBothSidesOfRelationshipWithKey(object, "timePoints");
  }

  public com.dyned.woremotesiteconfig.eom.TimePoint createTimePointsRelationship() {
    EOClassDescription eoClassDesc = EOClassDescription.classDescriptionForEntityName("TimePoint");
    EOEnterpriseObject eo = eoClassDesc.createInstanceWithEditingContext(editingContext(), null);
    editingContext().insertObject(eo);
    addObjectToBothSidesOfRelationshipWithKey(eo, "timePoints");
    return (com.dyned.woremotesiteconfig.eom.TimePoint) eo;
  }

  public void deleteTimePointsRelationship(com.dyned.woremotesiteconfig.eom.TimePoint object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "timePoints");
    editingContext().deleteObject(object);
  }

  public void deleteAllTimePointsRelationships() {
    Enumeration objects = timePoints().immutableClone().objectEnumerator();
    while (objects.hasMoreElements()) {
      deleteTimePointsRelationship((com.dyned.woremotesiteconfig.eom.TimePoint)objects.nextElement());
    }
  }


  public static StoredSite createStoredSite(EOEditingContext editingContext, String jmHost
) {
    StoredSite eo = (StoredSite) EOUtilities.createAndInsertInstance(editingContext, _StoredSite.ENTITY_NAME);    
		eo.setJmHost(jmHost);
    return eo;
  }

  public static NSArray<StoredSite> fetchAllStoredSites(EOEditingContext editingContext) {
    return _StoredSite.fetchAllStoredSites(editingContext, null);
  }

  public static NSArray<StoredSite> fetchAllStoredSites(EOEditingContext editingContext, NSArray<EOSortOrdering> sortOrderings) {
    return _StoredSite.fetchStoredSites(editingContext, null, sortOrderings);
  }

  public static NSArray<StoredSite> fetchStoredSites(EOEditingContext editingContext, EOQualifier qualifier, NSArray<EOSortOrdering> sortOrderings) {
    EOFetchSpecification fetchSpec = new EOFetchSpecification(_StoredSite.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray<StoredSite> eoObjects = (NSArray<StoredSite>)editingContext.objectsWithFetchSpecification(fetchSpec);
    return eoObjects;
  }

  public static StoredSite fetchStoredSite(EOEditingContext editingContext, String keyName, Object value) {
    return _StoredSite.fetchStoredSite(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static StoredSite fetchStoredSite(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray<StoredSite> eoObjects = _StoredSite.fetchStoredSites(editingContext, qualifier, null);
    StoredSite eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = (StoredSite)eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one StoredSite that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static StoredSite fetchRequiredStoredSite(EOEditingContext editingContext, String keyName, Object value) {
    return _StoredSite.fetchRequiredStoredSite(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static StoredSite fetchRequiredStoredSite(EOEditingContext editingContext, EOQualifier qualifier) {
    StoredSite eoObject = _StoredSite.fetchStoredSite(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no StoredSite that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static StoredSite localInstanceIn(EOEditingContext editingContext, StoredSite eo) {
    StoredSite localInstance = (eo == null) ? null : (StoredSite)EOUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
