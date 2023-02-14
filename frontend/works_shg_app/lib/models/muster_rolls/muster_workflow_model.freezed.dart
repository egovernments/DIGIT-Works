// coverage:ignore-file
// GENERATED CODE - DO NOT MODIFY BY HAND
// ignore_for_file: type=lint
// ignore_for_file: unused_element, deprecated_member_use, deprecated_member_use_from_same_package, use_function_type_syntax_for_parameters, unnecessary_const, avoid_init_to_null, invalid_override_different_default_values_named, prefer_expression_function_bodies, annotate_overrides, invalid_annotation_target

part of 'muster_workflow_model.dart';

// **************************************************************************
// FreezedGenerator
// **************************************************************************

T _$identity<T>(T value) => value;

final _privateConstructorUsedError = UnsupportedError(
    'It seems like you constructed your class using `MyClass._()`. This constructor is only meant to be used by freezed and you are not supposed to need it nor use it.\nPlease check the documentation here for more information: https://github.com/rrousselGit/freezed#custom-getters-and-methods');

MusterWorkFlowModel _$MusterWorkFlowModelFromJson(Map<String, dynamic> json) {
  return _MusterWorkFlowModel.fromJson(json);
}

/// @nodoc
mixin _$MusterWorkFlowModel {
  @JsonKey(name: 'ProcessInstances')
  List<ProcessInstances>? get processInstances =>
      throw _privateConstructorUsedError;

  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;
  @JsonKey(ignore: true)
  $MusterWorkFlowModelCopyWith<MusterWorkFlowModel> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $MusterWorkFlowModelCopyWith<$Res> {
  factory $MusterWorkFlowModelCopyWith(
          MusterWorkFlowModel value, $Res Function(MusterWorkFlowModel) then) =
      _$MusterWorkFlowModelCopyWithImpl<$Res, MusterWorkFlowModel>;
  @useResult
  $Res call(
      {@JsonKey(name: 'ProcessInstances')
          List<ProcessInstances>? processInstances});
}

/// @nodoc
class _$MusterWorkFlowModelCopyWithImpl<$Res, $Val extends MusterWorkFlowModel>
    implements $MusterWorkFlowModelCopyWith<$Res> {
  _$MusterWorkFlowModelCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? processInstances = freezed,
  }) {
    return _then(_value.copyWith(
      processInstances: freezed == processInstances
          ? _value.processInstances
          : processInstances // ignore: cast_nullable_to_non_nullable
              as List<ProcessInstances>?,
    ) as $Val);
  }
}

/// @nodoc
abstract class _$$_MusterWorkFlowModelCopyWith<$Res>
    implements $MusterWorkFlowModelCopyWith<$Res> {
  factory _$$_MusterWorkFlowModelCopyWith(_$_MusterWorkFlowModel value,
          $Res Function(_$_MusterWorkFlowModel) then) =
      __$$_MusterWorkFlowModelCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call(
      {@JsonKey(name: 'ProcessInstances')
          List<ProcessInstances>? processInstances});
}

/// @nodoc
class __$$_MusterWorkFlowModelCopyWithImpl<$Res>
    extends _$MusterWorkFlowModelCopyWithImpl<$Res, _$_MusterWorkFlowModel>
    implements _$$_MusterWorkFlowModelCopyWith<$Res> {
  __$$_MusterWorkFlowModelCopyWithImpl(_$_MusterWorkFlowModel _value,
      $Res Function(_$_MusterWorkFlowModel) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? processInstances = freezed,
  }) {
    return _then(_$_MusterWorkFlowModel(
      processInstances: freezed == processInstances
          ? _value._processInstances
          : processInstances // ignore: cast_nullable_to_non_nullable
              as List<ProcessInstances>?,
    ));
  }
}

/// @nodoc
@JsonSerializable()
class _$_MusterWorkFlowModel implements _MusterWorkFlowModel {
  const _$_MusterWorkFlowModel(
      {@JsonKey(name: 'ProcessInstances')
          final List<ProcessInstances>? processInstances})
      : _processInstances = processInstances;

  factory _$_MusterWorkFlowModel.fromJson(Map<String, dynamic> json) =>
      _$$_MusterWorkFlowModelFromJson(json);

  final List<ProcessInstances>? _processInstances;
  @override
  @JsonKey(name: 'ProcessInstances')
  List<ProcessInstances>? get processInstances {
    final value = _processInstances;
    if (value == null) return null;
    // ignore: implicit_dynamic_type
    return EqualUnmodifiableListView(value);
  }

