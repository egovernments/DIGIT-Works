// coverage:ignore-file
// GENERATED CODE - DO NOT MODIFY BY HAND
// ignore_for_file: type=lint
// ignore_for_file: unused_element, deprecated_member_use, deprecated_member_use_from_same_package, use_function_type_syntax_for_parameters, unnecessary_const, avoid_init_to_null, invalid_override_different_default_values_named, prefer_expression_function_bodies, annotate_overrides, invalid_annotation_target, unnecessary_question_mark

part of 'muster_workflow_model.dart';

// **************************************************************************
// FreezedGenerator
// **************************************************************************

T _$identity<T>(T value) => value;

final _privateConstructorUsedError = UnsupportedError(
    'It seems like you constructed your class using `MyClass._()`. This constructor is only meant to be used by freezed and you are not supposed to need it nor use it.\nPlease check the documentation here for more information: https://github.com/rrousselGit/freezed#adding-getters-and-methods-to-our-models');

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
abstract class _$$MusterWorkFlowModelImplCopyWith<$Res>
    implements $MusterWorkFlowModelCopyWith<$Res> {
  factory _$$MusterWorkFlowModelImplCopyWith(_$MusterWorkFlowModelImpl value,
          $Res Function(_$MusterWorkFlowModelImpl) then) =
      __$$MusterWorkFlowModelImplCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call(
      {@JsonKey(name: 'ProcessInstances')
          List<ProcessInstances>? processInstances});
}

/// @nodoc
class __$$MusterWorkFlowModelImplCopyWithImpl<$Res>
    extends _$MusterWorkFlowModelCopyWithImpl<$Res, _$MusterWorkFlowModelImpl>
    implements _$$MusterWorkFlowModelImplCopyWith<$Res> {
  __$$MusterWorkFlowModelImplCopyWithImpl(_$MusterWorkFlowModelImpl _value,
      $Res Function(_$MusterWorkFlowModelImpl) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? processInstances = freezed,
  }) {
    return _then(_$MusterWorkFlowModelImpl(
      processInstances: freezed == processInstances
          ? _value._processInstances
          : processInstances // ignore: cast_nullable_to_non_nullable
              as List<ProcessInstances>?,
    ));
  }
}

/// @nodoc
@JsonSerializable()
class _$MusterWorkFlowModelImpl implements _MusterWorkFlowModel {
  const _$MusterWorkFlowModelImpl(
      {@JsonKey(name: 'ProcessInstances')
          final List<ProcessInstances>? processInstances})
      : _processInstances = processInstances;

  factory _$MusterWorkFlowModelImpl.fromJson(Map<String, dynamic> json) =>
      _$$MusterWorkFlowModelImplFromJson(json);

  final List<ProcessInstances>? _processInstances;
  @override
  @JsonKey(name: 'ProcessInstances')
  List<ProcessInstances>? get processInstances {
    final value = _processInstances;
    if (value == null) return null;
    if (_processInstances is EqualUnmodifiableListView)
      return _processInstances;
    // ignore: implicit_dynamic_type
    return EqualUnmodifiableListView(value);
  }

  @override
  String toString() {
    return 'MusterWorkFlowModel(processInstances: $processInstances)';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$MusterWorkFlowModelImpl &&
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
  _$$MusterWorkFlowModelImplCopyWith<_$MusterWorkFlowModelImpl> get copyWith =>
      __$$MusterWorkFlowModelImplCopyWithImpl<_$MusterWorkFlowModelImpl>(
          this, _$identity);

  @override
  Map<String, dynamic> toJson() {
    return _$$MusterWorkFlowModelImplToJson(
      this,
    );
  }
}

abstract class _MusterWorkFlowModel implements MusterWorkFlowModel {
  const factory _MusterWorkFlowModel(
          {@JsonKey(name: 'ProcessInstances')
              final List<ProcessInstances>? processInstances}) =
      _$MusterWorkFlowModelImpl;

  factory _MusterWorkFlowModel.fromJson(Map<String, dynamic> json) =
      _$MusterWorkFlowModelImpl.fromJson;

  @override
  @JsonKey(name: 'ProcessInstances')
  List<ProcessInstances>? get processInstances;
  @override
  @JsonKey(ignore: true)
  _$$MusterWorkFlowModelImplCopyWith<_$MusterWorkFlowModelImpl> get copyWith =>
      throw _privateConstructorUsedError;
}

ProcessInstances _$ProcessInstancesFromJson(Map<String, dynamic> json) {
  return _ProcessInstances.fromJson(json);
}

/// @nodoc
mixin _$ProcessInstances {
  String get tenantId => throw _privateConstructorUsedError;
  String? get businessService => throw _privateConstructorUsedError;
  String? get id => throw _privateConstructorUsedError;
  String? get businessId => throw _privateConstructorUsedError;
  String? get action => throw _privateConstructorUsedError;
  AuditDetails? get auditDetails => throw _privateConstructorUsedError;
  Assigner? get assigner => throw _privateConstructorUsedError;
  List<Assignees>? get assignes => throw _privateConstructorUsedError;
  List<WorkflowDocument>? get documents => throw _privateConstructorUsedError;
  String? get comment => throw _privateConstructorUsedError;
  List<NextActions>? get nextActions => throw _privateConstructorUsedError;
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
      {String tenantId,
      String? businessService,
      String? id,
      String? businessId,
      String? action,
      AuditDetails? auditDetails,
      Assigner? assigner,
      List<Assignees>? assignes,
      List<WorkflowDocument>? documents,
      String? comment,
      List<NextActions>? nextActions,
      @JsonKey(name: 'state') WorkflowState? workflowState});

