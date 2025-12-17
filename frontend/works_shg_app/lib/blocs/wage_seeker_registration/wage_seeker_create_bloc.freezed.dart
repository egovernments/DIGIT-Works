// coverage:ignore-file
// GENERATED CODE - DO NOT MODIFY BY HAND
// ignore_for_file: type=lint
// ignore_for_file: unused_element, deprecated_member_use, deprecated_member_use_from_same_package, use_function_type_syntax_for_parameters, unnecessary_const, avoid_init_to_null, invalid_override_different_default_values_named, prefer_expression_function_bodies, annotate_overrides, invalid_annotation_target, unnecessary_question_mark

part of 'wage_seeker_create_bloc.dart';

// **************************************************************************
// FreezedGenerator
// **************************************************************************

T _$identity<T>(T value) => value;

final _privateConstructorUsedError = UnsupportedError(
    'It seems like you constructed your class using `MyClass._()`. This constructor is only meant to be used by freezed and you are not supposed to need it nor use it.\nPlease check the documentation here for more information: https://github.com/rrousselGit/freezed#adding-getters-and-methods-to-our-models');

/// @nodoc
mixin _$WageSeekerCreateEvent {
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function(
            IndividualDetails? individualDetails,
            SkillDetails? skillDetails,
            LocationDetails? locationDetails,
            FinancialDetails? financialDetails)
        create,
    required TResult Function(String name, String uid) verifyAdhar,
    required TResult Function() dispose,
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
    TResult? Function(String name, String uid)? verifyAdhar,
    TResult? Function()? dispose,
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
    TResult Function(String name, String uid)? verifyAdhar,
    TResult Function()? dispose,
    required TResult orElse(),
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult map<TResult extends Object?>({
    required TResult Function(CreateWageSeekerEvent value) create,
    required TResult Function(VerifyAdharEvent value) verifyAdhar,
    required TResult Function(CreateWageSeekerDisposeEvent value) dispose,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(CreateWageSeekerEvent value)? create,
    TResult? Function(VerifyAdharEvent value)? verifyAdhar,
    TResult? Function(CreateWageSeekerDisposeEvent value)? dispose,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(CreateWageSeekerEvent value)? create,
    TResult Function(VerifyAdharEvent value)? verifyAdhar,
    TResult Function(CreateWageSeekerDisposeEvent value)? dispose,
    required TResult orElse(),
  }) =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $WageSeekerCreateEventCopyWith<$Res> {
  factory $WageSeekerCreateEventCopyWith(WageSeekerCreateEvent value,
          $Res Function(WageSeekerCreateEvent) then) =
      _$WageSeekerCreateEventCopyWithImpl<$Res, WageSeekerCreateEvent>;
}

/// @nodoc
class _$WageSeekerCreateEventCopyWithImpl<$Res,
        $Val extends WageSeekerCreateEvent>
    implements $WageSeekerCreateEventCopyWith<$Res> {
  _$WageSeekerCreateEventCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;
}

/// @nodoc
abstract class _$$CreateWageSeekerEventImplCopyWith<$Res> {
  factory _$$CreateWageSeekerEventImplCopyWith(
          _$CreateWageSeekerEventImpl value,
          $Res Function(_$CreateWageSeekerEventImpl) then) =
      __$$CreateWageSeekerEventImplCopyWithImpl<$Res>;
  @useResult
  $Res call(
      {IndividualDetails? individualDetails,
      SkillDetails? skillDetails,
      LocationDetails? locationDetails,
      FinancialDetails? financialDetails});
}

/// @nodoc
class __$$CreateWageSeekerEventImplCopyWithImpl<$Res>
    extends _$WageSeekerCreateEventCopyWithImpl<$Res,
        _$CreateWageSeekerEventImpl>
    implements _$$CreateWageSeekerEventImplCopyWith<$Res> {
  __$$CreateWageSeekerEventImplCopyWithImpl(_$CreateWageSeekerEventImpl _value,
      $Res Function(_$CreateWageSeekerEventImpl) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? individualDetails = freezed,
    Object? skillDetails = freezed,
    Object? locationDetails = freezed,
    Object? financialDetails = freezed,
  }) {
    return _then(_$CreateWageSeekerEventImpl(
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

class _$CreateWageSeekerEventImpl implements CreateWageSeekerEvent {
  const _$CreateWageSeekerEventImpl(
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
  String toString() {
    return 'WageSeekerCreateEvent.create(individualDetails: $individualDetails, skillDetails: $skillDetails, locationDetails: $locationDetails, financialDetails: $financialDetails)';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$CreateWageSeekerEventImpl &&
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
  _$$CreateWageSeekerEventImplCopyWith<_$CreateWageSeekerEventImpl>
      get copyWith => __$$CreateWageSeekerEventImplCopyWithImpl<
          _$CreateWageSeekerEventImpl>(this, _$identity);

  @override
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function(
            IndividualDetails? individualDetails,
            SkillDetails? skillDetails,
            LocationDetails? locationDetails,
            FinancialDetails? financialDetails)
        create,
    required TResult Function(String name, String uid) verifyAdhar,
    required TResult Function() dispose,
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
    TResult? Function(String name, String uid)? verifyAdhar,
    TResult? Function()? dispose,
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
    TResult Function(String name, String uid)? verifyAdhar,
    TResult Function()? dispose,
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
    required TResult Function(CreateWageSeekerEvent value) create,
    required TResult Function(VerifyAdharEvent value) verifyAdhar,
    required TResult Function(CreateWageSeekerDisposeEvent value) dispose,
  }) {
    return create(this);
  }

  @override
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(CreateWageSeekerEvent value)? create,
    TResult? Function(VerifyAdharEvent value)? verifyAdhar,
    TResult? Function(CreateWageSeekerDisposeEvent value)? dispose,
  }) {
    return create?.call(this);
  }

  @override
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(CreateWageSeekerEvent value)? create,
    TResult Function(VerifyAdharEvent value)? verifyAdhar,
    TResult Function(CreateWageSeekerDisposeEvent value)? dispose,
    required TResult orElse(),
  }) {
    if (create != null) {
      return create(this);
    }
    return orElse();
  }
}

