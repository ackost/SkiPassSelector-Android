package com.example.skipassselector;

import java.util.Calendar;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.CalendarView.OnDateChangeListener;
import android.widget.Spinner;
import android.widget.Toast;


public class MainActivity extends Activity {
	Calendar calendar = Calendar.getInstance();
	//Spinner spinner = (Spinner) findViewById(R.id.tickets_spinner);
	//ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, R.array.ticket_names);              
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
		//spinner.setAdapter(adapter);
		
		CalendarView calendarView=(CalendarView) findViewById(R.id.calendarView1);
        calendarView.setOnDateChangeListener(new OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
            	
            	            	
                 Toast.makeText(getApplicationContext(), "Ticket added", Toast.LENGTH_SHORT).show();
            	}
        	});
	}
	
		
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	
}
