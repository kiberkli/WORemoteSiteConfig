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

package com.dyned.woremotesiteconfig.javamonitor;

import java.util.Iterator;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.apache.log4j.Logger;

import com.dyned.woremotesiteconfig.Application;
import com.dyned.woremotesiteconfig.WebPageFromURL;
import com.dyned.woremotesiteconfig.eom.StoredApp;
import com.dyned.woremotesiteconfig.eom.StoredSite;
import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.eocontrol.EOGlobalID;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSMutableArray;

public class Site {

	private static Logger log = Logger.getLogger(Site.class);

	private String jmHost;
	private String jmPort;
	private String jmPassword;
	private NSMutableArray<SiteApplication> applications;
	private NSMutableArray<SiteHost> hosts; 
	private EOGlobalID storedSiteEOGlobalID;

	public Site(String newJMHost, String newJMPort, String newPassword, StoredSite aStoredSite) {
		super();

		jmHost = newJMHost;
		jmPort = newJMPort;
		jmPassword = newPassword;

		applications = null;
		hosts = null;

		storedSiteEOGlobalID = aStoredSite.editingContext().globalIDForObject(aStoredSite);

		retrieveSiteHostList();
		retrieveSiteApplicationList();

		log.debug("Added new site: " + jmHost);
	}

	// --------------------------------------------------------------------------------

	@SuppressWarnings("rawtypes")
	public void retrieveSiteHostList() {

		/*
		 * curl -X GET http://monitorhost:port/apps/WebObjects/JavaMonitor.woa/ra/mHosts.json
		 */

		String theURLToGet = Application.jmRAURL;
		theURLToGet = theURLToGet.replace(Application.jmHostMatchString, jmHost);
		if (this.jmPort() == null || this.jmPort().isEmpty()) 			theURLToGet = theURLToGet.replace(Application.jmPortMatchString, "80");
		else 															theURLToGet = theURLToGet.replace(Application.jmPortMatchString, jmPort);
		if (this.jmPassword() == null || this.jmPassword().isEmpty())	theURLToGet = theURLToGet.replace(Application.jmPasswordMatchString, "");
		else															theURLToGet = theURLToGet.replace(Application.jmPasswordMatchString, jmPassword);
		theURLToGet = theURLToGet.replace(Application.jmRESTRouteMatchString, Application.jmHOSTSROUTE+".json");

		log.info("URL: " + theURLToGet);

		WebPageFromURL hostsPage = new WebPageFromURL(theURLToGet, "GET", null);
		String jmResults = hostsPage.content;

		log.debug("Results: " + jmResults);

		if (jmResults != null && !jmResults.isEmpty()) {
		JSONArray jsonHosts =  (JSONArray)JSONSerializer.toJSON(jmResults);
		if (jsonHosts.isArray()) {
			NSMutableArray<SiteHost> hostArray = new NSMutableArray<SiteHost>();
			Iterator iterator = jsonHosts.iterator();
			log.info("Start");
			while (iterator.hasNext()) {
				JSONObject jsonHost = (JSONObject)iterator.next();
				SiteHost siteHost = new SiteHost(jsonHost);
				hostArray.addObjects(siteHost);
			}
			log.info("Done");
			hosts = new NSMutableArray<SiteHost>(hostArray); 
		}
	}
	}
	
	// --------------------------------------------------------------------------------

