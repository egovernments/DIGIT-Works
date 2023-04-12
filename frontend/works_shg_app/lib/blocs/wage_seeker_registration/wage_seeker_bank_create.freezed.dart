// coverage:ignore-file
// GENERATED CODE - DO NOT MODIFY BY HAND
// ignore_for_file: type=lint
// ignore_for_file: unused_element, deprecated_member_use, deprecated_member_use_from_same_package, use_function_type_syntax_for_parameters, unnecessary_const, avoid_init_to_null, invalid_override_different_default_values_named, prefer_expression_function_bodies, annotate_overrides, invalid_annotation_target, unnecessary_question_mark

part of 'wage_seeker_bank_create.dart';

// **************************************************************************
// FreezedGenerator
// **************************************************************************

T _$identity<T>(T value) => value;

final _privateConstructorUsedError = UnsupportedError(
    'It seems like you constructed your class using `MyClass._()`. This constructor is only meant to be used by freezed and you are not supposed to need it nor use it.\nPlease check the documentation here for more information: https://github.com/rrousselGit/freezed#custom-getters-and-methods');

/// @nodoc
mixin _$WageSeekerBankCreateEvent {
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function(
            String? tenantId,
            String? accountHolderName,
            String? accountNo,
            String? accountType,
            String? ifscCode,
            String? referenceId,
            String? indId,
            String? bankName)
        create,
    required TResult Function() dispose,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function(
            String? tenantId,
            String? accountHolderName,
            String? accountNo,
            String? accountType,
            String? ifscCode,
            String? referenceId,
            String? indId,
            String? bankName)?
        create,
    TResult? Function()? dispose,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function(
            String? tenantId,
            String? accountHolderName,
            String? accountNo,
            String? accountType,
            String? ifscCode,
            String? referenceId,
            String? indId,
            String? bankName)?
        create,
    TResult Function()? dispose,
    required TResult orElse(),
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult map<TResult extends Object?>({
    required TResult Function(CreateBankWageSeekerEvent value) create,
    required TResult Function(CreateBankWageSeekerDisposeEvent value) dispose,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(CreateBankWageSeekerEvent value)? create,
    TResult? Function(CreateBankWageSeekerDisposeEvent value)? dispose,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(CreateBankWageSeekerEvent value)? create,
    TResult Function(CreateBankWageSeekerDisposeEvent value)? dispose,
    required TResult orElse(),
  }) =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $WageSeekerBankCreateEventCopyWith<$Res> {
  factory $WageSeekerBankCreateEventCopyWith(WageSeekerBankCreateEvent value,
          $Res Function(WageSeekerBankCreateEvent) then) =
      _$WageSeekerBankCreateEventCopyWithImpl<$Res, WageSeekerBankCreateEvent>;
}

/// @nodoc
class _$WageSeekerBankCreateEventCopyWithImpl<$Res,
        $Val extends WageSeekerBankCreateEvent>
    implements $WageSeekerBankCreateEventCopyWith<$Res> {
  _$WageSeekerBankCreateEventCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;
}

/// @nodoc
abstract class _$$CreateBankWageSeekerEventCopyWith<$Res> {
  factory _$$CreateBankWageSeekerEventCopyWith(
          _$CreateBankWageSeekerEvent value,
          $Res Function(_$CreateBankWageSeekerEvent) then) =
      __$$CreateBankWageSeekerEventCopyWithImpl<$Res>;
  @useResult
  $Res call(
      {String? tenantId,
      String? accountHolderName,
      String? accountNo,
      String? accountType,
      String? ifscCode,
      String? referenceId,
      String? indId,
      String? bankName});
}

/// @nodoc
class __$$CreateBankWageSeekerEventCopyWithImpl<$Res>
    extends _$WageSeekerBankCreateEventCopyWithImpl<$Res,
        _$CreateBankWageSeekerEvent>
    implements _$$CreateBankWageSeekerEventCopyWith<$Res> {
  __$$CreateBankWageSeekerEventCopyWithImpl(_$CreateBankWageSeekerEvent _value,
      $Res Function(_$CreateBankWageSeekerEvent) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? tenantId = freezed,
    Object? accountHolderName = freezed,
    Object? accountNo = freezed,
    Object? accountType = freezed,
    Object? ifscCode = freezed,
    Object? referenceId = freezed,
    Object? indId = freezed,
    Object? bankName = freezed,
  }) {
    return _then(_$CreateBankWageSeekerEvent(
      tenantId: freezed == tenantId
          ? _value.tenantId
          : tenantId // ignore: cast_nullable_to_non_nullable
              as String?,
      accountHolderName: freezed == accountHolderName
          ? _value.accountHolderName
          : accountHolderName // ignore: cast_nullable_to_non_nullable
              as String?,
      accountNo: freezed == accountNo
          ? _value.accountNo
          : accountNo // ignore: cast_nullable_to_non_nullable
              as String?,
      accountType: freezed == accountType
          ? _value.accountType
          : accountType // ignore: cast_nullable_to_non_nullable
              as String?,
      ifscCode: freezed == ifscCode
          ? _value.ifscCode
          : ifscCode // ignore: cast_nullable_to_non_nullable
              as String?,
      referenceId: freezed == referenceId
          ? _value.referenceId
          : referenceId // ignore: cast_nullable_to_non_nullable
              as String?,
      indId: freezed == indId
          ? _value.indId
          : indId // ignore: cast_nullable_to_non_nullable
              as String?,
      bankName: freezed == bankName
          ? _value.bankName
          : bankName // ignore: cast_nullable_to_non_nullable
              as String?,
    ));
  }
}

