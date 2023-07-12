// coverage:ignore-file
// GENERATED CODE - DO NOT MODIFY BY HAND
// ignore_for_file: type=lint
// ignore_for_file: unused_element, deprecated_member_use, deprecated_member_use_from_same_package, use_function_type_syntax_for_parameters, unnecessary_const, avoid_init_to_null, invalid_override_different_default_values_named, prefer_expression_function_bodies, annotate_overrides, invalid_annotation_target, unnecessary_question_mark

part of 'individual_wms_search.dart';

// **************************************************************************
// FreezedGenerator
// **************************************************************************

T _$identity<T>(T value) => value;

final _privateConstructorUsedError = UnsupportedError(
    'It seems like you constructed your class using `MyClass._()`. This constructor is only meant to be used by freezed and you are not supposed to need it nor use it.\nPlease check the documentation here for more information: https://github.com/rrousselGit/freezed#custom-getters-and-methods');

/// @nodoc
mixin _$IndividualWMSSearchEvent {
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function(String tenant, String name) nameSearch,
    required TResult Function(String tenant, List<String>? ids) idSearch,
    required TResult Function() dispose,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function(String tenant, String name)? nameSearch,
    TResult? Function(String tenant, List<String>? ids)? idSearch,
    TResult? Function()? dispose,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function(String tenant, String name)? nameSearch,
    TResult Function(String tenant, List<String>? ids)? idSearch,
    TResult Function()? dispose,
    required TResult orElse(),
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult map<TResult extends Object?>({
    required TResult Function(SearchWMSIndividualNameEvent value) nameSearch,
    required TResult Function(SearchWMSIndividualIdEvent value) idSearch,
    required TResult Function(DisposeSearchWMSIndividualEvent value) dispose,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(SearchWMSIndividualNameEvent value)? nameSearch,
    TResult? Function(SearchWMSIndividualIdEvent value)? idSearch,
    TResult? Function(DisposeSearchWMSIndividualEvent value)? dispose,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(SearchWMSIndividualNameEvent value)? nameSearch,
    TResult Function(SearchWMSIndividualIdEvent value)? idSearch,
    TResult Function(DisposeSearchWMSIndividualEvent value)? dispose,
    required TResult orElse(),
  }) =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $IndividualWMSSearchEventCopyWith<$Res> {
  factory $IndividualWMSSearchEventCopyWith(IndividualWMSSearchEvent value,
          $Res Function(IndividualWMSSearchEvent) then) =
      _$IndividualWMSSearchEventCopyWithImpl<$Res, IndividualWMSSearchEvent>;
}

/// @nodoc
class _$IndividualWMSSearchEventCopyWithImpl<$Res,
        $Val extends IndividualWMSSearchEvent>
    implements $IndividualWMSSearchEventCopyWith<$Res> {
  _$IndividualWMSSearchEventCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;
}

/// @nodoc
abstract class _$$SearchWMSIndividualNameEventCopyWith<$Res> {
  factory _$$SearchWMSIndividualNameEventCopyWith(
          _$SearchWMSIndividualNameEvent value,
          $Res Function(_$SearchWMSIndividualNameEvent) then) =
      __$$SearchWMSIndividualNameEventCopyWithImpl<$Res>;
  @useResult
  $Res call({String tenant, String name});
}

/// @nodoc
class __$$SearchWMSIndividualNameEventCopyWithImpl<$Res>
    extends _$IndividualWMSSearchEventCopyWithImpl<$Res,
        _$SearchWMSIndividualNameEvent>
    implements _$$SearchWMSIndividualNameEventCopyWith<$Res> {
  __$$SearchWMSIndividualNameEventCopyWithImpl(
      _$SearchWMSIndividualNameEvent _value,
      $Res Function(_$SearchWMSIndividualNameEvent) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? tenant = null,
    Object? name = null,
  }) {
    return _then(_$SearchWMSIndividualNameEvent(
      tenant: null == tenant
          ? _value.tenant
          : tenant // ignore: cast_nullable_to_non_nullable
              as String,
      name: null == name
          ? _value.name
          : name // ignore: cast_nullable_to_non_nullable
              as String,
    ));
  }
}

