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

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

public class SiteApplication {

	private static Logger log = Logger.getLogger(SiteApplication.class);

	public static final String TYPE = "type";
	public static final String ADAPTOR = "adaptor";
	public static final String ADAPTORTHREADS = "adaptorThreads";
	public static final String ADAPTORTHREADSMAX = "adaptorThreadsMax";
	public static final String ADAPTORTHREADSMIN = "adaptorThreadsMin";
	public static final String ADDITIONALARGS = "additionalArgs";
	public static final String AUTOOPENINBROWSER = "autoOpenInBrowser";
	public static final String AUTORECOVER = "autoRecover";
	public static final String CACHINGENABLED = "cachingEnabled";
	public static final String CNCTTIMEOUT = "cnctTimeout";
	public static final String DEBUGGINGENABLED = "debuggingEnabled";
	public static final String DORMANT = "dormant";
	public static final String LIFEBEATINTERVAL = "lifebeatInterval";
	public static final String LISTENQUEUESIZE = "listenQueueSize";
	public static final String MACOUTPUTPATH = "macOutputPath";
	public static final String MACPATH = "macPath";
	public static final String MINIMUMACTIVESESSIONSCOUNT = "minimumActiveSessionsCount";
	public static final String NAME = "name";
	public static final String NOTIFICATIONEMAILADDR = "notificationEmailAddr";
	public static final String NOTIFICATIONEMAILENABLED = "notificationEmailEnabled";
	public static final String OLDNAME = "oldname";
	public static final String PHASEDSTARTUP = "phasedStartup";
	public static final String POOLSIZE = "poolsize";
	public static final String PROJECTSEARCHPATH = "projectSearchPath";
	public static final String RECVBUFSIZE = "recvBufSize";
	public static final String RECVTIMEOUT = "recvTimeout";
	public static final String REDIR = "redir";
	public static final String RETRIES = "retries";
	public static final String SCHEDULER = "scheduler";
	public static final String SENDBUFSIZE = "sendBufSize";
	public static final String SENDTIMEOUT = "sendTimeout";
	public static final String SESSIONTIMEOUT = "sessionTimeOut";
	public static final String STARTINGPORT = "startingPort";
	public static final String STATISTICSPASSWORD = "statisticsPassword";
	public static final String TIMEFORSTARTUP = "timeForStartup";
	public static final String UNIXOUTPUTPATH = "unixOutputPath";
	public static final String UNIXPATH = "unixPath";
	public static final String URLVERSION = "urlVersion";
	public static final String WINOUTPUTPATH = "winOutputPath";
	public static final String WINPATH = "winPath";

	private String type;
	private String adaptor;
	private String adaptorThreads;
	private String adaptorThreadsMax;
	private String adaptorThreadsMin;
	private String additionalArgs;
	private String autoOpenInBrowser;
	private String autoRecover;
	private String cachingEnabled;
	private String cnctTimeout;
	private String debuggingEnabled;
	private String dormant;
	private String lifebeatInterval;
	private String listenQueueSize;
	private String macOutputPath;
	private String macPath;
	private String minimumActiveSessionsCount;
	private String name;
	private String notificationEmailAddr;
	private String notificationEmailEnabled;
	private String oldname;
	private String phasedStartup;
	private String poolsize;
	private String projectSearchPath;
	private String recvBufSize;
	private String recvTimeout;
	private String redir;
	private String retries;
	private String scheduler;
	private String sendBufSize;
	private String sendTimeout;
	private String sessionTimeOut;
	private String startingPort;
	private String statisticsPassword;
	private String timeForStartup;
	private String unixOutputPath;
	private String unixPath;
	private String urlVersion;
	private String winOutputPath;
	private String winPath;
	
	private Site parentSite;

