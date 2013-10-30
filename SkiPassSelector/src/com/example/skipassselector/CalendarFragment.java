package com.example.skipassselector;

import java.util.Calendar;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.PopupMenu;
import android.widget.Toast;
import android.widget.CalendarView.OnDateChangeListener;

public class CalendarFragment extends Fragment {
	/**
	 * The fragment argument representing the section number for this
	 * fragment.
	 */
	public static final String ARG_SECTION_NUMBER = "section_number";
	
	private String mdy;
	
	public interface OnTicketChangedListener {
		public void onTicketChangedListener();  //what to add in here?
	}

//	OnTicketChangedListener mListener;
	
	public CalendarFragment() {
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	}  
	  
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View fragView = inflater.inflate(R.layout.calendar_fragment_layout,container, false);
		
		CalendarView calendarView=(CalendarView) fragView.findViewById(R.id.calendarView1);
		View anchor = (View) fragView.findViewById(R.id.TextView_legend);
		
		final PopupMenu popupMenuMW = new PopupMenu(getActivity(), anchor);
		popupMenuMW.inflate(R.menu.ticket_menu);
		
		final PopupMenu popupMenuWknd = new PopupMenu(getActivity(), anchor);
		popupMenuWknd.inflate(R.menu.ticket_menu);
		
		//TODO: maybe add pre-Jan 2nd as 3rd popup menu?
		
		calendarView.setOnDateChangeListener(new OnDateChangeListener() {
	        @Override
	        public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
	        	Calendar calendar = Calendar.getInstance();
	        	calendar.set(year,month,dayOfMonth);
	        	
	        	String d = Integer.toString(dayOfMonth);
	        	String m = Integer.toString(month + 1);
	        	String y = Integer.toString(year);
	        	mdy = m + "/" + d + "/" + y;

				Toast.makeText(getActivity(),  mdy, Toast.LENGTH_SHORT).show();
	        	
	        	
	        	int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
	        	if (dayOfWeek == 1 || dayOfWeek == 7) {  // 1 = Sunday, 7 = Saturday            	
	        		popupMenuWknd.show();
	        	} else {
	        		popupMenuMW.show();
	        	}
	        		
	        }
	    });
		
		popupMenuMW.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {			
			String tMessage = "Midweek ticket added";
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				switch (item.getItemId()){
				case R.id.fourHr:
					MainActivity.fourHourMwSet.add(mdy);
					break;
				case R.id.eightHr:
					MainActivity.eightHourMwSet.add(mdy);
					break;
				case R.id.allday:
					MainActivity.allDayMwSet.add(mdy);
					break;
				case R.id.night:
					MainActivity.nightMwSet.add(mdy);
					break;
				case R.id.remove:
					MainActivity.fourHourMwSet.remove(mdy);
					MainActivity.eightHourMwSet.remove(mdy);
					MainActivity.allDayMwSet.remove(mdy);
					MainActivity.nightMwSet.remove(mdy);
					tMessage = "Midweek ticket removed";
				}
		//		mListener.onTicketChangedListener();
				
				Toast.makeText(getActivity(), tMessage, Toast.LENGTH_SHORT).show();
				
				
				
				Toast.makeText(getActivity(),  Integer.toString(MainActivity.fourHourMwSet.size()), Toast.LENGTH_SHORT).show();

				
				return true;
			}
		});
		
		popupMenuWknd.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {			
			String tMessage = "Weekend ticket added";
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				switch (item.getItemId()){
				case R.id.fourHr:
					MainActivity.fourHourWkndSet.add(mdy);
					break;
				case R.id.eightHr:
					MainActivity.eightHourWkndSet.add(mdy);
					break;
				case R.id.allday:
					MainActivity.allDayWkndSet.add(mdy);
					break;
				case R.id.night:
					MainActivity.nightWkndSet.add(mdy);
					break;
				case R.id.remove:
					MainActivity.fourHourWkndSet.remove(mdy);
					MainActivity.eightHourWkndSet.remove(mdy);
					MainActivity.allDayWkndSet.remove(mdy);
					MainActivity.nightWkndSet.remove(mdy);
					tMessage = "Weekend ticket removed";
				}
				
				
			//	mListener.onTicketChangedListener();
				
						
				Toast.makeText(getActivity(), tMessage, Toast.LENGTH_SHORT).show();
				
				Toast.makeText(getActivity(),  Integer.toString(MainActivity.fourHourMwSet.size()), Toast.LENGTH_SHORT).show();
				
				return true;
			}
		});
		
		return fragView;
	}
	

	
}