abstract class CreateWageSeekerEvent implements WageSeekerCreateEvent {
  const factory CreateWageSeekerEvent(
      {final IndividualDetails? individualDetails,
      final SkillDetails? skillDetails,
      final LocationDetails? locationDetails,
      final FinancialDetails? financialDetails}) = _$CreateWageSeekerEventImpl;

  IndividualDetails? get individualDetails;
  SkillDetails? get skillDetails;
  LocationDetails? get locationDetails;
  FinancialDetails? get financialDetails;
  @JsonKey(ignore: true)
  _$$CreateWageSeekerEventImplCopyWith<_$CreateWageSeekerEventImpl>
      get copyWith => throw _privateConstructorUsedError;
}

/// @nodoc
abstract class _$$VerifyAdharEventImplCopyWith<$Res> {
  factory _$$VerifyAdharEventImplCopyWith(_$VerifyAdharEventImpl value,
          $Res Function(_$VerifyAdharEventImpl) then) =
      __$$VerifyAdharEventImplCopyWithImpl<$Res>;
  @useResult
  $Res call({String name, String uid});
}

/// @nodoc
class __$$VerifyAdharEventImplCopyWithImpl<$Res>
    extends _$WageSeekerCreateEventCopyWithImpl<$Res, _$VerifyAdharEventImpl>
    implements _$$VerifyAdharEventImplCopyWith<$Res> {
  __$$VerifyAdharEventImplCopyWithImpl(_$VerifyAdharEventImpl _value,
      $Res Function(_$VerifyAdharEventImpl) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? name = null,
    Object? uid = null,
  }) {
    return _then(_$VerifyAdharEventImpl(
      name: null == name
          ? _value.name
          : name // ignore: cast_nullable_to_non_nullable
              as String,
      uid: null == uid
          ? _value.uid
          : uid // ignore: cast_nullable_to_non_nullable
              as String,
    ));
  }
}

/// @nodoc

class _$VerifyAdharEventImpl implements VerifyAdharEvent {
  const _$VerifyAdharEventImpl({required this.name, required this.uid});

  @override
  final String name;
  @override
  final String uid;

