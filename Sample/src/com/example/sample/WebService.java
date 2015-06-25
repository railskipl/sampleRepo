package com.example.sample;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class WebService 
{

	//Namespace of the Webservice - It is http://tempuri.org for .NET webservice
    private static String NAMESPACE = "http://www.esselgroup.org/getDetails/getDetailsRequest";
    //Webservice URL - It is asmx file location hosted in the server in case of .Net
        //Change the IP address to your machine IP address   
    private static String URL = "http://123.63.20.164:8001/soa-infra/services/CCBServices/EsselCCBAndroidIntegration/esselccbandroidintegrationbpel_client_ep?XSD=xsd/GetConsumerDetailsForPayment.xsd";
    //SOAP Action URI again http://tempuri.org
    private static String SOAP_ACTION = "http://www.esselgroup.org/getDetails/";
    static String METHOD_NAME = "getDetailsRequest";
 
    public static String invokeHelloWorldWS() {
        String resTxt = null;
        // Create request
        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        // Property which holds input parameters
        PropertyInfo sayHelloPI = new PropertyInfo();
        
        request.addProperty("ConsumerNo","410017357874");
		request.addProperty("MobileNo","898787897");
		request.addProperty("userid","abc");
		request.addProperty("password","123");
		request.addProperty("actid","10800123");
		request.addProperty("latitude","0");
		request.addProperty("longitude","0");
		request.addProperty("isOldConsumerFlag", "y");
		
        // Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        //Set envelope as dotNet
        envelope.dotNet = true;
        // Set output SOAP object
        envelope.setOutputSoapObject(request);
        // Create HTTP call object
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
 
        try {
            // Invoke web service
            androidHttpTransport.call(SOAP_ACTION+METHOD_NAME, envelope);
            // Get the response
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            // Assign it to resTxt variable static variable
            resTxt = response.toString();
            
 
        } catch (Exception e) {
            //Print error
            e.printStackTrace();
            //Assign error message to resTxt
            resTxt = "Error occured";
        }
        //Return resTxt to calling object
        return resTxt;
    }
	
}
