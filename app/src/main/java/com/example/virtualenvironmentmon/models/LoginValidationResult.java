package com.example.virtualenvironmentmon.models;

import it.corsinvest.proxmoxve.api.PveClient;

public class LoginValidationResult {
    public int loginCode;
    public PveClient client;

    public LoginValidationResult(int loginCode, PveClient client) {
        this.loginCode = loginCode;

        this.client = new PveClient("dummy_IP", 8006);

    }

    public LoginValidationResult() {
    }
}