  $AuditDetailsCopyWith<$Res>? get auditDetails;
  $AssignerCopyWith<$Res>? get assigner;
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
    Object? tenantId = null,
    Object? businessService = freezed,
    Object? id = freezed,
    Object? businessId = freezed,
    Object? action = freezed,
    Object? auditDetails = freezed,
    Object? assigner = freezed,
    Object? assignes = freezed,
    Object? documents = freezed,
    Object? comment = freezed,
    Object? nextActions = freezed,
    Object? workflowState = freezed,
  }) {
    return _then(_value.copyWith(
      tenantId: null == tenantId
          ? _value.tenantId
          : tenantId // ignore: cast_nullable_to_non_nullable
              as String,
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
      auditDetails: freezed == auditDetails
          ? _value.auditDetails
          : auditDetails // ignore: cast_nullable_to_non_nullable
              as AuditDetails?,
      assigner: freezed == assigner
          ? _value.assigner
          : assigner // ignore: cast_nullable_to_non_nullable
              as Assigner?,
      assignes: freezed == assignes
          ? _value.assignes
          : assignes // ignore: cast_nullable_to_non_nullable
              as List<Assignees>?,
      documents: freezed == documents
          ? _value.documents
          : documents // ignore: cast_nullable_to_non_nullable
              as List<WorkflowDocument>?,
      comment: freezed == comment
          ? _value.comment
          : comment // ignore: cast_nullable_to_non_nullable
              as String?,
      nextActions: freezed == nextActions
          ? _value.nextActions
          : nextActions // ignore: cast_nullable_to_non_nullable
              as List<NextActions>?,
      workflowState: freezed == workflowState
          ? _value.workflowState
          : workflowState // ignore: cast_nullable_to_non_nullable
              as WorkflowState?,
    ) as $Val);
  }

  @override
  @pragma('vm:prefer-inline')
  $AuditDetailsCopyWith<$Res>? get auditDetails {
    if (_value.auditDetails == null) {
      return null;
    }

    return $AuditDetailsCopyWith<$Res>(_value.auditDetails!, (value) {
      return _then(_value.copyWith(auditDetails: value) as $Val);
    });
  }

  @override
  @pragma('vm:prefer-inline')
  $AssignerCopyWith<$Res>? get assigner {
    if (_value.assigner == null) {
      return null;
    }

    return $AssignerCopyWith<$Res>(_value.assigner!, (value) {
      return _then(_value.copyWith(assigner: value) as $Val);
    });
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
abstract class _$$ProcessInstancesImplCopyWith<$Res>
    implements $ProcessInstancesCopyWith<$Res> {
  factory _$$ProcessInstancesImplCopyWith(_$ProcessInstancesImpl value,
          $Res Function(_$ProcessInstancesImpl) then) =
      __$$ProcessInstancesImplCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call(
      {String tenantId,
      String? businessService,
      String? id,
      String? businessId,
      String? action,
      AuditDetails? auditDetails,
      Assigner? assigner,
      List<Assignees>? assignes,
      List<WorkflowDocument>? documents,
      String? comment,
      List<NextActions>? nextActions,
      @JsonKey(name: 'state') WorkflowState? workflowState});

  @override
  $AuditDetailsCopyWith<$Res>? get auditDetails;
  @override
  $AssignerCopyWith<$Res>? get assigner;
  @override
  $WorkflowStateCopyWith<$Res>? get workflowState;
}

/// @nodoc
class __$$ProcessInstancesImplCopyWithImpl<$Res>
    extends _$ProcessInstancesCopyWithImpl<$Res, _$ProcessInstancesImpl>
    implements _$$ProcessInstancesImplCopyWith<$Res> {
  __$$ProcessInstancesImplCopyWithImpl(_$ProcessInstancesImpl _value,
      $Res Function(_$ProcessInstancesImpl) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? tenantId = null,
    Object? businessService = freezed,
    Object? id = freezed,
    Object? businessId = freezed,
    Object? action = freezed,
    Object? auditDetails = freezed,
    Object? assigner = freezed,
    Object? assignes = freezed,
    Object? documents = freezed,
    Object? comment = freezed,
    Object? nextActions = freezed,
    Object? workflowState = freezed,
  }) {
    return _then(_$ProcessInstancesImpl(
      tenantId: null == tenantId
          ? _value.tenantId
          : tenantId // ignore: cast_nullable_to_non_nullable
              as String,
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
      auditDetails: freezed == auditDetails
          ? _value.auditDetails
          : auditDetails // ignore: cast_nullable_to_non_nullable
              as AuditDetails?,
      assigner: freezed == assigner
          ? _value.assigner
          : assigner // ignore: cast_nullable_to_non_nullable
              as Assigner?,
      assignes: freezed == assignes
          ? _value._assignes
          : assignes // ignore: cast_nullable_to_non_nullable
              as List<Assignees>?,
      documents: freezed == documents
          ? _value._documents
          : documents // ignore: cast_nullable_to_non_nullable
              as List<WorkflowDocument>?,
      comment: freezed == comment
          ? _value.comment
          : comment // ignore: cast_nullable_to_non_nullable
              as String?,
      nextActions: freezed == nextActions
          ? _value._nextActions
          : nextActions // ignore: cast_nullable_to_non_nullable
              as List<NextActions>?,
      workflowState: freezed == workflowState
          ? _value.workflowState
          : workflowState // ignore: cast_nullable_to_non_nullable
              as WorkflowState?,
    ));
  }
}

/// @nodoc
@JsonSerializable()
class _$ProcessInstancesImpl implements _ProcessInstances {
  const _$ProcessInstancesImpl(
      {required this.tenantId,
      this.businessService,
      this.id,
      this.businessId,
      this.action,
      this.auditDetails,
      this.assigner,
      final List<Assignees>? assignes,
      final List<WorkflowDocument>? documents,
      this.comment,
      final List<NextActions>? nextActions,
      @JsonKey(name: 'state') this.workflowState})
      : _assignes = assignes,
        _documents = documents,
        _nextActions = nextActions;

  factory _$ProcessInstancesImpl.fromJson(Map<String, dynamic> json) =>
      _$$ProcessInstancesImplFromJson(json);

  @override
  final String tenantId;
  @override
  final String? businessService;
  @override
  final String? id;
  @override
  final String? businessId;
  @override
  final String? action;
  @override
  final AuditDetails? auditDetails;
  @override
  final Assigner? assigner;
  final List<Assignees>? _assignes;
  @override
  List<Assignees>? get assignes {
    final value = _assignes;
    if (value == null) return null;
    if (_assignes is EqualUnmodifiableListView) return _assignes;
    // ignore: implicit_dynamic_type
    return EqualUnmodifiableListView(value);
  }

  final List<WorkflowDocument>? _documents;
  @override
  List<WorkflowDocument>? get documents {
    final value = _documents;
    if (value == null) return null;
    if (_documents is EqualUnmodifiableListView) return _documents;
    // ignore: implicit_dynamic_type
    return EqualUnmodifiableListView(value);
  }

  @override
  final String? comment;
  final List<NextActions>? _nextActions;
  @override
  List<NextActions>? get nextActions {
    final value = _nextActions;
    if (value == null) return null;
    if (_nextActions is EqualUnmodifiableListView) return _nextActions;
    // ignore: implicit_dynamic_type
    return EqualUnmodifiableListView(value);
  }

  @override
  @JsonKey(name: 'state')
  final WorkflowState? workflowState;

  @override
  String toString() {
    return 'ProcessInstances(tenantId: $tenantId, businessService: $businessService, id: $id, businessId: $businessId, action: $action, auditDetails: $auditDetails, assigner: $assigner, assignes: $assignes, documents: $documents, comment: $comment, nextActions: $nextActions, workflowState: $workflowState)';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$ProcessInstancesImpl &&
            (identical(other.tenantId, tenantId) ||
                other.tenantId == tenantId) &&
            (identical(other.businessService, businessService) ||
                other.businessService == businessService) &&
            (identical(other.id, id) || other.id == id) &&
            (identical(other.businessId, businessId) ||
                other.businessId == businessId) &&
            (identical(other.action, action) || other.action == action) &&
            (identical(other.auditDetails, auditDetails) ||
                other.auditDetails == auditDetails) &&
            (identical(other.assigner, assigner) ||
                other.assigner == assigner) &&
            const DeepCollectionEquality().equals(other._assignes, _assignes) &&
            const DeepCollectionEquality()
                .equals(other._documents, _documents) &&
            (identical(other.comment, comment) || other.comment == comment) &&
            const DeepCollectionEquality()
                .equals(other._nextActions, _nextActions) &&
            (identical(other.workflowState, workflowState) ||
                other.workflowState == workflowState));
  }

  @JsonKey(ignore: true)
  @override
  int get hashCode => Object.hash(
      runtimeType,
      tenantId,
      businessService,
      id,
      businessId,
      action,
      auditDetails,
      assigner,
      const DeepCollectionEquality().hash(_assignes),
      const DeepCollectionEquality().hash(_documents),
      comment,
      const DeepCollectionEquality().hash(_nextActions),
      workflowState);

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$ProcessInstancesImplCopyWith<_$ProcessInstancesImpl> get copyWith =>
      __$$ProcessInstancesImplCopyWithImpl<_$ProcessInstancesImpl>(
          this, _$identity);

  @override
  Map<String, dynamic> toJson() {
    return _$$ProcessInstancesImplToJson(
      this,
    );
  }
}

abstract class _ProcessInstances implements ProcessInstances {
  const factory _ProcessInstances(
          {required final String tenantId,
          final String? businessService,
          final String? id,
          final String? businessId,
          final String? action,
          final AuditDetails? auditDetails,
          final Assigner? assigner,
          final List<Assignees>? assignes,
          final List<WorkflowDocument>? documents,
          final String? comment,
          final List<NextActions>? nextActions,
          @JsonKey(name: 'state') final WorkflowState? workflowState}) =
      _$ProcessInstancesImpl;

  factory _ProcessInstances.fromJson(Map<String, dynamic> json) =
      _$ProcessInstancesImpl.fromJson;

