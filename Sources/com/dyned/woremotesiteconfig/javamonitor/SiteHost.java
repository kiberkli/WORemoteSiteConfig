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

public class SiteHost {

	private static Logger log = Logger.getLogger(SiteHost.class);

	public static final String ID = "id";
	public static final String TYPE = "type";
	public static final String OSTYPE = "osType";
	public static final String ADDRESS = "address";
	public static final String NAME = "name";

	private String id;
	private String type;
	private String osType;
	private String address;
	private String name;

	public SiteHost(String newId, String newType, String newOSType, String newAddress, String newName) {
		super();
		
		type = newType;
		osType = newOSType;
		name = newName;

		if (newId == null || newId == "") {
			id = name;
		} else
			id = newId;
		if (newAddress == null || newAddress == "")
			address = name;
		else
			address = newAddress;

		log.debug("   id: " + id);
		log.debug("   type: " + type);
		log.debug("   osType: " + osType);
		log.debug("   address: " + address);
		log.debug("   name: " + name);

	}
	
	public SiteHost(JSONObject jsonObject) {
		super();
		
		log.debug("Parsing: " + jsonObject.toString());

		if (jsonObject != null) {
			
			try {
				id = jsonObject.getString(ID);
			} catch (JSONException ex) {
				log.error("key 'ID' not found.");
			}

			try {
				type = jsonObject.getString(TYPE);
			} catch (JSONException ex) {
				log.error("key 'TYPE' not found.");
			}

			try {
				osType = jsonObject.getString(OSTYPE);
			} catch (JSONException ex) {
				log.error("key 'OSTYPE' not found.");
			}

			try {
				address = jsonObject.getString(ADDRESS);
			} catch (JSONException ex) {
				log.error("key 'ADDRESS' not found.");
			}

			try {
				name = jsonObject.getString(NAME);
			} catch (JSONException ex) {
				log.error("key 'NAME' not found.");
			}
			
			log.debug("   id: " + id);
			log.debug("   type: " + type);
			log.debug("   osType: " + osType);
			log.debug("   address: " + address);
			log.debug("   name: " + name);

		}
		
	}
		
	public JSONObject jsonObject() {
		
		// {id: 'otherserver.com',type: 'MHost', osType: 'MACOSX',address: '192.168.20.5', name: 'otherserver.com'}
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put(ID, id);
		jsonObject.put(TYPE, osType);
		jsonObject.put(OSTYPE, osType);
		jsonObject.put(ADDRESS, address);
		jsonObject.put(NAME, name);
		
		return jsonObject;
	}
	
	public String jsonString() {
		return jsonObject().toString();
	}
	
	public String type() {
		return type;
	}

	public String name() {
		return name;
	}

	public String osType() {
		return osType;
	}

	public void setType(String aValue) {
		type = aValue;
	}

	public void setName(String aValue) {
		name = aValue;
	}

	public void setOsType(String aValue) {
		osType = aValue;
	}

}
