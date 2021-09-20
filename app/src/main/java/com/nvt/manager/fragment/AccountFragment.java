package com.nvt.manager.fragment;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.nvt.manager.ProfileActivity;
import com.nvt.manager.R;
import com.nvt.manager.adapter.NotificationReceiver;
import com.nvt.manager.model.DataBaseIntalizerUser;
import com.nvt.manager.model.UserDitures;
import com.nvt.manager.model.UserDituresDB;
import com.nvt.manager.viewmodel.AddUserDituresViewModel;

import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class AccountFragment extends Fragment {
    private RelativeLayout rlSettings;
    private Animation bottomAnimation;
    private Context context;
    private RelativeLayout rlAccount_profile;
    private TextView userName;
    private View view;
    private FragmentManager fragmentManager;
    private Switch btnSetNotification;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_account, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        iniViews();
    }

    private void iniViews() {
        bottomAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.bottom_animation);
        rlSettings = view.findViewById(R.id.rlSettings);
        fragmentManager = getChildFragmentManager();
        openNotification();

        userName = view.findViewById(R.id.username);
        rlAccount_profile = view.findViewById(R.id.rlAccount_profile);
        rlAccount_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddUserDituresViewModel viewModels = ViewModelProviders.of(AccountFragment.this).get(AddUserDituresViewModel.class);
                viewModels.getUserDitures().observe(getActivity(), new Observer<UserDitures>() {
                    @Override
                    public void onChanged(UserDitures userDitures) {
                        if (userDitures != null) {

                            Intent intent = new Intent(getContext(), ProfileActivity.class);
                            intent.putExtra("xxx",userDitures.getId());
                            Log.d("ssss", userDitures.getId()+"");
                            startActivity(intent);


                        }
                    }
                });

            }
        });

        DataBaseIntalizerUser.populateAsync(UserDituresDB.getUserDituresDB(getContext()));
        AddUserDituresViewModel viewModel = ViewModelProviders.of(this).get(AddUserDituresViewModel.class);
        // viewModel2.getIncome().observe(getActivity(), incomeAdapter::setIncomeDituresList);
        viewModel.getUserDitures().observe(getActivity(), new Observer<UserDitures>() {
            @Override
            public void onChanged(UserDitures userDitures) {
                if (userDitures != null) {

                    userName.setText(userDitures.getUsername());


                }
            }

        });

    }

    private void openNotification() {
        btnSetNotification = view.findViewById(R.id.btnSetNotification);
        btnSetNotification.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(Calendar.HOUR_OF_DAY, 8);
                    calendar.set(Calendar.MINUTE, 0);
                    calendar.set(Calendar.SECOND, 0);
                    Intent notifyIntent = new Intent(getContext(), NotificationReceiver.class);
                    notifyIntent.setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), 0, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                    AlarmManager alarmManager = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
                    alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 1000 * 60 * 60 * 24, pendingIntent);
                } else {

                }
            }
        });
    }


}