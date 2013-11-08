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
	private GraphicalView bgView;
	
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

		//debug console output
		System.out.println("ChartFragment resumed");
		//System.out.println("Chart id = " + this.getId());
		//System.out.println("Chart tag = " + this.getTag());
		
		LinearLayout chartContainer = (LinearLayout)getView().findViewById(R.id.chart_container);
		//if there's an existing bgView, remove it before adding new one (if not, new one won't display)
		if (bgView != null){
			chartContainer.removeView(bgView);
		}
		
		bgView = bg.getView(getActivity(), bg.addNewSeries());
		
		chartContainer.addView(bgView);
	}
	
	@Override
	public void onPause() {
		super.onPause();
		//debug console output
		System.out.println("ChartFragment paused");
	}
	
	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
	    super.setUserVisibleHint(isVisibleToUser);
	    if (isVisibleToUser) {
	    	//debug console output
	    	System.out.println("ChartFragment is visibleHint = " + isVisibleToUser);
	    	
	    	this.onResume();
	    }
	    else { 
	    	//debug console output
	    	System.out.println("ChartFragment is visibleHint = " + isVisibleToUser); 
	    }
	}
	
}