	public SiteApplication(JSONObject jsonObject) {
		super();

		log.debug("Parsing: " + jsonObject.toString());
		
		if (jsonObject != null) {
			type = jsonObject.getString(TYPE);
			adaptor = jsonObject.getString(ADAPTOR);
			adaptorThreads = jsonObject.getString(ADAPTORTHREADS);
			adaptorThreadsMax = jsonObject.getString(ADAPTORTHREADSMAX);
			adaptorThreadsMin = jsonObject.getString(ADAPTORTHREADSMIN);
			additionalArgs = jsonObject.getString(ADDITIONALARGS);
			autoOpenInBrowser = jsonObject.getString(AUTOOPENINBROWSER);
			autoRecover = jsonObject.getString(AUTORECOVER);
			cachingEnabled = jsonObject.getString(CACHINGENABLED);
			cnctTimeout = jsonObject.getString(CNCTTIMEOUT);
			debuggingEnabled = jsonObject.getString(DEBUGGINGENABLED);
			dormant = jsonObject.getString(DORMANT);
			lifebeatInterval = jsonObject.getString(LIFEBEATINTERVAL);
			listenQueueSize = jsonObject.getString(LISTENQUEUESIZE);
			macOutputPath = jsonObject.getString(MACOUTPUTPATH);
			macPath = jsonObject.getString(MACPATH);
			minimumActiveSessionsCount = jsonObject.getString(MINIMUMACTIVESESSIONSCOUNT);
			name = jsonObject.getString(NAME);
			notificationEmailAddr = jsonObject.getString(NOTIFICATIONEMAILADDR);
			notificationEmailEnabled = jsonObject.getString(NOTIFICATIONEMAILENABLED);
			oldname = jsonObject.getString(OLDNAME);
			phasedStartup = jsonObject.getString(PHASEDSTARTUP);
			poolsize = jsonObject.getString(POOLSIZE);
			projectSearchPath = jsonObject.getString(PROJECTSEARCHPATH);
			recvBufSize = jsonObject.getString(RECVBUFSIZE);
			recvTimeout = jsonObject.getString(RECVTIMEOUT);
			redir = jsonObject.getString(REDIR);
			retries = jsonObject.getString(RETRIES);
			scheduler = jsonObject.getString(SCHEDULER);
			sendBufSize = jsonObject.getString(SENDBUFSIZE);
			sendTimeout = jsonObject.getString(SENDTIMEOUT);
			sessionTimeOut = jsonObject.getString(SESSIONTIMEOUT);
			startingPort = jsonObject.getString(STARTINGPORT);
			statisticsPassword = jsonObject.getString(STATISTICSPASSWORD);
			timeForStartup = jsonObject.getString(TIMEFORSTARTUP);
			unixOutputPath = jsonObject.getString(UNIXOUTPUTPATH);
			unixPath = jsonObject.getString(UNIXPATH);
			urlVersion = jsonObject.getString(URLVERSION);
			winOutputPath = jsonObject.getString(WINOUTPUTPATH);
			winPath = jsonObject.getString(WINPATH);
			
			log.debug("   type: " + type);
			log.debug("   adaptor: " + adaptor);
			log.debug("   adaptorThreads: " + adaptorThreads);
			log.debug("   adaptorThreadsMax: " + adaptorThreadsMax);
			log.debug("   adaptorThreadsMin: " + adaptorThreadsMin);
			log.debug("   additionalArgs: " + additionalArgs);
			log.debug("   autoOpenInBrowser: " + autoOpenInBrowser);
			log.debug("   autoRecover: " + autoRecover);
			log.debug("   cachingEnabled: " + cachingEnabled);
			log.debug("   cnctTimeout: " + cnctTimeout);
			log.debug("   debuggingEnabled: " + debuggingEnabled);
			log.debug("   dormant: " + dormant);
			log.debug("   lifebeatInterval: " + lifebeatInterval);
			log.debug("   listenQueueSize: " + listenQueueSize);
			log.debug("   macOutputPath: " + macOutputPath);
			log.debug("   macPath: " + macPath);
			log.debug("   minimumActiveSessionsCount: " + minimumActiveSessionsCount);
			log.debug("   name: " + name);
			log.debug("   notificationEmailAddr: " + notificationEmailAddr);
			log.debug("   notificationEmailEnabled: " + notificationEmailEnabled);
			log.debug("   oldname: " + oldname);
			log.debug("   phasedStartup: " + phasedStartup);
			log.debug("   poolsize: " + poolsize);
			log.debug("   projectSearchPath: " + projectSearchPath);
			log.debug("   recvBufSize: " + recvBufSize);
			log.debug("   recvTimeout: " + recvTimeout);
			log.debug("   redir: " + redir);
			log.debug("   retries: " + retries);
			log.debug("   scheduler: " + scheduler);
			log.debug("   sendBufSize: " + sendBufSize);
			log.debug("   sendTimeout: " + sendTimeout);
			log.debug("   sessionTimeOut: " + sessionTimeOut);
			log.debug("   startingPort: " + startingPort);
			log.debug("   statisticsPassword: " + statisticsPassword);
			log.debug("   timeForStartup: " + timeForStartup);
			log.debug("   unixOutputPath: " + unixOutputPath);
			log.debug("   unixPath: " + unixPath);
			log.debug("   urlVersion: " + urlVersion);
			log.debug("   winOutputPath: " + winOutputPath);
			log.debug("   winPath: " + winPath);

		}
	}
	
