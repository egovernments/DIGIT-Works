// coverage:ignore-file
// GENERATED CODE - DO NOT MODIFY BY HAND
// ignore_for_file: type=lint
// ignore_for_file: unused_element, deprecated_member_use, deprecated_member_use_from_same_package, use_function_type_syntax_for_parameters, unnecessary_const, avoid_init_to_null, invalid_override_different_default_values_named, prefer_expression_function_bodies, annotate_overrides, invalid_annotation_target

part of 'search_muster_roll.dart';

// **************************************************************************
// FreezedGenerator
// **************************************************************************

T _$identity<T>(T value) => value;

final _privateConstructorUsedError = UnsupportedError(
    'It seems like you constructed your class using `MyClass._()`. This constructor is only meant to be used by freezed and you are not supposed to need it nor use it.\nPlease check the documentation here for more information: https://github.com/rrousselGit/freezed#custom-getters-and-methods');

/// @nodoc
mixin _$MusterRollSearchEvent {
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function() search,
    required TResult Function(String id) individualSearch,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function()? search,
    TResult? Function(String id)? individualSearch,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function()? search,
    TResult Function(String id)? individualSearch,
    required TResult orElse(),
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult map<TResult extends Object?>({
    required TResult Function(SearchMusterRollEvent value) search,
    required TResult Function(SearchIndividualMusterRollEvent value)
        individualSearch,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(SearchMusterRollEvent value)? search,
    TResult? Function(SearchIndividualMusterRollEvent value)? individualSearch,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(SearchMusterRollEvent value)? search,
    TResult Function(SearchIndividualMusterRollEvent value)? individualSearch,
    required TResult orElse(),
  }) =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $MusterRollSearchEventCopyWith<$Res> {
  factory $MusterRollSearchEventCopyWith(MusterRollSearchEvent value,
          $Res Function(MusterRollSearchEvent) then) =
      _$MusterRollSearchEventCopyWithImpl<$Res, MusterRollSearchEvent>;
}

/// @nodoc
class _$MusterRollSearchEventCopyWithImpl<$Res,
        $Val extends MusterRollSearchEvent>
    implements $MusterRollSearchEventCopyWith<$Res> {
  _$MusterRollSearchEventCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;
}

/// @nodoc
abstract class _$$SearchMusterRollEventCopyWith<$Res> {
  factory _$$SearchMusterRollEventCopyWith(_$SearchMusterRollEvent value,
          $Res Function(_$SearchMusterRollEvent) then) =
      __$$SearchMusterRollEventCopyWithImpl<$Res>;
}

/// @nodoc
class __$$SearchMusterRollEventCopyWithImpl<$Res>
    extends _$MusterRollSearchEventCopyWithImpl<$Res, _$SearchMusterRollEvent>
    implements _$$SearchMusterRollEventCopyWith<$Res> {
  __$$SearchMusterRollEventCopyWithImpl(_$SearchMusterRollEvent _value,
      $Res Function(_$SearchMusterRollEvent) _then)
      : super(_value, _then);
}

/// @nodoc

class _$SearchMusterRollEvent
    with DiagnosticableTreeMixin
    implements SearchMusterRollEvent {
  const _$SearchMusterRollEvent();

  @override
  String toString({DiagnosticLevel minLevel = DiagnosticLevel.info}) {
    return 'MusterRollSearchEvent.search()';
  }

  @override
  void debugFillProperties(DiagnosticPropertiesBuilder properties) {
    super.debugFillProperties(properties);
    properties.add(DiagnosticsProperty('type', 'MusterRollSearchEvent.search'));
  }

  @override
  bool operator ==(dynamic other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType && other is _$SearchMusterRollEvent);
  }

  @override
  int get hashCode => runtimeType.hashCode;

  @override
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function() search,
    required TResult Function(String id) individualSearch,
  }) {
    return search();
  }

  @override
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function()? search,
    TResult? Function(String id)? individualSearch,
  }) {
    return search?.call();
  }

  @override
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function()? search,
    TResult Function(String id)? individualSearch,
    required TResult orElse(),
  }) {
    if (search != null) {
      return search();
    }
    return orElse();
  }

  @override
  @optionalTypeArgs
  TResult map<TResult extends Object?>({
    required TResult Function(SearchMusterRollEvent value) search,
    required TResult Function(SearchIndividualMusterRollEvent value)
        individualSearch,
  }) {
    return search(this);
  }

  @override
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(SearchMusterRollEvent value)? search,
    TResult? Function(SearchIndividualMusterRollEvent value)? individualSearch,
  }) {
    return search?.call(this);
  }

  @override
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(SearchMusterRollEvent value)? search,
    TResult Function(SearchIndividualMusterRollEvent value)? individualSearch,
    required TResult orElse(),
  }) {
    if (search != null) {
      return search(this);
    }
    return orElse();
  }
}

abstract class SearchMusterRollEvent implements MusterRollSearchEvent {
  const factory SearchMusterRollEvent() = _$SearchMusterRollEvent;
}

