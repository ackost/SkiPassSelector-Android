package com.example.skipassselector;

import org.achartengine.GraphicalView;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class ChartFragment extends Fragment {
	/**
	 * The fragment argument representing the section number for this
	 * fragment.
	 */
	public static final String ARG_SECTION_NUMBER = "section_number";
	
	private BarGraph bg;
	
	public ChartFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	} 
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View fragView = inflater.inflate(R.layout.chart_fragment_layout,container, false);
		bg = new BarGraph();
		return fragView;
	}
	
		
	@Override
	public void onResume() {
		super.onResume();

		LinearLayout chartContainer = (LinearLayout)getView().findViewById(R.id.chart_container);
		
		GraphicalView bgView = bg.getView(getActivity(), bg.addNewSeries("fromResume"));
		chartContainer.addView(bgView);
		

	}
	
}
	
	
	
	

	
	