  @override
  String toString() {
    return 'WageSeekerCreateEvent.verifyAdhar(name: $name, uid: $uid)';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$VerifyAdharEventImpl &&
            (identical(other.name, name) || other.name == name) &&
            (identical(other.uid, uid) || other.uid == uid));
  }

  @override
  int get hashCode => Object.hash(runtimeType, name, uid);

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$VerifyAdharEventImplCopyWith<_$VerifyAdharEventImpl> get copyWith =>
      __$$VerifyAdharEventImplCopyWithImpl<_$VerifyAdharEventImpl>(
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
    required TResult Function(String name, String uid) verifyAdhar,
    required TResult Function() dispose,
  }) {
    return verifyAdhar(name, uid);
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
    TResult? Function(String name, String uid)? verifyAdhar,
    TResult? Function()? dispose,
  }) {
    return verifyAdhar?.call(name, uid);
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
    TResult Function(String name, String uid)? verifyAdhar,
    TResult Function()? dispose,
    required TResult orElse(),
  }) {
    if (verifyAdhar != null) {
      return verifyAdhar(name, uid);
    }
    return orElse();
  }

  @override
  @optionalTypeArgs
  TResult map<TResult extends Object?>({
    required TResult Function(CreateWageSeekerEvent value) create,
    required TResult Function(VerifyAdharEvent value) verifyAdhar,
    required TResult Function(CreateWageSeekerDisposeEvent value) dispose,
  }) {
    return verifyAdhar(this);
  }

  @override
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(CreateWageSeekerEvent value)? create,
    TResult? Function(VerifyAdharEvent value)? verifyAdhar,
    TResult? Function(CreateWageSeekerDisposeEvent value)? dispose,
  }) {
    return verifyAdhar?.call(this);
  }

  @override
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(CreateWageSeekerEvent value)? create,
    TResult Function(VerifyAdharEvent value)? verifyAdhar,
    TResult Function(CreateWageSeekerDisposeEvent value)? dispose,
    required TResult orElse(),
  }) {
    if (verifyAdhar != null) {
      return verifyAdhar(this);
    }
    return orElse();
  }
}

abstract class VerifyAdharEvent implements WageSeekerCreateEvent {
  const factory VerifyAdharEvent(
      {required final String name,
      required final String uid}) = _$VerifyAdharEventImpl;

  String get name;
  String get uid;
  @JsonKey(ignore: true)
  _$$VerifyAdharEventImplCopyWith<_$VerifyAdharEventImpl> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class _$$CreateWageSeekerDisposeEventImplCopyWith<$Res> {
  factory _$$CreateWageSeekerDisposeEventImplCopyWith(
          _$CreateWageSeekerDisposeEventImpl value,
          $Res Function(_$CreateWageSeekerDisposeEventImpl) then) =
      __$$CreateWageSeekerDisposeEventImplCopyWithImpl<$Res>;
}

/// @nodoc
class __$$CreateWageSeekerDisposeEventImplCopyWithImpl<$Res>
    extends _$WageSeekerCreateEventCopyWithImpl<$Res,
        _$CreateWageSeekerDisposeEventImpl>
    implements _$$CreateWageSeekerDisposeEventImplCopyWith<$Res> {
  __$$CreateWageSeekerDisposeEventImplCopyWithImpl(
      _$CreateWageSeekerDisposeEventImpl _value,
      $Res Function(_$CreateWageSeekerDisposeEventImpl) _then)
      : super(_value, _then);
}

/// @nodoc

class _$CreateWageSeekerDisposeEventImpl
    implements CreateWageSeekerDisposeEvent {
  const _$CreateWageSeekerDisposeEventImpl();

  @override
  String toString() {
    return 'WageSeekerCreateEvent.dispose()';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$CreateWageSeekerDisposeEventImpl);
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
    required TResult Function(String name, String uid) verifyAdhar,
    required TResult Function() dispose,
  }) {
    return dispose();
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
    TResult? Function(String name, String uid)? verifyAdhar,
    TResult? Function()? dispose,
  }) {
    return dispose?.call();
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
    TResult Function(String name, String uid)? verifyAdhar,
    TResult Function()? dispose,
    required TResult orElse(),
  }) {
    if (dispose != null) {
      return dispose();
    }
    return orElse();
  }

  @override
  @optionalTypeArgs
  TResult map<TResult extends Object?>({
    required TResult Function(CreateWageSeekerEvent value) create,
    required TResult Function(VerifyAdharEvent value) verifyAdhar,
    required TResult Function(CreateWageSeekerDisposeEvent value) dispose,
  }) {
    return dispose(this);
  }

  @override
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(CreateWageSeekerEvent value)? create,
    TResult? Function(VerifyAdharEvent value)? verifyAdhar,
    TResult? Function(CreateWageSeekerDisposeEvent value)? dispose,
  }) {
    return dispose?.call(this);
  }

  @override
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(CreateWageSeekerEvent value)? create,
    TResult Function(VerifyAdharEvent value)? verifyAdhar,
    TResult Function(CreateWageSeekerDisposeEvent value)? dispose,
    required TResult orElse(),
  }) {
    if (dispose != null) {
      return dispose(this);
    }
    return orElse();
  }
}

