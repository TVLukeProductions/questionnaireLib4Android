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

import de.lukeslog.questionnaire.main.QuestionActivity;
import android.content.SharedPreferences;
import android.widget.LinearLayout;

public abstract class ScaleAnswer extends Answer
{

	private String leftSide="";
	private String rightSide="";
	
	public ScaleAnswer(String variable, String leftSide, String rightSide)
	{
		super(variable);
		this.leftSide=leftSide;
		this.rightSide=rightSide;
	}
	
	public String getRightSide()
	{
		return rightSide;
	}
	
	public String getLeftSide()
	{
		return leftSide;
	}

}