  @override
  String toString() {
    return 'MusterWorkFlowModel(processInstances: $processInstances)';
  }

  @override
  bool operator ==(dynamic other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$_MusterWorkFlowModel &&
            const DeepCollectionEquality()
                .equals(other._processInstances, _processInstances));
  }

  @JsonKey(ignore: true)
  @override
  int get hashCode => Object.hash(
      runtimeType, const DeepCollectionEquality().hash(_processInstances));

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$_MusterWorkFlowModelCopyWith<_$_MusterWorkFlowModel> get copyWith =>
      __$$_MusterWorkFlowModelCopyWithImpl<_$_MusterWorkFlowModel>(
          this, _$identity);

  @override
  Map<String, dynamic> toJson() {
    return _$$_MusterWorkFlowModelToJson(
      this,
    );
  }
}

abstract class _MusterWorkFlowModel implements MusterWorkFlowModel {
  const factory _MusterWorkFlowModel(
          {@JsonKey(name: 'ProcessInstances')
              final List<ProcessInstances>? processInstances}) =
      _$_MusterWorkFlowModel;

  factory _MusterWorkFlowModel.fromJson(Map<String, dynamic> json) =
      _$_MusterWorkFlowModel.fromJson;

  @override
  @JsonKey(name: 'ProcessInstances')
  List<ProcessInstances>? get processInstances;
  @override
  @JsonKey(ignore: true)
  _$$_MusterWorkFlowModelCopyWith<_$_MusterWorkFlowModel> get copyWith =>
      throw _privateConstructorUsedError;
}

ProcessInstances _$ProcessInstancesFromJson(Map<String, dynamic> json) {
  return _ProcessInstances.fromJson(json);
}

/// @nodoc
mixin _$ProcessInstances {
  String? get tenantId => throw _privateConstructorUsedError;
  String? get businessService => throw _privateConstructorUsedError;
  String? get id => throw _privateConstructorUsedError;
  String? get businessId => throw _privateConstructorUsedError;
  String? get action => throw _privateConstructorUsedError;
  @JsonKey(name: 'state')
  WorkflowState? get workflowState => throw _privateConstructorUsedError;

  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;
  @JsonKey(ignore: true)
  $ProcessInstancesCopyWith<ProcessInstances> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $ProcessInstancesCopyWith<$Res> {
  factory $ProcessInstancesCopyWith(
          ProcessInstances value, $Res Function(ProcessInstances) then) =
      _$ProcessInstancesCopyWithImpl<$Res, ProcessInstances>;
  @useResult
  $Res call(
      {String? tenantId,
      String? businessService,
      String? id,
      String? businessId,
      String? action,
      @JsonKey(name: 'state') WorkflowState? workflowState});

  $WorkflowStateCopyWith<$Res>? get workflowState;
}

/// @nodoc
class _$ProcessInstancesCopyWithImpl<$Res, $Val extends ProcessInstances>
    implements $ProcessInstancesCopyWith<$Res> {
  _$ProcessInstancesCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? tenantId = freezed,
    Object? businessService = freezed,
    Object? id = freezed,
    Object? businessId = freezed,
    Object? action = freezed,
    Object? workflowState = freezed,
  }) {
    return _then(_value.copyWith(
      tenantId: freezed == tenantId
          ? _value.tenantId
          : tenantId // ignore: cast_nullable_to_non_nullable
              as String?,
      businessService: freezed == businessService
          ? _value.businessService
          : businessService // ignore: cast_nullable_to_non_nullable
              as String?,
      id: freezed == id
          ? _value.id
          : id // ignore: cast_nullable_to_non_nullable
              as String?,
      businessId: freezed == businessId
          ? _value.businessId
          : businessId // ignore: cast_nullable_to_non_nullable
              as String?,
      action: freezed == action
          ? _value.action
          : action // ignore: cast_nullable_to_non_nullable
              as String?,
      workflowState: freezed == workflowState
          ? _value.workflowState
          : workflowState // ignore: cast_nullable_to_non_nullable
              as WorkflowState?,
    ) as $Val);
  }

  @override
  @pragma('vm:prefer-inline')
  $WorkflowStateCopyWith<$Res>? get workflowState {
    if (_value.workflowState == null) {
      return null;
    }

    return $WorkflowStateCopyWith<$Res>(_value.workflowState!, (value) {
      return _then(_value.copyWith(workflowState: value) as $Val);
    });
  }
}