abstract class CreateWageSeekerDisposeEvent implements WageSeekerCreateEvent {
  const factory CreateWageSeekerDisposeEvent() =
      _$CreateWageSeekerDisposeEventImpl;
}

/// @nodoc
mixin _$WageSeekerCreateState {
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function() initial,
    required TResult Function() loading,
    required TResult Function(SingleIndividualModel? individualListModel)
        loaded,
    required TResult Function(AdharCardResponse? adharCardResponse) verified,
    required TResult Function(String? error) error,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function()? initial,
    TResult? Function()? loading,
    TResult? Function(SingleIndividualModel? individualListModel)? loaded,
    TResult? Function(AdharCardResponse? adharCardResponse)? verified,
    TResult? Function(String? error)? error,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function()? initial,
    TResult Function()? loading,
    TResult Function(SingleIndividualModel? individualListModel)? loaded,
    TResult Function(AdharCardResponse? adharCardResponse)? verified,
    TResult Function(String? error)? error,
    required TResult orElse(),
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult map<TResult extends Object?>({
    required TResult Function(_Initial value) initial,
    required TResult Function(_Loading value) loading,
    required TResult Function(_Loaded value) loaded,
    required TResult Function(_Verified value) verified,
    required TResult Function(_Error value) error,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(_Initial value)? initial,
    TResult? Function(_Loading value)? loading,
    TResult? Function(_Loaded value)? loaded,
    TResult? Function(_Verified value)? verified,
    TResult? Function(_Error value)? error,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(_Initial value)? initial,
    TResult Function(_Loading value)? loading,
    TResult Function(_Loaded value)? loaded,
    TResult Function(_Verified value)? verified,
    TResult Function(_Error value)? error,
    required TResult orElse(),
  }) =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $WageSeekerCreateStateCopyWith<$Res> {
  factory $WageSeekerCreateStateCopyWith(WageSeekerCreateState value,
          $Res Function(WageSeekerCreateState) then) =
      _$WageSeekerCreateStateCopyWithImpl<$Res, WageSeekerCreateState>;
}

/// @nodoc
class _$WageSeekerCreateStateCopyWithImpl<$Res,
        $Val extends WageSeekerCreateState>
    implements $WageSeekerCreateStateCopyWith<$Res> {
  _$WageSeekerCreateStateCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;
}

/// @nodoc
abstract class _$$InitialImplCopyWith<$Res> {
  factory _$$InitialImplCopyWith(
          _$InitialImpl value, $Res Function(_$InitialImpl) then) =
      __$$InitialImplCopyWithImpl<$Res>;
}

/// @nodoc
class __$$InitialImplCopyWithImpl<$Res>
    extends _$WageSeekerCreateStateCopyWithImpl<$Res, _$InitialImpl>
    implements _$$InitialImplCopyWith<$Res> {
  __$$InitialImplCopyWithImpl(
      _$InitialImpl _value, $Res Function(_$InitialImpl) _then)
      : super(_value, _then);
}

/// @nodoc

class _$InitialImpl extends _Initial {
  const _$InitialImpl() : super._();

  @override
  String toString() {
    return 'WageSeekerCreateState.initial()';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType && other is _$InitialImpl);
  }

  @override
  int get hashCode => runtimeType.hashCode;

  @override
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function() initial,
    required TResult Function() loading,
    required TResult Function(SingleIndividualModel? individualListModel)
        loaded,
    required TResult Function(AdharCardResponse? adharCardResponse) verified,
    required TResult Function(String? error) error,
  }) {
    return initial();
  }

  @override
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function()? initial,
    TResult? Function()? loading,
    TResult? Function(SingleIndividualModel? individualListModel)? loaded,
    TResult? Function(AdharCardResponse? adharCardResponse)? verified,
    TResult? Function(String? error)? error,
  }) {
    return initial?.call();
  }

  @override
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function()? initial,
    TResult Function()? loading,
    TResult Function(SingleIndividualModel? individualListModel)? loaded,
    TResult Function(AdharCardResponse? adharCardResponse)? verified,
    TResult Function(String? error)? error,
    required TResult orElse(),
  }) {
    if (initial != null) {
      return initial();
    }
    return orElse();
  }

  @override
  @optionalTypeArgs
  TResult map<TResult extends Object?>({
    required TResult Function(_Initial value) initial,
    required TResult Function(_Loading value) loading,
    required TResult Function(_Loaded value) loaded,
    required TResult Function(_Verified value) verified,
    required TResult Function(_Error value) error,
  }) {
    return initial(this);
  }

  @override
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(_Initial value)? initial,
    TResult? Function(_Loading value)? loading,
    TResult? Function(_Loaded value)? loaded,
    TResult? Function(_Verified value)? verified,
    TResult? Function(_Error value)? error,
  }) {
    return initial?.call(this);
  }

  @override
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(_Initial value)? initial,
    TResult Function(_Loading value)? loading,
    TResult Function(_Loaded value)? loaded,
    TResult Function(_Verified value)? verified,
    TResult Function(_Error value)? error,
    required TResult orElse(),
  }) {
    if (initial != null) {
      return initial(this);
    }
    return orElse();
  }
}

