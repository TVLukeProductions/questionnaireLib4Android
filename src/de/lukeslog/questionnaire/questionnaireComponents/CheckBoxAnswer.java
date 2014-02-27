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

import de.lukeslog.questionnaire.main.Summary;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.widget.LinearLayout;

/**
 * 
 * @author lukas ruge
 *
 */
public abstract class CheckBoxAnswer extends Answer
{
	private String label="";
	private String value="";
	private String image="";
	private Bitmap picture;
	private String skipTo="";
	
	public CheckBoxAnswer(String variable, String label, String value, String image, String skipTo)
	{
		super(variable);
		this.label=label;
		this.value=value;
		this.image=image;
		this.skipTo=skipTo;
	}
	
	public String getLabel()
	{
		return label;
	}
	
	public String getValue()
	{
		return value;
	}
	
	public String getImage()
	{
		return image;
	}
	
	public String getSkipTo()
	{
		return skipTo;
	}
	
	public Bitmap getPicture()
	{
		return picture;
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
