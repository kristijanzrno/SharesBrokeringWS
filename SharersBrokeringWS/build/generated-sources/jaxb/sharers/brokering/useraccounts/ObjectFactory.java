//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.5-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.01.19 at 08:38:23 PM GMT 
//


package sharers.brokering.useraccounts;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the sharers.brokering.useraccounts package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: sharers.brokering.useraccounts
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link AccountList }
     * 
     */
    public AccountList createAccountList() {
        return new AccountList();
    }

    /**
     * Create an instance of {@link Account }
     * 
     */
    public Account createAccount() {
        return new Account();
    }

    /**
     * Create an instance of {@link BoughtStock }
     * 
     */
    public BoughtStock createBoughtStock() {
        return new BoughtStock();
    }

    /**
     * Create an instance of {@link BoughtStocks }
     * 
     */
    public BoughtStocks createBoughtStocks() {
        return new BoughtStocks();
    }

}
