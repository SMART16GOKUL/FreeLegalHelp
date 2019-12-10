package com.example.freelegalhelp.data;

public class URLs {

    private static final String ROOT_URL = "http://m8itsolutions.com/legalhelp/api/userapi.php?apicall=";
    public static final String ROOT_URL1 = "http://m8itsolutions.com/legalhelp/api/adovocateapi.php?apicall=";



    public static final String URL_SIGNUP = ROOT_URL + "signup";
    public static final String URL_LOGIN= ROOT_URL + "login";
    public static final String URL_NEWCASE= ROOT_URL1 + "insertcomplaint";
    public static final String URL_VIEWCASE= ROOT_URL1 + "viewcomplaint";
    public static final String URL_VIEWPROFILE= ROOT_URL1 + "viewprofile";
    public static final String URL_PROFILEUPDATE= ROOT_URL1 + "updateusers";
    public static final String URL_VIEWCOMPLITEAM= ROOT_URL1 + "singlecomplaint";
    public static final String URL_UPDATECOMPLITEAM= ROOT_URL1 + "updateComplaint";


}