  @override
  String get tenantId;
  @override
  String? get businessService;
  @override
  String? get id;
  @override
  String? get businessId;
  @override
  String? get action;
  @override
  AuditDetails? get auditDetails;
  @override
  Assigner? get assigner;
  @override
  List<Assignees>? get assignes;
  @override
  List<WorkflowDocument>? get documents;
  @override
  String? get comment;
  @override
  List<NextActions>? get nextActions;
  @override
  @JsonKey(name: 'state')
  WorkflowState? get workflowState;
  @override
  @JsonKey(ignore: true)
  _$$ProcessInstancesImplCopyWith<_$ProcessInstancesImpl> get copyWith =>
      throw _privateConstructorUsedError;
}

NextActions _$NextActionsFromJson(Map<String, dynamic> json) {
  return _NextActions.fromJson(json);
}

/// @nodoc
mixin _$NextActions {
  String? get action => throw _privateConstructorUsedError;
  String? get uuid => throw _privateConstructorUsedError;
  String? get currentState => throw _privateConstructorUsedError;
  String? get nextState => throw _privateConstructorUsedError;
  String? get tenantId => throw _privateConstructorUsedError;
  List<String>? get roles => throw _privateConstructorUsedError;

  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;
  @JsonKey(ignore: true)
  $NextActionsCopyWith<NextActions> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $NextActionsCopyWith<$Res> {
  factory $NextActionsCopyWith(
          NextActions value, $Res Function(NextActions) then) =
      _$NextActionsCopyWithImpl<$Res, NextActions>;
  @useResult
  $Res call(
      {String? action,
      String? uuid,
      String? currentState,
      String? nextState,
      String? tenantId,
      List<String>? roles});
}

/// @nodoc
class _$NextActionsCopyWithImpl<$Res, $Val extends NextActions>
    implements $NextActionsCopyWith<$Res> {
  _$NextActionsCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? action = freezed,
    Object? uuid = freezed,
    Object? currentState = freezed,
    Object? nextState = freezed,
    Object? tenantId = freezed,
    Object? roles = freezed,
  }) {
    return _then(_value.copyWith(
      action: freezed == action
          ? _value.action
          : action // ignore: cast_nullable_to_non_nullable
              as String?,
      uuid: freezed == uuid
          ? _value.uuid
          : uuid // ignore: cast_nullable_to_non_nullable
              as String?,
      currentState: freezed == currentState
          ? _value.currentState
          : currentState // ignore: cast_nullable_to_non_nullable
              as String?,
      nextState: freezed == nextState
          ? _value.nextState
          : nextState // ignore: cast_nullable_to_non_nullable
              as String?,
      tenantId: freezed == tenantId
          ? _value.tenantId
          : tenantId // ignore: cast_nullable_to_non_nullable
              as String?,
      roles: freezed == roles
          ? _value.roles
          : roles // ignore: cast_nullable_to_non_nullable
              as List<String>?,
    ) as $Val);
  }
}

/// @nodoc
abstract class _$$NextActionsImplCopyWith<$Res>
    implements $NextActionsCopyWith<$Res> {
  factory _$$NextActionsImplCopyWith(
          _$NextActionsImpl value, $Res Function(_$NextActionsImpl) then) =
      __$$NextActionsImplCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call(
      {String? action,
      String? uuid,
      String? currentState,
      String? nextState,
      String? tenantId,
      List<String>? roles});
}

/// @nodoc
class __$$NextActionsImplCopyWithImpl<$Res>
    extends _$NextActionsCopyWithImpl<$Res, _$NextActionsImpl>
    implements _$$NextActionsImplCopyWith<$Res> {
  __$$NextActionsImplCopyWithImpl(
      _$NextActionsImpl _value, $Res Function(_$NextActionsImpl) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? action = freezed,
    Object? uuid = freezed,
    Object? currentState = freezed,
    Object? nextState = freezed,
    Object? tenantId = freezed,
    Object? roles = freezed,
  }) {
    return _then(_$NextActionsImpl(
      action: freezed == action
          ? _value.action
          : action // ignore: cast_nullable_to_non_nullable
              as String?,
      uuid: freezed == uuid
          ? _value.uuid
          : uuid // ignore: cast_nullable_to_non_nullable
              as String?,
      currentState: freezed == currentState
          ? _value.currentState
          : currentState // ignore: cast_nullable_to_non_nullable
              as String?,
      nextState: freezed == nextState
          ? _value.nextState
          : nextState // ignore: cast_nullable_to_non_nullable
              as String?,
      tenantId: freezed == tenantId
          ? _value.tenantId
          : tenantId // ignore: cast_nullable_to_non_nullable
              as String?,
      roles: freezed == roles
          ? _value._roles
          : roles // ignore: cast_nullable_to_non_nullable
              as List<String>?,
    ));
  }
}

/// @nodoc
@JsonSerializable()
class _$NextActionsImpl implements _NextActions {
  const _$NextActionsImpl(
      {this.action,
      this.uuid,
      this.currentState,
      this.nextState,
      this.tenantId,
      final List<String>? roles})
      : _roles = roles;

  factory _$NextActionsImpl.fromJson(Map<String, dynamic> json) =>
      _$$NextActionsImplFromJson(json);

  @override
  final String? action;
  @override
  final String? uuid;
  @override
  final String? currentState;
  @override
  final String? nextState;
  @override
  final String? tenantId;
  final List<String>? _roles;
  @override
  List<String>? get roles {
    final value = _roles;
    if (value == null) return null;
    if (_roles is EqualUnmodifiableListView) return _roles;
    // ignore: implicit_dynamic_type
    return EqualUnmodifiableListView(value);
  }

  @override
  String toString() {
    return 'NextActions(action: $action, uuid: $uuid, currentState: $currentState, nextState: $nextState, tenantId: $tenantId, roles: $roles)';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$NextActionsImpl &&
            (identical(other.action, action) || other.action == action) &&
            (identical(other.uuid, uuid) || other.uuid == uuid) &&
            (identical(other.currentState, currentState) ||
                other.currentState == currentState) &&
            (identical(other.nextState, nextState) ||
                other.nextState == nextState) &&
            (identical(other.tenantId, tenantId) ||
                other.tenantId == tenantId) &&
            const DeepCollectionEquality().equals(other._roles, _roles));
  }

  @JsonKey(ignore: true)
  @override
  int get hashCode => Object.hash(runtimeType, action, uuid, currentState,
      nextState, tenantId, const DeepCollectionEquality().hash(_roles));

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$NextActionsImplCopyWith<_$NextActionsImpl> get copyWith =>
      __$$NextActionsImplCopyWithImpl<_$NextActionsImpl>(this, _$identity);

  @override
  Map<String, dynamic> toJson() {
    return _$$NextActionsImplToJson(
      this,
    );
  }
}

abstract class _NextActions implements NextActions {
  const factory _NextActions(
      {final String? action,
      final String? uuid,
      final String? currentState,
      final String? nextState,
      final String? tenantId,
      final List<String>? roles}) = _$NextActionsImpl;

  factory _NextActions.fromJson(Map<String, dynamic> json) =
      _$NextActionsImpl.fromJson;

  @override
  String? get action;
  @override
  String? get uuid;
  @override
  String? get currentState;
  @override
  String? get nextState;
  @override
  String? get tenantId;
  @override
  List<String>? get roles;
  @override
  @JsonKey(ignore: true)
  _$$NextActionsImplCopyWith<_$NextActionsImpl> get copyWith =>
      throw _privateConstructorUsedError;
}

WorkflowDocument _$WorkflowDocumentFromJson(Map<String, dynamic> json) {
  return _WorkflowDocument.fromJson(json);
}

