import 'dart:async';

import 'package:dio/dio.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:flutter_training/data/repositories/bnd-repository/bnd_repository.dart';
import 'package:flutter_training/models/create-birth-registration/child_address_details.dart';
import 'package:flutter_training/models/create-birth-registration/child_details.dart';
import 'package:flutter_training/models/create-birth-registration/child_father_details.dart';
import 'package:flutter_training/models/create-birth-registration/child_mother_details.dart';
import 'package:flutter_training/models/create-birth-registration/create_birth_response.dart';
import 'package:flutter_training/services/urls.dart';
import 'package:flutter_training/utils/global_variables.dart';
import 'package:flutter_training/utils/localization_constants/i18_key_constants.dart'
    as i18;
import 'package:freezed_annotation/freezed_annotation.dart';

import '../../data/remote_client.dart';
import '../../models/create-birth-registration/birth_certificates_model.dart';

part 'create_birth_bloc.freezed.dart';

typedef BirthRegEmitter = Emitter<BirthRegState>;

class BirthRegBloc extends Bloc<BirthRegEvent, BirthRegState> {
  BirthRegBloc() : super(const BirthRegState.initial()) {
    on<RegBirthEvent>(_onCreate);
  }

  FutureOr<void> _onCreate(RegBirthEvent event, BirthRegEmitter emit) async {
    Client client = Client();
    try {
      emit(const BirthRegState.loading());
      CreateBirthResponse createBirthResponse =
          await BNDRepository(client.init())
              .createBirthCertificate(url: Urls.bndServices.createBirth, body: {
        BirthCertificatesList(birthCerts: [
          BirthCertificate(
              birthFatherInfo: BirthFatherInfo(
                  firstname: event.childFatherDetails.firstName,
                  lastname: event.childFatherDetails.lastName,
                  aadharno: event.childFatherDetails.aadhaarNo ?? '',
                  mobileno: event.childFatherDetails.mobileNo ?? '',
                  religion: event.childFatherDetails.religion ?? '',
                  nationality: event.childFatherDetails.nationality),
              birthMotherInfo: BirthMotherInfo(
                  firstname: event.childFatherDetails.firstName,
                  lastname: event.childMotherDetails.lastName,
                  aadharno: event.childMotherDetails.aadhaarNo ?? '',
                  mobileno: event.childMotherDetails.mobileNo ?? '',
                  religion: event.childMotherDetails.religion ?? '',
                  nationality: event.childMotherDetails.nationality),
              birthPresentaddr: BirthAddress(
                  buildingno: event.childAddressDetails.buildingNo,
                  houseno: event.childAddressDetails.houseNo,
                  streetname: event.childAddressDetails.streetname ?? '',
                  locality: event.childAddressDetails.locality ?? '',
                  tehsil: event.childAddressDetails.tehsil,
                  district: event.childAddressDetails.district,
                  city: event.childAddressDetails.city,
                  state: event.childAddressDetails.state,
                  pinno: event.childAddressDetails.pinNo,
                  country: event.childAddressDetails.country),
              birthPermaddr: BirthAddress(
                  buildingno: event.childAddressDetails.buildingNo,
                  houseno: event.childAddressDetails.houseNo,
                  streetname: event.childAddressDetails.streetname ?? '',
                  locality: event.childAddressDetails.locality ?? '',
                  tehsil: event.childAddressDetails.tehsil,
                  district: event.childAddressDetails.district,
                  city: event.childAddressDetails.city,
                  state: event.childAddressDetails.state,
                  pinno: event.childAddressDetails.pinNo,
                  country: event.childAddressDetails.country),
              registrationno: event.childDetails.regNo,
              hospitalname: event.childDetails.hospitalName,
              dateofreportepoch: event.childDetails.dateOfReport,
              dateofbirthepoch: event.childDetails.dateOfBirth,
              genderStr: event.childDetails.genderStr,
              firstname: event.childDetails.firstName,
              lastname: event.childDetails.lastName,
              placeofbirth: event.childDetails.placeOfBirth,
              checkboxforaddress: true,
              informantsname: event.childDetails.informantName,
              informantsaddress: event.childDetails.informantAddress,
              tenantid: GlobalVariables.userInfo!.tenantId,
              excelrowindex: -1,
              counter: 0)
        ]).toJson()
      });
      if (createBirthResponse != null &&
          createBirthResponse.statsMap?.successfulRecords == 1) {
        emit(const BirthRegState.loaded());
      } else if (createBirthResponse != null &&
          createBirthResponse.statsMap?.failedRecords == 1) {
        emit(BirthRegState.error(i18.common.registrationNoAlreadyExists));
      } else {
        emit(BirthRegState.error(i18.common.someErrorOccurred));
      }
    } on DioError catch (e) {
      emit(BirthRegState.error(i18.common.someErrorOccurred));
    }
  }
}

@freezed
class BirthRegEvent with _$BirthRegEvent {
  const factory BirthRegEvent.create({
    required ChildDetails childDetails,
    required ChildFatherDetails childFatherDetails,
    required ChildMotherDetails childMotherDetails,
    required ChildAddressDetails childAddressDetails,
  }) = RegBirthEvent;
}

@freezed
class BirthRegState with _$BirthRegState {
  const BirthRegState._();
  const factory BirthRegState.initial() = _Initial;
  const factory BirthRegState.loading() = _Loading;
  const factory BirthRegState.loaded() = _Loaded;
  const factory BirthRegState.error(String? error) = _Error;
}
