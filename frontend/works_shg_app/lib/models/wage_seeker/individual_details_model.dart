import 'dart:io';
import 'dart:typed_data';

import 'package:dart_mappable/dart_mappable.dart';
import 'package:works_shg_app/models/adharModel/adhar_response.dart';

part 'individual_details_model.mapper.dart';

@MappableClass()
class IndividualDetails with IndividualDetailsMappable {
  final String? aadhaarNo;
  final String? name;
  final String? fatherName;
  final String? relationship;
  final DateTime? dateOfBirth;
  final String? gender;
  final String? socialCategory;
  final String? mobileNumber;
  final File? imageFile;
  final Uint8List? bytes;
  final String? photo;
  final String? documentType;

  final bool? adharVerified;
  final int? timeStamp;
  final AdharCardResponse? adharCardResponse;

  IndividualDetails({
    this.aadhaarNo,
    this.name,
    this.fatherName,
    this.relationship,
    this.dateOfBirth,
    this.gender,
    this.socialCategory,
    this.mobileNumber,
    this.imageFile,
    this.bytes,
    this.photo,
    this.adharVerified,
    this.documentType,
    this.timeStamp,
    this.adharCardResponse
  });
}
