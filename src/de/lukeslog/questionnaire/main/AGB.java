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
import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

/**
 * show th agb
 * 
 * @author lukas ruge
 *
 */
public class AGB  extends Activity 
{
    
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agblayout);
        String url = getIntent().getStringExtra("url");
        WebView webview = (WebView) findViewById(R.id.webView1);
        webview.loadUrl(url);
    }
}
