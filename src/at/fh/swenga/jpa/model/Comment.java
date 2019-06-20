package at.fh.swenga.jpa.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity
@Table(name = "Comment")
public class Comment implements java.io.Serializable
{
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String comment;
	private int rating;
	@Version
	long version;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private SongModel song;
	
	public Comment()
	{
		super();
	}
	public Comment(String comment, int rating)
	{
		super();
		this.comment = comment;
		this.rating = rating;
	}
	
	public String getComment()
	{
		return comment;
	}
	public void setComment(String comment)
	{
		this.comment = comment;
	}
	public int getRating()
	{
		return rating;
	}
	public void setRating(int rating)
	{
		this.rating = rating;
	}
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((comment == null) ? 0 : comment.hashCode());
		result = prime * result + id;
		result = prime * result + rating;
		result = prime * result + ((song == null) ? 0 : song.hashCode());
		result = prime * result + (int) (version ^ (version >>> 32));
		return result;
	}
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Comment other = (Comment) obj;
		if (comment == null)
		{
			if (other.comment != null)
				return false;
		} else if (!comment.equals(other.comment))
			return false;
		if (id != other.id)
			return false;
		if (rating != other.rating)
			return false;
		if (song == null)
		{
			if (other.song != null)
				return false;
		} else if (!song.equals(other.song))
			return false;
		if (version != other.version)
			return false;
		return true;
	}
	
	
	

}
