package com.cetcme.rcldandroidZhejiang.xia;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.cetcme.rcldandroidZhejiang.R;
import com.cetcme.rcldandroidZhejiang.jecInfoHttp.GetWebDataWithHttpGet;
import com.cetcme.rcldandroidZhejiang.jecInfoHttp.ParseJson;
import com.cetcme.rcldandroidZhejiang.MyClass.NavigationView;
import com.umeng.analytics.MobclickAgent;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DetailPayInsuranceInfoActivity extends Activity {

	private ArrayList<HashMap<String, Object>> mSeachResultArrayList = null;

	private LinearLayout mDetailInfolLayout = null;
	private LayoutInflater mInflater = null;
	private String mURL = null;
	private final String TAG = "DetailPortInfoActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.detail_info_layout);


		mInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		mDetailInfolLayout = (LinearLayout) findViewById(R.id.detail_fish_ship_info);


		mSeachResultArrayList = new ArrayList<HashMap<String, Object>>();

		String shipDetectID = getIntent().getExtras().getString("id");
		new GetDetailInsuranceInfoTask().execute(new String[] { shipDetectID });

		initNavigationView();
	}

	public void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
	}
	public void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}

	public void onBackPressed() {
		super.onBackPressed();
		overridePendingTransition(R.anim.stay, R.anim.push_up_out_no_alpha);
	}

	private NavigationView navigationView;

	private void initNavigationView() {
		navigationView = (NavigationView) findViewById(R.id.nav_main_in_detail_info_layout);
		navigationView.setTitle("渔船详细信息");
		navigationView.setBackView(R.drawable.icon_back_button);
		navigationView.setClickCallback(new NavigationView.ClickCallback() {

			@Override
			public void onRightClick() {
				Log.i("main","点击了右侧按钮!");
			}

			@Override
			public void onBackClick() {
				Log.i("main","点击了左侧按钮!");
				onBackPressed();
			}
		});
	}

	private Handler mUpdateUIHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub

			HashMap<String, String> map = (HashMap<String, String>) msg.obj;
			String temp = "";

			Iterator iter = map.entrySet().iterator();
			while (iter.hasNext()) {
				Entry entry = (Entry) iter.next();
				String key = entry.getKey().toString();
				if ("null".equalsIgnoreCase(map.get(key).toString())) {
					map.put(key, "");
				}
			}
			View view1 = mInflater.inflate(R.layout.detail_info_item, null);
			((TextView) view1.findViewById(R.id.key))
					.setText(getString(R.string.c_boat_name));
			((TextView) view1.findViewById(R.id.value)).setText(map.get(
					"c_boat_name").toString());
			mDetailInfolLayout.addView(view1);

			View view2 = mInflater.inflate(R.layout.detail_info_item, null);
			((TextView) view2.findViewById(R.id.key))
					.setText(getString(R.string.c_boat_cde));
			((TextView) view2.findViewById(R.id.value)).setText(map.get(
					"c_boat_cde").toString());
			mDetailInfolLayout.addView(view2);

			View view3 = mInflater.inflate(R.layout.detail_info_item, null);
			((TextView) view3.findViewById(R.id.key))
					.setText(getString(R.string.c_nme_cn));
			((TextView) view3.findViewById(R.id.value)).setText(map.get(
					"c_nme_cn").toString());
			mDetailInfolLayout.addView(view3);

			View view5 = mInflater.inflate(R.layout.detail_info_item, null);
			((TextView) view5.findViewById(R.id.key))
					.setText(getString(R.string.c_ply_app_no));
			((TextView) view5.findViewById(R.id.value)).setText(map.get(
					"c_ply_app_no").toString());
			mDetailInfolLayout.addView(view5);

			View view4 = mInflater.inflate(R.layout.detail_info_item, null);
			((TextView) view4.findViewById(R.id.key))
					.setText(getString(R.string.c_ply_no));
			((TextView) view4.findViewById(R.id.value)).setText(map.get(
					"c_ply_no").toString());
			mDetailInfolLayout.addView(view4);

			View view6 = mInflater.inflate(R.layout.detail_info_item, null);
			((TextView) view6.findViewById(R.id.key))
					.setText(getString(R.string.c_app_nme));
			((TextView) view6.findViewById(R.id.value)).setText(map.get(
					"c_app_nme").toString());
			mDetailInfolLayout.addView(view6);

			View view7 = mInflater.inflate(R.layout.detail_info_item, null);
			((TextView) view7.findViewById(R.id.key))
					.setText(getString(R.string.c_app_cde));
			((TextView) view7.findViewById(R.id.value)).setText(map.get(
					"c_app_cde").toString());
			mDetailInfolLayout.addView(view7);

			View view8 = mInflater.inflate(R.layout.detail_info_item, null);
			((TextView) view8.findViewById(R.id.key))
					.setText(getString(R.string.t_insrnc_bgn_tm));
			temp = map.get("t_insrnc_bgn_tm").toString();
			if (null != temp && !"null".equalsIgnoreCase(temp)
					&& temp.length() > 0) {

				((TextView) view8.findViewById(R.id.value))
						.setText(getDateFromTime(temp.substring(
								temp.indexOf("(") + 1, temp.indexOf("+"))));
			} else {
				((TextView) view8.findViewById(R.id.value)).setText("");
			}
			mDetailInfolLayout.addView(view8);

			View view9 = mInflater.inflate(R.layout.detail_info_item, null);
			((TextView) view9.findViewById(R.id.key))
					.setText(getString(R.string.T_insrnc_end_tm));
			temp = map.get("T_insrnc_end_tm").toString();
			if (null != temp && !"null".equalsIgnoreCase(temp)
					&& temp.length() > 0) {

				((TextView) view9.findViewById(R.id.value))
						.setText(getDateFromTime(temp.substring(
								temp.indexOf("(") + 1, temp.indexOf("+"))));
			} else {
				((TextView) view9.findViewById(R.id.value)).setText("");
			}
			mDetailInfolLayout.addView(view9);

			View view10 = mInflater.inflate(R.layout.detail_info_item, null);
			((TextView) view10.findViewById(R.id.key))
					.setText(getString(R.string.n_prm));
			((TextView) view10.findViewById(R.id.value)).setText(map.get(
					"n_prm").toString());
			mDetailInfolLayout.addView(view10);

			View view14 = mInflater.inflate(R.layout.detail_info_item, null);
			((TextView) view14.findViewById(R.id.key))
					.setText(getString(R.string.n_amt));
			((TextView) view14.findViewById(R.id.value)).setText(map.get(
					"n_amt").toString());
			mDetailInfolLayout.addView(view14);

			View view17 = mInflater.inflate(R.layout.detail_info_item, null);
			((TextView) view17.findViewById(R.id.key))
					.setText(getString(R.string.t_app_udr_tm));
			temp = map.get("t_app_udr_tm").toString();
			if (null != temp && !"null".equalsIgnoreCase(temp)
					&& temp.length() > 0) {

				((TextView) view17.findViewById(R.id.value))
						.setText(getDateFromTime(temp.substring(
								temp.indexOf("(") + 1, temp.indexOf("+"))));
			} else {
				((TextView) view17.findViewById(R.id.value)).setText("");
			}
			mDetailInfolLayout.addView(view17);

			View view18 = mInflater.inflate(R.layout.detail_info_item, null);
			((TextView) view18.findViewById(R.id.key))
					.setText(getString(R.string.t_app_udr_tm));
			temp = map.get("t_udr_date").toString();
			if (null != temp && !"null".equalsIgnoreCase(temp)
					&& temp.length() > 0) {

				((TextView) view18.findViewById(R.id.value))
						.setText(getDateFromTime(temp.substring(
								temp.indexOf("(") + 1, temp.indexOf("+"))));
			} else {
				((TextView) view18.findViewById(R.id.value)).setText("");
			}

			mDetailInfolLayout.addView(view18);

			View view11 = mInflater.inflate(R.layout.detail_info_item, null);
			((TextView) view11.findViewById(R.id.key))
					.setText(getString(R.string.n_rate));
			((TextView) view11.findViewById(R.id.value)).setText(map.get(
					"n_rate").toString());
			mDetailInfolLayout.addView(view11);

			View view12 = mInflater.inflate(R.layout.detail_info_item, null);
			((TextView) view12.findViewById(R.id.key))
					.setText(getString(R.string.c_orig_flg));
			((TextView) view12.findViewById(R.id.value)).setText(map.get(
					"c_orig_flg").toString());
			mDetailInfolLayout.addView(view12);

			// c_udr_mrk
			View view13 = mInflater.inflate(R.layout.detail_info_item, null);
			((TextView) view13.findViewById(R.id.key))
					.setText(getString(R.string.c_udr_mrk));
			((TextView) view13.findViewById(R.id.value)).setText(map.get(
					"c_udr_mrk").toString());
			mDetailInfolLayout.addView(view13);

			super.handleMessage(msg);

		}
	};

	private HashMap<String, String> mDetailPortInfoHashMap = new HashMap<String, String>();

	class GetDetailInsuranceInfoTask extends AsyncTask<String, Void, String> {

		String mResult = null;
		Boolean isLoginOk = false;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub

			mURL = "http://61.164.218.155:5000/bpm/YZSoft/Webservice/AppWebservice.asmx/GetPlyDetail?";
			super.onPreExecute();
		}

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub

			mURL = mURL + "id=" + params[0];
			Log.d(TAG, "URL = " + mURL + ";");
			mResult = new GetWebDataWithHttpGet().executeGet(mURL);

			Log.d(TAG, "get result:" + mResult + "!!");
			if (null != mResult && mResult.length() > 0) {

				ParseJson parseJson = new ParseJson();
				mDetailPortInfoHashMap.clear();
				mDetailPortInfoHashMap = parseJson.getPlydetailInfo(mResult);
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub

			if (mDetailPortInfoHashMap.size() > 0) {
				Log.d(TAG, "get edtail ship data ok!");
				Message msg = new Message();
				msg.what = 0x00f0;
				msg.obj = mDetailPortInfoHashMap;
				mUpdateUIHandler.sendMessage(msg);
			}
			super.onPostExecute(result);
		}

		@Override
		protected void onProgressUpdate(Void... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);
		}

	}

	@SuppressLint("UseValueOf")
	public String getDateFromTime(String t) {

		String regEx = "[^\\-?0-9]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(t);
		Long time = 0L;
		Date date = null;
		try {
			time = new Long(m.replaceAll("").trim());
			date = new Date(time);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return new SimpleDateFormat("yyyy-MM-dd").format(date);
	}

}
