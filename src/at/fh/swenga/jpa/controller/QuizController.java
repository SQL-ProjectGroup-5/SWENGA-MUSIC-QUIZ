package at.fh.swenga.jpa.controller;

import java.io.OutputStream;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fluttercode.datafactory.impl.DataFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import at.fh.swenga.jpa.dao.DocumentRepository;
import at.fh.swenga.jpa.dao.QuizRepository;
import at.fh.swenga.jpa.dao.SongRepository;
import at.fh.swenga.jpa.helper.ZXingHelper;
import at.fh.swenga.jpa.model.QuizModel;
import at.fh.swenga.jpa.model.SongModel;

@Controller
public class QuizController {
	@Autowired
	QuizRepository quizRepository;
	@Autowired
	SongRepository songRepository;
	@Autowired
	DocumentRepository documentRepository;
	@RequestMapping(value = { "/quizzes", "listquizzes" })
	public String index(Model model) {
		List<QuizModel> quizzes = quizRepository.findAll();
		model.addAttribute("quizzes", quizzes);
		return "indexQuiz";
	}
	
	@RequestMapping("/")
	public String showIndex(Model model) {
		return "index";
	}
	@RequestMapping("/fillquizzes")
	@Transactional
	public String fillData(Model model) {
		DataFactory df = new DataFactory();
		Calendar publishDate = Calendar.getInstance();
		publishDate.setTime(df.getDateBetween(df.getDate(2000, 1, 1), df.getDate(2019, 1, 1)));
		QuizModel quizModel = new QuizModel ("Test",1,publishDate);
		quizModel.setSongs(songRepository.findAll());
		quizRepository.save(quizModel);
		return "forward:listquizzes";
	}
	
	@RequestMapping(value = "/play", method = RequestMethod.GET)
	public String handlePlay(@RequestParam(value = "id") int id,Model model) {
		model.addAttribute("gameIndex", id);
		return "game";
	}
	
	@RequestMapping("/quizStatistics")
	public String showQStatistics(Model model) {
		return "quizmaster";
	}
	@RequestMapping("/quizAdmin")
	@Transactional
	public String showQuizAdmin(Model model) {
		List<SongModel> songs = songRepository.findAll();
		model.addAttribute("songs", songs);
		return "quiz";
	}

	
	@RequestMapping(value = "qrcode/{id}", method = RequestMethod.GET)
	public void qrcode(@PathVariable("id") String id, HttpServletResponse response,HttpServletRequest request) throws Exception {
		response.setContentType("image/png");
		OutputStream outputStream = response.getOutputStream();
		String reqSrc = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/quizzes/";
		outputStream.write(ZXingHelper.getQRCodeImage(reqSrc+id, 200, 200));
		outputStream.flush();
		outputStream.close();
	}
}