/// @nodoc
mixin _$WorkflowDocument {
  String? get documentType => throw _privateConstructorUsedError;
  String? get documentUid => throw _privateConstructorUsedError;
  String? get fileStoreId => throw _privateConstructorUsedError;
  String? get id => throw _privateConstructorUsedError;
  String? get tenantId => throw _privateConstructorUsedError;
  String? get fileStore => throw _privateConstructorUsedError;
  bool? get isActive => throw _privateConstructorUsedError;
  int? get indexing => throw _privateConstructorUsedError;
  @JsonKey(name: 'additionalDetails')
  DocumentAdditionalDetails? get documentAdditionalDetails =>
      throw _privateConstructorUsedError;

  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;
  @JsonKey(ignore: true)
  $WorkflowDocumentCopyWith<WorkflowDocument> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $WorkflowDocumentCopyWith<$Res> {
  factory $WorkflowDocumentCopyWith(
          WorkflowDocument value, $Res Function(WorkflowDocument) then) =
      _$WorkflowDocumentCopyWithImpl<$Res, WorkflowDocument>;
  @useResult
  $Res call(
      {String? documentType,
      String? documentUid,
      String? fileStoreId,
      String? id,
      String? tenantId,
      String? fileStore,
      bool? isActive,
      int? indexing,
      @JsonKey(name: 'additionalDetails')
          DocumentAdditionalDetails? documentAdditionalDetails});

  $DocumentAdditionalDetailsCopyWith<$Res>? get documentAdditionalDetails;
}

/// @nodoc
class _$WorkflowDocumentCopyWithImpl<$Res, $Val extends WorkflowDocument>
    implements $WorkflowDocumentCopyWith<$Res> {
  _$WorkflowDocumentCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? documentType = freezed,
    Object? documentUid = freezed,
    Object? fileStoreId = freezed,
    Object? id = freezed,
    Object? tenantId = freezed,
    Object? fileStore = freezed,
    Object? isActive = freezed,
    Object? indexing = freezed,
    Object? documentAdditionalDetails = freezed,
  }) {
    return _then(_value.copyWith(
      documentType: freezed == documentType
          ? _value.documentType
          : documentType // ignore: cast_nullable_to_non_nullable
              as String?,
      documentUid: freezed == documentUid
          ? _value.documentUid
          : documentUid // ignore: cast_nullable_to_non_nullable
              as String?,
      fileStoreId: freezed == fileStoreId
          ? _value.fileStoreId
          : fileStoreId // ignore: cast_nullable_to_non_nullable
              as String?,
      id: freezed == id
          ? _value.id
          : id // ignore: cast_nullable_to_non_nullable
              as String?,
      tenantId: freezed == tenantId
          ? _value.tenantId
          : tenantId // ignore: cast_nullable_to_non_nullable
              as String?,
      fileStore: freezed == fileStore
          ? _value.fileStore
          : fileStore // ignore: cast_nullable_to_non_nullable
              as String?,
      isActive: freezed == isActive
          ? _value.isActive
          : isActive // ignore: cast_nullable_to_non_nullable
              as bool?,
      indexing: freezed == indexing
          ? _value.indexing
          : indexing // ignore: cast_nullable_to_non_nullable
              as int?,
      documentAdditionalDetails: freezed == documentAdditionalDetails
          ? _value.documentAdditionalDetails
          : documentAdditionalDetails // ignore: cast_nullable_to_non_nullable
              as DocumentAdditionalDetails?,
    ) as $Val);
  }

  @override
  @pragma('vm:prefer-inline')
  $DocumentAdditionalDetailsCopyWith<$Res>? get documentAdditionalDetails {
    if (_value.documentAdditionalDetails == null) {
      return null;
    }

    return $DocumentAdditionalDetailsCopyWith<$Res>(
        _value.documentAdditionalDetails!, (value) {
      return _then(_value.copyWith(documentAdditionalDetails: value) as $Val);
    });
  }
}

/// @nodoc
abstract class _$$WorkflowDocumentImplCopyWith<$Res>
    implements $WorkflowDocumentCopyWith<$Res> {
  factory _$$WorkflowDocumentImplCopyWith(_$WorkflowDocumentImpl value,
          $Res Function(_$WorkflowDocumentImpl) then) =
      __$$WorkflowDocumentImplCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call(
      {String? documentType,
      String? documentUid,
      String? fileStoreId,
      String? id,
      String? tenantId,
      String? fileStore,
      bool? isActive,
      int? indexing,
      @JsonKey(name: 'additionalDetails')
          DocumentAdditionalDetails? documentAdditionalDetails});

  @override
  $DocumentAdditionalDetailsCopyWith<$Res>? get documentAdditionalDetails;
}

/// @nodoc
class __$$WorkflowDocumentImplCopyWithImpl<$Res>
    extends _$WorkflowDocumentCopyWithImpl<$Res, _$WorkflowDocumentImpl>
    implements _$$WorkflowDocumentImplCopyWith<$Res> {
  __$$WorkflowDocumentImplCopyWithImpl(_$WorkflowDocumentImpl _value,
      $Res Function(_$WorkflowDocumentImpl) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? documentType = freezed,
    Object? documentUid = freezed,
    Object? fileStoreId = freezed,
    Object? id = freezed,
    Object? tenantId = freezed,
    Object? fileStore = freezed,
    Object? isActive = freezed,
    Object? indexing = freezed,
    Object? documentAdditionalDetails = freezed,
  }) {
    return _then(_$WorkflowDocumentImpl(
      documentType: freezed == documentType
          ? _value.documentType
          : documentType // ignore: cast_nullable_to_non_nullable
              as String?,
      documentUid: freezed == documentUid
          ? _value.documentUid
          : documentUid // ignore: cast_nullable_to_non_nullable
              as String?,
      fileStoreId: freezed == fileStoreId
          ? _value.fileStoreId
          : fileStoreId // ignore: cast_nullable_to_non_nullable
              as String?,
      id: freezed == id
          ? _value.id
          : id // ignore: cast_nullable_to_non_nullable
              as String?,
      tenantId: freezed == tenantId
          ? _value.tenantId
          : tenantId // ignore: cast_nullable_to_non_nullable
              as String?,
      fileStore: freezed == fileStore
          ? _value.fileStore
          : fileStore // ignore: cast_nullable_to_non_nullable
              as String?,
      isActive: freezed == isActive
          ? _value.isActive
          : isActive // ignore: cast_nullable_to_non_nullable
              as bool?,
      indexing: freezed == indexing
          ? _value.indexing
          : indexing // ignore: cast_nullable_to_non_nullable
              as int?,
      documentAdditionalDetails: freezed == documentAdditionalDetails
          ? _value.documentAdditionalDetails
          : documentAdditionalDetails // ignore: cast_nullable_to_non_nullable
              as DocumentAdditionalDetails?,
    ));
  }
}

/// @nodoc
@JsonSerializable()
class _$WorkflowDocumentImpl implements _WorkflowDocument {
  const _$WorkflowDocumentImpl(
      {this.documentType,
      this.documentUid,
      this.fileStoreId,
      this.id,
      this.tenantId,
      this.fileStore,
      this.isActive,
      this.indexing,
      @JsonKey(name: 'additionalDetails') this.documentAdditionalDetails});

  factory _$WorkflowDocumentImpl.fromJson(Map<String, dynamic> json) =>
      _$$WorkflowDocumentImplFromJson(json);

  @override
  final String? documentType;
  @override
  final String? documentUid;
  @override
  final String? fileStoreId;
  @override
  final String? id;
  @override
  final String? tenantId;
  @override
  final String? fileStore;
  @override
  final bool? isActive;
  @override
  final int? indexing;
  @override
  @JsonKey(name: 'additionalDetails')
  final DocumentAdditionalDetails? documentAdditionalDetails;

