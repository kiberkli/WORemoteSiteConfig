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

package com.dyned.woremotesiteconfig;

import com.webobjects.appserver.WOActionResults;
import com.webobjects.appserver.WORequest;
import com.webobjects.foundation.NSArray;

import er.extensions.appserver.ERXDirectAction;
import er.extensions.eof.ERXEC;

import com.dyned.woremotesiteconfig.components.Main;
import com.dyned.woremotesiteconfig.components.TimePointResults;
import com.dyned.woremotesiteconfig.eom.StoredSite;
import com.dyned.woremotesiteconfig.eom.TimePoint;
import com.dyned.woremotesiteconfig.eom.TimePointApplication;
import com.dyned.woremotesiteconfig.javamonitor.Site;
import com.dyned.woremotesiteconfig.javamonitor.SiteApplication;
import com.dyned.woremotesiteconfig.javamonitor.SiteInstance;

public class DirectAction extends ERXDirectAction {
	
	public static String REFERENCE_KEY = "reference";

	public DirectAction(WORequest request) {
		super(request);
	}

	@Override
	public WOActionResults defaultAction() {
		return pageWithName(Main.class.getName());
	}
	
	public WOActionResults timepointAction() {

		TimePointResults nextPage = pageWithName(TimePointResults.class);
		
		String reference = request().stringFormValueForKey(REFERENCE_KEY);
		if (reference == null || reference == "") {
			nextPage.setErrorOnPage("No reference provided");
			nextPage.setShortResult("Error");
			return nextPage;
		}
		
		TimePoint thisTimePoint = TimePoint.withReference(ERXEC.newEditingContext(), reference);
		if (thisTimePoint == null) {
			nextPage.setErrorOnPage("Did not find reference: "+reference);
			nextPage.setShortResult("Error");
			return nextPage;
		}

		StoredSite storedSite = thisTimePoint.storedSite();
		Site site = new Site(storedSite.jmHost(), storedSite.jmPort(), storedSite.jmPassword(), storedSite);
		
    	for (int siteAppIndex = 0; siteAppIndex < thisTimePoint.timePointApplications().count(); siteAppIndex++) {
    		TimePointApplication timePointApplication = (TimePointApplication)thisTimePoint.timePointApplications().objectAtIndex(siteAppIndex);
    		SiteApplication siteApplication = site.siteApplicationWithName(timePointApplication.storedApp().name());
    		NSArray<SiteInstance> siteInstancesForApplication = site.getSiteInstancesForSiteApplication(siteApplication);
    		site.sendExecuteTimePoint(siteApplication, siteInstancesForApplication, timePointApplication.instanceWeight());
    	}
		return nextPage;
	}
}
