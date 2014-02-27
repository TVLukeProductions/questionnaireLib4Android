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
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;
import de.lukeslog.questionnaire.main.QuestionActivity;
import de.lukeslog.questionnaire.main.Summary;

/**
 * 
 * 
 * @author lukas ruge
 *
 */
public abstract class Question 
{
	private String id="";
	private String text="";
	private String sectionText="";
	boolean randomize=false;
	protected ArrayList<Answer> answers = new ArrayList<Answer>();
	private ArrayList<Question> subquestions = new ArrayList<Question>();
	
	/**
	 * Question is a Question in a Questionnaire. SectionText is the Text of the section in which this question appears
	 * The id is a singlar id for the question
	 * the text
	 * 
	 * @param sectionText the text of the section the question appers in
	 * @param id an ID
	 * @param text the question itself
	 * @param answers an arraylist of answers
	 * @param randomize the information of the answers are supposed to be displayed randomized
	 */
	public Question(String sectionText, String id, String text, ArrayList<Answer> answers, boolean randomize)
	{
		this.id = id;
		this.text=text;
		System.out.println("Text:"+this.text);
		this.answers = answers;
		this.randomize=randomize;
		this.sectionText=sectionText;
	}
	
	/**
	 * returns the number of questions including subquestions if it is a matrix question 
	 * 
	 * @return number of questions as int (1 if this is not a matrix question)
	 */
	public int numberOfQuestionsAndSubquestions()
	{
		//if there are no subquestions the result will be 1
		int result = 1;
		//get all the subsquestions
		for(int i=0; i<subquestions.size(); i++)
		{
			//add the number of subquestions of every question, which will count itself automatically, since this function is called, in that question
			Question question = subquestions.get(i);
			//add that up
			result = result+question.numberOfQuestionsAndSubquestions();
		}
		return result;
	}
	
	/**
	 * add subquestions as an array. only matrix questions have subquestions and they are always CheckBoxQuestions
	 * 
	 * However, you can add any kind of questions, the viausalization at this point just does not support that at all... (I have no idea what is going to happen)
	 * 
	 * @param questions
	 */
	public void addSubQuestions(ArrayList<Question> questions)
	{
		subquestions=questions;
	}
	
	/**
	 * returns alist of all subquestion
	 * 
	 * @return
	 */
	public ArrayList<Question> getSubQuestions()
	{
		return subquestions;
	}
	
	/**
	 * set the section text.
	 * 
	 * The section text is the Text of the section the question appears in
	 * 
	 * @param sectionText
	 */
	public void setSectionText(String sectionText)
	{
		this.sectionText=sectionText;
	}
	
	/**
	 * returns the section text
	 * 
	 * The section text is the Text of the section the question appears in
	 * 
	 * @return
	 */
	public String getSectionText()
	{
		return sectionText;
	}
	
	/**
	 * set the etxt of the actual question
	 * 
	 * @param text
	 */
	public void setText(String text)
	{
		this.text=text;
	}
	
	/**
	 * get the text of the actual question.
	 * 
	 * @return
	 */
	public String getText()
	{
		System.out.println("getText()->"+text);
		return text;
	}
	
	/**
	 * Retuns all the answers of a question, answers may have subansers or contingient questions depending on the type of answer
	 * 
	 * @return ArrayList of ANswer objects
	 */
	public ArrayList<Answer> getAnswers()
	{
		return answers;
	}
	
	/**
	 * returns the ID of a question (may be blank in many cases)
	 * 
	 * @return String ID
	 */
	public String getID()
	{
		return id;
	}
	
	public void summary(Summary summary, LinearLayout summaryLayout, SharedPreferences settings)
	{
		TextView theqtext = new TextView(summary);
		
		theqtext.setText(text);
		 summaryLayout.addView(theqtext);
		 String resultSet=settings.getString("resultSet", "");
		 if(getSubQuestions().size()>1)
         {
			 ArrayList<Question> subQuestions = getSubQuestions();
			 ArrayList<Answer> answers =getAnswers();
			 RadioAnswer a = (RadioAnswer) answers.get(0);
		   	 final ArrayList<CheckBoxAnswer> cbas= a.getAnswers();
		   	 for(int i=0; i<subQuestions.size(); i++)
		   	 {
		   		 TextView tv = new TextView(summary);
		   		 String text = subQuestions.get(i).getText();
		   		 for(int j=0; j<cbas.size(); j++)
		   		 {
		   			String preselectx = settings.getString(subQuestions.get(i).getID(), " ");	
					String value = cbas.get(j).getValue();
					String label = cbas.get(j).getLabel();
		   			if(preselectx.equals(value))
					{
		   				text=text+": "+label;
		   				resultSet=resultSet+subQuestions.get(i).getID()+"="+value+" |X| ";
		   				Editor edit = settings.edit();
		   				edit.putString("resultSet", resultSet);
		   				edit.commit();
					}
		   		 }
		   		tv.setText(text);
		   		summaryLayout.addView(tv);
		   	 }	   	 
		   	 
         }
		 else
		 {
			 //not a matrix question
		   	 for(int i=0; i<answers.size(); i++)
		   	 {
		   		 Answer a = answers.get(i);
		   		 a.summary(summary, summaryLayout, settings);
		   	 }
		 }
		 TextView tv2 = new TextView(summary);
		 tv2.setText(" ");
		 summaryLayout.addView(tv2);
	}
	
