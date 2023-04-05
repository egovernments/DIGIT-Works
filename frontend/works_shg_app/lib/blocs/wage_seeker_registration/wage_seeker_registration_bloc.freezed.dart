// coverage:ignore-file
// GENERATED CODE - DO NOT MODIFY BY HAND
// ignore_for_file: type=lint
// ignore_for_file: unused_element, deprecated_member_use, deprecated_member_use_from_same_package, use_function_type_syntax_for_parameters, unnecessary_const, avoid_init_to_null, invalid_override_different_default_values_named, prefer_expression_function_bodies, annotate_overrides, invalid_annotation_target, unnecessary_question_mark

part of 'wage_seeker_registration_bloc.dart';

// **************************************************************************
// FreezedGenerator
// **************************************************************************

T _$identity<T>(T value) => value;

final _privateConstructorUsedError = UnsupportedError(
    'It seems like you constructed your class using `MyClass._()`. This constructor is only meant to be used by freezed and you are not supposed to need it nor use it.\nPlease check the documentation here for more information: https://github.com/rrousselGit/freezed#custom-getters-and-methods');

/// @nodoc
mixin _$WageSeekerBlocEvent {
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function(
            IndividualDetails? individualDetails,
            SkillDetails? skillDetails,
            LocationDetails? locationDetails,
            FinancialDetails? financialDetails)
        create,
    required TResult Function() clear,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function(
            IndividualDetails? individualDetails,
            SkillDetails? skillDetails,
            LocationDetails? locationDetails,
            FinancialDetails? financialDetails)?
        create,
    TResult? Function()? clear,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function(
            IndividualDetails? individualDetails,
            SkillDetails? skillDetails,
            LocationDetails? locationDetails,
            FinancialDetails? financialDetails)?
        create,
    TResult Function()? clear,
    required TResult orElse(),
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult map<TResult extends Object?>({
    required TResult Function(WageSeekerCreateEvent value) create,
    required TResult Function(WageSeekerClearEvent value) clear,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(WageSeekerCreateEvent value)? create,
    TResult? Function(WageSeekerClearEvent value)? clear,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(WageSeekerCreateEvent value)? create,
    TResult Function(WageSeekerClearEvent value)? clear,
    required TResult orElse(),
  }) =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $WageSeekerBlocEventCopyWith<$Res> {
  factory $WageSeekerBlocEventCopyWith(
          WageSeekerBlocEvent value, $Res Function(WageSeekerBlocEvent) then) =
      _$WageSeekerBlocEventCopyWithImpl<$Res, WageSeekerBlocEvent>;
}

/// @nodoc
class _$WageSeekerBlocEventCopyWithImpl<$Res, $Val extends WageSeekerBlocEvent>
    implements $WageSeekerBlocEventCopyWith<$Res> {
  _$WageSeekerBlocEventCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;
}

/// @nodoc
abstract class _$$WageSeekerCreateEventCopyWith<$Res> {
  factory _$$WageSeekerCreateEventCopyWith(_$WageSeekerCreateEvent value,
          $Res Function(_$WageSeekerCreateEvent) then) =
      __$$WageSeekerCreateEventCopyWithImpl<$Res>;
  @useResult
  $Res call(
      {IndividualDetails? individualDetails,
      SkillDetails? skillDetails,
      LocationDetails? locationDetails,
      FinancialDetails? financialDetails});
}

/// @nodoc
class __$$WageSeekerCreateEventCopyWithImpl<$Res>
    extends _$WageSeekerBlocEventCopyWithImpl<$Res, _$WageSeekerCreateEvent>
    implements _$$WageSeekerCreateEventCopyWith<$Res> {
  __$$WageSeekerCreateEventCopyWithImpl(_$WageSeekerCreateEvent _value,
      $Res Function(_$WageSeekerCreateEvent) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? individualDetails = freezed,
    Object? skillDetails = freezed,
    Object? locationDetails = freezed,
    Object? financialDetails = freezed,
  }) {
    return _then(_$WageSeekerCreateEvent(
      individualDetails: freezed == individualDetails
          ? _value.individualDetails
          : individualDetails // ignore: cast_nullable_to_non_nullable
              as IndividualDetails?,
      skillDetails: freezed == skillDetails
          ? _value.skillDetails
          : skillDetails // ignore: cast_nullable_to_non_nullable
              as SkillDetails?,
      locationDetails: freezed == locationDetails
          ? _value.locationDetails
          : locationDetails // ignore: cast_nullable_to_non_nullable
              as LocationDetails?,
      financialDetails: freezed == financialDetails
          ? _value.financialDetails
          : financialDetails // ignore: cast_nullable_to_non_nullable
              as FinancialDetails?,
    ));
  }
}