	public SiteApplication(String aName) {
		name = aName;
	}
	
	// -------------------------------------------------------------------------------------

	public String type() {
		return type;
	}


	public String adaptor() {
		return adaptor;
	}


	public String adaptorThreads() {
		return adaptorThreads;
	}


	public String adaptorThreadsMax() {
		return adaptorThreadsMax;
	}


	public String adaptorThreadsMin() {
		return adaptorThreadsMin;
	}


	public String additionalArgs() {
		return additionalArgs;
	}


	public String autoOpenInBrowser() {
		return autoOpenInBrowser;
	}


	public String autoRecover() {
		return autoRecover;
	}


	public String cachingEnabled() {
		return cachingEnabled;
	}


	public String cnctTimeout() {
		return cnctTimeout;
	}


	public String debuggingEnabled() {
		return debuggingEnabled;
	}


	public String dormant() {
		return dormant;
	}


	public String lifebeatInterval() {
		return lifebeatInterval;
	}


	public String listenQueueSize() {
		return listenQueueSize;
	}


	public String macOutputPath() {
		return macOutputPath;
	}


	public String macPath() {
		return macPath;
	}


	public String minimumActiveSessionsCount() {
		return minimumActiveSessionsCount;
	}


	public String name() {
		return name;
	}


	public String notificationEmailAddr() {
		return notificationEmailAddr;
	}


	public String notificationEmailEnabled() {
		return notificationEmailEnabled;
	}


	public String oldname() {
		return oldname;
	}


	public String phasedStartup() {
		return phasedStartup;
	}


	public String poolsize() {
		return poolsize;
	}


	public String projectSearchPath() {
		return projectSearchPath;
	}


	public String recvBufSize() {
		return recvBufSize;
	}


	public String recvTimeout() {
		return recvTimeout;
	}


	public String redir() {
		return redir;
	}


	public String retries() {
		return retries;
	}


	public String scheduler() {
		return scheduler;
	}


	public String sendBufSize() {
		return sendBufSize;
	}


	public String sendTimeout() {
		return sendTimeout;
	}


	public String sessionTimeOut() {
		return sessionTimeOut;
	}


	public String startingPort() {
		return startingPort;
	}


	public String statisticsPassword() {
		return statisticsPassword;
	}


	public String timeForStartup() {
		return timeForStartup;
	}


	public String unixOutputPath() {
		return unixOutputPath;
	}


	public String unixPath() {
		return unixPath;
	}


	public String urlVersion() {
		return urlVersion;
	}


	public String winOutputPath() {
		return winOutputPath;
	}


	public String winPath() {
		return winPath;
	}
	
	public Site parentSite() {
		return parentSite;
	}

	// -------------------------------------------------------------------------------------
	
	public void setType(String aValue) {
		type = aValue;
	}


	public void setAdaptor(String aValue) {
		adaptor = aValue;
	}


	public void setAdaptorThreads(String aValue) {
		adaptorThreads = aValue;
	}


	public void setAdaptorThreadsMax(String aValue) {
		adaptorThreadsMax = aValue;
	}


	public void setAdaptorThreadsMin(String aValue) {
		adaptorThreadsMin = aValue;
	}


	public void setAdditionalArgs(String aValue) {
		additionalArgs = aValue;
	}


	public void setAutoOpenInBrowser(String aValue) {
		autoOpenInBrowser = aValue;
	}


	public void setAutoRecover(String aValue) {
		autoRecover = aValue;
	}


