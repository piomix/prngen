package prngenerate;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.TreeMap;

import javax.xml.bind.JAXBException;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.xmlgraphics.util.MimeConstants;

import prngenerate.convertpdf.PdfToPCL;
import prngenerate.converttext.TextToPCL;
import prngenerate.packslip.GeneratePackSlip;
import prngenerate.packslip.model.PackSlipData;

public class PrnGenerate {
	static private Properties props = null;
	static public boolean testmode = false;
	static public boolean logtrace = true;

	public static String getProps(String propname) {
		String answ = null;
		if (props == null) {
			loadprops();
		}
		if (props != null) {
			answ = props.getProperty(propname).trim();
		}
		return answ;
	}
	
	synchronized static private PrnGenerateResult loadprops() {
		PrnGenerateResult answ = new PrnGenerateResult();
		props = new Properties();
		try {
			props.load(PrnGenerate.class.getClassLoader().getResourceAsStream("prngenerate.properties"));
			try {
				String mode = props.getProperty("mode").toLowerCase().trim();
				if (mode.equals("test")) {
					testmode = true;
				}
				mode = props.getProperty("logtrace").toLowerCase().trim();
				if (mode.equals("false")) {
					logtrace = false;
				}
			} catch (Exception e2) {
				answ.status = PrnGenerateResult.ERRORPROCESSINGFILE;
				answ.errMsg = "Could not set mode";
				answ.exceptionMsg = e2.getMessage();
				props = null;
			}
		} catch (Exception e) {
			answ.status = PrnGenerateResult.COULDNOTREADFILE;
			answ.errMsg = "Could not load properties";
			answ.exceptionMsg = e.getMessage();
			props = null;
		}
		return (answ);
	}

	public static String getTmpDir() {
		String dirname = "";

		if (props == null) {
			loadprops();
		}
		dirname = PrnGenerate.props.getProperty("tmpdata");
		dirname = FilenameUtils.separatorsToSystem(dirname);
		return dirname;
	}

	static public PrnGenerateResult prnGenerate(PackSlipData packdata, String sourceDirName, String outputfilename) {
		PrnGenerateResult answ = new PrnGenerateResult();
		answ.lastFileName = sourceDirName;

		if (props == null) {
			answ = loadprops();
		}

		if (answ.ok()) {
			logPrintln("prnGenerate: processing source directory");

			TreeMap<String, String> tmFiles = new TreeMap<String, String>();
			try {
				File folder = new File(sourceDirName);
				String[] files = folder.list();

				// extract actual files (skip directories) and put in order of last time
				// modified
				for (String file : files) {
					File rec = new File(file);
					if (!rec.isDirectory()) {
						String key = Long.toString(rec.lastModified()).trim() + file;
						tmFiles.put(key, folder.getPath() + File.separator + file);
						logPrintln(file);
					}
				}
				List<String> lstFiles = new ArrayList<String>();
				lstFiles.addAll(tmFiles.values());

				answ = prnGenerate(packdata, lstFiles, outputfilename);
			} catch (Exception e) {
				answ.status = PrnGenerateResult.OTHERERROR;
				answ.errMsg = "Error caught in prnGenerate(..dir..)";
				answ.exceptionMsg = e.getMessage();
				if (logtrace) {
					e.printStackTrace();
				}
			}
		}
		return (answ);
	}

	static public PrnGenerateResult prnGenerate(PackSlipData packdata, List<String> sourceFileNames,
			String outputfilename) {
		List<String> lstPCLfilenames = new ArrayList<String>();
		
		PrnGenerateResult answ = new PrnGenerateResult();

		if (props == null) {
			answ = loadprops();
		}
		
		if (answ.ok()) {
			// Pack Slip first
			
			try {
				String packSlipFileOut = GeneratePackSlip.generatePackSlip(packdata);
				lstPCLfilenames.add(packSlipFileOut);
			} catch (JAXBException | IOException e) {
				answ.status = PrnGenerateResult.ERRORPROCESSINGFILE;
				answ.errMsg = "Failed To Generate PackSlip";
				answ.exceptionMsg = e.getMessage();
				answ.lastFileName = "Pack Slip Object";
			}
			
		}
		
		if (answ.ok()) {
			// remaining files in order
			logPrintln("prnGenerate: processing file list");
			for (String fname:sourceFileNames) {
				try {
					if (fname.toLowerCase().endsWith("pdf")) {
						String pdfconversion = PdfToPCL.pdfListToPCL(fname);
						lstPCLfilenames.add(pdfconversion);
					} else if (fname.toLowerCase().endsWith("txt")) {
						String txtconversion = TextToPCL.convertText(fname);
						lstPCLfilenames.add(txtconversion);
						if (PrnGenerate.testmode) {
							String pdfversion = txtconversion.replaceAll("out", "pdf");
							String mimetype = MimeConstants.MIME_PDF;
							TextToPCL.convertText(fname, pdfversion,mimetype);
						}
						
					}
				} catch (Exception e) {
					answ.status = PrnGenerateResult.ERRORPROCESSINGFILE;
					answ.errMsg = "Failed To produce PCL";
					answ.exceptionMsg = e.getMessage();
					answ.lastFileName = fname;
				}
			}
		}
		
		if (answ.ok()) {
			File ftmp;
			String str;
				try {
					File fout = new File(outputfilename);
					int cnt = 0;
					for (String tmp:lstPCLfilenames) {
						answ.lastFileName = tmp;
						ftmp = new File(tmp);
						byte[] barr = FileUtils.readFileToByteArray(ftmp);
						PrnGenerate.logPrintln(barr.length);
						FileUtils.writeByteArrayToFile(fout, barr, (cnt>0));
						if (PrnGenerate.testmode==false) {
							ftmp.delete();
						}
						cnt++;
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					answ.status = PrnGenerateResult.COULDNOTREADFILE;
					answ.errMsg = "Failed To produce PCL";
					answ.exceptionMsg = e.getMessage();
				}
			
			// 	consolidate into single output file
		}
		return (answ);
	}

	public static void logPrint(String string) {
		if (logtrace) {
			System.out.print(string);
		}
	}

	public static void logPrintln(int num) {
		if (logtrace) {
			System.out.println(num);
		}
	}

	public static void logPrintln(String string) {
		if (logtrace) {
			System.out.println(string);
		}
	}
}
