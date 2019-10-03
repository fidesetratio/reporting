package com.app.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import com.app.model.City;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;



public class PDFGenerator {
	 public static ByteArrayInputStream generateReports(List<City> cities){

			Document document = new Document();
			ByteArrayOutputStream out = new ByteArrayOutputStream();

			try {

				PdfPTable table = new PdfPTable(3);
				table.setWidthPercentage(60);
				table.setWidths(new int[] { 1, 3, 3 });

				Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);

				PdfPCell hcell;
				hcell = new PdfPCell(new Phrase("Id", headFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Name", headFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Population", headFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(hcell);

				for (City city : cities) {

					PdfPCell cell;

					cell = new PdfPCell(new Phrase(city.getId().toString()));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell);

					cell = new PdfPCell(new Phrase(city.getName()));
					cell.setPaddingLeft(5);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					table.addCell(cell);

					cell = new PdfPCell(new Phrase(String.valueOf(city.getPopulation())));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					cell.setPaddingRight(5);
					table.addCell(cell);
				}

				PdfWriter.getInstance(document, out);
				document.open();
				document.add(table);

				document.close();

			} catch (DocumentException ex) {
				ex.printStackTrace();
			}

			return new ByteArrayInputStream(out.toByteArray());
	        
	 }
	 
	 
	 public static void doMerge(List<InputStream> list, OutputStream outputStream)
	            throws DocumentException, IOException {
	        Document document = new Document();
	        PdfWriter writer = PdfWriter.getInstance(document, outputStream);
	        document.open();
	        PdfContentByte cb = writer.getDirectContent();	        
	        for (InputStream in : list) {
	            PdfReader reader = new PdfReader(in);
	            for (int i = 1; i <= reader.getNumberOfPages(); i++) {
	                document.newPage();
	                //import the page from source pdf
	                PdfImportedPage page = writer.getImportedPage(reader, i);
	                //add the page to the destination pdf
	                cb.addTemplate(page, 0, 0);
	            }
	        }
	        outputStream.flush();
	        document.close();
	        outputStream.close();
	    }
	 
	 
	 public static InputStream createMessageWithPdf(String message)
		 throws DocumentException, IOException {
		 	    Document document = new Document();
		 		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		            PdfWriter.getInstance(document, baos);
		        document.open();
		        document.add(new Paragraph("Hello World!"));
		        document.close();
				InputStream isFromFirstData = new ByteArrayInputStream(baos.toByteArray()); 
		        return isFromFirstData;
		        
	 }
}
