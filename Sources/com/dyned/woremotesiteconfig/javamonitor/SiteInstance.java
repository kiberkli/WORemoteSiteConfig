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

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

public class SiteInstance {

	/*
	 * From: http://wiki.objectstyle.org/confluence/display/WO/Wonder+JavaMonitor+and+wotaskd
	 * 
	 * 	curl -X GET http://monitorhost:port/cgi-bin/WebObjects/JavaMonitor.woa/ra/mApplications/AjaxExample/addInstance&host=localhost
	 * 
	 */
	
	private static Logger log = Logger.getLogger(SiteInstance.class);
	
	private static final String STATE_ALIVE = "ALIVE";
	private static final String STATE_DEAD = "DEAD";
	
	private static final String NAME = "name";
	private static final String ID = "id";
	private static final String HOST = "host";
	private static final String PORT = "port";
	private static final String STATE = "state";
	private static final String DEATHS = "deaths";
	private static final String REFUSINGNEWSESSIONS = "refusingNewSessions";
	private static final String SCHEDULED = "scheduled";
	private static final String TRANSACTIONS = "transactions";
	private static final String ACTIVESESSIONS = "activeSessions";
	private static final String AVERAGEIDLEPERIOD = "averageIdlePeriod";
	private static final String AVGTRANSACTIONTIME = "avgTransactionTime";
	private static final String AUTORECOVER = "autoRecover";
	 
	private String name;
	private String id;
	private String host;
	private String port;
	private String state;
	private String deaths;
	private String refusingNewSessions;
	private String scheduled;
	private String transactions;
	private int activeSessions;
	private String averageIdlePeriod;
	private String avgTransactionTime;
	private String autoRecover;
	 
	public SiteInstance(String aHost) {
		super();
		host = aHost;
	}
	
	public SiteInstance(JSONObject jsonObject) {
		super();
		
		log.debug("Parsing: " + jsonObject.toString());

		if (jsonObject != null) {
			try {
				name = jsonObject.getString(NAME);
			} catch (JSONException ex) {
				log.error("key 'NAME' not found.");
			}

			try {
				id = jsonObject.getString(ID);
			} catch (JSONException ex) {
				log.error("key 'ID' not found.");
			}

			try {
				host = jsonObject.getString(HOST);
			} catch (JSONException ex) {
				log.error("key 'HOST' not found.");
			}

			try {
				port = jsonObject.getString(PORT);
			} catch (JSONException ex) {
				log.error("key 'PORT' not found.");
			}

			try {
				state = jsonObject.getString(STATE);
			} catch (JSONException ex) {
				log.error("key 'STATE' not found.");
			}

			try {
				deaths = jsonObject.getString(DEATHS);
			} catch (JSONException ex) {
				log.error("key 'DEATHS' not found.");
			}

			try {
				refusingNewSessions = jsonObject.getString(REFUSINGNEWSESSIONS);
			} catch (JSONException ex) {
				log.error("key 'REFUSINGNEWSESSIONS' not found.");
			}

			try {
				scheduled = jsonObject.getString(SCHEDULED);
			} catch (JSONException ex) {
				log.error("key 'SCHEDULED' not found.");
			}

			try {
				transactions = jsonObject.getString(TRANSACTIONS);
			} catch (JSONException ex) {
				log.error("key 'TRANSACTIONS' not found.");
			}

			try {
				String activeSessionsString = jsonObject.getString(ACTIVESESSIONS);
				activeSessions = Integer.valueOf(activeSessionsString);
			} catch (NumberFormatException ex1) {
				activeSessions = 0;
			} catch (JSONException ex) {
				log.error("key 'ACTIVESESSIONS' not found.");
			}

			try {
				averageIdlePeriod = jsonObject.getString(AVERAGEIDLEPERIOD);
			} catch (JSONException ex) {
				log.error("key 'AVERAGEIDLEPERIOD' not found.");
			}

			try {
				avgTransactionTime = jsonObject.getString(AVGTRANSACTIONTIME);
			} catch (JSONException ex) {
				log.error("key 'AVGTRANSACTIONTIME' not found.");
			}

			try {
				autoRecover = jsonObject.getString(AUTORECOVER);
			} catch (JSONException ex) {
				log.error("key 'AUTORECOVER' not found.");
			}
		}
	}
		
	public String name() {
		return name;
	}

	public String id() {
		return id;
	}

	public String host() {
		return host;
	}

	public String port() {
		return port;
	}

	public String state() {
		return state;
	}

	public String deaths() {
		return deaths;
	}

	public String refusingNewSessions() {
		return refusingNewSessions;
	}

	public String scheduled() {
		return scheduled;
	}

	public String transactions() {
		return transactions;
	}

	public int activeSessions() {
		return activeSessions;
	}

	public String averageIdlePeriod() {
		return averageIdlePeriod;
	}

	public String avgTransactionTime() {
		return avgTransactionTime;
	}

	public String autoRecover() {
		return autoRecover;
	}

	public void setName(String aValue) {
		name = aValue;
	}

	public void setId(String aValue) {
		id = aValue;
	}

	public void setHost(String aValue) {
		host = aValue;
	}

	public void setPort(String aValue) {
		port = aValue;
	}

	public void setState(String aValue) {
		state = aValue;
	}

	public void setDeaths(String aValue) {
		deaths = aValue;
	}

	public void setRefusingNewSessions(String aValue) {
		refusingNewSessions = aValue;
	}

	public void setScheduled(String aValue) {
		scheduled = aValue;
	}

	public void setTransactions(String aValue) {
		transactions = aValue;
	}

	public void setActiveSessions(int aValue) {
		activeSessions = aValue;
	}

	public void setAverageIdlePeriod(String aValue) {
		averageIdlePeriod = aValue;
	}

	public void setAvgTransactionTime(String aValue) {
		avgTransactionTime = aValue;
	}

	public void setAutoRecover(String aValue) {
		autoRecover = aValue;
	}
	
	// -----------------------------------------------------------------------------
	
	public boolean isAlive() {
		if (this.state != null)
			return (this.state.equalsIgnoreCase(STATE_ALIVE));
		else
			return false;
	}

	public boolean isDead() {
		if (this.state != null)
			return (this.state.equalsIgnoreCase(STATE_DEAD));
		else
			return true;
	}

}
