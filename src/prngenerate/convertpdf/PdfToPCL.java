package prngenerate.convertpdf;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.xmlgraphics.util.MimeConstants;

import prngenerate.PrnGenerate;

public class PdfToPCL {
	public static String pdfListToPCL(String fname) throws Exception {
		String mimetype = MimeConstants.MIME_PCL;
		
		File tempFile = File.createTempFile("pdf", ".out",new File(PrnGenerate.getTmpDir()));
		String outputfilename = tempFile.getPath();
		List<String> lstJPG = Pdf2Jpg.convertPDF2Jpg(fname);
		JpgListToPCL.convertJpgListToPCL(lstJPG, outputfilename);
		if (PrnGenerate.testmode==false) {
			for (String tmp:lstJPG) {
				File f = new File(tmp);
				f.delete();
			}
		}
		return outputfilename;
	}
}