abstract class _Initial extends WageSeekerCreateState {
  const factory _Initial() = _$InitialImpl;
  const _Initial._() : super._();
}

/// @nodoc
abstract class _$$LoadingImplCopyWith<$Res> {
  factory _$$LoadingImplCopyWith(
          _$LoadingImpl value, $Res Function(_$LoadingImpl) then) =
      __$$LoadingImplCopyWithImpl<$Res>;
}

/// @nodoc
class __$$LoadingImplCopyWithImpl<$Res>
    extends _$WageSeekerCreateStateCopyWithImpl<$Res, _$LoadingImpl>
    implements _$$LoadingImplCopyWith<$Res> {
  __$$LoadingImplCopyWithImpl(
      _$LoadingImpl _value, $Res Function(_$LoadingImpl) _then)
      : super(_value, _then);
}

/// @nodoc

class _$LoadingImpl extends _Loading {
  const _$LoadingImpl() : super._();

  @override
  String toString() {
    return 'WageSeekerCreateState.loading()';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType && other is _$LoadingImpl);
  }

  @override
  int get hashCode => runtimeType.hashCode;

  @override
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function() initial,
    required TResult Function() loading,
    required TResult Function(SingleIndividualModel? individualListModel)
        loaded,
    required TResult Function(AdharCardResponse? adharCardResponse) verified,
    required TResult Function(String? error) error,
  }) {
    return loading();
  }

  @override
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function()? initial,
    TResult? Function()? loading,
    TResult? Function(SingleIndividualModel? individualListModel)? loaded,
    TResult? Function(AdharCardResponse? adharCardResponse)? verified,
    TResult? Function(String? error)? error,
  }) {
    return loading?.call();
  }

  @override
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function()? initial,
    TResult Function()? loading,
    TResult Function(SingleIndividualModel? individualListModel)? loaded,
    TResult Function(AdharCardResponse? adharCardResponse)? verified,
    TResult Function(String? error)? error,
    required TResult orElse(),
  }) {
    if (loading != null) {
      return loading();
    }
    return orElse();
  }

  @override
  @optionalTypeArgs
  TResult map<TResult extends Object?>({
    required TResult Function(_Initial value) initial,
    required TResult Function(_Loading value) loading,
    required TResult Function(_Loaded value) loaded,
    required TResult Function(_Verified value) verified,
    required TResult Function(_Error value) error,
  }) {
    return loading(this);
  }

  @override
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(_Initial value)? initial,
    TResult? Function(_Loading value)? loading,
    TResult? Function(_Loaded value)? loaded,
    TResult? Function(_Verified value)? verified,
    TResult? Function(_Error value)? error,
  }) {
    return loading?.call(this);
  }

  @override
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(_Initial value)? initial,
    TResult Function(_Loading value)? loading,
    TResult Function(_Loaded value)? loaded,
    TResult Function(_Verified value)? verified,
    TResult Function(_Error value)? error,
    required TResult orElse(),
  }) {
    if (loading != null) {
      return loading(this);
    }
    return orElse();
  }
}

