package com.example.choiceweather;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.example.db.CityDatabase;

public class AddCityDialogFragment extends DialogFragment {
	public interface OnDialogClickListener {
		void onDialogPositiveClick();
	}

	private OnDialogClickListener listener = null;

	public AddCityDialogFragment(Context context, OnDialogClickListener listener) {
		this.listener = listener;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstance) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		// Get the layout inflater
		LayoutInflater inflater = getActivity().getLayoutInflater();
		final CityDatabase db = new CityDatabase(getActivity());
		final View v = inflater.inflate(R.layout.dialog_new_city, null);
		final EditText input = (EditText) v.findViewById(R.id.city_name_edit);

		// Inflate and set the layout for the dialog
		// Pass null as the parent view because its going in the dialog layout
		builder.setView(v)
				// Add action buttons
				.setPositiveButton(R.string.add_city_confirm,
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int id) {
								String cityName = input.getText().toString();
								db.addCity("" + cityName);
								listener.onDialogPositiveClick();
							}
						})
				.setNegativeButton(R.string.add_city_cancel,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {

							}
						});
		return builder.create();
	}

}