	public void setCachingEnabled(String aValue) {
		cachingEnabled = aValue;
	}


	public void setCnctTimeout(String aValue) {
		cnctTimeout = aValue;
	}


	public void setDebuggingEnabled(String aValue) {
		debuggingEnabled = aValue;
	}


	public void setDormant(String aValue) {
		dormant = aValue;
	}


	public void setLifebeatInterval(String aValue) {
		lifebeatInterval = aValue;
	}


	public void setListenQueueSize(String aValue) {
		listenQueueSize = aValue;
	}


	public void setMacOutputPath(String aValue) {
		macOutputPath = aValue;
	}


	public void setMacPath(String aValue) {
		macPath = aValue;
	}


	public void setMinimumActiveSessionsCount(String aValue) {
		minimumActiveSessionsCount = aValue;
	}


	public void setName(String aValue) {
		name = aValue;
	}


	public void setNotificationEmailAddr(String aValue) {
		notificationEmailAddr = aValue;
	}


	public void setNotificationEmailEnabled(String aValue) {
		notificationEmailEnabled = aValue;
	}


	public void setOldname(String aValue) {
		oldname = aValue;
	}


	public void setPhasedStartup(String aValue) {
		phasedStartup = aValue;
	}


	public void setPoolsize(String aValue) {
		poolsize = aValue;
	}


	public void setProjectSearchPath(String aValue) {
		projectSearchPath = aValue;
	}


	public void setRecvBufSize(String aValue) {
		recvBufSize = aValue;
	}


	public void setRecvTimeout(String aValue) {
		recvTimeout = aValue;
	}


	public void setRedir(String aValue) {
		redir = aValue;
	}


	public void setRetries(String aValue) {
		retries = aValue;
	}


	public void setScheduler(String aValue) {
		scheduler = aValue;
	}


	public void setSendBufSize(String aValue) {
		sendBufSize = aValue;
	}


	public void setSendTimeout(String aValue) {
		sendTimeout = aValue;
	}


	public void setSessionTimeOut(String aValue) {
		sessionTimeOut = aValue;
	}


	public void setStartingPort(String aValue) {
		startingPort = aValue;
	}


	public void setStatisticsPassword(String aValue) {
		statisticsPassword = aValue;
	}


	public void setTimeForStartup(String aValue) {
		timeForStartup = aValue;
	}


	public void setUnixOutputPath(String aValue) {
		unixOutputPath = aValue;
	}


	public void setUnixPath(String aValue) {
		unixPath = aValue;
	}


	public void setUrlVersion(String aValue) {
		urlVersion = aValue;
	}


	public void setWinOutputPath(String aValue) {
		winOutputPath = aValue;
	}


	public void setWinPath(String aValue) {
		winPath = aValue;
	}

	public void setParentSite(Site aSite) {
		parentSite = aSite;
	}

	// -------------------------------------------------------------------------------------

}


/*

Example

{
"type":"MApplication",
"adaptor":"WODefaultAdaptor",
"adaptorThreads":8,
"adaptorThreadsMax":32,
"adaptorThreadsMin":16,
"additionalArgs":null,
"autoOpenInBrowser":false,
"autoRecover":true,
"cachingEnabled":true,
"cnctTimeout":null,
"debuggingEnabled":false,
"dormant":null,
"lifebeatInterval":30,
"listenQueueSize":30,
"macOutputPath":null,
"macPath":null,
"minimumActiveSessionsCount":0,
"name":"CustomerService",
"notificationEmailAddr":null,
"notificationEmailEnabled":false,
"oldname":null,
"phasedStartup":true,
"poolsize":null,
"projectSearchPath":"()",
"recvBufSize":null,
"recvTimeout":null,
"redir":null,
"retries":null,
"scheduler":null,
"sendBufSize":null,
"sendTimeout":null,
"sessionTimeOut":3600,
"startingPort":2001,
"statisticsPassword":"well2000",
"timeForStartup":30,
"unixOutputPath":"/Local/Library/WebObjects/Logs/",
"unixPath":"/Local/Library/WebObjects/Applications/CustomerService.woa/CustomerService",
"urlVersion":null,
"winOutputPath":null,
"winPath":null
}

 */