abstract class _Loading extends WageSeekerCreateState {
  const factory _Loading() = _$LoadingImpl;
  const _Loading._() : super._();
}

/// @nodoc
abstract class _$$LoadedImplCopyWith<$Res> {
  factory _$$LoadedImplCopyWith(
          _$LoadedImpl value, $Res Function(_$LoadedImpl) then) =
      __$$LoadedImplCopyWithImpl<$Res>;
  @useResult
  $Res call({SingleIndividualModel? individualListModel});
}

/// @nodoc
class __$$LoadedImplCopyWithImpl<$Res>
    extends _$WageSeekerCreateStateCopyWithImpl<$Res, _$LoadedImpl>
    implements _$$LoadedImplCopyWith<$Res> {
  __$$LoadedImplCopyWithImpl(
      _$LoadedImpl _value, $Res Function(_$LoadedImpl) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? individualListModel = freezed,
  }) {
    return _then(_$LoadedImpl(
      freezed == individualListModel
          ? _value.individualListModel
          : individualListModel // ignore: cast_nullable_to_non_nullable
              as SingleIndividualModel?,
    ));
  }
}

/// @nodoc

class _$LoadedImpl extends _Loaded {
  const _$LoadedImpl(this.individualListModel) : super._();

  @override
  final SingleIndividualModel? individualListModel;

  @override
  String toString() {
    return 'WageSeekerCreateState.loaded(individualListModel: $individualListModel)';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$LoadedImpl &&
            (identical(other.individualListModel, individualListModel) ||
                other.individualListModel == individualListModel));
  }

  @override
  int get hashCode => Object.hash(runtimeType, individualListModel);

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$LoadedImplCopyWith<_$LoadedImpl> get copyWith =>
      __$$LoadedImplCopyWithImpl<_$LoadedImpl>(this, _$identity);

  @override
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function() initial,
    required TResult Function() loading,
    required TResult Function(SingleIndividualModel? individualListModel)
        loaded,
    required TResult Function(AdharCardResponse? adharCardResponse) verified,
    required TResult Function(String? error) error,
  }) {
    return loaded(individualListModel);
  }

  @override
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function()? initial,
    TResult? Function()? loading,
    TResult? Function(SingleIndividualModel? individualListModel)? loaded,
    TResult? Function(AdharCardResponse? adharCardResponse)? verified,
    TResult? Function(String? error)? error,
  }) {
    return loaded?.call(individualListModel);
  }

  @override
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function()? initial,
    TResult Function()? loading,
    TResult Function(SingleIndividualModel? individualListModel)? loaded,
    TResult Function(AdharCardResponse? adharCardResponse)? verified,
    TResult Function(String? error)? error,
    required TResult orElse(),
  }) {
    if (loaded != null) {
      return loaded(individualListModel);
    }
    return orElse();
  }

  @override
  @optionalTypeArgs
  TResult map<TResult extends Object?>({
    required TResult Function(_Initial value) initial,
    required TResult Function(_Loading value) loading,
    required TResult Function(_Loaded value) loaded,
    required TResult Function(_Verified value) verified,
    required TResult Function(_Error value) error,
  }) {
    return loaded(this);
  }

  @override
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(_Initial value)? initial,
    TResult? Function(_Loading value)? loading,
    TResult? Function(_Loaded value)? loaded,
    TResult? Function(_Verified value)? verified,
    TResult? Function(_Error value)? error,
  }) {
    return loaded?.call(this);
  }

  @override
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(_Initial value)? initial,
    TResult Function(_Loading value)? loading,
    TResult Function(_Loaded value)? loaded,
    TResult Function(_Verified value)? verified,
    TResult Function(_Error value)? error,
    required TResult orElse(),
  }) {
    if (loaded != null) {
      return loaded(this);
    }
    return orElse();
  }
}

