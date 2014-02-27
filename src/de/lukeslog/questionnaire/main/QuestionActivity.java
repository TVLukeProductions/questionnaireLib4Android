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

import de.lukeslog.questionnaire.R;
import de.lukeslog.questionnaire.questionnaireComponents.*;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 
 * An activity displaying a question.
 * 
 * the onCreate is rather complex and strange.... but its a complex issue
 * 
 * @author lukas ruge
 *
 */
public class QuestionActivity extends Activity 
{
	public static String PREFS_NAME = "";
	/** 
	 * Called when the activity is first created. 
	 */
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
    	 super.onCreate(savedInstanceState);
         setContentView(R.layout.question);
         //get the questionnaire
         Questionnaire q =QuestionnaireActivity.QUESTIONNAIRE;
         TextView questionnaireName = (TextView) findViewById(R.id.questionnaireName);
         questionnaireName.setText(q.getTitle());
         PREFS_NAME=q.getTitle();
         final SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
         final int qnr= settings.getInt("Question", 0); //qnr is the number at which we are right now
         int x = q.getQuestions().size();
         //the progress bar
         ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
         progressBar.setMax(x);
         progressBar.setProgress(qnr+1);
         //untouched is actually false when the question is untouched... and true if has already been changed in some way.
         //only if it is true the nextButton can be pressed
         //TODO: on questions where there is more then one thing to do, this has to be reflected in more then one of these...
         final boolean untouched= settings.getBoolean("Q_"+qnr, false);
         //generating some intents for later
         final Intent nextQuestionIntent = new Intent(this, QuestionActivity.class);
         final Intent questionnaireSummary = new Intent(this, Summary.class);
         ArrayList<Question> questions = q.getQuestions();
         final int qsize=questions.size();
         System.out.println("QNR:"+questions.size());
         if(qnr<questions.size())
         {
             Question question = questions.get(qnr);
             TextView sectionName = (TextView) findViewById(R.id.sectionName);
             sectionName.setText(question.getSectionText());
             TextView questionNumber = (TextView) findViewById(R.id.questionNumber);
             questionNumber.setText(""+(qnr+1));
             TextView questionText =(TextView) findViewById(R.id.questionText);
             questionText.setText(""+question.getText());
             LinearLayout answerLayout = (LinearLayout) findViewById(R.id.answerLayout);
             //--------------------------------------------------------------------------------------
             // Drawing is done by Questions and their Answers individually
             //--------------------------------------------------------------------------------------
             question.draw(this, answerLayout, settings, qnr);

             //--------------------------------------------------------------------------------------
             // The Buttons
             //--------------------------------------------------------------------------------------
             ImageButton prevousButton = (ImageButton) findViewById(R.id.prevousButton);
             ImageButton nextButton = (ImageButton) findViewById(R.id.nextButton);
             if(qnr==0)
             {
            	 //this does nothing...
            	 prevousButton.setClickable(false);
             }
             if(qnr==questions.size()-1)
             {
            	 //if the last question is reached, set the image to another image
            	 //TODO: find a good image
            	 nextButton.setImageResource(R.drawable.letter);
             }
             //nextbuttonlistener
             nextButton.setOnClickListener(new View.OnClickListener() 
             {

				@Override
				public void onClick(View v) 
				{
					SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
			        //int qnr= settings.getInt("Question", 0);
					final Editor edit = settings.edit();
					edit.putInt("Question", (qnr+1));
					edit.commit();
					if(qnr==qsize-1)
		            {
						startActivity(questionnaireSummary);
		            }
					else
					{
						startActivity(nextQuestionIntent);
					}
					QuestionActivity.this.finish();
				}
             });
             //previousbuttonlistner
             prevousButton.setOnClickListener(new View.OnClickListener() 
             {

				@Override
				public void onClick(View v) 
				{
					SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
			        //int qnr= settings.getInt("Question", 0);
					if(qnr==0)
					{
						//if qnr==0 this is the first question. the thing can just be closed
						QuestionActivity.this.finish();
					}
					else
					{
						final Editor edit = settings.edit();
						edit.putInt("Question", (qnr-1));
						edit.commit();
						startActivity(nextQuestionIntent);
						QuestionActivity.this.finish();
					}
				}
             });
             //if !untouched the user has not changed anything in the question. he can not continue...
             if(!untouched)
             {
            	 nextButton.setClickable(false);
             }
         }         
         
    }
    
    /**
     * method to make toast notifications on screen while in development
     * 
     * @param text
     */
    public void toastNotification(String text)
    {
    	Context context = getApplicationContext();
    	int duration = Toast.LENGTH_SHORT;

    	Toast toast = Toast.makeText(context, text, duration);
    	toast.show();
    }
    
    /**
     *
     * called when a question is first worked on to activate the untouched setting...
     * 
     * @param qnr
     */
	public void questionWorkedOn(int qnr) 
	{
		ImageButton nextButton = (ImageButton) findViewById(R.id.nextButton);
		nextButton.setClickable(true);
		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
		final Editor edit = settings.edit();
		edit.putBoolean("Q_"+qnr, true);
		edit.commit();
	}
}
