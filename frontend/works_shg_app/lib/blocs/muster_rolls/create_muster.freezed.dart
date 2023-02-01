// coverage:ignore-file
// GENERATED CODE - DO NOT MODIFY BY HAND
// ignore_for_file: type=lint
// ignore_for_file: unused_element, deprecated_member_use, deprecated_member_use_from_same_package, use_function_type_syntax_for_parameters, unnecessary_const, avoid_init_to_null, invalid_override_different_default_values_named, prefer_expression_function_bodies, annotate_overrides, invalid_annotation_target

part of 'create_muster.dart';

// **************************************************************************
// FreezedGenerator
// **************************************************************************

T _$identity<T>(T value) => value;

final _privateConstructorUsedError = UnsupportedError(
    'It seems like you constructed your class using `MyClass._()`. This constructor is only meant to be used by freezed and you are not supposed to need it nor use it.\nPlease check the documentation here for more information: https://github.com/rrousselGit/freezed#custom-getters-and-methods');

/// @nodoc
mixin _$MusterCreateEvent {
  String get tenantId => throw _privateConstructorUsedError;
  String get registerId => throw _privateConstructorUsedError;
  int get startDate => throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function(String tenantId, String registerId, int startDate)
        create,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function(String tenantId, String registerId, int startDate)?
        create,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function(String tenantId, String registerId, int startDate)? create,
    required TResult orElse(),
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult map<TResult extends Object?>({
    required TResult Function(CreateMusterEvent value) create,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(CreateMusterEvent value)? create,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(CreateMusterEvent value)? create,
    required TResult orElse(),
  }) =>
      throw _privateConstructorUsedError;

  @JsonKey(ignore: true)
  $MusterCreateEventCopyWith<MusterCreateEvent> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $MusterCreateEventCopyWith<$Res> {
  factory $MusterCreateEventCopyWith(
          MusterCreateEvent value, $Res Function(MusterCreateEvent) then) =
      _$MusterCreateEventCopyWithImpl<$Res, MusterCreateEvent>;
  @useResult
  $Res call({String tenantId, String registerId, int startDate});
}

/// @nodoc
class _$MusterCreateEventCopyWithImpl<$Res, $Val extends MusterCreateEvent>
    implements $MusterCreateEventCopyWith<$Res> {
  _$MusterCreateEventCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? tenantId = null,
    Object? registerId = null,
    Object? startDate = null,
  }) {
    return _then(_value.copyWith(
      tenantId: null == tenantId
          ? _value.tenantId
          : tenantId // ignore: cast_nullable_to_non_nullable
              as String,
      registerId: null == registerId
          ? _value.registerId
          : registerId // ignore: cast_nullable_to_non_nullable
              as String,
      startDate: null == startDate
          ? _value.startDate
          : startDate // ignore: cast_nullable_to_non_nullable
              as int,
    ) as $Val);
  }
}

/// @nodoc
abstract class _$$CreateMusterEventCopyWith<$Res>
    implements $MusterCreateEventCopyWith<$Res> {
  factory _$$CreateMusterEventCopyWith(
          _$CreateMusterEvent value, $Res Function(_$CreateMusterEvent) then) =
      __$$CreateMusterEventCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call({String tenantId, String registerId, int startDate});
}

/// @nodoc
class __$$CreateMusterEventCopyWithImpl<$Res>
    extends _$MusterCreateEventCopyWithImpl<$Res, _$CreateMusterEvent>
    implements _$$CreateMusterEventCopyWith<$Res> {
  __$$CreateMusterEventCopyWithImpl(
      _$CreateMusterEvent _value, $Res Function(_$CreateMusterEvent) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? tenantId = null,
    Object? registerId = null,
    Object? startDate = null,
  }) {
    return _then(_$CreateMusterEvent(
      tenantId: null == tenantId
          ? _value.tenantId
          : tenantId // ignore: cast_nullable_to_non_nullable
              as String,
      registerId: null == registerId
          ? _value.registerId
          : registerId // ignore: cast_nullable_to_non_nullable
              as String,
      startDate: null == startDate
          ? _value.startDate
          : startDate // ignore: cast_nullable_to_non_nullable
              as int,
    ));
  }
}

/// @nodoc

class _$CreateMusterEvent implements CreateMusterEvent {
  const _$CreateMusterEvent(
      {required this.tenantId,
      required this.registerId,
      required this.startDate});

  @override
  final String tenantId;
  @override
  final String registerId;
  @override
  final int startDate;

  @override
  String toString() {
    return 'MusterCreateEvent.create(tenantId: $tenantId, registerId: $registerId, startDate: $startDate)';
  }

  @override
  bool operator ==(dynamic other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$CreateMusterEvent &&
            (identical(other.tenantId, tenantId) ||
                other.tenantId == tenantId) &&
            (identical(other.registerId, registerId) ||
                other.registerId == registerId) &&
            (identical(other.startDate, startDate) ||
                other.startDate == startDate));
  }

  @override
  int get hashCode => Object.hash(runtimeType, tenantId, registerId, startDate);

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$CreateMusterEventCopyWith<_$CreateMusterEvent> get copyWith =>
      __$$CreateMusterEventCopyWithImpl<_$CreateMusterEvent>(this, _$identity);

  @override
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function(String tenantId, String registerId, int startDate)
        create,
  }) {
    return create(tenantId, registerId, startDate);
  }

  @override
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function(String tenantId, String registerId, int startDate)?
        create,
  }) {
    return create?.call(tenantId, registerId, startDate);
  }

  @override
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function(String tenantId, String registerId, int startDate)? create,
    required TResult orElse(),
  }) {
    if (create != null) {
      return create(tenantId, registerId, startDate);
    }
    return orElse();
  }

  @override
  @optionalTypeArgs
  TResult map<TResult extends Object?>({
    required TResult Function(CreateMusterEvent value) create,
  }) {
    return create(this);
  }

  @override
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(CreateMusterEvent value)? create,
  }) {
    return create?.call(this);
  }

  @override
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(CreateMusterEvent value)? create,
    required TResult orElse(),
  }) {
    if (create != null) {
      return create(this);
    }
    return orElse();
  }
}

