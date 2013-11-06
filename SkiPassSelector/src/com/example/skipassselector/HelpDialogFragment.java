package com.example.skipassselector;


import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class HelpDialogFragment extends DialogFragment {
				
		@Override
	  	public Dialog onCreateDialog(Bundle savedInstanceState) {
	        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
	        builder.setMessage(R.string.help_contents);
	        // Create the AlertDialog object and return it
	        return builder.create();
	    }
	  	

}
