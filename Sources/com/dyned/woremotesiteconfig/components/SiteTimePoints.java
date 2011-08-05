/*

Copyright (c) 2011, DynEd International, Inc.
All rights reserved.

Redistribution and use in source and binary forms, with or without modification, 
are permitted provided that the following conditions are met:

	* Redistributions of source code must retain the above copyright notice, 
	  this list of conditions and the following disclaimer.

	* Redistributions in binary form must reproduce the above copyright notice, 
	  this list of conditions and the following disclaimer in the documentation 
	  and/or other materials provided with the distribution.

	* Neither the name of DynEd International, Inc. nor the names of its 
	  contributors may be used to endorse or promote products derived from this 
	  software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND 
ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED 
WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE 
DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR 
ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES 
(INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; 
LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON 
ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT 
(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS 
SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

*/

package com.dyned.woremotesiteconfig.components;

import org.apache.log4j.Logger;

import com.dyned.woremotesiteconfig.DirectAction;
import com.dyned.woremotesiteconfig.Session;
import com.dyned.woremotesiteconfig.eom.StoredApp;
import com.dyned.woremotesiteconfig.eom.StoredSite;
import com.dyned.woremotesiteconfig.eom.TimePoint;
import com.dyned.woremotesiteconfig.eom.TimePointApplication;
import com.dyned.woremotesiteconfig.javamonitor.Site;
import com.dyned.woremotesiteconfig.javamonitor.SiteApplication;
import com.dyned.woremotesiteconfig.javamonitor.SiteInstance;
import com.webobjects.appserver.WOContext;
import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSMutableDictionary;
import com.webobjects.foundation.NSTimestamp;

import er.extensions.components.ERXComponent;

public class SiteTimePoints extends ERXComponent {

	private static Logger log = Logger.getLogger(SiteTimePoints.class);

	public EOEditingContext ec;

	public String errorOnPage = null;

	public StoredSite storedSite = null;
	public TimePoint timePointForEditing = null;
	public TimePoint timePointInList = null;
	public TimePointApplication timePointApplicationInList = null;

	public String title = null;
	public Integer hour = 0;
	public Integer min = 0;
	public Integer instanceWeight  = null;

	public Site site;
	public NSArray<SiteApplication> siteApplications = null;
	public SiteApplication siteApplicationSelected = null;
	public SiteApplication siteApplicationInList = null;

	public boolean showCrontable = false;
	
    public SiteTimePoints(WOContext context) {
        super(context);
		ec = ((Session)session()).defaultEditingContext();
		errorOnPage = new String();
    }

	public void awake() {
		log.info("=================================== Awake");
		super.awake();
	}

	public void sleep() {
		log.info("=================================== Asleep");
		super.sleep();
		errorOnPage = "";
		title = "";
		hour = 0;
		min = 0;
	}

    // Page objects ------------------------------------------------------------------------------------------------------------

	public NSArray<TimePoint> timePoints() {
		if (storedSite != null) 
			return storedSite.sortedTimePoints(); //storedSite.timePoints();
		else
			return null;
	}

	public int rowspanForApps() {
		if (timePointInList != null && timePointInList.timePointApplications() != null)
			return timePointInList.timePointApplications().count()+1;
		else
			return 1;
	}

    public void setStoredSite(StoredSite aSiteForPage) {
    	if (aSiteForPage != null) {
    		storedSite = aSiteForPage;
    		site = new Site(storedSite.jmHost(), storedSite.jmPort(), storedSite.jmPassword(), storedSite);
    	}
    }

    public SiteTimePoints showFormForTimePointInList() {
    	timePointForEditing = timePointInList;
    	return null;
    }

    public boolean editTimePoint() {
    	return (timePointInList.equals(timePointForEditing));
    }

	public Main selectAnotherApp() {
		return pageWithName(Main.class);
	}
	
	public SiteTimePoints toggleShowCrontable() {
		showCrontable = !showCrontable;
		return null;
	}
	
	public String referenceDirectActionURL() {
    	NSMutableDictionary<String, Object> queryParams = new NSMutableDictionary<String, Object>();
    	queryParams.takeValueForKey(Boolean.FALSE, "wosid");
    	queryParams.takeValueForKey(timePointInList.reference(), DirectAction.REFERENCE_KEY);
    	return "http://"
    		+ context().request()._serverName()
    		+ ":" + application().port()
    		+ context().directActionURLForActionNamed("timepoint", queryParams);
	}
	
