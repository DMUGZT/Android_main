package com.example.test;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.example.test.database.DatabaseHelper;
import com.example.test.database.IncomeDAO;
import com.example.test.utils.UserSessionManager;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class RecordActivity extends BottomSheetDialogFragment {

    private EditText inputNumber;
    private boolean isDotUsed = false;
    private String selectedCategory = "支出"; // Default category
    private Calendar selectedDate = Calendar.getInstance();
    private String selectedType = "餐饮"; // Default type
    private IncomeDAO incomeDAO;
    private UserSessionManager sessionManager;
    @Override
    public void onStart() {
        super.onStart();
        BottomSheetDialog dialog = (BottomSheetDialog) getDialog();
        if (dialog != null) {
            ViewGroup bottomSheet = dialog.findViewById(com.google.android.material.R.id.design_bottom_sheet);
            BottomSheetBehavior<ViewGroup> behavior = BottomSheetBehavior.from(bottomSheet);
            bottomSheet.getLayoutParams().height = (int) (getResources().getDisplayMetrics().heightPixels * 0.7);
            behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        }
        incomeDAO = new IncomeDAO(getContext());
        sessionManager = new UserSessionManager(getContext());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_record, container, false);


        inputNumber = view.findViewById(R.id.input_number);

        Button btn1 = view.findViewById(R.id.btn_1);
        Button btn2 = view.findViewById(R.id.btn_2);
        Button btn3 = view.findViewById(R.id.btn_3);
        Button btn4 = view.findViewById(R.id.btn_4);
        Button btn5 = view.findViewById(R.id.btn_5);
        Button btn6 = view.findViewById(R.id.btn_6);
        Button btn7 = view.findViewById(R.id.btn_7);
        Button btn8 = view.findViewById(R.id.btn_8);
        Button btn9 = view.findViewById(R.id.btn_9);
        Button btn0 = view.findViewById(R.id.btn_0);
        Button btnDot = view.findViewById(R.id.btn_dot);
        Button btnDelete = view.findViewById(R.id.btn_delete);
        Button btnConfirm = view.findViewById(R.id.btn_confirm);

        Button btnExpense = view.findViewById(R.id.btn_expense);
        Button btnIncome = view.findViewById(R.id.btn_income);
        Button btnIgnore = view.findViewById(R.id.btn_ignore);
        Spinner spinnerDate = view.findViewById(R.id.spinner_date);

        Button btnFood = view.findViewById(R.id.btn_food);
        Button btnTransport = view.findViewById(R.id.btn_transport);
        Button btnClothing = view.findViewById(R.id.btn_clothing);
        Button btnShopping = view.findViewById(R.id.btn_shopping);
        // Add more buttons for other categories

        // Setup Date Spinner
        List<String> dates = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        for (int i = 0; i < 30; i++) {
            dates.add((calendar.get(Calendar.MONTH) + 1) + "月" + calendar.get(Calendar.DAY_OF_MONTH) + "日");
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        ArrayAdapter<String> dateAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, dates);
        dateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDate.setAdapter(dateAdapter);
        spinnerDate.setSelection(0);

        spinnerDate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                calendar.set(Calendar.DAY_OF_MONTH, position + 1);
                selectedDate = calendar;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

        btnExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedCategory = "支出";
//                btnExpense.setBackgroundResource(R.drawable.button_selected_background);
//                btnIncome.setBackgroundResource(R.drawable.button_background);
//                btnIgnore.setBackgroundResource(R.drawable.button_background);
            }
        });

        btnIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedCategory = "入账";
//                btnExpense.setBackgroundResource(R.drawable.button_background);
//                btnIncome.setBackgroundResource(R.drawable.button_selected_background);
//                btnIgnore.setBackgroundResource(R.drawable.button_background);
            }
        });

        btnIgnore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedCategory = "不计入收支";
