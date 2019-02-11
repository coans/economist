/**
 * Every controller should be extended from this class
 *
 * @author Juraj Bilic
 * @version $Author: jbilic $ ($Revision: 1.2 $) - $Date: 2012-05-21 14:18:54 $
 * @since 1.0.0
 */

package com.economist.config;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.economist.auth.AuthorizationService;
import com.economist.db.entity.User;
import com.economist.dto.KifKufDTO;
import com.economist.dto.StavkaNalogaDTO;
import com.economist.model.BilansResultBean;
import com.economist.model.KifKufSearchBean;
import com.economist.model.SearchBean;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

@Controller("/" + BaseController.CONTROLLER)
public class BaseController {

	public static final String CONTROLLER = "base";
	public static final String MODEL_USER = "user";

	private static final String BASEURL = "baseurl";
	private static final String VERISON = "verison";
	
	private static final String KLASE_KONTA = "klaseKonta";
	private static final String DATUM_PATTERN = "datumPattern";
	private static final String POTRAZUJE = "potrazuje";
	private static final String DUGUJE = "duguje";
	private static final String SALDO = "saldo";
	private static final String TABLE_CLASS = "tableClass";
	private static final String LOKACIJE = "lokacije";
	
	
	@Value("${base.url}")
	protected String baseUrl;
	
	@Value("${product.version}")
	protected String productVersion;
	
	@Value("${datum.format}")
	protected String datumFormat;
	
