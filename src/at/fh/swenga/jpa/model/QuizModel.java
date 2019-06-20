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
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Quiz")
public class QuizModel implements java.io.Serializable {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String title;
	private int difficulty;
	private Calendar creationDate;

	@ManyToMany(fetch = FetchType.EAGER)
	private Collection<SongModel> songs;

	@OneToMany(mappedBy = "quiz", fetch = FetchType.LAZY)
	private Collection<Comment> comments;

	@OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL)
	private Collection<ResultModel> results;

	public QuizModel(String title, int difficulty, Calendar creationDate) {
		super();
		this.title = title;
		this.difficulty = difficulty;
		this.creationDate = creationDate;
	}

	public Collection<Comment> getComments() {
		return comments;
	}

	public void setComments(Collection<Comment> comments) {
		this.comments = comments;
	}

	public QuizModel() {
		super();
	}

	public int getId() {
		return id;
	}

	public Collection<ResultModel> getResults() {
		return results;
	}

	public void setResults(Collection<ResultModel> results) {
		this.results = results;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
	}

	public Calendar getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Calendar creationDate) {
		this.creationDate = creationDate;
	}

	public Collection<SongModel> getSongs() {
		return songs;
	}

	public void setSongs(Collection<SongModel> songs) {
		this.songs = songs;
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
		QuizModel other = (QuizModel) obj;
		if (id != other.id)
			return false;
		return true;
	}

}
