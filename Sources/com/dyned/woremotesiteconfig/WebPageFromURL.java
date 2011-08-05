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

import java.util.regex.*;

import java.net.*;
import java.io.*;

import org.apache.log4j.Logger;

public class WebPageFromURL {

	private static Logger log = Logger.getLogger(WebPageFromURL.class);

	public String urlString;
	public String content;
	public String contentWithoutHTML;
	public Long date;
	public Long expirationDate;
	public Long modificationDate;
	public Integer responseCode;

	// -------------------------------------------------------------------------------------------------
	public WebPageFromURL(String theURLToGet, String method, String postData) {
		super();

		log.debug("= = = = = = = = = = = = = = = = = = FINAL URL Call: " + theURLToGet);
		
		URL webPage = null;
		HttpURLConnection urlConnection = null;
		StringBuffer postDataContent = null;

		// Content:
		InputStream pageContentStream = null;
		BufferedReader pageReader = null;
		StringBuffer pageContent = new StringBuffer();

		if (method == null)
			method = "GET";

		if ( (theURLToGet == null) || (theURLToGet == "") ) {
			log.error(" - theURLToGet is null.");
			return;
		}

		try {
			webPage = new URL(theURLToGet);
			urlConnection = (HttpURLConnection)webPage.openConnection();
			urlConnection.setReadTimeout(15*1000);
			urlConnection.setDoOutput(true);
			urlConnection.setRequestMethod(method);

			if (method.equalsIgnoreCase("POST")) {
				//postDataContent = new StringBuffer(URLEncoder.encode(postData, "UTF-8"));
				postDataContent = new StringBuffer(postData);
				urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
				urlConnection.setRequestProperty("Content-Length", "" + postDataContent.length());
				DataOutputStream stream = new DataOutputStream(urlConnection.getOutputStream());
				stream.writeBytes(postDataContent.toString());
				stream.close();
			}
			
			pageContentStream = urlConnection.getInputStream();
			responseCode = new Integer(urlConnection.getResponseCode());

			pageReader = new BufferedReader( new InputStreamReader(pageContentStream) );
			String pageLine = pageReader.readLine();
			while (pageLine != null) {
				pageContent.append(pageLine);
				pageContent.append("\n");
				pageLine = pageReader.readLine();
			}

			pageContentStream.close();

			// Set some outside variables
			urlString = theURLToGet;
			content = pageContent.toString();
			contentWithoutHTML = cleanUpPunktuation(removeHTMLTags(pageContent.toString()));
			date = new Long(urlConnection.getDate());
			expirationDate = new Long(urlConnection.getExpiration());
			modificationDate = new Long(urlConnection.getLastModified());

		} catch (FileNotFoundException ex) {
			log.error(" - Page not found: " + ex.getMessage());
			log.error("   URL: " + theURLToGet);
			content = "OK";
			//ex.printStackTrace();
			return;
		} catch (MalformedURLException ex) {
			log.error(" - MalformedURLException error: " + ex.getMessage());
			log.error("   URL: " + theURLToGet);
			//ex.printStackTrace();
			return;
		} catch (UnsupportedEncodingException ex) {
			log.error(" - Failed to URLEncode " + postData);
			log.error("   using data AS-IS");
			//ex.printStackTrace();
			return;
		} catch (UnknownServiceException ex) {
			log.error(" - Failed creating output stream for post data.");
			//ex.printStackTrace();
			return;
		} catch (IOException ex) {
			log.error(" - IOException: " + ex.getStackTrace());
			log.error("   URL: " + theURLToGet);
			//ex.printStackTrace();
			return;
		}
	}

	// -------------------------------------------------------------------------------------------------
	public String getContentWithoutHTML() {
		return contentWithoutHTML;
	}

	// -------------------------------------------------------------------------------------------------
	public String getContent() {
		return content;
	}

	// -------------------------------------------------------------------------------------------------
	public Long getDate() {
		return date;
	}

	// -------------------------------------------------------------------------------------------------
	public Long getExpirationDate() {
		return expirationDate;
	}

	// -------------------------------------------------------------------------------------------------
	public Long getModificationDate() {
		return modificationDate;
	}

	public Integer getResponseCode() {
		return responseCode;
	}

	// -------------------------------------------------------------------------------------------------
	// -------------------------------------------------------------------------------------------------
	// -------------------------------------------------------------------------------------------------
	
	private String cleanUpLinebreaks(String theText) {
		StringBuffer cleanText = new StringBuffer();
		Pattern lineBreaksPattern = Pattern.compile("[\r\n]");
		Matcher lineBreakMatcher = lineBreaksPattern.matcher(theText);

		boolean results = lineBreakMatcher.find();
		while (results)
		{
			lineBreakMatcher.appendReplacement(cleanText, " ");
			results = lineBreakMatcher.find();
		}
		lineBreakMatcher.appendTail(cleanText);

		return cleanText.toString();
	}

	// -------------------------------------------------------------------------------------------------
	private String cleanUpPunktuation(String theText) {
		StringBuffer cleanText = new StringBuffer();
		//Pattern lineBreaksPattern = Pattern.compile("[-,:!?./&<>\\"]");
		//Pattern lineBreaksPattern = Pattern.compile("[^\\w]");
		//Pattern lineBreaksPattern = Pattern.compile("[^\\w][ \t]+");
		//Pattern lineBreaksPattern = Pattern.compile("[\\W\t ]+");
		Pattern lineBreaksPattern = Pattern.compile("[^a-zA-Z-]+");
		Matcher lineBreakMatcher = lineBreaksPattern.matcher(theText);

		boolean results = lineBreakMatcher.find();
		while (results)
		{
			lineBreakMatcher.appendReplacement(cleanText, " ");
			results = lineBreakMatcher.find();
		}
		lineBreakMatcher.appendTail(cleanText);

		return cleanText.toString();
	}

	// -------------------------------------------------------------------------------------------------
	private String removeHTMLTags(String theText) {
		StringBuffer cleanText = new StringBuffer();
		//Pattern lineBreaksPattern = Pattern.compile("<(.|\n|\r)+?>|&[a-zA-Z0-9]+;"); // <(.|\n)+?>
		Pattern lineBreaksPattern = Pattern.compile("<(.|\n|\r)+?>|&[#a-zA-Z0-9]+;"); // <(.|\n)+?>
		Matcher lineBreakMatcher = lineBreaksPattern.matcher(theText);

		boolean results = lineBreakMatcher.find();
		while (results)
		{
			lineBreakMatcher.appendReplacement(cleanText, "");
			results = lineBreakMatcher.find();
		}
		lineBreakMatcher.appendTail(cleanText);

		return cleanText.toString();
	}
	// -------------------------------------------------------------------------------------------------
}
