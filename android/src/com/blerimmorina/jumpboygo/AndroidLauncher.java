package com.blerimmorina.jumpboygo;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.blerimmorina.jumpboygo.handlers.AdsManager;
import com.blerimmorina.jumpboygo.main.Game;
import com.chartboost.sdk.CBLocation;
import com.chartboost.sdk.Chartboost;
import com.chartboost.sdk.ChartboostDelegate;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;


public class AndroidLauncher extends AndroidApplication {
	protected AdView adview;

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Chartboost.startWithAppId(this, "580bf8db43150f40e2bf956e", "8c945a019fbd6f371f89815ba072292001eb6bf4");
		Chartboost.onCreate(this);


		RelativeLayout layout = new RelativeLayout(this);

		AdsManager androidManager = new AndroidAdsManager();

		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		View gameview = initializeForView(new Game(androidManager), config);
		layout.addView(gameview);

		adview = new AdView(this);
		adview.setAdSize(AdSize.SMART_BANNER);
		adview.setAdUnitId("ca-app-pub-4044738077600137/6989389004");

		AdRequest.Builder builder = new AdRequest.Builder();
		RelativeLayout.LayoutParams adParams = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT
		);
		adParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);

		layout.addView(adview, adParams);
		adview.loadAd(builder.build());

		setContentView(layout);
	}

	@Override
	public void onStart() {
		super.onStart();
		Chartboost.onStart(this);
		Chartboost.showInterstitial(CBLocation.LOCATION_MAIN_MENU);
	}

	@Override
	public void onResume() {
		super.onResume();
		Chartboost.onResume(this);
	}

	@Override
	public void onPause() {
		super.onPause();
		Chartboost.onPause(this);
	}

	@Override
	public void onStop() {
		super.onStop();
		Chartboost.onStop(this);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Chartboost.onDestroy(this);
	}

	@Override
	public void onBackPressed() {
		// If an interstitial is on screen, close it.
		if (Chartboost.onBackPressed())
			return;
		else
			super.onBackPressed();
	}

	public class AndroidAdsManager implements AdsManager {

		public Integer adsShowWindow = 0;

		private Integer j = 1;

		public void showAdsGameOver() {

			if(adsShowWindow == 1) {
				return;
			} else {
				adsShowWindow = 1;
				j++;
			}

			if((j%3) == 0) {
				System.out.println("game over ads");
				Chartboost.showInterstitial(CBLocation.LOCATION_GAMEOVER);
			}
		}


		public void closeWindow() {
			adsShowWindow=0;
		}
	}
}
