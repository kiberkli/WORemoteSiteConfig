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

import com.dyned.woremotesiteconfig.Session;
import com.dyned.woremotesiteconfig.eom.StoredApp;
import com.dyned.woremotesiteconfig.eom.StoredInstanceHost;
import com.dyned.woremotesiteconfig.eom.StoredSite;
import com.dyned.woremotesiteconfig.javamonitor.Site;
import com.dyned.woremotesiteconfig.javamonitor.SiteApplication;
import com.dyned.woremotesiteconfig.javamonitor.SiteHost;
import com.dyned.woremotesiteconfig.javamonitor.SiteInstance;
import com.dyned.woremotesiteconfig.tasks.SendNewInstances;
import com.dyned.woremotesiteconfig.tasks.SendStartInstances;
import com.dyned.woremotesiteconfig.tasks.SendStopInstances;

import er.coolcomponents.CCAjaxLongResponsePage;

import com.webobjects.appserver.WOActionResults;
import com.webobjects.appserver.WOContext;
import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSMutableArray;

import er.extensions.components.ERXComponent;

public class SiteApplicationInstancesConfiguration extends ERXComponent {

	private static Logger log = Logger.getLogger(SiteApplicationInstancesConfiguration.class);

	private boolean inBackground = true;
	public EOEditingContext ec;

	public String errorOnPage = null;

	public StoredSite storedSite = null;
	public StoredApp storedApp = null;

	public Site site = null;
	public SiteApplication siteApplication = null;

	public NSMutableArray<StoredInstanceHost> _instanceHostList = null;
	public StoredInstanceHost instanceHostItemInList = null;

	public NSArray<SiteHost> siteHostList;
	public SiteHost siteHostItemInList;
	public SiteHost siteHostSelected;

	public NSArray<SiteInstance> applicationInstances;
	
	public Integer instances = null;
	

	public NSArray<String> daysOfWeek = new NSArray<String>("Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Staturday");
	public String dayOfWeekItemInlist;

