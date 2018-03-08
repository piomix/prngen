package prngenerate.test;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.xmlgraphics.util.MimeConstants;

import prngenerate.packslip.GeneratePackSlip;

public class Demo {
	public static void main(String[] args) {
		try {
			List<String> lstPCLFiles = new ArrayList<String>();
			
			List<String> lstPDF = new ArrayList<String>();
			lstPDF.add("/testdata/EPS15-DOC-AR-001WelcomePacket_v06282017.pdf");
			lstPDF.add("/testdata/TICKETP_DT_6465_20171113132644.pdf");
			lstPDF.add("/testdata/EXACTUS_PHARMACY_SOLUTIONS_Dr_Refill_request_1.2.pdf");
			

			//PdfToPCL.PdfListToPCL(lstPDF,"/testdata/","/testdata/test_pdf.pcl");
			
			//TextToPCL.convertText("/testdata/sample.txt","Drug information or something","/testdata/test_text.pdf",MimeConstants.MIME_PDF);
			
			InputStream xslStream = Test.class.getResourceAsStream("/pdf2pcl/pack-slip.xsl");

			//GeneratePackSlip.convertXML("/testdata/packdata.xml",xslStream,"/testdata/test_xml.pdf",MimeConstants.MIME_PDF);
			
			
			lstPCLFiles.add("/testdata/test_pdf.pcl");
			lstPCLFiles.add("/testdata/test_text.pcl");
			lstPCLFiles.add("/testdata/test_xml.pcl");
			
			combine(lstPCLFiles,"/testdata/test_all.pcl");
		} catch (Exception e) {
		}
	}

	private static void combine(List<String> lstPCLFiles, String outputFileName) {
		//Copy the list of files into a single output file
	}
}
