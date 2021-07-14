package net.dreamparadise.eastExcel;

import com.alibaba.excel.EasyExcel;

import java.util.ArrayList;
import java.util.List;

public class TestWrite {
    public static void main(String[] args) {
        List<UserData> userDataList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            UserData data = new UserData();
            data.setUid(i);
            data.setUsername("tom" + i);
            userDataList.add(data);
        }
        String fileName = "C:\\Users\\Scott\\Downloads\\Documents\\excel\\01.xlsx";
        EasyExcel.write(fileName,UserData.class).sheet("用户信息").doWrite(userDataList);

    }
}
