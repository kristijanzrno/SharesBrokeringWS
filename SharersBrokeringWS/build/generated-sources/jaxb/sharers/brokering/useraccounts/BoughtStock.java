//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.5-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.01.18 at 12:34:31 AM GMT 
//


package sharers.brokering.useraccounts;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for bought_stock complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="bought_stock">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="company_name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="company_symbol" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="no_of_bought_shares" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="date_bought" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "bought_stock", propOrder = {
    "companyName",
    "companySymbol",
    "noOfBoughtShares",
    "dateBought"
})
public class BoughtStock {

    @XmlElement(name = "company_name", required = true)
    protected String companyName;
    @XmlElement(name = "company_symbol", required = true)
    protected String companySymbol;
    @XmlElement(name = "no_of_bought_shares")
    protected int noOfBoughtShares;
    @XmlElement(name = "date_bought", required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dateBought;

    /**
     * Gets the value of the companyName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * Sets the value of the companyName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCompanyName(String value) {
        this.companyName = value;
    }

    /**
     * Gets the value of the companySymbol property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCompanySymbol() {
        return companySymbol;
    }

    /**
     * Sets the value of the companySymbol property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCompanySymbol(String value) {
        this.companySymbol = value;
    }

    /**
     * Gets the value of the noOfBoughtShares property.
     * 
     */
    public int getNoOfBoughtShares() {
        return noOfBoughtShares;
    }

    /**
     * Sets the value of the noOfBoughtShares property.
     * 
     */
    public void setNoOfBoughtShares(int value) {
        this.noOfBoughtShares = value;
    }

    /**
     * Gets the value of the dateBought property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDateBought() {
        return dateBought;
    }

    /**
     * Sets the value of the dateBought property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDateBought(XMLGregorianCalendar value) {
        this.dateBought = value;
    }

}
