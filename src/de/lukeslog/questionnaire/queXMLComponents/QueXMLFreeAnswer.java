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
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import de.lukeslog.questionnaire.main.QuestionActivity;
import de.lukeslog.questionnaire.main.Summary;
import de.lukeslog.questionnaire.questionnaireComponents.*;

/**
 * 
 * 
 * @author lukas ruge
 *
 */
public class QueXMLFreeAnswer extends FreeAnswer
{

	public QueXMLFreeAnswer(String variable, String text, String format, String length,String label) 
	{
		super(variable, text, format, length, label);
	}

	@Override
	public void draw(final QuestionActivity activity, LinearLayout answerLayout, final SharedPreferences settings, final int questionNumber)
	{
		final String variable = getVariable();
		 String preselect= settings.getString(variable, "");
		 TextView text = new TextView(activity);
		 text.setText(getLabel());
		 text.setWidth(420);
		 answerLayout.addView(text);
		 final EditText quest = new EditText(activity);
		 String format = getFormat();
		 //Depending on the format of it, the textpad may be diferent
		 if(format.equals("integer"))
		 {
			 quest.setInputType(InputType.TYPE_CLASS_NUMBER);
		 }
		 if(format.equals("date"))
		 {
			 quest.setInputType(InputType.TYPE_CLASS_DATETIME);
		 }
		 if(format.equals("currency"))
		 {
			 quest.setInputType(InputType.TYPE_CLASS_NUMBER);
		 }
		 //quest.setMinWidth(300);
		 quest.setMaxEms(getLength());
		 //if this question has been answered before, this will set the value to the preselected value
		 quest.setText(preselect);
		 quest.addTextChangedListener(new TextWatcher()
		 {
			 //With every change the data in the shared variables ic changed too
			@Override
			public void afterTextChanged(Editable arg0) 
			{
				Editor edit = settings.edit();
				String value = quest.getEditableText().toString();
				edit.putString(variable, value);
				edit.commit();
				//toastNotification(variable+"="+settings.getString(variable, ""));	
				activity.questionWorkedOn(questionNumber);
			}

			@Override
			public void beforeTextChanged(CharSequence arg0,
					int arg1, int arg2, int arg3) 
			{
				
			}

			@Override
			public void onTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) 
			{
				
			}
			 
		 });
		 answerLayout.setOrientation(LinearLayout.VERTICAL);
		 quest.setWidth(420);
		 answerLayout.addView(quest);
		
	}

	@Override
	public void summary(Summary summary, LinearLayout summaryLayout, SharedPreferences settings) 
	{
		super.summary(summary, summaryLayout, settings);
		TextView tv = new TextView(summary);
		final String variable = getVariable();
		String preselectx = settings.getString(variable, " ");
		String text = getVariable();
		String label = getLabel();
		tv.setText(label+": "+preselectx);
		summaryLayout.addView(tv);
	}

}
