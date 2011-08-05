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

package com.dyned.woremotesiteconfig.eom;

import java.util.Iterator;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.apache.log4j.Logger;

import com.dyned.woremotesiteconfig.Application;
import com.dyned.woremotesiteconfig.WebPageFromURL;
import com.dyned.woremotesiteconfig.javamonitor.SiteApplication;
import com.webobjects.eocontrol.EOSortOrdering;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSMutableArray;

public class StoredSite extends _StoredSite {
	
	private static Logger log = Logger.getLogger(StoredSite.class);
	
	public NSArray<TimePoint> sortedTimePoints() {
		NSMutableArray<EOSortOrdering> sortOrderings = new NSMutableArray<EOSortOrdering>();
		sortOrderings.addObject(new EOSortOrdering(TimePoint.HOUR_KEY, EOSortOrdering.CompareAscending));
		sortOrderings.addObject(new EOSortOrdering(TimePoint.MIN_KEY, EOSortOrdering.CompareAscending));
		return this.timePoints(null, sortOrderings, true);
	}
	
	public void refreshApps() {
		/*
		 * curl -X GET http://monitorhost:port/cgi-bin/WebObjects/JavaMonitor.woa/ra/mApplications.json
		 */

		String theURLToGet = Application.jmRAURL;
		theURLToGet = theURLToGet.replace(Application.jmHostMatchString, this.jmHost());
		theURLToGet = theURLToGet.replace(Application.jmPortMatchString, this.jmPort());
		theURLToGet = theURLToGet.replace(Application.jmPasswordMatchString, this.jmPassword());
		theURLToGet = theURLToGet.replace(Application.jmRESTRouteMatchString, Application.jmAPPROUTE+".json");

		WebPageFromURL appsPage = new WebPageFromURL(theURLToGet, "GET", null);
		String jmResults = appsPage.content;

		log.debug("Results: " + jmResults);
		
		if (jmResults == null)
			return;

		JSONArray jsonApps =  (JSONArray)JSONSerializer.toJSON(jmResults);
		if (jsonApps.isArray()) {
			this.storedApps().takeValueForKey(false, "active");

			Iterator iterator = jsonApps.iterator();
			while (iterator.hasNext()) {
				JSONObject jsonApp = (JSONObject)iterator.next();
				SiteApplication siteApplication = new SiteApplication(jsonApp);

				StoredApp app = null;
				try {
					app = StoredApp.fetchStoredApp(this.editingContext(), StoredApp.NAME_KEY, siteApplication.name());
					if (app == null) {
						app = StoredApp.createStoredApp(this.editingContext(), siteApplication.name(), this);
						this.addToStoredAppsRelationship(app);
					}
					app.setActive(true);
		    	} catch (Exception ex) {
					log.error(ex.getMessage());
				}
			}
		}

	}
}
