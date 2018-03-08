package prngenerate.convertpdf;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.tools.imageio.ImageIOUtil;

import prngenerate.PrnGenerate;

public class Pdf2Jpg {

	public static List<String> convertPDF2Jpg(String completeFilePathSource) throws Exception {
		List<String> lst = new ArrayList<String>();
		
		PDDocument pdDoc;
		try {
			pdDoc = PDDocument.load(new File(completeFilePathSource));
			int numpages = pdDoc.getNumberOfPages();
			PDFRenderer pdfRenderer = new PDFRenderer(pdDoc);
			String filename;
			for (int idx = 0; idx < numpages; idx++) {
				File tempFile = File.createTempFile("pdf", ".jpg",new File(PrnGenerate.getTmpDir()));
				filename = tempFile.getPath();
				BufferedImage image = pdfRenderer.renderImageWithDPI(idx, 300, ImageType.GRAY);
				ImageIOUtil.writeImage(image,filename , 300);
				lst.add(filename);
			}
			pdDoc.close();
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		} catch (Throwable t) {
			t.printStackTrace();
			throw t;
		}
		return lst;
	}
}
