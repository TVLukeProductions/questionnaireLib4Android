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

import android.graphics.Bitmap;

/**
 * 
 * 
 * @author lukas ruge
 *
 */
public abstract class Person {

	private String salutation="";
	private String firstName="";
	private String lastName="";
	private String organisation="";
	private Address adress;
	private String phoneNumber="";
	private String faxNumber="";
	private String emailaddress="";
	private String website="";
	private Bitmap picture;
	
	public Person(String firstName, String lastName)
	{
		this.firstName = firstName;
		this.lastName=lastName;
	}
	
	public void setSalutation(String salutation)
	{
		this.salutation=salutation;
	}
	
	public String getSalutation()
	{
		return salutation;
	}
	
	public void setOrganisation(String organisation)
	{
		this.organisation=organisation;
	}
	
	public String getOrganisation()
	{
		return organisation;
	}
	
	public void setEmailadress(String emailaddress)
	{
		this.emailaddress = emailaddress;
	}
	
	public String getEmailaddress()
	{
		return emailaddress;
	}
	
	public void setFirstName(String firstName)
	{
		this.firstName=firstName;
	}
	
	public String getFirstName()
	{
		return firstName;
	}
	
	public void setLastName(String lastName)
	{
		this.lastName=lastName;
	}
	
	public String getLastName()
	{
		return lastName;
	}
	
	public String getName()
	{
		return getSalutation()+" "+getFirstName()+" "+getLastName();
	}
	
	public String getPhoneNumber()
	{
		return phoneNumber;
	}
	
	public void setPhoneNumber(String phoneNumber)
	{
		this.phoneNumber=phoneNumber;
	}
	
	public void setAddress(Address address)
	{
		this.adress = address;
	}
	
	public Address getAddress()
	{
		return adress;
	}
	
	public void setWebsite(String website)
	{
		this.website = website;
	}
	
	public String getWebsite()
	{
		return website;
	}
	
	public void setFaxNumber(String faxNumber)
	{
		this.faxNumber=faxNumber;
	}
	
	public String getFaxNumber()
	{
		return faxNumber;
	}
}
