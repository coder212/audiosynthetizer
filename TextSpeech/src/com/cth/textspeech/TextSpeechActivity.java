package com.cth.textspeech;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public  class TextSpeechActivity extends Activity implements OnInitListener {
	
	private int CHECK_DATA = 0;
	private TextToSpeech tts;
	private EditText input;
	private Button strt;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODOs Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		input = (EditText)findViewById(R.id.inputtext);
		strt = (Button)findViewById(R.id.startspeech);
		strt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODOs Auto-generated method stub
				String text = input.getText().toString();
				if(text !=null && text.length()>0){
					Toast.makeText(TextSpeechActivity.this, "mengucapkan : "+text, Toast.LENGTH_LONG).show();
					tts.speak(text, TextToSpeech.QUEUE_ADD, null);
				}
			}
		});
		
		Intent intent = new Intent();
		intent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
		startActivityForResult(intent, CHECK_DATA);
		
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODOs Auto-generated method stub
		//super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == CHECK_DATA){
			if(resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS){
				// success , create tts instance
				tts = new TextToSpeech(this, this);
			}else{
				//install data because missing it.
				Intent installIntent = new Intent();
				installIntent.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
				startActivity(installIntent);
			}
		}
	}

	@Override
	public void onInit(int status) {
		// TODOs Auto-generated method stub
		if(status == TextToSpeech.SUCCESS){
			Toast.makeText(TextSpeechActivity.this,"berhasil inisiasi text to speech", Toast.LENGTH_LONG).show();
		}else{
			Toast.makeText(TextSpeechActivity.this,"gagal inisiasi text to speech", Toast.LENGTH_LONG).show();
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODOs Auto-generated method stub
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODOs Auto-generated method stub
		switch(item.getItemId()){
		case R.id.settings:
			startActivity(new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS));
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
		//return super.onOptionsItemSelected(item);
	}

}
