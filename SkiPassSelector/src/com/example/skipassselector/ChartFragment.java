package com.example.skipassselector;

import org.achartengine.GraphicalView;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ChartFragment extends Fragment {
	/**
	 * The fragment argument representing the section number for this
	 * fragment.
	 */
	public static final String ARG_SECTION_NUMBER = "section_number";
	
	public ChartFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	} 
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View fragView = inflater.inflate(R.layout.chart_fragment_layout,container, false);
		
		BarGraph bg = new BarGraph();
		GraphicalView bgView = bg.getView(getActivity());
		
		ViewGroup chartContainer = (ViewGroup)fragView.findViewById(R.id.chart_container);
		chartContainer.addView(bgView);
		
		return fragView;
	}
	
	@Override
	public void onResume(){
		super.onResume();
		
		BarGraph bg = new BarGraph();
		GraphicalView bgView = bg.getView(getActivity());
		
		ViewGroup chartContainer = (ViewGroup)getView().findViewById(R.id.chart_container);
		chartContainer.addView(bgView);
		
	}
	
//	public View refreshChart(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
//		View fragView = inflater.inflate(R.layout.chart_fragment_layout,container, false);
//		
//		BarGraph bg = new BarGraph();
//		View bgView = bg.getView(getActivity());
//		
//		ViewGroup chartContainer = (ViewGroup)fragView.findViewById(R.id.chart_container);
//		chartContainer.addView(bgView);
//		
//		return fragView;
//
//	}
	
}
	
	
	
	

	
	
