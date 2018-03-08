<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:fo="http://www.w3.org/1999/XSL/Format"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
	<xsl:output indent="yes" method="xml" />
	<xsl:template match="/">
		<fo:root>
			<fo:layout-master-set>
				<fo:simple-page-master margin-right=".2in"
					margin-left=".2in" margin-bottom=".2in" margin-top=".2in"
					page-width="11in" page-height="8.5in" master-name="letter-landscape">
					<fo:region-body />
				</fo:simple-page-master>
			</fo:layout-master-set>
			<fo:page-sequence master-reference="letter-landscape">
				<fo:flow flow-name="xsl-region-body">
					<!-- fo:block-container left="30pt" top="20pt"
						position="absolute">
						<xsl:choose>        
											This code inserted a CVS logo at a designated spot on the pack slip
											Left it here as an example
							<xsl:when
								test="packslip/ship-from/pharmacy_type='H' or packslip/ship-from/pharmacy_type='C'">
								<fo:block>
									<fo:external-graphic content-height="0.50in"
										scaling="uniform" src="/usr2/batch/gif/CVS_specialty_bw.jpg" />
								</fo:block>
							</xsl:when>
							<xsl:otherwise>
								<fo:block>
									<fo:external-graphic content-height="0.50in"
										scaling="uniform" src="/usr2/batch/gif/CVS_pharmacy_bw.jpg" />
								</fo:block>
							</xsl:otherwise>
						</xsl:choose >
					</fo:block-container -->
					<fo:block-container left="560pt" top="80pt"
						position="absolute">
						<xsl:variable select="packslip/shipnum" name="shipnum" />
						<fo:block>
							<fo:instream-foreign-object>
								<barcode:barcode message="{$shipnum}"
									xmlns:barcode="http://barcode4j.krysalis.org/ns">
									<barcode:code128>
										<barcode:height> 12mm </barcode:height>
									</barcode:code128>
								</barcode:barcode>
							</fo:instream-foreign-object>
						</fo:block>
					</fo:block-container>
					<fo:block-container left="250pt" top="5pt"
						position="absolute" font-size="10pt" width="500pt" height="200pt">
						<fo:block>
							<fo:inline font-size="12pt" font-weight="bold"> PACKING SLIP -
								THIS IS NOT A BILL </fo:inline>
						</fo:block>
					</fo:block-container>
					<fo:block-container left="300pt" top="20pt"
						position="absolute" font-size="10pt" width="500pt" height="200pt">
						<fo:block>
							<fo:inline font-size="12pt" font-weight="bold"> Ship
								Information: </fo:inline>
							Please notify us of any shipping address changes.
						</fo:block>
						<fo:block>
							<xsl:value-of select="packslip/ship-to/name" />
						</fo:block>
						<fo:block>
							<xsl:value-of select="packslip/ship-to/addr1" />
						</fo:block>
						<fo:block>
							<xsl:value-of select="packslip/ship-to/addr2" />
						</fo:block>
						<fo:block>
							<xsl:value-of select="packslip/ship-to/city" />
							,
							<xsl:value-of select="packslip/ship-to/state" />
							<xsl:value-of select="packslip/ship-to/postalcode" />
						</fo:block>
					</fo:block-container>
					<fo:block-container left="35pt" top="85pt"
						position="absolute" font-size="10pt" width="500pt" height="14pt">
						<fo:block>
							<xsl:value-of select="packslip/ship-from/name" />
							<xsl:value-of select="packslip/ship-from/addr1" />
							<xsl:value-of select="packslip/ship-from/city" />
							,
							<xsl:value-of select="packslip/ship-from/state" />
							<xsl:value-of select="packslip/ship-from/postalcode" />
						</fo:block>
					</fo:block-container>
					<fo:block-container left="130pt" top="105pt"
						position="absolute" font-size="10pt" width="410pt" height="100pt">
						<fo:block> You have the right to consult with a pharmacist about
							the prescriptions in this order. If you would like to speak to a
							Pharmacist or have any questions about this order please call
						</fo:block>
						<fo:block>
							<xsl:attribute name="text-align">center</xsl:attribute>
							<xsl:value-of select="packslip/contactnumber" />
							IMMEDIATELY.
						</fo:block>
					</fo:block-container>
					<fo:block-container left="470pt" top="140pt"
						position="absolute" font-size="10pt" width="280pt" height="1000pt"
						block-progression-dimension="auto">
						<fo:table width="100%" table-layout="fixed" border-width="1pt"
							border-style="solid" border-color="black">
							<fo:table-body font-size="10pt" font-weight="normal"
								font-family="sans-serif">
								<fo:table-row>
									<fo:table-cell height="125" border-width="1pt"
										border-style="solid" border-color="black" text-align="center"
										padding="2pt">
										<fo:block font-weight="bold" text-decoration="underline"> SPECIAL
											INSTRUCTIONS </fo:block>
										<fo:block>
											<xsl:value-of select="packslip/instructs" />
										</fo:block>
									</fo:table-cell>
								</fo:table-row>
							</fo:table-body>
						</fo:table>
					</fo:block-container>
					<fo:block-container left="470pt" top="280pt"
						position="absolute" font-size="10pt" width="280pt" height="140pt"
						block-progression-dimension="auto">
						<fo:table width="100%" table-layout="fixed" border-width="1pt"
							border-style="solid" border-color="black">
							<fo:table-body font-weight="normal" font-family="sans-serif">
								<fo:table-row>
									<fo:table-cell font-size="9pt" border-width="1pt"
										border-style="solid" border-color="black" padding="2pt">
										<fo:block> Call your doctor for medical advice about side
											effects. You may report side effects to the FDA at
											1-800-FDA-1088 or at http://www.fda.gov/medwatch </fo:block>
										<fo:block>
											Written information about this prescription has been provided
											for you. Please read this information before you take the
											medication. If you have questions concerning this
											prescription, a pharmacist is available during normal
											business hours to answer these questions at
											<xsl:value-of
												select="packslip/contactNumbers/localPharmacyContactNumber" />
											or
											<xsl:value-of
												select="packslip/contactNumbers/dispensingPharmacyContactNumber" />
										</fo:block>
										<fo:block> Do not flush unused medications or pour down a sink
											or drain. </fo:block>
									</fo:table-cell>
								</fo:table-row>
							</fo:table-body>
						</fo:table>
					</fo:block-container>
					<fo:block-container left="20pt" top="555pt"
						position="absolute" font-size="12pt" width="410pt" height="140pt"
						color="grey">
						<fo:block>Estimated Shipped Date:</fo:block>
					</fo:block-container>
					<fo:block-container left="240pt" top="555pt"
						position="absolute" font-size="12pt" width="410pt" height="140pt"
						color="grey">
						<fo:block>
							<xsl:value-of select="packslip/ship-date" />
						</fo:block>
					</fo:block-container>
					<fo:block-container left="480pt" top="555pt"
						position="absolute" font-size="12pt" width="410pt" height="140pt"
						font-weight="bold" color="grey">
						<fo:block>
							<xsl:attribute name="text-align">justify</xsl:attribute>
							<xsl:value-of select="packslip/ship-from/dba_name" />
						</fo:block>
					</fo:block-container>
					<fo:block-container left="20pt" top="420pt"
						position="absolute" font-size="10pt" width="730pt" height="105pt"
						block-progression-dimension="auto">
						<fo:table width="100%" table-layout="fixed" border-width="1pt"
							border-style="solid" border-color="black">
							<fo:table-body>
								<fo:table-row>
									<fo:table-cell height="45">
										<fo:block font-size="10pt">
											<xsl:value-of select="packslip/important" />
										</fo:block>
										<fo:block>
											If a less expensive generically equivalent drug is available
											for certain brand drugs, you have the right to choose between
											the generic and the brand name drug. If you have any
											questions about the availability of generically equivalent
											drugs for your prescriptions, please call
											<xsl:value-of
												select="packslip/contactNumbers/genericDrugAvailContactNumber" />
											to speak to a pharmacist.
										</fo:block>
										<fo:block>
											<xsl:value-of select="packslip/stateTexts/fepBlock" />
										</fo:block>
									</fo:table-cell>
								</fo:table-row>
								<fo:table-row>
									<fo:table-cell height="60" display-align="after">
										<fo:block>
											<xsl:value-of select="packslip/stateTexts/textBlock1" />
										</fo:block>
										<fo:block>
											<xsl:value-of select="packslip/stateTexts/textBlock2" />
										</fo:block>
										<fo:block>
											<xsl:value-of select="packslip/stateTexts/textBlock3" />
										</fo:block>
									</fo:table-cell>
								</fo:table-row>
							</fo:table-body>
						</fo:table>
					</fo:block-container>
					<fo:block-container left="20pt" top="525pt"
						position="absolute" font-size="8pt" width="730pt" height="100pt"
						block-progression-dimension="auto">
						<fo:table width="100%" table-layout="fixed" border-width="0pt"
							border-style="solid" border-color="transparent">
							<fo:table-body>
								<fo:table-row>
									<fo:table-cell height="100">
										<fo:block />
									</fo:table-cell>
								</fo:table-row>
							</fo:table-body>
						</fo:table>
					</fo:block-container>
					<fo:block-container left="20pt" top="140pt"
						position="absolute" width="16cm" height="11cm">
						<fo:table width="100%" table-layout="fixed" border-width="1pt"
							border-style="solid" border-color="black">
							<fo:table-column border-width="1pt" border-style="solid"
								border-color="black" column-width="2.5cm" />
							<fo:table-column border-width="1pt" border-style="solid"
								border-color="black" column-width="5.5cm" />
							<fo:table-column border-width="1pt" border-style="solid"
								border-color="black" column-width="2.5cm" />
							<fo:table-column border-width="1pt" border-style="solid"
								border-color="black" column-width="2.5cm" />
							<fo:table-column border-width="1pt" border-style="solid"
								border-color="black" column-width="2.5cm" />
							<fo:table-body font-size="8pt" font-weight="normal"
								font-family="sans-serif">
								<fo:table-row>
									<fo:table-cell border-width="1pt" border-style="solid"
										border-color="black" padding="2pt" number-columns-spanned="5">
										<fo:block>
											Order Number:
											<xsl:value-of select="packslip/ordernum" />
										</fo:block>
									</fo:table-cell>
								</fo:table-row>
								<fo:table-row background-color="darkgreen">
									<fo:table-cell font-size="11" font-weight="bold"
										border-width="1pt" border-style="solid" border-color="black"
										text-align="center" color="white" number-columns-spanned="5">
										<fo:block> ORDER SUMMARY </fo:block>
									</fo:table-cell>
								</fo:table-row>
								<fo:table-row font-size="9" text-align="center">
									<fo:table-cell font-weight="bold" border-width="1pt"
										border-style="solid" border-color="black" padding="2pt">
										<fo:block>Rx No</fo:block>
									</fo:table-cell>
									<fo:table-cell font-weight="bold" border-width="1pt"
										border-style="solid" border-color="black" padding="1pt">
										<fo:block>Description</fo:block>
									</fo:table-cell>
									<fo:table-cell font-weight="bold" border-width="1pt"
										border-style="solid" border-color="black" padding="1pt">
										<fo:block text-align="right"> Qty Ord </fo:block>
									</fo:table-cell>
									<fo:table-cell font-weight="bold" border-width="1pt"
										border-style="solid" border-color="black" padding="1pt">
										<fo:block text-align="right"> Qty Ship </fo:block>
									</fo:table-cell>
									<fo:table-cell font-weight="bold" border-width="1pt"
										border-style="solid" border-color="black" padding="1pt">
										<fo:block text-align="right"> Co Pay </fo:block>
									</fo:table-cell>
								</fo:table-row>
								<xsl:for-each select="packslip/rxs/rx">
									<fo:table-row>
										<xsl:choose>
											<xsl:when test="position() mod 2 != 1">
												<xsl:attribute name="background-color">white</xsl:attribute>
											</xsl:when>
											<xsl:otherwise>
												<xsl:attribute name="background-color">rgb(217,236,255)</xsl:attribute>
											</xsl:otherwise>
										</xsl:choose>
										<fo:table-cell border-width="1pt" border-style="solid"
											border-color="black" padding="2pt">
											<fo:block height="3mm">
												<xsl:value-of select="rxnum" />
											</fo:block>
										</fo:table-cell>
										<fo:table-cell border-width="1pt" border-style="solid"
											border-color="black" padding="2pt">
											<fo:block>
												<xsl:value-of select="descr" />
											</fo:block>
										</fo:table-cell>
										<fo:table-cell border-width="1pt" border-style="solid"
											border-color="black" padding="2pt">
											<fo:block text-align="right">
												<xsl:value-of select="qty-ord" />
											</fo:block>
										</fo:table-cell>
										<fo:table-cell border-width="1pt" border-style="solid"
											border-color="black" padding="2pt">
											<fo:block text-align="right">
												<xsl:value-of select="qty-ship" />
											</fo:block>
										</fo:table-cell>
										<fo:table-cell border-width="1pt" border-style="solid"
											border-color="black" padding="2pt">
											<fo:block text-align="right">
												<xsl:value-of select="copay" />
											</fo:block>
										</fo:table-cell>
									</fo:table-row>
								</xsl:for-each>
								<xsl:call-template name="blank-rows">
									<xsl:with-param select="16 - count(packslip/rxs/rx)"
										name="count" />
								</xsl:call-template>
							</fo:table-body>
						</fo:table>
					</fo:block-container>
				</fo:flow>
			</fo:page-sequence>
		</fo:root>
	</xsl:template>
	<xsl:template name="blank-rows">
		<xsl:param name="count" />
		<fo:table-row>
			<xsl:choose>
				<xsl:when test="$count mod 2 = 1">
					<xsl:attribute name="background-color">white</xsl:attribute>
				</xsl:when>
				<xsl:otherwise>
					<xsl:attribute name="background-color">rgb(217,236,255)</xsl:attribute>
				</xsl:otherwise>
			</xsl:choose>
			<fo:table-cell border-width="1pt" border-style="solid"
				border-color="black" padding="2pt">
				<fo:block-container height="3mm">
					<fo:block />
				</fo:block-container>
			</fo:table-cell>
			<fo:table-cell border-width="1pt" border-style="solid"
				border-color="black" padding="2pt">
				<fo:block />
			</fo:table-cell>
			<fo:table-cell border-width="1pt" border-style="solid"
				border-color="black" padding="2pt">
				<fo:block />
			</fo:table-cell>
			<fo:table-cell border-width="1pt" border-style="solid"
				border-color="black" padding="2pt">
				<fo:block />
			</fo:table-cell>
			<fo:table-cell border-width="1pt" border-style="solid"
				border-color="black" padding="2pt">
				<fo:block />
			</fo:table-cell>
		</fo:table-row>
		<xsl:if test="$count > 1">
			<xsl:call-template name="blank-rows">
				<xsl:with-param select="$count - 1" name="count" />
			</xsl:call-template>
		</xsl:if>
	</xsl:template>
</xsl:stylesheet>
