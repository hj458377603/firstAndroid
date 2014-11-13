package com.potato.gamevideo.activity;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.potato.gamevideo.R;
import com.potato.gamevideo.view.fragment.ChatMainFragment;
import com.potato.gamevideo.view.fragment.ContactFragment;
import com.potato.gamevideo.view.fragment.MeFragment;
import com.potato.gamevideo.view.fragment.NewsFragment;

public class WeChatActivity extends FragmentActivity {
	private List<Fragment> data;
	private ViewPager viewPager;
	private FragmentPagerAdapter adapter = null;
	private ImageView chatImage;
	private ImageView contactImage;
	private ImageView newsImage;
	private ImageView meImage;
	private TextView chatTxt;
	private TextView contactTxt;
	private TextView newsTxt;
	private TextView meTxt;
	private int txtColor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wechat);

		viewPager = (ViewPager) findViewById(R.id.viewPager);
		chatImage = (ImageView) findViewById(R.id.img_wechat_bottom_1);
		contactImage = (ImageView) findViewById(R.id.img_wechat_bottom_2);
		newsImage = (ImageView) findViewById(R.id.img_wechat_bottom_3);
		meImage = (ImageView) findViewById(R.id.img_wechat_bottom_4);
		chatTxt = (TextView) findViewById(R.id.txt_wechat_bottom_1);
		contactTxt = (TextView) findViewById(R.id.txt_wechat_bottom_2);
		newsTxt = (TextView) findViewById(R.id.txt_wechat_bottom_3);
		meTxt = (TextView) findViewById(R.id.txt_wechat_bottom_4);

		txtColor =  getResources().getColor(R.color.wechatGreen);//得到配置文件里的颜色
		
		
		ChatMainFragment chatMainFragment = new ChatMainFragment();
		ContactFragment contactFragment = new ContactFragment();
		NewsFragment newsFragment = new NewsFragment();
		MeFragment meFragment = new MeFragment();
		data = new ArrayList<Fragment>();
		data.add(chatMainFragment);
		data.add(contactFragment);
		data.add(newsFragment);
		data.add(meFragment);

		adapter = new FragmentPagerAdapter(getSupportFragmentManager()) {

			@Override
			public int getCount() {
				return data.size();
			}

			@Override
			public Fragment getItem(int arg0) {
				return data.get(arg0);
			}
		};

		viewPager.setAdapter(adapter);
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@SuppressLint("ResourceAsColor")
			@Override
			public void onPageSelected(int arg0) {
				resetColor();
				switch (arg0) {
				case 0:
					chatImage.setImageResource(R.drawable.wechat_bottom_1_1);
					chatTxt.setTextColor(txtColor);
					break;
				case 1:
					contactImage.setImageResource(R.drawable.wechat_bottom_2_1);
					contactTxt
							.setTextColor(txtColor);
					break;
				case 2:
					newsImage.setImageResource(R.drawable.wechat_bottom_3_1);
					newsTxt.setTextColor(txtColor);
					break;
				case 3:
					meImage.setImageResource(R.drawable.wechat_bottom_4_1);
					meTxt.setTextColor(txtColor);
					break;
				default:
					break;
				}
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});
	}

	@SuppressLint("ResourceAsColor")
	private void resetColor() {
		chatImage.setImageResource(R.drawable.wechat_bottom_1_0);
		contactImage.setImageResource(R.drawable.wechat_bottom_2_0);
		newsImage.setImageResource(R.drawable.wechat_bottom_3_0);
		meImage.setImageResource(R.drawable.wechat_bottom_4_0);

		chatTxt.setTextColor(Color.BLACK);
		contactTxt.setTextColor(Color.BLACK);
		newsTxt.setTextColor(Color.BLACK);
		meTxt.setTextColor(Color.BLACK);
	}
}
