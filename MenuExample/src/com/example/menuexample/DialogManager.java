package com.example.menuexample;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class DialogManager {

	public void showDialog(Context context, String title, String message, Boolean status){
		
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        
        alertDialog.setTitle(title);
 
        alertDialog.setMessage(message);
 
        if(status != null)
            alertDialog.setIcon((status) ? R.drawable.bus2 : R.drawable.marker_default);
 
        
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });
 
        alertDialog.show();
	}
	
}
