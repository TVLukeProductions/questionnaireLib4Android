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

import de.lukeslog.questionnaire.R;
import de.lukeslog.questionnaire.queXMLComponents.QueXMLQuestionnaire;
import de.lukeslog.questionnaire.questionnaireComponents.*;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

/**
 * This Activity creates a qustionnaire by showing the user some data on the questionnaire aswell as the button to start
 * The Subcalss of Quesionnaires to be used depends on the information
 * in the Intent starting the activity
 * 
 * @author lukas ruge
 *
 */
public class QuestionnaireActivity extends Activity 
{
	String ressource="";
	public static Questionnaire QUESTIONNAIRE;
	public static String PREFS_NAME = "";
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.questionnairemain);
        ressource = getIntent().getStringExtra("ressourceURI");
        String type = getIntent().getStringExtra("QuestionnaireType");
        String termsAndConditions_String = getIntent().getStringExtra("termsAndCondition");
        
        Questionnaire q=null;
        if(type.equals("QueXML"))
        {
        	q = new QueXMLQuestionnaire(ressource);
        }
        final Button startbutton = (Button) findViewById(R.id.startbuttonX);
        startbutton.setClickable(false);
        //TODO: Loading Screen
        //Since parsing the XML or loading the data from the internet may take a while, we do this
        int counter=0;
        while(q.getTitle().equals("") && counter<20)
        {
	        try 
	        {
				Thread.sleep(2000);
			} 
	        catch (InterruptedException e) 
	        {
				e.printStackTrace();
			}
	        counter++;
        }
        //if a questionnaire could be retrieved, lets fill in the View
        if(!(q.getTitle().equals("")))
        {
        	//get the questionnaire
	        TextView questionnaireTitle = (TextView) findViewById(R.id.questionnaireTitle);
	        questionnaireTitle.setText(q.getTitle());
	        //the title of the questionnaire is also the name of the shared preferences... this is probably not that smart, since no two questionnaires can ever be named the same
	        //TODO: find a better way to name the shared prefernces or another way to cary the variables at all...
	        PREFS_NAME=q.getTitle();
	        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
			final Editor edit = settings.edit();
			//Question is a counter that counts up at what question we are (since we havent started, we initialize at 0)
			edit.putInt("Question", 0);
			edit.commit();
			//the finished variable in the sharedPreferences tells us, if the user has already finished the questionnaire, in that case he should not do it again
			final Boolean bx = settings.getBoolean("finished", false);
			if(bx)
			{
				//set the text that he has alread done this
				TextView alreadyDone = (TextView) findViewById(R.id.alreadyDone);
				alreadyDone.setText("This questionnaire has been done already");
				//change the start button to reset
				startbutton.setText("RESET");
			}
			else
			{
				//if finished is false, already done should not be visisble
				TextView alreadyDone = (TextView) findViewById(R.id.alreadyDone);
				alreadyDone.setText("");
				startbutton.setText("Start");
			}
			//set the subitle
	        TextView questionnaireSubTitle = (TextView) findViewById(R.id.questionnaireSubTitle);
	        questionnaireSubTitle.setText(q.getSubTitle()+" ("+q.getQuestions().size()+" Questions)");
	        //Investigator
	        Person investigator = q.getInvestigator();
	        if(investigator!=null)
	        {
		        TextView investiagorName = (TextView) findViewById(R.id.investiagorName);
		        TextView investiagorOrganisation = (TextView) findViewById(R.id.investiagorOrganisation);
		        TextView investiagorStreet = (TextView) findViewById(R.id.investiagorStreet);
		        TextView investiagorPostal = (TextView) findViewById(R.id.investiagorPostal);
		        TextView investiagorCountry = (TextView) findViewById(R.id.investiagorCountry);
		        TextView investiagorPhone = (TextView) findViewById(R.id.investiagorPhone);
		        TextView investiagorFax = (TextView) findViewById(R.id.investiagorFax);
		        TextView investiagorEmail = (TextView) findViewById(R.id.investiagorEmail);
		        TextView investiagorWebsite = (TextView) findViewById(R.id.investiagorWebsite);
		        
		        
		        Address address = investigator.getAddress();
		        
		        investiagorName.setText(investigator.getName());
		        investiagorOrganisation.setText(investigator.getOrganisation());
		        investiagorStreet.setText(address.getStreet());
		        investiagorPostal.setText(address.getPostalCode()+" "+address.getCity()+" "+address.getSuburb());
		        investiagorCountry.setText(address.getCountry());
		        investiagorPhone.setText(investigator.getPhoneNumber());
		        investiagorFax.setText(investigator.getFaxNumber());
		        investiagorEmail.setText(investigator.getEmailaddress());
		        investiagorWebsite.setText(investigator.getWebsite());
		        
	        }
	        //DataCollector
	        Person datacollector = q.getdataCollector();
	        if(datacollector!=null)
	        {
	        	TextView datacollectorName= (TextView) findViewById(R.id.datacollectorName);
		        TextView investiagorOrganisation = (TextView) findViewById(R.id.datacollectorOrganisation);
		        TextView investiagorStreet = (TextView) findViewById(R.id.datacollectorStreet);
		        TextView investiagorPostal = (TextView) findViewById(R.id.datacollectorPostal);
		        TextView investiagorCountry = (TextView) findViewById(R.id.datacollectorCountry);
		        TextView investiagorPhone = (TextView) findViewById(R.id.datacollectorPhone);
		        TextView investiagorFax = (TextView) findViewById(R.id.datacollectorFax);
		        TextView investiagorEmail = (TextView) findViewById(R.id.datacollectorEmail);
		        TextView investiagorWebsite = (TextView) findViewById(R.id.datacollectorWebsite);
		        
		        
		        Address address = datacollector.getAddress();
		        
		        datacollectorName.setText(datacollector.getName());
		        investiagorOrganisation.setText(datacollector.getOrganisation());
		        investiagorStreet.setText(address.getStreet());
		        investiagorPostal.setText(address.getPostalCode()+" "+address.getCity()+" "+address.getSuburb());
		        investiagorCountry.setText(address.getCountry());
		        investiagorPhone.setText(datacollector.getPhoneNumber());
		        investiagorFax.setText(datacollector.getFaxNumber());
		        investiagorEmail.setText(datacollector.getEmailaddress());
		        investiagorWebsite.setText(datacollector.getWebsite());
	        }
	        //set thi Info text
	        TextView questionnaireInfo = (TextView) findViewById(R.id.questionnaireInfo);
	        questionnaireInfo.setText(q.getBeforeText());
	        //other views will access the Questionnaire through this satic object, it would probably be smart to just put the values in there, but then we could not 
	        //use this when the program has crashed to preserve the values...
	        QUESTIONNAIRE=q;
	        //create some intents to start the survey or show the AGB
	        final Intent questionintent = new Intent(this, QuestionActivity.class);
	        final Intent agbintent = new Intent(this, AGB.class);
	        agbintent.putExtra("url", termsAndConditions_String); 
	        //TODO: put an extra in the agbintetn that has the website which should be gotten out of the intent that started this activity
	        //AGB Textlink
	        TextView agblink = (TextView) findViewById(R.id.agblink);
	        agblink.setText(Html.fromHtml("<font color='blue'><u>Terms and Conditions</u></font>"));
	        agblink.setOnClickListener(new View.OnClickListener() 
	        {
	            public void onClick(View v)
	            {
	            	//starting the AGB Activity
	            	startActivity(agbintent);
	            }
	        });
	        //startbutton
	        startbutton.setOnClickListener(new View.OnClickListener()
	        {
	        	public void onClick(View v)
	        	{
	        		//only if it is clickable (that means the user has accepted the AGB)
	        		if(startbutton.isClickable())
	        		{
	        			//get the setting
	        			SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
	        			final Boolean bxx = settings.getBoolean("finished", false);
	        			//if the questionnaire has been finished one can only reset it but not take part
	        			if(bxx)
	        			{
	        				reset();
	        			}
	        			else
	        			{
	        				//start the survey/questionnaire
	            			startActivity(questionintent);	
	        			}
	        		}
	        		else
	        		{
	        			//This never happends...
	        			toastNotification("You have not acceptes the terms and conditions.");
	        		}
	        	}
	        });
	        startbutton.setClickable(false);
	        final CheckBox acceptAGB = (CheckBox) findViewById(R.id.acceptAGB);
	        acceptAGB.setOnCheckedChangeListener(new OnCheckedChangeListener() 
	        {
	
				@Override
				public void onCheckedChanged(CompoundButton arg0, boolean arg1) 
				{
					if(acceptAGB.isChecked())
					{
						
						startbutton.setClickable(true);
					}
					else
					{
						startbutton.setClickable(false);
					}
				}
	        });
        }
        else
        {
        	//TODO: create a message, that the questionnaire could not be loaded because of a timeout.
        }
    }
   
    
    @Override
    public void onResume()
    {
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
		final Editor edit = settings.edit();
		edit.putInt("Question", 0);
		edit.commit();
		Boolean bx = settings.getBoolean("finished", false);
		if(bx)
		{
			TextView alreadyDone = (TextView) findViewById(R.id.alreadyDone);
			alreadyDone.setText("This questionnaire has been done already");
			final Button startbutton = (Button) findViewById(R.id.startbuttonX);
			startbutton.setText("RESET");
			Boolean bx_x = settings.getBoolean("finished_x", false);
			if(bx_x)
			{
				edit.putBoolean("finished_x", false);
				edit.commit();
				Intent resultIntent = new Intent();
				//fill the rsult intent with the result from the questionnaire
				String bla = settings.getString("resultSet", "");
				resultIntent.putExtra("resultSet", bla);
				setResult(Activity.RESULT_OK, resultIntent);
				QuestionnaireActivity.this.finish();
			}
		}
		else
		{
			TextView alreadyDone = (TextView) findViewById(R.id.alreadyDone);
			alreadyDone.setText("");
		}
		super.onResume();
    }
    
    /**
     * Creating a toast notification
     * @param text
     */
    private void toastNotification(String text)
    {
    	Context context = getApplicationContext();
    	int duration = Toast.LENGTH_SHORT;

    	Toast toast = Toast.makeText(context, text, duration);
    	toast.show();
    }
    
    /**
     * resetting all the share preferences for the questionnaire and resetting the button to start the questionnaire
     */
    private void reset()
    {
    	//get preferences
    	SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
		final Editor edit = settings.edit();
		//clear the preferences
		edit.clear();
		//set Question to 0 as to start anew
		edit.putInt("Question", 0);
		edit.commit();
		Button startbutton = (Button) findViewById(R.id.startbuttonX);
		startbutton.setText("Umfrage beginnen");
		TextView alreadyDone = (TextView) findViewById(R.id.alreadyDone);
		alreadyDone.setText("");
    }
}