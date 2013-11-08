package com.example.skipassselector;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.Toast;

public class ResetDialog extends DialogFragment {
	
		OnResetSelectedListener mCallback;
		
		public interface OnResetSelectedListener {
			public void onPositiveClickListener();
		}
		
	  	@Override
	  	public Dialog onCreateDialog(Bundle savedInstanceState) {
	        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
	        builder.setTitle(R.string.reset_title);
	        builder.setMessage(R.string.reset_message)
	               .setPositiveButton(R.string.reset_button, new DialogInterface.OnClickListener() {
	                   public void onClick(DialogInterface dialog, int id) {
	                       mCallback.onPositiveClickListener();
	                       Toast.makeText(getActivity(), "All dates cleared", Toast.LENGTH_SHORT).show();
	                   }
	               })
	               .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
	                   public void onClick(DialogInterface dialog, int id) {
	                       // User cancelled the dialog
	                   }
	               });
	        // Create the AlertDialog object and return it
	        return builder.create();
	    }
	  	
	  	@Override
	  	public void onAttach (Activity activity){
	  		super.onAttach(activity);
	  		try {
	  			mCallback = (OnResetSelectedListener) activity;
	  		} catch (ClassCastException e) {
	  			throw new ClassCastException(activity.toString() + "must implement OnResetSelectedListener");
	  		}
	  	}
	  
}
