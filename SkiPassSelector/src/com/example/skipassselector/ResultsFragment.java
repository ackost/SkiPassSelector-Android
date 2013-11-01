package com.example.skipassselector;

import android.support.v4.app.Fragment; 
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class ResultsFragment extends Fragment{

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
		TextView seasonPassTextView = (TextView)getView().findViewById(R.id.seasonPassResultsText);
		seasonPassTextView.setText("449");
		TextView windowRateTextView = (TextView)getView().findViewById(R.id.windowRateResultsText);
		windowRateTextView.setText(Integer.toString(MainActivity.getWindowRateTotal()));
	}
	
	
}