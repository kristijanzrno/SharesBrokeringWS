/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DOCwebServices;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import saved.currency.rates.Rates;

/**
 *
 * @author kristijanzrno
 */
public class CurrencyRatesClient {

    private WebTarget webTarget;
    private Client client;
    private static final String SERVICE_URL = "http://localhost:8080/CurrencyRatesRetainer/webresources";

    public CurrencyRatesClient() {
        client = ClientBuilder.newClient();
        webTarget = client.target(SERVICE_URL);
    }

    public Rates getRates() {
        return webTarget.path("CurrencyRates").path("getRates").request().get(Rates.class);
    }

}
