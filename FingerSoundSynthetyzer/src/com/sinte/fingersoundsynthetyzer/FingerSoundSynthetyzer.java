package com.sinte.fingersoundsynthetyzer;

import android.app.Activity;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class FingerSoundSynthetyzer extends Activity implements OnTouchListener {

	AudioSynthetysTask audiosynth;
	static final float BASE_FREQUENCY= 440;
	float synth_frequency = BASE_FREQUENCY;
	boolean play = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODOs Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		View mView = this.findViewById(R.id.MainView);
		mView.setOnTouchListener(this);
		audiosynth = new AudioSynthetysTask();
		audiosynth.execute();
		
	}

	@Override
	protected void onPause() {
		// TODOs Auto-generated method stub
		super.onPause();
		
		play = false;
		finish();
		
	}

	@Override
	public boolean onTouch(View v, MotionEvent me) {
		// TODOs Auto-generated method stub
		int action = me.getAction();
		switch(action){
		    case MotionEvent.ACTION_DOWN:
		    	play=true;
		    	synth_frequency = me.getX() + BASE_FREQUENCY;
		    	Log.v("freque",""+synth_frequency);
		    	break;
		    case MotionEvent.ACTION_MOVE:
		    	play = true;
		    	synth_frequency = me.getX() + BASE_FREQUENCY;
		    	Log.v("freque",""+synth_frequency);
		    	break;
		    case MotionEvent.ACTION_UP:
		    	play = false;
		    	break;
		    case MotionEvent.ACTION_CANCEL:
		    	break;
		    default:
		    	break;
		}
		return true;
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
			float angle = 0;
			while (true) {
				if (play)
				{
				    for (int i = 0; i < buffer.length; i++)
				    {
				        float angular_frequency =
				        (float)(2*Math.PI) * synth_frequency / SAMPLE_RATE;
				        buffer[i] =(short)(Short.MAX_VALUE * ((float) Math.sin(angle)));
				        angle += angular_frequency;
				    }
				    audioTrack.write(buffer, 0, buffer.length);
				} else {
				     try {
				         Thread.sleep(50);
				} catch (InterruptedException e) {
				          e.printStackTrace();
				    }
				}
			}
				
	
			//return null;
		}
		
	}
	
}
