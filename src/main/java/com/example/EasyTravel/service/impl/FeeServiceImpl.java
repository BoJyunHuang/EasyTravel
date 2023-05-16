package com.example.EasyTravel.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.EasyTravel.constants.RtnCode;
import com.example.EasyTravel.entity.Fee;
import com.example.EasyTravel.repository.FeeDao;
import com.example.EasyTravel.service.ifs.FeeService;
import com.example.EasyTravel.vo.FeeResponse;

@Service
public class FeeServiceImpl implements FeeService {

	@Autowired
	private FeeDao feeDao;

	@Override
	public FeeResponse addProject(String project, int cc, double rate, int threshold) {
		// 防止輸入空白或錯誤
		if (!StringUtils.hasText(project) || cc < 0 || rate < 0 || threshold < 0) {
			return new FeeResponse(RtnCode.CANNOT_EMPTY.getMessage());
		}
		// 新增費率
		return feeDao.insertProject(project, cc, rate, threshold) == 1 ? new FeeResponse(RtnCode.SUCCESSFUL.getMessage())
				: new FeeResponse(RtnCode.ALREADY_EXISTED.getMessage());
	}

	@Override
	public FeeResponse reviseProject(String project, int cc, double rate, int threshold) {
		// 防止輸入為空
		if (!StringUtils.hasText(project) || cc < 0) {
			return new FeeResponse(RtnCode.CANNOT_EMPTY.getMessage());
		}
		int res1 = 0, res2 = 0;
		// 修改費率
		if (rate > 0) {
			res1 = feeDao.updateRate(project, cc, rate);
		}
		// 修改時間區間
		if (threshold > 0) {
			res2 = feeDao.updateThreshold(project, cc, threshold);
		}
		return (res1 == 0 && res2 == 0) ? new FeeResponse(RtnCode.INCORRECT.getMessage())
				: new FeeResponse(RtnCode.SUCCESSFUL.getMessage());
	}

	@Override
	public FeeResponse deleteProject(String project, int cc, double rate) {
		// 刪除費率
		return feeDao.deleteProject(project, cc, rate) == 0 ? new FeeResponse(RtnCode.INCORRECT.getMessage())
				: new FeeResponse(RtnCode.SUCCESSFUL.getMessage());
	}

	@Override
	public FeeResponse findProject(String project) {
		// 查找資料
		List<Fee> resList = feeDao.findByProjectOrderByThresholdDesc(StringUtils.hasText(project) ? project : "");
		return resList == null ? new FeeResponse(RtnCode.NOT_FOUND.getMessage())
				: new FeeResponse(resList, RtnCode.SUCCESSFUL.getMessage());
	}

	@Override
	public FeeResponse calculate(String project, int threshold) {
		// 防止空輸入
		if (StringUtils.hasText(project) || threshold < 0) {
			return new FeeResponse(RtnCode.CANNOT_EMPTY.getMessage());
		}
		// 尋找方案
		FeeResponse resList = findProject(project);
		if (resList.getMessage().equals(RtnCode.NOT_FOUND.getMessage())) {
			return resList;
		}
		// 計算價錢
		int total = 0;
		for (Fee f : resList.getFeeList()) {
			int resdualTime = 0;
			if (threshold < f.getThreshold()) {
				continue;
			} else if (threshold > f.getThreshold()) {
				resdualTime = threshold - f.getThreshold();
				total += f.getRate() * resdualTime;
				// TODO
			} else {
				total += f.getRate() * f.getThreshold();
			}
		}
		return new FeeResponse(total, RtnCode.SUCCESS.getMessage());
	}

}
