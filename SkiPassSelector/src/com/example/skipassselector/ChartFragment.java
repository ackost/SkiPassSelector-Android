package com.example.skipassselector;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

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

		System.out.println("ChartFragment onResume called");
		LinearLayout chartContainer = (LinearLayout)getView().findViewById(R.id.chart_container);
		if (bgView != null){
			chartContainer.removeView(bgView);
		}
		Date dateStamp = new java.util.Date();
		Format formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String ds = formatter.format(dateStamp);
		System.out.println("ds = " + ds);
		bgView = bg.getView(getActivity(), bg.addNewSeries(ds));
		chartContainer.addView(bgView);
	}
	
	@Override
	public void onPause() {
		super.onPause();
		System.out.println("ChartFragment paused");
	}
	
	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
	    super.setUserVisibleHint(isVisibleToUser);
	    if (isVisibleToUser) {
	    	System.out.println("ChartFragment is visibleHint = " + isVisibleToUser);
	    	this.onResume();
	    }
	    else { System.out.println("ChartFragment is visibleHint = " + isVisibleToUser); }
	}
	
}
	
	
	
	

	
	
