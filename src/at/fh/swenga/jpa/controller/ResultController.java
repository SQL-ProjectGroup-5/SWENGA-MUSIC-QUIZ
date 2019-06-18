package at.fh.swenga.jpa.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;

import at.fh.swenga.jpa.dao.QuizRepository;
import at.fh.swenga.jpa.dao.ResultRepository;
import at.fh.swenga.jpa.dao.SongRepository;
import at.fh.swenga.jpa.model.QuizModel;
import at.fh.swenga.jpa.model.ResultModel;
import at.fh.swenga.jpa.model.SongModel;

@Controller
public class ResultController {
	@Autowired
	QuizRepository quizRepository;
	@Autowired
	SongRepository songRepository;
	@Autowired
	ResultRepository resultRepository;

	@RequestMapping(value = { "/results", "listresults" })
	public String index(Model model) {
		List<ResultModel> results = resultRepository.findAll();
		model.addAttribute("results", results);
		return "indexResults";
	}

	@RequestMapping("/admin")
	@Transactional
	public String showAdmin(Model model) {
		List<SongModel> songs = songRepository.findAll();
		model.addAttribute("songs", songs);
		return "admin";
	}

	@RequestMapping(value = "/createResult", method = RequestMethod.POST)
	@Transactional
	public String handleResult(@RequestParam(value = "gid") int gid, @RequestParam(value = "nickname") String nickname,
			@RequestParam(value = "qid", required = false) int qid, @RequestParam(value = "result") String answer,
			HttpSession session, Model model) {
		String sessionID = RequestContextHolder.currentRequestAttributes().getSessionId();
		
		QuizModel quiz = quizRepository.findById(gid).get();
		List<SongModel> currSongs = new ArrayList<SongModel>(quiz.getSongs());
		SongModel currQuestion = currSongs.get(qid-1);
		model.addAttribute("gameIndex", gid);
		model.addAttribute("questionIndex", qid);
		model.addAttribute("nickname", nickname);
		if (answer.equals(currQuestion.getTitle())) {
			model.addAttribute("message", "Supa war richtig!");
			ResultModel currResult = new ResultModel(quiz, currQuestion, nickname, true, 1.2f,sessionID);
			resultRepository.save(currResult);
		} else {
			ResultModel currResult = new ResultModel(quiz, currQuestion, nickname, false, 1.2f,sessionID);
			resultRepository.save(currResult);
			model.addAttribute("errorMessage", "Faaalsch");
		}
		return "forward:/play";
	}

	@RequestMapping("/fillresults")
	@Transactional
	public String fillData(Model model) {
		SongModel theSong = songRepository.findTopById(1);
		QuizModel theQuiz = quizRepository.findTopById(1);
		ResultModel resultModel = new ResultModel(theQuiz, theSong, "EinQuizUser", true, 1.2f);
		resultRepository.save(resultModel);
		return "forward:listresults";
	}
}