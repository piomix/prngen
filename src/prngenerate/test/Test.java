package prngenerate.test;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.fop.apps.FOPException;
import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.FormattingResults;
import org.apache.fop.apps.PageSequenceResults;
import org.apache.xmlgraphics.util.MimeConstants;
import org.xml.sax.SAXException;

import prngenerate.converttext.TextToPCL;
import prngenerate.fop.RunFop;

public class Test {

	public static void main(String[] args) {
		try {
			InputStream in = new FileInputStream(new File("C:/testdata/sample.txt"));
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			StringBuilder out = new StringBuilder();
			String line;
			String start = TextToPCL.getStartblock();
			String end = TextToPCL.getEndblock();

			while ((line = reader.readLine()) != null) {
				line = StringEscapeUtils.escapeXml(line);
				out.append(start+"\n"+line+"\n"+end+"\n");
			}

			String template = TextToPCL.getFo_template();

			template = template.replaceAll("TITLE GOES HERE", "This is our title");
			template = template.replaceAll("BODY GOES HERE", out.toString());
			
			System.out.println(template);
			
			ByteArrayInputStream stream = new ByteArrayInputStream(template.getBytes());
			Source src=new StreamSource(stream);
			RunFop.processFOP(src, new File("/testdata/page.pdf"),MimeConstants.MIME_PDF);
		} catch (FOPException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	

}
