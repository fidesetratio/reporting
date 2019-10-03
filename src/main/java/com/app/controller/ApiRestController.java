package com.app.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.model.City;
import com.app.util.PDFGenerator;
import com.itextpdf.text.DocumentException;

@RestController
@RequestMapping("/api/pdf")
public class ApiRestController {
	@GetMapping(value = "/customers",
            produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> customersReport() throws IOException {
     
		City city = new City();
		city.setId(new Long(1));
		city.setName("name");
		city.setPopulation(10);
		
		List<City> cities = new ArrayList<City>();
		cities.add(city);
		
		   List<InputStream> list = new ArrayList<InputStream>();
	    ByteArrayInputStream bis = PDFGenerator.generateReports(cities);
	   list.add(bis);
	   InputStream in = null;
	    try {
			in = PDFGenerator.createMessageWithPdf("patar");
		    list.add(in);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
		
		ByteArrayOutputStream baos1 = new ByteArrayOutputStream();
		
		
		try {
			PDFGenerator.doMerge(list, baos1);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		InputStream isFromFirstData = new ByteArrayInputStream(baos1.toByteArray()); 
		
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=customers.pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource( isFromFirstData));
    }
}
