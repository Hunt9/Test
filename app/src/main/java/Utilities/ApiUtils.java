package Utilities;


public class ApiUtils {

    public static String BASE_URL = "http://www.circlesltd.com/fyp_ammar/";

    public static SOService getSOService() {

        return RetrofitClient.getClient(BASE_URL).create(SOService.class);
    }
}