abstract class CreateMusterEvent implements MusterCreateEvent {
  const factory CreateMusterEvent(
      {required final String tenantId,
      required final String registerId,
      required final int startDate}) = _$CreateMusterEvent;

  @override
  String get tenantId;
  @override
  String get registerId;
  @override
  int get startDate;
  @override
  @JsonKey(ignore: true)
  _$$CreateMusterEventCopyWith<_$CreateMusterEvent> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
mixin _$MusterCreateState {
  bool get loading => throw _privateConstructorUsedError;
  MusterRollsModel? get musterRollsModel => throw _privateConstructorUsedError;

  @JsonKey(ignore: true)
  $MusterCreateStateCopyWith<MusterCreateState> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $MusterCreateStateCopyWith<$Res> {
  factory $MusterCreateStateCopyWith(
          MusterCreateState value, $Res Function(MusterCreateState) then) =
      _$MusterCreateStateCopyWithImpl<$Res, MusterCreateState>;
  @useResult
  $Res call({bool loading, MusterRollsModel? musterRollsModel});

  $MusterRollsModelCopyWith<$Res>? get musterRollsModel;
}

/// @nodoc
class _$MusterCreateStateCopyWithImpl<$Res, $Val extends MusterCreateState>
    implements $MusterCreateStateCopyWith<$Res> {
  _$MusterCreateStateCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? loading = null,
    Object? musterRollsModel = freezed,
  }) {
    return _then(_value.copyWith(
      loading: null == loading
          ? _value.loading
          : loading // ignore: cast_nullable_to_non_nullable
              as bool,
      musterRollsModel: freezed == musterRollsModel
          ? _value.musterRollsModel
          : musterRollsModel // ignore: cast_nullable_to_non_nullable
              as MusterRollsModel?,
    ) as $Val);
  }

  @override
  @pragma('vm:prefer-inline')
  $MusterRollsModelCopyWith<$Res>? get musterRollsModel {
    if (_value.musterRollsModel == null) {
      return null;
    }

    return $MusterRollsModelCopyWith<$Res>(_value.musterRollsModel!, (value) {
      return _then(_value.copyWith(musterRollsModel: value) as $Val);
    });
  }
}

/// @nodoc
abstract class _$$_MusterCreateStateCopyWith<$Res>
    implements $MusterCreateStateCopyWith<$Res> {
  factory _$$_MusterCreateStateCopyWith(_$_MusterCreateState value,
          $Res Function(_$_MusterCreateState) then) =
      __$$_MusterCreateStateCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call({bool loading, MusterRollsModel? musterRollsModel});

  @override
  $MusterRollsModelCopyWith<$Res>? get musterRollsModel;
}

/// @nodoc
class __$$_MusterCreateStateCopyWithImpl<$Res>
    extends _$MusterCreateStateCopyWithImpl<$Res, _$_MusterCreateState>
    implements _$$_MusterCreateStateCopyWith<$Res> {
  __$$_MusterCreateStateCopyWithImpl(
      _$_MusterCreateState _value, $Res Function(_$_MusterCreateState) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? loading = null,
    Object? musterRollsModel = freezed,
  }) {
    return _then(_$_MusterCreateState(
      loading: null == loading
          ? _value.loading
          : loading // ignore: cast_nullable_to_non_nullable
              as bool,
      musterRollsModel: freezed == musterRollsModel
          ? _value.musterRollsModel
          : musterRollsModel // ignore: cast_nullable_to_non_nullable
              as MusterRollsModel?,
    ));
  }
}

/// @nodoc

class _$_MusterCreateState extends _MusterCreateState {
  const _$_MusterCreateState({this.loading = false, this.musterRollsModel})
      : super._();

  @override
  @JsonKey()
  final bool loading;
  @override
  final MusterRollsModel? musterRollsModel;

  @override
  String toString() {
    return 'MusterCreateState(loading: $loading, musterRollsModel: $musterRollsModel)';
  }

  @override
  bool operator ==(dynamic other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$_MusterCreateState &&
            (identical(other.loading, loading) || other.loading == loading) &&
            (identical(other.musterRollsModel, musterRollsModel) ||
                other.musterRollsModel == musterRollsModel));
  }

  @override
  int get hashCode => Object.hash(runtimeType, loading, musterRollsModel);

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$_MusterCreateStateCopyWith<_$_MusterCreateState> get copyWith =>
      __$$_MusterCreateStateCopyWithImpl<_$_MusterCreateState>(
          this, _$identity);
}

abstract class _MusterCreateState extends MusterCreateState {
  const factory _MusterCreateState(
      {final bool loading,
      final MusterRollsModel? musterRollsModel}) = _$_MusterCreateState;
  const _MusterCreateState._() : super._();

  @override
  bool get loading;
  @override
  MusterRollsModel? get musterRollsModel;
  @override
  @JsonKey(ignore: true)
  _$$_MusterCreateStateCopyWith<_$_MusterCreateState> get copyWith =>
      throw _privateConstructorUsedError;
}
