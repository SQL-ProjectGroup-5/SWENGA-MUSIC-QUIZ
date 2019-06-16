package at.fh.swenga.jpa.controller;

import java.io.OutputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.fluttercode.datafactory.impl.DataFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import at.fh.swenga.jpa.dao.DocumentRepository;
import at.fh.swenga.jpa.dao.SongRepository;
import at.fh.swenga.jpa.model.DocumentModel;
import at.fh.swenga.jpa.model.SongModel;
import at.fh.swenga.jpa.model.User;
import at.fh.swenga.jpa.model.UserRole;

@Controller
public class SongController {

	@Autowired
	SongRepository songRepository;

	@Autowired
	DocumentRepository documentRepository;
	/*
	@RequestMapping(value = {"/"})
	public String handleLogin() {
		return "login";
	}*/
	
	@RequestMapping("/songAdmin")
	@Transactional
	public String showSongAdmin(Model model) {
		List<SongModel> songs = songRepository.findAll();
		model.addAttribute("songs", songs);
		return "songs";
	}

	@RequestMapping("/fillsongs")
	@Transactional
	public String fillData(Model model) {
		DataFactory df = new DataFactory();
		Calendar publishDate = Calendar.getInstance();
		publishDate.setTime(df.getDateBetween(df.getDate(2000, 1, 1), df.getDate(2019, 1, 1)));
		SongModel songModel = new SongModel("TheBestCoderEver",publishDate, "A song Vol 27");
		songModel.setAnswer1("Super Song1");
		songModel.setAnswer2("Super Song2");
		songModel.setAnswer3("Super Song3");
		songRepository.save(songModel);
		return "forward:songAdmin";
	}
	
	@RequestMapping(value = "/addsong", method = RequestMethod.POST)
	@Transactional
	public String addSongPost(@RequestParam String interpret, @RequestParam String title,@RequestParam String date,@RequestParam String answer1,@RequestParam String answer2,@RequestParam String answer3, Model model) {
		DataFactory df = new DataFactory();
		Calendar publishDate = Calendar.getInstance();
		publishDate.setTime(df.getDateBetween(df.getDate(2000, 1, 1), df.getDate(2019, 1, 1)));
		SongModel songModel = new SongModel(interpret,publishDate, title);
		songModel.setAnswer1(answer1);
		songModel.setAnswer2(answer2);
		songModel.setAnswer3(answer3);
		songRepository.save(songModel);
		return "forward:songAdmin";
	}
	

	@RequestMapping("/delete")
	public String deleteData(Model model, @RequestParam int id) {
		songRepository.deleteById(id);

		return "forward:list";
	}


	
	
	/**
	 * Display the upload form
	 * @param model
	 * @param songId
	 * @return
	 */
	@RequestMapping(value = "/upload", method = RequestMethod.GET)
	public String showUploadForm(Model model, @RequestParam("id") int songId) {
		model.addAttribute("songId", songId);
		return "uploadFile";
	}
	
	
	/**
	 * Save uploaded file to the database (as 1:1 relationship to song)
	 * 
	 * @param model
	 * @param songId
	 * @param file
	 * @return
	 */
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public String uploadDocument(Model model, @RequestParam("id") int songId,
			@RequestParam("myFile") MultipartFile file) {
 
		try {
 
			Optional<SongModel> songOpt = songRepository.findById(songId);
			if (!songOpt.isPresent()) throw new IllegalArgumentException("No song with id "+songId);
 
			SongModel song = songOpt.get();
 
			// Already a document available -> delete it
			if (song.getDocument() != null) {
				documentRepository.delete(song.getDocument());
				// Don't forget to remove the relationship too
				song.setDocument(null);
			}
 
			// Create a new document and set all available infos
 
			DocumentModel document = new DocumentModel();
			document.setContent(file.getBytes());
			document.setContentType(file.getContentType());
			document.setCreated(new Date());
			document.setFilename(file.getOriginalFilename());
			document.setName(file.getName());
			song.setDocument(document);
			songRepository.save(song);
		} catch (Exception e) {
			model.addAttribute("errorMessage", "Error:" + e.getMessage());
		}
 
		return "forward:/songAdmin";
	}
 

	@RequestMapping("/download")
	public void download(@RequestParam("documentId") int documentId, HttpServletResponse response) {
 
		Optional<DocumentModel> docOpt = documentRepository.findById(documentId);
		if (!docOpt.isPresent()) throw new IllegalArgumentException("No document with id "+documentId);
 
		DocumentModel doc = docOpt.get();
 
		try {
			response.setHeader("Content-Disposition", "inline;filename=\"" + doc.getFilename() + "\"");
			OutputStream out = response.getOutputStream();
				// application/octet-stream
			response.setContentType(doc.getContentType());
			out.write(doc.getContent());
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
 

	@ExceptionHandler(Exception.class)
	public String handleAllException(Exception ex) {
		return "error";
	}
	
}