/// @nodoc

class _$WageSeekerCreateEvent
    with DiagnosticableTreeMixin
    implements WageSeekerCreateEvent {
  const _$WageSeekerCreateEvent(
      {this.individualDetails,
      this.skillDetails,
      this.locationDetails,
      this.financialDetails});

  @override
  final IndividualDetails? individualDetails;
  @override
  final SkillDetails? skillDetails;
  @override
  final LocationDetails? locationDetails;
  @override
  final FinancialDetails? financialDetails;

  @override
  String toString({DiagnosticLevel minLevel = DiagnosticLevel.info}) {
    return 'WageSeekerBlocEvent.create(individualDetails: $individualDetails, skillDetails: $skillDetails, locationDetails: $locationDetails, financialDetails: $financialDetails)';
  }

  @override
  void debugFillProperties(DiagnosticPropertiesBuilder properties) {
    super.debugFillProperties(properties);
    properties
      ..add(DiagnosticsProperty('type', 'WageSeekerBlocEvent.create'))
      ..add(DiagnosticsProperty('individualDetails', individualDetails))
      ..add(DiagnosticsProperty('skillDetails', skillDetails))
      ..add(DiagnosticsProperty('locationDetails', locationDetails))
      ..add(DiagnosticsProperty('financialDetails', financialDetails));
  }

  @override
  bool operator ==(dynamic other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$WageSeekerCreateEvent &&
            (identical(other.individualDetails, individualDetails) ||
                other.individualDetails == individualDetails) &&
            (identical(other.skillDetails, skillDetails) ||
                other.skillDetails == skillDetails) &&
            (identical(other.locationDetails, locationDetails) ||
                other.locationDetails == locationDetails) &&
            (identical(other.financialDetails, financialDetails) ||
                other.financialDetails == financialDetails));
  }

  @override
  int get hashCode => Object.hash(runtimeType, individualDetails, skillDetails,
      locationDetails, financialDetails);

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$WageSeekerCreateEventCopyWith<_$WageSeekerCreateEvent> get copyWith =>
      __$$WageSeekerCreateEventCopyWithImpl<_$WageSeekerCreateEvent>(
          this, _$identity);

  @override
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function(
            IndividualDetails? individualDetails,
            SkillDetails? skillDetails,
            LocationDetails? locationDetails,
            FinancialDetails? financialDetails)
        create,
    required TResult Function() clear,
  }) {
    return create(
        individualDetails, skillDetails, locationDetails, financialDetails);
  }

  @override
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function(
            IndividualDetails? individualDetails,
            SkillDetails? skillDetails,
            LocationDetails? locationDetails,
            FinancialDetails? financialDetails)?
        create,
    TResult? Function()? clear,
  }) {
    return create?.call(
        individualDetails, skillDetails, locationDetails, financialDetails);
  }

  @override
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function(
            IndividualDetails? individualDetails,
            SkillDetails? skillDetails,
            LocationDetails? locationDetails,
            FinancialDetails? financialDetails)?
        create,
    TResult Function()? clear,
    required TResult orElse(),
  }) {
    if (create != null) {
      return create(
          individualDetails, skillDetails, locationDetails, financialDetails);
    }
    return orElse();
  }

  @override
  @optionalTypeArgs
  TResult map<TResult extends Object?>({
    required TResult Function(WageSeekerCreateEvent value) create,
    required TResult Function(WageSeekerClearEvent value) clear,
  }) {
    return create(this);
  }

  @override
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(WageSeekerCreateEvent value)? create,
    TResult? Function(WageSeekerClearEvent value)? clear,
  }) {
    return create?.call(this);
  }

  @override
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(WageSeekerCreateEvent value)? create,
    TResult Function(WageSeekerClearEvent value)? clear,
    required TResult orElse(),
  }) {
    if (create != null) {
      return create(this);
    }
    return orElse();
  }
}

