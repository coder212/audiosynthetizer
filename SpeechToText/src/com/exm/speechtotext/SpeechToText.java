package com.exm.speechtotext;

import java.util.ArrayList;
import java.util.Locale;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class SpeechToText extends Activity {

	private TextView text;
	private ImageButton btn;
	private final int REQ_CODE_SPEECH_INPUT = 100;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODOs Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		getActionBar().hide();
		
		text = (TextView)findViewById(R.id.textfromspeak);
		btn = (ImageButton)findViewById(R.id.btnSpeak);
		btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODOs Auto-generated method stub
				
				promptSpeech();
				
			}
		});
	}
	
	private void promptSpeech(){
		Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
		intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
		intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
		intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"ayo ngomong aja ");
		try{
			startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
		}catch (ActivityNotFoundException e){
			Toast.makeText(getApplicationContext(),"devicemu ngga support speech to text.",Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODOs Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		switch(requestCode){
		case REQ_CODE_SPEECH_INPUT:
			if(resultCode == RESULT_OK && data != null){
				ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
				text.setText(result.get(0));
			}
			break;
		}
	}
	
	
}
