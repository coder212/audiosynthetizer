package com.contoh.audiosinthetyzer;

import android.app.Activity;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class AudioSinthetyzerActivity extends Activity implements OnClickListener {

	Button start,end;
	AudioSynthetysTask audiosynth;
	boolean keepOnGoing = false;
	float synth_frequency = 440; //440 Hz, Middle A
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODOs Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		start = (Button)findViewById(R.id.start);
		start.setOnClickListener(this);
		end = (Button)findViewById(R.id.end);
		end.setOnClickListener(this);
		
		end.setEnabled(false);
		
	}

	@Override
	protected void onPause() {
		// TODOs Auto-generated method stub
		super.onPause();
		keepOnGoing = false;
		end.setEnabled(false);
		start.setEnabled(true);
	}

	@Override
	public void onClick(View v) {
		// TODOs Auto-generated method stub
		if(v == start){
			keepOnGoing = true;
			
			audiosynth = new AudioSynthetysTask();
			audiosynth.execute();
			
			end.setEnabled(true);
			start.setEnabled(false);
		}else if(v == end){
			keepOnGoing = false;
			
			end.setEnabled(false);
			start.setEnabled(true);
		}
		
	}
	
	private class AudioSynthetysTask extends AsyncTask<Void, Void, Void>{

		@Override
		protected Void doInBackground(Void... params) {
			// TODOs Auto-generated method stub
			final int SAMPLE_RATE = 11025;
			
			@SuppressWarnings("deprecation")
			int minSize = AudioTrack.getMinBufferSize(SAMPLE_RATE, AudioFormat.CHANNEL_CONFIGURATION_STEREO, AudioFormat.ENCODING_PCM_16BIT);
			@SuppressWarnings("deprecation")
			AudioTrack audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC, SAMPLE_RATE, AudioFormat.CHANNEL_CONFIGURATION_STEREO, AudioFormat.ENCODING_PCM_16BIT, minSize, AudioTrack.MODE_STREAM);
			audioTrack.play();
			short[] buffer = new short[minSize];
			float angular_frequency = (float)(2*Math.PI)* synth_frequency / SAMPLE_RATE;
			float angle = 0;
			while(keepOnGoing){
				
				for(int i =0 ;i < buffer.length;i++){
					
					buffer[i] = (short)(Short.MAX_VALUE * ((float)Math.sin(angle)));
					angle += angular_frequency;
					
				}
				
				audioTrack.write(buffer, 0, buffer.length);
				
			}
			return null;
		}
		
	}

}
