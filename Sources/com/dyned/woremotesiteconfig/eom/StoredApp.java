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

import com.webobjects.eocontrol.EOEditingContext;

public class StoredApp extends _StoredApp {
	
	private static Logger log = Logger.getLogger(StoredApp.class);
	
	private boolean active;
	
	public void setActive(boolean aFlag) {
		active = aFlag;
	}
	
	public boolean active() {
		return active;
	}
	
	public boolean notActive() {
		return !active;
	}

	public void awakeFromInsertion(EOEditingContext ec) {
		super.awakeFromInsertion(ec);
		this.setInstanceInterleave(1);

		this.setScheduleBeginHour("01");
		this.setScheduleEndHour("06");
		this.setScheduleHourlyInterval("12");
		this.setScheduleType("DAILY");
		this.setScheduleWeekDay("Sunday");
	}

	public void awakeFromFetch(EOEditingContext ec) {
		super.awakeFromFetch(ec);
		if (this.instanceInterleave() == null || this.instanceInterleave().intValue() <= 0)
			this.setInstanceInterleave(1);
	}

}
