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
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import de.lukeslog.questionnaire.main.QuestionActivity;
import de.lukeslog.questionnaire.main.Summary;
import de.lukeslog.questionnaire.questionnaireComponents.AnalogScaleAnswer;
import de.lukeslog.questionnaire.questionnaireComponents.Answer;

public class QueXMLAnalogScaleAnswer extends AnalogScaleAnswer
{

	public QueXMLAnalogScaleAnswer(String variable, String leftSide, String rightSide) 
	{
		super(variable, leftSide, rightSide);
		// TODO Auto-generated constructor stub
	}
	
	public void draw(final QuestionActivity activity, LinearLayout answerLayout, final SharedPreferences settings, final int questionNumber)
	{
		 final String variable = getVariable();
		 String preselectx= settings.getString(variable, "0");
		 int preselect = Integer.parseInt(preselectx);
		 final SeekBar sb = new SeekBar(activity);
		 sb.setProgress(preselect);
		 String leftSide = getLeftSide();
		 String rightSide = getRightSide();
		 //get screen width
		 //DisplayMetrics metrics = new DisplayMetrics();
		 int pixel=activity.getWindowManager().getDefaultDisplay().getWidth();
		 System.out.println("pixel "+pixel);
		 pixel=pixel-150;
		 System.out.println(activity.getResources().getDisplayMetrics().densityDpi);
		 int dp = (int)(pixel*activity.getResources().getDisplayMetrics().density);
		 double dpx = dp;
		 double w= dpx/1.5;
		 int wx = (int) w;
		 System.out.println(wx); 
		 TextView l = new TextView(activity);
		 l.setText(leftSide);
		 l.setWidth(75);
		 answerLayout.addView(l);
		 
		 sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
		 {
			  @Override
			  public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) 
			  {
				  arg0.setProgress(arg1);
				  System.out.println("progress changed "+arg1);
				  Editor edit = settings.edit();
				  edit.putString(variable, ""+arg1);
				  edit.commit();
				  //toastNotification(variable+"="+settings.getString(variable, ""));	
				  activity.questionWorkedOn(questionNumber);
			  }
			 
			  @Override
			  public void onStartTrackingTouch(SeekBar arg0) 
			  {
			    
			  }
			 
			  @Override
			  public void onStopTrackingTouch(SeekBar arg0) 
			  {
				  System.out.println("stopped tracking");
			    
			  }
		 });
	     sb.setLayoutParams(new LinearLayout.LayoutParams(pixel,  50));
		 answerLayout.addView(sb);
		 TextView r = new TextView(activity);
		 r.setText(rightSide);
		 r.setWidth(75);
		 answerLayout.addView(r);
	}

	@Override
	public void summary(Summary summary, LinearLayout summaryLayout, SharedPreferences settings) 
	{
		super.summary(summary, summaryLayout, settings);
		TextView tv = new TextView(summary);
		final String variable = getVariable();
		String preselectx = settings.getString(variable, "0");
		tv.setText(""+preselectx+" %");
		summaryLayout.addView(tv);
	}


}
