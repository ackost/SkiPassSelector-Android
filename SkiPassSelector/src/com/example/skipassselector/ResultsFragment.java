package com.example.skipassselector;

import android.support.v4.app.Fragment; 
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class ResultsFragment extends Fragment{
	/**
	 * The fragment argument representing the section number for this
	 * fragment.
	 */
	public static final String ARG_SECTION_NUMBER = "section_number";
	
	public ResultsFragment() {
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View fragView = inflater.inflate(R.layout.results_fragment_layout,container, false);
		
		return fragView;
	}
	
	public void setText(int id, String text) {
		TextView textView = (TextView)getView().findViewById(id);
		textView.setText(text);
	}
	
	@Override
	public void onResume() {
		super.onResume();
		
		//debug console output
		System.out.println("ResultsFragment resumed");
		
		TextView seasonPassTextView = (TextView)getView().findViewById(R.id.seasonPassResultsText);
		seasonPassTextView.setText("$" + Integer.toString(MainActivity.getSeasonPassTotal()) + ".00");
		
		TextView windowRateTextView = (TextView)getView().findViewById(R.id.windowRateResultsText);
		windowRateTextView.setText("$" + Integer.toString(MainActivity.getWindowRateTotal()) + ".00");
		
		TextView AcRateTextView = (TextView)getView().findViewById(R.id.advantageCardResultsText);
		AcRateTextView.setText("$" + String.format("%.2f", MainActivity.getAcRateTotal()));
		
		TextView NccRateTextView = (TextView)getView().findViewById(R.id.nightClubResultsText);
		NccRateTextView.setText("$" + String.format("%.2f", MainActivity.getNccRateTotal()));
		
		TextView NccAcRateTextView = (TextView)getView().findViewById(R.id.nccAcResultsText);
		NccAcRateTextView.setText("$" + String.format("%.2f", MainActivity.getNccAcRateTotal()));
		
		double lowAvg = 0;
		String lowName = null;
		int days = MainActivity.datesAndTickets.size();
		if (days != 0) {
			double avgSP = MainActivity.getSeasonPassTotal() / days;
			double avgWR = MainActivity.getWindowRateTotal() / days;
			double avgAC = MainActivity.getAcRateTotal() / days;
			double avgNCC = MainActivity.getNccRateTotal() / days;
			double avgNccAc = MainActivity.getNccAcRateTotal() / days;
				
			if (avgSP < avgWR) {
				lowAvg = avgSP; 
				lowName = getActivity().getString(R.string.Season_pass);
			} else {
				lowAvg = avgWR;
				lowName = getActivity().getString(R.string.Window_rate);}
			if (avgAC < lowAvg) {
				lowAvg = avgAC;
				lowName = getActivity().getString(R.string.Adv_card);
				}
			if (avgNCC < lowAvg) {
				lowAvg = avgNCC;
				lowName = getActivity().getString(R.string.Night_card);
				}
			if (avgNccAc < lowAvg) {
				lowAvg = avgNccAc;
				lowName = getActivity().getString(R.string.NCC_w_AC);
				}
		
			TextView LowestAvgResultsTextView = (TextView)getView().findViewById(R.id.lowestAvgResultsText);
			LowestAvgResultsTextView.setText("$" + String.format("%.2f", lowAvg) + " / trip");
			TextView LowestAvgTextView = (TextView)getView().findViewById(R.id.lowestAvgText);
			LowestAvgTextView.setText(R.string.Lowest_per_trip);
			TextView LowestAvgTextName = (TextView)getView().findViewById(R.id.lowestAvgName);
			LowestAvgTextName.setText(lowName);
			
		}
	}
	
	@Override
	public void onPause() {
		super.onPause();
		
		//debug console output
		System.out.println("ResultsFragment paused");
	}
	

	
}
