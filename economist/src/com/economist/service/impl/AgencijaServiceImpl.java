package com.economist.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.economist.db.entity.Agencija;
import com.economist.db.entity.Preduzece;
import com.economist.db.repository.AgencijaRepository;
import com.economist.db.repository.PreduzeceRepository;
import com.economist.dto.AgencijaDTO;
import com.economist.dto.PreduzeceDTO;
import com.economist.service.AgencijaService;
import com.economist.service.PreduzeceService;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
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
import com.itextpdf.text.pdf.draw.VerticalPositionMark;

@Service
public class AgencijaServiceImpl implements AgencijaService {

	@Autowired
	private AgencijaRepository agencijaRepository;
	@Autowired
	private PreduzeceService preduzeceService;
	@Autowired
	private PreduzeceRepository preduzeceRepository;
	
	@Override
	public Agencija findOne(int id) {
		return agencijaRepository.findOne(id);
	}

	@Override
	public AgencijaDTO findOneDTO(int id) {
//		generareFaktura();
		Agencija agencija = agencijaRepository.findOne(id);
		if (agencija != null) {
			return new AgencijaDTO(agencija);
		}
		return null;
	}

	@Override
	public List<Agencija> findAll() {
		return agencijaRepository.findAll();
	}

	@Override
	public List<AgencijaDTO> findAllDTO() {
		return mapToDTO(agencijaRepository.findAll());
	}

	@Override
	public void save(AgencijaDTO agencijaDTO) {
		Agencija bean = new Agencija();
		if (agencijaDTO.getId() != null) {
			bean = agencijaRepository.findOne(agencijaDTO.getId());
		}
		bean.setNaziv(agencijaDTO.getNaziv());
		bean.setEmail(agencijaDTO.getEmail());
		
		agencijaRepository.save(bean);
	}
	
	private List<AgencijaDTO> mapToDTO(List<Agencija> agencijas) {
		if (agencijas != null) {
			List<AgencijaDTO> result = new ArrayList<AgencijaDTO>();
			for (Agencija agencija : agencijas) {
				result.add(new AgencijaDTO(agencija));
			}
			return result;
		}
		return null;
	}
	
