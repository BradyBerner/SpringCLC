package com.gcu.model;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

public class AlbumModel 
{
	private int ID; //The ID of this Album, primarily used in conjunction with the database
    private int userID; // The ID of the user with which this ID is associated 
    @NotEmpty(message = "This is a required field")
    @Size(min = 1, max = 40, message = "Please make sure the name of the album is between 1 and 40 characters")
    private String name; //The name of this album
    @Size(min = 1, max = 40, message = "Please make sure the name of the artist is between 1 and 40 characters")
    private String artist; //The creator of this album
    @Size(max = 255, message = "Please limit the album's description to 255 characters")
    private String description; // A description of this album
    @Size(max = 14, message = "Please limit the album's genre to 14 characters")
    private String genre; //The genre of this album
    private List<SongModel> tracks; //The tracks associated with this album

    public AlbumModel(){
        ID = 0;
        userID = 0;
        name = "";
        artist="";
        description = "";
        genre = "";
        tracks= new ArrayList<SongModel>();
    }

    public AlbumModel(int ID, int albumID, String name, String artist, String description, String genre, List<SongModel> tracks) {
        this.ID = ID;
        this.userID = albumID;
        this.name = name;
        this.artist=artist;
        this.description = description;
        this.genre = genre;
        this.tracks= tracks;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre){
        this.genre = genre;
    }

	public String getArtist() {
		return artist;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	public List<SongModel> getTracks() {
		return tracks;
	}

	public void setTracks(List<SongModel> tracks) {
		this.tracks = tracks;
	}
}