	public NSArray<String> hoursOfDay = new NSArray<String>("00", "01", "02", "03", "04", "05", "06", "07", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23");
	public String hourOfDayItemInList1;
	public String hourOfDayItemInList2;

	public NSArray<String> intervals = new NSArray<String>("1", "2", "3", "4", "6", "8", "12");
	public String intervalItemInList;

	public SiteApplicationInstancesConfiguration(WOContext context) {
		super(context);
		ec = ((Session)session()).defaultEditingContext();

		_instanceHostList = new NSMutableArray<StoredInstanceHost>();
		errorOnPage = new String();
	}

	// --------------------------------------------------------------------------------

	public void awake() {
		super.awake();
		instances = 0;
		applicationInstances = null;
	}

	public void sleep() {
		super.sleep();
	}

	// --------------------------------------------------------------------------------

	public void setStoredSiteApplication(StoredApp aStoredApplication) {
		if (aStoredApplication != null) {
			storedApp = aStoredApplication;
			_instanceHostList = storedApp.storedInstanceHosts().mutableClone();
			siteApplication = new SiteApplication(storedApp.name());
			storedSite = storedApp.storedSite();
			
			site = new Site(storedSite.jmHost(), storedSite.jmPort(), storedSite.jmPassword(), storedSite);
			siteHostList = site.hosts();
		}
	}
	
	// --------------------------------------------------------------------------------

	public void applyScheduleToSystem() {
		int weekDay = daysOfWeek.indexOf(storedApp.scheduleWeekDay());
		if (site.sendScheduleForApplicationInstances(siteApplication, storedApp.scheduleBeginHour(), storedApp.scheduleEndHour(), weekDay, storedApp.scheduleHourlyInterval(), storedApp.scheduleType())) {
			log.info("Sent schedule to site.");
		} else {
			errorOnPage = "Sending schedule to site failed.";
			log.error(errorOnPage);
		}
	}
	
	// --------------------------------------------------------------------------------

	public WOActionResults startInstancesOnNode() {
		boolean noError = true;
		String host = instanceHostItemInList.hostName();
		
		if (inBackground) {
			Runnable task = new SendStartInstances(site, siteApplication, host);
			
			CCAjaxLongResponsePage nextPage = pageWithName(CCAjaxLongResponsePage.class);
			nextPage.setTask(task);

			return nextPage;
		} else {
			NSArray<SiteInstance> siteInstances = site.getSiteInstancesForSiteApplication(siteApplication);
			for (SiteInstance siteInstance : siteInstances) {
				if (siteInstance.host().equalsIgnoreCase(host)) {
					log.info("Starting instance on " + host);
					noError = site.sendStartInstance(siteApplication, siteInstance);
				}
			}
			if (!noError)
				errorOnPage = "Some instances had problems starting.";
		} 
		
		return null;
	}
	
	public WOActionResults stopInstancesOnNode() {
		boolean noError = true;
		String host = instanceHostItemInList.hostName();

		if (inBackground) {
			Runnable task = new SendStopInstances(site, siteApplication, host);

			CCAjaxLongResponsePage nextPage = pageWithName(CCAjaxLongResponsePage.class);
			nextPage.setTask(task);

			return nextPage;
		} else {
			NSArray<SiteInstance> siteInstances = site.getSiteInstancesForSiteApplication(siteApplication);
			for (SiteInstance siteInstance : siteInstances) {
				if (siteInstance.host().equalsIgnoreCase(host)) {
					log.info("Stopping instance on " + host);
					noError = site.sendStopInstance(siteApplication, siteInstance);
				}
			}
			if (!noError)
				errorOnPage = "Some instances had problems stopping.";
		}

		return null;
	}

	// -----------------------------------------------------------------------------------------------------------
	
	public SiteApplicationInstancesConfiguration addFormDataToArray() {
		errorOnPage = "";

		String hostName = null;

		if (siteHostSelected != null)
			hostName = siteHostSelected.name();
		else {
			errorOnPage = "No host/node selected.";
			log.error(errorOnPage);
			return null;
		}

		if (instances != null && instances.intValue() > 0) {
			StoredInstanceHost newInstanceHost = StoredInstanceHost.createStoredInstanceHost(ec, hostName, instances, storedApp);
			_instanceHostList.addObject(newInstanceHost);
		} else {
			errorOnPage = "The number of instances for a node can not be 0.";
			log.error(errorOnPage);
		}
		return null;
	}

	public SiteApplicationInstancesConfiguration removeFromArray() {
		errorOnPage = "";
		_instanceHostList.removeObject(instanceHostItemInList);
		instanceHostItemInList.storedApp().removeFromStoredInstanceHostsRelationship(instanceHostItemInList);
		ec.deleteObject(instanceHostItemInList);
		return null;
	}

	// --------------------------------------------------------------------------------

	public SiteApplicationInstancesConfiguration saveInstanceNodesToDatabase() {
		boolean noError = true;

		noError = _saveInstanceNodesToDatabase();
		if (!noError) 
			errorOnPage = "Unable to save changes to database.";

		return null;
	}

	// --------------------------------------------------------------------------------

	public WOActionResults commitInstanceNodesToSystem() {

		if (inBackground) {
			Runnable task = new SendNewInstances(site, siteApplication, _arrayOfInstances());

			CCAjaxLongResponsePage nextPage = pageWithName(CCAjaxLongResponsePage.class);
			nextPage.setTask(task);

			return nextPage;
		} else {
			boolean noError = _sendNewBatchInstances();
			if (!noError)
				errorOnPage = "Some or all instances where not updated or this process was interrupted, please try again."; //
		}

		return null;
	}

	// --------------------------------------------------------------------------------

	private boolean _saveInstanceNodesToDatabase() {
		boolean noError = true;
		try {
			ec.saveChanges();
		} catch (Exception ex) {
			noError = false;
			log.error(ex.getMessage());
		}
		return noError;
	}


	private boolean _sendNewBatchInstances() {
		boolean noError = true;

		
		// Delete any existing instances:
		log.info("############################## Start deleting existing instances...");
		noError = site.sendDeleteInstancesForApplication(siteApplication);
		log.info("############################## ...done");

		if (noError) {

			log.info("############################## Start processing new instances...");

			log.info("############################## Sending new instances to site.");
			// Send instances to site
			noError = site.sendNewInstancesForApplication(_arrayOfInstances(), siteApplication);
			log.info("############################## ...done");

		}

		return noError;
	}

	private NSArray<SiteInstance> _arrayOfInstances() {
		int interleave = storedApp.instanceInterleave().intValue();
		int totalInstances = totalInstances();
		int totalInstancesIndex = 1;
		NSArray<StoredInstanceHost> instanceHostsList = instanceHostsList();
		NSMutableArray<SiteInstance> siteAppInstances = new NSMutableArray<SiteInstance>();

		instanceHostsList.takeValueForKey(0, "instancesAllocated");
		
		do {
			for (int instanceHostListIndex = 0; instanceHostListIndex < instanceHostsList.count(); instanceHostListIndex++) {
				StoredInstanceHost instanceHost = instanceHostsList.objectAtIndex(instanceHostListIndex);
				for (int interleaveIndex = 1; interleaveIndex <= interleave; interleaveIndex++) {
					if (instanceHost.instancesAllocated() < instanceHost.instances()) {
						log.debug("Working on " + totalInstancesIndex);
						SiteInstance siteInstance = new SiteInstance(instanceHost.hostName());
						siteAppInstances.addObject(siteInstance);
						
						instanceHost.setInstancesAllocated(instanceHost.instancesAllocated()+1);
						totalInstancesIndex++;
						
						if (totalInstancesIndex > totalInstances) return siteAppInstances;
					}
				}
				if (instanceHostListIndex >= instanceHostsList.count()) {
					instanceHostListIndex = 0;
					log.debug("Reset host array list.");
				}
			}
		} while (totalInstancesIndex <= totalInstances);
		return siteAppInstances;
	}
	
	// --------------------------------------------------------------------------------

	public NSArray<StoredInstanceHost> instanceHostsList() {
		return _instanceHostList.immutableClone();
	}

	public Integer totalInstances() {
		int instanceHostsListCount = instanceHostsList().count();
		int instanceCount = 0;
		for (int i=0; i<instanceHostsListCount; i++) {
			StoredInstanceHost instanceHost = (StoredInstanceHost)instanceHostsList().objectAtIndex(i);
			instanceCount = instanceCount + instanceHost.instances().intValue();
		}

		return Integer.valueOf(instanceCount);
	}

	public int runningInstancesForHost() {
		
		if (applicationInstances == null)
			applicationInstances = site.getSiteInstancesForSiteApplication(siteApplication);

		if (applicationInstances == null)
			return -1;
		
		String host = instanceHostItemInList.hostName();
		int count = 0;

		for (SiteInstance siteInstance : applicationInstances ) {
			if (siteInstance.host().equalsIgnoreCase(host) && siteInstance.isAlive()) {
				count++;
			}			
		}

		return count;		
	}

	public Main selectAnotherApp() {
		return pageWithName(Main.class);
	}

	// --------------------------------------------------------------------------------
	// --------------------------------------------------------------------------------

	
//	public void setSiteApplication(SiteApplication aSiteApplication) {
//
//		log.info("Setting application " + aSiteApplication.name() + " for page.");
//
//		siteApplication = aSiteApplication;
//
//		site = siteApplication.parentSite();
//		siteHostList = site.hosts();
//		storedSite = site.storedSite();
//
//		storedApp = null;
//
//		try {
//			EOQualifier qualifier = EOQualifier.qualifierWithQualifierFormat(StoredApp.STORED_SITE_KEY+" = %@ and " + StoredApp.NAME_KEY+" = '" + aSiteApplication.name() + "'", new NSArray<StoredSite>(storedSite));
//			storedApp = StoredApp.fetchStoredApp(ec, qualifier);
//			_instanceList = storedApp.storedInstanceHosts().mutableClone();
//
//		} catch (MoreThanOneException ex) {
//			errorOnPage = "Found more then one app, this needs to be fixed.";
//			log.error(errorOnPage);
//		} catch (Exception ex) {
//			log.error("Failed getting site or app from database, it may not exist.");
//		}
//
//		if (storedApp == null)
//			storedApp = StoredApp.createStoredApp(ec, siteApplication.name(), storedSite);
//	}

}