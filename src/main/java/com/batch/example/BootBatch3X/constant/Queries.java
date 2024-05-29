package com.batch.example.BootBatch3X.constant;

public class Queries {

    public final static String customer_insert = "INSERT INTO  CUSTOMERS (ID,FIRST_NAME,LAST_NAME," +
            "EMAIL,GENDER,CONTACT_NO,COUNTRY,DOB"+
            ") VALUES(:id,:firstName,:lastName,:email,:gender,:contactNo,:country,:dob)";
}
