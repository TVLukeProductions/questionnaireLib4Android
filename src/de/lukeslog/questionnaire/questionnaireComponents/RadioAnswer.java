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
import android.content.SharedPreferences.Editor;
import android.widget.LinearLayout;
import de.lukeslog.questionnaire.main.Summary;

/**
 * 
 * @author lukas ruge
 *
 */
public abstract class RadioAnswer extends Answer
{

	private ArrayList<CheckBoxAnswer> answers = new ArrayList<CheckBoxAnswer>();
	
	public RadioAnswer(String variable) 
	{
		super(variable);
	}
	
	public void addAnswer(CheckBoxAnswer answer)
	{
		answers.add(answer);
	}
	
	public ArrayList<CheckBoxAnswer> getAnswers()
	{
		return answers;
	}
	
	@Override
	public void summary(Summary summary, LinearLayout summaryLayout, SharedPreferences settings) 
	{
		String variable = getVariable();
		String entry = settings.getString(variable, "");
		String resultSet = settings.getString("resultSet", "");
		if(resultSet.contains(variable+"="+entry+" |X| "))
		{
			
		}
		else
		{
			resultSet=resultSet+variable+"="+entry+" |X| ";
			Editor edit = settings.edit();
			edit.putString("resultSet", resultSet);
			edit.commit();
		}
	}

}
