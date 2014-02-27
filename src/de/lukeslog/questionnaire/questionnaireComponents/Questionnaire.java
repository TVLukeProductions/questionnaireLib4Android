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

import java.util.ArrayList;

/**
 * 
 * @author lukas ruge
 *
 */
public abstract class Questionnaire {

	private String qTitle="";
	private String qSubTitle="";
	private Person investigator;
	private Person dataCollector;
	private String qbefore="";
	private String qafter="";
	private String qappendix="";
	private String qImage ="";
	
	private ArrayList<Question> questions = new ArrayList<Question>();
	
	/**
	 * This method returns the title of a questionnaire as a String
	 * If there is no title set, the method will return an empty string.
	 * 
	 * @return the title of the questionnaire
	 */
	public String getTitle()
	{
		return qTitle;
	}
	
	/**
	 * This method returns the subtitle of a questionnaire as a string. 
	 * If there is no subtitle set, the method will return an empty string.
	 * 
	 * @return the subtitle of the questionnaire
	 */
	public String getSubTitle()
	{
		return qSubTitle;
	}
	
	
	/**
	 * This method can be used to set the title of the questionnaire
	 * 
	 * @param qTitle a String containing the title of the questionnaire
	 */
	public void setTitle(String qTitle)
	{
		this.qTitle = qTitle;
	}
	
	/**
	 * This method can be used to set the subtitle of the questionnaire
	 * 
	 * @param qSubTitle a String containing the subtitle of the questionnaire
	 */
	public void setSubTitle(String qSubTitle)
	{
		this.qSubTitle = qSubTitle;
	}
	
	/**
	 * Get the investigator as a Person object.
	 * 
	 * The investigator is the person responsible for the study or test in which a survey is performed.
	 * this method can be used to get an object of the person
	 * 
	 * @return the investigator
	 */
	public Person getInvestigator()
	{
		return investigator;
	}
	
	/**
	 * Set the investigator
	 * 
	 * The investigator is the person responsible for the study or test in which a survey is performed.
	 * This method may be used to set a new person object as the investigator
	 * 
	 * @param investigator a Person-Object with the data from the investigator
	 */
	public void setInvestigator(Person investigator)
	{
		this.investigator = investigator;
	}
	
	/**
	 * Get the data collector.
	 * 
	 * The data collector is a person that as the task of handling the survey or questionnaire.
	 * this method may be used to get an pobject of the person type
	 * 
	 * @return the data collector
	 */
	public Person getdataCollector()
	{
		return dataCollector;
	}
	
	/**
	 * Set The data collector
	 * 
	 * The data collector is a person that as the task of handling the survey or questionnaire.
	 * 
	 * @param dataCollector
	 */
	public void setDataCollector(Person dataCollector)
	{
		this.dataCollector=dataCollector;
	}
	
	/**
	 * Returns an ArrayList containing the questions
	 * 
	 * @return
	 */
	public ArrayList<Question> getQuestions()
	{
		return questions;
	}
	
	public void addQuestion(Question question)
	{
		questions.add(question);
	}
	
	public void addQuestion(int position, Question question)
	{
		if(position<=questions.size())
		{
			questions.add(position, question);
		}
	}
	
	public Question getQuestion(int position)
	{
		Question q = questions.get(position);
		return q;
	}
	
	public String getBeforeText()
	{
		return qbefore;
	}
	
	public void addToBeforeText(String before)
	{
		qbefore=qbefore+"\n"+before;
	}
	
	public void setBeforeText(String before)
	{
		qbefore=before;
	}
	
	public String getAfterText()
	{
		return qafter;
	}
	
	public void addToAfterText(String after)
	{
		qafter=qafter+"\n"+after;
	}
	
	public void setAfterText(String after)
	{
		qafter=after;
	}
	
	public String getAppendicText()
	{
		return qappendix;
	}
	
	public void AddToAppendixText(String appendix)
	{
		qappendix=qappendix+"\n"+appendix;
	}
	
	public void setAppendixText(String appendix)
	{
		qappendix=appendix;
	}
	
}