	public void draw(QuestionActivity activity, LinearLayout answerLayout, SharedPreferences settings, int questionNumber) 
	{
		 if(getSubQuestions().size()>1)
         {
        	 drawMatrixQuestion(activity, answerLayout, settings, questionNumber);
         }
		 else
		 {
			 //not a matrix question
		   	 for(int i=0; i<answers.size(); i++)
		   	 {
		   		 Answer a = answers.get(i);
		   		 a.draw(activity, answerLayout, settings, questionNumber);
		   	 }
		 }
	}
	

	private void drawMatrixQuestion(final QuestionActivity activity, LinearLayout answerLayout, final SharedPreferences settings, final int questionNumber) 
	{
   	 ArrayList<Question> subQuestions = getSubQuestions();
   	 ArrayList<Answer> answers =getAnswers();
   	 //[A]
   	 RadioAnswer a = (RadioAnswer) answers.get(0);
   	 final ArrayList<CheckBoxAnswer> cbas= a.getAnswers();
   	 
   	 TableLayout matrix = new TableLayout(activity);
   	 int collums = cbas.size()+1;
   	 int rows = subQuestions.size()+1;
   	 for(int i=0; i<rows; i++)
   	 {
   		 TableRow tr = new TableRow(activity);
   		 if(i==0)
   		 {
   			 //header
   			 for(int j=0; j<collums; j++)
   			 {
   				 if(j==0)
   				 {
   					 TextView corner = new TextView(activity);
   					 tr.addView(corner);
   				 }
   				 else
   				 {
   					 //[B]
   					 CheckBoxAnswer cba = cbas.get(j-1);
   					 TextView at = new TextView(activity);
   					 at.setText(cba.getLabel()+"   ");
   					 tr.addView(at);
   				 }
   			 }
   		 }
   		 else
   		 {
			Question subquestion = subQuestions.get(i-1);
			final String theqID=subquestion.getID();
				 //[C]
   			 final ArrayList<RadioButton> radioGroup = new ArrayList<RadioButton>();
   			 for(int j=0; j<collums-1; j++)
   			 {
   				 RadioButton rb = new RadioButton(activity);
   				 radioGroup.add(rb);
   			 }
   			 //add Listener to all of these buttons, make sure they
   			 //set the either stuff unchecked and
   			 //put stuff into the SharePreferences
   			 for(int j=0; j<radioGroup.size(); j++)
   			 {
   				 final RadioButton rb = radioGroup.get(j);
				 final String value = cbas.get(j).getValue();
					 //[D]
				 String preselect = settings.getString(theqID, "");
					 if(preselect.equals(value))
					 {
						 rb.setChecked(true);
					 }
   				 rb.setOnCheckedChangeListener(new OnCheckedChangeListener()
   				 {

						@Override
						public void onCheckedChanged(CompoundButton arg0, boolean arg1) 
						{
							activity.questionWorkedOn(questionNumber);
							if(rb.isChecked())
							{
								for(int k=0; k<radioGroup.size(); k++)
								{
									RadioButton b = radioGroup.get(k);
									if(b.equals(rb))
									{
										final Editor edit = settings.edit();
										edit.putString(theqID, value);
										edit.commit();
										//[D(2)]
									}
									else
									{
										b.setChecked(false);
									}
								}
							}
						}
   					 
   				 });
   			 }
   			 //adding the subquestion and the radiobuttonbs to the view
   			 for(int j=0; j<collums; j++)
   			 {
   				if(j==0)
   				{
       				TextView tv = new TextView(activity);
       				tv.setText(subquestion.getText()+"  ");
       				tr.addView(tv);
   				}
   				else
   				{
   					RadioButton rb = radioGroup.get(j-1);
   					tr.addView(rb);
   				}
   			 }
   			 //adding subquestion
   		 }
   		 tr.setPadding(4, 4, 4, 4);
   		 matrix.addView(tr);
   		 matrix.setPadding(4, 4, 4, 4);
   		 
   	 }
   	 answerLayout.addView(matrix);
		
	}
}
