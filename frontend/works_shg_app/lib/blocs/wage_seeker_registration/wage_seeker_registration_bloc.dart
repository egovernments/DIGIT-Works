import 'dart:async';

import 'package:flutter/foundation.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:freezed_annotation/freezed_annotation.dart';
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
  }

  FutureOr<void> _onCreate(
      WageSeekerCreateEvent event, WageSeekerBlocEmitter emit) async {
    emit(WageSeekerBlocState.create(
        individualDetails: event.individualDetails,
        locationDetails: event.locationDetails,
        skillDetails: event.skillDetails,
        financialDetails: event.financialDetails));
  }

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
