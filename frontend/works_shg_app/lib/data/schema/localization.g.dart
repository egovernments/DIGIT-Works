// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'localization.dart';

// **************************************************************************
// TypeAdapterGenerator
// **************************************************************************

class KeyLocaleModelAdapter extends TypeAdapter<KeyLocaleModel> {
  @override
  final int typeId = 0;

  @override
  KeyLocaleModel read(BinaryReader reader) {
    final numOfFields = reader.readByte();
    final fields = <int, dynamic>{
      for (int i = 0; i < numOfFields; i++) reader.readByte(): reader.read(),
    };
    return KeyLocaleModel()
      ..locale = fields[0] as String?
      ..localizationsList = (fields[1] as List?)?.cast<Localization>();
  }

  @override
  void write(BinaryWriter writer, KeyLocaleModel obj) {
    writer
      ..writeByte(2)
      ..writeByte(0)
      ..write(obj.locale)
      ..writeByte(1)
      ..write(obj.localizationsList);
  }

  @override
  int get hashCode => typeId.hashCode;

  @override
  bool operator ==(Object other) =>
      identical(this, other) ||
      other is KeyLocaleModelAdapter &&
          runtimeType == other.runtimeType &&
          typeId == other.typeId;
}

class LocalizationAdapter extends TypeAdapter<Localization> {
  @override
  final int typeId = 1;

  @override
  Localization read(BinaryReader reader) {
    final numOfFields = reader.readByte();
    final fields = <int, dynamic>{
      for (int i = 0; i < numOfFields; i++) reader.readByte(): reader.read(),
    };
    return Localization()
      ..code = fields[0] as String?
      ..message = fields[1] as String?
      ..module = fields[2] as String?
      ..locale = fields[3] as String?;
  }

  @override
  void write(BinaryWriter writer, Localization obj) {
    writer
      ..writeByte(4)
      ..writeByte(0)
      ..write(obj.code)
      ..writeByte(1)
      ..write(obj.message)
      ..writeByte(2)
      ..write(obj.module)
      ..writeByte(3)
      ..write(obj.locale);
  }

  @override
  int get hashCode => typeId.hashCode;

  @override
  bool operator ==(Object other) =>
      identical(this, other) ||
      other is LocalizationAdapter &&
          runtimeType == other.runtimeType &&
          typeId == other.typeId;
}
