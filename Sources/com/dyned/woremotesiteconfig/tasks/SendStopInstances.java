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

package com.dyned.woremotesiteconfig.tasks;

import org.apache.log4j.Logger;

import com.dyned.woremotesiteconfig.javamonitor.Site;
import com.dyned.woremotesiteconfig.javamonitor.SiteApplication;
import com.dyned.woremotesiteconfig.javamonitor.SiteInstance;
import com.webobjects.foundation.NSArray;

public class SendStopInstances implements Runnable {

	public static final Logger log = Logger.getLogger(SendNewInstances.class);

	private SiteApplication _siteApplication = null;
	private Site _site = null;
	private String _host = null;

	public SendStopInstances(Site site, SiteApplication siteApplication, String host) {
		_site = site;
		_siteApplication = siteApplication;
		_host = host;
	}

	public void run() {
		boolean noError = true;
		log.info("Beginning background task.");

		try {
			NSArray<SiteInstance> siteInstances = _site.getSiteInstancesForSiteApplication(_siteApplication);
			
			for (SiteInstance siteInstance : siteInstances) {
				if (siteInstance.host().equalsIgnoreCase(_host)) {
					log.info("Stopping instance on " + _host);
					noError = _site.sendStopInstance(_siteApplication, siteInstance);
				}
			}
		} catch (RuntimeException ex) {
			log.error("Error: starting instances on "+_host);
			ex.printStackTrace();
		}

		if (!noError)
			log.error("Error: problem stopping instances on "+_host);

		log.info("Done with background task.");

	}
}