/// @nodoc
abstract class _$$SearchIndividualMusterRollEventCopyWith<$Res> {
  factory _$$SearchIndividualMusterRollEventCopyWith(
          _$SearchIndividualMusterRollEvent value,
          $Res Function(_$SearchIndividualMusterRollEvent) then) =
      __$$SearchIndividualMusterRollEventCopyWithImpl<$Res>;
  @useResult
  $Res call({String id});
}

/// @nodoc
class __$$SearchIndividualMusterRollEventCopyWithImpl<$Res>
    extends _$MusterRollSearchEventCopyWithImpl<$Res,
        _$SearchIndividualMusterRollEvent>
    implements _$$SearchIndividualMusterRollEventCopyWith<$Res> {
  __$$SearchIndividualMusterRollEventCopyWithImpl(
      _$SearchIndividualMusterRollEvent _value,
      $Res Function(_$SearchIndividualMusterRollEvent) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? id = null,
  }) {
    return _then(_$SearchIndividualMusterRollEvent(
      id: null == id
          ? _value.id
          : id // ignore: cast_nullable_to_non_nullable
              as String,
    ));
  }
}

/// @nodoc

class _$SearchIndividualMusterRollEvent
    with DiagnosticableTreeMixin
    implements SearchIndividualMusterRollEvent {
  const _$SearchIndividualMusterRollEvent({this.id = ''});

  @override
  @JsonKey()
  final String id;

  @override
  String toString({DiagnosticLevel minLevel = DiagnosticLevel.info}) {
    return 'MusterRollSearchEvent.individualSearch(id: $id)';
  }

  @override
  void debugFillProperties(DiagnosticPropertiesBuilder properties) {
    super.debugFillProperties(properties);
    properties
      ..add(
          DiagnosticsProperty('type', 'MusterRollSearchEvent.individualSearch'))
      ..add(DiagnosticsProperty('id', id));
  }

  @override
  bool operator ==(dynamic other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$SearchIndividualMusterRollEvent &&
            (identical(other.id, id) || other.id == id));
  }

  @override
  int get hashCode => Object.hash(runtimeType, id);

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$SearchIndividualMusterRollEventCopyWith<_$SearchIndividualMusterRollEvent>
      get copyWith => __$$SearchIndividualMusterRollEventCopyWithImpl<
          _$SearchIndividualMusterRollEvent>(this, _$identity);

  @override
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function() search,
    required TResult Function(String id) individualSearch,
  }) {
    return individualSearch(id);
  }

  @override
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function()? search,
    TResult? Function(String id)? individualSearch,
  }) {
    return individualSearch?.call(id);
  }

  @override
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function()? search,
    TResult Function(String id)? individualSearch,
    required TResult orElse(),
  }) {
    if (individualSearch != null) {
      return individualSearch(id);
    }
    return orElse();
  }

  @override
  @optionalTypeArgs
  TResult map<TResult extends Object?>({
    required TResult Function(SearchMusterRollEvent value) search,
    required TResult Function(SearchIndividualMusterRollEvent value)
        individualSearch,
  }) {
    return individualSearch(this);
  }

  @override
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(SearchMusterRollEvent value)? search,
    TResult? Function(SearchIndividualMusterRollEvent value)? individualSearch,
  }) {
    return individualSearch?.call(this);
  }

  @override
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(SearchMusterRollEvent value)? search,
    TResult Function(SearchIndividualMusterRollEvent value)? individualSearch,
    required TResult orElse(),
  }) {
    if (individualSearch != null) {
      return individualSearch(this);
    }
    return orElse();
  }
}

abstract class SearchIndividualMusterRollEvent
    implements MusterRollSearchEvent {
  const factory SearchIndividualMusterRollEvent({final String id}) =
      _$SearchIndividualMusterRollEvent;

  String get id;
  @JsonKey(ignore: true)
  _$$SearchIndividualMusterRollEventCopyWith<_$SearchIndividualMusterRollEvent>
      get copyWith => throw _privateConstructorUsedError;
}

