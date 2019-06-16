package at.fh.swenga.jpa.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

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
		return "admin";
	}

	@RequestMapping("/fillresults")
	@Transactional
	public String fillData(Model model) {
		SongModel theSong = songRepository.findTopById(1);
		QuizModel theQuiz = quizRepository.findTopById(1);
		ResultModel resultModel = new ResultModel (theQuiz,theSong,"EinQuizUser",true,1.2f);
		resultRepository.save(resultModel);
		return "forward:listresults";
	}
}