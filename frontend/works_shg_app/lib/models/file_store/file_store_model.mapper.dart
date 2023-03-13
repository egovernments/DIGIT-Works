// coverage:ignore-file
// GENERATED CODE - DO NOT MODIFY BY HAND
// ignore_for_file: type=lint
// ignore_for_file: unused_element

part of 'file_store_model.dart';

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
      fileStoreId: container.$getOpt(map, 'fileStoreId'),
      tenantId: container.$getOpt(map, 'tenantId'));

  @override
  Function get encoder => encode;
  dynamic encode(FileStoreModel v) => toMap(v);
  Map<String, dynamic> toMap(FileStoreModel f) => {
        'fileStoreId': container.$enc(f.fileStoreId, 'fileStoreId'),
        'tenantId': container.$enc(f.tenantId, 'tenantId')
      };

  @override
  String stringify(FileStoreModel self) =>
      'FileStoreModel(fileStoreId: ${container.asString(self.fileStoreId)}, tenantId: ${container.asString(self.tenantId)}, id: ${container.asString(self.id)}, url: ${container.asString(self.url)})';
  @override
  int hash(FileStoreModel self) =>
      container.hash(self.fileStoreId) ^
      container.hash(self.tenantId) ^
      container.hash(self.id) ^
      container.hash(self.url);
  @override
  bool equals(FileStoreModel self, FileStoreModel other) =>
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
  $R call({String? fileStoreId, String? tenantId});
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
  $R call({Object? fileStoreId = $none, Object? tenantId = $none}) =>
      $then(FileStoreModel(
          fileStoreId: or(fileStoreId, $value.fileStoreId),
          tenantId: or(tenantId, $value.tenantId)));
}
