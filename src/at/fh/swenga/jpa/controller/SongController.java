package at.fh.swenga.jpa.controller;

import java.io.OutputStream;
import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import at.fh.swenga.jpa.dao.DocumentRepository;
import at.fh.swenga.jpa.dao.SongRepository;
import at.fh.swenga.jpa.dao.UserDao;
import at.fh.swenga.jpa.dao.UserRoleDao;
import at.fh.swenga.jpa.model.DocumentModel;
import at.fh.swenga.jpa.model.SongModel;
import at.fh.swenga.jpa.model.User;

@Controller
public class SongController {
	
	@Autowired
	SongRepository songRepository;

	@Autowired
	DocumentRepository documentRepository;
	@Autowired
	UserRoleDao userRoleRepository;

	@Autowired
	UserDao userRepository;

	@RequestMapping("/songAdmin")
	@Transactional
	public String showSongAdmin(Model model, Principal principal) {
		User user = userRepository.findByUsername(principal.getName()).get(0);
		List<SongModel> songs = new ArrayList<SongModel>();
		if (user.getUserRoles().contains(userRoleRepository.getRole("ROLE_ADMIN"))) {
			songs = songRepository.findAllWithResults();
		} else {
			songs = songRepository.findByUserIdWithResults(user.getId());
		}
		model.addAttribute("songs", songs);
		return "songs";
	}

	@RequestMapping("/fillsongs")
	@Transactional
	public String fillData(Model model, Principal principal) {
		DataFactory df = new DataFactory();
		Calendar publishDate = Calendar.getInstance();

		String answers[][] = { {"Like a Rolling Stone","Bob Dylan"}, {"Imagine","Beatles"}, 
							{"I Cant Get No Satisfaction","Rolling Stones"}, {"What’s Going On","Marvin Gaye"},
							{"Respect"," Aretha Franklin"}, {"Good Vibrations","Beach Boys"}, 
							{"Johnny B. Goode"," Chuck Berry"}, {"Hey Jude","Beatles"}, {"Smells Like Teen Spirit","Nirvana"},
							{"Whatd I Say","Ray Charles"},{"My Generation","The Who"}};
		
		String wrongAnswers[] = {"Rise like a Phoenix","I want it that way","Spring time","Give up","Baby dont go","I love weed",
				"Scream","Drop it like its hot","Love is all around me","Down under"};
		
		String genre[] = {"Blues","Folk","Brutal Metal","Heavy Metal","Soft Metal","Love","Rock","Pop","Classic"};
		

		int number = df.getNumberBetween(0, answers.length);
		
		publishDate.setTime(df.getDateBetween(df.getDate(2000, 1, 1), df.getDate(2019, 1, 1)));
		SongModel songModel = new SongModel(answers[number][1], publishDate, answers[number][0]);
		songModel.setAnswer1(wrongAnswers[df.getNumberBetween(0, wrongAnswers.length)]);
		songModel.setAnswer2(wrongAnswers[df.getNumberBetween(0, wrongAnswers.length)]);
		songModel.setAnswer3(wrongAnswers[df.getNumberBetween(0, wrongAnswers.length)]);
		songModel.setGenre(genre[df.getNumberBetween(0, genre.length)]);
		songModel.setStartTime(2);
		songModel.setTimeToAnswer(35);
		User user = userRepository.findByUsername(principal.getName()).get(0);
		songModel.setUser(user);
		songRepository.save(songModel);

		return "forward:songAdmin";
	}

	@RequestMapping(value = "/addsong", method = RequestMethod.POST)
	@Transactional
	public String addSongPost(@RequestParam String interpret, @RequestParam String title,
			@RequestParam String publishDate, @RequestParam String answer1, @RequestParam String answer2,
			@RequestParam String answer3, @RequestParam String genre, @RequestParam String startTime,
			@RequestParam String timeToAnswer, Model model, Principal principal) throws ParseException {
		Calendar publishDateC = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
		try {
			Date date = sdf.parse(publishDate);
			publishDateC.setTime(date);
			SongModel songModel = new SongModel(interpret, publishDateC, title);
			songModel.setAnswer1(answer1);
			songModel.setAnswer2(answer2);
			songModel.setAnswer3(answer3);
			songModel.setAnswer3(genre);
			User user = userRepository.findByUsername(principal.getName()).get(0);
			songModel.setUser(user);
			songModel.setStartTime(Integer.parseInt(startTime));
			songModel.setTimeToAnswer(Integer.parseInt(timeToAnswer));
			
			model.addAttribute("message", "Added song: " + songModel.getTitle());
			songRepository.save(songModel);
		} catch (ParseException e) {
			model.addAttribute("errorMessage", "Invalid Input");
		}
		return "forward:songAdmin";
	}

	@RequestMapping(value = "/editsong", method = RequestMethod.GET)
	@Transactional
	public String getSongEdit(@RequestParam int id, Model model) {
		Optional<SongModel> songOpt = songRepository.findById(id);
		if (songOpt.isPresent()) {
			SongModel song = songOpt.get();
			model.addAttribute("preFilledSong", song);
		} else {
			model.addAttribute("errorMessage", "Couldn't find song " + id);
		}
		return "forward:songAdmin";
	}

	@RequestMapping(value = "/editsong", method = RequestMethod.POST)
	@Transactional
	public String postSongEdit(@Valid SongModel changedSong, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			String errorMessage = "";
			for (FieldError fieldError : bindingResult.getFieldErrors()) {
				errorMessage += fieldError.getField() + " is invalid: " + fieldError.getCode() + "<br>";
			}
			model.addAttribute("errorMessage", errorMessage);
			return "forward:songAdmin";
		}
		Optional<SongModel> songOpt = songRepository.findById(changedSong.getId());
		if (!songOpt.isPresent()) {

			model.addAttribute("errorMessage", "Song does not exist!<br>");

		} else {
			SongModel song = songOpt.get();
			song.setInterpret(changedSong.getInterpret());
			song.setPublishDate(changedSong.getPublishDate());
			song.setTitle(changedSong.getTitle());
			song.setAnswer1(changedSong.getAnswer1());
			song.setAnswer2(changedSong.getAnswer2());
			song.setAnswer3(changedSong.getAnswer3());
			song.setGenre(changedSong.getGenre());
			song.setStartTime(changedSong.getStartTime());
			song.setTimeToAnswer(changedSong.getTimeToAnswer());
			model.addAttribute("message", "Changed song " + changedSong.getId());
			songRepository.save(song);
		}
		return "forward:songAdmin";

	}

	@RequestMapping("/deletesong")
	public String deleteData(Model model, @RequestParam int id) {
		songRepository.deleteById(id);

		return "forward:songAdmin";
	}

	/**
	 * Display the upload form
	 * 
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
	@Transactional
	public String uploadDocument(Model model, @RequestParam("id") int songId,
			@RequestParam("myFile") MultipartFile file) {

		try {

			Optional<SongModel> songOpt = songRepository.findById(songId);
			if (!songOpt.isPresent())
				throw new IllegalArgumentException("No song with id " + songId);

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
		if (!docOpt.isPresent())
			throw new IllegalArgumentException("No document with id " + documentId);

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
