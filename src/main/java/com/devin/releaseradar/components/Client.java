package com.devin.releaseradar.components;

public class Client {
    
    String clientId;
    String clientCredentials;


    public Client(String clientId, String clientCredentials) {
        this.clientId = clientId;
        this.clientCredentials = clientCredentials;
    }

    public String toString() {
        String textBlock = "grant_type=client_credentials&client_id=3e078e19a7584e069eacd7cd4a6e4fac&client_secret=b922b2a704e64e65a87438e01ba3b46a";
        return textBlock.formatted(clientCredentials, clientId);
    }
}
