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

package de.lukeslog.questionnaire.main;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

import de.lukeslog.questionnaire.R;
import de.lukeslog.questionnaire.queXMLComponents.QueXMLCheckBoxAnswer;
import de.lukeslog.questionnaire.queXMLComponents.QueXMLFreeAnswer;
import de.lukeslog.questionnaire.queXMLComponents.QueXMLRadioAnswer;
import de.lukeslog.questionnaire.questionnaireComponents.Answer;
import de.lukeslog.questionnaire.questionnaireComponents.CheckBoxAnswer;
import de.lukeslog.questionnaire.questionnaireComponents.FreeAnswer;
import de.lukeslog.questionnaire.questionnaireComponents.Question;
import de.lukeslog.questionnaire.questionnaireComponents.Questionnaire;
import de.lukeslog.questionnaire.questionnaireComponents.RadioAnswer;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * This calss is an activity to present the user with a summary of the Questionnaire he just completed.
 * All Questions are listed aswell as the answers.
 * 
 * @author lukas ruge
 *
 */
public class Summary extends Activity 
{

	public static String PREFS_NAME = "";
	/** 
	 * Called when the activity is first created. 
	 */
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
    	super.onCreate(savedInstanceState);
	    setContentView(R.layout.summary);
        Questionnaire q =QuestionnaireActivity.QUESTIONNAIRE;
        PREFS_NAME=q.getTitle();
	    Button sendButton = (Button) findViewById(R.id.sendButton);
	    LinearLayout summaryLayout = (LinearLayout) findViewById (R.id.summaryLayout);
	    summarize(summaryLayout);
	    //this
	    sendButton.setOnClickListener(new OnClickListener()
	    {

			@Override
			public void onClick(View v) 
			{
				SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
				final Editor edit = settings.edit();
				String resultset = settings.getString("resultSet", "");
				System.out.println(resultset);
				edit.putBoolean("finished", true);
				edit.putBoolean("finished_x", true);
				edit.commit();
				Summary.this.finish();
				
			}
	    	
	    });
    }
    
	private void summarize(LinearLayout summaryLayout) 
	{
		Questionnaire q =QuestionnaireActivity.QUESTIONNAIRE;
		ArrayList<Question> questions = q.getQuestions();
		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
		for(int i=0; i<questions.size(); i++)
		{
			Question question = questions.get(i);
			question.summary(this, summaryLayout, settings);
		}

	}
}
