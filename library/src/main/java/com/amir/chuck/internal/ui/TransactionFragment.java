package com.amir.chuck.internal.ui;

import com.amir.chuck.internal.data.HttpTransaction;

interface TransactionFragment {
    void transactionUpdated(HttpTransaction transaction);
}