abstract class WageSeekerCreateEvent implements WageSeekerBlocEvent {
  const factory WageSeekerCreateEvent(
      {final IndividualDetails? individualDetails,
      final SkillDetails? skillDetails,
      final LocationDetails? locationDetails,
      final FinancialDetails? financialDetails}) = _$WageSeekerCreateEvent;

  IndividualDetails? get individualDetails;
  SkillDetails? get skillDetails;
  LocationDetails? get locationDetails;
  FinancialDetails? get financialDetails;
  @JsonKey(ignore: true)
  _$$WageSeekerCreateEventCopyWith<_$WageSeekerCreateEvent> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class _$$WageSeekerClearEventCopyWith<$Res> {
  factory _$$WageSeekerClearEventCopyWith(_$WageSeekerClearEvent value,
          $Res Function(_$WageSeekerClearEvent) then) =
      __$$WageSeekerClearEventCopyWithImpl<$Res>;
}

/// @nodoc
class __$$WageSeekerClearEventCopyWithImpl<$Res>
    extends _$WageSeekerBlocEventCopyWithImpl<$Res, _$WageSeekerClearEvent>
    implements _$$WageSeekerClearEventCopyWith<$Res> {
  __$$WageSeekerClearEventCopyWithImpl(_$WageSeekerClearEvent _value,
      $Res Function(_$WageSeekerClearEvent) _then)
      : super(_value, _then);
}

/// @nodoc

class _$WageSeekerClearEvent
    with DiagnosticableTreeMixin
    implements WageSeekerClearEvent {
  const _$WageSeekerClearEvent();

  @override
  String toString({DiagnosticLevel minLevel = DiagnosticLevel.info}) {
    return 'WageSeekerBlocEvent.clear()';
  }

  @override
  void debugFillProperties(DiagnosticPropertiesBuilder properties) {
    super.debugFillProperties(properties);
    properties.add(DiagnosticsProperty('type', 'WageSeekerBlocEvent.clear'));
  }

  @override
  bool operator ==(dynamic other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType && other is _$WageSeekerClearEvent);
  }

  @override
  int get hashCode => runtimeType.hashCode;

  @override
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function(
            IndividualDetails? individualDetails,
            SkillDetails? skillDetails,
            LocationDetails? locationDetails,
            FinancialDetails? financialDetails)
        create,
    required TResult Function() clear,
  }) {
    return clear();
  }

  @override
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function(
            IndividualDetails? individualDetails,
            SkillDetails? skillDetails,
            LocationDetails? locationDetails,
            FinancialDetails? financialDetails)?
        create,
    TResult? Function()? clear,
  }) {
    return clear?.call();
  }

  @override
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function(
            IndividualDetails? individualDetails,
            SkillDetails? skillDetails,
            LocationDetails? locationDetails,
            FinancialDetails? financialDetails)?
        create,
    TResult Function()? clear,
    required TResult orElse(),
  }) {
    if (clear != null) {
      return clear();
    }
    return orElse();
  }

  @override
  @optionalTypeArgs
  TResult map<TResult extends Object?>({
    required TResult Function(WageSeekerCreateEvent value) create,
    required TResult Function(WageSeekerClearEvent value) clear,
  }) {
    return clear(this);
  }

  @override
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(WageSeekerCreateEvent value)? create,
    TResult? Function(WageSeekerClearEvent value)? clear,
  }) {
    return clear?.call(this);
  }

  @override
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(WageSeekerCreateEvent value)? create,
    TResult Function(WageSeekerClearEvent value)? clear,
    required TResult orElse(),
  }) {
    if (clear != null) {
      return clear(this);
    }
    return orElse();
  }
}

abstract class WageSeekerClearEvent implements WageSeekerBlocEvent {
  const factory WageSeekerClearEvent() = _$WageSeekerClearEvent;
}

