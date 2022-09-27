package com.amir.chuck.internal.ui;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.amir.chuck.R;
import com.amir.chuck.internal.data.HttpTransaction;

public class TransactionOverviewFragment extends Fragment implements TransactionFragment {

    TextView url;
    TextView method;
    TextView protocol;
    TextView status;
    TextView response;
    TextView ssl;
    TextView requestTime;
    TextView responseTime;
    TextView duration;
    TextView requestSize;
    TextView responseSize;
    TextView totalSize;

    private HttpTransaction transaction;

    public TransactionOverviewFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.chuck_fragment_transaction_overview, container, false);
        url = (TextView) view.findViewById(R.id.url);
        method = (TextView) view.findViewById(R.id.method);
        protocol = (TextView) view.findViewById(R.id.protocol);
        status = (TextView) view.findViewById(R.id.status);
        response = (TextView) view.findViewById(R.id.response);
        ssl = (TextView) view.findViewById(R.id.ssl);
        requestTime = (TextView) view.findViewById(R.id.request_time);
        responseTime = (TextView) view.findViewById(R.id.response_time);
        duration = (TextView) view.findViewById(R.id.duration);
        requestSize = (TextView) view.findViewById(R.id.request_size);
        responseSize = (TextView) view.findViewById(R.id.response_size);
        totalSize = (TextView) view.findViewById(R.id.total_size);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        populateUI();
    }

    @Override
    public void transactionUpdated(HttpTransaction transaction) {
        this.transaction = transaction;
        populateUI();
    }

    private void populateUI() {
        if (isAdded() && transaction != null) {
            url.setText(transaction.getUrl());
            method.setText(transaction.getMethod());
            protocol.setText(transaction.getProtocol());
            status.setText(transaction.getStatus().toString());
            response.setText(transaction.getResponseSummaryText());
            ssl.setText((transaction.isSsl() ? R.string.chuck_yes : R.string.chuck_no));
            requestTime.setText(transaction.getRequestDateString());
            responseTime.setText(transaction.getResponseDateString());
            duration.setText(transaction.getDurationString());
            requestSize.setText(transaction.getRequestSizeString());
            responseSize.setText(transaction.getResponseSizeString());
            totalSize.setText(transaction.getTotalSizeString());
        }
    }
}
