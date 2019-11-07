package com.gcu.model;

import org.hibernate.validator.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * Model class for a single song. This class cannot be interacted with directly without the context of an associated album
 */
public class SongModel 
{

    private int ID; //The ID with which this song is associated in the database
    private int albumID; //The album with which this song is associated
    @NotEmpty(message = "This is a required field")
    @Size(min = 1, max = 40, message = "Please make sure the name of the song is between 1 and 40 characters")
    private String name; //The name of this song
    @Size(max = 255, message = "Please limit the song's description to 255 characters")
    @NotEmpty(message = "This is a required field")
    @Size(min = 1, max = 40, message = "Please make sure the name of the artist is between 1 and 40 characters")
    private String artist; //The creator of this song

    public SongModel(){
        ID = 0;
        albumID = 0;
        name = "";
        artist="";
    }

    public SongModel(int ID, int albumID, String name, String artist) {
        this.ID = ID;
        this.albumID = albumID;
        this.name = name;
        this.artist=artist;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getAlbumID() {
        return albumID;
    }

    public void setAlbumID(int albumID) {
        this.albumID = albumID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

	public String getArtist() {
		return artist;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}
}