	@SuppressWarnings("rawtypes")
	public void retrieveSiteApplicationList() {

		/*
		 * curl -X GET http://monitorhost:port/cgi-bin/WebObjects/JavaMonitor.woa/ra/mApplications.json
		 */

		String theURLToGet = Application.jmRAURL;
		theURLToGet = theURLToGet.replace(Application.jmHostMatchString, jmHost());
		if (this.jmPort() == null || this.jmPort().isEmpty()) 			theURLToGet = theURLToGet.replace(Application.jmPortMatchString, "80");
		else 															theURLToGet = theURLToGet.replace(Application.jmPortMatchString, jmPort);
		if (this.jmPassword() == null || this.jmPassword().isEmpty())	theURLToGet = theURLToGet.replace(Application.jmPasswordMatchString, "");
		else															theURLToGet = theURLToGet.replace(Application.jmPasswordMatchString, jmPassword);
		theURLToGet = theURLToGet.replace(Application.jmRESTRouteMatchString, Application.jmAPPROUTE+".json");

		log.info("URL: " + theURLToGet);

		WebPageFromURL appsPage = new WebPageFromURL(theURLToGet, "GET", null);
		String jmResults = appsPage.content;

		log.debug("Results: " + jmResults);

		if (jmResults != null && !jmResults.isEmpty()) {
		JSONArray jsonApps =  (JSONArray)JSONSerializer.toJSON(jmResults);
		if (jsonApps.isArray()) {
			NSMutableArray<SiteApplication> appArray = new NSMutableArray<SiteApplication>();
			Iterator iterator = jsonApps.iterator();
			log.info("Start");
			while (iterator.hasNext()) {
				JSONObject jsonApp = (JSONObject)iterator.next();
				SiteApplication siteApplication = new SiteApplication(jsonApp);
				siteApplication.setParentSite(this);
				appArray.addObjects(siteApplication);
			}
			log.info("Done");
			applications = new NSMutableArray<SiteApplication>(appArray); 
		}
	}
	}

	// --------------------------------------------------------------------------------

	public SiteApplication siteApplicationWithName(String appName) {
		for (int i=0; i < this.applications().count(); i++) {
			if (applications().objectAtIndex(i).name().equalsIgnoreCase(appName)) return (SiteApplication)applications().objectAtIndex(i);
		}
		return null;
	}

	// --------------------------------------------------------------------------------

	@SuppressWarnings("rawtypes")
	public NSArray<SiteInstance> getSiteInstancesForSiteApplication(SiteApplication siteApplication) {
		NSMutableArray<SiteInstance> results = null;

		/*
		 * curl "http://oracle11:8080    /cgi-bin/WebObjects/JavaMonitor.woa/admin/info            ?pw=well2000    &type=app&name=Codes"
		 *       http://[JMHOST]:[JMPORT]/cgi-bin/WebObjects/JavaMonitor.woa/admin/[JMDiractAction]?pw=[JMPASSWORD]&
		 */

		String theURLToGet = Application.jmDAURL; 
		theURLToGet = theURLToGet.replace(Application.jmHostMatchString, this.jmHost);
		if (this.jmPort() == null || this.jmPort().isEmpty()) 			theURLToGet = theURLToGet.replace(Application.jmPortMatchString, "80");
		else 															theURLToGet = theURLToGet.replace(Application.jmPortMatchString, jmPort);
		if (this.jmPassword() == null || this.jmPassword().isEmpty())	theURLToGet = theURLToGet.replace(Application.jmPasswordMatchString, "");
		else															theURLToGet = theURLToGet.replace(Application.jmPasswordMatchString, jmPassword);
		theURLToGet = theURLToGet.replace(Application.jmDirectActionMatchString, "info");
		theURLToGet = theURLToGet + "type=app&name="+siteApplication.name();

		log.debug("URL: " + theURLToGet);

		WebPageFromURL webPageFromURL = new WebPageFromURL(theURLToGet, "GET", null);
		String jmResults = webPageFromURL.content;

		log.debug("Results: " + jmResults);

		if (jmResults != null && !jmResults.isEmpty()) {
		JSONArray jsonInst =  (JSONArray)JSONSerializer.toJSON(jmResults);
		if (jsonInst.isArray()) {
			results = new NSMutableArray<SiteInstance>();
			Iterator iterator = jsonInst.iterator();
			while (iterator.hasNext()) {
				JSONObject jsonObject = (JSONObject)iterator.next();
				SiteInstance siteInstance = new SiteInstance(jsonObject);
				results.addObject(siteInstance);
			}

		}
		return results.immutableClone();
		} else
			return null;
	}

