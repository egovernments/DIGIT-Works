// coverage:ignore-file
// GENERATED CODE - DO NOT MODIFY BY HAND
// ignore_for_file: type=lint
// ignore_for_file: unused_element

part of 'file_store_model.dart';

class FileStoreListModelMapper extends MapperBase<FileStoreListModel> {
  static MapperContainer container = MapperContainer(
    mappers: {FileStoreListModelMapper()},
  )..linkAll({FileStoreModelMapper.container});

  @override
  FileStoreListModelMapperElement createElement(MapperContainer container) {
    return FileStoreListModelMapperElement._(this, container);
  }

  @override
  String get id => 'FileStoreListModel';

  static final fromMap = container.fromMap<FileStoreListModel>;
  static final fromJson = container.fromJson<FileStoreListModel>;
}

class FileStoreListModelMapperElement
    extends MapperElementBase<FileStoreListModel> {
  FileStoreListModelMapperElement._(super.mapper, super.container);

  @override
  Function get decoder => decode;
  FileStoreListModel decode(dynamic v) =>
      checkedType(v, (Map<String, dynamic> map) => fromMap(map));
  FileStoreListModel fromMap(Map<String, dynamic> map) =>
      FileStoreListModel(fileStoreIds: container.$getOpt(map, 'fileStoreIds'));

  @override
  Function get encoder => encode;
  dynamic encode(FileStoreListModel v) => toMap(v);
  Map<String, dynamic> toMap(FileStoreListModel f) =>
      {'fileStoreIds': container.$enc(f.fileStoreIds, 'fileStoreIds')};

  @override
  String stringify(FileStoreListModel self) =>
      'FileStoreListModel(fileStoreIds: ${container.asString(self.fileStoreIds)})';
  @override
  int hash(FileStoreListModel self) => container.hash(self.fileStoreIds);
  @override
  bool equals(FileStoreListModel self, FileStoreListModel other) =>
      container.isEqual(self.fileStoreIds, other.fileStoreIds);
}

mixin FileStoreListModelMappable {
  String toJson() =>
      FileStoreListModelMapper.container.toJson(this as FileStoreListModel);
  Map<String, dynamic> toMap() =>
      FileStoreListModelMapper.container.toMap(this as FileStoreListModel);
  FileStoreListModelCopyWith<FileStoreListModel, FileStoreListModel,
          FileStoreListModel>
      get copyWith => _FileStoreListModelCopyWithImpl(
          this as FileStoreListModel, $identity, $identity);
  @override
  String toString() => FileStoreListModelMapper.container.asString(this);
  @override
  bool operator ==(Object other) =>
      identical(this, other) ||
      (runtimeType == other.runtimeType &&
          FileStoreListModelMapper.container.isEqual(this, other));
  @override
  int get hashCode => FileStoreListModelMapper.container.hash(this);
}

extension FileStoreListModelValueCopy<$R, $Out extends FileStoreListModel>
    on ObjectCopyWith<$R, FileStoreListModel, $Out> {
  FileStoreListModelCopyWith<$R, FileStoreListModel, $Out>
      get asFileStoreListModel =>
          base.as((v, t, t2) => _FileStoreListModelCopyWithImpl(v, t, t2));
}

typedef FileStoreListModelCopyWithBound = FileStoreListModel;

abstract class FileStoreListModelCopyWith<$R, $In extends FileStoreListModel,
    $Out extends FileStoreListModel> implements ObjectCopyWith<$R, $In, $Out> {
  FileStoreListModelCopyWith<$R2, $In, $Out2>
      chain<$R2, $Out2 extends FileStoreListModel>(
          Then<FileStoreListModel, $Out2> t, Then<$Out2, $R2> t2);
  ListCopyWith<$R, FileStoreModel,
          FileStoreModelCopyWith<$R, FileStoreModel, FileStoreModel>>?
      get fileStoreIds;
  $R call({List<FileStoreModel>? fileStoreIds});
}

class _FileStoreListModelCopyWithImpl<$R, $Out extends FileStoreListModel>
    extends CopyWithBase<$R, FileStoreListModel, $Out>
    implements FileStoreListModelCopyWith<$R, FileStoreListModel, $Out> {
  _FileStoreListModelCopyWithImpl(super.value, super.then, super.then2);
  @override
  FileStoreListModelCopyWith<$R2, FileStoreListModel, $Out2>
      chain<$R2, $Out2 extends FileStoreListModel>(
              Then<FileStoreListModel, $Out2> t, Then<$Out2, $R2> t2) =>
          _FileStoreListModelCopyWithImpl($value, t, t2);

  @override
  ListCopyWith<$R, FileStoreModel,
          FileStoreModelCopyWith<$R, FileStoreModel, FileStoreModel>>?
      get fileStoreIds => $value.fileStoreIds != null
          ? ListCopyWith(
              $value.fileStoreIds!,
              (v, t) => v.copyWith.chain<$R, FileStoreModel>($identity, t),
              (v) => call(fileStoreIds: v))
          : null;
  @override
  $R call({Object? fileStoreIds = $none}) => $then(
      FileStoreListModel(fileStoreIds: or(fileStoreIds, $value.fileStoreIds)));
}

class FileStoreModelMapper extends MapperBase<FileStoreModel> {
  static MapperContainer container = MapperContainer(
    mappers: {FileStoreModelMapper()},
  );

