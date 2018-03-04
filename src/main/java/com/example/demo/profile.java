package com.example.demo;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

@Controller
public class profile 
{
	@GetMapping(value="/")
	public ModelAndView renderPage()
	{
		ModelAndView upload = new ModelAndView();
		
		upload.setViewName("upload");
		
		
		return upload;
	}
	
	@PostMapping(value="/upload")
	public ModelAndView uploadS3(@RequestParam("file") MultipartFile image) throws IOException
	{
		
		ModelAndView upload = new ModelAndView();
		BasicAWSCredentials cred = new BasicAWSCredentials("AKIAIOOUPUME2UCHM2BQ","pCqA+eSZRsMDDgFhBUFoCeVgFRTHOggpkVPgMuJR");
		AmazonS3 s3client = AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(cred))
				.withRegion(Regions.US_EAST_2)
				.build();
		
		PutObjectRequest req = new PutObjectRequest("meribuckethaibc",image.getOriginalFilename(), image.getInputStream(), 
				new ObjectMetadata())
				.withCannedAcl(CannedAccessControlList.PublicRead);
			
		String Src_Img = "http://" + "meribuckethaibc" + ".s3.amazonaws.com/" + image.getOriginalFilename();
		s3client.putObject(req);
		upload.addObject("Imgsrc",Src_Img);
		upload.setViewName("display");
		
		return upload;
	}
	
	
	
	
}