	// --------------------------------------------------------------------------------

	public boolean sendNewInstancesForApplication(NSArray<SiteInstance> siteInstances, SiteApplication siteApplication) {
		boolean noError = true;
		for (int loop=0; loop<siteInstances.count();loop++) {
			SiteInstance siteInstance = siteInstances.objectAtIndex(loop);
			noError = _sendNewInstance(siteInstance, siteApplication);
		}
		_sendAutoRecoverOff(siteApplication, 0);
		return noError;
	}

	private boolean _sendNewInstance(SiteInstance siteInstance, SiteApplication siteApplication) {

		/*
		 * curl -X GET http://monitorhost:port/cgi-bin/WebObjects/JavaMonitor.woa/ra/mApplications/AjaxExample/addInstance&host=localhost
		 */

		boolean noError = true;

		String theURLToGet = Application.jmRAURL; 
		theURLToGet = theURLToGet.replace(Application.jmHostMatchString, jmHost);
		if (this.jmPort() == null || this.jmPort().isEmpty()) 			theURLToGet = theURLToGet.replace(Application.jmPortMatchString, "80");
		else 															theURLToGet = theURLToGet.replace(Application.jmPortMatchString, jmPort);
		if (this.jmPassword() == null || this.jmPassword().isEmpty())	theURLToGet = theURLToGet.replace(Application.jmPasswordMatchString, "");
		else															theURLToGet = theURLToGet.replace(Application.jmPasswordMatchString, jmPassword);
		theURLToGet = theURLToGet.replace(Application.jmRESTRouteMatchString, Application.jmAPPROUTE+"/"+siteApplication.name()+"/addInstance");
		theURLToGet = theURLToGet + "host="+siteInstance.host();

		WebPageFromURL hostsPage = new WebPageFromURL(theURLToGet, "GET", null);
		String jmResults = hostsPage.content;
		
		log.debug("Results: " + jmResults);		

		if (jmResults != null && !jmResults.isEmpty())
		return noError;
		else
			return false;
	}

	// --------------------------------------------------------------------------------
	// Deleting instances:

	public boolean sendDeleteInstancesForApplication(SiteApplication siteApplication) {
		boolean noError = true;
		NSArray<SiteInstance> siteInstances = getSiteInstancesForSiteApplication(siteApplication);
		if (siteInstances != null && siteInstances.count() > 0) {
			_sendDeleteInstances(siteInstances, siteApplication);
			log.debug("Waiting...");
			try {
				Thread.sleep(5000);
				log.debug("Confirming...");
				for (int checks = 0; checks < 5; checks++) {
					siteInstances = getSiteInstancesForSiteApplication(siteApplication);
					if (siteInstances == null || siteInstances.count() == 0)
						break;
					else
						noError = false;
					Thread.sleep(2000);
				}
				log.debug("...ok");
			} catch (InterruptedException ex) {
				log.error("Failed to confirm deleted instances for "+siteApplication.name());
				noError = false;
			}
		}
		return noError; // Return false on error.
	}

	private void _sendDeleteInstances(NSArray<SiteInstance> siteInstances, SiteApplication siteApplication) {
		for (int loop = 0; loop < siteInstances.count(); loop++) {
			_sendDeleteInstance(siteInstances.objectAtIndex(loop), siteApplication, false);
		}
	}

