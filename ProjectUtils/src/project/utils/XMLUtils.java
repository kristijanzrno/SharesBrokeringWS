/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.StringReader;
import java.time.Instant;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 *
 * @author kristijanzrno
 */
public class XMLUtils {

    // Class created to put the jaxb xml marshalling/unmarshalling code block in a simple static function
    // Because they are many times in different pieces of the project    
    
    public static void marshallObject(Object marshallObject, File file) {
        try {
            javax.xml.bind.JAXBContext jaxbCtx = javax.xml.bind.JAXBContext.newInstance(marshallObject.getClass().getPackage().getName());
            javax.xml.bind.Marshaller marshaller = jaxbCtx.createMarshaller();
            marshaller.setProperty(javax.xml.bind.Marshaller.JAXB_ENCODING, "UTF-8"); //NOI18N
            marshaller.setProperty(javax.xml.bind.Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(marshallObject, file);
        } catch (javax.xml.bind.JAXBException ex) {
            java.util.logging.Logger.getLogger("global").log(java.util.logging.Level.SEVERE, null, ex); //NOI18N
        }
    }

    public static Object unmarshallObject(File file, String context) {
        try {
            javax.xml.bind.JAXBContext jaxbCtx = javax.xml.bind.JAXBContext.newInstance(context);
            javax.xml.bind.Unmarshaller unmarshaller = jaxbCtx.createUnmarshaller();
            return unmarshaller.unmarshal(file); //NOI18N
        } catch (javax.xml.bind.JAXBException ex) {
            java.util.logging.Logger.getLogger("global").log(java.util.logging.Level.SEVERE, null, ex); //NOI18N
        }
        return null;
    }

    // Unmarshalling the object from a given string instead of file
    public static Object unmarshallObjectFromString(String data, String context) {
        try {
            javax.xml.bind.JAXBContext jaxbCtx = javax.xml.bind.JAXBContext.newInstance(context);
            javax.xml.bind.Unmarshaller unmarshaller = jaxbCtx.createUnmarshaller();
            return unmarshaller.unmarshal(new StringReader(data));
        } catch (javax.xml.bind.JAXBException ex) {
            java.util.logging.Logger.getLogger("global").log(java.util.logging.Level.SEVERE, null, ex); //NOI18N
        }
        return null;
    }

    // Reading the XML file to a string
    public static String readToString(File file) {
        BufferedReader br;
        StringBuilder sb = new StringBuilder();
        String result;
        try {
            br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line + "\n");
            }
            result = sb.toString();
        } catch (Exception e) {
            result = "";
        }
        return result;
    }

    // Generating a new XMLCalendar date with the current date/time
    // Used for updating the last-updated information in stock objects
    public static XMLGregorianCalendar currentDate() {
        Date date = new Date();
        Instant instant = date.toInstant();
        String dateTime = instant.toString();
        XMLGregorianCalendar xmlDate = null;
        try {
            xmlDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(dateTime);
        } catch (DatatypeConfigurationException ex) {
            Logger.getLogger(XMLUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return xmlDate;
    }
}