/// @nodoc
mixin _$MusterRollSearchState {
  bool get loading => throw _privateConstructorUsedError;
  MusterRollsModel? get musterRollsModel => throw _privateConstructorUsedError;
  MusterRollsModel? get individualMusterRollModel =>
      throw _privateConstructorUsedError;

  @JsonKey(ignore: true)
  $MusterRollSearchStateCopyWith<MusterRollSearchState> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $MusterRollSearchStateCopyWith<$Res> {
  factory $MusterRollSearchStateCopyWith(MusterRollSearchState value,
          $Res Function(MusterRollSearchState) then) =
      _$MusterRollSearchStateCopyWithImpl<$Res, MusterRollSearchState>;
  @useResult
  $Res call(
      {bool loading,
      MusterRollsModel? musterRollsModel,
      MusterRollsModel? individualMusterRollModel});

  $MusterRollsModelCopyWith<$Res>? get musterRollsModel;
  $MusterRollsModelCopyWith<$Res>? get individualMusterRollModel;
}

/// @nodoc
class _$MusterRollSearchStateCopyWithImpl<$Res,
        $Val extends MusterRollSearchState>
    implements $MusterRollSearchStateCopyWith<$Res> {
  _$MusterRollSearchStateCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? loading = null,
    Object? musterRollsModel = freezed,
    Object? individualMusterRollModel = freezed,
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
      individualMusterRollModel: freezed == individualMusterRollModel
          ? _value.individualMusterRollModel
          : individualMusterRollModel // ignore: cast_nullable_to_non_nullable
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

  @override
  @pragma('vm:prefer-inline')
  $MusterRollsModelCopyWith<$Res>? get individualMusterRollModel {
    if (_value.individualMusterRollModel == null) {
      return null;
    }

    return $MusterRollsModelCopyWith<$Res>(_value.individualMusterRollModel!,
        (value) {
      return _then(_value.copyWith(individualMusterRollModel: value) as $Val);
    });
  }
}

/// @nodoc
abstract class _$$_MusterRollSearchStateCopyWith<$Res>
    implements $MusterRollSearchStateCopyWith<$Res> {
  factory _$$_MusterRollSearchStateCopyWith(_$_MusterRollSearchState value,
          $Res Function(_$_MusterRollSearchState) then) =
      __$$_MusterRollSearchStateCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call(
      {bool loading,
      MusterRollsModel? musterRollsModel,
      MusterRollsModel? individualMusterRollModel});

  @override
  $MusterRollsModelCopyWith<$Res>? get musterRollsModel;
  @override
  $MusterRollsModelCopyWith<$Res>? get individualMusterRollModel;
}

/// @nodoc
class __$$_MusterRollSearchStateCopyWithImpl<$Res>
    extends _$MusterRollSearchStateCopyWithImpl<$Res, _$_MusterRollSearchState>
    implements _$$_MusterRollSearchStateCopyWith<$Res> {
  __$$_MusterRollSearchStateCopyWithImpl(_$_MusterRollSearchState _value,
      $Res Function(_$_MusterRollSearchState) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? loading = null,
    Object? musterRollsModel = freezed,
    Object? individualMusterRollModel = freezed,
  }) {
    return _then(_$_MusterRollSearchState(
      loading: null == loading
          ? _value.loading
          : loading // ignore: cast_nullable_to_non_nullable
              as bool,
      musterRollsModel: freezed == musterRollsModel
          ? _value.musterRollsModel
          : musterRollsModel // ignore: cast_nullable_to_non_nullable
              as MusterRollsModel?,
      individualMusterRollModel: freezed == individualMusterRollModel
          ? _value.individualMusterRollModel
          : individualMusterRollModel // ignore: cast_nullable_to_non_nullable
              as MusterRollsModel?,
    ));
  }
}

/// @nodoc

class _$_MusterRollSearchState extends _MusterRollSearchState
    with DiagnosticableTreeMixin {
  const _$_MusterRollSearchState(
      {this.loading = false,
      this.musterRollsModel,
      this.individualMusterRollModel})
      : super._();

  @override
  @JsonKey()
  final bool loading;
  @override
  final MusterRollsModel? musterRollsModel;
  @override
  final MusterRollsModel? individualMusterRollModel;

  @override
  String toString({DiagnosticLevel minLevel = DiagnosticLevel.info}) {
    return 'MusterRollSearchState(loading: $loading, musterRollsModel: $musterRollsModel, individualMusterRollModel: $individualMusterRollModel)';
  }

  @override
  void debugFillProperties(DiagnosticPropertiesBuilder properties) {
    super.debugFillProperties(properties);
    properties
      ..add(DiagnosticsProperty('type', 'MusterRollSearchState'))
      ..add(DiagnosticsProperty('loading', loading))
      ..add(DiagnosticsProperty('musterRollsModel', musterRollsModel))
      ..add(DiagnosticsProperty(
          'individualMusterRollModel', individualMusterRollModel));
  }

  @override
  bool operator ==(dynamic other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$_MusterRollSearchState &&
            (identical(other.loading, loading) || other.loading == loading) &&
            (identical(other.musterRollsModel, musterRollsModel) ||
                other.musterRollsModel == musterRollsModel) &&
            (identical(other.individualMusterRollModel,
                    individualMusterRollModel) ||
                other.individualMusterRollModel == individualMusterRollModel));
  }

  @override
  int get hashCode => Object.hash(
      runtimeType, loading, musterRollsModel, individualMusterRollModel);

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$_MusterRollSearchStateCopyWith<_$_MusterRollSearchState> get copyWith =>
      __$$_MusterRollSearchStateCopyWithImpl<_$_MusterRollSearchState>(
          this, _$identity);
}

abstract class _MusterRollSearchState extends MusterRollSearchState {
  const factory _MusterRollSearchState(
          {final bool loading,
          final MusterRollsModel? musterRollsModel,
          final MusterRollsModel? individualMusterRollModel}) =
      _$_MusterRollSearchState;
  const _MusterRollSearchState._() : super._();

  @override
  bool get loading;
  @override
  MusterRollsModel? get musterRollsModel;
  @override
  MusterRollsModel? get individualMusterRollModel;
  @override
  @JsonKey(ignore: true)
  _$$_MusterRollSearchStateCopyWith<_$_MusterRollSearchState> get copyWith =>
      throw _privateConstructorUsedError;
}