  @override
  FileStoreModelMapperElement createElement(MapperContainer container) {
    return FileStoreModelMapperElement._(this, container);
  }

  @override
  String get id => 'FileStoreModel';

  static final fromMap = container.fromMap<FileStoreModel>;
  static final fromJson = container.fromJson<FileStoreModel>;
}

class FileStoreModelMapperElement extends MapperElementBase<FileStoreModel> {
  FileStoreModelMapperElement._(super.mapper, super.container);

  @override
  Function get decoder => decode;
  FileStoreModel decode(dynamic v) =>
      checkedType(v, (Map<String, dynamic> map) => fromMap(map));
  FileStoreModel fromMap(Map<String, dynamic> map) => FileStoreModel(
      name: container.$getOpt(map, 'name'),
      id: container.$getOpt(map, 'id'),
      url: container.$getOpt(map, 'url'),
      fileStoreId: container.$getOpt(map, 'fileStoreId'),
      tenantId: container.$getOpt(map, 'tenantId'));

  @override
  Function get encoder => encode;
  dynamic encode(FileStoreModel v) => toMap(v);
  Map<String, dynamic> toMap(FileStoreModel f) => {
        'name': container.$enc(f.name, 'name'),
        'id': container.$enc(f.id, 'id'),
        'url': container.$enc(f.url, 'url'),
        'fileStoreId': container.$enc(f.fileStoreId, 'fileStoreId'),
        'tenantId': container.$enc(f.tenantId, 'tenantId')
      };

  @override
  String stringify(FileStoreModel self) =>
      'FileStoreModel(name: ${container.asString(self.name)}, fileStoreId: ${container.asString(self.fileStoreId)}, tenantId: ${container.asString(self.tenantId)}, id: ${container.asString(self.id)}, url: ${container.asString(self.url)})';
  @override
  int hash(FileStoreModel self) =>
      container.hash(self.name) ^
      container.hash(self.fileStoreId) ^
      container.hash(self.tenantId) ^
      container.hash(self.id) ^
      container.hash(self.url);
  @override
  bool equals(FileStoreModel self, FileStoreModel other) =>
      container.isEqual(self.name, other.name) &&
      container.isEqual(self.fileStoreId, other.fileStoreId) &&
      container.isEqual(self.tenantId, other.tenantId) &&
      container.isEqual(self.id, other.id) &&
      container.isEqual(self.url, other.url);
}

mixin FileStoreModelMappable {
  String toJson() =>
      FileStoreModelMapper.container.toJson(this as FileStoreModel);
  Map<String, dynamic> toMap() =>
      FileStoreModelMapper.container.toMap(this as FileStoreModel);
  FileStoreModelCopyWith<FileStoreModel, FileStoreModel, FileStoreModel>
      get copyWith => _FileStoreModelCopyWithImpl(
          this as FileStoreModel, $identity, $identity);
  @override
  String toString() => FileStoreModelMapper.container.asString(this);
  @override
  bool operator ==(Object other) =>
      identical(this, other) ||
      (runtimeType == other.runtimeType &&
          FileStoreModelMapper.container.isEqual(this, other));
  @override
  int get hashCode => FileStoreModelMapper.container.hash(this);
}

extension FileStoreModelValueCopy<$R, $Out extends FileStoreModel>
    on ObjectCopyWith<$R, FileStoreModel, $Out> {
  FileStoreModelCopyWith<$R, FileStoreModel, $Out> get asFileStoreModel =>
      base.as((v, t, t2) => _FileStoreModelCopyWithImpl(v, t, t2));
}

typedef FileStoreModelCopyWithBound = FileStoreModel;

abstract class FileStoreModelCopyWith<$R, $In extends FileStoreModel,
    $Out extends FileStoreModel> implements ObjectCopyWith<$R, $In, $Out> {
  FileStoreModelCopyWith<$R2, $In, $Out2>
      chain<$R2, $Out2 extends FileStoreModel>(
          Then<FileStoreModel, $Out2> t, Then<$Out2, $R2> t2);
  $R call(
      {String? name,
      String? id,
      String? url,
      String? fileStoreId,
      String? tenantId});
}

class _FileStoreModelCopyWithImpl<$R, $Out extends FileStoreModel>
    extends CopyWithBase<$R, FileStoreModel, $Out>
    implements FileStoreModelCopyWith<$R, FileStoreModel, $Out> {
  _FileStoreModelCopyWithImpl(super.value, super.then, super.then2);
  @override
  FileStoreModelCopyWith<$R2, FileStoreModel, $Out2>
      chain<$R2, $Out2 extends FileStoreModel>(
              Then<FileStoreModel, $Out2> t, Then<$Out2, $R2> t2) =>
          _FileStoreModelCopyWithImpl($value, t, t2);

  @override
  $R call(
          {Object? name = $none,
          Object? id = $none,
          Object? url = $none,
          Object? fileStoreId = $none,
          Object? tenantId = $none}) =>
      $then(FileStoreModel(
          name: or(name, $value.name),
          id: or(id, $value.id),
          url: or(url, $value.url),
          fileStoreId: or(fileStoreId, $value.fileStoreId),
          tenantId: or(tenantId, $value.tenantId)));
}
