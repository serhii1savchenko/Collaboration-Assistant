package com.netcracker.ca.model;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

public class Project {

	private int id;
	private String title;
	private String description;
	private Date startDate;
	private Date endDate;
	private University university;
	private List<Team> teams;

	public Project() {
	}

	public Project(int id) {
		this.id = id;
	}

	public Project(int id, String title, String description, Date startDate, Date endDate,
			University university) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.startDate = startDate;
		this.endDate = endDate;
		this.university = university;
	}

	public int getId() {
		return id;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public University getUniversity() {
		return university;
	}

	public void setUniversity(University university) {
		this.university = university;
	}
	
	public List<Team> getTeams() {
		return teams;
	}

	public void setTeams(List<Team> teams) {
		this.teams = teams;
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
		Project other = (Project) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder("Project");
		return builder.append(" [id=").append(id).append(", title=").append(title).append(", description=")
				.append(description).append(", startDate=").append(startDate).append(", endDate=").append(endDate)
				.append(", university.id=").append(university != null ? university.getId() : 0).append("]").toString();
	}

}