/// @nodoc

class _$SearchWMSIndividualNameEvent
    with DiagnosticableTreeMixin
    implements SearchWMSIndividualNameEvent {
  const _$SearchWMSIndividualNameEvent(
      {required this.tenant, required this.name});

  @override
  final String tenant;
  @override
  final String name;

  @override
  String toString({DiagnosticLevel minLevel = DiagnosticLevel.info}) {
    return 'IndividualWMSSearchEvent.nameSearch(tenant: $tenant, name: $name)';
  }

  @override
  void debugFillProperties(DiagnosticPropertiesBuilder properties) {
    super.debugFillProperties(properties);
    properties
      ..add(DiagnosticsProperty('type', 'IndividualWMSSearchEvent.nameSearch'))
      ..add(DiagnosticsProperty('tenant', tenant))
      ..add(DiagnosticsProperty('name', name));
  }

  @override
  bool operator ==(dynamic other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$SearchWMSIndividualNameEvent &&
            (identical(other.tenant, tenant) || other.tenant == tenant) &&
            (identical(other.name, name) || other.name == name));
  }

  @override
  int get hashCode => Object.hash(runtimeType, tenant, name);

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$SearchWMSIndividualNameEventCopyWith<_$SearchWMSIndividualNameEvent>
      get copyWith => __$$SearchWMSIndividualNameEventCopyWithImpl<
          _$SearchWMSIndividualNameEvent>(this, _$identity);

  @override
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function(String tenant, String name) nameSearch,
    required TResult Function(String tenant, List<String>? ids) idSearch,
    required TResult Function() dispose,
  }) {
    return nameSearch(tenant, name);
  }

  @override
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function(String tenant, String name)? nameSearch,
    TResult? Function(String tenant, List<String>? ids)? idSearch,
    TResult? Function()? dispose,
  }) {
    return nameSearch?.call(tenant, name);
  }

  @override
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function(String tenant, String name)? nameSearch,
    TResult Function(String tenant, List<String>? ids)? idSearch,
    TResult Function()? dispose,
    required TResult orElse(),
  }) {
    if (nameSearch != null) {
      return nameSearch(tenant, name);
    }
    return orElse();
  }

  @override
  @optionalTypeArgs
  TResult map<TResult extends Object?>({
    required TResult Function(SearchWMSIndividualNameEvent value) nameSearch,
    required TResult Function(SearchWMSIndividualIdEvent value) idSearch,
    required TResult Function(DisposeSearchWMSIndividualEvent value) dispose,
  }) {
    return nameSearch(this);
  }

  @override
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(SearchWMSIndividualNameEvent value)? nameSearch,
    TResult? Function(SearchWMSIndividualIdEvent value)? idSearch,
    TResult? Function(DisposeSearchWMSIndividualEvent value)? dispose,
  }) {
    return nameSearch?.call(this);
  }

  @override
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(SearchWMSIndividualNameEvent value)? nameSearch,
    TResult Function(SearchWMSIndividualIdEvent value)? idSearch,
    TResult Function(DisposeSearchWMSIndividualEvent value)? dispose,
    required TResult orElse(),
  }) {
    if (nameSearch != null) {
      return nameSearch(this);
    }
    return orElse();
  }
}

abstract class SearchWMSIndividualNameEvent
    implements IndividualWMSSearchEvent {
  const factory SearchWMSIndividualNameEvent(
      {required final String tenant,
      required final String name}) = _$SearchWMSIndividualNameEvent;

  String get tenant;
  String get name;
  @JsonKey(ignore: true)
  _$$SearchWMSIndividualNameEventCopyWith<_$SearchWMSIndividualNameEvent>
      get copyWith => throw _privateConstructorUsedError;
}

