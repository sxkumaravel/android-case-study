package com.target.dealbrowserpoc.dealbrowser.data;


import android.util.Log;

import com.google.gson.Gson;

import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Component to fetch available Deals list data.
 *
 * @author kumars
 */
public class ApiDataSource {

    private RestTemplate mRestTemplate;

    public ApiDataSource() {
        mRestTemplate = new RestTemplate();
        mRestTemplate.getMessageConverters().add(new StringHttpMessageConverter(Charset.forName("UTF-8")));
        mRestTemplate.setInterceptors(new ArrayList<ClientHttpRequestInterceptor>());
    }

    /**
     * Loads and returns the deal list.
     *
     * @return list of {@link DealItem}.
     */
    public List<DealItem> getDeals() {
        try {
            String response = mRestTemplate.getForObject("http://target-deals.herokuapp.com/api/deals", String.class);
            DealsDTO dealsDTO = new Gson().fromJson(response, DealsDTO.class);
            return dealsDTO.getData();
        } catch (Exception e) {
            Log.e("ApiDataSource", e.getMessage());
        }

        return new ArrayList<>();
    }
}