/// @nodoc

class _$CreateBankWageSeekerEvent implements CreateBankWageSeekerEvent {
  const _$CreateBankWageSeekerEvent(
      {this.tenantId,
      this.accountHolderName,
      this.accountNo,
      this.accountType,
      this.ifscCode,
      this.referenceId,
      this.indId,
      this.bankName});

  @override
  final String? tenantId;
  @override
  final String? accountHolderName;
  @override
  final String? accountNo;
  @override
  final String? accountType;
  @override
  final String? ifscCode;
  @override
  final String? referenceId;
  @override
  final String? indId;
  @override
  final String? bankName;

  @override
  String toString() {
    return 'WageSeekerBankCreateEvent.create(tenantId: $tenantId, accountHolderName: $accountHolderName, accountNo: $accountNo, accountType: $accountType, ifscCode: $ifscCode, referenceId: $referenceId, indId: $indId, bankName: $bankName)';
  }

  @override
  bool operator ==(dynamic other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$CreateBankWageSeekerEvent &&
            (identical(other.tenantId, tenantId) ||
                other.tenantId == tenantId) &&
            (identical(other.accountHolderName, accountHolderName) ||
                other.accountHolderName == accountHolderName) &&
            (identical(other.accountNo, accountNo) ||
                other.accountNo == accountNo) &&
            (identical(other.accountType, accountType) ||
                other.accountType == accountType) &&
            (identical(other.ifscCode, ifscCode) ||
                other.ifscCode == ifscCode) &&
            (identical(other.referenceId, referenceId) ||
                other.referenceId == referenceId) &&
            (identical(other.indId, indId) || other.indId == indId) &&
            (identical(other.bankName, bankName) ||
                other.bankName == bankName));
  }

  @override
  int get hashCode => Object.hash(runtimeType, tenantId, accountHolderName,
      accountNo, accountType, ifscCode, referenceId, indId, bankName);

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$CreateBankWageSeekerEventCopyWith<_$CreateBankWageSeekerEvent>
      get copyWith => __$$CreateBankWageSeekerEventCopyWithImpl<
          _$CreateBankWageSeekerEvent>(this, _$identity);

  @override
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function(
            String? tenantId,
            String? accountHolderName,
            String? accountNo,
            String? accountType,
            String? ifscCode,
            String? referenceId,
            String? indId,
            String? bankName)
        create,
    required TResult Function() dispose,
  }) {
    return create(tenantId, accountHolderName, accountNo, accountType, ifscCode,
        referenceId, indId, bankName);
  }

  @override
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function(
            String? tenantId,
            String? accountHolderName,
            String? accountNo,
            String? accountType,
            String? ifscCode,
            String? referenceId,
            String? indId,
            String? bankName)?
        create,
    TResult? Function()? dispose,
  }) {
    return create?.call(tenantId, accountHolderName, accountNo, accountType,
        ifscCode, referenceId, indId, bankName);
  }

  @override
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function(
            String? tenantId,
            String? accountHolderName,
            String? accountNo,
            String? accountType,
            String? ifscCode,
            String? referenceId,
            String? indId,
            String? bankName)?
        create,
    TResult Function()? dispose,
    required TResult orElse(),
  }) {
    if (create != null) {
      return create(tenantId, accountHolderName, accountNo, accountType,
          ifscCode, referenceId, indId, bankName);
    }
    return orElse();
  }

  @override
  @optionalTypeArgs
  TResult map<TResult extends Object?>({
    required TResult Function(CreateBankWageSeekerEvent value) create,
    required TResult Function(CreateBankWageSeekerDisposeEvent value) dispose,
  }) {
    return create(this);
  }

  @override
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(CreateBankWageSeekerEvent value)? create,
    TResult? Function(CreateBankWageSeekerDisposeEvent value)? dispose,
  }) {
    return create?.call(this);
  }

  @override
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(CreateBankWageSeekerEvent value)? create,
    TResult Function(CreateBankWageSeekerDisposeEvent value)? dispose,
    required TResult orElse(),
  }) {
    if (create != null) {
      return create(this);
    }
    return orElse();
  }
}