	public NSTimestamp currentDateTime() {
		return new NSTimestamp();
	}

    // Handle Time Points -------------------------------------------------------------------------------------------------------

    public SiteTimePoints addNewTimePoint() {

    	boolean noError = true;

    	if (hour < 0 || hour > 23 || min < 0 || min > 59) {
    		errorOnPage = "Please give me a proper time for the new time point. (HH: 0 - 23, MM: 0 - 59)";
    		return null;
    	}

    	TimePoint timePoint = TimePoint.createTimePoint(ec, hour, min, title, storedSite);
    	storedSite.addToTimePointsRelationship(timePoint);
    	try {
    		ec.saveChanges();
    	} catch (Exception ex) {
    		noError = false;
    		ec.revert();
			errorOnPage = "Failed to add time point: " + ex.getMessage();
			log.error(errorOnPage);
			ex.printStackTrace();
		}

    	if (noError) {
    		NSArray<StoredApp> storedApplications = storedSite.storedApps();
    		for (int appLoop = 0; appLoop < storedApplications.count(); appLoop++) {
    			TimePointApplication timePointApplication = TimePointApplication.createTimePointApplication(ec, Integer.valueOf(0), (StoredApp)storedApplications.objectAtIndex(appLoop), timePoint);
    			log.info("created timePointApplication:: "+timePointApplication);
    		}
    	}

    	try {
    		ec.saveChanges();
    	} catch (Exception ex) {
    		noError = false;
    		ec.revert();
			errorOnPage = "Failed to add time point: " + ex.getMessage();
			log.error(errorOnPage);
			ex.printStackTrace();
		}

    	return null;
    }
    
    public SiteTimePoints deleteTimePoint() {
    	ec.deleteObject(timePointInList);
    	try {
    		ec.saveChanges();
    	} catch (Exception ex) {
    		ec.revert();
    		errorOnPage = "Failed to delete time point: " + ex.getMessage();
			log.error(errorOnPage);
			ex.printStackTrace();
		}
    	return null;
    }
    
    public SiteTimePoints saveTimePointChange() {

    	if (timePointForEditing.hour() < 0 || timePointForEditing.hour() > 23 || timePointForEditing.min() < 0 || timePointForEditing.min() > 59) {
    		errorOnPage = "Please give me a proper time for the edited time point. (HH: 0 - 23, MM: 0 - 59)";
    		return null;	
    	}

    	try {
    		ec.saveChanges();
    	} catch (Exception ex) {
    		ec.revert();
			log.error("Failed to save time point change.");
			ex.printStackTrace();
		}
    	timePointForEditing = null;
    	return null;
    }
    
    public SiteTimePoints cancelTimePointChange() {
    	timePointForEditing = null;
    	return null;
    }

    public SiteTimePoints makeItSo() {
    	for (int siteAppIndex = 0; siteAppIndex < timePointInList.timePointApplications().count(); siteAppIndex++) {
    		TimePointApplication timePointApplication = (TimePointApplication)timePointInList.timePointApplications().objectAtIndex(siteAppIndex);
    		SiteApplication siteApplication = site.siteApplicationWithName(timePointApplication.storedApp().name());
    		NSArray<SiteInstance> siteInstancesForApplication = site.getSiteInstancesForSiteApplication(siteApplication);
    		site.sendExecuteTimePoint(siteApplication, siteInstancesForApplication, timePointApplication.instanceWeight());
    	}
    	//site.getSiteInstancesForSiteApplication(timePointInList.timePointApplications())
    	return null;
    }

    // Handle Apps for Time Points ---------------------------------------------------------------------------------------------

    public SiteTimePoints addTimePointApplication() {
    	TimePointApplication timePointApplication = new TimePointApplication();
    	timePointApplication.setInstanceWeight(instanceWeight);
    	timePointApplication.setStoredAppRelationship(StoredApp.fetchRequiredStoredApp(ec, StoredApp.NAME_KEY, siteApplicationSelected.name()));
    	return null;
    }

    public SiteTimePoints deleteTimePointApplication() {
    	timePointInList.removeFromTimePointApplicationsRelationship(timePointApplicationInList);
    	ec.deleteObject(timePointApplicationInList);
    	try {
    		ec.saveChanges();
    	} catch (Exception ex) {
    		ec.revert();
			log.error("Failed to delete time point application for "+storedSite.jmHost());
			ex.printStackTrace();
		}
    	return null;
    }
}