/// @nodoc
abstract class _$$_ProcessInstancesCopyWith<$Res>
    implements $ProcessInstancesCopyWith<$Res> {
  factory _$$_ProcessInstancesCopyWith(
          _$_ProcessInstances value, $Res Function(_$_ProcessInstances) then) =
      __$$_ProcessInstancesCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call(
      {String? tenantId,
      String? businessService,
      String? id,
      String? businessId,
      String? action,
      @JsonKey(name: 'state') WorkflowState? workflowState});

  @override
  $WorkflowStateCopyWith<$Res>? get workflowState;
}

/// @nodoc
class __$$_ProcessInstancesCopyWithImpl<$Res>
    extends _$ProcessInstancesCopyWithImpl<$Res, _$_ProcessInstances>
    implements _$$_ProcessInstancesCopyWith<$Res> {
  __$$_ProcessInstancesCopyWithImpl(
      _$_ProcessInstances _value, $Res Function(_$_ProcessInstances) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? tenantId = freezed,
    Object? businessService = freezed,
    Object? id = freezed,
    Object? businessId = freezed,
    Object? action = freezed,
    Object? workflowState = freezed,
  }) {
    return _then(_$_ProcessInstances(
      tenantId: freezed == tenantId
          ? _value.tenantId
          : tenantId // ignore: cast_nullable_to_non_nullable
              as String?,
      businessService: freezed == businessService
          ? _value.businessService
          : businessService // ignore: cast_nullable_to_non_nullable
              as String?,
      id: freezed == id
          ? _value.id
          : id // ignore: cast_nullable_to_non_nullable
              as String?,
      businessId: freezed == businessId
          ? _value.businessId
          : businessId // ignore: cast_nullable_to_non_nullable
              as String?,
      action: freezed == action
          ? _value.action
          : action // ignore: cast_nullable_to_non_nullable
              as String?,
      workflowState: freezed == workflowState
          ? _value.workflowState
          : workflowState // ignore: cast_nullable_to_non_nullable
              as WorkflowState?,
    ));
  }
}

/// @nodoc
@JsonSerializable()
class _$_ProcessInstances implements _ProcessInstances {
  const _$_ProcessInstances(
      {this.tenantId,
      this.businessService,
      this.id,
      this.businessId,
      this.action,
      @JsonKey(name: 'state') this.workflowState});

  factory _$_ProcessInstances.fromJson(Map<String, dynamic> json) =>
      _$$_ProcessInstancesFromJson(json);

  @override
  final String? tenantId;
  @override
  final String? businessService;
  @override
  final String? id;
  @override
  final String? businessId;
  @override
  final String? action;
  @override
  @JsonKey(name: 'state')
  final WorkflowState? workflowState;

  @override
  String toString() {
    return 'ProcessInstances(tenantId: $tenantId, businessService: $businessService, id: $id, businessId: $businessId, action: $action, workflowState: $workflowState)';
  }

  @override
  bool operator ==(dynamic other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$_ProcessInstances &&
            (identical(other.tenantId, tenantId) ||
                other.tenantId == tenantId) &&
            (identical(other.businessService, businessService) ||
                other.businessService == businessService) &&
            (identical(other.id, id) || other.id == id) &&
            (identical(other.businessId, businessId) ||
                other.businessId == businessId) &&
            (identical(other.action, action) || other.action == action) &&
            (identical(other.workflowState, workflowState) ||
                other.workflowState == workflowState));
  }

  @JsonKey(ignore: true)
  @override
  int get hashCode => Object.hash(runtimeType, tenantId, businessService, id,
      businessId, action, workflowState);

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$_ProcessInstancesCopyWith<_$_ProcessInstances> get copyWith =>
      __$$_ProcessInstancesCopyWithImpl<_$_ProcessInstances>(this, _$identity);

  @override
  Map<String, dynamic> toJson() {
    return _$$_ProcessInstancesToJson(
      this,
    );
  }
}

abstract class _ProcessInstances implements ProcessInstances {
  const factory _ProcessInstances(
          {final String? tenantId,
          final String? businessService,
          final String? id,
          final String? businessId,
          final String? action,
          @JsonKey(name: 'state') final WorkflowState? workflowState}) =
      _$_ProcessInstances;

  factory _ProcessInstances.fromJson(Map<String, dynamic> json) =
      _$_ProcessInstances.fromJson;

