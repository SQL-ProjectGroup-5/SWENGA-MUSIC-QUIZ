package at.fh.swenga.jpa.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Results")
public class ResultModel implements java.io.Serializable {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String synonym;
	private boolean correct;
	private float time;
	@ManyToOne
	SongModel song;
	@ManyToOne
	QuizModel quiz;
	
	private String sessionID; 
	public ResultModel() {
		super();
	}

	public ResultModel(QuizModel quiz, SongModel song, String synonym, boolean correct, float time) {
		super();
		this.song = song;
		this.synonym = synonym;
		this.correct = correct;
		this.time = time;
		this.quiz = quiz;
	}
	public ResultModel(QuizModel quiz, SongModel song, String synonym, boolean correct, float time, String sessionID) {
		super();
		this.song = song;
		this.synonym = synonym;
		this.correct = correct;
		this.time = time;
		this.quiz = quiz;
		this.sessionID = sessionID;
	}
	
	public String getSessionID() {
		return sessionID;
	}

	public void setSessionID(String sessionID) {
		this.sessionID = sessionID;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSynonym() {
		return synonym;
	}

	public void setSynonym(String synonym) {
		this.synonym = synonym;
	}

	public boolean isCorrect() {
		return correct;
	}

	public void setCorrect(boolean correct) {
		this.correct = correct;
	}

	public float getTime() {
		return time;
	}

	public void setTime(float time) {
		this.time = time;
	}

	public SongModel getSong() {
		return song;
	}

	public void setSong(SongModel song) {
		this.song = song;
	}

	public QuizModel getQuiz() {
		return quiz;
	}

	public void setQuiz(QuizModel quiz) {
		this.quiz = quiz;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
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
		ResultModel other = (ResultModel) obj;
		if (id != other.id)
			return false;
		return true;
	}

}
