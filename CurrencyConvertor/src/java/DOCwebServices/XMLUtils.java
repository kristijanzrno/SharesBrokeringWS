/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DOCwebServices;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.StringReader;


/**
 *
 * @author kristijanzrno
 */
public class XMLUtils {

    public static void marshallList(Object marshallObject, File file) {
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

    public static Object unmarshallList(File file, String data, String context) {
        try {
            javax.xml.bind.JAXBContext jaxbCtx = javax.xml.bind.JAXBContext.newInstance(context);
            javax.xml.bind.Unmarshaller unmarshaller = jaxbCtx.createUnmarshaller();
            if(file != null)
                return unmarshaller.unmarshal(file); //NOI18N
            else 
                return unmarshaller.unmarshal(new StringReader(data));
        } catch (javax.xml.bind.JAXBException ex) {
            java.util.logging.Logger.getLogger("global").log(java.util.logging.Level.SEVERE, null, ex); //NOI18N
        }
        return null;
    }

    public static String readToString(File file) {
        BufferedReader br;
        StringBuilder sb = new StringBuilder();
        String result;
        try {
            br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line+"\n");
            }
            result = sb.toString();
        } catch (Exception e) {
            result = "";
        }
        return result;
    }

}