abstract class _Loaded extends WageSeekerCreateState {
  const factory _Loaded(final SingleIndividualModel? individualListModel) =
      _$LoadedImpl;
  const _Loaded._() : super._();

  SingleIndividualModel? get individualListModel;
  @JsonKey(ignore: true)
  _$$LoadedImplCopyWith<_$LoadedImpl> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class _$$VerifiedImplCopyWith<$Res> {
  factory _$$VerifiedImplCopyWith(
          _$VerifiedImpl value, $Res Function(_$VerifiedImpl) then) =
      __$$VerifiedImplCopyWithImpl<$Res>;
  @useResult
  $Res call({AdharCardResponse? adharCardResponse});

  $AdharCardResponseCopyWith<$Res>? get adharCardResponse;
}

/// @nodoc
class __$$VerifiedImplCopyWithImpl<$Res>
    extends _$WageSeekerCreateStateCopyWithImpl<$Res, _$VerifiedImpl>
    implements _$$VerifiedImplCopyWith<$Res> {
  __$$VerifiedImplCopyWithImpl(
      _$VerifiedImpl _value, $Res Function(_$VerifiedImpl) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? adharCardResponse = freezed,
  }) {
    return _then(_$VerifiedImpl(
      freezed == adharCardResponse
          ? _value.adharCardResponse
          : adharCardResponse // ignore: cast_nullable_to_non_nullable
              as AdharCardResponse?,
    ));
  }

  @override
  @pragma('vm:prefer-inline')
  $AdharCardResponseCopyWith<$Res>? get adharCardResponse {
    if (_value.adharCardResponse == null) {
      return null;
    }

    return $AdharCardResponseCopyWith<$Res>(_value.adharCardResponse!, (value) {
      return _then(_value.copyWith(adharCardResponse: value));
    });
  }
}

/// @nodoc

class _$VerifiedImpl extends _Verified {
  const _$VerifiedImpl(this.adharCardResponse) : super._();

  @override
  final AdharCardResponse? adharCardResponse;

  @override
  String toString() {
    return 'WageSeekerCreateState.verified(adharCardResponse: $adharCardResponse)';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$VerifiedImpl &&
            (identical(other.adharCardResponse, adharCardResponse) ||
                other.adharCardResponse == adharCardResponse));
  }

  @override
  int get hashCode => Object.hash(runtimeType, adharCardResponse);

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$VerifiedImplCopyWith<_$VerifiedImpl> get copyWith =>
      __$$VerifiedImplCopyWithImpl<_$VerifiedImpl>(this, _$identity);

  @override
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function() initial,
    required TResult Function() loading,
    required TResult Function(SingleIndividualModel? individualListModel)
        loaded,
    required TResult Function(AdharCardResponse? adharCardResponse) verified,
    required TResult Function(String? error) error,
  }) {
    return verified(adharCardResponse);
  }

  @override
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function()? initial,
    TResult? Function()? loading,
    TResult? Function(SingleIndividualModel? individualListModel)? loaded,
    TResult? Function(AdharCardResponse? adharCardResponse)? verified,
    TResult? Function(String? error)? error,
  }) {
    return verified?.call(adharCardResponse);
  }

  @override
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function()? initial,
    TResult Function()? loading,
    TResult Function(SingleIndividualModel? individualListModel)? loaded,
    TResult Function(AdharCardResponse? adharCardResponse)? verified,
    TResult Function(String? error)? error,
    required TResult orElse(),
  }) {
    if (verified != null) {
      return verified(adharCardResponse);
    }
    return orElse();
  }

  @override
  @optionalTypeArgs
  TResult map<TResult extends Object?>({
    required TResult Function(_Initial value) initial,
    required TResult Function(_Loading value) loading,
    required TResult Function(_Loaded value) loaded,
    required TResult Function(_Verified value) verified,
    required TResult Function(_Error value) error,
  }) {
    return verified(this);
  }

  @override
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(_Initial value)? initial,
    TResult? Function(_Loading value)? loading,
    TResult? Function(_Loaded value)? loaded,
    TResult? Function(_Verified value)? verified,
    TResult? Function(_Error value)? error,
  }) {
    return verified?.call(this);
  }

  @override
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(_Initial value)? initial,
    TResult Function(_Loading value)? loading,
    TResult Function(_Loaded value)? loaded,
    TResult Function(_Verified value)? verified,
    TResult Function(_Error value)? error,
    required TResult orElse(),
  }) {
    if (verified != null) {
      return verified(this);
    }
    return orElse();
  }
}

