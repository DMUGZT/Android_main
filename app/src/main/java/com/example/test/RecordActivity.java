package com.example.test;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import com.example.test.database.IncomeDAO;
import com.example.test.utils.UserSessionManager;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
        Button btnDate = view.findViewById(R.id.btn_date);

        Button btnFood = view.findViewById(R.id.btn_food);
        Button btnTransport = view.findViewById(R.id.btn_transport);
        Button btnClothing = view.findViewById(R.id.btn_clothing);
        Button btnShopping = view.findViewById(R.id.btn_shopping);
        // Add more buttons for other categories

        btnDate.setText(new SimpleDateFormat("yyyy-M-dd").format(new Date()));

        // Setup Date Spinner
        List<String> dates = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//                                inputNumber.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                                btnDate.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                            }

                        }, year, month, day);
                datePickerDialog.show();
            }
        });

        btnExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedCategory = "支出";
            }
        });

        btnIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedCategory = "入账";
            }
        });

        btnIgnore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedCategory = "不计入收支";
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
                if (number == 0) {
                    Toast.makeText(getActivity(), "请输入有效的金额", Toast.LENGTH_SHORT).show();
                    return;
                }
                String dateStr = btnDate.getText().toString();
                if (dateStr.equals("选择日期")){
                    Calendar calendar = Calendar.getInstance();
                    int currentYear = calendar.get(Calendar.YEAR);
                    int currentMonth = calendar.get(Calendar.MONTH)+1;
                    int currentDay = calendar.get(Calendar.DAY_OF_MONTH);
                    dateStr = currentYear+"-"+currentMonth+"-"+currentDay;
                }
                String message = "分类: " + selectedCategory + "\n类型: " + selectedType + "\n金额: " + number + "\n日期: " + dateStr;
                String type = selectedType;
                int amount = (int) number; // Convert to int for database
                String description = selectedCategory;
                int cashType = 1;

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage(message)
                        .setTitle("提示")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // Do nothing
                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();

                String userId = sessionManager.getUserId();
                long newRowId = incomeDAO.addIncome(type, dateStr, amount, description, cashType, Integer.parseInt(userId));
                if (newRowId != -1) {
//                    Toast.makeText(getActivity(), "数据插入成功", Toast.LENGTH_SHORT).show();
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    DetailsFragment df = (DetailsFragment) fragmentManager.findFragmentByTag("details_fragment_tag");
                    BillsFragment bf = (BillsFragment) fragmentManager.findFragmentByTag("bills_fragment_tag");
                    if (df != null) {
                        df.refreshData();
                        bf.refreshData();
                    }
                } else {
//                    Toast.makeText(getActivity(), "数据插入失败", Toast.LENGTH_SHORT).show();
                }

                dismiss();
            }
        });

        return view;
    }

    private void resetTypeButtonsBackground() {
        // Reset background for type buttons if necessary
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