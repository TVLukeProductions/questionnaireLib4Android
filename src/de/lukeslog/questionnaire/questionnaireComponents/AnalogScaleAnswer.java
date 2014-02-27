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

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.widget.LinearLayout;
import de.lukeslog.questionnaire.main.Summary;

public abstract class AnalogScaleAnswer extends ScaleAnswer
{

	public AnalogScaleAnswer(String variable, String leftSide, String rightSide) 
	{
		super(variable, leftSide, rightSide);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void summary(Summary summary, LinearLayout summaryLayout, SharedPreferences settings) 
	{
		String variable = getVariable();
		String entry = settings.getString(variable, "0");
		String resultSet = settings.getString("resultSet", "");
		resultSet=resultSet+variable+"="+entry+" |X| ";
		Editor edit = settings.edit();
		edit.putString("resultSet", resultSet);
		edit.commit();
	}

}
