package com.nvt.manager.model;

import android.os.AsyncTask;

import com.nvt.manager.R;

import java.util.ArrayList;
import java.util.List;

public class DatabaseIntalizer {
    public static void populateAsync(final ExpendituresDB expendituresDB) {
        new PopulateDbasync(expendituresDB).execute();
    }

    protected static class PopulateDbasync extends AsyncTask<Void, Void, Void> {
        private final ExpendituresDB expenditures;

        PopulateDbasync(ExpendituresDB expenditures) {
            this.expenditures = expenditures;
        }

        @Override
        protected Void doInBackground(final Void... voids) {
            if (expenditures.getExpendituresDao().rowCount() == 0) {
                List<Expenditures> expendituress = new ArrayList<>();
                expendituress.add(new Expenditures("1000", "xxx", R.drawable.bills, "bill", 12, 3, 2012));
                expendituress.add(new Expenditures("1000", "xxx", R.drawable.bills, "bill", 12, 3, 2012));
                expendituress.add(new Expenditures("1000", "xxx", R.drawable.bills, "bill", 12, 3, 2012));
                expendituress.add(new Expenditures("1000", "xxx", R.drawable.bills, "bill", 12, 3, 2012));
                expendituress.add(new Expenditures("1000", "xxx", R.drawable.bills, "bill", 12, 3, 2012));
                expendituress.add(new Expenditures("1000", "xxx", R.drawable.bills, "bill", 12, 3, 2012));
                expendituress.add(new Expenditures("1000", "xxx", R.drawable.bills, "bill", 12, 3, 2012));
            }
            return null;
        }
    }
}