  @override
  String? get tenantId;
  @override
  String? get businessService;
  @override
  String? get id;
  @override
  String? get businessId;
  @override
  String? get action;
  @override
  @JsonKey(name: 'state')
  WorkflowState? get workflowState;
  @override
  @JsonKey(ignore: true)
  _$$_ProcessInstancesCopyWith<_$_ProcessInstances> get copyWith =>
      throw _privateConstructorUsedError;
}

WorkflowState _$WorkflowStateFromJson(Map<String, dynamic> json) {
  return _WorkflowState.fromJson(json);
}

/// @nodoc
mixin _$WorkflowState {
  String? get tenantId => throw _privateConstructorUsedError;
  String? get businessServiceId => throw _privateConstructorUsedError;
  String? get applicationStatus => throw _privateConstructorUsedError;
  String? get state => throw _privateConstructorUsedError;
  bool? get isStartState => throw _privateConstructorUsedError;
  bool? get isTerminateState => throw _privateConstructorUsedError;
  bool? get isStateUpdatable => throw _privateConstructorUsedError;

  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;
  @JsonKey(ignore: true)
  $WorkflowStateCopyWith<WorkflowState> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $WorkflowStateCopyWith<$Res> {
  factory $WorkflowStateCopyWith(
          WorkflowState value, $Res Function(WorkflowState) then) =
      _$WorkflowStateCopyWithImpl<$Res, WorkflowState>;
  @useResult
  $Res call(
      {String? tenantId,
      String? businessServiceId,
      String? applicationStatus,
      String? state,
      bool? isStartState,
      bool? isTerminateState,
      bool? isStateUpdatable});
}

/// @nodoc
class _$WorkflowStateCopyWithImpl<$Res, $Val extends WorkflowState>
    implements $WorkflowStateCopyWith<$Res> {
  _$WorkflowStateCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? tenantId = freezed,
    Object? businessServiceId = freezed,
    Object? applicationStatus = freezed,
    Object? state = freezed,
    Object? isStartState = freezed,
    Object? isTerminateState = freezed,
    Object? isStateUpdatable = freezed,
  }) {
    return _then(_value.copyWith(
      tenantId: freezed == tenantId
          ? _value.tenantId
          : tenantId // ignore: cast_nullable_to_non_nullable
              as String?,
      businessServiceId: freezed == businessServiceId
          ? _value.businessServiceId
          : businessServiceId // ignore: cast_nullable_to_non_nullable
              as String?,
      applicationStatus: freezed == applicationStatus
          ? _value.applicationStatus
          : applicationStatus // ignore: cast_nullable_to_non_nullable
              as String?,
      state: freezed == state
          ? _value.state
          : state // ignore: cast_nullable_to_non_nullable
              as String?,
      isStartState: freezed == isStartState
          ? _value.isStartState
          : isStartState // ignore: cast_nullable_to_non_nullable
              as bool?,
      isTerminateState: freezed == isTerminateState
          ? _value.isTerminateState
          : isTerminateState // ignore: cast_nullable_to_non_nullable
              as bool?,
      isStateUpdatable: freezed == isStateUpdatable
          ? _value.isStateUpdatable
          : isStateUpdatable // ignore: cast_nullable_to_non_nullable
              as bool?,
    ) as $Val);
  }
}

/// @nodoc
abstract class _$$_WorkflowStateCopyWith<$Res>
    implements $WorkflowStateCopyWith<$Res> {
  factory _$$_WorkflowStateCopyWith(
          _$_WorkflowState value, $Res Function(_$_WorkflowState) then) =
      __$$_WorkflowStateCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call(
      {String? tenantId,
      String? businessServiceId,
      String? applicationStatus,
      String? state,
      bool? isStartState,
      bool? isTerminateState,
      bool? isStateUpdatable});
}

