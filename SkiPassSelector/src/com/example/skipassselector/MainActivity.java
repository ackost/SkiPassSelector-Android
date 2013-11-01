package com.example.skipassselector;

import java.util.HashMap;
import java.util.Locale;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.widget.Toast;

import com.example.skipassselector.CalendarFragment.OnTicketChangedListener;


public class MainActivity extends FragmentActivity implements
		ActionBar.TabListener, OnTicketChangedListener  {  //

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a
	 * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which
	 * will keep every loaded fragment in memory. If this becomes too memory
	 * intensive, it may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	SectionsPagerAdapter mSectionsPagerAdapter;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;
	
	//HashMap uses dates as keys, ticket types as values
	public static HashMap<String, String> datesAndTickets = new HashMap<String, String>();
			
	static TicketSets ticketsets;  //enum of ticket names
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);     
        
		// Set up the action bar.
		final ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		// Create the adapter that will return a fragment for each of the 
		// primary sections of the app.
		mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);

		//Create initial ResultsFragment and put it in the ViewPager
		ResultsFragment firstRF = new ResultsFragment();
		firstRF.setArguments(getIntent().getExtras());
		getSupportFragmentManager().beginTransaction().add(R.id.pager, firstRF, "tag_123").commit();
		
		//Create initial ChartFragment and put it in the ViewPager
		ChartFragment firstCF = new ChartFragment();
		firstCF.setArguments(getIntent().getExtras());
		getSupportFragmentManager().beginTransaction().add(R.id.pager, firstCF).commit();
		
		
		
		// When swiping between different sections, select the corresponding
		// tab. We can also use ActionBar.Tab#select() to do this if we have
		// a reference to the Tab.
		mViewPager
				.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
					@Override
					public void onPageSelected(int position) {
						actionBar.setSelectedNavigationItem(position);
					}
				});

		// For each of the sections in the app, add a tab to the action bar.
		for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
			// Create a tab with text corresponding to the page title defined by
			// the adapter. Also specify this Activity object, which implements
			// the TabListener interface, as the callback (listener) for when
			// this tab is selected.
			actionBar.addTab(actionBar.newTab()
					.setText(mSectionsPagerAdapter.getPageTitle(i))
					.setTabListener(this));
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onTabSelected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
		// When the given tab is selected, switch to the corresponding page in
		// the ViewPager.
		mViewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	@Override
	public void onTabReselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}


	public static int getWindowRateTotal() {
		return ticketMath_WindowRate();
	}
	
	public static int ticketMath_WindowRate() {
		// returns the sum of all tickets * rates, at full price
		int fhmwDays = 0;
		int ehmwDays = 0;
		int admwDays = 0;
		int nmwDays = 0;
		int fhwkndDays = 0;
		int ehwkndDays = 0;
		int adwkndDays = 0;
		int nwkndDays = 0;
		
		int total = 0;
		
		for (String s : datesAndTickets.values()){
			if (s.equals("FOUR_HOUR_MW"))
				fhmwDays++;
			if (s.equals("EIGHT_HOUR_MW"))
				ehmwDays++;
			if (s.equals("ALL_DAY_MW"))
				admwDays++;
			if (s.equals("NIGHT_MW"))
				nmwDays++;
			if (s.equals("FOUR_HOUR_WKND"))
				fhwkndDays++;
			if (s.equals("EIGHT_HOUR_WKND"))
				ehwkndDays++;
			if (s.equals("ALL_DAY_WKND"))
				adwkndDays++;
			if (s.equals("NIGHT_WKND"))
				nwkndDays++;
		}
		
		total = (fhmwDays * 50) + (ehmwDays * 55) + (admwDays * 64) + (nmwDays * 42) + 
				(fhwkndDays * 60) + (ehwkndDays * 67) + (adwkndDays * 71) + (nwkndDays * 42);
		
		
		return total;
	}
	
	public static float getAcRateTotal() {
		return ticketMath_AdvantageCard();
	}
	
	public static float ticketMath_AdvantageCard() {
		// returns the sum tickets * rates, at advantage card rates, after removing 1 of every 6 days 
		
		int fhmwDays = 0;
		int ehmwDays = 0;
		int admwDays = 0;
		int nmwDays = 0;
		int fhwkndDays = 0;
		int ehwkndDays = 0;
		int adwkndDays = 0;
		int nwkndDays = 0;
		
		//TODO: put an IF here to determine if early price should be used
		// if (not early flag = true) {
		//float total = 119; } else {
		float total = 84;
		
		for (String s : datesAndTickets.values()){
			if (s.equals("FOUR_HOUR_MW"))
				fhmwDays++;
			if (s.equals("EIGHT_HOUR_MW"))
				ehmwDays++;
			if (s.equals("ALL_DAY_MW"))
				admwDays++;
			if (s.equals("NIGHT_MW"))
				nmwDays++;
			if (s.equals("FOUR_HOUR_WKND"))
				fhwkndDays++;
			if (s.equals("EIGHT_HOUR_WKND"))
				ehwkndDays++;
			if (s.equals("ALL_DAY_WKND"))
				adwkndDays++;
			if (s.equals("NIGHT_WKND"))
				nwkndDays++;
		}
		
		int[] daysCountArray = {fhmwDays, ehmwDays, admwDays, nmwDays, fhwkndDays, ehwkndDays, adwkndDays, nwkndDays};
		int[] ratesArray = {50, 55, 64, 42, 60, 67, 71, 42};
		
		//if any single ticket types have more than 6, reduce free days from those ticket types first
		for (int i = 0; i < daysCountArray.length; i++) {
			if (daysCountArray[i] >= 6) {
				daysCountArray[i] = daysCountArray[i] - (daysCountArray[i] / 6);
			}
		}
		
		//if (after any reductions above) the total days of tickets is more than 6, reduce free days (indiscriminately)
		int dayCountSum = 0;
		//first, add up the days from all categories
		for (int j : daysCountArray) {
			dayCountSum = dayCountSum + j;
		}
		//find how many free days, and remove that number of days from the first available ticket type 
		if (dayCountSum >= 6) {
			int reduceDays = dayCountSum / 6;
			for (int k = 0; k < daysCountArray.length; k++) {	
				while (reduceDays > 0 && daysCountArray[k] > 0) {   //as long as there are tickets that need to be removed, 
					daysCountArray[k] = daysCountArray[k]--;        //and there are tickets of this type available to subtract,
					reduceDays = reduceDays--;						//reduce both values by 1 (at a time)
				}
				//now do the math to find the window rate cost of remaining tickets
				total = total + (daysCountArray[k] * ratesArray[k]);	 
				// now reduce total by advantage card discount (40%)
				total = (float) (total * 0.6);
			}
		}
			
		return total;
	}
	
	//TODO: public static float getNccRateTotal()
	//TODO: public static float ticketMath_Ncc(...)
	//TODO: public static float getNccAcRateTotal()
	//TODO: public static float ticketMathNccAc(...)
	
	
	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			// getItem is called to instantiate the fragment for the given page.
			switch (position) {
			case 0:
				Fragment fragment = new CalendarFragment();
				Bundle args = new Bundle();
				args.putInt(CalendarFragment.ARG_SECTION_NUMBER, position + 1);
				fragment.setArguments(args);
				return fragment;
			case 1:
				Fragment fragment2 = new ChartFragment();
				Bundle args2 = new Bundle();
				args2.putInt(ChartFragment.ARG_SECTION_NUMBER, position + 1);
				fragment2.setArguments(args2);
				return fragment2;
			case 2:
				Fragment fragment3 = new ResultsFragment();
				Bundle args3 = new Bundle();
				args3.putInt(ResultsFragment.ARG_SECTION_NUMBER, position + 1);
				fragment3.setArguments(args3);
				return fragment3;
				
			
			//default to Calendar Fragment	
			default:
				Fragment fragment4 = new CalendarFragment();
				Bundle args4 = new Bundle();
				args4.putInt(CalendarFragment.ARG_SECTION_NUMBER, position + 1);
				fragment4.setArguments(args4);
				return fragment4;
			}
					
		}

		@Override
		public int getCount() {
			// How many tabs will be used.
			return 3;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();
			switch (position) {
			case 0:
				return getString(R.string.title_section1).toUpperCase(l);
			case 1:
				return getString(R.string.title_section3).toUpperCase(l);
			case 2:
				return getString(R.string.title_section2).toUpperCase(l);
			}
			return null;
		}
	}

	@Override
	public void onTicketChangedListener() {
		
		String tMessage = "Just testing this listener";
		Toast.makeText(MainActivity.this, tMessage, Toast.LENGTH_SHORT).show();

			
	}

	
	
	
	

}
