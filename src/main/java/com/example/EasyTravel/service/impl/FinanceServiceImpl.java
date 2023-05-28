package com.example.EasyTravel.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.EasyTravel.constants.RtnCode;
import com.example.EasyTravel.entity.Finance;
import com.example.EasyTravel.repository.FinanceDao;
import com.example.EasyTravel.service.ifs.FinanceService;
import com.example.EasyTravel.vo.FinanceResponse;

@Service
public class FinanceServiceImpl implements FinanceService {

	private List<String> titles = new ArrayList<>(Arrays.asList("vip", "vehicle_cost", "maintenance", "rent_income"));
	private List<String> details = new ArrayList<>(
			Arrays.asList("vip","bike", "scooter", "motorcycle", "heavy motorcycle", "sedan", "ven", "suv", "A", "B", "C"));

	@Autowired
	private FinanceDao financeDao;

	@Override
	public FinanceResponse addReport(String title, String detail, int price, LocalDate buildDate) {
		// 防止錯誤輸入
		if (!titles.contains(title) || !details.contains(detail) || price < 0 || buildDate == null) {
			return new FinanceResponse(RtnCode.INCORRECT.getMessage());
		}
		// 新增資料
		return financeDao.insertReport(title, detail, price, buildDate) == 0
				? new FinanceResponse(RtnCode.ALREADY_EXISTED.getMessage())
				: new FinanceResponse(RtnCode.SUCCESSFUL.getMessage());
	}

	@Override
	public FinanceResponse findTitle(String title, int month) {
		// 查詢資料
		List<Finance> resList = financeDao.searchTitle(title, month);
		return resList.size() == 0 ? new FinanceResponse(RtnCode.NOT_FOUND.getMessage())
				: new FinanceResponse(resList, RtnCode.SUCCESS.getMessage());
	}

	@Override
	public FinanceResponse findDetail(String detail, int month) {
		// 查詢資料
		List<Finance> resList = financeDao.searchDetail(detail, month);
		return resList.size() == 0 ? new FinanceResponse(RtnCode.NOT_FOUND.getMessage())
				: new FinanceResponse(resList, RtnCode.SUCCESS.getMessage());
	}

	@Override
	public FinanceResponse monthlyReport(int month) {
		// 查詢資料
		List<Finance> resList = financeDao.searchMonthData(month);
		// 回傳結果
		return resList.size() == 0 ? new FinanceResponse(RtnCode.NOT_FOUND.getMessage())
				: new FinanceResponse(organize(resList), RtnCode.SUCCESS.getMessage());
	}

	@Override
	public FinanceResponse monthlyRatio(int month) {
		// 查詢資料
		List<Finance> resList = financeDao.searchMonthData(month);
		// 回傳結果
		return resList.size() == 0 ? new FinanceResponse(RtnCode.NOT_FOUND.getMessage())
				: new FinanceResponse(organizeRatio(resList), RtnCode.SUCCESS.getMessage());
	}

	// 私有方法，結果整理
	private Map<String, Object> organize(List<Finance> list) {
		// 建立容器
		Map<String, Object> financeMap = new HashMap<>();
		Map<String, Double> vehicleCostMap = new HashMap<>();
		Map<String, Double> maintenanceMap = new HashMap<>();
		Map<String, Double> rentMap = new HashMap<>();
		// 分類處理
		for (Finance f : list) {
			switch (f.getTitle()) {
			case "vehicle_cost":
				vehicleCostMap.put(f.getDetail(), vehicleCostMap.getOrDefault(f.getDetail(), 0.0) + f.getPrice());
				break;
			case "maintenance":
				maintenanceMap.put(f.getDetail(), maintenanceMap.getOrDefault(f.getDetail(), 0.0) + f.getPrice());
				break;
			case "rent_income":
				rentMap.put(f.getDetail(), rentMap.getOrDefault(f.getDetail(), 0.0) + f.getPrice());
				break;
			default:
				financeMap.put(f.getTitle(), (double) financeMap.getOrDefault(f.getTitle(), 0.0) + f.getPrice());
			}
		}
		financeMap.put("vehicle_cost", vehicleCostMap);
		financeMap.put("maintenance", maintenanceMap);
		financeMap.put("rent_income", rentMap);
		// 回傳整理結果
		return financeMap;
	}

	// 私有方法，結果整理比例
	@SuppressWarnings("unchecked")
	private Map<String, Object> organizeRatio(List<Finance> list) {
		// 整理數據
		Map<String, Object> financeMap = organize(list);
		// 計算總額
		double totalIncome = 0, riTotalIncome = 0, vipIncome = 0;
		double totalExpense = 0, vcTotalExpense = 0, mTotalExpense = 0;
		for (Entry<String, Object> e : financeMap.entrySet()) {
			switch (e.getKey()) {
			case "vehicle_cost":
				for (Double vce : ((Map<String, Double>) e.getValue()).values()) {
					vcTotalExpense += vce;
				}
				break;
			case "maintenance":
				for (Double me : ((Map<String, Double>) e.getValue()).values()) {
					mTotalExpense += me;
				}
				break;
			case "rent_income":
				for (Double rie : ((Map<String, Double>) e.getValue()).values()) {
					riTotalIncome += rie;
				}
				break;
			default:
				vipIncome = (double) e.getValue();
			}
		}
		totalIncome = totalIncome + vipIncome + riTotalIncome;
		totalExpense = totalExpense + vcTotalExpense + mTotalExpense;
		// 轉換百分比
		for (Entry<String, Object> e : financeMap.entrySet()) {
			switch (e.getKey()) {
			case "vehicle_cost":
				Map<String, Double> vehicleCostMap = new HashMap<>(((Map<String, Double>) e.getValue()));
				for (Entry<String, Double> vce : vehicleCostMap.entrySet()) {
					vehicleCostMap.put(vce.getKey(), Math.round((vce.getValue() / vcTotalExpense * 1000.0)) / 10.0);
				}
				financeMap.put(e.getKey(), vehicleCostMap);
				break;
			case "maintenance":
				Map<String, Double> maintenanceMap = new HashMap<>(((Map<String, Double>) e.getValue()));
				for (Entry<String, Double> me : maintenanceMap.entrySet()) {
					maintenanceMap.put(me.getKey(), Math.round((me.getValue() / mTotalExpense * 1000.0)) / 10.0);
				}
				financeMap.put(e.getKey(), maintenanceMap);
				break;
			case "rent_income":
				Map<String, Double> rentMap = new HashMap<>(((Map<String, Double>) e.getValue()));
				for (Entry<String, Double> rie : rentMap.entrySet()) {
					rentMap.put(rie.getKey(), Math.round((rie.getValue() / riTotalIncome * 1000.0)) / 10.0);
				}
				financeMap.put(e.getKey(), rentMap);
				break;
			default:
				financeMap.put(e.getKey(), Math.round(((double) e.getValue() / totalIncome * 1000.0)) / 10.0);
			}
		}
		// 儲存百分比資訊
		financeMap.put("vehicle_cost_ratio", Math.round((vcTotalExpense / totalExpense * 1000.0)) / 10.0);
		financeMap.put("maintenance_ratio", Math.round((mTotalExpense / totalExpense * 1000.0)) / 10.0);
		financeMap.put("rent_income_ratio", Math.round((riTotalIncome / totalIncome * 1000.0)) / 10.0);
		financeMap.put("total_income",totalIncome);
		financeMap.put("total_expense",totalExpense);
		return financeMap;
	}

}