	private void _sendDeleteInstance(SiteInstance siteInstance, SiteApplication siteApplication, boolean forceQuit) {

		String theURLToGet = null;

		log.info("Deleting instance...");

		log.info("... turn scheduled off");
		_sendScheduleOff(siteApplication, Integer.valueOf(siteInstance.id()).intValue());

		log.info("... turn autorecover off");
		_sendAutoRecoverOff(siteApplication,  Integer.valueOf(siteInstance.id()).intValue());
		
		if (forceQuit) {
			log.info("... force quitting");
			_sendForceQuitInstance(siteApplication, Integer.valueOf(siteInstance.id()).intValue());
		} else {
			log.info("... simple quitting");
			_sendStopInstance(siteApplication, Integer.valueOf(siteInstance.id()).intValue());
		}

		log.info("... deleting");

		theURLToGet = Application.jmRAURL; 
		theURLToGet = theURLToGet.replace(Application.jmHostMatchString, jmHost);
		if (this.jmPort() == null || this.jmPort().isEmpty()) 			theURLToGet = theURLToGet.replace(Application.jmPortMatchString, "80");
		else 															theURLToGet = theURLToGet.replace(Application.jmPortMatchString, jmPort);
		if (this.jmPassword() == null || this.jmPassword().isEmpty())	theURLToGet = theURLToGet.replace(Application.jmPasswordMatchString, "");
		else															theURLToGet = theURLToGet.replace(Application.jmPasswordMatchString, jmPassword);
		theURLToGet = theURLToGet.replace(Application.jmRESTRouteMatchString, Application.jmAPPROUTE+"/"+siteApplication.name()+"/deleteInstance");
		theURLToGet = theURLToGet + "id="+siteInstance.id();

		log.debug("URL: " + theURLToGet);

		WebPageFromURL webPageFromURL4 = new WebPageFromURL(theURLToGet, "GET", null);
		String jmResults4 = webPageFromURL4.content;

		log.debug("Results: " + jmResults4);		
	}

	// --------------------------------------------------------------------------------

	public void sendNewHost(SiteHost newHost) {

		/* curl -X POST -d \
		 * 		"{id: 'otherserver.com',type: 'MHost', osType: 'MACOSX',address: '192.168.20.5', name: 'otherserver.com'}" \
		 * 		http://monitorhost:port/apps/WebObjects/JavaMonitor.woa/ra/mHosts.json
		 */

		String theURLToGet = Application.jmRAURL; 
		theURLToGet = theURLToGet.replace(Application.jmHostMatchString, jmHost);
		if (this.jmPort() == null || this.jmPort().isEmpty()) 			theURLToGet = theURLToGet.replace(Application.jmPortMatchString, "80");
		else 															theURLToGet = theURLToGet.replace(Application.jmPortMatchString, jmPort);
		if (this.jmPassword() == null || this.jmPassword().isEmpty())	theURLToGet = theURLToGet.replace(Application.jmPasswordMatchString, "");
		else															theURLToGet = theURLToGet.replace(Application.jmPasswordMatchString, jmPassword);
		theURLToGet = theURLToGet.replace(Application.jmRESTRouteMatchString, Application.jmHOSTSROUTE+".json");

		String postData = newHost.jsonString();

		log.debug("URL: " + theURLToGet);
		log.debug("Post data: " + postData);

		WebPageFromURL hostsPage = new WebPageFromURL(theURLToGet, "POST", postData);
		String jmResults = hostsPage.content;

		log.debug("Results: " + jmResults);

	}

	// --------------------------------------------------------------------------------

	public boolean sendExecuteTimePoint(SiteApplication siteApplication, NSArray<SiteInstance> configuredInstances, Integer weight) {
		boolean noError = true;

		int instanceID = 0;
		int instancesToRun = (int)((double)configuredInstances.count() * ( (double)weight.intValue() / 100));

		if (instancesToRun == 0) 
			instancesToRun = 1;

		log.debug("weight.intValue()           : "+weight.intValue());
		log.debug("configuredInstances.count() : "+configuredInstances.count());
		log.debug("instancesToRun              : "+instancesToRun);

		// Stop loop
		for (instanceID = instancesToRun+1; instanceID <= configuredInstances.count(); instanceID++) {
			SiteInstance thisInstance = configuredInstances.objectAtIndex(instanceID-1);			
			if (_sendScheduleOff(siteApplication, instanceID) &&  _sendAutoRecoverOff(siteApplication, instanceID))
				if (thisInstance.activeSessions() > 0)
					noError = _sendRefuseNewSessionsOn(siteApplication, instanceID); 
				else
					noError = _sendStopInstance(siteApplication, instanceID);
			else
				noError = false;
		}

		// Start loop
		if (noError) {
			for (instanceID = 1; instanceID <= instancesToRun; instanceID++) {
				noError = _sendStartInstance(siteApplication, instanceID);
				if (noError) {
					_sendAutoRecoverOn(siteApplication, instanceID);
					_sendScheduleOn(siteApplication, instanceID);
				}
			}
		}
		return noError;
	}

