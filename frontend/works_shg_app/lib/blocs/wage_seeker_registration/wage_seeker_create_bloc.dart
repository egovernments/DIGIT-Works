import 'dart:async';
import 'dart:convert';

import 'package:dio/dio.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:freezed_annotation/freezed_annotation.dart';
import 'package:works_shg_app/services/urls.dart';

import '../../data/remote_client.dart';
import '../../data/repositories/wage_seeker_repository/wage_seeker_repository.dart';
import '../../models/adharModel/adhar_response.dart';
import '../../models/attendance/individual_list_model.dart';
import '../../models/wage_seeker/financial_details_model.dart';
import '../../models/wage_seeker/individual_details_model.dart';
import '../../models/wage_seeker/location_details_model.dart';
import '../../models/wage_seeker/skill_details_model.dart';
import '../../utils/date_formats.dart';

part 'wage_seeker_create_bloc.freezed.dart';

typedef WageSeekerCreateEmitter = Emitter<WageSeekerCreateState>;

class WageSeekerCreateBloc
    extends Bloc<WageSeekerCreateEvent, WageSeekerCreateState> {
  WageSeekerCreateBloc() : super(const WageSeekerCreateState.initial()) {
    on<CreateWageSeekerEvent>(_onCreate);
    on<CreateWageSeekerDisposeEvent>(_onDispose);
    on<VerifyAdharEvent>(_onVerifyAdhar);
  }

  FutureOr<void> _onVerifyAdhar(
      VerifyAdharEvent event, WageSeekerCreateEmitter emit) async {
    Client client = Client();

    final data = {
      "uid": event.uid,
      "uidType": "A",
      "consent": "Y",
      "subAuaCode": "0002590000",
      "txn": "",
      "isPI": "y",
      "isBio": "n",
      "isOTP": "n",
      "bioType": "n",
      "name": event.name,
      "dob": "",
      "gender": "",
      "rdInfo": "",
      "rdData": "",
      "otpValue": ""
    };
    try {
      emit(const WageSeekerCreateState.loading());
      AdharCardResponse s =
          await WageSeekerRepository(client.init()).verifyingAdharCard(
        url: Urls.wageSeekerServices.adharVerifyUrl,
        body: jsonEncode(data),
      );

      emit(WageSeekerCreateState.verified(s));
    } catch (e) {
      emit(WageSeekerCreateState.error(e.toString()));
    }
  }

  FutureOr<void> _onCreate(
      CreateWageSeekerEvent event, WageSeekerCreateEmitter emit) async {
    Client client = Client();
    try {
      
      emit(const WageSeekerCreateState.loading());
      SingleIndividualModel individualListModel =
          await WageSeekerRepository(client.init()).createIndividual(
              url: Urls.wageSeekerServices.individualCreate,
              body: {
            "Individual": {
              "tenantId": event.locationDetails?.city,
              "name": {"givenName": event.individualDetails?.name?.trim()},
              "dateOfBirth": DateFormats.getFilteredDate(
                  event.individualDetails?.dateOfBirth.toString() ?? ''),
              "gender": event.individualDetails?.gender,
              "mobileNumber": event.individualDetails?.mobileNumber,
              "address": [
                {
                  "tenantId": event.locationDetails?.city,
                  "pincode": event.locationDetails?.pinCode != ''
                      ? event.locationDetails?.pinCode
                      : null,
                  "city": event.locationDetails?.city,
                  "street": event.locationDetails?.streetName != null &&
                          event.locationDetails?.streetName != ""
                      ? event.locationDetails?.streetName?.trim().toString()
                      : null,
                  "doorNo": event.locationDetails?.doorNo != null &&
                          event.locationDetails?.doorNo != ""
                      ? event.locationDetails?.doorNo?.trim().toString()
                      : null,
                  "type": "PERMANENT",
                  "locality": {"code": event.locationDetails?.locality},
                  "ward": {"code": event.locationDetails?.ward}
                }
              ],
              "fatherName": event.individualDetails?.fatherName?.trim(),
              "husbandName": null,
              "relationship": event.individualDetails?.relationship,
              "identifiers": [
                {
                  "identifierType": event.individualDetails?.documentType,
                  "identifierId": event.individualDetails?.aadhaarNo
                }
              ],
              "skills": event.skillDetails?.individualSkills
                  ?.map((e) => {"type": e.type, "level": e.level})
                  .toList(),
              "photo": event.individualDetails?.photo,
              "additionalFields": {
                "fields": [
                  {
                    "key": "SOCIAL_CATEGORY",
                    "value": event.individualDetails?.socialCategory
                  },
                  {
                    "key": "is_aadhaar_verified",
                    "value": event.individualDetails?.adharVerified ?? false
                  },
                  {
                    "key": "verification_time",
                    "value": event.individualDetails?.timeStamp
                  }
                  
                ],
                "adhaar_res": 
                    event.individualDetails?.adharCardResponse != null
                        ? {
                            "ret": event.individualDetails?.adharCardResponse?.ret,
                            "err": event.individualDetails?.adharCardResponse?.err,
                            "status": event.individualDetails?.adharCardResponse?.status,
                            "errMsg": event.individualDetails?.adharCardResponse?.errMsg,
                            "txn": event.individualDetails?.adharCardResponse?.txn,
                            "responseCode": event.individualDetails?.adharCardResponse?.responseCode,
                            "uidToken":
                                event.individualDetails?.adharCardResponse?.uidToken,
                            "mobileNumber": event.individualDetails?.adharCardResponse?.mobileNumber,
                            "email": event.individualDetails?.adharCardResponse?.email
                          }
                        : null
                  
              }
            }
          });
      await Future.delayed(const Duration(seconds: 1));
      emit(WageSeekerCreateState.loaded(individualListModel));
    } on DioException catch (e) {
      emit(WageSeekerCreateState.error(e.response?.data['Errors'][0]['code']));
    }
  }

  FutureOr<void> _onDispose(
      CreateWageSeekerDisposeEvent event, WageSeekerCreateEmitter emit) async {
    emit(const WageSeekerCreateState.initial());
  }
}

@freezed
class WageSeekerCreateEvent with _$WageSeekerCreateEvent {
  const factory WageSeekerCreateEvent.create(
      {IndividualDetails? individualDetails,
      SkillDetails? skillDetails,
      LocationDetails? locationDetails,
      FinancialDetails? financialDetails}) = CreateWageSeekerEvent;

  const factory WageSeekerCreateEvent.verifyAdhar({
    required String name,
    required String uid,
  }) = VerifyAdharEvent;

  const factory WageSeekerCreateEvent.dispose() = CreateWageSeekerDisposeEvent;
}

@freezed
class WageSeekerCreateState with _$WageSeekerCreateState {
  const WageSeekerCreateState._();

  const factory WageSeekerCreateState.initial() = _Initial;
  const factory WageSeekerCreateState.loading() = _Loading;
  const factory WageSeekerCreateState.loaded(
    SingleIndividualModel? individualListModel,
  ) = _Loaded;
  const factory WageSeekerCreateState.verified(
    AdharCardResponse? adharCardResponse,
  ) = _Verified;
  const factory WageSeekerCreateState.error(String? error) = _Error;
}
