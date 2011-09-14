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

import com.webobjects.appserver.WOContext;
import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.foundation.NSArray;

import er.extensions.components.ERXComponent;

import com.dyned.woremotesiteconfig.Session;
import com.dyned.woremotesiteconfig.eom.StoredApp;
import com.dyned.woremotesiteconfig.eom.StoredSite;

public class Main extends ERXComponent {
	
	private static Logger log = Logger.getLogger(Main.class);

	public Session session;
	public EOEditingContext ec;

	public String errorOnPage = null;
	public String newHostName = null;
		
	public StoredSite storedSiteItemInList = null;
	public StoredSite storedSiteForEditing = null;
	public StoredApp siteApplicationInList = null;

	public Main(WOContext context) {
		super(context);	
		session = ((Session)session());
		ec = session.defaultEditingContext();
		newHostName = new String();
	}
	
	public void awake() {
		super.awake();
		newHostName = "";
		errorOnPage = "";
	}
	
	public NSArray<StoredSite> storedSiteList() {
		return StoredSite.fetchAllStoredSites(ec);
	}
	
	public SiteApplicationInstancesConfiguration siteApplicationInstancesConfiguration() {		
		SiteApplicationInstancesConfiguration nextPage = (SiteApplicationInstancesConfiguration)pageWithName(SiteApplicationInstancesConfiguration.class);
		nextPage.setStoredSiteApplication(siteApplicationInList);
		return nextPage;
	}
	
	public SiteTimePoints siteTimePoints() {
		if (storedSiteItemInList.available()) {
		SiteTimePoints nextPage = (SiteTimePoints)pageWithName(SiteTimePoints.class);
		nextPage.setStoredSite(storedSiteItemInList);
		session.setStoredSite(storedSiteItemInList);
		return nextPage;
		} else
			return null;
	}
	
	public Main refreshAppsForSite() {
		storedSiteItemInList.refreshApps();
    	try {
    		ec.saveChanges();
    	} catch (Exception ex) {
    		ec.revert();
    		errorOnPage = "Failed to refresh apps: "+ex.getMessage();
			log.error(errorOnPage);
			ex.printStackTrace();
		}
		if (storedSiteItemInList.notAvailable()) {
			log.error("Refresh failed. Site may not be available.");
			errorOnPage = "Site '"+storedSiteItemInList.name()+"' may not be available.";
		}
		return null;
	}
	
	public Main deleteStoredAppFromStore() {
		storedSiteItemInList.removeFromStoredAppsRelationship(siteApplicationInList);
		ec.deleteObject(siteApplicationInList);
    	try {
    		ec.saveChanges();
    	} catch (Exception ex) {
    		ec.revert();
    		errorOnPage = "Failed to remove app: "+ex.getMessage();
			log.error(errorOnPage);
			ex.printStackTrace();
		}
		return null;
	}
	
	// ----------------------------------------------------------------------------------------------
	
	public Main addStoredSite() {
		if (newHostName != null && newHostName != "") {
			storedSiteForEditing = StoredSite.createStoredSite(ec, newHostName);
		} else {
			storedSiteForEditing = null;
			errorOnPage = "The host name can not be empty for new sites.";
		}
		return null;
	}
	
	public Main saveStoredSite() {
    	try {
    		ec.saveChanges();
    		storedSiteForEditing = null;
    	} catch (Exception ex) {
    		ec.revert();
    		errorOnPage = "Failed to save site: "+ex.getMessage();
			log.error(errorOnPage);
			ex.printStackTrace();
		}
		return null;
	}
	
	public Main cancelStoredSite() {
		ec.revert();
		storedSiteForEditing = null;
		return null;		
	}

	public Main editStoredSite() {
		storedSiteForEditing = storedSiteItemInList;
		return null;
	}
	
	public Main deleteStoredSite() {
		ec.deleteObject(storedSiteItemInList);
    	try {
    		ec.saveChanges();
    	} catch (Exception ex) {
    		ec.revert();
    		errorOnPage = "Failed to delete site: "+ex.getMessage();
			log.error(errorOnPage);
			ex.printStackTrace();
		}
		return null;
	}
	
	// ----------------------------------------------------------------------------------------------

}


/*
public Main getAppsList() {
	
	site = new Site(jmHost, jmPort, jmPassword);

	if (!coughtError) {
		theURLToGet = Application.jmRAURL;
		theURLToGet = theURLToGet.replace(Application.jmHostMatchString, site.jmHost());
		theURLToGet = theURLToGet.replace(Application.jmPortMatchString, site.jmPort());
		theURLToGet = theURLToGet.replace(Application.jmPasswordMatchString, site.jmPassword());
		theURLToGet = theURLToGet.replace(Application.jmRESTRouteMatchString, Application.jmAPPROUTE);

		log.info("URL: " + theURLToGet);

		WebPageFromURL appsPage = new WebPageFromURL(theURLToGet, "GET", null);
		jmResults = appsPage.content;

		JSONArray woApps =  (JSONArray)JSONSerializer.toJSON(jmResults);
		if (woApps.isArray()) {
			NSMutableArray<SiteApplication> appArray = new NSMutableArray<SiteApplication>();
			Iterator iterator = woApps.iterator();
			log.info("Start");
			while (iterator.hasNext()) {
				JSONObject woApp = (JSONObject)iterator.next();
				SiteApplication siteApplication = new SiteApplication(woApp);
				appArray.addObjects(siteApplication);
			}
			log.info("Done");
			site.setWOApplications(appArray); 
		} else {
			coughtError = true;
			errorOnPage = "Failed gettng applications from " + site.jmHost();
		}
	}
			
	if (!coughtError) {
		theURLToGet = Application.jmRAURL;
		theURLToGet = theURLToGet.replace(Application.jmHostMatchString, jmHost);
		theURLToGet = theURLToGet.replace(Application.jmPortMatchString, jmPort);
		theURLToGet = theURLToGet.replace(Application.jmPasswordMatchString, jmPassword);
		theURLToGet = theURLToGet.replace(Application.jmRESTRouteMatchString, Application.jmHOSTSROUTE);

		log.info("URL: " + theURLToGet);

		WebPageFromURL hostsPage = new WebPageFromURL(theURLToGet, "GET", null);
		jmResults = hostsPage.content;

		JSONArray woHosts =  (JSONArray)JSONSerializer.toJSON(jmResults);
		if (woHosts.isArray()) {
			NSMutableArray<SiteHost> hostArray = new NSMutableArray<SiteHost>();
			Iterator iterator = woHosts.iterator();
			log.info("Start");
			while (iterator.hasNext()) {
				JSONObject woApp = (JSONObject)iterator.next();
				SiteHost siteHost = new SiteHost(woApp);
				hostArray.addObjects(siteHost);
			}
			log.info("Done");
			site.setHosts(hostArray); 
		} else {
			coughtError = true;
			errorOnPage = "Results are not an array.";
		}
	}
	
	SiteHost newHost = new SiteHost(null, "MHost", "UNIX", "10.0.0.23", "10.0.0.23");
	site.sendNewHost(newHost);

	return null;
}
*/
