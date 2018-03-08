package prngenerate.packslip;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

import javax.xml.bind.JAXBException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Templates;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.fop.apps.FOPException;
import org.apache.fop.apps.Fop;
import org.apache.xmlgraphics.util.MimeConstants;

import prngenerate.PrnGenerate;
import prngenerate.fop.RunFop;
import prngenerate.packslip.model.PackSlipData;
import prngenerate.test.Test;

public class GeneratePackSlip {

	public static void convertXML(Source src, Source xsl, File outputfile,String mimetype) {
		try {
			OutputStream out = new java.io.FileOutputStream(outputfile);
			
			Fop fop = RunFop.getFopFactory().newFop(mimetype,
					  RunFop.getFoUserAgent(), out);

			// Setup JAXP using identity transformer
			Transformer transformer = RunFop.getTransformerFactory().newTransformer(xsl);

			// Setup input and output for XSLT transformation
			// Setup input stream

			// Resulting SAX events (the generated FO) must be piped through to FOP
			Result res = new SAXResult(fop.getDefaultHandler());

			// Start XSLT transformation and FOP processing
			transformer.transform(src, res);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FOPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static String generatePackSlip(PackSlipData packdata) throws JAXBException, IOException {

		String xml = packdata.convertPackSlipDataToXML();
		String xsl = IOUtils.toString(GeneratePackSlip.class.getResourceAsStream("/prngenerate/packslip/pack-slip.xsl"), StandardCharsets.UTF_8.name());
		
		Source srcXSL =new StreamSource( new ByteArrayInputStream(xsl.getBytes()));
		Source srcXML = new StreamSource( new ByteArrayInputStream(xml.getBytes()));

		String mimetype = MimeConstants.MIME_PCL;
		
		File tempFile = File.createTempFile("packslip", ".out",new File(PrnGenerate.getTmpDir()));

		String outputfilename =  tempFile.getCanonicalFile().getPath();
	    
		PrnGenerate.logPrintln("Pack slip output PCL filename: "+ outputfilename);
		convertXML(srcXML, srcXSL, tempFile, mimetype);
		
		if (PrnGenerate.testmode) {
			srcXSL =new StreamSource( new ByteArrayInputStream(xsl.getBytes()));
			srcXML = new StreamSource( new ByteArrayInputStream(xml.getBytes()));

			mimetype = MimeConstants.MIME_PDF;
			
			tempFile = File.createTempFile("packslip", ".pdf",new File(PrnGenerate.getTmpDir()));
		    
			PrnGenerate.logPrintln("Pack slip output PDF filename: "+ tempFile.getCanonicalFile().getPath());
			convertXML(srcXML, srcXSL, tempFile, mimetype);
		}
		
		return outputfilename;
	}
}
