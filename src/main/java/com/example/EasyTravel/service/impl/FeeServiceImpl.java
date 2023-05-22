package com.example.EasyTravel.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
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
		return feeDao.insertProject(project, cc, rate, threshold) == 1
				? new FeeResponse(RtnCode.SUCCESSFUL.getMessage())
				: new FeeResponse(RtnCode.ALREADY_EXISTED.getMessage());
	}

	@Override
	public FeeResponse deleteProject(String project, int cc, double rate) {
		// 刪除費率
		int flag = (rate < 0 && cc < 0) ? feeDao.deleteProjects(project)
				: (cc > 0) ? feeDao.deleteProjects(project, cc) : feeDao.deleteProject(project, cc, rate);
		return flag == 0 ? new FeeResponse(RtnCode.INCORRECT.getMessage())
				: new FeeResponse(RtnCode.SUCCESSFUL.getMessage());
	}

	@Override
	public FeeResponse findProjects(String project, int cc) {
		// 查找資料
		List<Fee> resList = !StringUtils.hasText(project) ? null
				: (cc < 0 ? feeDao.searchProjectsByCcUp(project) : feeDao.searchProjectsByThresholdUp(project, cc));
		return resList == null ? new FeeResponse(RtnCode.NOT_FOUND.getMessage())
				: new FeeResponse(resList, RtnCode.SUCCESS.getMessage());
	}

	@Override
	public FeeResponse calculate(String project, int cc, int period) {
		// 防止空輸入
		if (period <= 0) {
			return new FeeResponse(RtnCode.CANNOT_EMPTY.getMessage());
		}
		// 尋找方案
		List<Fee> resList = feeDao.searchProjectsByThresholdUp(StringUtils.hasText(project) ? project : null,
				cc < 0 ? 0 : cc);
		if (CollectionUtils.isEmpty(resList)) {
			return new FeeResponse(RtnCode.NOT_FOUND.getMessage());
		}
		// 計算價錢
		double total = 0;
		double lastRate = 0;
		int lastThreshold = 0;
		for (int i = 0; i < resList.size(); i++) {
			// 超出時間區域，計算區間費率
			if (period >= resList.get(i).getThreshold()) {
				total += (project.equals("bike"))
						? resList.get(i).getRate() * (resList.get(i).getThreshold() - lastThreshold)
						: 0;
				lastRate = resList.get(i).getRate();
				lastThreshold = resList.get(i).getThreshold();
				// 在時間區域間，需用前次時間閾值進行計算
			} else {
				total += (project.equals("bike")) ? resList.get(i).getRate() * (period - lastThreshold)
						: lastRate * period;
				break;
			}
		}
		// 回傳
		return new FeeResponse((int) Math.round(total), RtnCode.SUCCESS.getMessage());
	}

}
