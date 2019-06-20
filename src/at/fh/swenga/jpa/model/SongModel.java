package at.fh.swenga.jpa.model;

import java.util.Calendar;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Version;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "Song")
public class SongModel implements java.io.Serializable {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String interpret;
	@DateTimeFormat(pattern = "dd.MM.yyyy")
	private Calendar publishDate;
	private String Title;
	private String genre;
	private int startTime;
	private int timeToAnswer;
	@OneToOne(cascade = CascadeType.ALL)
	private DocumentModel document;
	@Version
	long version;
	@ManyToMany(mappedBy = "songs")
	private Collection<QuizModel> quizzes;

	@OneToMany(mappedBy = "song", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Collection<ResultModel> results;

	@ManyToOne(fetch = FetchType.LAZY)
	private User user;

	public SongModel(String interpret, Calendar publishDate, String title) {
		super();
		this.interpret = interpret;
		this.publishDate = publishDate;
		Title = title;
	}

	private String answer1;
	private String answer2;
	private String answer3;

	public String getAnswer1() {
		return answer1;
	}

	public void setAnswer1(String answer1) {
		this.answer1 = answer1;
	}

	public String getAnswer2() {
		return answer2;
	}

	public void setAnswer2(String answer2) {
		this.answer2 = answer2;
	}

	public String getAnswer3() {
		return answer3;
	}

	public void setAnswer3(String answer3) {
		this.answer3 = answer3;
	}

	public SongModel() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getInterpret() {
		return interpret;
	}

	public void setInterpret(String interpret) {
		this.interpret = interpret;
	}

	public Calendar getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(Calendar publishDate) {
		this.publishDate = publishDate;
	}

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}

	public DocumentModel getDocument() {
		return document;
	}

	public void setDocument(DocumentModel document) {
		this.document = document;
	}

	public Collection<ResultModel> getResults() {
		return results;
	}

	public void setResults(Collection<ResultModel> results) {
		this.results = results;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public int getStartTime() {
		return startTime;
	}

	public void setStartTime(int startTime) {
		this.startTime = startTime;
	}

	public int getTimeToAnswer() {
		return timeToAnswer;
	}

	public void setTimeToAnswer(int answerTime) {
		this.timeToAnswer = answerTime;
	}

	public Collection<QuizModel> getQuizzes() {
		return quizzes;
	}

	public void setQuizzes(Collection<QuizModel> quizzes) {
		this.quizzes = quizzes;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((Title == null) ? 0 : Title.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SongModel other = (SongModel) obj;
		if (Title == null) {
			if (other.Title != null)
				return false;
		} else if (!Title.equals(other.Title))
			return false;
		return true;
	}

}
