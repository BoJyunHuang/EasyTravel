package com.example.EasyTravel.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.EasyTravel.entity.VehicleEntity;
import com.example.EasyTravel.repository.VehicleDao;
import com.example.EasyTravel.service.ifs.VehicleService;
import com.example.EasyTravel.vo.VehicleRequest;
import com.example.EasyTravel.vo.VehicleResponse;

@Service
public class VehicleServiceImpl implements VehicleService {

	@Autowired
	private VehicleDao vehicleDao;

	@Override
	public VehicleResponse addCar(VehicleRequest vehicleRequest) {
//		�P�_ > ���P���X
//		�O�_��J���P���X
		if (!StringUtils.hasText(vehicleRequest.getLicensePlate())) {
			return new VehicleResponse("error : �п�J���P���X");
		}
//		�����O�_�s�b
		Optional<VehicleEntity> op = vehicleDao.findById(vehicleRequest.getLicensePlate());
		if (op.isPresent()) {
			return new VehicleResponse("error : �������w�s�b");
		}
//		�P�_ > �������O & cc�����T���j�p
		if (!StringUtils.hasText(vehicleRequest.getCategory())) {
			return new VehicleResponse("error : �п�J�������O");
		}
		switch (vehicleRequest.getCategory()) {
		// bike > 0
		case "bike":
			if (vehicleRequest.getCc() != 0) {
				return new VehicleResponse("error : cc�Ƴ]�w���~");
			}
//			�p�G�S��break, �P�_false�|�@���i��U�@��case
			break;
		// scooter > 1~250
		case "scooter":
			if (vehicleRequest.getCc() < 1 || vehicleRequest.getCc() >= 250) {
				return new VehicleResponse("error : cc�Ƴ]�w���~");
			}
			break;
		// motorcycle > 251~550
		case "motorcycle":
			if (vehicleRequest.getCc() < 251 || vehicleRequest.getCc() >= 550) {
				return new VehicleResponse("error : cc�Ƴ]�w���~");
			}
			break;
		// heavy motorcycle > 550~
		case "heavy motorcycle":
			if (vehicleRequest.getCc() < 550) {
				return new VehicleResponse("error : cc�Ƴ]�w���~");
			}
			break;
		// sedan > �۩w�q(1200~4200)
		case "sedan":
			if (vehicleRequest.getCc() <= 1200 || vehicleRequest.getCc() >= 4200) {
				return new VehicleResponse("error : cc�Ƴ]�w���~");
			}
			break;
		// ven, SUV > �۩w�q(2000~6600)
		case "ven":
		case "suv":
			if (vehicleRequest.getCc() <= 2000 || vehicleRequest.getCc() >= 6600) {
				return new VehicleResponse("error : cc�Ƴ]�w���~");
			}
			break;
//			�Y���ŦX�H�Wcase, ���J���������O���~
		default:
			return new VehicleResponse("error : �п�J���Ĩ������O");
		}

//		�_�l�A�Ф�
		vehicleRequest.setStartServingDate(LocalDate.now());
//		�̷s�ˬd��(=�_�l�A�Ф�)
		vehicleRequest.setLatestCheckDate(LocalDate.now());
//		�i���ɪ��A
		vehicleRequest.setAvailable(false);
//		��l�`���{=0
		vehicleRequest.setOdo(0);
//		����
		if (vehicleRequest.getPrice() < 1) {
			return new VehicleResponse("error : ����]�w���~");
		}

		VehicleEntity saveVehicle = new VehicleEntity(vehicleRequest.getLicensePlate(), vehicleRequest.getCategory(),
				vehicleRequest.getCc(), vehicleRequest.getStartServingDate(), vehicleRequest.getLatestCheckDate(),
				vehicleRequest.isAvailable(), vehicleRequest.getOdo(), vehicleRequest.getPrice());
		vehicleDao.save(saveVehicle);
		return new VehicleResponse(saveVehicle, "���\ : �s�W�������\");
	}

	@Override
	public VehicleResponse updateCarInfo(VehicleRequest vehicleRequest) {
//		�P�_ > ���P���X
//		�O�_��J���P���X
		if (!StringUtils.hasText(vehicleRequest.getLicensePlate())) {
			return new VehicleResponse("error : �п�J���P���X");
		}
//		�����O�_�s�b
		Optional<VehicleEntity> op = vehicleDao.findById(vehicleRequest.getLicensePlate());
		if (!op.isPresent()) {
			return new VehicleResponse("error : ���������s�b");
		}

//		�[�`�s�`���{��
		if(vehicleRequest.getOdo() < 0) {
			return new VehicleResponse("error : �`���{�ƿ��~");
		}
		double oldOdo = op.get().getOdo();
		double newOdo = 0.0;
		newOdo = oldOdo + vehicleRequest.getOdo();
		
//		�]�w�svehicle��op�����F�� > �s�i�ϥΪ��A&�s�`���{�ƻ\�L�¸�� > �s�i��Ʈw
		VehicleEntity updateVehicle = op.get();
		updateVehicle.updateVehicleEntity(vehicleRequest.isAvailable(), newOdo);
		vehicleDao.save(updateVehicle);
		return new VehicleResponse(updateVehicle, "�ק��Ʀ��\");
	}

	@Override
	public VehicleResponse maintenanceCar(VehicleRequest vehicleRequest) {
//		�P�_ > ���P���X
//		�O�_��J���P���X
		if (!StringUtils.hasText(vehicleRequest.getLicensePlate())) {
			return new VehicleResponse("error : �п�J���P���X");
		}
//		�����O�_�s�b
		Optional<VehicleEntity> op = vehicleDao.findById(vehicleRequest.getLicensePlate());
		if (!op.isPresent()) {
			return new VehicleResponse("error : ���������s�b");
		}
		
//		���˭ר������i���Ϊ��A > �]�w��false
		
		return null;
	}

//	�}��7�~�B����10�~��12W�����B�T��15�~��60W����
	@Override
	public VehicleResponse scrapCar(VehicleRequest vehicleRequest) {
//		�P�_ > ���P���X
//		�O�_��J���P���X
		if (!StringUtils.hasText(vehicleRequest.getLicensePlate())) {
			return new VehicleResponse("error : �п�J���P���X");
		}
//		�����O�_�s�b
		Optional<VehicleEntity> op = vehicleDao.findById(vehicleRequest.getLicensePlate());
		if (!op.isPresent()) {
			return new VehicleResponse("error : ���������s�b");
		}
		
		LocalDate today = LocalDate.now();
		LocalDate startServingDate = op.get().getStartServingDate();
//		�P�_����
		switch(op.get().getCategory()) {
		case "bike":
			if(today.getYear() - startServingDate.getYear() >= 7) {
//				�i���Ϊ��A��false
				op.get().setAvailable(false);
			}else {
				return new VehicleResponse("�������|�L�����o");
			}
			break;
		case "scooter":
		case "motorcycle":
		case "heavy motorcycle":
			if(today.getYear() - startServingDate.getYear() >= 10 || op.get().getOdo() >= 120_000 ) {
				op.get().setAvailable(false);
			}else {
				return new VehicleResponse("�������|�L�����o");
			}
			break;
		case "sedan":
		case "ven":
		case "suv":
			if(today.getYear() - startServingDate.getYear() >= 15 || op.get().getOdo() >= 600_000 ) {
				op.get().setAvailable(false);
			}else {
				return new VehicleResponse("�������|�L�����o");
			}
			break;
		default:
			return new VehicleResponse("error : �п�J���Ĩ������O");
		}
		VehicleEntity scrapCar = op.get();
		vehicleDao.save(scrapCar);
		return new VehicleResponse(scrapCar, "�w���o������");
	}

	
	@Override
	public VehicleResponse findCarByCategory(VehicleRequest vehicleRequest) {
		if(!StringUtils.hasText(vehicleRequest.getCategory())) {
			return new VehicleResponse("�п�J���d�ߨ���");
		}
		List<VehicleEntity> carList = new ArrayList<VehicleEntity>();
		List<String> category = Arrays.asList("bike", "scooter", "motorcycle", "heavy motorcycle",
				"sedan", "ven", "suv");
		if(!category.contains(vehicleRequest.getCategory())) {
			return new VehicleResponse("�L������");
		}
		carList.addAll(vehicleDao.findAllByCategoryOrderByAvailableDesc(vehicleRequest.getCategory()));
		return new VehicleResponse(carList, "success");
	}

}