	@Autowired
	private MessageSource messageSource;
	
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(datumFormat);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}
	
	@ModelAttribute(MODEL_USER)
	public User getUser() {
		return AuthorizationService.getAuthentificatedUser();
	}
	
	@ModelAttribute(BASEURL)
	public String getBaseUrl() {
		return baseUrl;
	}

	@ModelAttribute(VERISON)
	public String getProductVersion() {
		return productVersion;
	}

	@ModelAttribute(KLASE_KONTA)
	public List<Integer> getKlaseKonta() {
		return Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);
	}
	
	@ModelAttribute(LOKACIJE)
	public List<String> getLokacije() {
		return Arrays.asList("RS", "FBIH", "BRCKO");
	}
	
	@ModelAttribute(DATUM_PATTERN)
	public String getDatumPattern() {
		return datumFormat;
	}
	
	@ModelAttribute(TABLE_CLASS)
	public String getTableClass() {
		return "table table-striped table-bordered";
	}
	
	public void setZbirniRed(List<StavkaNalogaDTO> stavke, ModelMap model) {
		BigDecimal duguje = BigDecimal.ZERO;
		BigDecimal potrazuje = BigDecimal.ZERO;
		
		for (StavkaNalogaDTO stavka : stavke) {
			if (stavka.getDugujeStavka() != null) {
				duguje = duguje.add(stavka.getDugujeStavka());
			}
			if (stavka.getDugujeProtivStavka() != null) {
				duguje = duguje.add(stavka.getDugujeProtivStavka());
			}
			if (stavka.getDugujePDV() != null) {
				duguje = duguje.add(stavka.getDugujePDV());
			}
			if (stavka.getPotrazujeStavka() != null) {
				potrazuje = potrazuje.add(stavka.getPotrazujeStavka());
			}
			if (stavka.getPotrazujeProtivStavka() != null) {
				potrazuje = potrazuje.add(stavka.getPotrazujeProtivStavka());
			}
			if (stavka.getPotrazujePDV() != null) {
				potrazuje = potrazuje.add(stavka.getPotrazujePDV());
			}
		}
		model.addAttribute(DUGUJE, duguje);
		model.addAttribute(POTRAZUJE, potrazuje);
		model.addAttribute(SALDO, duguje.subtract(potrazuje));
	}
	
	public void setZbirniRedKifKuf(List<KifKufDTO> stavke, ModelMap model) {
		BigDecimal duguje = BigDecimal.ZERO;
		BigDecimal potrazuje = BigDecimal.ZERO;
		
		for (KifKufDTO stavka : stavke) {
			if (stavka.getIznos() != null) {
				duguje = duguje.add(stavka.getIznos());
			}
			if (stavka.getUkupno() != null) {
				potrazuje = potrazuje.add(stavka.getUkupno());
			}
		}
		model.addAttribute(DUGUJE, duguje);
		model.addAttribute(POTRAZUJE, potrazuje);
		model.addAttribute(SALDO, duguje.subtract(potrazuje));
	}
	
	public void generatePDF(HttpServletRequest request, HttpServletResponse response, List<StavkaNalogaDTO> stavkas, String reportTitle, SearchBean searchBean, String filename) throws DocumentException, IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		SimpleDateFormat formatter = new SimpleDateFormat(getDatumPattern());
		BaseFont baseFont = BaseFont.createFont(BaseFont.TIMES_ROMAN, BaseFont.CP1250, BaseFont.EMBEDDED);
		if (stavkas != null && !stavkas.isEmpty()) {
			Document doc = new Document();
			PdfWriter.getInstance(doc, baos);
			doc.open();
			
			Paragraph preduzece = new Paragraph(stavkas.get(0).getNalog().getPreduzece().getNaziv(), new Font(baseFont, 18, Font.BOLD));
			preduzece.setAlignment(Element.ALIGN_CENTER);
			doc.add(preduzece);
			
			Paragraph title = new Paragraph(reportTitle, new Font(baseFont, 12, Font.BOLD));
			title.setAlignment(Element.ALIGN_CENTER);
//			addEmptyLine(title, 1);
			doc.add(title);
			
			if (searchBean.getKomitent() != null && searchBean.getKomitent().getNaziv() != null) {
				Paragraph kontoOdDo = new Paragraph("Komitent: " + searchBean.getKomitent().getNaziv(), new Font(baseFont, 10, Font.BOLD));
				kontoOdDo.setAlignment(Element.ALIGN_CENTER);
				doc.add(kontoOdDo);
			}
			
			Paragraph kontoOdDo = new Paragraph("Konto: " + searchBean.getKontoOd().getSifra() + " - " + searchBean.getKontoDo().getSifra(), new Font(baseFont, 10, Font.BOLD));
			kontoOdDo.setAlignment(Element.ALIGN_CENTER);
	//		addEmptyLine(kontoOdDo, 1);
			doc.add(kontoOdDo);
			
			Paragraph datumOdDo = new Paragraph("Datum: " + formatter.format(searchBean.getDatumOd()) + " - " + formatter.format(searchBean.getDatumDo()), new Font(baseFont, 10, Font.BOLD));
			datumOdDo.setAlignment(Element.ALIGN_CENTER);
			addEmptyLine(datumOdDo, 1);
			doc.add(datumOdDo);
	
			// Creating a table object 
			PdfPTable table = new PdfPTable(9); // 9 columns.
			table.setWidths(new float[] { 3f, 6f, 8f, 12f, 15f, 11f, 8f, 9f, 8f });
			Font headerFont = new Font(baseFont, 10, Font.BOLD);
			PdfPCell c1 = new PdfPCell(new Phrase("#", headerFont));  c1.setHorizontalAlignment(Element.ALIGN_CENTER); c1.setBackgroundColor(BaseColor.LIGHT_GRAY); table.addCell(c1);	
	        c1 = new PdfPCell(new Phrase(messageSource.getMessage("broj", null, request.getLocale()), headerFont)); c1.setHorizontalAlignment(Element.ALIGN_CENTER); c1.setBackgroundColor(BaseColor.LIGHT_GRAY); table.addCell(c1);
	        c1 = new PdfPCell(new Phrase(messageSource.getMessage("datum", null, request.getLocale()), headerFont)); c1.setHorizontalAlignment(Element.ALIGN_CENTER); c1.setBackgroundColor(BaseColor.LIGHT_GRAY); table.addCell(c1);
	        c1 = new PdfPCell(new Phrase(messageSource.getMessage("opis", null, request.getLocale()), headerFont )); c1.setHorizontalAlignment(Element.ALIGN_CENTER); c1.setBackgroundColor(BaseColor.LIGHT_GRAY); table.addCell(c1);
	        c1 = new PdfPCell(new Phrase(messageSource.getMessage("konto", null, request.getLocale()), headerFont)); c1.setHorizontalAlignment(Element.ALIGN_CENTER); c1.setBackgroundColor(BaseColor.LIGHT_GRAY); table.addCell(c1);
	        c1 = new PdfPCell(new Phrase(messageSource.getMessage("komitent", null, request.getLocale()), headerFont)); c1.setHorizontalAlignment(Element.ALIGN_CENTER); c1.setBackgroundColor(BaseColor.LIGHT_GRAY); table.addCell(c1);
	        c1 = new PdfPCell(new Phrase(messageSource.getMessage("duguje", null, request.getLocale()), headerFont)); c1.setHorizontalAlignment(Element.ALIGN_CENTER); c1.setBackgroundColor(BaseColor.LIGHT_GRAY); table.addCell(c1);
	        c1 = new PdfPCell(new Phrase(messageSource.getMessage("potrazuje", null, request.getLocale()), headerFont)); c1.setHorizontalAlignment(Element.ALIGN_CENTER); c1.setBackgroundColor(BaseColor.LIGHT_GRAY); table.addCell(c1);
	        c1 = new PdfPCell(new Phrase(messageSource.getMessage("saldo", null, request.getLocale()), headerFont)); c1.setHorizontalAlignment(Element.ALIGN_CENTER); c1.setBackgroundColor(BaseColor.LIGHT_GRAY); table.addCell(c1);
	        table.setHeaderRows(1);
	        
	        int counter = 1;
	        Font cellFont = new Font(baseFont, 8, Font.NORMAL);
	        for (StavkaNalogaDTO stavka : stavkas) {
	        	PdfPCell rBroj = new PdfPCell(new Paragraph(String.valueOf(counter), cellFont)); table.addCell(rBroj);
	        	PdfPCell broj = new PdfPCell(new Paragraph(stavka.getNalog().getBroj(), cellFont)); table.addCell(broj);
	        	PdfPCell datum = new PdfPCell(new Paragraph(formatter.format(stavka.getDatum()), cellFont)); table.addCell(datum);
	        	PdfPCell opis = new PdfPCell(new Paragraph(stavka.getOpis(), cellFont)); table.addCell(opis);
	        	PdfPCell konto = new PdfPCell(new Paragraph(stavka.getKontoStavka().getSifraNaziv(), cellFont)); table.addCell(konto);
	        	PdfPCell komitent = new PdfPCell(new Paragraph(stavka.getKomitent().getNaziv(), cellFont)); table.addCell(komitent);
	        	PdfPCell duguje = new PdfPCell(new Paragraph(stavka.getDugujeStavka().toString(), cellFont)); duguje.setHorizontalAlignment(Element.ALIGN_RIGHT); table.addCell(duguje);
	        	PdfPCell potrazuje = new PdfPCell(new Paragraph(stavka.getPotrazujeStavka().toString(), cellFont)); potrazuje.setHorizontalAlignment(Element.ALIGN_RIGHT); table.addCell(potrazuje);
	        	PdfPCell saldo = new PdfPCell(new Paragraph(stavka.getSaldoStavka().toString(), cellFont)); saldo.setHorizontalAlignment(Element.ALIGN_RIGHT); table.addCell(saldo);
	        	counter++;
			}
	
			doc.add(table);
			doc.close();
		}
		// setting some response headers
		response.setHeader("Expires", "0");
		response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
		response.setHeader("Pragma", "public");
		response.setContentType("application/pdf; charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentLength(baos.size());
		response.addHeader("Content-Disposition", "attachment; filename=" + filename + ".pdf");
		// write ByteArrayOutputStream to the ServletOutputStream
		OutputStream os = response.getOutputStream();
		baos.writeTo(os);
		os.flush();
		os.close();
	}
	
	public void generateKifKufPDF(HttpServletRequest request, HttpServletResponse response, List<KifKufDTO> result, String reportTitle, KifKufSearchBean searchBean, List<String> headers, String filename) throws DocumentException, IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		SimpleDateFormat formatter = new SimpleDateFormat(getDatumPattern());
		BaseFont baseFont = BaseFont.createFont(BaseFont.TIMES_ROMAN, BaseFont.CP1250, BaseFont.EMBEDDED);
		if (result != null && !result.isEmpty()) {
			Document doc = new Document();
			PdfWriter.getInstance(doc, baos);
			doc.open();
			
			Paragraph preduzece = new Paragraph(getUser().getPreduzece().getNaziv(), new Font(baseFont, 18, Font.BOLD));
			preduzece.setAlignment(Element.ALIGN_CENTER);
			doc.add(preduzece);
			
			Paragraph title = new Paragraph(reportTitle, new Font(baseFont, 12, Font.BOLD));
			title.setAlignment(Element.ALIGN_CENTER);
//			addEmptyLine(title, 1);
			doc.add(title);
			
			if (searchBean.getKomitent() != null && searchBean.getKomitent().getNaziv() != null) {
				Paragraph kontoOdDo = new Paragraph("Komitent: " + searchBean.getKomitent().getNaziv(), new Font(baseFont, 10, Font.BOLD));
				kontoOdDo.setAlignment(Element.ALIGN_CENTER);
				doc.add(kontoOdDo);
			}
			
			Paragraph datumOdDo = new Paragraph("Datum: " + formatter.format(searchBean.getDatumOd()) + " - " + formatter.format(searchBean.getDatumDo()), new Font(baseFont, 10, Font.BOLD));
			datumOdDo.setAlignment(Element.ALIGN_CENTER);
			addEmptyLine(datumOdDo, 1);
			doc.add(datumOdDo);
	
			// Creating a table object 
			PdfPTable table = new PdfPTable(7);
			table.setWidths(new float[] { 3f, 6f, 8f, 15f, 10f, 10f, 10f});
			
			Font headerFont = new Font(baseFont, 10, Font.BOLD);
			for (String headerCell : headers) {
				PdfPCell c1 = new PdfPCell(new Phrase(headerCell, headerFont));  c1.setHorizontalAlignment(Element.ALIGN_CENTER); c1.setBackgroundColor(BaseColor.LIGHT_GRAY); table.addCell(c1);
			}	
			table.setHeaderRows(1);
	        
	        int counter = 1;
	        Font cellFont = new Font(baseFont, 8, Font.NORMAL);
	        for (KifKufDTO stavka : result) {
	        	PdfPCell rBroj = new PdfPCell(new Paragraph(String.valueOf(counter), cellFont)); rBroj.setHorizontalAlignment(Element.ALIGN_CENTER); table.addCell(rBroj);
	        	PdfPCell broj = new PdfPCell(new Paragraph(stavka.getBrojFakture(), cellFont)); table.addCell(broj);
	        	PdfPCell datum = new PdfPCell(new Paragraph(formatter.format(stavka.getDatum()), cellFont)); table.addCell(datum);
	        	PdfPCell komitent = new PdfPCell(new Paragraph(stavka.getKomitent(), cellFont)); table.addCell(komitent);
	        	PdfPCell ukupno = new PdfPCell(new Paragraph(stavka.getUkupno().toString(), cellFont)); ukupno.setHorizontalAlignment(Element.ALIGN_RIGHT); table.addCell(ukupno);
	        	PdfPCell iznos = new PdfPCell(new Paragraph(stavka.getIznos().toString(), cellFont)); iznos.setHorizontalAlignment(Element.ALIGN_RIGHT); table.addCell(iznos);
	        	PdfPCell pdv = new PdfPCell(new Paragraph(stavka.getPdv().toString(), cellFont)); pdv.setHorizontalAlignment(Element.ALIGN_RIGHT); table.addCell(pdv);
	        	counter++;
			}
	
			doc.add(table);
			doc.close();
		}
		// setting some response headers
		response.setHeader("Expires", "0");
		response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
		response.setHeader("Pragma", "public");
		response.setContentType("application/pdf; charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentLength(baos.size());
		response.addHeader("Content-Disposition", "attachment; filename=" + filename + ".pdf");

		// write ByteArrayOutputStream to the ServletOutputStream
		OutputStream os = response.getOutputStream();
		baos.writeTo(os);
		os.flush();
		os.close();
	}
	
	public void generateBilansPDF(HttpServletResponse response,Map<Integer, List<BilansResultBean>> result, String reportTitle, SearchBean searchBean, List<String> headers, String filename) throws DocumentException, IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		SimpleDateFormat formatter = new SimpleDateFormat(getDatumPattern());
		BaseFont baseFont = BaseFont.createFont(BaseFont.TIMES_ROMAN, BaseFont.CP1250, BaseFont.EMBEDDED);
		if (result != null && !result.isEmpty()) {
			Document doc = new Document();
			PdfWriter.getInstance(doc, baos);
			doc.open();
			
			Paragraph preduzece = new Paragraph(getUser().getPreduzece().getNaziv(), new Font(baseFont, 18, Font.BOLD));
			preduzece.setAlignment(Element.ALIGN_CENTER);
			doc.add(preduzece);
			
			Paragraph title = new Paragraph(reportTitle, new Font(baseFont, 12, Font.BOLD));
			title.setAlignment(Element.ALIGN_CENTER);
			doc.add(title);
			
			Paragraph kontoOdDo = new Paragraph("Konto: " + searchBean.getKontoOd().getSifra() + " - " + searchBean.getKontoDo().getSifra(), new Font(baseFont, 10, Font.BOLD));
			kontoOdDo.setAlignment(Element.ALIGN_CENTER);
			doc.add(kontoOdDo);
			
			Paragraph datumOdDo = new Paragraph("Datum: " + formatter.format(searchBean.getDatumOd()) + " - " + formatter.format(searchBean.getDatumDo()), new Font(baseFont, 10, Font.BOLD));
			datumOdDo.setAlignment(Element.ALIGN_CENTER);
			addEmptyLine(datumOdDo, 1);
			doc.add(datumOdDo);
	
			for (Integer key : result.keySet()) {
				// Creating a table object 
				PdfPTable table = new PdfPTable(5);
				table.setWidths(new float[] { 3f, 15f, 10f, 10f, 10f});
				
				Font headerFont = new Font(baseFont, 10, Font.BOLD);
				for (String headerCell : headers) {
					PdfPCell c1 = new PdfPCell(new Phrase(headerCell, headerFont));  c1.setHorizontalAlignment(Element.ALIGN_CENTER); c1.setBackgroundColor(BaseColor.LIGHT_GRAY); table.addCell(c1);
				}	
				table.setHeaderRows(1);
		        
		        int counter = 1;
		        Font cellFont = new Font(baseFont, 8, Font.NORMAL);
		        BigDecimal dugujeUkupno = BigDecimal.ZERO;
		        BigDecimal potrazujeUkupno = BigDecimal.ZERO;
		        for (BilansResultBean stavka : result.get(key)) {
		        	PdfPCell rBroj = new PdfPCell(new Paragraph(String.valueOf(counter), cellFont)); rBroj.setHorizontalAlignment(Element.ALIGN_CENTER); table.addCell(rBroj);
		        	PdfPCell konto = new PdfPCell(new Paragraph(stavka.getKonto().getSifraNaziv(), cellFont)); table.addCell(konto);
		        	PdfPCell duguje = new PdfPCell(new Paragraph(stavka.getDuguje().toString(), cellFont)); duguje.setHorizontalAlignment(Element.ALIGN_RIGHT); table.addCell(duguje);
		        	PdfPCell potrazuje = new PdfPCell(new Paragraph(stavka.getPotrazuje().toString(), cellFont)); potrazuje.setHorizontalAlignment(Element.ALIGN_RIGHT); table.addCell(potrazuje);
		        	PdfPCell saldo = new PdfPCell(new Paragraph(stavka.getSaldo().toString(), cellFont)); saldo.setHorizontalAlignment(Element.ALIGN_RIGHT); table.addCell(saldo);
		        	counter++;
		        	dugujeUkupno = dugujeUkupno.add(stavka.getDuguje());
		        	potrazujeUkupno = potrazujeUkupno.add(stavka.getPotrazuje());
				}
		        table.addCell("");
		        Font footerFont = new Font(baseFont, 8, Font.BOLD);
		        PdfPCell ukupno = new PdfPCell(new Paragraph("Ukupno", footerFont)); ukupno.setHorizontalAlignment(Element.ALIGN_CENTER);
		        table.addCell(ukupno);
		        PdfPCell dugujeUkupnoCell = new PdfPCell(new Paragraph(dugujeUkupno.toString(), footerFont)); dugujeUkupnoCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		        table.addCell(dugujeUkupnoCell);
		        PdfPCell potrazujeUkupnoCell = new PdfPCell(new Paragraph(potrazujeUkupno.toString(), footerFont)); potrazujeUkupnoCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		        table.addCell(potrazujeUkupnoCell);
		        PdfPCell saldoUkupnoCell = new PdfPCell(new Paragraph(dugujeUkupno.subtract(potrazujeUkupno).toString(), footerFont)); saldoUkupnoCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		        table.addCell(saldoUkupnoCell);
		        
		        Paragraph klasa = new Paragraph("Klasa " + key, new Font(baseFont, 10, Font.BOLD));
		        klasa.setAlignment(Element.ALIGN_CENTER); 
		        addEmptyLine(klasa, 1);
		        doc.add(klasa);
		        
		        doc.add(table);
			}
			doc.close();
		}
		// setting some response headers
		response.setHeader("Expires", "0");
		response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
		response.setHeader("Pragma", "public");
		response.setContentType("application/pdf; charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentLength(baos.size());
		response.addHeader("Content-Disposition", "attachment; filename=" + filename + ".pdf");

		// write ByteArrayOutputStream to the ServletOutputStream
		OutputStream os = response.getOutputStream();
		baos.writeTo(os);
		os.flush();
		os.close();
	}
	
	 private static void addEmptyLine(Paragraph paragraph, int number) {
		 for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }
	
	public long getTimeZoneOffset(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		long timeOffset = 0l;
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("timezoneoffset")) {
					timeOffset = Long.valueOf(cookie.getValue());
					break;
				}
			}
		}
		return timeOffset;
	}
}