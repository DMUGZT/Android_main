package com.example.test;

/**
 * 全局常量
 * @author zaki
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AppConstants {
    private static List<String> accountTypes = new ArrayList<>(Arrays.asList(
            "现金", "微信钱包", "支付宝", "银行卡", "基金"
    ));

    public static List<String> getAccountTypes() {
        return accountTypes;
    }

    public static void addAccountType(String type) {
        if (!accountTypes.contains(type)) {
            accountTypes.add(type);
        }
    }

    public static void removeAccountType(String type) {
        accountTypes.remove(type);
    }

    public static void updateAccountTypes(List<String> types) {
        accountTypes.clear();
        accountTypes.addAll(types);
    }
}
