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

import java.util.ArrayList;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
public class QueXMLRadioAnswer extends RadioAnswer 
{

	public QueXMLRadioAnswer(String variable) 
	{
		super(variable);
	}

	@Override
	public void draw(final QuestionActivity activity, LinearLayout answerLayout, final SharedPreferences settings, final int questionNumber)
	{
		 ArrayList<CheckBoxAnswer> radioOptions = getAnswers();
		 final RadioGroup group= new RadioGroup(activity);
		 for(int j=0; j<radioOptions.size(); j++)
		 {
			 CheckBoxAnswer option = radioOptions.get(j);
			 String label = option.getLabel();
			 final String value = option.getValue();
			 final String variable = option.getVariable();
			 final RadioButton rb = new RadioButton(activity);
			 rb.setText(label);
			 String preselect= settings.getString(variable, "");
			 if(preselect.equals(value))
			 {
				 //if this question has been answered before, this wiull set the value to the preselected value
				 rb.setChecked(true);
			 }
			 //rb.setTextColor(Color.BLACK);
			 rb.setOnCheckedChangeListener(new OnCheckedChangeListener()
			 {
					@Override
				public void onCheckedChanged(CompoundButton arg0, boolean arg1) 
				{
						activity.questionWorkedOn(questionNumber);
						if(rb.isChecked())
						{
							Editor edit = settings.edit();
							edit.putString(variable, value);
							edit.commit();
							for(int k=0; k<group.getChildCount(); k++)
							{
								RadioButton b = (RadioButton) group.getChildAt(k);
								if(b.equals(rb))
								{
									
								}
								else
								{
									//nececcary because of the preselect. otherwise all buttons in the radiogroup may be selected.
									b.setChecked(false);
								}
							}
						}
				}
			 });
			 group.addView(rb);
		 }
		 answerLayout.addView(group);
	}

	@Override
	public void summary(Summary summary, LinearLayout summaryLayout, SharedPreferences settings)
	{
		super.summary(summary, summaryLayout, settings);
		ArrayList<CheckBoxAnswer> radioOptions = getAnswers();
		for(int j=0; j<radioOptions.size(); j++)
		{
			CheckBoxAnswer option = radioOptions.get(j);
			String label = option.getLabel();
			final String value = option.getValue();
			final String variable = option.getVariable();
			String preselectx = settings.getString(variable, " ");	
			TextView tv = new TextView(summary);
			if(preselectx.equals(value))
			{
				tv.setText(label);
			}
			summaryLayout.addView(tv);
		 }
		
	}

}
