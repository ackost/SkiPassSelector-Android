package com.example.skipassselector;

import java.util.HashSet;
import java.util.Locale;

import com.example.skipassselector.CalendarFragment.OnTicketChangedListener;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.widget.Toast;


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
	
	
	
	
	// HashSets will hold strings representations of the dates tickets are added
	static HashSet<String> fourHourMwSet = new HashSet<String>();
	static HashSet<String> eightHourMwSet = new HashSet<String>();
	static HashSet<String> allDayMwSet = new HashSet<String>();
	static HashSet<String> nightMwSet = new HashSet<String>();
	
	static HashSet<String> fourHourWkndSet = new HashSet<String>();
	static HashSet<String> eightHourWkndSet = new HashSet<String>();
	static HashSet<String> allDayWkndSet = new HashSet<String>();
	static HashSet<String> nightWkndSet = new HashSet<String>();
			
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


	public static float getWindowRateTotal() {
		return ticketMath_WindowRate(fourHourMwSet, eightHourMwSet, allDayMwSet, nightMwSet, fourHourWkndSet, eightHourWkndSet, allDayWkndSet, nightWkndSet);
	}
	
	public static float ticketMath_WindowRate(HashSet<String> fhmw, HashSet<String> ehmw, HashSet<String> admw, HashSet<String> nmw, 
			HashSet<String> fhwknd, HashSet<String> ehwknd, HashSet<String> adwknd, HashSet<String> nwknd) {
		// returns the sum of all tickets * rates, at full price
		int fhmwDays = fhmw.size();
		int ehmwDays = ehmw.size();
		int admwDays = admw.size();
		int nmwDays = nmw.size();
		
		int fhwkndDays = fhwknd.size();
		int ehwkndDays = ehwknd.size();
		int adwkndDays = adwknd.size();
		int nwkndDays = nwknd.size();
		
		float total = 0;
		
		total = (fhmwDays * R.integer.fhmwRate) + (ehmwDays * R.integer.ehmwRate) + (admwDays * R.integer.admwRate) + (nmwDays * R.integer.nmwRate) + 
				(fhwkndDays * R.integer.fhwkndRate) + (ehwkndDays * R.integer.ehwkndRate) + (adwkndDays * R.integer.adwkndRate) + (nwkndDays * R.integer.adwkndRate);
		
		return total;
	}
	
	public static float getAcRateTotal() {
		return ticketMath_AdvantageCard(fourHourMwSet, eightHourMwSet, allDayMwSet, nightMwSet, fourHourWkndSet, eightHourWkndSet, allDayWkndSet, nightWkndSet);
	}
	
	public static float ticketMath_AdvantageCard(HashSet<String> fhmw, HashSet<String> ehmw, HashSet<String> admw, HashSet<String> nmw, 
			HashSet<String> fhwknd, HashSet<String> ehwknd, HashSet<String> adwknd, HashSet<String> nwknd) {
		// returns the sum tickets * rates, at advantage card rates, after removing 1 of every 6 days 
		
		int fhmwDays = fhmw.size();
		int ehmwDays = ehmw.size();
		int admwDays = admw.size();
		int nmwDays = nmw.size();
		
		int fhwkndDays = fhwknd.size();
		int ehwkndDays = ehwknd.size();
		int adwkndDays = adwknd.size();
		int nwkndDays = nwknd.size();
		
		//TODO: put an IF here to determine if early price should be used
		float total = R.integer.advantageCard;
		
		int[] daysCountArray = {fhmwDays, ehmwDays, admwDays, nmwDays, fhwkndDays, ehwkndDays, adwkndDays, nwkndDays};
		int[] ratesArray = {R.integer.fhmwRate, R.integer.ehmwRate, R.integer.admwRate, R.integer.nmwRate, R.integer.fhwkndRate, R.integer.ehwkndRate, R.integer.adwkndRate, nwkndDays * R.integer.nwkndRate};
		//if any single ticket types have more than 6, reduce free days from those ticket types first
		for (int i = 0; i < daysCountArray.length; i++) {
			if (daysCountArray[i] >= 6) {
				daysCountArray[i] = daysCountArray[i] - (daysCountArray[i] / 6);
			}
		}
		
		//if (after any reductions above) the total days of tickets is more than 6, reduce free days (indiscriminately)
		int dayCountSum = 0;
		//first, are there more than 6 total days?
		for (int j = 0; j < daysCountArray.length; j++) {
			dayCountSum = dayCountSum + daysCountArray[j];
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
				// What this really means is:  total = (fhmwDays * R.integer.fhmwRate) + (ehmwDays * R.integer.ehmwRate) + (admwDays * R.integer.admwRate) + (nmwDays * R.integer.nmwRate) + 
				//			(fhwkndDays * R.integer.fhwkndRate) + (ehwkndDays * R.integer.ehwkndRate) + (adwkndDays * R.integer.adwkndRate) + (nwkndDays * R.integer.nwkndRate);
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
		// TODO Auto-generated method stub
		String tMessage = "Just testing this listener";
		Toast.makeText(MainActivity.this, tMessage, Toast.LENGTH_SHORT).show();
			
		//mSectionsPagerAdapter.getItem(1);
	}

	
	
	
	

}