  @override
  String toString() {
    return 'WorkflowDocument(documentType: $documentType, documentUid: $documentUid, fileStoreId: $fileStoreId, id: $id, tenantId: $tenantId, fileStore: $fileStore, isActive: $isActive, indexing: $indexing, documentAdditionalDetails: $documentAdditionalDetails)';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$WorkflowDocumentImpl &&
            (identical(other.documentType, documentType) ||
                other.documentType == documentType) &&
            (identical(other.documentUid, documentUid) ||
                other.documentUid == documentUid) &&
            (identical(other.fileStoreId, fileStoreId) ||
                other.fileStoreId == fileStoreId) &&
            (identical(other.id, id) || other.id == id) &&
            (identical(other.tenantId, tenantId) ||
                other.tenantId == tenantId) &&
            (identical(other.fileStore, fileStore) ||
                other.fileStore == fileStore) &&
            (identical(other.isActive, isActive) ||
                other.isActive == isActive) &&
            (identical(other.indexing, indexing) ||
                other.indexing == indexing) &&
            (identical(other.documentAdditionalDetails,
                    documentAdditionalDetails) ||
                other.documentAdditionalDetails == documentAdditionalDetails));
  }

  @JsonKey(ignore: true)
  @override
  int get hashCode => Object.hash(
      runtimeType,
      documentType,
      documentUid,
      fileStoreId,
      id,
      tenantId,
      fileStore,
      isActive,
      indexing,
      documentAdditionalDetails);

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$WorkflowDocumentImplCopyWith<_$WorkflowDocumentImpl> get copyWith =>
      __$$WorkflowDocumentImplCopyWithImpl<_$WorkflowDocumentImpl>(
          this, _$identity);

  @override
  Map<String, dynamic> toJson() {
    return _$$WorkflowDocumentImplToJson(
      this,
    );
  }
}

abstract class _WorkflowDocument implements WorkflowDocument {
  const factory _WorkflowDocument(
          {final String? documentType,
          final String? documentUid,
          final String? fileStoreId,
          final String? id,
          final String? tenantId,
          final String? fileStore,
          final bool? isActive,
          final int? indexing,
          @JsonKey(name: 'additionalDetails')
              final DocumentAdditionalDetails? documentAdditionalDetails}) =
      _$WorkflowDocumentImpl;

  factory _WorkflowDocument.fromJson(Map<String, dynamic> json) =
      _$WorkflowDocumentImpl.fromJson;

  @override
  String? get documentType;
  @override
  String? get documentUid;
  @override
  String? get fileStoreId;
  @override
  String? get id;
  @override
  String? get tenantId;
  @override
  String? get fileStore;
  @override
  bool? get isActive;
  @override
  int? get indexing;
  @override
  @JsonKey(name: 'additionalDetails')
  DocumentAdditionalDetails? get documentAdditionalDetails;
  @override
  @JsonKey(ignore: true)
  _$$WorkflowDocumentImplCopyWith<_$WorkflowDocumentImpl> get copyWith =>
      throw _privateConstructorUsedError;
}

DocumentAdditionalDetails _$DocumentAdditionalDetailsFromJson(
    Map<String, dynamic> json) {
  return _DocumentAdditionalDetails.fromJson(json);
}

/// @nodoc
mixin _$DocumentAdditionalDetails {
  String? get fileName => throw _privateConstructorUsedError;
  String? get fileType => throw _privateConstructorUsedError;
  String? get tenantId => throw _privateConstructorUsedError;

  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;
  @JsonKey(ignore: true)
  $DocumentAdditionalDetailsCopyWith<DocumentAdditionalDetails> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $DocumentAdditionalDetailsCopyWith<$Res> {
  factory $DocumentAdditionalDetailsCopyWith(DocumentAdditionalDetails value,
          $Res Function(DocumentAdditionalDetails) then) =
      _$DocumentAdditionalDetailsCopyWithImpl<$Res, DocumentAdditionalDetails>;
  @useResult
  $Res call({String? fileName, String? fileType, String? tenantId});
}

/// @nodoc
class _$DocumentAdditionalDetailsCopyWithImpl<$Res,
        $Val extends DocumentAdditionalDetails>
    implements $DocumentAdditionalDetailsCopyWith<$Res> {
  _$DocumentAdditionalDetailsCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? fileName = freezed,
    Object? fileType = freezed,
    Object? tenantId = freezed,
  }) {
    return _then(_value.copyWith(
      fileName: freezed == fileName
          ? _value.fileName
          : fileName // ignore: cast_nullable_to_non_nullable
              as String?,
      fileType: freezed == fileType
          ? _value.fileType
          : fileType // ignore: cast_nullable_to_non_nullable
              as String?,
      tenantId: freezed == tenantId
          ? _value.tenantId
          : tenantId // ignore: cast_nullable_to_non_nullable
              as String?,
    ) as $Val);
  }
}

/// @nodoc
abstract class _$$DocumentAdditionalDetailsImplCopyWith<$Res>
    implements $DocumentAdditionalDetailsCopyWith<$Res> {
  factory _$$DocumentAdditionalDetailsImplCopyWith(
          _$DocumentAdditionalDetailsImpl value,
          $Res Function(_$DocumentAdditionalDetailsImpl) then) =
      __$$DocumentAdditionalDetailsImplCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call({String? fileName, String? fileType, String? tenantId});
}

/// @nodoc
class __$$DocumentAdditionalDetailsImplCopyWithImpl<$Res>
    extends _$DocumentAdditionalDetailsCopyWithImpl<$Res,
        _$DocumentAdditionalDetailsImpl>
    implements _$$DocumentAdditionalDetailsImplCopyWith<$Res> {
  __$$DocumentAdditionalDetailsImplCopyWithImpl(
      _$DocumentAdditionalDetailsImpl _value,
      $Res Function(_$DocumentAdditionalDetailsImpl) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? fileName = freezed,
    Object? fileType = freezed,
    Object? tenantId = freezed,
  }) {
    return _then(_$DocumentAdditionalDetailsImpl(
      fileName: freezed == fileName
          ? _value.fileName
          : fileName // ignore: cast_nullable_to_non_nullable
              as String?,
      fileType: freezed == fileType
          ? _value.fileType
          : fileType // ignore: cast_nullable_to_non_nullable
              as String?,
      tenantId: freezed == tenantId
          ? _value.tenantId
          : tenantId // ignore: cast_nullable_to_non_nullable
              as String?,
    ));
  }
}

/// @nodoc
@JsonSerializable()
class _$DocumentAdditionalDetailsImpl implements _DocumentAdditionalDetails {
  const _$DocumentAdditionalDetailsImpl(
      {this.fileName, this.fileType, this.tenantId});

  factory _$DocumentAdditionalDetailsImpl.fromJson(Map<String, dynamic> json) =>
      _$$DocumentAdditionalDetailsImplFromJson(json);

  @override
  final String? fileName;
  @override
  final String? fileType;
  @override
  final String? tenantId;

  @override
  String toString() {
    return 'DocumentAdditionalDetails(fileName: $fileName, fileType: $fileType, tenantId: $tenantId)';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$DocumentAdditionalDetailsImpl &&
            (identical(other.fileName, fileName) ||
                other.fileName == fileName) &&
            (identical(other.fileType, fileType) ||
                other.fileType == fileType) &&
            (identical(other.tenantId, tenantId) ||
                other.tenantId == tenantId));
  }

  @JsonKey(ignore: true)
  @override
  int get hashCode => Object.hash(runtimeType, fileName, fileType, tenantId);

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$DocumentAdditionalDetailsImplCopyWith<_$DocumentAdditionalDetailsImpl>
      get copyWith => __$$DocumentAdditionalDetailsImplCopyWithImpl<
          _$DocumentAdditionalDetailsImpl>(this, _$identity);

  @override
  Map<String, dynamic> toJson() {
    return _$$DocumentAdditionalDetailsImplToJson(
      this,
    );
  }
}

abstract class _DocumentAdditionalDetails implements DocumentAdditionalDetails {
  const factory _DocumentAdditionalDetails(
      {final String? fileName,
      final String? fileType,
      final String? tenantId}) = _$DocumentAdditionalDetailsImpl;

  factory _DocumentAdditionalDetails.fromJson(Map<String, dynamic> json) =
      _$DocumentAdditionalDetailsImpl.fromJson;

  @override
  String? get fileName;
  @override
  String? get fileType;
  @override
  String? get tenantId;
  @override
  @JsonKey(ignore: true)
  _$$DocumentAdditionalDetailsImplCopyWith<_$DocumentAdditionalDetailsImpl>
      get copyWith => throw _privateConstructorUsedError;
}

Assignees _$AssigneesFromJson(Map<String, dynamic> json) {
  return _Assignees.fromJson(json);
}