	public void generareFaktura() {
		for (Agencija agencija: findAll()) {
			List<PreduzeceDTO> preduzeca = preduzeceService.findByAgencija(agencija);
			for (PreduzeceDTO preduzece: preduzeca) {
				if ((preduzece.getMjesecnaFaktura() != null && preduzece.getMjesecnaFaktura()) || Calendar.getInstance().get(Calendar.MONTH) == 0) {
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.YYYY.");
					FileOutputStream fos = null;
					try {
						BaseFont baseFont = BaseFont.createFont("C:\\Windows\\Fonts\\arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
						Chunk vertikalniRazmak = new Chunk(new VerticalPositionMark());
						
						Document doc = new Document();
						PdfWriter.getInstance(doc, baos);
						doc.open();
						Paragraph razmakNaPocetku = new Paragraph(" ");
						razmakNaPocetku.setSpacingAfter(20);
						doc.add(razmakNaPocetku);
						
						Paragraph nazivAgencije = new Paragraph(agencija.getNaziv(), new Font(baseFont, 40, Font.BOLD));
						nazivAgencije.setAlignment(Element.ALIGN_LEFT);
						nazivAgencije.add(new Chunk(vertikalniRazmak));
						nazivAgencije.setAlignment(Element.ALIGN_RIGHT);
						nazivAgencije.setFont(new Font(baseFont, 10, Font.NORMAL));
						nazivAgencije.add(agencija.getUlica());
						//nazivAgencije.setSpacingBefore(30);
						nazivAgencije.setSpacingAfter(7);
						doc.add(nazivAgencije);
						
						Paragraph tekst = new Paragraph("КЊИГОВОДСТВЕНА АГЕНЦИЈА", new Font(baseFont, 15, Font.NORMAL));
						tekst.setAlignment(Element.ALIGN_LEFT);
						tekst.add(new Chunk(vertikalniRazmak));
						tekst.setAlignment(Element.ALIGN_RIGHT);
						tekst.setFont(new Font(baseFont, 10, Font.NORMAL));
						tekst.add(agencija.getNaselje());
						doc.add(tekst);
						
						Paragraph telefon = new Paragraph("Телефон: " + agencija.getTelefon(), new Font(baseFont, 15, Font.NORMAL));
						telefon.setAlignment(Element.ALIGN_LEFT);
						telefon.add(new Chunk(vertikalniRazmak));
						telefon.setAlignment(Element.ALIGN_RIGHT);
						telefon.setFont(new Font(baseFont, 10, Font.NORMAL));
						telefon.add(agencija.getPostanskiBroj() + " " + agencija.getGrad());
						telefon.setSpacingAfter(10);
						doc.add(telefon);
						
						Paragraph zracun = new Paragraph("Жиро рачун - " + agencija.getZiroRacun(), new Font(baseFont, 15, Font.BOLD));
						zracun.setAlignment(Element.ALIGN_RIGHT);
						doc.add(zracun);
						
						Paragraph email = new Paragraph("E-пошта: " + agencija.getEmail(), new Font(baseFont, 15, Font.BOLD));
						email.setAlignment(Element.ALIGN_RIGHT);
						doc.add(email);
						
						Paragraph jib = new Paragraph("ЈИБ: " + agencija.getJib(), new Font(baseFont, 15, Font.BOLD));
						jib.setAlignment(Element.ALIGN_RIGHT);
						jib.setSpacingAfter(50);
						doc.add(jib);
						
						Paragraph racunBr = new Paragraph("Рачун бр: " + getBrojFakture(preduzece), new Font(baseFont, 20, Font.BOLD));
						racunBr.setAlignment(Element.ALIGN_RIGHT);
						racunBr.add(new Chunk(vertikalniRazmak));
						racunBr.setAlignment(Element.ALIGN_LEFT);
						racunBr.setFont(new Font(baseFont, 10, Font.NORMAL));
						racunBr.add(preduzece.getNaziv());
						doc.add(racunBr);
						
						Paragraph datumIzdavanja = new Paragraph("Датум издавања рачуна: " + formatter.format(new Date()), new Font(baseFont, 10, Font.NORMAL));
						datumIzdavanja.setAlignment(Element.ALIGN_RIGHT);
						datumIzdavanja.add(new Chunk(vertikalniRazmak));
						datumIzdavanja.setAlignment(Element.ALIGN_LEFT);
						datumIzdavanja.setFont(new Font(baseFont, 10, Font.NORMAL));
						datumIzdavanja.add(preduzece.getAdresa());
						doc.add(datumIzdavanja);
						
						Paragraph mjestoIzdavanja = new Paragraph("Мјесто издавања рачуна: " + agencija.getGrad(), new Font(baseFont, 10, Font.NORMAL));
						mjestoIzdavanja.setAlignment(Element.ALIGN_RIGHT);
						mjestoIzdavanja.add(new Chunk(vertikalniRazmak));
						mjestoIzdavanja.setAlignment(Element.ALIGN_LEFT);
						mjestoIzdavanja.setFont(new Font(baseFont, 10, Font.NORMAL));
						String jibString = preduzece.getJib() != null ? preduzece.getJib() : "";
						mjestoIzdavanja.add("ЈИБ: " + jibString);
						doc.add(mjestoIzdavanja);
	
						if (preduzece.getMjesecnaFaktura() != null && preduzece.getMjesecnaFaktura()) {
							Paragraph datumIsporuke = new Paragraph("Датум испоруке услуге: " + /*formatter.format(getStartDay()) +*/ "01-" + formatter.format(getEndDay()), new Font(baseFont, 10, Font.NORMAL));
							datumIsporuke.setAlignment(Element.ALIGN_LEFT);
							datumIsporuke.setSpacingAfter(100);
							doc.add(datumIsporuke);
						} else {
							int proslaGodina = Calendar.getInstance().get(Calendar.YEAR) - 1;
							Paragraph datumIsporuke = new Paragraph("Датум испоруке услуге: 01.01. - 31.12." + proslaGodina, new Font(baseFont, 10, Font.NORMAL));
							datumIsporuke.setAlignment(Element.ALIGN_LEFT);
							datumIsporuke.setSpacingAfter(100);
							doc.add(datumIsporuke);
						}
						
						// Creating a table object 
						PdfPTable table = new PdfPTable(5); // 5 columns.
						table.setWidths(new float[] { 5f, 15f, 8f, 8f, 8f});
						Font headerFont = new Font(baseFont, 10, Font.BOLD);
						PdfPCell c1 = new PdfPCell(new Phrase("Р.број", headerFont));  c1.setHorizontalAlignment(Element.ALIGN_CENTER); c1.setBackgroundColor(BaseColor.LIGHT_GRAY); table.addCell(c1);	
				        c1 = new PdfPCell(new Phrase("Врста услуга", headerFont)); c1.setHorizontalAlignment(Element.ALIGN_CENTER); c1.setBackgroundColor(BaseColor.LIGHT_GRAY); table.addCell(c1);
				        c1 = new PdfPCell(new Phrase("Број јединица", headerFont)); c1.setHorizontalAlignment(Element.ALIGN_CENTER); c1.setBackgroundColor(BaseColor.LIGHT_GRAY); table.addCell(c1);
				        c1 = new PdfPCell(new Phrase("Износ у КМ", headerFont)); c1.setHorizontalAlignment(Element.ALIGN_CENTER); c1.setBackgroundColor(BaseColor.LIGHT_GRAY); table.addCell(c1);
				        c1 = new PdfPCell(new Phrase("Износ у КМ", headerFont)); c1.setHorizontalAlignment(Element.ALIGN_CENTER); c1.setBackgroundColor(BaseColor.LIGHT_GRAY); table.addCell(c1);
				        table.setHeaderRows(1);
				        
				        for (int i = 0; i < 1; i++){
				        	PdfPCell c2 = new PdfPCell(new Phrase("1.", headerFont)); c2.setHorizontalAlignment(Element.ALIGN_CENTER); table.addCell(c2);
				        	c2 = new PdfPCell(new Phrase("Књиговодствене услуге", headerFont)); c2.setHorizontalAlignment(Element.ALIGN_CENTER); table.addCell(c2);
				        	c2 = new PdfPCell(new Phrase("1", headerFont)); c2.setHorizontalAlignment(Element.ALIGN_CENTER); table.addCell(c2);
				        	c2 = new PdfPCell(new Phrase(preduzece.getIznos().toString() + ",00", headerFont)); c2.setHorizontalAlignment(Element.ALIGN_CENTER); table.addCell(c2);
				        	c2 = new PdfPCell(new Phrase(preduzece.getIznos().toString() + ",00", headerFont)); c2.setHorizontalAlignment(Element.ALIGN_CENTER); table.addCell(c2);
				        	
				        	c2 = new PdfPCell(new Phrase("", headerFont)); c2.setHorizontalAlignment(Element.ALIGN_CENTER); table.addCell(c2);
				        	c2 = new PdfPCell(new Phrase("", headerFont)); c2.setHorizontalAlignment(Element.ALIGN_CENTER); table.addCell(c2);
				        	c2 = new PdfPCell(new Phrase("", headerFont)); c2.setHorizontalAlignment(Element.ALIGN_CENTER); table.addCell(c2);
				        	c2 = new PdfPCell(new Phrase("Укупно:", headerFont)); c2.setHorizontalAlignment(Element.ALIGN_CENTER); table.addCell(c2);
				        	c2 = new PdfPCell(new Phrase(preduzece.getIznos().toString() + ",00", headerFont)); c2.setHorizontalAlignment(Element.ALIGN_CENTER); table.addCell(c2);
				        }
						
				        table.setSpacingAfter(200);
				        doc.add(table);
				        
				        
				        Paragraph mp = new Paragraph("M.P.", new Font(baseFont, 10, Font.NORMAL));
						mp.setAlignment(Element.ALIGN_CENTER);
						mp.setSpacingAfter(20);
						doc.add(mp);
						
						Paragraph odgovornoLice = new Paragraph("Одговорно лице:", new Font(baseFont, 10, Font.NORMAL));
						odgovornoLice.setAlignment(Element.ALIGN_RIGHT);
						odgovornoLice.setSpacingAfter(5);
						doc.add(odgovornoLice);
						
						Paragraph linija = new Paragraph("_________________", new Font(baseFont, 10, Font.NORMAL));
						linija.setAlignment(Element.ALIGN_RIGHT);
						doc.add(linija);
						
						Paragraph odgovornoLiceIme = new Paragraph(agencija.getOdgovornoLice(), new Font(baseFont, 10, Font.NORMAL));
						odgovornoLiceIme.setAlignment(Element.ALIGN_RIGHT);
						doc.add(odgovornoLiceIme);
						
						doc.close();	
						
						String fileName = agencija.getId() + "-" + Calendar.getInstance().get(Calendar.YEAR) + "-" + Calendar.getInstance().get(Calendar.MONTH) + ".pdf";
						String filePath = "C:\\Users\\oroz\\" + fileName;
						fos = new FileOutputStream(new File(filePath));
						baos.writeTo(fos);
					}
					catch (DocumentException de) {
						de.printStackTrace();
					}
					catch (IOException e) {
						e.printStackTrace();
					}
					finally {
						try {
							if (fos != null) {
								fos.close();
							}
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						try {
							if (baos != null) {
								baos.close();
							}
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
		}
	}
	
	private Date getStartDay() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        //setTimeToBeginningOfDay(calendar);
        return calendar.getTime();
	}
	
	private Date getEndDay() {
		Calendar calendar = Calendar.getInstance();
        if (Calendar.getInstance().get(Calendar.MONTH) == 0) { //januar
    		calendar.set(Calendar.MONTH, 11);
    		calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) - 1);
            calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        } else {
        	calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1);
            calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        }
        //setTimeToBeginningOfDay(calendar);
        return calendar.getTime();
	}
	
	private String getBrojFakture(PreduzeceDTO preduzece) {
		String brojFakture = preduzece.getRedniBrojFakture() + "/" + preduzece.getGodinaFakture();
		Preduzece p = preduzeceRepository.findOne(preduzece.getId());
		if (p.getMjesecnaFaktura() != null && p.getMjesecnaFaktura()) {
			if (Calendar.getInstance().get(Calendar.MONTH) == 11) { //decembar
				p.setRedniBrojFakture(1);
				p.setGodinaFakture(Calendar.getInstance().get(Calendar.YEAR) + 1);
			} else {
				p.setRedniBrojFakture(preduzece.getRedniBrojFakture() + 1);
			}
		} else {
			p.setGodinaFakture(Calendar.getInstance().get(Calendar.YEAR) + 1);
		}
		preduzeceRepository.save(p);
		
		return brojFakture;
	}
}
