package com.academy.fundamentals.moviesapp.Networking.DAO.Video;

import java.util.List;

public class VideosListResponse {
	private int id;
	private List<VideoModel> results;

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setResults(List<VideoModel> results){
		this.results = results;
	}

	public List<VideoModel> getResults(){
		return results;
	}

	@Override
 	public String toString(){
		return 
			"VideosListResponse{" +
			"id = '" + id + '\'' + 
			",results = '" + results + '\'' + 
			"}";
		}
}