/// @nodoc
abstract class _$$SearchWMSIndividualIdEventCopyWith<$Res> {
  factory _$$SearchWMSIndividualIdEventCopyWith(
          _$SearchWMSIndividualIdEvent value,
          $Res Function(_$SearchWMSIndividualIdEvent) then) =
      __$$SearchWMSIndividualIdEventCopyWithImpl<$Res>;
  @useResult
  $Res call({String tenant, List<String>? ids});
}

/// @nodoc
class __$$SearchWMSIndividualIdEventCopyWithImpl<$Res>
    extends _$IndividualWMSSearchEventCopyWithImpl<$Res,
        _$SearchWMSIndividualIdEvent>
    implements _$$SearchWMSIndividualIdEventCopyWith<$Res> {
  __$$SearchWMSIndividualIdEventCopyWithImpl(
      _$SearchWMSIndividualIdEvent _value,
      $Res Function(_$SearchWMSIndividualIdEvent) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? tenant = null,
    Object? ids = freezed,
  }) {
    return _then(_$SearchWMSIndividualIdEvent(
      tenant: null == tenant
          ? _value.tenant
          : tenant // ignore: cast_nullable_to_non_nullable
              as String,
      ids: freezed == ids
          ? _value._ids
          : ids // ignore: cast_nullable_to_non_nullable
              as List<String>?,
    ));
  }
}

/// @nodoc

class _$SearchWMSIndividualIdEvent
    with DiagnosticableTreeMixin
    implements SearchWMSIndividualIdEvent {
  const _$SearchWMSIndividualIdEvent(
      {required this.tenant, final List<String>? ids})
      : _ids = ids;

  @override
  final String tenant;
  final List<String>? _ids;
  @override
  List<String>? get ids {
    final value = _ids;
    if (value == null) return null;
    if (_ids is EqualUnmodifiableListView) return _ids;
    // ignore: implicit_dynamic_type
    return EqualUnmodifiableListView(value);
  }

  @override
  String toString({DiagnosticLevel minLevel = DiagnosticLevel.info}) {
    return 'IndividualWMSSearchEvent.idSearch(tenant: $tenant, ids: $ids)';
  }

  @override
  void debugFillProperties(DiagnosticPropertiesBuilder properties) {
    super.debugFillProperties(properties);
    properties
      ..add(DiagnosticsProperty('type', 'IndividualWMSSearchEvent.idSearch'))
      ..add(DiagnosticsProperty('tenant', tenant))
      ..add(DiagnosticsProperty('ids', ids));
  }

  @override
  bool operator ==(dynamic other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$SearchWMSIndividualIdEvent &&
            (identical(other.tenant, tenant) || other.tenant == tenant) &&
            const DeepCollectionEquality().equals(other._ids, _ids));
  }

  @override
  int get hashCode => Object.hash(
      runtimeType, tenant, const DeepCollectionEquality().hash(_ids));

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$SearchWMSIndividualIdEventCopyWith<_$SearchWMSIndividualIdEvent>
      get copyWith => __$$SearchWMSIndividualIdEventCopyWithImpl<
          _$SearchWMSIndividualIdEvent>(this, _$identity);

  @override
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function(String tenant, String name) nameSearch,
    required TResult Function(String tenant, List<String>? ids) idSearch,
    required TResult Function() dispose,
  }) {
    return idSearch(tenant, ids);
  }

  @override
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function(String tenant, String name)? nameSearch,
    TResult? Function(String tenant, List<String>? ids)? idSearch,
    TResult? Function()? dispose,
  }) {
    return idSearch?.call(tenant, ids);
  }

  @override
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function(String tenant, String name)? nameSearch,
    TResult Function(String tenant, List<String>? ids)? idSearch,
    TResult Function()? dispose,
    required TResult orElse(),
  }) {
    if (idSearch != null) {
      return idSearch(tenant, ids);
    }
    return orElse();
  }

  @override
  @optionalTypeArgs
  TResult map<TResult extends Object?>({
    required TResult Function(SearchWMSIndividualNameEvent value) nameSearch,
    required TResult Function(SearchWMSIndividualIdEvent value) idSearch,
    required TResult Function(DisposeSearchWMSIndividualEvent value) dispose,
  }) {
    return idSearch(this);
  }

  @override
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(SearchWMSIndividualNameEvent value)? nameSearch,
    TResult? Function(SearchWMSIndividualIdEvent value)? idSearch,
    TResult? Function(DisposeSearchWMSIndividualEvent value)? dispose,
  }) {
    return idSearch?.call(this);
  }

  @override
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(SearchWMSIndividualNameEvent value)? nameSearch,
    TResult Function(SearchWMSIndividualIdEvent value)? idSearch,
    TResult Function(DisposeSearchWMSIndividualEvent value)? dispose,
    required TResult orElse(),
  }) {
    if (idSearch != null) {
      return idSearch(this);
    }
    return orElse();
  }
}

