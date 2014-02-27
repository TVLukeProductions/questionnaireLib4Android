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

import android.content.SharedPreferences;
import android.widget.LinearLayout;
import de.lukeslog.questionnaire.main.QuestionActivity;
import de.lukeslog.questionnaire.main.Summary;

/**
 * 
 * Lukas Ruge
 * 
 * @author lukas
 *
 */
public abstract class Answer 
{

	private ArrayList<Question> contigentQuestions = new ArrayList<Question>();
	private String variable;
	
	/**
	 * 
	 * if label is left blank the question is understood as a free response question
	 * 
	 * @param label
	 * @param value
	 */
	public Answer(String variable)
	{
		this.variable=variable;
	}
	
	public void addContigentQuestion(Question question)
	{
		contigentQuestions.add(question);
	}
	
	public ArrayList<Question> getContigentQuestions()
	{
		return contigentQuestions;
	}
	
	public String getVariable()
	{
		return variable;
	}
	
	public abstract void draw(QuestionActivity activity, LinearLayout answerLayout, SharedPreferences settings, int questionNumber);
	
	public abstract void summary(Summary summary, LinearLayout summaryLayout, SharedPreferences settings);
	
}