//                btnExpense.setBackgroundResource(R.drawable.button_background);
//                btnIncome.setBackgroundResource(R.drawable.button_background);
//                btnIgnore.setBackgroundResource(R.drawable.button_selected_background);
            }
        });

        View.OnClickListener numberClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button button = (Button) v;
                inputNumber.append(button.getText().toString());
            }
        };

        btn1.setOnClickListener(numberClickListener);
        btn2.setOnClickListener(numberClickListener);
        btn3.setOnClickListener(numberClickListener);
        btn4.setOnClickListener(numberClickListener);
        btn5.setOnClickListener(numberClickListener);
        btn6.setOnClickListener(numberClickListener);
        btn7.setOnClickListener(numberClickListener);
        btn8.setOnClickListener(numberClickListener);
        btn9.setOnClickListener(numberClickListener);
        btn0.setOnClickListener(numberClickListener);

        btnDot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isDotUsed) {
                    inputNumber.append(".");
                    isDotUsed = true;
                }
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String currentText = inputNumber.getText().toString();
                if (!currentText.isEmpty()) {
                    if (currentText.endsWith(".")) {
                        isDotUsed = false;
                    }
                    inputNumber.setText(currentText.substring(0, currentText.length() - 1));
                }
            }
        });

        // Type buttons listeners
        View.OnClickListener typeClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTypeButtonsBackground();
                Button selectedButton = (Button) v;
//                selectedButton.setBackgroundResource(R.drawable.icon_selected_background);
                selectedType = selectedButton.getText().toString();
            }
        };

        btnFood.setOnClickListener(typeClickListener);
        btnTransport.setOnClickListener(typeClickListener);
        btnClothing.setOnClickListener(typeClickListener);
        btnShopping.setOnClickListener(typeClickListener);
        // Set onClickListener for other type buttons similarly

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double number = getInputNumber();
                String message = "分类: " + selectedCategory + "\n类型: " + selectedType + "\n金额: " + number + "\n日期: " + dates.get(spinnerDate.getSelectedItemPosition());
//                String category = selectedCategory;
                String type = selectedType;
                int amount = (int) number;//todo 数据库类型转换，插入数据库之前都是浮点
                String date = dates.get(spinnerDate.getSelectedItemPosition());
                String description = selectedCategory;
                int cashType = 1;
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage(message)
                        .setTitle("提示")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();
                String userId = sessionManager.getUserId();
                // 插入数据库
                long newRowId = incomeDAO.addIncome(type,date,amount,description,cashType,Integer.parseInt(userId));
                if (newRowId != -1) {
                    // 插入成功
                    Toast.makeText(getActivity(), "数据插入成功", Toast.LENGTH_SHORT).show();

                    // 获取 FragmentManager
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

                    // 查找 Fragment 实例
                    DetailsFragment df = (DetailsFragment) fragmentManager.findFragmentByTag("details_fragment_tag");

                    if (df != null) {
                        // 调用刷新数据的方法
                        df.refreshData();
                    }
                } else {
                    // 插入失败
                    Toast.makeText(getActivity(), "数据插入失败", Toast.LENGTH_SHORT).show();
                }

                dismiss();
            }
        });

        return view;
    }

    private void resetTypeButtonsBackground() {
//        getView().findViewById(R.id.btn_food).setBackgroundResource(R.drawable.icon_food);
//        getView().findViewById(R.id.btn_transport).setBackgroundResource(R.drawable.icon_transport);
//        getView().findViewById(R.id.btn_clothing).setBackgroundResource(R.drawable.icon_clothing);
//        getView().findViewById(R.id.btn_shopping).setBackgroundResource(R.drawable.icon_shopping);

    }

    public double getInputNumber() {
        String inputText = inputNumber.getText().toString();
        try {
            return Double.parseDouble(inputText);
        } catch (NumberFormatException e) {
            return 0; // or handle the error as needed
        }
    }

    public String getSelectedCategory() {
        return selectedCategory;
    }

    public Calendar getSelectedDate() {
        return selectedDate;
    }

    public String getSelectedType() {
        return selectedType;
    }
}