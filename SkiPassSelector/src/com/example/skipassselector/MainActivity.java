package com.example.skipassselector;

import java.util.HashMap;
import java.util.Locale;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
//import android.widget.Toast;

import com.example.skipassselector.CalendarFragment.OnTicketChangedListener;
import com.example.skipassselector.ResetDialog.OnResetSelectedListener;


public class MainActivity extends FragmentActivity implements
		ActionBar.TabListener, OnTicketChangedListener, OnResetSelectedListener  {  //

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
	public static boolean earlyPassPriceFlag = true;		
		
	
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
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()){
		case R.id.reset_button:
			ResetDialog resetDialog = new ResetDialog();
			resetDialog.show(getSupportFragmentManager(),"resetter");
			return true;
		case R.id.help:
			HelpDialogFragment helpDialog = new HelpDialogFragment();
			helpDialog.show(getSupportFragmentManager(),"helper");
			return true;
		case R.id.earlyPassFlagItem:
			if (item.isChecked()) {
				item.setChecked(false);
				earlyPassPriceFlag=false;
			} else {
				item.setChecked (true);
				earlyPassPriceFlag=true;
			}
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
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

	public static int getSeasonPassTotal() {
		if (earlyPassPriceFlag) {
			return 449; 
		} else {
			return 649;
		}
	}
	
	public static int getWindowRateTotal() {
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
			if (s.startsWith("NIGHT_MW"))  //counts both Early Season Flag and non-ESF night tickets
				nmwDays++;
			if (s.equals("FOUR_HOUR_WKND"))
				fhwkndDays++;
			if (s.equals("EIGHT_HOUR_WKND"))
				ehwkndDays++;
			if (s.equals("ALL_DAY_WKND"))
				adwkndDays++;
			if (s.startsWith("NIGHT_WKND"))  //counts both Early Season Flag and non-ESF night tickets
				nwkndDays++;
		}
		
		total = (fhmwDays * 50) + (ehmwDays * 55) + (admwDays * 64) + (nmwDays * 42) + 
				(fhwkndDays * 60) + (ehwkndDays * 67) + (adwkndDays * 71) + (nwkndDays * 42);
		
		
		return total;
	}
	
	public static double getAcRateTotal() {
		// returns the sum tickets * rates, at advantage card rates, after removing 1 of every 6 days 
		
		int fhmwDays = 0;
		int ehmwDays = 0;
		int admwDays = 0;
		int nmwDays = 0;
		int fhwkndDays = 0;
		int ehwkndDays = 0;
		int adwkndDays = 0;
		int nwkndDays = 0;
		
		int reduceDays = 0;
		double total = 0;
		
		for (String s : datesAndTickets.values()){
			if (s.equals("FOUR_HOUR_MW"))
				fhmwDays++;
			if (s.equals("EIGHT_HOUR_MW"))
				ehmwDays++;
			if (s.equals("ALL_DAY_MW"))
				admwDays++;
			if (s.startsWith("NIGHT_MW"))  // counts both Early Season Flag and non-ESF night tickets
				nmwDays++;
			if (s.equals("FOUR_HOUR_WKND"))
				fhwkndDays++;
			if (s.equals("EIGHT_HOUR_WKND"))
				ehwkndDays++;
			if (s.equals("ALL_DAY_WKND"))
				adwkndDays++;
			if (s.startsWith("NIGHT_WKND"))  // counts both Early Season Flag and non-ESF night tickets
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
			reduceDays = dayCountSum / 6;
		}
		
		for (int k = 0; k < daysCountArray.length; k++) {	
			while (reduceDays > 0 && daysCountArray[k] > 0) {   //as long as there are tickets that need to be removed, 
				daysCountArray[k] = daysCountArray[k]--;        //and there are tickets of this type available to subtract,
				reduceDays = reduceDays--;						//reduce both values by 1 (at a time)
			}
			//now do the math to find the window rate cost of remaining tickets
			total = total + (daysCountArray[k] * ratesArray[k]);	 
			
			
		}
		// now reduce total by advantage card discount (40%)
		total = (double) (total * 0.6);
		
		if (earlyPassPriceFlag) {
			 total = total + 84; 
		} else {
			total =  total + 119;
		}
		return total;
	}
	
	public static double getNccRateTotal(){
		// Night tickets after Jan 2nd are free
		// other tickets are at window rate
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
			if (s.equals("NIGHT_MW_ESF"))   //night tickets without early season flag are free
				nmwDays++;
			if (s.equals("FOUR_HOUR_WKND"))
				fhwkndDays++;
			if (s.equals("EIGHT_HOUR_WKND"))
				ehwkndDays++;
			if (s.equals("ALL_DAY_WKND"))
				adwkndDays++;
			if (s.equals("NIGHT_WKND_ESF"))  //night tickets without early season flag are free
				nwkndDays++;
		}
		
		total = (fhmwDays * 50) + (ehmwDays * 55) + (admwDays * 64) + (nmwDays * 42) +
				(fhwkndDays * 60) + (ehwkndDays * 67) + (adwkndDays * 71) + (nwkndDays * 42);
		
		if (earlyPassPriceFlag) {
			 total = total + 179; 
		} else {
			total =  total + 204;
		}
		
		return total;
	};
	
	public static double getNccAcRateTotal(){
		//night tickets after Jan 2nd are free
		//non-night tickets and any tickets before Jan 2nd are 40%
		//every 6th ticket (not counting nights after Jan 2nd) is free
		int fhmwDays = 0;
		int ehmwDays = 0;
		int admwDays = 0;
		int nmwDays = 0;
		int fhwkndDays = 0;
		int ehwkndDays = 0;
		int adwkndDays = 0;
		int nwkndDays = 0;
		
		int reduceDays = 0;
		double total = 0;
		
		//gets counts of all tickets excepts Nights after Jan 2nd
		for (String s : datesAndTickets.values()){
			if (s.equals("FOUR_HOUR_MW"))
				fhmwDays++;
			if (s.equals("EIGHT_HOUR_MW"))
				ehmwDays++;
			if (s.equals("ALL_DAY_MW"))
				admwDays++;
			if (s.equals("NIGHT_MW_ESF"))
				nmwDays++;
			if (s.equals("FOUR_HOUR_WKND"))
				fhwkndDays++;
			if (s.equals("EIGHT_HOUR_WKND"))
				ehwkndDays++;
			if (s.equals("ALL_DAY_WKND"))
				adwkndDays++;
			if (s.equals("NIGHT_WKND_ESF"))
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
			reduceDays = dayCountSum / 6;
		}
		
		for (int k = 0; k < daysCountArray.length; k++) {	
			while (reduceDays > 0 && daysCountArray[k] > 0) {   //as long as there are tickets that need to be removed, 
				daysCountArray[k] = daysCountArray[k]--;        //and there are tickets of this type available to subtract,
				reduceDays = reduceDays--;						//reduce both values by 1 (at a time)
			}
			//now do the math to find the window rate cost of remaining tickets
			total = total + (daysCountArray[k] * ratesArray[k]);	 
		}
		
		// now reduce total by advantage card discount (40%)
		total = (double) (total * 0.6);
		
		if (earlyPassPriceFlag) {
			 total = total + 199; 
		} else {
			total =  total + 224;
		}
			
		return total;
	};
	
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
				Fragment fragment0 = new CalendarFragment();
				Bundle args0 = new Bundle();
				args0.putInt(CalendarFragment.ARG_SECTION_NUMBER, position + 1);
				fragment0.setArguments(args0);
				return fragment0;
			case 1:
				Fragment fragment1 = new ChartFragment();
				Bundle args1 = new Bundle();
				args1.putInt(ChartFragment.ARG_SECTION_NUMBER, position + 1);
				fragment1.setArguments(args1);
				return fragment1;
			case 2:
				Fragment fragment2 = new ResultsFragment();
				Bundle args2 = new Bundle();
				args2.putInt(ResultsFragment.ARG_SECTION_NUMBER, position + 1);
				fragment2.setArguments(args2);
				return fragment2;
				
			}
			return null;
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
				return getString(R.string.title_section2).toUpperCase(l);
			case 2:
				return getString(R.string.title_section3).toUpperCase(l);
			}
			return null;
		}
	}

	
	@Override
	public void onTicketChangedListener() {
	
	//  debug toast output
	//	String tMessage = "Just testing this listener";
	//	Toast.makeText(MainActivity.this, tMessage, Toast.LENGTH_SHORT).show();

	}
	

	@Override
	public void onPositiveClickListener() {
		datesAndTickets.clear();
	}
}