	// --------------------------------------------------------------------------------

	public boolean sendStopInstance(SiteApplication siteApplication, SiteInstance siteInstance) {
		boolean noError = true;
		int instanceNumber = Integer.parseInt(siteInstance.id());
		if (_sendScheduleOff(siteApplication, instanceNumber) && _sendAutoRecoverOff(siteApplication, instanceNumber)) {
			if (siteInstance.activeSessions() > 0)
				noError = _sendRefuseNewSessionsOn(siteApplication, instanceNumber);
			else
				noError = _sendStopInstance(siteApplication, instanceNumber);
		}
		return noError;
	}
	
	// --------------------------------------------------------------------------------

	public boolean sendStartInstance(SiteApplication siteApplication, SiteInstance siteInstance) {
		boolean noError = true;
		int instanceNumber = Integer.parseInt(siteInstance.id());
		noError = _sendStartInstance(siteApplication, instanceNumber);
		_sendAutoRecoverOn(siteApplication, instanceNumber);
		_sendScheduleOn(siteApplication, instanceNumber);
		return noError;		
	}
	
	// --------------------------------------------------------------------------------

	private boolean _sendRefuseNewSessionsOn(SiteApplication siteApplication, int instanceNumber) {
		return _sendGenericDirectAction(siteApplication, instanceNumber, "turnRefuseNewSessionsOn");
	}

	private boolean _sendForceQuitInstance(SiteApplication siteApplication, int instanceNumber) {
		return _sendGenericDirectAction(siteApplication, instanceNumber, "forceQuit");
	}
	
	private boolean _sendStopInstance(SiteApplication siteApplication, int instanceNumber) {
		return _sendGenericDirectAction(siteApplication, instanceNumber, "stop");
	}

	private boolean _sendStartInstance(SiteApplication siteApplication, int instanceNumber) {
		return _sendGenericDirectAction(siteApplication, instanceNumber, "start");
	}

	private boolean _sendScheduleOff(SiteApplication siteApplication, int instanceNumber) {
		return _sendGenericDirectAction(siteApplication, instanceNumber, "turnScheduledOff");
	}

	private boolean _sendScheduleOn(SiteApplication siteApplication, int instanceNumber) {
		return _sendGenericDirectAction(siteApplication, instanceNumber, "turnScheduledOn");
	}

	private boolean _sendAutoRecoverOff(SiteApplication siteApplication, int instanceNumber) {
		return _sendGenericDirectAction(siteApplication, instanceNumber, "turnAutoRecoverOff");
	}

	private boolean _sendAutoRecoverOn(SiteApplication siteApplication, int instanceNumber) {
		return _sendGenericDirectAction(siteApplication, instanceNumber, "turnAutoRecoverOn");
	}