/// @nodoc
mixin _$Assignees {
  String? get emailId => throw _privateConstructorUsedError;
  int? get id => throw _privateConstructorUsedError;
  String? get mobileNumber => throw _privateConstructorUsedError;
  String? get name => throw _privateConstructorUsedError;
  String? get tenantId => throw _privateConstructorUsedError;
  String? get uuid => throw _privateConstructorUsedError;
  String? get userName => throw _privateConstructorUsedError;

  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;
  @JsonKey(ignore: true)
  $AssigneesCopyWith<Assignees> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $AssigneesCopyWith<$Res> {
  factory $AssigneesCopyWith(Assignees value, $Res Function(Assignees) then) =
      _$AssigneesCopyWithImpl<$Res, Assignees>;
  @useResult
  $Res call(
      {String? emailId,
      int? id,
      String? mobileNumber,
      String? name,
      String? tenantId,
      String? uuid,
      String? userName});
}

/// @nodoc
class _$AssigneesCopyWithImpl<$Res, $Val extends Assignees>
    implements $AssigneesCopyWith<$Res> {
  _$AssigneesCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? emailId = freezed,
    Object? id = freezed,
    Object? mobileNumber = freezed,
    Object? name = freezed,
    Object? tenantId = freezed,
    Object? uuid = freezed,
    Object? userName = freezed,
  }) {
    return _then(_value.copyWith(
      emailId: freezed == emailId
          ? _value.emailId
          : emailId // ignore: cast_nullable_to_non_nullable
              as String?,
      id: freezed == id
          ? _value.id
          : id // ignore: cast_nullable_to_non_nullable
              as int?,
      mobileNumber: freezed == mobileNumber
          ? _value.mobileNumber
          : mobileNumber // ignore: cast_nullable_to_non_nullable
              as String?,
      name: freezed == name
          ? _value.name
          : name // ignore: cast_nullable_to_non_nullable
              as String?,
      tenantId: freezed == tenantId
          ? _value.tenantId
          : tenantId // ignore: cast_nullable_to_non_nullable
              as String?,
      uuid: freezed == uuid
          ? _value.uuid
          : uuid // ignore: cast_nullable_to_non_nullable
              as String?,
      userName: freezed == userName
          ? _value.userName
          : userName // ignore: cast_nullable_to_non_nullable
              as String?,
    ) as $Val);
  }
}

/// @nodoc
abstract class _$$AssigneesImplCopyWith<$Res>
    implements $AssigneesCopyWith<$Res> {
  factory _$$AssigneesImplCopyWith(
          _$AssigneesImpl value, $Res Function(_$AssigneesImpl) then) =
      __$$AssigneesImplCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call(
      {String? emailId,
      int? id,
      String? mobileNumber,
      String? name,
      String? tenantId,
      String? uuid,
      String? userName});
}

/// @nodoc
class __$$AssigneesImplCopyWithImpl<$Res>
    extends _$AssigneesCopyWithImpl<$Res, _$AssigneesImpl>
    implements _$$AssigneesImplCopyWith<$Res> {
  __$$AssigneesImplCopyWithImpl(
      _$AssigneesImpl _value, $Res Function(_$AssigneesImpl) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? emailId = freezed,
    Object? id = freezed,
    Object? mobileNumber = freezed,
    Object? name = freezed,
    Object? tenantId = freezed,
    Object? uuid = freezed,
    Object? userName = freezed,
  }) {
    return _then(_$AssigneesImpl(
      emailId: freezed == emailId
          ? _value.emailId
          : emailId // ignore: cast_nullable_to_non_nullable
              as String?,
      id: freezed == id
          ? _value.id
          : id // ignore: cast_nullable_to_non_nullable
              as int?,
      mobileNumber: freezed == mobileNumber
          ? _value.mobileNumber
          : mobileNumber // ignore: cast_nullable_to_non_nullable
              as String?,
      name: freezed == name
          ? _value.name
          : name // ignore: cast_nullable_to_non_nullable
              as String?,
      tenantId: freezed == tenantId
          ? _value.tenantId
          : tenantId // ignore: cast_nullable_to_non_nullable
              as String?,
      uuid: freezed == uuid
          ? _value.uuid
          : uuid // ignore: cast_nullable_to_non_nullable
              as String?,
      userName: freezed == userName
          ? _value.userName
          : userName // ignore: cast_nullable_to_non_nullable
              as String?,
    ));
  }
}

/// @nodoc
@JsonSerializable()
class _$AssigneesImpl implements _Assignees {
  const _$AssigneesImpl(
      {this.emailId,
      this.id,
      this.mobileNumber,
      this.name,
      this.tenantId,
      this.uuid,
      this.userName});

  factory _$AssigneesImpl.fromJson(Map<String, dynamic> json) =>
      _$$AssigneesImplFromJson(json);

  @override
  final String? emailId;
  @override
  final int? id;
  @override
  final String? mobileNumber;
  @override
  final String? name;
  @override
  final String? tenantId;
  @override
  final String? uuid;
  @override
  final String? userName;

  @override
  String toString() {
    return 'Assignees(emailId: $emailId, id: $id, mobileNumber: $mobileNumber, name: $name, tenantId: $tenantId, uuid: $uuid, userName: $userName)';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$AssigneesImpl &&
            (identical(other.emailId, emailId) || other.emailId == emailId) &&
            (identical(other.id, id) || other.id == id) &&
            (identical(other.mobileNumber, mobileNumber) ||
                other.mobileNumber == mobileNumber) &&
            (identical(other.name, name) || other.name == name) &&
            (identical(other.tenantId, tenantId) ||
                other.tenantId == tenantId) &&
            (identical(other.uuid, uuid) || other.uuid == uuid) &&
            (identical(other.userName, userName) ||
                other.userName == userName));
  }

  @JsonKey(ignore: true)
  @override
  int get hashCode => Object.hash(
      runtimeType, emailId, id, mobileNumber, name, tenantId, uuid, userName);

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$AssigneesImplCopyWith<_$AssigneesImpl> get copyWith =>
      __$$AssigneesImplCopyWithImpl<_$AssigneesImpl>(this, _$identity);

  @override
  Map<String, dynamic> toJson() {
    return _$$AssigneesImplToJson(
      this,
    );
  }
}

abstract class _Assignees implements Assignees {
  const factory _Assignees(
      {final String? emailId,
      final int? id,
      final String? mobileNumber,
      final String? name,
      final String? tenantId,
      final String? uuid,
      final String? userName}) = _$AssigneesImpl;

  factory _Assignees.fromJson(Map<String, dynamic> json) =
      _$AssigneesImpl.fromJson;

  @override
  String? get emailId;
  @override
  int? get id;
  @override
  String? get mobileNumber;
  @override
  String? get name;
  @override
  String? get tenantId;
  @override
  String? get uuid;
  @override
  String? get userName;
  @override
  @JsonKey(ignore: true)
  _$$AssigneesImplCopyWith<_$AssigneesImpl> get copyWith =>
      throw _privateConstructorUsedError;
}

WorkflowState _$WorkflowStateFromJson(Map<String, dynamic> json) {
  return _WorkflowState.fromJson(json);
}

/// @nodoc
mixin _$WorkflowState {
  String get tenantId => throw _privateConstructorUsedError;
  String? get businessServiceId => throw _privateConstructorUsedError;
  String? get applicationStatus => throw _privateConstructorUsedError;
  String? get state => throw _privateConstructorUsedError;
  bool? get isStartState => throw _privateConstructorUsedError;
  bool? get isTerminateState => throw _privateConstructorUsedError;
  List<WorkflowActions>? get actions => throw _privateConstructorUsedError;
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
      {String tenantId,
      String? businessServiceId,
      String? applicationStatus,
      String? state,
      bool? isStartState,
      bool? isTerminateState,
      List<WorkflowActions>? actions,
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
    Object? tenantId = null,
    Object? businessServiceId = freezed,
    Object? applicationStatus = freezed,
    Object? state = freezed,
    Object? isStartState = freezed,
    Object? isTerminateState = freezed,
    Object? actions = freezed,
    Object? isStateUpdatable = freezed,
  }) {
    return _then(_value.copyWith(
      tenantId: null == tenantId
          ? _value.tenantId
          : tenantId // ignore: cast_nullable_to_non_nullable
              as String,
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
      actions: freezed == actions
          ? _value.actions
          : actions // ignore: cast_nullable_to_non_nullable
              as List<WorkflowActions>?,
      isStateUpdatable: freezed == isStateUpdatable
          ? _value.isStateUpdatable
          : isStateUpdatable // ignore: cast_nullable_to_non_nullable
              as bool?,
    ) as $Val);
  }
}