abstract class _Verified extends WageSeekerCreateState {
  const factory _Verified(final AdharCardResponse? adharCardResponse) =
      _$VerifiedImpl;
  const _Verified._() : super._();

  AdharCardResponse? get adharCardResponse;
  @JsonKey(ignore: true)
  _$$VerifiedImplCopyWith<_$VerifiedImpl> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class _$$ErrorImplCopyWith<$Res> {
  factory _$$ErrorImplCopyWith(
          _$ErrorImpl value, $Res Function(_$ErrorImpl) then) =
      __$$ErrorImplCopyWithImpl<$Res>;
  @useResult
  $Res call({String? error});
}

/// @nodoc
class __$$ErrorImplCopyWithImpl<$Res>
    extends _$WageSeekerCreateStateCopyWithImpl<$Res, _$ErrorImpl>
    implements _$$ErrorImplCopyWith<$Res> {
  __$$ErrorImplCopyWithImpl(
      _$ErrorImpl _value, $Res Function(_$ErrorImpl) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? error = freezed,
  }) {
    return _then(_$ErrorImpl(
      freezed == error
          ? _value.error
          : error // ignore: cast_nullable_to_non_nullable
              as String?,
    ));
  }
}

/// @nodoc

class _$ErrorImpl extends _Error {
  const _$ErrorImpl(this.error) : super._();

  @override
  final String? error;

  @override
  String toString() {
    return 'WageSeekerCreateState.error(error: $error)';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$ErrorImpl &&
            (identical(other.error, error) || other.error == error));
  }

  @override
  int get hashCode => Object.hash(runtimeType, error);

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$ErrorImplCopyWith<_$ErrorImpl> get copyWith =>
      __$$ErrorImplCopyWithImpl<_$ErrorImpl>(this, _$identity);

  @override
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function() initial,
    required TResult Function() loading,
    required TResult Function(SingleIndividualModel? individualListModel)
        loaded,
    required TResult Function(AdharCardResponse? adharCardResponse) verified,
    required TResult Function(String? error) error,
  }) {
    return error(this.error);
  }

  @override
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function()? initial,
    TResult? Function()? loading,
    TResult? Function(SingleIndividualModel? individualListModel)? loaded,
    TResult? Function(AdharCardResponse? adharCardResponse)? verified,
    TResult? Function(String? error)? error,
  }) {
    return error?.call(this.error);
  }

  @override
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function()? initial,
    TResult Function()? loading,
    TResult Function(SingleIndividualModel? individualListModel)? loaded,
    TResult Function(AdharCardResponse? adharCardResponse)? verified,
    TResult Function(String? error)? error,
    required TResult orElse(),
  }) {
    if (error != null) {
      return error(this.error);
    }
    return orElse();
  }

  @override
  @optionalTypeArgs
  TResult map<TResult extends Object?>({
    required TResult Function(_Initial value) initial,
    required TResult Function(_Loading value) loading,
    required TResult Function(_Loaded value) loaded,
    required TResult Function(_Verified value) verified,
    required TResult Function(_Error value) error,
  }) {
    return error(this);
  }

  @override
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(_Initial value)? initial,
    TResult? Function(_Loading value)? loading,
    TResult? Function(_Loaded value)? loaded,
    TResult? Function(_Verified value)? verified,
    TResult? Function(_Error value)? error,
  }) {
    return error?.call(this);
  }

  @override
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(_Initial value)? initial,
    TResult Function(_Loading value)? loading,
    TResult Function(_Loaded value)? loaded,
    TResult Function(_Verified value)? verified,
    TResult Function(_Error value)? error,
    required TResult orElse(),
  }) {
    if (error != null) {
      return error(this);
    }
    return orElse();
  }
}

abstract class _Error extends WageSeekerCreateState {
  const factory _Error(final String? error) = _$ErrorImpl;
  const _Error._() : super._();

  String? get error;
  @JsonKey(ignore: true)
  _$$ErrorImplCopyWith<_$ErrorImpl> get copyWith =>
      throw _privateConstructorUsedError;
}
