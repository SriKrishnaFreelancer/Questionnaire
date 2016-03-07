package com.nunna.questionnaire.services;


import com.squareup.okhttp.OkHttpClient;

import java.util.concurrent.TimeUnit;

import retrofit.RestAdapter;
import retrofit.client.OkClient;

/**
 * Created by Sri Krishna on 02-02-2016.
 */
public class RestClient {
    private static WebAPI webAPI;
    private static final String BASE_URL = " http://54.179.128.29:8080";

    public static final int CONNECTION_TIME_OUT = 15000;
    public static final int READ_TIME_OUT = 15000;
    public static final int WRITE_TIME_OUT = 15000;

    static {
        setupRestClient();
    }

    //
    private RestClient() {
    }

    /**
     * Method to create and return web api
     *
     * @return
     */
    public static WebAPI getAPI() {
        return webAPI;
    }

    /*
    creating the rest adapter with required configuration
     */
    private static void setupRestClient() {
        //OkHttpClient configuration
        // OkHttpClient okHttpClient= App.getInstance().getMyHttpClient();
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setConnectTimeout(CONNECTION_TIME_OUT, TimeUnit.MILLISECONDS);
        okHttpClient.setWriteTimeout(WRITE_TIME_OUT, TimeUnit.MILLISECONDS);
        okHttpClient.setReadTimeout(READ_TIME_OUT, TimeUnit.MILLISECONDS);

        RestAdapter.Builder builder = new RestAdapter.Builder();
//        builder.setConverter(new DynamicJsonConverter());
        builder.setEndpoint(BASE_URL);
        builder.setLogLevel(RestAdapter.LogLevel.FULL);
        builder.setClient(new OkClient(okHttpClient));

        RestAdapter restAdapter = builder.build();
        webAPI = restAdapter.create(WebAPI.class);
    }

//    static class DynamicJsonConverter implements Converter {
//
//        @Override public Object fromBody(TypedInput typedInput, Type type) throws ConversionException {
//            try {
//                InputStream in = typedInput.in(); // convert the typedInput to String
//                String jsonString = fromStream(in);
//                in.close(); // we are responsible to close the InputStream after use
//
//
//                Map<String, Object> data = new Gson().fromJson(jsonString, type);
//
//                for (Iterator<Map.Entry<String, Object>> it = data.entrySet().iterator(); it.hasNext();) {
//                    Map.Entry<String, Object> entry = it.next();
//                    if (entry.getValue() == null) {
//                        it.remove();
//                    } else if (entry.getValue().getClass().equals(ArrayList.class)) {
//                        if (((ArrayList<?>) entry.getValue()).size() == 0) {
//                            it.remove();
//                        }
//                    }
//                }
//                return new GsonBuilder().setPrettyPrinting().create().toJson(data);
////                if (String.class.equals(type)) {
////                    return string;
////                } else {
////                    return new Gson().fromJson(string, type); // convert to the supplied type, typically Object, JsonObject or Map<String, Object>
////                }
//            } catch (Exception e) { // a lot may happen here, whatever happens
//                throw new ConversionException(e); // wrap it into ConversionException so retrofit can process it
//            }
//        }
//
//        @Override public TypedOutput toBody(Object object) { // not required
//            return null;
//        }
//
//        private static String fromStream(InputStream in) throws IOException {
//            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
//            StringBuilder out = new StringBuilder();
//            String line;
//            while ((line = reader.readLine()) != null) {
//                out.append(line);
//                out.append("\r\n");
//            }
//            return out.toString();
//        }
//    }
}