/// @nodoc
abstract class _$$WorkflowStateImplCopyWith<$Res>
    implements $WorkflowStateCopyWith<$Res> {
  factory _$$WorkflowStateImplCopyWith(
          _$WorkflowStateImpl value, $Res Function(_$WorkflowStateImpl) then) =
      __$$WorkflowStateImplCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call(
      {String tenantId,
      String? businessServiceId,
      String? applicationStatus,
      String? state,
      bool? isStartState,
      bool? isTerminateState,
      List<WorkflowActions>? actions,
      bool? isStateUpdatable});
}

/// @nodoc
class __$$WorkflowStateImplCopyWithImpl<$Res>
    extends _$WorkflowStateCopyWithImpl<$Res, _$WorkflowStateImpl>
    implements _$$WorkflowStateImplCopyWith<$Res> {
  __$$WorkflowStateImplCopyWithImpl(
      _$WorkflowStateImpl _value, $Res Function(_$WorkflowStateImpl) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? tenantId = null,
    Object? businessServiceId = freezed,
    Object? applicationStatus = freezed,
    Object? state = freezed,
    Object? isStartState = freezed,
    Object? isTerminateState = freezed,
    Object? actions = freezed,
    Object? isStateUpdatable = freezed,
  }) {
    return _then(_$WorkflowStateImpl(
      tenantId: null == tenantId
          ? _value.tenantId
          : tenantId // ignore: cast_nullable_to_non_nullable
              as String,
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
      actions: freezed == actions
          ? _value._actions
          : actions // ignore: cast_nullable_to_non_nullable
              as List<WorkflowActions>?,
      isStateUpdatable: freezed == isStateUpdatable
          ? _value.isStateUpdatable
          : isStateUpdatable // ignore: cast_nullable_to_non_nullable
              as bool?,
    ));
  }
}

/// @nodoc
@JsonSerializable()
class _$WorkflowStateImpl implements _WorkflowState {
  const _$WorkflowStateImpl(
      {required this.tenantId,
      this.businessServiceId,
      this.applicationStatus,
      this.state,
      this.isStartState,
      this.isTerminateState,
      final List<WorkflowActions>? actions,
      this.isStateUpdatable})
      : _actions = actions;

  factory _$WorkflowStateImpl.fromJson(Map<String, dynamic> json) =>
      _$$WorkflowStateImplFromJson(json);

  @override
  final String tenantId;
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
  final List<WorkflowActions>? _actions;
  @override
  List<WorkflowActions>? get actions {
    final value = _actions;
    if (value == null) return null;
    if (_actions is EqualUnmodifiableListView) return _actions;
    // ignore: implicit_dynamic_type
    return EqualUnmodifiableListView(value);
  }

  @override
  final bool? isStateUpdatable;

  @override
  String toString() {
    return 'WorkflowState(tenantId: $tenantId, businessServiceId: $businessServiceId, applicationStatus: $applicationStatus, state: $state, isStartState: $isStartState, isTerminateState: $isTerminateState, actions: $actions, isStateUpdatable: $isStateUpdatable)';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$WorkflowStateImpl &&
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
            const DeepCollectionEquality().equals(other._actions, _actions) &&
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
      const DeepCollectionEquality().hash(_actions),
      isStateUpdatable);

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$WorkflowStateImplCopyWith<_$WorkflowStateImpl> get copyWith =>
      __$$WorkflowStateImplCopyWithImpl<_$WorkflowStateImpl>(this, _$identity);

  @override
  Map<String, dynamic> toJson() {
    return _$$WorkflowStateImplToJson(
      this,
    );
  }
}

abstract class _WorkflowState implements WorkflowState {
  const factory _WorkflowState(
      {required final String tenantId,
      final String? businessServiceId,
      final String? applicationStatus,
      final String? state,
      final bool? isStartState,
      final bool? isTerminateState,
      final List<WorkflowActions>? actions,
      final bool? isStateUpdatable}) = _$WorkflowStateImpl;

  factory _WorkflowState.fromJson(Map<String, dynamic> json) =
      _$WorkflowStateImpl.fromJson;

  @override
  String get tenantId;
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
  List<WorkflowActions>? get actions;
  @override
  bool? get isStateUpdatable;
  @override
  @JsonKey(ignore: true)
  _$$WorkflowStateImplCopyWith<_$WorkflowStateImpl> get copyWith =>
      throw _privateConstructorUsedError;
}

WorkflowActions _$WorkflowActionsFromJson(Map<String, dynamic> json) {
  return _WorkflowActions.fromJson(json);
}

/// @nodoc
mixin _$WorkflowActions {
  List<String>? get roles => throw _privateConstructorUsedError;

  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;
  @JsonKey(ignore: true)
  $WorkflowActionsCopyWith<WorkflowActions> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $WorkflowActionsCopyWith<$Res> {
  factory $WorkflowActionsCopyWith(
          WorkflowActions value, $Res Function(WorkflowActions) then) =
      _$WorkflowActionsCopyWithImpl<$Res, WorkflowActions>;
  @useResult
  $Res call({List<String>? roles});
}

/// @nodoc
class _$WorkflowActionsCopyWithImpl<$Res, $Val extends WorkflowActions>
    implements $WorkflowActionsCopyWith<$Res> {
  _$WorkflowActionsCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? roles = freezed,
  }) {
    return _then(_value.copyWith(
      roles: freezed == roles
          ? _value.roles
          : roles // ignore: cast_nullable_to_non_nullable
              as List<String>?,
    ) as $Val);
  }
}

/// @nodoc
abstract class _$$WorkflowActionsImplCopyWith<$Res>
    implements $WorkflowActionsCopyWith<$Res> {
  factory _$$WorkflowActionsImplCopyWith(_$WorkflowActionsImpl value,
          $Res Function(_$WorkflowActionsImpl) then) =
      __$$WorkflowActionsImplCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call({List<String>? roles});
}

/// @nodoc
class __$$WorkflowActionsImplCopyWithImpl<$Res>
    extends _$WorkflowActionsCopyWithImpl<$Res, _$WorkflowActionsImpl>
    implements _$$WorkflowActionsImplCopyWith<$Res> {
  __$$WorkflowActionsImplCopyWithImpl(
      _$WorkflowActionsImpl _value, $Res Function(_$WorkflowActionsImpl) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? roles = freezed,
  }) {
    return _then(_$WorkflowActionsImpl(
      roles: freezed == roles
          ? _value._roles
          : roles // ignore: cast_nullable_to_non_nullable
              as List<String>?,
    ));
  }
}

/// @nodoc
@JsonSerializable()
class _$WorkflowActionsImpl implements _WorkflowActions {
  const _$WorkflowActionsImpl({final List<String>? roles}) : _roles = roles;

  factory _$WorkflowActionsImpl.fromJson(Map<String, dynamic> json) =>
      _$$WorkflowActionsImplFromJson(json);

  final List<String>? _roles;
  @override
  List<String>? get roles {
    final value = _roles;
    if (value == null) return null;
    if (_roles is EqualUnmodifiableListView) return _roles;
    // ignore: implicit_dynamic_type
    return EqualUnmodifiableListView(value);
  }