abstract class SearchWMSIndividualIdEvent implements IndividualWMSSearchEvent {
  const factory SearchWMSIndividualIdEvent(
      {required final String tenant,
      final List<String>? ids}) = _$SearchWMSIndividualIdEvent;

  String get tenant;
  List<String>? get ids;
  @JsonKey(ignore: true)
  _$$SearchWMSIndividualIdEventCopyWith<_$SearchWMSIndividualIdEvent>
      get copyWith => throw _privateConstructorUsedError;
}

/// @nodoc
abstract class _$$DisposeSearchWMSIndividualEventCopyWith<$Res> {
  factory _$$DisposeSearchWMSIndividualEventCopyWith(
          _$DisposeSearchWMSIndividualEvent value,
          $Res Function(_$DisposeSearchWMSIndividualEvent) then) =
      __$$DisposeSearchWMSIndividualEventCopyWithImpl<$Res>;
}

/// @nodoc
class __$$DisposeSearchWMSIndividualEventCopyWithImpl<$Res>
    extends _$IndividualWMSSearchEventCopyWithImpl<$Res,
        _$DisposeSearchWMSIndividualEvent>
    implements _$$DisposeSearchWMSIndividualEventCopyWith<$Res> {
  __$$DisposeSearchWMSIndividualEventCopyWithImpl(
      _$DisposeSearchWMSIndividualEvent _value,
      $Res Function(_$DisposeSearchWMSIndividualEvent) _then)
      : super(_value, _then);
}

/// @nodoc

class _$DisposeSearchWMSIndividualEvent
    with DiagnosticableTreeMixin
    implements DisposeSearchWMSIndividualEvent {
  const _$DisposeSearchWMSIndividualEvent();

  @override
  String toString({DiagnosticLevel minLevel = DiagnosticLevel.info}) {
    return 'IndividualWMSSearchEvent.dispose()';
  }

  @override
  void debugFillProperties(DiagnosticPropertiesBuilder properties) {
    super.debugFillProperties(properties);
    properties
        .add(DiagnosticsProperty('type', 'IndividualWMSSearchEvent.dispose'));
  }

  @override
  bool operator ==(dynamic other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$DisposeSearchWMSIndividualEvent);
  }

  @override
  int get hashCode => runtimeType.hashCode;

  @override
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function(String tenant, String name) nameSearch,
    required TResult Function(String tenant, List<String>? ids) idSearch,
    required TResult Function() dispose,
  }) {
    return dispose();
  }

  @override
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function(String tenant, String name)? nameSearch,
    TResult? Function(String tenant, List<String>? ids)? idSearch,
    TResult? Function()? dispose,
  }) {
    return dispose?.call();
  }

  @override
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function(String tenant, String name)? nameSearch,
    TResult Function(String tenant, List<String>? ids)? idSearch,
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
    required TResult Function(SearchWMSIndividualNameEvent value) nameSearch,
    required TResult Function(SearchWMSIndividualIdEvent value) idSearch,
    required TResult Function(DisposeSearchWMSIndividualEvent value) dispose,
  }) {
    return dispose(this);
  }

  @override
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(SearchWMSIndividualNameEvent value)? nameSearch,
    TResult? Function(SearchWMSIndividualIdEvent value)? idSearch,
    TResult? Function(DisposeSearchWMSIndividualEvent value)? dispose,
  }) {
    return dispose?.call(this);
  }

  @override
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(SearchWMSIndividualNameEvent value)? nameSearch,
    TResult Function(SearchWMSIndividualIdEvent value)? idSearch,
    TResult Function(DisposeSearchWMSIndividualEvent value)? dispose,
    required TResult orElse(),
  }) {
    if (dispose != null) {
      return dispose(this);
    }
    return orElse();
  }
}

