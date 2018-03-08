package prngenerate.convertpdf;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Templates;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.io.FilenameUtils;
import org.apache.fop.apps.FOPException;
import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.FormattingResults;
import org.apache.xmlgraphics.util.MimeConstants;
import org.apache.fop.apps.PageSequenceResults;
import org.xml.sax.SAXException;

import prngenerate.fop.RunFop;

public class JpgListToPCL {

	
	public static void convertJpgListToPCL(List<String> filenames, String outputfilename) {
		String fo = "";
		String tmp = null;
				
		fo += "<?xml version=\"1.0\" encoding=\"utf-8\"?>";
		fo += "<fo:root xmlns:fo=\"http://www.w3.org/1999/XSL/Format\">";
		fo += "  <fo:layout-master-set>";							
		fo += "    <fo:simple-page-master master-name=\"standard\" page-width=\"8.5in\" page-height=\"11in\">";
		fo += "      <fo:region-body />";
		fo += "    </fo:simple-page-master>";
		fo += "  </fo:layout-master-set>";
		for (String str:filenames) {
			tmp = FilenameUtils.separatorsToUnix(str);
			fo += "  <fo:page-sequence master-reference=\"standard\">";
			fo += "    <fo:flow flow-name=\"xsl-region-body\">";
			fo += "      <fo:block display-align=\"center\">";
			fo += "        <fo:external-graphic src=\""+tmp+"\"" ;
			fo += "                width=\"96%\" content-width=\"scale-to-fit\" content-height=\"96%\"/>";
			fo += "      </fo:block>";
			fo += "    </fo:flow>";
			fo += "  </fo:page-sequence>";
		}
		fo += "</fo:root>";
		try {
			ByteArrayInputStream stream = new ByteArrayInputStream(fo.getBytes());
			Source src=new StreamSource(stream);
			RunFop.processFOP(src,new File(outputfilename),MimeConstants.MIME_PCL);
		} catch (FOPException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	}
