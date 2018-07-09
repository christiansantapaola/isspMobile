package com.issp.mobileprogrammingapp.network.REST;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.issp.mobileprogrammingapp.entity.PageMetaData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MetaDataPageRequest extends Request<PageMetaData> {
    private Response.Listener<PageMetaData> listener;
    public MetaDataPageRequest(String url,
                               Response.Listener<PageMetaData> listener,
                               Response.ErrorListener errorListener) {
        super(Method.GET, url, errorListener);
        this.listener = listener;
    }

    @Override
    protected Response<PageMetaData> parseNetworkResponse(NetworkResponse response) {
        try {
            String json = new String(response.data);
            String header = new String(HttpHeaderParser.parseCharset(response.headers));
            JSONArray array = new JSONArray(json);
            JSONObject jsonObject = array.getJSONObject(0);
            PageMetaData metaData = new PageMetaData();
            metaData.setPage(jsonObject.getInt("page"));
            metaData.setPages(jsonObject.getInt("pages"));
            metaData.setPer_page(jsonObject.getInt("per_page"));
            metaData.setTotal(jsonObject.getInt("total"));
            return Response.success(metaData, HttpHeaderParser.parseCacheHeaders(response));
        } catch (JSONException e) {
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected void deliverResponse(PageMetaData response) {
        listener.onResponse(response);
    }
}