	private boolean _sendGenericDirectAction(SiteApplication siteApplication, int instanceNumber, String directAction) {
		boolean noError = true;

		String theURLToGet = Application.jmDAURL; 
		theURLToGet = theURLToGet.replace(Application.jmHostMatchString, jmHost);
		if (this.jmPort() == null || this.jmPort().isEmpty()) 			theURLToGet = theURLToGet.replace(Application.jmPortMatchString, "80");
		else 															theURLToGet = theURLToGet.replace(Application.jmPortMatchString, jmPort);
		if (this.jmPassword() == null || this.jmPassword().isEmpty())	theURLToGet = theURLToGet.replace(Application.jmPasswordMatchString, "");
		else															theURLToGet = theURLToGet.replace(Application.jmPasswordMatchString, jmPassword);
		theURLToGet = theURLToGet.replace(Application.jmDirectActionMatchString, directAction);
		if (instanceNumber == 0)
			theURLToGet = theURLToGet + "type=app&name="+siteApplication.name();
		else
			theURLToGet = theURLToGet + "type=ins&name="+siteApplication.name()+ "-"+instanceNumber;

		log.debug("URL: " + theURLToGet);

		WebPageFromURL webPageFromURL = new WebPageFromURL(theURLToGet, "GET", null);
		String jmResults = webPageFromURL.content;

		log.debug("Results: " + jmResults);
		if (!jmResults.contains("OK")) 
			noError = false;

		if (!noError) log.info("Direct action: " + directAction + " failed");

		return noError;
	}

	// --------------------------------------------------------------------------------

	public boolean sendScheduleForApplicationInstances(SiteApplication siteApplication, String beginHour, String endHour, int weekDay, String hourlyInterval, String type) {
		log.debug("Type: "+type+" - Begin: "+beginHour+"- End: "+endHour+"- Day: "+weekDay+"- Hourly interval: "+hourlyInterval);

		boolean noError = true;

		if (_sendScheduleTypeForApplicationInstances(siteApplication, type)) {
			if ("DAILY".equals(type))
				noError = _sendSchduleRangeForApplicationInstances(siteApplication, "dailyScheduleRange", beginHour, endHour, weekDay, hourlyInterval);
			else if ("HOURLY".equals(type))
				noError = _sendSchduleRangeForApplicationInstances(siteApplication, "hourlyScheduleRange", beginHour, endHour, weekDay, hourlyInterval);
			else if ("WEEKLY".equals(type))
				noError = _sendSchduleRangeForApplicationInstances(siteApplication, "weeklyScheduleRange", beginHour, endHour, weekDay, hourlyInterval);
			else
				noError = false;
		}
		
		return noError;
	}
	
//	http://monitor.host:port/cgi-bin/WebObjects/JavaMonitor.woa/admin/scheduleType?type=app&name=AjaxExample&scheduleType=DAILY
//	http://monitor.host:port/cgi-bin/WebObjects/JavaMonitor.woa/admin/scheduleType?type=app&name=AjaxExample&scheduleType=HOURLY
//	http://monitor.host:port/cgi-bin/WebObjects/JavaMonitor.woa/admin/scheduleType?type=app&name=AjaxExample&scheduleType=WEEKLY

	private boolean _sendScheduleTypeForApplicationInstances(SiteApplication siteApplication, String type) {
		boolean noError = true;
		
		String theURLToGet = Application.jmDAURL;
		theURLToGet = theURLToGet.replace(Application.jmHostMatchString, jmHost);
		if (this.jmPort() == null || this.jmPort().isEmpty()) 			theURLToGet = theURLToGet.replace(Application.jmPortMatchString, "80");
		else 															theURLToGet = theURLToGet.replace(Application.jmPortMatchString, jmPort);
		if (this.jmPassword() == null || this.jmPassword().isEmpty())	theURLToGet = theURLToGet.replace(Application.jmPasswordMatchString, "");
		else															theURLToGet = theURLToGet.replace(Application.jmPasswordMatchString, jmPassword);
		theURLToGet = theURLToGet.replace(Application.jmDirectActionMatchString, "scheduleType");
		theURLToGet = theURLToGet + "type=app&name="+siteApplication.name();
		
		theURLToGet = theURLToGet + "&scheduleType="+type;

		log.debug("URL: " + theURLToGet);

		WebPageFromURL webPageFromURL = new WebPageFromURL(theURLToGet, "GET", null);
		String jmResults = webPageFromURL.content;

		log.debug("Results: " + jmResults);
		if (!jmResults.contains("OK")) 
			noError = false;

		if (!noError) log.info("Direct action: scheduleType failed");

		return noError;
	}
	
//	http://monitor.host:port/cgi-bin/WebObjects/JavaMonitor.woa/admin/dailyScheduleRange?type=app&name=AjaxExample&hourBegin=1&hourEnd=9
//	http://monitor.host:port/cgi-bin/WebObjects/JavaMonitor.woa/admin/hourlyScheduleRange?type=app&name=AjaxExample&hourBegin=1&hourEnd=9&interval=6
//	http://monitor.host:port/cgi-bin/WebObjects/JavaMonitor.woa/admin/weeklyScheduleRange?type=app&name=AjaxExample&hourBegin=1&hourEnd=9&weekDay=1

