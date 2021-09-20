package com.nvt.manager.fragment;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.nvt.manager.R;
import com.nvt.manager.adapter.ExpenseAdapter;
import com.nvt.manager.adapter.IncomeAdapter;
import com.nvt.manager.model.DataBaseIntalizerIncome;
import com.nvt.manager.model.DatabaseIntalizer;
import com.nvt.manager.model.Expenditures;
import com.nvt.manager.model.ExpendituresDB;
import com.nvt.manager.model.IncomeDitures;
import com.nvt.manager.model.IncomeDituresDB;
import com.nvt.manager.viewmodel.AddExpendituresViewModel;
import com.nvt.manager.viewmodel.AddIncomeDituresViewModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener;

public class HomeDayFragment extends Fragment {
    private TextView txtCostExpense;
    private TextView txtCostIncome;
    private TextView totalCost;
    private TextView txt_cost_expense;
    private TextView txt_cost_income;

    private HorizontalCalendar horizontalCalendar;
    private RecyclerView rcExpend;
    private RecyclerView rcIncome;
    private List<Expenditures> expenditures = new ArrayList<>();
    private List<IncomeDitures> incomeDitures = new ArrayList<>();
    ExpenseAdapter expenseAdapter = new ExpenseAdapter(expenditures);


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_home_day, container, false);

/*
        txtCostExpense = rootView.findViewById(R.id.txtCostExpense);
        txtCostIncome = rootView.findViewById(R.id.txtCostIncoming);
*/

        DatabaseIntalizer.populateAsync(ExpendituresDB.getExpendituresDB(getContext()));
        DataBaseIntalizerIncome.populateAsync(IncomeDituresDB.getIncomeDituresBD(getContext()));

        /* start before 1 month from now */
        Calendar startDate = Calendar.getInstance();
        startDate.add(Calendar.MONTH, -2);

        /* end after 1 month from now */
        Calendar endDate = Calendar.getInstance();
        endDate.add(Calendar.MONTH, 2);

        horizontalCalendar = new HorizontalCalendar.Builder(rootView, R.id.calendarView)
                .range(startDate, endDate)
                .datesNumberOnScreen(5)
                .configure()
                .formatTopText("MMM")
                .formatMiddleText("dd")
                .formatBottomText("EEE")
                .textSize(14f, 24f, 14f)
                .showTopText(true)
                .showBottomText(true)
                .textColor(Color.LTGRAY, Color.WHITE)
                .end()
                .build();

        horizontalCalendar.setCalendarListener(new HorizontalCalendarListener() {
            @Override
            public void onDateSelected(Calendar date, int position) {
                Toast.makeText(getContext(), DateFormat.format("EEE, MMM d, yyyy", date) + " is selected!33333333", Toast.LENGTH_SHORT).show();
                int day = Integer.valueOf(DateFormat.format("d", date).toString().trim());
                rcExpend = (RecyclerView) rootView.findViewById(R.id.recyclerViewDay);
                rcExpend.setLayoutManager(new LinearLayoutManager(getActivity()));
                rcExpend.setAdapter(expenseAdapter);

                AddExpendituresViewModel viewModel = ViewModelProviders.of(HomeDayFragment.this).get(AddExpendituresViewModel.class);
                viewModel.getExpanddituresDay(day).observe(getActivity(), expenseAdapter::setExpendituresList);

                rcIncome = rootView.findViewById(R.id.recyclerViewDay_1);

                IncomeAdapter incomeAdapter = new IncomeAdapter(incomeDitures);
                rcIncome.setLayoutManager(new LinearLayoutManager(getActivity()));
                rcIncome.setAdapter(incomeAdapter);


                AddIncomeDituresViewModel viewModel2 = ViewModelProviders.of(HomeDayFragment.this).get(AddIncomeDituresViewModel.class);
                viewModel2.getIncomeDituresDay(day).observe(getActivity(), incomeAdapter::setIncomeDituresList);
               /* viewModel.getExpanddituresDay(day).observe(getActivity(), new Observer<List<Expenditures>>() {
                    @Override
                    public void onChanged(List<Expenditures> expendituresList) {
                        if (expendituresList != null) {
                            for (int i = 0; i < expendituresList.size(); i++) {
                                Expenditures expenditures = expendituresList.get(i);
                                txtCostExpense.setText(expenditures.getMoney());
                            }
                        }
                    }

                });*/

            }


        });

        return rootView;
    }

}