abstract class CreateBankWageSeekerEvent implements WageSeekerBankCreateEvent {
  const factory CreateBankWageSeekerEvent(
      {final String? tenantId,
      final String? accountHolderName,
      final String? accountNo,
      final String? accountType,
      final String? ifscCode,
      final String? referenceId,
      final String? indId,
      final String? bankName}) = _$CreateBankWageSeekerEvent;

  String? get tenantId;
  String? get accountHolderName;
  String? get accountNo;
  String? get accountType;
  String? get ifscCode;
  String? get referenceId;
  String? get indId;
  String? get bankName;
  @JsonKey(ignore: true)
  _$$CreateBankWageSeekerEventCopyWith<_$CreateBankWageSeekerEvent>
      get copyWith => throw _privateConstructorUsedError;
}

/// @nodoc
abstract class _$$CreateBankWageSeekerDisposeEventCopyWith<$Res> {
  factory _$$CreateBankWageSeekerDisposeEventCopyWith(
          _$CreateBankWageSeekerDisposeEvent value,
          $Res Function(_$CreateBankWageSeekerDisposeEvent) then) =
      __$$CreateBankWageSeekerDisposeEventCopyWithImpl<$Res>;
}

/// @nodoc
class __$$CreateBankWageSeekerDisposeEventCopyWithImpl<$Res>
    extends _$WageSeekerBankCreateEventCopyWithImpl<$Res,
        _$CreateBankWageSeekerDisposeEvent>
    implements _$$CreateBankWageSeekerDisposeEventCopyWith<$Res> {
  __$$CreateBankWageSeekerDisposeEventCopyWithImpl(
      _$CreateBankWageSeekerDisposeEvent _value,
      $Res Function(_$CreateBankWageSeekerDisposeEvent) _then)
      : super(_value, _then);
}

/// @nodoc

class _$CreateBankWageSeekerDisposeEvent
    implements CreateBankWageSeekerDisposeEvent {
  const _$CreateBankWageSeekerDisposeEvent();

  @override
  String toString() {
    return 'WageSeekerBankCreateEvent.dispose()';
  }

  @override
  bool operator ==(dynamic other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$CreateBankWageSeekerDisposeEvent);
  }

  @override
  int get hashCode => runtimeType.hashCode;

  @override
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function(
            String? tenantId,
            String? accountHolderName,
            String? accountNo,
            String? accountType,
            String? ifscCode,
            String? referenceId,
            String? indId,
            String? bankName)
        create,
    required TResult Function() dispose,
  }) {
    return dispose();
  }

  @override
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function(
            String? tenantId,
            String? accountHolderName,
            String? accountNo,
            String? accountType,
            String? ifscCode,
            String? referenceId,
            String? indId,
            String? bankName)?
        create,
    TResult? Function()? dispose,
  }) {
    return dispose?.call();
  }

  @override
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function(
            String? tenantId,
            String? accountHolderName,
            String? accountNo,
            String? accountType,
            String? ifscCode,
            String? referenceId,
            String? indId,
            String? bankName)?
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
    required TResult Function(CreateBankWageSeekerEvent value) create,
    required TResult Function(CreateBankWageSeekerDisposeEvent value) dispose,
  }) {
    return dispose(this);
  }

  @override
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(CreateBankWageSeekerEvent value)? create,
    TResult? Function(CreateBankWageSeekerDisposeEvent value)? dispose,
  }) {
    return dispose?.call(this);
  }

  @override
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(CreateBankWageSeekerEvent value)? create,
    TResult Function(CreateBankWageSeekerDisposeEvent value)? dispose,
    required TResult orElse(),
  }) {
    if (dispose != null) {
      return dispose(this);
    }
    return orElse();
  }
}

