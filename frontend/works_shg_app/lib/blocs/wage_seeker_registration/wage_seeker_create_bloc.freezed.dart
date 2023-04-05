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
    'It seems like you constructed your class using `MyClass._()`. This constructor is only meant to be used by freezed and you are not supposed to need it nor use it.\nPlease check the documentation here for more information: https://github.com/rrousselGit/freezed#custom-getters-and-methods');

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
    TResult Function()? dispose,
    required TResult orElse(),
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult map<TResult extends Object?>({
    required TResult Function(CreateWageSeekerEvent value) create,
    required TResult Function(CreateWageSeekerDisposeEvent value) dispose,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(CreateWageSeekerEvent value)? create,
    TResult? Function(CreateWageSeekerDisposeEvent value)? dispose,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(CreateWageSeekerEvent value)? create,
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
abstract class _$$CreateWageSeekerEventCopyWith<$Res> {
  factory _$$CreateWageSeekerEventCopyWith(_$CreateWageSeekerEvent value,
          $Res Function(_$CreateWageSeekerEvent) then) =
      __$$CreateWageSeekerEventCopyWithImpl<$Res>;
  @useResult
  $Res call(
      {IndividualDetails? individualDetails,
      SkillDetails? skillDetails,
      LocationDetails? locationDetails,
      FinancialDetails? financialDetails});
}

/// @nodoc
class __$$CreateWageSeekerEventCopyWithImpl<$Res>
    extends _$WageSeekerCreateEventCopyWithImpl<$Res, _$CreateWageSeekerEvent>
    implements _$$CreateWageSeekerEventCopyWith<$Res> {
  __$$CreateWageSeekerEventCopyWithImpl(_$CreateWageSeekerEvent _value,
      $Res Function(_$CreateWageSeekerEvent) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? individualDetails = freezed,
    Object? skillDetails = freezed,
    Object? locationDetails = freezed,
    Object? financialDetails = freezed,
  }) {
    return _then(_$CreateWageSeekerEvent(
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

class _$CreateWageSeekerEvent implements CreateWageSeekerEvent {
  const _$CreateWageSeekerEvent(
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
  bool operator ==(dynamic other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$CreateWageSeekerEvent &&
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
  _$$CreateWageSeekerEventCopyWith<_$CreateWageSeekerEvent> get copyWith =>
      __$$CreateWageSeekerEventCopyWithImpl<_$CreateWageSeekerEvent>(
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
    required TResult Function(CreateWageSeekerDisposeEvent value) dispose,
  }) {
    return create(this);
  }

  @override
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(CreateWageSeekerEvent value)? create,
    TResult? Function(CreateWageSeekerDisposeEvent value)? dispose,
  }) {
    return create?.call(this);
  }

  @override
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(CreateWageSeekerEvent value)? create,
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
      final FinancialDetails? financialDetails}) = _$CreateWageSeekerEvent;

  IndividualDetails? get individualDetails;
  SkillDetails? get skillDetails;
  LocationDetails? get locationDetails;
  FinancialDetails? get financialDetails;
  @JsonKey(ignore: true)
  _$$CreateWageSeekerEventCopyWith<_$CreateWageSeekerEvent> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class _$$CreateWageSeekerDisposeEventCopyWith<$Res> {
  factory _$$CreateWageSeekerDisposeEventCopyWith(
          _$CreateWageSeekerDisposeEvent value,
          $Res Function(_$CreateWageSeekerDisposeEvent) then) =
      __$$CreateWageSeekerDisposeEventCopyWithImpl<$Res>;
}

/// @nodoc
class __$$CreateWageSeekerDisposeEventCopyWithImpl<$Res>
    extends _$WageSeekerCreateEventCopyWithImpl<$Res,
        _$CreateWageSeekerDisposeEvent>
    implements _$$CreateWageSeekerDisposeEventCopyWith<$Res> {
  __$$CreateWageSeekerDisposeEventCopyWithImpl(
      _$CreateWageSeekerDisposeEvent _value,
      $Res Function(_$CreateWageSeekerDisposeEvent) _then)
      : super(_value, _then);
}

/// @nodoc

class _$CreateWageSeekerDisposeEvent implements CreateWageSeekerDisposeEvent {
  const _$CreateWageSeekerDisposeEvent();

  @override
  String toString() {
    return 'WageSeekerCreateEvent.dispose()';
  }

  @override
  bool operator ==(dynamic other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$CreateWageSeekerDisposeEvent);
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
    required TResult Function(CreateWageSeekerDisposeEvent value) dispose,
  }) {
    return dispose(this);
  }

  @override
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(CreateWageSeekerEvent value)? create,
    TResult? Function(CreateWageSeekerDisposeEvent value)? dispose,
  }) {
    return dispose?.call(this);
  }

  @override
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(CreateWageSeekerEvent value)? create,
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
  const factory CreateWageSeekerDisposeEvent() = _$CreateWageSeekerDisposeEvent;
}

