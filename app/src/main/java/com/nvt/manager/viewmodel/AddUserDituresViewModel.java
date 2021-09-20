package com.nvt.manager.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.nvt.manager.model.UserDitures;
import com.nvt.manager.model.UserDituresDB;



public class AddUserDituresViewModel extends AndroidViewModel {

    private UserDituresDB userDituresDB;
    private final LiveData<UserDitures> users;

    public AddUserDituresViewModel(Application application) {
        super(application);
        userDituresDB = UserDituresDB.getUserDituresDB(application);
        users = UserDituresDB.getUserDituresDB(application).getUserDituresDao().getAll();
    }

    public void addUserDituresDB(UserDitures userDitures){
        userDituresDB.getUserDituresDao().insert(userDitures);
    }

    public void upDate(UserDitures userDitures){
        userDituresDB.getUserDituresDao().update(userDitures);
    }

    public LiveData<UserDitures> getUserDitures(){
        return users;
    }


}