/// @nodoc
mixin _$WageSeekerBlocState {
  IndividualDetails? get individualDetails =>
      throw _privateConstructorUsedError;
  SkillDetails? get skillDetails => throw _privateConstructorUsedError;
  LocationDetails? get locationDetails => throw _privateConstructorUsedError;
  FinancialDetails? get financialDetails => throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function(
            IndividualDetails? individualDetails,
            SkillDetails? skillDetails,
            LocationDetails? locationDetails,
            FinancialDetails? financialDetails)
        create,
    required TResult Function(
            IndividualDetails? individualDetails,
            SkillDetails? skillDetails,
            LocationDetails? locationDetails,
            FinancialDetails? financialDetails)
        clear,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function(
            IndividualDetails? individualDetails,
            SkillDetails? skillDetails,
            LocationDetails? locationDetails,
            FinancialDetails? financialDetails)?
        create,
    TResult? Function(
            IndividualDetails? individualDetails,
            SkillDetails? skillDetails,
            LocationDetails? locationDetails,
            FinancialDetails? financialDetails)?
        clear,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function(
            IndividualDetails? individualDetails,
            SkillDetails? skillDetails,
            LocationDetails? locationDetails,
            FinancialDetails? financialDetails)?
        create,
    TResult Function(
            IndividualDetails? individualDetails,
            SkillDetails? skillDetails,
            LocationDetails? locationDetails,
            FinancialDetails? financialDetails)?
        clear,
    required TResult orElse(),
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult map<TResult extends Object?>({
    required TResult Function(_CreateWageSeeker value) create,
    required TResult Function(_EditWageSeeker value) clear,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(_CreateWageSeeker value)? create,
    TResult? Function(_EditWageSeeker value)? clear,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(_CreateWageSeeker value)? create,
    TResult Function(_EditWageSeeker value)? clear,
    required TResult orElse(),
  }) =>
      throw _privateConstructorUsedError;

  @JsonKey(ignore: true)
  $WageSeekerBlocStateCopyWith<WageSeekerBlocState> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $WageSeekerBlocStateCopyWith<$Res> {
  factory $WageSeekerBlocStateCopyWith(
          WageSeekerBlocState value, $Res Function(WageSeekerBlocState) then) =
      _$WageSeekerBlocStateCopyWithImpl<$Res, WageSeekerBlocState>;
  @useResult
  $Res call(
      {IndividualDetails? individualDetails,
      SkillDetails? skillDetails,
      LocationDetails? locationDetails,
      FinancialDetails? financialDetails});
}

/// @nodoc
class _$WageSeekerBlocStateCopyWithImpl<$Res, $Val extends WageSeekerBlocState>
    implements $WageSeekerBlocStateCopyWith<$Res> {
  _$WageSeekerBlocStateCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? individualDetails = freezed,
    Object? skillDetails = freezed,
    Object? locationDetails = freezed,
    Object? financialDetails = freezed,
  }) {
    return _then(_value.copyWith(
      individualDetails: freezed == individualDetails
          ? _value.individualDetails
          : individualDetails // ignore: cast_nullable_to_non_nullable
              as IndividualDetails?,
      skillDetails: freezed == skillDetails
          ? _value.skillDetails
          : skillDetails // ignore: cast_nullable_to_non_nullable
              as SkillDetails?,
      locationDetails: freezed == locationDetails
          ? _value.locationDetails
          : locationDetails // ignore: cast_nullable_to_non_nullable
              as LocationDetails?,
      financialDetails: freezed == financialDetails
          ? _value.financialDetails
          : financialDetails // ignore: cast_nullable_to_non_nullable
              as FinancialDetails?,
    ) as $Val);
  }
}

/// @nodoc
abstract class _$$_CreateWageSeekerCopyWith<$Res>
    implements $WageSeekerBlocStateCopyWith<$Res> {
  factory _$$_CreateWageSeekerCopyWith(
          _$_CreateWageSeeker value, $Res Function(_$_CreateWageSeeker) then) =
      __$$_CreateWageSeekerCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call(
      {IndividualDetails? individualDetails,
      SkillDetails? skillDetails,
      LocationDetails? locationDetails,
      FinancialDetails? financialDetails});
}

