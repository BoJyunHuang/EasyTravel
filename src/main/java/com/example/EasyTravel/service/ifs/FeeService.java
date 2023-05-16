package com.example.EasyTravel.service.ifs;

import com.example.EasyTravel.vo.FeeResponse;

public interface FeeService {

	public FeeResponse addProject(String project, int cc, double rate, int interval);

	public FeeResponse reviseProject(String project, int cc, double rate, int interval);

	public FeeResponse deleteProject(String project, int cc, double rate);

	public FeeResponse findProject(String project);

	public FeeResponse calculate(String project, int interval);
}
