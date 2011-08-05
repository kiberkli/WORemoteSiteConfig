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

import org.apache.log4j.Logger;

import com.webobjects.eoaccess.EOUtilities;
import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.eocontrol.EOGlobalID;
import com.webobjects.eocontrol.EOKeyGlobalID;
import com.webobjects.eocontrol.EOSortOrdering;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSMutableArray;

public class TimePoint extends _TimePoint {

	private static Logger log = Logger.getLogger(TimePoint.class);
	
	public static final String ID_KEY = "id";

	public static TimePoint withReference(EOEditingContext ec, String reference) {
		return (TimePoint)EOUtilities.objectWithPrimaryKeyValue(ec,TimePoint.ENTITY_NAME, Integer.valueOf(reference));
	}
	
	@SuppressWarnings("unchecked")
	public String reference() {
		EOEditingContext ec = this.editingContext();
		if (ec == null) {
			log.error("No Editing context, BAD");
			return null;
		}
		
		EOGlobalID gid = ec.globalIDForObject(this);
		if (gid.isTemporary()) {
			log.debug("No PK, not saved to database.");
			return null;
		}
		
		EOKeyGlobalID kGid = (EOKeyGlobalID)gid;
		NSArray keyValues = kGid.keyValuesArray();
		if (keyValues.count() != 1 || !(keyValues.objectAtIndex(0) instanceof Integer))
			return null;
		
		Integer referenceInt = (Integer)keyValues.objectAtIndex(0);
		
		return String.valueOf(referenceInt.intValue());
	}
	
	public NSArray<TimePointApplication> sortedTimePointApplications() {
		NSMutableArray<EOSortOrdering> sortOrderings = new NSMutableArray<EOSortOrdering>();
		sortOrderings.addObject(new EOSortOrdering(TimePointApplication.STORED_APP_KEY+"."+StoredApp.NAME_KEY, EOSortOrdering.CompareAscending));
		return this.timePointApplications(null, sortOrderings, true);
	}

}


//Integer result = null;
//
//try {
//	NSDictionary dict = EOUtilities.primaryKeyForObject(this.editingContext(), this);
//	result = (Integer)(dict.objectForKey(ID_KEY));
//} catch (RuntimeException ex) {
//	NSLog.err.appendln(ex.getMessage());
//    NSLog.err.appendln("Failed to get primary key for EOGenericRecord, Object not saved yet?.");
//}
//
//return result;
