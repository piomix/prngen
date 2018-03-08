package prngenerate.packslip.model;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.io.IOUtils;

@XmlRootElement(name = "packslip")
public class PackSlipData {
	ShipFrom shipFrom = null;

	ShipTo shipTo = null;

	String contactnumber = null;

	String shipnum = null;

	String ordernum = null;

	ContactNumbers contactNumbers = null;

	List<RX> rx = null;

	StateTexts stateTexts = null;

	String instructs = null;

	String shipdate = null;

	String pickupdate = null;

	String important = null;
	
	String copaytotal = null;

	@XmlElement(name = "ship-from")
	public ShipFrom getShipFrom() {
		return shipFrom;
	}

	public void setShipFrom(ShipFrom shipFrom) {
		this.shipFrom = shipFrom;
	}

	@XmlElement(name = "ship-to")
	public ShipTo getShipTo() {
		return shipTo;
	}

	public void setShipTo(ShipTo shipTo) {
		this.shipTo = shipTo;
	}

	public String getContactnumber() {
		return contactnumber;
	}

	public void setContactnumber(String contactnumber) {
		this.contactnumber = contactnumber;
	}

	public String getShipnum() {
		return shipnum;
	}

	public void setShipnum(String shipnum) {
		this.shipnum = shipnum;
	}

	public String getOrdernum() {
		return ordernum;
	}

	public void setOrdernum(String ordernum) {
		this.ordernum = ordernum;
	}

	public ContactNumbers getContactNumbers() {
		return contactNumbers;
	}

	public void setContactNumbers(ContactNumbers contactNumbers) {
		this.contactNumbers = contactNumbers;
	}

	public List<RX> getRx() {
		return rx;
	}

	@XmlElementWrapper(name = "rxs")
	public void setRx(List<RX> rx) {
		this.rx = rx;
	}

	public StateTexts getStateTexts() {
		return stateTexts;
	}

	public void setStateTexts(StateTexts stateTexts) {
		this.stateTexts = stateTexts;
	}

	public String getInstructs() {
		return instructs;
	}

	public void setInstructs(String instructs) {
		this.instructs = instructs;
	}

	@XmlElement(name = "ship-date")
	public String getShipdate() {
		return shipdate;
	}

	public void setShipdate(String shipdate) {
		this.shipdate = shipdate;
	}

	@XmlElement(name = "pickup_date")
	public String getPickupdate() {
		return pickupdate;
	}

	public void setPickupdate(String pickupdate) {
		this.pickupdate = pickupdate;
	}

	public String getImportant() {
		return important;
	}

	public void setImportant(String important) {
		this.important = important;
	}

	public static void main(String[] args) {
		PackSlipData ps = setupDummyRec();
		try {
			String xml = ps.convertPackSlipDataToXML();
			System.out.println(xml);
		} catch (JAXBException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String convertPackSlipDataToXML() throws JAXBException, IOException {
		String xmlString = "";
		JAXBContext jaxbContext;
		ByteArrayOutputStream bout = new ByteArrayOutputStream();

		jaxbContext = JAXBContext.newInstance(PackSlipData.class);
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

		PackSlipData packSlipData = PackSlipData.setupDummyRec();
		jaxbMarshaller.marshal(packSlipData ,bout);
		ByteArrayInputStream in = new ByteArrayInputStream(bout.toByteArray());
		xmlString = IOUtils.toString(in, StandardCharsets.UTF_8.name());
		return xmlString;
	}

	public static PackSlipData setupDummyRec() {
		PackSlipData ps = new PackSlipData();
		ps.shipFrom = new ShipFrom();
		ps.shipFrom.addr1 = "123 DC";
		ps.shipFrom.city = "DC CITY";
		ps.shipFrom.state = "PA";
		ps.shipFrom.postalcode = "12345";
		ps.shipFrom.dba_name = "Magellan Health";
		ps.shipTo = new ShipTo();
		ps.shipTo.name = "Ima Patient";
		ps.shipTo.addr1 = "123 Street Address";
		ps.shipTo.addr1 = "Suite 16";
		ps.shipTo.city = "Anywhere";
		ps.shipTo.postalcode = "12345";
		ps.shipTo.state = "PA";
		ps.contactnumber = "(123) 123-1233";
		ps.shipnum = "#SHIP12345";
		ps.ordernum = "#ORD412341";
		ps.contactNumbers = new ContactNumbers();
		ps.contactNumbers.dispensingPharmacyContactNumber = "(123) 123-1234";
		ps.contactNumbers.localPharmacyContactNumber = "(123) 123-1234";
		ps.contactNumbers.genericDrugAvailContactNumber = "(123) 123-1234";
		ps.rx = new ArrayList<RX>();
		RX rx = new RX();
		rx.rxnum = "12312313";
		rx.descr = "Miracle Cure All";
		rx.qtyord = "10";
		rx.qtyship = "1";
		rx.copay = "$12.50";
		ps.rx.add(rx);
		ps.copaytotal = "$12.50";
		ps.stateTexts = new StateTexts();
		ps.stateTexts.fepBlock = "FEP BLOCK TEXT GOES HERE";
		ps.stateTexts.textBlock1 = "BLOCK1 TEXT GOES HERE";
		ps.stateTexts.textBlock2 = "BLOCK2 TEXT GOES HERE";
		ps.stateTexts.textBlock3 = "BLOCK3 TEXT GOES HERE";
		ps.instructs = "Instructions show HERE";
		ps.shipdate = "12/31/2018";
		ps.pickupdate = "01/01/2019";
		ps.important = "This is a really important message";
		return ps;
	}

}
