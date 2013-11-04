package com.example.skipassselector;

import java.util.Calendar;



import android.app.Activity;
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
	String esf = null;
	
	public interface OnTicketChangedListener {
		public void onTicketChangedListener();  
	}

	OnTicketChangedListener mListener;
	
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
		
		calendarView.setOnDateChangeListener(new OnDateChangeListener() {
	        @Override
	        public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
	        	Calendar calendar = Calendar.getInstance();
	        	calendar.set(year,month,dayOfMonth);
	        	
	        	String d = Integer.toString(dayOfMonth);
	        	String m = Integer.toString(month + 1);
	        	String y = Integer.toString(year);
	        	mdy = m + "/" + d + "/" + y;
	        	
	        	if (month+1 > 9 || (month+1==1 && dayOfMonth < 3)){
	        		esf = "_ESF";  
	        	}
	        	
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
					MainActivity.datesAndTickets.put(mdy, "FOUR_HOUR_MW");
					break;
				case R.id.eightHr:
					MainActivity.datesAndTickets.put(mdy, "EIGHT_HOUR_MW");
					break;
				case R.id.allday:
					MainActivity.datesAndTickets.put(mdy, "ALL_DAY_MW");
					break;
				case R.id.night:
					MainActivity.datesAndTickets.put(mdy, "NIGHT_MW" + esf);
					break;
				case R.id.remove:
					MainActivity.datesAndTickets.remove(mdy);
					tMessage = "Midweek ticket removed";
				}
		
				mListener.onTicketChangedListener();
				
				Toast.makeText(getActivity(), tMessage, Toast.LENGTH_SHORT).show();
				
				
				//Toast.makeText(getActivity(),  Integer.toString(MainActivity.datesAndTickets.size()), Toast.LENGTH_SHORT).show();
				Toast.makeText(getActivity(),  Integer.toString(MainActivity.getWindowRateTotal()), Toast.LENGTH_SHORT).show();
				
				
				return true;
			}
		});
		
		popupMenuWknd.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {			
			String tMessage = "Weekend ticket added";
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				switch (item.getItemId()){
				case R.id.fourHr:
					MainActivity.datesAndTickets.put(mdy, "FOUR_HOUR_WKND");
					break;
				case R.id.eightHr:
					MainActivity.datesAndTickets.put(mdy, "EIGHT_HOUR_WKND");
					break;
				case R.id.allday:
					MainActivity.datesAndTickets.put(mdy, "ALL_DAY_WKND");
					break;
				case R.id.night:					
					MainActivity.datesAndTickets.put(mdy, "NIGHT_WKND" + esf);
					break;
				case R.id.remove:
					MainActivity.datesAndTickets.remove(mdy);
					tMessage = "Weekend ticket removed";
				}
				
				
				mListener.onTicketChangedListener();
				
						
				Toast.makeText(getActivity(), tMessage, Toast.LENGTH_SHORT).show();
				
			//	Toast.makeText(getActivity(),  Integer.toString(MainActivity.datesAndTickets.size()), Toast.LENGTH_SHORT).show();
				Toast.makeText(getActivity(),  Integer.toString(MainActivity.getWindowRateTotal()), Toast.LENGTH_SHORT).show();
				
				return true;
			}
		});
		
		return fragView;
	}
	
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			mListener = (OnTicketChangedListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString() +" must implement OnTicketChangedListener");
		}
	}
	
	@Override
	public void onPause() {
		super.onPause();
		System.out.println("CalendarFragment paused");
	}
	
	@Override
	public void onResume() {
		super.onResume();
		System.out.println("CalendarFragment resumed");
	}
	
}