/// @nodoc
class __$$_CreateWageSeekerCopyWithImpl<$Res>
    extends _$WageSeekerBlocStateCopyWithImpl<$Res, _$_CreateWageSeeker>
    implements _$$_CreateWageSeekerCopyWith<$Res> {
  __$$_CreateWageSeekerCopyWithImpl(
      _$_CreateWageSeeker _value, $Res Function(_$_CreateWageSeeker) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? individualDetails = freezed,
    Object? skillDetails = freezed,
    Object? locationDetails = freezed,
    Object? financialDetails = freezed,
  }) {
    return _then(_$_CreateWageSeeker(
      individualDetails: freezed == individualDetails
          ? _value.individualDetails
          : individualDetails // ignore: cast_nullable_to_non_nullable
              as IndividualDetails?,
      skillDetails: freezed == skillDetails
          ? _value.skillDetails
          : skillDetails // ignore: cast_nullable_to_non_nullable
              as SkillDetails?,
      locationDetails: freezed == locationDetails
          ? _value.locationDetails
          : locationDetails // ignore: cast_nullable_to_non_nullable
              as LocationDetails?,
      financialDetails: freezed == financialDetails
          ? _value.financialDetails
          : financialDetails // ignore: cast_nullable_to_non_nullable
              as FinancialDetails?,
    ));
  }
}

/// @nodoc

class _$_CreateWageSeeker extends _CreateWageSeeker
    with DiagnosticableTreeMixin {
  const _$_CreateWageSeeker(
      {this.individualDetails,
      this.skillDetails,
      this.locationDetails,
      this.financialDetails})
      : super._();

  @override
  final IndividualDetails? individualDetails;
  @override
  final SkillDetails? skillDetails;
  @override
  final LocationDetails? locationDetails;
  @override
  final FinancialDetails? financialDetails;

  @override
  String toString({DiagnosticLevel minLevel = DiagnosticLevel.info}) {
    return 'WageSeekerBlocState.create(individualDetails: $individualDetails, skillDetails: $skillDetails, locationDetails: $locationDetails, financialDetails: $financialDetails)';
  }

  @override
  void debugFillProperties(DiagnosticPropertiesBuilder properties) {
    super.debugFillProperties(properties);
    properties
      ..add(DiagnosticsProperty('type', 'WageSeekerBlocState.create'))
      ..add(DiagnosticsProperty('individualDetails', individualDetails))
      ..add(DiagnosticsProperty('skillDetails', skillDetails))
      ..add(DiagnosticsProperty('locationDetails', locationDetails))
      ..add(DiagnosticsProperty('financialDetails', financialDetails));
  }

  @override
  bool operator ==(dynamic other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$_CreateWageSeeker &&
            (identical(other.individualDetails, individualDetails) ||
                other.individualDetails == individualDetails) &&
            (identical(other.skillDetails, skillDetails) ||
                other.skillDetails == skillDetails) &&
            (identical(other.locationDetails, locationDetails) ||
                other.locationDetails == locationDetails) &&
            (identical(other.financialDetails, financialDetails) ||
                other.financialDetails == financialDetails));
  }

  @override
  int get hashCode => Object.hash(runtimeType, individualDetails, skillDetails,
      locationDetails, financialDetails);

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$_CreateWageSeekerCopyWith<_$_CreateWageSeeker> get copyWith =>
      __$$_CreateWageSeekerCopyWithImpl<_$_CreateWageSeeker>(this, _$identity);

  @override
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function(
            IndividualDetails? individualDetails,
            SkillDetails? skillDetails,
            LocationDetails? locationDetails,
            FinancialDetails? financialDetails)
        create,
    required TResult Function(
            IndividualDetails? individualDetails,
            SkillDetails? skillDetails,
            LocationDetails? locationDetails,
            FinancialDetails? financialDetails)
        clear,
  }) {
    return create(
        individualDetails, skillDetails, locationDetails, financialDetails);
  }

  @override
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function(
            IndividualDetails? individualDetails,
            SkillDetails? skillDetails,
            LocationDetails? locationDetails,
            FinancialDetails? financialDetails)?
        create,
    TResult? Function(
            IndividualDetails? individualDetails,
            SkillDetails? skillDetails,
            LocationDetails? locationDetails,
            FinancialDetails? financialDetails)?
        clear,
  }) {
    return create?.call(
        individualDetails, skillDetails, locationDetails, financialDetails);
  }

  @override
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function(
            IndividualDetails? individualDetails,
            SkillDetails? skillDetails,
            LocationDetails? locationDetails,
            FinancialDetails? financialDetails)?
        create,
    TResult Function(
            IndividualDetails? individualDetails,
            SkillDetails? skillDetails,
            LocationDetails? locationDetails,
            FinancialDetails? financialDetails)?
        clear,
    required TResult orElse(),
  }) {
    if (create != null) {
      return create(
          individualDetails, skillDetails, locationDetails, financialDetails);
    }
    return orElse();
  }

  @override
  @optionalTypeArgs
  TResult map<TResult extends Object?>({
    required TResult Function(_CreateWageSeeker value) create,
    required TResult Function(_EditWageSeeker value) clear,
  }) {
    return create(this);
  }

  @override
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(_CreateWageSeeker value)? create,
    TResult? Function(_EditWageSeeker value)? clear,
  }) {
    return create?.call(this);
  }

  @override
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(_CreateWageSeeker value)? create,
    TResult Function(_EditWageSeeker value)? clear,
    required TResult orElse(),
  }) {
    if (create != null) {
      return create(this);
    }
    return orElse();
  }
}

