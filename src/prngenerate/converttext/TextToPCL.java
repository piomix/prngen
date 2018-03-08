package prngenerate.converttext;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.activation.MimeType;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.fop.apps.FOPException;
import org.apache.xmlgraphics.util.MimeConstants;
import org.xml.sax.SAXException;

import prngenerate.PrnGenerate;
import prngenerate.fop.RunFop;
import prngenerate.test.Test;

public class TextToPCL {
	static String startblock=null;
	static String endblock=null;
	static private String fo_template;
	
	
	public static String getStartblock() {
		if (startblock == null) {
			startblock  ="\n<fo:block font-size=\"10pt\"\n";
			startblock +=" font-family=\"sans-serif\"\n";
			startblock += " white-space=\"pre\"\n";
			startblock += " page-break-after=\"always\"\n";
		    startblock += " line-height=\"11pt\"\n";
		    startblock += " space-before=\"3pt\"\n";
		    startblock += " space-after=\"3pt\"\n";
		    startblock += " text-align=\"start\">\n";
		}
		return startblock;
	}

	public static void setStartblock(String startblock) {
		TextToPCL.startblock = startblock;
	}

	public static String getEndblock() {
		if (endblock == null) {
			endblock = "</fo:block>";
		}
		return endblock;
	}

	public static void setEndblock(String endblock) {
		TextToPCL.endblock = endblock;
	}

	public static String getFo_template() {
		if (fo_template == null) {
			InputStream in = Test.class.getResourceAsStream("/prngenerate/converttext/text_page_template.fo");
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			StringBuilder out = new StringBuilder();
			String line;
			try {
				while ((line = reader.readLine()) != null) {
					out.append(line+"\n");
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			fo_template = out.toString();
		}
		return fo_template;
	}

	public static void convertText(String inputfilename, String outputfilename,String mimetype) throws IOException, FOPException {
			String str = PrnGenerate.getProps("maxtextlinesperblock");
			int linesperblock = Integer.parseInt(str);
			InputStream in = new FileInputStream(new File(inputfilename));
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			StringBuilder out = new StringBuilder();
			String line;
			String start = TextToPCL.getStartblock();
			String end = TextToPCL.getEndblock();
	
			int linesthisblock=0;
			while ((line = reader.readLine()) != null) {
				if (linesthisblock==0) {
					out.append(start+"\n");
				}
				line = StringEscapeUtils.escapeXml(line);
				out.append(line+"\n");
				linesthisblock++;
				if (linesthisblock>=linesperblock) {
					out.append(end+"\n");
					linesthisblock = 0;
				}
			}
			if (linesthisblock>0) {
				out.append(end+"\n");
			}
	
			String template = TextToPCL.getFo_template();
	
			template = template.replaceAll("BODY GOES HERE", out.toString());
			
			System.out.println(template);
			
			ByteArrayInputStream stream = new ByteArrayInputStream(template.getBytes());
			Source src=new StreamSource(stream);
			RunFop.processFOP(src, new File(outputfilename),mimetype);
	}

	public static String convertText(String fname) throws IOException, SAXException {
		
		File tempFile = File.createTempFile("txt", ".out",new File(PrnGenerate.getTmpDir()));
		String outputfilename = tempFile.getPath();
		
		convertText(fname, outputfilename,MimeConstants.MIME_PDF);
		return outputfilename;
	}

}
