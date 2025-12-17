import 'dart:async';
import 'dart:io';

import 'package:flutter/foundation.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:freezed_annotation/freezed_annotation.dart';
import 'package:works_shg_app/models/adharModel/adhar_response.dart';
import 'package:works_shg_app/models/wage_seeker/financial_details_model.dart';
import 'package:works_shg_app/models/wage_seeker/individual_details_model.dart';
import 'package:works_shg_app/models/wage_seeker/location_details_model.dart';
import 'package:works_shg_app/models/wage_seeker/skill_details_model.dart';

part 'wage_seeker_registration_bloc.freezed.dart';

typedef WageSeekerBlocEmitter = Emitter<WageSeekerBlocState>;

class WageSeekerBloc extends Bloc<WageSeekerBlocEvent, WageSeekerBlocState> {
  WageSeekerBloc() : super(const WageSeekerBlocState.create()) {
    on<WageSeekerCreateEvent>(_onCreate);
    on<WageSeekerClearEvent>(_onClear);

    on<WageSeekerIdentificationCreateEvent>(_onIdentificationCreate);

    on<WageSeekerDetailsCreateEvent>(_onDetailsCreate);

    on<WageSeekerSkillCreateEvent>(_onSkillCreate);

    on<WageSeekerPhotoCreateEvent>(_onPhotoCreate);
  }

// Event handler for WageSeekerPhotoCreateEvent
  FutureOr<void> _onPhotoCreate(
      WageSeekerPhotoCreateEvent event, WageSeekerBlocEmitter emit) async {
    state.maybeMap(
      orElse: () => {},
      create: (value) {
        emit(
          value.copyWith(
            individualDetails: value.individualDetails!.copyWith(
              photo: event.photo,
              imageFile: event.imageFile,
              bytes: event.bytes,
            ),
          ),
        );
      },
    );
  }

// Event handler for WageSeekerSkillCreateEvent
  FutureOr<void> _onSkillCreate(
      WageSeekerSkillCreateEvent event, WageSeekerBlocEmitter emit) async {
    state.maybeMap(
      orElse: () => {},
      create: (value) {
        emit(value.copyWith(
          skillDetails: event.skillDetails,
        ));
      },
    );
  }

// Event handler for WageSeekerPhotoCreateEvent
  FutureOr<void> _onIdentificationCreate(
      WageSeekerIdentificationCreateEvent event,
      WageSeekerBlocEmitter emit) async {
// Get the current state of the bloc
    WageSeekerBlocState state = this.state;

// Check if aadhaarNo is null in individualDetails of the current state
    if (state.individualDetails?.aadhaarNo == null) {
      // Emit a new state with updated individualDetails if aadhaarNo is null
      emit(WageSeekerBlocState.create(
        individualDetails: IndividualDetails(
          aadhaarNo: event.number,
          name: event.name,
          documentType: event.documentType,
          adharVerified: event.adharVerified,
          timeStamp: event.timeStamp,
          adharCardResponse: event.adharCardResponse,
        ),
      ));
    } else {
      emit(
// Emit a new state with updated individualDetails if aadhaarNo is not null
          state.copyWith(
              individualDetails: state.individualDetails?.copyWith(
        aadhaarNo: event.number,
        name: event.name,
        documentType: event.documentType,
        adharVerified: event.adharVerified,
         timeStamp: event.timeStamp,
        adharCardResponse: event.adharCardResponse,
      )));
    }

    // emit(WageSeekerBlocState.create(
    //   individualDetails: IndividualDetails(
    //     aadhaarNo: event.number,
    //     name: event.name,
    //     documentType: event.documentType,
    //     adharVerified: event.adharVerified,
    //   ),
    // ));
  }

  // Event handler for WageSeekerDetailsCreateEvent
  FutureOr<void> _onDetailsCreate(
      WageSeekerDetailsCreateEvent event, WageSeekerBlocEmitter emit) async {
    state.maybeMap(
      orElse: () =>
          {}, // Do nothing if the state does not match the expected state
      create: (value) {
        emit(value.copyWith(
            individualDetails: value.individualDetails!.copyWith(
          dateOfBirth: event.dob,
          gender: event.gender,
          fatherName: event.fatherName,
          relationship: event.relationShip,
          socialCategory: event.socialCategory,
          mobileNumber: event.mobileNumber,
        )));
      },
    );
  }

// Event handler for WageSeekerCreateEvent
  FutureOr<void> _onCreate(
      WageSeekerCreateEvent event, WageSeekerBlocEmitter emit) async {
    state.maybeMap(
      orElse: () => {},
      create: (value) {
        emit(value.copyWith(
          financialDetails: event.financialDetails,
          locationDetails: event.locationDetails,
        ));
      },
    );
  }

// Event handler for WageSeekerClearEvent
  FutureOr<void> _onClear(
      WageSeekerClearEvent event, WageSeekerBlocEmitter emit) async {
    emit(WageSeekerBlocState.clear(
        individualDetails: IndividualDetails(),
        locationDetails: LocationDetails(),
        skillDetails: SkillDetails(),
        financialDetails: FinancialDetails()));
  }
}

@freezed
class WageSeekerBlocEvent with _$WageSeekerBlocEvent {
  const factory WageSeekerBlocEvent.create(
      {IndividualDetails? individualDetails,
      SkillDetails? skillDetails,
      LocationDetails? locationDetails,
      FinancialDetails? financialDetails}) = WageSeekerCreateEvent;

  const factory WageSeekerBlocEvent.identificationCreate({
    required String documentType,
    required String number,
    required String name,
    required bool adharVerified,
    required int timeStamp,
    AdharCardResponse? adharCardResponse
  }) = WageSeekerIdentificationCreateEvent;

  const factory WageSeekerBlocEvent.detailsCreate(
      {required String fatherName,
      required DateTime dob,
      required String relationShip,
      required String gender,
      required String socialCategory,
      required String mobileNumber}) = WageSeekerDetailsCreateEvent;

  const factory WageSeekerBlocEvent.skillCreate({
    required SkillDetails skillDetails,
  }) = WageSeekerSkillCreateEvent;

  const factory WageSeekerBlocEvent.photoCreate({
    File? imageFile,
    Uint8List? bytes,
    String? photo,
  }) = WageSeekerPhotoCreateEvent;

  const factory WageSeekerBlocEvent.clear() = WageSeekerClearEvent;
}

@freezed
class WageSeekerBlocState with _$WageSeekerBlocState {
  const WageSeekerBlocState._();

  const factory WageSeekerBlocState.create(
      {IndividualDetails? individualDetails,
      SkillDetails? skillDetails,
      LocationDetails? locationDetails,
      FinancialDetails? financialDetails}) = _CreateWageSeeker;
  const factory WageSeekerBlocState.clear(
      {IndividualDetails? individualDetails,
      SkillDetails? skillDetails,
      LocationDetails? locationDetails,
      FinancialDetails? financialDetails}) = _EditWageSeeker;
}
