package com.genesis.crypto.wallet.domain.coincap;

import java.util.List;

public class CoincapHistory {

    private List<CoincapHistoryItem> data;

    public List<CoincapHistoryItem> getData() {
        return data;
    }

    public void setData(List<CoincapHistoryItem> data) {
        this.data = data;
    }
}