/// @nodoc
class __$$_WorkflowStateCopyWithImpl<$Res>
    extends _$WorkflowStateCopyWithImpl<$Res, _$_WorkflowState>
    implements _$$_WorkflowStateCopyWith<$Res> {
  __$$_WorkflowStateCopyWithImpl(
      _$_WorkflowState _value, $Res Function(_$_WorkflowState) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? tenantId = freezed,
    Object? businessServiceId = freezed,
    Object? applicationStatus = freezed,
    Object? state = freezed,
    Object? isStartState = freezed,
    Object? isTerminateState = freezed,
    Object? isStateUpdatable = freezed,
  }) {
    return _then(_$_WorkflowState(
      tenantId: freezed == tenantId
          ? _value.tenantId
          : tenantId // ignore: cast_nullable_to_non_nullable
              as String?,
      businessServiceId: freezed == businessServiceId
          ? _value.businessServiceId
          : businessServiceId // ignore: cast_nullable_to_non_nullable
              as String?,
      applicationStatus: freezed == applicationStatus
          ? _value.applicationStatus
          : applicationStatus // ignore: cast_nullable_to_non_nullable
              as String?,
      state: freezed == state
          ? _value.state
          : state // ignore: cast_nullable_to_non_nullable
              as String?,
      isStartState: freezed == isStartState
          ? _value.isStartState
          : isStartState // ignore: cast_nullable_to_non_nullable
              as bool?,
      isTerminateState: freezed == isTerminateState
          ? _value.isTerminateState
          : isTerminateState // ignore: cast_nullable_to_non_nullable
              as bool?,
      isStateUpdatable: freezed == isStateUpdatable
          ? _value.isStateUpdatable
          : isStateUpdatable // ignore: cast_nullable_to_non_nullable
              as bool?,
    ));
  }
}

/// @nodoc
@JsonSerializable()
class _$_WorkflowState implements _WorkflowState {
  const _$_WorkflowState(
      {this.tenantId,
      this.businessServiceId,
      this.applicationStatus,
      this.state,
      this.isStartState,
      this.isTerminateState,
      this.isStateUpdatable});

  factory _$_WorkflowState.fromJson(Map<String, dynamic> json) =>
      _$$_WorkflowStateFromJson(json);

  @override
  final String? tenantId;
  @override
  final String? businessServiceId;
  @override
  final String? applicationStatus;
  @override
  final String? state;
  @override
  final bool? isStartState;
  @override
  final bool? isTerminateState;
  @override
  final bool? isStateUpdatable;

  @override
  String toString() {
    return 'WorkflowState(tenantId: $tenantId, businessServiceId: $businessServiceId, applicationStatus: $applicationStatus, state: $state, isStartState: $isStartState, isTerminateState: $isTerminateState, isStateUpdatable: $isStateUpdatable)';
  }

  @override
  bool operator ==(dynamic other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$_WorkflowState &&
            (identical(other.tenantId, tenantId) ||
                other.tenantId == tenantId) &&
            (identical(other.businessServiceId, businessServiceId) ||
                other.businessServiceId == businessServiceId) &&
            (identical(other.applicationStatus, applicationStatus) ||
                other.applicationStatus == applicationStatus) &&
            (identical(other.state, state) || other.state == state) &&
            (identical(other.isStartState, isStartState) ||
                other.isStartState == isStartState) &&
            (identical(other.isTerminateState, isTerminateState) ||
                other.isTerminateState == isTerminateState) &&
            (identical(other.isStateUpdatable, isStateUpdatable) ||
                other.isStateUpdatable == isStateUpdatable));
  }

  @JsonKey(ignore: true)
  @override
  int get hashCode => Object.hash(
      runtimeType,
      tenantId,
      businessServiceId,
      applicationStatus,
      state,
      isStartState,
      isTerminateState,
      isStateUpdatable);

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$_WorkflowStateCopyWith<_$_WorkflowState> get copyWith =>
      __$$_WorkflowStateCopyWithImpl<_$_WorkflowState>(this, _$identity);

  @override
  Map<String, dynamic> toJson() {
    return _$$_WorkflowStateToJson(
      this,
    );
  }
}

abstract class _WorkflowState implements WorkflowState {
  const factory _WorkflowState(
      {final String? tenantId,
      final String? businessServiceId,
      final String? applicationStatus,
      final String? state,
      final bool? isStartState,
      final bool? isTerminateState,
      final bool? isStateUpdatable}) = _$_WorkflowState;

  factory _WorkflowState.fromJson(Map<String, dynamic> json) =
      _$_WorkflowState.fromJson;

  @override
  String? get tenantId;
  @override
  String? get businessServiceId;
  @override
  String? get applicationStatus;
  @override
  String? get state;
  @override
  bool? get isStartState;
  @override
  bool? get isTerminateState;
  @override
  bool? get isStateUpdatable;
  @override
  @JsonKey(ignore: true)
  _$$_WorkflowStateCopyWith<_$_WorkflowState> get copyWith =>
      throw _privateConstructorUsedError;
}