/// @nodoc
mixin _$WageSeekerCreateState {
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function() initial,
    required TResult Function() loading,
    required TResult Function(SingleIndividualModel? individualListModel)
        loaded,
    required TResult Function(String? error) error,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function()? initial,
    TResult? Function()? loading,
    TResult? Function(SingleIndividualModel? individualListModel)? loaded,
    TResult? Function(String? error)? error,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function()? initial,
    TResult Function()? loading,
    TResult Function(SingleIndividualModel? individualListModel)? loaded,
    TResult Function(String? error)? error,
    required TResult orElse(),
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult map<TResult extends Object?>({
    required TResult Function(_Initial value) initial,
    required TResult Function(_Loading value) loading,
    required TResult Function(_Loaded value) loaded,
    required TResult Function(_Error value) error,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(_Initial value)? initial,
    TResult? Function(_Loading value)? loading,
    TResult? Function(_Loaded value)? loaded,
    TResult? Function(_Error value)? error,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(_Initial value)? initial,
    TResult Function(_Loading value)? loading,
    TResult Function(_Loaded value)? loaded,
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
abstract class _$$_InitialCopyWith<$Res> {
  factory _$$_InitialCopyWith(
          _$_Initial value, $Res Function(_$_Initial) then) =
      __$$_InitialCopyWithImpl<$Res>;
}

/// @nodoc
class __$$_InitialCopyWithImpl<$Res>
    extends _$WageSeekerCreateStateCopyWithImpl<$Res, _$_Initial>
    implements _$$_InitialCopyWith<$Res> {
  __$$_InitialCopyWithImpl(_$_Initial _value, $Res Function(_$_Initial) _then)
      : super(_value, _then);
}

/// @nodoc

class _$_Initial extends _Initial {
  const _$_Initial() : super._();

  @override
  String toString() {
    return 'WageSeekerCreateState.initial()';
  }

  @override
  bool operator ==(dynamic other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType && other is _$_Initial);
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
  const factory _Initial() = _$_Initial;
  const _Initial._() : super._();
}

/// @nodoc
abstract class _$$_LoadingCopyWith<$Res> {
  factory _$$_LoadingCopyWith(
          _$_Loading value, $Res Function(_$_Loading) then) =
      __$$_LoadingCopyWithImpl<$Res>;
}

/// @nodoc
class __$$_LoadingCopyWithImpl<$Res>
    extends _$WageSeekerCreateStateCopyWithImpl<$Res, _$_Loading>
    implements _$$_LoadingCopyWith<$Res> {
  __$$_LoadingCopyWithImpl(_$_Loading _value, $Res Function(_$_Loading) _then)
      : super(_value, _then);
}

/// @nodoc

class _$_Loading extends _Loading {
  const _$_Loading() : super._();

  @override
  String toString() {
    return 'WageSeekerCreateState.loading()';
  }

  @override
  bool operator ==(dynamic other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType && other is _$_Loading);
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
  const factory _Loading() = _$_Loading;
  const _Loading._() : super._();
}

/// @nodoc
abstract class _$$_LoadedCopyWith<$Res> {
  factory _$$_LoadedCopyWith(_$_Loaded value, $Res Function(_$_Loaded) then) =
      __$$_LoadedCopyWithImpl<$Res>;
  @useResult
  $Res call({SingleIndividualModel? individualListModel});
}

/// @nodoc
class __$$_LoadedCopyWithImpl<$Res>
    extends _$WageSeekerCreateStateCopyWithImpl<$Res, _$_Loaded>
    implements _$$_LoadedCopyWith<$Res> {
  __$$_LoadedCopyWithImpl(_$_Loaded _value, $Res Function(_$_Loaded) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? individualListModel = freezed,
  }) {
    return _then(_$_Loaded(
      freezed == individualListModel
          ? _value.individualListModel
          : individualListModel // ignore: cast_nullable_to_non_nullable
              as SingleIndividualModel?,
    ));
  }
}

/// @nodoc

class _$_Loaded extends _Loaded {
  const _$_Loaded(this.individualListModel) : super._();

  @override
  final SingleIndividualModel? individualListModel;

  @override
  String toString() {
    return 'WageSeekerCreateState.loaded(individualListModel: $individualListModel)';
  }

  @override
  bool operator ==(dynamic other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$_Loaded &&
            (identical(other.individualListModel, individualListModel) ||
                other.individualListModel == individualListModel));
  }

  @override
  int get hashCode => Object.hash(runtimeType, individualListModel);

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$_LoadedCopyWith<_$_Loaded> get copyWith =>
      __$$_LoadedCopyWithImpl<_$_Loaded>(this, _$identity);

  @override
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function() initial,
    required TResult Function() loading,
    required TResult Function(SingleIndividualModel? individualListModel)
        loaded,
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
      _$_Loaded;
  const _Loaded._() : super._();

  SingleIndividualModel? get individualListModel;
  @JsonKey(ignore: true)
  _$$_LoadedCopyWith<_$_Loaded> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class _$$_ErrorCopyWith<$Res> {
  factory _$$_ErrorCopyWith(_$_Error value, $Res Function(_$_Error) then) =
      __$$_ErrorCopyWithImpl<$Res>;
  @useResult
  $Res call({String? error});
}

/// @nodoc
class __$$_ErrorCopyWithImpl<$Res>
    extends _$WageSeekerCreateStateCopyWithImpl<$Res, _$_Error>
    implements _$$_ErrorCopyWith<$Res> {
  __$$_ErrorCopyWithImpl(_$_Error _value, $Res Function(_$_Error) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? error = freezed,
  }) {
    return _then(_$_Error(
      freezed == error
          ? _value.error
          : error // ignore: cast_nullable_to_non_nullable
              as String?,
    ));
  }
}

/// @nodoc

class _$_Error extends _Error {
  const _$_Error(this.error) : super._();

  @override
  final String? error;

  @override
  String toString() {
    return 'WageSeekerCreateState.error(error: $error)';
  }

  @override
  bool operator ==(dynamic other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$_Error &&
            (identical(other.error, error) || other.error == error));
  }

  @override
  int get hashCode => Object.hash(runtimeType, error);

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$_ErrorCopyWith<_$_Error> get copyWith =>
      __$$_ErrorCopyWithImpl<_$_Error>(this, _$identity);

  @override
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function() initial,
    required TResult Function() loading,
    required TResult Function(SingleIndividualModel? individualListModel)
        loaded,
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
  const factory _Error(final String? error) = _$_Error;
  const _Error._() : super._();

  String? get error;
  @JsonKey(ignore: true)
  _$$_ErrorCopyWith<_$_Error> get copyWith =>
      throw _privateConstructorUsedError;
}