abstract class _CreateWageSeeker extends WageSeekerBlocState {
  const factory _CreateWageSeeker(
      {final IndividualDetails? individualDetails,
      final SkillDetails? skillDetails,
      final LocationDetails? locationDetails,
      final FinancialDetails? financialDetails}) = _$_CreateWageSeeker;
  const _CreateWageSeeker._() : super._();

  @override
  IndividualDetails? get individualDetails;
  @override
  SkillDetails? get skillDetails;
  @override
  LocationDetails? get locationDetails;
  @override
  FinancialDetails? get financialDetails;
  @override
  @JsonKey(ignore: true)
  _$$_CreateWageSeekerCopyWith<_$_CreateWageSeeker> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class _$$_EditWageSeekerCopyWith<$Res>
    implements $WageSeekerBlocStateCopyWith<$Res> {
  factory _$$_EditWageSeekerCopyWith(
          _$_EditWageSeeker value, $Res Function(_$_EditWageSeeker) then) =
      __$$_EditWageSeekerCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call(
      {IndividualDetails? individualDetails,
      SkillDetails? skillDetails,
      LocationDetails? locationDetails,
      FinancialDetails? financialDetails});
}

/// @nodoc
class __$$_EditWageSeekerCopyWithImpl<$Res>
    extends _$WageSeekerBlocStateCopyWithImpl<$Res, _$_EditWageSeeker>
    implements _$$_EditWageSeekerCopyWith<$Res> {
  __$$_EditWageSeekerCopyWithImpl(
      _$_EditWageSeeker _value, $Res Function(_$_EditWageSeeker) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? individualDetails = freezed,
    Object? skillDetails = freezed,
    Object? locationDetails = freezed,
    Object? financialDetails = freezed,
  }) {
    return _then(_$_EditWageSeeker(
      individualDetails: freezed == individualDetails
          ? _value.individualDetails
          : individualDetails // ignore: cast_nullable_to_non_nullable
              as IndividualDetails?,
      skillDetails: freezed == skillDetails
          ? _value.skillDetails
          : skillDetails // ignore: cast_nullable_to_non_nullable
              as SkillDetails?,
      locationDetails: freezed == locationDetails
          ? _value.locationDetails
          : locationDetails // ignore: cast_nullable_to_non_nullable
              as LocationDetails?,
      financialDetails: freezed == financialDetails
          ? _value.financialDetails
          : financialDetails // ignore: cast_nullable_to_non_nullable
              as FinancialDetails?,
    ));
  }
}

/// @nodoc

class _$_EditWageSeeker extends _EditWageSeeker with DiagnosticableTreeMixin {
  const _$_EditWageSeeker(
      {this.individualDetails,
      this.skillDetails,
      this.locationDetails,
      this.financialDetails})
      : super._();

  @override
  final IndividualDetails? individualDetails;
  @override
  final SkillDetails? skillDetails;
  @override
  final LocationDetails? locationDetails;
  @override
  final FinancialDetails? financialDetails;

  @override
  String toString({DiagnosticLevel minLevel = DiagnosticLevel.info}) {
    return 'WageSeekerBlocState.clear(individualDetails: $individualDetails, skillDetails: $skillDetails, locationDetails: $locationDetails, financialDetails: $financialDetails)';
  }