abstract class DisposeSearchWMSIndividualEvent
    implements IndividualWMSSearchEvent {
  const factory DisposeSearchWMSIndividualEvent() =
      _$DisposeSearchWMSIndividualEvent;
}

/// @nodoc
mixin _$IndividualWMSSearchState {
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function() initial,
    required TResult Function() loading,
    required TResult Function(WMSIndividualListModel? individualListModel)
        loaded,
    required TResult Function(String? error) error,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function()? initial,
    TResult? Function()? loading,
    TResult? Function(WMSIndividualListModel? individualListModel)? loaded,
    TResult? Function(String? error)? error,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function()? initial,
    TResult Function()? loading,
    TResult Function(WMSIndividualListModel? individualListModel)? loaded,
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
abstract class $IndividualWMSSearchStateCopyWith<$Res> {
  factory $IndividualWMSSearchStateCopyWith(IndividualWMSSearchState value,
          $Res Function(IndividualWMSSearchState) then) =
      _$IndividualWMSSearchStateCopyWithImpl<$Res, IndividualWMSSearchState>;
}

/// @nodoc
class _$IndividualWMSSearchStateCopyWithImpl<$Res,
        $Val extends IndividualWMSSearchState>
    implements $IndividualWMSSearchStateCopyWith<$Res> {
  _$IndividualWMSSearchStateCopyWithImpl(this._value, this._then);

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
    extends _$IndividualWMSSearchStateCopyWithImpl<$Res, _$_Initial>
    implements _$$_InitialCopyWith<$Res> {
  __$$_InitialCopyWithImpl(_$_Initial _value, $Res Function(_$_Initial) _then)
      : super(_value, _then);
}

/// @nodoc

class _$_Initial extends _Initial with DiagnosticableTreeMixin {
  const _$_Initial() : super._();

  @override
  String toString({DiagnosticLevel minLevel = DiagnosticLevel.info}) {
    return 'IndividualWMSSearchState.initial()';
  }

  @override
  void debugFillProperties(DiagnosticPropertiesBuilder properties) {
    super.debugFillProperties(properties);
    properties
        .add(DiagnosticsProperty('type', 'IndividualWMSSearchState.initial'));
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
    required TResult Function(WMSIndividualListModel? individualListModel)
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
    TResult? Function(WMSIndividualListModel? individualListModel)? loaded,
    TResult? Function(String? error)? error,
  }) {
    return initial?.call();
  }

  @override
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function()? initial,
    TResult Function()? loading,
    TResult Function(WMSIndividualListModel? individualListModel)? loaded,
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

abstract class _Initial extends IndividualWMSSearchState {
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
    extends _$IndividualWMSSearchStateCopyWithImpl<$Res, _$_Loading>
    implements _$$_LoadingCopyWith<$Res> {
  __$$_LoadingCopyWithImpl(_$_Loading _value, $Res Function(_$_Loading) _then)
      : super(_value, _then);
}

/// @nodoc

class _$_Loading extends _Loading with DiagnosticableTreeMixin {
  const _$_Loading() : super._();

  @override
  String toString({DiagnosticLevel minLevel = DiagnosticLevel.info}) {
    return 'IndividualWMSSearchState.loading()';
  }

  @override
  void debugFillProperties(DiagnosticPropertiesBuilder properties) {
    super.debugFillProperties(properties);
    properties
        .add(DiagnosticsProperty('type', 'IndividualWMSSearchState.loading'));
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
    required TResult Function(WMSIndividualListModel? individualListModel)
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
    TResult? Function(WMSIndividualListModel? individualListModel)? loaded,
    TResult? Function(String? error)? error,
  }) {
    return loading?.call();
  }

  @override
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function()? initial,
    TResult Function()? loading,
    TResult Function(WMSIndividualListModel? individualListModel)? loaded,
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

abstract class _Loading extends IndividualWMSSearchState {
  const factory _Loading() = _$_Loading;
  const _Loading._() : super._();
}

/// @nodoc
abstract class _$$_LoadedCopyWith<$Res> {
  factory _$$_LoadedCopyWith(_$_Loaded value, $Res Function(_$_Loaded) then) =
      __$$_LoadedCopyWithImpl<$Res>;
  @useResult
  $Res call({WMSIndividualListModel? individualListModel});
}

/// @nodoc
class __$$_LoadedCopyWithImpl<$Res>
    extends _$IndividualWMSSearchStateCopyWithImpl<$Res, _$_Loaded>
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
              as WMSIndividualListModel?,
    ));
  }
}