  @override
  String toString() {
    return 'WorkflowActions(roles: $roles)';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$WorkflowActionsImpl &&
            const DeepCollectionEquality().equals(other._roles, _roles));
  }

  @JsonKey(ignore: true)
  @override
  int get hashCode =>
      Object.hash(runtimeType, const DeepCollectionEquality().hash(_roles));

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$WorkflowActionsImplCopyWith<_$WorkflowActionsImpl> get copyWith =>
      __$$WorkflowActionsImplCopyWithImpl<_$WorkflowActionsImpl>(
          this, _$identity);

  @override
  Map<String, dynamic> toJson() {
    return _$$WorkflowActionsImplToJson(
      this,
    );
  }
}

abstract class _WorkflowActions implements WorkflowActions {
  const factory _WorkflowActions({final List<String>? roles}) =
      _$WorkflowActionsImpl;

  factory _WorkflowActions.fromJson(Map<String, dynamic> json) =
      _$WorkflowActionsImpl.fromJson;

  @override
  List<String>? get roles;
  @override
  @JsonKey(ignore: true)
  _$$WorkflowActionsImplCopyWith<_$WorkflowActionsImpl> get copyWith =>
      throw _privateConstructorUsedError;
}

Assigner _$AssignerFromJson(Map<String, dynamic> json) {
  return _Assigner.fromJson(json);
}

/// @nodoc
mixin _$Assigner {
  String? get emailId => throw _privateConstructorUsedError;
  int? get id => throw _privateConstructorUsedError;
  String? get mobileNumber => throw _privateConstructorUsedError;
  String? get name => throw _privateConstructorUsedError;
  String? get tenantId => throw _privateConstructorUsedError;
  String? get uuid => throw _privateConstructorUsedError;
  String? get userName => throw _privateConstructorUsedError;

  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;
  @JsonKey(ignore: true)
  $AssignerCopyWith<Assigner> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $AssignerCopyWith<$Res> {
  factory $AssignerCopyWith(Assigner value, $Res Function(Assigner) then) =
      _$AssignerCopyWithImpl<$Res, Assigner>;
  @useResult
  $Res call(
      {String? emailId,
      int? id,
      String? mobileNumber,
      String? name,
      String? tenantId,
      String? uuid,
      String? userName});
}

/// @nodoc
class _$AssignerCopyWithImpl<$Res, $Val extends Assigner>
    implements $AssignerCopyWith<$Res> {
  _$AssignerCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? emailId = freezed,
    Object? id = freezed,
    Object? mobileNumber = freezed,
    Object? name = freezed,
    Object? tenantId = freezed,
    Object? uuid = freezed,
    Object? userName = freezed,
  }) {
    return _then(_value.copyWith(
      emailId: freezed == emailId
          ? _value.emailId
          : emailId // ignore: cast_nullable_to_non_nullable
              as String?,
      id: freezed == id
          ? _value.id
          : id // ignore: cast_nullable_to_non_nullable
              as int?,
      mobileNumber: freezed == mobileNumber
          ? _value.mobileNumber
          : mobileNumber // ignore: cast_nullable_to_non_nullable
              as String?,
      name: freezed == name
          ? _value.name
          : name // ignore: cast_nullable_to_non_nullable
              as String?,
      tenantId: freezed == tenantId
          ? _value.tenantId
          : tenantId // ignore: cast_nullable_to_non_nullable
              as String?,
      uuid: freezed == uuid
          ? _value.uuid
          : uuid // ignore: cast_nullable_to_non_nullable
              as String?,
      userName: freezed == userName
          ? _value.userName
          : userName // ignore: cast_nullable_to_non_nullable
              as String?,
    ) as $Val);
  }
}

/// @nodoc
abstract class _$$AssignerImplCopyWith<$Res>
    implements $AssignerCopyWith<$Res> {
  factory _$$AssignerImplCopyWith(
          _$AssignerImpl value, $Res Function(_$AssignerImpl) then) =
      __$$AssignerImplCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call(
      {String? emailId,
      int? id,
      String? mobileNumber,
      String? name,
      String? tenantId,
      String? uuid,
      String? userName});
}

/// @nodoc
class __$$AssignerImplCopyWithImpl<$Res>
    extends _$AssignerCopyWithImpl<$Res, _$AssignerImpl>
    implements _$$AssignerImplCopyWith<$Res> {
  __$$AssignerImplCopyWithImpl(
      _$AssignerImpl _value, $Res Function(_$AssignerImpl) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? emailId = freezed,
    Object? id = freezed,
    Object? mobileNumber = freezed,
    Object? name = freezed,
    Object? tenantId = freezed,
    Object? uuid = freezed,
    Object? userName = freezed,
  }) {
    return _then(_$AssignerImpl(
      emailId: freezed == emailId
          ? _value.emailId
          : emailId // ignore: cast_nullable_to_non_nullable
              as String?,
      id: freezed == id
          ? _value.id
          : id // ignore: cast_nullable_to_non_nullable
              as int?,
      mobileNumber: freezed == mobileNumber
          ? _value.mobileNumber
          : mobileNumber // ignore: cast_nullable_to_non_nullable
              as String?,
      name: freezed == name
          ? _value.name
          : name // ignore: cast_nullable_to_non_nullable
              as String?,
      tenantId: freezed == tenantId
          ? _value.tenantId
          : tenantId // ignore: cast_nullable_to_non_nullable
              as String?,
      uuid: freezed == uuid
          ? _value.uuid
          : uuid // ignore: cast_nullable_to_non_nullable
              as String?,
      userName: freezed == userName
          ? _value.userName
          : userName // ignore: cast_nullable_to_non_nullable
              as String?,
    ));
  }
}

/// @nodoc
@JsonSerializable()
class _$AssignerImpl implements _Assigner {
  const _$AssignerImpl(
      {this.emailId,
      this.id,
      this.mobileNumber,
      this.name,
      this.tenantId,
      this.uuid,
      this.userName});

  factory _$AssignerImpl.fromJson(Map<String, dynamic> json) =>
      _$$AssignerImplFromJson(json);

  @override
  final String? emailId;
  @override
  final int? id;
  @override
  final String? mobileNumber;
  @override
  final String? name;
  @override
  final String? tenantId;
  @override
  final String? uuid;
  @override
  final String? userName;

  @override
  String toString() {
    return 'Assigner(emailId: $emailId, id: $id, mobileNumber: $mobileNumber, name: $name, tenantId: $tenantId, uuid: $uuid, userName: $userName)';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$AssignerImpl &&
            (identical(other.emailId, emailId) || other.emailId == emailId) &&
            (identical(other.id, id) || other.id == id) &&
            (identical(other.mobileNumber, mobileNumber) ||
                other.mobileNumber == mobileNumber) &&
            (identical(other.name, name) || other.name == name) &&
            (identical(other.tenantId, tenantId) ||
                other.tenantId == tenantId) &&
            (identical(other.uuid, uuid) || other.uuid == uuid) &&
            (identical(other.userName, userName) ||
                other.userName == userName));
  }

  @JsonKey(ignore: true)
  @override
  int get hashCode => Object.hash(
      runtimeType, emailId, id, mobileNumber, name, tenantId, uuid, userName);

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$AssignerImplCopyWith<_$AssignerImpl> get copyWith =>
      __$$AssignerImplCopyWithImpl<_$AssignerImpl>(this, _$identity);

  @override
  Map<String, dynamic> toJson() {
    return _$$AssignerImplToJson(
      this,
    );
  }
}

abstract class _Assigner implements Assigner {
  const factory _Assigner(
      {final String? emailId,
      final int? id,
      final String? mobileNumber,
      final String? name,
      final String? tenantId,
      final String? uuid,
      final String? userName}) = _$AssignerImpl;

  factory _Assigner.fromJson(Map<String, dynamic> json) =
      _$AssignerImpl.fromJson;

  @override
  String? get emailId;
  @override
  int? get id;
  @override
  String? get mobileNumber;
  @override
  String? get name;
  @override
  String? get tenantId;
  @override
  String? get uuid;
  @override
  String? get userName;
  @override
  @JsonKey(ignore: true)
  _$$AssignerImplCopyWith<_$AssignerImpl> get copyWith =>
      throw _privateConstructorUsedError;
}