abstract class CreateBankWageSeekerDisposeEvent
    implements WageSeekerBankCreateEvent {
  const factory CreateBankWageSeekerDisposeEvent() =
      _$CreateBankWageSeekerDisposeEvent;
}

/// @nodoc
mixin _$WageSeekerBankCreateState {
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function() initial,
    required TResult Function() loading,
    required TResult Function(BankingDetailsModel? bankingDetailsModel,
            BankAccounts? bankAccounts)
        loaded,
    required TResult Function(String? error) error,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function()? initial,
    TResult? Function()? loading,
    TResult? Function(BankingDetailsModel? bankingDetailsModel,
            BankAccounts? bankAccounts)?
        loaded,
    TResult? Function(String? error)? error,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function()? initial,
    TResult Function()? loading,
    TResult Function(BankingDetailsModel? bankingDetailsModel,
            BankAccounts? bankAccounts)?
        loaded,
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
abstract class $WageSeekerBankCreateStateCopyWith<$Res> {
  factory $WageSeekerBankCreateStateCopyWith(WageSeekerBankCreateState value,
          $Res Function(WageSeekerBankCreateState) then) =
      _$WageSeekerBankCreateStateCopyWithImpl<$Res, WageSeekerBankCreateState>;
}

/// @nodoc
class _$WageSeekerBankCreateStateCopyWithImpl<$Res,
        $Val extends WageSeekerBankCreateState>
    implements $WageSeekerBankCreateStateCopyWith<$Res> {
  _$WageSeekerBankCreateStateCopyWithImpl(this._value, this._then);

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
    extends _$WageSeekerBankCreateStateCopyWithImpl<$Res, _$_Initial>
    implements _$$_InitialCopyWith<$Res> {
  __$$_InitialCopyWithImpl(_$_Initial _value, $Res Function(_$_Initial) _then)
      : super(_value, _then);
}

/// @nodoc

class _$_Initial extends _Initial {
  const _$_Initial() : super._();

  @override
  String toString() {
    return 'WageSeekerBankCreateState.initial()';
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
    required TResult Function(BankingDetailsModel? bankingDetailsModel,
            BankAccounts? bankAccounts)
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
    TResult? Function(BankingDetailsModel? bankingDetailsModel,
            BankAccounts? bankAccounts)?
        loaded,
    TResult? Function(String? error)? error,
  }) {
    return initial?.call();
  }

  @override
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function()? initial,
    TResult Function()? loading,
    TResult Function(BankingDetailsModel? bankingDetailsModel,
            BankAccounts? bankAccounts)?
        loaded,
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

abstract class _Initial extends WageSeekerBankCreateState {
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
    extends _$WageSeekerBankCreateStateCopyWithImpl<$Res, _$_Loading>
    implements _$$_LoadingCopyWith<$Res> {
  __$$_LoadingCopyWithImpl(_$_Loading _value, $Res Function(_$_Loading) _then)
      : super(_value, _then);
}

/// @nodoc

class _$_Loading extends _Loading {
  const _$_Loading() : super._();

  @override
  String toString() {
    return 'WageSeekerBankCreateState.loading()';
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
    required TResult Function(BankingDetailsModel? bankingDetailsModel,
            BankAccounts? bankAccounts)
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
    TResult? Function(BankingDetailsModel? bankingDetailsModel,
            BankAccounts? bankAccounts)?
        loaded,
    TResult? Function(String? error)? error,
  }) {
    return loading?.call();
  }

  @override
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function()? initial,
    TResult Function()? loading,
    TResult Function(BankingDetailsModel? bankingDetailsModel,
            BankAccounts? bankAccounts)?
        loaded,
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

abstract class _Loading extends WageSeekerBankCreateState {
  const factory _Loading() = _$_Loading;
  const _Loading._() : super._();
}

/// @nodoc
abstract class _$$_LoadedCopyWith<$Res> {
  factory _$$_LoadedCopyWith(_$_Loaded value, $Res Function(_$_Loaded) then) =
      __$$_LoadedCopyWithImpl<$Res>;
  @useResult
  $Res call(
      {BankingDetailsModel? bankingDetailsModel, BankAccounts? bankAccounts});
}

/// @nodoc
class __$$_LoadedCopyWithImpl<$Res>
    extends _$WageSeekerBankCreateStateCopyWithImpl<$Res, _$_Loaded>
    implements _$$_LoadedCopyWith<$Res> {
  __$$_LoadedCopyWithImpl(_$_Loaded _value, $Res Function(_$_Loaded) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? bankingDetailsModel = freezed,
    Object? bankAccounts = freezed,
  }) {
    return _then(_$_Loaded(
      freezed == bankingDetailsModel
          ? _value.bankingDetailsModel
          : bankingDetailsModel // ignore: cast_nullable_to_non_nullable
              as BankingDetailsModel?,
      freezed == bankAccounts
          ? _value.bankAccounts
          : bankAccounts // ignore: cast_nullable_to_non_nullable
              as BankAccounts?,
    ));
  }
}

/// @nodoc

class _$_Loaded extends _Loaded {
  const _$_Loaded(this.bankingDetailsModel, this.bankAccounts) : super._();

  @override
  final BankingDetailsModel? bankingDetailsModel;
  @override
  final BankAccounts? bankAccounts;

  @override
  String toString() {
    return 'WageSeekerBankCreateState.loaded(bankingDetailsModel: $bankingDetailsModel, bankAccounts: $bankAccounts)';
  }

  @override
  bool operator ==(dynamic other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$_Loaded &&
            (identical(other.bankingDetailsModel, bankingDetailsModel) ||
                other.bankingDetailsModel == bankingDetailsModel) &&
            (identical(other.bankAccounts, bankAccounts) ||
                other.bankAccounts == bankAccounts));
  }

  @override
  int get hashCode =>
      Object.hash(runtimeType, bankingDetailsModel, bankAccounts);

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
    required TResult Function(BankingDetailsModel? bankingDetailsModel,
            BankAccounts? bankAccounts)
        loaded,
    required TResult Function(String? error) error,
  }) {
    return loaded(bankingDetailsModel, bankAccounts);
  }

  @override
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function()? initial,
    TResult? Function()? loading,
    TResult? Function(BankingDetailsModel? bankingDetailsModel,
            BankAccounts? bankAccounts)?
        loaded,
    TResult? Function(String? error)? error,
  }) {
    return loaded?.call(bankingDetailsModel, bankAccounts);
  }

  @override
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function()? initial,
    TResult Function()? loading,
    TResult Function(BankingDetailsModel? bankingDetailsModel,
            BankAccounts? bankAccounts)?
        loaded,
    TResult Function(String? error)? error,
    required TResult orElse(),
  }) {
    if (loaded != null) {
      return loaded(bankingDetailsModel, bankAccounts);
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

abstract class _Loaded extends WageSeekerBankCreateState {
  const factory _Loaded(final BankingDetailsModel? bankingDetailsModel,
      final BankAccounts? bankAccounts) = _$_Loaded;
  const _Loaded._() : super._();

  BankingDetailsModel? get bankingDetailsModel;
  BankAccounts? get bankAccounts;
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
    extends _$WageSeekerBankCreateStateCopyWithImpl<$Res, _$_Error>
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
    return 'WageSeekerBankCreateState.error(error: $error)';
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
    required TResult Function(BankingDetailsModel? bankingDetailsModel,
            BankAccounts? bankAccounts)
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
    TResult? Function(BankingDetailsModel? bankingDetailsModel,
            BankAccounts? bankAccounts)?
        loaded,
    TResult? Function(String? error)? error,
  }) {
    return error?.call(this.error);
  }

  @override
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function()? initial,
    TResult Function()? loading,
    TResult Function(BankingDetailsModel? bankingDetailsModel,
            BankAccounts? bankAccounts)?
        loaded,
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

abstract class _Error extends WageSeekerBankCreateState {
  const factory _Error(final String? error) = _$_Error;
  const _Error._() : super._();

  String? get error;
  @JsonKey(ignore: true)
  _$$_ErrorCopyWith<_$_Error> get copyWith =>
      throw _privateConstructorUsedError;
}