/// @nodoc

class _$_Loaded extends _Loaded with DiagnosticableTreeMixin {
  const _$_Loaded(this.individualListModel) : super._();

  @override
  final WMSIndividualListModel? individualListModel;

  @override
  String toString({DiagnosticLevel minLevel = DiagnosticLevel.info}) {
    return 'IndividualWMSSearchState.loaded(individualListModel: $individualListModel)';
  }

  @override
  void debugFillProperties(DiagnosticPropertiesBuilder properties) {
    super.debugFillProperties(properties);
    properties
      ..add(DiagnosticsProperty('type', 'IndividualWMSSearchState.loaded'))
      ..add(DiagnosticsProperty('individualListModel', individualListModel));
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
    required TResult Function(WMSIndividualListModel? individualListModel)
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
    TResult? Function(WMSIndividualListModel? individualListModel)? loaded,
    TResult? Function(String? error)? error,
  }) {
    return loaded?.call(individualListModel);
  }

  @override
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function()? initial,
    TResult Function()? loading,
    TResult Function(WMSIndividualListModel? individualListModel)? loaded,
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

abstract class _Loaded extends IndividualWMSSearchState {
  const factory _Loaded(final WMSIndividualListModel? individualListModel) =
      _$_Loaded;
  const _Loaded._() : super._();

  WMSIndividualListModel? get individualListModel;
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
    extends _$IndividualWMSSearchStateCopyWithImpl<$Res, _$_Error>
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

class _$_Error extends _Error with DiagnosticableTreeMixin {
  const _$_Error(this.error) : super._();

  @override
  final String? error;

  @override
  String toString({DiagnosticLevel minLevel = DiagnosticLevel.info}) {
    return 'IndividualWMSSearchState.error(error: $error)';
  }

  @override
  void debugFillProperties(DiagnosticPropertiesBuilder properties) {
    super.debugFillProperties(properties);
    properties
      ..add(DiagnosticsProperty('type', 'IndividualWMSSearchState.error'))
      ..add(DiagnosticsProperty('error', error));
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
    required TResult Function(WMSIndividualListModel? individualListModel)
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
    TResult? Function(WMSIndividualListModel? individualListModel)? loaded,
    TResult? Function(String? error)? error,
  }) {
    return error?.call(this.error);
  }

  @override
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function()? initial,
    TResult Function()? loading,
    TResult Function(WMSIndividualListModel? individualListModel)? loaded,
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

abstract class _Error extends IndividualWMSSearchState {
  const factory _Error(final String? error) = _$_Error;
  const _Error._() : super._();

  String? get error;
  @JsonKey(ignore: true)
  _$$_ErrorCopyWith<_$_Error> get copyWith =>
      throw _privateConstructorUsedError;
}
