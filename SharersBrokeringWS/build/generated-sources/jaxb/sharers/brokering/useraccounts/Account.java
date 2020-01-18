//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.5-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.01.18 at 07:53:54 AM GMT 
//


package sharers.brokering.useraccounts;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for account complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="account">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="account_name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="account_password" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="account_level" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="account_bought_stocks" type="{com.shares.brokering.accounts}bought_stocks"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "account", propOrder = {
    "accountName",
    "accountPassword",
    "accountLevel",
    "accountBoughtStocks"
})
public class Account {

    @XmlElement(name = "account_name", required = true)
    protected String accountName;
    @XmlElement(name = "account_password", required = true)
    protected String accountPassword;
    @XmlElement(name = "account_level")
    protected int accountLevel;
    @XmlElement(name = "account_bought_stocks", required = true)
    protected BoughtStocks accountBoughtStocks;

    /**
     * Gets the value of the accountName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAccountName() {
        return accountName;
    }

    /**
     * Sets the value of the accountName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAccountName(String value) {
        this.accountName = value;
    }

    /**
     * Gets the value of the accountPassword property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAccountPassword() {
        return accountPassword;
    }

    /**
     * Sets the value of the accountPassword property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAccountPassword(String value) {
        this.accountPassword = value;
    }

    /**
     * Gets the value of the accountLevel property.
     * 
     */
    public int getAccountLevel() {
        return accountLevel;
    }

    /**
     * Sets the value of the accountLevel property.
     * 
     */
    public void setAccountLevel(int value) {
        this.accountLevel = value;
    }

    /**
     * Gets the value of the accountBoughtStocks property.
     * 
     * @return
     *     possible object is
     *     {@link BoughtStocks }
     *     
     */
    public BoughtStocks getAccountBoughtStocks() {
        return accountBoughtStocks;
    }

    /**
     * Sets the value of the accountBoughtStocks property.
     * 
     * @param value
     *     allowed object is
     *     {@link BoughtStocks }
     *     
     */
    public void setAccountBoughtStocks(BoughtStocks value) {
        this.accountBoughtStocks = value;
    }

}