  @override
  void debugFillProperties(DiagnosticPropertiesBuilder properties) {
    super.debugFillProperties(properties);
    properties
      ..add(DiagnosticsProperty('type', 'WageSeekerBlocState.clear'))
      ..add(DiagnosticsProperty('individualDetails', individualDetails))
      ..add(DiagnosticsProperty('skillDetails', skillDetails))
      ..add(DiagnosticsProperty('locationDetails', locationDetails))
      ..add(DiagnosticsProperty('financialDetails', financialDetails));
  }

  @override
  bool operator ==(dynamic other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$_EditWageSeeker &&
            (identical(other.individualDetails, individualDetails) ||
                other.individualDetails == individualDetails) &&
            (identical(other.skillDetails, skillDetails) ||
                other.skillDetails == skillDetails) &&
            (identical(other.locationDetails, locationDetails) ||
                other.locationDetails == locationDetails) &&
            (identical(other.financialDetails, financialDetails) ||
                other.financialDetails == financialDetails));
  }

  @override
  int get hashCode => Object.hash(runtimeType, individualDetails, skillDetails,
      locationDetails, financialDetails);

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$_EditWageSeekerCopyWith<_$_EditWageSeeker> get copyWith =>
      __$$_EditWageSeekerCopyWithImpl<_$_EditWageSeeker>(this, _$identity);

  @override
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function(
            IndividualDetails? individualDetails,
            SkillDetails? skillDetails,
            LocationDetails? locationDetails,
            FinancialDetails? financialDetails)
        create,
    required TResult Function(
            IndividualDetails? individualDetails,
            SkillDetails? skillDetails,
            LocationDetails? locationDetails,
            FinancialDetails? financialDetails)
        clear,
  }) {
    return clear(
        individualDetails, skillDetails, locationDetails, financialDetails);
  }

  @override
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function(
            IndividualDetails? individualDetails,
            SkillDetails? skillDetails,
            LocationDetails? locationDetails,
            FinancialDetails? financialDetails)?
        create,
    TResult? Function(
            IndividualDetails? individualDetails,
            SkillDetails? skillDetails,
            LocationDetails? locationDetails,
            FinancialDetails? financialDetails)?
        clear,
  }) {
    return clear?.call(
        individualDetails, skillDetails, locationDetails, financialDetails);
  }

  @override
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function(
            IndividualDetails? individualDetails,
            SkillDetails? skillDetails,
            LocationDetails? locationDetails,
            FinancialDetails? financialDetails)?
        create,
    TResult Function(
            IndividualDetails? individualDetails,
            SkillDetails? skillDetails,
            LocationDetails? locationDetails,
            FinancialDetails? financialDetails)?
        clear,
    required TResult orElse(),
  }) {
    if (clear != null) {
      return clear(
          individualDetails, skillDetails, locationDetails, financialDetails);
    }
    return orElse();
  }

  @override
  @optionalTypeArgs
  TResult map<TResult extends Object?>({
    required TResult Function(_CreateWageSeeker value) create,
    required TResult Function(_EditWageSeeker value) clear,
  }) {
    return clear(this);
  }

  @override
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(_CreateWageSeeker value)? create,
    TResult? Function(_EditWageSeeker value)? clear,
  }) {
    return clear?.call(this);
  }

  @override
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(_CreateWageSeeker value)? create,
    TResult Function(_EditWageSeeker value)? clear,
    required TResult orElse(),
  }) {
    if (clear != null) {
      return clear(this);
    }
    return orElse();
  }
}

abstract class _EditWageSeeker extends WageSeekerBlocState {
  const factory _EditWageSeeker(
      {final IndividualDetails? individualDetails,
      final SkillDetails? skillDetails,
      final LocationDetails? locationDetails,
      final FinancialDetails? financialDetails}) = _$_EditWageSeeker;
  const _EditWageSeeker._() : super._();

  @override
  IndividualDetails? get individualDetails;
  @override
  SkillDetails? get skillDetails;
  @override
  LocationDetails? get locationDetails;
  @override
  FinancialDetails? get financialDetails;
  @override
  @JsonKey(ignore: true)
  _$$_EditWageSeekerCopyWith<_$_EditWageSeeker> get copyWith =>
      throw _privateConstructorUsedError;
}
