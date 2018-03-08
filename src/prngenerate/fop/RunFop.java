package prngenerate.fop;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;

import org.apache.fop.apps.FOPException;
import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.FormattingResults;
import org.apache.fop.apps.MimeConstants;
import org.apache.fop.apps.PageSequenceResults;

public class RunFop {
	static FopFactory fopFactory = null;
	static FOUserAgent foUserAgent=null;
	static TransformerFactory transformerFactory = null;
	static {
		try {
			fopFactory = FopFactory.newInstance(new File(".").toURI());
			foUserAgent = fopFactory.newFOUserAgent();
			transformerFactory = TransformerFactory.newInstance();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	
	public static TransformerFactory getTransformerFactory() {
		return transformerFactory;
	}



	public static void processFOP(Source src, File output,String outputType) throws IOException, FOPException {
		OutputStream out = null;

		try {
			out = new FileOutputStream(output);
			out = new BufferedOutputStream(out);

			Fop fop = fopFactory.newFop(outputType, foUserAgent, out);

			TransformerFactory factory = TransformerFactory.newInstance();
			Transformer transformer = factory.newTransformer();

			Result res = new SAXResult(fop.getDefaultHandler());

			transformer.transform(src, res);

			FormattingResults foResults = fop.getResults();
			java.util.List pageSequences = foResults.getPageSequences();
			for (java.util.Iterator it = pageSequences.iterator(); it.hasNext();) {
				PageSequenceResults pageSequenceResults = (PageSequenceResults) it.next();
				System.out.println("PageSequence "
						+ (String.valueOf(pageSequenceResults.getID()).length() > 0 ? pageSequenceResults.getID()
								: "<no id>")
						+ " generated " + pageSequenceResults.getPageCount() + " pages.");
			}
			System.out.println("Generated " + foResults.getPageCount() + " pages in total.");

		} catch (Exception e) {
			e.printStackTrace(System.err);
			System.exit(-1);
		} finally {
			out.close();
		}
	}



	public static FopFactory getFopFactory() {
		return fopFactory;
	}



	public static FOUserAgent getFoUserAgent() {
		return foUserAgent;
	}
}
