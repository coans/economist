package com.economist.controller;

import java.beans.PropertyEditorSupport;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.economist.config.BaseController;
import com.economist.db.entity.Komitent;
import com.economist.db.entity.Konto;
import com.economist.dto.KomitentDTO;
import com.economist.dto.KontoDTO;
import com.economist.dto.StavkaNalogaDTO;
import com.economist.model.SearchBean;
import com.economist.service.KomitentService;
import com.economist.service.KontoService;
import com.economist.service.StavkaNalogaService;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;


@Controller
@RequestMapping(AnalitikaController.CONTROLLER)
public class AnalitikaController extends BaseController {
	
	final static Logger logger = Logger.getLogger(AnalitikaController.class);
	
	public static final String CONTROLLER = "api/analitika";
	public static final String VIEW_DEFAULT = "analitika";
	
	@Autowired
	private KontoService kontoService;
	@Autowired
	private KomitentService komitentService;
	@Autowired
	private StavkaNalogaService stavkaNalogaService;
	@Autowired
	private MessageSource messageSource;
	
	@RequestMapping(method = RequestMethod.GET)
	public String defaultView(ModelMap model, HttpServletRequest request, HttpSession session, Locale locale) {
		model.addAttribute("search", new SearchBean());
		model.addAttribute("action", CONTROLLER + "/generate");
		model.addAttribute("konta", kontoService.findAnalitickaKonta(getUser().getAgencija()));
		List<KomitentDTO> komitents = komitentService.findByAgencija(getUser().getAgencija());
		komitents.add(0, new KomitentDTO());
		model.addAttribute("komitents", komitents);
		return VIEW_DEFAULT;
	}
	
	@RequestMapping(value = "generate", method = RequestMethod.POST)
	public String generate(@ModelAttribute("search") SearchBean search, Errors errors, ModelMap model, HttpServletRequest request, HttpServletResponse response) throws DocumentException, IOException {
		
			Konto kontoOd = kontoService.find(search.getKontoOd().getId());
			Konto kontoDo = kontoService.find(search.getKontoDo().getId());
			List<StavkaNalogaDTO> result = null;
			if (search.getKomitent() != null && search.getKomitent().getId() != null) {
				Komitent komitent = komitentService.find(search.getKomitent().getId());
				result = stavkaNalogaService.analitika(kontoOd.getSifra(), kontoDo.getSifra(), search.getDatumOd(), search.getDatumDo(), getUser().getPreduzece(), komitent);
			} else {
				result = stavkaNalogaService.analitika(kontoOd.getSifra(), kontoDo.getSifra(), search.getDatumOd(), search.getDatumDo(), getUser().getPreduzece());
			}
		if (request.getParameter("pretraga") != null) {	
			model.addAttribute("search", search);
			model.addAttribute("action", CONTROLLER + "/generate");
			model.addAttribute("konta", kontoService.findAnalitickaKonta(getUser().getAgencija()));
			List<KomitentDTO> komitents = komitentService.findByAgencija(getUser().getAgencija());
			komitents.add(0, new KomitentDTO());
			model.addAttribute("komitents", komitents);
			model.addAttribute("stavkes", result);
			setZbirniRed(result, model);
			
			return VIEW_DEFAULT;
		} else {
			//generate pdf
//			Document document = new Document();
//			PdfWriter.getInstance(document, response.getOutputStream()/*new FileOutputStream("iTextHelloWorld.pdf")*/);
//			 
//			document.open();
//			Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
//			Chunk chunk = new Chunk("Hello World", font);
//			 
//			document.add(chunk);
//			document.close();
//			response.addHeader("Content-Disposition", "attachment; filename=test.pdf");
			generatePDF(request, response, result, messageSource.getMessage("analitika.header", null, request.getLocale()));
			return ""; //TODO problem pravi ova povratna vrijednost
		}
		
		
	}

	private void generatePDF(HttpServletRequest request,
			HttpServletResponse response, List<StavkaNalogaDTO> stavkas, String reportTitle) throws DocumentException, IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		Document doc = new Document();
		PdfWriter.getInstance(doc, baos/*new FileOutputStream("iTextHelloWorld.pdf")*/);
		doc.open();
		Paragraph preduzece = new Paragraph(/*stavkas.get(0).getNalog().getPreduzece().getNaziv())*/"Preduzece 1", new Font(Font.FontFamily.TIMES_ROMAN, 18,
	            Font.BOLD));
		preduzece.setAlignment(Element.ALIGN_CENTER);
		doc.add(preduzece);
		addEmptyLine(preduzece, 2);
		
		Paragraph title = new Paragraph(reportTitle, new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD));
		title.setAlignment(Element.ALIGN_CENTER);
		doc.add(title);
		addEmptyLine(title, 2);

		// Creating a table object 
		PdfPTable table = new PdfPTable(3); // 3 columns.

        PdfPCell cell1 = new PdfPCell(new Paragraph("Cell 1"));
        PdfPCell cell2 = new PdfPCell(new Paragraph("Cell 2"));
        PdfPCell cell3 = new PdfPCell(new Paragraph("Cell 3"));

        table.addCell(cell1);
        table.addCell(cell2);
        table.addCell(cell3);
		doc.add(table);
		doc.close();

		// setting some response headers
		response.setHeader("Expires", "0");
		response.setHeader("Cache-Control",
		        "must-revalidate, post-check=0, pre-check=0");
		response.setHeader("Pragma", "public");
		// setting the content type
		response.setContentType("application/pdf");
		// the contentlength
		response.setContentLength(baos.size());
		response.addHeader("Content-Disposition", "attachment; filename=test.pdf");
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

	@Override
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		super.initBinder(binder);
		binder.registerCustomEditor(KontoDTO.class, new PropertyEditorSupport() {
			@Override
			public void setAsText(String id) {
				KontoDTO konto = new KontoDTO();
				konto.setId(Integer.parseInt(id));
				setValue(konto);
			}
		});
		
		binder.registerCustomEditor(KomitentDTO.class, new PropertyEditorSupport() {
			@Override
			public void setAsText(String id) {
				if (!id.isEmpty()) {
					KomitentDTO komitent = new KomitentDTO();
					komitent.setId(Integer.parseInt(id));
					setValue(komitent);
				}
			}
		});
	}
}
