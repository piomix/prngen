package prngenerate.test;

import prngenerate.PrnGenerate;
import prngenerate.PrnGenerateResult;
import prngenerate.packslip.model.PackSlipData;

public class TestPrnGenerate {
	private static final String sourceDirName = "/testdata/PrnGenerate_SourceFiles";
	private static final String outputFileName = "/testdata/PrnGenerate.pcl";

	public static void main(String[] args) {
		PackSlipData packdata = new PackSlipData();
		packdata = PackSlipData.setupDummyRec();

		PrnGenerateResult res = PrnGenerate.prnGenerate(packdata, sourceDirName, outputFileName);

		if (!res.ok()) {
			PrnGenerate.logPrint("****PrnGenerate failed with status: ****");
			PrnGenerate.logPrintln(res.status);
			PrnGenerate.logPrintln("err msg:   "+res.errMsg);
			PrnGenerate.logPrintln("exception: "+res.exceptionMsg);
			PrnGenerate.logPrintln("last file: "+res.lastFileName);
		} else {
			PrnGenerate.logPrintln("PrnGenerate done.");
		}
	}
}