	private boolean _sendSchduleRangeForApplicationInstances(SiteApplication siteApplication, String directAction, String beginHour, String endHour, int weekDay, String hourlyInterval) {
		boolean noError = true;
		
		String theURLToGet = Application.jmDAURL;
		theURLToGet = theURLToGet.replace(Application.jmHostMatchString, jmHost);
		if (this.jmPort() == null || this.jmPort().isEmpty()) 			theURLToGet = theURLToGet.replace(Application.jmPortMatchString, "80");
		else 															theURLToGet = theURLToGet.replace(Application.jmPortMatchString, jmPort);
		if (this.jmPassword() == null || this.jmPassword().isEmpty())	theURLToGet = theURLToGet.replace(Application.jmPasswordMatchString, "");
		else															theURLToGet = theURLToGet.replace(Application.jmPasswordMatchString, jmPassword);
		theURLToGet = theURLToGet.replace(Application.jmDirectActionMatchString, directAction);
		theURLToGet = theURLToGet + "type=app&name="+siteApplication.name();
		
		theURLToGet = theURLToGet + "&hourBegin="+beginHour;
		theURLToGet = theURLToGet + "&hourEnd="+endHour;
		if ("hourlyScheduleRange".equals(directAction))
			theURLToGet = theURLToGet + "&interval="+hourlyInterval;
		if ("weeklyScheduleRange".equals(directAction))
			theURLToGet = theURLToGet + "&weekDay="+weekDay;

		log.debug("URL: " + theURLToGet);

		WebPageFromURL webPageFromURL = new WebPageFromURL(theURLToGet, "GET", null);
		String jmResults = webPageFromURL.content;

		log.debug("Results: " + jmResults);
		if (!jmResults.contains("OK")) 
			noError = false;

		if (!noError) log.info("Direct action: " + directAction + " failed");

		return noError;
	}

	// --------------------------------------------------------------------------------

	
	public void setHosts(NSArray<SiteHost> newHosts) {
		hosts = new NSMutableArray<SiteHost>(newHosts);
		log.debug("Added new hosts to " + jmHost + ": " + hosts.toString());
	}

	public void addHost(SiteHost newHost) {
		hosts.addObjects(newHost);
	}

	// --------------------------------------------------------------------------------
	public void setSiteApplications(NSArray<SiteApplication> newSiteApplication) {
		applications = new NSMutableArray<SiteApplication>(newSiteApplication);
		log.debug("Added new applications to " + jmHost + ".");
	}

	public void addSiteApplication(SiteApplication newSiteApplication) {
		applications.addObjects(newSiteApplication);
	}

	// --------------------------------------------------------------------------------

	public StoredSite storedSite(EOEditingContext ec) {
		return (StoredSite)ec.objectForGlobalID(storedSiteEOGlobalID);
	}
	
	public EOGlobalID storedSiteEOGlobalID() {
		return storedSiteEOGlobalID;
	}
	
	public String jmHost() {
		return jmHost;
	}

	public String jmPort() {
		return jmPort;
	}

	public String jmPassword() {
		return jmPassword;
	}

	public NSArray<SiteApplication> applications() {
		return applications.immutableClone();
	}

	public NSArray<SiteHost> hosts() {
		if (hosts != null) return hosts.immutableClone();
		else return null;
	}
}
