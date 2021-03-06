/*
 * Copyright (C) Lukas Ruge
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.lukeslog.questionnaire.questionnaireComponents;

/**
 * 
 * @author lukas ruge
 *
 */
public abstract class Address {

	private String street="";
	private String city="";
	private String suburb="";
	private String postalcode="";
	private String country="";
	
	public Address(String street, String city, String suburb, String postalcode, String country)
	{
		this.street=street;
		this.city = city;
		this.suburb=suburb;
		this.postalcode=postalcode;
		this.country=country;
	}
	
	public String getStreet()
	{
		return street;
	}
	
	public String getCity()
	{
		return city;
	}
	
	public String getSuburb()
	{
		return suburb;
	}
	
	public String getPostalCode()
	{
		return postalcode;
	}
	
	public String getCountry()
	{
		return country;
	}
}
