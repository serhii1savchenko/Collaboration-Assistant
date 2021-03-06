package com.netcracker.ca.dao;

import java.util.List;

import com.netcracker.ca.model.User;

public interface CuratorDao {

	void add(int curatorId, int projectId, int teamId);
	
	void delete(int curatorId, int projectId);
	
	List<User> getByTeam(int teamId);
	
	List<User> getByProject(int projectId);
	
	List<User> getByMeeting(int meetingId);

	List<User> getFreeCurators();
	
}
