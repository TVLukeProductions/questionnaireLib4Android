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

package de.lukeslog.questionnaire.queXMLComponents;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;
import de.lukeslog.questionnaire.main.QuestionActivity;
import de.lukeslog.questionnaire.main.Summary;
import de.lukeslog.questionnaire.questionnaireComponents.*;

/**
 * 
 * 
 * @author lukas ruge
 *
 */
public class QueXMLCheckBoxAnswer extends CheckBoxAnswer
{

	public QueXMLCheckBoxAnswer(String variable, String label, String value, String image, String skipTo) 
	{
		super(variable, label, value, image, skipTo);
	}

	@Override
	public void draw(final QuestionActivity activity, LinearLayout answerLayout, final SharedPreferences settings, final int questionNumber)
	{
		final String variable = getVariable();
		 String text = settings.getString(variable, "");
		 String label = getLabel();
		 System.out.println(label);
		 final String value = getValue();
		 CheckBox cb = new CheckBox(activity);
		 cb.setText(label);
		 if(text.contains(value+","))
		 {
			 cb.setChecked(true);
		 }
		 cb.setOnCheckedChangeListener(new OnCheckedChangeListener()
		 {
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) 
			{
				if(arg1)
				{
					activity.questionWorkedOn(questionNumber);
					String text = settings.getString(variable, "");
					text=value+","+text;
					Editor edit = settings.edit();
					edit.putString(variable, text);
					edit.commit();
				}
				else
				{
					String text = settings.getString(variable, "");
					text=text.replace(value+",", "");
					Editor edit = settings.edit();
					edit.putString(variable, text);
					edit.commit();
				}
			}
		 });
		 answerLayout.setOrientation(LinearLayout.VERTICAL);
		 answerLayout.addView(cb);
		
	}

	@Override
	public void summary(Summary summary, LinearLayout summaryLayout, SharedPreferences settings) 
	{
		super.summary(summary, summaryLayout, settings);
		String label = getLabel();
		final String value = getValue();
	 	final String variable = getVariable();
	 	String preselectx = settings.getString(variable, " ");	
	 	TextView tv = new TextView(summary);
	 	if(preselectx.contains(value))
	 	{
		 	tv.setText(label);
		 	summaryLayout.addView(tv);
	 	}
		
	}

}
